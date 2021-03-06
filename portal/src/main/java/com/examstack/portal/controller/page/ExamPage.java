package com.examstack.portal.controller.page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.exam.Exam;
import com.examstack.common.domain.exam.ExamHistory;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.exam.UserQuestionHistory;
import com.examstack.common.domain.personality.PersonalityCharactor;
import com.examstack.common.domain.personality.PersonalityScore;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.question.QuestionType;
import com.examstack.common.util.ExamPaperAdapter;
import com.examstack.common.util.Page;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.ExamPaperService;
import com.examstack.portal.service.ExamService;
import com.examstack.portal.service.QuestionHistoryService;
import com.examstack.portal.service.QuestionService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.sf.ehcache.search.expression.And;

@Controller
public class ExamPage {

	@Autowired
	private ExamService examService;
	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private QuestionHistoryService questionHistoryService;
	@Autowired
	private QuestionService questionService;

	@RequestMapping(value = "/exam-list", method = RequestMethod.GET)
	public String examListPage(Model model, HttpServletRequest request) {

		
		int userId = 0;
		if (SecurityContextHolder.getContext().getAuthentication() != null && !SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().endsWith("anonymousUser")){
			
			UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userId = userInfo.getUserid();
		}
		
		Page<Exam> page = new Page<Exam>();
		page.setPageSize(50);
		page.setPageNo(1);
		
		List<Exam> examListToStart = examService.getExamListToStart(userId, null, 1, 2);
	
		//model.addAttribute("examListToApply", examListToApply);
		model.addAttribute("examListToStart", examListToStart);

		model.addAttribute("userId", userId);
		return "exam";
	}

	/**
	 * 开始考试（公有考试和私有考试）
	 * 
	 * @param model
	 * @param request
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/student/exam-start/{examId}/{bfromhistory}", method = RequestMethod.GET)
	public String examStartPage(Model model, HttpServletRequest request, @PathVariable("examId") int examId, @PathVariable("bfromhistory") Integer bfromhistory) {

		//TO-DO:学员开始考试时，将开始时间传到消息队列，用户更新用户开始考试的时间。如果数据库中时间不为空，则不更新
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";

		int duration = 0;
		Exam exam = examService.getExamById(examId);

		if (exam == null || exam.getApproved() != 1 || exam.getExpTime().before(new Date()) || exam.getExamType() == 3) {
			model.addAttribute("errorMsg", "考试未审核或当前时间不能考试或考试类型错误");
			return "error";
		}

		ExamHistory examHistory = examService
				.getUserExamHistByUserIdAndExamId(userInfo.getUserid(), examId, 0, 1, 2, 3);
		Date startTime;
		
		/* 预留改动给将来需要暂停考试，保存之前的做题答案 */
		if((bfromhistory == 1) && (examHistory != null))
		{
			startTime = examHistory.getStartTime() == null ? new Date() : examHistory.getStartTime();
		}
		else
		{
			startTime = new Date();
		}

		ExamPaper examPaper = examPaperService.getExamPaperById(exam.getExamPaperId());
		
		duration = examPaper.getDuration();

		String examString = (new ExamPaperAdapter(examPaper)).getPaper2String4ExamTest(strUrl);
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HHmmss");
		
		model.addAttribute("startTime", startTime);
		model.addAttribute("paperParts", examPaper.getPaperParts());
		if(examHistory != null && examHistory.getHistId() > 0) // 有历史记录
		{
			model.addAttribute("examHistoryId", examHistory.getHistId());
		}else {
			model.addAttribute("examHistoryId", "");
		}
		
		model.addAttribute("examId", examId);
		model.addAttribute("examPaperId", exam.getExamPaperId());
		model.addAttribute("duration", duration * 60);
		model.addAttribute("htmlStr", examString);
		model.addAttribute("examPaper", examPaper);

		userInfo.setHistId(0);
		return "examing";
	}
	
	
	/**
	 * 开始性格测试，分不同的学术类别
	 * @param model
	 * @param request
	 * @param examId
	 * @param xuepaiId
	 * @return
	 */
	@RequestMapping(value = "/student/personalitytest-start/{examId}/{xuepaiId}", method = RequestMethod.GET)
	public String personalitytestStartPage(Model model, HttpServletRequest request, @PathVariable("examId") int examId, @PathVariable("xuepaiId") int xuepaiId) {

		//TO-DO:学员开始考试时，将开始时间传到消息队列，用户更新用户开始考试的时间。如果数据库中时间不为空，则不更新
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";

		int duration = 60; // 设定时间是60分钟

		List<QuestionQueryResult> questionList = examService.getPersonalityTestQuestionQueryResultByXuepaiId(xuepaiId);

		StringBuilder sb = new StringBuilder();
		for (QuestionQueryResult question : questionList) {
			QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
			sb.append(adapter.getUserExamPaper());
		}
		model.addAttribute("duration", duration * 60);
		model.addAttribute("htmlStr", sb.toString());

		userInfo.setHistId(0);
		
		return "examing-personalitytest";
	}

	@RequestMapping(value = "/student/getPersonalityTestReport", method = RequestMethod.POST)
	public String getPersonalityTestReport(Model model, HttpServletRequest request,@RequestParam String PersonalityScoreListStr) 
	{
		//	List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>() {
	//}.getType());
		Gson gson = new Gson();
		List<PersonalityScore> PersonalityScoreList = gson.fromJson(PersonalityScoreListStr, new TypeToken<List<PersonalityScore>>(){}.getType());
		int totalScore = 0;
		for(PersonalityScore item : PersonalityScoreList)
		{
			totalScore += item.getDanxiangScore();
		}
	
		model.addAttribute("resultCharactorName",PersonalityScoreList.get(0).getName());
		model.addAttribute("totalScore",totalScore);
		model.addAttribute("PersonalityScoreList",PersonalityScoreList);
		model.addAttribute("resultCharactorSummary",PersonalityScoreList.get(0).getSummary());
		return "PersonalityTestReport";
	}
	/**
	 * 模拟考试
	 * 
	 * @param model
	 * @param request
	 * @param examId
	 * @return
	 */
	@RequestMapping(value = "/student/model-test-start/{examId}", method = RequestMethod.GET)
	public String modelTestStartPage(Model model, HttpServletRequest request, @PathVariable("examId") int examId) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";

		int duration = 0;
		Exam exam = examService.getExamById(examId);

		if (exam.getApproved() != 1 || exam.getExpTime().before(new Date()) || exam.getExamType() != 3) {
			model.addAttribute("errorMsg", "考试未审核或当前时间不能考试或考试类型错误");
			return "error";
		}

		ExamPaper examPaper = examPaperService.getExamPaperById(exam.getExamPaperId());
		
		ExamHistory examHistory = examService
				.getUserExamHistByUserIdAndExamId(userInfo.getUserid(), examId, 0, 1, 2, 3);
		int historyId = 0;
		
		if (examHistory == null) {
			//练习默认审核通过
			historyId = examService.addUserExamHist(userInfo.getUserid(), examId, examPaper.getId(),1);
		}else{
			historyId = examHistory.getHistId();
		}

		String content = examPaper.getContent();

		Gson gson = new Gson();
		duration = examPaper.getDuration();

		List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>() {
		}.getType());

		StringBuilder sb = new StringBuilder();
		for (QuestionQueryResult question : questionList) {
			QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
			sb.append(adapter.getUserExamPaper());
		}

		model.addAttribute("examHistoryId", historyId);
		model.addAttribute("examId", examId);
		model.addAttribute("examPaperId", examPaper.getId());
		model.addAttribute("duration", duration * 60);
		model.addAttribute("htmlStr", sb.toString());
		userInfo.setHistId(0);
		return "examing";
	}
	
	/**
	 * 学员试卷
	 * @param model
	 * @param request
	 * @param examhistoryId
	 * @return
	 */
	@RequestMapping(value = "/student/student-answer-sheet/{examId}", method = RequestMethod.GET)
	private String studentAnswerSheetPage(Model model, HttpServletRequest request, @PathVariable int examId) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ExamHistory history = examService.getUserExamHistByUserIdAndExamId(userInfo.getUserid(), examId, 2, 3);
		int examPaperId = history.getExamPaperId();
		
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
		StringBuilder sb = new StringBuilder();
		if(examPaper.getContent() != null && !examPaper.getContent().equals("")){
			Gson gson = new Gson();
			String content = examPaper.getContent();
			List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());
			
			for(QuestionQueryResult question : questionList){
				QuestionAdapter adapter = new QuestionAdapter(question,strUrl);
				sb.append(adapter.getStringFromXML());
			}
		}
		
		model.addAttribute("htmlStr", sb);
		model.addAttribute("exampaperid", examPaperId);
		model.addAttribute("examHistoryId", history.getHistId());
		model.addAttribute("exampapername", examPaper.getName());
		model.addAttribute("examId", history.getExamId());
		return "student-answer-sheet";
	}
	
	@RequestMapping(value = "student/finish-exam/{examId}", method = RequestMethod.GET)
	public String examFinishedPage(Model model,@PathVariable("examId") int examId) {
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		ExamHistory history = examService.getUserExamHistByUserIdAndExamId(userInfo.getUserid(), examId, 2, 3);
		Gson gson = new Gson();
		List<QuestionQueryResult> questionList = gson.fromJson(history.getContent(), new TypeToken<List<QuestionQueryResult>>(){}.getType());
		
		List<Integer> idList = new ArrayList<Integer>();
		for (QuestionQueryResult q : questionList) {
			idList.add(q.getQuestionId());
		}
		
		AnswerSheet as = gson.fromJson(history.getAnswerSheet(), AnswerSheet.class);
		
		HashMap<Integer, AnswerSheetItem> hm = new HashMap<Integer,AnswerSheetItem>();
		for(AnswerSheetItem item : as.getAnswerSheetItems()){
			hm.put(item.getQuestionId(), item);
		}

		int total = questionList.size();
		int wrong = 0;
		int right = 0;

		HashMap<Integer, QuestionStatistic> reportResultMap = new HashMap<Integer, QuestionStatistic>();
		List<QuestionQueryResult> questionQueryList = questionService.getQuestionAnalysisListByIdList(idList);
		Map<Integer,KnowledgePoint> pointMap = questionService.getKnowledgePointByFieldId(null);
		HashMap<Integer, Boolean> answer = new HashMap<Integer, Boolean>();
		
		for(QuestionQueryResult result : questionQueryList){
			QuestionStatistic statistic = reportResultMap.get(result.getKnowledgePointId());
			if(statistic == null)
				statistic = new QuestionStatistic();
			statistic.setPointId(result.getKnowledgePointId());
			statistic.setPointName(pointMap.get(result.getKnowledgePointId()).getPointName());
			statistic.setAmount(statistic.getAmount() + 1);
			if(hm.get(result.getQuestionId()).isRight()){
				statistic.setRightAmount(statistic.getRightAmount() + 1);
				right ++;
				answer.put(result.getQuestionId(), true);
			}else{
				statistic.setWrongAmount(statistic.getWrongAmount() + 1);
				wrong ++;
				answer.put(result.getQuestionId(), false);
			}
			total ++;
			reportResultMap.put(result.getKnowledgePointId(), statistic);
		}

		model.addAttribute("total", total);
		model.addAttribute("wrong", wrong);
		model.addAttribute("right", right);
		model.addAttribute("reportResultMap", reportResultMap);
		model.addAttribute("create_time", history.getCreateTime());
		model.addAttribute("answer", answer);
		model.addAttribute("idList", idList);
		return "exam-finished";
	}
	
	@RequestMapping(value = "student/finished-submit", method = RequestMethod.GET)
	public String finishedSubmitPage(Model model){
		return "finished-submit";
	}
}
