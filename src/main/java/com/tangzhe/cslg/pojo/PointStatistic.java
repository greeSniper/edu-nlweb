package com.tangzhe.cslg.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于统计课程达成度
 */
public class PointStatistic {

    private String pointId;
    private String pointName;
    private String desc;
    private List<CourseStatistic> courseList = new ArrayList<>();

    private String value;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getPointName() {
        return "指标点"+pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<CourseStatistic> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseStatistic> courseList) {
        this.courseList = courseList;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
