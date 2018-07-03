package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface MajorService {
    void add(Major major);

    void pageQuery(PageBean pageBean);

    void edit(QueryVo queryVo);

    void deleteBatch(String ids);

    List<Major> findAll();

    Major findByName(String majorName);

    List<Major> findMajorByDepartId(String departId);
}
