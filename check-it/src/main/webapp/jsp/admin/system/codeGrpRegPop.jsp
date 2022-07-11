<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens">
	<form class="info_area popup_area" id="system0402Form">
        <ul>
            <li>
            	<div>
	                <span class="title">코드 그룹 ID</span>
	                <span class="word" id="menuIdNm">
	                	<input type="text" class="info_textbox w100p" name="codeGrpId" id="codeGrpId" maxlength="4" required="required">
	                </span>
                </div>
                <div>
	                <span class="title">코드 그룹 명</span>
					<span class="word" id="sysTypeNm">
						<input type="text" class="info_textbox w100p" name="codeGrpName" id="codeGrpName" required="required">
					</span>
				</div>
            </li>
            <li>
                <div>
	                <span class="title">비고</span>
	                <span class="word">
	                	<input type="text" class="info_textbox w100p" name="codeGrpDesc" id="codeGrpDesc" maxlength="1495">
	                </span>
                </div>
            </li>
            <li>
            	<div>
	                <span class="title">사용여부</span>
	                <span class="word">
				        <span class="rd_radiobox_1 mt8">
				            <label class="radiobox">
				                <input type="radio" name="useYn" value="Y" checked="checked"/>
				                <span></span>
				            </label>
				        </span>
				        <span class="userText fs14 l40 ml5">예</span>
				        <span class="rd_radiobox_1 mt8">
				            <label class="radiobox">
				                <input type="radio" name="useYn" value="N"/>
				                <span></span>
				            </label>
				        </span>
				        <span class="userText fs14 l40 ml5">아니오</span>
	                </span>
                </div>
            </li> 
        </ul>
    </form>
    <div class="info_button_area">
   		<button class="button_info black" id="closeBtn">
			<span>닫기</span>
		</button>
		<button class="button_info oreage" id="saveBtn" data-write>
			<span>저장</span>
		</button>
	</div>
</div>