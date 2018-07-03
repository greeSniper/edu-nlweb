package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Exp;
import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.service.ByyqService;
import com.tangzhe.cslg.service.ExpService;
import com.tangzhe.cslg.service.PointService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 实验 Controller
 */
@Controller
@RequestMapping("exp")
public class ExpController {

    private static final String LIST = "pre/exp";

    @Autowired
    private ExpService expService;
    @Autowired
    private PointService pointService;
    @Autowired
    private ByyqService byyqService;

    /**
     * 添加实验
     */
    @RequestMapping("/add")
    public String add(Exp exp, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        exp.setTeacherId(teacher.getId());
        exp.setTeacherName(teacher.getTeacherName());
        expService.add(exp);
        return LIST;
    }

    /**
     * 添加实验重写
     */
    @RequestMapping("/add2")
    public String add2(Exp exp, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        exp.setTeacherId(teacher.getId());
        exp.setTeacherName(teacher.getTeacherName());
        expService.add2(exp);
        return LIST;
    }

    /**
     * 批量删除实验
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        expService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 批量删除实验重写
     */
    @RequestMapping("/deleteBatch2")
    public String deleteBatch2(String ids) {
        expService.deleteBatch2(ids);
        return LIST;
    }

    /**
     * 通过课程id查询指标点
     */
    @RequestMapping("/findPointByCourseId")
    @ResponseBody
    public List<Point> findPointByCourseId(String courseId) {
        List<Point> list = pointService.findPointByCourseId(courseId);
        return list;
    }

    /**
     * 通过课程id查询毕业要求指标点
     */
    @RequestMapping("/findZbbyqByCourseId")
    @ResponseBody
    public List<Zbyyq> findZbbyqByCourseId(String courseId) {
        List<Zbyyq> list = byyqService.findZbbyqByCourseId(courseId);
        return list;
    }

    /**
     * 修改实验
     */
    @RequestMapping("/edit")
    public String edit(Exp exp, String expContent2) {
        exp.setExpContent(expContent2);
        expService.edit(exp);
        return LIST;
    }

    /**
     * 修改实验重写
     */
    @RequestMapping("/edit2")
    public String edit2(Exp exp, String expContent2) {
        exp.setExpContent(expContent2);
        expService.edit2(exp);
        return LIST;
    }

    /**
     * 分页查询实验
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows, HttpSession session) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        //获取实验负责人
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        expService.pageQuery(pageBean, teacher.getId());
        return pageBean;
    }

    /**
     * 实验关联指标点
     */
    @RequestMapping("/associate")
    public String associate(Exp exp, String expContent3) {
        exp.setExpContent(expContent3);
        expService.edit(exp);
        return LIST;
    }

    /**
     * 实验关联指标点重写
     */
    @RequestMapping("/associate2")
    public String associate2(Exp exp, String expContent3) {
        exp.setExpContent(expContent3);
        expService.edit2(exp);
        return LIST;
    }

}














