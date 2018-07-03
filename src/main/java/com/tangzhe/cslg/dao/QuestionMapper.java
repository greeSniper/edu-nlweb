package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Question;
import com.tangzhe.cslg.pojo.QuestionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    int deleteByPrimaryKey(String id);

    int insert(Question record);

    int insertSelective(Question record);

    Question selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);

    int findCountByTpId(String tpId);

    List<QuestionVo> findPageByTpId(@Param("startRow") int startRow,
                                    @Param("pageSize") int pageSize,
                                    @Param("tpId") String tpId);

    List<Question> findQuestionListByTestPaperId(String id);

    List<Question> findQuestionListByPointId(String pointId);

    List<Question> findQuestionListByTpIdAndZbyyqId(@Param("tpId") String tpId,
                                                    @Param("pointId") String pointId);
}