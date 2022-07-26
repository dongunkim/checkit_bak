<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd" id="boardForm">
    <h2>게시판 상세</h2>
   	<div class="info_area">
        <h3>상세 내용</h3>
        <ul>
            <li>
   	            <span class="title">제목</span>
                <span class="word" id="boardTitle"></span>
            </li>
            <li>
            	<div>
	                <span class="title">등록자</span>
	                <span class="word" id="regId"></span>
            	</div>
            	<div>
	                <span class="title">등록일시</span>
	                <span class="word" id="regDt" data-type="datetime"></span>
            	</div>
            </li>
            <li>
   	            <span class="title">조회수</span>
                <span class="word" id="viewCount"></span>
            </li>     
			<li>
                <span class="title">첨부파일</span>
                <span class="word" id="attachList"></span>
            </li>      
            <li>
            	<span class="title">내용</span>
                <span class="word">
                	<span id="boardDesc" name="boardDesc"></span>
                </span>
            </li> 
            <li>
            	<span class="title">BP 표시 여부</span>
                <span class="word">
					<input type="radio" name="bpDispYn" id="bpDispY" value="Y" checked="checked"/>표시
					<input type="radio" name="bpDispYn" id="bpDispN" value="N"/>표시 안함
                </span>
            </li> 
            <li>
            	<span class="title">사용 여부</span>
                <span class="word">
					<input type="radio" name="useYn" id="useY" value="Y" checked="checked"/>사용
					<input type="radio" name="useYn" id="useN" value="N"/>사용 안함
                </span>
            </li> 
        </ul>
    </div>
    <div class="info_button_area">
		<button class="button_info black" id="backBtn">
			<span>목록</span>
		</button>
		<button class="button_info red" id="delBtn" data-write>
			<span>삭제</span>
		</button>
		<button class="button_info blue" id="editBtn" data-write>
			<span>수정</span>
		</button>
	</div>
</div>
