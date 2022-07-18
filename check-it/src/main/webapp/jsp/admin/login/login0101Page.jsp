<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="login_logo"></div>
<div class="login_wrap">
	<div class="login_box">
		<div class="box_2">
			<dl>
				<dt>업무지원시스템</dt>
				<dd>
					<input type="hidden" name="csIdcCheck" value="1" />
					<div>
						<input type="text" class="input_txt" id="userId" name="userId" placeholder="아이디를 입력하세요"/>
					</div>
					<div>
						<input type="password" class="input_txt" id="passwd" name="passwd" placeholder="비밀번호를 입력하세요"/>
					</div>					
					<div>
						<button class="" id="loginBtn">로그인</button>
					</div>
				</dd>
			</dl>
			<div class="box_3">
				<div>
					<button id="idSearchBtn">아이디찾기</button>
				</div>
				<div>
					<button id="pwdSearchBtn">비밀번호찾기</button>
				</div>
				<div>
					<button id="userAddReqBtn">사용자등록요청</button>
				</div>
			</div>
		</div>
		
	</div>
</div>