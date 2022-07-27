initFunction = function(){
	$.jqGridWrapper('#jqGrid',{
		url: "/admin/boardmgmt/selBoardMgmtList.ajax",
		colNames: [
				"Id",
				"작성자",
				"제목",
				"등록일시",
				"조회수",
				"내용"
			],
		colModel: [
				{ name:'boardId'    ,index:'BOARD_ID'    ,width: 5, align:"left" },
				{ name:'regUserName',index:'REG_USER_NAME',width: 10, align:"left", sortable:false },
				{ name:'boardTitle' ,index:'BOARD_TITLE' ,width: 50, align:"left", sortable:false },
				{ name:'regDt'      ,index:'REG_DT'      ,width: 10, align:"center"},
				{ name:'viewCount'  ,index:'VIEW_COUNT'  ,width: 10, align:"center", sortable:false},
				{ name:'boardDesc'  ,index:'BOARD_DESC'  ,hidden:true}
			],
		sortorder: "desc",
		sortname: 'BOARD_ID',
		onSelectRow: function(rowid) {
			var param = {};
			param.boardId = $("#jqGrid").getRowData(rowid).boardId;
			param.boardType = $("#boardType").val();
			movePage("/admin/boardmgmt/boardMgmtDetail.do",param);
		}
	});

    eventFunction(result);

	// 검색 조건 유지를 위한 파라미터 설정.
	initSearchParam();

	// 데이터 검색
	search();
}

eventFunction = function(data){
	// 검색 버튼
	$("#searchBtn").unbind().bind("click",function(){
		search();
	});

	// 등록 버튼
	$("#regBtn").unbind().bind("click",function(){
		var param = {};
		movePage("/admin/boardmgmt/boardMgmtReg.do",param);
	});

	// Excel 다운로드 버튼
	$("#excelDownBtn").unbind().bind("click", function(){
		downloadExcel("/admin/boardmgmt/selBoardMgmtExcel.ajax",$("#frm").serialize());
	});

	// Enter Key 이벤트
	utils.enter("#searchText","#searchBtn");
}

// 상세 페이지 이동후 돌아온 경우 기존 검색 조건 설정.
initSearchParam = function(){
	if(result.searchParam != null && result.searchParam != undefined){
		// 검색 조건 설정
		utils.inputData(result.searchParam);
		// Grid 파라미터 설정
		$("#jqGrid").setGridParam({'page': result.searchParam.page});
		$("#jqGrid").setGridParam({'sortname': result.searchParam.sortname});
		$("#jqGrid").setGridParam({'sortorder': result.searchParam.sortorder});
		$("#jqGrid").setGridParam({'rowNum': result.searchParam.rowNum});
		$(".ui-pg-selbox").val(result.searchParam.rowNum).prop("selected", true); // 페이지당 건수 Selectbox 설정
	}
}

// 데이터 검색
search = function (){
	var params = $('#frm').serializeObject();
	$('#jqGrid').setGridParam({'datatype': 'json'}).setGridParam({'postData': params}).trigger('reloadGrid');
}

//페이지 이동
movePage = function(url,param){
	// 검색 파라미터 유지를 위한 파라미터 추가
	param.searchParam = $('#frm').serializeObject();
	param.searchParam.page = $("#jqGrid").getGridParam("page");
	param.searchParam.sortname = $("#jqGrid").getGridParam("sortname");
	param.searchParam.sortorder = $("#jqGrid").getGridParam("sortorder");
	param.searchParam.rowNum = $("#jqGrid").getGridParam("rowNum");

	utils.movePage(url, param);
}
