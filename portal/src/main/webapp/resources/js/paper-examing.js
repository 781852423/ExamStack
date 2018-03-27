$(function() {
	$(document).ready(function(){
	modal.prepare();
	examing.initial();
	});

});

var examing = {
	initial : function initial() {
		$(window).scroll(examing.fixSideBar);

		this.initialModel();

		this.refreshNavi();

		this.bindNaviBehavior();

		this.addNumber();

		this.bindOptClick();
	
		this.bindFinishOne();
		
		this.initMarker();

		this.loadAnswerSheet();
	
		this.startTimer();

		this.bindSubmit();
	
	},
	
	initialModel : function initialModel(){
		$(".answer-desc").hide();
		$(".question-point-content").hide();
		//$('.firstrow-col2').css("height",$('.firstrow-col1').height())
		$(".answer-desc").hide();
		$(".qt-finished .answer-desc").show();
		
		$(".question-body input").removeAttr("disabled");
		$(".qt-finished .question-body input").attr("disabled","disabled");
		
		$(function(){
			$("#recyleExambtn").click(function(){
				
				$.confirm({
				    title: '确定清除此考试的记录重新开始做题吗？',
				    content: '清除后会重新计时并清除之前答题记录，确认继续，取消返回',
				    buttons: {
						        confirm: {
								            text: '确定',
								            btnClass: 'btn-blue',
								            keys: ['enter', 'shift'],
								            action: function(){
										            	var currentURL = window.location.href;
														window.location.href= currentURL.substring(0,currentURL.lastIndexOf('/')) + '/0';
														window.location.load();
									            	    $.alert('完成');
						                            }
					               
					                       },
				      
							        cancel: {
									            text: '取消',
									            btnClass: 'btn-blue',
									            keys: ['enter', 'shift'],
									            action: function(){
								                   $.alert('已取消');
							                     }
				                             } 
				              }
				           }
				       );
				
			});
		});
	},
	
	fixSideBar : function fixSideBar() {
		var nav = $("#bk-exam-control");
		var title = $("#exampaper-title");
		var container = $("#exampaper-desc-container");
		var paper = $("#exampaper-body");
		if ($(this).scrollTop() > 147) {
			nav.addClass("fixed");
			title.addClass("exampaper-title-fixed");
			container.addClass("exampaper-desc-container-fixed");
			paper.addClass("exampaper-body-fixed");
		} else {
			nav.removeClass("fixed");
			title.removeClass("exampaper-title-fixed");
			container.removeClass("exampaper-desc-container-fixed");
			paper.removeClass("exampaper-body-fixed");
		}
	},
	/**
	 * 完成一道题触发的function
	 */
	bindFinishOne : function bindFinishOne() {
		$(".question input[type=radio]").change(function() {
			var current_index = $("li.question").index($(this).parent().parent().parent().parent());
			$($("a.question-navi-item")[current_index]).addClass("pressed");
			$(this).parent().parent().find(".question-list-item-selected").removeClass("question-list-item-selected");
			$(this).parent().addClass("question-list-item-selected");
		});

		$(".question input[type=checkbox]").change(function() {
			var current_question = $(this).parent().parent().parent().parent();
			var current_index = $("li.question").index(current_question);
			var checkedboxs = current_question.find("input[type=checkbox]:checked");
			if (checkedboxs.length > 0) {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
			}
			
			if($(this).parent().hasClass("question-list-item-selected")){
				$(this).parent().removeClass("question-list-item-selected");
			}else{
				$(this).parent().addClass("question-list-item-selected");
			}
		});

		$(".question textarea").bind('input propertychange', function() {

			var current_index = $("li.question").index($(this).parent().parent());
			if ($(this).val() != "") {
				$($("a.question-navi-item")[current_index]).addClass("pressed");
			} else {
				$($("a.question-navi-item")[current_index]).removeClass("pressed");
			}
		});

	},

	bindNaviBehavior : function bindNaviBehavior() {

		var nav = $("#question-navi");
		var naviheight = $("#question-navi").height() - 33;
		var bottompx = "-" + naviheight + "px;";
		// alert(naviheight);

		var scrollBottomRated = $("footer").height() + 2 + 100 + naviheight;
		// alert($("footer").height() );
		// alert(scrollBottomRated);

		$("#exampaper-footer").height($("#question-navi").height());

		nav.css({
			position : 'fixed',
			bottom : '0px',
			"z-index" : '1'	
		});

		

		$("#question-navi-controller").click(function() {
			var scrollBottom = document.body.scrollHeight - $(window).scrollTop() - $(window).height();

			var nav = $("#question-navi");
			var attr = nav.attr("style");

			if (nav.css("position") == "fixed") {
				if (nav.css("bottom") == "0px") {
					nav.css({
						bottom : "-" + naviheight + "px"
					});
				} else {
					nav.css({
						bottom : 0
					});
				}

			}

		});

	},

	securityHandler : function securityHandler() {
		// 右键禁用
		if (document.addEventListener) {
			document.addEventListener("contextmenu", function(e) {e.preventDefault();}, false);
		} else {
			document.attachEvent("contextmenu", function(e) {
				 e.preventDefault();
			 });
		}

		$(window).bind('beforeunload', function() {
			return "考试正在进行中...";
		});
	},

	/**
	 * 刷新试题导航
	 */
	refreshNavi : function refreshNavi() {
		$("div.mftm_con").empty();
		// 浏览每个part
		var parts = $("#exampaper-body div.part");
		var partLength = parts.length;
		for(var pIndex = 0; pIndex < partLength; pIndex++)
		{
			// 浏览每一部分的题目
			var currentPart = parts[pIndex];
			var questions = $(currentPart).find("li.question");
			var partId = $(currentPart).attr("id");
			partId = partId.substr(partId.lastIndexOf('_')+1);
			questions.each(function(index) {
				var questionId = $(this).find(".question-id").text();
				var btnhtml = "<a class=\"question-navi-item\"  href=\"" + examing.getCurrentPageRawUrl() +"#question_" +questionId+ "\">" + (index + 1) + "</a>";
				$("#question-navi-content div.mkrf_item#answersheet_"+partId + " div.mftm_con").append(btnhtml);
			});
		}
		
		// 针对题目part切换的a标签，也重新写
		// $("#myId").attr("href","www.xxx.com"); 
		$('a.partNavi').each(function()
		{
			$(this).attr("href",examing.getCurrentPageRawUrl()+$(this).attr("href")); 
		}
		);
		
	},
	
	getCurrentPageRawUrl:function getCurrentPageRawUrl()
	{
	    if(window.location.href.indexOf('#') >= 0)
    	{
	    	return window.location.href.substr(0,window.location.href.indexOf('#'));
    	}else {
			return window.location.href;
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



	/**
	 * 开始倒计时
	 */
	startTimer : function startTimer() {
		var timestamp = parseInt($("#exam-timestamp").text());
		console.log("timestamp=" + timestamp);
		var int = setInterval(function() {
			$("#exam-timestamp").text(timestamp);
			$("#exam-clock").text(examing.toHHMMSS(timestamp));
			if(timestamp < 600){
				var exam_clock = $("#question-time");
				exam_clock.removeClass("question-time-normal");
				exam_clock.addClass("question-time-warning");
			}
			var period = timestamp % 60;
			
			if(period == 0)
				examing.saveAnswerSheet();
			timestamp-- || examing.examTimeOut(int); 
		}, 1000);
	},
	
	
	/**
	 * 考试时间到
	 * @param int
	 */
	examTimeOut : function examTimeOut (int){
		clearInterval(int); 
		// 提示考试时间到，要交卷
		alert("考试时间到，关闭此窗口自动交卷，如需重新做题，点击页面上部'重新开始’按钮");
		setTimeout(examing.finishExam(),6000);
		examing.ShowAnswerResult();
	
	},

	/**
	 * 时间formater
	 *
	 * @param timestamp
	 * @returns {String}
	 */
	toHHMMSS : function toHHMMSS(timestamp) {
		var sec_num = parseInt(timestamp, 10);
		var hours = Math.floor(sec_num / 3600);
		var minutes = Math.floor((sec_num - (hours * 3600)) / 60);
		var seconds = sec_num - (hours * 3600) - (minutes * 60);

		if (hours < 10) {
			hours = "0" + hours;
		}
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		var time = hours + ':' + minutes + ':' + seconds;
		return time;
	},

	
	/**
	 * 对题目重新编号排序
	 */
	addNumber : function addNumber() {
		var parts = $("#exampaper-body div.part");
		var partLength = parts.length;
		for(var pIndex = 0; pIndex < partLength; pIndex++)
		{
			// 浏览每一部分的题目
			var currentPart = parts[pIndex];
			var questions = $(currentPart).find("li.question");
		
			questions.each(function(index) {
				$(this).find(".question-no").text(index + 1 + ".");
			});
		}
	},

	// 显示题目的正确答案和答题是否正确，同时在panel上显示出合适的标志
	// 对题目的答案，如果是客观题，则标注北京颜色到相应的图标上
	ShowAnswerResult: function ShowAnswerResult()
	{
		// 遍历每一个题目的答题情况
		// 根据每个part计算得分
		
		var allQuestion = $("li.question");
		var allQuestionLength = allQuestion.length;
		
		for( var index = 0; index < allQuestionLength; index++ )
		{
			var thisquestion  = $(allQuestion[index]);
			
			thisquestion.addClass("qt-finished");
			thisquestion.find(".question-body input").attr("disabled","disabled");
			thisquestion.find(".answer-desc").show();
			
			// 判断是否是客观题
			var ActiveQuestionTypeId = thisquestion.find(".question-type-id").text();
			var myAnswer = examing.getAnswerValue(thisquestion);
			var correctAnswer = thisquestion.find(".answer_value").text();
		
			if(ActiveQuestionTypeId <= 3 )
			{
				// 本题正确、错误的选项标注颜色
				   examing.tagCorrectAndYourAnswer(thisquestion);
				   
				    if(myAnswer == undefined || myAnswer == "" || myAnswer == null)
				    	{
				    	  $(thisquestion).find(".answerResultDesc").html("<strong>本题未答</strong><br/>");
				    	}
				    else
				    	{
					    	if( myAnswer == correctAnswer)
							{
				        	   
								$(thisquestion).find(".answerResultDesc").html("回答正确");
								$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-success");
								$($("a.question-navi-item")[index]).addClass("qni-success");
							}else
							{
								
								$(thisquestion).find(".answerResultDesc").html("回答错误" +
				                 "你的回答：" + myAnswer);	
								$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-error");
								$($("a.question-navi-item")[index]).addClass("qni-error");
								$(thisquestion).find(".answerResultDesc").css({color:"red"})
							}
				    	}
			           
			}else
			{
			    $(thisquestion).find(".answerResultDesc").html("此题为客观题，请根据以下参考答案自行判断答题是否正确");	
			}
			
		}
		
	},
	// 调用这个函数的前提是，该题目是客观题
	tagCorrectAndYourAnswer: function tagCorrectAndYourAnswer(thisquestion)
	{
		// 找到本题正确答案,标记出来
		if(thisquestion == null || thisquestion == "")
			{
				console.log('没有传入题目，无法对答案标记tag');
				return false;
			}
		var correctAnswer = $(thisquestion).find(".answer_value").text();
		var myAnswer = examing.getAnswerValue(thisquestion);
		var optionList = $(thisquestion).find(".question-list-item input");
		var optionNum = optionList.length;
		// console.log('tagCorrectAndYourAnswer ' + optionNum + '个选项');
		// 如果相同，则标注参考答案的颜色，省去你的选项，否则都标注
		var optionValue = "";
		// 遍历每个选项，只要里面的选项value值 in correctAnswer内，就把它颜色标注来
		for(var index=0; index < optionNum; index++)
		{
			optionValue = $(optionList[index]).val();
			if(correctAnswer.indexOf(optionValue) >= 0)
			{
				$(optionList[index]).addClass("correctAnswer");
				// console.log('option ' + optionValue + '被标注');
			}
		}
		
	},
	
	calculateTotalPoint: function calculateTotalPoint()
	{
		// 遍历答题卡内容
		var answerSheet4Parts = $("#question-navi-content .mkrf_item");
		var totalPoint = 0.0;
		var thisPartTotalPoint = 0.0;
		answerSheet4Parts.each(function()
		{
			thisPartTotalPoint = 0.0;
			
			// 找到对应的partId
			var partId = $(this).attr("id"); // 格式answersheet_2
			
		     partId = partId.substr(partId.lastIndexOf('_')+1); // 获取partId
		     // 获取每个partId对应的每道题分数
		     var eachQuestionPoint = $(this).find('.PointPerQuestion').text();
		     
		     var answerSheetItemATags = $(this).find("a.question-navi-item");
		   // 看其样式是否是正确答题的
		     answerSheetItemATags.each(function()
    		 {
    	 		var aClassStr = $(this).attr("class");
    	 		if(aClassStr.indexOf("qni-success") >=0)
	 			{
    	 			// 答题正确
    	 			thisPartTotalPoint += parseFloat(eachQuestionPoint);
	 			}
    		 }

		     );
		     // 把这个part的分数标记到part标题右侧
		     $("div#part_"+partId).find(".mkfs_rgt").text(thisPartTotalPoint+"分");
		     console.log("partId_" + partId + "获得总分:" + thisPartTotalPoint);
		  // 把所有part分数加起来
		     totalPoint += thisPartTotalPoint;
		}
		 
		),
	    
		// 显示到试卷上面
		$('#exampaper-title-name .tot_score').text("得分："+ totalPoint+"分");
		console.log('总分：' + totalPoint);
	},
	
	getAnswerValue : function getAnswerValue(questionVar) {
		
		var thisquestion;
		if(questionVar == undefined || questionVar == null || questionVar == "")
		{
			console.log("传进来的信息是空的，因此选择目前可见的题目\n")
		    return;
		}else
		{
			thisquestion = questionVar;
		}
	
		
		var answer;
		
		if(thisquestion.hasClass("qt-singlechoice")){
			var radio_checked = $(thisquestion).find("input[type=radio]:checked");
			var radio_all = $(thisquestion).find("input[type=radio]");
			if(radio_checked.length == 0){
				answer = "";
			}else{
				var current_index = $(radio_all).index(radio_checked);
				answer = String.fromCharCode(65 + current_index);
			}
			
		}else 	if( $(thisquestion).hasClass("qt-multiplechoice")){
			
			var checkbox_checked = $(thisquestion).find("input[type=checkbox]:checked");
			var checkbox_all = $(thisquestion).find("input[type=checkbox]");
			if(checkbox_checked.length == 0){
				answer = "";
			}else{
				var tm_answer = "";
				for(var l = 0 ; l < checkbox_checked.length; l++){
					var current_index = $(checkbox_all).index($(checkbox_checked[l]));
					tm_answer = tm_answer + String.fromCharCode(65 + current_index);
				}
				answer = tm_answer;
			}
		} else 	if( $(thisquestion).hasClass("qt-trueorfalse")){
			
			var radio_checked = $(thisquestion).find("input[type=radio]:checked");
			var radio_all = $(thisquestion).find("input[type=radio]");
			if(radio_checked.length == 0){
				answer = "";
			}else{
				var current_index = $(radio_all).index(radio_checked);
				answer = (current_index==0)?"正确":"错误";
			}
		}else{
			answer = $(thisquestion).find("textarea").val();
		}
		console.log("getAnswerValue获得了你的答案：" + answer + "\n")
		return answer;
	},
	
	disableInput : function disableInput(questionIndex){
		
	},

	bindSubmit : function bindSubmit() {
		$("#question-submit button").click(function() {
			if (confirm("确认交卷吗？")) {
				examing.ShowAnswerResult();
				examing.calculateTotalPoint();
				examing.finishExam();
			}
		});
	},

	finishExam : function finishExam() {
		modal.showProgress();
		var answerSheetItems = examing.genrateAnswerSheet();
		var data = new Object();
		var examHistroyId = $("#hist-id").val();
		data.examHistroyId = examHistroyId;
		data.answerSheetItems = answerSheetItems;
		var timeStr = $("#exam-clock").text();
		var time = timeStr.split(":");
		var hours = parseInt(time[0]);
		var minutes = parseInt(time[1]);
		var seconds = parseInt(time[2]);
		data.duration = hours * 3600 + minutes * 60 + seconds;
		data.examId=$("#exam-id").val();
		data.examPaperId=$("#paper-id").val();
		//data.startTime=$("#start-time").val();
		
		$("#question-submit button").attr("disabled", "disabled");
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "student/exam-submit",
			data : JSON.stringify(data)
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				$(window).unbind('beforeunload');
				util.success("交卷成功,总分在页面顶部显示，可以查看页面参考答案！", function() {
					// window.location.replace(document.getElementsByTagName('base')[0].href + 'student/finished-submit');
					$("#question-submit button").text("已完成交卷");
				});
			} else {
				util.error(message.result);
				$("#question-submit button").removeAttr("disabled");
			}
			modal.hideProgress();
		});
		request.fail(function(jqXHR, textStatus) {
			alert("系统繁忙请稍后尝试");
			$("#question-submit button").removeAttr("disabled");
			modal.hideProgress();
		});
	},

	genrateAnswerSheet : function genrateAnswerSheet() {
		//		var as = new Array();
		/*var as = {};*/
		var answerSheetItems = new Array();
		var questions = $(".question");

		for (var i = 0; i < questions.length; i++) {
			var answerSheetItem = new Object();

			if ($(questions[i]).hasClass("qt-singlechoice")) {
				var radio_checked = $(questions[i]).find("input[type=radio]:checked");
				var radio_all = $(questions[i]).find("input[type=radio]");
				if (radio_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var current_index = $(radio_all).index(radio_checked);
					answerSheetItem.answer = String.fromCharCode(65 + current_index);
				}
				answerSheetItem.questionTypeId = 1;
			} else if ($(questions[i]).hasClass("qt-multiplechoice")) {

				var checkbox_checked = $(questions[i]).find("input[type=checkbox]:checked");
				var checkbox_all = $(questions[i]).find("input[type=checkbox]");
				if (checkbox_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var tm_answer = "";
					for (var l = 0; l < checkbox_checked.length; l++) {
						var current_index = $(checkbox_all).index($(checkbox_checked[l]));
						tm_answer = tm_answer + String.fromCharCode(65 + current_index);
					}
					answerSheetItem.answer = tm_answer;
				}
				answerSheetItem.questionTypeId = 2;
			} else if ($(questions[i]).hasClass("qt-trueorfalse")) {

				var radio_checked = $(questions[i]).find("input[type=radio]:checked");
				var radio_all = $(questions[i]).find("input[type=radio]");
				if (radio_checked.length == 0) {
					answerSheetItem.answer = "";
				} else {
					var current_index = $(radio_all).index(radio_checked);
					answerSheetItem.answer = (current_index == 0) ? "T" : "F";
				}
				answerSheetItem.questionTypeId = 3;
			} else if ($(questions[i]).hasClass("qt-fillblank")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 4;
			} else if ($(questions[i]).hasClass("qt-shortanswer")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 5;
			} else if ($(questions[i]).hasClass("qt-essay")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 6;
			} else if ($(questions[i]).hasClass("qt-analytical")) {
				answerSheetItem.answer = $(questions[i]).find("textarea").val();
				answerSheetItem.questionTypeId = 7;
			}
			answerSheetItem.point = 0;
			answerSheetItem.questionId = $(questions[i]).find(".question-id").text();

			
			answerSheetItems.push(answerSheetItem);
		}
		return answerSheetItems;
	},
	bindOptClick : function bindOptClick(){
		$("input[type=radio]").click(function(event){
			
			event.stopPropagation();
		});
		$("input[type=checkbox]").click(function(event){
			
			event.stopPropagation();
		});
		$(".question-list-item").click(function(){
			if($(this).find("input[type=radio]").length>0){
				$(this).find("input").click();
			}else if($(this).find("input[type=checkbox]").length>0){
				$(this).find("input").click();
			}
			
			
		});
		
	},
	loadAnswerSheet : function loadAnswerSheet(){
		var answerSheet = eval('(' + localStorage.answerSheet + ')');
		// console.log(eval(answerSheet));
		this.mergeQuestionAnswer(answerSheet);
	},
	
	saveAnswerSheet : function saveAnswerSheet(){
		console.log("answerSheet saving...");

		
		var answerSheetItems = examing.genrateAnswerSheet();
		var data = new Object();
		var examHistroyId = $("#hist-id").val();
		data.examHistroyId = examHistroyId;
		data.answerSheetItems = answerSheetItems;
		var timeStr = $("#exam-clock").text();
		var time = timeStr.split(":");
		var hours = parseInt(time[0]);
		var minutes = parseInt(time[1]);
		var seconds = parseInt(time[2]);
		data.duration = hours * 3600 + minutes * 60 + seconds;
		data.examId=$("#exam-id").val();
		data.examPaperId=$("#paper-id").val();
		
		localStorage.answerSheet = JSON.stringify(data);
		
	},
	
	mergeQuestionAnswer : function mergeQuestionAnswer(answerSheet){
		// 如果做试卷的人希望从头开始做,endsWith 0,那么也不要启用localStorage
		if (answerSheet==null || answerSheet.examHistroyId != $("#hist-id").val() || window.location.href.endsWith("0"))
			return false;
		var questions = $("li.question");

		// 如果是从新开始的计算时间，则不要本地存储的duration
		if(!window.location.href.endsWith("0"))
		{
		    $("#exam-timestamp").text(answerSheet.duration);
		}
		
		
		var list = answerSheet.answerSheetItems;
		var quetion_navis = $("a.question-navi-item");
		
		if(list.length != questions.length){
			return false;
		}
		for(var i = 0 ; i < list.length; i++){
			if(list[i].questionTypeId == 1){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var tmp_char =tmp_answer.charAt(0);
				$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
				$(quetion_navis[i]).addClass("pressed");
				$(questions[i]).find("input[value='"+tmp_char+ "']").parent().addClass("question-list-item-selected");
			}else if(list[i].questionTypeId == 2){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var answer_length = tmp_answer.length;
				for(var l = 0 ; l < answer_length; l++){
					var tmp_char =tmp_answer.charAt(l);
					$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
					$(questions[i]).find("input[value='"+tmp_char+ "']").parent().addClass("question-list-item-selected");
				}
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 3){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				var tmp_char =tmp_answer.charAt(0);
				$(questions[i]).find("input[value='"+tmp_char+ "']").prop('checked', true);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 4){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 5){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 6){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}else if(list[i].questionTypeId == 7){
				var tmp_answer = list[i].answer;
				if(tmp_answer == "") continue;
				$(questions[i]).find("textarea").val(tmp_answer);
				$(quetion_navis[i]).addClass("pressed");
			}
		}
	},
	initMarker : function initMarker(){
		$(".question-title").delegate(".question-mark", "click", function() {
			if($(this).find(".fa-bookmark").length > 0){
				$(this).html("<i class=\"fa fa-bookmark-o\">");
				
				var current_index = $("li.question").index($(this).parent().parent());
				$($("a.question-navi-item")[current_index]).removeClass("question-navi-item-marked");
				
			}else{
				$(this).html("<i class=\"fa fa-bookmark\">");
				var current_index = $("li.question").index($(this).parent().parent());
				$($("a.question-navi-item")[current_index]).addClass("question-navi-item-marked");
			}
			
			
		});

		var markhtml = "<span class=\"question-mark\" title=\"Marked as uncertain\"><i class=\"fa fa-bookmark-o\"></span>";
		$(".question-title").append(markhtml);
		
	
	}
};

var modal = {
	prepare : function prepare() {
		$(".content").append("<div id=\"loading-progress\" style=\"display:none;\"><div id=\"loading-content\"> <h2>正在提交您的答案</h2><img class=\"loading-gif\" src=\"resources/images/loading.gif\"/><div> </div>");

	},
	showProgress : function showProgress() {
		$("#loading-progress").show();
	},

	hideProgress : function hideProgress() {
		$("#loading-progress").hide();
	}
};
