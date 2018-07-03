package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.service.CourseService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 课程 Controller
 */
@Controller
@RequestMapping("course")
public class CourseController {

    private static final String LIST = "sysadmin/course";
    private static final String POINT_VIEW = "sysadmin/coursepointview";

    @Autowired
    private CourseService courseService;

    /**
     * 新增课程
     */
    @RequestMapping("/add")
    public String add(Course course) {
        courseService.add(course);
        return LIST;
    }

    /**
     * 分页查询课程
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        courseService.pageQuery(pageBean);
        return pageBean;
    }

    /**
     * 修改课程
     */
    @RequestMapping("/edit")
    public String edit(Course course) {
        courseService.edit(course);
        return LIST;
    }

    /**
     * 批量删除课程
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        courseService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 课程关联指标点
     */
    @RequestMapping("/associate")
    public String associate(String courseId, String[] pointIds, String majorId) {
        courseService.associate(courseId, pointIds, majorId);
        return LIST;
    }

    /**
     * 查询该班级所在院系的所有课程
     */
    @RequestMapping("/findCourseByDepartId")
    @ResponseBody
    public PageBean findCourseByDepartId(CslgClass cslgClass,
                                         Integer page, Integer rows,
                                         String courseName, String teacherName) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        courseService.findPageCourseByDepartId(pageBean, cslgClass, courseName, teacherName);
        return pageBean;
    }

    /**
     * 进入所选中课程的指标点列表页面
     */
    @RequestMapping("/toCoursePointView")
    public String toCoursePointView(String id, HttpServletRequest request) {
        request.setAttribute("courseId", id);
        return POINT_VIEW;
    }

    /**
     * 通过课程id分页查询指标点
     */
    @RequestMapping("/pagePointQuery")
    @ResponseBody
    public PageBean pagePointQuery(Integer page, Integer rows, String id) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        courseService.pagePointQuery(pageBean, id);
        return pageBean;
    }

    /**
     * 批量舍弃当前课程关联的指标点
     */
    @RequestMapping("/deletePointBatch")
    public String deletePointBatch(String ids, String id, HttpServletRequest request) {
        courseService.deletePointBatch(ids, id);
        request.setAttribute("courseId", id);
        return POINT_VIEW;
    }

}
