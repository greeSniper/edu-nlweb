package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.MajorMapper;
import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业 Service
 */
@Service
@Transactional
public class MajorServiceImpl implements MajorService {

    @Autowired
    private MajorMapper majorMapper;

    /**
     * 新增专业
     */
    public void add(Major major) {
        //设置id
        major.setId(UUIDUtils.getId());
        //插入
        majorMapper.insert(major);
    }

    /**
     * 分页查询专业
     */
    public void pageQuery(PageBean pageBean) {
        int count = majorMapper.findByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<QueryVo> list = majorMapper.findByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    /**
     * 修改专业
     */
    public void edit(QueryVo queryVo) {
        Major major = majorMapper.selectByPrimaryKey(queryVo.getId());
        major.setDepartId(queryVo.getDepart_id());
        major.setMajorCode(queryVo.getMajor_code());
        major.setMajorName(queryVo.getMajor_name());

        majorMapper.updateByPrimaryKeySelective(major);
    }

    /**
     * 批量删除专业
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            majorMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 查询所有专业
     */
    public List<Major> findAll() {
        return majorMapper.findAll();
    }

    /**
     * 通过专业名称查询专业
     */
    public Major findByName(String majorName) {
        return majorMapper.findByName(majorName);
    }

    /**
     * 通过院系id查询专业
     */
    public List<Major> findMajorByDepartId(String departId) {
        return majorMapper.findMajorByDepartId(departId);
    }

}
