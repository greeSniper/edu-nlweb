package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.OtherMapper;
import com.tangzhe.cslg.dao.ZbyyqMapper;
import com.tangzhe.cslg.entity.Other;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.OtherVo;
import com.tangzhe.cslg.pojo.StuExpVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 其他题目 Service
 */
@Service
@Transactional
public class OtherServiceImpl implements OtherService {

    @Autowired
    private OtherMapper otherMapper;
    @Autowired
    private ZbyyqMapper zbyyqMapper;

    public void add(Other other) {
        other.setId(UUIDUtils.getId());
        otherMapper.insertSelective(other);
    }

    public void pageQuery(PageBean pageBean, String teacherId) {
        int count = otherMapper.findCountByTeacherId(teacherId);
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<OtherVo> list = otherMapper.findPageByTeacherId((currentPage-1)*pageSize, pageSize, teacherId);
            pageBean.setRows(list);
        }
    }

    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            otherMapper.deleteByPrimaryKey(id);
        }
    }

    public void edit(Other other) {
        Zbyyq zbyyq = zbyyqMapper.selectByPrimaryKey(other.getPointId());
        if(zbyyq != null) {
            other.setPointName(zbyyq.getName());
        }
        otherMapper.updateByPrimaryKeySelective(other);
    }

    /**
     * 通过课程id和负责人id查询其他题目列表
     */
    public List<Other> findOtherListByCourseIdAndTeacherId(String courseId, String teacherId) {
        return otherMapper.findOtherListByCourseIdAndTeacherId(courseId, teacherId);
    }

    /**
     * 通过课程id和其他题目名称查询其他题目
     */
    public Other findOtherByCourseIdAndTeacherIdAndOtherName(String courseId, String teacherId, String otherName) {
        return otherMapper.findOtherByCourseIdAndTeacherIdAndOtherName(courseId, teacherId, otherName);
    }

    /**
     * 往stu_exp表中批量插入数据
     */
    public void saveStuOtherBatch(List<StuExpVo> seList) {
        for(StuExpVo se : seList) {
            otherMapper.insertStuOther(se);
        }
    }

    /**
     * 查询该矩阵该课程该毕业要求指标点负责人是当前老师的所有其他题目
     */
    public List<Other> findOtherListByMidAndCourseIdAndPointIdAndTeacherId(String mid, String courseId, String id, String teacherId) {
        return otherMapper.findOtherListByMidAndCourseIdAndPointIdAndTeacherId(mid, courseId, id, teacherId);
    }

}
