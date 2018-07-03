package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.Department;
import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.service.DepartmentService;
import com.tangzhe.cslg.service.TeacherService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 教师 Controller
 */
@Controller
@RequestMapping("teacher")
public class TeacherController {

    private static final String LIST = "person/teacher";

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有院系
     */
    @RequestMapping("/departmentList")
    @ResponseBody
    public List<Department> departmentList() {
        return departmentService.findAll();
    }

    /**
     * 添加教师
     */
    @RequestMapping("/add")
    public String add(Teacher teacher) {
        teacherService.add(teacher);
        return LIST;
    }

    /**
     * 分页查询
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        teacherService.pageQuery(pageBean);
        return pageBean;
    }

    /**
     * 修改教师
     */
    @RequestMapping("/edit")
    public String edit(Teacher teacher) {
        teacherService.edit(teacher);
        return LIST;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        teacherService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 设置管理员
     */
    @RequestMapping("/saveBatch")
    public String saveBatch(String ids) {
        teacherService.saveBatch(ids);
        return LIST;
    }

    /**
     * 还原为普通用户
     */
    @RequestMapping("/restoreBatch")
    public String restoreBatch(String ids) {
        teacherService.restoreBatch(ids);
        return LIST;
    }

    /**
     * 查询所有课程出去该教师已经关联过的课程
     */
    @RequestMapping("/findCourseToAssociation")
    @ResponseBody
    public List<Course> findCourseToAssociation(Teacher teacher) {
        List<Course> list = teacherService.findCourseToAssociation(teacher.getId());
        return list;
    }

    /**
     * 通过教师id查询课程
     */
    @RequestMapping("/findCourseByTeacherId")
    @ResponseBody
    public List<Course> findCourseByTeacherId(Teacher teacher) {
        List<Course> list = teacherService.findCourseByTeacherId(teacher.getId());
        return list;
    }

    /**
     * 确定教师关联课程
     */
    @RequestMapping("/assignCourseToTeacher")
    public String assignCourseToTeacher(Teacher teacher, String[] courseIds) {
        teacherService.assignCourseToTeacher(teacher.getId(), courseIds);
        return LIST;
    }

}
