
/**
 * 
* jqGrid의 공통 설정에 대한 Wrapper
*  
*/

(function($){

	$.extend({
		jqGridWrapper: function (options) {
			
			var settings = $.extend({				
				mtype: "POST",				
				datatype: "local",
				postData: searchData,
				page: (isNull(searchData.pageNo) ? 1 : searchData.pageNo),
				autowidth: true,
				height: 350,
				rowNum: (isNull(searchData.rowNum) ? 15 : searchData.rowNum),
				rowList: [5, 10, 15, 20, 30],				
				pager: "#jqGridPager",				
				rownumbers: true,
				caption: false,
				cmTemplate: { sortable: false },
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
					if(obj.total.cnt!=undefined)$(".pagecount > em").text(core.defaultString(obj.total.cnt, "0").money());
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
				    records: function(obj) {return obj.total.cnt;},
				    page   : function(obj) {return obj.param.pageNo;},
				    total  : function(obj) {return Math.ceil(obj.total.cnt / obj.param.pageSize);},
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
			
			$("#jqGrid").jqGrid(settings);
		},
		
		jqGridNoPagingWrapper: function (id,options) {
		
			var settings = $.extend({				
				mtype: "POST",				
				datatype: "json",
				postData: searchData,
				autowidth: true,
				caption: false,
				cmTemplate: { sortable: false },
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

	$.fn.tuiTableRowSpan = function(colIndexs, compareColId) {
	    return $(this).each(function() {
	        //var indexs  = eval("([" + colIndexs + "])");
	        var indexs = colIndexs;
	        var girdId = $(this).attr("id");
	        for (var i = 0; i < indexs.length; i++) {
	            var colIdx = indexs[i];
	            var that;
	            var thatVal = "";
	            var cColId = "";
	            $('tbody tr', $(this)).each(function(row) {
	             $("#"+girdId + " [aria-describedby=" +girdId + "_" + colIdx + "]").eq(row).filter(':visible').each(function(col) {
	              if( compareColId == "" || compareColId == null || compareColId == undefined || ( compareColId != null && typeof compareColId == "object" && !Object.keys(compareColId).length ) ){
	               cColId = colIdx;
	              }else{
	               cColId = compareColId;
	              }
	              
	              var thisVal = $("#"+girdId + " [aria-describedby=" +girdId + "_" + cColId + "]").eq(row).html();
	              
	              if (that != null && thisVal == thatVal) {
	                   
	                        rowspan = $(that).attr("rowSpan");
	                        if (rowspan == undefined) {
	
	                            $(that).attr("rowSpan", 1);
	                            rowspan = $(that).attr("rowSpan");
	                        }
	                        rowspan = Number(rowspan) + 1;
	                        $(that).attr("rowSpan", rowspan); // rowspan을 해준다
	                        //$(this).remove(); // rowspan 당한 컬럼을 제거 한다.
	                        $(this).hide(); // rowspan 당한 컬럼을 숨긴다.
	                    } else {
	                        that  = this;
	                        thatVal = thisVal;
	                    }
	                    // that = (that == null) ? this : that; // set the that if not already set
	                });
	
	            });
	        }
	    });
	};


})(jQuery);