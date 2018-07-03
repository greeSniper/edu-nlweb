package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Other;
import com.tangzhe.cslg.pojo.StuExpVo;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface OtherService {
    void add(Other other);

    void pageQuery(PageBean pageBean, String teacherId);

    void deleteBatch(String ids);

    void edit(Other other);

    List<Other> findOtherListByCourseIdAndTeacherId(String courseId, String teacherId);

    Other findOtherByCourseIdAndTeacherIdAndOtherName(String courseId, String teacherId, String otherName);

    void saveStuOtherBatch(List<StuExpVo> seList);

    List<Other> findOtherListByMidAndCourseIdAndPointIdAndTeacherId(String mid, String courseId, String id, String teacherId);
}
