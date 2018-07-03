package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Student;

/**
 * 学生VO类
 */
public class StudentVo extends Student {

    private String departName;

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

}
