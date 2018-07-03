package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.pojo.PointVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PointMapper {
    int deleteByPrimaryKey(String id);

    int insert(Point record);

    int insertSelective(Point record);

    Point selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Point record);

    int updateByPrimaryKey(Point record);

    int findByCount(@Param("majorId") String majorId, @Param("pointName") String pointName);

    List<PointVo> findByPage(@Param("startRow") int startRow,
                             @Param("pageSize") int pageSize,
                             @Param("majorId") String majorId,
                             @Param("pointName") String pointName);

    List<Point> findPointByMajorId(@Param("majorId") String majorId, @Param("courseId") String courseId);

    List<Point> findPointByTestPaperId(String id);

    List<Point> findPointByCourseId(String courseId);

    List<Point> findPointListByMajorIdAndCourseId(@Param("majorId") String majorId,
                                                  @Param("courseId") String courseId);
}