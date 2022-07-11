/**
 * 화면제어용 JS
 * @author devkimsj
 */
$(document).ready(function(){

	setSiteMenu();

});

setSiteMenu = function(){

	let menuLink = [];

	$("#siteMapArea").empty();
	var num = 0;
	//	// 메뉴 만들기
	$.each(menuList, function(index, menudata){
		// 1레벨
		if(menudata.lv === 2 && menudata.useYn === "Y" && menudata.isDown === "Y"){
			$("#siteMapArea").append("<li id=\"menu" + menudata.menuId + "\"><div id=\"depth_1\">  <a href=\"javascript:void(0)\" class=\"depth_1_link number_"+num+"\">" + menudata.menuName + "</a><ul id=\"siteSubMenu" + menudata.menuId + "\" class=\"depth_2_wrap\"></ul></li>");
		}
		// 2레벨
		if(menudata.lv === 3 && menudata.useYn === "Y"  && (menudata.methodR === "R" || menudata.methodW === "W")){
			$("#siteSubMenu" + menudata.upMenuId).append("<li id=\"siteSubMenu" + menudata.menuId + "\" ><a href=\"javascript:void(0)\" data-site-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
		}
		// 3레벨
		if(menudata.lv === 4 && menudata.useYn === "Y"  && (menudata.methodR === "R" || menudata.methodW === "W")){
			if($("#siteSubMenu" + menudata.upMenuId).find("ul").length == 0){
				$("#siteSubMenu" + menudata.upMenuId).find("a").addClass("rightarrow").after("<ul style=\"display:none\"></ul>");
			}
			$("#siteSubMenu" + menudata.upMenuId).find("ul").append("<li id=\"siteSubMenu" + menudata.menuId + "\"><a href=\"javascript:void(0)\" data-site-link=\"menu" + menudata.menuId + "\">" + menudata.menuName + "</a></li>");
		}
		menuLink["menu" + menudata.menuId] = utils.defaultString(menudata.menuUrl);
		num = num+1;
	});

	$("#siteMapArea > li > div > ul > li").unbind().bind({
		mouseover : function(){
			$(".rightarrow").next().hide();
			$(this).find("ul").show();
		},
		mouseleave : function(){
			$(this).find("ul").hide();
		}
	})

	// 클릭이벤트 처리
	$("[data-site-link]").unbind().bind("click", function(){
		let linkId = $(this).attr("data-site-link");
		let link = utils.defaultString(menuLink[linkId]);
		if(link == ""){
			if($("[data-site-link=\"" + linkId + "\"]").next().find("li").length == 0){
				alert("개발중입니다.");
			}
		}else{
			utils.movePage(link);
		}
	});

}