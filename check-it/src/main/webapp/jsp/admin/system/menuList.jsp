<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
	<div>
		<h2>메뉴관리</h2>
		<!---기본 가로 제목영역 80px-->
		<div style="flex:auto;">
			<div class="menasd">
				<div class="tapsub_list">
					<ul>
						<li class="tap on" id="tab1"><a href="javascript:void(0)">업무지원시스템</a></li>
						<li class="tap" id="tab2"><a href="javascript:void(0)">고객지원시스템</a></li>
					</ul>
				</div>
			</div>
			<div class="treebox_wrap">
				<div id="tree_target"></div>
				<div class="border_box info_area tree_infobox ">
					<div class="list_button_area">
						<div class="flr">
							<button class="button_sh w110 oreage" id="addMenuBtn" style="display : none;" data-write>
								<span>추가</span>
							</button>
							<button class="button_sh w110 blue" id="updMenuBtn" style="display : none;" data-write>
								<span>수정</span>
							</button>
							<button class="button_sh w110 red" id="remMenuBtn" style="display : none;" data-write>
								<span>삭제</span>
							</button>
						</div>
					</div>
					<h3>메뉴 정보</h3>
					<ul id="detailArea">
						<li>
							<div>
								<span class="title">메뉴 ID</span>
								<span class="word" id="menuId"></span>
							</div>
							<div>
								<span class="title">시스템 타입</span>
								<span class="word" id="sysTypeNm"></span>
							</div>
						</li>
						<li>
							<div>
								<span class="title">메뉴명</span>
								<span class="word" id="menuName"></span>
							</div>
							<div>
								<span class="title">메뉴 영문명</span>
								<span class="word" id="menuEnName"></span>
							</div>
						</li>
						<li>
							<span class="title">정렬 순서</span>
							<span class="word">
								<input type="text" class="info_textbox" name="sortSeq" id="sortSeq" data-type="number">
								<button class="button_sh w110 black" style="float:left; display:none;" id="sortChangeBtn" data-write>
									<span>변경</span>
								</button>
							</span>
						</li>
						<li>
							<div>
								<span class="title">메뉴 URL</span>
								<span class="word" id="menuUrl"></span>
							</div>
							<div>
								<span class="title">보이는 상태</span>
								<span class="word" id="useYnNm"></span>
							</div>
						</li>
					</ul>
					<div class="list_button_area">
						<div class="flr">
							<button class="button_sh w110 black" id="pidSaveBtn" data-write>
								<span>프로그램 추가</span>
							</button>
							<button class="button_sh w220 black" data-write>
								<span>템프 프로그램 선택 추가</span>
							</button>
						</div>
					</div>	
					<div id="othersListBox" style="float: right; padding-top: 10px;">
						<div class="border_list">
							<table id="sysm0201ListTable">
								<colgroup id="transCol">

								</colgroup>
								<thead>
									<tr id="transTh">
										<th>PID</th>
										<th>프로그램명</th>
										<th colspan="2">서블릿패스</th>
										<th colspan="2">메소드</th>
										<th>변경</th>
									</tr>
								</thead>
								<tbody id="addRowArea">
									<tr>
										<td colspan="7" align="center">조회된 목록이 없습니다.</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>