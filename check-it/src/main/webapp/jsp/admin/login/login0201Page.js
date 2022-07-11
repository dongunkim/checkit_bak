/**
 * 세션아웃
 */
initFunction = function(){
	DIALOG.alert({
		msg : "다른 기기에 로그인이 되어 자동으로 로그아웃되었습니다."
	}, function(){
			utils.movePage("/admin/login/login0101Page.do");
	});
	eventFunction();
}

eventFunction = function(){
	
}
