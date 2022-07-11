/**
 * SMS 인증 팝업
 */
initFunction = function(){
	setTimer();
	eventFunction();
}

let interval;
eventFunction = function(){

	parent.$("#popupCloseBtn").hide();

	// 로그인 연장하기
	$("#loginRenewBtn").unbind().bind("click", function(){
		parent.$("#popupCloseBtn").show();
		setPopupData({success:"00"});
		closePopup();
	});

	// 로그아웃
	$("#logoutBtn").unbind().bind("click", function(){
		setPopupData({success:"01"});
//		closePopup();
	});

}

setTimer = function(){

	if($.type(interval) !== "undefined"){
		clearInterval(interval);
	}
	certTimeOver = false;
	$("#timer").text("60");

	let timer = 60;
	let seconds;

	interval = setInterval(function(){
		seconds = timer < 10 ? "0" + timer : timer;
		$("#timer").text(seconds);

		if (--timer < 0) {
            timer = 0;
            clearInterval(interval);
            setPopupData({success:"01"});
		}

    }, 1000);

}