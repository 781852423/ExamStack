package com.examstack.management.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.Group2Field;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.PointStatistic;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionContent;
import com.examstack.common.domain.question.QuestionFilter;
import com.examstack.common.domain.question.QuestionParent;
import com.examstack.common.domain.question.QuestionParentIdAndTitleDesc;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.domain.question.QuestionTag;
import com.examstack.common.domain.question.QuestionType;
import com.examstack.common.domain.question.Tag;
import com.examstack.common.domain.question.charactorType;
import com.examstack.common.util.Page;
import com.examstack.common.util.file.ExcelUtil;
import com.examstack.management.persistence.QuestionMapper;
import com.examstack.management.persistence.QuestionParentMapper;
import com.google.gson.Gson;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:21:13
 */
@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private QuestionParentMapper questionParentMapper;

	@Override
	public List<Question> getQuestionList(Page<Question> pageModel, QuestionFilter qf) {
		// TODO Auto-generated method stub
		return questionMapper.getQuestionList(qf, pageModel);
	}

	@Override
	public List<Field> getAllField(Page<Field> page) {
		
		return questionMapper.getAllField(page);
	}

	@Override
	public List<KnowledgePoint> getKnowledgePointByFieldId(int fieldId, Page<KnowledgePoint> page) {
		// TODO Auto-generated method stub
		return questionMapper.getKnowledgePointByFieldId(fieldId, page);
	}

	@Override
	public List<QuestionType> getQuestionTypeList() {
		// TODO Auto-generated method stub
		return questionMapper.getQuestionTypeList();
	}
	
	@Override 
	
	public List<QuestionParentIdAndTitleDesc>
	  getQuestionParentIdAndTitleDescList() {
		// TODO Auto-generated method stub
		return questionMapper.getQuestionParentIdAndTitleDescList();
	}

	@Override
	public List<Tag> getTags(Page<Tag> page) {
		// TODO Auto-generated method stub
		return questionMapper.getTags(page);
	}

	@Override
	public void addTag(Tag tag) {
		// TODO Auto-generated method stub
		questionMapper.addTag(tag);
	}

	@Override
	@Transactional
	public void addQuestion(Question question) {
	
		try {
				questionMapper.insertQuestion(question);
				for (Integer i : question.getPointList()) {
					questionMapper.addQuestionKnowledgePoint(question.getId(), i);
				}
				// 最终落实到xml文件中addQuestionTag
				addQuestionTag(question.getId(),0,question.getTagList()); // 添加代码添加tags
				// 添加charactor
				addQuestionCharactorType(question.getId(),0,question.getCharactorTypeList())
;			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private void addQuestionCharactorType(int questionId, int userId, List<charactorType> charactorTypeList) {
		
		try {
			List<Integer> idList = new ArrayList<Integer>();
			if(charactorTypeList == null || charactorTypeList.size() == 0)
			{
				return;
			}
			for (charactorType t : charactorTypeList) {
				//idList.add(t.getCharactorTypeId());	
				t.setQuestionId(questionId);
			}
			questionMapper.deleteQuestionCharactorTypeByQuestionId(questionId);
			
			questionMapper.addQuestionCharactorType(charactorTypeList);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	@Transactional
	public void addQuestionParent(QuestionParent questionParent)
	{
		// 针对完全是题干的题目，如何添加进去，如下
		try {
			questionParentMapper.insertQuestionParent(questionParent);
		
	} catch (Exception e) {
		throw new RuntimeException(e.getMessage());
	}
	}

	@Override
	public void addField(Field field) {
		// TODO Auto-generated method stub
		questionMapper.addField(field);
	}

	@Override
	public void addKnowledgePoint(KnowledgePoint point) {
		// TODO Auto-generated method stub
		questionMapper.addKnowledgePoint(point);
	}

	@Override
	public List<QuestionTag> getQuestionTagByQuestionIdAndUserId(int questionId, int userId, Page<QuestionTag> page) {
		// TODO Auto-generated method stub
		return questionMapper.getQuestionTagByQuestionIdAndUserId(questionId, userId, page);
	}

	@Override
	@Transactional
	public void addQuestionTag(int questionId, int userId, List<QuestionTag> questionTagList) {
		
		try {
			List<Integer> idList = new ArrayList<Integer>();
			if(questionTagList == null || questionTagList.size() == 0)
			{
				return;
			}
			for (QuestionTag t : questionTagList) {
				idList.add(t.getTagId());
				t.setQuestionId(questionId); // 原先的questionTagList里面questionId为0
			}
			questionMapper.deleteQuestionTag(questionId, userId, idList.size() == 0 ? null : idList);
			
			questionMapper.addQuestionTag(questionTagList);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 重载，整合了tag的功能
	 * 
	 * @see com.extr.service.QuestionServiceImpl#updateQuestionPoint(Question
	 *      question)
	 * @param question
	 * @param userId
	 * @param questionTagList
	 */
	@Override
	@Transactional
	public void updateQuestionPoint(Question question, int userId, List<QuestionTag> questionTagList) {
		// TODO Auto-generated method stub
		try {
			questionMapper.deleteQuestionPointByQuestionId(question.getId());
			for (int id : question.getPointList()) {
				questionMapper.addQuestionKnowledgePoint(question.getId(), id);
			}
			List<Integer> idList = new ArrayList<Integer>();
			for (QuestionTag t : questionTagList) {
				idList.add(t.getTagId());
			}

			if (questionTagList != null && questionTagList.size() != 0) {
				questionMapper.deleteQuestionTag(question.getId(), userId, idList.size() == 0 ? null : idList);
				questionMapper.addQuestionTag(questionTagList);
			} else {
				questionMapper.deleteQuestionTag(question.getId(), userId, idList.size() == 0 ? null : idList);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getClass().getName());
		}
	}

	@Override
	public void deleteFieldByIdList(List<Integer> idList) {
		// TODO Auto-generated method stub
		questionMapper.deleteFieldByIdList(idList);
	}

	@Override
	public void deleteKnowledgePointByIdList(List<Integer> idList) {
		// TODO Auto-generated method stub
		questionMapper.deleteKnowledgePointByIdList(idList);
	}

	@Override
	public void deleteTagByIdList(List<Integer> idList) {
		// TODO Auto-generated method stub
		questionMapper.deleteTagByIdList(idList);
	}

	@Override
	public Question getQuestionByQuestionId(int questionId) {
		// TODO Auto-generated method stub
		return questionMapper.getQuestionByQuestionId(questionId);
	}

	@Override
	public List<QuestionQueryResult> getQuestionDescribeListByIdList(List<Integer> idList) {
		List<QuestionQueryResult> questionList = questionMapper.getQuestionAnalysisListByIdList(idList);
		return questionList;
	}

	@Override
	public void deleteQuestionByQuestionId(int questionId) {
		// TODO Auto-generated method stub
		questionMapper.deleteQuestionByQuestionId(questionId);
	}

	@Override
	public HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> getQuestionStrutsMap(List<Integer> idList) {
		// TODO Auto-generated method stub
		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> hm = new HashMap<Integer, HashMap<Integer, List<QuestionStruts>>>();
		List<QuestionStruts> questionList = questionMapper.getQuestionListByPointId(idList);
		for (QuestionStruts q : questionList) {
			HashMap<Integer, List<QuestionStruts>> hashmap = new HashMap<Integer, List<QuestionStruts>>();
			List<QuestionStruts> ql = new ArrayList<QuestionStruts>();
			if (hm.containsKey(q.getPointId()))
				hashmap = hm.get(q.getPointId());
			if (hashmap.containsKey(q.getQuestionTypeId()))
				ql = hashmap.get(q.getQuestionTypeId());
			ql.add(q);
			hashmap.put(q.getQuestionTypeId(), ql);
			hm.put(q.getPointId(), hashmap);
		}
		return hm;
	}

	@Transactional
	@Override
	public void uploadQuestions(String filePath, String username, int fieldId) {
		// comment below line on 2017-06-20解决题库导入的时候文件找不到的问题
		// String strPath = ",webapps,files,question," + username + ",tmp";
		// 2018-7-12 修改question的name字段，保留50个字，而不是10个字
		String strPath = ",webapps,files,training," + username;
		filePath = System.getProperty("catalina.base") + strPath.replace(',', File.separatorChar) + File.separatorChar
				+ filePath.substring(filePath.lastIndexOf("/")+1);
		Map<String, KnowledgePoint> pointMap = this.getKnowledgePointMapByFieldId(fieldId, null);
		int index = 2;
		try {
			List<Map<String, String>> questionMapList = ExcelUtil.ExcelToList(filePath);
			for (Map<String, String> map : questionMapList) {

				System.out.println(map);
				Question question = new Question();
				question.setName(map.get("题目").length() > 50 ? map.get("题目").substring(0, 50) + "..." : map.get("题目"));
				if (map.get("类型").equals("单选题") || map.get("类型").equals("单项选择题"))
					question.setQuestion_type_id(1);
				else if (map.get("类型").equals("多选题") || map.get("类型").equals("多项选择题"))
					question.setQuestion_type_id(2);
				else if (map.get("类型").equals("判断题"))
					question.setQuestion_type_id(3);
				else if (map.get("类型").equals("填空题"))
					question.setQuestion_type_id(4);
				else if (map.get("类型").equals("简答题"))
					question.setQuestion_type_id(5);
				else if (map.get("类型").equals("论述题"))
					question.setQuestion_type_id(6);
				else if (map.get("类型").equals("分析题"))
					question.setQuestion_type_id(7);

				question.setAnalysis(map.get("解析"));
				question.setAnswer(map.get("答案"));
				if (question.getQuestion_type_id() == 3) {
					if (map.get("答案").equals("对"))
						question.setAnswer("T");
					if (map.get("答案").equals("错"))
						question.setAnswer("F");
				}

				KnowledgePoint kp = pointMap.get(map.get("知识类"));
				if(kp == null)
					throw new Exception("知识类不存在：" + map.get("知识类"));
				List<Integer> pointList = new ArrayList<Integer>();
				pointList.add(kp.getPointId());
				question.setReferenceName(map.get("出处"));
				question.setExamingPoint(map.get("知识点"));
				question.setKeyword(map.get("知识关键点"));
				question.setPoints(map.get("分值").equals("") ? 0 : Float.parseFloat(map.get("分值")));
				QuestionContent qc = new QuestionContent();

				Iterator<String> it = map.keySet().iterator();
				List<String> keyStr = new ArrayList<String>();
				while (it.hasNext()) {
					String key = it.next();
					if (key.contains("选项"))
						keyStr.add(key.replace("选项", ""));
				}

				Collections.sort(keyStr);
				LinkedHashMap<String, String> choiceList = new LinkedHashMap<String, String>();
				for (int i = 0; i < keyStr.size(); i++) {
					if (!map.get("选项" + keyStr.get(i)).trim().equals(""))
						choiceList.put(keyStr.get(i), map.get("选项" + keyStr.get(i)));
				}
				if (question.getQuestion_type_id() == 1 || question.getQuestion_type_id() == 2)
					qc.setChoiceList(choiceList);
				qc.setTitle(map.get("题目"));
				Gson gson = new Gson();
				String content = gson.toJson(qc);
				question.setContent(content);
				question.setCreator(username);
				question.setPointList(pointList);
				this.addQuestion(question);
				index++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			throw new RuntimeException("第" + index + "行有错误，请检查！" + e.getMessage());
		}
	}

	@Override
	public Map<String, KnowledgePoint> getKnowledgePointMapByFieldId(int fieldId, Page<KnowledgePoint> page) {
		// TODO Auto-generated method stub
		Map<String, KnowledgePoint> map = new HashMap<String, KnowledgePoint>();
		List<KnowledgePoint> pointList = questionMapper.getKnowledgePointByFieldId(fieldId, page);
		for (KnowledgePoint point : pointList) {
			map.put(point.getPointName(), point);
		}
		return map;
	}
	
	@Override
	public Map<Integer, Map<Integer, QuestionStatistic>> getTypeQuestionStaticByFieldId(int fieldId) {
		// 选出的格式如下：
		/*
		 * 
		 * fieldId   pointId   pointName    QuestionTypeId    questionTypeName   amount
	          7	           13	      判断推理	    1	                                                           单选题	           8
		 */
		List<QuestionStatistic> statisticList = questionMapper.getTypeQuestionStaticByFieldId(fieldId);
		Map<Integer, Map<Integer, QuestionStatistic>> map = new HashMap<Integer, Map<Integer, QuestionStatistic>>();
		for(QuestionStatistic statistic : statisticList){
			Map<Integer, QuestionStatistic> tmp = map.get(statistic.getPointId());
			if(tmp == null){
				tmp = new HashMap<Integer, QuestionStatistic>();
			}
			tmp.put(statistic.getQuestionTypeId(), statistic);
			map.put(statistic.getPointId(), tmp);
		}
		return map;
	}

	@Override
	public Map<Integer, String> getKnowledgePointMap(int fieldId) {
		// TODO Auto-generated method stub
		List<KnowledgePoint> knowledgeList = null;
		if(fieldId == 0)
			knowledgeList = questionMapper.getKnowledgePointByFieldId(0, null);
		else 
			knowledgeList = questionMapper.getKnowledgePointByFieldId(fieldId, null);
		Map<Integer, String> knowledgeMap = new HashMap<Integer, String>();
		for(KnowledgePoint kp : knowledgeList){
			knowledgeMap.put(kp.getPointId(), kp.getPointName());
		}
		return knowledgeMap;
	}

	@Override
	public Map<Integer, String> getQuestionTypeMap() {
		// TODO Auto-generated method stub
		List<QuestionType> typeList = this.getQuestionTypeList();
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for(QuestionType tp : typeList){
			typeMap.put(tp.getId(), tp.getName());
		}
		return typeMap;
	}

	@Transactional
	@Override
	public void updateQuestion(Question question, List<QuestionTag> questionTagList) {
		// TODO Auto-generated method stub
		try {
			questionMapper.updateQuestion(question);
			questionMapper.deleteQuestionPointByQuestionId(question.getId());
			for (int id : question.getPointList()) {
				questionMapper.addQuestionKnowledgePoint(question.getId(), id);
			}
			List<Integer> idList = new ArrayList<Integer>();
			for (QuestionTag t : questionTagList) {
				idList.add(t.getTagId());
			}

			if (questionTagList != null && questionTagList.size() != 0) {
				questionMapper.deleteQuestionTag(question.getId(), 0, null);
				questionMapper.addQuestionTag(questionTagList);
			} else {
				questionMapper.deleteQuestionTag(question.getId(), 0, null);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e.getClass().getName());
		}
		
	}

	@Transactional
	@Override
	public Question getQuestionDetail(int questionId,int userId) {
		// TODO Auto-generated method stub
		try {
			Question question = questionMapper.getQuestionByQuestionId(questionId);
			List<QuestionTag> tagList = questionMapper.getQuestionTagByQuestionIdAndUserId(questionId, userId, null);
			List<KnowledgePoint> pointList = questionMapper.getQuestionPoint(questionId);
			question.setTagList(tagList);
			question.setKnowledgePoint(pointList);
			return question;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<PointStatistic> getPointCount(int fieldId, Page<PointStatistic> page) {
		// TODO Auto-generated method stub
		return questionMapper.getPointCount(fieldId, page);
	}

	@Override
	public List<Group2Field> getGroup2FieldByGroupId(int groupId) {
		// TODO Auto-generated method stub
		return questionMapper.getGroup2FieldByGroupId(groupId);
	}

	@Override
	public List<Group2Field> getGroup2FieldAll() {
		// TODO Auto-generated method stub
		return questionMapper.getGroup2FieldAll();
	}

	@Override
	public void addField2Group(List<Group2Field> group2FieldList) {
		try {
			// 检查一下目前的关联关系里面是否已经有了记录
			// 目前调用的group2FieldList里面groupId都是一样的，只是fieldId不同
			if(group2FieldList != null && group2FieldList.size() >0)
			{
				List<Group2Field> existingLink = this.getGroup2FieldByGroupId(group2FieldList.get(0).getGroupId());
				
				for(Group2Field g2f : group2FieldList)
				{
					
					if( !existingLink.contains(g2f))
					{
						questionMapper.insertGroup2Field(g2f);
					}
					
				}
			}
			
		} catch (Exception e) {
			
			throw new RuntimeException(e.getClass().getName());
		}
		
	}

	@Override
	public void delField2Group(int group2FieldId) {
		try {
			questionMapper.deleteGroup2FieldById(group2FieldId);
			
		} catch (Exception e) {
			
			throw new RuntimeException(e.getClass().getName());
		}
		
	}

	@Override
	public List<charactorType> getCharactorTypes() {
		// TODO Auto-generated method stub
		return questionMapper.getCharactorTypes();
	}
}
