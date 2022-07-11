<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
    <h2>고객 공지 등록</h2>
   	<div class="info_area" id="bord0303Reg">
        <h3>공지 내용</h3>
        <ul>
            <li>            	
                <span class="title">구분</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
            <li>
                <span class="title">제목</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="bbsTitle" id="bbsTitle" style="width:100%">
                </span>
            </li>
            <li>
            	<span class="title">고객공지여부</span>
                <span class="word">
                	<span class="rd_checkbox_1">
						<label class="check">
							<input type="checkbox" id="csChk" name="csChk"/>
							<span></span>
						</label>
					</span>
					<span class="userText fs14 l26 ml5 fll"><label for="csChk">특정고객에게만 캘린더 공지를 하실경우 체크해주세요.</label></span>
                </span>
			</li>
            <li id="csSelectArea" style="display:none;">
				<span class="title">고객선택</span>
				<span class="word">
					<div id="csNmRecvs" name="csNmRecvs" style="height:80px;border: 1px solid #ccc;resize:none;font-size: 15px;color: #353535;padding-left: 10px;overflow-y: scroll;">
							
					</div>
					<!--  
					<div><input type="text" id="nameSearchBox" style="height:31px;background-color:transparent;border:0 solid black;"></div>
					<span>
						<textarea class="textarea_box" name="csNmRecvs" id="csNmRecvs" readonly="readonly" style="width:90%;height:70px;"></textarea>
					</span>
					-->
					<span>
						<button class="button_sm_g gray" id="common0105Btn" data-write>
							<span>고객불러오기</span>
						</button>
					</span>
				</span>
			</li>
			<li id="gongjiDdArea" style="display:none;">
				<span class="title">공지일</span>
				<span class="word">
					<input type="text" name="gongjiDd" id="gongjiDd" class="input_txt w165 input_day btnCal_day">
				</span>
			</li>
             <li>
                <span class="title">첨부파일</span>
                <span class="word">
                	<div id="fileupload" data-file-gbn="bbs" style="width:100%"></div>
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
			<span>목록</span>
		</button>   		
		<button class="button_info oreage" id="regBtn">
			<span>저장</span>
		</button>
	</div>
	
</div>
