/**
 * ID 찾기 팝업
 */
initFunction = function(){
	eventFunction();
	$("#hpNo").onlyNumber();
}

eventFunction = function(){

	// 확인
	$("#searchBtn").unbind().bind("click", function(){

		let userNm = $("#userNm").val();
		let hpNo   = $("#hpNo").val();
		let email  = $("#email").val();

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

		let url = "/admin/login/idSearch.do";
		let params = {};
			params.userNm = userNm;
			params.hpNo   = hpNo;
			params.email  = email;

		utils.ajax(url, params, function(data){
			console.log(data);
			if(data.result.userYn == "Y"){
				DIALOG.alert({
					title: "알림",
					msg: "가입중인 ID는 \n" + data.result.userId + "\n입니다."
				}, function () {
					closePopup();
				});
			}else{
				DIALOG.alert("등록된 정보가 없습니다.\n이름,휴대폰,이메일을 정확히 입력하세요.");
				return false;
			}
		});

	});

	// 취소
	$("#closeBtn").unbind().bind("click", function(){
		closePopup();
	});

}
