<%@ page contentType="text/html; charset=UTF-8"%>
<div class="header">
	<div class="section_hd">
		<!-- 링크영역 -->
		<div class="nav_area">
			<ul class="area_link fs12">	
				<li><a href="javascript:void(0)"><b>${sessionScope.userNm}</b>님 환영합니다.</a></li>
				<li class="dropdown_menu">    
                    <button class="btn_dropdown"><i></i></button>
                    <ul class="dropdown_box">
                        <li><a href="javascript:void(0)" onclick="utils.movePage('/admin/login/logout.do')">로그아웃</a></li>
                        <li><a href="javascript:void(0)" id="changeInfoBtn">정보변경</a></li>
                    </ul>
                </li>
				<li><a href="javascript:void(0)">접속시간 ${sessionScope.loginTime}</a></li>
				<li><a href="javascript:void(0)" id="sitemapBtn">사이트맵</a></li>
			</ul>
		</div>
		<div class="menu_area">
			<!-- 링크영역 -->
			<h1 class="ml25 mt3" onclick="utils.movePage('/admin/main/main0101Page.do')" style="cursor:pointer;"></h1>
			<!-- 메인메뉴 -->
			<ul class="area_nav fs18 mt15 ml50" id="menuArea">
			</ul>
			<!-- 메인메뉴 -->
		</div>
	</div>
</div>
