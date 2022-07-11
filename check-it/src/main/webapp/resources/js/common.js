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
