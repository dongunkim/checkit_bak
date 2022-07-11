
/**
 * 화면제어용 JS
 * @author devkimsj
 */
$(window).load(function(){

	let intervalTime = 6000 * 100; // 10분
	let sessionTimer;
	sessionTimer = setInterval(function(){
		utils.ajax("/common/accessTime.do", {}, rtnTime, false);
	}, intervalTime);

	rtnTime = function(data){

		intervalTime = Number(Number(data.result.sessionTime) * 1000)  - 6000;

		if(Number(data.result.sessionTime) <= 63){
			clearInterval(sessionTimer);

			// 세션만료 팝업
			$(this).popup({
				 title : "세션시간 만료 알림"
				,width : "700px"
				,height : "370px"
				,scrolling : "auto"
				,url : "/common/comm0109Pop.do"
				,callBackFn : function(data){

					if(data.success == "00"){
						utils.ajax("/common/accessNew.do", {}, function(){
							clearInterval(sessionTimer);
							intervalTime = 4000;
							sessionTimer = setInterval(function(){
								utils.ajax("/common/accessTime.do", {}, rtnTime, false);
							}, intervalTime);
						});
					}else{
						utils.movePage("/admin/login/logout.do");
					}

				}
			});

		}else{
			clearInterval(sessionTimer);
			sessionTimer = setInterval(function(){
				utils.ajax("/common/accessTime.do", {}, rtnTime, false);
			}, intervalTime);
		}
	};

	setAdMenu(menuId);

	// 대쉬보드 왼쪽 건수
//	utils.ajax("/admin/main/mainLeftCnt.do", {}, function(data){
//
//		// 메인 왼쪽영역
//		$("#main0101Cnt").text(data.result.main0101Cnt.totCnt);
//		$("#main0102Cnt").text(data.result.main0102Cnt.totCnt);
//		$("#main0103Cnt").text(data.result.main0103Cnt.totCnt);
//		$("#main0104Cnt").text(data.result.main0104Cnt.icnt);
//		$("#main0105Cnt").text(data.result.main0105Cnt.totCnt);
//
//		// 왼쪽 메뉴 삽입
//		let leftMenu = [];
//		leftMenu.push("<div class=\"realtime_area_side\">");
//		leftMenu.push("<ul>");
//		leftMenu.push("<li>");
//		leftMenu.push("<div class=\"count_re\">");
//		leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/wkmn/wkmn0201List.do')\">일반작업요청현황</div>");
//		leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0101Cnt.totCnt) + "</div>");
//		leftMenu.push("</div>");
//		leftMenu.push("<div class=\"line\"></div>");
//		leftMenu.push("</li>");
//		leftMenu.push("<li>");
//		leftMenu.push("<div class=\"count_re\">");
//		leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/wkmn/wkmn0701List.do')\">영업지원작업현황</div>");
//		leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0102Cnt.totCnt) + "</div>");
//		leftMenu.push("</div>");
//		leftMenu.push("<div class=\"line\"></div>");
//		leftMenu.push("</li>");
//		leftMenu.push("<li>");
//		leftMenu.push("<div class=\"count_re\">");
//		leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/cumn/cumn0501List.do')\">상담요청현황</div>");
//		leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0103Cnt.totCnt) + "</div>");
//		leftMenu.push("</div>");
//		leftMenu.push("<div class=\"line\"></div>");
//		leftMenu.push("</li>");
//		leftMenu.push("<li>");
//		leftMenu.push("<div class=\"count_re\">");
//		leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/cumn/cumn0301Reg.do')\">방문출입현황</div>");
//		leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0104Cnt.icnt) + "</div>");
//		leftMenu.push("</div>");
//		leftMenu.push("<div class=\"line\"></div>");
//		leftMenu.push("</li>");
//		leftMenu.push("<li>");
//		leftMenu.push("<div class=\"count_re\">");
//		leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/cumn/cumn0401Reg.do')\">장비반출입현황</div>");
//		leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0105Cnt.totCnt) + "</div>");
//		leftMenu.push("</div>");
//		leftMenu.push("<div class=\"line\"></div>");
//		leftMenu.push("</li>");
//		
//		if($("#submenu"+menuId).parents("ul").prev().text() == '인프라관리' || $("#submenu"+menuId).parents("ul").parents("ul").prev().text() == '인프라관리'){
//			leftMenu.push("<li>");
//			leftMenu.push("<div class=\"count_re\">");
//			leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/rscr/rscr2704List.do')\">인프라작업현황</div>");
//			leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0106Cnt.totCnt) + "</div>");
//			leftMenu.push("</div>");
//			leftMenu.push("<div class=\"line\"></div>");
//			leftMenu.push("</li>");
//			leftMenu.push("<li>");
//			leftMenu.push("<div class=\"count_re\">");
//			leftMenu.push("<div class=\"title\" onclick=\"utils.movePage('/admin/ermn/ermn0301List.do')\">인프라장애현황</div>");
//			leftMenu.push("<div class=\"number\">" + utils.defaultString(data.result.main0107Cnt.totCnt) + "</div>");
//			leftMenu.push("</div>");
//			leftMenu.push("<div class=\"line\"></div>");
//			leftMenu.push("</li>");
//		}
//		
//		leftMenu.push("</ul>");
//		leftMenu.push("</div>");
//
//		$(".contens_bd").prepend(leftMenu.join(""));
//		leftView();
//
//	}, false);

	// 알림
//	utils.ajax("/admin/main/main0106PopCount.do", {}, function(data){
//		if(data.result.cnt.number() != 0){
//			$("#pushBtn").empty().append("<p>" + data.result.cnt + "</p>");
//		}
//	}, false);

	// 쪽지
//	utils.ajax("/admin/main/main0107PopCount.do", {}, function(data){
//		if(data.result.cnt.number() != 0){
//			$("#messageBtn").empty().append("<p>" + data.result.cnt + "</p>");
//		}
//	}, false);

	// 알림 팝업
//	$("#pushBtn").popup({
//		 title : "알림 정보 내역"
//		,width : "1000px"
//		,height : "650px"
//		,scrolling : "auto"
//		,url : "/admin/main/main0106ListPop.do"
//		,callBackFn : function(data){
//
//			movePage = function(url, params){
//
//				utils.loading(true);
//				$("#pushMoveForm").remove();
//				var strHtml = "<form id=\"pushMoveForm\" name=\"pushMoveForm\" method=\"post\" action=\"" + url + "\"></form>";
//				$("body").append(strHtml);	// 화면에 form 등 생성
//				if(typeof params != "undefined"){
//					var paramStr = "";
//					$.each(params, function(index, item){
//						type = typeof(item);
//
//						if(type === "string"){
//
//							paramStr += "<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\" value=\"" + item + "\"/>";
//
//						}else if(type === "number"){
//
//							paramStr += "<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\" value=\"" + item + "\"/>";
//
//						}else{
//
//							$("#pushMoveForm").append("<input type=\"hidden\" id=\"" + index + "\" name=\"" + index + "\"/>");
//							$("#" + index).val(JSON.stringify(item));
//
//						}
//					});
////					paramStr += "<input type=\"hidden\" id=\"" + $("meta[name='_csrf_parameter']").attr("content") + "\" name=\"" + $("meta[name='_csrf_parameter']").attr("content") + "\" value=\"" + $("meta[name='_csrf']").attr("content") + "\"/>";
//					$("#pushMoveForm").append(paramStr);
//				}
//
//				$("#pushMoveForm").submit();	// submit
//			}
//			const menuCode = data.menuCode;
//
//			if(menuCode == "23") { 		//온라인 상담
//
//				let params = JSON.parse(data.linkData);
//					// 알림에서 들어갈때는 목록에서 검색하는 값인 searchParam이 없으므로 search 값을 "N"으로 처리해서 전달한다.
//					params.searchParam = [];
//					params.searchParam.push({search : "N"});
//					movePage("/admin/cumn/cumn0502Detail.do", params);
//
//			}else if(menuCode == "26") {   	//정규작업
//
//				let params = JSON.parse(data.linkData);
//					movePage("/admin/sale/sale0202Detail.do", params);
//
//			}else if(menuCode == "115") {    	//기술지원 요청
//
//				let params = JSON.parse(data.linkData);
//					// 알림에서 들어갈때는 목록에서 검색하는 값인 searchParam이 없으므로 search 값을 "N"으로 처리해서 전달한다.
//					params.searchParam = [];
//					params.searchParam.push({search : "N"});
//					movePage("/admin/sale/sale0402Detail.do", params);
//
//			}else if(menuCode == "27") {     	//일반작업
//
//				let params = JSON.parse(data.linkData);
//					movePage("/admin/wkmn/wkmn0201Detail.do", params);
//
//			}else if(menuCode == "75") {    	//승인작업
//
//				let params = JSON.parse(data.linkData);
//
//				// 구분이 JK(정규승인) 일때 승인처리 정규탭으로, 아닐시 승인처리 내부탭으로 이동한다. (wkmn01ServiceImpl - param.put("gubunText", "JK")) , (wkmn03ServiceImpl - param.put("gubunText", "NB"))
//				if(params.gubunText == "JK"){
//					movePage("/admin/wkmn/wkmn0603Detail.do", params);
//				}else{
//					movePage("/admin/wkmn/wkmn0604Detail.do", params);
//				}
//
//
//			}else if(menuCode == "54") {    	//사용자등록
//
//				let params = JSON.parse(data.linkData);
//					movePage("/admin/sysm/sysm0101List.do", params);
//
//			}else if(menuCode == "73") {    	//OP작업
//
//				let params = JSON.parse(data.linkData);
//					params.searchParam = [];
//					params.searchParam.push({search : "N"});
//					movePage("/admin/wkmn/wkmn0402Detail.do", params);
//
//			}else if(menuCode == "159") {    	//협력사 작업공지
//
//				/*let params = JSON.parse(data.linkData);
//					movePage("/admin/sale/sale0402Detail.do", params);*/
//
//			}else if(menuCode == "1052") {    	//인프라 작업
//
//				let params = JSON.parse(data.linkData);
//					movePage("/admin/rscr/rscr2704Detail.do", params);
//
//			}
//			// 팝업닫기
//			closePopup();
//		}
//	});

	// 쪽지 팝업
//	$("#messageBtn").popup({
//		 title : "쪽지"
//		,width : "1000px"
//		,height : "750px"
//		,scrolling : "auto"
//		,url : "/admin/main/main0104ListPop.do"
//		,callBackFn : function(data){
//			if(data.viewCountDown){
//				let viewCount = $("#messageBtn").text().number();
//				if(viewCount <= 1){
//					$("#messageBtn").empty();
//				}else{
//					$("#messageBtn").empty().append("<p>" + (viewCount - 1) + "</p>");
//				}
//			}
//		}
//	});

	// 정보변경
	$("#changeInfoBtn").popup({
		 title : "개인정보 변경"
		,width : "710px"
		,height : "352px"
		,url : "/admin/main/main0107Pop.do"
		,callBackFn : function(data){
			if(data.success){
				closePopup();
				utils.reload();
				// 개인정보변경 팝업
////				$(this).popup({
////					 title : "개인정보변경"
////					,width : "720px"
////					,height : "530px"
////					,url : "/admin/main/main0108Pop.do"
////					,callBackFn : function(data){
////						if(data.success){
////							closePopup();
////							utils.reload();
////						}
////					}
////				});
			}else if(data.close){
				closePopup();
			}
		}
	});

	// 공지/게시판
	$("#boardArea").unbind().bind("click", function(){
		utils.movePage("/admin/bord/bord0101List.do");
	});

	// 전화번호부
	$("#telBoardArea").unbind().bind("click", function(){
		utils.movePage("/admin/bord/bord0601List.do");
	});

	// 파일관리
	$("#fileMngBtn").unbind().bind("click", function(){
		utils.movePage("/admin/bord/bord0501List.do");
	});

	// IP검색
	$("#ipSearchArea").unbind().bind("click", function(){
		// 기본팝업
		$(this).popup({
			 title : "IP 검색"
			,width : "1300px"
			,height: "600px"
			,scrolling : "auto"
			,url : "/admin/bord/bord0701Pop.do"
		});
    });
	
	// KMS
	$("#kmsArea").unbind().bind("click", function(){
		utils.movePage("/admin/main/main0109List.do");
	});

	// 사이트맵 열기
	$("#sitemapBtn").unbind().bind("click", function(){
        $("#sitemap_wrap").slideDown(600);
    });

	// 사이트맵 닫기
    $("#sitemap_close").click(function(){
        $("#sitemap_wrap").hide();
    });


    // 통합검색
	$("#allSearchBtn").unbind().bind("click", function(){

		let searchWord = $("#allSearchWord").val();
		if(searchWord == ""){
			DIALOG.alert("통합검색할 단어를 입력해주세요.", function(){
				$("#allSearchWord").focus();
			});
			return false;
		}

		if(searchWord.length < 2){
			DIALOG.alert("통합검색할 단어를 2글자 이상 입력해주세요.", function(){
				$("#allSearchWord").focus();
			});
			return false;
		}

		let params = {};
			params.searchWord = searchWord;
		utils.movePage("/admin/sech/sech0101Page.do", params);
	});
	// 통합검색 텍스트 엔터
	$("#allSearchWord").enter("#allSearchBtn");
	
	//드랍다운 버튼
	$(".btn_dropdown").unbind().bind("click", function(){
		$('.btn_dropdown').toggleClass('click');
        $('.dropdown_box').toggleClass('on');
	});
	
	// URL복사
	$("#urlCopy").unbind().bind("click", function(){
		let params = {};	
		if(toPageUrl.indexOf("?")>0){
			params.realUrl = toPageUrl.substring(0,toPageUrl.indexOf("?"));
			params.realParam = toPageUrl.substring(toPageUrl.indexOf("?")+1, toPageUrl.length);
		}else{
			params.realUrl = toPageUrl.substring(0,toPageUrl.indexOf("?"));
		}
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
					var textarea = document.createElement("textarea");
					document.body.appendChild(textarea);
					textarea.value = location.origin + response.result.shortUrl;
					textarea.select();
					
					let success = document.execCommand("copy");
					if(success){
						DIALOG.alert("URL이 복사되었습니다.");
						document.body.removeChild(textarea);
						return false;
					}else{
						DIALOG.alert("이 브라우저는 지원하지 않습니다.");
						document.body.removeChild(textarea);
						return false;
					}
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
	

});

setMenu = function(){

	let menuLink = [];

	$("#menuArea").empty();

	//	// 메뉴 만들기
	$.each(menuList, function(index, menudata){
		if(!(utils.defaultString(rid) == "103" && menudata.menuId == "9") && !(utils.defaultString(rid) == "103" && menudata.menuId == "45")){
			// 1레벨
			if(menudata.lv === 2){
				$("#menuArea").append("<li id=\"menu" + menudata.menuId + "\"><a href=\"javascript:void(0)\">" + menudata.menuName + "</a><ul id=\"submenu" + menudata.menuId + "\" style=\"display:none\"></ul></li>");
			}
			// 2레벨
			if(menudata.lv === 3){
				$("#submenu" + menudata.upMenuId).append("<li id=\"submenu" + menudata.menuId + "\" ><a href=\"javascript:void(0)\" data-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
			}
			// 3레벨
			if(menudata.lv === 4){
				if($("#submenu" + menudata.upMenuId).find("ul").length == 0){
					$("#submenu" + menudata.upMenuId).find("a").addClass("rightarrow").after("<ul style=\"display:none\"></ul>");
				}
				$("#submenu" + menudata.upMenuId).find("ul").append("<li id=\"submenu" + menudata.menuId + "\"><a href=\"javascript:void(0)\" data-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
			}
			menuLink["menu" + menudata.menuId] = utils.defaultString(menudata.menuUrl);
		}
	});

	// 메뉴 1레벨 이벤트 처리
	$("#menuArea, #menuArea > li").unbind().bind({
		mouseover : function(){
			const menuId = $(this).attr("id");
			const menuDisplay = $("#sub" + menuId).css("display");
			if(menuDisplay == "none"){
				$("#menuArea > li > ul").hide();
				$("#menuArea").find(".active").removeClass("active");
				$(this).addClass("active");
				$("#sub" + menuId).show();

				$("#menuArea > li > ul > li").unbind().bind({
					mouseover : function(){
						$(".rightarrow").next().hide();
						$(this).find("ul").show();
					}
				})

			}
		}
		,
		mouseleave : function(){
			if($(this).parents("#menuArea").length == 0){
				$("#menuArea").find(".active").removeClass("active");
				$("#menuArea").find("ul").hide();
			}
		}
	});

	// 클릭이벤트 처리
	$("[data-link]").unbind().bind("click", function(){
		let linkId = $(this).attr("data-link");
		let link = utils.defaultString(menuLink[linkId]);
		if(link == ""){
			if($("[data-link=\"" + linkId + "\"]").next().find("li").length == 0){
				alert("개발중입니다.");
			}
		}else{

			// 서비스 통계는 popup
			if(link.indexOf("stat0501Page") > 0){
				window.open(link);
			}else{
				utils.movePage(link);
			}

		}
	});

	// 즐겨찾기
	let bookMarkObj = JSON.parse(utils.defaultString(localStorage.getItem("bookMark"), "[]"));
	let isBookMarkButton = false;
	let menuName, menuId, menuUrl;
	// 즐겨찾기 버튼 추가
	$.each(menuList, function(idx, menuData){
		if(menuData.lv > 2 && utils.defaultString(menuData.menuUrl).indexOf(document.location.pathname) > -1){
			menuName = menuData.menuName;
			menuId   = menuData.menuId;
			menuUrl  = document.location.pathname;
			isBookMarkButton = true;
			return false;
		}
	});

	addBookMarkHtmlFn = function(){

		$("#bookMarkArea").remove();
		let bookMarkHtml = [];
			bookMarkHtml.push("<div style=\"position: absolute;top: 60px;right: -185px;padding: 10px;border: 2px solid #42af50;border-radius: 10px;font-size: 0.723em;background-color: #efefef;color: #fff;\" id=\"bookMarkArea\">");
			bookMarkHtml.push("<dl style=\"line-height: 24px;margin: 0;padding: 0;\">");

			if(isBookMarkButton){

				// 즐겨찾기를 할수 있는 메뉴일경우
				if(isBookMarkButton){
					let isBookMark = false;
					let bookMarkYn = "N";
					$.each(bookMarkObj, function(idx, bookMark){
						if(bookMark.menuId == menuId){
							bookMarkYn = "Y";
							return false;
						}
					});

					let backgroundImage;
					if(bookMarkYn == "Y"){
						backgroundImage = "/resources/admin/images/icon/star_on.png";
					}else{
						backgroundImage = "/resources/admin/images/icon/star_off.png";
					}
					bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기 <button class=\"bookmark_star\" id=\"addBookMarkBtn\" style=\"background:url('" + backgroundImage + "')no-repeat; background-size:21px;\"></button></dt>");

				}else{
					bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기</dt>");
				}

			}else{
				bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기</dt>");
			}

			bookMarkHtml.push("<dd style=\"background-color: #fff;color: #000;padding: 5px;margin-top: 5px;border-radius: 5px;width:145px;\">");

			if(bookMarkObj.length > 0){
				$.each(bookMarkObj, function(idx, data){
					bookMarkHtml.push("<div style=\"font-size:14px;\"><a href=\"javascript:void(0);\" style=\"color:#42af50 !important; font-weight:400;\" onclick=\"utils.movePage('" + data.menuUrl + " ');\">" + data.menuName + "</a><img src=\"/resources/admin/images/button/cancelBtn.gif\" style=\"float:right;cursor: pointer;\" data-deletebookmark=\"" + data.menuId + "\"></div>");
				});
			}else{
				bookMarkHtml.push("<div style=\"font-size:14px;\">정보가 없습니다.</div>");
			}

			bookMarkHtml.push("</dd>");
			bookMarkHtml.push("</dl>");
			bookMarkHtml.push("</div>");

		$(".contens_bd").append(bookMarkHtml.join(""));

		// 즐겨찾기 버튼클릭시
		$("#addBookMarkBtn").unbind().bind("click", function(){

			let _this = this;
			if($(_this).data("bookmark") == "Y"){

				DIALOG.confirm({
					title: "알림",
					msg: "즐겨찾기에서 삭제하시겠습니까?"
				}, function () {
					if(this.key == "ok") {
						$.each(bookMarkObj, function(idx, data){
							if(data.menuId == menuId){
								bookMarkObj.splice(idx, 1);
								return false;
							}
						});

						localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

						DIALOG.alert("삭제 되었습니다.", function(){
							$(_this).css("background", "url('/resources/admin/images/icon/star_off.png')");
							$(_this).data("bookmark", "N");
							addBookMarkHtmlFn();
						});
					}
				});

			}else{

				if(bookMarkObj.length >= 7){
					DIALOG.alert("즐겨찾기는 최대 7개까지 가능합니다.");
					return false;
				}

				DIALOG.confirm({
					title: "알림",
					msg: "즐겨찾기 등록하시겠습니까?"
				}, function () {
					if (this.key == "ok") {
						bookMarkObj.push({menuName: menuName, menuId: menuId, menuUrl: menuUrl});
						localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

						DIALOG.alert("등록 되었습니다.", function(){
							$(_this).css("background", "url('/resources/admin/images/icon/star_on.png')");
							$(_this).data("bookmark", "Y");
							addBookMarkHtmlFn();
						});
					}
				});
			}
		});

		$("[data-deletebookmark]").unbind().bind("click", function(){

			let _this = this;
			let delMenuId = $(_this).data("deletebookmark");

			DIALOG.confirm({
				title: "알림",
				msg: "즐겨찾기에서 삭제하시겠습니까?"
			}, function () {
				if(this.key == "ok") {
					$.each(bookMarkObj, function(idx, data){
						if(data.menuId == delMenuId){
							bookMarkObj.splice(idx, 1);

							if(utils.defaultString(data.menuUrl).indexOf(document.location.pathname) > -1){
								$("#addBookMarkBtn").css("background-color", "#b9babc");
								$("#addBookMarkBtn").data("bookmark", "N");
							}

							return false;
						}
					});

					localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

					DIALOG.alert("삭제 되었습니다.", function(){
						addBookMarkHtmlFn();
					});
				}
			});
		});
	}
	// 오른쪽의 즐겨찾기 html 추가
	addBookMarkHtmlFn();
}

setAdMenu = function(checkMenuId){

	let nowUrl = location.pathname;
	let menuRollRead, menuRollWrite;
	let menuLink = [];

	$("#menuArea").empty();

	// 메뉴 만들기
	$.each(menuList, function(index, menudata){
		// 1레벨
		if(menudata.lv === 2 && menudata.useYn === "Y" && menudata.isDown === "Y"){
			$("#menuArea").append("<li id=\"menu" + menudata.menuId + "\"><a href=\"javascript:void(0)\">" + menudata.menuName + "</a><ul id=\"submenu" + menudata.menuId + "\" style=\"display:none\"></ul></li>");
		}
		// 2레벨
		if(menudata.lv === 3 && menudata.useYn === "Y"  && (menudata.methodR === "R" || menudata.methodW === "W")){
			$("#submenu" + menudata.upMenuId).append("<li id=\"submenu" + menudata.menuId + "\" ><a href=\"javascript:void(0)\" data-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
		}
		// 3레벨
		if(menudata.lv === 4 && menudata.useYn === "Y"  && (menudata.methodR === "R" || menudata.methodW === "W")){
			if($("#submenu" + menudata.upMenuId).find("ul").length == 0){
				$("#submenu" + menudata.upMenuId).find("a").addClass("rightarrow").after("<ul style=\"display:none\"></ul>");
			}
			$("#submenu" + menudata.upMenuId).find("ul").append("<li id=\"submenu" + menudata.menuId + "\"><a href=\"javascript:void(0)\" data-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
		}

		if(checkMenuId == menudata.menuId){
			menuRollRead  = menudata.methodR;
			menuRollWrite = menudata.methodW;
		}

		menuLink["menu" + menudata.menuId] = utils.defaultString(menudata.menuUrl);
	});

	// 메뉴 1레벨 이벤트 처리
	$("#menuArea, #menuArea > li").unbind().bind({
		mouseover : function(){
			const menuId = $(this).attr("id");
			const menuDisplay = $("#sub" + menuId).css("display");
			if(menuDisplay == "none"){
				$("#menuArea > li > ul").hide();
				$("#menuArea").find(".active").removeClass("active");
				$(this).addClass("active");
				$("#sub" + menuId).show();

				$("#menuArea > li > ul > li").unbind().bind({
					mouseover : function(){
						$(".rightarrow").next().hide();
						$(this).find("ul").show();
					}
				})

			}
		}
		,
		mouseleave : function(){
			if($(this).parents("#menuArea").length == 0){
				$("#menuArea").find(".active").removeClass("active");
				$("#menuArea").find("ul").hide();
			}
		}
	});

	// 클릭이벤트 처리
	$("[data-link]").unbind().bind("click", function(){
		let linkId = $(this).attr("data-link");
		let link = utils.defaultString(menuLink[linkId]);
		if(link == ""){
			if($("[data-link=\"" + linkId + "\"]").next().find("li").length == 0){
				alert("개발중입니다.");
			}
		}else{

			// 서비스 통계는 popup
			if(link.indexOf("stat0501Page") > 0){
				window.open(link);
			}else{
				utils.movePage(link);
			}

		}
	});

	// 즐겨찾기
	let bookMarkObj = JSON.parse(utils.defaultString(localStorage.getItem("bookMark"), "[]"));
	let isBookMarkButton = false;
	let menuName, menuId, menuUrl;
	// 즐겨찾기 버튼 추가
	$.each(menuList, function(idx, menuData){
		if(menuData.lv > 2 && utils.defaultString(menuData.menuUrl).indexOf(document.location.pathname) > -1){
			menuName = menuData.menuName;
			menuId   = menuData.menuId;
			menuUrl  = document.location.pathname;
			isBookMarkButton = true;
			return false;
		}
	});

	addBookMarkHtmlFn = function(){

		$("#bookMarkArea").remove();
		let bookMarkHtml = [];
			bookMarkHtml.push("<div style=\"position: absolute;top: 60px;right: -185px;padding: 10px;border: 2px solid #42af50;border-radius: 10px;font-size: 0.723em;background-color: #efefef;color: #fff;\" id=\"bookMarkArea\">");
			bookMarkHtml.push("<dl style=\"line-height: 24px;margin: 0;padding: 0;\">");

			if(isBookMarkButton){

				// 즐겨찾기를 할수 있는 메뉴일경우
				if(isBookMarkButton){
					let isBookMark = false;
					let bookMarkYn = "N";
					$.each(bookMarkObj, function(idx, bookMark){
						if(bookMark.menuId == menuId){
							bookMarkYn = "Y";
							return false;
						}
					});

					let backgroundImage;
					if(bookMarkYn == "Y"){
						backgroundImage = "/resources/admin/images/icon/star_on.png";
					}else{
						backgroundImage = "/resources/admin/images/icon/star_off.png";
					}
					bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기 <button class=\"bookmark_star\" id=\"addBookMarkBtn\" style=\"background:url('" + backgroundImage + "')no-repeat; background-size:21px;\"></button></dt>");

				}else{
					bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기</dt>");
				}

			}else{
				bookMarkHtml.push("<dt style=\"color:#767070;font-size: 16px;font-weight: 600;\">즐겨찾기</dt>");
			}

			bookMarkHtml.push("<dd style=\"background-color: #fff;color: #000;padding: 5px;margin-top: 5px;border-radius: 5px;width:145px;\">");

			if(bookMarkObj.length > 0){
				$.each(bookMarkObj, function(idx, data){
					bookMarkHtml.push("<div style=\"font-size:14px;\"><a href=\"javascript:void(0);\" style=\"color:#42af50 !important; font-weight:400;\" onclick=\"utils.movePage('" + data.menuUrl + " ');\">" + data.menuName + "</a><img src=\"/resources/admin/images/button/cancelBtn.gif\" style=\"float:right;cursor: pointer;\" data-deletebookmark=\"" + data.menuId + "\"></div>");
				});
			}else{
				bookMarkHtml.push("<div style=\"font-size:14px;\">정보가 없습니다.</div>");
			}

			bookMarkHtml.push("</dd>");
			bookMarkHtml.push("</dl>");
			bookMarkHtml.push("</div>");

		$(".contens_bd").append(bookMarkHtml.join(""));

		// 즐겨찾기 버튼클릭시
		$("#addBookMarkBtn").unbind().bind("click", function(){

			let _this = this;
			if($(_this).data("bookmark") == "Y"){

				DIALOG.confirm({
					title: "알림",
					msg: "즐겨찾기에서 삭제하시겠습니까?"
				}, function () {
					if(this.key == "ok") {
						$.each(bookMarkObj, function(idx, data){
							if(data.menuId == menuId){
								bookMarkObj.splice(idx, 1);
								return false;
							}
						});

						localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

						DIALOG.alert("삭제 되었습니다.", function(){
							$(_this).css("background", "url('/resources/admin/images/icon/star_off.png')");
							$(_this).data("bookmark", "N");
							addBookMarkHtmlFn();
						});
					}
				});

			}else{

				if(bookMarkObj.length >= 7){
					DIALOG.alert("즐겨찾기는 최대 7개까지 가능합니다.");
					return false;
				}

				DIALOG.confirm({
					title: "알림",
					msg: "즐겨찾기 등록하시겠습니까?"
				}, function () {
					if (this.key == "ok") {
						bookMarkObj.push({menuName: menuName, menuId: menuId, menuUrl: menuUrl});
						localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

						DIALOG.alert("등록 되었습니다.", function(){
							$(_this).css("background", "url('/resources/admin/images/icon/star_on.png')");
							$(_this).data("bookmark", "Y");
							addBookMarkHtmlFn();
						});
					}
				});
			}
		});

		$("[data-deletebookmark]").unbind().bind("click", function(){

			let _this = this;
			let delMenuId = $(_this).data("deletebookmark");

			DIALOG.confirm({
				title: "알림",
				msg: "즐겨찾기에서 삭제하시겠습니까?"
			}, function () {
				if(this.key == "ok") {
					$.each(bookMarkObj, function(idx, data){
						if(data.menuId == delMenuId){
							bookMarkObj.splice(idx, 1);

							if(utils.defaultString(data.menuUrl).indexOf(document.location.pathname) > -1){
								$("#addBookMarkBtn").css("background-color", "#b9babc");
								$("#addBookMarkBtn").data("bookmark", "N");
							}

							return false;
						}
					});

					localStorage.setItem("bookMark", JSON.stringify(bookMarkObj));

					DIALOG.alert("삭제 되었습니다.", function(){
						addBookMarkHtmlFn();
					});
				}
			});

		});

	}

	if(utils.defaultString(menuRollWrite) != "W"){
		if($("[data-write]").parents("td").length > 0){
			$("[data-write]").parents("td").unbind();
		}
		// 쓰기권한 삭제
		$("[data-write]").unbind().remove();
	}

	if(utils.defaultString(menuRollRead) != "R"){
		if($("[data-read]").parents("td").length > 0){
			$("[data-read]").parents("td").unbind();
		}
		// 읽기권한 삭제
		$("[data-read]").unbind().remove();
	}

	// 오른쪽의 즐겨찾기 html 추가
	addBookMarkHtmlFn();

	rollFn();
}