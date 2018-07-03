package com.tangzhe.cslg.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 毕业要求模板
 */
public class Byyqmb {
    private String id;

    private String name;

    private Date createTime;

    public String getCreateTimeFormat() {
        return new SimpleDateFormat("yyyy-MM-dd").format(createTime);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}