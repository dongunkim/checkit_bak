// DEV - GH.BAEK
var params = {};
var options = {};
options.params = {};

initFunction = function(){

	var editor = $.editorWrapper('#boardDesc');

	eventFunction(result);
}

eventFunction = function(data){
	// 등록 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		// Validation
		let validator = [
			{ key : "boardTitle", label : "제목" },
			{ key : "boardDesc", label : "내용" }
		];

		options.url = "/admin/boardmgmt/insBoardMgmt.ajax";
		$("#boardReg").formSubmit(options, validator, function(result){
			console.log(result);
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: "정상 등록되었습니다."
				}, function(){
					utils.movePage("/admin/boardmgmt/boardMgmtList.do");
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: "등록시 오류가 발생하였습니다."
				});
			}
		});

	});

	// 이전 페이지 클릭 이벤트
	$("#backBtn").unbind().on("click", function(){
//		history.go(-1);
		var url = "/admin/boardmgmt/boardMgmtList.do";
		utils.movePage(url, data);
	});

}