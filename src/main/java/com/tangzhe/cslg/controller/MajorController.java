package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Department;
import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.service.DepartmentService;
import com.tangzhe.cslg.service.MajorService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 专业 Controller
 */
@Controller
@RequestMapping("major")
public class MajorController {

    private static final String LIST = "sysadmin/major";

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MajorService majorService;

    /**
     * 查询所有院系
     */
    @RequestMapping("/departmentList")
    @ResponseBody
    public List<Department> departmentList() {
        List<Department> list = departmentService.findAll();
        return list;
    }

    /**
     * 新增专业
     */
    @RequestMapping("/add")
    public String add(Major major) {
        majorService.add(major);
        return LIST;
    }

    /**
     * 分页查询专业
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        majorService.pageQuery(pageBean);
        return pageBean;
    }

    /**
     * 修改专业
     */
    @RequestMapping("/edit")
    public String edit(QueryVo queryVo) {
        majorService.edit(queryVo);
        return LIST;
    }

    /**
     * 批量删除专业
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        majorService.deleteBatch(ids);
        return LIST;
    }

}
