// DEV - GH.BAEK
let params = {};
let options = {};
options.params = {};
let csNoArr = [];
initFunction = function(){
	console.log(result);
	// SELECT BOX의 상위 코드 SETTING
	utils.getCommNm("271", "bbsGb");
	// 에디터 삽입 (500은 Size)
	$("#bbsDesc").edit(500);
    eventFunction();
}

eventFunction = function(){
	// 등록 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		if($("#bbsGb").val() == ""){
			DIALOG.alert("구분을 선택해주세요.");
			return false;
		}

		let validator = [
			{ key : "bbsTitle", label : "제목" }
			,function(){

				if($("#csChk").is(":checked")){

					if(csNoArr.length == 0){
						DIALOG.alert({
							title: "알림",
							msg: "고객공지를 선택하셨습니다.\n공지할 고객을 선택해 주세요."
						});
						return false;
					}

					if($("#gongjiDd").val() == ""){
						DIALOG.alert({
							title: "알림",
							msg: "고객공지를 선택하셨습니다.\n공지일을 입력해 주세요."
						});
						return false;
					}

					options.params = {};
					options.params.csGongjiYn = "Y";
					options.params.csNoArr = [];
					options.params.csNoArr = csNoArr;

					return true;

				}

			}
		];

		if($("#bbsDesc").getEditContent() == ""){
			DIALOG.alert("내용을 입력해주세요.")
			return;
		}

		options.url = "/admin/board/insertBord0303.do";

		$("#bord0303Reg").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				}, function(){
					utils.movePage("/admin/board/bord0301List.do", result);
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
//		history.go(-1);
		var url = "/admin/board/bord0301List.do";
		utils.movePage(url, result);
	});

	// 폼적용 없음
	$("#bordFormEmpty").unbind().bind("click", function(){
		$("#bbsDesc").setEdit("");
	});

	// 폼적용 일반
	$("#bordFormNoBasic").unbind().bind("click", function(){
		$("#bbsDesc").setEdit(bordForm(0));
	});

	// 고객공지여부
	$("#csChk").unbind().bind("click", function(){
		if(this.checked){
			$("#csSelectArea").show();
			$("#gongjiDdArea").show();
			$("#csNmRecvs").val("");
			$("#gongjiDdArea").val("");
		}else{
			$("#csSelectArea").hide();
			$("#gongjiDdArea").hide();
		}
	});
	
	$(".select_text_box_x").unbind().bind("click", function(){
		let delCsNo = $(this).data("del");
		$.each(csNoArr, function(idx, delData){
			if(delData.csNo == delCsNo){
				csNoArr.splice(idx, 1);
				return false;
			}
		});
		$(this).parent().remove();
	});
	
	// 대상자 설정리스트
	$("#common0105Btn").unbind().bind("click", function(){
		let params = {};
		params.list = csNoArr;
		$(this).popup({
		 title : "대상자 설정"
		,width : "1160px"
		,params: params
		,height: "700px"
		,url : "/common/comm0105Pop.do"
		,tab: "N|N|N|N|N|Y|N|N"  // 탭제어
		,callBackFn : function(data){
			csNoArr=[];
			$("#csNmRecvs").find("div").empty();
				$.each(data.csData, function(idx, callBackData){
					csNoArr.push({csNo:callBackData.csNo,csName:callBackData.csName});
					let addHtml = "<div class=\"select_text_box\" contenteditable=\"false\"><div class=\"select_text_box_x\" data-del=\"" + callBackData.csNo + "\">X</div><span style=\"padding-right:15px;\" data-csname=\"" + callBackData.csName + "\">" + callBackData.csName + "</span></div>";
					$("#csNmRecvs").prepend(addHtml);
				});
			}
		});
	});
}

