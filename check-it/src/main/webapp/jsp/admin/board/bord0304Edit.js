// DEV - GH.BAEK
var params = {};
var options = {};
//options.params = {};
let bbsDescData;
let csNoArr = [];

initFunction = function(){
	// 파일리스트
	$("#bord0304Fileupload").fileUploadForm({
		type : "attachUpdate"
		,attachId : result.editDesc.attachId
	});

	// 에디터 삽입 (500은 Size)
	$("#bbsDesc").edit(500, decodeURIComponent(result.editDesc.bbsDesc));
	console.log(result);
	// 기타 내용 추가
	$("#bbsGb").val(result.editDesc.bbsGb);
	$("#bbsTitle").val(result.editDesc.bbsTitle);
	$("#bord0304Edit").reloadDataType();
	if(result.editDesc.csGongjiYn == "Y"){
		$("#csChk").attr("checked",true);
		$("#csSelectArea").show();
		$("#gongjiDdArea").show();
		$("#csNmRecvs").val("");
		$("#gongjiDdArea").val("");
		$("#gongjiDd").val(result.editDesc.gongjiDd.substr(0,4)+"-"+result.editDesc.gongjiDd.substr(4,2)+"-"+result.editDesc.gongjiDd.substr(6,2));
	}
	
	/*
	if(result.editDescUser.list.length>0){
		for(var i=0;i<result.editDescUser.list.length;i++){
			var csNm = result.editDescUser.list[i].csName;
			var csNo = result.editDescUser.list[i].csNo;
			csNoArr.push({"csNo":csNo,"csName":csNm});
			let addHtml = "<div class=\"select_text_box\" contenteditable=\"false\"><div class=\"select_text_box_x\" data-del=\"" + csNo + "\">X</div><span style=\"padding-right:15px;\" data-csname=\"" + csNm + "\">" + csNm + "</span></div>";
			$("#csNmRecvs").prepend(addHtml);
		}
	}
	*/
	
	
	eventFunction(result);
}

eventFunction = function(data){
	// 저장 버튼 이벤트
	$("#regBtn").unbind().on("click", function(){
		options.params = {};
		let validator = [
			{ key : "bbsTitle", label : "제목" }
			/*,{ key : "bbsDesc", label : "내용" }*/
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
	
					
					options.params.csGongjiYn = "Y";
					options.params.csNoArr = [];
//					let tmpCsArr=[];
//					let csList = $("#csNmRecvs").val();
//					let csListArr = csList.split(";");
//					for(var j=0;j<csListArr.length;j++){
//						for(var k=0; k<csNoArr.length;k++){
//							if(csListArr[j]==csNoArr[k].csNm){
//								tmpCsArr.push(csNoArr[k].csNo);
//							}
//						}
//					}
					options.params.csNoArr = csNoArr;
	
					return true;
	
				}
			}
		];
		if($("#bbsDesc").getEditContent() == ""){
			DIALOG.alert("내용을 입력해주세요.")
			return;
		}
		options.url = "/admin/board/updateBord0304.do";
		options.params.bbsNo = data.editDesc.bbsNo;
		options.msg = "저장하시겠습니까?";
		$("#bord0304Edit").formSubmit(options, validator, function(result){
			if(result.errorCode == "00"){
				DIALOG.alert({
					title: "알림",
					msg: result.errorMessage
				}, function(){
					let url = "/admin/board/bord0302Detail.do";
					utils.movePage(url, data.editDesc);
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
		let url = "/admin/board/bord0302Detail.do";
		utils.movePage(url, data.editDesc);
	});

	// 폼적용 없음
	$("#bordFormEmpty").unbind().bind("click", function(){
		// 기존의 텍스트를 가져와서 다시 출력한다.
		bbsDescData = $("#bbsDesc").getEditContent();
		$("#bbsDesc").setEdit(decodeURIComponent(result.editDesc.bbsDesc));
		$("#bordArea").hide();
	});

	// 폼적용 일반
	$("#bordFormNoBasic").unbind().bind("click", function(){
		bbsDescData = $("#bbsDesc").getEditContent();
		$("#bbsDesc").setEdit(bordForm(0));
		$("#bordArea").show();
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
		,height: "700px"
		,params: params
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

