var options = {};
options.params = {};

initFunction = function(){
	var editor = $.editorWrapper('#boardDesc');
	editor.value = result.boardDesc;
	
	// 파일리스트
	$("#boardFileupload").fileUploadForm({
		type : "attachUpdate"
		,attachId : result.attachId
	});

	utils.inputData(result);

	eventFunction(result);
}

eventFunction = function(data){
	// 저장 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		let validator = [
			{ key : "boardTitle", label : "제목" },
			{ key : "boardDesc", label : "내용" }
		];
		options.url = "/admin/boardmgmt/updBoardMgmt.ajax";
		options.params.boardType = "01";
		options.params.boardId = data.boardId;
		options.msg = "저장하시겠습니까?";
		$("#boardEdit").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: "정상적으로 수정되었습니다."
				}, function(){
					let url = "/admin/boardmgmt/boardMgmtDetail.do";
					utils.movePage(url, data);
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: "처리중 오류가 발생하였습니다."
				});
			}
		});

	});

	// 이전 페이지 클릭 이벤트
	$("#backBtn").unbind().on("click", function(){
		let url = "/admin/boardmgmt/boardMgmtDetail.do";
		utils.movePage(url, data);
	});
}
