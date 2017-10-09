$(function() {
	examing.initial();
});

var examing = {
		
		initial : function initial() {
			this.refreshNavi();
			this.bindNaviBehavior();
			this.addNumber();

			this.updateSummery();
			this.bindQuestionFilter();
			this.bindfocus();
			this.bindOpenModal();
			this.addRemoveBtn();
			this.bindRemoveQustionFromPaper();
			this.blindChangePoint();
			this.bindAddQustionToPaper();
			this.bindSavePaper();

		},
		bindNaviBehavior : function bindNaviBehavior() {

			var nav = $("#question-navi");
			var naviheight = $("#question-navi").height() - 33;
		

			$("#exampaper-footer").height($("#question-navi").height());

			nav.css({
				position : 'fixed',
				bottom : '0px',
				"z-index" : '1'	
			});

			

			$("#question-navi-controller").click(function() {
				if($("#question-navi-content").is(":visible") ){
					$("#question-navi-content").hide();
				}else{
					$("#question-navi-content").show();
					
				}
				

			});

		},
		/**
		 * 对题目重新编号排序
		 */
		addNumber : function addNumber() {
			var questions = $("li.question");

			questions.each(function(index) {
				$(this).find(".question-no").text(index + 1 + ".");
			});
		},
		/**
		 * 切换考题类型事件
		 */
		bindQuestionFilter : function bindQuestionFilter() {

			$("#exampaper-desc").delegate("span.exampaper-filter-item", "click", function() {
				var qtype = $(this).find(".efi-qcode").text();
				examing.doQuestionFilt(qtype);
			});
		},
		/**
		 * 刷新试题导航
		 */
		refreshNavi : function refreshNavi() {
			$("#question-navi-content").empty();
			var questions = $("li.question");

			questions.each(function(index) {
				var btnhtml = "<a class=\"question-navi-item\">" + (index + 1) + "</a>";
				$("#question-navi-content").append(btnhtml);
			});
		},
		/**
		 * 更新题目简介信息
		 */
		updateSummery : function updateSummery() {
			examing.refreshTotalPoint();
		},
		/**
		 * 计算总分
		 */
		refreshTotalPoint : function refreshTotalPoint(){
			var question_sum_p_all = 0;
			var point_array = $(".question-point");
			for(var i = 0; i<point_array.length;i++){
				var pointtmp = parseFloat($(point_array[i]).text());
				
				if(isNaN(pointtmp)){
					continue;
				}else{
					question_sum_p_all = question_sum_p_all + pointtmp * 10;
				}
			}
			$("#exampaper-total-point").text(question_sum_p_all/10);
		},
		
		/**
		 *切换到指定的题目部分
		 */
		doQuestionFilt : function doQuestionFilt(questiontype) {
			
			if($("#exampaper-desc .efi-" + questiontype).hasClass("efi-selected")){
				return false;
			}else{
				var questions = $("li.question");
				questions.hide();
				$("#exampaper-body ." + questiontype).show();
				
				$(".exampaper-filter-item").removeClass("efi-selected");
				$("#exampaper-desc .efi-" + questiontype).addClass("efi-selected");
			}
		},
		questiontypes : new Array({
			"name" : "单选题",
			"code" : "qt-singlechoice"
		}, {
			"name" : "多选题",
			"code" : "qt-multiplechoice"
		}, {
			"name" : "判断题",
			"code" : "qt-trueorfalse"
		}, {
			"name" : "填空题",
			"code" : "qt-fillblank"
		}, {
			"name" : "简答题",
			"code" : "qt-shortanswer"
		}, {
			"name" : "论述题",
			"code" : "qt-essay"
		}, {
			"name" : "分析题",
			"code" : "qt-analytical"
		}),
		
		bindOpenModal : function bindOpenModal(){
				$("#add-more-qt-to-paper").click(function() {
					// 定位此时的partId,在本行第二个td
					var partId= $(this).parent().parent().children("td").eq(1).text();
					console.log('获取到该partId=' + partId);
				     $("#partIdSpanInModelDailog").text(partId);
					$("#question-selector-modal").modal({backdrop:true,keyboard:true});
				
				});
		},
		/**
		 * 绑定考题focus事件(点击考题导航)
		 */
		bindfocus : function bindfocus() {
			$("#question-navi").delegate("a.question-navi-item ", "click", function() {
				var clickindex = $("a.question-navi-item").index(this);
				var questions = $("li.question");
				var targetQuestion = questions[clickindex];
				
				var targetQuestionType = $(questions[clickindex]).find(".question-type").text();
				
				examing.doQuestionFilt("qt-" + targetQuestionType);
//				$(targetQuestion).focus();
				
				examing.scrollToElement($(targetQuestion));
			});
		},
		
		addRemoveBtn : function(){
			var deletehtml = "<a class=\"tmp-ques-remove\" title=\"删除此题\">删除</a>";
			$(".question-title").append(deletehtml);
			
		},
		
		bindRemoveQustionFromPaper : function bindRemoveQustionFromPaper(){
			$("#exampaper-body").on("click", "a.tmp-ques-remove", function(){
				$(this).parent().parent().remove();
				examing.refreshNavi();
				examing.addNumber();
				examing.updateSummery();
				examing.reloadIframe();
			});
		},
		scrollToElement : function scrollToElement(selector, time, verticalOffset) {
			time = typeof (time) != 'undefined' ? time : 500;
			verticalOffset = typeof (verticalOffset) != 'undefined' ? verticalOffset : 0;
			element = $(selector);
			offset = element.offset();
			offsetTop = offset.top + verticalOffset;
			$('html, body').animate({
				scrollTop : offsetTop
			}, time);
		},
		
		blindChangePoint : function blindChangePoint() {
			$("#exampaper-body").on("click", "span.question-point", function(){
				$("#question-point-modal").modal({backdrop:true,keyboard:true});
				
				$("#question-point-modal .qt-point-destination input").val($(this).text());
				
				var questions = $("li.question");
				var indexno = questions.index($(this).parent().parent().parent());
				
				$("#qt-point-target-index").text(indexno + 1);
				
				
			});
			
			$("#update-point-btn").click(function(){
				var targetno = parseInt($("#qt-point-target-index").text());
				var newPoint = parseFloat($(".qt-point-destination input").val());
				if(targetno<=0 || isNaN(targetno)||newPoint<=0||isNaN(newPoint)){
					return false;
				}else{
					$($("li.question")[targetno-1]).find(".question-point").text(newPoint);
					examing.refreshTotalPoint();
					$("#question-point-modal").modal('hide');
					return false;
				}
			});
			
			$("#update-point-type-btn").click(function(){
				var targetno = parseInt($("#qt-point-target-index").text());
				var newPoint = parseFloat($(".qt-point-destination input").val());
				if(targetno<=0 || isNaN(targetno)||newPoint<=0||isNaN(newPoint)){
					return false;
				}else{
					var qt_type = $($("li.question")[targetno-1]).find(".question-type").text();
					$("li.qt-" + qt_type + " .question-point").text(newPoint);
					examing.refreshTotalPoint();
					$("#question-point-modal").modal('hide');
					return false;
				}
			});
		},
		
		composeEntity : function composeEntity(){
			//先查找每个part,然后在对part下面的questionId找到
			var parts = $("#exampaper-body").find('div.part');
			var partObj = new Object();
			var partObjArray = new Array();
			
			/**
			 * 生成partObjArray对象，里面对象有两个属性：partId和questionIdList
			 */
			for(var index = 0; index < parts.length; index++)
			{
				var thisPart = $(parts[index]);
				var partId = $(thisPart).attr('id');
				partObj.id = partId;
				var questions4part = $(thisPart).find('li.question');
				var partQuestiodArray=new Array();
				
				for (var qIndex = 0; qIndex < questions4part.length; qIndex++)
					{
						var questionId = $(questions4part[qIndex]).find('span.question-id').text();
						var Question = new Object();
						Question.id=questionId;
						partQuestiodArray.push(Question);
					}
				partObj.questions = partQuestiodArray;
				
				partObjArray.push(partObj);
				partObj = new Object(); // 新生成对象
			}
			
			
		
			return partObjArray;
		},
		// 试题保存
		bindSavePaper : function bindSavePaper(){
			var btn = $(".save-paper-btn");
			btn.click(function(){
				
				var partObjArray = examing.composeEntity();
				// 判断是否存在重复的题目，没有重复的，则继续往下
				var request = $.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + '/exampaper/update-exampaper/' + $("#exampaper-id").text(),
						data : JSON.stringify(partObjArray)
					});
					
					request.done(function(message,tst,jqXHR) {
						if(!util.checkSessionOut(jqXHR))return false;
						if (message.result == "success") {
							util.success("修改成功", function() {
								document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/exampaper/exampaper-list/0';
							});
						}else {
							util.error("操作失败请稍后尝试");
						}
					});
					request.fail(function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					});
				}
			});
		},
		
		bindAddQustionToPaper : function bindAddQustionToPaper(){
			$("button#add-list-to-exampaper").click(function() {
				
				var values = new Array();
				var checkboxs = $("#qt-selector-iframe").contents().find("table input:checked");
				$.each(checkboxs, function() {
					var id = $(this).val();
					values.push(id);
				});
				if (checkboxs.length == 0) {
					util.notify("请选择需要添加的试题!");
				}else{
					var partId=$('#partIdSpanInModelDailog').text();
					if(isNaN(partId))
					{
					  util.error("partId没有获取到，获取到的partId=" + partId +"不能添加成功");
					  return false;
					}
					var request = $.ajax({
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						type : "POST",
						url : util.getCurrentRole() + '/exampaper/get-question-detail4add/'+partId,
						data : JSON.stringify(values)
					});
					request.done(function(questionList,tst,jqXHR) {
						if(!util.checkSessionOut(jqXHR))return false;
						for(var i=0;i<questionList.length;i++){
							var question=questionList[i];
							var deletehtml = "<a class=\"tmp-ques-remove\" title=\"删除此题\">删除</a>";
                            
							var newquestion = $('<div/>').html(question.content).contents();
							newquestion.find(".question-title").append(deletehtml);
							// 找到此行对应的partId，根据此id，定位到相应div
							var partId=$('#partIdSpanInModelDailog').text();
							$("#exampaper-body div#"+partId+" div.questions4part").append(newquestion);
						}
						examing.refreshNavi();
						examing.addNumber();
						examing.updateSummery();
						examing.reloadIframe();
						$("#question-selector-modal").modal('hide');

						var questions = $("li.question");
						examing.scrollToElement($(questions[questions.length-1]));
						
					});
						 
					request.fail(function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					});
				}
			});
		},
		reloadIframe : function reloadIframe(){
			 document.getElementById('qt-selector-iframe').contentWindow.location.reload();
		},
		
		getType : function getType(input) {
		    var m = (/[\d]+(\.[\d]+)?/).exec(input);
		    if (m) {
		       // Check if there is a decimal place
		       if (m[1]) { return 'float'; }
		       else { return 'int'; }          
		    }
		    return 'string';
		}
};













