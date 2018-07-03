package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Byyqmb;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.ByyqmbVo;
import com.tangzhe.cslg.pojo.FbyyqVo;
import com.tangzhe.cslg.pojo.PointStatistic;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface ByyqService {
    void pageQuery(PageBean pageBean);

    List<FbyyqVo> fbyyqList();

    void add(Zbyyq zbyyq);

    void edit(Zbyyq zbyyq);

    void deleteBatch(String ids);

    ByyqmbVo getAddByyqmbData();

    void saveByyqmb(String courseIdPointIdQuanzhong, String byyqmbName);

    void pageByyqmbQuery(PageBean pageBean);

    Byyqmb findByyqmbById(String id);

    ByyqmbVo getEditByyqmbData(String mid);

    void editByyqmb(String courseIdPointIdQuanzhong, String byyqmbName, String mid);

    void deleteByyqmbBatch(String ids);

    List<Byyqmb> findAllByyqmb();

    Byyqmb findByyqmbByClassId(String classId);

    List<Zbyyq> findZbbyqByCourseId(String courseId);

    List<Zbyyq> findAllZbyyqList();

    List<FbyyqVo> findAllFbyyq();

    List<PointStatistic> findPointStatisticListByFbyyqId(String fid);

    double findQuanzhongByMidAndZbyyqIdAndCourseId(String mid, String pointId, String courseId);

    void findByyqByPage(PageBean pageBean);

    FbyyqVo findFbyyqById(String id);
}
