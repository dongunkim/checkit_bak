// DEV - GH.BAEK
var params = {};
var options = {};
options.params = {};
let bbsDescData;

initFunction = function(){
	// 파일리스트
	$("#bord0204Fileupload").fileUploadForm({
		type : "attachUpdate"
		,attachId : result.attachId
	});

	// 에디터 삽입 (500은 Size)
	$("#bbsDesc").edit(500, decodeURIComponent(result.bbsDesc));

	// 기타 내용 추가
	$("#bbsGb").val(result.bbsGb);
	$("#bbsTitle").val(result.bbsTitle);
	$("#bord0204Edit").reloadDataType();

	eventFunction(result);
}

eventFunction = function(data){
	// 저장 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		let validator = [
			{ key : "bbsTitle", label : "제목" }
			/*,{ key : "bbsDesc", label : "내용" }*/
		];
		options.url = "/admin/bord/updateBord0204.do";
		options.params.bbsNo = data.bbsNo;
		options.msg = "저장하시겠습니까?";
		$("#bord0204Edit").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				}, function(){
					let url = "/admin/bord/bord0202Detail.do";
					utils.movePage(url, data);
				});
			}else{
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				});
			}
		});

	});

	// 이전 페이지 클릭 이벤트
	$("#backBtn").unbind().on("click", function(){
		let url = "/admin/bord/bord0202Detail.do";
		utils.movePage(url, data);
	});

	// 폼적용 없음
	$("#bordFormEmpty").unbind().bind("click", function(){
		// 기존의 텍스트를 가져와서 다시 출력한다.
		bbsDescData = $("#bbsDesc").getEditContent();
		$("#bbsDesc").setEdit(decodeURIComponent(result.bbsDesc));
		$("#bordArea").hide();
	});

	// 폼적용 일반
	$("#bordFormNoBasic").unbind().bind("click", function(){
		bbsDescData = $("#bbsDesc").getEditContent();
		$("#bbsDesc").setEdit(bordForm(0));
		$("#bordArea").show();
	});
}
