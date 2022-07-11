initFunction = function(){

	// 고객공지
//	if(result.main0101List.length > 0){
//
//		go0101DetailPage = function(bbsNo){
//			let params = {};
//				params.bbsNo = bbsNo;
//			utils.movePage("/admin/bord/bord0302Detail.do", params);
//		}
//
//		let main0101ListHtml = [];
//		$.each(result.main0101List, function(idx, data){
//			main0101ListHtml.push("<tr style=\"cursor:pointer;\" onclick=\"go0101DetailPage('" + data.bbsNo + "')\">");
//			main0101ListHtml.push("<td>" + (idx + 1) + "</td>");
//			main0101ListHtml.push("<td>" + data.gongjiGubunNm + "</td>");
//			main0101ListHtml.push("<td>" + data.bbsTitle + "</td>");
//			main0101ListHtml.push("<td>" + data.regDt.date().print("yyyy-mm-dd") + "</td>");
//			main0101ListHtml.push("</tr>");
//		});
//
//		$("#main0101ListTable > tbody").empty().append(main0101ListHtml.join(""));
//	}else{
//
//	}

	// 내부공지
//	if(result.main0102List.length > 0){
//
//		go0102DetailPage = function(bbsNo){
//			let params = {};
//				params.bbsNo = bbsNo;
//			utils.movePage("/admin/bord/bord0202Detail.do", params);
//		}
//
//		let main0102ListHtml = [];
//		$.each(result.main0102List, function(idx, data){
//			main0102ListHtml.push("<tr style=\"cursor:pointer;\" onclick=\"go0102DetailPage('" + data.bbsNo + "')\">");
//			main0102ListHtml.push("<td>" + (idx + 1) + "</td>");
//			main0102ListHtml.push("<td>" + data.gongjiGubunNm + "</td>");
//			main0102ListHtml.push("<td>" + data.bbsTitle + "</td>");
//			main0102ListHtml.push("<td>" + data.regDt.date().print("yyyy-mm-dd") + "</td>");
//			main0102ListHtml.push("</tr>");
//		});
//
//		$("#main0102ListTable > tbody").empty().append(main0102ListHtml.join(""));
//	}else{
//
//	}

//    eventFunction();

}

//eventFunction = function(){
//
//	// 받은쪽지함
//	utils.ajax("/admin/main/main0103List.do", {}, function(data){
//		let main0103ListHtml = [];
//		if(data.result.length > 0){
//
//			go0103DetailPage = function(){
//				$(this).popup({
//					 title : "쪽지"
//					,width : "1000px"
//					,height : "750px"
//					,scrolling : "auto"
//					,url : "/admin/main/main0104ListPop.do"
//					,callBackFn : function(data){
//						if(data.viewCountDown){
//							let viewCount = $("#messageBtn").text().number();
//							if(viewCount <= 1){
//								$("#messageBtn").empty();
//							}else{
//								$("#messageBtn").empty().append("<p>" + (viewCount - 1) + "</p>");
//							}
//						}
//					}
//				});
//			}
//			$.each(data.result, function(idx, data){
//				main0103ListHtml.push("<li>");
//				main0103ListHtml.push("<a href=\"javascript:void(0)\" onclick=\"go0103DetailPage()\">");
//				main0103ListHtml.push("<span class=\"title\">[" + data.regIdNm + "] " + data.noteTitle + "</span>");
//				main0103ListHtml.push("<span class=\"day\">" + data.regDt.date().print("yyyy-mm-dd hh:mi:ss") + "</span>");
//				main0103ListHtml.push("</a>");
//				main0103ListHtml.push("</li>");
//			});
//		}else{
//			main0103ListHtml.push("<li>");
//			main0103ListHtml.push("<span>받은 쪽지가 없습니다.</span>");
//			main0103ListHtml.push("</li>");
//		}
//		$("#main0103ListArea").empty().append(main0103ListHtml.join(""));
//	}, false);
//
//	// 장애현황
//	utils.ajax("/admin/main/main0104List.do", {}, function(data){
//		let main0104ListHtml = [];
//		if(data.result.length > 0){
//
//			go0104DetailPage = function(tjNo){
//				let params = {};
//					params.tjNo = tjNo;
//				utils.movePage("/admin/ermn/ermn0102Detail.do", params);
//			}
//			$.each(data.result, function(idx, data){
//				main0104ListHtml.push("<li>");
//				main0104ListHtml.push("<a href=\"javascript:void(0)\" onclick=\"go0104DetailPage('" + data.tjNo + "')\">");
//				main0104ListHtml.push("<span class=\"title\">");
//				main0104ListHtml.push("<ul>");
//				main0104ListHtml.push("<li>" + utils.defaultString(data.centerNm) + "</li>");
//				main0104ListHtml.push("<li>" + utils.defaultString(data.sectionNm) + "</li>");
//				main0104ListHtml.push("<li>" + utils.defaultString(data.title) + "</li>");
//				main0104ListHtml.push("</ul>");
//				main0104ListHtml.push("</span>");
//				main0104ListHtml.push("<span class=\"day\">" + data.startDt.date().print("yyyy-mm-dd hh:mi") + "</span>");
//				main0104ListHtml.push("</a>");
//				main0104ListHtml.push("</li>");
//			});
//		}else{
//			main0103ListHtml.push("<li>");
//			main0103ListHtml.push("<span>조회된 내역이 없습니다..</span>");
//			main0103ListHtml.push("</li>");
//		}
//		$("#main0104ListArea").empty().append(main0104ListHtml.join(""));
//	}, false);
//
//	// 파일관리 자료실
//	utils.ajax("/admin/bord/bord0501List.do", {}, function(data){
//		let main0105ListHtml = [];
//		if(data.result.list.length > 0){
//
//			go0105DetailPage = function(fileMngSeq){
//				let params = {};
//					params.fileMngSeq = fileMngSeq;
//				utils.movePage("/admin/bord/bord0502DetailList.do", params);
//			}
//
//			$.each(data.result.list, function(idx, data){
//				if(idx == 5){
//					return false;
//				}
//				main0105ListHtml.push("<li>");
//				main0105ListHtml.push("<a href=\"javascript:void(0)\" onclick=\"go0105DetailPage('" + data.fileMngSeq + "')\">");
//				main0105ListHtml.push("<span class=\"title\">" + data.fileNm + "</span>");
//				main0105ListHtml.push("<span class=\"day\">" + data.regDt.date().print("yyyy-mm-dd hh mi:ss") + "</span>");
//				main0105ListHtml.push("</a>");
//				main0105ListHtml.push("</li>");
//			});
//		}else{
//			main0105ListHtml.push("<li>");
//			main0105ListHtml.push("<span>등록된 파일이 없습니다.</span>");
//			main0105ListHtml.push("</li>");
//		}
//		$("#main0105ListArea").empty().append(main0105ListHtml.join(""));
//	}, false);
//
//	// 고객공지 More 버튼
//	$("#main0101MoreBtn").unbind().bind("click", function(){
//		utils.movePage("/admin/bord/bord0301List.do");
//	});
//
//	// 내부공지 More 버튼
//	$("#main0102MoreBtn").unbind().bind("click", function(){
//		utils.movePage("/admin/bord/bord0201List.do");
//	});
//
//	// 파일관리 More 버튼
//	$("#fileMngMoreBtn").unbind().bind("click", function(){
//		utils.movePage("/admin/bord/bord0501List.do");
//	});
//
//	// 쪽지 팝업
//	$("#mainMessageBtn").popup({
//		 title : "쪽지"
//		,width : "1000px"
//		,height : "750px"
//		,scrolling : "auto"
//		,url : "/admin/main/main0104ListPop.do"
//	});
//
//	// 장애현황 More 버튼
//	$("#main0104MoreBtn").unbind().bind("click", function(){
//		utils.movePage("/admin/ermn/ermn0101List.do");
//	});
//
//}