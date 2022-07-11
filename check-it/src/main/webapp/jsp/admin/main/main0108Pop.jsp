<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="contens_bd" style="width:650px;">
	<div class="info_area" id="userInfoDiv">
		<h3>등록정보</h3>
		<ul>
			<li>
				<span class="title">사용자ID</span>
				<span class="word">${sessionScope.userId}</span>
			</li>
			<li>
				<span class="title">사용자이름</span>
				<span class="word">
					<input type="text" id="userNm" name="userNm" autocomplete="off" class="input_txt w200" placeholder="이름을 입력하세요."/>
				</span>
			</li>
			<li>
				<span class="title">소속</span>
				<span class="word" id="manageCid">
				</span>
			</li>
			<li>
				<span class="title">비밀번호 변경</span>
				<span class="word">
					<input type="password" id="passwd" name="passwd" autocomplete="off" class="input_txt w200"/>
				</span>
			</li>
			<li>
				<span class="title">비밀번호 확인</span>
				<span class="word">
					<input type="password" id="passwdChk" name="passwdChk" autocomplete="off" class="input_txt w200" />
				</span>
			</li>
			<li>
				<span class="title">전화번호</span>
				<span class="word">
					<select class="info_selectbox w100" name="tel1" id="tel1">
						<option value="">-선택-</option>
					</select>
					<input type="text" class="info_textbox w120" style="margin-right:7px;" name="tel2" id="tel2" maxlength="4" data-type="number"/>
					<input type="text" class="info_textbox w120" name="tel3" id="tel3" maxlength="4" data-type="number"/>
				</span>
			</li>
			<li>
				<span class="title">휴대폰번호</span>
				<span class="word">
					<select class="info_selectbox w100" name="hp1" id="hp1">
						<option value="">-선택-</option>
					</select>
					<input type="text" class="info_textbox w120" style="margin-right:7px;" name="hp2" id="hp2" maxlength="4" data-type="number"/>
					<input type="text" class="info_textbox w120" name="hp3" id="hp3" maxlength="4" data-type="number"/>
				</span>
			</li>
			<li>
				<span class="title">이메일</span>
				<span class="word">
					<input type="text" class="info_textbox w100" name="email1" id="email1"/>
					<span class="fll l0">@</span>
					<select class="info_selectbox email w100" name="email2" id="email2">
					</select>
				</span>
			</li>
		</ul>
	</div>
	<div class="info_button_area">
		<button class="button_info black" id="closeBtn">
			<span>취소</span>
		</button>
		<button class="button_info oreage" id="addBtn">
			<span>저장</span>
		</button>
	</div>
</div>