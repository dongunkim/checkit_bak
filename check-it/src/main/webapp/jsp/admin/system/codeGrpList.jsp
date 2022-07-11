<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
	<div style="height:1100px;">
		<h2>공통코드 관리</h2>
		<!---기본 가로 제목영역 80px-->
		<div class="allcode_setting">
			<div>
				<div class="input_area">
					<!-- 한줄에 입력박스 2개-->
					<div class="w1x2">
						<dl class="w100p">
							<dt>설명 검색</dt>
							<dd>
								<input type="text" id="searchKeyword" name="searchKeyword" class="input_txt" placeholder="검색어를 입력해주세요">
							</dd>
						</dl>
						
					</div>
					
					<div class="input_button_area" id="input_area2">
						<div class="flr">
							<button class="button_sh w110 m1_button_2" id="searchAllBtn">
								<span>전체리스트</span>
							</button>
							<button class="button_sh w110 m1_button_2" id="srcBtn">
								<span>검색</span>
							</button>
						</div>
					</div>
					
				</div>
				<div class="list_button_area">
			    	<div class="flr">
				        <button class="button_sh w110 oreage" id="regBtn" data-write>
				        	<span>등록</span>
				        </button>
			        </div>
			    </div>
				
				<div class="border_box">
					<div class="border_list" style="overflow:auto; overflow-x:hidden; height:600px">
						<table id="system0401ListTable">
							<colgroup>
							<col width="10%">
							<col width="">
							<col width="">
							<col width="20%">
							</colgroup>
							<thead>
							
							</thead>
							<tbody>
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div>
				<div class="info_area" id="" style="margin-top:-63px;">
				   	<h3>코드정보</h3>
			        <ul>        
			            <li>
			                <span class="title">코드 그룹 ID</span>
			                <span class="word" id="codeGrpId"></span>
			            </li>
			            <li>
			                <span class="title">코드 그룹 명</span>
			                <span class="word" id="codeGrpName"></span>
			            </li>
			            <li>
			                <span class="title">코드 그룹 설명</span>
			                <span class="word" id="codeGrpDesc"></span>
			            </li>
			            <li>
			                <span class="title">사용여부</span>
			                <span class="word" id="useYn"></span>
			            </li>
			        </ul>
				</div>
				<div class="info_button_area" style="display: none;">
			        <button class="button_info oreage" id="savBtn" data-write>
			       		<span>저장</span>
			       	</button>
			        <button class="button_info red" id="defBtn" data-write>
			        	<span>초기화</span>
			        </button>
			       	<button class="button_info gray" id="delBtn" data-write>
			        	<span>삭제</span>
			        </button>
      				<button class="button_info black" id="addBtn" data-write>
			        	<span>추가</span>
			        </button>
			    </div>
			    <div class="infoeq_area" style="display: none;">
					<ul>

					</ul>
				</div>
			</div>
		</div>
	</div>
</div>