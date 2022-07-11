<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" href="/resources/lib/jodit/jodit.min.css" />
<script src="/resources/lib/jodit/jodit.min.js"></script>

<div class="contens_bd">
    <h2>신규진단 상세</h2>
   	<div class="info_area" id="boardReg">
        <h3>진단 기본 정보</h3>
        <ul>
            <li>
                <span class="title">제목</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
                <span class="title">상품서비스명</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
                <span class="title">오픈 예정일</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
            	<div>
	                <span class="title">신청자</span>
	                <span class="word">2022-06-07 / 김신청(P000001), 휴대전화:010-0000-0001</span>
                </div>
            </li>            	
            <li>
            	<div>
	                <span class="title">삭제자</span>
	                <span class="word">2022-06-07 / 김신청(P000001), 휴대전화:010-0000-0001</span>
                </div>
            </li>            	
        </ul>
        <h3>진단 대상 서버 정보</h3>
        <ul>
		 	<li style="border-bottom: none;"></li>
		   	<div style="text-align: right;margin-bottom: 10px;">
				<button class="button_sh black" id="addHostBtn">
					<span>서버 추가</span>
				</button>   		
			</div>
		   	<table id="hostGrid"></table>
		   	<div style="text-align: right;margin-top: 10px;">
				<button class="button_sh oreage" id="delHostBtn">
					<span>선택된 서버 삭제</span>
				</button>   		
			</div>
        </ul>
        <h3>진단 대상 세부 정보</h3>
        <ul>
            <li>            	
                <span class="title">진단분류선택</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
            <li>            	
                <span class="title">윤용체제 정보</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
            <li>            	
                <span class="title">계정 정보</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
            <li>            	
                <span class="title">조치 담당자</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
		 	<li style="border-bottom: none;">
			   	<div>
					<button class="button_sh oreage" id="applyBtn" data-write>
						<span>적용</span>
					</button>
					<button class="button_sh black" id="addBtn">
						<span>추가</span>
					</button>   		
				</div>
		 	</li>
		   	<table id="objGrid"></table>
        </ul>
        <h3>진단 접수</h3>
        <ul>
            <li>            	
                <span class="title">진단자</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
		   	<div class="info_button_area">
				<button class="button_info oreage" id="regBtn" data-write>
					<span>진단 접수</span>
				</button>
				<button class="button_info black" id="backBtn">
					<span>목록</span>
				</button>   		
			</div>
        </ul>
        <h3>진단 계획</h3>
        <ul>
            <li>
                <span class="title">진단일정</span>
				<div class="dayinput input-group btnCal_multy">
					<span>
						<input type="text" name="calFrom" id="calFrom" class="input_txt input_day">
					</span>
					<span class="w20">~</span>
					<span>
						<input type="text" name="calTo" id="calTo" class="input_txt input_day">
					</span>
				</div>
            </li>
            <li>
	            <p>▶ 신청자 계획 알림 메시지</p>
            </li>
            <li>            	
                <span class="title">수신</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="mailRegRev" id="bbsTitle" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">참조</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="mailRegRef" id="bbsTitle" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">메시지</span>
                <span class="word">
                	<span id="mailRegDesc" name="mailRegDesc"></span>
                </span>
		 	</li>
            <li>            	
                <span class="title">추가메시지</span>
                <span class="word">
					<span>
						<textarea class="textarea_box" name="mailRegAdd" id="mailRegAdd" style="height:70px;"></textarea>
					</span>
                </span>
		 	</li>
            <li>
                <span class="word">
                	<span class="rd_checkbox_1">
						<label class="check">
							<input type="checkbox" id="csChk" name="csChk"/>
							<span></span>
						</label>
					</span>
					<span class="userText fs14 l26 ml5 fll"><label for="csChk">알림 메시지 발송</label></span>
                </span>
			</li>
            <li>
	            <p>▶ 보안관제 계획 알림 메시지</p>
            </li>
            <li>            	
                <span class="title">수신</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="mailMonRev" id="mailMonRev" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">참조</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="mailMonRef" id="mailMonRef" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">메시지</span>
                <span class="word">
                	<span id="mailMonDesc" name="mailMonDesc"></span>
                </span>
		 	</li>
            <li>            	
                <span class="title">추가메시지</span>
                <span class="word">
					<span>
						<textarea class="textarea_box" name="mailMonAdd" id="mailMonAdd" style="height:70px;"></textarea>
					</span>
                </span>
		 	</li>
            <li>
                <span class="word">
                	<span class="rd_checkbox_1">
						<label class="check">
							<input type="checkbox" id="csChk" name="csChk"/>
							<span></span>
						</label>
					</span>
					<span class="userText fs14 l26 ml5 fll"><label for="csChk">알림 메시지 발송</label></span>
                </span>
			</li>
		   	<div class="info_button_area">
				<button class="button_info oreage" id="regBtn" data-write>
					<span>계획 완료</span>
				</button>
				<button class="button_info black" id="backBtn">
					<span>목록</span>
				</button>   		
			</div>
        </ul>
        <h3>진단 및 이행 점검</h3>
        <ul>
		 	<li style="border-bottom: none;"></li>
		   	<table id="diagObjGrid"></table>
		 	<li style="margin-bottom: 20px;border-bottom: none;">
			   	<div>
					<button class="button_sh grey" id="copyResultBtn">
						<span>최근 진단 결과로 복사</span>
					</button>   		
				</div>
		 	</li>

		 	<div style="text-align:center;margin-bottom:20px;" id="diagHist">
		 		<div style="margin-bottom:10px;">
					<span class="title" >진단 이력</span>
		 		</div>
		   		<table id="diagHistGrid"></table>
			   	<div style="text-align:right;margin-top:10px;">
					<button class="button_sh grey" id="closeHistBtn">
						<span>닫기</span>
					</button>   		
				</div>
		 	</div>

		 	<div>
		 		<div style="text-align:center;">
               		<span class="title">항목별 진단 결과</span>
               	</div>
               	<span >
					<input type="checkbox" id="csChk" name="csChk"/>
				</span>
				<span class=""><label for="csChk">취약 항목만 보기</label></span>
			</div>
		   	<table id="diagResultGrid"></table>

		   	<div class="info_button_area">
				<button class="button_info oreage" id="regBtn" data-write>
					<span>진단 완료</span>
				</button>
				<button class="button_info black" id="backBtn">
					<span>목록</span>
				</button>   		
			</div>
        </ul>
    </div>
	
</div>
