package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Exp;
import com.tangzhe.cslg.pojo.ExpVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpMapper {
    int deleteByPrimaryKey(String id);

    int insert(Exp record);

    int insertSelective(Exp record);

    Exp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Exp record);

    int updateByPrimaryKey(Exp record);

    int findCountByTeacherId(String teacherId);

    List<ExpVo> findPageByTeacherId(@Param("startRow") int startRow,
                                    @Param("pageSize") int pageSize,
                                    @Param("teacherId") String teacherId);

    List<Exp> findExpListByPointId(String pointId);

    Exp findExpByCourseIdAndExpName(@Param("courseId") String courseId,
                                    @Param("expName") String expName);

    List<Exp> findExpListByMidAndPointId(@Param("courseId") String courseId,
                                         @Param("mid") String mid,
                                         @Param("pointId") String pointId);

    List<Exp> findExpListByCourseIdAndMidAndPointIdAndTeacherId(@Param("courseId") String courseId,
                                                                @Param("mid") String mid,
                                                                @Param("pointId") String pointId,
                                                                @Param("teacherId") String teacherId);
}