//css 및 js 동적 추가
canvas = {
		setElement : function(option){
			if(option.type == "canvas"){
				// 랙 : 310001, 케이지 : 310002, 오피스 : 310003
				$("#d_rack").data("drawInfo", {type:"310001", img:'/resources/admin/images/photo/sample_icon_1.png', col:2, row:1});
				$("#d_cage").data("drawInfo", {type:"310002", img:'/resources/admin/images/photo/sample_icon_2.png', col:4, row:4});
				$("#type2").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:2});
				$("#type3").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:1, row:2});
				$("#type4").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:3, row:5});
				$("#type5").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:9});
				$("#type6").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:5});
				$("#type7").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:5, row:14});
			}else if(option.type == "rack"){
				$("#test1").data("drawInfo", {type:"1U", col:1, row:1, text:"라우터", color:"#e60c34"});
				$("#test2").data("drawInfo", {type:"2U", col:1, row:2, text:"라우터", color:"#e60c34"});
				$("#test3").data("drawInfo", {type:"2U", col:1, row:2, text:"Server", color:"#3f3737"});
				$("#test4").data("drawInfo", {type:"3U", col:1, row:3, text:"Server", color:"#3f3737"});
				$("#test5").data("drawInfo", {type:"4U", col:1, row:4, text:"Server", color:"#3f3737"});
				$("#test6").data("drawInfo", {type:"4U", col:1, row:4, text:"Storage", color:"#1e155e"});
				$("#test7").data("drawInfo", {type:"1U", col:1, row:1, text:"Switch", color:"#0c8ce6"});
				$("#test8").data("drawInfo", {type:"1U", col:1, row:1, text:"Patch Panel", color:"#3fbd13"});
				$("#test9").data("drawInfo", {type:"8U", col:1, row:8, text:"LCD Monitor", color:"#a09d93"});
				$("#test10").data("drawInfo", {type:"1U", col:1, row:1, text:"Power Strip", color:"#bd137f"});
				$("#test11").data("drawInfo", {type:"1U", col:1, row:1, text:"UPS", color:"#7b13bd"});
				$("#test12").data("drawInfo", {type:"1U", col:1, row:1, text:"Key Board", color:"#bd8b13"});
			}
		}
		,
		/**
		 * 도면 초기화
		 */
		init : function(option){
			
			this.setElement(option);
			
			if(option.type == "canvas"){
				$("#" + option.targetId).empty().addClass("canvas_area");
				let targetDiv = document.getElementById(option.targetId);
				// table 생성
				let table = document.createElement("table");
					table.id = option.targetId + "Table";
					table.style.height = "100%";
					table.style.width  = "100%";
					
				targetDiv.appendChild(table);
				option.targetObj = $("#" + option.targetId + "Table");
				option.targetElement = document.getElementById(option.targetId + "Table");
				
				this.makeCanvas(option);
			}else if(option.type == "rack"){
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
			
		}
		,
		/**
		 * 캔버스 만들기
		 */
		makeCanvas : function(option){
			
			let targetObj       = option.targetObj;
			const targetElement = option.targetElement; //$("#" + option.targetId);
			const colSize       = option.colSize;       // width
			const rowSize       = option.rowSize;       // height
			
			// 그리기
			for(let row = 0; row < rowSize; row++) {
				let tr = targetElement.insertRow(row);
				
				for(let col = 0; col < colSize; col++) {
					let td = tr.insertCell(col);
					td.classList.add("droptarget");
				}
				
			}
			
			// 라인생성
			const lineTr = targetObj.find("tr");
			const lineTd = lineTr.eq(0).find("td");
			
			for(let i = 0; i < lineTr.length; i++){
				lineTr.eq(i).find("td").eq(0).text(i).removeClass("droptarget").addClass("line_text");
			}
			
			for(let i = 0; i < lineTd.length; i++){
				lineTd.eq(i).text(i).removeClass("droptarget").addClass("line_text");
			}
			
			// 포지션정보 등록
			for(let i = 0; i < lineTr.length; i++){
				let findTd = lineTr.eq(i).find("td");
				for(let j = 0; j < findTd.length; j++){
					findTd.eq(j).data("pos", {row:i, col:j});
				}
			}
			
			// 드래그 & 드랍 이벤트 등록
			this.dragstart(this);
			this.drag();
			this.dragend(option);
			this.dragenter(this, option);
			this.dragover();
			this.drop(option);
			
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
				
				for(let col = 0; col < colSize; col++) {
					let td = tr.insertCell(col);
					td.classList.add("droptarget");
					
//					console.log($(td).context.innerText);
				}
				
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
			document.addEventListener("ondragstart", function(event) {
				event.target.style.opacity = "1";
			});
		}
		,
		dragend : function(option){
			document.addEventListener("dragend", function(event) {
				event.target.style.opacity = "1";
				if(option.type == "canvas"){
					$(".canvas_over_board").removeClass("canvas_over_board");
				}else if(option.type == "rack"){
					$(".rack_over_board").removeClass("rack_over_board");
				}
				
			});
		}
		,
		dragenter : function(_this, option){
			document.addEventListener("dragenter", function(event) {
				if(event.target.className == "droptarget" || event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){
					
					if(option.type == "canvas"){
						$(".canvas_over_board").removeClass("canvas_over_board");
						event.target.className += " canvas_over_board";
						let dataObj = $("#" + _this.dragId).data("drawInfo");
						
						// 가로
						for(let i = 1; i < dataObj.col; i++){
							$(".canvas_over_board").next().addClass("canvas_over_board");
						}
						
						// 세로
						$.each($(".canvas_over_board"), function(){
							for(let i = 1; i < dataObj.row; i++){
								$(this).parents("table").find("tr").eq($(this).context.parentElement.rowIndex + i).find("td").eq($(this).context.cellIndex).addClass("canvas_over_board");
							}
						});
					}else if(option.type == "rack"){
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
					if(option.type == "canvas"){
						$(".canvas_over_board").removeClass("canvas_over_board");
					}else if(option.type == "rack"){
						$(".rack_over_board").removeClass("rack_over_board");
					}
					
				}
			});
		}
		,drop : function(option){
			let _this = this;
			let newNodeIndex = 0;
			document.addEventListener("drop", function(event) {
				event.preventDefault();
				if(event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){
					
					if(option.type == "canvas"){
						
						let targetId = event.dataTransfer.getData("targetId");
						let data = $("#" + targetId).data("drawInfo");
						
						// 충돌여부 체크
						let isCrash = false;
						$.each($(".canvas_over_board"), function(iex, val){
							if($(this).hasClass("canvas_new_node")){
								alert("먼저 생성된 도면과 겹쳐진 생성 입니다.");
								isCrash = true;
								return false;
							}
						});
						
						if($("#canvasDataModal").length > 0){
							alert("정보가 등록되지 않은 상면이 존재합니다.");
							return false;
						}
						
						if(!isCrash){
							// 이미지 element 만들기
							let img = _this.createImg(data);
							let div = _this.createDiv();
								div.appendChild(img);
								div.classList.add("canvas_td_div");
								
								let offsets = $(event.target).offset();
								let top = offsets.top;
								let left = offsets.left;
								
								let addTop  = data.row * 11;
								let addLeft = data.col * 11;
								
								let modalOption = {
													 top: top
													,addTop: addTop
													,left: left
													,addLeft: addLeft
													,target: "newNode" + newNodeIndex
												  };
								// 설치 장비 정보추가
								$.extend(modalOption, data);
								
								_this.addTypeSelectModal(modalOption);
								
						    event.target.appendChild(div);
						    $(".canvas_over_board").addClass("canvas_new_node").addClass("newNode" + newNodeIndex);
							$(".canvas_over_board").removeClass("canvas_over_board");
							_this.addCol(option.targetId, event.target.parentElement.rowIndex, newNodeIndex);
							_this.addRow(option.targetId, event.target.cellIndex, newNodeIndex);
							_this.hideNewNode(newNodeIndex);
							newNodeIndex++;
						}
						
					}else if(option.type == "rack"){
						
						let targetId = event.dataTransfer.getData("targetId");
						let data = $("#" + targetId).data("drawInfo");
						
						// 충돌여부 체크
						let isCrash = false;
						$.each($(".rack_over_board"), function(iex, val){
							if($(this).hasClass("rack_new_node")){
								alert("먼저 등록된 장비와 중복 입니다.");
								isCrash = true;
								return false;
							}
						});
						
						// 충돌여부 체크
						if($(".rack_over_board").length != data.row){
							alert("정확한 위치에 드래그해주세요.");
							isCrash = true;
							return false;
						}
						
						if(!isCrash){
							
							// 이미지 element 만들기
							let div = _this.createDiv();
							
							if(data.row > 1){
								div.classList.add("rack_td_div");
							}else{
								div.classList.add("rack_td_div1");
							}
								
						    event.target.appendChild(div);
						    $(".rack_over_board").addClass("rack_new_node").addClass("newNode" + newNodeIndex);
							$(".rack_over_board").removeClass("rack_over_board");
							$(".newNode" + newNodeIndex).css({"background-color": data.color, "color": "#ffffff"});
							if(data.row > 1){
								$(".newNode" + newNodeIndex).find("div").css("height", "30px").html("<span class=\"unit_raund\">" + data.type + "</span><span class=\"unit_text\">" + data.text + "</span>");
							}else{
								$(".newNode" + newNodeIndex).find("div").html("<span class=\"unit_raund rauter_small\">" + data.type + "</span><span class=\"unit_text\">" + data.text + "</span>");
							}
							
							_this.addRow(option.targetId, event.target.cellIndex, newNodeIndex);
							_this.hideNewNode(newNodeIndex);
							newNodeIndex++;
						}
						
					}
				}
			});
		}
		,
		/**
		 * div element 만들기
		 */
		createDiv : function(){
			
			return document.createElement("div");
		}
		,
		/**
		 * 이미지 element 만들기
		 */
		createImg : function(data){
			
			let img = document.createElement("img");
				img.src = data.img;
//				img.style.cursor = "pointer";
				img.width  = "15";
				img.height = "15";
				
//			let inputImg = document.createElement("input");
//				inputImg.setAttribute("type", "image");
//				inputImg.setAttribute("src", data.img);
				
			return img;
		}
		,
		/**
		 * 행 합치기
		 */
		addCol : function(targetId, colIdx, newNodeIdx){
			let _this = $("#" + targetId);
			_this.each(function(){
				let colspan = 0;
				$("tr", this).filter(":eq(" + colIdx +")").each(function(row) {
					$(this).find("td").filter(':visible').each(function(col) {
//						console.log(newNodeIdx, $(this).hasClass("newNode" + newNodeIdx), $(this).attr("class"));
						if($(this).hasClass("newNode" + newNodeIdx)){
							colspan++;
							if(colspan == 1){
								$(this).find(".canvas_td_div").css("width", (colspan * 20) + "px")
								$(this).attr("colspan", colspan);
							}else{
								$(".newNode" + newNodeIdx).eq(0).find(".canvas_td_div").css("width", (colspan * 20) + "px");
								$(".newNode" + newNodeIdx).eq(0).attr("colspan", colspan);
							}
							
						}
					});
				});
			});

		}
		,
		/**
		 * 열 합치기
		 */
		addRow : function(targetId, rowIdx, newNodeIdx){
			let _this = $("#" + targetId);
			_this.each(function(){
				let rowspan = 0;
				$("tr", this).each(function(row){
					$("td",this).eq(rowIdx).filter(':visible').each(function(col){
//						console.log($(this).hasClass("newNode" + newNodeIdx), $(this).attr("class"));
						if($(this).hasClass("newNode" + newNodeIdx)){
							rowspan++;
							if(rowspan == 1){
								$(this).attr("rowspan", rowspan);
							}else{
								$(".newNode" + newNodeIdx).eq(0).attr("rowspan", rowspan);
							}
							
						}
					});
				});
			});
		}
		,
		/**
		 * 노드 숨기기
		 */
		hideNewNode : function(nodeId){
			$.each($(".newNode" + nodeId), function(idx, val){
				if(idx != 0){
					$(this).removeClass("newNode" + nodeId).addClass("hideNode" + nodeId).hide();
				}
			});
		}
		,
		/**
		 * 삭제 또는 모달창 띄우기
		 */
		delConfirmModal : function(option){
			$("#delConfirmModal").remove();
			let _this = this;
			let html = [];
				html.push("<div class=\"ax5-ui-picker direction-top\" id=\"delConfirmModal\" data-picker-els=\"root\" style=\"top: " + (option.top + option.addTop + 31) + "px; left: " + (option.left + option.addLeft - 95) + "px;\">");
				html.push("<div class=\"ax-picker-body\">");
				html.push("<div class=\"ax-picker-content\" data-picker-els=\"content\" style=\"width:180px;\">");
				html.push("<div class=\"ax-picker-buttons\" style=\"padding: 0px 0px 3px 0px;\">");
				html.push("<button data-picker-btn=\"popup\" class=\"btn btn-default btn-primary\">Rack관리 팝업</button>");
				html.push("<button data-picker-btn=\"cancel\" class=\"btn btn-default btn-danger\">삭제</button>");
				html.push("</div>");
				html.push("</div>");
				html.push("<div class=\"ax-picker-arrow\"></div>");
				html.push("</div>");
				html.push("</div>");
				
			$("body").append(html.join(""));
			
			// 윈도우 사이즈가 바뀔경우 위치 조정
			$(window).resize(function(){
				let offsets  = $("." + option.target).offset();
				let top  = offsets.top + option.addTop;
				let left = offsets.left + option.addLeft;
				$("#delConfirmModal").css({top:(top + 31) + "px", left:(left - 95) + "px"})
			});
			
			// 팝업버튼
			$("[data-picker-btn=\"popup\"]").unbind().bind("click", function(){
				let popupOption = {};
					popupOption.title = "Rack 관리";
					popupOption.url   = "/admin/rscr/rscr0402Pop.do";
					
				popup.open(popupOption);
				$("#delConfirmModal").remove();
			});
			
			// 삭제버튼
			$("[data-picker-btn=\"cancel\"]").unbind().bind("click", function(){
				
				const target = option.target;
				const hideTarget = option.target.replace("newNode", "")
				$("." + target).empty().removeClass("canvas_new_node newNode" + target).removeAttr("colspan").removeAttr("rowspan");
				$(".hideNode" + hideTarget).removeClass("canvas_new_node hideNode" + hideTarget).removeAttr("style");
				$("#delConfirmModal").remove();
				
			});
			
		}
		,
		/**
		 * 신규등록 여부와 데이터 가져올지 여부
		 */
		addTypeSelectModal : function(option){
			
			let _this = this;
			
			let html = [];
				html.push("<div class=\"ax5-ui-picker direction-top\" id=\"canvasConfirmModal\" data-picker-els=\"root\" style=\"top: " + (option.top + option.addTop + 27) + "px; left: " + (option.left + option.addLeft - 44) + "px;\">");
				html.push("<div class=\"ax-picker-body\">");
				html.push("<div class=\"ax-picker-content\" data-picker-els=\"content\" style=\"width:70px;\">");
				html.push("<div class=\"ax-picker-buttons\" style=\"padding: 0px 0px 3px 0px;\">");
				html.push("<button data-draw-btn=\"newBtn\" class=\"btn btn-primary btn-sm\" style=\"margin-bottom: 3px;width: 70px;\">신규</button>");
				html.push("<button data-draw-btn=\"importBtn\" class=\"btn btn-success btn-sm\" style=\"margin-bottom: 3px;\">가져오기</button>");
				html.push("<button data-draw-btn=\"deleteBtn\" class=\"btn btn-danger btn-sm\" style=\"width: 70px;\">삭제</button>");
				html.push("</div>");
				html.push("</div>");
				html.push("<div class=\"ax-picker-arrow\"></div>");
				html.push("</div>");
				html.push("</div>");
				
			$("body").append(html.join(""));
			
			// 윈도우 사이즈가 바뀔경우 위치 조정
			$(window).resize(function(){
				let offsets  = $("." + option.target).offset();
				let top  = offsets.top + option.addTop;
				let left = offsets.left + option.addLeft;
				console.log(top, left);
				$("#canvasConfirmModal").css({top:(top + 27) + "px", left:(left - 44) + "px"});
			});
			
			// 신규 버튼 클릭시 상면 등록 화면 호출
			$("[data-draw-btn=\"newBtn\"]").unbind().bind("click", function(){
				console.log(option);
				console.log(option);
				
				let params = {};
					params.centerCd     = $("#searchCenterCd").val(); // 센터 코드
					params.zoneCd       = $("#searchZoneCd").val();   // ZONE 코드
					params.sanSectionCd = option.type;                // 상면구분 코드
				
				// 상면생성팝업
				$(this).popup({
					 title : "상면생성"
					,width : "61%"
					,height: "60%"
					,params : params
					,url : "/admin/rscr/rscr0405Pop.do"
					,callBackFn : function(data){
						// 취소버튼을 눌렀을 경우
						if(data.type == "cancel"){
							const target = option.target;
							const hideTarget = option.target.replace("newNode", "");
							$("." + target).empty().removeClass("canvas_new_node newNode" + target).removeAttr("colspan").removeAttr("rowspan");
							$(".hideNode" + hideTarget).removeClass("canvas_new_node hideNode" + hideTarget).removeAttr("style");
						}
						
						// 상면이 생성됐을 경우
						if(data.type == "success"){
							const target = option.target;
							$("." + target).find("div").attr("id", "San_" + data.sanNo); // 상면번호 맵핑
							
							// 툴팁 생성
							$("." + target).find("div").addClass("btn").append("<font style=\"font-size:12px;color: #ff0000;font-weight: bold;\">상면</font>");
							$("." + target).find("div").attr("data-toggle", "tooltip");
							$("." + target).find("div").attr("data-placement", "right");
							$("." + target).find("div").attr("title", data);
							
							// 툴팁 클릭이벤트
							$("." + target).find("div").unbind().bind("click", function(){
								_this.delConfirmModal(option);
							});
							
							$("[data-toggle=\"tooltip\"]").tooltip();
							
						}
						
					}
				});
				// 버튼영역 삭제
				$("#canvasConfirmModal").remove();
			});
			
			// 가져오기 버튼 클릭시 등록된 기존 리스트 가져오기
			$("[data-draw-btn=\"importBtn\"]").unbind().bind("click", function(){
				$("#canvasConfirmModal").remove();
			});
			
			// 팝업삭제 및 상면 삭제
			$("[data-draw-btn=\"deleteBtn\"]").unbind().bind("click", function(){
				const target = option.target;
				const hideTarget = option.target.replace("newNode", "")
				$("." + target).empty().removeClass("canvas_new_node newNode" + target).removeAttr("colspan").removeAttr("rowspan");
				$(".hideNode" + hideTarget).removeClass("canvas_new_node hideNode" + hideTarget).removeAttr("style");
				$("#canvasConfirmModal").remove();
			});
			
		}
		,
		/**
		 * 데이터등록모달
		 */
		putDataModal : function(option){
			
			let _this = this;
			
			let html = [];
				html.push("<div class=\"ax5-ui-picker direction-top\" id=\"canvasDataModal\" data-picker-els=\"root\" style=\"top: " + (option.top + option.addTop + 27) + "px; left: " + (option.left + option.addLeft - 66) + "px;\">");
				html.push("<div class=\"ax-picker-body\">");
				html.push("<div class=\"ax-picker-content\" data-picker-els=\"content\" style=\"width:120px;\">");
				html.push("<form style=\"padding:0 5px;\" id=\"canvasDataModalForm\">");
				html.push("<div class=\"form-group\" style=\"margin-bottom: 8px;\">");
				html.push("<label for=\"exampleInputWidth\">정보입력</label>");
				html.push("<input type=\"input\" class=\"form-control\" id=\"drawInfo\" name=\"drawInfo\" placeholder=\"정보입력\">");
				html.push("</div>");
				html.push("</form>");
				html.push("<div class=\"ax-picker-buttons\" style=\"padding: 0px 0px 3px 0px;\">");
				html.push("<button data-picker-btn=\"ok\" class=\"btn btn-default btn-primary\">등록</button>");
				html.push("</div>");
				html.push("</div>");
				html.push("<div class=\"ax-picker-arrow\"></div>");
				html.push("</div>");
				html.push("</div>");
				
			$("body").append(html.join(""));
			
			// 윈도우 사이즈가 바뀔경우 위치 조정
			$(window).resize(function(){
				let offsets  = $("." + option.target).offset();
				let top  = offsets.top + option.addTop;
				let left = offsets.left + option.addLeft;
				console.log(top, left);
				$("#canvasDataModal").css({top:(top + 27) + "px", left:(left - 66) + "px"})
			});
			
			inpuDataClick = function(){
				const target = option.target;
				let data = $("#canvasDataModalForm").find("#drawInfo").val();
				
				if(data == ""){
					alert("입력하시지 않으셨습니다.");
					return false;
				}
				
				$("." + target).find("div").addClass("btn").append("<font style=\"font-size:12px;color: #ff0000;font-weight: bold;\">" + data + "</font>");
				
				$("." + target).find("div").attr("data-toggle", "tooltip");
				$("." + target).find("div").attr("data-placement", "right");
				$("." + target).find("div").attr("title", data);
				
				// 
				$("." + target).find("div").unbind().bind("click", function(){
					
					_this.delConfirmModal(option);

				});
				
				$("[data-toggle=\"tooltip\"]").tooltip();
				$("#canvasDataModal").remove();
			}
			
			$("#canvasDataModalForm").find("#drawInfo").unbind().bind("keydown", function(){
				if(event.keyCode == 13){
					inpuDataClick();
				}
			}).focus();
			
			$("[data-picker-btn=\"ok\"]").unbind().bind("click", function(){
				
				inpuDataClick();
				
			});
			
		}
		
}

popup = {
		open : function(option){
			const _this = this;
//			mask.open();
			let popupHtml = [];
			let width = option.width;
			let height = option.height;
			let scrolling = option.scrolling; 
			if(scrolling == "undefined"){ scrolling == "no";}
			
			popupHtml.push("<div class=\"modalpopup\">");
			popupHtml.push("<div class=\"modalcontens\" style=\"width:" + width + ";height:" + height + ";\">");
			popupHtml.push("<h2>" + option.title + "</h2>");
			popupHtml.push("<iframe src=\"" + option.url + "\" width=\"100%\" height=\"100%\"  frameborder=\"0\" scrolling=\"" + scrolling + "\" ></iframe>");
			popupHtml.push("<a href=\"javascript:void(0)\"><div class=\"close\"></div></a>");
			popupHtml.push("</div>");
			popupHtml.push("</div>");
			
			$(".wrap").append(popupHtml.join(""));
			
			$(".modalpopup").find(".close").unbind().bind("click", function(){
				_this.close();
			});
		    
		}
		,
		close : function(){
			mask.close();
			$(".modalpopup").remove();
		}
}
