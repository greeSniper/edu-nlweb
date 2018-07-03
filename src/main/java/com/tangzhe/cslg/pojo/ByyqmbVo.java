package com.tangzhe.cslg.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 毕业要求模板页面所需数据VO类
 */
public class ByyqmbVo {

    private List<ByyqVo> byyqVo = new ArrayList<>();
    private List<CourseVo> courseVo = new ArrayList<>();

    public List<ByyqVo> getByyqVo() {
        return byyqVo;
    }

    public void setByyqVo(List<ByyqVo> byyqVo) {
        this.byyqVo = byyqVo;
    }

    public List<CourseVo> getCourseVo() {
        return courseVo;
    }

    public void setCourseVo(List<CourseVo> courseVo) {
        this.courseVo = courseVo;
    }

}
