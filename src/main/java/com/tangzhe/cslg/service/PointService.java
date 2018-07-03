package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface PointService {
    void add(Point point);

    void pageQuery(PageBean pageBean, Point point);

    void edit(Point point);

    void deleteBatch(String ids);

    List<Point> findPointByMajorId(String majorId, String courseId);

    List<Point> findPointByTestPaperId(String id);

    List<Point> findPointByCourseId(String courseId);

    List<Point> findPointListByMajorIdAndCourseId(String majorId, String courseId);
}
