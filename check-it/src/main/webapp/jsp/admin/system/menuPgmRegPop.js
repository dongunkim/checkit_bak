initFunction = function(){
	utils.inputData(result);
	
	$("#readWriteWR").prop("checked", true);
	
	eventFunction();
}

eventFunction = function(){
	$("#closeBtn").unbind().on("click", function(){
		closePopup();
	});

	$("#saveBtn").unbind().on("click", function(){
		let params = {},
		url = "";
		params.pid = utils.defaultString(result.pid);
		
		
		if(result.flag == "pidSave"){
			url = "/admin/system/menuPgmInsert.do";
		} else {
			url = "/admin/system/menuPgmUpdate.do";
		}

		if($("#prgName").val() == ""){DIALOG.alert("프로그램명을 입력해 주세요."); return false;}
		if($("#servletPath").val() == ""){DIALOG.alert("서블릿패스를 입력해 주세요."); return false;}
		
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
