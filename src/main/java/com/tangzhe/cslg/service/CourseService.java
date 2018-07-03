package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.pojo.ByyqPointVo;
import com.tangzhe.cslg.pojo.CourseStatistic;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface CourseService {
    void add(Course course);

    void pageQuery(PageBean pageBean);

    void edit(Course course);

    void deleteBatch(String ids);

    void associate(String courseId, String[] pointIds, String majorId);

    void findPageCourseByDepartId(PageBean pageBean, CslgClass cslgClass, String courseName, String teacherName);

    List<Course> findCourseByTeacherId(String teacherId);

    void pagePointQuery(PageBean pageBean, String id);

    void deletePointBatch(String ids, String id);

    Course findCourseById(String courseId);

    List<Course> findCourseListByClassIdAndTeacherId(String classId, String id);

    List<Course> findCourseListByClassId(String classId);

    List<ByyqPointVo> findZbyyqListByCourseIdAndMid(String courseId, String mid);

    List<CourseStatistic> findCourseListByMidAndZbyyqId(String mid, String pointId);

    List<CourseStatistic> findCourseListByMidAndFbyyqId(String mid, String fid);

    List<Course> findAllCourse();
}
