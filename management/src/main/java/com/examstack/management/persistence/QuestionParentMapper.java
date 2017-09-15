package com.examstack.management.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.examstack.common.domain.question.Field;
import com.examstack.common.domain.question.KnowledgePoint;
import com.examstack.common.domain.question.PointStatistic;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionFilter;
import com.examstack.common.domain.question.QuestionParent;
import com.examstack.common.domain.question.QuestionParentIdAndTitleDesc;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStatistic;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.domain.question.QuestionTag;
import com.examstack.common.domain.question.QuestionType;
import com.examstack.common.domain.question.Tag;
import com.examstack.common.util.Page;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午8:32:33
 */
public interface QuestionParentMapper {

	public List<QuestionParent> getQuestionParentList();
	public void insertQuestionParent(QuestionParent questionParent) throws Exception;
	public void deleteQuestionParentById(@Param("Id") int id);
}
