package com.tangzhe.cslg.pojo;

import com.tangzhe.cslg.entity.Question;

/**
 * 题目VO类
 */
public class QuestionVo extends Question {

    private String tpName;

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }
}
