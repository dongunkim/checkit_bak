<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens">
	<form class="info_area popup_area" id="sysm0402Form">
        <ul>
            <li>
                <span class="title">권한ID</span>
                <span class="word" id="rid">
                </span>
            </li>
            <li>
                <span class="title">권한명</span>
				<span class="word" id="sysTypeNm">
					<input type="text" class="info_textbox" name="rname" id="rname" required="required" title="권한명" maxlength="370">
				</span>
               
            </li>
            <li>
           		<span class="title">설명</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="description" id="description" required="required" title="설명" maxlength="1495">
                </span>
            </li> 
           	<li>
                <span class="title">사용여부</span>
                <span class="word">
			        <span class="rd_radiobox_1 mt8">
			            <label class="radiobox">
			                <input type="radio" name="usedYn" value="Y"/>
			                <span></span>
			            </label>
			        </span>
			        <span class="userText fs14 l40 ml5">사용</span>
			        <span class="rd_radiobox_1 mt8">
			            <label class="radiobox">
			                <input type="radio" name="usedYn" value="N"/>
			                <span></span>
			            </label>
			        </span>
			        <span class="userText fs14 l40 ml5">사용안함</span>
                </span>
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