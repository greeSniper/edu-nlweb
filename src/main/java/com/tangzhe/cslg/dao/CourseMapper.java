package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.ByyqPointVo;
import com.tangzhe.cslg.pojo.CourseStatistic;
import com.tangzhe.cslg.pojo.CourseVo;
import com.tangzhe.cslg.pojo.PointVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    int deleteByPrimaryKey(String id);

    int insert(Course record);

    int insertSelective(Course record);

    Course selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);

    int findByCount();

    List<CourseVo> findByPage(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    void deletePointCourseByCourseId(String courseId);

    void insertPointCourse(@Param("pointId") String pointId, @Param("courseId") String courseId);

    void deletePointCourseByMajorIdAndCourseId(@Param("majorId") String majorId, @Param("courseId") String courseId);

    int findCountByMajorId(@Param("majorId") String majorId,
                           @Param("courseName") String courseName,
                           @Param("teacherName") String teacherName);

    List<CourseVo> findPageByMajorId(@Param("startRow") int startRow,
                                     @Param("pageSize") int pageSize,
                                     @Param("majorId") String majorId,
                                     @Param("courseName") String courseName,
                                     @Param("teacherName") String teacherName);

    List<Course> findCourseByTeacherId(String teacherId);

    int findPointCountByCourseId(String id);

    List<PointVo> findPointPageByCourseId(@Param("startRow") int startRow,
                                          @Param("pageSize") int pageSize,
                                          @Param("id") String id);

    void deletePointCourse(@Param("pointId") String pointId,
                           @Param("courseId") String courseId);

    List<Course> findCourseListByClassIdAndTeacherId(@Param("classId") String classId,
                                                     @Param("teacherId") String teacherId);

    List<Course> findCourseListByClassId(String classId);

    List<CourseVo> findCourseVoList();

    List<CourseVo> findCourseVoListByMid(String mid);

    List<Zbyyq> findZbyyqByCourseIdAndMid(@Param("courseId") String courseId,
                                          @Param("mid") String mid);

    List<ByyqPointVo> findZbyyqListByCourseIdAndMid(@Param("courseId") String courseId,
                                                    @Param("mid") String mid);

    List<CourseStatistic> findCourseListByMidAndZbyyqId(@Param("mid") String mid,
                                                        @Param("pointId") String pointId);

    List<CourseStatistic> findCourseListByMidAndFbyyqId(@Param("mid") String mid,
                                                        @Param("fid") String fid);

    List<Course> findAllCourse();
}