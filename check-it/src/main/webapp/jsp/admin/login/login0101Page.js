let failCnt = 0;
initFunction = function(){

    eventFunction();

}

eventFunction = function(){
	
	history.pushState(null, null, location.href);
	window.onpopstate = function(event){
		history.go(1);
	};

	// 로그인
	$("#loginBtn").bind("click", function(){
		
		let answer = "";
		if(failCnt >= 5){
			if($("#answer").val().length == 0){
				showCapChar();
				DIALOG.alert("자동 로그인 방지번호를 입력하여 주십시오.");
				return;
			}else{
				answer = $("#answer").val();
			}
			
		}
		
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
			params.sndAnswer = answer;
		utils.ajax(url, params, function(response){
			if(response.result.errorCode == "98"){
				failCnt = response.result.failCnt;
				showCapChar();
				refreshBtn();
				utils.loading(false);
				DIALOG.alert(response.result.errorMessage);
			}else if(response.result.errorCode == "99"){
				failCnt = response.result.failCnt;
				refreshBtn();
				if($("#captchaDiv").css("display") == "none" && failCnt >= 5){
					DIALOG.alert("로그인 5회이상 실패하셨습니다.<br/>재 로그인시 아이디/비밀번호와 <br/>자동 로그인 방지번호를 함께 <br/>입력하여 주십시오.");
				}else{
					DIALOG.alert(response.result.errorMessage);
				}
				showCapChar();
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
	
	$("#refreshBtn").bind("click", function(){
		refreshBtn();
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

showCapChar = function(){
	if(failCnt >= 5){
		 $("#captchaDiv").show();
	}
}

//영문음성은 나오나 한국어음성은 개발중
audio = function(){
	var lang = "ko"
	if(locale.content != "ko"){
		lang = "en"
	}
	
	var rand = Math.random();
	var url = "/common/captchaAudio.do";
	$.ajax({
		url: url,
		type : "POST",
		dataType : "text",
		data: "rand=" +rand+'&lang='+lang,
		async: false,
		success: function(resp){
			var uAgent = navigator.userAgent;
			var soundUrl = '/common/captchaAudio.do?rand=' +rand +'lang='+lang;
			
			if(uAgent.indexOf('Trident') > -1 || uAgent.indexOf('MSIE') > -1){
				winPlayer(soundUrl+'&agent=msie');
			}else if(!!document.createElement('audio').canPlayType){
				try{
					new Audio(soundUrl).play();
				}catch(e){
					winPlayer(soundUrl);
				}
			}else{
				window.open(soundUrl, '', 'width=1, heigth=1');
			}
		}
	});
}

refreshBtn = function(type){
		var rand = Math.random();
		var url = "/common/captchaImg.do?rand="+rand;
		$("#captchaImg").attr("src",url);
}

winPlayer = function(objUrl){
		$("#captchaAudio").html(' <bgsound src="' + objUrl + '">');
}
