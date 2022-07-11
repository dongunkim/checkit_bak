<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ page import="org.springframework.security.core.userdetails.UserDetailsService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	
	<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]--><!--ie9 미만 접속시 html5, Media Query지원 스트립트 연결-->

	
	<!-- 퍼블리싱 css start -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/w_base.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/w_reset.css"/>"/>
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/w_media.css"/>"/>
	<!-- 퍼블리싱 css start -->
	
	<!-- 퍼블리싱 js start -->
	<script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/common.js"/>"></script>
	<!-- 퍼블리싱 js end -->
	
	<!-- 개발용 css start -->
	<link rel="stylesheet" type="text/css" href="<c:url value="/resources/lib/axj/css/AXJ.min.css"/>"/>
	<!-- 개발용 css start -->
	
	<!-- 개발용 js start -->
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXConfig.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXUtil.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/lib/axj/js/AXCore.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/utils.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/js/core.js"/>"></script>
	<!-- 개발용 js end -->
<script type="text/javascript">
$(document).ready(function () {
   
});
</script>
<title>고객지원시스템</title>
</head>
<body>
<div>
    <div>
      접근권한이 없습니다.<br> 담당자에게 문의하여 주시기 바랍니다. <br>
    <br>
    <a href="javascript:history.back()">확인</a> 
    </div>
</div>
</body>
</html>