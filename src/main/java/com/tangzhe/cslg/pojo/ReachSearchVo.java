package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.utils.ArithUtils;

/**
 * Created by 唐哲
 * 2017-11-26 16:54
 */
public class ReachSearchVo extends Student {

    private String pointId;
    private Double reach;
    private String stuId;
    private String pointName;
    private String courseId;

    public double getReachFormat() {
        return ArithUtils.myDecimal(reach);
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Double getReach() {
        return reach;
    }

    public void setReach(Double reach) {
        this.reach = reach;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

}
