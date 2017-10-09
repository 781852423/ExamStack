package com.examstack.management.controller.action.admin;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.common.domain.exam.AnswerSheetItem;
import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.Message;
import com.examstack.common.domain.exam.PaperCreatorParam;
import com.examstack.common.domain.exam.PaperPart;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.util.QuestionAdapter;
import com.examstack.common.util.StandardPasswordEncoderForSha1;
import com.examstack.management.security.UserInfo;
import com.examstack.management.service.ExamPaperService;
import com.examstack.management.service.QuestionService;
import com.google.gson.Gson;

@Controller
public class ExamPaperActionAdmin {

	@Autowired
	private ExamPaperService examPaperService;
	@Autowired
	private QuestionService questionService;

	
	/*@RequestMapping(value = "admin/exampaper-add", method = RequestMethod.POST)
	public @ResponseBody
	Message createExamPaper(@RequestBody PaperCreatorParam param) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Message message = new Message();
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName(param.getPaperName());
		examPaper.setDuration(param.getTime());
		examPaper.setPass_point(param.getPassPoint());
		examPaper.setField_id(param.getPaperType());
		examPaper.setCreator(userInfo.getUsername());
		examPaper.setTotal_point(param.getPaperPoint());
		examPaper.setIs_subjective(true);
		
		//手工组卷
		if(param.getQuestionKnowledgePointRate().size() == 0){
			try{
				
				examPaperService.insertExamPaper(examPaper);
			}catch(Exception ex){
				message.setResult(ex.getMessage());
			}
			message.setGeneratedId(examPaper.getId());
			return message;
		}
		List<Integer> idList = new ArrayList<Integer>();

		HashMap<Integer, Float> knowledgeMap = param
				.getQuestionKnowledgePointRate();
		Iterator<Integer> it = knowledgeMap.keySet().iterator();
		while(it.hasNext()){
			idList.add(it.next());
		}

		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap = questionService
				.getQuestionStrutsMap(idList);
		
		try{
			examPaperService.createExamPaper(questionMap, param.getQuestionTypeNum(),
					param.getQuestionTypePoint(),
					param.getQuestionKnowledgePointRate(), examPaper);
			message.setGeneratedId(examPaper.getId());
		}catch(Exception e){
			//e.printStackTrace();
			message.setResult(e.getMessage());
		}
		
		
		return message;
	}*/
	
	/**
	 * 自动或者手动组卷(插入一张空试卷)
	 * 
	 * @param examPaperParam
	 * @return
	 */
	@RequestMapping(value = "admin/exampaper-add", method = RequestMethod.POST)
	public @ResponseBody
	Message createExamPaper(@RequestBody PaperCreatorParam param) {

		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		System.out.println(param);
		Message message = new Message();
		ExamPaper examPaper = new ExamPaper();
		examPaper.setName(param.getPaperName());
		examPaper.setDuration(param.getTime());
		examPaper.setPass_point(param.getPassPoint());
		examPaper.setField_id(param.getPaperType());
		examPaper.setCreator(userInfo.getUsername());
		examPaper.setTotal_point(param.getPaperPoint());
		examPaper.setIs_subjective(false);
		examPaper.setPaperParts(param.getParts());
		
		//手工组卷,手工组卷完毕后直接返回
		if(param.getQuestionKnowledgePointRate().size() == 0){
			try{
				
				examPaperService.insertExamPaper(examPaper);
			}catch(Exception ex){
				message.setResult(ex.getMessage());
			}
			message.setGeneratedId(examPaper.getId());
			return message;
		}
		List<Integer> idList = new ArrayList<Integer>();

		HashMap<Integer, Float> knowledgeMap = param
				.getQuestionKnowledgePointRate();
		Iterator<Integer> it = knowledgeMap.keySet().iterator();
		while(it.hasNext()){
			idList.add(it.next());
		}

		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap = questionService
				.getQuestionStrutsMap(idList);
		
		try{
			examPaperService.createExamPaper(questionMap, param.getQuestionTypeNum(),
					param.getQuestionTypePoint(),
					param.getQuestionKnowledgePointRate(), examPaper);
			message.setGeneratedId(examPaper.getId());
		}catch(Exception e){
			//e.printStackTrace();
			message.setResult(e.getMessage());
		}
		
		
		return message;
	}
	
	/**
	 * 批量添加试题，返回json字符串对象
	 * @param model
	 * @param idList
	 * @return
	 */
	@RequestMapping(value = "/admin/exampaper/get-question-detail4add/{partId}", method = RequestMethod.POST)
	public @ResponseBody List<QuestionQueryResult> getQuestion5add(
			Model model, HttpServletRequest request, 
			@PathVariable("partId") int partId,
			@RequestBody List<Integer> idList) {
		String strUrl = "http://" + request.getServerName() // 服务器地址
				+ ":" + request.getServerPort() + "/";
		
		Set<Integer> set = new TreeSet<Integer>();
		for(int id : idList){
			set.add(id);
		}
		idList.clear();
		Iterator<Integer> it = set.iterator();
		while(it.hasNext()){
			idList.add(it.next());
		}
		// 每道题的分数由part决定
		PaperPart pp = examPaperService.getPaperPartById(partId);
		List<QuestionQueryResult> returnList = questionService.getQuestionDescribeListByIdList(idList);
		
		for(QuestionQueryResult question : returnList){
			question.setQuestionPoint(pp.getPointPerQuestion());
			QuestionAdapter adapter = new QuestionAdapter(question, strUrl);
			question.setContent(adapter.getStringFromXML());
		}
		return returnList;
	}
	
	/*
	 * 根据前台js传过来的试题part参数（partId和questions(只有ID获取到了)进行接收，更新数据库的信息）
	 * 这里不更新对应paper与part的关系，这些在创建paper时候就已经确定了，只对各个part对应questions做更新
	 */
	@RequestMapping(value = "/admin/exampaper/update-exampaper/{examPaperId}", method = RequestMethod.POST)
	public @ResponseBody
	Message exampaperOnUpdate(Model model,
			@PathVariable("examPaperId") int examPaperId,
			@RequestBody List<PaperPart> parts) {
		
		Message message = new Message();
		try{
			ExamPaper examPaper = new ExamPaper();
			// 总分根据每个part计算得来
			int totalPoint = 0;
			
		    for(PaperPart pp : parts)
		    {
		    	PaperPart tmp = examPaperService.getPaperPartById(pp.getId());
		    	totalPoint += tmp.getQuestionCount()*tmp.getPointPerQuestion();
		    	
		    	// 主要是更新每个part对应的题目
				examPaperService.updateExamPartQuestions(pp); // 删掉之前的关联，然后插入新的关联关系
		    }
			examPaper.setTotal_point(totalPoint);
			examPaper.setId(examPaperId);
			
			
			examPaperService.updateExamPaper(examPaper);
		}catch(Exception e){
			message.setResult(e.getLocalizedMessage());
		}
		return message;
	}
	
	@RequestMapping(value = "/admin/exampaper/paper-delete", method = RequestMethod.POST)
	public @ResponseBody Message deleteExamPaper(@RequestBody Integer examPaperId){
		Message message = new Message();
		try{
			ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId);
			if(examPaper.getStatus() == 1){
				message.setResult("已发布的试卷不允许删除");
				return message;
			}
			examPaperService.deleteExamPaper(examPaperId);
		}catch(Exception e){
			message.setResult(e.getClass().getName());
		}
		return message;
	}
	
	/**
	 * 生成试卷doc
	 * @param examPaperId
	 * @return
	 */
	@RequestMapping(value = "admin/exampaper/create-doc-{examPaperId}", method = RequestMethod.GET)
	public @ResponseBody Message createDocPaper(@PathVariable("examPaperId") int examPaperId){
		Message msg = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String dateStr = new SimpleDateFormat("yyyyMMddhh24mmss").format(new Date());
		String filePath = System.getProperty("catalina.base")
				+ ",webapps,files,tmp," + userInfo.getUsername() + "," + dateStr;
		String relativePath = "files,tmp," + userInfo.getUsername() + "," + dateStr;
		ExamPaper examPaper = examPaperService.getExamPaperById(examPaperId); // 指向examPaperMapper.xml中getExamPaperById
		try {
			examPaperService.generateDoc(examPaper, filePath.replace(",", File.separator));
			msg.setMessageInfo((relativePath + "," + examPaper.getName() + ".docx").replace(",", File.separator));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
}
