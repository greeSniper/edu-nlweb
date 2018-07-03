package com.tangzhe.cslg.pojo;

/**
 * 学生实验关系VO类
 */
public class StuExpVo {

    private String stuId;
    private String expId;
    private Double score;
    private Double quanzhong;

    private String otherId;

    public StuExpVo() {}

    public StuExpVo(String stuId, String expId, Double score, Double quanzhong) {
        this.stuId = stuId;
        this.expId = expId;
        this.score = score;
        this.quanzhong = quanzhong;
    }

    public StuExpVo(String stuId, Double score, Double quanzhong, String otherId) {
        this.stuId = stuId;
        this.score = score;
        this.quanzhong = quanzhong;
        this.otherId = otherId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getQuanzhong() {
        return quanzhong;
    }

    public void setQuanzhong(Double quanzhong) {
        this.quanzhong = quanzhong;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

}
