$(function() {
	question_add.initial();

});

question_add = {
	initial : function initial() {
		this.commonInit();
		this.bindAddPoint();
		this.bindChangeQuestionType();
		this.bindAddOpt();
		this.bindRemoveOpt();
		this.bindSubmit();
		this.getParentIDs();
		this.getTags();
		this.getCharactorTypes();
	},
	
	commonInit: function commonInit()
	{
		//$("div.form-line.form-question-answer1.correct-answer").hide();
		$("div.form-line.form-question-reference").hide();
		$("div.form-line.form-question-examingpoint").hide();
		$("div.form-line.form-question-keyword").hide();
	},
/**
 * 
<option value="1">单选题</option>
<option value="2">多选题</option>
<option value="3">判断题</option>
<option value="4">填空题</option>
<option value="5">简答题</option>
<option value="6">论述题</option>							
<option value="7">计算题</option>							
<option value="8">名词解释</option>							
<option value="9">QuestionParent</option>						
<option value="10">性格测试</option>
 */
	bindChangeQuestionType : function changeQuestionType() {
		$("#question-type select").change(function() {
			if (1 == $(this).val()) {
				$(".correct-answer").hide();
				$(".form-question-opt").show();
				$(".form-question-answer1").show();
				question_add.copyToAnswer();
			} else if (2 == $(this).val()) {
				$(".correct-answer").hide();
				$(".form-question-opt").show();
				$(".form-question-answer-muti").show();
				question_add.copyToAnswer();
			} else if (3 == $(this).val()) {
				$(".correct-answer").hide();
				$(".form-question-opt").hide();
				$(".form-question-answer-boolean").show();
			} else if(9 == $(this).val()){
				$(".form-question-opt").hide();
				$(".question-knowledge").hide();
				$(".form-question-reference").hide();
				$(".form-question-examingpoint").hide();
				$(".form-question-keyword").hide();
				$(".form-question-analysis").hide();
				$(".correct-answer").hide();
				
				//9表示纯的提干，没有问，也没有答案，例如阅读理解的正文，综合分析的正文部分
				
			}else if (10 == $(this).val()) {
				$(".correct-answer").hide();
				
				$("div.form-line.form-question-answer1.correct-answer").hide();
				
				$("div.form-line.form-question-analysis").hide();
				
				$(".form-question-opt").show();
				//$(".form-question-answer1").show();
				//question_add.copyToAnswer();
				//去掉D选项
				$($('.add-opt-items .add-opt-item')[3]).remove();
				// ABC都设置上默认的内容
				$($('.add-opt-items .add-opt-item input')[0]).val("很符合");
				$($('.add-opt-items .add-opt-item input')[1]).val("一般符合");
				$($('.add-opt-items .add-opt-item input')[2]).val("不符合");
			}
			else {
				$(".correct-answer").hide();
				$(".form-question-opt").hide();
				$(".form-question-answer-text").show();
			}

		});
	},

	bindSubmit : function bindSubmit() {
		$("#question-add-form").submit(function() {

			var verify_result = question_add.verifyInput();//如果是单独的题目正文，则不做任何检查
			if (verify_result) {
			//	var question_entity = question_add.composeEntity();
				var data = question_add.composeEntity();
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "POST",
					url : "secure/question/question-add",
					data : JSON.stringify(data),
					success : function(message, tst, jqXHR) {
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							util.success("添加成功", function() {
								//document.location.href = document.getElementsByTagName('base')[0].href + util.getCurrentRole() + '/question/question-list';
								question_add.clearContent();
							});
						} else {
							util.error("操作失败请稍后尝试");
						}

					},
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					}
				});
			}

			return false;
		});
	},
	
	
	clearContent : function clearContent(){
		$(".add-question-ta").val("");
		$(".display-opt-img").remove();
		$(".display-content-img").remove();
		$(".form-question-reference input").val("");
		$(".form-question-examingpoint input").val("");
		$(".form-question-keyword input").val("");
		$(".form-question-analysis input").val("");
		// 如果不是性格测试，就清空
		if($("#question-type select").val() != 10)
		{
			$(".form-question-opt-item").val("");
		}
		
		
		$("body").scrollTop(0);
	},

	/**
	 * 检查输入合法性
	 */
	verifyInput : function verifyInput() {
		$(".form-message").empty();
		$(".has-error").removeClass("has-error");
		var question_type = $("#question-type select").val();
		if(question_type == 9 || question_type == 10)
		{
			return true; // 如果纯提干，不做任何检查;如果是10，性格测试，也尽量不要限制
		}
		
		var result = true;
		result = result && question_add.checkKnowledge();
		if (1 == question_type) {
			var r_checkContent = question_add.checkContent();
			var r_checkOpt = question_add.checkOpt();
			result = result && r_checkContent && r_checkOpt;
		} else if (2 == question_type) {
			var r_checkContent = question_add.checkContent();
			var r_checkOpt = question_add.checkOpt();
			var r_checkAnswerMuti = question_add.checkAnswerMuti();
			result = result && r_checkContent && r_checkOpt && r_checkAnswerMuti;
		} else if (3 == question_type) {
			var r_checkContent = question_add.checkContent();
			result = result && r_checkContent;
		} 	
		else {
			var r_checkContent = question_add.checkContent();
			var r_checkAnswerText = question_add.checkAnswerText();
			result = result && r_checkContent && r_checkAnswerText;
		}
		var r_checkAnalysis = question_add.checkAnalysis();
		var r_checkReference = question_add.checkReference();
		var r_checkExamingPoint = question_add.checkExamingPoint();
		var r_checkKeyword = question_add.checkKeyword();

		result = result && r_checkAnalysis && r_checkReference && r_checkExamingPoint && r_checkKeyword;

		return result;
	},

	checkKnowledge : function checkKnowledge() {
		var result = true;

		if ($("#point-to-select option").length == 0) {
			$(".question-knowledge .form-message").text("该试题至少对应一个知识点");
			$("#point-to-select").addClass("has-error");
			result = false;
		} else if ($("#point-to-select option").length > 4) {
			$(".question-knowledge .form-message").text("知识点数量不应该超过4个");
			$("#point-to-select").addClass("has-error");
			result = false;
		}

		return result;

	},

	/**
	 *检查试题内容
	 */
	checkContent : function checkContent() {
		var content = $(".question-content textarea").val();
		if (content == "") {
			$(".question-content .form-message").text("请输入试题内容");
			$(".question-content textarea").focus();
			$(".question-content textarea").addClass("has-error");
			return false;
		} else if (content.length > 5000) {
			$(".question-content .form-message").text("内容过长，请保持在5000个字符以内");
			$(".question-content textarea").focus();
			$(".question-content textarea").addClass("has-error");
			return false;
		} else {
			return true;
		}
	},

	/**
	 *检查选项内容
	 */
	checkOpt : function checkOpt() {
		var question_opt_items = $(".form-question-opt-item");
		var result = true;
		for (var i = 0; i < question_opt_items.length; i++) {
			var item_value = question_opt_items[i].value;
			if (item_value == "") {
				$(".form-question-opt .form-message").text("请输入选项内容");
				$(question_opt_items[i]).focus();
				$(question_opt_items[i]).addClass("has-error");
				result = true; // 只提示，还是允许通过
				break;
			} else if (item_value.length > 300) {
				$(".form-question-opt .form-message").text("选项内容请保持在300个字符以内");
				$(question_opt_items[i]).focus();
				$(question_opt_items[i]).addClass("has-error");
				result = false;
				break;
			}
		}
		return result;
	},

	/**
	 *检查多选题选项选择情况
	 */
	checkAnswerMuti : function checkAnswerMuti() {
		var muti_answer_opts = $(".form-question-answer-muti input[type=checkbox]");
		for (var i = 0; i < muti_answer_opts.length; i++) {
			if (muti_answer_opts[i].checked == true) {
				return true;
			}
		}
		var messagebox = $(".form-question-answer-muti .form-message");
		messagebox.text("请至少选择一个选项");
		messagebox.height(messagebox.height() + 1);
		messagebox.height(messagebox.height() - 1);
		return false;

	},

	/**
	 *检查参考答案选择情况
	 */
	checkAnswerText : function checkAnswerText() {
		var content = $(".form-question-answer-text textarea").val();
		if (content == "") {
			$(".form-question-answer-text .form-message").text("请输入参考答案");
			return false;
		} else if (content.length > 5000) {
			$(".form-question-answer-text .form-message").text("内容过长，请保持在5000个字符以内");
			return false;
		} else {
			return true;
		}
	},

	checkReference : function checkReference() {
		var content = $(".form-question-reference input").val();
		if (content.length > 50) {
			$(".form-question-reference input").focus();
			$(".form-question-reference input").addClass("has-error");
			$(".form-question-reference .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},
	
	checkExamingPoint : function checkExamingPoint() {
		var content = $(".form-question-examingpoint input").val();
		if (content.length > 50) {
			$(".form-question-examingpoint input").focus();
			$(".form-question-examingpoint input").addClass("has-error");
			$(".form-question-examingpoint .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},
	checkKeyword : function checkKeyword() {
		var content = $(".form-question-keyword input").val();
		if (content.length > 50) {
			$(".form-question-keyword input").focus();
			$(".form-question-keyword input").addClass("has-error");
			$(".form-question-keyword .form-message").text("内容过长，请保持在50个字符以内");
			return false;
		} else
			return true;
	},

	checkAnalysis : function checkAnalysis() {
		var content = $(".form-question-analysis textarea").val();
		if (content.length > 5000) {
			$(".form-question-analysis textarea").focus();
			$(".form-question-analysis textarea").addClass("has-error");
			$(".form-question-analysis .form-message").text("内容过长，请保持在5000个字符以内");
			return false;
		} else
			return true;
	},

	/**
	 *添加一个选项
	 */

	bindAddOpt : function bindAddOpt() {

		$("#ques-add-opt").click(function() {
			var optlength = $(".form-question-opt .add-opt-item").length;
			if (optlength > 5) {
				$(".form-question-opt .form-message").text("选项不能超过6个");
				return false;
			}
			var text = "<span class=\"add-opt-item\"><label class=\"que-opt-no\">" + String.fromCharCode(65 + optlength) + "</label><input type=\"text\" class=\"df-input-narrow form-question-opt-item\"/> <span class=\"add-img add-opt-img\">添加图片</span> <span><i class=\"small-icon ques-remove-opt fa fa-minus-square\" title=\"删除此选项\"></i></span> </span>";
			$(".add-opt-items").append(text);
			question_add.copyToAnswer();
		});
	},

	/**
	 *删除一个选项
	 */
	bindRemoveOpt : function bindRemoveOpt() {
		$(".form-question-opt").on("click", ".ques-remove-opt", function() {
			$(this).parent().parent().remove();
			question_add.rearrange();
			question_add.copyToAnswer();

		});

	},

	/**
	 *选项重新排序
	 */
	rearrange : function rearrange() {
		var opts = $(".form-question-opt .que-opt-no");
		opts.each(function(index) {
			$(this).text(String.fromCharCode(65 + index));
		});
	},

	/**
	 *
	 */
	bindAddPoint : function bindAddPoint() {
		$("#add-point-btn").click(function() {
			var field = $("#field-select > option:selected");
			var point = $("#point-from-select > option:selected");
			if (field.length == 0 || point.length == 0) {
				util.error("请选择需要添加的知识点");
				return false;
			}

			var html = "<option value=\"" + point.attr("value") + "\">" + field.text() + " > " + point.text() + "</option>";
			var p = point.attr("value");
			if (!question_add.checkPointDuplicate(p)) {
				util.error("不能重复添加");
				return false;
			}

			$("#point-to-select").append(html);
			return false;
		});

		$("#del-point-btn").click(function() {
			$("#point-to-select > option:selected").remove();
			return false;
		});

		$("#remove-all-point-btn").click(function() {
			$("#point-to-select").empty();
			return false;
		});
	},

	checkPointDuplicate : function checkPointDuplicate(p) {
		var points = $("#point-to-select option");
		for (var i = 0; i < points.length; i++) {
			var point = $(points[i]).attr("value");
			if (point == p)
				return false;
		}

		return true;
	},

	copyToAnswer : function copyToAnswer() {
		var questionType = $("#question-type select");
		var optlength = $(".form-question-opt-item").length;
		if (1 == questionType.val()) {
			$(".form-question-answer1 select").empty();
			for (var i = 0; i < optlength; i++) {
				$(".form-question-answer1 select").append("<option value=\"" + String.fromCharCode(65 + i) + "\">" + String.fromCharCode(65 + i) + "</option>");
			}

		} else if (2 == questionType.val()) {
			$(".form-question-answer-muti .muti-opt-item").remove();
			for (var i = 0; i < optlength; i++) {
				$(".form-question-answer-muti .form-message").before("<span class=\"muti-opt-item\"><input type=\"checkbox\" value=\"" + String.fromCharCode(65 + i) + "\"/><label class=\"que-opt-no\">" + String.fromCharCode(65 + i) + "</label><br /></span>");
			}
		}
	},

	composeEntity : function composeEntity() {
		var question_entity = new Object();
		//var data = new Object();
		
		question_entity.name = $(".question-content textarea").val().substring(0, 50);
		question_entity.question_type_id = $(".question-type select").val();

		var pointList = new Array();
		var pointOpts = $("#point-to-select option");
		for (var i = 0; i < pointOpts.length; i++) {
			
			pointList.push($(pointOpts[i]).attr("value")); //知识点部分
		}

		question_entity.pointList = pointList;

		// 添加代码获取tags jie 20170905
		var tagList = new Array();

		$(".q-tag-label-list .q-label-item").each(function() {
			var tag = new Object();
			tag.tagId = $(this).data("id"); 
			// questionId可以暂时不用获取，注释掉,传到后台统一添加到表里面即可
			// tag.questionId = $("#add-update-questionid").text(); 
			tagList.push(tag);
		});
		
		// data.tags = tags;
		question_entity.tagList = tagList;
		
		// 添加代码获取性格特征 jie 20170926
		var charactorTypeList = new Array();

		$(".q-charactorType-label-list .q-label-item").each(function() {
			var charactorType = new Object();
			// charactorTypeId
			charactorType.charactorTypeId = $(this).data("id"); 
			// questionId可以暂时不用获取，注释掉,传到后台统一添加到表里面即可
			// tag.questionId = $("#add-update-questionid").text(); 
			charactorTypeList.push(charactorType);
		});
		
		// data.charactorTypeList = charactorTypeList;
		question_entity.charactorTypeList = charactorTypeList;
		
		if (1 == question_entity.question_type_id) {
			question_entity.answer = $(".form-question-answer1 select").val();
		} else if (2 == question_entity.question_type_id) {
			var checkboxs = $(".form-question-answer-muti input:checked");
			var tmp_v = "";
			for (var i = 0; i < checkboxs.length; i++) {
				tmp_v = tmp_v + checkboxs[i].value;
			}
			question_entity.answer = tmp_v;

		} else if (3 == question_entity.question_type_id) {
			question_entity.answer = $(".form-question-answer-boolean select").val();
		} else if(10 == question_entity.question_type_id) // 性格测试没有标准答案,用四个NNNN代替
			{
				question_entity.answer = 'NNNN';
			}
		else {
			question_entity.answer = $(".form-question-answer-text textarea").val();
		}
		
		question_entity.questionContent = question_add.composeContent();

		question_entity.analysis = $(".form-question-analysis textarea").val();
		question_entity.referenceName = $(".form-question-reference input").val();
		question_entity.examingPoint = $(".form-question-examingpoint input").val();
		question_entity.keyword = $(".form-question-keyword input").val();
		question_entity.parentId=$("#question-parentId-select").val(); // 获取parentId
		//data.question = question_entity;
		
		return question_entity;
	},

	// 获取选项部分
	composeContent : function composeContent() {
		
		var question_type_id = $(".question-type select").val();
		var content = new Object();
		var content_img = $(".display-content-img");
		var content_img_string = content_img.data("url");
		content.title = $(".question-content textarea").val();
		var choiceMap = {};
		var imageMap = {};
		var pointList = new Array();
		
		$("point-to-select option").each(function(){
			pointList.push($(this).val());
		});
		if (content_img.length > 0) {
			content.titleImg = content_img_string;
		}
		if (1 == question_type_id || 10 == question_type_id) {
			var add_opt_items = $(".add-opt-item");

			for (var i = 0; i < add_opt_items.length; i++) {
				var add_opt_item = $(add_opt_items[i]);
				//选项标签
				var opt_img = add_opt_item.find(".display-opt-img");
				if (opt_img.length > 0) {
					imageMap[add_opt_item.children(".que-opt-no").text()] = opt_img.data("url");
				}
				choiceMap[add_opt_item.children(".que-opt-no").text()] = add_opt_item.children("input").val();
			}
			
		} else if (2 == question_type_id) {
			var add_opt_items = $(".add-opt-item");

			for (var i = 0; i < add_opt_items.length; i++) {
				var add_opt_item = $(add_opt_items[i]);
				//选项标签
				var opt_img = add_opt_item.find(".display-opt-img");
				if (opt_img.length > 0) {
					imageMap[add_opt_item.children(".que-opt-no").text()] = opt_img.data("url");
				}
				choiceMap[add_opt_item.children(".que-opt-no").text()] = add_opt_item.children("input").val();
			}
		}
		content.choiceImgList = imageMap;
		content.choiceList = choiceMap;
		
		return content;
	},
	
	getParentIDs: function getParentIDs()
	{
		$("#searchParentIds").click(function() {

				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : "GET",
					url : "secure/question/getParentIDs",
					success : function(message, tst, jqXHR) {
						// function里面的参数，第一个是返回的数据，第二个个是返回的状态
					    
						if (!util.checkSessionOut(jqXHR))
							return false;
						if (message.result == "success") {
							//alert(message.messageInfo);
							var parentIdandDescArray = message.object;
							var optionstring = "";
							for(var i=0; i<parentIdandDescArray.length; i++)
							{
							   optionstring +="<option value=\"" + parentIdandDescArray[i].parentId + "\" >" + parentIdandDescArray[i].titleDesc +"</option>";
						
							}
							//alert(optionstring);
							$("#question-parentId-select").html("<option value=''>请选择...</option>" + optionstring);
							
						} else {
							util.error("操作失败请稍后尝试");
						}

					},
					error : function(jqXHR, textStatus) {
						util.error("操作失败请稍后尝试");
					}
				});
	
			
	});
},

getTags: function getTags()
{
	$(".add-tag-btn").click(function() {
		var label_ids = $(".q-label-item");
		var flag = 0;
		label_ids.each(function() {
			if ($(this).data("id") == $("#tag-from-select").val())
				flag = 1;
		});
		if (flag == 0) {
			var selected = $("#tag-from-select").find("option:selected");

			$(".q-tag-label-list").append("<span class=\"label label-info q-label-item\" data-privatee=" + selected.data("privatee") + " data-creator=" + selected.data("creator") + " data-memo=" + selected.data("memo") + " data-id=" + $("#tag-from-select").val() + " data-createTime=" + selected.data("createTime") + ">" + $("#tag-from-select :selected").text() + "  <i class=\"fa fa-times\"></i>	</span>");
		} else {
			util.error("不能重复添加");
		}
	});


	$(".q-tag-label-list").on("click", ".fa", function() {
		$(this).parent().remove();
	});
},
getCharactorTypes: function getCharactorTypes()
{
	$(".add-charactorType-btn").click(function() {
		var label_ids = $(".q-label-item");
		var flag = 0;
		label_ids.each(function() {
			if ($(this).data("id") == $("#charactortype-from-select").val())
				flag = 1;
		});
		if (flag == 0) {
			var selected = $("#charactortype-from-select").find("option:selected");

			$(".q-charactorType-label-list").append("<span class=\"label label-info q-label-item\"  data-id=" + $("#charactortype-from-select").val() + ">" + $("#charactortype-from-select :selected").text() + "  <i class=\"fa fa-times\"></i>	</span>");
		} else {
			util.error("不能重复添加");
		}
	});


	$(".q-charactorType-label-list").on("click", ".fa", function() {
		$(this).parent().remove();
	});
}
};

