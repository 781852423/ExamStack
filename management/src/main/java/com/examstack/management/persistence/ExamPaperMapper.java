package com.examstack.management.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.Paper2Part;
import com.examstack.common.domain.exam.PaperPart;
import com.examstack.common.domain.question.Question;
import com.examstack.common.util.Page;

public interface ExamPaperMapper {

	public List<ExamPaper> getExamPaperList(@Param("searchStr") String searchStr,@Param("paperType") String paperType, @Param("page") Page<ExamPaper> page);
	
	public void insertExamPaper(ExamPaper examPaper);
	
	public void insertPaperPart(PaperPart paperPart);
	
	public ExamPaper getExamPaperById(int examPaperId);
	
	public void updateExamPaper(ExamPaper examPaper);
	
	public void deleteExamPaper(int id);
	
	public List<ExamPaper> getEnabledExamPaperList(@Param("userName") String userName,@Param("page") Page<ExamPaper> page);

	public void insertPaper2PartRelation(Paper2Part p2p);

	public PaperPart getPaperPartById(int partId);

	public void updateExamPartQuestions(@Param("part") PaperPart pp);

	public List<PaperPart> getPaperPartsByPaperId(int exampaperId);

	public List<Integer> getQuestionIdListByPaperPartId(Integer paperPartId);

	public void deletePartQuestionsByPartId(Integer partId);
}
