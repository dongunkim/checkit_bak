initFunction = function() {
	console.log('-----------------------> loginPage.js called');
    eventFunction();
}

eventFunction = function() {	
	history.pushState(null, null, location.href);
	window.onpopstate = function(event){
		history.go(1);
	};

	console.log('-----------------------> loginPage.js > eventFunction');
	
	$("#loginBtn").bind("click", function() {		
		if ($("#userId").val().trim().length == 0) {
			DIALOG.alert("아이디를 입력하세요.", function() {
				$("#userId").focus();				
			});
			return;
		}
		
		if ($("#passwd").val().trim().length == 0) {
			DIALOG.alert("비밀번호를 입력하세요.", function() {
				$("#passwd").focus();	
			});			
			return;
		}
		
		utils.loading(true);
		var url = "/login/loginCheck.ajax";
		var params = {};
			params.userId = $("#userId").val().trim();
			params.passwd = $("#passwd").val().trim();
			
		utils.ajax(url, params, function(response) {
			console.log('------------------------> response');			
			console.log(response);
			utils.loading(false);
			return;
			
			// 사용자 없음
			if (response.result.errorCode == "99") {
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);
			}
			// 비밀번호 오류 
			else if (response.result.errorCode == "98") {
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);				
			}
			// 잠김상태 
			else if (response.result.errorCode == "91") {
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);				
			} 
			// 비밀번호 만료
			else if (response.result.errorCode == "92") {
				utils.loading(false);
				
				// 비밀번호 변경 팝업
				DIALOG.confirm({
					msg : "비밀번호 변경일이 3개월을 초과하였습니다.</br> 변경 후 로그인이 가능합니다."
				}, function(){
					if (this.key == "ok") {
						$(this).popup({
							 title : "비밀번호 변경"
							,width : "780px"
							,height: "430px"
							,url : "/admin/main/main0107Pop.do"
							,params : {oldPwCnk: "Y"}
							,callBackFn : function(data){
								closePopup();
								utils.movePage("/main/main.do");
							}
						});
					}
				});
				
			}
			// 정상 로그인
			else if (response.result.errorCode == "00") {
				utils.loading(false);
				
				if ($.type(result.returnUrl) !== "undefined" && (result.returnUrl).indexOf("/common/sendUrl/") == 0) {
					utils.movePage(result.returnUrl);	
				} else {
					utils.movePage("/main/main.do");	
				}
				
			}  
			else {
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);
			}
		}, false);
	});
	
	
	// 아이디 신청 팝업
	$("#userApplyBtn").popup({
		 title : "아이디신청"
		,width : "720px"
		,height: "680px"
		,url : "/login/userApplyPop.do"
	});

	// Enter Key 이벤트
	utils.enter("#userId, #passwd", "#loginBtn");

}
