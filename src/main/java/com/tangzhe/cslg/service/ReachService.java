package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.ReachSearchVo;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

/**
 * Created by 唐哲
 * 2017-11-26 17:00
 */
public interface ReachService {
    void findListByPage(PageBean pageBean, ReachSearchVo reachSearchVo);

    List<CslgClass> findAllClass();

    List<Zbyyq> findAllPoint();

    List<ReachSearchVo> findDownload(ReachSearchVo reachSearchVo);
}
