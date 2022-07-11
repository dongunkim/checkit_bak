/**
 * 사용자 등록요청
 */
initFunction = function(){

	let url = "/common/commBuseoTreeList.do";
	let params = {};
		params.code = "1";
	utils.ajax(url, params, function(response){
		let html = [];
		html.push("<option value=\"\">-선택-</option>");
		$.each(response.result.list, function(idx, data){
			html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
		});
		$("#manageCid").empty().append(html.join(""));
	});

	eventFunction();
	selectPut();
}

eventFunction = function(){

	let useYn = "D";
	$("#userId").unbind().bind("keyup", function(e){
		let userId = $(this).val();
		
		if(!(e.keyCode >= 37 && e.keyCode <= 40)) {
			$(this).val(userId.replace(/[^a-z0-9]/gi, ""));
		}

		useYn = "D";

		if(userId.length == 0){
			$("#checkText").empty();
		}else if(userId.length > 5){
			var regx2 = /[a-zA-Z]/;		// 영문자 포함 체크
			var regx3 = /[0-9]/;
			var regx4 = /^[a-zA-Z]/;
			
			if(!regx2.test(userId) && regx3.test(userId)){
				useYn = "D";
				$("#checkText").css("color", "#ff0000;").text("[ID는 숫자만 사용할 수 없습니다.]");
			} else if(!regx4.test(userId)){
				useYn = "D";
				$("#checkText").css("color", "#ff0000;").text("[ID는 영문으로만 시작해야 합니다.]");
			}
			else {
				let url = "/admin/login/newIdCheck.do";
				let params = {};
					params.userId = userId;
				utils.ajax(url, params, function(response){
					if(response.result.useYn == "Y"){
						useYn = "Y";
						$("#checkText").css("color", "#0000ff;").text("[사용가능합니다]");
					}else{
						$("#checkText").css("color", "#ff0000;").text("[이미 사용중입니다]");
						useYn = "N";
					}
				}, false);
			}
		}else{
			$("#checkText").css("color", "#ff0000").text("[6자 이상 입력하세요]");
		}

	});

	$("#manageCid").unbind().bind("change", function(){
		$("#formationCd_2").hide();
		$("#formationCd_2").empty();
		let code = $(this).val();

		let url = "/common/commBuseoTreeList.do";
		let params = {};
			params.code = code;
		utils.ajax(url, params, function(response){
			let html = [];
			$.each(response.result.list, function(idx, data){
				html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
			});
			if(html.length > 0){
				$("#formationCd").show().empty().append(html.join(""));
			}else{
				$("#formationCd").hide();
				$("#formationCd").empty();
			}
		});
	});


	$("#formationCd").unbind().bind("change", function(){
		$("#formationCd_2").hide();
		$("#formationCd_2").empty();
		let code = $(this).val();

		if(code.substr(4,2) == "50"){
			let url = "/common/commBuseoTreeList.do";
			let params = {};
				params.code = code;
			utils.ajax(url, params, function(response){
				let html = [];
				$.each(response.result.list, function(idx, data){
					html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
				});
				if(html.length > 0){
					$("#formationCd_2").show().empty().append(html.join(""));
				}else{
					$("#formationCd_2").hide();
					$("#formationCd_2").empty();
				}
			});
		}
	});


	// 확인
	$("#addBtn").unbind().bind("click", function(){

		let validator = [
			 { key : "userId", label : "사용자ID" }
			,{ key : "userNm", label : "사용자이름" }
			,{ key : ["tel1", "tel2", "tel3"], name : "telNo", label : "전화번호", type : "tel" }
			,{ key : ["hp1", "hp2", "hp3"], name : "hpNo", label : "휴대폰", type : "hp" }
			,{ key : ["email1", "email2"], name : "email", label : "이메일", type : "email" }
			,{ key : "applyDesc", label : "상담내용" }
			,function(){

				if(useYn == "D"){
					DIALOG.alert("아이디를 정확히 입력하세요.");
					return false;
				}

				if(useYn == "N"){
					DIALOG.alert("현재 사용중인 아이디를 입력하셨습니다.");
					return false;
				}

				if($("#applyDesc").val() == ""){
					DIALOG.alert("신청사유를 간단히 입력해주세요.");
					return false;
				}

				return true;
			}
		];

		let options = {};
			options.url = "/admin/login/insertNewUser.do";
			// 등록 알림용 추가 (2019.03.04 DEV_gh.baek)
			options.params = {};
			options.params.manageCidText = $("#manageCid option:selected").text();
			options.params.formationCdText = $("#formationCd option:selected").text();

		$("#newUserDiv").formSubmit(options, validator, function(result){

			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				}, function () {
					closePopup();
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
		closePopup();
	});

}

selectPut = function(){
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

	$.each(code.hp, function(index, data){
		let selectOption = $("<option>", {
			  text : data.name
			 ,value : data.code
		});
		$("#selectData").append(selectOption);
	});
}
