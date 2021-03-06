package com.examstack.portal.service;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.personality.PersonalityQuestionItem;
import com.examstack.common.domain.personality.PersonalityScore;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.util.Page;
import com.examstack.common.util.StringUtil;
import com.examstack.portal.persistence.ExamMapper;
import com.examstack.portal.persistence.ExamPaperMapper;
import com.examstack.portal.persistence.QuestionMapper;
import com.examstack.portal.service.ExamService;

@Service("examService")
public class ExamServiceImpl implements ExamService {

	@Autowired
	private ExamMapper examMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Override
	public ExamHistory getUserExamHistBySeriNo(String seriNo,int approved) {
		
		return examMapper.getUserExamHistBySeriNo(seriNo,approved);
	}
	@Override
	public Exam getExamById(int examId) {
		
		return examMapper.getExamById(examId);
	}
	@Override
	public ExamHistory getUserExamHistByUserIdAndExamId(int userId, int examId, int ... approved) {
		if(approved != null && approved.length == 0)
			approved = null;
		return examMapper.getUserExamHistByUserIdAndExamId(userId, examId, approved);
	}
	@Override
	public int addUserExamHist(int userId,int examId,int examPaperId,int approved) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		try {
			ExamPaper examPaper = examPaperMapper.getExamPaperById(examPaperId);
			ExamHistory history = new ExamHistory();
			history.setExamId(examId);
			history.setExamPaperId(examPaperId);
			history.setContent(examPaper.getContent());
			history.setDuration(examPaper.getDuration());
			
			history.setApproved(approved);
			Date now = new Date();
			String seriNo = sdf.format(now) + StringUtil.format(userId, 3) + StringUtil.format(examId, 3);
			history.setSeriNo(seriNo);
			
			history.setUserId(userId);
			examMapper.addUserExamHist(history);
			return history.getHistId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getCause());
		}
	}
	@Override
	public ExamHistory getUserExamHistListByHistId(int histId) {
		
		return examMapper.getUserExamHistListByHistId(histId);
	}
	@Override
	public List<Exam> getExamListToApply(int userId, Page<Exam> page) {
		
		return examMapper.getExamListToApply(userId, page);
	}
	@Override
	public List<Exam> getExamListToStart(int userId, Page<Exam> page, int ... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamListToStart(userId, typeIdList, page);
	}
	@Override
	public List<Exam> getExamList(Page<Exam> page, int... typeIdList) {
		
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getExamList(typeIdList,page);
	}
	@Override
	public List<ExamHistory> getUserExamHistByUserId(int userId, Page<ExamHistory> page, int... typeIdList) {
		// TODO Auto-generated method stub
		if(typeIdList != null && typeIdList.length == 0)
			typeIdList = null;
		return examMapper.getUserExamHistByUserId(userId, typeIdList, page);
	}
	@Override
	public List<QuestionQueryResult> getPersonalityTestQuestionQueryResultByXuepaiId(int xuepaiId) {

		return examMapper.getPersonalityTestQuestionQueryResultByXuepaiId(xuepaiId);
	}
	
	@Override
	public List<PersonalityScore> getPersonalityTestingResult(List<PersonalityQuestionItem> pQuestionScoreList, int xuepaiId)
	{
		// 获取这些问题Id对应的性格类别，计算得分
		// TODO 需要根据传进来的quesionId和answer，来计算其每道题的得分，并获取每道题的性格代码charactorID和对应的学派
	
		HashMap<Integer, String> questionIdAndScoreMap = new HashMap<Integer,String>(pQuestionScoreList.size());
		for(PersonalityQuestionItem it : pQuestionScoreList)
		{
			questionIdAndScoreMap.put(it.getQuestionId(), it.getAnswer());
		}
		List<PersonalityQuestionItem> pQLst =  examMapper.getPersonalityQuestionItems(questionIdAndScoreMap.keySet());
		
		// 以上返回的只有answer和score还没赋值，开始填充
		for(PersonalityQuestionItem pt : pQLst)
		{
			String answer = questionIdAndScoreMap.get(pt.getQuestionId());
			pt.setAnswer(answer);
			if(answer != null && answer.equalsIgnoreCase("A"))
			{
				pt.setScore(2);
			}else if(answer != null && answer.equalsIgnoreCase("B"))
			{
				pt.setScore(1);
			}else
			{
				pt.setScore(0);
			}
		}
		// 每一个都有了分数了，计算不同种类的总分
		List<PersonalityScore> pScoreLst = null;
		
        pScoreLst = questionMapper.getPersonalityLst(xuepaiId);
        int charactorId = 0;
        int danxiangScore = 0;
        for(PersonalityScore ps : pScoreLst)
        {
        	charactorId = ps.getId();
        	danxiangScore = 0;
        	// 一个个计算性格属性的分数
        	for(PersonalityQuestionItem pt : pQLst)
        	{
        		if(pt.getCharactorId() == charactorId)
        		{
        			danxiangScore += pt.getScore();
        		}
        	}
        	ps.setDanxiangScore(danxiangScore);
        }
		return pScoreLst;
	}
	@Override
	public float AddExamResult(List<AnswerSheetItem> aSheetItems) {
		// TODO Auto-generated method stub
		return 0;
	}
}
