package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface CslgClassService {
    void add(CslgClass cslgClass);

    void pageQuery(PageBean pageBean, QueryVo queryVo);

    void edit(CslgClass cslgClass);

    void deleteBatch(String ids);

    void classSelectCourseBatch(String ids, String classId);

    void classUndoSelectCourseBatch(String ids, String classId);

    CslgClass findById(String id);

    void findCourseHasSelectedByClassId(PageBean pageBean, CslgClass cslgClass);

    CslgClass findByName(String className);

    List<CslgClass> findClassListByTeacherId(String teacherId);

    void associationByyqmb(String classId, String mid);

    void delAssociationByyqmb(String classId, String mid);
}
