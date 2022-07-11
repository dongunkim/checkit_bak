<%@ page contentType="text/html; charset=UTF-8"%>
<div class="contens_bd" id="bord0202Form">
    <h2>내부 공지 조회</h2>
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
                <span class="word">
                	<div id="bord0202Fileupload" data-file-gbn="bbs"></div>
                </span>
            </li>      
            <li>
            	<span class="title">내용</span>
                <span class="word">
                	<span id="boardDesc" name="boardDesc"></span>
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
