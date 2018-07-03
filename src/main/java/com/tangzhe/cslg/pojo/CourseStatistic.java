package com.tangzhe.cslg.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于统计课程达成度
 */
public class CourseStatistic {

    private String courseId;
    private String courseName;
    private String quanzhong;
    private List<PointStatistic> pointList = new ArrayList<>();

    private double qz;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getQuanzhong() {
        return "（"+String.valueOf(qz*100)+"%）";
    }

    public void setQuanzhong(String quanzhong) {
        this.quanzhong = quanzhong;
    }

    public List<PointStatistic> getPointList() {
        return pointList;
    }

    public void setPointList(List<PointStatistic> pointList) {
        this.pointList = pointList;
    }

    public double getQz() {
        return qz;
    }

    public void setQz(double qz) {
        this.qz = qz;
    }

}
