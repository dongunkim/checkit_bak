const userId = result.userId;
initFunction = function(){

	let option = {};
		option.colGroup = [
			 { key : "rid", label : "선택", width : "5", align : "center", type : "allCheckbox"}
			,{ key : "rname", label : "권한명", width : "10", align : "center"}
			,{ key : "description", label : "설명", width : "10", align : "center"}
			];

		$("#sysm0102PopListTable").list(option);

	eventFunction();
}

eventFunction = function(){

	// 저장
	$("#saveBtn").unbind().bind("click", function(){

		$("#sysm0102PopListTable").getCheckedList(function(checkedList){

			if(checkedList.length == 0){
				DIALOG.alert("부여할 권한명을 체크해 주세요.");
				return false;
			}
			DIALOG.confirm({
				title: "알림",
				msg: "권한을 부여 하시겠습니까?"
			}, function () {
				if (this.key == "ok") {
					let url = "/admin/system/insertSysm0101.do";
					let params = {};
						params.userId    = userId;
						params.paramList = JSON.stringify(checkedList);

					utils.ajax(url, params, function(data){
						if(data.result.errorCode == "00"){

							DIALOG.alert({
								title: "알림",
								msg: data.result.errorMessage
							}, function(){
								setPopupData({success: true, userId: userId});
							});

						}else{
							DIALOG.alert({
								title: "알림",
								msg: data.result.errorMessage
							});
						}

					});
				}
			});

		});

	});
//	//전체선택삭제
//	$("#sysm0102PopListTableallCheckbox").remove();
	
	$("#sysm0102PopListTableallCheckbox").parents(".check").remove();
	
	//체크박스 하나만 클릭
	$("input[name='sysm0102PopListTableallCheckbox']").on('change', function() {
	    $("input[name='sysm0102PopListTableallCheckbox']").not(this).prop('checked', false);  
	});
	
	// 닫기
	$("#closeBtn").unbind().bind("click", function(){
		closePopup();
	});

}