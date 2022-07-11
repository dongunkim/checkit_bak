let userId;
let roleList;
let firstStatus;
let companyCd;
let preStatus =""; //현상태
let preManage =""; //현소속
let preManageCid = ""; //20190426 소속1
let preFormationCd = "" ; //20190426 소속2
let preFormationCd_2 = ""; //20190426 소속3
let preRoleViewArea = ""; //20190426 권한정보

initFunction = function(){

	$("#userInfoArea").css("display", "none");
	$("#srchFormationCd3").hide();

	let url = "/common/commBuseoTreeList.do";
	let params = {};
		params.code = "1";
	utils.ajax(url, params, function(response){
		let html = [];
		html.push("<option value=\"\">-선택-</option>");
		$.each(response.result.list, function(idx, data){
			html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
		});
		$("#manageCid").empty().html(html.join(""));
	});

	utils.getCommNm("140", "srchFormationCd1");

	let urlR = "/admin/system/sysm0102Pop.do";
	let paramsR = {};
	utils.ajax(urlR, paramsR, function(response){
		$("#searchRid").empty();	
		$("#searchRid").append("<option value=\"\">-선택-</option>");
		$.each(response.result.list, function(idx, data){
			$("#searchRid").append("<option value=\"" + data.rid + "\">" + data.rname + "</option>");
		});
	});
	
	let option = {};
		option.ajax = true;
		option.colGroup = [
			// default
			 { key : "userId", label : "ID", width : "10", align : "center"}
			,{ key : "userNm", label : "이름", width : "10", align : "center"}
			,{ key : "manageCnm", label : "센터", width : "10", align : "center"}
			,{ key : "formationNm", label : "소속", width : "10", align : "center"}
			,{ key : "dutyYn", label : "근태대상", width : "10", align : "center"}
			,{ key : "status", label : "상태", width : "10", align : "center",
				html : function(data){
					if(data.status == "A"){
						return "사용";
					}else if(data.status == "O"){
						return "요청";
					}else if(data.status == "L"){
						return "잠금";
					}
				}
			}
			,{ key : "rname", label : "권한", width : "10", align : "center"}
		];

		option.allClick = function(data){

			//20190426 소속/권한 초기화
			preStatus = "";
			preManageCid = ""; 
			preFormationCd = "" ;
			preFormationCd_2 = "";
			neWroleViewArea = "";
			
			
			$("#userInfoArea").show();
			userId = data.userId;
			$("#firstStatus").val(data.status);

			// 사용자 정보조회
			url = "/admin/system/sysm0101Info.do";
			param = {};
			param.userId = data.userId;
			utils.ajax(url, param, function(userInfo){
				utils.inputData(userInfo.result);
				
				//사용여부 현상태
				let targetId = $("input[type=radio][name=status]:checked").attr("id");
				preStatus = $("label[for="+targetId+"]").text();
				
				let url = "/common/commBuseoTreeList.do";
				let params = {};
					params.code = "1";
				utils.ajax(url, params, function(response){

					let html = [];
					html.push("<option value=\"\">-선택-</option>");
					$.each(response.result.list, function(idx, data){
						if(data.code == userInfo.result.manageCid){
							html.push("<option value=\"" + data.code + "\" selected>" + data.codeName + "</option>");							
							//소속1
							preManageCid = data.codeName;
						} else {
							html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
						}
					});
					//소속 1
					$("#manageCid").empty().html(html.join(""));

					// 부서정보조회
					let url = "/common/commBuseoTreeList.do";
					let params = {};
						params.code = userInfo.result.manageCid;
					//2depth ajax
					utils.ajax(url, params, function(response){
						let formationCd = userInfo.result.formationCd;
						if(formationCd.substr(4,1) == "5" && formationCd.substr(4,2) != "50"){
							let html = [];
							html.push("<option value=\"\">-선택-</option>");
							$.each(response.result.list, function(idx, data){
								if(data.code == formationCd.substr(0,4)+"50"){
									html.push("<option value=\"" + data.code + "\" selected>" + data.codeName + "</option>");
									//20190426 소속2
									preFormationCd = data.codeName;									
								} else {
									html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
								}
							});
							if(html.length > 0){
								$("#formationCd").show().empty().html(html.join(""));
								$("#formationCd").val();
							}else{
								$("#formationCd").hide();
								$("#formationCd").empty();
							}

							let url = "/common/commBuseoTreeList.do";
							let params = {};
								params.code = formationCd.substr(0,4)+"50";
							//3depth ajax
							utils.ajax(url, params, function(response){
								let html = [];
								$.each(response.result.list, function(idx, data){
									if(data.code == formationCd){
										html.push("<option value=\"" + data.code + "\" selected>" + data.codeName + "</option>");
										//20190426 소속3
										preFormationCd_2 = data.codeName;
									} else {
										html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
									}
								});
								if(html.length > 0){
									$("#formationCd_2").show().empty().html(html.join(""));
								}else{
									$("#formationCd_2").hide();
									$("#formationCd_2").empty();
								}
							});
						} else {
							let html = [];
							$.each(response.result.list, function(idx, data){
								if(userInfo.result.formationCd == data.code){
									html.push("<option value=\"" + data.code + "\" selected>" + data.codeName + "</option>");
									//20190426 소속2
									preFormationCd = data.codeName;
								} else {
									html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
								}
							});

							if(html.length > 0){
								$("#formationCd").show().empty().html(html.join(""));
								$("#formationCd_2").hide();
								$("#formationCd_2").empty();
							}else{
								$("#formationCd").hide();
								$("#formationCd").empty();
								$("#formationCd_2").hide();
								$("#formationCd_2").empty();
							}
						}											

					});

					
					params = {};
					params.code = "925";
					params.type = "select";
					params.targetId = "position";
					utils.getCommonCode(params, function(){
						$("#position").val(userInfo.result.position);
					});

				});

			});

			option.callBackFn = function(){
				$("#userInfoArea").hide();
			}
			// 사용자 권한 목록
			roleListFn({userId: data.userId},'Y');

			$("#rolePopBtn").popup({
				 title : "권한 목록"
				,width : "600px"
				,height: "700px"
				,scrolling : "auto"
				,params : {userId: userId, roleList : roleList}
				,url : "/admin/system/sysm0102Pop.do"
				,callBackFn : function(popData){
					if(popData.success){
						roleListFn({userId: popData.userId});
						closePopup();
					}
				}
			});								
			return false;
		}

	$("#sysm0101ListTable").list(option);
    eventFunction();
}

eventFunction = function(){


	$("#manageCid").unbind().bind("change", function(){

		$("#formationCd_2").hide();

		let code = $(this).val();

		let url = "/common/commBuseoTreeList.do";
		let params = {};
			params.code = code;
		utils.ajax(url, params, function(response){
			let html = [];
			html.push("<option value=\"\">-선택-</option>");
			$.each(response.result.list, function(idx, data){
				html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
			});
			if(html.length > 0){
				$("#formationCd").show().empty().html(html.join(""));
			}else{
				$("#formationCd").hide();
				$("#formationCd").empty();
			}
		});
	});

	$("#formationCd").unbind().bind("change", function(){

		$("#formationCd_2").hide();
		$("#formationCd_2").empty();

		let code = $(this).val();

		if(code.substr(4,2) == "50"){
			let url = "/common/commBuseoTreeList.do";
			let params = {};
				params.code = code;
			utils.ajax(url, params, function(response){
				let html = [];
				html.push("<option value=\"\">-선택-</option>");
				$.each(response.result.list, function(idx, data){
					html.push("<option value=\"" + data.code + "\">" + data.codeName + "</option>");
				});
				if(html.length > 0){
					$("#formationCd_2").show().empty().html(html.join(""));
				}else{
					$("#formationCd_2").hide();
					$("#formationCd_2").empty();
				}
			});
		}
	});


	$("#companyNm").autoComplete({
		ajax: {
			 url : "/common/comm0102List.do"
			,searchTarget : {searchWord:"csName", code:"csNo"}
			,callBackFn : function(data){
				$("#companyNm").val(data.name);
				companyCd = data.code;
			}
		}
	});
	$("#companyNm").unbind().bind("change", function(){
		companyCd = "";
	});

	//검색조건 - 소속
	$("#srchFormationCd1").unbind().bind("change paste keyup",function(){
		var srchFormationCd1 = $("#srchFormationCd1").val();
		let srchFormationCd3="";
		let srchFormationCdSelect="";
		$("#srchFormationCd3").val(srchFormationCd3);
		$("#srchFormationCd2").val(srchFormationCd3);
		$("#srchFormationCdSelect").val(srchFormationCdSelect);
		if(srchFormationCd1 != ""){
			let url = "/admin/system/sysm0101srchFormationCd2.do";
			let param = {};
			let optionArray = [];
			param.srchFormationCd1 = srchFormationCd1;
			utils.ajax(url, param, function(data){
				optionArray.push("<option value=\"\">-선택-</option>");
				$.each(data.result.list, function(i,o){
					optionArray.push("<option value=\"" + o.formationCd + "\">" + o.formationNm + "</option>");
				});
				$("#srchFormationCdSelect").html(optionArray);
			});
		}
		else{
			$("#srchFormationCdSelect").empty();
			$("#srchFormationCdSelect").html('<option value="">-선택-</option>');
		}
	});

	//검색조건 - 소속상세
	$("#srchFormationCdSelect").unbind().bind("change paste keyup",function(){
		let srchFormationCdSelect = $("#srchFormationCdSelect").val();
		let srchFormationCd3="";
		$("#srchFormationCd2").val(srchFormationCd3);
		// DEPTH2가 운영일때, 선택한 DEPTH3의 VALUE를 DEPTH2의 VALUE로 넣어준다. 요청사항 (DEV_gh.baek 2019.02.27)
		//if(srchFormationCdSelect == '140150'){
			//$("#srchFormationCd3").show();
		//alert(srchFormationCd2);
			if(srchFormationCd2 != ""){
				let url = "/common/commBuseoTreeList.do";
				let param = {};
				param.code = srchFormationCdSelect;
				let optionArray = [];
				utils.ajax(url, param, function(data){
					if(data.result.list !=""){
						$("#srchFormationCd3").show();
						srchFormationCd3= $("#srchFormationCd3").val();;
						optionArray.push("<option value=\"\">-선택-</option>");
						$.each(data.result.list, function(i,o){
							optionArray.push("<option value=\"" + o.code + "\">" + o.codeName + "</option>");
						});
						$("#srchFormationCd3").html(optionArray);
						//if 조건으로 키값선택 됐는지 안됐는지 구분하기 
						$("#srchFormationCd3").unbind().bind("change paste keyup",function(){
							srchFormationCd3 = $("#srchFormationCd3").val();
							$("#srchFormationCd2").val(srchFormationCd3);
							
							
						});
						
						if(srchFormationCd3 ==""){
							$("#srchFormationCd2").val(srchFormationCdSelect.substr(0,5));
						}
						
					
					}else{
						$("#srchFormationCd3").hide();
						$("#srchFormationCd2").val(srchFormationCdSelect);
					}
				});
			}else{
				$("#srchFormationCd3").empty();
				$("#srchFormationCd3").html('<option value="">-선택-</option>');
		}
	});

	// 저장
	$("#regBtn").unbind().bind("click", function(){
		
		
		//사용여부 변경정보
		let newTargetId = $("input[type=radio][name=status]:checked").attr("id");
		let newStatus = $("label[for="+newTargetId+"]").text();
		let status = $("input[name='status']:checked").val();
		
		if(status == "O"){
			DIALOG.alert("사용자 상태를 요청상태로 변경할수 없습니다.");
			return false;
		}

		//20190426 소속 이전
		preManage = preManageCid + " " + preFormationCd + " " + preFormationCd_2 ;
		
		//20190426 소속 변경정보
		let manageCid = $("#manageCid option:selected").text();  //소속사 1
		let formationCd = $("#formationCd option:selected").text();  //소속사 2
		let formationCd_2 = $("#formationCd_2 option:selected").text();  //소속사 3
		
		let newManage = manageCid + " " + formationCd + " " + formationCd_2 ;		
		//alert(preManage +"->"+ newManage);
				
		//20190426 권한지정 변경정보
		let neWroleViewArea = $("#roleViewArea").html();
		//alert(preRoleViewArea +"->"+neWroleViewArea);
		
		
		if(utils.defaultString(userId) == ""){
			DIALOG.alert("사용자 권한을 지정해 주세요.");
			return false;
		}

		let validator = [];
		let options = {};
			options.url = "/admin/system/insertSysm0102.do";
			options.params = {};
			options.params.userId = userId;
			options.params.firstStatus = $("#firstStatus").val();
			options.params.companyCd = companyCd;
			//사용여부 변경여부
			if(preStatus != newStatus){
				//alert(preStatus + "->" + newStatus);
				$("#hstDesc").val(preStatus + "->" + newStatus);			
			}
			//소속변경여부
			if(preManage != newManage){		
				$("#manageDesc").val(preManage + "->" + newManage);				
			}
			
			//권한변경여부
			if(preRoleViewArea != neWroleViewArea){
				$("#roleViewAreaDesc").val(preRoleViewArea + "->" + neWroleViewArea);
			}
			
			
			options.msg = "저장하시겠습니까?";
		$("#userInfoArea").formSubmit(options, validator, function(result){
			DIALOG.alert({
				title: "알림",
				msg: result.errorMessage
			}, function(){
				$(".input_area").find("button").click();
			});
		});

	});

	// 삭제
	$("#delBtn").unbind().bind("click", function(){
		
		let targetId = $("input[type=radio][name=status]").attr("id");
		let preStatus = $("label[for="+targetId+"]").text();
		DIALOG.confirm({
			title: "알림",
			msg: "삭제 하시겠습니까?"
		}, function () {
			if (this.key == "ok") {
				let url = "/admin/system/deleteSysm0102.do";
				let params = {};
					params.userId = userId;
					params.hstDesc = preStatus + "->" + "삭제";
					params.status = "D";
				utils.ajax(url, params, function(result){
					console.log("result", result);
					if(result.result.errorCode == "00"){
						DIALOG.alert({
							title: "알림",
							msg: result.result.errorMessage
						}, function(){
							if(this.key == "ok"){
								utils.reload();
							}
						});
					}else{
						DIALOG.alert({
							title: "알림",
							msg: result.result.errorMessage
						});
					}
				});
			}
		});

	});

	$("#searchUserNm").unbind().on("keydown", function(event){
		if(event.keyCode == 13){
			$(".input_button_area > button").eq(1).click();
			return false;
		}
	});
	
	// 마스킹 해제 팝업
	$("#maskReleaseBtn").unbind().on("click", function(){
		maskRelease();
	});
}

/**
 * 권한조회
 */
roleListFn = function(rollInfo,firstYn){	
	// 부서정보조회
	let url = "/admin/system/sysm0102List.do";
	let params = {};
		params.userId = rollInfo.userId;
	utils.ajax(url, params, function(response){
		let html = [];
		roleList = response.result.list;
		$.each(response.result.list, function(idx, data){
			html.push((idx + 1) + ". " + data.rname + "<br/>")
		});
		$("#roleViewArea").html(html.join(""));		
		
		if("Y" == firstYn){			
			//20190426 초기 권한 저장 
			preRoleViewArea = $("#roleViewArea").html();
		}
	});
}

//---------------------------------------
//마스킹 해제
//---------------------------------------
maskRelease = function(){
	$(this).popup({
		title : "SMS 인증"
		,width : "700px"
		,height: "380px"
		,url : "/common/commCertPop.do"
		,callBackFn : function(data){
			if(data.success == "00"){
				$(".input_button_area > button").eq(1).click();
			}
			return false;
		}
	});
}
