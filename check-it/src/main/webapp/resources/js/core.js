
const DIALOG = new ax5.ui.dialog({
	title: "경고"
});

var popupOptionArr = [];
var isSetParam = false;
var popupCloseCallback = {
	page : [],
	put : function(_p){
		this.page.push(_p);
	},
	executeLast:function(){
		if(this.page.length > 0){
			p = this.page.pop();
			utils.triggerSanRackUnit(p);
			this.page = []; //init
		}
	}
};

(function($){

	$.core = $.core ||{}, $.extend($.core,{
        version : "1.0.1"
	}),
	$.fn.extend({
		serializeObject : function(){
			let obj = null;
			    try {
			        if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
			            let arr = this.serializeArray();
			            if (arr) {
			                obj = {};
			                jQuery.each(arr, function() {
			                    obj[this.name] = this.value;
			                });
			            }//if ( arr ) {
			        }
			    } catch (e) {
			        alert(e.message);
			    } finally {
			    }

			    return obj;
		}
		,
		/**
		 * 리스트 호출
		 * @param list
		 * @param option(object) : 리스트 옵션모음
		 */
		list : function(option, paramList){
			
			utils.loading(true);
			sessionTimer = setInterval(function(){

				if(isSetParam){
					clearInterval(sessionTimer);
					utils.loading(false);
				}

			}, 300);

			if($.type(option.ajax) !== "undefined" && option.ajax){
				this.ajaxList(option);
				return false;
			}

			let list = result.list;
			let total = result.total.cnt;
			let params = result.param;
			let targetId = $(this).selector.replace("#", "");
			let callBackFn = option.callBackFn;
			let _this = this;

			if($.type(paramList) !== "undefined"){
				list = paramList;
			}

			if($.type(list) !== "array"){
				toast.push("데이터를 확인하세요! 배열이 아닙니다.");
				return false;
			}

			let codeLen = 0;

			_this.makeTable(option, list, total, params, targetId);
			_this.makePaging(total, params);
			_this.makeSort(targetId, params);

			if($.type(callBackFn) === "function"){
				callBackFn(list);
			}

			// 검색영역 버튼
			$.each($(".input_button_area").find("button"), function(){
				if($.type($(this).attr("id")) === "undefined"){
					$(this).unbind().bind("click", function(){
						let params = {};
							params.search = "Y";
						core.movePage(params);

					});
				}
			});

			$("#searchAllBtn").unbind().bind("click", function(){
				core.allSearchMovePage();
			});

			// 엔터 이벤트 처리
			$.each($(".input_area").find("input"), function(idx, data){
				if(!$(this).hasClass("input_day")){
					$(this).unbind().bind("keydown", function(event){
						if(event.keyCode == 13){
							let params = {};
								params.search = "Y";
							core.movePage(params);
						}
					});
				}
			});
//			$(".input_area").find("input").unbind().bind("keydown", function(event){
//				if(event.keyCode == 13){
//					let params = {};
//						params.search = "Y";
//					core.movePage(params);
//				}
//			});

			// csv저장  버튼
			$("#csvDownBtn").unbind().bind("click", function(){
				const url = document.location.pathname;

				let params = {};
					params = result.param;
					params.csvDownload = "Y";
					params.fileName = $("h2").text();
					params.headList = JSON.stringify(option.colGroup);

				var ajaxOption = {
                    url: url,
                    data: (typeof params === "object")?$.param(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
                    success: function(data) {
                    	if(data.result.csvDownload == 'N'){
                    		DIALOG.alert({
        						title: "알림",
        						msg: data.result.errorMessage
        					});
                    	}else{
	                    	var csvFormStr = [];
								csvFormStr.push("<form action=\"/common/csvDownload.do\" method=\"post\">");
								csvFormStr.push("</form>");
							var csvDownloadForm = $(csvFormStr.join(""));
								csvDownloadForm.appendTo("body");
								csvDownloadForm.submit().remove();
                    	}

                    },
                    error: function(req, status, error) {
                    },
                    beforeSend: function(xhr){
    	            	xhr.setRequestHeader("ajax", true);
    	            	core.loading(true);
		    		},
		            complete: function(){
		            	core.loading(false);
				    }
                };

				$.ajax(ajaxOption);
			});

			// 검색영역 파라메터 처리
			setTimeout(function(){
				let inputParams = result.param;
				if($.type(inputParams.search) !== "undefined"){
					if(inputParams.search == "Y"){
						$.each(inputParams, function(idx, val){

							let tagName = $("[name='" + idx + "']").prop("tagName");;

							if($.type(tagName) !== "undefined"){
								$("[name='" + idx + "']").val(val).trigger("change");

								if($("[name='" + idx + "']").val() != val){
									setTimeout(function(){
										$("[name='" + idx + "']").val(val).trigger("change");
										if($("[name='" + idx + "']").val() != val){
											setTimeout(function(){
												$("[name='" + idx + "']").val(val).trigger("change");
											}, 300);
										}
									}, 300);
								}
							}

						});

						isSetParam = true;

					}else{
						isSetParam = true;
					}
				}else{
					isSetParam = true;
				}
			}, 300);

		}
		,
		ajaxList : function(option){

			let _this  = this;
			let url    = option.url;
			let params = option.params;
			let targetId = $(this).selector.replace("#", "");
			let callBackFn = option.callBackFn;

			var ajaxOption = {
                    url: url,
                    data: (typeof params === "object")?$.param(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
                    success: function(data, status, xhr) {
                    	if(xhr.responseText == "{\"result\":{}}"){ //세션이 끊어져서 ajax 통신 후 result값이 아예 없이 들어오는경우
                    		location.href = "/admin/login/login0101Page.do" ; //로그아웃 창으로 튕겨버림
                    	}else{
                    	let list = data.result.list;
            			let total = data.result.total.cnt;
            			let params = data.result.param;
            			if($.type(list) !== "array"){
            				toast.push("데이터를 확인하세요! 배열이 아닙니다.");
            				return false;
            			}

            			_this.makeTable(option, list, total, params, targetId);
            			_this.makePaging(total, params);


            			if($.type(callBackFn) === "function"){
            				callBackFn(data);
            			}

            			// 검색영역 버튼
						$.each($(".input_button_area").find("button"), function(){
							if($.type($(this).attr("id")) === "undefined"){
								$(this).unbind().bind("click", function(){
									$(this).attr("type", "button");
		            					if($("#ajaxSearchForm").length == 0){
		            						$(this).parents(".input_area").wrap("<form id=\"ajaxSearchForm\" action=\"\"></form>");
		            					}
		            					option.params = $("#ajaxSearchForm").serializeObject()
		            				_this.ajaxList(option);
								});
							}
						});

						$("#searchAllBtn").unbind().bind("click", function(){
							if($("#ajaxSearchForm").length == 0){
        						$(this).parents(".input_area").wrap("<form id=\"ajaxSearchForm\" action=\"\"></form>");
        					}
							$("#ajaxSearchForm")[0].reset();
							option.params = $("#ajaxSearchForm").serializeObject()
							$(".input_area").unwrap();
							_this.ajaxList(option);
						});
                    }
                },
                error: function(req, status, error) {
                },
                beforeSend: function(xhr){
	            	xhr.setRequestHeader("ajax", true);
	            	core.loading(true);
	    		},
	            complete: function(){
	            	core.loading(false);
			    }
            };
		_this.setInstance("ajaxInfo", option);
        $.ajax(ajaxOption);

		},
		
		makeSort : function(targetId, param){
			//940 공통 코드에 등록된 URL 만 sort 기능 추가
			let params1 = {};
			params1.code = "940";
			params1.type = "string";
	
			var cUrl = window.location.pathname;
			var cUrlCnt = 0;	
			utils.getCommonCode(params1,function(urlResult){

				$.each(urlResult, function(idx, data){
					if (cUrl.indexOf(data.cname) != -1) {
						cUrlCnt++;
					}
				});
				
				if(cUrlCnt > 0){
					var th = $("#" + targetId).find("thead > tr > th");
			
					th.css("cursor","pointer");
					th.append("<span style=font-size:11px;margin-left:3px;color:#E13C2C>▼</span>");
					th.attr("sortType","desc");
					
					if($.type(param) !== "undefined"){
						if(param.sort != ''){
							var thKey = $("#"+targetId).find("thead > tr > th[key='"+param.sort+"']");
							if(param.sortType == "desc"){
								thKey.attr("sortType","desc");
								thKey.find("span").html("▼");
							} else {
								thKey.attr("sortType","asc");
								thKey.find("span").html("▲");
							}
						}
					}
					
					th.bind("click", function(e){
						var sort = $(this).attr("key");
						var sortType = $(this).attr("sortType");
						
						$("#sort").val(sort);
						if(sortType == "desc"){
							$("#sortType").val("asc");
							let params = {};
							params.search = "Y";
							core.movePage(params);
						} else {
							$("#sortType").val("desc");
							let params = {};
							params.search = "Y";
							core.movePage(params);
						}
						
					});
				}	
			});
		},
		
		makeTable : function(option, list, total, params, targetId){

			let _this = this;
			let isHeader = ($.type(option.viewHeader) === "undefined")?true:option.header;
			let colSpan = option.colGroup.length;
			_this.setInstance("option", option);
			$("#" + targetId).empty();

			// head 생성
			if(isHeader){
				let theadTr = $("#" + targetId).append("<colgroup></colgroup>").append("<thead><tr></tr></head>");

				let col, th;

				if(option.isNo){
					col = $("<col>", {
						 width : "35px"
					});

					th = $("<th>", {
						 text : "NO"
					});

					theadTr.find("tr").append(th);
					theadTr.find("colgroup").append(col);
				}

				$.each(option.colGroup, function(colIndex, colData){

					col = $("<col>", {
						 width : colData.width + "%"
					});

					th = $("<th>", {
						 text : colData.label,
					     key : colData.key
					});

					if(colData.type == "allCheckbox"){

						// 모바일일경우 체크박스 크기
						if(utils.isMobile()){
							col = $("<col>", {
								 width : "8%"
							});
						}else{
							col = $("<col>", {
								 width : "5%"
							});
						}

						let checkbox = [];

						checkbox.push("<th>");
						checkbox.push("<label class=\"check\">");
						checkbox.push("<input type=\"checkbox\" id=\"" + targetId + "allCheckbox\"/>");
						checkbox.push("<span></span>");
						checkbox.push("</label>");
						checkbox.push("</th>");

						theadTr.find("tr").append(checkbox.join(""));

						$("#" + targetId + "allCheckbox").unbind().bind("click", function(){
							let checkboxThis = this;
							let checkboxObj = $("input[name='" + targetId + "allCheckbox']");
							$.each(checkboxObj, function(){
								if($(this).attr("disabled") != "disabled"){
									$(this).prop("checked", checkboxThis.checked);
								}
							})
						});

					}else{
						theadTr.find("tr").append(th);
					}

					theadTr.find("colgroup").append(col);

				});
			}

			if(total > 0){

				$(".pagecount > em").text(core.defaultString(total, "0").money());

				_this.setInstance("list", list);
				let tbody = $("#" + targetId).append("<tbody></tbody>");
				// body 생성
				$.each(list, function(listIndex, listData){
					// index 추가
					if($.type(list[listIndex].index) === "undefined"){
						list[listIndex].index = listIndex;
					}

					tbody.append("<tr></tr>");

					if(option.isNo){
						let noTd = $("<td>", {
							text : total - (listIndex - (1 - params.pageNo.number()) * 10)
							,css : {textAlign : "center"}
						});
						$("#" + targetId + " > tbody").find("tr").eq(listIndex).append(noTd);
					}
					$.each(option.colGroup, function(colIndex, colData){

						$.each(listData, function(key, value){

							let type = colData.type;
							value = core.defaultString(value);

							if(key == colData.key){

								if($.type(type) === "string"){

									switch(type){

										case "string" :
											value = value.toString();
										break;
										case "number" :
											value = value.number();
										break;
										case "phone" :
											value = value.phone();
										break;
										case "date" :
											if(core.defaultString(value) == ""){
												value = "";
											}else{
												value = value.date().print();
											}
										break;
										case "datetime" :
											if(core.defaultString(value) == ""){
												value = "";
											}else{

												if(value.number() == 0){
													value = "";
												}else{
													value = value.date().print("yyyy-mm-dd hh:mi:ss");
												}

											}
										break;
										case "datetime2" :
											if(core.defaultString(value) == ""){
												value = "";
											}else{
												if(value.number() == 0){
													value = "";
												}else{
													value = value.date().print("yyyy-mm-dd hh:mi");
												}

											}
										break;
										case "datetime3" :
											if(core.defaultString(value) == ""){
												value = "";
											}else{
												if(value.number() == 0){
													value = "";
												}else{
													value = value.date().print("yyyy-mm-dd");
												}

											}
										break;
										case "time" :
											if(utils.defaultString(value) == ""){
												value = "";
											}else{
												if(value.length == 4){
													value = value.substring(0,2) + ":" + value.substring(2,4);
												}
											}
										break;
										case "comma" :
											value = value.money();
										break;
										default:
											value = value;
									}

								}

								let td = $("<td>", {
									 text : value
									,css : {textAlign : colData.align}
								});

								if(colData.type == "allCheckbox"){

									let checkbox = [];
										checkbox.push("<td>");
										checkbox.push("<label class=\"check\">");
										checkbox.push("<input type=\"checkbox\" name=\"" + targetId + "allCheckbox\" value=\"" + value + "\"/>");
										checkbox.push("<span></span>");
										checkbox.push("</label>");
										checkbox.push("</td>");

									td = checkbox.join("");

								}

								if($.type(colData.code) === "boolean"){
									td.attr("data-code", value.toString());
								}

								if($.type(option.allClick) === "function"){
									let tr = $("#" + targetId + " > tbody").find("tr").eq(listIndex);
									tr.css("cursor", "pointer");
									tr.unbind().bind("click", function(event){
										if(!$(event.target).parents("label").hasClass("check")){
											$.extend(option, this);
											option.allClick(listData);
										}
									});
								}else{
									if($.type(colData.click) === "function"){
										td.css("cursor", "pointer");
										td.unbind().bind("click", function(){
											colData.click(listData);
										});
									}
								}

								if($.type(colData.html) === "function"){
									let rtnFn = colData.html(listData);
									td.empty().html(rtnFn);
								}

								$("#" + targetId + " > tbody").find("tr").eq(listIndex).append(td);

								if($.type(colData.disabled) === "function"){
									if(colData.disabled(listData)){
										$("input[name='" + targetId + "allCheckbox']").eq(listIndex).attr("disabled", "disabled").parent().removeClass().addClass("check_dis");
									}
								}

								if($.type(colData.checked) === "function"){
									if(colData.checked(listData)){
										$("input[name='" + targetId + "allCheckbox']").eq(listIndex).prop("checked", "checked");
									}
								}

								// 공통코드 처리
								if($.type(colData.code) === "boolean"){

									let code     = value.toString().substring(0, value.toString().length - 3);
									let codeData = _this.getInstance("codeData_" + code);

									if($.type(codeData) === "undefined"){
										_this.setCode(code, function(){
											codeData = _this.getInstance("codeData_" + code);
											if(codeData.isCode){
												$.each(codeData.data, function(index, data){
													if(value == data.code){
														$("[data-code=\"" + value.toString() + "\"]").text(data.cname);
														return false;
													}
												});
											}
										});
									}else{
										if(codeData.isCode){
											$.each(codeData.data, function(index, data){
												if(value == data.code){
													$("[data-code=\"" + value.toString() + "\"]").text(data.cname);
													return false;
												}
											});
										}
									}

								}

							}
						});
					});
				});

			}else{
				if(option.isNo){
					colSpan += 1;
				}
				$("#" + targetId).append("<tbody><tr style=\"text-align:center;\"><td colSpan=\"" + colSpan + "\">조회된 내역이 없습니다.</td></tr></tbody>")
			}

		},
		/**
		 * 페이징 처리
		 */
		makePaging : function(total, params){

			let targetId = $(this).selector.replace("#", "");
			if(total > 0 && params.isPaging){

				var pageSelectYn = false; 
				
				let params1 = {};
				params1.code = "939";
				params1.type = "string";
		
				var cUrl = window.location.pathname;
				var cUrlCnt = 0;	
				utils.getCommonCode(params1,function(urlResult){

					$.each(urlResult, function(idx, data){
						
						if (cUrl.indexOf(data.cname) != -1) {
							cUrlCnt++;
							//pageSelectYn=false;
						} else {
							//pageSelectYn=true;
						}
						
					});
					
					if(cUrlCnt > 0){
						pageSelectYn=false;
					} else {
						pageSelectYn=true;
					}
					
					let pageSize  = 10;   // 페이지에 보여줄 row
					if(typeof params.pageSize == "undefined"){
						pageSize = 10;
					} else {
						pageSize  = params.pageSize;
					}
					
					let pageNo    = params.pageNo.number();     // 현재 페이지번호
					let pageCount = total.number(); //총건수
	
					let totalPageNo = Math.ceil(pageCount / pageSize); // 총페이지 번 호
					let lastNo      = (pageNo + 10 > totalPageNo)?totalPageNo:pageNo + 10;
					let startPageNo = (Math.floor((pageNo - 1)/10) * 10) + 1;
					let endPageNo = ((startPageNo + 10 <= totalPageNo)?startPageNo + 10:totalPageNo + 1);
					let pagingHtml = [];
	
						if(totalPageNo == 1){
							pagingHtml.push("<div class=\"count_number paging\"><span class=\"d_left_a\" style=\"cursor:pointer;\"></span>");
						}else{
							pagingHtml.push("<div class=\"count_number paging\"><span class=\"d_left_a\" onclick=\"core.paging(1, '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
						}
	
						if(startPageNo - 10 < 0){
							if((pageNo - 1) == 0){
								pagingHtml.push("<span class=\"left_a\"></span>");
							}else{
								pagingHtml.push("<span class=\"left_a\" onclick=\"core.paging(" + (pageNo - 1) + ", '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
							}
						}else{
							pagingHtml.push("<span class=\"left_a\" onclick=\"core.paging(" + (startPageNo - 10) + ", '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
						}
	
						pagingHtml.push("<ul>");
	
						for(let i = startPageNo; i < endPageNo; i++){
	
							if(i == pageNo){
								pagingHtml.push("<li class=\"active\">" + i + "</li>");
							}else{
								pagingHtml.push("<li onclick=\"core.paging(" + i + ", '" + targetId + "')\">" + i + "</li>");
							}
	
						}
	
						pagingHtml.push("</ul>");
	
						if(startPageNo + 10 > totalPageNo){
							if((pageNo + 1) > totalPageNo){
								pagingHtml.push("<span class=\"right_a\"></span>");
							}else{
								pagingHtml.push("<span class=\"right_a\" onclick=\"core.paging(" + (pageNo + 1) + ", '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
							}
						}else{
							pagingHtml.push("<span class=\"right_a\" onclick=\"core.paging(" + (startPageNo + 10) + ", '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
						}
	
						if(totalPageNo == 1){
							pagingHtml.push("<span class=\"d_right_a\" style=\"cursor:pointer;\"></span>");
						}else{
							pagingHtml.push("<span class=\"d_right_a\" onclick=\"core.paging(" + totalPageNo + ", '" + targetId + "')\" style=\"cursor:pointer;\"></span>");
						}
						
						if(pageSelectYn){
							pagingHtml.push("<select class=\"select_txt_small\" id=\"pageSize\" name=\"pageSize\">");
							pagingHtml.push("<option value=\"10\">10</option>");
							pagingHtml.push("<option value=\"20\">20</option>");
							pagingHtml.push("<option value=\"50\">50</option>");
							pagingHtml.push("<option value=\"100\">100</option>");
							pagingHtml.push("</select>");	
						}
						pagingHtml.push("</div>");
						
					$.each($(".count_number"), function(){
						if(!$(this).hasClass("paging")){
							$(this).remove();
						}
					});
					if($("#" + targetId).next().hasClass("paging")){
						$("#" + targetId).next().remove();
						$("#" + targetId).after(pagingHtml.join(""));
					}else{
						$("#" + targetId).after(pagingHtml.join(""));
					}
					
					if(pageSelectYn){
						$("#pageSize").change().bind("change", function(){
							if($("#pageSize").val() != params.pageSize){
								let params = {};
								params.search = "Y";
								core.movePage(params);	
							}
						});
						if($.type(result.param.pageSize) !== "undefined"){
							$('#pageSize').val(result.param.pageSize);	
						}
						
					}

				});

			}else{
				if($("#" + targetId).next().hasClass("paging")){
					$("#" + targetId).next().remove();
				}
//				$.each($(".count_number"), function(){
//					if(!$(this).hasClass("paging")){
//						$(this).remove();
//					}
//				});
//				$(".count_number.paging").remove();
			}

			if(utils.isMobile()){
				$(".count_number > ul > li").css({width:"15px", height:"30px", margin:"0 1px"});
				$(".count_number > span.d_left_a").css({backgroundSize: "20px", marginTop: "5px"});
				$(".count_number > span.left_a").css({backgroundSize: "20px", marginTop: "5px"});
				$(".count_number > span.d_right_a").css({backgroundSize: "20px", marginTop: "5px"});
				$(".count_number > span.right_a").css({backgroundSize: "20px", marginTop: "5px"});
				$(".count_number > span").css({width:"20px", height:"30px"});
			}
			
			// 버튼 클릭시
			/*$("#pageSize").change().bind("change", function(){
				if(typeof params.pageSize !== "undefined"){
					if($("#pageSize").val() != params.pageSize){
						let params = {};
						params.search = "Y";
						core.movePage(params);	
					}
				}
			});*/
		},
		/**
		 * 데이터 수정
		 */
		setRowData : function(index, jsonData){
			let list = this.getInstance("list");
			let setData = $.extend({}, list[index], jsonData);
			list[index] = setData;
			this.setInstance("list", list);
		},
		/**
		 * 리스트 정보
		 */
		getList : function(callbackFn){
			let list = this.getInstance("list");

			if(typeof callbackFn === "function"){
				callbackFn(checkedList);
			}

			return list;
		},
		/**
		 * 리스트 추가
		 */
		setList : function(option, addList){
			let targetId = $(this).selector.replace("#", "");
			this.setInstance("list", addList);
			this.setInstance("option", option);

			let total = addList.length;
			let params = {};

			if($.type(addList) !== "array"){
				toast.push("데이터를 확인하세요! 배열이 아닙니다.");
				return false;
			}

			this.makeTable(option, addList, total, params, targetId);
//			this.makePaging(total, params);

			if($.type(option.callBackFn) === "function"){
				option.callBackFn(addList);
			}
		},
		/**
		 * 기존 리스트에 리스트 추가
		 */
		addList : function(option, addList){
			let targetId = $(this).selector.replace("#", "");
			let tableList = $("#" + targetId).getInstance("list");
			let list = $.merge(tableList, addList);
			// 중복 제거
			list = list.filter(function(a) {
				let key = Object.keys(a).map(function(k){
					return a[k];
				}).join("|");

				if(!this[key]){
					return this[key] = true;
				}
			}, Object.create(null));

			this.setInstance("list", list);
			this.setInstance("option", option);

			let total = list.length;
			let params = {};

			if($.type(list) !== "array"){
				toast.push("데이터를 확인하세요! 배열이 아닙니다.");
				return false;
			}

			this.makeTable(option, list, total, params, targetId);
//			this.makePaging(total, params);

			if($.type(option.callBackFn) === "function"){
				option.callBackFn(list);
			}
		},
		/**
		 * 리스트 삭제
		 */
		deleteList : function(delList, callBackFn){
			let targetId = $(this).selector.replace("#", "");
			let list = $("#" + targetId).getInstance("list");
			let option = $("#" + targetId).getInstance("option");

			$.each(delList.reverse(), function(idx, data){
				list.splice(data.index, 1);
			});

			list = list.filter(function(a) {
				let key = Object.keys(a).map(function(k){
					return a[k];
				}).join("|");

				if(!this[key]){
					return this[key] = true;
				}
			}, Object.create(null));

			this.setInstance("list", list)
			let total = list.length;
			let params = {};

			if($.type(list) !== "array"){
				toast.push("데이터를 확인하세요! 배열이 아닙니다.");
				return false;
			}
			this.makeTable(option, list, total, params, targetId);
			this.makePaging(total, params);

			if($.type(option.callBackFn) === "function"){
				option.callBackFn(list);
			}
			if($.type(callBackFn) === "function"){
				callBackFn(list);
			}
		},
		/**
		 * 코드 등록
		 */
		setCode : function(pcode, callackFn){

			let _this = this;
			let targetId = $(this).selector.replace("#", "");
			let params = {};
				params.code = pcode;

	        let option = {
	                url: "/common/getCommonCode.do",
	                data: (typeof params === "object")?JSON.stringify(params):params,
	                type: "post",
	                cache: false,
	                dataType: "json",
	                contentType: "application/json; charset=UTF-8;",
	                success: function(data) {

	                	let isCode = false;
	                	if(data.result.list.length > 0){
	                		isCode = true;
	                	}

	                	let codeLen = 0;
	                	_this.setInstance("codeData_" + pcode, {isCode: isCode, data: data.result.list});

	                	callackFn();
	                },
	                error: function(req, status, error) {
	                },
	                beforeSend: function(xhr){
	                	xhr.setRequestHeader("ajax", true);
		    		},
		            complete: function(){
				    }
	            };
	        $.ajax(option);

		},
		/**
		 * 인스턴스 생성
		 */
		setInstance : function(type, data){

			let targetId = $(this).selector.replace("#", "");

			let instance;
			if($.type(Window.setInstance) === "undefined"){
				instance = Window.setInstance = [];
			}else{
				instance = Window.setInstance;
			}

			let	instanceData;
			if($.type(instance[targetId]) === "undefined"){
				instanceData = instance[targetId] = [];
			}else{
				instanceData = instance[targetId];
			}
				instanceData[type] = data;

		},
		/**
		 * 인스턴스 취득
		 */
		getInstance : function(type){

			let targetId = $(this).selector.replace("#", "");
			let data;
			if($.type(Window.setInstance) === "undefined"){
				data = [];
			}else{
				if($.type(Window.setInstance[targetId]) === "undefined"){
					data = [];
				}else{
					data = Window.setInstance[targetId][type];
				}
			}
			return data;

		},
		/**
		 * 조회 함수
		 */
		search : function(params, gridInitCallBackFn){


		},
		/**
		 * 체크된 항목 데이터값 취득
		 */
		getCheckedList : function(callbackFn){

			let targetId = $(this).selector.replace("#", "");
			let list = $("#" + targetId).getInstance("list");
			let checkedList = [];

			$.each($("input[name='" + targetId + "allCheckbox']"), function(idx, val){
				if(this.checked){
					list[idx].index = idx;
					checkedList.push(list[idx]);
				}
			});

			if(typeof callbackFn === "function"){
				callbackFn(checkedList);
			}

			return checkedList;
		}
		,
		/**
		 * 폼생성후 submit
		 */
		formSubmit : function(options, valiObj, callback){
			if($.type(valiObj) === "function"){
				callback = valiObj;
				valiObj = [];
			}
			if(!this.validator(valiObj)){
				return false;
			}else{

				let targetId = $(this).selector.replace("#", "");
				let params = options.params;

				if($.type(params) === "undefined"){
					params = {};
				}

				if(options.msg == ""){
					return false;
				}
				if(options.title == ""){
					return false;
				}

				DIALOG.confirm({
					title: (($.type(options.title) === "undefined")?"알림":options.title),
					msg: (($.type(options.msg) === "undefined")?"등록하시겠습니까?":options.msg)
				}, function () {
					if (this.key == "ok") {
						if($("#"+ targetId + "Form").length == 0){

							$("#" + targetId).wrapAll("<form id=\"" + targetId + "Form\" name=\"" + targetId + "Form\" action=\"" + options.url + "\" method=\"post\"></form>");
							let str = "";

							if(Object.keys(params).length > 0){

								$.each(params, function(i, v){

									let t = typeof(v);

									if(t === "string"){

										if(params.hasOwnProperty(i)){

											if($("#" + i).length == 0){
												str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
											}else{
												let tagName = $("#" + i).prop("tagName").toUpperCase();
												if(tagName == "INPUT"){
													$("#" + i).val(v);
												}else{
													str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
												}
											}
										}

									}else{

										if(params.hasOwnProperty(i)){
											if($("#" + i).length == 0){
												$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
												$("#" + i).val(JSON.stringify(v));
											}else{
												let tagName = $("#" + i).prop("tagName").toUpperCase();
												if(tagName == "INPUT"){
													$("#" + i).val(JSON.stringify(v));
												}else{
													$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
													$("#" + i).val(JSON.stringify(v));
												}
											}
										}

									}

								});

							}

							// 일단 파일업로드form이 단건이란 가정하에 작성
							if($("#"+ targetId + "Form").find("[data-file-gbn]").length > 0){

								let uploadTargetId = $("#"+ targetId + "Form").find("[data-file-gbn]").attr("id");
								let uploadFiles = $("#" + uploadTargetId).getInstance("uploadFiles");

								if($.type(uploadFiles) !== "undefined"){
									let files = [];
									$.each(uploadFiles, function(index, data){
										if(data.result.fileList[0].cud != "U"){
											data.result.fileList[0].boardType = $("#boardType").val();
											files.push(data.result.fileList[0]);
										}
									});

									let fileList = {attachFileList:files};

									$.each(fileList, function(i, v){

										let t = typeof(v);

										if(t === "string"){

											if(fileList.hasOwnProperty(i)){

												if($("#" + i).length == 0){
													str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
												}else{
													$("#" + i).val(v);
												}
											}

										}else{

											if(fileList.hasOwnProperty(i)){
												if($("#" + i).length == 0){
													$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
													$("#" + i).val(JSON.stringify(v));
												}else{
													$("#" + i).val(JSON.stringify(v));
												}
											}

										}
									});
								}

							}

							$("#" + targetId + "Form").append(str);

						}else{

							let str = "";

							if(Object.keys(params).length > 0){

								let i, v;
								$.each(params, function(i, v){
									t = typeof(v);

									if(t === "string"){

										if(params.hasOwnProperty(i)){

											if($("#" + i).length == 0){
												str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
											}else{
												let tagName = $("#" + i).prop("tagName").toUpperCase();
												if(tagName == "INPUT"){
													$("#" + i).val(v);
												}else{
													str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
												}
											}
										}

									}else{

										if(params.hasOwnProperty(i)){
											if($("#" + i).length == 0){
												$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
												$("#" + i).val(JSON.stringify(v));
											}else{
												let tagName = $("#" + i).prop("tagName").toUpperCase();
												if(tagName == "INPUT"){
													$("#" + i).val(JSON.stringify(v));
												}else{
													$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
													$("#" + i).val(JSON.stringify(v));
												}
											}
										}

									}
								})

							}

							if($("#"+ targetId + "Form").find("[data-file-gbn]").length > 0){

								let uploadTargetId = $("#"+ targetId + "Form").find("[data-file-gbn]").attr("id");
								let uploadFiles = $("#" + uploadTargetId).getInstance("uploadFiles");

								if($.type(uploadFiles) !== "undefined"){
									let files = [];
									$.each(uploadFiles, function(index, data){
										data.result.fileList[0].fileGbn = $("#fileGbn").val();
										files.push(data.result.fileList[0]);
									});

									let fileList = {attachFileList:files};

									$.each(fileList, function(i, v){
										let t = typeof(v);

										if(t === "string"){

											if(fileList.hasOwnProperty(i)){

												if($("#" + i).length == 0){
													str += "<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\" value=\"" + v + "\"/>";
												}else{
													$("#" + i).val(v);
												}
											}

										}else{

											if(fileList.hasOwnProperty(i)){
												if($("#" + i).length == 0){
													$("#" + targetId + "Form").append("<input type=\"hidden\" id=\"" + i + "\" name=\"" + i + "\"/>");
													$("#" + i).val(JSON.stringify(v));
												}else{
													$("#" + i).val(JSON.stringify(v));
												}
											}

										}
									});
								}

							}

							// 에디터가 있을경우 에디터의 데이터도 form에 추가
							/*
							if(typeof window.AXEditor_instances != "undefined"){
								$.each(window.AXEditor_instances, function(index, klass){

									let editId = klass.config.targetID;
									let editContent = klass.getContent();

									if(klass.getContentCheck()){
										editContent = utils.replaceAll(editContent, '"', "&quot;");
									}else{
										editContent = "";
									}
									if($("[name='" + editId + "']").length == 0){
										str += "<input type=\"hidden\" name=\"" + editId + "\" value=\"" + editContent + "\"/>";
									}else{
										$("[name='" + editId + "']").val(editContent);
									}

								});
							}
							*/

							$("#" + targetId + "Form").append(str);
							$("#" + targetId + "Form").attr("action", options.url);

						}

						$("#" + targetId + "Form").ajaxForm({
													 dataType: "json"
													,type: "post"
													,error: function(e, m){
														utils.loading(false);
														console.log(e, m);
													}
													,beforeSerialize: function(data){
														// form을 직렬화하기전 엘레먼트의 속성을 수정할 수도 있다.
													}
													,beforeSend: function(xhr){
														xhr.setRequestHeader("ajax", true);
														setTimeout(function() {
															utils.loading(true);
														},10);
													}
													,beforeSubmit: function(data, form, option) {
														utils.loading(true);
													}
													,success: function(response) {
														// 컨트롤러 실행 후 성공시 넘어옴
														if(typeof callback === "function"){
															callback(response.result);
														}else if(typeof callback === "string"){
															new Function(callback + "('" + response.result + "')")();
														}
														utils.loading(false);
													}

						});

						$("#" + targetId + "Form").submit();
						$("#" + targetId + "Form").contents().unwrap();
					}
				});
			}

		}
		,
		csvDownloadFn : function(option){

			// csv저장  버튼
			$($(this).selector).unbind().bind("click", function(){
				const url = option.url;

				let params = $.type(option.params) === "undefined"?{}:option.params;
					params.csvDownload = "Y";
					params.fileName = $("h2").text();
					params.headList = JSON.stringify(option.colGroup);

				var ajaxOption = {
                    url: url,
                    data: (typeof params === "object")?$.param(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
                    success: function(data) {
                    	if(data.result.csvDownload == 'N'){
                    		DIALOG.alert({
        						title: "알림",
        						msg: data.result.errorMessage
        					});
                    	}else{
	                    	var csvFormStr = [];
								csvFormStr.push("<form action=\"/common/csvDownload.do\" method=\"post\">");
								csvFormStr.push("</form>");
							var csvDownloadForm = $(csvFormStr.join(""));
								csvDownloadForm.appendTo("body");
								csvDownloadForm.submit().remove();
                    	}

                    },
                    error: function(req, status, error) {
                    },
                    beforeSend: function(xhr){
    	            	xhr.setRequestHeader("ajax", true);
    	            	core.loading(true);
		    		},
		            complete: function(){
		            	core.loading(false);
				    }
                };

				$.ajax(ajaxOption);
			});

		}
		,
		/**
		 * 파일업로드폼
		 */
		fileUploadForm : function(uploadOption){
			
			let targetId = $(this).selector.replace("#", "");
			let targetObj = $("#" + targetId);
			targetObj.empty();

			let __type__ = uploadOption.type;

			uuid = function(){
				function s4() {
					return ((1 + Math.random()) * 0x10000 | 0).toString(16).substring(1);
				}
				return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4() + s4() + s4();
			}

			let fileGbn = $("[data-file-gbn]").attr("data-file-gbn");
			if($.type(fileGbn) === "undefined"){
				toast.push("data-file-gbn이 존재 하지 않습니다.");
			}

			let fileuploadHtml = [];
			// 등록 또는 수정일경우
			if(__type__ === "attachInsert" || __type__ === "attachUpdate"){

				fileuploadHtml.push("<div class=\"dregArea\" id=\"dragover\">");
				fileuploadHtml.push("<table width=\"100%\" height=\"100%\">");
				fileuploadHtml.push("<tr>");
				fileuploadHtml.push("<td valign=\"middle\" align=\"center\"><h1>드래그한 파일을 여기에 올리세요.</h1></td>");
				fileuploadHtml.push("</tr>");
				fileuploadHtml.push("</table>");
				fileuploadHtml.push("</div>");
				fileuploadHtml.push("<div class=\"DH20\"></div>");
				fileuploadHtml.push("<div data-ax5uploader=\"" + targetId + "Uploader\" style=\"margin-bottom: 5px;\">");
				fileuploadHtml.push("<button data-ax5uploader-button=\"selector\" class=\"btn btn-primary\">파일선택</button> <font style=\"color:red;font-size:17px;\">(업로드 제한 파일사이즈 20MB)</font>");
				fileuploadHtml.push("<div><font style=\"color:blue;font-size:17px;\">파일을 끌어다 놓으세요.</font></div>");
				fileuploadHtml.push("</div>");

			}
				fileuploadHtml.push("<div data-ax5grid=\"" + targetId + "Grid\" style=\"height: 175px;\"></div>");

			if(__type__ === "attachInsert" || __type__ === "attachUpdate"){

				fileuploadHtml.push("<div style=\"padding:0;margin-top: 5px;\" data-btn-wrap>");
				fileuploadHtml.push("<button class=\"btn btn-default\" data-upload-btn=\"removeFile\">파일삭제</button>");
				fileuploadHtml.push("<input type=\"hidden\" name=\"fileuploadUUID\" id=\"fileuploadUUID\" value=\"" + uuid() + "\">");
				fileuploadHtml.push("<input type=\"hidden\" name=\"fileGbn\" id=\"fileGbn\" value=\"" + fileGbn + "\">");
				fileuploadHtml.push("</div>");

			}

				targetObj.append(fileuploadHtml.join(""));

			let gridConfig = {};

			// 기본 컬럼정의
			let _columns = [
							{key: "fileName", label: "파일명", width: "70%"},
							{key: "fileSize", label: "파일사이즈", width: "8%", align: "right", formatter: function () {
								return ax5.util.number(this.value, {byte: true});
							}},
							{key: "createdAt", label: "등록일시", width: "16%", align: "center", formatter: function () {
								return ax5.util.date(this.value, {"return": "yyyy/MM/dd hh:mm:ss"});
							}}
							];

			// 등록 또는 수정일경우
			if(__type__ === "attachInsert" || __type__ === "attachUpdate"){

				_columns[0].width = "76%";

				$.extend(gridConfig, {header: {align: "center"}, showLineNumber: false, showRowSelector: true, multipleSelect: true, lineNumberColumnWidth: 40, rowSelectorColumnWidth: 27})

				let DRAGOVER = $("#dragover");

				// upload
				targetObj.uploader = new ax5.ui.uploader({
					//debug: true,
					target: $("[data-ax5uploader=\"" + targetId + "Uploader\"]"),
					form: {
						 action: "/common/fileupload.do"
						,fileName: "file"
						,uuid: $("#fileuploadUUID").val()
						,attachId: core.defaultString(uploadOption.attachId, "0")
					},
					multiple: true,
					dropZone: {
						target: $(document.body),
						onclick: function () {
							// 사용을 원하는 경우 구현
							return;
							if(!this.self.selectFile()) {
								console.log("파일 선택 지원 안됨");
							}
						},
						ondragover: function () {
							//this.self.$dropZone.addClass("dragover");
							DRAGOVER.show();
							DRAGOVER.on("dragleave", function () {
								DRAGOVER.hide();
							});
						},
						ondragout: function () {
							//this.self.$dropZone.removeClass("dragover");
						},
						ondrop: function () {
							DRAGOVER.hide();
						}
					},
					validateSelectedFiles: function () {
						if(this.selectedFiles.length == 1){
//							if($.type(this.selectedFiles[0].name) === "undefined"){
//								DIALOG.alert("파일을 확인후 다시 시도해주세요.");
//								return false;
//							}
							if(this.selectedFiles[0].length == 0){
								DIALOG.alert("파일을 확인후 다시 시도해주세요.");
								return false;
							}
						}
						// 10개 이상 업로드 되지 않도록 제한.
						let fileList = targetObj.getInstance("uploadFiles");
						if($.type(fileList) === "undefined"){
							fileList = [];
						}
						let len = 0;
						$.each(fileList, function(idx, data){
							let fileObj = data.result.fileList[0];
							if(fileObj.cud != "D" && fileObj.cud != "ID"){
								len++;
							}
						});

						if(len + this.selectedFiles.length > 10){
							DIALOG.alert("파일은 10개 이상 올리실수 없습니다.");
							return false;
						}else{
							return true;
						}
					},
					onuploaderror: function () {
						DIALOG.alert("파일업로드중 오류가 발생하였습니다.");
						return false;
					},
					onuploadComplete: function () {
						let _this = this;
						let dt = "".date().print("yyyy-mm-dd hh:mi:ss");
						let fileArr = [];
						let ckUploadFiles = this.self.uploadedFiles;
						let uploadFiles = ($.type(targetObj.getInstance("uploadFiles")) === "undefined"?[]:targetObj.getInstance("uploadFiles"));
						let isUpload = true;

						$.each(ckUploadFiles, function(idx, data){
							if(data.result.errorCode != "00"){
								DIALOG.alert("파일업로드중 오류가 발생하였습니다.");
								_this.self.removeFileAll();
								isUpload = false;
								return false;
							}else{
								uploadFiles.push(data);
							}
						});

						if(isUpload){
							let fileList = targetObj.getInstance("uploadFiles");
							let checkUploadFiles  = [];
							let uniqueData        = [];
							let uniqueUploadFiles = [];

							$.each(uploadFiles, function(index, data){
								let fileObj = data.result.fileList[0];

								if(fileObj.cud != "ID"){
									let clonFileObj = {};
										clonFileObj.fileName  = fileObj.fileName;
										clonFileObj.filePath  = fileObj.filePath;
										clonFileObj.fileSize  = fileObj.fileSize;
										clonFileObj.inputName = fileObj.inputName;

									let isAdd = true;
									$.each(checkUploadFiles, function(idx, chkData){
										if(JSON.stringify(clonFileObj) == JSON.stringify(chkData)){
											targetObj.uploader.removeFile(idx);
											isAdd = false;
											return false;
										}
									});
									if(isAdd){
										if(fileObj.cud != "D"){
											fileObj.createdAt = dt;
											fileArr.push(fileObj);
										}
										uniqueUploadFiles.push(data);
										checkUploadFiles.push(clonFileObj);
									}

								}
							});

							targetObj.setInstance("uploadFiles", uniqueUploadFiles);
							targetObj.grid.setData(fileArr);
						}

					}
				});

				// 업로드 인스턴스 생성
				targetObj.setInstance("uploader", targetObj.uploader);

			}else if(__type__ === "attachList"){

				_columns.push({key: "download", label: "다운로드", width: "6%", align: "center", formatter: function () {
					return "<i class=\"fa fa-download\" aria-hidden=\"true\" style=\"cursor:pointer;\"></i>"
				}});

				$.extend(gridConfig, {header: {align: "center"},
									  showLineNumber: true,
									  showRowSelector: false,
									  multipleSelect: false,
									  lineNumberColumnWidth: 40,
									  rowSelectorColumnWidth: 27,
									  body: {
											onClick: function () {
												if(this.column.key == "download"){
													location.href="/common/fileDownload.do?attachId=" + this.item.attachId + "&attachSeq=" + this.item.attachSeq;
												}
											}
										}})

			}

			$.extend(gridConfig, {
					target: $("[data-ax5grid=\"" + targetId + "Grid\"]"),
					columns: _columns
				});

			// grid
			targetObj.grid = new ax5.ui.grid(gridConfig);

			// 그리드 인스턴스 생성
			targetObj.setInstance("grid", targetObj.grid);

			// uploadView
			let uploadView = {
					initView: function () {
						$("[data-btn-wrap]").clickAttr(this, "data-upload-btn", {
							"removeFile": function () {
								let deleteFiles = targetObj.grid.getList("selected");
								let fileList    = targetObj.getInstance("uploadFiles");
								if (deleteFiles.length == 0) {
									DIALOG.alert("선택된 파일이 없습니다.");
									return;
								}else{
									DIALOG.confirm({
										title: "알림",
										msg: "선택된 파일을 삭제 하시겠습니까?"
									}, function () {
										if(this.key == "ok"){

											let uploadFiles = [];
											$.each(deleteFiles.reverse(), function(index, data){
												targetObj.uploader.removeFile(data.__index);
												if(data.cud == "I"){
													fileList.splice(data.__index, 1);
												}else{
													fileList[data.__index].result.fileList[0].cud = "D";
//													uploadFiles.push(fileList[data.__index]);
												}
											});
											targetObj.setInstance("uploadFiles", fileList);
											targetObj.grid.removeRow("selected");
										}
									});
								}
							}
						});
					},
					setData: function (data) {
						targetObj.uploader.setUploadedFiles(data);
						targetObj.grid.setData(UPLOAD.uploadedFiles);
					}
			};

			uploadView.initView();

			if(__type__ === "attachList" || __type__ === "attachUpdate"){

				let url = "/common/commFileList.do";
				let params = {};
					params.attachId = uploadOption.attachId;

				if($.type(params.attachId) === "undefined"){
					return false;
				}

	            let option = {
	                    url: url,
	                    data: (typeof params === "object")?$.param(params):params,
	                    type: "post",
	                    cache: false,
	                    dataType: "json",
	                    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
	                    success: function(response) {
	                    	let attachFileList = response.result.list;
	    					let uploadFiles = [];
	    					let fileList = [];
	    					$.each(attachFileList, function(index, data){
	    						let fileData = {attachId:data.attachId, attachSeq:data.attachSeq, createdAt:data.regDt, fileChangeName:data.fileName, fileContentType:data.fileContentType, fileName:data.fileOriginName, filePath:data.filePath, fileSize:data.fileSize, cud:"U"};
	    						fileList.push(fileData);
	    						uploadFiles.push({result:{errorMessage: "정상", errorCode: "00", fileList: [fileData]}});
	    					});

	    					$("#" + targetId).setInstance("uploadFiles", uploadFiles);
	    					$("#" + targetId).getInstance("grid").setData(fileList);
	    					$(".fa.fa-download").css({background:"url(/resources/images/icon/download_btn.png)", backgroundSize:"11px"});
	                    },
	                    error: function(req, status, error) {
	                    },
	                    beforeSend: function(xhr){
	    	            	xhr.setRequestHeader("ajax", true);
	                    	utils.loading(true);
			    		},
			            complete: function(){
		            		utils.loading(false);
					    }
	                };
	            $.ajax(option);

			}

		}
		,
		/**
		 * data-code, data-type 리로드
		 */
		reloadDataType : function(){

			let targetArea = $($(this).selector);

			$.each(targetArea.find("[data-code]"), function(idx, data){
				let dataCode = $(this).attr("data-code");

				if(dataCode == ""){
					let tagName = $(this).prop("tagName");
					if(tagName.toLowerCase() == "input" || tagName.toLowerCase() == "textarea"){
						dataCode = $(this).val();
					}else{
						dataCode = $(this).text();
					}
				}

				let target  = $(this);
				utils.getCommonCode(dataCode.substring(0, dataCode.length - 3), function(codeObj){
					$.each(codeObj.list, function(codeIndex, codeData){
						if(codeData.code == dataCode){
							let tagName = target.prop("tagName");
							// input 처리
							if(tagName.toLowerCase() == "input"){
								let type = target.attr("type").toLowerCase();
								if(type == "text" || type == "hidden"){
									target.val(codeData.cname);
								}
							// textarea 처리
							}else if(tagName.toLowerCase() == "textarea"){
								target.text(codeData.cname);
							// select box 처리
							}else if(tagName.toLowerCase() == "select"){
								target.val(codeData.cname);
							}else{
								target.text(codeData.cname);
							}
						}
					});
				});
			});

			$.each(targetArea.find("[data-type]"), function(idx, data){

				let dataType = $(this).attr("data-type");
				let value = $(this).text();

				if(value != ""){
					switch(dataType){

						case "string" :
							$(this).text(value.toString());
						break;
						case "number" :
							$(this).text(value.number());
						break;
						case "phone" :
							$(this).text(value.phone());
						break;
						case "date" :
							if(core.defaultString(value) == ""){
								$(this).text("");
							}else{
								$(this).text(value.date().print());
							}
						break;
						case "datetime" :
							if(core.defaultString(value) == ""){
								$(this).text("");
							}else{
								if(value.number() == 0){
									$(this).text("");
								}else{
									$(this).text(value.trim().date().print("yyyy-mm-dd hh:mi:ss"));
								}
							}
						break;
						case "datetime2" :
							if(core.defaultString(value) == ""){
								$(this).text("");
							}else{
								if(value.number() == 0){
									$(this).text("");
								}else{
									$(this).text(value.trim().date().print("yyyy-mm-dd hh:mi"));
								}
							}
						break;
						case "datetime3" :
							if(core.defaultString(value) == ""){
								$(this).text("");
							}else{
								if(value.number() == 0){
									$(this).text("");
								}else{
									$(this).text(value.trim().date().print("yyyy-mm-dd"));
								}
							}
						break;
						case "comma" :
							$(this).text(value.money());
						break;
						default:
							$(this).text(value);

					}
				}

			});

		}
		,
		/**
		 * 자동완성
		 */
		autoComplete : function(dataOption){

			let targetId = $(this).selector.replace("#", "");
			let targetObj = $("#" + targetId);
			let offset = targetObj.offset();
			let top = offset.top + 77;
			let left = offset.left;
			let width = targetObj.width() + 13;

			let autoWirteAreaArr = []
			let autoWriteAreaId = "autoWriteArea" + $(".ui-menu").length

			autoWirteAreaArr.push("<ul id=\"" + autoWriteAreaId + "\" class=\"autocomplte\" style=\"display:none;\"></ul>");

			$("#" + targetId).attr("autocomplete", "off");
			$("#" + targetId).after(autoWirteAreaArr.join(""));

			// 초성변경
			hancho = function(str) {
				cho = ["ㄱ","ㄲ","ㄴ","ㄷ","ㄸ","ㄹ","ㅁ","ㅂ","ㅃ","ㅅ","ㅆ","ㅇ","ㅈ","ㅉ","ㅊ","ㅋ","ㅌ","ㅍ","ㅎ"];
				result = "";
				for(i=0; i < str.length; i++) {
					code = str.charCodeAt(i)-44032;
					if(code > -1 && code < 11172){
						result += cho[Math.floor(code/588)];
					}else{
						result += str.substring(i, i + 1);
					}
				}
				return result;
			}

			if($.type(dataOption.ajax) === "undefined"){
				let list = dataOption.source;

				let uniqueData = [];
				// 중복 제거
				$.each(list, function(i, el){
					if($.inArray(el, uniqueData) === -1) uniqueData.push(el);
				});

				let makeData = [];

				$.each(uniqueData, function(index, data){
					makeData.push({originData:data, choData:core.defaultString(hancho(data), data)});
				});

				targetObj.bind({
					change : function(){
						if($.type(dataOption.ajax.clear) !== "undefined"){
							dataOption.ajax.clear();
						}
					}
					,
					keyup: function(){
//						let val = hancho(this.value);
						let val = this.value;
						let isCho = false;
//						let code = val.charCodeAt(0)-44032;
//						if(code > -1 && code < 11172){
//							isCho = true;
//						}

						if(val == ""){
							$("#" + autoWriteAreaId).empty().hide();
							return false;
						}
						let putArr = [];
						$.each(makeData, function(index, data){
							if(isCho){
								if(data.choData.indexOf(val) != -1){
									putArr.push("<li>");
									putArr.push("<div>" + data.originData + "</div>");
									putArr.push("</li>");
								}
							}else{
								if(data.originData.indexOf(val) != -1){
									putArr.push("<li>");
									putArr.push("<a href=\"javascript:void(0)\"><div class=\"autoComplete-click-area\">" + data.originData + "</div></a>");
									putArr.push("</li>");
								}
							}

						});

						if(putArr.length > 0){
							$("#" + autoWriteAreaId).empty().append(putArr.join("")).show();
						}else{
							$("#" + autoWriteAreaId).empty().hide();
						}

						// 버튼 클릭시
						$("#" + autoWriteAreaId).find(".autoComplete-click-area").unbind().bind("click", function(){
							targetObj.val($(this).text());
							$("#" + autoWriteAreaId).empty().hide();
						});

					}
					,
					focusout : function(){
						setTimeout(function(){
							$("#" + autoWriteAreaId).empty().hide();
						}, 200);
					}
				});
			}else{

				if($.type(dataOption.ajax.params) === "undefined"){
					dataOption.ajax.params = {};
				}

				let searchWord = dataOption.ajax.searchTarget.searchWord;
				let paramCode  = dataOption.ajax.searchTarget.code;
				let addWord    = dataOption.ajax.searchTarget.addWord;

				let option = {
						url: dataOption.ajax.url,
						data: (typeof dataOption.ajax.params === "object")?$.param(dataOption.ajax.params):dataOption.ajax.params,
						type: "post",
						cache: false,
						dataType: "json",
						contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
						success: function(response) {

							let list = [];
							$.each(response.result.list, function(index, data){
								list.push({searchWord: data[searchWord], code: data[paramCode], addWord: data[addWord]});
							});

							let uniqueData = [];
							// 중복 제거
							$.each(list, function(i, el){
								if($.inArray(el, uniqueData) === -1) uniqueData.push(el);
							});

							let makeData = [];
							$.each(uniqueData, function(index, data){
								makeData.push({originData:data, choData:core.defaultString(hancho(data), data)});
							});

							let tabIndex = 0;

							targetObj.unbind().bind({
								change : function(){
									if($.type(dataOption.ajax.clear) !== "undefined"){
										dataOption.ajax.clear();
									}
								}
								,
								keyup: function(){
//									let val = hancho(this.value);
									let val = this.value;
									let isCho = false;
//									let code = val.charCodeAt(0)-44032;
//									if(code > -1 && code < 11172){
//									isCho = true;!
//								}
									if(val == ""){
										$("#" + autoWriteAreaId).empty().hide();
										return false;
									}
									let putArr = [];
									$.each(makeData, function(index, data){
										if(isCho){
											if(data.choData.indexOf(val) != -1){
												putArr.push("<li>");
												putArr.push("<div>" + data.originData + "</div>");
												putArr.push("</li>");
											}
										}else{
											if(utils.defaultString(data.originData.searchWord, "#", "").indexOf(val) != -1){

												putArr.push("<li>");
												putArr.push("<a href=\"javascript:void(0);\" data-focus=\"" + index + "\">");
												putArr.push("<div class=\"autoComplete-click-area\" style=\"width:95%;\" data-code=\"" + data.originData.searchWord + "|" + data.originData.code +"\">" + data.originData.searchWord + ($.type(data.originData.addWord) === "undefined"?"":", " + data.originData.addWord) + "</div>");
												putArr.push("</a>");
												putArr.push("</li>");
											}
										}

									});

									if(putArr.length > 0){
										$("#" + autoWriteAreaId).empty().append(putArr.join("")).show();
									}else{
										$("#" + autoWriteAreaId).empty().hide();
									}
									// 버튼 클릭시
									$("#" + autoWriteAreaId).find(".autoComplete-click-area").unbind().bind("click", function(){
										let arrData = $(this).data("code").split("|");
										let data = {name : arrData[0], code : arrData[1]}
										dataOption.ajax.callBackFn(data);
										$("#" + autoWriteAreaId).empty().hide();
									});

								}
								,
								keydown : function(event){

									let focusTag = $($(":focus")[0]).prop("tagName");

									moveFocus = function(moveIndex){

										$("[data-focus]").eq(moveIndex).unbind().bind("keydown", function(event){

											let size = $("[data-focus]").length;

											if(event.keyCode === 38){
												tabIndex--;
											// 방향키 아래
											}else if(event.keyCode === 40){
												tabIndex++;
											// 엔터키
											}else if(event.keyCode === 13){
												let arrData = $(this).find("[data-code]").data("code").split("|");
												let data = {name : arrData[0], code : arrData[1]}
												dataOption.ajax.callBackFn(data);
												$("#" + autoWriteAreaId).empty().hide();
												return;
											}else{
												return;
											}

											if(tabIndex == -1){
												tabIndex = 0;
												return;
											}

											if(size == tabIndex){
												tabIndex--;
												return;
											}

											$(".autocomplte > li").removeAttr("style");
											$("[data-focus]").eq(tabIndex).focus();
											$("[data-focus]").eq(tabIndex).parent().css({background:"#fd7a23", color:"#fff", fontWeight:"600"});
											moveFocus(tabIndex);

										});

									}

									if(focusTag == "INPUT"){
										// 방향키 아래
										if(event.keyCode === 40){
											$("[data-focus]").eq(tabIndex).focus();
											$("[data-focus]").eq(tabIndex).parent().css({background:"#fd7a23", color:"#fff", fontWeight:"600"});

											moveFocus(tabIndex);

										}else{
											return;
										}
									}

								}
								,
								focusout : function(){
									tabIndex = 0;
									setTimeout(function(){
//										$("#" + autoWriteAreaId).empty().hide();
									}, 200);
								}
							});

						},
						error: function(req, status, error) {
						},
						beforeSend: function(xhr){
							xhr.setRequestHeader("ajax", true);
//							utils.loading(true);
						},
						complete: function(){
//							utils.loading(false);
						}
				};
				$.ajax(option);
			}

		}
		/**
		 * 밸리데이션 체크
		 */
		,validator : function(valiObj){
			let targetId = $(this).selector.replace("#", "");
			let rtn = true;
			let validatorArr = [];
			$.each(valiObj, function(index, data){
				if($.type(data) === "function"){
					if(data() == false){
						rtn = false;
						return false;
					}
				}else{
					validatorArr.push(data);
				}
			});

			if(rtn == true){
				$.each(validatorArr, function(index, data){

					let val = alertMsg ="";
					let focusObj;
					// 주소일경우
					if(data.type == "address"){
						let checkData = "";
						$.each(data.key, function(addressIndex, addressData){
							val = core.defaultString($("input[name=" + addressData + "]").val());
							checkData += val;
							if(val == ""){
								focusObj = $("input[name=" + addressData + "]");
								alertMsg = data.label + "을(를) 입력해주세요.";
								rtn = false;
								return false;
							}
						});
					}else if(data.type == "email"){
						let checkData = "";
						$.each(data.key, function(mailIndex, mailData){
							let mailVal = "";
							if(mailIndex == 0){
								focusObj = $("input[name=" + mailData + "]");
								mailVal = core.defaultString($("input[name=" + mailData + "]").val());
								checkData += mailVal;
							}else{
								mailVal = core.defaultString($("input[name=" + mailData + "]").val());
								checkData += "@" + mailVal;
							}
							val = mailVal;
							if(val == ""){
								alertMsg = data.label + "을(를) 입력해주세요.";
								rtn = false;
								return false;
							}
						});

						if(rtn){
							let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
							if(!regExp.test(checkData)){
								val = "";
								alertMsg = "잘못된 이메일입니다.";
								rtn = false;
							}else{
								if($("#" + data.name).length > 0){
									$("#" + data.name).val(checkData);
								}else{
									let hiddenInputBox = $("<input>", {
										  type : "hidden"
										 ,name : data.name
										 ,id : data.name
										 ,value : checkData
									});
									$("#" + targetId).append(hiddenInputBox);
								}

							}
						}
					}else if(data.type == "tel"){
						let checkData = "";
						$.each(data.key, function(telIndex, telData){
							let telVal = "";
							if(telIndex == 0){
								focusObj = $("select[name=" + telData + "]");
								telVal = core.defaultString($("select[name=" + telData + "]").val());
								checkData += telVal;
							}else{
								focusObj = $("input[name=" + telData + "]");
								telVal = core.defaultString($("input[name=" + telData + "]").val());
								checkData += "-" + telVal;
							}
							val = telVal;
							if(val == ""){
								alertMsg = data.label + "을(를) 입력해주세요.";
								rtn = false;
								return false;
							}
						});

						if(rtn){
							let regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
							if(!regExp.test(checkData)){
								val = "";
								alertMsg = "잘못된 전화번호입니다.";
								rtn = false;
							}else{
								if($("#" + data.name).length > 0){
									$("#" + data.name).val(checkData);
								}else{
									let hiddenInputBox = $("<input>", {
										  type : "hidden"
										 ,name : data.name
										 ,id : data.name
										 ,value : checkData
									});
									$("#" + targetId).append(hiddenInputBox);
								}

							}
						}

					}else if(data.type == "hp"){
						let checkData = "";
						$.each(data.key, function(hpIndex, hpData){
							let hpVal = "";
							if(hpIndex == 0){
								focusObj = $("select[name=" + hpData + "]");
								hpVal = core.defaultString($("select[name=" + hpData + "]").val());
								checkData += hpVal;
							}else{
								focusObj = $("input[name=" + hpData + "]");
								hpVal = core.defaultString($("input[name=" + hpData + "]").val());
								checkData += "-" + hpVal;
							}
							val = hpVal;
							if(val == ""){
								alertMsg = data.label + "을(를) 입력해주세요.";
								rtn = false;
								return false;
							}
						});

						if(rtn){
							let regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
							if(!regExp.test(checkData)){
								val = "";
								alertMsg = "잘못된 휴대폰번호입니다.";
								rtn = false;
							}else{
								if($("#" + data.name).length > 0){
									$("#" + data.name).val(checkData);
								}else{
									let hiddenInputBox = $("<input>", {
										  type : "hidden"
										 ,name : data.name
										 ,id : data.name
										 ,value : checkData
									});
									$("#" + targetId).append(hiddenInputBox);
								}

							}
						}

					}else{
						let tagName = $("[name="+ data.key +"]").prop("tagName");

						if(tagName != undefined){
							if(tagName.toLowerCase() == "input"){

								let type = $("input[name="+ data.key +"]").attr("type").toLowerCase();
								if(type == "radio"){
									focusObj = $("input[name=" + data.key + "]").first();
									val = $("input[name=" + data.key + "]:checked").val();
									alertMsg = data.label + "을(를) 선택해주세요.";
								}else if(type == "checkbox"){
									focusObj = $("input[name=" + data.key + "]").first();
									val = $("input[name=" + data.key + "]:checked").val();
									alertMsg = data.label + "을(를) 선택해주세요.";
								}else if(type == "text" || type == "hidden" || type == "number" || type == "phone"){
									focusObj = $("input[name=" + data.key + "]");
									val = $("input[name=" + data.key + "]").val();
									alertMsg = data.label + "을(를) 입력하세요.";
								}
							}else if(tagName.toLowerCase() == "textarea"){
								focusObj = $("textarea[name=" + data.key + "]");
								val = $("textarea[name=" + data.key + "]").val();
								alertMsg = data.label + "을(를) 입력하세요.";
							// select box 처리
							}else if(tagName.toLowerCase() == "select"){
								focusObj = $("select[name=" + data.key + "]");
								val = $("select[name=" + data.key + "]").val();
								alertMsg = data.label + "을(를) 선택해주세요.";
							}else if(tagName.toLowerCase() == "span" || tagName.toLowerCase() == "div"){
								// 에디터일경우
								if($.type(window.AXEditor_instances) != "undefined"){
									$.each(window.AXEditor_instances, function(index, klass){
										let editId = klass.config.targetID;
										let editContent = klass.getContent();
										if(data.key == editId && !klass.getContentCheck()){
											focusObj = $("input[name=" + data.key + "]");
											val = "";
											alertMsg = data.label + "을(를) 선택해주세요.";
										}else{
											val = utils.replaceAll(editContent, '"', "&quot;");
										}

									});
								}
							}
						}else{
							toast.push("잘못된 식별자입니다. key = [" + data.key + "]");
							return false;
						}

					}

					if($.type(val) === "undefined" || val == ""){
						DIALOG.alert(alertMsg, function(){
							focusObj.focus();
						});
						rtn = false;
						return false;
					}

				});
			}

			return rtn;
		}
		,
		/**
		 * 클립보드
		 */
		clibboard : function(){

			let targetId = $(this).selector.replace("#", "");

			if($("#clipboardTarget").length == 0){
				$("body").append("<input type=\"text\" id=\"clipboardTarget\" value=\"\"/ style=\"position:absolute;top:-9999em;\">");
			}
			$("#" + targetId).bind("mousedown", function(){

				let params = {};
					params.realUrl = location.pathname;
					params.gbnCd   = "01";

				let option = {
                    url: "/common/makeShortUrl.do",
                    data: (typeof params === "object")?$.param(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
                    success: function(response) {

                    	try{
                    		$("#clipboardTarget").val(location.origin + response.result.shortUrl);
                    	}catch(error){
                    		DIALOG.alert("이 브라우저는 지원하지 않습니다.");
							return false;
                    	}

                    },
                    error: function(req, status, error) {
                    },
                    beforeSend: function(xhr){
                    	xhr.setRequestHeader("ajax", true);
                    	utils.loading(true);
		    		},
		            complete: function(){
				    }
                };
            $.ajax(option);

			});

			$("#" + targetId).bind("click", function(){

				fn = function(){
					$("#clipboardTarget").select();
					let success = document.execCommand("copy", false, null);
					if(success){
						DIALOG.alert("복사되었습니다.");
						return false;
					}else{
						DIALOG.alert("이 브라우저는 지원하지 않습니다.");
						return false;
					}
				}

				setTimeout(fn, 500);

			});

		}
		,
		clickover : function(option){
//			let targetId = $(this).selector.replace("#", "");
			picker.bind({
				target: $(this).selector,
				direction: "top",
				contentWidth: $.type(option.width) === "undefined"?200:option.width,
				content: function (makeHtml) {

					let html = "<form style=\"padding:0 5px;\" id=\"infoModal\">" + option.html() + "</form>";
					makeHtml(html);

				}
				,
				onStateChanged: function () {
					if (this.state == "open") {
					}
				}
				,
				btns: {
					ok: {
						label: ($.type(option.label) === "undefined"?"생성":option.label), theme: "btn-primary", onClick: function () {
							let callBackData = $("#infoModal").serializeObject();
							option.callBackFn(callBackData);
							this.self.close();
						}
					}
				}
			});
		}
		,
		/**
		 * 팝업
		 */
		popup : function(option){
			openPopup = function(option){

				const iframeLen = window.top.popupOptionArr.length;

				const _this = this;
				let popupHtml = [];
				let title    = option.title;
				let url      = option.url;
				let width    = option.width;
				let height   = option.height;
				let addClass = ($.type(option.addClass) === "undefined"?"":option.addClass);
				let params   = option.params;
				let tab      = option.tab;
				let load     = option.load;
				let parentPop = ($.type(option.parentPop) === "undefined"?"N":option.parentPop);
				let scrolling = ($.type(option.scrolling) === "undefined"?"no":option.scrolling);

				if($.type(tab) !== "undefined"){
					if($.type(params) !== "undefined"){
						params.tab = tab;
					}else{
						params = {};
						params.tab = tab;
					}

				}

				// 팝업이 하나인경우
				if(iframeLen == 0){

					popupHtml.push("<div class=\"modalpopup\" id=\"modalPopupArea\">");
					popupHtml.push("<div class=\"modalcontens " + addClass + "\" style=\"width:" + width + ";height:" + height + ";\">");
					popupHtml.push("<h2>" + title + "</h2>");
					popupHtml.push("<form name=\"popupIframeform" + iframeLen + "\" id=\"popupIframeform" + iframeLen + "\" action=\"" + url + "\" method=\"POST\" target=\"popupIframe" + iframeLen + "\">");

					let arrayParam = [];
					if($.type(params) != "undefined"){
						$.each(params, function(idx, val){

							if($.type(val) === "array"){
								arrayParam.push({key:idx, value:val});
								val = "";
							}

							popupHtml.push("<input type=\"hidden\" name=\"" + idx + "\" value=\"" + val + "\">");
						});
					}

					popupHtml.push("</form>");
					popupHtml.push("<iframe width=\"100%\" height=\"100%\" name=\"popupIframe" + iframeLen + "\" id=\"popupIframe" + iframeLen + "\" frameborder=\"0\" scrolling=\"" + scrolling + "\" ></iframe>");
					popupHtml.push("<a href=\"javascript:void(0)\"><div class=\"close\" id=\"popupCloseBtn\"></div></a>");
					popupHtml.push("</div>");
					popupHtml.push("</div>");

					var popupTargetArea;
					var popupTargetIframe;
					if(parentPop == "Y"){
						popupTargetArea   = parent.$("body");
						popupTargetIframe = parent.$("#popupIframeform" + iframeLen);
					}else{
						popupTargetArea   = $("body");
						popupTargetIframe = $("#popupIframeform" + iframeLen);
					}

					popupTargetArea.append(popupHtml.join(""));

					if(arrayParam.length > 0){
						$.each(arrayParam, function(idx, paramData){
							if(parentPop == "Y"){
								parent.$("input[name='" + paramData.key + "']").val(JSON.stringify(paramData.value));
							}else{
								$("input[name='" + paramData.key + "']").val(JSON.stringify(paramData.value));
							}
						})
					}
					if(parentPop == "Y"){
						parent.$("#popupIframeform" + iframeLen).submit();
					}else{
						$("#popupIframeform" + iframeLen).submit();
					}
					//$("#popupIframeform" + iframeLen).submit();

				}else{

					$("#modalPopupArea", window.top.document).contents().find("h2").text(title); // 타이틀명 변경
					$("#modalPopupArea", window.top.document).find(".modalcontens").css({width: width, height: height}); // 사이즈 변경

					if($(window.top.document).find("#popupIframeform" + iframeLen).length > 0){
						$(window.top.document).find("#popupIframeform" + iframeLen).remove();
					}

					popupHtml.push("<form name=\"popupIframeform" + iframeLen + "\" id=\"popupIframeform" + iframeLen + "\" action=\"" + url + "\" method=\"POST\" target=\"popupIframe" + iframeLen + "\">");
					
					var cUrl = window.location.pathname;
					let arrayParam = [];
					if($.type(params) != "undefined"){
						$.each(params, function(idx, val){
							if (cUrl.indexOf('/admin/rscr/rscr2704WorkRegPop.do') != -1) { //인프라관리 > 작업요청 > 작업계획서 > 고객팝업 인 경우만 JSON.stringify로 추가
								if($.type(val) === "array"){
									arrayParam.push({key:idx, value:val});
									val = "";
								}
							}

							popupHtml.push("<input type=\"hidden\" name=\"" + idx + "\" value=\"" + val + "\">");
						});
					}

					popupHtml.push("</form>");
					popupHtml.push("<iframe width=\"100%\" height=\"100%\" name=\"popupIframe" + iframeLen + "\" id=\"popupIframe" + iframeLen + "\" frameborder=\"0\" scrolling=\"" + scrolling + "\" ></iframe>");

					$(window.top.document).find(".modalcontens").append(popupHtml.join(""));

					if(arrayParam.length > 0){
						var cUrl = window.location.pathname;
						if (cUrl.indexOf('/admin/rscr/rscr2704WorkRegPop.do') != -1) { //인프라관리 > 작업요청 > 작업계획서 > 고객팝업 인 경우만 JSON.stringify로 추가
							$.each(arrayParam, function(idx, paramData){
								parent.$("input[name='" + paramData.key + "']").val(JSON.stringify(paramData.value));
							})
						}
					}

					// 기존 ifrmae 숨기고 새로운 iframe 보여지게 처리
					$(window.top.document).find("#popupIframe" + (iframeLen - 1)).hide();
					$(window.top.document).find("#popupIframeform" + iframeLen).submit();

				}

				// 상단 X버튼 클릭
				$("#popupCloseBtn").unbind().bind("click", function(){
					_this.closePopup();
				});

				// 아이프레임이 로드 되었을경우 콜백함수 추가
				$(window.top.document).find("#popupIframe" + iframeLen).load( function(){

					let pobj = $(window.top.document).find("#popupIframeform" + iframeLen).find("input");
					if(pobj.length > 0){
						pobj.unwrap();
						$(window.top.document).find(".modalcontens").find("p").remove();
					}
					// 팝업 이동
					if(iframeLen == 0){
						$(window.top.document).find(".modalcontens").css("cursor", "move");
						$(window.top.document).find(".modalcontens").draggable();
					}

					$(this).get(0).contentWindow.setPopupData = option.callBackFn;
					$(this).get(0).contentWindow.closePopup = _this.closePopup;

					if($.type(load) === "function"){
						load("popupIframe" + iframeLen);
					}

                });

				window.top.popupOptionArr.push(option);

				// 팝업가운데 띄우기
				$(".modalcontens", window.top.document).css("position", "absolute");
//				$(".modalcontens", window.top.document).css("top", Math.max(0, (($(window.top.document).height() - $(".modalcontens", window.top.document).outerHeight()) / 2) + $(window.top.document).scrollTop()) + "px");
				$(".modalcontens", window.top.document).css("top", "10%");
				$(".modalcontens", window.top.document).css("left", Math.max(0, (($(window.top.document).width() - $(".modalcontens", window.top.document).outerWidth()) / 2) + $(window.top.document).scrollLeft()) + "px");
				$(".modalcontens", window.top.document).css("margin-left", "0px");

			}

			// 닫기
			closePopup = function(){

				let popupLen = window.top.popupOptionArr.length;
				// 모두 닫기
				if(popupLen == 1){
					window.top.popupOptionArr = [];
					if($("#modalPopupArea").length == 0){
						if(parent.$("#modalPopupArea").length == 1){
							parent.$("#modalPopupArea").remove();
						}
					}else{
						$("#modalPopupArea").remove();
					}
				}else{
					window.top.popupOptionArr.splice(popupLen - 1, 1);
					let title = window.top.popupOptionArr[popupLen - 2].title;
					let width = window.top.popupOptionArr[popupLen - 2].width;
					let height = window.top.popupOptionArr[popupLen - 2].height;
					$("#modalPopupArea", window.top.document).contents().find("h2").text(title); // 타이틀명 변경
					$("#modalPopupArea", window.top.document).find(".modalcontens").css({width: width, height: height}); // 사이즈 변경
					$(window.top.document).find("#popupIframe" + (popupLen - 2)).show();
					$(window.top.document).find("#popupIframe" + (popupLen - 1)).remove();

					// 팝업가운데 띄우기
					$(".modalcontens", window.top.document).css("position", "absolute");
//					$(".modalcontens", window.top.document).css("top", Math.max(0, (($(window.top.document).height() - $(".modalcontens", window.top.document).outerHeight()) / 2) + $(window.top.document).scrollTop()) + "px");
					$(".modalcontens", window.top.document).css("top", "10%");
					$(".modalcontens", window.top.document).css("left", Math.max(0, (($(window.top.document).width() - $(".modalcontens", window.top.document).outerWidth()) / 2) + $(window.top.document).scrollLeft()) + "px");
					$(".modalcontens", window.top.document).css("margin-left", "0px");
				}
				window.top.popupCloseCallback.executeLast();
			}

			// 팝업열기
			if($(this).selector == ""){
				openPopup(option);
			}else{
				$($(this).selector).unbind().bind("click", function(){
					openPopup(option);
				});
			}

//			if($.type(this.selector.option.close) !== "undefined"){
//				$(this).selector.option.close.unbind().bind("click", function(){
//					close();
//				});
//			}

		}
		,enter : function(clickTarget){
			$(this).unbind().bind("keyup", function(event){
				if(event.keyCode == 13){
					$(clickTarget).click();
				}
			})
		}
		,
		/**
		 * 숫자만 입력
		 */
		onlyNumber : function(){
			let target = $(this).selector;

			$(target).unbind().bind({
				keydown : function(event){
					event = event || window.event;
					let keyID = (event.which) ? event.which : event.keyCode;
					if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39){
						return;
					}else{
						return false;
					}
				}
				,
				keyup : function(event){
					event = event || window.event;
					let keyID = (event.which) ? event.which : event.keyCode;
					if( keyID == 8 || keyID == 9 || keyID == 46 || keyID == 37 || keyID == 39 ){
						return;
					}else{
						event.target.value = event.target.value.replace(/[^0-9]/g, "");
					}
				}
				,
				focusout : function (event) {
					event.target.value = event.target.value.replace(/[^0-9]/g, "");
			    }
			});
		},
		onlyWord : function(){
			let target = $(this).selector;
			let regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
			$(target).unbind().bind({
				keydown : function(event){
					event.target.value = event.target.value.replace(regExp, "");
				}
				,
				keyup : function(event){
					event.target.value = event.target.value.replace(regExp, "");
				}
				,
				focusout : function (event) {
					event.target.value = event.target.value.replace(regExp, "");
			    }
			});
		},
		rowspan : function(colIdx){
			let _this = this;
			if($.type(colIdx) === "number"){
				_this.each(function(){
					var that;
					$("tr", this).each(function(row) {
						$("td:eq(" + colIdx + ")", this).filter(":visible").each(function(col) {

							if($(this).html() == $(that).html()){
								rowspan = $(that).attr("rowspan") || 1;
								rowspan = Number(rowspan) + 1;

								$(that).attr("rowspan", rowspan);

								$(this).hide();
							} else {
								that = this;
							}
							that = (that == null) ? this : that;
						});
					});
				});
			}else{
				$.each(colIdx, function(idx, data){

					_this.each(function(){
						var that;
						$("tr", this).each(function(row) {
							$("td:eq(" + data + ")", this).filter(":visible").each(function(col) {
								
								if($(this).html() == $(that).html() && (!data || data && $(this).prev().html() == $(that).prev().html())){
									rowspan = $(that).attr("rowspan") || 1;
									rowspan = Number(rowspan) + 1;

									$(that).attr("rowspan", rowspan);

									$(this).hide();
								} else {
									that = this;
								}
								that = (that == null) ? this : that;
							});
						});
					});

				})
			}

			$.each($(this).find("td"), function(idx, data){
				if($(this).css("display") === "none"){
					$(this).remove();
				}
			});

		}
	})

})(jQuery);

core = {
		allSearchMovePage : function(){

			let url = document.location.pathname;
			if (url == ""){
				alert("이동할 URL이 설정되지 않았습니다.");
				return;
			}
			this.loading(true);
			$("#nextForm").unwrap();
			let strHtml = "<form id=\"nextForm\" name=\"nextForm\" method=\"post\" action=\"" + url + "\"></form>";
			$("body").wrap(strHtml);	// 화면에 form 등 생성
			$("#nextForm")[0].reset();
			$("#nextForm").submit();	// submit
		}
		,
		movePage : function(param){

			alert('AAA');
			let url = document.location.pathname;
			if (url == ""){
				alert("이동할 URL이 설정되지 않았습니다.");
				return;
			}
			this.loading(true);
			$("#nextForm").unwrap();
			let strHtml = "<form id=\"nextForm\" name=\"nextForm\" method=\"post\" action=\"" + url + "\"></form>";
			$("body").wrap(strHtml);	// 화면에 form 등 생성
			if(typeof param != "undefined"){
				let paramStr = "";
				$.each(param, function(index, item){
					type = typeof(item);

					if(type === "string"){

						paramStr += "<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\" value=\"" + item + "\"/>";

					}else{

						$("#nextForm").append("<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\"/>");
						$("#" + index).val(JSON.stringify(item));

					}
				});
				$("#nextForm").append(paramStr);
			}
			

			$("#nextForm").submit();	// submit
		}
		,
		paging : function(pageNo, targetId){

			let option = $("#" + targetId).getInstance("ajaxInfo");

			if($.type(option) === "undefined"){
				let url = document.location.href;
				if (url == ""){
					alert("이동할 URL이 설정되지 않았습니다.");
					return;
				}
				this.loading(true);
				$("#nextForm").unwrap();
				let strHtml = [];
					strHtml.push("<form id=\"nextForm\" name=\"nextForm\" method=\"post\" action=\"" + url + "\">");
					strHtml.push("<input type=\"hidden\" id=\"search\" name=\"search\" value=\"Y\"/>");
					strHtml.push("<input type=\"hidden\" id=\"pageNo\" name=\"pageNo\" value=\"" + pageNo + "\"/>");

					if(location.search.replace("?").split("&").length > 0){
						let addParam = location.search.replace("?", "").split("&");
						$.each(addParam, function(idx, addData){
							strHtml.push("<input type=\"hidden\" id=\"" + addData.split("=")[0] + "\" name=\"" + addData.split("=")[0] + "\" value=\"" + addData.split("=")[1] + "\"/>");
						})
					}

					strHtml.push("</form>");
				$("body").wrap(strHtml.join(""));
				$("#nextForm").submit();
			}else{
				if($.type(option.params) === "string" || $.type(option.params) === "undefined"){
					option.params = {};
				}

				option.params.pageNo = pageNo;
				$("#" + targetId).ajaxList(option);

			}

		}
		,
		/**
		 * 스트링 변환
		 * @param str
		 * @param defaultStr
		 * @returns {String}
		 */
		defaultString : function(str, defaultStr) {

			if(typeof defaultStr === "undefined"){
				defaultStr = "";
			}

			if($.type(str) === "number" && str == 0){
				return str;
			}

			if(str == "undefined" || str == "null" || str == undefined || str == null || str == " " || str == ""){
				if(defaultStr != undefined){
					str = defaultStr;
				}
			}

			return str;
		}
		,
		/**
		 * 로딩
		 * @param isLoading
		 * @param text
		 */
		loading : function(isLoading){

			if(isLoading){

				let maskHeight = $(document).height();
				let maskWidth  = $(document).width();

				if(utils.isMobile()){

					$(".dev-mobile-loading-background").css({"width":maskWidth, "height":maskHeight});
					$(".dev-mobile-loading-background, .dev-mobile-loadingImg").show();
					$(".dev-mobile-loadingImg").css({"top": parseFloat($(".dev-mobile-loadingImg").css("top")) + $(document).scrollTop()}) ;

					$(window).resize(function(){
						var resizeHeight = $(document).height();
						var resizeWidth = $(document).width();

						$(".dev-mobile-loading-background").css({"width":resizeWidth, "height":resizeHeight});
					});

				}else{

					$(".dev-modal-background").css({"width":maskWidth, "height":maskHeight});
					$(".dev-modal-background, .dev-loadingImg").show();
					$(".dev-loadingImg").css({"top": parseFloat($(".dev-loadingImg").css("top")) + $(document).scrollTop()}) ;

					$(window).resize(function(){
						var resizeHeight = $(document).height();
						var resizeWidth = $(document).width();

						$(".dev-modal-background").css({"width":resizeWidth, "height":resizeHeight});
					});

				}

			}else{

				if(utils.isMobile()){
					$(".dev-mobile-loading-background, .dev-mobile-loadingImg").hide();
				}else{
					$(".dev-modal-background, .dev-loadingImg").hide();
				}

			}

		},
		
		queryObject : function() {
			var url = document.location.href;
		    var qs = url.substring(url.indexOf('?') + 1).split('&');
		    for(var i = 0, result = {}; i < qs.length; i++){
		        qs[i] = qs[i].split('=');
		        result[qs[i][0]] = decodeURIComponent(qs[i][1]);
		    }
		    return result;
		},
		
		queryString : function() {
			if (document.location.search.indexOf('?') == -1) return '';
			var s = [];
			var r20 = /%20/g;
			$.each(document.location.search.substr(1).split('&'),function(c,q){
			  var i = q.split('=');
			  if (i.length > 1) {
				  s[ s.length ] = encodeURIComponent( i[0].toString() ) + "=" + encodeURIComponent( i[1].toString() );
			  }
			});
			return unescape(s.join( "&" ).replace( r20, "+" ));
		}
}