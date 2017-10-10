package com.examstack.common.util;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.examstack.common.domain.exam.PaperPart;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionQueryResult;

public class PaperPartAdapter {

	private PaperPart paperPart;


	
	public PaperPart getPaperPart() {
		return paperPart;
	}

	public void setPaperPart(PaperPart paperPart) {
		this.paperPart = paperPart;
	}
	public PaperPartAdapter(PaperPart paperPart)
	{
		this.paperPart = paperPart;
	}
	
	public PaperPartAdapter(){}
	
	public String getPaperPartAndQuestionsAdhere(String strUrl)
	{
		StringBuilder sb = new StringBuilder();
		
		List<Question> questions = paperPart.getQuestions();
		
		// 先把此部分的title放进去
		sb.append("<div id=\"part_" + paperPart.getId() + "\" class=\"part\">");
			sb.append("<div class=\"partTitle\">");
			sb.append(paperPart.getName());
			sb.append("<span class=\"partSummary\">"+paperPart.getSummary() + "每题"+paperPart.getPointPerQuestion()  + "分</span>");
			sb.append("<span class=\"mkfs_rgt\">" + "</span>");
			sb.append("</div>");
		sb.append("<div class=\"questions4part\">");
		// 下面开始铺试题
		if(paperPart.getQuestions() != null && paperPart.getQuestions().size() >0)
		{
			for(Question question : paperPart.getQuestions())
			{
				QuestionQueryResult qqr = new QuestionQueryResult(question);
				QuestionAdapter adapter = new QuestionAdapter(qqr,strUrl);
				sb.append(adapter.getStringFromXML());
			}
			
			
		}
		// 把结尾铺上
		
		sb.append("</div></div>");
		
		return sb.toString();
	}
}
