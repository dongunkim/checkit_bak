initFunction = function(){
	$("#passwd").focus();
	eventFunction(result);

}

eventFunction = function(result){

	$("#passwd").unbind().on("keyup", function(e){
		if(e.keyCode == 13){
			$("#checkBtn").click();
		}
	});
	
	parent.$("#popupCloseBtn").unbind().bind("click", function(){
		if(result.oldPwCnk == "Y"){
			DIALOG.alert({
				title: "경고",
				msg: "패스워드를 변경하지 않으시면 로그인이 되지않습니다."
			}, function(){
				utils.movePage("/admin/login/logout.do");
			});
		}else{
			closePopup();
		}
	});
	
	// 확인버튼
	$("#checkBtn").unbind().bind("click", function(){
		if($("#passwd").val() == ""){
			DIALOG.alert({
				title: "경고",
				msg: "비밀번호를 입력하세요."
			}, function(){
				$("#passwd").focus();
			});
			return false;
		}else{
			let url = "/admin/main/pwdCheck.do";
			let params = {};
				params.userId = $("#userId").text();
				params.passwd = $("#passwd").val();
			utils.ajax(url, params, function(data){
				if(data.result.errorCode == "00"){
					$(this).popup({
						 title : "개인정보변경"
						,width : "720px"
						,height : "530px"
						,url : "/admin/main/main0108Pop.do"
						,params : {oldPwCnk: result.oldPwCnk}
						,callBackFn : function(data){
							if(data.success){
								if(data.oldPwCnk == "Y"){
									setPopupData(data);
									closePopup();
								}else{
									setPopupData(data);
									closePopup();
								}
							}else if(data.close){
								if(data.oldPwCnk == "Y"){
									utils.movePage("/admin/login/logout.do");
								}else{
									setPopupData(data);
									closePopup();
								}
								
							}
						}
					});
				}else{
					DIALOG.alert({
						title: "경고",
						msg: data.result.errorMessage
					}, function(){
						$("#passwd").val("").focus();
					});
				}
			}, false);

		}
	});

	// 취소
	$("#closeBtn").unbind().bind("click", function(){
		if(result.oldPwCnk == "Y"){
			DIALOG.alert({
				title: "경고",
				msg: "패스워드를 변경하지 않으시면 로그인이 되지않습니다."
			}, function(){
				utils.movePage("/admin/login/logout.do");
			});
		}else{
			closePopup();
		}
		
		
	});

}