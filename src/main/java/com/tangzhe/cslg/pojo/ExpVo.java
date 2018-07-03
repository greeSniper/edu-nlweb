package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Exp;

/**
 * 实验VO类
 */
public class ExpVo extends Exp {

    private String courseName;
    private String majorName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

}
