//---------------------------------------------
// 전역변수 세팅
//---------------------------------------------
let params,
WChecked = true;
RChecked = true;
clickSysType = "";
clickRid = "";
initFunction = function(initResult){

	if(utils.defaultString(initResult) == ""){
		initResult = {};
		initResult.sysType = "1";
	}

	setGridFn(initResult);

	eventFunction(initResult);
}
//---------------------------------------------
//클릭이벤트
//---------------------------------------------
eventFunction = function(data){
	$("#regBtn").unbind().on("click", function(){
		params = {};
		params.flag = "I";
		params.sysType = data.sysType;
		roleRegPopFn(params);
	});

	$("#method_W").unbind().on("click", function(){
		if(WChecked == true){
			$("input[id^=method_W]").prop("checked", true);
			WChecked = false;
		} else {
			$("input[id^=method_W]").prop("checked", false);
			WChecked = true;
		}
	});

	$("#method_R").unbind().on("click", function(){
		if(RChecked == true){
			$("input[id^=method_R]").prop("checked", true);
			RChecked = false;
		} else {
			$("input[id^=method_R]").prop("checked", false);
			RChecked = true;
		}
	});

	$("#savBtn").unbind().on("click", function(){

		let paramsA = {};
		let paramsB = {};
		let paramsAList = [];
		let paramsBList = [];
		let params = {};
		let url = "/admin/system/rolePgmInsert.do";

		$("#sysRoleArea > tr").each(function(){
			paramsA = {};
			paramsB = {};
			let menuId = "";
			$(this).find("input").each(function(){
				let name = $(this).attr("name");
				if(name == "menuId"){
					menuId = $(this).val();
				}
				if($(this).attr("type") == "checkbox"){
					if($(this).prop("checked") == true){
						if(name == "method_R"){
							paramsAList.push(menuId);
						}else {
							paramsBList.push(menuId);
						}
					}
				}
			});
		});

		params.paramsAList = JSON.stringify(paramsAList);
		params.paramsBList = JSON.stringify(paramsBList);
		params.rid = clickRid;
		DIALOG.confirm({
			msg : "저장 하시겠습니까?"
		}, function(){
			if(this.key == "ok"){
				utils.ajax(url, params, function(res){
					if(res.result.errorCode == "00"){
						DIALOG.alert({
							msg : res.result.errorMessage
						}, function(){
							if(this.key == "ok"){
								let params = {};
								params.rid = clickRid;
								params.sysType = clickSysType;
								 clickSysType = data.sysType;
								 if(clickSysType == "1"){
									 params.startCd = "0001";
								 } else {
									 params.startCd = "0002";
								 }
								let url = "/admin/system/roleTreeList.do";
								utils.ajax(url, params, function(res){
									 gridFn(res.result.list);
								});
							}
						});
					}
				});
			}
		});
	})

	$("li[id^=tab]").unbind().on("click", function(){
		params = {};
		$(".tap").removeClass("on");
		$(this).addClass("on");
		let id = $(this).attr("id");
		if(id == "tab1"){
			params.sysType="1";
		} else {
			params.sysType="2";
		}

		$(".info_area > ul").find(".word").each(function(){
			$(this).empty()
		});
		$("#sysRoleArea").empty();
		initFunction(params);
	});
}
//---------------------------------------------
// 그리드
//---------------------------------------------
setGridFn = function(data){

	let param = {};
	param.sysType = data.sysType;
	url = "/admin/system/ajaxRoleList.do",
	option = {};
	option.ajax = true;
	option.url  = url;
	option.params = param;
	option.colGroup = [
		 { key : "rname", label : "권한명", width : "20", align : "center", click : function(data){
			 utils.inputData(data);
			 clickRid = data.rid;
			 clickSysType = data.sysType;
			 if(clickSysType == "1"){
				 data.startCd = "0001";
			 } else {
				 data.startCd = "0002";
			 }

			 let url = "/admin/system/roleTreeList.do";
			 utils.ajax(url, data, function(res){
				 gridFn(res.result.list);
			 });

		}}
		,{ key : "description", label : "설명", width : "30", align : "left"}
		,{ key : "usedYn", label : "사용", width : "20", align : "center"}
		,{ key : "usedYn", label : "수정", width : "20", align : "center",
			html : function(data){
				updPop = function(rid, rname, description, usedYn){
					params = {};
					params.rid = rid;
					params.rname = rname;
					params.description = description;
					params.usedYn = usedYn;
					params.flag = "U";
					params.sysType = data.sysType;

					roleRegPopFn(params);
					return false;
				}
				let str = [];
				str.push("<p onclick=\"javascript:updPop('"+data.rid+"', '"+data.rname+"', '"+data.description+"', '"+data.usedYn+"')\" style=\"cursor:pointer;\">[수정]</p>")
				return str.join("");
			}
		}
	];

	$("#roleListTable").list(option);
}


roleRegPopFn = function(data){
	$(this).popup({
		 title : "권한 등록"
		,width : "50%"
		,height: "50%"
		,scrolling: "auto"
		,url : "/admin/system/roleRegPop.do"
		,params : data
		,callBackFn : function(res){
			params = {};
			params.sysType = res.result.sysType;

			initFunction(params);
		}
	});
}

gridFn = function(data){

	let html = [];

	$.each(data, function(idx, obj){
		html.push("	<tr>");
		html.push("	<input type=\"hidden\" id=\"menuId_"+idx+"\" name=\"menuId\" value="+obj.menuId+">");
		if(obj.lvl == "1"){
			html.push("		<td style=\"text-align:left;\">");
		} else if(obj.lvl == "2"){
			html.push("		<td style=\"text-align:left;padding-left:100px\">");
		} else if(obj.lvl == "3"){
			html.push("		<td style=\"text-align:left;padding-left:150px\">");
		} else {
			html.push("		<td style=\"text-align:left;padding-left:200px\">");
		}
		html.push(obj.menuName);
		html.push("		</td>");
		html.push("		<td>");
		html.push("			<span class=\"rd_checkbox_1\" style=\"margin-top: 5px;\">");
		html.push("				<label class=\"check\">");
		if(obj.methodR == "R"){
			html.push("					<input type=\"checkbox\" id=\"method_R_"+idx+"\" name=\"method_R\" value=\"R\" checked>");
		} else {
			html.push("					<input type=\"checkbox\" id=\"method_R_"+idx+"\" name=\"method_R\" value=\"R\">");
		}
		html.push("					<span></span>");
		html.push("				</label>");
		html.push("			</span>");
		html.push("			<span class=\"userText fs14 l26 ml5 fll\">");
		html.push("				<label for=\"method_R_"+idx+"\">읽기</label>");
		html.push("			</span>");
		html.push("		</td>");
		html.push("		<td>");
		html.push("			<span class=\"rd_checkbox_1\" style=\"margin-top: 5px;\">");
		html.push("				<label class=\"check\">");
		if(obj.methodW == "W"){
			html.push("					<input type=\"checkbox\" id=\"method_W_"+idx+"\" name=\"method_W\" value=\"W\" checked>");
		} else {
			html.push("					<input type=\"checkbox\" id=\"method_W_"+idx+"\" name=\"method_W\" value=\"W\">");
		}
		html.push("					<span></span>");
		html.push("				</label>");
		html.push("			</span>");
		html.push("			<span class=\"userText fs14 l26 ml5 fll\">");
		html.push("				<label for=\"method_W_"+idx+"\">쓰기</label>");
		html.push("			</span>");
		html.push("		</td>");
		html.push("	</tr>");
	});
	$("#sysRoleArea").html(html.join(""));
}
