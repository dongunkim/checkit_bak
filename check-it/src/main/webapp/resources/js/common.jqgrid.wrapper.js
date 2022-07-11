
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
//				datatype: "local",
				datatype: "json",
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
})(jQuery);