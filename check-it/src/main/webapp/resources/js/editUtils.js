(function($){

	$.edit = $.edit ||{}, $.extend($.edit,{
        version : "1.0.1"
	}),
	$.fn.extend({
		/**
		 * 에디터 호출
		 */
		edit : function(heigth, content){

			var items = utils.replaceAll($(this).selector, " ", "").split(",");
			var isReturn = true;
			$.each(items, function(index, item){

				var targetId = item.replace("#", "");
				var targetLen = $("#" + targetId).length;

				if(targetLen == 0){
					toast.push("에디터 [" + targetId + "] TARGET ID가 없습니다.");
					isReturn = false;
					return false;
				}else if(targetLen > 1){
					toast.push("중복된 에디터 [" + targetId + "] TARGET ID가 있습니다.");
					isReturn = false;
					return false;
				}

			});

			if(isReturn){

				$.each(items, function(index, item){

					var targetId = item.replace("#", "");
					editUtils.editInit(targetId, heigth, content);

				});

			}else{
				return isReturn;
			}

		},
		/**
		 * 에디터 데이터 set
		 */
		setEdit : function(data){

			var target = $(this).selector.replace("#", "");
			var instance = editUtils.getInstance(target);
			instance.setContent(data);

		},
		/**
		 * 에디터 데이터 get
		 */
		getEditContent : function(){

			var target = $(this).selector.replace("#", "");
			var instance = editUtils.getInstance(target);
			var editContent = instance.getContent();

			if(instance.getContentCheck()){
				if(editContent === "undefined"){
					editContent = "";
				}else{
					editContent = utils.replaceAll(editContent, '"', "&quot;");
				}
			}else{
				editContent = "";
			}

			return editContent;

		}
	})

})(jQuery);

editUtils ={
		/**
		 * 에디터 instance
		 * @param target
		 * @returns
		 */
		getInstance : function(target){

			var targetId;
			if($.type(target) === "undefined"){
				targetId = this.target;
			}else{
				targetId = target;
			}

			var instance;
			$.each(window.AXEditor_instances, function(index, klass){
				if(klass.config.targetID == targetId){
					instance = klass;
					return false;
				}
			});
			$.extend(this, instance);
			return instance;
		},
		/**
		 * 에디터 초기화및 공통환경추가
		 */
		editInit : function(targetId, heigth, content){
			this.editMake(targetId, heigth, content);
		},
		/**
		 * 에디터 만들기
		 */
		editMake : function(targetId, heigth, content){

			this.target = targetId;
			editTarget = $(targetId);
			window.editTarget = new AXEditor();

			var defaultOption = {
					 targetID: targetId
					,lang: "kr"
					,height: ($.type(heigth) === "undefined")?200:heigth
					,frameSrc: "/resources/lib/axj/html/AXEditor.html"
					,editorFontFamily: "Malgun Gothic"
					,fonts: ["Malgun Gothic", "Gulim", "Dotum", "궁서"]
					,toolbar: {
						 fontFamily: true
						,fontSize: true
						,fontDecoration: true
						,color: true
						,textAlign: true
						,orderList: false
						,lineHeight: true
						,url: true
						,imoticon: false
						,pageBreak: false
					 }
					,onReady: function (){
						if(!($.type(content) === "undefined")){
							$("#" + targetId).setEdit(content);
						}
					 }
					,onresize: function(){
						console.log(this);
					}
				}

			editTarget.setConfig(defaultOption);

		}
}