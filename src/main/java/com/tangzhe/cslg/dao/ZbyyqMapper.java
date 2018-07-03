package com.tangzhe.cslg.dao;

import com.tangzhe.cslg.entity.Byyqmb;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ZbyyqMapper {
    int deleteByPrimaryKey(String id);

    int insert(Zbyyq record);

    int insertSelective(Zbyyq record);

    Zbyyq selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Zbyyq record);

    int updateByPrimaryKey(Zbyyq record);

    int findByCount();

    List<Zbyyq> findByPage(@Param("startRow") int startRow, @Param("pageSize") int pageSize);

    List<FbyyqVo> findFbyyqList();

    FbyyqVo findFbyyqByFid(String fid);

    List<ByyqVo> findByyqVoGroupByFid();

    List<PointVo> findZbyyqListByFid(String id);

    void insertToCourseByyq(@Param("courseId") String courseId,
                            @Param("zbyyqId") String zbyyqId,
                            @Param("mid") String mid,
                            @Param("quanzhong") double quanzhong);

    List<QuanzhongVo> findQzListByMidAndCourseId(@Param("mid") String mid,
                                                 @Param("courseId") String courseId);

    void deleteCourseByyqByMid(String mid);

    List<Zbyyq> findZbbyqByCourseId(String courseId);

    List<Zbyyq> findAllZbyyqList();

    List<FbyyqVo> findAllFbyyq();

    List<PointStatistic> findPointStatisticListByFbyyqId(String fid);

    double findQuanzhongByMidAndZbyyqIdAndCourseId(@Param("mid") String mid,
                                                   @Param("pointId") String pointId,
                                                   @Param("courseId") String courseId);

    int findByyqByCount();

    List<FbyyqVo> findByyqByPage(@Param("startRow") int startRow,
                               @Param("pageSize") int pageSize);

    FbyyqVo findFbyyqById(String id);
}