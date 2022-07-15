var searchData = {};

initFunction = function(){
	// 공지구분 SETTING
	utils.getCommNm("931", "searchBbsGb");
	searchData.boardType = "01";
	
	$.jqGridWrapper({
		url: "/admin/board/boardList.ajax",
		colNames: [
				"No",
				"제목",
				"작성자",
				"파일",
				"등록일시",
				"조회수",
				"내용"
			],
		colModel: [
				{ name:'boardId'   ,index:'boardId'   ,width: 5, align:"left" },
				{ name:'boardTitle',index:'boardTitle',width: 50, align:"left" },
				{ name:'regId'     ,index:'regNm'     ,width: 10, align:"left" },
				{ name:'attachId'  ,index:'attachId'  ,width: 10, align:"left" },
				{ name:'regDt'     ,index:'regDt'     ,width: 10, align:"center"},
				{ name:'viewCount' ,index:'viewCount' ,width: 10, align:"center"},
				{ name:'boardDesc' ,index:'viewCount' ,hidden:true}
			],
		subGrid:true,
	    subGridOptions : {
			// configure the icons from theme rolloer
			plusicon: "ui-icon-triangle-1-e",
			minusicon: "ui-icon-triangle-1-s",
			openicon: "ui-icon-arrowreturn-1-e"
		},
		subGridRowExpanded:function(subgrid_id,row_id){
			var ret = $("#jqGrid").jqGrid('getRowData', row_id).boardDesc;
			$("#"+subgrid_id).html($("#jqGrid").jqGrid('getRowData', row_id).boardDesc);
		},
		onSelectRow: function(rowid) {
			var param = $("#jqGrid").getRowData(rowid);
			param.boardType = "01";
			var url = "/admin/board/boardDetail.do";
			utils.movePage(url, param);
		},
		loadComplete:function(obj){
			var subGridOptions = $("#jqGrid").getGridParam('subGridOptions');
    		var plusIcon = subGridOptions.plusicon;
    		
    		$("#jqgh_" + $("#jqGrid")[0].id + "_subgrid").html('<a href="javascript:toggleSubgrid()" style="cursor: pointer;"><span class="ui-icon ' + plusIcon +'"></span></a>');
    		
    		// 페이징
			if(obj.total.cnt!=undefined)$(".pagecount > em").text(core.defaultString(obj.total.cnt, "0").money());
    		
//			$("jqgrid 아이디").tuiTableRowSpan(["boardTitle, "머지 하고자 하는 컬럼 id", "머지 하고자 하는 컬럼 id"], "머지 그룹 기준 컬럼 id");
		    $("#jqGrid").tuiTableRowSpan(["regId"],"boardTitle");

    		/*
    		.click(function () {
				console.log($(this).find(">a>span"));
		    });
		    */
    		
    		/*
			var ids = $("#jqGrid").getDataIDs();
			for(var i in ids){
				$("#jqGrid").expandSubGridRow(i);
			}
			*/

			//$("#jqGrid").jqGrid("hideCol","subgrid");
			//$(".subgrid-cell").hide();
		}
	});

	search();
	
    eventFunction(result);
}

function toggleSubgrid(){
	var subGridOptions = $("#jqGrid").getGridParam('subGridOptions');
	var plusIcon = subGridOptions.plusicon;
	var minusIcon = subGridOptions.minusicon;
	var $subgrid = $("#jqgh_" + $("#jqGrid")[0].id + "_subgrid");
    var $spanIcon = $subgrid.find(">a>span");
	var $body = $subgrid.closest(".ui-jqgrid-view").find(">.ui-jqgrid-bdiv>div>.ui-jqgrid-btable>tbody");
    if ($spanIcon.hasClass(plusIcon)) {
        $spanIcon.removeClass(plusIcon).addClass(minusIcon)
		$body.find(">tr.jqgrow>td.sgcollapsed").click();
    } else {
        $spanIcon.removeClass(minusIcon).addClass(plusIcon)
        $body.find(">tr.jqgrow>td.sgexpanded").click();
    }
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
  		expand();
	});
	
		// 엑셀 다운로드
	$("#excelDownBtn").unbind().bind("click", function(){
		utils.excelDown("stat0101Table1", "기본서비스");
	});

	// 등록 버튼
	$("#regBtn").unbind().bind("click",function(){
		let params = {};
			params = result.param;
			//params.search = "Y";
		utils.movePage("/admin/board/boardReg.do", params);
	});
}