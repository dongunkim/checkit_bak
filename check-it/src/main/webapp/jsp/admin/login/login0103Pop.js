/**
 * 비밀번호 찾기 팝업
 */
initFunction = function(){
	eventFunction();
	$("#hpNo").onlyNumber();
}

eventFunction = function(){

	// 확인
	$("#searchBtn").unbind().bind("click", function(){

		let userId = $("#userId").val();
		let userNm = $("#userNm").val();
		let hpNo   = $("#hpNo").val();
		let email  = $("#email").val();

		if(userId == ""){
			DIALOG.alert({
				title: "경고",
				msg: "아이디를 입력해 주세요."
			}, function () {
				$("#userId").focus();
			});
			return false;
		}

		if(userNm == ""){
			DIALOG.alert({
				title: "경고",
				msg: "이름을 입력해 주세요."
			}, function () {
				$("#userNm").focus();
			});
			return false;
		}

		if(hpNo == ""){
			DIALOG.alert({
				title: "경고",
				msg: "휴대폰번호를 입력해 주세요."
			}, function () {
				$("#hpNo").focus();
			});
			return false;
		}

		if(email == ""){
			DIALOG.alert({
				title: "경고",
				msg: "이메일을 입력해 주세요."
			}, function () {
				$("#email").focus();
			});
			return false;
		}

		let url = "/admin/login/pwdSearch.do";
		let params = {};
			params.userId = userId;
			params.userNm = userNm;
			params.hpNo   = hpNo;
			params.email  = email;
			params.confirmType = $("input[name='confirmType']:checked").val();

		utils.ajax(url, params, function(data){
			if(data.result.userYn == "Y"){
				let sendType = "";
				if(params.confirmType == "sms"){
					sendType = "휴대폰으로"
				}else{
					sendType = "이메일로"
				}

				DIALOG.alert({
					title: "알림",
					msg: sendType + "\n임시비밀번호를 발송하였습니다."
				}, function () {
//					closePopup();
				});
			}else{
				DIALOG.alert("등록된 정보가 없습니다.\n아이디,이름,휴대폰,이메일을 정확히 입력하세요.");
				return false;
			}
		});
	});

	// 취소
	$("#closeBtn").unbind().bind("click", function(){
		closePopup();
	});

}
