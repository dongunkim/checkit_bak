<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="contens_bd" style="width:650px;">
	<div class="info_area">
		<h3>등록정보</h3>
		<ul>
			<li>
				<span class="title">이름</span>
				<span class="word">
					<input type="text" id="userNm" name="userNm" autocomplete="off" class="input_txt w200"  placeholder="이름을 입력하세요."/>
				</span>
			</li>
			<li>
				<span class="title">휴대폰</span>
				<span class="word">
					<input type="text" id="hpNo" name="hpNo" autocomplete="off" class="input_txt w200"  placeholder="휴대폰번호를 입력하세요."/>
				</span>
			</li>
			<li>
				<span class="title">이메일</span>
				<span class="word">
					<input type="text" id="email" name="email" autocomplete="off" class="input_txt w200"  placeholder="이메일을 입력하세요."/>
				</span>
			</li>
		</ul>
	</div>
	<div class="noteditems_area" style="margin-top:20px">
		<div class="title b">참고사항</div>
		<ul>
			<li>가입시 등록하신 이름, 휴대폰, 이메일을 입력하세요.</li>
		</ul>
	</div>
	<div class="info_button_area">
		<button class="button_info black" id="closeBtn">
			<span>취소</span>
		</button>
		<button class="button_info oreage" id="searchBtn">
			<span>확인</span>
		</button>
	</div>
</div>