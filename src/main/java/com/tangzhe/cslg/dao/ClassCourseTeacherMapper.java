package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.ClassCourseTeacher;
import com.tangzhe.cslg.pojo.CourseVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ClassCourseTeacherMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClassCourseTeacher record);

    int insertSelective(ClassCourseTeacher record);

    ClassCourseTeacher selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClassCourseTeacher record);

    int updateByPrimaryKey(ClassCourseTeacher record);

    ClassCourseTeacher selectByClassIdAndCourseIdAndTeacherId(@Param("classId") String classId,
                                                @Param("courseId") String courseId,
                                                @Param("teacherId") String teacherId);

    int findCourseCountByClassId(String classId);

    List<CourseVo> findCoursePageByClassId(@Param("startRow") int startRow,
                                           @Param("pageSize") int pageSize,
                                           @Param("classId") String classId);
}