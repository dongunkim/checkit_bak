//---------------------------------------------
// 전역변수 세팅
//---------------------------------------------
let params,
url,
root,
tempHeigher,
idx = 0,
addCodeArray = [],
delCodeArray = [],
updCodeArray = [],
strHtml;
initFunction = function(){

	setGridFn();

	eventFunction();
}
//---------------------------------------------
//클릭이벤트
//---------------------------------------------
eventFunction = function(){

	//---------------------------------------------
	// 검색 엔터 이벤트
	//---------------------------------------------
	$("#searchKeyword").unbind().on("keydown", function(event){
		if(event.keyCode == 13){
			let param = {};
			param.searchKeyword = $(this).val();
			setGridFn(param);
		}
	});

	//---------------------------------------------
	// 검색 엔터 이벤트
	//---------------------------------------------
	$("#srcBtn").unbind().on("click", function(event){
		let param = {};
		param.searchKeyword = $("#searchKeyword").val();
		setGridFn(param);
	});

	//---------------------------------------------
	// 전체리스트 검색
	//---------------------------------------------
	$("#searchAllBtn").unbind().on("click", function(event){
		setGridFn();
	});

	//---------------------------------------------
	// 코드 등록 버튼
	//---------------------------------------------
	$("#regBtn").unbind().on("click", function(){
		$(this).popup({
			// 코드 등록 팝업
			 title : "코드 그룹 등록"
			,width : "60%"
			,height: "26%"
			,url : "/admin/system/codeGrpRegPop.do"
			,params : {"cmd" : "I"}
			,scrolling: "auto"
			,callBackFn : function(){
			}
		});
	});

	//---------------------------------------------
	// 코드 추가 버튼
	//---------------------------------------------
	$("#addBtn").unbind().on("click", function(){
		if($(".infoeq_area > ul > li").length < 3){
			if($(".infoeq_area > ul > li[style=\"display: none;\"]").length > 0){
				$(".infoeq_area > ul > li[style=\"display: none;\"]").find("option").css("display", "none");
				$(".infoeq_area > ul > li[style=\"display: none;\"]").first().css("display", "block");
			} else {
				let list = [];
				params = {};
				params.depth = $(".infoeq_area > ul > li").length;
				list.push(params);
				params.list = list;
				makeDepthForm(params);
			}
		} else {
			$(".infoeq_area > ul > li[style=\"display: none;\"]").first().find("select option").css("display", "none");
			$(".infoeq_area > ul > li[style=\"display: none;\"]").first().css("display","block");
		}
		return false;
	});
	//---------------------------------------------
	// 코드 삭제 버튼
	//---------------------------------------------
	$("#delBtn").unbind().on("click", function(){
		$($(".infoeq_area li[style=\"display: block;\"]").get().reverse()).each(function(){
			if($(this).attr("id") != "depth_00"){
				$(this).css("display", "none");
				let selVal = $(this).prev().find("select").val();
				$(this).find("select option").each(function(){
					if(selVal == $(this).attr("data-id") && $(this).attr("data-use") == "Y"){

						$(this).removeAttr("selected");
						$(this).attr("data-use", "N");
						$(this).css("display", "none");

						params = {};
						params.upCodeId = $(this).attr("data-id");
						params.codeGrpId = root;
						params.depth = $(this).parent().attr("id").replace("selDepth_","");
						delCodeArray.push(params);
					}
				})
				return false;
			}
		});
	});

	//---------------------------------------------
	// 코드 초기화 버튼
	//---------------------------------------------
	$("#defBtn").unbind().on("click", function(){
		DIALOG.confirm({
			title : "알림",
			msg : "하위코드를 변경 전으로 <br/> 초기화 하시겠습니까?"
		}, function(){
			if(this.key == "ok"){
				//초기화
				addCodeArray = [],
				delCodeArray = [],
				updCodeArray = [];
				$(".infoeq_area > ul").empty();
				params = {};
				params.codeGrpId = root;
				params.upCodeId = root;
				setDepthFn(params);
			}
		})
	});

	//---------------------------------------------
	// 코드 저장 버튼
	//---------------------------------------------
	$("#savBtn").unbind().on("click", function(){

		DIALOG.confirm({
			title : "알림",
			msg : "저장 하시겠습니까?"
		}, function(){
			if(this.key == "ok"){
				let url = "/admin/system/system04ResourceProcess.do";
				let params = {};
				params.addCodeArray = JSON.stringify(addCodeArray),
				params.delCodeArray = JSON.stringify(delCodeArray),
				params.updCodeArray = JSON.stringify(updCodeArray);

				utils.ajax(url, params, function(rtn){
					if(rtn.result.errorCode == "00"){
						DIALOG.alert({
							title: "알림",
							msg: rtn.result.errorMessage
						}, function () {

							$(".infoeq_area > ul").empty();

							addCodeArray = [],
							delCodeArray = [],
							updCodeArray = [];

							params = {};
							params.codeGrpId = root;
							params.upCodeId = root;
							setDepthFn(params);
						});
					}else{
						DIALOG.alert({
							title: "알림",
							msg: rtn.result.errorMessage
						});
					}
				});
			}
		});
	});

}
//---------------------------------------------
// 그리드
//---------------------------------------------
setGridFn = function(data){
	let param = {};
	param = data;

	url = "/admin/system/system04GetCodeList.do",
	option = {};
	option.ajax = true;
	option.url  = url;
	option.params = param;
	option.colGroup = [
		 { key : "codeGrpId", label : "코드 그룹 ID", width : "20", align : "center"}
		,{ key : "codeGrpName", label : "코드 그룹 명", width : "50", align : "center", click : function(data){

			addCodeArray = [],
			delCodeArray = [],
			updCodeArray = [];

			utils.inputData(data);
			url = "/admin/system/system04GetDepthCodeList.do",
			params = {};
			//전역변수 설정
			root = data.codeGrpId;
			params.codeGrpId = data.codeGrpId;
			params.upCodeId = data.codeGrpId;
			params.depth = 0;

			$(".infoeq_area > ul").empty();
			$(".infoeq_area").css("display","block");
			$(".info_button_area").css("display","block");

			idx = 0;

			setDepthFn(params);
		}}
		,{ key : "useYn", label : "사용", width : "10", align : "center"}
		,{ key : "useYn", label : "속성", width : "20", align : "center",
			html : function(data){

				touch = function(id, pageUrl, codeGrpId, codeGrpName, codeGrpDesc, useYn){
					params = {};
					params.codeGrpId = codeGrpId,
					params.codeGrpName = codeGrpName,
					params.codeGrpDesc = codeGrpDesc,
					params.useYn = useYn,
					params.cmd = "U";

					let title = "",
					height = "";
					if(id == "default"){
						title = "코드 그룹 수정";
						height = "35%";
					} else {
						title = "공통코드 세부속성";
						height = "70%";
					}

					$(this).popup({
						 title : title
						,width : "60%"
						,height: height
						,url : pageUrl
						,scrolling: "auto"
						,params:params
						,callBackFn : function(){

						}
					});
				}

				deleteFn = function(codeGrpId){
					DIALOG.confirm({
						title : "알림",
						msg : "삭제하시겠습니까?"
					}, function(){
						if(this.key == "ok"){
							params = {};
							params.codeGrpId = codeGrpId;
							params.cmd = "D";
							url = "/admin/system/system04Process.do";
							utils.ajax(url, params, function(rtn){
								if(rtn.result.errorCode == "00"){
									DIALOG.alert({
										title: "알림",
										msg: rtn.result.errorMessage
									}, function () {
										utils.reload();
									});
								}else{
									DIALOG.alert({
										title: "알림",
										msg: rtn.result.errorMessage
									});
								}
							});
						}
					});
					return false;
				}

				strHtml="";
				strHtml += "<p onclick=\"javascript:touch('default', '/admin/system/codeGrpRegPop.do', '"+data.codeGrpId+"', '"+data.codeGrpName+"','"+data.codeGrpDesc+"','"+data.useYn+"');\" style=\"cursor:pointer;\">[기본속성]</p>";
				strHtml += "<p onclick=\"javascript:touch('detail', '/admin/system/codeListPop.do', '"+data.codeGrpId+"', '"+data.codeGrpName+"','"+data.codeGrpDesc+"','"+data.useYn+"');\" style=\"cursor:pointer;\">[세부속성]</p>";
				strHtml += "<p onclick=\"javascript:deleteFn('"+data.codeGrpId+"');\" style=\"cursor:pointer;\">[코드삭제]</p>";
				return strHtml;
			}
		}
	];

	$("#system0401ListTable").list(option);
}
//---------------------------------------------
// 뎁스
//---------------------------------------------
setDepthFn = function(data){
	params = data;
	console.log(url);
	console.log(params);
	utils.ajax(url, params, function(rtn){
		let depthMap = rtn.result;
		params = {};
		if(depthMap.list.length > 0){
			params.codeGrpId = root,
			params.upCodeId = depthMap.list[0].codeId;
			params.depth = depthMap.list[0].depth;
			makeDepthForm(depthMap);
			setDepthFn(params);
			//depth data set
		} else {
			params.codeGrpId = depthMap.param.codeGrpId,
			params.upCodeId = depthMap.param.upCodeId;
			params.depth = depthMap.param.depth;
			makeDepthForm(depthMap);
		}
	}, false);
	return false;
}
//---------------------------------------------
// 뎁스 UI
//---------------------------------------------
let seconde = "";
let cnt=0;
makeDepthForm = function(data){
	//반복 index
	idx++;
	console.log(data);
	if(data.length == 0){
		$("#depth_0"+ idx +"").css("display", "none");
	} else {
		let strArray = [],
		selArray = [];
		if(data.list.length > 0){
			cnt = data.list[0].depth;
		} else {
			cnt = (Number(cnt)+1);
		}
		if(data.list.length > 0){
			console.log("cnt : "+cnt);
			if($("#depth_0"+ cnt +"").length == 0){
				strArray.push("<li id=\"depth_0"+ cnt +"\" style=\"display: block;\">");
				strArray.push("	<div class=\"title\">"+(Number(cnt) + 1) +" depth code</div>");
				strArray.push("	<dl class=\"list\">");
				strArray.push("<dd>");
				strArray.push("<select multiple=\"multiple\" name=\"selDepth_"+cnt+"\" id=\"selDepth_"+cnt+"\" class=\"multiSel_depth\" style=\"background-color:#fff; width:100%; border:0; background:none; height:150px; font-size:16px\">");
				strArray.push("</select>");
				strArray.push("</dd>");
				strArray.push("</dl>");
				strArray.push("<div class=\"button_area\">");
				strArray.push("<button class=\"button_info w80 oreage\" id=\"depthAddBtn_"+cnt+"\" style=\"height:30px;\" data-write>");
				strArray.push("<span>추가</span>");
				strArray.push("</button>");
				strArray.push("<button class=\"button_info w80 gray\" id=\"depthUpdBtn_"+cnt+"\" style=\"height:30px;\" data-write>");
				strArray.push("<span>수정</span>");
				strArray.push("</button>");
				strArray.push("<button class=\"button_info black w80\" id=\"depthDelBtn_"+cnt+"\" style=\"height:30px;\" data-write>");
				strArray.push("	<span>삭제</span>");
				strArray.push("</button>");
				strArray.push("</div>");
				strArray.push("</li>");
				$(".infoeq_area > ul").append(strArray.join("").trim());
			} else {
				console.log("data.param.upCodeId : "+data.param.upCodeId);
				if($("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").length > 0){
					$("#depth_0"+cnt+"").css("display", "block");
					let first = $("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").first().val();
					if($("#selDepth_"+(Number(cnt)+1)+" option[data-id="+first+"][data-use=Y]").length > 0){
						$("#depth_0"+(Number(cnt)+1)+"").css("display", "block");
					} else {
						$("#depth_0"+(Number(cnt)+1)+"").css("display", "none");
					}
				}
			}
		} else {
			if($("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").length == 0){
				let first = $("#selDepth_"+cnt+" option:selected").val();
				if($("#selDepth_"+Number(cnt+1)+" option[data-id="+first+"][data-use=Y]").length > 0){
					if($("#selDepth_"+Number(cnt+1)+" option[data-id="+first+"][data-use=Y]:selected").length == 0){
						$("#selDepth_"+Number(cnt+1)+" option").removeAttr("selected");
						$("#selDepth_"+Number(cnt+1)+" option[data-id="+first+"][data-use=Y]").first().prop("selected", true);
					}
					if(seconde != $("#selDepth_"+Number(cnt+1)+" option[data-id="+first+"][data-use=Y]:selected").val()){
						seconde = $("#selDepth_"+Number(cnt+1)+" option[data-id="+first+"][data-use=Y]:selected").val();
						if($("#selDepth_"+Number(cnt+2)+" option[data-id="+seconde+"][data-use=Y]").length > 0){
							$("#selDepth_"+Number(cnt+2)+" option").css("display", "none");
							$("#selDepth_"+Number(cnt+2)+" option[data-id="+seconde+"][data-use=Y]").first().prop("selected", true);
							$("#selDepth_"+Number(cnt+2)+" option[data-id="+seconde+"][data-use=Y]").css("display", "block");
						}
					}
				} else {
					$("#depth_0"+cnt+"").css("display", "none");
				}
			} else {
				$("#selDepth_"+cnt+" option[data-id!="+data.param.upCodeId+"][data-use=Y]").removeAttr("selected");
				$("#selDepth_"+cnt+" option[data-id!="+data.param.upCodeId+"][data-use=Y]").css("display", "none");
				$("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").css("display", "block");
				$("#selDepth_"+cnt+" option").removeAttr("selected");
				$("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").first().prop("selected", true);
				$("#depth_0"+cnt+"").css("display", "block");

				let first = $("#selDepth_"+cnt+" option[data-id="+data.param.upCodeId+"][data-use=Y]").first().val();
				if($("#selDepth_"+(Number(cnt)+1)+" option[data-id="+first+"][data-use=Y]").length == 0){
					$("#depth_0"+(Number(cnt)+1 )+"").css("display", "none");
				} else {
					$("#selDepth_"+(Number(cnt)+1)+" option[data-id!="+first+"][data-use=Y]").removeAttr("selected");
					$("#selDepth_"+(Number(cnt)+1)+" option[data-id!="+first+"][data-use=Y]").css("display", "none");
					$("#selDepth_"+(Number(cnt)+1)+" option[data-id="+first+"][data-use=Y]").css("display", "block");
					$("#selDepth_"+(Number(cnt)+1)+" option").removeAttr("selected");
					$("#selDepth_"+(Number(cnt)+1)+" option[data-id="+first+"][data-use=Y]").first().prop("selected", true);
					$("#depth_0"+(Number(cnt)+1)+"").css("display", "block");
				}
			}
		}
		let chkUpCodeId, //data-id값을 확인하기 위한 변수
		chkCnt = 0;		//해당 data-id 값 유무 flag

		//option setting
		$.each(data.list, function(idx, value){
			if(utils.defaultString(value.upCodeId) != ""){
				if(idx == 0){
					selArray.push("<option data-id=\""+value.upCodeId+"\" data-use=\"Y\" value="+value.codeId+" selected>["+value.codeId+"]"+value.codeName+"</option>");
				} else {
					selArray.push("<option data-id=\""+value.upCodeId+"\" data-use=\"Y\" value="+value.codeId+">["+value.codeId+"]"+value.codeName+"</option>");
				}
				chkUpCodeId = utils.defaultString(value.upCodeId);
			}
		});

		if(utils.defaultString(chkUpCodeId) != ""){
			$("#selDepth_"+cnt+" option").each(function(){
				$(this).css("display", "none");
				if($(this).attr("data-id") == chkUpCodeId){
					chkCnt++;
				}
			});
			//해당 heigher 유무에 따른 option 추가
			if(chkCnt == 0){
				$("#selDepth_"+cnt+" option").prop("selected", false);
				$("#selDepth_"+cnt+"").append(selArray.join(''));
				$("#depth_0"+cnt+"").css("display", "block");
			} else {
				$("#selDepth_"+cnt+" option[data-id="+chkUpCodeId+"][data-use=\"Y\"]").css("display", "block");
				$("#selDepth_"+cnt+" option").prop("selected", false);
				$("#selDepth_"+cnt+" option[data-id="+chkUpCodeId+"]:eq(0)").prop("selected", true);
			}
		}
	}

	//depth 선택박스
	$("select[id^=selDepth_]").unbind().on("change", function(){

		let upCodeId = $(this).find(":selected").val();
		let lvl = $(this).attr("id").replace("selDepth_", "");

		params = {};
		params.codeGrpId = root,
		params.upCodeId = upCodeId;
		params.depth = lvl;
		cnt = $(this).attr("id").replace("selDepth_","");
		idx = $(this).attr("id").replace("selDepth_","");

		$("#depth_0"+lvl).nextAll().css("display", "none");
		//depth data set
		setDepthFn(params);
	});

	//depth 내부 추가 버튼 클릭시
	$("button[id^=depthAddBtn_]").unbind().on("click", function(){
		let depth = Number($(this).attr("id").replace("depthAddBtn_","")) - 1;
		params = {};
		params.codeGrpId = root;
		params.addCodeArray = addCodeArray;
		if(depth < 0){
			depth = 0;
			params.depth = depth;
			params.upCodeId = root;
		} else {
			params.depth = Number(depth)+1;
			params.upCodeId = $("#selDepth_"+depth+"").val()[0];
			params.cmd = "I";
		}
		//코드 리소스 등록 팝업
		$(this).popup({
			 title : "코드 등록"
			,width : "60%"
			,height: "40%"
			,url : "/admin/system/codeRegPop.do"
			,params : params
			,scrolling: "auto"
			,callBackFn : function(rtn){
				console.log(rtn);
				$("#depth_0"+(Number(rtn.depth)+1)).css("display", "none");
				$("#depth_0"+(Number(rtn.depth)+2)).css("display", "none");
				$("#selDepth_"+rtn.depth+" option").prop("selected", false);
				$("#selDepth_"+(Number(rtn.depth)+1)+" option").css("display", "none");
				$("#selDepth_"+(Number(rtn.depth)+2)+" option").css("display", "none");
				$("#selDepth_"+rtn.depth+"").append("<option data-id=\""+rtn.upCodeId+"\" data-use=\"Y\" value=\""+rtn.codeId+"\" selected>["+rtn.codeId+"]"+rtn.codeName+"</option>")
				rtn.flag = "I";
				addCodeArray.push(rtn);
			}
		});
		return false;
	});

	//depth 내부 수정 버튼 클릭시
	$("button[id^=depthUpdBtn_]").unbind().on("click", function(){
		let depth = $(this).attr("id").replace("depthUpdBtn_","");
		let subStr = $("#selDepth_"+depth+" option:selected").attr("data-id");
		let selTxt = $("#selDepth_"+depth+" option:selected").text();
		let selVal = $("#selDepth_"+depth+"").val()[0];
		params = {};
		params.depth = depth,
		params.codeGrpId = root,
		params.upCodeId = subStr,
		params.codeId = selVal,
		params.cmd = "U",
		params.codeName = selTxt;
		$(this).popup({
			// 코드 등록 팝업
			 title : "코드 등록"
			,width : "60%"
			,height: "20%"
			,url : "/admin/system/codeEditPop.do"
			,params : params
			,scrolling: "auto"
			,callBackFn : function(rtn){
				$("#selDepth_"+rtn.depth+" option[value="+rtn.codeId+"]").text("["+rtn.codeId+"]"+rtn.codeName+"");
				let flag = true;
				if(addCodeArray.length > 0){
					$.each(addCodeArray, function(idx, obj){
						if(obj.codeId == rtn.codeId){
							addCodeArray[idx] = rtn;
							flag = false;
							return false;
						}
					})
				}
				if(updCodeArray.length > 0){
					$.each(updCodeArray, function(idx, obj){
						if(obj.codeId == rtn.codeId){
							updCodeArray[idx] = rtn;
							flag = false;
							return false;
						}
					})
				}
				if(flag){
					rtn.flag = "U";
					updCodeArray.push(rtn);
					console.log(updCodeArray);
				}
			}
		});
	});

	//depth 내부 삭제 버튼 클릭시
	$("button[id^=depthDelBtn_]").unbind().on("click", function(){
		let depth = $(this).attr("id").replace("depthDelBtn_", "");
		params = {};
		params.codeId = $("#selDepth_"+depth+"").val()[0];
		params.depth = depth;
		params.codeGrpId = root;
		params.upCodeId = $("#selDepth_"+depth+" option:selected").attr("data-id");
		if(updCodeArray.length > 0){
			$.each(updCodeArray, function(idx, obj){
				if(obj.codeId == params.codeId){
					updCodeArray.splice(idx, 1);
					return false;
				}
			})
		}
		if(addCodeArray.length > 0){
			$.each(addCodeArray, function(idx, obj){
				if(obj.codeId == params.codeId){
					addCodeArray.splice(idx, 1);
					return false;
				}
			})
		}
		delCodeArray.push(params);
		eeFn(params);
	});
}
//---------------------------------------------
// 뎁스 코드 선택 시 기능
//---------------------------------------------
eeFn = function(data){

	$("#selDepth_"+data.depth+" option:selected").css("display", "none");
	$("#selDepth_"+data.depth+" option:selected").attr("data-use", "N");

	if($("#selDepth_"+(Number(data.depth)+1)+" option[data-id="+data.codeGrpId+"][data-use=Y]").length > 0){
		$("#selDepth_"+(Number(data.depth)+1)+" option[data-id="+data.codeGrpId+"][data-use=Y]").each(function(){
			$(this).css("display", "none");
			$(this).attr("data-use", "N");

			params = {};
			params.codeGrpId = $(this).val();
			params.depth = Number(data.depth)+1;
			params.upCodeId = $(this).attr("data-id");

			eeFn(params);

			$.each(delCodeArray, function(idx, obj){
				if(obj.codeGrpId == params.codeGrpId){
					delCodeArray[idx] = params;
				} else {
					delCodeArray.push(params);
				}
			})
		});
	} else {
		return false;
	}
}

