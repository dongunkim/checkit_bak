<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
	<meta id="_csrf_parameter" name="_csrf_parameter" content="${_csrf.parameterName}"/>
	
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]--><!--ie9 미만 접속시 html5, Media Query지원 스트립트 연결-->

	<!-- 퍼블리싱 css start -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/css/w_base.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/css/w_reset.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/css/w_media.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/admin/css/all_base.css"/>"/>
	<!-- 퍼블리싱 css start -->
	
	<!-- 퍼블리싱 js start -->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-ui.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.form.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-direct.min.js"/>"></script>
	<!-- 퍼블리싱 js end -->
	
	<!-- 개발용 css start -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/axj/css/AXJ.min.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/font-awesome.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5picker.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5calendar.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5uploader.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5dialog.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5grid.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5modal.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5mask.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/css/jquery-ui.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/css/dev_cust.css"/>">
	<!-- 개발용 css start -->
	
	<!-- 개발용 js start -->
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXJ.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXConfig.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXUtil.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXCore.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5core.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5picker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5formatter.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5calendar.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5uploader.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5dialog.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5grid.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5modal.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5mask.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ui.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/admin/js/roll.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/admin/js/menu.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/utils.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/editUtils.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/code.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/core.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/treeUtils.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/chart/dist/Chart.bundle.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/chart.js"/>"></script>
	<!-- 개발용 js end -->

<script type="text/javascript">
//<![CDATA[
let result   = ${(result == null || result == "")? "{}" : result};
const menuList = ${(menuList == null || menuList == "")? "{}" : menuList};
const menuId   = ${(menuId == null || menuId == "")? "" : menuId};
$(document).ready(function(){
	if(typeof initFunction === "function"){
		if(typeof utils.defaultString(result) === "object"){
			initFunction(result);
		}else{
			initFunction();
		}
	}
});
//]]>
</script>
<%
	// 화면에 해당하는 js 파일을 자동으로 include 한다.
	String url = request.getServletPath().toString();
	if( url.lastIndexOf(".") > 0 ) {
		String jsPath = url.substring(0, url.lastIndexOf("/"));
		if(jsPath.indexOf("jsp/") == -1){
			jsPath = "/jsp" + url.substring(0, url.lastIndexOf("/"));
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
<title>고객지원시스템</title>
</head>
<body>
    <div class="wrap">
        <!-- body -->
        <!-- 서브메뉴 -->
	    <!-- 컨텐츠 start -->
	    <decorator:body/>
	    <!-- 컨텐츠 end -->
        <!-- body -->
    </div>
    <page:applyDecorator id="adminLoadingTemplate" name="adminLoadingTemplate"/>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</body>
</html>