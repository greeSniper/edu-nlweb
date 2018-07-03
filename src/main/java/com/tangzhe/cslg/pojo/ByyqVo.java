package com.tangzhe.cslg.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 毕业要求VO类
 */
public class ByyqVo {

    private String id;
    private String name;
    private int size;
    private List<PointVo> point = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<PointVo> getPoint() {
        return point;
    }

    public void setPoint(List<PointVo> point) {
        this.point = point;
    }

}
