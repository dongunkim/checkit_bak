initFunction = function(){

	utils.inputData(result);

	$("input[name=useYn][value="+result.useYn+"]").prop("checked", "checked");

	eventFunction();
}

eventFunction = function(){
	$("#closeBtn").unbind().on("click", function(){
		closePopup();
	});

	$("#saveBtn").unbind().on("click", function(){
		let params = {},
		url = "";

		if(result.flag == "save"){
			url = "/admin/system/menuInsert.do";
		} else {
			url = "/admin/system/menuUpdate.do";
		}

		if($("#menuName").val() == ""){DIALOG.alert("메뉴명을 입력해 주세요."); return false;}

		$("#testDiv").find("input").each(function(){
			$this = $(this);
			let value = $this.val(),
			name = $this.attr("name"),
			checked = $this.prop("checked"),
			type = $this.attr("type");

			if((type == "radio" && checked == true) ||
				type != "radio"){
				params[name] = value;
			}
		});

		utils.ajax(url, params, function(data){
			if(data.result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: data.result.errorMessage
				}, function () {
					setPopupData(data);
					closePopup();
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: data.result.errorMessage
				});
			}
		});

	});

}
