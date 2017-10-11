package com.examstack.portal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.PaperPart;
import com.examstack.common.domain.question.Question;
import com.examstack.portal.persistence.ExamPaperMapper;
import com.examstack.portal.persistence.QuestionMapper;

@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperService {

	@Autowired
	private ExamPaperMapper examPaperMapper;
	
	@Autowired
	private QuestionMapper questionMapper;
	@Override
	public ExamPaper getExamPaperById(int examPaperId) {
		ExamPaper ep = new ExamPaper();
		ep = examPaperMapper.getExamPaperById(examPaperId);
		// 获取ep的parts getPaperPartsByPaperId
		List<PaperPart> ppList= getPaperPartsByPaperId(examPaperId);
		ep.setPaperParts(ppList);
		return ep;
		
	}

	private List<PaperPart> getPaperPartsByPaperId(int examPaperId) {

				List<PaperPart> parts = examPaperMapper.getPaperPartsByPaperId(examPaperId);
				// 获取questions
				for(PaperPart pp : parts)
				{
					List<Integer> questionIds = examPaperMapper.getQuestionIdListByPaperPartId(pp.getId());
					// 根据ID，获取question
					List<Question> questions = null;
					if(questionIds != null && questionIds.size() > 0)
					{
						questions = questionMapper.getQuestionListByIdList(questionIds);
					}
					
					pp.setQuestions(questions);
				}
				return parts;
			}
	

}
