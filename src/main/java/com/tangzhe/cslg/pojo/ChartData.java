package com.tangzhe.cslg.pojo;

/**
 * 用于学生达成度图像数据
 */
public class ChartData {

    //能力值图属性
    private String data;
    private double litres;

    //折线图属性
    private String pointName;
    private double reach;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getLitres() {
        return litres;
    }

    public void setLitres(double litres) {
        this.litres = litres;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public double getReach() {
        return reach;
    }

    public void setReach(double reach) {
        this.reach = reach;
    }

}
