package com.examstack.common.util;

// 2017-8-25修改，增加了case 为9的题目类型。该类型只有题干，没有答案，也没有问题，只是一个综合分析或者阅读理解的正文部分
import java.util.Iterator;

import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionContent;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.util.xml.Object2Xml;
import com.google.gson.Gson;



public class QuestionAdapter {

	private Question question;
	private QuestionContent questionContent;
	private QuestionContent questionParentContent;
	private AnswerSheetItem answerSheetItem;
	private QuestionQueryResult questionQueryResult;
	private String baseUrl;

	public String pointStrFormat(float point){
		
		if(point > (int)point){
			return point + "";
		}
		return (int)point + "";
	}
	/**
	 * 
	 * @param question
	 *            试题
	 * @param answerSheetItem
	 *            答题卡
	 * @param questionQueryResult
	 *            试题描述：正确于判断题，数据库里面存储的答案是”T“表示正确，”F“表示错误
	 */
	public QuestionAdapter(Question question, AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		
		this.question = question;
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		
		// 如果parentContent里面是空的，则gson.fromJson将返回为null
		this.questionParentContent = gson.fromJson(question.getParentContent(), QuestionContent.class);
		
		this.baseUrl = baseUrl;
	}
	
	public QuestionAdapter(AnswerSheetItem answerSheetItem,
			QuestionQueryResult questionQueryResult, String baseUrl) {
		this.answerSheetItem = answerSheetItem;
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		this.questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
		this.questionParentContent = gson.fromJson(questionQueryResult.getParentContent(), QuestionContent.class);
		
		this.baseUrl = baseUrl;
	}

	public QuestionAdapter(QuestionQueryResult questionQueryResult,
			String baseUrl) {
		this.questionQueryResult = questionQueryResult;
		Gson gson = new Gson();
		
		this.questionContent = gson.fromJson(questionQueryResult.getContent(), QuestionContent.class);
		this.questionParentContent = gson.fromJson(questionQueryResult.getParentContent(), QuestionContent.class);
		
		this.baseUrl = baseUrl;
	}

	public String getParentContentTitle()
	{
		if(this.questionParentContent != null)
		{
			return this.questionParentContent.getTitle();
		}else
		{
			return "";
		}
	}
	
	public String getParentContentTitleImage()
	{
		if(this.questionParentContent != null)
		{
			return this.questionParentContent.getTitleImg();
		}else
		{
			return "";
		}
	}
	
	
	/**
	 * 组卷专用
	 * 被practice调用
	 * @return
	 */
	public String getStringFromXML() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		case 8:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 9:
			sb.append("<li class=\"question qt-rawTitle\">");
			break;
		case 10:
			sb.append("<li class=\"question qt-singlechoice\">"); // 目前考虑性格测试也是单选题
			break;
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[计算题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 8:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[名词解释]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 10:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it10 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it10.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it10.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span class='answerResultDesc'></span></span><span>参考答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("正确").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错误").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else if(questionQueryResult.getQuestionTypeId() == 10)
		{
			sb.append("<span class=\"answer_value\">").append("性格测试，无标准答案，只是推测性格特征")
			.append("</span><br>");
		}
		else
		{
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
			.append("</span><br>");
		}
			
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}

	public String getReportStringFromXML(){
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		case 8:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append("<span class=\"question-li-text\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[计算题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 8:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[名词解释]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		sb.append("<span class='answerResultDesc'></span><span>参考答案：</span>");
		if (questionQueryResult.getQuestionTypeId() == 3) {
			if (questionQueryResult.getAnswer().equals("T"))
				sb.append("<span class=\"answer_value\">").append("正确").append("</span><br>");
			else if (questionQueryResult.getAnswer().equals("F"))
				sb.append("<span class=\"answer_value\">").append("错误").append("</span><br>");
			else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		} else
			sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
					.append("</span><br>");

		sb.append("<span>  你的解答：</span>");
		if (answerSheetItem.getQuestionTypeId() == 3) {
			if (answerSheetItem.getAnswer().trim().equals("T"))
				sb.append("<span>").append("正确").append("</span>");
			else if (answerSheetItem.getAnswer().trim().equals("F"))
				sb.append("<span>").append("错误").append("</span>");
			else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");
		} else
			sb.append("<span>").append(answerSheetItem.getAnswer())
					.append("</span>");
		sb.append("</div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-info\">");
		sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getReferenceName());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-warning\">");
		sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getAnalysis());
		sb.append("</p></div></div>");
		sb.append("<div class=\"answer-desc-detail\">");
		sb.append("<label class=\"label label-success\">");
		sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
		sb.append("</label>");
		sb.append("<div class=\"answer-desc-content\">");
		sb.append("<p>");
		sb.append(questionQueryResult.getPointName());
		sb.append("</p></div></div>");
		sb.append("</div>");

		sb.append("</li>");
		return sb.toString();
	}
	/**
	 * 
	 * @param showAnswer
	 *            是否显示正确的答案。如果有答题卡，会显示用户的答案
	 * @param showPoint
	 *            是否显示分数
	 * @param showAnalysis
	 *            是否显示分析和来源
	 * @return
	 */
	public String getStringFromXML(boolean showAnswer, boolean showPoint,
			boolean showAnalysis) {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		case 8:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 9: // 只是一个提干，类似阅读理解或者综合分析的正文部分
			sb.append("<li class=\"question qt-rawTitle\">");
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[计算题]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 8:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[名词解释]</span>");
			if (showPoint){
				sb.append("<span class=\"question-point-content\">");
				sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
				sb.append("</span>");
			}
				//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId()).append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		default:
			break;
		}
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		if (showAnswer) {

			sb.append("<span class='answerResultDesc'></span><span>参考答案：</span>");
			if (questionQueryResult.getQuestionTypeId() == 3) {
				if (questionQueryResult.getAnswer().equals("T"))
					sb.append("<span class=\"answer_value\">").append("正确").append("</span><br>");
				else if (questionQueryResult.getAnswer().equals("F"))
					sb.append("<span class=\"answer_value\">").append("错误").append("</span><br>");
				else
					sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
							.append("</span><br>");
			} else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		}

		if (answerSheetItem != null) {

			sb.append("<span>  你的解答：</span>");
			if (answerSheetItem.getQuestionTypeId() == 3) {
				if (answerSheetItem.getAnswer().trim().equals("T"))
					sb.append("<span>").append("正确").append("</span>");
				else if (answerSheetItem.getAnswer().trim().equals("F"))
					sb.append("<span>").append("错误").append("</span>");
				else
					sb.append("<span>").append(answerSheetItem.getAnswer())
							.append("</span>");
			} else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");

		}
		sb.append("</div>");
		if (showAnalysis) {
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-info\">");
			sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getReferenceName());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-warning\">");
			sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getAnalysis());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-success\">");
			sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getPointName());
			sb.append("</p></div></div>");
			sb.append("</div>");
		}

		sb.append("</li>");
		return sb.toString();
	}

	/*
	 * 学生选择模拟考试，系统提供的针对试卷试题的adapter
	 */
	public String getUserExamPaper() {
		StringBuilder sb = new StringBuilder();

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("<li class=\"question qt-singlechoice\">");
			break;
		case 2:
			sb.append("<li class=\"question qt-multiplechoice\">");
			break;
		case 3:
			sb.append("<li class=\"question qt-trueorfalse\">");
			break;
		case 4:
			sb.append("<li class=\"question qt-fillblank\">");
			break;
		case 5:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 6:
			sb.append("<li class=\"question qt-essay\">");
			break;
		case 7:
			sb.append("<li class=\"question qt-analytical\">");
			break;
		case 8:
			sb.append("<li class=\"question qt-shortanswer\">");
			break;
		case 9:
			sb.append("<li class=\"question qt-rawTitle\">");
			break;	
		default:
			break;
		}

		sb.append("<div class=\"question-title\">");
		sb.append("<div class=\"question-title-icon\"></div>");
		sb.append("<span class=\"question-no\">").append("</span>");
		sb.append("<span class=\"question-type\" style=\"display: none;\">");

		switch (questionQueryResult.getQuestionTypeId()) {
		case 1:
			sb.append("singlechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[单选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it1 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it1.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it1.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"radio\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 2:
			sb.append("multiplechoice").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[多选题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			Iterator<String> it2 = questionContent.getChoiceList().keySet()
					.iterator();
			sb.append("<ul class=\"question-opt-list\">");
			while (it2.hasNext()) {
				sb.append("<li class=\"question-list-item\">");
				String key = it2.next();
				String value = questionContent.getChoiceList().get(key);
				sb.append("<input type=\"checkbox\" value=\"")
						.append(key)
						.append("\" name=\"question-radio1\" class=\"question-input\">");
				sb.append(key).append(": ").append(value);
				if (questionContent.getChoiceImgList() != null)
					if (questionContent.getChoiceImgList().containsKey(key))
						sb.append(
								"<img class=\"question-opt-img question-img\" src=\"")
								.append(baseUrl)
								.append(questionContent.getChoiceImgList().get(
										key)).append("\" />");
				sb.append("</span>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 3:
			sb.append("trueorfalse").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[判断题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<ul class=\"question-opt-list\">");
			
			sb.append("<li class=\"question-list-item\">").append(
					"<input type=\"radio\" value=\"T\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">正确</span>").append("</li>");
			
			sb.append("<li class=\"question-list-item\">").append("<input type=\"radio\" value=\"F\" name=\"question-radio2\" class=\"question-input\">")
					.append("<span class=\"question-li-text\">错误</span>").append("</li>");
			
			sb.append("</ul>");
			sb.append("</form>");
			break;
		case 4:
			sb.append("fillblank").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[填空题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 5:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 6:
			sb.append("essay").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[论述题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 7:
			sb.append("analytical").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[分析题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		case 8:
			sb.append("shortanswer").append("</span>");
			sb.append("<span class=\"knowledge-point-id\" style=\"display: none;\">").append(questionQueryResult.getKnowledgePointId()).append("</span>");
			sb.append("<span class=\"question-type-id\" style=\"display: none;\">").append(questionQueryResult.getQuestionTypeId()).append("</span>");
			sb.append("<span>[简答题]</span>");
			sb.append("<span class=\"question-point-content\">");
			sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("</span>");
			//sb.append("<span>(</span><span class=\"question-point\">").append(pointStrFormat(questionQueryResult.getQuestionPoint())).append("</span><span>分)</span>");
			sb.append("<span class=\"question-id\" style=\"display:none;\">")
					.append(questionQueryResult.getQuestionId())
					.append("</span>");
			sb.append("</div>");
			sb.append("<form class=\"question-body\">");
			setQuestionBody(sb);
			sb.append("<textarea class=\"question-textarea\"></textarea>");
			sb.append("</form>");
			break;
		
		default:
			break;

		}
		// --jie add below lines to add answer sheet on 20170925
		
		sb.append("<div class=\"answer-desc\">");
		sb.append("<div class=\"answer-desc-summary\">");
		// 显示答案
			sb.append("<span class='answerResultDesc'></span><span>参考答案：</span>");
			if (questionQueryResult.getQuestionTypeId() == 3) {
				if (questionQueryResult.getAnswer().equals("T"))
					sb.append("<span class=\"answer_value\">").append("正确").append("</span><br>");
				else if (questionQueryResult.getAnswer().equals("F"))
					sb.append("<span class=\"answer_value\">").append("错误").append("</span><br>");
				else
					sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
							.append("</span><br>");
			} else
				sb.append("<span class=\"answer_value\">").append(questionQueryResult.getAnswer())
						.append("</span><br>");
		

		if (answerSheetItem != null) {

			sb.append("<span>  你的解答：</span>");
			if (answerSheetItem.getQuestionTypeId() == 3) {
				if (answerSheetItem.getAnswer().trim().equals("T"))
					sb.append("<span>").append("正确").append("</span>");
				else if (answerSheetItem.getAnswer().trim().equals("F"))
					sb.append("<span>").append("错误").append("</span>");
				else
					sb.append("<span>").append(answerSheetItem.getAnswer())
							.append("</span>");
			} else
				sb.append("<span>").append(answerSheetItem.getAnswer())
						.append("</span>");

		}
		sb.append("</div>");
		
		// 显示答题分析
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-info\">");
			sb.append("<i class=\"fa fa-paw\"></i><span> 来源</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getReferenceName());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-warning\">");
			sb.append("<i class=\"fa fa-flag\"></i><span> 解析</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getAnalysis());
			sb.append("</p></div></div>");
			sb.append("<div class=\"answer-desc-detail\">");
			sb.append("<label class=\"label label-success\">");
			sb.append("<i class=\"fa fa-bookmark\"></i><span> 考点</span>");
			sb.append("</label>");
			sb.append("<div class=\"answer-desc-content\">");
			sb.append("<p>");
			sb.append(questionQueryResult.getPointName());
			sb.append("</p></div></div>");
			sb.append("</div>");
		

		sb.append("</li>");
		return sb.toString();
	}
	
	private void setQuestionBody(StringBuilder sb)
	{
		// 先把parent题干的内容展示
					sb.append("<p class=\"question-body-text\">").append(this.getParentContentTitle());
					if ((this.questionParentContent != null) && (this.questionParentContent.getTitleImg() != null))
					{
						if (!this.questionParentContent.getTitleImg().trim().equals(""))
							{
							sb.append(
									"<img class=\"question-content-img question-img\" src=\"")
									.append(baseUrl)
									.append(this.questionParentContent.getTitleImg())
									.append("\" />");
							}
					}
					// 再展示question自己的title和image
					sb.append("<br>"+questionContent.getTitle());
					if (questionContent.getTitleImg() != null)
					{
						if (!questionContent.getTitleImg().trim().equals(""))
							{
							   sb.append(
							
									"<img class=\"question-content-img question-img\" src=\"")
									.append(baseUrl)
									.append(questionContent.getTitleImg())
									.append("\" />");
							}
					}
					sb.append("</p>");
	}
}
