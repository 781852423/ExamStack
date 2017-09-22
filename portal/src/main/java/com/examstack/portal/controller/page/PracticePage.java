package com.examstack.portal.controller.page;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.UserQuestionHistory;
import com.examstack.common.domain.practice.KnowledgePointAnalysisResult;
import com.examstack.common.domain.practice.TypeAnalysis;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.question.QuestionType;
import com.examstack.common.domain.user.Group;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.QuestionHistoryService;
import com.examstack.portal.service.QuestionService;
import com.examstack.portal.service.UserService;

@Controller
public class PracticePage {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionHistoryService questionHistoryService;
	@Autowired
	private UserService userService;
	/**
	 * 强化练习
	 * @param model
	 * @param request
	 * @param knowledgePointId
	 * @param questionTypeId
	 * @return
	 */
	@RequestMapping(value = "/student/practice-improve/{fieldId}/{knowledgePointId}/{questionTypeId}", method = RequestMethod.GET)
	public String practiceImprove(Model model, HttpServletRequest request,
			@PathVariable("fieldId") int fieldId,
			@PathVariable("knowledgePointId") int knowledgePointId,
			@PathVariable("questionTypeId") int questionTypeId) {

		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		List<QuestionQueryResult> qqrList = questionService
				.getQuestionAnalysisListByPointIdAndTypeId(questionTypeId,
						knowledgePointId);
		String questionTypeName = "";
		String fieldName = "";
		try{
			fieldName = qqrList.get(0).getPointName().split(">")[1];
		}catch(Exception e){
			//log.info(e.getMessage());
		}
		
		Map<Integer,QuestionType> questionTypeMap = questionService.getQuestionTypeMap();
		for(Map.Entry<Integer,QuestionType> entry : questionTypeMap.entrySet()){
			
			if(entry.getKey() == questionTypeId){
				questionTypeName = entry.getValue().getName();
				break;
			}	
		}
		int amount = qqrList.size();
		StringBuilder sb = new StringBuilder();
		for(QuestionQueryResult qqr : qqrList){
			QuestionAdapter adapter = new QuestionAdapter(qqr,strUrl);
			sb.append(adapter.getStringFromXML());
		}
		
		model.addAttribute("questionStr", sb.toString());
		model.addAttribute("amount", amount);
		model.addAttribute("fieldName", fieldName);
		model.addAttribute("questionTypeName", questionTypeName);
		model.addAttribute("practiceName", "强化练习");
		model.addAttribute("knowledgePointId", knowledgePointId);
		model.addAttribute("questionTypeId", questionTypeId);
		model.addAttribute("fieldId", fieldId);
		return "practice-improve-qh";
	}
	
	/**
	 * 错题练习
	 * 
	 * @param model
	 * @param exam_history_id
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/student/practice-incorrect/{fieldId}/{knowledgePointId}", method = RequestMethod.GET)
	public String practiceIncorrectQuestions(Model model, HttpServletRequest request,@PathVariable("fieldId") int fieldId,@PathVariable("knowledgePointId") int knowledgePointId) {
		
		
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		
		Map<Integer,List<UserQuestionHistory>> historyMap = questionHistoryService.getUserQuestionHist(userInfo.getUserid(), fieldId);
		
		List<UserQuestionHistory> historyList = historyMap.get(knowledgePointId);
		
		List<Integer> idList = new ArrayList<Integer>();
		for(UserQuestionHistory history : historyList){
			if(!history.isRight())
				idList.add(history.getQuestionId());
		}
		
		List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
		if(idList.size() > 0)
			qqrList = questionService.getQuestionAnalysisListByIdList(idList);
		
		int amount = idList.size();
		StringBuilder sb = new StringBuilder();
		for(QuestionQueryResult qqr : qqrList){
			QuestionAdapter adapter = new QuestionAdapter(qqr,strUrl);
			sb.append(adapter.getStringFromXML());
		}
		
		model.addAttribute("questionStr", sb.toString());
		model.addAttribute("amount", amount);
		model.addAttribute("questionTypeName", "错题库");
		model.addAttribute("practiceName", "错题练习");
		return "practice-improve";
	}
	
	
	/**
	 * 获取用户的练习记录（试题ID）
	 * @param userId
	 * @param knowledgePointId
	 * @return
	 */
	@RequestMapping(value = "/student/practice-improve-his/{fieldId}/{knowledgePointId}/{questionTypeId}", method = RequestMethod.GET)
	public @ResponseBody List<Integer> getFinishedQuestionId(Model model, HttpServletRequest request,@PathVariable("fieldId") int fieldId,
			@PathVariable("knowledgePointId") int knowledgePointId,@PathVariable("questionTypeId") int questionTypeId){
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		Map<Integer,List<UserQuestionHistory>> historyMap = questionHistoryService.getUserQuestionHist(userInfo.getUserid(), fieldId);
		
		List<Integer> l = new ArrayList<Integer>();
		
		
		return l;
		
		
	}
	
	@RequestMapping(value = "/student/practice-test/{fieldId}", method = RequestMethod.GET)
	public String practiceStartNew(Model model, HttpServletRequest request,@PathVariable("fieldId") int fieldId) {

		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		Map<Integer, Map<Integer, List<QuestionQueryResult>>>  map = questionService.getQuestionMapByFieldId(fieldId, null);
		List<QuestionQueryResult> qqrList = new ArrayList<QuestionQueryResult>();
		for(Map.Entry<Integer, Map<Integer, List<QuestionQueryResult>>> entry : map.entrySet()){
			if(entry.getValue().containsKey(1))
				qqrList.addAll(entry.getValue().get(1));
			if(entry.getValue().containsKey(2))
				qqrList.addAll(entry.getValue().get(2));
			if(entry.getValue().containsKey(3))
				qqrList.addAll(entry.getValue().get(3));
		}
		int amount = 0;
		if(qqrList.size() == 0){
			model.addAttribute("errorMsg", "无可用的题目");
			return "error";
		}
			
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i = 0 ; i < 20 ; i ++){
			int questionCount = qqrList.size();
			int index = random.nextInt(questionCount);
			QuestionAdapter adapter = new QuestionAdapter(qqrList.get(index),strUrl);
			sb.append(adapter.getStringFromXML());
			qqrList.remove(index);
			amount ++;
			if(qqrList.size() == 0)
				break;
		}
		
		model.addAttribute("questionStr", sb.toString());
		model.addAttribute("amount", amount);
		model.addAttribute("questionTypeName", "随机题");
		model.addAttribute("practiceName", "随机练习");
		return "practice-improve";
	}
	
	@RequestMapping(value = "/student/practice-list", method = RequestMethod.GET)
	public String practiceListPage(Model model, HttpServletRequest request, @RequestParam(value="fieldId",required=false,defaultValue="0") int fieldId){
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		
		/*
		 * 对应questionMapper的getAllField SQL 查询出的是题库的列表：例如5	商业银行基础知识	商业银行基础知识	0	0
		 *
		 */
		List<Group> userGroups = userService.getGroupListByUserId(userInfo.getUserid(), null);
		
		
		List<Integer> groupList = new ArrayList<Integer>();
		
		if(userGroups != null)
		{
			for(Group g : userGroups)
			{
				groupList.add(g.getGroupId());
			}
		}
		
	     System.out.println("用户所在的组号码:" + groupList);
		List<Field> fieldList = questionService.getAllField(groupList);
		
		// fieldList剔除那些没有相应题目的题库,只要其removeable为1就可以不显示
		
		List<Field> NonRemoveableFieldList = new ArrayList<Field>();
		for(Field fd : fieldList)
		{
			if(fd.isRemoveable() == false) 
			{
				NonRemoveableFieldList.add(fd);
			}
		}
		
		fieldList = NonRemoveableFieldList;
				
        System.out.println("用户获取的题库名称、ID" + fieldList);
		// 目前没有fieldID =0的选项
		if(fieldId == 0 || fieldList == null)
		{
			if(fieldList != null && fieldList.size() > 0)		
			{
			    fieldId = fieldList.get(0).getFieldId();
			}
			else
			{
				return "noPracticeAvailable";
			}
		}
			
		Map<Integer, Map<Integer, QuestionStatistic>> questionMap = questionService.getTypeQuestionStaticByFieldId(fieldId);
		Map<Integer, Map<Integer, QuestionStatistic>> historyMap = questionHistoryService.getTypeQuestionHistStaticByFieldId(fieldId, userInfo.getUserid());
		Map<Integer, QuestionStatistic> historyStatisticMap = questionHistoryService.getQuestionHistStaticByFieldId(fieldId, userInfo.getUserid());
		Map<Integer, KnowledgePoint> pointMap = questionService.getKnowledgePointByFieldId(null, fieldId);
		
		List<KnowledgePointAnalysisResult> kparl = new ArrayList<KnowledgePointAnalysisResult>();
		for(Map.Entry<Integer, Map<Integer, QuestionStatistic>> entry : questionMap.entrySet()){
			KnowledgePointAnalysisResult kpar = new KnowledgePointAnalysisResult();
			kpar.setKnowledgePointId(entry.getKey());
			List<TypeAnalysis> tal = new ArrayList<TypeAnalysis>();
			int totalRightAmount = 0;
			int totalAmount = 0;
			for(Map.Entry<Integer, QuestionStatistic> entry1 : entry.getValue().entrySet()){
				TypeAnalysis ta = new TypeAnalysis();
				ta.setQuestionTypeId(entry1.getKey());
				ta.setQuestionTypeName(entry1.getValue().getQuestionTypeName());
				int rightAmount = 0;
				int wrongAmount = 0;
				try {
					rightAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getRightAmount();
				} catch (Exception e) {}
				try {
					wrongAmount = historyMap.get(entry.getKey()).get(entry1.getKey()).getWrongAmount();
				} catch (Exception e) {}
				ta.setRightAmount(rightAmount);
				ta.setWrongAmount(wrongAmount);
				ta.setRestAmount(entry1.getValue().getAmount() - rightAmount - wrongAmount);
				tal.add(ta);
				if(kpar.getKnowledgePointName() == null)
					kpar.setKnowledgePointName(entry1.getValue().getPointName());
				totalRightAmount += rightAmount;
				totalAmount += entry1.getValue().getAmount();
			}
			kpar.setTypeAnalysis(tal);
			if(totalAmount > 0)
				kpar.setFinishRate((float)totalRightAmount / (float)totalAmount);
			kparl.add(kpar);
		}
		
		model.addAttribute("kparl", kparl);
		model.addAttribute("fieldId", fieldId);
		model.addAttribute("historyMap", historyStatisticMap);
		model.addAttribute("pointMap", pointMap);
		
		
		
		model.addAttribute("fieldList", fieldList);// 只显示有题目的题库
		return "practice";
	}
	
}
