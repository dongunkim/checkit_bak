var options = {};
options.params = {};

initFunction = function(){

	utils.inputData(result);
	
	var editor = Jodit.make('#boardDesc', {
		"uploader": {
			"insertImageAsBase64URI": true
		}
	});
	editor.value = result.boardDesc;
	
	// 파일리스트
	$("#boardFileupload").fileUploadForm({
		type : "attachUpdate",
		attachId : result.attachId
	});

	eventFunction(result);
}

eventFunction = function(data){
	// 저장 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		var validator = [
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
					movePage("/admin/boardmgmt/boardMgmtDetail.do");
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: "처리중 오류가 발생하였습니다."
				});
			}
		});
	});

	// 이전 버튼 클릭
	$("#backBtn").unbind().on("click", function(){
		movePage("/admin/boardmgmt/boardMgmtDetail.do");
	});
}

// 상세 화면으로 이동
movePage = function(url){
	var param = {};
	param.boardType = result.boardType;
	param.boardId = result.boardId;
	param.searchParam = result.searchParam;
	utils.movePage(url, param);
}
