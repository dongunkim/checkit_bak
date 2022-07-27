<%@ page contentType="text/html; charset=UTF-8"%>

<div class="contens_bd">
    <h2>게시판 목록</h2>
    <!---기본 가로 제목영역 80px-->
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
								<option value="board_title">제목</option>
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
			<button class="button_sh w110 m1_button_3" id="searchBtn" style="float:right"><span>검색</span></button>
		</div>
		<!-- 검색 버튼 -->
	</div>
	<!-- 검색 하단 버튼 -->
	<div class="list_button_area" id="input_area2">
		<div class="pagecount l30">전체<em>0</em></div>
		<div class="flr">
			<button class="button_sh w110 m1_button_3 gray" id="excelDownBtn"><span>XLS저장</span></button>
			<button class="button_sh w110 oreage" id="regBtn" data-write><span>등록</span></button>
		</div>
	</div>
    <div class="border_box">
	   	<table id="jqGrid"></table>
	   	<div id="jqGridPager"></div>
    </div>
</div>
