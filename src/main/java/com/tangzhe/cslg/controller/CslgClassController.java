package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.service.CslgClassService;
import com.tangzhe.cslg.service.MajorService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 班级 Controller
 */
@Controller
@RequestMapping("cslgClass")
public class CslgClassController {

    private static final String LIST = "sysadmin/class";
    private static final String COURSE_LIST = "sysadmin/courselist";
    private static final String CLASS_COURSE_HAS_SELECTED_LIST = "sysadmin/classCourseHasSelected";

    @Autowired
    private MajorService majorService;
    @Autowired
    private CslgClassService cslgClassService;

    /**
     * 查询所有专业
     */
    @RequestMapping("/majorList")
    @ResponseBody
    public List<Major> majorList() {
        List<Major> list = majorService.findAll();
        return list;
    }

    /**
     * 新增班级
     */
    @RequestMapping("/add")
    public String add(CslgClass cslgClass) {
        cslgClassService.add(cslgClass);
        return LIST;
    }

    /**
     * 分页查询班级
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows, QueryVo queryVo) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        cslgClassService.pageQuery(pageBean, queryVo);
        return pageBean;
    }

    /**
     * 修改班级
     */
    @RequestMapping("/edit")
    public String edit(CslgClass cslgClass) {
        cslgClassService.edit(cslgClass);
        return LIST;
    }

    /**
     * 批量删除
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        cslgClassService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 点击班级选课按钮，进入选课页面
     */
    @RequestMapping("/selectCourse")
    public String selectCourse(CslgClass cslgClass, HttpServletRequest request) {
        request.setAttribute("cslgClass", cslgClass);
        return COURSE_LIST;
    }

    /**
     * courselist.jsp中点击选课按钮，为班级批量选课
     */
    @RequestMapping("/classSelectCourseBatch")
    public String classSelectCourseBatch(String ids, String classId) {
        //ids：课程id_教师id,课程id_教师id,课程id_教师id
        //classId：班级id
        cslgClassService.classSelectCourseBatch(ids, classId);
        return LIST;
    }

    /**
     * courselist.jsp中点击取消选课按钮发送的请求，为班级批量取消选课
     */
    @RequestMapping("/classUndoSelectCourseBatch")
    public String classUndoSelectCourseBatch(String ids, String classId) {
        //ids：课程id_教师id,课程id_教师id,课程id_教师id
        //classId：班级id
        cslgClassService.classUndoSelectCourseBatch(ids, classId);
        return LIST;
    }

    /**
     * class.jsp中点击已选课程按钮发送的请求，进入班级已选课程页面
     */
    @RequestMapping("/toClassCourseHasSelectedView")
    public String toClassCourseHasSelectedView(HttpServletRequest request, CslgClass cslgClass) {
        CslgClass cc = cslgClassService.findById(cslgClass.getId());
        request.setAttribute("cc", cc);
        return CLASS_COURSE_HAS_SELECTED_LIST;
    }

    /**
     * classCourseHasSelected.jsp中/cslgClass/findCourseHasSelectedByClassId?id="+classId请求
     * 分页查询班级已选课程
     */
    @RequestMapping("/findCourseHasSelectedByClassId")
    @ResponseBody
    public PageBean findCourseHasSelectedByClassId(Integer page, Integer rows, CslgClass cslgClass) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        cslgClassService.findCourseHasSelectedByClassId(pageBean, cslgClass);
        return pageBean;
    }

    /**
     * 班级关联指标点权重矩阵
     */
    @RequestMapping("/associationByyqmb")
    public String associationByyqmb(String classId, String mid) {
        cslgClassService.associationByyqmb(classId, mid);
        return LIST;
    }

    /**
     * 解除班级关联指标点权重矩阵
     */
    @RequestMapping("/delAssociationByyqmb")
    public String delAssociationByyqmb(String classId, String mid) {
        cslgClassService.delAssociationByyqmb(classId, mid);
        return LIST;
    }

}
