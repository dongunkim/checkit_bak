<%@ page language="java" contentType="text/html;charset=euc-kr"%>

<style type="text/css">
.style1 {
	font-size: 12px;
	color: #666666;
	letter-spacing: -1px;
}
</style>
<html:html xhtml="true">
<head>
<title>Error</title>
<link href='${pageContext.request.contextPath}/css/struts.css' type="text/css" rel="stylesheet" />
<html:base />

<script type="text/javascript">
	alert("잘못된 경로로 접근하셨습니다. 메인화면으로 이동합니다.");
	location.href="/admin/main/main0101Page.do";
</script>

</head> 
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">



</body>
</html:html>


