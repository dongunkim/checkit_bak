<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd" style="width:650px;">
	<div class="info_area">
		<h3>비밀번호 재확인</h3>
		<ul>
			<li>
				<span class="title">사용자ID</span>
				<span class="word" id="userId">${sessionScope.userId}</span>
			</li>
			<li>
				<span class="title">비밀번호</span>
				<span class="word">
					<input type="password" id="passwd" name="passwd" autocomplete="off" class="input_txt w200"/>
				</span>
			</li>
		</ul>
	</div>
	<div class="noteditems_area" style="margin-top:20px">
		<div class="title b">참고사항</div>
		<ul>
			<li>본인확인을 위해 한번 더 비밀번호를 입력해 주세요.</li>
			<li>비밀번호는 타인에게 노출되지 않도록 주의하세요. </li>
		</ul>
	</div>
	<div class="info_button_area">
		<button class="button_info black" id="closeBtn">
			<span>취소</span>
		</button>
		<button class="button_info oreage" id="checkBtn">
			<span>확인</span>
		</button>
	</div>
</div>