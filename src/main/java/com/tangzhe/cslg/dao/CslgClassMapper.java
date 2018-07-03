package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.pojo.ClassByyqmbVo;
import com.tangzhe.cslg.pojo.QueryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CslgClassMapper {
    int deleteByPrimaryKey(String id);

    int insert(CslgClass record);

    int insertSelective(CslgClass record);

    CslgClass selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CslgClass record);

    int updateByPrimaryKey(CslgClass record);

    int findByCount(QueryVo queryVo);

    List<QueryVo> findByPage(QueryVo queryVo);

    CslgClass findByName(String className);

    List<CslgClass> findClassListByTeacherId(String teacherId);

    ClassByyqmbVo findClassByyqmbByClassId(String classId);

    void insertClassByyqmb(@Param("classId") String classId, @Param("mid") String mid);

    void deleteClassByyqmb(@Param("classId") String classId, @Param("mid") String mid);

    List<CslgClass> findAll();
}