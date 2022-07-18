/**
 * 공통 유틸 스크립트 함수
 * @author devkimsj
 */
utils = {
		isMobile: function(){
			let isMobile = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent)? true:false;
			return isMobile;
		},
		/**
		 * ajax 통신함수
		 * @param url
		 * @param param
		 * @param callback
		 */
		ajax: function(url, params, callback, isLoading) {

			if (typeof isLoading === "undefined") {
				isLoading = true;
			}

			if (typeof params === "undefined") {
				params = {};
			}
			
			var option = {
					url: url,
					data: (typeof params === "object") ? $.param(params) : params,
					type: "post",
					cache: false,   
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					beforeSend: function(xhr) {						
						if (isLoading) {
							setTimeout(function() {
								utils.loading(true);
							}, 50);
						}
					},
					success: function(result, status, xhr) {
						//세션 타임아웃이 아니고, 중복 로그인으로 인해 세션이 끊어져서 ajax 통신 후 result값이 아예 없이 들어오는경우
						if (url != "/admin/cumn/cumn0301TempCsUnique.do" && url != "/admin/sysm/sysm0101Info.do" 
							&& url != "/admin/wkmn/wkmn06OverlapCheck.do" && url != "/admin/wkmn/wkmn06CheckDuplicate.do"
							&& url != "/admin/wire/wire05OverlapCheck.do" && url != "/admin/wire/wire05CheckDuplicate.do"
							&& url != "/admin/stat/stat04CheckDuplicatedCheck.do" && url != "/admin/stat/stat0402DeleteDuplicatedCheck.do"
							&& url != "/admin/sysm/sysm04CheckDuplicate.do" && url != "/common/amSearch.do"
							&& url != "/admin/cumn/searchCarDetail.do"
							&& url != "/common/accessNew.do" && xhr.responseText == "{\"result\":{}}"){ 
                    		//location.href = "/admin/login/login0101Page.do" ; //로그아웃 창으로 튕겨버림
                    	} else {
                    		callback(result);
                    	}
					},
					error: function(req, status, error) {
					},					
					complete: function() {
						if (isLoading) {
							setTimeout(function() {
								utils.loading(false);
							}, 50);
						}
					},
			};
			$.ajax(option);

		},
		/**
		 * 공통코드 함수
		 * @param url
		 * @param param
		 * @param callback
		 */
		getCommonCode: function(code, callback){

			let params = {};
			if($.type(code) === "string"){
				params.code = code;
			}else{
				params = code;
			}
			
            var option = {
                    url: "/common/getCommonCode.do",
                    data: (typeof params === "object")?JSON.stringify(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/json; charset=UTF-8;",
                    beforeSend: function(xhr) {},
                    success: function(response) {

                    	if($.type(code) === "string"){
                    		callback(response.result);
            			}else{
            				let list = response.result.list;
            				let target = $("#" + params.targetId);
        					let firstOp = target.find("option").eq(0);
        					target.empty();
        					target.append(firstOp);
            				if(list.length > 0){
            					$.each(list, function(index, listData){
            						let selectOption = $("<option>", {
            							  text : listData.codeName
            							 ,value : listData.codeId
            						});
            						target.append(selectOption);
            					});

            					if($.type(params.onchange) === "string"){
            						target.unbind().bind("change", function(){
            							new Function(params.onchange + "('" + this.value.toString() + "')")();
            						});
            					}else if($.type(params.onchange) === "function"){
            						target.unbind().bind("change", function(){
            							params.onchange(this.value.toString());
            						});
            					}
            				}
            				if($.type(callback) !== "undefined"){
            					callback(list);
            				}
            			}
                    },
                    error: function(req, status, error) {},
		            complete: function(){}
                };
            $.ajax(option);

		},
		/**
		 * 공통코드 함수 (하위 depth code 설정)
		 * @param url
		 * @param param
		 * @param callback
		 */
		getCommonCodeSubDepth: function(code, callback){

			let params = {};
			if($.type(code) === "string"){
				params.code = code;
			}else{
				params = code;
			}
			
            var option = {
                    url: "/common/getCommonCodeSubDepth.do",
                    data: (typeof params === "object")?JSON.stringify(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/json; charset=UTF-8;",
                    success: function(response) {
                    	if($.type(code) === "string"){
                    		callback(response.result);
            			}else{
            				let list = response.result.list;
            				let target = $("#" + params.targetId);
        					let firstOp = target.find("option").eq(0);
        					target.empty();
        					target.append(firstOp);
            				if(list.length > 0){
            					$.each(list, function(index, listData){
            						let selectOption = $("<option>", {
            							  text : listData.cname
            							 ,value : listData.code
            						});
            						target.append(selectOption);
            					});

            					if($.type(params.onchange) === "string"){
            						target.unbind().bind("change", function(){
            							new Function(params.onchange + "('" + this.value.toString() + "')")();
            						});
            					}else if($.type(params.onchange) === "function"){
            						target.unbind().bind("change", function(){
            							params.onchange(this.value.toString());
            						});
            					}
            				}
            				if($.type(callback) !== "undefined"){
            					callback(list);
            				}
            			}
                    },
                    error: function(req, status, error) {
                    },
                    beforeSend: function(xhr){
                    	xhr.setRequestHeader("ajax", true);
		    		},
		            complete: function(){
//	            		utils.loading(false);
				    }
                };
            $.ajax(option);
		},
		/**
		 * 고객조회
		 */
		getCsSearch : function(csNo, callBackFn){
			let params = {};
				params.csNo = csNo;

			var option = {
					url: "/common/commCsSearch.do",
					data: (typeof params === "object")?$.param(params):params,
					type: "post",
					cache: false,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					success: function(response) {
						callBackFn(response.result);
					},
					error: function(req, status, error) {
					},
					beforeSend: function(xhr){
//						let token = $("meta[name='_csrf']").attr("content");
//						let header = $("meta[name='_csrf_header']").attr("content");
//						xhr.setRequestHeader(header, token);
						xhr.setRequestHeader("ajax", true);
					},
					complete: function(){
					}
			};
            $.ajax(option);
		},
		/**
		 * 사용자 조회
		 */
		getUserSearch : function(userId, callBackFn){
			let params = {};
				params.userId = userId;

			var option = {
					url: "/common/commCsSearch.do",
					data: (typeof params === "object")?$.param(params):params,
					type: "post",
					cache: false,
					dataType: "json",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8;",
					success: function(response) {
						callBackFn(response.result);
					},
					error: function(req, status, error) {
					},
					beforeSend: function(xhr){
//						let token = $("meta[name='_csrf']").attr("content");
//						let header = $("meta[name='_csrf_header']").attr("content");
//						xhr.setRequestHeader(header, token);
						xhr.setRequestHeader("ajax", true);
					},
					complete: function(){
					}
			};
            $.ajax(option);
		},
		/**
		 * 로딩
		 * @param isLoading
		 * @param text
		 */
		loading : function(isLoading){

			if(isLoading){

				let maskHeight = $(document).height();
				let maskWidth  = $(document).width();

				if(this.isMobile()){

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
		/**
		 * Json 데이터 input 처리
		 * @param data
		 */
		inputData : function(data){
			let _this=this;
			$("input[type=radio]").removeAttr("checked");
			$("input[type=checkbox]").removeAttr("checked");
			let resetHtml = "<form id=\"resetForm\" name=\"resetForm\"></form>";
			$("body").wrap(resetHtml);
			$("#resetForm")[0].reset();
			$("#resetForm").contents().unwrap();

			if($.type(data) === "string" || $.type(data) === "undefined"){
				if($("[data-type]").length > 0){
					let dataTypeArr = $("[data-type]");
					$.each(dataTypeArr, function(index, data){
						let dataType = $(data).attr("data-type");
						utils.getDataType(dataType, "", $(data));
					});
				}
			}

			let codeNmArr = [];
			let attachId;
			$.each(data, function(key, value){

				// dataType 처리
				let obj = $("#" + key);
				let dataType = obj.attr("data-type");
				value = utils.defaultString(value);
				if($.type(dataType) !== "undefined"){
					value = utils.getDataType(dataType, value, obj);
				}

				let tagName = obj.prop("tagName");
				if(tagName != undefined){

					// input 처리
					if(tagName.toLowerCase() == "input"){
						let type = obj.attr("type").toLowerCase();

						if(type == "radio"){
							obj.filter("[value=" + value + "]").prop("checked", true);
						}else if(type == "checkbox"){
							obj.filter("[value=" + value + "]").prop("checked", true);
						}else if(type == "text" || type == "hidden" || type == "number"){
							obj.val(value);
						}

					// textarea 처리
					}else if(tagName.toLowerCase() == "textarea"){
						obj.html(value);
					// select box 처리
					}else if(tagName.toLowerCase() == "select"){
						obj.val(value);
					}else{
						if($.type(value) == "string"){
							value = _this.defaultString(value).replace(/\n/g,"<br/>");
						}
						obj.html(value);
					}
				}else{
					obj = $("input[name=" + key + "]");
					dataType = obj.attr("data-type");
					if($.type(dataType) !== "undefined"){
						value = utils.getDataType(dataType, value, obj);
					}
					value = _this.defaultString(value);
					tagName = obj.prop("tagName");
					if(tagName != undefined){

						if(tagName.toLowerCase() == "input"){
							let type = obj.attr("type").toLowerCase();
							if(type == "radio" && value != ""){
								obj.filter("[value=" + value + "]").prop("checked", true);
							}else if(type == "checkbox" && value != ""){
								obj.filter("[value=" + value + "]").prop("checked", true);
							}else if((type == "text" || type == "hidden" || type == "number") && value != ""){
								obj.val(value);
							}
						}else if(tagName.toLowerCase() == "textarea"){
							obj.html(value);
						// select box 처리
						}else if(tagName.toLowerCase() == "select"){
							obj.val(value);
						}else{
							if($.type(value) == "string"){
								value = _this.defaultString(value).replace(/\n/g,"<br/>")
							}
							obj.html(value);
						}
					}
				}

				// 공통코드
				let isDataCode = $("#" + key).attr("data-code");
				if($.type(isDataCode) !== "undefined"){
					$("#" + key).attr("data-code", value);
					let dataCode = value.substring(0, value.length - 3);
					codeNmArr.push({key:key, value:value, code:dataCode});
				}

				if(key == "attachId"){
					attachId = value;
				}
			});

			// 공통코드 Set
			if(codeNmArr.length > 0){

				$.each(codeNmArr, function(index, data){
					utils.getCommonCode(data.code, function(codeObj){
						$.each(codeObj.list, function(codeIndex, codeData){
							if(codeData.code == data.value){
								let tagName = $("#" + data.key).prop("tagName");
								// input 처리
								if(tagName.toLowerCase() == "input"){
									let type = $("#" + data.key).attr("type").toLowerCase();
									if(type == "text" || type == "hidden"){
										$("#" + data.key).val(codeData.cname);
									}
								// textarea 처리
								}else if(tagName.toLowerCase() == "textarea"){
									$("#" + data.key).text(codeData.cname);
								// select box 처리
								}else if(tagName.toLowerCase() == "select"){
									$("#" + data.key).val(codeData.cname);
								}else{
									$("#" + data.key).text(codeData.cname);
								}
							}
						});
					});
				});
			}

		},
		/**
		 * 데이터 타입
		 * @param dataType
		 * @param value
		 */
		getDataType : function(dataType, value, obj){

			let _this = this;
			if(_this.defaultString(value) == ""){
				value = _this.defaultString(value);
			}

			if($.type(dataType) === "string"){

				switch(dataType){

					case "string" :
						value = value.toString();
					break;
					case "number" :
						value = value.number();
						_this.onlyNumber(obj);
					break;
					case "phone" :
						value = value.phone();
					break;
					case "date" :
						if(utils.defaultString(value) == ""){
							value = "";
						}else{
							value = value.date().print();
						}
					break;
					case "datetime" :
						if(utils.defaultString(value) == ""){
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
						if(utils.defaultString(value) == ""){
							value = "";
						}else{
							if(value.number() == 0){
								value = "";
							}else{
								value = value.date().print("yyyy-mm-dd hh:mi");
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
			return value;
		},
		/**
		 * 숫자만 입력
		 */
		onlyNumber : function(obj){
			obj.bind({
				keydown : function(event){
					event = event || window.event;
					var keyID = (event.which) ? event.which : event.keyCode;
					if ( (keyID >= 48 && keyID <= 57) || (keyID >= 96 && keyID <= 105) || keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
						return;
					}else{
						return false;
					}
				}
				,
				keyup : function(event){
					event = event || window.event;
					var keyID = (event.which) ? event.which : event.keyCode;
					if( keyID == 8 || keyID == 46 || keyID == 37 || keyID == 39 ){
						return;
					}else{
						event.target.value = event.target.value.replace(/[^0-9]/g, "");
					}
				}
			});
		},
		/**
		 * 페이지 이동
		 * @param url
		 * @param param
		 */
		movePage : function(url, param){
			if (url == ""){
				alert("이동할 URL이 설정되지 않았습니다.");
				return;
			}
			if($.type(param) === "undefined" || param == null){
				param = {};
				param.search = "N";
			}else{
				if(this.defaultString(param.search) == ""){
					param.search = "N";
				}
			}
			utils.loading(true);
			$("#nextForm").unwrap();
			var strHtml = "<form id=\"nextForm\" name=\"nextForm\" method=\"post\" action=\"" + url + "\"></form>";
			$("body").wrap(strHtml);	// 화면에 form 등 생성
			$("#nextForm")[0].reset();
			if(typeof param != "undefined"){
				var paramStr = "";
				$.each(param, function(index, item){
					type = typeof(item);

					if(type === "string"){

						paramStr += "<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\" value=\"" + item + "\"/>";

					}else if(type === "number"){

						paramStr += "<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\" value=\"" + item + "\"/>";

					}else{

						$("#nextForm").append("<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\"/>");
						$("#" + index).val(JSON.stringify(item));

					}
				});
				$("#nextForm").append(paramStr);
			}

			$("#nextForm").submit();	// submit
		},
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

			if(str == 0){
				return str;
			}

			if(str == "undefined" || str == "null" || str == undefined || str == null || str == " " || str == ""){
				if(defaultStr != undefined){
					str = defaultStr;
				}
			}

			return str;
		},
		/**
		 * 문자열더하기
		 * @param str
		 * @param defaultStr
		 * @returns {String}
		 */
		addString : function(str, addStr){

			if(typeof addStr === "undefined"){
				defaultStr = "";
			}

			if(str == "undefined" || str == "null" || str == undefined || str == null || str == " " || str == ""){
				str = "";
			}else{
				str = str + addStr;
			}

			return str;

		},
		/**
		 * 전체 문자치환
		 * @param strString
		 * @param strAfter
		 * @param strNext
		 * @returns
		 */
		replaceAll : function(strString, strAfter, strNext) {

			var tmpStr = strString;
			while (tmpStr.indexOf(strAfter) != -1) {
				tmpStr = tmpStr.replace(strAfter, strNext);
			}

			return tmpStr;

		},
		reload: function(){
			let url = document.location.pathname;

			let param;
			if($.type(result.param) === "undefined"){
				param = {};
				param.search = "N";
			}else{
				param = result.param;
				if(utils.defaultString(param.search) == ""){
					param.search = "N";
				}else{
					param.search = "Y";
				}
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
		/**
		 * 앵커
		 * @param target (타겟의 ID, 또는 Class) 주의 #, . 붙여주세요 ex utils.anchor("#testdiv") or utils.anchor(".testdiv")
		 */
		anchor : function(target){

			var offset = 0;

			if(target == "top"){
				offset = 0;
			}else{
				offset = $(target).offset().top;
			}

			$("html, body").animate({scrollTop:offset}, 500);
		}
		/**
		 * 엔터 키 이벤트
		 */
		,enter : function(target, clickTarget) {
			$(target).bind("keydown", function(event) {
				if(event.keyCode == 13) {
					$(clickTarget).click();
					event.preventDefault();			
				}
			})
		},

		/**
		 * input 박스 숫자만 처리
		 * $("타켓아이디").bind("keydown", function(){
		 * 		utils.inputNumber(this);
		 * })
		 */
		inputNumber : function(_this){

    		var keyCode = event.keyCode;
    		var isNumber = false;

    		if((
    			48 <= keyCode && keyCode < 58)
    			|| (96 <= keyCode && keyCode < 106)
    			|| (37 <= keyCode && keyCode < 41)
    			|| keyCode == 8
    			|| keyCode == 46
    			|| keyCode == 17
    			|| keyCode == 67
    			|| keyCode == 86
    			){
    			isNumber = true;
    		}else if(keyCode == 13){
    			isNumber = false;
    			_this.blur();
    		}else{
    			isNumber = false;
    			_this.blur();
    		}

    	},
		getCommNm : function(cd, id){
			let params = {};
			params.code = cd;
			params.type = "select";
			params.targetId = id;
			utils.getCommonCode(params);
		}
    	,
		getCommonNmSubDepth : function(rootCd, higherCd, id){
			let params = {};
			params.code = rootCd;
			params.higher = higherCd;
			params.type = "select";
			params.targetId = id;
			utils.getCommonCodeSubDepth(params);
		}
    	,
    	/**
		 *  공통 코스 조회후 select 세팅 후 값 selected=true 하고 onchange 여부 주기
		 *  cd : 코드값 , id : targetId , cnNm : 세팅할 값 , onChangeYn : onchange 여부 Y/N
		 * @param
		 */
    	commCodeSet : function(cd , id , cdNm , onChangeYn, callBackFn){

    		let params = {};
			if($.type(cd) === "string"){
				params.code = cd;
			}else{
				params = cd;
			}
    		params.targetId = id;

    		var option = {
                    url: "/common/getCommonCode.do",
                    data: (typeof params === "object")?JSON.stringify(params):params,
                    type: "post",
                    cache: false,
                    dataType: "json",
                    contentType: "application/json; charset=UTF-8;",
                    success: function(response) {


            			let list = response.result.list;
            			if(list.length > 0){
        					let target = $("#" + params.targetId);
        					let firstOp = target.find("option").eq(0);
    						target.empty();
    						target.append(firstOp);
        					$.each(list, function(index, listData){
        						let selectOption = $("<option>", {
        							  text : listData.cname
        							 ,value : listData.code
        						});
        						target.append(selectOption);
        					});
        					target.val(cdNm);
        					/*onChange value 변경 금지*/
        					if(onChangeYn == "N"){ target.attr("disabled","true");}
        				}
            			if($.type(callBackFn) != "undefined"){
        					setTimeout(function(){
        						callBackFn();
        					});
        				}
                    },
                    error: function(req, status, error) {
                    },
                    beforeSend: function(xhr){
//                    	var token = $("meta[name='_csrf']").attr("content");
//                    	var header = $("meta[name='_csrf_header']").attr("content");
//                    	xhr.setRequestHeader(header, token);
                    	xhr.setRequestHeader("ajax", true);
                    	//utils.loading(true);
    	    		},
    	            complete: function(){

                		//utils.loading(false);
    			    }
                };

            $.ajax(option);


    	},

    	/**
    	 * 엑셀 다운로드
    	 */
    	excelDown : function(targetId, fileName){

    		let exportTable = $("#" + targetId).clone();
    			exportTable.find("input").each(function (index, elem) { $(elem).remove(); });
    		let excelText = [];
    			excelText.push("<html xmlns:x=\"urn:schemas-microsoft-com:office:excel\">");
    			excelText.push("<head><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet>");
    			excelText.push("<x:Name>Sheet1</x:Name>");
    			excelText.push("<x:WorksheetOptions><x:Panes></x:Panes></x:WorksheetOptions></x:ExcelWorksheet>");
    			excelText.push("</x:ExcelWorksheets></x:ExcelWorkbook></xml></head><body>");
    			excelText.push("<table border=\"1px\">");
    			excelText.push(exportTable.html());
    			excelText.push("</table></body></html>");
    		let blob = new Blob(["\ufeff"+excelText.join("")], { type: "application/vnd.ms-excel;charset=euc-kr" });
    		window.saveAs(blob, fileName + ".xls");

    	}
    	,
    	includeFile : function(filename, filetype, callBackFn){

    		let fileRef;
			if (filetype == "js") { //if filename is a external JavaScript file
				fileRef = document.createElement("script");
				fileRef.setAttribute("type", "text/javascript");
				fileRef.setAttribute("src", filename);
			}else if(filetype == "css"){ //if filename is an external CSS file
				fileRef = document.createElement("link");
				fileRef.setAttribute("rel", "stylesheet");
				fileRef.setAttribute("type", "text/css");
				fileRef.setAttribute("href", filename);
			}

			if(typeof fileRef != "undefined"){
				document.getElementsByTagName("head")[0].appendChild(fileRef);

				if($.type(callBackFn) != "undefined"){
					setTimeout(function(){
						callBackFn();
					}, 500);
				}
			}

    	},
    	/**
    	 * 오늘날짜 취득
    	 * @param type yyyy-mm-dd hh:mi:ss(형식은 맞춰서 쓰세요.)
    	 */
    	getToday : function(type){

    		let today = "".date().print(type);

    		return today;
    	},
    	/**
    	 * 이메일 체크
    	 */
    	emailCheck : function(checkData){

    		let regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			if(!regExp.test(checkData)){
				return false;
			}

			return true;

    	},
    	/**
    	 * 전화번호 체크
    	 */
    	telNoCheck : function(checkData){

    		let regExp = /^\d{2,3}-\d{3,4}-\d{4}$/;
			if(!regExp.test(checkData)){
				return false;
			}
			return true;
    	},
    	/**
    	 * 휴대폰 번호 체크
    	 */
    	hpNoCheck : function(checkData){

    		let regExp = /^01([0|1|6|7|8|9]?)-?([0-9]{3,4})-?([0-9]{4})$/;
			if(!regExp.test(checkData)){
				return false;
			}

			return true;

    	},
    	/**
    	 * 권한 재설정
    	 */
    	rightReload : function(){
    		let menuUrl = document.location.pathname;
    		let menuName, menuId;
    		$.each(menuList, function(idx, menuData){
    			if(menuData.lv > 2 && utils.defaultString(menuData.menuUrl).indexOf(document.location.pathname) > -1){
    				menuName = menuData.menuName;
    				menuId   = menuData.menuId;
    				menuUrl  = document.location.pathname;
    				isBookMarkButton = true;
    				return false;
    			}
    		});
    		setAdMenu(menuId);
    	},
    	triggerSanRackUnit : function(exclude){
    		setTimeout(function(){ 
    			window.top.$('iframe').each(function(){
        			if(typeof $(this)[0].contentWindow.sanRackUnit === 'function'){ 
        				$(this)[0].contentWindow.sanRackUnit(exclude, this);
        			}
        		}); 
    		}, 10);
    	},
		cachedScript : function( url, options ) {
			options = $.extend( options || {}, {
			    dataType: "script",
			    cache: true,
			    url: url
			});
			return jQuery.ajax( options );
		},
    	/**
    	 * 빈 문자열 체크
    	 */
		isEmpty : function(str){
			if(typeof str == "undefined" || str == undefined || str == null || str == "null" || str == "")
		        return true;
		    else
		        return false ;
		}
    	
}


/**
  * null 체크
 **/
function isNull(str) {
	if (str == null) return true;
	if (str == "NaN") return true;
	
	
	var chkStr = new String(str);
	if (chkStr.valueOf() == "undefined") return true;
	if (chkStr == null) return true;
	if (chkStr.toString().length == 0) return true;
	
	return false;
}


/**
  * 숫자에 콤마 찍어주기
 **/
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}


/**
  * Ajax 호출함수
 **/
function serverCall(sendData, sendUrl, callback) {
	$.ajax({
		url: sendUrl,
		type: "POST",
		data: sendData,
		dataType: "json",
		success: function(data) {
			console.log("data \t:\n" + JSON.stringify(data));			
			callback(data);
		}
	});
}
