package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface StudentService {
    void saveBatch(List<Student> studentList);

    void pageQuery(PageBean pageBean, StudentVo studentVo);

    void deleteBatch(String ids);

    List<Student> findStudentListByClassId(String classId);

    StuExpVo findStuExpByStuIdAndExpId(String stuId, String expId);

    List<StuTpScoreVo> findStudentListByClassIdAndTpId(String classId, String tpId);

    void insertReach(String stuId, String id, String courseId, double reachSum);

    void insertReachBatch(List<ReachVo> reachList);

    void findReachListByPage(PageBean pageBean, ReachVo reachVo);

    List<ReachVo> findReachVoListByStuId(String id);

    Student findStudentById(String id);

    List<ReachVo> findReachVoListByStuIdAndCourseId(String stuId, String courseId);

    void deleteReachBatch();

    StuExpVo findStuOtherByStuIdAndotherId(String stuId, String otherId);

    ReachVo findReachByStuIdAndZbyyqIdAndCourseId(String id, String id1, String id2);

    QuanzhongVo findQuanzhongByCourseIdAndZbyyqIdAndMid(String id, String id1, String mid);

    List<ReachVo> findReachByMidAndPointIdAndCourseId(String mid, String pointId, String courseId);
}
