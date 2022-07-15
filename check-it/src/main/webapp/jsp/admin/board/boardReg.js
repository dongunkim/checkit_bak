// DEV - GH.BAEK
var params = {};
var options = {};
options.params = {};

initFunction = function(){
	// SELECT BOX의 상위 코드 SETTING
	utils.getCommNm("931", "bbsGb");
	// 에디터 삽입 (500은 Size)
	//$("#bbsDesc").edit(500);
    eventFunction(result);
}

eventFunction = function(data){
	// 등록 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		// Validation
		let validator = [
			{ key : "bbsTitle", label : "제목" }
		];

		/*
		if($("#bbsDesc").getEditContent() == ""){
			DIALOG.alert("내용을 입력해주세요.")
			return;
		}
		*/

		options.url = "/admin/board/insertBoard.ajax";
		$("#boardReg").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: "정상 등록되었습니다."
				}, function(){
					utils.movePage("/admin/board/boardList.do");
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
		var url = "/admin/board/boardList.do";
		utils.movePage(url, data);
	});

	// 폼적용 없음
	$("#bordFormEmpty").unbind().bind("click", function(){
		$("#bbsDesc").setEdit("");
	});

	// 폼적용 일반
	$("#bordFormNoBasic").unbind().bind("click", function(){
		$("#bbsDesc").setEdit(bordForm(0));
	});
}
