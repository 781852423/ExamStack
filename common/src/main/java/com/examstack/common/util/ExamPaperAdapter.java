package com.examstack.common.util;

import java.util.List;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.PaperPart;

public class ExamPaperAdapter {

	private ExamPaper examPaper;

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}
	
	public ExamPaperAdapter(){}
	
	public ExamPaperAdapter(ExamPaper examPaper)
	{
		this.examPaper = examPaper;
	}
	
	/*
	 * 必须保证此时examPaper对象不为空
	 */
	public String getPaper2String(String strUrl)
	{
		StringBuilder sb = new StringBuilder();
		List<PaperPart> parts = this.examPaper.getPaperParts();
		
		if(parts != null && parts.size() > 0)
		{
			for(PaperPart pp : parts)
			{
				PaperPartAdapter ppa = new PaperPartAdapter(pp);
				sb.append(ppa.getPaperPartAndQuestionsAdhere(strUrl));
			}
		}
		
		
		return sb.toString();
	}
}
