/**
 * 사용자 등록요청
 */
let oldPwCnk = result.param.oldPwCnk;
initFunction = function(){
	console.log(result);
	eventFunction(result);
	selectPut();
}

eventFunction = function(result){
	
	parent.$("#popupCloseBtn").unbind().bind("click", function(){
		if(result.param.oldPwCnk == "Y"){
			DIALOG.alert({
				title: "경고",
				msg: "패스워드를 변경하지 않으시면 로그인이 되지않습니다."
			}, function(){
				utils.movePage("/admin/login/logout.do");
			});
		}else{
			location.href=document.location;
			setPopupData({close:true});
		}
		
	});
	
	// 확인
	$("#addBtn").unbind().bind("click", function(){

		$password1 = $("#passwd"),
			$password2 = $("#passwdChk");
		password1Val = $password1.val(),
			password2Val = $password2.val();

		if(password1Val != "" || password2Val != ""){
			if ($("#passwd").val() != $("#passwdChk").val()) {
				DIALOG.alert({
					title: "알림",
					msg: "입력하신 비밀번호와 확인하신 비밀번호가 다릅니다."
				}, function () {
					$("#passwd").val("");
					$("#passwdChk").val("");
					$("#passwd").focus();
				});
				return false;
			}

			/*pw 확인*/
			if (!checkPassPattern(password1Val)) {
				return;
			}
			if (!checkPassPattern(password2Val)) {
				return;
			}
		}

		let validator = [
			 { key : "userNm", label : "사용자이름" }
			,{ key : ["tel1", "tel2", "tel3"], name : "telNo", label : "전화번호", type : "tel" }
			,{ key : ["hp1", "hp2", "hp3"], name : "hpNo", label : "휴대폰", type : "hp" }
			,{ key : ["email1", "email2"], name : "email", label : "이메일", type : "email" }
		];

		let options = {};
			options.url = "/admin/main/updateUser.do";

		$("#userInfoDiv").formSubmit(options, validator, function(result){

			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				}, function () {
					setPopupData({success:true, oldPwCnk:oldPwCnk});
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				});
			}
		});

	});

	// 취소
	$("#closeBtn").unbind().bind("click", function(){
		if(result.param.oldPwCnk == "Y"){
			DIALOG.alert({
				title: "경고",
				msg: "패스워드를 변경하지 않으시면 로그인이 되지않습니다."
			}, function(){
				setPopupData({close:true, oldPwCnk:result.param.oldPwCnk});
//				utils.movePage("/admin/login/logout.do");
			});
		}else{
			location.href=document.location;
			setPopupData({close:true});
		}
//		closePopup();
	});

}

selectPut = function(){


	let manageCid = "";
	let manageCnm = result.manageCnm;
	let formationNm = result.formationNm;

	if(manageCnm == null || manageCnm == "null" || manageCnm == "" || typeof manageCnm == "undefined") manageCnm = "" ;
	if(formationNm == null ||formationNm == "null" || formationNm == "" || typeof formationNm == "undefined") formationNm = "" ;
	if(utils.defaultString(result.formationCd).substr(3,2) == "15" && utils.defaultString(result.formationCd).substr(5,1) != "0"){
		manageCid = manageCnm + "  운영   " + formationNm;
	} else {
		manageCid = manageCnm + "  " + formationNm;
	}



	$("#manageCid").html(manageCid);

	$.each(code.tel, function(index, data){
		let selectOption = $("<option>", {
			  text : data.name
			 ,value : data.code
		});
		$("#tel1").append(selectOption);
	});

	$.each(code.hp, function(index, data){
		let selectOption = $("<option>", {
			  text : data.name
			 ,value : data.code
		});
		$("#hp1").append(selectOption);
	});

	$("#userNm").val(result.userNm);
	let telNo = utils.defaultString(result.telNo);
	let hpNo  = utils.defaultString(result.hpNo);
	let email = utils.defaultString(result.email);

	if(telNo.split("-").length == 3){
		let telSplit = telNo.split("-");
		$("#tel1").val(telSplit[0]);
		$("#tel2").val(telSplit[1]);
		$("#tel3").val(telSplit[2]);
	}

	if(hpNo.split("-").length == 3){
		let hpSplit = hpNo.split("-");
		$("#hp1").val(hpSplit[0]);
		$("#hp2").val(hpSplit[1]);
		$("#hp3").val(hpSplit[2]);
	}

	if(email.split("@").length == 2){
		let emailSplit = email.split("@");
		$("#email1").val(emailSplit[0]);
		$("#emailSelect").val(emailSplit[1]);
		if(utils.defaultString($("#emailSelect").val()) != ""){
			$("#email2").val(emailSplit[1]);
			$("#email2").hide();
		}else{
			$("#emailSelect").val("");
			$("#email2").val(emailSplit[1]);
		}
	}

}

/*pw 유효성 확인 */
checkPassPattern = function(pass) {
	var regx1 = /^[^ ]{10,20}$/; // 10자리 이상 20 이하 체크
	var regx2 = /[a-zA-Z]/; // 영문자 포함 체크
	var regx3 = /[0-9]/; // 숫자 포함 체크
	var regx4 = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(]/gi;
	var regx5 = /\"|\'/;

	if (!regx1.test(pass)) {
		DIALOG.alert({
			title: "경고",
			msg: "비밀번호는 10자리 이상, 문자, 숫자, 특수문자 중 2가지 혼용 입력해주세요." + "\n" + '특수문자 "' + " 및 ' 포함 불가"
		}, function(){
			$("#passwd").focus();
		});
		return false;
	}
	if (regx2.test(pass) && !regx3.test(pass) && !regx4.test(pass) && !regx5.test(pass)) {
		DIALOG.alert({
			title: "경고",
			msg: "비밀번호는 10자리 이상, 문자, 숫자, 특수문자 중 2가지 혼용 입력해주세요." + "\n" + '특수문자 "' + " 및 ' 포함 불가"
		}, function(){
			$("#passwd").focus();
		});
		return false;
	}
	if (!regx2.test(pass) && !regx3.test(pass) && regx4.test(pass) && !regx5.test(pass)) {
		DIALOG.alert({
			title: "경고",
			msg: "비밀번호는 10자리 이상, 문자, 숫자, 특수문자 중 2가지 혼용 입력해주세요." + "\n" + '특수문자 "' + " 및 ' 포함 불가"
		}, function(){
			$("#passwd").focus();
		});
		return false;
	}
	if (!regx2.test(pass) && regx3.test(pass) && !regx4.test(pass) && !regx5.test(pass)) {
		DIALOG.alert({
			title: "경고",
			msg: "비밀번호는 10자리 이상, 문자, 숫자, 특수문자 중 2가지 혼용 입력해주세요." + "\n" + '특수문자 "' + " 및 ' 포함 불가"
		}, function(){
			$("#passwd").focus();
		});
		return false;
	}

	return true;
}