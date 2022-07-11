let sysType = "1";
initFunction = function(){

//	utils.includeFile("/resources/js/treeUtils.js", "js");
	$("#tree_target").tree({
		 ajax : true
		,url : "/admin/system/menuTreeList.do"
		,paramData: {menuId:"0", sysType:sysType}
		,height : 745
		,callBackFn: function(data){
			//모든레벨 적용
			
			commonFn(data);
			//그리드
			if(JSON.stringify(data.lvl) > 2){
				setGridFn(data);
			}
			
			ajaxEventFunction(data);
		}
	}, false);

	eventFunction();
}
eventFunction = function(){
	$("li[id^=tab]").unbind().on("click", function(){

		$(".tap").removeClass("on");
		$(this).addClass("on");
		let id = $(this).attr("id");
		if(id == "tab1"){
			sysType="1";
		} else {
			sysType="2";
		}
		initFunction();
	});
}




ajaxEventFunction = function(data){
	$("#addMenuBtn, #updMenuBtn").unbind().on("click", function(){
		let id = $(this).prop("id"),
		flag = "",
		params = {};
		
		if(id == "addMenuBtn"){
			params.flag = "save";
			params.upMenuId = utils.defaultString(data.menuId);
		} else if(id == "updMenuBtn"){
			params.flag = "update";
			params.menuId = utils.defaultString(data.menuId);
			params.menuIdNm = utils.defaultString(data.menuId);
			params.menuUrl = utils.defaultString(data.menuUrl);
			params.menuEnName = utils.defaultString(data.menuEnName);
			params.menuName = utils.defaultString(data.menuName);
		}
		params.useYn = utils.defaultString(data.useYn);
		params.sysType = utils.defaultString(data.sysType);
		params.sysTypeNm = utils.defaultString(data.sysTypeNm);

		$(this).popup({
			 title : "메뉴 등록"
			,width : "50%"
			,height: "40%"
			,scrolling : "auto"
			,params : params
			,url : "/admin/system/menuRegPop.do"
			,callBackFn : function(data){
				utils.reload();
			}
		});
	});

	$("#pidSaveBtn").unbind().on("click", function(){
		let id = $(this).prop("id"),
		flag = "",
		params = {};
		params.flag = "pidSave";
		params.menuId = utils.defaultString(data.menuId);
		params.menuIdNm = utils.defaultString(data.menuId);
		params.menuName = utils.defaultString(data.menuName);
		params.sysType = utils.defaultString(data.sysType);
		params.sysTypeNm = utils.defaultString(data.sysTypeNm);

		$(this).popup({
			 title : "프로그램 추가"
			,width : "50%"
			,height: "40%"
			,scrolling : "auto"
			,params : params
			,url : "/admin/system/menuPgmRegPop.do"
			,callBackFn : function(data){

			}
		});
	});
	
	$("#remMenuBtn").unbind().on("click", function(){
		let id = $(this).prop("id");
			DIALOG.confirm({
				title : "알림",
				msg : "삭제 하시겠습니까?"
			}, function(){
				if(this.key == "ok"){
					let url = "/admin/system/menuDelete.do",
					params = {};
					params.menuId = utils.defaultString(data.menuId);
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
		});
	}
/*//정렬 순서 세팅
setSortSeqFn = function(data){
	let sortSeq = data.sortSeq,
	sortStr = [];
	for(var i=1; i<=sortSeq; i++){
		sortStr.push("<option value=\"" + i + "\">" + i + "</option>");
	}
	$("#sortSeq").html(sortStr.join(''));
}*/
//모든레벨 적용
commonFn = function(data){
	
	let useYn = data.useYn,
		menuType = data.menuType,
		mid = data.menuId,
		lvl = data.lvl,
		sysType = data.sysType;
	$("#sortSeq").onlyNumber();

	switch(useYn){
		case "Y" : useYnNm = "예"; break;
		case "N" : useYnNm = "아니오"; break;
	}

	switch(sysType){
		case "1" : sysTypeNm = "업무지원시스템"; break;
		case "2" : sysTypeNm = "고객지원시스템"; break;
	}

	data.useYnNm = useYnNm;
	data.sysTypeNm = sysTypeNm;

	utils.inputData(data);

	if(lvl < 2){
		$("#addMenuBtn").show();
		$("#updMenuBtn").hide();
		$("#remMenuBtn").hide();
		$("#sortChangeBtn").hide();
	} else {
		$("#addMenuBtn").show();
		$("#updMenuBtn").show();
		$("#remMenuBtn").show();
		$("#sortChangeBtn").show();
	}
}
//
setGridFn = function(data){
	let url = "/admin/system/menuPgmList.do",
	option = {};
	option.ajax = true;
	option.url  = url;
	option.result = result;
	option.params = {};
	option.params.menuId = data.menuId;
	option.colGroup = [
		 { key : "pid", label : "PID", width : "5", align : "center"}
		,{ key : "pname", label : "프로그램명", width : "30", align : "center"}
		,{ key : "servletPath", label : "서블릿패스", width : "15", align : "center"}
		,{ key : "methodType", label : "메소드", width : "5", align : "center",
			html : function(data){
				switch(data.methodType)
				{
				case "R" :
					return "읽기";
				case "W" :
					return "쓰기";
				}
			}
		}
		,{ key : "loginYn", label : "수정", width : "5", align : "center",
			html : function(res){
				
				let strArray = "";
				strArray += "<button class=\"button_sh w30 blue\" id=\"pidUpdateBtn\" data-write><span>수정</span></button>";
				return strArray;
			}, click : function(res){
				let id = $(this).prop("id"),
				flag = "",
				params = {};
				
				res.prgNm = utils.defaultString(res.pname);
				res.sysType = utils.defaultString(res.sysType);
				res.menuId = utils.defaultString(data.menuId);
				res.menuIdNm = utils.defaultString(data.menuId);
				res.pid = utils.defaultString(res.pid);			
				
				$(this).popup({
					 title : "프로그램 추가"
					,width : "50%"
					,height: "40%"
					,scrolling : "auto"
					,params : res
					,url : "/admin/system/menuPgmRegPop.do"
					,callBackFn : function(data){
						utils.reload();
					}
				});
				
				return false;
			}
		}
		,{ key : "loginYn", label : "삭제", width : "5", align : "center",
			html : function(res){
				
				let strArray = "";
				strArray += "<button class=\"button_sh w30 red\" id=\"pidDeleteBtn\" data-write><span>삭제</span></button></div>";
				return strArray;
			}, click : function(res){
				
				let id = $(this).prop("id");
				
				DIALOG.confirm({
					title : "알림",
					msg : "삭제 하시겠습니까?"
				}, function(){
					if(this.key == "ok"){
						let url = "/admin/system/sysm02MenuPgmDelete.do",
						params = {};
						params.menuId = utils.defaultString(data.menuId);
						params.pid = utils.defaultString(res.pid);
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
			}
		}
	];
	$("#sysm0201ListTable").list(option);
}