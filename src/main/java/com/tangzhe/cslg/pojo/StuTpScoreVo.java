package com.tangzhe.cslg.pojo;

/**
 * 学生试卷题目分数VO类
 */
public class StuTpScoreVo {

    private String stuId;
    private String tpId;
    private String score;
    private String stuName;

    public StuTpScoreVo() {}

    public StuTpScoreVo(String stuId, String tpId, String score, String stuName) {
        this.stuId = stuId;
        this.tpId = tpId;
        this.score = score;
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

}
