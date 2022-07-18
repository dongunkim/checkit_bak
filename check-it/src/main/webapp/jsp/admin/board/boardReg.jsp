<%@ page contentType="text/html; charset=UTF-8"%>
<link rel="stylesheet" href="/resources/lib/jodit/jodit.min.css" />
<script src="/resources/lib/jodit/jodit.min.js"></script>

<div class="contens_bd">
    <h2>게시판 등록</h2>
   	<div class="info_area" id="boardReg">
		<input type="hidden" name="boardType" id="boardType" value="01">
        <h3>게시판 내용</h3>
        <ul>
            <li>
                <span class="title">제목</span>
                <span class="word">
                	<input type="text" class="info_textbox" name="boardTitle" id="boardTitle" style="width:100%">
                </span>
            </li>
             <li>
                <span class="title">첨부파일</span>
                <span class="word">
                	<div id="fileupload" data-file-gbn="bbs" style="width:100%"></div>
                </span>
            </li>
            <li>
                <span class="title">내용</span>
                <span class="word">
                	<textarea id="boardDesc" name="boardDesc"></textarea>
					<script>
						const editor = Jodit.make('#boardDesc', {
							"uploader": {
								"insertImageAsBase64URI": true
							}
						});
					</script>
                </span>
            </li>
        </ul>
    </div>
   	<div class="info_button_area">
		<button class="button_info black" id="backBtn">
			<span>목록</span>
		</button>   		
		<button class="button_info oreage" id="regBtn" data-write>
			<span>저장</span>
		</button>
	</div>
	
</div>
