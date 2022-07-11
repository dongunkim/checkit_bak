let params;
initFunction = function(){

	console.log("result", result);
	let codeName = result.codeName.split("]");
	result.codeName = codeName[1];
	utils.inputData(result);
	eventFunction();
}

eventFunction = function(data){

	$("#closeBtn").unbind().on("click", function(){
		closePopup();
	});

	$("#saveBtn").unbind().on("click", function(){
		params = {};
		params = $("#system0405Form").serializeObject();
		console.log(params);
		params.depth = result.depth;
		params.upCodeId = result.upCodeId;
		params.codeGrpId = result.codeGrpId;
		params.codeId = result.codeId;

		setPopupData(params);
		closePopup();
	});
}
