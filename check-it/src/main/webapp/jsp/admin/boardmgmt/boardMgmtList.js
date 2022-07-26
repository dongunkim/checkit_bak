var searchData = {};

initFunction = function(){
	searchData.boardType = "01";
	
	$.jqGridWrapper({
		url: "/admin/boardmgmt/selBoardMgmtList.ajax",
		colNames: [
				"No",
				"작성자",
				"제목",
				"등록일시",
				"조회수",
				"내용"
			],
		colModel: [
				{ name:'boardId'    ,index:'boardId'    ,width: 5, align:"left" },
				{ name:'regUserName',index:'regUserName',width: 10, align:"left" },
				{ name:'boardTitle' ,index:'boardTitle' ,width: 50, align:"left" },
				{ name:'regDt'      ,index:'regDt'      ,width: 10, align:"center"},
				{ name:'viewCount'  ,index:'viewCount'  ,width: 10, align:"center"},
				{ name:'boardDesc'  ,index:'viewCount'  ,hidden:true}
			],
		onSelectRow: function(rowid) {
			var param = $("#jqGrid").getRowData(rowid);
			param.boardType = "01";
			var url = "/admin/boardmgmt/boardMgmtDetail.do";
			utils.movePage(url, param);
		}
	});

	search();
	
    eventFunction(result);
}

function search() {
	var nowPage = 1;
	$("#nowPage").val(nowPage);

	var formData = $("#frm").serializeObject();		
	$("#jqGrid").setGridParam({'datatype': 'json'}).setGridParam({'page': nowPage}).setGridParam({'postData': formData}).trigger('reloadGrid');
}

eventFunction = function(data){
	// 검색 버튼
	$("#searchBtn").unbind().bind("click",function(){
		search();
	});

	// 등록 버튼
	$("#regBtn").unbind().bind("click",function(){
		let params = {};
		//params = result.param;
		utils.movePage("/admin/boardmgmt/boardMgmtReg.do", params);
	});

	// Enter Key 이벤트
	utils.enter("#searchText","#searchBtn");

}