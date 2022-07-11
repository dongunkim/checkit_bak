<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens" id="testDiv">
	<div class="info_area popup_area">
        <ul>
            <li>
            	<div>
	                <span class="title">프로그램ID</span>
	                <span class="word" id="pid">
	                <input type="hidden" name="pidNm" id="pidNm">
	                </span>
                </div>
            	<div>
	                <span class="title">메뉴ID</span>
	                <span class="word" id="menuIdNm"></span>
	                <input type="hidden" name="menuId" id="menuId">
                </div>              
            </li>
            <li>
            	<div>
	                <span class="title">프로그램명</span>
	                <span class="word">
	                	<input type="text" class="info_textbox w190" name="pname" id="pname">
	                </span>
                </div>
                <div>
	                <span class="title">서블릿패스</span>
	                <span class="word" >
						<input type="text" class="info_textbox w190" name="servletPath" id="servletPath">
	                </span>
                </div>
            </li>
            <!-- <li>
            	 <div>
	                <span class="title">프로그램명</span>
	                <span class="word">
	                	<input type="text" class="info_textbox w190" name="prgName" id="prgName">
	                </span>
                </div>
                <div>
					<span class="title">구분</span>
					<span class="word" id="sysTypeNm"></span>
					<input type="hidden" name="sysType" id="sysType">
				</div>
            </li> -->
            <li>
            	<div>
	                <span class="title">메소드</span>
	                <span class="word">
				        <span class="rd_radiobox_1 mt8">
				            <label class="radiobox">
				                <input type="radio" name="readWriteWR" id="readWriteWR" value="R"/>
				                <span></span>
				            </label>
				        </span>
				        <span class="userText fs14 l40 ml5">읽기</span>
				        <span class="rd_radiobox_1 mt8">
				            <label class="radiobox">
				                <input type="radio" name="readWriteWR" id="readWriteWR" value="W"/>
				                <span></span>
				            </label>
				        </span>
				        <span class="userText fs14 l40 ml5">쓰기</span>
	                </span>
                </div>
                <div>
	                <span class="title">시스템 타입</span>
	                <span class="word" id="sysTypeNm"></span>
	                <input type="hidden" name="sysType" id="sysType">
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