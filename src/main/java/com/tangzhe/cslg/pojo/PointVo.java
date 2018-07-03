package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Point;

/**
 * 指标点VO类
 */
public class PointVo extends Point {

    private String majorName;
    private String courseName;

    private String name;
    private String fid;
    private String fname;
    private String byyqDesc;
    private int orderNo;

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getByyqDesc() {
        return byyqDesc;
    }

    public void setByyqDesc(String byyqDesc) {
        this.byyqDesc = byyqDesc;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

}
