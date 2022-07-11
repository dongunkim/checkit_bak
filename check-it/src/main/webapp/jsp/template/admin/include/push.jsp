<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<script type="text/javascript">
//<![CDATA[
const host = document.location.host;
let webSocket = new WebSocket("ws://" + host + "/push");

webSocket.onerror = function(event) {
	console.log(event.data);
};

webSocket.onopen = function(event) {
	console.log("socket 연결성공");
};

webSocket.onmessage = function(event) {
	//let result = JSON.parse(event.data).result;
	console.log(event);
	// 알림
	if(event.data == "P"){
// 		utils.ajax("/admin/main/main0106PopList.do", {}, function(data){
// 			if(data.result.total.cnt.number() != 0){
// 				let html = [];
// 					html.push("<p>" + data.result.total.cnt + "</p>");
// 					html.push("<div class=\"bubble1\" id=\"pushLayerBtn\">");
// 					html.push("<span>알람이 도착 했습니다, 확인해 주세요!</span>");
// 					html.push("</div>");
// 				$("#pushBtn").empty().append(html.join(""));
				
// 				$("#pushLayerBtn").unbind().bind("click", function(){
// 					$(this).remove();
// 					$(this).popup({
// 						 title : "알림 정보 내역"
// 						,width : "1000px"
// 						,height : "600px"
// 						,scrolling : "auto"
// 						,url : "/admin/main/main0106ListPop.do"
// 					});
// 				});
				
// 			}
// 		}, false);
	// 쪽지	
	}else if(event.data == "M"){
// 		utils.ajax("/admin/main/main0107PopList.do", {}, function(data){
// 			if(data.result.total.cnt.number() != 0){
// 				let html = [];
// 					html.push("<p>" + data.result.total.cnt + "</p>");
// 					html.push("<div class=\"bubble2\" id=\"messageLayerBtn\">");
// 					html.push("<span>쪽지가 도착 했습니다, 확인해 주세요!</span>");
// 					html.push("</div>");
// 				$("#messageBtn").empty().append(html.join(""));
				
// 				$("#messageLayerBtn").unbind().bind("click", function(){
// 					$(this).remove();
// 					$(this).popup({
// 						 title : "쪽지"
// 						,width : "1000px"
// 						,height : "750px"
// 						,scrolling : "auto"
// 						,url : "/admin/main/main0104ListPop.do"
// 					});
// 				});
// 			}
// 		}, false);
	}
};
//]]>
</script>