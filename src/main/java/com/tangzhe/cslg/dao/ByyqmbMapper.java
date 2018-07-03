package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Byyqmb;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ByyqmbMapper {
    int deleteByPrimaryKey(String id);

    int insert(Byyqmb record);

    int insertSelective(Byyqmb record);

    Byyqmb selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Byyqmb record);

    int updateByPrimaryKey(Byyqmb record);

    int findByyqmbByCount();

    List<Byyqmb> findByyqmbByPage(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    List<Byyqmb> findAllByyqmb();

    Byyqmb findByyqmbByClassId(String classId);
}