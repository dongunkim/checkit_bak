var searchParams = {};	//트리 조회화면 조건 파라미터

(function($){

	$.tree = $.tree ||{}, $.extend($.tree,{
        version : "1.0.1"
	}),
	$.fn.extend({
		/**
		 * 트리 호출
		 * @param option(object) : 트리 옵션모음
		 */
		tree : function(option){
			if(option.url != "/admin/ipmg/ipmg0201List.do"){
				searchParams = option.paramData.searchParams;
			}
			option.targetId = $(this).selector.replace("#", "");

			var targetLen = $("#" + option.targetId).length;

			if(targetLen == 0){
				toast.push("트리 [" + option.targetId + "] TARGET ID가 없습니다.");
				return false;
			}else if(targetLen > 1){
				toast.push("중복된 트리 [" + option.targetId + "] TARGET ID가 있습니다.");
				return false;
			}

			if($.type(option.search) !== "undefined"){
				option.search.targetId = option.targetId;
				treeUtils.search(option.search);
			}else{
				treeUtils.treeInit(option);
				this.setTreeOption(option.targetId, option);
			}

		}
		,
		/**
		 * 트리 리로드
		 */
		treeReload : function(){

			let targetId          = $(this).selector.replace("#", "");
			let clickObject       = $("[data-click=\"Y\"]");
			let clickObjectCode   = $("[data-click=\"Y\"]").data("code");
			let clickObjectUpCode = $("[data-click=\"Y\"]").data("up-code");
			let clickObjectUp     = $("[data-code=\"" + clickObjectUpCode + "\"]").next();
			let removeObject      = clickObjectUp.next().find("li");
			let nodeData          = JSON.parse(decodeURIComponent(clickObjectUp.data("node")));
			let option = $("#" + targetId).getTreeOption();
				option.paramData       = nodeData;
				option.targetArea      = clickObjectUp;
				option.searchWord      = clickObjectCode

			clickObjectUp.removeClass().addClass("treeloading");
			treeUtils.getAjaxTreeData(option, function(treeList){
				clickObjectUp.removeClass().addClass("expanded");
				removeObject.remove();
				treeUtils.setSearchNode(treeList, option);
			});

		}
		,
		/**
		 * 트리옵션 저장
		 */
		setTreeOption : function(targetId, option){

			if($.type(Window.setTreeOption) === "undefined"){
				Window.setTreeOption = [];
			}else{
				Window.setTreeOption;
			}

			Window.setTreeOption[targetId] = option;

		}
		,
		/**
		 * 트리옵션 취득
		 */
		getTreeOption : function(){
			let targetId = $(this).selector.replace("#", "");
			return Window.setTreeOption[targetId];
		}
	})

})(jQuery);

treeUtils ={
		/**
		 * 트리 초기화및 공통환경추가
		 */
		treeInit : function(option){
			let _this = this;
			let initTreeAreaHtml = [];
				initTreeAreaHtml.push("<div class=\"tree_container\" style=\"height:" + (option.height + 5) + "px;width: 320px;\">");
				initTreeAreaHtml.push("<div class=\"tree_content\" style=\"top: 0px; height:" + (option.height + 5) + "px;border: 1px solid #b1b1b1;\">");
				initTreeAreaHtml.push("<div class=\"list_button_area\" style=\"position:absolute; top:0; right:20px; z-index:99\">");
				initTreeAreaHtml.push("<div>");
				initTreeAreaHtml.push("<button class=\"button_sh w50 red\" id=\"treeAllOpenBtn\"><span style=\"font-size:24px; font-weight:bold; line-height:30px;\">+</span></button>");
				initTreeAreaHtml.push("</div>");
				initTreeAreaHtml.push("<div style=\"margin-top:10px\">");
				initTreeAreaHtml.push("<button class=\"button_sh w50 black\" id=\"treeAllCloseBtn\"><span style=\"font-size:24px; font-weight:bold; line-height:30px;\">-</span></button>");
				initTreeAreaHtml.push("</div>");
				initTreeAreaHtml.push("</div>");
				initTreeAreaHtml.push("<ul style=\"overflow:auto; height:" + (option.height + 3) + "px;\" class=\"tree_area\" id=\"" + option.targetId + "Area\">");
				initTreeAreaHtml.push("</ul>");
				initTreeAreaHtml.push("</div>");
				initTreeAreaHtml.push("</div>");

			$("#" + option.targetId).empty().append(initTreeAreaHtml.join(""));
			this.getAjaxTreeData(option);

			let allOpen = false;
			// 전체열기
			$("#treeAllOpenBtn").unbind().bind("click", function(){
				let targetId = option.targetId;

				if(allOpen){
					$.each($("#" + targetId).find(".collapsed"), function(){
						$(this).removeClass().addClass("expanded");
						$(this).next().show();
					});
				}else{
					let clickObj;
					let nodeAjaxCall = function(){
						clickObj = [];
						$.each($("#" + targetId).find(".collapsed"), function(){
							let thisObject = $(this);
							if(thisObject.next().html() == ""){
								clickObj.push(thisObject);
							}else{
								$(this).removeClass().addClass("expanded");
								thisObject.next().show();
							}
						});

						if(clickObj.length == 0){
							clearInterval(timer);
							allOpen = true;
							return false;
						}

						$.each(clickObj, function(){
							let thisObject = this;
							let nodeData = JSON.parse(decodeURIComponent(thisObject.data("node")));
								thisObject.removeClass().addClass("treeloading");
								option.paramData = nodeData;
							_this.getAjaxTreeData(option, function(treeList){
								thisObject.removeClass().addClass("expanded");
								option.targetArea = thisObject;
								_this.setNode(treeList, option);
							});
						});

					};
					let timer = setInterval(nodeAjaxCall, 800);
				}

			});

			// 전체 닫기
			$("#treeAllCloseBtn").unbind().bind("click", function(){
				let targetId = option.targetId;
				$.each($("#" + targetId).find(".expanded"), function(){
					$(this).removeClass().addClass("collapsed");
					$(this).next().hide();
				});
			});
			
			setTimeout(function() {

				let queryObject= core.queryObject();
				if(typeof queryObject.code !== "undefined" && queryObject.code !== ""){
					treeUtils.treeOpen(queryObject.code);
				}
	
			}, 500);

		}
		,
		treeOpen : function(code){

			let targetId = "tree_target";
			let option = $("#" + targetId).getTreeOption();
			$.each($("#tree_targetArea").find("li > span"), function(){
				let thisObject = $(this);
				let nodeData = JSON.parse(decodeURIComponent(thisObject.data("node")));
				if(nodeData.code == code){
					
					if($.type(option.callBackFn) === "function"){
						option.callBackFn(nodeData);
					}
					
					if(thisObject.hasClass("collapsed")){
						if(thisObject.next().html() == ""){
							thisObject.removeClass().addClass("treeloading");
							option.paramData = nodeData;
							option.targetArea = thisObject;
		
							treeUtils.getAjaxTreeData(option, function(treeList){
								thisObject.removeClass().addClass("expanded");
								treeUtils.setNode(treeList, option);
							});
		
						}else{
							thisObject.removeClass().addClass("expanded");
							thisObject.next().show();
						}
		
					}else{
						thisObject.removeClass().addClass("collapsed");
						thisObject.next().hide();
					}
			
				}
			
			});
			
		},
		/**
		 * 트리 노드 삽입
		 */
		setNode : function(treeList, option){
			let _this = this;
			let targetId 	= option.targetId;
			let targetArea 	= option.targetArea;
			let clickNodeNm = option.clickNodeNm;
			let isTextColor = ($.type(option.textColor) === "undefined")?false:true;
			let textColorKey;
			let textColorStartLvl;
			let textColor = {};

			if(isTextColor){
				textColorKey      = option.textColor.key;
				textColorStartLvl = option.textColor.startLevel;
				$.each(option.textColor.data, function(idx, data){
					textColor[data.value] = data.color;
				});
			}

			$.each(treeList, function(idx, data){
				let setTreeHtml = [];
				setTreeHtml.push("<li>");
				if(isTextColor && data.lvl >= textColorStartLvl){
					setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" " + (($.type(textColor[data[textColorKey]]) === "undefined")?"":"style=\"color:" + textColor[data[textColorKey]] + ";\"") + ">" + data.codeName + "</a>");
				}else{
					setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" >" + data.codeName + "</a>");
				}

				if(data.isDown == "Y"){
					setTreeHtml.push("<span class=\"collapsed\" data-node=\"" + encodeURIComponent(JSON.stringify(data)) + "\"></span>");
				}
				setTreeHtml.push("<ul>");
				setTreeHtml.push("</ul>");
				setTreeHtml.push("</li>");

				let trHtml = $(setTreeHtml.join(""));

				trHtml.find("[data-code]").unbind().bind("click", function(){
					if($.type(option.callBackFn) === "function"){
						option.callBackFn(data);
					}

					$("[data-click=\"Y\"]").css({backgroundColor: ""});
					$("[data-click=\"Y\"]").removeAttr("data-click");
					$(this).css({backgroundColor: treeUtils.colors.basic});
					$(this).attr("data-click", "Y");
				});

				$.each(trHtml.find("[data-node]"), function(){
					$(this).unbind().bind("click", function(){
						let thisObject = $(this);
						if(thisObject.hasClass("collapsed")){

							if(thisObject.next().html() == ""){

								let nodeData = JSON.parse(decodeURIComponent(thisObject.data("node")));
								thisObject.removeClass().addClass("treeloading");
								option.paramData = nodeData;
								option.targetArea = thisObject;

								_this.getAjaxTreeData(option, function(treeList){
									thisObject.removeClass().addClass("expanded");
									_this.setNode(treeList, option);
								});

							}else{
								thisObject.removeClass().addClass("expanded");
								thisObject.next().show();
							}

						}else{
							thisObject.removeClass().addClass("collapsed");
							thisObject.next().hide();
						}

					});

				});

				if($.type(option.targetArea) == "undefined"){
					$("#" + option.targetId + "Area").append(trHtml);
				}else{
					option.targetArea.next().show().append(trHtml);
				}

			});

		}
		,
		/**
		 * 트리 노드 삽입 및 검색
		 */
		setSearchNode : function(treeList, option){
			let _this = this;
			let targetId 	    = option.targetId;
			let targetArea 	    = option.targetArea;
			let clickNodeNm     = option.clickNodeNm;
			let isTextColor = ($.type(option.textColor) === "undefined")?false:true;
			let textColorKey;
			let textColorStartLvl;
			let textColor = {};
			let searchWord      = option.searchWord;
			let searchStartWord = option.searchStartWord;
			let preSearchStartWord = option.preSearchStartWord;
			let nextNodeArr = [];

			if(isTextColor){
				textColorKey      = option.textColor.key;
				textColorStartLvl = option.textColor.startLevel;
				$.each(option.textColor.data, function(idx, data){
					textColor[data.value] = data.color;
				});
			}

			$.each(treeList, function(idx, data){
				let setTreeHtml = [];
				setTreeHtml.push("<li>");
				if(data.code == searchWord){
					if(isTextColor && data.lvl >= textColorStartLvl){
						setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" data-click=\"Y\" style=\"background-color: #d7d7d7 !important;" + (($.type(textColor[data[textColorKey]]) === "undefined")?"":"color:" + textColor[data[textColorKey]] + ";") + "\">" + data.codeName + "</a>");
					}else{
						setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" style=\"background-color: #d7d7d7 !important;\">" + data.codeName + "</a>");
					}
				}else{
					if(isTextColor && data.lvl >= textColorStartLvl){
						setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" " + (($.type(textColor[data[textColorKey]]) === "undefined")?"":"style=\"color:" + textColor[data[textColorKey]] + ";\"") + ">" + data.codeName + "</a>");
					}else{
						setTreeHtml.push("<a href=\"javascript:void(0)\" data-up-code=\"" + data.upCode + "\" data-code=\"" + data.code + "\" data-lvl=\"" + data.lvl + "\" >" + data.codeName + "</a>");
					}
				}
				if(data.isDown == "Y"){
					setTreeHtml.push("<span class=\"collapsed\" data-node=\"" + encodeURIComponent(JSON.stringify(data)) + "\"></span>");
				}
				setTreeHtml.push("<ul>");
				setTreeHtml.push("</ul>");
				setTreeHtml.push("</li>");

				let trHtml = $(setTreeHtml.join(""));

				trHtml.find("[data-code]").unbind().bind("click", function(){
					if($.type(option.callBackFn) === "function"){
						option.callBackFn(data);
					}

					$("[data-click=\"Y\"]").css({backgroundColor: ""});
					$("[data-click=\"Y\"]").removeAttr("data-click");
					$(this).css({backgroundColor: treeUtils.colors.basic});
					$(this).attr("data-click", "Y");

				});

				$.each(trHtml.find("[data-node]"), function(){
					$(this).unbind().bind("click", function(){
						let thisObject = $(this);
						if(thisObject.hasClass("collapsed")){

							if(thisObject.next().html() == ""){
								let nodeData = JSON.parse(decodeURIComponent(thisObject.data("node")));
								thisObject.removeClass().addClass("treeloading");
								option.paramData = nodeData;
								option.targetArea = thisObject;

								_this.getAjaxTreeData(option, function(treeList){
									thisObject.removeClass().addClass("expanded");
									_this.setNode(treeList, option);
								});

							}else{
								thisObject.removeClass().addClass("expanded");
								thisObject.next().show();
							}

						}else{
							thisObject.removeClass().addClass("collapsed");
							thisObject.next().hide();
						}

					});

				});

				if($.type(option.targetArea) == "undefined"){
					$("#" + option.targetId + "Area").append(trHtml);
				}else{
					option.targetArea.next().show().append(trHtml);
				}

				if(data.code.indexOf(searchStartWord) > -1){
					nextNodeArr.push($("[data-code=\"" + data.code + "\"]").next());
				}
				
				if(data.code.indexOf(preSearchStartWord) > -1 && preSearchStartWord != ""){
					nextNodeArr.push($("[data-code=\"" + data.code + "\"]").next());
				}

			});

			if($("[data-click=\"Y\"]").length > 0){
				$("[data-click=\"Y\"]").focus();
			}else{
				if(nextNodeArr.length > 0){

					$.each(nextNodeArr, function(idx, nextNode){
						let nodeOption = $.extend({}, option);
						if(nextNode.hasClass("collapsed")){
							if(nextNode.next().html() == ""){
								let nodeData = JSON.parse(decodeURIComponent(nextNode.data("node")));
								nextNode.removeClass().addClass("treeloading");
								nodeOption.paramData = nodeData;
								nodeOption.targetArea = nextNode;

								_this.getAjaxTreeData(nodeOption, function(treeList){
									nextNode.removeClass().addClass("expanded");
									_this.setSearchNode(treeList, nodeOption);
								});
							}else{
								nextNode.removeClass().addClass("expanded");
								nextNode.next().show();
							}
						}

					});

				}
			}

		}
		,
		/**
		 * ajax 트리
		 */
		getAjaxTreeData : function(option, callBackFn){
			let _this  = this;
			let targetId = option.targetId;
			let params = {};

				if($.type(option.paramData) === "object"){
					params = option.paramData;
				}else{
					params[option.clickNodeNm] = option.paramData;
				}

			// 배선관리 > 현황조회 > 위치별 배선 장비 내역 조회 화면 ajax 트리 실행시 검색조건 파라미터 추가
			if(option.url == "/admin/wire/wire0601TreeList.do"){
				let paramKeys = Object.keys(searchParams);
				for(i = 0; i < paramKeys.length; i++){
					let key = paramKeys[i];
					params[key] = searchParams[key];
				}
			}
			
			let ajaxOption = {
					url: option.url,
					data: ($(params) === "object")?JSON.stringify(params):params,
					type: "post",
					cache: false,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					success: function(data) {
						let treeList = data.result.list;
						if($.type(callBackFn) === "function"){
							callBackFn(treeList);
						}else{
							_this.setNode(treeList, option);
						}
					},
					error: function(req, status, error) {
					},
					beforeSend: function(xhr){
						xhr.setRequestHeader("ajax", true);
						setTimeout(function(){
								utils.loading(true);
						}, 50);
					},
					complete: function(){
						setTimeout(function(){
								utils.loading(false);
						}, 50);
					}
			};
			$.ajax(ajaxOption);
		}
		/**
		 * 검색
		 */
		,search : function(searchOption){

			let _this = this;
			let targetId = searchOption.targetId;
			let option = $("#" + targetId).getTreeOption();
			let searchStartWord = searchOption.searchStartWord;
			let preSearchStartWord = searchOption.preSearchStartWord;
			let searchWord      = searchOption.searchWord;
			nodeClickFn = function(thisObject){
				let nodeOption = $.extend({}, option);
				// 열려 있는 노드중 검색
				let searchNodeObj = $("[data-code=\"" + searchWord + "\"]");
				if(searchNodeObj.length > 0){
					$.each($("#" + targetId).find(".collapsed"), function(){
						if($(this).next().html() != ""){
							$(this).removeClass().addClass("expanded");
							$(this).next().show();
						}
					});
					$("[data-click=\"Y\"]").css({backgroundColor: ""});
					$("[data-click=\"Y\"]").removeAttr("data-click");
					searchNodeObj.css({backgroundColor: treeUtils.colors.basic});
					searchNodeObj.attr("data-click", "Y");
					searchNodeObj.focus();
				}else{
					if(thisObject.hasClass("collapsed")){
						if(thisObject.next().html() == ""){
							let nodeData = JSON.parse(decodeURIComponent(thisObject.data("node")));
							thisObject.removeClass().addClass("treeloading");
							nodeOption.paramData = nodeData;
							nodeOption.targetArea = thisObject;
							nodeOption.searchWord = searchWord;
							nodeOption.searchStartWord = searchStartWord;
							nodeOption.preSearchStartWord = preSearchStartWord;
							
							_this.getAjaxTreeData(nodeOption, function(treeList){
								thisObject.removeClass().addClass("expanded");
								_this.setSearchNode(treeList, nodeOption);
							});
						}else{
							thisObject.removeClass().addClass("expanded");
							thisObject.next().show();
						}
					}else{
							let nextNodeArr = [];
							$.each($("[data-code]"), function(idx, data){
								let dataCode = $(this).data("code");
								if(dataCode.toString().indexOf(searchStartWord) > -1){
									nextNodeArr.push($("[data-code=\"" + dataCode + "\"]").next());
								}else if(dataCode.toString().indexOf(preSearchStartWord) > -1 && preSearchStartWord != ""){
									nextNodeArr.push($("[data-code=\"" + dataCode + "\"]").next());
								}
							});

							if(nextNodeArr.length > 0){

								$.each(nextNodeArr, function(idx, nextNode){
									let nodeOption = $.extend({}, option);
									if(nextNode.hasClass("collapsed")){
										if(nextNode.next().html() == ""){
											let nodeData = JSON.parse(decodeURIComponent(nextNode.data("node")));
											nextNode.removeClass().addClass("treeloading");
											nodeOption.paramData = nodeData;
											nodeOption.targetArea = nextNode;
											nodeOption.searchWord = searchWord;
											nodeOption.searchStartWord = searchStartWord;

											_this.getAjaxTreeData(nodeOption, function(treeList){
												nextNode.removeClass().addClass("expanded");
												_this.setSearchNode(treeList, nodeOption);
											});
										}else{
											nextNode.removeClass().addClass("expanded");
											nextNode.next().show();
										}
									}

								});

							}
					}
				}
			}

			if($("[data-click=\"Y\"]").length > 0){

				$("[data-click=\"Y\"]").css({backgroundColor: ""});
				$("[data-click=\"Y\"]").removeAttr("data-click");

				let searchNodeObj = $("[data-code=\"" + searchWord + "\"]");
				if(searchNodeObj.length > 0){
					$.each($("#" + targetId).find(".collapsed"), function(){
						if($(this).next().html() != ""){
							$(this).removeClass().addClass("expanded");
							$(this).next().show();
						}
					});
					$("[data-click=\"Y\"]").css({backgroundColor: ""});
					$("[data-click=\"Y\"]").removeAttr("data-click");
					searchNodeObj.css({backgroundColor: treeUtils.colors.basic});
					searchNodeObj.attr("data-click", "Y");
					searchNodeObj.focus();
				}else{
					let nextNodeArr = [];
					$.each($("[data-code]"), function(idx, data){
						let dataCode = $(this).data("code");
						if(dataCode.toString().indexOf(searchStartWord) > -1){
							nextNodeArr.push($("[data-code=\"" + dataCode + "\"]").next());
						}else if(dataCode.toString().indexOf(preSearchStartWord) > -1 && preSearchStartWord != ""){
							nextNodeArr.push($("[data-code=\"" + dataCode + "\"]").next());
						}
					});

					if(nextNodeArr.length > 0){

						$.each(nextNodeArr, function(idx, nextNode){
							let nodeOption = $.extend({}, option);
							if(nextNode.hasClass("collapsed")){
								if(nextNode.next().html() == ""){
									let nodeData = JSON.parse(decodeURIComponent(nextNode.data("node")));
									nextNode.removeClass().addClass("treeloading");
									nodeOption.paramData = nodeData;
									nodeOption.targetArea = nextNode;
									nodeOption.searchWord = searchWord;
									nodeOption.searchStartWord = searchStartWord;

									_this.getAjaxTreeData(nodeOption, function(treeList){
										nextNode.removeClass().addClass("expanded");
										_this.setSearchNode(treeList, nodeOption);
									});
								}else{
									nextNode.removeClass().addClass("expanded");
									nextNode.next().show();
								}
							}

						});

					}
				}

			}else{
				$.each($("[data-lvl=\"1\"]"), function(){
					nodeClickFn($(this).next("[data-node]"));
				});
			}

		}
		,
		colors : {
			 red    : "#ff0000 !important"
			,orange : "#ffa500 !important"
			,yellow : "#ffff00 !important"
			,green  : "#008000 !important"
			,blue   : "#0000ff !important"
			,purple : "#800080 !important"
			,basic  : "#d7d7d7 !important"
		}
}