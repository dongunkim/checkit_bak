<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" href="/resources/lib/jodit/jodit.min.css" />
<script src="/resources/lib/jodit/jodit.min.js"></script>

<div class="contens_bd">
    <h2>모의해킹 진단 상세</h2>
   	<div class="info_area" id="boardReg">
        <h3>점검 기본 정보</h3>
        <ul>
            <li>
                <span class="title">점검 년월</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
                <span class="title">점검명</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
                <span class="title">점검 기간</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
            <li>
                <span class="title">점검 목적</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>            	
        </ul>
        <h3>점검 대상</h3>
		<div class="input_area">
		    <div class="w1x2">
			<form id="frm">
				<input type="hidden" name="boardType" id="boardType" value="01">
				<dl>
					<dt>조건검색</dt>
					<dd>
						<div class="twobox">
							<span>
								<select class="select_txt" id="searchGbn" name="searchGbn">
									<option value="">-선택-</option>
									<option value="bbs_title">제목</option>
									<option value="reg_id">작성자</option>
								</select>
							</span>
							<span>
								<input type="text" name="searchText" id="searchText" class="input_txt" placeholder="검색어를 입력해주세요">
							</span>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>날짜</dt>
					<dd>
						<div class="dayinput input-group btnCal_multy">
							<span>
								<input type="text" name="calFrom" id="calFrom" class="input_txt input_day">
							</span>
							<span class="w20">~</span>
							<span>
								<input type="text" name="calTo" id="calTo" class="input_txt input_day">
							</span>
						</div>
					</dd>
				</dl>
			</form>
			</div>
			<!-- 검색 버튼 -->
			<div class="input_button_area">
				<button class="button_sh w110 m1_button_2" id="searchAllBtn"><span>전체리스트</span></button>
				<button class="button_sh w110" id="searchBtn"><span>검색</span></button>
			</div>
			<!-- 검색 버튼 -->
		</div>

        <ul>
		 	<li style="border-bottom: none;"></li>
		   	<div style="text-align: right;margin-bottom: 10px;">
				<button class="button_sh black" id="addHostBtn">
					<span>서버 추가</span>
				</button>   		
				<button class="button_sh oreage" id="delHostBtn">
					<span>선택 삭제</span>
				</button>   		
			</div>
		   	<table id="urlGrid"></table>
        </ul>

        <h3>점검계획 메일발송</h3>
        <ul>
            <li>            	
                <span class="title">점검기간</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">제목</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">메일본문</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
		 	</li>
            <li>            	
                <span class="title">참조</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
		 	</li>
        </ul>
        <ul>
		 	<div>
		 		<div style="text-align:center;margin-top:20px;">
               		<span class="title">메일발송 대상</span>
               	</div>
               	<div style="text-align:right;">
					<button class="button_sh black" id="addHostBtn">
						<span>csc에 발송</span>
					</button>   		
					<button class="button_sh black" id="delHostBtn">
						<span>발송</span>
					</button>   		
					<button class="button_sh black" id="delHostBtn">
						<span>refresh</span>
					</button>   		
               	</div>
			</div>
		   	<table id="mailUrlGrid"></table>

		   	<div class="info_button_area">
				<button class="button_info oreage" id="regBtn" data-write>
					<span>진단 완료</span>
				</button>
				<button class="button_info black" id="backBtn">
					<span>목록</span>
				</button>   		
			</div>
        </ul>

        <h3>점검자 지정</h3>
		<div class="input_area">
		    <div class="w1x2">
			<form id="frm">
				<input type="hidden" name="boardType" id="boardType" value="01">
				<dl>
					<dt>조건검색</dt>
					<dd>
						<div class="twobox">
							<span>
								<select class="select_txt" id="searchGbn" name="searchGbn">
									<option value="">-선택-</option>
									<option value="bbs_title">제목</option>
									<option value="reg_id">작성자</option>
								</select>
							</span>
							<span>
								<input type="text" name="searchText" id="searchText" class="input_txt" placeholder="검색어를 입력해주세요">
							</span>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>날짜</dt>
					<dd>
						<div class="dayinput input-group btnCal_multy">
							<span>
								<input type="text" name="calFrom" id="calFrom" class="input_txt input_day">
							</span>
							<span class="w20">~</span>
							<span>
								<input type="text" name="calTo" id="calTo" class="input_txt input_day">
							</span>
						</div>
					</dd>
				</dl>
			</form>
			</div>
			<!-- 검색 버튼 -->
			<div class="input_button_area">
				<button class="button_sh w110 m1_button_2" id="searchAllBtn"><span>전체리스트</span></button>
				<button class="button_sh w110" id="searchBtn"><span>검색</span></button>
			</div>
			<!-- 검색 버튼 -->
		</div>
        <ul>
		 	<li style="border-bottom: none;"></li>
		   	<div style="text-align: right;margin-bottom: 10px;">
				<button class="button_sh black" id="addHostBtn">
					<span>서버 추가</span>
				</button>   		
				<button class="button_sh oreage" id="delHostBtn">
					<span>선택 삭제</span>
				</button>   		
			</div>
		   	<table id="urlGrid"></table>
        </ul>


        <h3>점검 결과</h3>
		<div class="input_area">
		    <div class="w1x2">
			<form id="frm">
				<input type="hidden" name="boardType" id="boardType" value="01">
				<dl>
					<dt>조건검색</dt>
					<dd>
						<div class="twobox">
							<span>
								<select class="select_txt" id="searchGbn" name="searchGbn">
									<option value="">-선택-</option>
									<option value="bbs_title">제목</option>
									<option value="reg_id">작성자</option>
								</select>
							</span>
							<span>
								<input type="text" name="searchText" id="searchText" class="input_txt" placeholder="검색어를 입력해주세요">
							</span>
						</div>
					</dd>
				</dl>
				<dl>
					<dt>날짜</dt>
					<dd>
						<div class="dayinput input-group btnCal_multy">
							<span>
								<input type="text" name="calFrom" id="calFrom" class="input_txt input_day">
							</span>
							<span class="w20">~</span>
							<span>
								<input type="text" name="calTo" id="calTo" class="input_txt input_day">
							</span>
						</div>
					</dd>
				</dl>
			</form>
			</div>
			<!-- 검색 버튼 -->
			<div class="input_button_area">
				<button class="button_sh w110 m1_button_2" id="searchAllBtn"><span>전체리스트</span></button>
				<button class="button_sh w110" id="searchBtn"><span>검색</span></button>
			</div>
			<!-- 검색 버튼 -->
		</div>
        <ul>
		 	<li style="border-bottom: none;"></li>
		   	<div style="text-align: right;margin-bottom: 10px;">
				<button class="button_sh black" id="addHostBtn">
					<span>서버 추가</span>
				</button>   		
				<button class="button_sh oreage" id="delHostBtn">
					<span>선택 삭제</span>
				</button>   		
			</div>
		   	<table id="urlGrid"></table>

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
        </ul>

        <h3>점검 결과 승인</h3>
        <ul>
            <li>            	
                <span class="title">진단자</span>
                <span class="word">
                	<select class="info_selectbox" name="bbsGb" id="bbsGb">
						<option value="">-선택-</option>
					</select>
                </span>            	
		 	</li>
		   	<table id="urlGrid"></table>
        </ul>
    </div>
</div>
