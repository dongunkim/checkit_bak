// DEV - GH.BAEK
var params = {};
var options = {};
options.params = {};
var searchData = {};

/*
 * Event 바인딩
 */
eventFunction = function(data){
	//
	$("#delHostBtn").unbind().on("click", function(){
		var data = $("#hostGrid").getRowData();
		var delHostList = [];
		for(var i=0 ; i < data.length ; i++){
			var row = data[i];
			if(row.chkHost == 'Y'){
				delHostList.push(row.hostId)
			}
		}
		
		var url = "/portal/infra/ajaxDelHost.do";
		var params = {};
		params.hostList = JSON.stringify(delHostList);
		console.log(params);
		utils.ajax(url, params, function(res){
			console.log(res);
			alert('삭제 되었습니다.');
			$("#hostGrid").trigger("reloadGrid");
		});
	});
		
	// 이전 페이지 클릭 이벤트
	$("#backBtn").unbind().on("click", function(){
//		history.go(-1);
		var url = "/portal/infra/infraDiagList.do";
		utils.movePage(url, data);
	});

}

initFunction = function(){

	searchData = result;

	$.jqGridNoPagingWrapper(urlGrid,{
		url: "/portal/infra/infraDiagHostList.ajax",
		colNames: [
				"hostId",
				"호스트명",
				"IP 주소",
				"유형",
				"운영체제",
				"DBMS",
				"웹서버",
				"WAS",
				"웹서비스",
				"선택",
			],
		colModel: [
				{ name:'hostId'      ,index:'hostId'    ,hidden:true },
				{ name:'hostName'    ,index:'hostName'  ,width: 10, align:"left" },
				{ name:'ipAddr'      ,index:'ipAddr'  ,width: 10, align:"left" },
				{ name:'equipType'   ,index:'hostCount'  ,width: 10, align:"left" },
				{ name:'isOs'        ,index:'isOs'  ,width: 10, align:"center"},
				{ name:'isDbms'      ,index:'isDbms'      ,width: 10, align:"center"},
				{ name:'isWebServer' ,index:'isWebServer'   ,width: 8, align:"center"},
				{ name:'isWas'       ,index:'isWas'     ,width: 8, align:"center"},
				{ name:'isWebService',index:'isWebService'    ,width: 8, align:"center"},
				{ name:'chkHost'    ,index: 'chkHost', width: 5, align:  'center', formatter: 'checkbox', editoptions: { value:"Y:N"}, formatoptions:{disabled : false}}
			]
	});
	$("#hostGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'hostName', numberOfColumns: 3, titleText: '서버 정보' },
			{ startColumnName: 'isOS', numberOfColumns: 5, titleText: '분류' },
		]
	});

	$.jqGridNoPagingWrapper(mailUrlGrid,{
		url: "/portal/infra/infraDiagHostList.ajax",
		colNames: [
				"hostId",
				"호스트명",
				"IP 주소",
				"유형",
				"운영체제",
				"DBMS",
				"웹서버",
				"WAS",
				"웹서비스",
				"선택",
			],
		colModel: [
				{ name:'hostId'      ,index:'hostId'    ,hidden:true },
				{ name:'hostName'    ,index:'hostName'  ,width: 10, align:"left" },
				{ name:'ipAddr'      ,index:'ipAddr'  ,width: 10, align:"left" },
				{ name:'equipType'   ,index:'hostCount'  ,width: 10, align:"left" },
				{ name:'isOs'        ,index:'isOs'  ,width: 10, align:"center"},
				{ name:'isDbms'      ,index:'isDbms'      ,width: 10, align:"center"},
				{ name:'isWebServer' ,index:'isWebServer'   ,width: 8, align:"center"},
				{ name:'isWas'       ,index:'isWas'     ,width: 8, align:"center"},
				{ name:'isWebService',index:'isWebService'    ,width: 8, align:"center"},
				{ name:'chkHost'    ,index: 'chkHost', width: 5, align:  'center', formatter: 'checkbox', editoptions: { value:"Y:N"}, formatoptions:{disabled : false}}
			]
	});
	$("#hostGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'hostName', numberOfColumns: 3, titleText: '서버 정보' },
			{ startColumnName: 'isOS', numberOfColumns: 5, titleText: '분류' },
		]
	});

	// 항목별 진단 결과
	$.jqGridNoPagingWrapper(diagResultGrid,{
		url: "/portal/infra/infraDiagObjList.ajax",
		colNames: [
				"그룹명",
				"점검항목",
				"위험도",
				"진단 결과",
				"조치",
				"기한연장",
				"승인",
				"결과",
				"상세"
			],
		colModel: [
				{ name:'category1Name',index:'category1Name',width: 10, align:"left" },
				{ name:'category2Name',index:'category2Name',width: 10, align:"left" },
				{ name:'hostName' ,index:'hostName' ,width: 10, align:"left" },
				{ name:'ipAddr'   ,index:'ipAddr' ,width: 10, align:"left", formatter: resultFormat },
				{ name:'verUrl'   ,index:'verUrl' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
				{ name:'appr' ,index:'appr' ,width: 10, align:"left" },
				{ name:'result' ,index:'result' ,width: 10, align:"left", formatter: resultInputFormat },
				{ name:'detail' ,index:'detail' ,width: 10, align:"left" },
			],
	});
	var colModel = $("#diagResultGrid").jqGrid('getGridParam', 'colModel');
	var title = resultInputHtml(0,0);
	$("#diagResultGrid").jqGrid("setLabel", colModel[7]['name'], title); 

	$("#diagResultGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'category1Name', numberOfColumns: 3, titleText: '진단항목'},
			{ startColumnName: 'verUrl', numberOfColumns: 3, titleText: '취약점 조치'},
			{ startColumnName: 'result', numberOfColumns: 2, titleText: '이행점검'},
		]
	});

	$.jqGridNoPagingWrapper(objGrid,{
		url: "/portal/infra/infraDiagObjList.ajax",
		colNames: [
				"대분류",
				"소분류",
				"호스트명",
				"IP",
				"URL/버전",
				"조치담당"
			],
		colModel: [
				{ name:'category1Name',index:'category1Name',width: 10, align:"left" },
				{ name:'category2Name',index:'category2Name',width: 10, align:"left" },
				{ name:'hostName' ,index:'hostName' ,width: 10, align:"left" },
				{ name:'ipAddr'   ,index:'ipAddr' ,width: 10, align:"left" },
				{ name:'verUrl'   ,index:'verUrl' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
			],
		multiselect : true
	});
	$("#objGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'category1Name', numberOfColumns: 2, titleText: '진단항목분류'},
			{ startColumnName: 'hostName', numberOfColumns: 4, titleText: '호스트'},
		]
	});
	
	$.jqGridNoPagingWrapper(diagObjGrid,{
		url: "/portal/infra/infraDiagObjList.ajax",
		colNames: [
				"대분류",
				"소분류",
				"호스트명",
				"IP",
				"URL/버전",
				"조치담당자",
				"진단자",
				"진단일자",
				"진단결과",
				"이행점검",
				"진단이력",
			],
		colModel: [
				{ name:'category1Name',index:'category1Name',width: 10, align:"left" },
				{ name:'category2Name',index:'category2Name',width: 10, align:"left" },
				{ name:'hostName' ,index:'hostName' ,width: 10, align:"left" },
				{ name:'ipAddr'   ,index:'ipAddr' ,width: 10, align:"left" },
				{ name:'verUrl'   ,index:'verUrl' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
				{ name:'diagUsers' ,index:'diagUsers' ,width: 10, align:"left" },
				{ name:'diagDate' ,index:'diagDate' ,width: 10, align:"left" },
				{ name:'diagResult' ,index:'diagResult' ,width: 10, align:"left" },
				{ name:'rediagResult' ,index:'rediagResult' ,width: 10, align:"left" },
				{ width: 5, align:"left", formatter: histButton },
			],
		rownumbers: true,
		multiselect : true
	});

	$.jqGridNoPagingWrapper(diagHistGrid,{
		url: "/portal/infra/infraDiagObjList.ajax",
		colNames: [
				"진단차수",
				"조치담당자",
				"신청일",
				"진단자",
				"취약점 수",
				"조치 수",
				"예외요청(승인)수",
				"기한연장(승인)수"
			],
		colModel: [
				{ name:'category1Name',index:'category1Name',width: 10, align:"left" },
				{ name:'category2Name',index:'category2Name',width: 10, align:"left" },
				{ name:'hostName' ,index:'hostName' ,width: 10, align:"left" },
				{ name:'ipAddr'   ,index:'ipAddr' ,width: 10, align:"left" },
				{ name:'verUrl'   ,index:'verUrl' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
			],
	});

	// 항목별 진단 결과
	$.jqGridNoPagingWrapper(diagResultGrid,{
		url: "/portal/infra/infraDiagObjList.ajax",
		colNames: [
				"그룹명",
				"점검항목",
				"위험도",
				"진단 결과",
				"조치",
				"기한연장",
				"승인",
				"결과",
				"상세"
			],
		colModel: [
				{ name:'category1Name',index:'category1Name',width: 10, align:"left" },
				{ name:'category2Name',index:'category2Name',width: 10, align:"left" },
				{ name:'hostName' ,index:'hostName' ,width: 10, align:"left" },
				{ name:'ipAddr'   ,index:'ipAddr' ,width: 10, align:"left", formatter: resultFormat },
				{ name:'verUrl'   ,index:'verUrl' ,width: 10, align:"left" },
				{ name:'fixUsers' ,index:'fixUsers' ,width: 10, align:"left" },
				{ name:'appr' ,index:'appr' ,width: 10, align:"left" },
				{ name:'result' ,index:'result' ,width: 10, align:"left", formatter: resultInputFormat },
				{ name:'detail' ,index:'detail' ,width: 10, align:"left" },
			],
	});
	var colModel = $("#diagResultGrid").jqGrid('getGridParam', 'colModel');
	var title = resultInputHtml(0,0);
	$("#diagResultGrid").jqGrid("setLabel", colModel[7]['name'], title); 

	$("#diagResultGrid").jqGrid('setGroupHeaders', {
		useColSpanStyle: true,
		groupHeaders:[
			{ startColumnName: 'category1Name', numberOfColumns: 3, titleText: '진단항목'},
			{ startColumnName: 'verUrl', numberOfColumns: 3, titleText: '취약점 조치'},
			{ startColumnName: 'result', numberOfColumns: 2, titleText: '이행점검'},
		]
	});

	$("#diagHist").hide();

	// $(".ui-jqgrid-labels").hide(); 라벨 숨기기

    eventFunction(result);
}

// 그리드 안에 Selectbox 넣기
function resultInputFormat (cellvalue, options, rowObject) {
	var html=resultInputHtml(cellvalue,options['rowId']);
	return html;
};

function resultInputHtml (cellvalue, index) {
	var html="";
	html += '<select class="info_selectbox" name="result" id="result_'+index+'" style="height:100%;width:90%">';
	html += '<option value="">-선택-</option>';
	html += '<option value="">N/A</option>';
	html += '<option value="">취약</option>';
	html += '<option value="">양호</option>';
	html += '</select>';

	return html;
};

function resultFormat (cellvalue, options, rowObject) {
	return '<a href="javascript:resultDetailPop(\''+cellvalue+'\');" id="main0101MoreBtn" styl="cursor:pointer">취약</a>';
};

function resultDetailPop(id){
	alert(id);
}

function histButton (cellvalue, options, rowObject) {
	return '<input type="button" onclick="javascript:clickHist(\''+rowObject.diagObjId+'\');" value="이력"/>';
};

function clickHist(id){
	console.log("id="+id);
	$("#diagHist").show();
}
