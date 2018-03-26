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

		this.updateSummery();
	
		this.bindQuestionFilter();

		this.bindfocus();
	
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

	},

	bindNaviBehavior : function bindNaviBehavior() {

		var nav = $("#question-navi");
		var naviheight = $("#question-navi").height() - 33;
		var bottompx = "-" + naviheight + "px;";

		var scrollBottomRated = $("footer").height() + 2 + 100 + naviheight;

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
		
		examing.doQuestionFilt($($(".exampaper-filter-item")[0]).find(".efi-qcode").text());
	},

	questiontypes : new Array({
		"name" : "单选题",
		"code" : "qt-singlechoice"
	}, {
		"name" : "多选题",
		"code" : "qt-multiplechoice"
	}),
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
			
			examing.scrollToElement($(targetQuestion),0,-100);
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
			console.log("period :" + period);
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
	 *切换到指定的题型 
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
		} 
		
		console.log("getAnswerValue获得了你的答案：" + answer + "\n")
		return answer;
	},
	
	disableInput : function disableInput(questionIndex){
		
	},

	bindSubmit : function bindSubmit() {
		$("#question-submit button").click(function() {
			if (confirm("确认交卷吗？")) {
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
	
		
		$("#question-submit button").attr("disabled", "disabled");
		var request = $.ajax({
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			type : "POST",
			url : "student/personalitytest-submit",
			data : JSON.stringify(data)
		});

		request.done(function(message, tst, jqXHR) {
			if (!util.checkSessionOut(jqXHR))
				return false;
			if (message.result == "success") {
				$(window).unbind('beforeunload');
				util.success("交卷成功,请查看报告！", function() {
					// window.location.replace(document.getElementsByTagName('base')[0].href + 'student/finished-submit');
					$("#question-submit button").text("已完成交卷");
					form = $("<form method='post' action='student/getPersonalityTestReport'></form>");
				    str = JSON.stringify(message.object);
				    console.log('str:' + str);
					input = $("<input type='text'>").val(str).attr('name','PersonalityScoreListStr')
					form.append(input);
					$(document.body).append(form);
					form.submit()
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
		// 此处省略对answerSheet的保存工作,考虑性格测试可能会反复的做,因此直接return false
		return false;
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
