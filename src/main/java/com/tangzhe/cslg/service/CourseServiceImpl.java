package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.ClassCourseTeacherMapper;
import com.tangzhe.cslg.dao.CourseMapper;
import com.tangzhe.cslg.entity.ClassCourseTeacher;
import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.pojo.ByyqPointVo;
import com.tangzhe.cslg.pojo.CourseStatistic;
import com.tangzhe.cslg.pojo.CourseVo;
import com.tangzhe.cslg.pojo.PointVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程 Service
 */
@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassCourseTeacherMapper classCourseTeacherMapper;

    /**
     * 新增课程
     */
    public void add(Course course) {
        course.setId(UUIDUtils.getId());
        courseMapper.insert(course);
    }

    /**
     * 分页查询课程
     */
    public void pageQuery(PageBean pageBean) {
        int count = courseMapper.findByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<CourseVo> list = courseMapper.findByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    /**
     * 修改课程
     */
    public void edit(Course course) {
        Course c = courseMapper.selectByPrimaryKey(course.getId());
        c.setCourseCode(course.getCourseCode());
        c.setCourseDesc(course.getCourseDesc());
        c.setCourseEnglishname(course.getCourseEnglishname());
        c.setCourseName(course.getCourseName());

        courseMapper.updateByPrimaryKeySelective(c);
    }

    /**
     * 批量删除课程
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            courseMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 课程关联指标点
     */
    public void associate(String courseId, String[] pointIds, String majorId) {
        //删除指标点课程关系表中该专业该课程的所有指标点
        //courseMapper.deletePointCourseByMajorIdAndCourseId(majorId, courseId);

        if(pointIds!=null && pointIds.length>0) {
            //遍历pointIds插入指标点课程关系记录
            for(String pointId : pointIds) {
                courseMapper.insertPointCourse(pointId, courseId);
            }
        }
    }

    /**
     * 查询该班级所在院系的所有课程
     */
    public void findPageCourseByDepartId(PageBean pageBean,
                                         CslgClass cslgClass,
                                         String courseName, String teacherName) {
        //获取专业
        String majorId = cslgClass.getMajorId();

        //查询总记录数
        int count = courseMapper.findCountByMajorId(majorId, courseName, teacherName);
        //设置记录数
        pageBean.setTotal(count);

        List<CourseVo> list = new ArrayList<>();
        //若记录数大于0,则查询记录
        if(count > 0) {
            //当前页
            int currentPage = pageBean.getCurrentPage();
            //每页记录数
            int pageSize = pageBean.getPageSize();

            //分页查询记录
            list = courseMapper.findPageByMajorId((currentPage-1)*pageSize, pageSize, majorId, courseName, teacherName);

            //查询是否已经选过该课，选过则设置CourseVo的isSelect为1
            for(CourseVo cv : list) {
                String classId = cslgClass.getId();
                String courseId = cv.getId();
                String teacherId = cv.getTeacherId();
                ClassCourseTeacher cct = classCourseTeacherMapper.selectByClassIdAndCourseIdAndTeacherId(classId, courseId, teacherId);
                if(cct != null) {
                    cv.setIsSelect(1);
                }
            }
        }
        //设置记录
        pageBean.setRows(list);
    }

    /**
     * 通过教师id查询课程
     */
    public List<Course> findCourseByTeacherId(String teacherId) {
        return courseMapper.findCourseByTeacherId(teacherId);
    }

    /**
     * 通过课程id分页查询指标点
     */
    public void pagePointQuery(PageBean pageBean, String id) {
        int count = courseMapper.findPointCountByCourseId(id);
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<PointVo> list = courseMapper.findPointPageByCourseId((currentPage-1)*pageSize, pageSize, id);
            pageBean.setRows(list);
        }
    }

    /**
     * 批量舍弃当前课程关联的指标点
     */
    public void deletePointBatch(String ids, String id) {
        String[] pointIds = ids.split(",");
        for(String pointId : pointIds) {
            courseMapper.deletePointCourse(pointId, id);
        }
    }

    /**
     * 通过id查询课程
     */
    public Course findCourseById(String courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    public List<Course> findCourseListByClassIdAndTeacherId(String classId, String id) {
        return courseMapper.findCourseListByClassIdAndTeacherId(classId, id);
    }

    public List<Course> findCourseListByClassId(String classId) {
        return courseMapper.findCourseListByClassId(classId);
    }

    /**
     * 通过课程id和mid查询毕业要求指标点列表
     */
    public List<ByyqPointVo> findZbyyqListByCourseIdAndMid(String courseId, String mid) {
        return courseMapper.findZbyyqListByCourseIdAndMid(courseId, mid);
    }

    /**
     * 通过 毕业要求模板id 和 毕业要求指标点id 查询课程列表
     */
    public List<CourseStatistic> findCourseListByMidAndZbyyqId(String mid, String pointId) {
        return courseMapper.findCourseListByMidAndZbyyqId(mid, pointId);
    }

    /**
     * 通过毕业要求模板id和父毕业要求id查询课程
     */
    public List<CourseStatistic> findCourseListByMidAndFbyyqId(String mid, String fid) {
        return courseMapper.findCourseListByMidAndFbyyqId(mid, fid);
    }

    public List<Course> findAllCourse() {
        return courseMapper.findAllCourse();
    }

}
