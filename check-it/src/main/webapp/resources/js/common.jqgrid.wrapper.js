
/**
 * 
* jqGrid의 공통 설정에 대한 Wrapper
*  
*/

(function($){

	$.extend({
		jqGridWrapper: function (id,options) {
			
			var settings = $.extend({				
				mtype: "POST",				
				datatype: "local",
				postData: "",
//				page: (isNull(searchData.pageNo) ? 1 : searchData.pageNo),
				page: 1,
				autowidth: true,
				height: 350,
//				rowNum: (isNull(searchData.rowNum) ? 15 : searchData.rowNum),
				rowNum: 15,
				rowList: [5, 10, 15, 20, 30],				
				pager: "#jqGridPager",				
				rownumbers: true,
				caption: false,
//				cmTemplate: { sortable: false },
				loadonce: false,
				//styleUI : 'Bootstrap',
				colNames:  [],
				colModel: [],
				prmNames: { page: 'pageNo', rows: 'pageSize' },			
				gridComplete: function() {					
				},
				loadError: function(jqXHR, textStatus, errorThrown) {
					jqGridLoadErrorHandler($(this), jqXHR, textStatus, errorThrown);
				},
				loadComplete: function(obj) {
		    		// 페이징
					if(obj.total!=undefined)$(".pagecount > em").text(core.defaultString(obj.total, "0").money());
				},
				loadError: function(xhr, status, error) {
					console.log(xhr);
					/*
					alert("Status : " + xhr.responseJSON.code + "("+ xhr.responseJSON.status + ")" +
							"\nMessage : " + xhr.responseJSON.message +
								"\n시스템 관리자에게 문의 하거나, 잠시후 다시 시도 바랍니다.")
					*/
					
					console.log("xhr : ");
					console.log(xhr);
				},
				jsonReader: {
					root   : function(obj) {return obj.list;},
				    records: function(obj) {return obj.total;},
				    page   : function(obj) {return obj.paging.pageNo;},
				    total  : function(obj) {return Math.ceil(obj.total / obj.paging.pageSize);},
				    repeatitems: false,
				    repeat: false
				},
				onSelectRow: function(rowid) {					
				},
				onPaging: function(pgButton) {					
					if (pgButton == 'next_jqGridPager')
						$("#pageNo").val($("#jqGrid").getGridParam('page') + 1);
					else if (pgButton == 'prev_jqGridPager')
						$("#pageNo").val($("#jqGrid").getGridParam('page') - 1);
					else if (pgButton == 'last_jqGridPager')
						$("#pageNo").val($("#jqGrid").getGridParam('lastpage'));
					else if (pgButton == 'first_jqGridPager')
						$("#pageNo").val(1);
					else if (pgButton == 'user') {
						var totalPage = $("#jqGrid").getGridParam('total');
						var userInput = Number($("#jqGridPager .ui-pg-input").val());					
						if (totalPage < userInput)
							$("#pageNo").val($("#jqGrid").getGridParam('lastpage'));
						else if (totalPage >= userInput && userInput > 0) {
							$("#pageNo").val(userInput);
						}					
					}					
				}
			}, options);
			
			$(id).jqGrid(settings);
		},
		
		jqGridNoPagingWrapper: function (id,options) {
		
			var settings = $.extend({				
				mtype: "POST",				
				datatype: "json",
//				postData: searchData,
				postData: searchData,
				autowidth: true,
				caption: false,
//				cmTemplate: { sortable: false },
				loadonce: false,
				//styleUI : 'Bootstrap',
				colNames:  [],
				colModel: [],
				gridComplete: function() {					
				},
				loadError: function(jqXHR, textStatus, errorThrown) {
					jqGridLoadErrorHandler($(this), jqXHR, textStatus, errorThrown);
				},
				loadError: function(xhr, status, error) {
					alert("Status : " + xhr.responseJSON.code + "("+ xhr.responseJSON.status + ")" +
							"\nMessage : " + xhr.responseJSON.message +
								"\n시스템 관리자에게 문의 하거나, 잠시후 다시 시도 바랍니다.")
					console.log("xhr : ");
					console.log(xhr);					
				},
				jsonReader: {
					root   : function(obj) {return obj.list;},
				    repeatitems: false,
				    repeat: false
				},
				onSelectRow: function(rowid) {					
				},
			}, options);
			
			$(id).jqGrid(settings);
		}

	});

	$.fn.rowspans = function(colIndex1,colIndex2) {
		return $(this).each(function() {
			var girdId = $(this).attr("id");
            var befIdx1;
            var befIdx1Val = "";
            var befIdx2;
            var befIdx2Val = "";
			$('tbody tr', $(this)).each(function(row) {
				$("#"+girdId + " [aria-describedby=" +girdId + "_" + colIndex1 + "]").eq(row).filter(':visible').each(function(col) {
					cur1 = this;
					cur2 = $("#"+girdId + " [aria-describedby=" +girdId + "_" + colIndex2 + "]").eq(row)[0];
					//console.log(cur2);
					var curVal1 = $("#"+girdId + " [aria-describedby=" +girdId + "_" + colIndex1 + "]").eq(row).html();
					var curVal2 = $("#"+girdId + " [aria-describedby=" +girdId + "_" + colIndex2 + "]").eq(row).html();
					if (befIdx1 != null && curVal1 == befIdx1Val) { // 이전값과 같으면
						// Index2 처리
						if (befIdx2 != null && curVal2 == befIdx2Val) { // 이전값과 같으면
							rowspan = $(befIdx2).attr("rowSpan") == undefined ? 1 : $(befIdx2).attr("rowSpan");
							rowspan = Number(rowspan) + 1;
							$(befIdx2).attr("rowSpan", rowspan); // rowspan을 해준다
							$(cur2).hide(); // rowspan 당한 컬럼을 숨긴다.
						} else {
							befIdx2  = cur2;
							befIdx2Val = curVal2;
						}
						
						// Index1 처리
						rowspan = $(befIdx1).attr("rowSpan") == undefined ? 1 : $(befIdx1).attr("rowSpan");
						rowspan = Number(rowspan) + 1;
						$(befIdx1).attr("rowSpan", rowspan); // rowspan을 해준다
						$(cur1).hide(); // rowspan 당한 컬럼을 숨긴다.
					} else {
						befIdx1  = cur1;
						befIdx1Val = curVal1;
						befIdx2  = cur2;
						befIdx2Val = curVal2;
					}
				});
			});
		});
	};

})(jQuery);