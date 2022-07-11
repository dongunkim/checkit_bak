rack = {
		setElement : function(option){

			$("#server").data("drawInfo", {type:"server", row:1, text:"서버", color:"#e60c34"});
			$("#network").data("drawInfo", {type:"network", row:1, text:"네트워크", color:"#1e155e"});

			$("#serverSelect, #networkSelect").unbind().bind("change", function(){
				let id  = $(this).attr("id");
				let val = $(this).val();
				if(id == "serverSelect"){
					$("#server").data("drawInfo", {type:"server", row:val, text:"서버", color:"#e60c34"});
				}else if(id == "networkSelect"){
					$("#network").data("drawInfo", {type:"network", row:val, text:"네트워크", color:"#1e155e"});
				}
			});

		}
		,
		/**
		 * 도면 초기화
		 */
		init : function(option){

			this.setElement(option);

			$("#" + option.targetId).empty().addClass("rack_area");
			let targetDiv = document.getElementById(option.targetId);
			// table 생성
			let table = document.createElement("table");
				table.id = option.targetId + "Table";

			targetDiv.appendChild(table);
			option.targetObj = $("#" + option.targetId + "Table");
			option.targetElement = document.getElementById(option.targetId + "Table");
			this.makeRack(option);

		}
		,
		makeRack : function(option){
			let targetObj       = option.targetObj;
			const targetElement = option.targetElement; //$("#" + option.targetId);
			const colSize       = option.colSize;       // width
			const rowSize       = option.rowSize;       // height

			// 그리기
			for(let row = 0; row < rowSize; row++) {
				let tr = targetElement.insertRow(row);
					tr.setAttribute("data-position", rowSize - row);
				for(let col = 0; col < colSize; col++) {
					let td = tr.insertCell(col);
					td.classList.add("droptarget");
				}

			}

			let defaultHeight = 583;
			let tableHeight = 571;
			let unitHtml = [];

			for(let i = rowSize; i > 0; i--){
				unitHtml.push("<tr>");
				unitHtml.push("<td>" + i + "</td>");
				unitHtml.push("</tr>");
				if(i > 30){
					defaultHeight += 19;
					tableHeight += 19;
				}
			}

			$("#" + option.targetId).css("height", tableHeight + "px");
			$("#unitLenthDiv").css("height", defaultHeight + "px");
			$("#unitLenthTable> tbody").empty().append(unitHtml.join(""));

			if($.type(option.road) === "function"){
				option.road();
			}

			if(!option.dragAndDrop){
				return false;
			}

			// 드래그 & 드랍 이벤트 등록
			this.dragstart(this);
			this.drag();
			this.dragend(option);
			this.dragenter(this, option);
			this.dragover();
			this.drop(option);

		}
		,dragstart : function(_this){
			document.addEventListener("dragstart", function(event) {

				if($("[data-status]").length > 0){
					event.preventDefault();
				}

				if(event.target.localName === "img"){
					event.preventDefault();
				}
				_this.dragId = event.target.id;
				event.dataTransfer.setData("targetId", event.target.id);
				event.target.style.opacity = "0.4";
			});
		}
		,
		drag : function(){
			document.addEventListener("drag", function(event) {
				event.target.style.opacity = "1";
			});
		}
		,
		dragend : function(option){
			document.addEventListener("dragend", function(event) {
				event.target.style.opacity = "1";
				$(".rack_over_board").removeClass("rack_over_board");
			});
		}
		,
		dragenter : function(_this, option){
			console.log(_this);
			document.addEventListener("dragenter", function(event) {
				if(event.target.className == "droptarget" || event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){

					$(".rack_over_board").removeClass("rack_over_board");
					event.target.className += " rack_over_board";
					let dataObj = $("#" + _this.dragId).data("drawInfo");

					// 세로
					$.each($(".rack_over_board"), function(){
						for(let i = 1; i < dataObj.row; i++){
							$(this).parents("table").find("tr").eq($(this).context.parentElement.rowIndex + i).find("td").eq($(this).context.cellIndex).addClass("rack_over_board");
						}
					});

				}
			});
		}
		,dragover : function(){
			document.addEventListener("dragover", function(event) {
				event.preventDefault();
			});
		}
		,dragleave : function(option){
			document.addEventListener("dragleave", function(event) {
				if(event.target.className == "droptarget") {

					$(".rack_over_board").removeClass("rack_over_board");

				}
			});
		}
		,drop : function(option){
			let _this = this;
			let newNodeIndex = 0;
			document.addEventListener("drop", function(event) {
				event.preventDefault();
				if(event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){

					let targetId = event.dataTransfer.getData("targetId");
					let data = $("#" + targetId).data("drawInfo");

					// 충돌여부 체크
					let isCrash = false;
					$.each($(".rack_over_board"), function(iex, val){
						if($(this).hasClass("position_ok")){
							alert("먼저 등록된 장비와 중복 입니다.");
							isCrash = true;
							return false;
						}
					});

					if($(".rack_over_board").length != data.row){
						alert("정확한 위치에 드래그해주세요.");
						isCrash = true;
						return false;
					}

					if(!isCrash){
						noData();
						let rackHeight = 18;
						for(let i = 1; i < data.row.number(); i++){
							rackHeight += 18.5;
							if(i % 2 == 0){
								rackHeight = rackHeight + 1;
							}
						}

						let div = $("<div/>", {
							 "class" : "rack_div"
							,css : {
								 height : rackHeight + "px"
								,backgroundColor : data.color
								,padding : (rackHeight/2 - 9) + "px"
							}
						}).attr({"data-status": "C", "data-type": data.type, "data-unit-size": data.row});

						div.append("<span class=\"unit_raund\">" + data.row + "U</span><span class=\"unit_text\">" + data.text + "</span>")
						$(".rack_over_board").eq(0).append(div).addClass("position_ok");
						$(".rack_over_board").removeClass("rack_over_board");

						$(".rack_div").popover({
							 html:true
							,trigger : "click"
							,placement : "right"
							,content: function() {

								let btnHtml = [];
									btnHtml.push("<button data-picker-btn=\"searchBtn\" class=\"btn btn-default btn-primary\" style=\"margin: -5px -10px -5px -10px;width:65px;\">조회</button><br/>");
									btnHtml.push("<button data-picker-btn=\"cancelBtn\" class=\"btn btn-default btn-danger\" style=\"margin: 10px -10px -5px -10px;width:65px;\">삭제</button>");

								return btnHtml.join("");
							}
						});

						$("body").unbind().bind("mousedown", function (e) {

							// 조회 버튼
							$("[data-picker-btn=\"searchBtn\"]").unbind().bind("click", function(){
								let type = $(this).parents("tr").find(".rack_div").attr("data-type");
								if(type == "server"){
									serverList();
									$(".rack_div").popover("hide");
								}else if(type == "network"){
									networkList();
									$(".rack_div").popover("hide");
								}
							});

							// 삭제 버튼
							$("[data-picker-btn=\"cancelBtn\"]").unbind().bind("click", function(){
								let td = $("<td/>", {
									 "class" : "droptarget"
								});
								$(this).parents("tr").empty().append(td);
								noData();
							});

							$(".rack_div").each(function () {
								if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $(".popover").has(e.target).length === 0) {
									$(this).popover("hide");
								}
							});

						});

					}

				}
			});
		}

}