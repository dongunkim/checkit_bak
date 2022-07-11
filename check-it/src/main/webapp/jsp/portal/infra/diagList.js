var searchData = {};

initFunction = function(){
	
	$.jqGridWrapper({
		url: "/portal/infra/ajaxDiagList.do",
		colNames: [
				"",
				"진단신청명",
				"상품서비스명",
				"호스트(대상수)",
				"신청자",
				"신청완료",
				"접수",
				"계획",
				"진단",
				"취약점",
				"진단자",
				"태그"
			],
		colModel: [
				{ name:'diagId'     ,index:'diagId'    ,hidden:true },
				{ name:'diagName'   ,index:'diagName'  ,width: 10, align:"left" },
				{ name:'productId'   ,index:'productId'  ,width: 10, align:"left" },
				{ name:'hostCount'   ,index:'hostCount'  ,width: 10, align:"left" },
				{ name:'reqUserId'   ,index:'reqUserId'  ,width: 10, align:"center"},
				{ name:'reqDt'       ,index:'reqDt'      ,width: 10, align:"center", formatter:statusFormatter},
				{ name:'acceptDt'    ,index:'acceptDt'   ,width: 8, align:"center", formatter:statusFormatter},
				{ name:'planDt'      ,index:'planDt'     ,width: 8, align:"center", formatter:statusFormatter},
				{ name:'diagDt'     ,index:'diagDt'    ,width: 8, align:"center", formatter:statusFormatter},
				{ name:'rediagDt'   ,index:'rediagDt'  ,width: 8, align:"center", formatter:statusFormatter},
				{ name:'diagUserId' ,index:'diagUserId',width: 10, align:"center"},
				{ name:'tag'         ,index:'tag'        ,width: 10, align:"center"}
			],
		onSelectRow: function(rowid) {
			let param = $("#jqGrid").getRowData(rowid);
			let url = "/portal/infra/diagDetail.do";
			utils.movePage(url, param);
		},
	});
	$("#jqGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'diagName', numberOfColumns: 4, titleText: '진단신청' },
			{ startColumnName: 'reqDt', numberOfColumns: 5, titleText: '진단상태' },
			{ startColumnName: 'diagUserId', numberOfColumns: 1, titleText: '진단결과' }
		]
	});
	
	
	search();

    eventFunction(result);
}

function statusFormatter(cellvalue)
{
	if(cellvalue=='0')
		html = '→';
	else
		html = cellvalue == null ? '' : cellvalue;
    return html;
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

	// 검색 버튼
	$("#searchAllBtn").unbind().bind("click",function(){
		$('frm').each(function() {
			this.reset();
		});
  		search();
	});
	
}