//css 및 js 동적 추가
canvas = {
		setElement : function(option){
			// 랙 : 310001, 케이지 : 310002, 오피스 : 310003
			$("#d_rack").data("drawInfo", {type:"310001", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:1});
			$("#d_cage").data("drawInfo", {type:"310002", img:'/resources/admin/images/photo/sample_icon_1.png', col:4, row:4});
			$("#type2").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:2});
			$("#type3").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:1, row:2});
			$("#type4").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:3, row:5});
			$("#type5").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:9});
			$("#type6").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:2, row:5});
			$("#type7").data("drawInfo", {type:"", img:'/resources/admin/images/photo/sample_icon_2.png', col:5, row:14});
		}
		,
		/**
		 * 도면 초기화
		 */
		init : function(option){
			
			this.setElement(option);
			
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
			this.dragStart(this);
			this.drag();
			this.dragEnd(option);
			this.dragEnter(this, option);
			this.dragOver();
			this.drop(option);
			
		}
		,dragStart : function(_this){
			document.addEventListener("dragstart", function(event){
				let centerCd = $("#searchCenterCd").val(); // 센터 코드
				let zoneCd   = $("#searchZoneCd").val();   // ZONE 코드
				if(centerCd == "" || zoneCd == ""){
					event.preventDefault();
				}
				
				if(event.target.localName === "img"){
					event.preventDefault();
				}
				
				_this.dragId = event.target.id;
				
				console.log(_this.dragId);
				event.dataTransfer.setData("targetId", event.target.id);
				event.target.style.opacity = "0.4";
			});
		}
		,
		drag : function(){
			document.addEventListener("ondragstart", function(event){
				event.target.style.opacity = "1";
			});
		}
		,
		dragEnd : function(option){
			document.addEventListener("dragend", function(event) {
				event.target.style.opacity = "1";
				$(".canvas_over_board").removeClass("canvas_over_board");
			});
		}
		,
		dragEnter : function(_this, option){
			document.addEventListener("dragenter", function(event) {
				if(event.target.className == "droptarget" || event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){
					
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
							$(_this).parents("table").find("tr").eq($(_this).context.parentElement.rowIndex + i).find("td").eq($(_this).context.cellIndex).addClass("canvas_over_board");
						}
					});
					
				}
			});
		}
		,dragOver : function(){
			document.addEventListener("dragover", function(event) {
				event.preventDefault();
			});
		}
		,dragLeave : function(option){
			document.addEventListener("dragleave", function(event) {
				if(event.target.className == "droptarget") {
					$(".canvas_over_board").removeClass("canvas_over_board");
				}
			});
		}
		,drop : function(){
			document.addEventListener("drop", function(event) {
				event.preventDefault();
				if(event.target.className == "droptarget canvas_over_board" || event.target.className == "droptarget rack_over_board"){
					canvas.modalAllClose();
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
						
						let len = $(".canvas_td_div").length;
						let divTargetId = "newNode" + len;
						
						// 이미지 element 만들기
//						let img = canvas.createImg(data);
						let div = canvas.createDiv();
//							div.appendChild(img);
							div.classList.add("canvas_td_div");
							div.style.width = "100%";
							// 상면 기본정보 set
							let offsets = $(event.target).offset();
							let top     = offsets.top;
							let left    = offsets.left;
							
							let addTop  = data.row * 11;
							let addLeft = data.col * 11;
							
							let modalOption = {
												 top: top
												,addTop: addTop
												,left: left
												,addLeft: addLeft
												,target: divTargetId
											  };
							// 설치 장비 정보추가
							$.extend(modalOption, data);
							canvas.addBtnModal(modalOption);
							
					    event.target.appendChild(div);
					    $(".canvas_over_board").addClass("canvas_new_node").addClass(divTargetId);
						$(".canvas_over_board").removeClass("canvas_over_board");
						canvas.addCol("canvasArea", event.target.parentElement.rowIndex, divTargetId);
						canvas.addRow("canvasArea", event.target.cellIndex, divTargetId);
						canvas.hideNewNode(len);
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
				
			return img;
		}
		,
		/**
		 * 행 합치기
		 */
		addCol : function(targetId, colIdx, divTargetId){
			let _this = $("#" + targetId);
			_this.each(function(){
				let colspan = 0;
				$("tr", this).filter(":eq(" + colIdx +")").each(function(row) {
					$(this).find("td").filter(':visible').each(function(col) {
						if($(this).hasClass(divTargetId)){
							colspan++;
							if(colspan == 1){
//								$(this).find(".canvas_td_div").css("width", "100%")
								$(this).attr("colspan", colspan);
							}else{
//								$("." + divTargetId).eq(0).find(".canvas_td_div").css("width", "100%");
								$("." + divTargetId).eq(0).attr("colspan", colspan);
							}
							
						}
					});
				});
			});
//			$(".newNode" + newNodeIdx).removeClass(".newNode" + newNodeIdx);
		}
		,
		/**
		 * 열 합치기
		 */
		addRow : function(targetId, rowIdx, divTargetId){
			let _this = $("#" + targetId);
			_this.each(function(){
				let rowspan = 0;
				$("tr", this).each(function(row){
					$("td",this).eq(rowIdx).filter(':visible').each(function(col){
						if($(this).hasClass("" + divTargetId)){
							rowspan++;
							if(rowspan == 1){
								$(this).attr("rowspan", rowspan);
							}else{
								$("." + divTargetId).eq(0).attr("rowspan", rowspan);
							}
							
						}
					});
				});
			});
//			$(".newNode" + newNodeIdx).removeClass(".newNode" + newNodeIdx);
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
		 * 신규등록 여부와 데이터 가져올지 여부
		 */
		addBtnModal : function(option){
			
			let _this = this;
			
			let html = [];
				html.push("<div class=\"ax5-ui-picker direction-top\" id=\"canvasConfirmModal\" data-picker-els=\"root\" style=\"top: " + (option.top + option.addTop + 27) + "px; left: " + (option.left + option.addLeft - 44) + "px;\">");
				html.push("<div class=\"ax-picker-body\">");
				html.push("<div class=\"ax-picker-content\" data-picker-els=\"content\" style=\"width:71px;\">");
				html.push("<div class=\"ax-picker-buttons\" style=\"padding: 0px 0px 3px 0px;\">");
				/*html.push("<button data-draw-btn=\"newBtn\" class=\"btn btn-primary btn-sm\" style=\"margin-bottom: 3px;width: 72px;\">신규</button>");*/
				html.push("<button data-draw-btn=\"importBtn\" class=\"btn btn-success btn-sm\" style=\"margin-bottom: 3px;\">가져오기</button>");
				html.push("<button data-draw-btn=\"deleteBtn\" class=\"btn btn-danger btn-sm\" style=\"width: 72px;\">삭제</button>");
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
				$("#canvasConfirmModal").css({top:(top + 27) + "px", left:(left - 44) + "px"});
			});
			
			// 신규 버튼 클릭시 상면 등록 화면 호출
//			$("[data-draw-btn=\"newBtn\"]").unbind().bind("click", function(){
//				
//				let params = {};
//					params.centerCd     = $("#searchCenterCd").val(); // 센터 코드
//					params.zoneCd       = $("#searchZoneCd").val();   // ZONE 코드
//					params.sanSectionCd = option.type;                // 상면구분 코드
//				
//				// 상면생성팝업
//				$(this).popup({
//					 title : "상면생성"
//					,width : "61%"
//					,height: "60%"
//					,params : params
//					,url : "/admin/rscr/rscr0405Pop.do"
//					,callBackFn : function(data){
//						// 취소버튼을 눌렀을 경우
//						if(data.type == "cancel"){
//							const target = option.target;
//							const hideTarget = target.replace("newNode", "");
//							$("." + target).empty().removeClass("canvas_new_node newNode" + target).removeAttr("colspan").removeAttr("rowspan");
//							$(".hideNode" + hideTarget).removeClass("canvas_new_node hideNode" + hideTarget).removeAttr("style");
//						}
//						
//						// 상면이 생성됐을 경우
//						if(data.type == "success"){
//							const target = option.target;
//							$("." + target).find("div").attr("id", "San_" + data.sanNo); // 상면번호 맵핑
//							
//							// 툴팁 생성
//							$("." + target).find("div").addClass("btn").append("<font style=\"font-size:12px;color: #ff0000;font-weight: bold;\">상면</font>");
//							$("." + target).find("div").attr("data-toggle", "tooltip");
//							$("." + target).find("div").attr("data-placement", "right");
//							$("." + target).find("div").attr("title", data);
//							
//							// 툴팁 클릭이벤트
//							$("." + target).find("div").unbind().bind("click", function(){
//								$("#canvasConfirmModal").hide();
//								_this.clickModal(option);
//							});
//							
//							$("[data-toggle=\"tooltip\"]").tooltip();
//							
//						}
//						
//					}
//				});
//				// 버튼영역 삭제
//				$("#canvasConfirmModal").remove();
//			});
			
			// 가져오기 버튼 클릭시 등록된 기존 리스트 가져오기
			$("[data-draw-btn=\"importBtn\"]").unbind().bind("click", function(){
				
				let params = {};
					params.centerCd     = $("#searchCenterCd").val(); // 센터 코드
					params.zoneCd       = $("#searchZoneCd").val();   // ZONE 코드
					params.sanSectionCd = option.type;                // 상면구분 코드
					
				// 상면생성팝업
				$(this).popup({
					 title : "상면조회"
					,width : "61%"
					,height: "70%"
					,params : params
					,url : "/admin/rscr/rscr1206Pop.do"
					,callBackFn : function(data){
						// 취소버튼을 눌렀을 경우
						if(data.type == "cancel"){
							const target = option.target;
							const hideTarget = target.replace("newNode", "hideNode");
							$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
							$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
						}
						
						// 상면을 선택했을경우
						if(data.type == "success"){
							
							// 랙 상면설치(렉이면서 설치가 안된경우)
							if(data.sanSectionCd == "310001" && data.setYn == "N"){
								$(this).popup({
									 title : "상면설치"
									,width : "61%"
									,height: "60%"
									,url : "/admin/rscr/rscr0503Pop.do"
									,params : {sanNo: data.sanNo, sanPage:"Y"}
									,callBackFn : function(data){
										if(data.type == "cancel"){
											const target = option.target;
											const hideTarget = target.replace("newNode", "hideNode");
											$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
											$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
										}
									}
								});
							}
							
							// 랙 상면 해체/수정(렉이면서 설치가 된)
							if(data.sanSectionCd == "310001" && data.setYn == "Y"){
								$(this).popup({
									 title : "상면 해체/수정"
									,width : "61%"
									,height: "60%"
									,url : "/admin/rscr/rscr0504Pop.do"
									,params : {sanNo: data.sanNo, sanPage:"Y"}
									,callBackFn : function(data){
										if(data.type == "cancel"){
											const target = option.target;
											const hideTarget = target.replace("newNode", "hideNode");
											$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
											$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
										}
									}
								});
							}
							
							// 케이지 상면 설치(케이지이면서 설치가 안된경우)
							if(data.sanSectionCd == "310002" && data.setYn == "N"){
								$(this).popup({
									 title : "상면설치"
									,width : "61%"
									,height: "60%"
									,url : "/admin/rscr/rscr0505Pop.do"
									,params : {sanNo: data.sanNo, sanPage:"Y"}
									,callBackFn : function(data){
										if(data.type == "cancel"){
											const target = option.target;
											const hideTarget = target.replace("newNode", "hideNode");
											$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
											$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
										}
									}
								});
							}
							
							// 케이지 상면 해체/수정(케이지이면서 설치가 된)
							if(data.sanSectionCd == "310002" && data.setYn == "Y"){
								$(this).popup({
									 title : "상면 해체/수정"
									,width : "61%"
									,height: "60%"
									,url : "/admin/rscr/rscr0506Pop.do"
									,params : {sanNo: data.sanNo, sanPage:"Y"}
									,callBackFn : function(data){
										if(data.type == "cancel"){
											const target = option.target;
											const hideTarget = target.replace("newNode", "hideNode");
											$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
											$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
										}
									}
								});
							}
							
							const target = option.target;
							$("." + target).find("div").attr("id", "San_" + data.sanNo); // 상면번호 맵핑

							// 툴팁 생성
							$("." + target).find("div").addClass("btn").append("<font style=\"font-size:12px;color: #ff0000;font-weight: bold;\">" + data.no + "</font>");
							$("." + target).find("div").attr("data-toggle", "tooltip");
							$("." + target).find("div").attr("data-placement", "right");
							$("." + target).find("div").attr("title", data.no);
							
							// 툴팁 클릭이벤트
							$("." + target).find("div").unbind().bind("click", function(){
								if($("#canvasConfirmModal").length > 0){
									_this.modalAllClose();
									return false;
								}
								option.sanNo = data.sanNo;
								_this.clickModal(option);
							});
							
							$("[data-toggle=\"tooltip\"]").tooltip();
							
						}
						
					}
				});
				
				$("#canvasConfirmModal").remove();
			});
			
			// 팝업삭제 및 상면 삭제
			$("[data-draw-btn=\"deleteBtn\"]").unbind().bind("click", function(){
				const target = option.target;
				const hideTarget = option.target.replace("newNode", "hideNode");
				$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
				$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
				$("#canvasConfirmModal").remove();
			});
			
		}
		,
		/**
		 * 삭제, Rack관리 팝업띄우기
		 */
		clickModal : function(option){
			this.modalAllClose();
			let _this = this;
			let html = [];
				html.push("<div class=\"ax5-ui-picker direction-top\" id=\"canvasConfirmModal\" data-picker-els=\"root\" style=\"top: " + (option.top + option.addTop + 31) + "px; left: " + (option.left + option.addLeft - 95) + "px;\">");
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
				$("#canvasConfirmModal").css({top:(top + 31) + "px", left:(left - 95) + "px"})
			});
			
			// 팝업버튼
			$("[data-picker-btn=\"popup\"]").unbind().bind("click", function(){
				$(this).popup({
					 title : "Rack 관리"
					,width : "1600px"
					,height: "71%"
					,addClass: "meeting"
					,url : "/admin/rscr/rscr0407Pop.do"
					,params : {sanNo: option.sanNo}
					,scrolling : "auto"
				});
				$(".modalcontens").css("left", "50%");
				$("#canvasConfirmModal").remove();
			});
			
			// 삭제버튼
			$("[data-picker-btn=\"cancel\"]").unbind().bind("click", function(){
				const target = option.target;
				const hideTarget = option.target.replace("newNode", "hideNode");
				$("." + target).empty().removeClass("canvas_new_node " + target).removeAttr("colspan").removeAttr("rowspan");
				$("." + hideTarget).removeClass("canvas_new_node " + hideTarget).removeAttr("style");
				$("#canvasConfirmModal").remove();
				
			});
			
		}
		,modalAllClose : function(){
			$.each($("#canvasConfirmModal"), function(idx, data){
				$(this).remove();
			})
		}
		
}