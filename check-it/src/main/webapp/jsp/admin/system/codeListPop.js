let params,
	url,
	strHtml;
	WChecked = true;
	RChecked = true;
	rootCodeId = "";
initFunction = function(){
	setGridFn(result);
	eventFunction();
}

eventFunction = function(data){
	// 닫기
	$("#closeBtn").unbind().on("click", function(){
		closePopup();
	});
	
	// 저장
	$("#saveBtn").unbind().bind("click", function(){
		
		let paramsAList = [];
		let params = {};
		let url = "/admin/system/system04UpdateAttCodeResource.do";
		
		$("#sysCodeArea > tr").each(function(){		
			let codeId = "";
			$(this).find("input").each(function(){
				let name = $(this).attr("name");
				if(name == "codeId"){
					codeId = $(this).val();
				}
				if($(this).attr("type") == "checkbox"){
					if($(this).prop("checked") == true){
						paramsAList.push(codeId);   //searchYn check 
					}
				}
			});
		});
		
		params.paramsAList = JSON.stringify(paramsAList);
		console.log("**** : " + rootCodeId);
		params.codeGrpId = rootCodeId;
		DIALOG.confirm({
			title: "알림",
			msg: "공통코드 세부속성을 저장 하시겠습니까?"
		}, function () {
			if (this.key == "ok") {				
				console.log(JSON.stringify(params));				
				utils.ajax(url, params, function(data){
					if(data.result.errorCode == "00"){
						DIALOG.alert({
							title: "공통코드 세부속성",
							msg: data.result.errorMessage
						}, function(){
							closePopup();
						});
					}else{
						DIALOG.alert({
							title: "공통코드 세부속성",
							msg: data.result.errorMessage
						});
					}
				});
			}
		});
	});
	
	
	$("#searchYn").unbind().on("click", function(){
		if(WChecked == true){
			$("input[id^=searchYn]").prop("checked", true);
			WChecked = false;
		} else {
			$("input[id^=searchYn]").prop("checked", false);
			WChecked = true;
		}
	});
}

setGridFn = function(data){
	rootCodeId = data.codeGrpId;
	let url = "/admin/system/system04GetUnderCodeList.do",
		params = {"codeGrpId" : data.codeGrpId};	
	utils.ajax(url, params, function(res){
		 gridFn(res.result.list);
	});
}



gridFn = function(data){

	let html = [];

	$.each(data, function(idx, obj){
		html.push("	<tr>");
		html.push("		<input type=\"hidden\" id=\"codeId_"+idx+"\" name=\"codeId\" value="+obj.codeId+">");
		html.push("		<td align=center style=\"text-align:center;\" width=\"30%\">");
		html.push(			obj.codeId);
		html.push("		</td>");
		
		html.push("		<td align=center style=\"text-align:center;\" width=\"40%\">");
		html.push(			obj.codeName); 
		html.push("		</td>");


		
		html.push("		<td width=\"30%\" >");
		html.push("			<span style=\"margin-top: 5px;\ text-align:center; \">");
		html.push("				<label class=\"check\" >");
		if(obj.searchYn == "Y"){
			html.push("					<input type=\"checkbox\" id=\"searchYn_"+idx+"\" name=\"searchYn\" value=\"Y\" checked>");
		} else {
			html.push("					<input type=\"checkbox\" id=\"searchYn_"+idx+"\" name=\"searchYn\" value=\"N\">");
		}
		html.push("					<span></span>");
		html.push("				</label>");
		html.push("			</span>");
		html.push("		</td>");
		html.push("	</tr>");
	});
	$("#sysCodeArea").html(html.join(""));
}
