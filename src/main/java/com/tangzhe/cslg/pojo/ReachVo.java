package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.utils.ArithUtils;

/**
 * 达成度VO类
 */
public class ReachVo {

    private String stuId;
    private String pointId;
    private String courseId;
    private double reach;
    private double quanzhong;

    private String id;
    private String stuName;
    private String courseName;
    private String pointName;
    private String className;
    private String majorName;

    public double getReachFormat() {
        return ArithUtils.myDecimal(reach);
    }

    public ReachVo() {}

    public ReachVo(String stuId, String pointId, String courseId, double reach) {
        this.stuId = stuId;
        this.pointId = pointId;
        this.courseId = courseId;
        this.reach = reach;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public double getQuanzhong() {
        return quanzhong;
    }

    public void setQuanzhong(double quanzhong) {
        this.quanzhong = quanzhong;
    }

}
