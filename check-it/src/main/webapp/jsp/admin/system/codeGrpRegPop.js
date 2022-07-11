let params,
url,
cmd;
initFunction = function(){

	cmd = result.cmd;

	utils.inputData(result);

	let useYn = utils.defaultString(result.useYn);

	if(useYn != ""){
		$("input[name=useYn][value=\""+useYn+"\"]").prop("checked", true);
	} else {
		$("input[name=useYn][value=\"Y\"]").prop("checked", true);
	}

	if(cmd == "U"){
		$("#codeId").prop("readonly", true);
	}

	eventFunction();
}
eventFunction = function(){

	$("#closeBtn").unbind().on("click",function(){
		closePopup();
	});

	$("#saveBtn").unbind().on("click", function(){
		params = {};
		params = $("#codeGrpForm").serializeObject();
		params.cmd = cmd;
		url = "/admin/system/codeGrpProcess.do";

		let valiChk = true;

		$("input[required=required]").each(function(){
			$this = $(this);
			let value = $this.val();
			if(value == ""){
				DIALOG.alert($this.parent().prev().text()+"을(를) 입력해주세요.");
				valiChk = false;
				return false;
			}
		})
		if(valiChk){
			utils.ajax(url, params, function(rtn){
				if(rtn.result.errorCode == "00"){
					DIALOG.alert({
						title: "알림",
						msg: rtn.result.errorMessage
					}, function () {
						parent.utils.reload();
						closePopup();
					});
				}else{
					DIALOG.alert({
						title: "알림",
						msg: rtn.result.errorMessage
					});
				}
			});
		}
	});
}