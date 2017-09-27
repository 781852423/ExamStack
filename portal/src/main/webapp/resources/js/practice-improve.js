$(function() {
	modal.prepare();
	examing.initial();

});

var examing = {
	initial : function initial() {
		this.initialModel();
		this.refreshNavi();
		this.bindNaviBehavior();
		this.addNumber();
		this.bindOptClick();
//		this.securityHandler();
//		this.updateSummery();
		this.bindfocus();
		this.bindFinishOne();
//		this.startTimer();
		this.bindSwitchQuestion();
		this.bindSubmitQuestion();
	},
	
	examModel : examModel = true,
	
	initialModel : function initialModel(){
		$(".answer-desc").hide();
		$(".question-point-content").hide();
		
		$("#switch-model-btn").click(function(){
			if($(this).data("exam") == true){
				$(this).data("exam",false);
				examing.examModel = false;
				$(this).removeClass("btn-success");
				$(this).addClass("btn-info");
				$(this).text("背题模式");
//				$("#bk-conent-comment").show();
				$(".answer-desc").show();
				
				$(".question-body input").attr("disabled","disabled");
				
				$("#submit-q-btn").hide();
				
			}else{
				$(this).data("exam",true);
				examing.examModel = true;
				$(this).removeClass("btn-info");
				$(this).addClass("btn-success");
				$(this).text("答题模式");
				
//				$("#bk-conent-comment").hide();
				$(".answer-desc").hide();
				$(".qt-finished .answer-desc").show();
				
				$(".question-body input").removeAttr("disabled");
				$(".qt-finished .question-body input").attr("disabled","disabled");
				$("#submit-q-btn").show();
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
			bottom : "-" + naviheight + "px",
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
//		 右键禁用
		
		if (document.addEventListener) {
			document.addEventListener("contextmenu", function(e) {
				 e.preventDefault();
			 }, false);
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
		$("#exam-control #question-navi").empty();
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
		if ($(".question").length === 0) {
			return false;
		}
		var questiontypes = this.questiontypes;
		var summery = "";
		for (var i = 0; i < questiontypes.length; i++) {
			var question_sum_q = $("." + questiontypes[i].code).length;
			if (question_sum_q == 0) {
				continue;
			} else {
				summery = summery + "<span class=\"exampaper-filter-item efi-" + questiontypes[i].code + "\">" 
				+ questiontypes[i].name + "[<span class=\"efi-fno\">0</span>/<span class=\"efi-tno\">" 
				+ $("." + questiontypes[i].code).length + "</span>]<span class=\"efi-qcode\" style=\"display:none;\">" 
				+ questiontypes[i].code + "</span></span>";
			}
		}
		// summery = summery.substring(0, summery.length - 2);
		$("#exampaper-desc").html(summery);
		
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
	/**
	 * 绑定考题focus事件(点击考题导航)
	 */
	bindfocus : function bindfocus() {
		$("#question-navi").delegate("a.question-navi-item ", "click", function() {
			var clickindex = $("a.question-navi-item").index(this);
			var questions = $("li.question");
			
			questions.hide();
			$(questions[clickindex]).show();
			
			$(".qni-selected").removeClass("qni-selected");
			$($("a.question-navi-item")[clickindex]).addClass("qni-selected");
			
		});
	},



	/**
	 * 完成一道题触发的function
	 */
	bindFinishOne : function bindFinishOne() {
		$(".question input[type=radio]").change(function() {
			var current_index = $("li.question").index($(this).parent().parent());
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

	},
	
	// 只要点击了选项，就调用这个函数去按下答题板和更新进度条
	updateAswerPanelAndProgress: function updateAswerPanelAndProgress()
	{
		console.log("检测到答题了！\n");
	    var thisquestion  = $(".question:visible");
	    
		var answer = examing.getAnswerValue(thisquestion);
		
		if(answer != "" && answer != null)
		{
			// 根据题号更精确的锁定press
			var questionNo = examing.getQuestionNo(thisquestion);
			console.log("第"+questionNo+'答题了\n');
			
			$($("a.question-navi-item")[questionNo-1]).addClass("pressed");
		}
	    examing.updateProgress();
		
	},
	
	// 获取questionNO
	getQuestionNo: function getQuestionNo(thisquestion)
	{
		if(thisquestion == undefined || thisquestion == null){
			console.log("getQuestionNo函数没接收到参数，参数无效，为null\n")
			return;
		}
		var questionNo = $(thisquestion).find("span.question-no").text();
		console.log('getQuestionNo函数内，获取题号:' + questionNo);
		
		if(questionNo != undefined && questionNo != null && questionNo != "")
		{
		   questionNo =  questionNo.substring(0,questionNo.lastIndexOf('.'));
			console.log('getQuestionNo函数内，获取整形题号:' + questionNo);
		}
		return questionNo;
	},
	/*
	 * 点击“提交答案”的按钮之后，出现的动作，修改原代码，加入以下逻辑：
	 * 如果点击“提交答案”按钮，则根据题目进度进行提示，若没有做完，则提示是否继续做？
	 * 如果已经做完，则更新面板上的东西，对、错、空白都标明
	 */
	bindSubmitQuestion : function bindSubmitQuestion(){
		$("#submit-q-btn").click(function(){
			
			// 遍历每一个题目，查看其正确与否并做好标记，未答题的写明：未作答
			// 添加代码，展示答题是否正确，直接判断
			examing.ShowAnswerResult();
			
			// 判断题目是否全部完成
			
			if(examing.updateProgress() == false)
			{
				
				var continueSubmit = confirm("题目尚未做完，确定提交吗？");
				   if(!continueSubmit)
					{
					   return false;
					}
			}
			else
			{
			   var continueSubmit = confirm("题目作答完毕，确定提交吗？");
			   if(!continueSubmit)
				{
				   return false;
				}
			}
		
			// 针对提交需要做的事情
			// 更新答题板panel上所有的答题效果：红色标注错误、蓝色标注答题正确，其他的不变
			// 遍历每个题目，去显示其做题结果
			examing.ShowAnswerResult();
	        
			
			//把已经做过的题目，无论是正确，还是错误的，都发到后台，作为统计信息
			//没有写答案的不发到后台
			examing.SendQuestionPractiveResult2Server();

            //			modal.showProgress();
			$(this).attr("disabled","disabled");

			
		});
		
		
	},
	
	SendQuestionPractiveResult2Server: function SendQuestionPractiveResult2Server()
	{
		// 遍历所有的问题
		var allQuestion = $(".question");
		var allQuestionLength = allQuestion.length;
		var questionHistoryList = new Array();
		
		for(var index = 0 ; index < allQuestionLength; index++)
		{
			var thisquestion = $(allQuestion[index]);
			
			if(thisquestion == null || thisquestion == undefined || thisquestion == "")
			{
				util.error("没有找到需要发送的题目信息");
				return false;
			}
			var data = new Object();
			var myAnswer = examing.getAnswerValue(thisquestion)
			if( myAnswer == undefined || myAnswer == "" || myAnswer == null)
			{
			   continue;
			}
			data.myAnswer = myAnswer;
			data.questionId = thisquestion.find(".question-id").text();
			data.questionTypeId = thisquestion.find(".question-type-id").text();
			data.pointId = thisquestion.find(".knowledge-point-id").text();
			data.answer = thisquestion.find(".answer_value").text();
			questionHistoryList.push(data);		
		}
		
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			async:false,
			type : "POST",
			url : "student/practice-improve/savePractiveHistory",
			data : JSON.stringify(questionHistoryList)
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				$(window).unbind('beforeunload');
				util.success("答案提交成功！");

			} else {
				util.error(message.result);
			}

			$("#submit-q-btn").removeAttr("disabled");
		});
		request.fail(function(jqXHR, textStatus) {
			util.error("系统繁忙请稍后尝试");
//			modal.hideProgress();
			$("#submit-q-btn").removeAttr("disabled");
		});
		
	},
	
	// 显示题目的正确答案和答题是否正确，同时在panel上显示出合适的标志
	// 对题目的答案，如果是客观题，则标注北京颜色到相应的图标上
	ShowAnswerResult: function ShowAnswerResult()
	{
		// 遍历每一个题目的答题情况
		var allQuestion = $(".question");
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
				        	   
								$(thisquestion).find(".answerResultDesc").html("<strong>回答正确</strong><br/>");
								$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-success");
								$($("a.question-navi-item")[index]).addClass("qni-success");
							}else
							{
								$(thisquestion).find(".answerResultDesc").html("<strong>回答错误</strong><br/>" +
				                 "你的回答：" + myAnswer);	
								$(thisquestion).find(".answer-desc-summary").addClass("answer-desc-error");
								$($("a.question-navi-item")[index]).addClass("qni-error");
								$(thisquestion).find(".answerResultDesc").css({color:"red"})
							}
				    	}
			           
			}else
			{
			    $(thisquestion).find(".answerResultDesc").html("<strong>此题为客观题，请根据以下参考答案自行判断答题是否正确</strong><br/>");	
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
		console.log('tagCorrectAndYourAnswer ' + optionNum + '个选项');
		// 如果相同，则标注参考答案的颜色，省去你的选项，否则都标注
		var optionValue = "";
		// 遍历每个选项，只要里面的选项value值 in correctAnswer内，就把它颜色标注来
		for(var index=0; index < optionNum; index++)
		{
			optionValue = $(optionList[index]).val();
			if(correctAnswer.indexOf(optionValue) >= 0)
			{
				$(optionList[index]).addClass("correctAnswer");
				console.log('option ' + optionValue + '被标注');
			}
		}
		
	},
	
	getAnswerValue : function getAnswerValue(questionVar) {
		
		var thisquestion;
		if(questionVar == undefined || questionVar == null || questionVar == "")
		{
			//console.log("传进来的信息是空的，因此选择目前可见的题目\n")
		   thisquestion = $(".question:visible");
		}else
		{
			//console.log("接收到传进来的questionxinxi\n")
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
		//console.log("getAnswerValue获得了你的答案：" + answer + "\n")
		return answer;
	},
	
	disableInput : function disableInput(questionIndex){
		
	},

	/**
	 * 开始倒计时
	 */
	startTimer : function startTimer() {
		var timestamp = parseInt($("#exam-timestamp").text());
		var int = setInterval(function() {
			$("#exam-timestamp").text(timestamp);
			$("#exam-clock").text(examing.toHHMMSS(timestamp));

			timestamp++;
		}, 1000);
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
		var questions = $("li.question");

		questions.each(function(index) {
			$(this).find(".question-no").text(index + 1 + ".");
		});
	},


	
	
	bindSwitchQuestion : function bindSwitchQuestion(){
		$(".question").hide();
		$($(".question")[0]).show();
		
		$($("a.question-navi-item")[0]).addClass("qni-selected");
			
		$("#previous-q-btn").click(function(){
//			examing.saveAnswerSheet();
		
			var allQuestion = $(".question");
			var thisquestion  = $(".question:visible");
			var thisindex = $(".question").index(thisquestion);
			if(thisindex == 0){
				return false;
			}else{
				
				thisquestion.hide();
				$(allQuestion[thisindex - 1]).show();
				$(".qni-selected").removeClass("qni-selected");
				$($("a.question-navi-item")[thisindex - 1]).addClass("qni-selected");
				
			}
		});
		
		$("#next-q-btn").click(function(){

			var allQuestion = $(".question");
			var thisquestion  = $(".question:visible");
			var thisindex = $(".question").index(thisquestion);
			var allQuestionLength = allQuestion.length;
			if(thisindex == allQuestionLength - 1){
				return false;
			}else{
				
				thisquestion.hide();
				$(allQuestion[thisindex + 1]).show();
				$(".qni-selected").removeClass("qni-selected");
				$($("a.question-navi-item")[thisindex + 1]).addClass("qni-selected");
				
			}
		});
	},
	
	loadStatus : function loadStatus(){
		modal.showProgress();
		
		var knowledgePointId = $("#knowledgePointId").text();
		var questionTypeId = $("#questionTypeId").text();
		var fieldId = $("#fieldId").text();
		
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "GET",
			async:false,
			cache: false,
			url : "student/practice-improve-his/" + fieldId + "/" + knowledgePointId + "/" + questionTypeId
		});
		
		request.done(function(data,tst,jqXHR) {
			if(!util.checkSessionOut(jqXHR))return false;
			
			if (data != null && data.length > 0 ) {
//				alert(data);
				var questionList =  $("li.question");
				
				var lastestIndex = data[data.length - 1];
				
				if(data.length >= questionList.length){
					util.notify("您已经完成了该知识类型的所有题，不过您仍然可以继续练习这些题。");
					$("a.question-navi-item").addClass("pressed");
				}else{
					for(var i = 0; i <questionList.length; i++){
						var questionId = parseInt($(questionList[i]).find(".question-id").text());
						if($.inArray(questionId, data) != -1){
//							alert($.inArray(questionId, data));
							$($("a.question-navi-item")[i]).addClass(
							"pressed");
						}
						
						if(lastestIndex == questionId ){
							var lastquestionIndex = i + 1;
							util.notify("您上次做到第 <strong>" + lastquestionIndex + "</strong> 题，现在您可以继续练习。");
							
							var questions = $("li.question");
							questions.hide();
							$(questions[i]).show();
								
							$(".qni-selected").removeClass("qni-selected");
							$($("a.question-navi-item")[i]).addClass("qni-selected");
								
						}
					}
				}
				
				
				
			}
			examing.updateProgress();
			modal.hideProgress();
		});
		
		request.fail(function(jqXHR, textStatus) {
			util.error("读取数据失败！");
//			$("#answer-save-info").addClass("answer-save-info-error");
//			modal.hideProgress();
			return false;
		});
	},
	
	updateProgress : function updateProgress(){
		var total = $("li.question").length;
		var finished = 100 * $("#question-navi-content .pressed").length;
		
		$(".h-progress span").attr("style","width:" + finished/total + "%;");
		var isFinished = false;
		if(finished/total == 1)
			{
			  isFinished = true;
			}
		return isFinished;
		
	},
	
	bindOptClick : function bindOptClick(){
		$("input[type=radio]").click(function(event){
			examing.updateAswerPanelAndProgress();
			event.stopPropagation();
		});
		$("input[type=checkbox]").click(function(event){
			examing.updateAswerPanelAndProgress();
			event.stopPropagation();
		});
		$(".question-list-item").click(function(){
			if($(this).find("input[type=radio]").length>0){
				$(this).find("input").click();
			}else if($(this).find("input[type=checkbox]").length>0){
				$(this).find("input").click();
			}
			examing.updateAswerPanelAndProgress();
		});
		
	}
};

var modal = {
	prepare : function prepare() {
		$(".content").append("<div id=\"loading-progress\" style=\"display:none;\"><div id=\"loading-content\"> <h2>正在读取您的记录</h2><img class=\"loading-gif\" src=\"resources/images/loading.gif\"/><div> </div>");

	},
	showProgress : function showProgress() {
		$("#loading-progress").show();
	},

	hideProgress : function hideProgress() {
		$("#loading-progress").hide();
	}
};
