initFunction = function(){
	// 파일리스트
	/*
	$("#boardFileupload").fileUploadForm({
		type : "attachList"
			,attachId : result.attachId
	});
	*/
	
	utils.inputData(result);
	$("#bbsDesc").html(result.bbsDesc);
    eventFunction(result);
}

eventFunction = function(data){
	// 수정 버튼 클릭 이벤트
	$("#editBtn").unbind().on("click", function(){
		var url = "/admin/board/boardEdit.do";
		data.bbsDesc = encodeURIComponent(data.bbsDesc);
		utils.movePage(url,data);
	});

	$("#delBtn").unbind().on("click", function(){
		/*기본값 세팅*/
		let validator = [];
		let options = {};
		options.url = "/admin/board/deleteBoard.do";
		options.params = {};
		options.params.boardId = data.boardId;
		options.params.boardType = data.boardType;
		options.msg = "삭제하시겠습니까?";
		$("#boardForm").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				let param = {};
				var url = "/admin/board/boardList.do";
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
//		history.go(-1);
		var url = "/admin/board/boardList.do";
		utils.movePage(url, JSON.parse(result.param));
	});
}