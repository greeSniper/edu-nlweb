package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface TeacherService {
    Teacher login(Teacher teacher);

    void editPassword(Teacher loginUser);

    void add(Teacher teacher);

    void pageQuery(PageBean pageBean);

    void edit(Teacher teacher);

    void deleteBatch(String ids);

    void saveBatch(String ids);

    void restoreBatch(String ids);

    List<Course> findCourseToAssociation(String id);

    List<Course> findCourseByTeacherId(String id);

    void assignCourseToTeacher(String id, String[] courseIds);
}
