<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.io.File"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="page" uri="http://www.opensymphony.com/sitemesh/page"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
pageContext.setAttribute("CR", "\r"); 
pageContext.setAttribute("LF", "\n"); 
%>
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
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery-direct.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.form.js"/>"></script>	
	<!-- 퍼블리싱 js end -->
	
	<!-- 개발용 css start -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5dialog.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5grid.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/ax5/css/ax5uploader.css"/>">
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/css/dev_cust.css"/>">
	<!-- 개발용 css start -->
	
	<!-- 개발용 js start -->
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXJ.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5core.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5picker.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5dialog.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5grid.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/ax5/js/ax5uploader.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/ui.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/admin/js/roll.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/admin/js/menu.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/admin/js/siteMap.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/utils.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/core.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/fileSaveUtils.js"/>"></script>
	<!-- 개발용 js end -->

<c:set var="toRequest" value="${fn:replace(fn:replace(toRequestUri, LF, ''), CR, '')}" />
<script type="text/javascript">
//<![CDATA[
var result   = ${(result == null || result == "")? "{}" : result};
const menuList = ${(menuList == null || menuList == "")? "{}" : menuList};
const menuId   = 1;
const rid      = ${(rid == null || rid == "")? "" : rid};
const toPageUrl = '${toRequest }';
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
<title>업무지원시스템</title>
</head>
<body>
	<div class="wrap">
		<!-- header start -->
		<page:applyDecorator id="adminHeaderTemplate" name="adminHeaderTemplate"/>
		<!-- header end -->
		<!-- body -->
		<div class="container">
			<!-- 사이트맵 -->
			<page:applyDecorator id="adminSiteMapTemplate" name="adminSiteMapTemplate"/>
			<!-- 사이트맵 -->
			<!-- 컨텐츠 start -->
			<decorator:body/>
			<!-- 컨텐츠 end-->
		</div>
		<!-- body -->
		<!-- footer start -->
		<!-- <page:applyDecorator id="adminFooterTemplate" name="adminFooterTemplate"/> -->
		<!-- footer end -->
	</div>
	<page:applyDecorator id="adminLoadingTemplate" name="adminLoadingTemplate"/>
	<a href="javascript:void(0)" class="btn_top"><img src="/resources/images/icon/btn_top.png"></a>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</body>
</html>