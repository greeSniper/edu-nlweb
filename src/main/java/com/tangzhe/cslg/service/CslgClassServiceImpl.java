package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.ClassCourseTeacherMapper;
import com.tangzhe.cslg.dao.CslgClassMapper;
import com.tangzhe.cslg.entity.ClassCourseTeacher;
import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.pojo.ClassByyqmbVo;
import com.tangzhe.cslg.pojo.CourseVo;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级 Service
 */
@Service
@Transactional
public class CslgClassServiceImpl implements CslgClassService {

    @Autowired
    private CslgClassMapper cslgClassMapper;
    @Autowired
    private ClassCourseTeacherMapper classCourseTeacherMapper;

    /**
     * 新增班级
     */
    public void add(CslgClass cslgClass) {
        cslgClass.setId(UUIDUtils.getId());
        cslgClassMapper.insert(cslgClass);
    }

    /**
     * 分页查询班级
     */
    public void pageQuery(PageBean pageBean, QueryVo queryVo) {
        int count = cslgClassMapper.findByCount(queryVo);
        List<QueryVo> list = new ArrayList<>();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            queryVo.setStartRow((currentPage-1)*pageSize);
            queryVo.setPageSize(pageSize);
            list = cslgClassMapper.findByPage(queryVo);
            pageBean.setRows(list);
        } else {
            pageBean.setRows(list);
        }
    }

    /**
     * 修改班级
     */
    public void edit(CslgClass cslgClass) {
        CslgClass cc = cslgClassMapper.selectByPrimaryKey(cslgClass.getId());
        cc.setGrade(cslgClass.getGrade());
        cc.setClassCode(cslgClass.getClassCode());
        cc.setClassName(cslgClass.getClassName());
        cc.setMajorId(cslgClass.getMajorId());

        cslgClassMapper.updateByPrimaryKeySelective(cc);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            cslgClassMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * courselist.jsp中点击选课按钮，为班级批量选课
     */
    public void classSelectCourseBatch(String ids, String classId) {
        //ids：课程id_教师id,课程id_教师id,课程id_教师id
        //classId：班级id
        String[] idArr = ids.split(",");
        for(String cidtid : idArr) {
            //cidtid：课程id_教师id
            String courseId = cidtid.split("_")[0];
            String teacherId = cidtid.split("_")[1];

            //判断该班级是否已经选了这个课
            ClassCourseTeacher classCourseTeacher = classCourseTeacherMapper.selectByClassIdAndCourseIdAndTeacherId(classId, courseId, teacherId);
            if(classCourseTeacher != null) {
                //已选，则continue
                continue;
            }
            //未选，则将classId，courseId，teacherId存入数据库表class_course_teacher中
            ClassCourseTeacher cct = new ClassCourseTeacher();
            cct.setId(UUIDUtils.getId());
            cct.setClassId(classId);
            cct.setCourseId(courseId);
            cct.setTeacherId(teacherId);
            classCourseTeacherMapper.insertSelective(cct);
        }
    }

    /**
     * courselist.jsp中点击取消选课按钮发送的请求，为班级批量取消选课
     */
    public void classUndoSelectCourseBatch(String ids, String classId) {
        String[] idArr = ids.split(",");
        for(String cidtid : idArr) {
            String courseId = cidtid.split("_")[0];
            String teacherId = cidtid.split("_")[1];

            //判断该班级是否已经选了这个课
            ClassCourseTeacher classCourseTeacher = classCourseTeacherMapper.selectByClassIdAndCourseIdAndTeacherId(classId, courseId, teacherId);
            if(classCourseTeacher == null) {
                //未选，则continue
                continue;
            }
            //已选，则根据classId，courseId，teacherId删除数据库表class_course_teacher中数据
            ClassCourseTeacher cct = new ClassCourseTeacher();
            cct.setId(UUIDUtils.getId());
            cct.setClassId(classId);
            cct.setCourseId(courseId);
            cct.setTeacherId(teacherId);
            classCourseTeacherMapper.deleteByPrimaryKey(classCourseTeacher.getId());
        }
    }

    /**
     * 根据班级id查询班级
     */
    public CslgClass findById(String id) {
        return cslgClassMapper.selectByPrimaryKey(id);
    }

    /**
     * classCourseHasSelected.jsp中/cslgClass/findCourseHasSelectedByClassId?id="+classId请求
     * 分页查询班级已选课程
     */
    public void findCourseHasSelectedByClassId(PageBean pageBean, CslgClass cslgClass) {
        int count = classCourseTeacherMapper.findCourseCountByClassId(cslgClass.getId());
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<CourseVo> list = classCourseTeacherMapper.findCoursePageByClassId((currentPage-1)*pageSize, pageSize, cslgClass.getId());

            //将所有courseVo的isSelect设置为1
            for(CourseVo cv : list) {
                cv.setIsSelect(1);
            }

            pageBean.setRows(list);
        }
    }

    /**
     * 通过班级名称查询班级
     */
    public CslgClass findByName(String className) {
        return cslgClassMapper.findByName(className);
    }

    /**
     * 通过教师id查询班级
     */
    public List<CslgClass> findClassListByTeacherId(String teacherId) {
        return cslgClassMapper.findClassListByTeacherId(teacherId);
    }

    /**
     * 班级关联指标点权重矩阵
     */
    public void associationByyqmb(String classId, String mid) {
        //先检查该班级是否已经有毕业要求模板了
        ClassByyqmbVo cbv = cslgClassMapper.findClassByyqmbByClassId(classId);
        if(cbv == null) {
            //还没有毕业要求模板，则插入数据到class_byyqmb表
            cslgClassMapper.insertClassByyqmb(classId, mid);
        }
    }

    /**
     * 解除班级关联指标点权重矩阵
     */
    public void delAssociationByyqmb(String classId, String mid) {
        cslgClassMapper.deleteClassByyqmb(classId, mid);
    }

}
