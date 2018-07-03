package com.tangzhe.cslg.entity;

import java.util.Date;

/**
 * 学生
 */
public class Student {
    private String id;

    private String classId;

    private String stuCode;

    private String stuName;

    private String gender;

    private String className;

    private String grade;

    private String majorId;

    private String majorName;

    private String idCard;

    private String telephone;

    private String address;

    private String enrollment;

    private Integer atSchool = 0; //0表示在校，1表示不在校

    private Date birthday;

    private String email;

    private Integer commendCount = 0; //默认为0

    public Student() {}

    public Student(String id, String classId, String stuCode, String stuName, String gender, String className, String grade, String majorId, String majorName, String idCard, String telephone, String address, String enrollment, Date birthday, String email) {
        this.id = id;
        this.classId = classId;
        this.stuCode = stuCode;
        this.stuName = stuName;
        this.gender = gender;
        this.className = className;
        this.grade = grade;
        this.majorId = majorId;
        this.majorName = majorName;
        this.idCard = idCard;
        this.telephone = telephone;
        this.address = address;
        this.enrollment = enrollment;
        this.birthday = birthday;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId == null ? null : classId.trim();
    }

    public String getStuCode() {
        return stuCode;
    }

    public void setStuCode(String stuCode) {
        this.stuCode = stuCode == null ? null : stuCode.trim();
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName == null ? null : stuName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId == null ? null : majorId.trim();
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(String enrollment) {
        this.enrollment = enrollment == null ? null : enrollment.trim();
    }

    public Integer getAtSchool() {
        return atSchool;
    }

    public void setAtSchool(Integer atSchool) {
        this.atSchool = atSchool;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getCommendCount() {
        return commendCount;
    }

    public void setCommendCount(Integer commendCount) {
        this.commendCount = commendCount;
    }
}