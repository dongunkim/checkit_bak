<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
	<meta id="_csrf_parameter" name="_csrf_parameter" content="${_csrf.parameterName}"/>
		
	<link rel='stylesheet' href='/resources/portal/css/css-pro-layout.css'>
    <link rel='stylesheet' href='/resources/portal/fonts/remixicon.css'>
    <link rel="stylesheet" href="/resources/portal/css/style.css">

    <link rel="stylesheet" type="text/css" href="/resources/portal/js/lib/css/jquery-ui.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/portal/js/lib/jqgrid-4.7.1/css/ui.jqgrid.css" />
    <link rel="stylesheet" type="text/css" href="/resources/portal/js/lib/jqgrid-4.7.1/css/ui.jqgrid-bootstrap.css" />

    <script type="text/javascript" src="/resources/portal/js/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/portal/js/jquery/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/resources/portal/js/jquery/jquery.form.js"></script>
    <script type="text/javascript" src="/resources/portal/js/jquery/jquery-direct.min.js"></script>
    <script type="text/javascript" src="/resources/portal/js/lib/jqgrid-4.7.1/i18n/grid.locale-ko.js"></script>
    <script type="text/javascript" src="/resources/portal/js/lib/jqgrid-4.7.1/js/minified/jquery.jqGrid.min.js"></script>
    
    <!-- 개발용 css start -->	
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5dialog.css"/>">
	<!-- 개발용 css start -->
	
    <!-- 개발용 js start -->
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5core.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5picker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5formatter.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5dialog.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/core.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/utils.js"/>"></script>
	<!-- 개발용 js end -->
    
<script type="text/javascript">
//<![CDATA[
let isLogin = ${(sessionScope.isLogin == null || sessionScope.isLogin == "") ? false : true};
let result = ${(result == null || result == "")? "{}" : result};

//팝업이 열려있는데 로그아웃이 되었을경우
if (!isLogin) {
	if ($.type(window.top.popupOptionArr) !== "undefined") {
		if (window.top.popupOptionArr.length > 0) {
			window.top.location.href = "/login/loginPage.do";			
		}
	}
}
$(document).ready(function() {
	if (typeof initFunction === "function") {		
		if (typeof utils.defaultString(result) === "object") {
			console.log('--------------------TEST 1');
			console.log(result);
			initFunction(result);
		} else {			
			initFunction();
		}
	}
});
//]]>
</script>
<%
	// 화면에 해당하는 js 파일을 자동으로 include 한다.
	String url = request.getServletPath().toString();	
	if( url.lastIndexOf(".") > -1) {
		String jsPath = url.substring(0, url.lastIndexOf("/"));
		if (jsPath.indexOf("jsp/") == -1) {
			jsPath = "/jsp/portal" + url.substring(0, url.lastIndexOf("/"));			
		}
		String screenId = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
		String jsFileNm = screenId + ".js";
		
		// js 파일이 있을 경우만 include 한다.
		File jsFile = new File(request.getSession().getServletContext().getRealPath(jsPath) + jsFileNm);		
		if (jsFile.isFile()) {
			System.out.println("Script included");
			out.print("<script type=\"text/javascript\" src=\"" + request.getContextPath() + jsPath + jsFileNm + "\"></script>");
		}
	}
%>
<title>IT보안진단시스템</title>
</head>
<body>
	<decorator:body/>
	<page:applyDecorator id="loadingTemplate" name="loadingTemplate"/>
</body>
</html>