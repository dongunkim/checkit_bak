<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
    <h2>내부 공지 수정</h2>
   	<div class="info_area" id="bord0204Edit">
        <h3>상세 내용</h3>
        <ul>
            <li>            	
                <span class="title">구분</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="bbsGb" id="bbsGb" data-code="" disabled="disabled" style="width:100%">
                </span>            	
		 	</li>
            <li>
                <span class="title">제목</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="bbsTitle" id="bbsTitle" style="width:100%">
                </span>
            </li>
             <li>
                <span class="title">첨부파일</span>
                <span class="word">
                	<div id="bord0204Fileupload" data-file-gbn="bbs" style="width:100%"></div>
                </span>
            </li>
            <li>
                <span class="title">내용</span>
                <span class="word">
					<div id="bbsDesc"></div>
					<div style="height:10px;"></div>
					<div class="input_area">
						<div class="w1x1">
							<dl>
								<dt>폼적용</dt>
								<dd>
								<div class="radiochack_dd w80">
									<span class="rd_radiobox_1 mt8">
									<label class="radiobox">
									<input type="radio" name="bordForm" id="bordFormEmpty" checked="checked"/>
									<span></span>
									</label>
									</span>
									<span class="userText fs14 l40 ml5">없음</span>
								</div>
								<div class="radiochack_dd w80">
									<span class="rd_radiobox_1 mt8">
									<label class="radiobox">
									<input type="radio" name="bordForm" id="bordFormNoBasic"/>
									<span></span>
									</label>
									</span>
									<span class="userText fs14 l40 ml5">적용</span>
								</div>
								</dd>
							</dl>
						</div>
					</div>
                </span>
            </li>
        </ul>
    </div>
   	<div class="info_button_area">
		<button class="button_info black" id="backBtn">
			<span>이전</span>
		</button>   		
		<button class="button_info oreage" id="regBtn" data-write>
			<span>저장</span>
		</button>
	</div>	
</div>