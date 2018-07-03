package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.CslgClassMapper;
import com.tangzhe.cslg.dao.StudentMapper;
import com.tangzhe.cslg.dao.ZbyyqMapper;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.ReachSearchVo;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 唐哲
 * 2017-11-26 17:00
 */
@Service
@Transactional
public class ReachServiceImpl implements ReachService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private CslgClassMapper cslgClassMapper;
    @Autowired
    private ZbyyqMapper zbyyqMapper;

    /**
     * 查询所有学生达成度列表
     */
    public void findListByPage(PageBean pageBean, ReachSearchVo reachSearchVo) {
        String grade = reachSearchVo.getGrade();
        String classId = reachSearchVo.getClassId();
        String courseId = reachSearchVo.getCourseId();
        String pointId = reachSearchVo.getPointId();
        Double reach = reachSearchVo.getReach();

        int count = studentMapper.findReachSearchListByCount(reachSearchVo);
        pageBean.setTotal(count);

        List<ReachSearchVo> list = new ArrayList<>();
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            list = studentMapper.findReachSearchListByPage((currentPage-1)*pageSize, pageSize,
                    grade, classId, courseId, pointId, reach);
        }
        pageBean.setRows(list);
    }

    /**
     * 查询所有班级
     */
    public List<CslgClass> findAllClass() {
        return cslgClassMapper.findAll();
    }

    /**
     * 查询所有指标点
     */
    public List<Zbyyq> findAllPoint() {
        return zbyyqMapper.findAllZbyyqList();
    }

    /**
     * 查询下载的内容
     */
    public List<ReachSearchVo> findDownload(ReachSearchVo reachSearchVo) {
        return studentMapper.findDownload(reachSearchVo);
    }

}
