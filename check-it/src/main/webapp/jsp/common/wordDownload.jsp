<%-- <%@ page language="java" contentType="application/vnd.word;charset=UTF-8" pageEncoding="UTF-8"%> --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%

/* response.setHeader("Content-Disposition", "attachment;filename=aaa.doc");
response.setHeader("Content-Description", "JSP Generated Data");
response.setContentType("application/vnd.ms-word"); */

%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript">
	//<![CDATA[
	 var result = ${(result == null || result == "")? "{}" : result};
	//]]>
	</script>
<style>

.blue{background-color:#0985ef !important;}/*파란색*/
.red{background-color:#d96161 !important;}/*레드*/
.oreage{background-color:#ef8009 !important;}/*오렌지 - 저장, 등록, 수정*/
.gray{background-color:#E13C2C !important;}/*그레이 - 삭제, 이전, 취소*/
.black{background-color:#535353 !important;}/*블랙 - 목록, 검색*/
.pink{background-color:#f77c7c !important;}

.blue_f{color:#0985ef !important;}/*파란색*/
.oreage_f{color:#ef8009 !important;}/*오렌지 - 저장, 삭제*/
.gray_f{color:#afafaf !important;}/*그레이 - 수정*/
.black_f{color:#535353 !important;}/*블랙 - 이전, 목록*/
.red_f{color:#d96161 !important;}/*레드*/

/*서브페이지 제목*/
h2{color:#535353; font-size:30px; font-weight:500; margin:10px 0!important;}/*큰 타이틀*/
h3{color:#535353; font-size:16px; font-weight:400; margin:5px 0!important;}/*소 타이틀*/
h4{position:relative; color:#535353; font-size:18px; font-weight:400; padding:20px 0 10px 35px !important; border-bottom:2px solid #f79a07; font-weight:500;}
h5{color:#535353; font-size:25px; font-weight:500; margin:10px 0!important;}
h4::before{content:''; position:absolute; left:0; top:19px; width:30px; height:30px; background:url('../images/icon/info_icon_1.png') no-repeat;}
h4 > span a{position:absolute; right:0; top:19px; font-size:14px; line-height:40px; color:#fd7a23;}
/**서브페이지 제목*/

/*180727 추가 모달팝업*/
.modalpopup{position: fixed; top: 0; right: 0; bottom: 0; left: 0; background: rgba(0, 0, 0, 0.3); /* opacity:0; */ -webkit-transition: opacity 400ms ease-in; -moz-transition: opacity 400ms ease-in; transition: opacity 400ms ease-in; /* pointer-events: none; */ z-index:101}
.modalpopup:target{opacity:1; pointer-events: auto;}
.modalpopup .modalcontens{position: absolute;top: 10%;left: 50%; width: 1600px;height: 80%;margin-left:-800px;background-color: #fff;}/*팝업 기본*/
.modalpopup .modalcontens h2{margin:0 !important;font-size: 23px;border-bottom: 2px solid #ccc;height: 50px;line-height: 50px;padding: 0 15px;background-color: #5d5d5d;color: #fff;}
.modalpopup .modalcontens .close{position:absolute; top:10px; right:10px; width:28px; height:28px; background:url('../images/icon/popclose_icon_1.png') no-repeat; cursor:pointer;}
.modalpopup .modalcontens .popup_contens h3{padding:6px 0 6px 35px !important;}
.modalpopup .modalcontens .popup_contens h3::before{top:4px !important;}

/*모달팝업 타이틀*/
.popup_area{overflow:hidden; margin-top:10px;}
.popup_area:first-child{margin:0 !important;}
.popup_area > h3{padding:4px 0 0px 35px !important; border:0 !important; margin-top:10px !important;}
.popup_area > h3:first-child{margin:0 !important;}
.popup_area > h3::before{top:2px;}
/*모달팝업 타이틀*/

/*모달팝업 컨텐츠 들어가는곳*/
.popup_contens{padding:10px 10px;}
/**모달팝업 컨텐츠 들어가는곳*/

/*정보 리스트*/
.info_area{position:relative;}
.info_area > h3{position:relative; color:#535353; font-size:18px; font-weight:500; padding:20px 0 5px 35px;}
.info_area > h3::before{content:''; position:absolute; left:0; top:18px; width:30px; height:30px; background:url('../images/icon/info_icon_1.png') no-repeat;}
.info_area > ul{}
.info_area > ul li{position:relative; padding:7px 20px; border-bottom:1px solid #d5d5d5; float:none;}
.info_area > ul li::after{content:''; display:block; clear:both;}
.info_area > ul li:first-child{border-top:2px solid #f79a07;}
.info_area > ul li:last-child{border-bottom:1px solid #b1b1b1;}
.info_area > ul li > div{position:relative; float:left; width:calc(100% / 2);}/*한테이블 안에 두개 들어갈 시*/
/* .info_area > ul li > div{position:relative; float:left; width:calc(90%);}한테이블 안에 두개 들어갈 시 */
/*한테이블 안에 세개 들어갈 시 예시 cumn0401Reg.jsp*/
.info_area > ul li > div.three_line{position:relative; float:left; width:100%;}
.info_area > ul li > div.three_line > span{float:left}
.info_area > ul li > div.three_line > span.title{width:60px; color:#868585;}
.info_area > ul li > div.three_line > span.word{width:calc(100% / 6); padding:0 15px; color:#000; position:relative; }
/*한테이블 안에 세개 들어갈 시 예시 cumn0401Reg.jsp*/
.info_area > ul li > span,
.info_area > ul li > div > span{position:relative; float:left; font-size:16px; line-height:30px; }
.info_area > ul li > span.title,
.info_area > ul li > div span.title{width:120px; color:#868585;}/*제목*/
.info_area > ul li > span.word,
.info_area > ul li > div span.word{width:calc(100% - 120px); padding:0 15px; color:#000; position:relative; }/*내용*/
.info_area > ul li > div span.word > span{float:left; }
.info_area > ul li > div span.word > span.time_mr5{margin-right:10px; line-height:29px;}
.info_area > ul li > div span.word > span:last-child{margin:0}
.info_area > ul li > span.sub{width:70px;}/*버튼 및 여러가지*/

/* 한 공간에 2개 선택지가 들어갈때 사용 */
.info_area > ul li > div > span.word > .two{float:left;}
.info_area > ul li > div > span.word > .two > span{float:left; width:calc(100% / 2 - 4px); margin-right:5px; text-align:center;}
.info_area > ul li > div > span.word > .two > span > select,.info_area > ul li > div > span.word > .two > span > input{width:100% !important;}
.info_area > ul li > div > span.word > .two > span:last-child{margin:0;}

/* 한 공간에 3개 선택지가 들어갈때 사용 (해당 스타일시트는 날짜 시 분 ~ 날짜 시 분 들어갔을때 사용)*/
.info_area > ul li > div > span.word > .three{float:left;}
.info_area > ul li > div > span.word > .three > span{float:left; width:calc(100% / 3 - 40px); margin-right:5px; text-align:center;}
.info_area > ul li > div > span.word > .three > span:last-child{margin:0;}

/*테이블 영역*/
.table_area{width:100%;}
.table_area > h3 > i{font-size:13px;color:red;line-height: 27px;padding-left: 10px;}
.table_area > h3 > i.load{font-size:16px;color:blue;line-height: 27px;padding-left: 10px;}
.table_area > table{width:100%; table-layout:fixed; font-size:15px; border:1px solid #e8e8e8; border-top:2px solid #f79a07; }
.table_area > table tr{}
.table_area > table.down_alltotal tbody > tr:last-child,/*맨 아래 합계*/
.table_area > table.right_alltoral tr > td:last-child, /*맨 오른쪽 합계 td*/
.table_area > table.right_alltoral tr > th:last-child /*맨 오른쪽 합계 th*/
{background:rgba(255,147,0,0.6);font-weight:700; font-size:18px; color:#000000;}
.table_area > table th{background-color:#f3f3f3; height:40px;}
.dubble_table_area > table th{background-color:#f3f3f3; height:auto; border:1px solid #ccc}
.table_area > table th::after{content:''; float:right; display:inline-block; width:1px; height:25px; background-color:#ccc; }
.dubble_table_area > table th::after{content:''; float:right; display:inline-block; width:1px; height:auto; background-color:#ccc; }
.table_area > table th:last-child::after{background:none;}
.table_area > table td{text-align:center; height:30px; border:1px solid #e8e8e8;}
.table_area > table td.input > input{vertical-align:top; border:0; width:100%; height:29px; background:#fbffbe;}
.table_area > table td.input > textarea{vertical-align:top; border:0; width:100%; height:100%; background:#fbffbe; resize:none;}

.table_dule{}
.table_dule > span{float:left; width:calc(100% / 2 - 20px); margin-right:20px;}
.table_dule > span:last-child{margin-right:0;}
/*테이블 영역*/

/*버튼 기본*/
button > span{color:#fff; font-size:15px; font-weight:500; padding:0 5px;}
.button_sh{background-color:#535353; width:auto; height:30px; border-radius:3px; margin:0 2px;}/*검색 기본 버튼*/
.button_sm{background-color:#ef8009; width:auto; height:30px; border-radius:3px; }/*작은 오렌지 컬러 */
.button_sm_g{background-color:#b9b9b9; width:auto; height:30px; border-radius:3px; }/*작은 그레이 컬러 */
.button_list{display:table; margin:30px auto; background-color:#ef8009; width:200px; height:40px; border-radius:3px;}/*목록보기 버튼*/
.button_info{float:right; margin:10px 5px; width:120px; height:40px; border-radius:3px;}/*기본정보 버튼 베이스*/
.button_info_nawd{float:left; margin:0px 5px; width:120px; height:30px; border-radius:3px;}/*기본정보 버튼 베이스(테이블 안쪽에 들어가는 버튼)*/
.button_info_akcc{float:left; margin:0px 5px; width:90px; height:30px; border-radius:3px;}/*기본정보 버튼 베이스(조그만한게 )*/
.button_rack{background-color:#eff2f7; border:1px solid #dbdbdb;}
.button_rack > span{color:#ef8009;}
/**버튼 기본*/

</style>
<title>업무지원시스템</title>
</head>
<body>
	<%-- <c:if test="${result != null}">
	${result}
	</c:if> --%>
	<div class="popup_contens">
		<div class="info_area popup_area">
			<h2 id="title"></h2>
			<h3>장애개요</h3>
			<ul>
				<li>
					<span class="title">내용</span>
					<span class="word" id="stateDesc"></span>
				</li>
				<li>
					<span class="title">이장 시간</span>
					<span class="word">
						<em id="startDt"></em>
						<em class="w20"> ~ </em>
						<em id="endDt"></em>
						&nbsp;
						(
						<em id="changeDt"></em>
						<em>&nbsp;분</em>
						)
					</span>				
				</li>
				<li>
					<span class="title">피해 시설</span>
					<span class="word" id="troubleResoDesc"></span>
				</li>
				<li>
					<span class="title">피해 내역</span>
					<span class="word" id="troubleResoDesc">
						<table class="dev-table">
						<colgroup>
							<col style="width:25%;"></col>
							<col style="width:25%;"></col>
							<col style="width:25%;"></col>
							<col style="width:25%;"></col>
						</colgroup>
						<tr>
							<td style="border:1px solid;">대상 회선 수</td>
							<td style="border:1px solid;" id="totLineCnt"></td>
							<td style="border:1px solid;">피해 회선 수</td>
							<td style="border:1px solid;"></td>
						</tr>
						<tr>
							<td style="border:1px solid;">전주 트래픽</td>
							<td style="border:1px solid;"></td>
							<td style="border:1px solid;">금주 트래픽</td>
							<td style="border:1px solid;"></td>
						</tr>
						<tr>
							<td style="border:1px solid;">대상 서버 수</td>
							<td style="border:1px solid;"></td>
							<td style="border:1px solid;">피해 서버 수</td>
							<td style="border:1px solid;"></td>
						</tr>
						</table>
					</span>
				</li>
			</ul>
			<h3>장애원인 및 조치</h3>
			<ul>
				<li>
					<span class="title">장애 원인</span>
					<span class="word" id="troubleCd">
						
					</span>
				</li>
			</ul>
			<!-- 2번째 팝업에서 Hide 되는 부분 STR -->
			<h3>조치 내역</h3>
			<ul>
				<li>
					<div>
						<span class="title">작업내역</span>
						<span class="word" style="color:red;" id="jobStatusCd" data-code></span>
						<br>
						<span class="title">　</span>
						<span class="word" id="jobDesc"></span>
					</div>
				</li>
				<li>
					<span class="title">조치자</span>
					<span class="word" id="jobTakeUserid">
						
					</span>
				</li>	
			</ul>
			<!-- 2번째 팝업에서 Hide 되는 부분 END -->
			<h3>개선대책</h3>
			<ul>
				<li>
					<span class="title"></span>
					<span class="word" style="padding-left:120px;">
						<table class="dev-table">
							<colgroup>
								<col style="width:25%;"></col>
								<col style="width:75%;"></col>
							</colgroup>
							<tr>
								<td style="border:1px solid;">개선 방안</td>
								<td style="border:1px solid;"  id="improvement"></td>
							</tr>
						</table>
					</span>
				</li>
			</ul>
			<h3>기타사항</h3>
			<ul>
				<li>
					<span class="title"></span>
					<span class="word" id="tjEtcDesc"></span>
				</li>
			</ul>
			<h3>네트워크 구성현황</h3>
			<ul>
				<li>
					<span class="title"></span>
					<span class="word" id="tjEtcDesc"></span>
				</li>
			</ul>			
		</div>
	</div>
</body>
</html>