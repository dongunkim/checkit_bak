
$(function(){
    common.calendar();
    common.etcEvt();

});



common = {
    calendar:function(){
        $(".datepicker").datepicker({
            dateFormat: "yy-mm-dd"
        });
    },
    etcEvt:function(){

        $("body").on("click", ".select-item-wrap01>a", function(){
            var ck = $(this).parents(".select-item-wrap01").eq(0).hasClass("on");
            if(ck){
                $(this).parents(".select-item-wrap01").eq(0).removeClass("on");
            }else{
                $(this).parents(".select-item-wrap01").eq(0).addClass("on");
            }
        });
        $("body").on("mouseleave", ".select-item-wrap01", function(){
            $(".select-item-wrap01").removeClass("on");
        });
        $("body").on("click", ".select-item-wrap01>.select-result-wrap ul li a", function(){
            $(this).parents(".select-result-wrap").eq(0).prev("a").text($(this).find("span").text());
            $(".select-item-wrap01").removeClass("on");
        });

        $("body").on("click", ".layout .sidebar .menu .menu-item>a", function(){
            var ck = $(this).hasClass("on");
            if(ck){
                $(this).removeClass("on");
                $(this).next(".sub-menu-list").slideUp(300);
            }else{
                $(this).parents("ul").eq(0).children().children("a").removeClass("on");
                $(this).parents("ul").eq(0).children().children(".sub-menu-list").slideUp(300);
                $(this).addClass("on");
                $(this).next(".sub-menu-list").slideDown(300);
            }
        });

    },
    popOpen:function(o){
        $(o).addClass("ing");
        setTimeout(function(){
            $(o).addClass("on");
        },200);
    },
    popClose:function(o){
        $(o).removeClass("on");
        setTimeout(function(){
            $(o).removeClass("ing");
        },500)
    }
}




