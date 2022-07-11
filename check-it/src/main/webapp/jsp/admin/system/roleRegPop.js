initFunction = function(){

	utils.inputData(result);

	let usedYn = utils.defaultString(result.usedYn);

	if(usedYn != ""){
		$("input[name=usedYn][value=\""+usedYn+"\"]").prop("checked", true);
	} else {
		$("input[name=usedYn][value=\"Y\"]").prop("checked", true);
	}
	eventFunction();
}
eventFunction = function(){

	$("#closeBtn").unbind().on("click",function(){
		closePopup();
	});

	$("#saveBtn").unbind().on("click", function(){
		let flag = true,
			url = "/admin/system/roleInsert.do",
			params = {};
			params.rid = result.rid;
			params.rname = $("#rname").val();
			params.description = $("#description").val();
			params.usedYn = $("input[name=usedYn]:checked").val();
			params.sysType = result.sysType;
			params.flag = result.flag;

		$("input").each(function(){
			if($(this).attr("required") == "required"){
				let value = $(this).val();
				let title = $(this).attr("title");
				if(value == ""){
					DIALOG.alert(title+"을(를) 입력해주세요.");
					flag = false;
					return false;
				}
			}
		});
		if(flag){
			DIALOG.confirm({
				title : "알림",
				msg : "저장 하시겠습니까?"
			}, function(){
				if(this.key == "ok"){
					utils.ajax(url, params, function(res){
						if(res.result.errorCode == "00"){
							DIALOG.alert({
								title: "알림",
								msg: res.result.errorMessage
							}, function () {
								setPopupData(res);
								closePopup();
							});
						}else{
							DIALOG.alert({
								title: "알림",
								msg: res.result.errorMessage
							});
						}
					});
				}
			});
		}
	});
}