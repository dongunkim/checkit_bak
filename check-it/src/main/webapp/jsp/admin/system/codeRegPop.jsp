<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens">
	<form class="info_area popup_area" id="system0404Form">
        <ul>
            <li>
            	<div>
	                <span class="title">코드 ID</span>
	                <span class="word" id="menuIdNm">
	                	<i class="fll mr20" id="upCodeId"></i>
	                	<input type="text" class="info_textbox w120" name="codeId" id="codeId" maxlength="14" required="required">
	                	<button class="button_sh black fll ml5" id="checkDup">
	                		<span>중복확인</span>
	                	</button>
	                </span>
                </div>
                <div>
                	<span class="title">코드 명</span>
	                <span class="word">
	                	<input type="text" class="info_textbox" name="codeName" id="cname">
	                </span>
                </div>
            </li>
            <li>
                <span class="title">비고</span>
                <span class="word">
					<input type="text" class="info_textbox" name="codeDesc" id="codeDesc" style="width:710px;">
                </span>
            </li> 
        </ul>
    </form>
    <div class="info_button_area">
   		<button class="button_info black" id="closeBtn">
			<span>닫기</span>
		</button>
		<button class="button_info oreage" id="saveBtn">
			<span>저장</span>
		</button>
	</div>
</div>