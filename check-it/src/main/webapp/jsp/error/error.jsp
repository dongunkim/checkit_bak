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

</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<tr>
		<td align="center">
		<table width="660" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="10"><img src="${pageContext.request.contextPath}/images/error/not_img05.gif"></td>
			</tr>
			<tr>
				<td height="10"><img src="${pageContext.request.contextPath}/images/error/not_img01.gif"></td>
			</tr>
			<tr>
				<td align="center" background="${pageContext.request.contextPath}/images/error/not_img03.gif">
				<table width="521" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="${pageContext.request.contextPath}/images/error/not_img04.gif"></td>
					</tr>
					<tr>
						<td height="20">&nbsp;</td>
					</tr>
					<tr>
						<td height="20" align="center">
						<table width="509" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td height="25" class="style1">
									<img src="${pageContext.request.contextPath}/images/error/icon1.gif" width="13" height="11" align="absmiddle">
									찾으려는 페이지가 제거되었거나, 이름이 변경되었거나, 일시적으로 사용이 중단되었습니다.
								</td>
							</tr>
							<tr>
								<td height="25" class="style1">
									<img src="${pageContext.request.contextPath}/images/error/icon1.gif" width="13" height="11" align="absmiddle">
									브라우저의 주소 표시줄에 웹 사이트 주소의 철자와 형식이 정확한지 확인하십시오.
								</td>
							</tr>
							<tr>
								<td height="25" class="style1">
									<img src="${pageContext.request.contextPath}/images/error/icon1.gif" width="13" height="11" align="absmiddle">
									웹 사이트 관리자에게 링크가 잘못되었음을 알려주십시오.
								</td>
							</tr>
						</table>
						</td>
					</tr>
					<tr>
						<td height="35">&nbsp;</td>
					</tr>
					<tr>
						<td align="center">
							<a href="javascript:history.back()">
								<img src="${pageContext.request.contextPath}/images/error/not_img07.gif" width="144" height="24" border="0">
							</a>
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><img src="${pageContext.request.contextPath}/images/error/not_img02.gif"></td>
			</tr>
			<tr>
				<td align="center" height="30" valign="bottom">
					<img src="${pageContext.request.contextPath}/images/error/not_img06.gif" border="0">
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html:html>
