<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
    <h2>공지/게시판</h2>
    <!---기본 가로 제목영역 80px-->
	<div class="input_area">
	    <div class="w1x2">
			<dl>
				<dt>공지구분</dt>
				<dd>
					<select class="select_txt" id="searchBbsGb" name="searchBbsGb">
						<option value="">-선택-</option>
					</select>
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
		</div>
		<!-- 검색 버튼 -->
		<div class="input_button_area">
			<button class="button_sh w110 m1_button_2" id="searchAllBtn"><span>전체리스트</span></button>
			<button class="button_sh w110"><span>검색</span></button>
		</div>
		<!-- 검색 버튼 -->
	</div>
	<!-- 검색 하단 버튼 -->
	<!---기본 가로 제목영역 100px-->
	<div class="list_button_area" id="input_area2">
		<div class="pagecount l30">전체<em>0</em></div>
		<div class="flr">
			<button class="button_sh w110 oreage" id="regBtn" data-write>
				<span>등록</span>
			</button>
		</div>
	</div>
    <div class="border_box">
        <div class="border_list">
            <table id="bord0301ListTable">
                <colgroup>
                    <col width="10%">
                    <col width="">
                    <col width="60%">
                    <col width="">
                </colgroup>
                <tr>
                    <td colspan="4" align="center"><span>데이터가 없습니다</span></td>
                </tr>
            </table>
        </div>
    </div>
</div>
