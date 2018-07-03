package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Exp;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface ExpService {
    void add(Exp exp);

    void pageQuery(PageBean pageBean, String teacherId);

    void deleteBatch(String ids);

    void edit(Exp exp);

    Exp findExpByCourseIdAndExpName(String courseId, String expName);

    List<Exp> findExpListByPointId(String pointId);

    void add2(Exp exp);

    void edit2(Exp exp);

    void deleteBatch2(String ids);

    List<Exp> findExpListByMidAndPointId(String courseId, String mid, String pointId);

    List<Exp> findExpListByCourseIdAndMidAndPointIdAndTeacherId(String courseId, String mid, String pointId, String teacherId);
}
