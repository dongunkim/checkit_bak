<%@ page contentType="text/html; charset=UTF-8"%>
<div class="main_wrap">
	<div class="realtime_area">
		<ul>
			<li>
				<div class="count_re">
					<div class="number" id="main0101Cnt"></div>
					<div class="title">일반작업요청현황</div>
				</div>
				<div class="more_re">
					<div class="icon w1"></div>
					<div class="more" style="cursor:pointer;" onclick="utils.movePage('/admin/wkmn/wkmn0201List.do')">more</div>
				</div>
				<div class="line"></div>
			</li>
			<li>
				<div class="count_re">
					<div class="number" id="main0102Cnt"></div>
					<div class="title">영업지원작업현황</div>
				</div>
				<div class="more_re">
					<div class="icon w5"></div>
					<div class="more" style="cursor:pointer;" onclick="utils.movePage('/admin/wkmn/wkmn0701List.do')">more</div>
				</div>
				<div class="line"></div>
			</li>
			<li>
				<div class="count_re">
					<div class="number" id="main0103Cnt"></div>
					<div class="title">상담요청현황</div>
				</div>
				<div class="more_re">
					<div class="icon w2"></div>
					<div class="more" style="cursor:pointer;" onclick="utils.movePage('/admin/cumn/cumn0501List.do')">more</div>
				</div>
				<div class="line"></div>
			</li>
			<li>
				<div class="count_re">
					<div class="number" id="main0104Cnt"></div>
					<div class="title">방문출입현황</div>
				</div>
				<div class="more_re">
					<div class="icon w3">
					</div>
					<div class="more" style="cursor:pointer;" onclick="utils.movePage('/admin/cumn/cumn0301Reg.do')">more</div>
				</div>
				<div class="line"></div>
			</li>
			<li>
				<div class="count_re">
					<div class="number" id="main0105Cnt"></div>
					<div class="title">장비반출입현황</div>
				</div>
				<div class="more_re">
					<div class="icon w4">
					</div>
					<div class="more" style="cursor:pointer;" onclick="utils.movePage('/admin/cumn/cumn0401Reg.do')">more</div>
				</div>
				<div class="line">
			</div>
			</li>
		</ul>
	</div>
	<div class="board_area">
		<h5>업무 지원현황</h5>
		<div class="table_side_m">
			<h4 class="icon1">고객공지<span><a href="javascript:void(0)" id="main0101MoreBtn">More</a></span></h4>
			<table id="main0101ListTable">
				<colgroup>
					<col style="width:10%;"/>
					<col style="width:20%;"/>
					<col style="width:40%;"/>
					<col style="width:30%;"/>
				</colgroup>
				<thead>
					<tr>
						<th>No.</th>
						<th>공지구분</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr style="height:90px;">
						<td colspan="4">조회된 데이터가 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="table_side_m">
			<h4 class="icon1">내부공지<span><a href="javascript:void(0)" id="main0102MoreBtn">More</a></span></h4>
			<table id="main0102ListTable">
				<colgroup>
					<col style="width:10%;"/>
					<col style="width:20%;"/>
					<col style="width:40%;"/>
					<col style="width:30%;"/>
				</colgroup>
				<thead>
					<tr>
						<th>No.</th>
						<th>공지구분</th>
						<th>제목</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr style="height:90px;">
						<td colspan="4">조회된 데이터가 없습니다.</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="n_d_b_board">
			<div class="boxtable notice_box">
				<dl>
					<dt>
					<span class="title">받은쪽지</span>
					<a href="javascript:void(0)" id="mainMessageBtn"><span class="more">more</span></a>
					</dt>
					<dd id="main0103ListArea">
						<div style="text-align:center;margin-top:51px;">
							<img src="/resources/lib/axj/css/images/dx-progresss-ani-white-2.gif"/>
						</div>
					</dd>
				</dl>
			</div>
			<div class="boxtable down_box">
				<dl>
					<dt>
					<span class="title">장애현황</span>
					<a href="javascript:void(0)" id="main0104MoreBtn"><span class="more">more</span></a>
					</dt>
					<dd id="main0104ListArea">
						<div style="text-align:center;margin-top:51px;">
							<img src="/resources/lib/axj/css/images/dx-progresss-ani-white-2.gif"/>
						</div>
					</dd>
				</dl>
			</div>
			<div class="boxtable boder_box">
				<dl>
					<dt>
					<span class="title">파일관리 자료실</span>
					<a href="javascript:void(0)" id="fileMngMoreBtn"><span class="more">more</span></a>
					</dt>
					<dd id="main0105ListArea">
						<div style="text-align:center;margin-top:51px;">
							<img src="/resources/lib/axj/css/images/dx-progresss-ani-white-2.gif"/>
						</div>
					</dd>
				</dl>
			</div>
		</div>
	</div>
</div>