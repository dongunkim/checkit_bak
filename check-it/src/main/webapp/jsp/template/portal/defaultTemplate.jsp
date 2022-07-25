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
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
	<!-- 개발용 js end -->

<script type="text/javascript">

</script>
<%
	// 화면에 해당하는 js 파일을 자동으로 include 한다.
	String url = request.getServletPath().toString();
	if( url.lastIndexOf(".") > 0 ) {
		String jsPath = url.substring(0, url.lastIndexOf("/"));
		if(jsPath.indexOf("jsp/") == -1){
			jsPath = "/jsp/portal" + url.substring(0, url.lastIndexOf("/"));
		}
		String screenId = url.substring(url.lastIndexOf("/"), url.lastIndexOf("."));
		String jsFileNm = screenId + ".js";
		// js 파일이 있을 경우만 include 한다.
		File jsFile = new File(request.getSession().getServletContext().getRealPath(jsPath) + jsFileNm);
		
		if (jsFile.isFile()){
			out.print("<script type=\"text/javascript\" src=\"" + request.getContextPath() + jsPath + jsFileNm + "\"></script>");
		}
		
	}
%>
<title>IT보안진단시스템</title>
</head>
<body>
	<div class="layout has-sidebar fixed-sidebar fixed-header">
		<!-- left start -->
		<page:applyDecorator id="leftTemplate" name="leftTemplate"/>
	    <div id="overlay" class="overlay"></div>
		<!-- left end -->

		<div class="layout">
			<!-- header start -->
			<page:applyDecorator id="headerTemplate" name="headerTemplate"/>
			<!-- header End -->
			
			<!-- 컨텐츠 start -->
			<decorator:body/>
			<!-- 컨텐츠 end-->

			<!-- footer start -->
			<page:applyDecorator id="footerTemplate" name="footerTemplate"/>
			<!-- footer end -->
	        <div class="overlay"></div>
		</div>
	</div>
	<page:applyDecorator id="loadingTemplate" name="loadingTemplate"/>
	<a href="javascript:void(0)" class="btn_top"><img src="/resources/images/icon/btn_top.png"></a>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

	<script type="text/javascript" src="<c:url value="/resources/js/script.js"/>"></script>
</body>
</html>