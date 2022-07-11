initFunction = function(){
	// 파일리스트
	$("#bord0302Fileupload").fileUploadForm({
		type : "attachList"
			,attachId : result.attachId
	});

	utils.inputData(result);
	$("#bbsDesc").html(result.bbsDesc);
    eventFunction(result);
}

eventFunction = function(data){
	// 수정 버튼 클릭 이벤트
	$("#editBtn").unbind().on("click", function(){
		var url = "/admin/board/bord0304Edit.do";
		data.bbsDesc = encodeURIComponent(data.bbsDesc);
		utils.movePage(url,data);
	});

	$("#delBtn").unbind().on("click", function(){
		/*기본값 세팅*/
		let validator = [];
		let options = {};
		options.url = "/admin/board/deleteBord0305.do";
		options.params = {};
		options.params.bbsNo = data.bbsNo;
		options.params.regId = data.regId;
		options.msg = "삭제하시겠습니까?";
		$("#bord0302Form").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				let param = {};
				var url = "/admin/board/bord0301List.do";
				utils.movePage(url , param);
			}else{
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				});
			}
		});
	});

	$("#backBtn").unbind().on("click", function(){
		var url = "/admin/board/bord0301List.do";
		utils.movePage(url, JSON.parse(result.param));
	});
}