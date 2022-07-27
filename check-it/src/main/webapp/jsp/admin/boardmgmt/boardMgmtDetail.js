initFunction = function(){
	utils.inputData(result);
	
    eventFunction(result);
}

eventFunction = function(data){
	// 수정 버튼 클릭
	$("#editBtn").unbind().on("click", function(){
		var url = "/admin/boardmgmt/boardMgmtEdit.do";
		utils.movePage(url,data);
	});

	// 삭제 버튼 클릭
	$("#delBtn").unbind().on("click", function(){
		/*기본값 세팅*/
		let validator = [];
		let options = {};
		options.url = "/admin/boardmgmt/delBoardMgmt.ajax";
		options.params = {};
		options.params.boardId = data.boardId;
		options.params.boardType = data.boardType;
		options.msg = "삭제하시겠습니까?";
		$("#boardForm").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: "정상적으로 삭제되었습니다."
				}, function(){
					let url = "/admin/boardmgmt/boardMgmtList.do";
					var params = {};
					params.searchParam = data.searchParam;
					utils.movePage(url, params);
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: "삭제시 오류가 발생하였습니다."
				});
			}
		});
	});

	// 목록 버튼 클릭
	$("#backBtn").unbind().on("click", function(){
		var url = "/admin/boardmgmt/boardMgmtList.do";
		var params = {};
		params.searchParam = result.searchParam;
		utils.movePage(url, params);
	});
}