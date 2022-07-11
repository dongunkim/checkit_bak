/**
 * 권한제어용 JS
 * @author devkimsj
 */
let isMessage  = false; // 쪽지
let isBoard    = false; // 공지/게시판
let isTelBoard = false; // 전화번호부
let isIpSearch = false; // ip검색
let isBord0301 = false; // 고객공지
let isBord0201 = false; // 내부공지
let isErmn0101 = false; // 장애현황
let isBord0501 = false; // 파일관리 자료실
let isWkmn0201 = false; // 일반작업요청현황
let isWkmn0701 = false; // 영업지원작업현황
let isCumn0501 = false; // 상담요청현황
let isCumn0301 = false; // 방문출입현황
let isCumn0401 = false; // 장비출입현황

let isKms	   = false; // KMS

const nowLoc = location.pathname;
$(window).load(function(){
	$.each(menuList, function(idx, menuData){
		if(nowLoc.indexOf("main0101Page.do") == -1){
			if(utils.defaultString(menuData.menuUrl).indexOf(nowLoc) > -1){
				if(utils.defaultString(menuData.methodR) === "" && utils.defaultString(menuData.methodW) === ""){
					$(".wrap").remove();
					DIALOG.alert({
						title: "알림",
						msg: "잘못된 경로로 접근하셨습니다."
					}, function () {
						utils.movePage("/admin/main/main0101Page.do");
					});
				}
			}
		}
	});
});

rollFn = function(){

	if(window.top.popupOptionArr.length > 0){
		$("#bookMarkArea").remove();
		$(".realtime_area_side").remove();
		return;
	}

	$.each(menuList, function(idx, menuData){

		// 쪽지
		if(menuData.menuId === 15 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isMessage = true;
		}
		// 공지/게시판
		if(menuData.menuId === 174 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isBoard = true;
		}
		// 전화번호부
		if(menuData.menuId === 175 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isTelBoard = true;
		}
		// ip검색
		if(menuData.menuId === 168 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isIpSearch = true;
		}
		// 고객공지
		if(menuData.menuId === 177 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isBord0301 = true;
		}
		// 내부공지
		if(menuData.menuId === 176 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isBord0201 = true;
		}
		// 장애현황
		if(menuData.menuId === 30 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isErmn0101 = true;
		}
		// 파일관리 자료실
		if(menuData.menuId === 1005 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isBord0501 = true;
		}
		// 일반작업요청현황
		if(menuData.menuId === 27 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isWkmn0201 = true;
		}
		// 영업지원작업현황
		if(menuData.menuId === 116 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isWkmn0701 = true;
		}
		// 상담요청현황
		if(menuData.menuId === 23 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isCumn0501 = true;
		}
		// 방문출입현황
		if(menuData.menuId === 21 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isCumn0301 = true;
		}
		// 장비출입현황
		if(menuData.menuId === 22 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isCumn0401 = true;
		}
		// KMS
		if(menuData.menuId === 14 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isKms = true;
		}
		// 자료정보관리(KMS, 임시 제작)
		if(menuData.menuId === 1018 && (menuData.methodR === "R" || menuData.methodW === "W")){
			isKms = true;
		}
		
	});

	if(!isMessage){
		$("#messageBtn").remove();
	}

	if(!isBoard){
		$("#boardArea").remove();
	}

	if(!isTelBoard){
		$("#telBoardArea").remove();
	}

	if(!isIpSearch){
		$("#ipSearchArea").remove();
	}

	// 메인일경우
	if(menuId == 1){
		if(!isBord0301){
			$("#main0101MoreBtn").remove();
			$("#main0101ListTable > tbody").empty().html("<tr style=\"height:90px;\"><td colspan=\"4\">조회내역이 없습니다.</td></tr>");
			$("#main0101ListTable").removeAttr("id");
		}
		if(!isBord0201){
			$("#main0102MoreBtn").remove();
			$("#main0102ListTable > tbody").empty().html("<tr style=\"height:90px;\"><td colspan=\"4\">조회내역이 없습니다.</td></tr>");
			$("#main0102ListTable").removeAttr("id");
		}
		if(!isMessage){
			$("#mainMessageBtn").remove();
			$("#main0103ListArea").empty().html("<li><span>받은 쪽지가 없습니다.</span></li>");
			$("#main0103ListArea").removeAttr("id");
		}
		if(!isErmn0101){
			$("#main0104MoreBtn").remove();
			$("#main0104ListArea").empty().html("<li><span>조회내역이 없습니다.</span></li>");
			$("#main0104ListArea").removeAttr("id");
		}
		if(!isBord0501){
			$("#fileMngMoreBtn").remove();
			$("#main0105ListArea").empty().html("<li><span>조회내역이 없습니다.</span></li>");
			$("#main0105ListArea").removeAttr("id");
		}

		if(!isWkmn0201 && !isWkmn0701 && !isCumn0501 && !isCumn0301 && !isCumn0401){
			$(".realtime_area").remove();
			$(".board_area").css({width: "100%"});
		}else{
			if(!isCumn0401){
				$(".realtime_area >> li").eq(4).remove();
			}
			if(!isCumn0301){
				$(".realtime_area >> li").eq(3).remove();
			}
			if(!isCumn0501){
				$(".realtime_area >> li").eq(2).remove();
			}
			if(!isWkmn0701){
				$(".realtime_area >> li").eq(1).remove();
			}
			if(!isWkmn0201){
				$(".realtime_area >> li").eq(0).remove();
			}
		}

	}

}

leftView = function(){
	if(!isWkmn0201 && !isWkmn0701 && !isCumn0501 && !isCumn0301 && !isCumn0401){
		$(".realtime_area_side").remove();
	}else{
		if(!isCumn0401){
			$(".realtime_area_side >> li").eq(4).remove();
		}
		if(!isCumn0301){
			$(".realtime_area_side >> li").eq(3).remove();
		}
		if(!isCumn0501){
			$(".realtime_area_side >> li").eq(2).remove();
		}
		if(!isWkmn0701){
			$(".realtime_area_side >> li").eq(1).remove();
		}
		if(!isWkmn0201){
			$(".realtime_area_side >> li").eq(0).remove();
		}
	}
}
