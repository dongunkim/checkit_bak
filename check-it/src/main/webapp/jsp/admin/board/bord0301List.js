initFunction = function(){
	// 공지구분 SETTING
	utils.getCommNm("271", "searchBbsGb");

	let option = {};
	option.colGroup = [
		 { key : "bbsNo", label : "No", width : "20", align : "center" }
		,{ key : "bbsGbNm", label : "구분", width : "5", align : "center" }
		,{ key : "csGongjiYn", label : "캘린더공지", width : "10", align : "center"}
		,{ key : "bbsTitle", label : "제목", width : "40", align : "left" }
		,{ key : "attachId", label : "파일", width : "5", align : "center"
			, html : function(data){
				let img = "";
				if(data.attachId != null){
					img+= "<img src='/resources/images/asis/ico_attach.gif' align='absmiddle' /> ";
				}
				return img;
			}
		}
		,{ key : "regNm", label : "작성자", width : "8", align : "center" }
		,{ key : "regDt", label : "등록일시", width : "15", align : "center" , type : "datetime"}
		,{ key : "viewCnt", label : "조회수", width : "7", align : "center" }
	];

	option.allClick = function(data){
		let url = "/admin/board/bord0302Detail.do";
		data.param = result.param;
		utils.movePage(url, data);
	}

	$("#bord0301ListTable").list(option);
    eventFunction(result);

}

eventFunction = function(data){

	// 내부게시판 탭
	$("#tab1").unbind().bind("click", function(){
		utils.movePage("/admin/board/bord0101List.do");
	});

	// 내부공지 탭
	$("#tab2").unbind().bind("click", function(){
		utils.movePage("/admin/board/bord0201List.do");
	});

	// FAQ 탭
	$("#tab4").unbind().bind("click", function(){
		utils.movePage("/admin/board/bord0401List.do");
	});

	// 파일관리 탭
	$("#tab5").unbind().bind("click", function(){
		utils.movePage("/admin/board/bord0501List.do");
	});
	
	// 개인정보
	$("#tab6").unbind().bind("click", function(){
		utils.movePage("/admin/board/bord0801List.do");
	});
	
	// 등록버튼
	$("#regBtn").unbind().bind("click",function(){
		let params = {};
			params = result.param;
			params.search = "Y";
		utils.movePage("/admin/board/bord0303Reg.do", params);
	});
}