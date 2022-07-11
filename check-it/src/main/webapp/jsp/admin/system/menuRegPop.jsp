<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens" id="testDiv">
	<div class="info_area popup_area">
        <ul>
            <li>
            	<div>
	                <span class="title">메뉴ID</span>
	                <span class="word" id="menuIdNm"></span>
	                <input type="hidden" name="menuId" id="menuId">
	                <input type="hidden" name="upMenuId" id="upMenuId">
                </div>
                <div>
	                <span class="title">시스템 타입</span>
	                <span class="word" id="sysTypeNm"></span>
	                <input type="hidden" name="sysType" id="sysType">
                </div>
            </li>
            <li>
            	<div>
	                <span class="title">메뉴명</span>
	                <span class="word">
	                	<input type="text" class="info_textbox w190" name="menuName" id="menuName">
	                </span>
                </div>
                <div>
	                <span class="title">메뉴 영문명</span>
	                <span class="word">
	                	<input type="text" class="info_textbox w190" name="menuEnName" id="menuEnName">
	                </span>
                </div>
            </li>
            <li>
           		<div>
	                <span class="title">메뉴 URL</span>
	                <span class="word">
						<input type="text" class="info_textbox w190" name="menuUrl" id="menuUrl">
	                </span>
                </div>
            	<div>
	                <span class="title">보이는 상태</span>
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
    </div>
    <div class="info_button_area">
   		<button class="button_info black" id="closeBtn">
			<span>닫기</span>
		</button>
		<button class="button_info oreage" id="saveBtn">
			<span>저장</span>
		</button>
	</div>
</div>