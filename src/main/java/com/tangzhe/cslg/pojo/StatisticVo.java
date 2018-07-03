package com.tangzhe.cslg.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程达成度统计VO类
 */
public class StatisticVo {

    private String name; //毕业要求1

    private List<PointStatistic> pointList = new ArrayList<>(); //子毕业要求指标点列表

    private List<CourseStatistic> courseList = new ArrayList<>(); //课程列表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PointStatistic> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointStatistic> pointList) {
        this.pointList = pointList;
    }

    public List<CourseStatistic> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseStatistic> courseList) {
        this.courseList = courseList;
    }

}
