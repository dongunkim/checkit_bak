let failCnt = 0;
initFunction = function() {
    eventFunction();
}

eventFunction = function() {	
	history.pushState(null, null, location.href);
	window.onpopstate = function(event){
		history.go(1);
	};

	// 로그인
	$("#loginBtn").bind("click", function() {
		
		if($("#userId").val().length == 0){
			DIALOG.alert("아이디를 입력하여 주십시오.");
			return;
		}
		
		if($("#passwd").val().length == 0){
			DIALOG.alert("비밀번호를 입력하여 주십시오.");
			return;
		}
		
		utils.loading(true);
		var url = "/admin/login/adLogin.do";
		var params = {};
			params.userId = $("#userId").val();
			params.passwd = $("#passwd").val();
			//params.sndAnswer = answer;
		utils.ajax(url, params, function(response) {
			console.log('------------------------> response');			
			console.log(response);
			if (response.result.errorCode == "98"){
				failCnt = response.result.failCnt;
				
				
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);
			} else if(response.result.errorCode == "99"){
				failCnt = response.result.failCnt;
				
				if($("#captchaDiv").css("display") == "none" && failCnt >= 5){
					DIALOG.alert("로그인 5회이상 실패하셨습니다.<br/>재 로그인시 아이디/비밀번호와 <br/>자동 로그인 방지번호를 함께 <br/>입력하여 주십시오.");
				}else{
					DIALOG.alert(response.result.errorMessage);
				}
				
				$("#answer").val("");
				utils.loading(false);
			}else if(response.result.errorCode == "00"){
				utils.loading(false);
				
				if($.type(result.returnUrl) !== "undefined" && (result.returnUrl).indexOf("/common/sendUrl/") == 0){
					utils.movePage(result.returnUrl);	
				} else {
					utils.movePage("/admin/main/main0101Page.do");	
				}
				
			}else if(response.result.errorCode == "01"){
				utils.loading(false);
				// 비밀번호 변경 팝업
				DIALOG.confirm({
					msg : "비밀번호 변경일이 3개월을 초과하였습니다.</br> 변경 후 로그인이 가능합니다."
				}, function(){
					if(this.key == "ok"){
						$(this).popup({
							 title : "개인정보 변경"
							,width : "780px"
							,height: "430px"
							,url : "/admin/main/main0107Pop.do"
							,params : {oldPwCnk: "Y"}
							,callBackFn : function(data){
								closePopup();
								utils.movePage("/admin/main/main0101Page.do");
							}
						});
					}else{
						utils.movePage("/admin/login/logout.do");
					}
				});
				
			}else{
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);
			}
		}, false);

	});
	
		
	// id 찾기 팝업
	$("#idSearchBtn").popup({
		 title : "ID 찾기"
		,width : "780px"
		,height: "430px"
		,url : "/admin/login/login0102Pop.do"
	});

	// 비밀번호 찾기 팝업
	$("#pwdSearchBtn").popup({
		 title : "비밀번호 찾기"
		,width : "780px"
		,height: "550px"
		,url : "/admin/login/login0103Pop.do"
	});

	// 사용자 등록요청 팝업
	$("#userAddReqBtn").popup({
		 title : "사용자등록요청"
		,width : "720px"
		,height: "680px"
		,url : "/admin/login/login0104Pop.do"
	});

	utils.enter("#userId, #passwd", "#loginBtn");

}
