package com.examstack.portal.controller.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.exam.UserQuestionHistory;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionHistory;
import com.examstack.portal.security.UserInfo;
import com.examstack.portal.service.QuestionHistoryService;
import com.examstack.portal.service.QuestionService;
import com.google.gson.Gson;

@Controller
public class PracticeAction {
	
	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionHistoryService questionHistoryService;
	/**
	 * 练习模式完成一道题
	 * 页面“参加练习”后，处理提交按钮
	 * @param sp
	 * @return
	 */
	@RequestMapping(value = "/student/practice-improve", method = RequestMethod.POST)
	public @ResponseBody Message submitPractice(@RequestBody QuestionHistory qh) {
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		UserQuestionHistory history = new UserQuestionHistory();
		history.setQuestionId(qh.getQuestionId());
		history.setUserId(userInfo.getUserid());
		history.setQuestionTypeId(qh.getQuestionTypeId());
		boolean isRight = qh.getAnswer().equals(qh.getMyAnswer()) ? true : false;
		history.setRight(isRight);
		
		try {
			questionHistoryService.addUserQuestionHist(history);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setResult(e.getClass().getName());
			e.printStackTrace();
		}

		return msg;
	}
	
	// student/putFavoriteQuestion
	/**
	 * 收藏一道题
	 * @param sp
	 * @return
	 */
	@RequestMapping(value = "/student/putFavoriteQuestion", method = RequestMethod.POST)
	public @ResponseBody Message submitFavoriteQuestion(@RequestBody Question qh) {
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		Question qt = new Question();
		qt.setId(qh.getId());
		//qt.setUserId(userInfo.getUserid());
		
		try {
			  questionHistoryService.addUserFavoriteQuestion(qh.getId(), userInfo.getUserid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg.setResult(e.getClass().getName());
			e.printStackTrace();
		}

		return msg;
	}
	
	/**
	 * 练习模式处理答案提交，处理多道题目回答情况
	 * 
	 * @param sp
	 * @return
	 */
	@RequestMapping(value = "/student/practice-improve/savePractiveHistory", method = RequestMethod.POST)
	public @ResponseBody Message submitBulkPractice(@RequestBody List<QuestionHistory> qhList) {
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<UserQuestionHistory> userHistoryList = new ArrayList<UserQuestionHistory>();
		if(qhList == null || qhList.size() == 0)
		{
			msg.setMessageInfo("后台没有接到合适的数据");
			return msg;
		}
		
		for(QuestionHistory qh : qhList)
		{
			UserQuestionHistory history = new UserQuestionHistory();
			history.setQuestionId(qh.getQuestionId());
			history.setUserId(userInfo.getUserid());
			history.setQuestionTypeId(qh.getQuestionTypeId());
			// 分情况，如果答案是空的，则默认表示没有做题，不妨到历史记录里面
			if(qh.getMyAnswer() != "")
			{
				boolean isRight = qh.getAnswer().equals(qh.getMyAnswer()) ? true : false;
				history.setRight(isRight);
				userHistoryList.add(history);
			}
		}
		
		
		try {
			System.out.println("收到前台提交的答题信息：" + userHistoryList);
			questionHistoryService.addUserQuestionHist(userHistoryList);
		} catch (Exception e) {
			
			msg.setResult(e.getClass().getName());
			e.printStackTrace();
		}

		return msg;
	}
	
	/**
	 * 根据questionIds，获取是否收藏的状态
	 * 
	 * @param sp
	 * @return
	 */
	@RequestMapping(value = "/student/getFavoriteQuestionStatus", method = RequestMethod.POST)
	public @ResponseBody Message getFavoriteQuestionStatus(@RequestBody List<QuestionHistory> qhList) {
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<UserQuestionHistory> userHistoryList = new ArrayList<UserQuestionHistory>();
		if(qhList == null || qhList.size() == 0)
		{
			msg.setMessageInfo("后台没有接到合适的数据");
			System.out.println("后台没有接到合适的题目Id等信息");
			return msg;
		}
		
		for(QuestionHistory qh : qhList)
		{
			UserQuestionHistory history = new UserQuestionHistory();
			history.setQuestionId(qh.getQuestionId());
			history.setUserId(userInfo.getUserid());
			
			userHistoryList.add(history);
		}
		
		
		try {
			System.out.println("收到前台提交的题目Id等信息：" + userHistoryList);
			List<UserQuestionHistory> userHistoryListWithFavoriteStatus = questionHistoryService.getFavoriteQuestionStatus(userHistoryList);
			if(userHistoryListWithFavoriteStatus != null)
			{
				msg.setMessageInfo(new Gson().toJson(userHistoryListWithFavoriteStatus));
			}else {
				msg.setMessageInfo("");
			}
			
		} catch (Exception e) {
			
			msg.setResult(e.getClass().getName());
			e.printStackTrace();
		}

		return msg;
	}
}
