package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.StudentMapper;
import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生 Service
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 批量导入学生信息
     */
    public void saveBatch(List<Student> studentList) {
        for(Student student : studentList) {
            //通过id查询该学生是否已经录入
            Student stu = studentMapper.selectByPrimaryKey(student.getId());
            if(stu != null) {
                //若该学生已经被录入过则更新该学生信息
                studentMapper.updateByPrimaryKeySelective(student);
            } else {
                //若该学生没有被录入过则插入该学生信息
                studentMapper.insert(student);
            }
        }
    }

    /**
     * 分页查询学生
     */
    public void pageQuery(PageBean pageBean, StudentVo studentVo) {
        int count = studentMapper.findByCount(studentVo);
        pageBean.setTotal(count);

        List<StudentVo> list = new ArrayList<>();
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            list = studentMapper.findByPage((currentPage-1)*pageSize, pageSize,
                    studentVo.getStuCode(), studentVo.getStuName(), studentVo.getDepartName(),
                    studentVo.getMajorName(), studentVo.getClassName());
        }
        pageBean.setRows(list);
    }

    /**
     * 批量删除学生
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            studentMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 通过班级id查询学生
     */
    public List<Student> findStudentListByClassId(String classId) {
        return studentMapper.findStudentListByClassId(classId);
    }

    //通过学生id和实验id查询stu_exp表中数据
    public StuExpVo findStuExpByStuIdAndExpId(String stuId, String expId) {
        return studentMapper.findStuExpByStuIdAndExpId(stuId, expId);
    }

    /**
     * 通过班级id和试卷id查询学生成绩
     */
    public List<StuTpScoreVo> findStudentListByClassIdAndTpId(String classId, String tpId) {
        return studentMapper.findStudentListByClassIdAndTpId(classId, tpId);
    }

    /**
     * 将达成度合计存入reach数据表中
     */
    public void insertReach(String stuId, String id, String courseId, double reachSum) {
        studentMapper.insertReach(stuId, id, courseId, reachSum);
    }

    /**
     *
     * 将达成度合计批量存入reach数据表中
     */
    public void insertReachBatch(List<ReachVo> reachList) {
        for(ReachVo rv : reachList) {
            insertReach(rv.getStuId(), rv.getPointId(), rv.getCourseId(), rv.getReach());
        }
    }

    /**
     * 达成度分页查询
     */
    public void findReachListByPage(PageBean pageBean, ReachVo reachVo) {
        int count = studentMapper.findReachListByCount(reachVo);
        pageBean.setTotal(count);

        List<ReachVo> list = new ArrayList<>();
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            list = studentMapper.findReachListByPage((currentPage-1)*pageSize, pageSize,
                    reachVo.getStuId(), reachVo.getStuName(), reachVo.getCourseName(),
                    reachVo.getPointName(), reachVo.getClassName(), reachVo.getMajorName());
        }
        pageBean.setRows(list);
    }

    /**
     * 通过学生id查询reachVo列表
     */
    public List<ReachVo> findReachVoListByStuId(String id) {
        return studentMapper.findReachVoListByStuId(id);
    }

    public Student findStudentById(String id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    public List<ReachVo> findReachVoListByStuIdAndCourseId(String stuId, String courseId) {
        return studentMapper.findReachVoListByStuIdAndCourseId(stuId, courseId);
    }

    /**
     * 清空，删除所有学生指标点达成度数据
     */
    public void deleteReachBatch() {
        studentMapper.deleteReachBatch();
    }

    /**
     * 通过学生id和其他题目id查询stu_other表中记录
     */
    public StuExpVo findStuOtherByStuIdAndotherId(String stuId, String otherId) {
        return studentMapper.findStuOtherByStuIdAndOtherId(stuId, otherId);
    }

    /**
     * 通过 学生id 毕业要求指标点id 课程id 查询达成度
     */
    public ReachVo findReachByStuIdAndZbyyqIdAndCourseId(String id, String id1, String id2) {
        return studentMapper.findReachByStuIdAndZbyyqIdAndCourseId(id, id1, id2);
    }

    /**
     * 通过 课程id 毕业要求指标点id 毕业要求模板id 查询权重
     */
    public QuanzhongVo findQuanzhongByCourseIdAndZbyyqIdAndMid(String id, String id1, String mid) {
        return studentMapper.findQuanzhongByCourseIdAndZbyyqIdAndMid(id, id1, mid);
    }

    /**
     * 通过 毕业要求模板id 毕业要求指标点id 课程id 查询达成度
     */
    public List<ReachVo> findReachByMidAndPointIdAndCourseId(String mid, String pointId, String courseId) {
        return studentMapper.findReachByMidAndPointIdAndCourseId(mid, pointId, courseId);
    }

}
