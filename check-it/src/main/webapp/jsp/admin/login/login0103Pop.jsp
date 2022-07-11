<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="contens_bd" style="width:650px;">
	<div class="info_area">
		<h3>등록정보</h3>
		<ul>
			<li>
				<span class="title">아이디</span>
				<span class="word">
					<input type="text" id="userId" name="userId" autocomplete="off" class="input_txt w200"  placeholder="아이디를 입력하세요."/>
				</span>
			</li>
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
			<li>
				<span class="title">확인경로</span>
				<span class="word">
					<span class="rd_radiobox_1">
						<label class="radiobox">
							<input type="radio" id="confirmType1" name="confirmType" value="sms" checked="checked"/>
							<span></span>
						</label>
					</span>
					<span class="userText fs14 l26 ml5"><label for="confirmType1">휴대폰</label></span>
					<span class="rd_radiobox_1 ml20">
						<label class="radiobox">
							<input type="radio" id="confirmType2" name="confirmType" value="email" >
							<span></span>
						</label>
					</span>
					<span class="userText fs14 l26 ml5"><label for="confirmType2">이메일</label></span>
				</span>
			</li>
		</ul>
	</div>
	<div class="noteditems_area" style="margin-top:20px">
		<div class="title b">참고사항</div>
		<ul>
			<li>아이디는 ID찾기로 확인하실수 있습니다.</li>
			<li>가입시 등록하신 아이디, 이름, 휴대폰, 이메일을 입력하세요.</li>
			<li>모든 등록정보가 일치하면 요청하신 확인경로로 임시번호를 발급합니다.</li>
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