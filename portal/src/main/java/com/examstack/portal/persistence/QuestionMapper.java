package com.examstack.portal.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.personality.PersonalityCharactor;
import com.examstack.common.domain.personality.PersonalityScore;
import com.examstack.common.domain.personality.PersonalityTestXuepai;
import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.domain.question.QuestionType;
import com.examstack.common.domain.user.Group;
import com.examstack.common.util.Page;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:32:33
 */
public interface QuestionMapper {

	public List<QuestionType> getQuestionTypeList();

	public Question getQuestionByQuestionId(@Param("questionId") int questionId);

	/**
	 * 获取某一题型的试题
	 * 
	 * @param QuestionTypeId
	 * @param page
	 * @return
	 */
	public List<Question> getQuestionByTypeId(@Param("QuestionTypeId") int QuestionTypeId,
			@Param("page") Page<Question> page);

	/**
	 * 按知识点获取试题
	 * 
	 * @param idList
	 * @return
	 */
	List<QuestionStruts> getQuestionListByPointId(@Param("array") List<Integer> idList);

	/**
	 * 根据试题类型和知识点获取试题
	 * 
	 * @param typeId
	 * @param pointId
	 * @return
	 */
	List<QuestionQueryResult> getQuestionAnalysisListByPointIdAndTypeId(@Param("typeId") int typeId,
			@Param("pointId") int pointId);

	/**
	 * 根据试题id获取试题清单
	 * 
	 * @param idList
	 * @return
	 */
	List<QuestionQueryResult> getQuestionAnalysisListByIdList(@Param("array") List<Integer> idList);

	/**
	 * 获取所有的Field
	 * 
	 * @param page
	 * @return
	 */
	public List<Field> getAllField(@Param("userId") int userId);

	/**
	 * 获取Field下的知识点
	 * 
	 * @param fieldIdList
	 *            为null则获取所有知识点
	 * @param page
	 * @return
	 */
	public List<KnowledgePoint> getKnowledgePointByFieldId(@Param("array") int[] fieldIdList,
			@Param("page") Page<KnowledgePoint> page);

	/**
	 * 按专业获取试题
	 * 
	 * @param fieldId
	 * @param page
	 * @return
	 */
	public List<QuestionQueryResult> getQuestionListByFieldId(@Param("fieldId") int fieldId,
			@Param("page") Page<QuestionQueryResult> page);
	
	/**
	 * 根据fieldId,pointId分组统计试题数量
	 * @param fieldId
	 * @return
	 */
	public List<QuestionStatistic> getQuestionStaticByFieldId(int fieldId);
	
	/**
	 * 根据fieldId,pointId,typeId分组统计试题数量
	 * @param fieldId
	 * @return
	 */
	public List<QuestionStatistic> getTypeQuestionStaticByFieldId(int fieldId);

	/*
	 * 获取性格测试的学派信息，例如九型人格等
	 */
	public List<PersonalityTestXuepai> getPersonalityTestXuepai();

	public List<PersonalityScore> getPersonalityLst(int xuepaiId);

	public PersonalityCharactor getPersonalityTestCharactorById(int charactorId);

	public List<Question> getQuestionListByIdList(@Param("ids") List<Integer> ids);

	public List<QuestionQueryResult> getQuestionAnalysisListByIdListAndKnowLedgePointId(@Param("array") List<Integer> idList,
			@Param("knowledgePointId") int knowledgePointId);
}
