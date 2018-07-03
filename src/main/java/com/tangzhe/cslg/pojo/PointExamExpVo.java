package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Exp;
import com.tangzhe.cslg.entity.Other;
import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.entity.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于Excel输出VO类
 */
public class PointExamExpVo extends Point {

    private List<Question> qList = new ArrayList<>();
    private List<Exp> eList = new ArrayList<>();
    private List<Other> oList = new ArrayList<>();

    public List<Question> getqList() {
        return qList;
    }

    public void setqList(List<Question> qList) {
        this.qList = qList;
    }

    public List<Exp> geteList() {
        return eList;
    }

    public void seteList(List<Exp> eList) {
        this.eList = eList;
    }

    public List<Other> getoList() {
        return oList;
    }

    public void setoList(List<Other> oList) {
        this.oList = oList;
    }

}
