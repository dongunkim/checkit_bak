<%@ page contentType="text/html; charset=UTF-8"%>
<style>
<!--
.input_area > .w1x2 dl{float:left; padding:0 10px; width:calc(100% / 2 - 5px); border-right:1px solid #ccc; margin:2px 0; height:35px;}
-->
</style>
<div class="contens_bd" style="width:70%">
	<div class="allcode_setting">
		<div style="width: calc(100% /2 + 80px);">
			<h2>사용자관리</h2>
			<!-- 검색 영역 -->
			<div class="input_area">
				<!-- 한줄에 입력박스 2개-->
				<div class="w1x2">
					<dl>
						<dt>소속</dt>
						<dd>
							<select class="select_txt" id="srchFormationCd1" name="srchFormationCd1">
								<option value="">-선택-</option>
							</select>
						</dd>
					</dl>
					<dl>
						<dt>소속상세</dt>
						<dd>
							<select class="select_txt" id="srchFormationCdSelect" name="srchFormationCdSelect" style="width: 49%;">
								<option value="">-선택-</option>
							</select>
							<select class="select_txt" id="srchFormationCd3" name="srchFormationCd3" style="width: 49%;float: right;">
								<option value="">-선택-</option>
							</select>
							<input type="hidden" name="srchFormationCd2" id="srchFormationCd2" class="input_txt">
						</dd>
					</dl>
					<dl>
						<dt>사용자 ID</dt>
						<dd>
							<input type="text" name="searchUserId" id="searchUserId" class="input_txt" placeholder="사용자ID를 입력해주세요">
						</dd>
					</dl>
					<dl>
						<dt>상태별 검색</dt>
						<dd>
							<select class="select_txt" id="searchStatus" name="searchStatus">
								<option value="">-선택-</option>
								<option value="A">사용</option>
								<option value="O">요청</option>
								<option value="L">잠금</option>
							</select>							
						</dd>
					</dl>
					<dl>
						<dt>사용자명</dt>
						<dd>
							<input type="text" name="searchUserNm" id="searchUserNm" class="input_txt" placeholder="사용자명을 입력해주세요">
						</dd>
					</dl>
					<dl>
						<dt>권한</dt>
						<dd>
							<select class="select_txt" id="searchRid" name="searchRid">
								<option value="">-선택-</option>
							</select>
						</dd>
					</dl>
				</div>
				<!-- 한줄에 입력박스 2개-->
				<div class="input_button_area">
					<button class="button_sh w110" id="searchAllBtn"><span>전체리스트</span></button>
					<button class="button_sh w110"><span>검색</span></button>
				</div>
			</div>
			
			<div class="list_button_area">
				<div class="input_button_area" style="margin:5px 0 10px 0;">
					<button class="button_sh red w110" id="maskReleaseBtn"><span>Masking 해제</span></button>
				</div>
			</div>
			
			<div class="border_box">
				<div class="border_list">
					<table id="sysm0101ListTable">
						<colgroup>
							<col width="10%">
							<col width="">
							<col width="60%">
							<col width="">
						</colgroup>
						<tbody>

						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div id="userInfoArea" style="width: calc(100% /2 - 160px);">
			<div class="info_area">
				<h3>사용자 정보</h3>
				<ul>
					<li>
						<span class="title">사용자 ID</span>
						<span class="word" id="userId" name="userId"></span>
					</li>
					<li>
						<span class="title">사용자 이름</span>
						<span class="word">
							<input type="text" class="info_textbox" id="userNm" name="userNm" maxlength="100">
						</span>
					</li>
					<li>
						<span class="title">소속</span>
						<span class="word">
							<div>
								<select class="info_selectbox w120" name="manageCid" id="manageCid">
								</select>
							</div>
							<div>
								<select style="display:none;" class="info_selectbox w120" name="formationCd" id="formationCd">
								</select>
							</div>
							<div>
								<select style="display:none;" class="info_selectbox w120" name="formationCd_2" id="formationCd_2">
								</select>
							</div>
							<!-- 소속변경명 -->
							<input type="hidden" name="manageDesc" id="manageDesc" class="input_txt">
						</span>
					</li>
					<li>
						<span class="title">전화번호</span>
						<span class="word">
							<input type="text" class="info_textbox" id="telNo" name="telNo" maxlength="40">
						</span>
					</li>
					<li>
						<span class="title">휴대폰번호</span>
						<span class="word">
							<input type="text" class="info_textbox" id="hpNo" name="hpNo" maxlength="40">
						</span>
					</li>
					<li>
						<span class="title">E-MAIL</span>
						<span class="word">
							<input type="text" class="info_textbox" id="email" name="email" maxlength="250">
						</span>
					</li>
					<li>
						<span class="title">사용여부</span>
						<span class="word">
							<span class="rd_radiobox_1 pt3">
								<label class="radiobox">
									<input type="radio" name="status" id="status1" value="A" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="status1">사용</label></span>
							<span class="rd_radiobox_1 ml20 pt3">
								<label class="radiobox">
									<input type="radio" name="status" id="status2" value="O" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="status2">요청</label></span>
							<span class="rd_radiobox_1 ml20 pt3">
								<label class="radiobox">
									<input type="radio" name="status" id="status3" value="L" />
									<span></span>
								</label>
								<!-- 사용여부변경 -->
								<input type="hidden" name="hstDesc" id="hstDesc" class="input_txt">
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="status3">잠금</label></span>
							<input type="hidden" name="firstStatus" id="firstStatus" class="input_txt">
						</span>
					</li>
					<li>
						<span class="title">최종 접속IP</span>
						<span class="word" id="lastIp"></span>
					</li>
					<li>
						<span class="title">최종 접속시간</span>
						<span class="word" id="lastDt"></span>
					</li>
					<li>
						<span class="title">권한</span>
						<span class="word">
							<span id="roleViewArea"></span>
							<label id="rolePopBtn">[권한지정]</label>
						</span>
						<!-- 권한변경명 -->
						<input type="hidden" name="roleViewAreaDesc" id="roleViewAreaDesc" class="input_txt">
																		
					</li>
					<li>
						<span class="title">AM권한</span>
						<span class="word">
							<span class="rd_checkbox_1">
								<label class="check">
									<input type="checkbox" id="chkAmYn" name="chkAmYn" value="Y" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l26 ml5 fll"><label for="chkAmYn">(체크시 AM의 역할을 담당합니다.)</label></span>
						</span>
					</li>
					<li>
						<span class="title">신청 사유</span>
						<span class="word">
							<textarea class="textarea_box" name="applyDesc" id="applyDesc"></textarea>
						</span>
					</li>
					<li>
						<span class="title">근태대상</span>
						<span class="word">
							<span class="rd_radiobox_1 pt3">
								<label class="radiobox">
									<input type="radio" name="dutyYn" id="dutyY" value="Y" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="">예</label></span>
							<span class="rd_radiobox_1 ml20 pt3">
								<label class="radiobox">
									<input type="radio" name="dutyYn" id="dutyN" value="N" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="">아니오</label></span>
							<span>('예' 인 사용자는 근무일정표에 포함됩니다.)</span>
						</span>
					</li>
					<li>
						<span class="title">전화작업대상</span>
						<span class="word">
							<span class="rd_radiobox_1 pt3">
								<label class="radiobox">
									<input type="radio" name="telWorkYn" id="telWorkY" value="Y" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="">예</label></span>
							<span class="rd_radiobox_1 ml20 pt3">
								<label class="radiobox">
									<input type="radio" name="telWorkYn" id="telWorkN" value="N" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l30 ml5 fll"><label for="">아니오</label></span>
							<span>('예' 인 사용자는 전화상담의 작업예정자로 나옵니다.)</span>
						</span>
					</li>
					<li>
						<span class="title">공지 매체</span>
						<span class="word">
							<span class="rd_checkbox_1">
								<label class="check">
									<input type="checkbox" id="chkEmailYn" name="chkEmailYn" value="Y" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l26 ml5 fll">E-MAIL</span>
							<span class="rd_checkbox_1">
								<label class="check">
									<input type="checkbox" id="chkSmsYn" name="chkSmsYn" value="Y" />
									<span></span>
								</label>
							</span>
							<span class="userText fs14 l26 ml5 fll">SMS</span>
						</span>
					</li>
					<li>
						<span class="title">소속사</span>
						<span class="word">
							<input type="text" class="info_textbox" id="companyNm" name="companyNm" maxlength="100">
						</span>
					</li>
					<li>
						<span class="title">직책</span>
						<span class="word">
							<select class="info_selectbox" name="position" id="position">
								<option value="">-선택-</option>
							</select>
						</span>
					</li>
					<li>
						<span class="title">사번</span>
						<span class="word">
							<input type="text" class="info_textbox" id="sabun" name="sabun" maxlength="30">
						</span>
					</li>
					<li>
						<span class="title">정렬 순번</span>
						<span class="word">
							<input type="text" class="info_textbox" id="sortSeq" name="sortSeq">
						</span>
					</li>
				</ul>
			</div>
			<div class="info_button_area">
				<button class="button_info gray" id="delBtn" data-write>
					<span>삭제</span>
				</button>
				<button class="button_info oreage" id="regBtn" data-write>
					<span>저장</span>
				</button>
			</div>
		</div>
	</div>
</div>