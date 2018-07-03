package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Student record);

    int insertSelective(Student record);

    Student selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Student record);

    int updateByPrimaryKey(Student record);

    int findByCount(StudentVo studentVo);

    List<StudentVo> findByPage(@Param("startRow") int startRow,
                               @Param("pageSize") int pageSize,
                               @Param("stuCode") String stuCode,
                               @Param("stuName") String stuName,
                               @Param("departName") String departName,
                               @Param("majorName") String majorName,
                               @Param("className") String className);

    List<Student> findStudentListByClassId(String classId);

    void insertStuTpScore(StuTpScoreVo sts);

    void insertStuExp(StuExpVo se);

    StuExpVo findStuExpByStuIdAndExpId(@Param("stuId") String stuId,
                                       @Param("expId") String expId);

    List<StuTpScoreVo> findStudentListByClassIdAndTpId(@Param("classId") String classId,
                                                       @Param("tpId") String tpId);

    void insertReach(@Param("stuId") String stuId,
                     @Param("pointId") String pointId,
                     @Param("courseId") String courseId,
                     @Param("reachSum") double reachSum);

    int findReachListByCount(ReachVo reachVo);

    List<ReachVo> findReachListByPage(@Param("startRow") int startRow,
                                      @Param("pageSize") int pageSize,
                                      @Param("stuId") String stuId,
                                      @Param("stuName") String stuName,
                                      @Param("courseName") String courseName,
                                      @Param("pointName") String pointName,
                                      @Param("className") String className,
                                      @Param("majorName") String majorName);

    List<ReachVo> findReachVoListByStuId(String id);

    List<ReachVo> findReachVoListByStuIdAndCourseId(@Param("stuId") String stuId,
                                                    @Param("courseId") String courseId);

    void deleteReachBatch();

    StuExpVo findStuOtherByStuIdAndOtherId(@Param("stuId") String stuId,
                                           @Param("otherId") String otherId);

    ReachVo findReachByStuIdAndZbyyqIdAndCourseId(@Param("stuId") String stuId,
                                                  @Param("zbyyqId") String zbyyqId,
                                                  @Param("courseId") String courseId);

    QuanzhongVo findQuanzhongByCourseIdAndZbyyqIdAndMid(@Param("courseId") String courseId,
                                                        @Param("zbyyqId") String zbyyqId,
                                                        @Param("mid") String mid);

    List<ReachVo> findReachByMidAndPointIdAndCourseId(@Param("mid") String mid,
                                                      @Param("pointId") String pointId,
                                                      @Param("courseId") String courseId);

    int findReachSearchListByCount(ReachSearchVo reachSearchVo);

    List<ReachSearchVo> findReachSearchListByPage(@Param("startRow") int startRow,
                                                  @Param("pageSize") int pageSize,
                                                  @Param("grade") String grade,
                                                  @Param("classId") String classId,
                                                  @Param("courseId") String courseId,
                                                  @Param("pointId") String pointId,
                                                  @Param("reach") Double reach);

    List<ReachSearchVo> findDownload(ReachSearchVo reachSearchVo);
}