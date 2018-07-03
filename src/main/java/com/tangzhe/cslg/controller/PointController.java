package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.service.PointService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 指标点 Controller
 */
@Controller
@RequestMapping("point")
public class PointController {

    private static final String LIST = "sysadmin/point";

    @Autowired
    private PointService pointService;

    /**
     * 新增指标点
     */
    @RequestMapping("/add")
    public String add(Point point) {
        pointService.add(point);
        return LIST;
    }

    /**
     * 分页查询指标点
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows, Point point) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        pointService.pageQuery(pageBean, point);
        return pageBean;
    }

    /**
     * 修改指标点
     */
    @RequestMapping("/edit")
    public String edit(Point point) {
        pointService.edit(point);
        return LIST;
    }

    /**
     * 批量删除指标点
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        pointService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 根据专业id查询指标点
     */
    @RequestMapping("/findPointByMajorId")
    @ResponseBody
    public List<Point> findPointByMajorId(String majorId, String courseId) {
        List<Point> list = pointService.findPointByMajorId(majorId, courseId);
        return list;
    }

}
