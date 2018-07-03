package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.PointMapper;
import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.pojo.PointVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 指标点 Service
 */
@Service
@Transactional
public class PointServiceImpl implements PointService {

    @Autowired
    private PointMapper pointMapper;

    /**
     * 新增指标点
     */
    public void add(Point point) {
        point.setId(UUIDUtils.getId());
        pointMapper.insertSelective(point);
    }

    /**
     * 分页查询指标点
     */
    public void pageQuery(PageBean pageBean, Point point) {
        int count = pointMapper.findByCount(point.getMajorId(), point.getPointName());
        pageBean.setTotal(count);

        List<PointVo> list = new ArrayList<>();
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            list = pointMapper.findByPage((currentPage-1)*pageSize, pageSize, point.getMajorId(), point.getPointName());
        }
        pageBean.setRows(list);
    }

    /**
     * 修改指标点
     */
    public void edit(Point point) {
        Point p = pointMapper.selectByPrimaryKey(point.getId());
        p.setMajorId(point.getMajorId());
        p.setPointName(point.getPointName());
        p.setPointDesc(point.getPointDesc());

        pointMapper.updateByPrimaryKeySelective(p);
    }

    /**
     * 删除指标点
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            pointMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 根据专业id查询还未关联到该专业该课程的指标点
     */
    public List<Point> findPointByMajorId(String majorId, String courseId) {
        return pointMapper.findPointByMajorId(majorId, courseId);
    }

    /**
     * 根据试卷id查询指标点
     */
    public List<Point> findPointByTestPaperId(String id) {
        return pointMapper.findPointByTestPaperId(id);
    }

    /**
     * 通过课程id查询指标点
     */
    public List<Point> findPointByCourseId(String courseId) {
        return pointMapper.findPointByCourseId(courseId);
    }

    /**
     * 通过专业id和课程id查询指标点
     */
    public List<Point> findPointListByMajorIdAndCourseId(String majorId, String courseId) {
        return pointMapper.findPointListByMajorIdAndCourseId(majorId, courseId);
    }

}
