package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.DepartmentMapper;
import com.tangzhe.cslg.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 院系 Service
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 查询所有院系
     */
    public List<Department> findAll() {
        return departmentMapper.findAll();
    }

}
