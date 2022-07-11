<%@ page contentType="text/html; charset=UTF-8"%>
<div class="popup_contens" id="testDiv">
	<div class="info_area popup_area">
 		<div class="border_box">
			<div class="border_list" style="overflow:auto; overflow-x:hidden; height:400px">
				<table id="system0401ListTable">
					<colgroup>
						<col width="30%">
						<col width="40%">						
						<col width="30%">
					</colgroup>
					<thead>					
						<tr>
							<th>
								코드 ID
							</th>
							<th>
								코드 명
							</th>
							<th>
								<span class="rd_checkbox_1" style="margin-top: 5px;margin-left: 40%;">
									<label class="check">
										<input type="checkbox" value="Y">
										<span  id=searchYn></span>
									</label>
								</span>
								<span class="userText fs14 l26 ml5 fll">
									<label for="chkAmYn">검색여부</label>
								</span>
							</th>
						</tr>					
					</thead>
					<tbody id="sysCodeArea">	
							 		
					</tbody>
				</table>
			</div>
		</div>
    </div>
    <div class="info_button_area">
   		<button class="button_info black" id="closeBtn">
			<span>닫기</span>
		</button>
		<button class="button_info oreage" id="saveBtn" data-write>
			<span>저장</span>
		</button>
	</div>
</div>