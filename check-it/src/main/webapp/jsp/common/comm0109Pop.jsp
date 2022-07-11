<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="contens_bd" style="width:650px;height:290px;">
	<div style="text-align:left;padding-left: 30px;">
		<font style="position:relative; color:#ff0000bf; font-size:100px;"><strong id="timer">60</strong><i style="font-size: 50px;color: #666;line-height: 36px;position: absolute;bottom: 25px;margin: 0px 0px 9px 6px;width: 470px;">초 후 로그아웃 됩니다.</i></font>
	</div>
	<div class="noteditems_area" style="margin-top:20px">
		<div class="title b">세션만료 안내</div>
		<ul>
			<li>안전한 업무지원시스템 사용을 위해서 자동로그아웃 예정입니다.</li>
			<li><font style="color:#ff0000bf;"><strong>10</strong></font> 분간 사용이 없으실 경우 자동으로 로그아웃 됩니다.</li>
			<li><font style="color:#1b00ffbf;"><strong>로그아웃</strong></font>을 원하지 않으시면 로그인 연장하기 버튼을 클릭해 주세요.</li>
		</ul>
	</div>
	<div class="info_button_area">
		<button class="button_info black" id="logoutBtn">
			<span>로그아웃</span>
		</button>
		<button class="button_info oreage" id="loginRenewBtn">
			<span>로그인 연장하기</span>
		</button>
	</div>
</div>