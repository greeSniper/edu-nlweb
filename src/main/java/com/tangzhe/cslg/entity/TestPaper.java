package com.tangzhe.cslg.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 试卷
 */
public class TestPaper {
    private String id;

    private String courseId;

    private String majorId;

    private String courseName;

    private String tpName;

    private Double tpScore = 100.0;

    private String teacherId;

    private Date createTime;

    private Integer state = 0; //0表示使用，1表示作废

    private Date updateTime;

    private String majorName;

    private String teacherName;

    private String mid;

    /**
     * 用于页面展示出卷时间
     */
    public String getCreateTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd").format(this.createTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId == null ? null : majorId.trim();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName == null ? null : courseName.trim();
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName == null ? null : tpName.trim();
    }

    public Double getTpScore() {
        return tpScore;
    }

    public void setTpScore(Double tpScore) {
        this.tpScore = tpScore;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId == null ? null : teacherId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName == null ? null : teacherName.trim();
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

}