package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Other;
import com.tangzhe.cslg.pojo.OtherVo;
import com.tangzhe.cslg.pojo.StuExpVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OtherMapper {
    int deleteByPrimaryKey(String id);

    int insert(Other record);

    int insertSelective(Other record);

    Other selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Other record);

    int updateByPrimaryKey(Other record);

    int findCountByTeacherId(String teacherId);

    List<OtherVo> findPageByTeacherId(@Param("startRow") int startRow,
                                      @Param("pageSize") int pageSize,
                                      @Param("teacherId") String teacherId);

    List<Other> findOtherListByCourseIdAndTeacherId(@Param("courseId") String courseId,
                                                    @Param("teacherId") String teacherId);

    Other findOtherByCourseIdAndTeacherIdAndOtherName(@Param("courseId") String courseId,
                                                      @Param("teacherId") String teacherId,
                                                      @Param("otherName") String otherName);

    void insertStuOther(StuExpVo se);

    List<Other> findOtherListByMidAndCourseIdAndPointIdAndTeacherId(@Param("mid") String mid,
                                                                    @Param("courseId") String courseId,
                                                                    @Param("pointId") String pointId,
                                                                    @Param("teacherId") String teacherId);
}