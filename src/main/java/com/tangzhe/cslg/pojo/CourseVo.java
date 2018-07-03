package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程VO类
 */
public class CourseVo extends Course {

    private String teacherId;
    private String teacherName;
    private String departName;
    private Integer isSelect = 0; //0表示未选，1表示已选

    private List<QuanzhongVo> qzList = new ArrayList<>();

    public String getCourseIdAndTeacherId() {
        return this.getId()+"_"+this.getTeacherId();
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public List<QuanzhongVo> getQzList() {
        return qzList;
    }

    public void setQzList(List<QuanzhongVo> qzList) {
        this.qzList = qzList;
    }

}
