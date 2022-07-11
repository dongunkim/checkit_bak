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
					<div id="captchaDiv" style="display: none;">
						<label for="captcha" style="display:block; color: #fff;">자동 로그인 방지</label>
						<div>
							<div>
								<img id="captchaImg" title="캡차 이미지" src="/common/captchaImg.do" alt="캡차 이미지" />
								<div id="captchaAudio" style="display:none;"></div>
								<div class="input_button_area" style="width: 30%">
									<button class="button_sh w80 m1_button_3" id="refreshBtn" style="font-size: 1em;height: 30px;background-color: black;">
										새로고침
									</button>
									<!-- 
									<buttton class="button_sh" id="audioBtn">
										<span style="display: inline-block;width: 100%;font-size: 1.0em;color: #fff;text-align: center;line-height: 33px;">
											음성재생
										</span>
									</buttton>
									 -->
								</div>
							</div>
							<div style="margin-top: 10px">
								<input type="text" name="answer" id="answer" class="input_txt" placeholder="자동 로그인 방지번호를 입력하세요"/>
							</div>
						</div>	
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