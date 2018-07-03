package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.pojo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MajorMapper {
    int deleteByPrimaryKey(String id);

    int insert(Major record);

    int insertSelective(Major record);

    Major selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Major record);

    int updateByPrimaryKey(Major record);

    int findByCount();

    List<QueryVo> findByPage(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    List<Major> findAll();

    Major findByName(String majorName);

    List<Major> findMajorByDepartId(String departId);
}