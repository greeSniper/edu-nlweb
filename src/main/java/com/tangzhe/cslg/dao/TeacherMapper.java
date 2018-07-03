package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.pojo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    int deleteByPrimaryKey(String id);

    int insert(Teacher record);

    int insertSelective(Teacher record);

    Teacher selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Teacher record);

    int updateByPrimaryKey(Teacher record);

    Teacher findTeacherByUsernameAndPwd(Teacher teacher);

    int findByCount();

    List<QueryVo> findByPage(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    void updateType(@Param("id") String id, @Param("type") Integer type);

    List<Course> findCourseToAssociation(String id);

    List<Course> findCourseByTeacherId(String id);

    void deleteCourseTeacherByTeacherId(String id);

    void insertCourseTeacher(@Param("cid") String cid, @Param("tid") String tid);
}