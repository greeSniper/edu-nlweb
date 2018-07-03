package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.TestPaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestPaperMapper {
    int deleteByPrimaryKey(String id);

    int insert(TestPaper record);

    int insertSelective(TestPaper record);

    TestPaper selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TestPaper record);

    int updateByPrimaryKey(TestPaper record);

    int findCountByTeacherId(String teacherId);

    List<TestPaper> findPageByTeacherId(@Param("startRow") int startRow,
                               @Param("pageSize") int pageSize,
                               @Param("teacherId") String teacherId);

    void updateStateById(@Param("id") String id, @Param("state") int state);

    List<TestPaper> findTestPaperByTeacherId(String teacherId);
}