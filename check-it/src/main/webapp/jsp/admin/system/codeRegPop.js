let params,
checkDup = false;
let addCodeArray
initFunction = function(){
	addCodeArray = JSON.parse(result.addCodeArray)
	$("#upCodeId").html(result.upCodeId);
	eventFunction();
}

eventFunction = function(data){

	$("#codeId").onlyNumber();
	$("#codeId").on("keydown", function(event){
		checkDup = false;
		if(event.keyCode == 13){
			$("#checkDup").click();
		}
	});


	$("#checkDup").unbind().on("click", function(){
		if($("#codeId").val() == ""){
			DIALOG.alert("코드 ID를 입력해 주세요.");
		} else {
			let url = "/admin/system/checkDuplicate.do";
			params = {};
			params.codeId = $("#codeGrpId").text() + $("#codeId").val();
			let firstCheck = true;
			if(addCodeArray.length > 0){
				$.each(addCodeArray, function(idx, obj){
					if(obj.codeId == params.codeId){
						DIALOG.alert({
							title : "알림",
							msg : "중복된 코드입니다."
						}, function(){
							$("#codeId").focus();
						});
						checkDup = false;
						firstCheck = false;
						return false;
					}
				})
			}
			if(firstCheck){
				utils.ajax(url, params, function(res){
					if(res.result.existCnt > 0){
						DIALOG.alert({
							title : "알림",
							msg : "중복된 코드입니다."
						}, function(){
							$("#codeId").focus();
							checkDup = false;
						});
					} else {
						DIALOG.alert("사용 가능한 코드입니다.");
						checkDup = true;
					}
				});
			}
		}
		return false;
	});

	$("#closeBtn").unbind().on("click", function(){
		closePopup();
		return false;
	});

	$("#saveBtn").unbind().on("click", function(){
		if(checkDup == false){
			DIALOG.alert("중복확인을 해주세요.");
		} else {
			if($("#cname").val() == ""){
				DIALOG.alert("코드명을 입력해 해주세요.");
			} else {
				params = {};
				params = $("#system0404Form").serializeObject();
				params.depth = result.depth;
				params.codeId = ($("#upCodeId").text() + params.codeId).trim();
				params.upCodeId = result.upCodeId;
				params.codeGrpId = result.codeGrpId;
				setPopupData(params);
				closePopup();
			}
		}
		return false;
	});

}
