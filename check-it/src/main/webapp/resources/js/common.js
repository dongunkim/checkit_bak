/*
 * Default JS
 */
$(document).ready(function(){

    // 탑 버튼 숨김
    $(function() {
        $("a.btn_top").hide();
        $(window).scroll(function() {
            if ($(this).scrollTop() > 100) { // 스크롤 내릴 표시
                $('a.btn_top').fadeIn();
            } else {
                $('a.btn_top').fadeOut();
            }
        });

        $('a.btn_top').click(function() {
            $('body,html').animate({
                scrollTop: 0
            }, 500); // 탑 이동 스크롤 속도
            return false;
        });
    });

});

/**
 * Excel File Download
 * 주로 List 화면에서 Excel 다운로드 시 호출
**/
function downloadExcel(uri, condition) {				
	// 진행 바
	//var preparingFileModal = $("#preparing-file-modal");
	//preparingFileModal.dialog({modal: true});		
	//$("#progressbar").progressbar({value: false});
	
			
	$.fileDownload(uri, {
		httpMethod: "POST",
		data : condition,
		successCallback: function(url) {
			//preparingFileModal.dialog('close');
			// 진행바 닫기	
		},
		
		failCallback: function(responseHtml, url) {
			console.log(responseHtml);
			// Error 메시지
		}
	});
}