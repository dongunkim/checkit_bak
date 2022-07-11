
var diagram;
fool = {
		/**
		 * 도면 초기화
		 */
		init : function(option){

			let AllowTopLevel = true;
			let CellSize = new canvas.Size(30, 30);

			let $ = canvas.GraphObject.make;
			test = canvas;

			diagram =
				$(canvas.Diagram, option.targetId,
						{
							 minScale: 0.25
							,grid: $(canvas.Panel, "Grid",
									 { gridCellSize: CellSize }
									,$(canvas.Shape, "LineH", { stroke: "gray", strokeWidth: .5 })
									,$(canvas.Shape, "LineH", { stroke: "darkslategray", strokeWidth: 1.5, interval: 10 })
									,$(canvas.Shape, "LineV", { stroke: "gray", strokeWidth: .5 })
									,$(canvas.Shape, "LineV", { stroke: "darkslategray", strokeWidth: 1.5, interval: 10 })
							),
							 "draggingTool.isGridSnapEnabled": true
							,"draggingTool.gridSnapCellSpot": canvas.Spot.Center
							,"resizingTool.isGridSnapEnabled": true
							,allowDrop: true
							,ModelChanged: function(e) {
								if(e.isTransactionFinished) {
									document.getElementById("savedModel").textContent = diagram.model.toJson();
								}
							}
							,"animationManager.isEnabled": false
							,"undoManager.isEnabled": true
						});

			let rightElement = document.getElementById("rightMenu");

			let myRightMenu = $(canvas.HTMLInfo, {
				 show: showContextMenu
				,mainElement: rightElement
				});

			diagram.nodeTemplate =
				$(canvas.Node
						,"Auto",
						{
							 contextMenu: myRightMenu
							,resizable: true
							,resizeObjectName: "SHAPE"
							,locationSpot: new canvas.Spot(0, 0, CellSize.width / 2, CellSize.height / 2)
							,mouseDragEnter: function(e, node) {
								e.handled = true;
								node.findObject("SHAPE").fill = "red";
								highlightGroup(node.containingGroup, false);
							}
							,mouseDragLeave: function(e, node) {
								node.updateTargetBindings();
							}
							,mouseDrop: function(e, node) {
								node.diagram.commandHandler.deleteSelection();
		//						node.diagram.currentTool.doCancel();
							}
							,doubleClick: function(e, node){
								console.log(e);
								console.log(node.data);
							}
						}
						,new canvas.Binding("position", "pos", canvas.Point.parse).makeTwoWay(canvas.Point.stringify)
						,$(canvas.Shape
								, "Rectangle"
								,{
									 name: "SHAPE"
									,fill: "white"
									,cursor: "pointer"
									,stroke: "black"
									,strokeWidth: 2
		//							,minSize: CellSize
		//							,desiredSize: CellSize
								}
								,new canvas.Binding("fill", "color")
								,new canvas.Binding("desiredSize", "size", canvas.Size.parse).makeTwoWay(canvas.Size.stringify)
						)
						,$(canvas.TextBlock,
								{
								 alignment: canvas.Spot.Center
								,font: "bold 10px sans-serif"
								,margin: 6
								,isMultiline: false
		//						,editable: true
								},
								new canvas.Binding("text", "text").makeTwoWay())
				);

			function highlightGroup(grp, show) {
				if (!grp) return;
				if (show) {
					let tool = grp.diagram.toolManager.draggingTool;
					let map = tool.draggedParts || tool.copiedParts;  // this is a Map
					if (grp.canAddMembers(map.toKeySet())) {
						grp.isHighlighted = true;
						return;
					}
				}
				grp.isHighlighted = false;
			}

			let groupFill = "rgba(128,128,128,0.2)";
			let groupStroke = "gray";
			let dropFill = "rgba(128,255,255,0.2)";
			let dropStroke = "red";

			diagram.groupTemplate =
				$(canvas.Group, {
							 contextMenu: myRightMenu
							,resizable: true
							,resizeObjectName: "SHAPE"
							,locationSpot: new canvas.Spot(0, 0, CellSize.width/2, CellSize.height/2)
							}
				,new canvas.Binding("position", "pos", canvas.Point.parse).makeTwoWay(canvas.Point.stringify)
				,{
					 mouseDragEnter: function(e, grp, prev) { highlightGroup(grp, true); }
					,mouseDragLeave: function(e, grp, next) { highlightGroup(grp, false); }
					,mouseDrop: function(e, grp) {
						let ok = grp.addMembers(grp.diagram.selection, true);
						if (!ok) grp.diagram.currentTool.doCancel();
					}
				}
				,$(canvas.Shape, "Rectangle",{name: "SHAPE", fill: groupFill, stroke: groupStroke, minSize: new canvas.Size(CellSize.width*2, CellSize.height*2)}
				,new canvas.Binding("desiredSize", "size", canvas.Size.parse).makeTwoWay(canvas.Size.stringify)
				,new canvas.Binding("fill", "isHighlighted", function(h) { return h ? dropFill : groupFill; }).ofObject()
				,new canvas.Binding("stroke", "isHighlighted", function(h) { return h ? dropStroke: groupStroke; }).ofObject())
				,$(canvas.TextBlock,
								{
								 alignment: canvas.Spot.Center
								,font: "bold 16px sans-serif"
								,margin: 0
								,isMultiline: false
		//						,editable: true
								},
								new canvas.Binding("text", "text").makeTwoWay())
				);

			diagram.commandHandler.memberValidation = function(grp, node) {
				if(grp instanceof canvas.Group && node instanceof canvas.Group){
					return false;
				}
				return true;
			};

			diagram.mouseDragOver = function(e) {
				if (!AllowTopLevel) {
					if (!e.diagram.selection.all(function(p) { return p instanceof canvas.Group; })) {
						e.diagram.currentCursor = "not-allowed";
					}
				}
			};

			diagram.mouseDrop = function(e) {
				if (AllowTopLevel) {
					if (!e.diagram.commandHandler.addTopLevelParts(e.diagram.selection, true)) {
						e.diagram.currentTool.doCancel();
					}
				}else{
					if (!e.diagram.selection.all(function(p) { return p instanceof canvas.Group; })) {
						e.diagram.currentTool.doCancel();
					}
				}
			};

			dragItem = $(canvas.Palette, "dragItem", {
												 nodeTemplate: diagram.nodeTemplate
												,groupTemplate: diagram.groupTemplate
												,layout: $(canvas.GridLayout)
				});

			let green  = "rgb(51, 51, 51)";
			let blue   = "#81D4FA";
			let yellow = "#FFEB3B";
			let gray   = "#808080";
			let red    = "#f44336";
			let white  = "#ffffff";

			dragItem.model = new canvas.GraphLinksModel([
														 { text: "랙", color: white, size: "60 30", sanSectionCd: "310001", no: null, sanNo: null}
														,{ text: "케이지", isGroup: true, size: "90 90", sanSectionCd: "310002", no: null, sanNo: null}
														,{ text: "정류기", color: white, size: "60 30", sanSectionCd: "310004", no: null, sanNo: null }
														,{ text: "분전반", color: white, size: "60 30", sanSectionCd: "310005", no: null, sanNo: null }
														,{ text: "냉방기", color: white, size: "60 30", sanSectionCd: "310006", no: null, sanNo: null }
														,{ text: "축전지", color: white, size: "60 30", sanSectionCd: "310007", no: null, sanNo: null }
														,{ text: "S/UPS", color: white, size: "60 30", sanSectionCd: "310008", no: null, sanNo: null }
														,{ text: "DUPS", color: white, size: "60 30", sanSectionCd: "310009", no: null, sanNo: null }
														]);

			diagram.contextMenu = myRightMenu;

			rightElement.addEventListener("contextmenu", function(e) {
			    e.preventDefault();
			    return false;
			  }, false);

			function showContextMenu(obj, diagram, tool) {

				if(obj !== null){
					let cmd = diagram.commandHandler;
					console.log(obj.data);
					// 신규일경우
					if(obj.data.no === null){
						// 상면 캐이지일경우 가져오기, 삭제 버튼 보임
						if(obj.data.sanSectionCd == "310001" || obj.data.sanSectionCd == "310002"){
							document.getElementById("sanSearchPopBtn").style.display = "block";
						}else{
							document.getElementById("addInfoPop").style.display = "block";
						}
					}else{
						// 상면 캐이지일경우 가져오기, 삭제 버튼 보임
						if(obj.data.sanSectionCd == "310001" || obj.data.sanSectionCd == "310002"){
							document.getElementById("useInfoBtn").style.display = "block";
							document.getElementById("updateInfoBtn").style.display = "block";
							document.getElementById("rackInfoPopBtn").style.display = "block";
						}else{
							document.getElementById("useInfoBtn").style.display = "block";
						}
					}

					document.getElementById("deleteBtn").style.display	= "block";
					rightElement.style.display = "block";
					let mousePt = diagram.lastInput.viewPoint;
						rightElement.style.left = (mousePt.x + 150) + "px";
						rightElement.style.top  = (mousePt.y + 150) + "px";
				}

			}

		}

}