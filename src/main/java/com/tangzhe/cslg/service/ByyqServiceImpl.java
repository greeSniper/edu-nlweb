package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.ByyqmbMapper;
import com.tangzhe.cslg.dao.CourseMapper;
import com.tangzhe.cslg.dao.ZbyyqMapper;
import com.tangzhe.cslg.entity.Byyqmb;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 毕业要求 Service
 */
@Service
@Transactional
public class ByyqServiceImpl implements ByyqService {

    @Autowired
    private ZbyyqMapper zbyyqMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ByyqmbMapper byyqmbMapper;

    public void pageQuery(PageBean pageBean) {
        int count = zbyyqMapper.findByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<Zbyyq> list = zbyyqMapper.findByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    public List<FbyyqVo> fbyyqList() {
        return zbyyqMapper.findFbyyqList();
    }

    public void add(Zbyyq zbyyq) {
        zbyyq.setId(UUIDUtils.getId());
        FbyyqVo fv = zbyyqMapper.findFbyyqByFid(zbyyq.getFid());
        zbyyq.setFname(fv.getFname());
        zbyyqMapper.insertSelective(zbyyq);
    }

    public void edit(Zbyyq zbyyq) {
        zbyyqMapper.updateByPrimaryKeySelective(zbyyq);
    }

    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            zbyyqMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 获取添加毕业要求模板页面所需数据
     */
    public ByyqmbVo getAddByyqmbData() {
        //封装byyqVo
        List<ByyqVo> byyqVo = zbyyqMapper.findByyqVoGroupByFid();
        for(ByyqVo bv : byyqVo) {
            List<PointVo> point = zbyyqMapper.findZbyyqListByFid(bv.getId());
            bv.setPoint(point);
        }

        //封装courseVo
        List<CourseVo> courseVo = courseMapper.findCourseVoList();

        //封装byyqmbVo
        ByyqmbVo byyqmbVo = new ByyqmbVo();
        byyqmbVo.setByyqVo(byyqVo);
        byyqmbVo.setCourseVo(courseVo);

        return byyqmbVo;
    }

    /**
     * 保存毕业要求模板数据
     */
    public void saveByyqmb(String courseIdPointIdQuanzhong,
                           String byyqmbName) {
        //新增毕业要求模板
        Byyqmb byyqmb = new Byyqmb();
        String mid = UUIDUtils.getId();
        byyqmb.setId(mid);
        byyqmb.setName(byyqmbName);
        byyqmb.setCreateTime(new Date());
        byyqmbMapper.insert(byyqmb);

        //切割courseIdPointIdQuanzhong获取课程id，指标点id，权重
        String[] ids = courseIdPointIdQuanzhong.split(",");
        for(String id : ids) {
            String courseId = id.split("_")[0];
            String zbyyqId = id.split("_")[1];
            String quanzhongStr = id.split("_")[2];
            double quanzhong = Double.parseDouble(quanzhongStr);
            //插入数据到course_byyq表中
            zbyyqMapper.insertToCourseByyq(courseId, zbyyqId, mid, quanzhong);
        }
    }

    public void pageByyqmbQuery(PageBean pageBean) {
        int count = byyqmbMapper.findByyqmbByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<Byyqmb> list = byyqmbMapper.findByyqmbByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    public Byyqmb findByyqmbById(String id) {
        return byyqmbMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取毕业要求模板编辑页面数据
     */
    public ByyqmbVo getEditByyqmbData(String mid) {
        //封装byyqVo
        List<ByyqVo> byyqVo = zbyyqMapper.findByyqVoGroupByFid();
        for(ByyqVo bv : byyqVo) {
            List<PointVo> point = zbyyqMapper.findZbyyqListByFid(bv.getId());
            bv.setPoint(point);
        }

        //封装courseVo
        List<CourseVo> courseVo = courseMapper.findCourseVoListByMid(mid);
        for(CourseVo cv : courseVo) {
            List<QuanzhongVo> qzList = zbyyqMapper.findQzListByMidAndCourseId(mid, cv.getId());
            cv.setQzList(qzList);
        }

        //封装byyqmbVo
        ByyqmbVo byyqmbVo = new ByyqmbVo();
        byyqmbVo.setByyqVo(byyqVo);
        byyqmbVo.setCourseVo(courseVo);

        return byyqmbVo;
    }

    /**
     * 修改毕业要求模板数据
     */
    public void editByyqmb(String courseIdPointIdQuanzhong, String byyqmbName, String mid) {
        //通过id查询毕业要求模板
        Byyqmb byyqmb = byyqmbMapper.selectByPrimaryKey(mid);
        //修改名称
        byyqmb.setName(byyqmbName);
        //更新毕业要求模板
        byyqmbMapper.updateByPrimaryKeySelective(byyqmb);

        //删除该毕业要求模板中所有的数据，通过mid删除course_byyq中数据
        zbyyqMapper.deleteCourseByyqByMid(mid);

        //插入数据
        //切割courseIdPointIdQuanzhong获取课程id，指标点id，权重
        String[] ids = courseIdPointIdQuanzhong.split(",");
        for(String id : ids) {
            String courseId = id.split("_")[0];
            String zbyyqId = id.split("_")[1];
            String quanzhongStr = id.split("_")[2];
            double quanzhong = Double.parseDouble(quanzhongStr);
            //插入数据到course_byyq表中
            zbyyqMapper.insertToCourseByyq(courseId, zbyyqId, mid, quanzhong);
        }
    }

    /**
     * 批量删除毕业要求模板
     */
    public void deleteByyqmbBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            //先通过mid删除course_byyq中数据
            zbyyqMapper.deleteCourseByyqByMid(id);
            //后通过mid删除byyqmb表中数据
            byyqmbMapper.deleteByPrimaryKey(id);
        }
    }

    public List<Byyqmb> findAllByyqmb() {
        return byyqmbMapper.findAllByyqmb();
    }

    public Byyqmb findByyqmbByClassId(String classId) {
        return byyqmbMapper.findByyqmbByClassId(classId);
    }

    /**
     * 通过课程id查询毕业要求指标点
     */
    public List<Zbyyq> findZbbyqByCourseId(String courseId) {
        return zbyyqMapper.findZbbyqByCourseId(courseId);
    }

    /**
     * 查询所有毕业要求指标点
     */
    public List<Zbyyq> findAllZbyyqList() {
        return zbyyqMapper.findAllZbyyqList();
    }

    /**
     * 查询所有父毕业要求
     */
    public List<FbyyqVo> findAllFbyyq() {
        return zbyyqMapper.findAllFbyyq();
    }

    public List<PointStatistic> findPointStatisticListByFbyyqId(String fid) {
        return zbyyqMapper.findPointStatisticListByFbyyqId(fid);
    }

    public double findQuanzhongByMidAndZbyyqIdAndCourseId(String mid, String pointId, String courseId) {
        return zbyyqMapper.findQuanzhongByMidAndZbyyqIdAndCourseId(mid, pointId, courseId);
    }

    public void findByyqByPage(PageBean pageBean) {
        int count = zbyyqMapper.findByyqByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<FbyyqVo> list = zbyyqMapper.findByyqByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    public FbyyqVo findFbyyqById(String id) {
        return zbyyqMapper.findFbyyqById(id);
    }

}
