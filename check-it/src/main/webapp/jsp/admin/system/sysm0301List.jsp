<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd">
	<div style="height:800px;">
		<h2>권한 관리</h2>
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
		<div class="allcode_setting">
			<div>
				<div class="list_button_area">
			    	<div class="flr">
				        <button class="button_sh w110 oreage" id="regBtn" data-write>
				        	<span>등록</span>
				        </button>
			        </div>
			    </div>
				<div class="border_box">
					<div class="border_list" style="overflow:auto; overflow-x:hidden; height:800px">
						<table id="sysm0301ListTable">
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
				<div class="info_area">
				   	<h3>권한 정보</h3>
			        <ul>        
			            <li>
			                <span class="title">권한ID</span>
			                <span class="word" id="rid"></span>
			            </li>
			            <li>
			                <span class="title">권한명</span>
			                <span class="word" id="rname"></span>
			            </li>
			            <li>
			                <span class="title">설명</span>
			                <span class="word" id="description"></span>
			            </li>
			            <li>
			                <span class="title">샤용여부</span>
			                <span class="word" id="usedYn"></span>
			            </li>
			        </ul>
				</div>
				<div class="info_button_area">
			        <button class="button_info oreage" id="savBtn" data-write>
			       		<span>저장</span>
			       	</button>
			    </div>
				<div class="border_box" style="margin-bottom: 100px;">
					<div class="border_list" style="overflow:auto; overflow-x:hidden; height:425px;">
						<table id="sysm0301RoleTable">
							<colgroup>
							<col width="60%">
							<col width="20%">
							<col width="20%">
							</colgroup>
							<thead>
								<tr>
									<th>
										메뉴명
									</th>
									<th>
										<span class="rd_checkbox_1" style="margin-top: 5px;margin-left: 40%;">
											<label class="check">
												<input type="checkbox" value="Y">
												<span  id="method_R"></span>
											</label>
										</span>
										<span class="userText fs14 l26 ml5 fll">
											<label for="chkAmYn"></label>
										</span>
									</th>
									<th>
										<span class="rd_checkbox_1" style="margin-top: 5px;margin-left: 40%;">
											<label class="check">
												<input type="checkbox" id="chkAmYn" name="chkAmYn" value="Y">
												<span  id="method_W"></span>
											</label>
										</span>
										<span class="userText fs14 l26 ml5 fll">
											<label for="chkAmYn"></label>
										</span>
									</th>
								</tr>
							</thead>
							<tbody id="sysRoleArea">
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>
</div>