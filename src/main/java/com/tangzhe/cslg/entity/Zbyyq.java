package com.tangzhe.cslg.entity;

/**
 * 子毕业要求
 */
public class Zbyyq {
    private String id;

    private String fid;

    private String fname;

    private String name;

    private String byyqDesc;

    public String getPname() {
        return "指标点" + name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid == null ? null : fid.trim();
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getByyqDesc() {
        return byyqDesc;
    }

    public void setByyqDesc(String byyqDesc) {
        this.byyqDesc = byyqDesc == null ? null : byyqDesc.trim();
    }
}