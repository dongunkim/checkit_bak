/**
 * 화면제어용 JS
 * @author devkimsj
 */
$(document).ready(function(){

	// 탑 버튼 숨김(퍼블리싱)
	$(function() {
		$("a.btn_top").hide();
		$(window).scroll(function() {
			if ($(this).scrollTop() > 100) { // 스크롤 내릴 표시
				$("a.btn_top").fadeIn();
			} else {
				$("a.btn_top").fadeOut();
			}
		});

		$("a.btn_top").click(function() {
			$("body,html").animate({
				scrollTop: 0
			}, 500); // 탑 이동 스크롤 속도
			return false;
		});
	});

	// 아래로 개발영역
	// 달력(년)
	if($(".btnCal_year").length > 0){

		$.each($(".btnCal_year"), function(index, item){

			calendar.year($(this));

		});

	}

	// 달력(월)
	if($(".btnCal_month").length > 0){

		$.each($(".btnCal_month"), function(index, item){

			//input이 2개인 경우 from~to 전월~ 현재월 셋팅
			if($(this).find(".input_day").length == 2){
				var toDay = new Date(); toDay.setMonth(toDay.getMonth()-1);
				var toDay2 = new Date();
	
				$(this).find(".input_day:eq(0)").val(toDay.print("yyyy-mm"));
				$(this).find(".input_day:eq(1)").val(toDay2.print("yyyy-mm"));
				
			}
			calendar.month($(this));

		});

	}

	// 달력(일)
	if($(".btnCal_day").length > 0){

		$.each($(".btnCal_day"), function(index, item){

			calendar.day($(this));

		});

	}

	// 달력(멀티)
	if($(".btnCal_multy").length > 0){

		$.each($(".btnCal_multy"), function(index, item){

			//input이 2개인 경우 from~to 전월일~ 현재일 셋팅
			if($(this).find(".input_day").length == 2){
				var toDay = new Date(); toDay.setMonth(toDay.getMonth()-1);
				var toDay2 = new Date();
	
				$(this).find(".input_day:eq(0)").val(toDay.print("yyyy-mm-dd"));
				$(this).find(".input_day:eq(1)").val(toDay2.print("yyyy-mm-dd"));
				
			}
			
			$(this).attr("readonly", true);
			calendar.day($(this));

		});

	}

	$.each($(".input_day"), function(){
		let _this = this;
		$(_this).bind("click", function(){
			$(_this).val("");
		});
		$(_this).attr("readonly", true);
	});

	// 파일 업로드
	if($("#fileupload").length > 0){

		$("#fileupload").fileUploadForm({
			type : "attachInsert"
		});

	}

	// 이메일 포맷팅1
	if($(".email").length > 0){
		$.each($(".email"), function(index, data){
			let tagName = $(data).prop("tagName");
			if(tagName.toLowerCase() == "select"){
				let inputName = $(data).attr("name");
				let inputBox = $("<input>", {
					  type : "text"
					 ,name : inputName
					 ,id : inputName
					 ,addClass : "info_textbox w130"
					 ,style : "margin-right:7px;"
				});
				$(data).before(inputBox);
				$(data).attr("name", "emailSelect");
				$(data).attr("id", "emailSelect");
				$.each(code.email, function(index, data){
					let selectOption = $("<option>", {
						  text : data.name
						 ,value : data.code
					});
					$("#emailSelect").append(selectOption);
				});
				$(data).bind("change", function(){
					let val = $(this).val();
					if(val == ""){
						$("#" + inputName).val("");
						$("#" + inputName).show();
					}else{
						$("#" + inputName).val(val);
						$("#" + inputName).hide();
					}
				});
			}
		});
	}
	
	// 이메일 포맷팅2
	if($(".email2").length > 0){
		$.each($(".email2"), function(index, data){
			let tagName = $(data).prop("tagName");
			if(tagName.toLowerCase() == "select"){
				let inputName = $(data).attr("name");
				let inputBox = $("<input>", {
					  type : "text"
					 ,name : inputName
					 ,id : inputName
					 ,addClass : "info_textbox w130"
					 ,style : "margin-right:7px;"
				});
				$(data).before(inputBox);
				$(data).attr("name", "emailSelect2");
				$(data).attr("id", "emailSelect2");
				$.each(code.email, function(index, data){
					let selectOption = $("<option>", {
						  text : data.name
						 ,value : data.code
					});
					$("#emailSelect2").append(selectOption);
				});
				$(data).bind("change", function(){
					let val = $(this).val();
					if(val == ""){
						$("#" + inputName).val("");
						$("#" + inputName).show();
					}else{
						$("#" + inputName).val(val);
						$("#" + inputName).hide();
					}
				});
			}
		});
	}

});

let picker = new ax5.ui.picker();
ax5.info.weekNames = [{label: "일"},{label: "월"},{label: "화"},{label: "수"},{label: "목"},{label: "금"},{label: "토"}];
calendar = {
		year : function(obj){
			picker.bind({
				 target: obj
				,content: {
					 type: "date"
					,config: {
						 mode: "year"
						,selectMode: "year"
					}
					,
					formatter: {
						pattern: "date(year)"
					}
				}
			});
		}
		,
		month : function(obj){
			picker.bind({
				 target: obj
				,content: {
					 type: "date"
					,config: {
						 mode: "year"
						,selectMode: "month"
						,lang: {
							months: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]
						}
					}
					,
					formatter: {
						pattern: "date(month)"
					}
				}
			});
		}
		,
		day : function(obj){

			picker.bind({
				 target: obj
				,direction: "top"
				,content: {
					 width: 200
					,margin: 10
					,type: "date"
					,config: {
						control: {
							 left: "<i class=\"fa fa-chevron-left\"></i>"
							,yearTmpl: "%s"
							,monthTmpl: "%s"
							,right: "<i class=\"fa fa-chevron-right\"></i>"
						}
						,
						lang: {
							 yearTmpl: "%s년"
							,months: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"]
							,dayTmpl: "%s"
						}
						,
						marker: (function () {
							let marker = {};
							marker[ax5.util.date(new Date(), {"return": "yyyy-MM-dd", "add": {d: 0}})] = true;

							return marker;
						})()
					}
					,
					formatter: {
						pattern: "date"
					}
				}
				,
				onStateChanged: function () {
					if (this.state == "open") {
						let selectedValue = this.self.getContentValue(this.item["$target"]);
						if (!selectedValue) {
							this.item.pickerCalendar[0].ax5uiInstance.setSelection([ax5.util.date(new Date(), {"add": {d: 0}})]);
						}
					}
				}
			});

		}
};