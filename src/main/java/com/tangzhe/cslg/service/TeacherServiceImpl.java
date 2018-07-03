package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.TeacherMapper;
import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.Teacher;
import com.tangzhe.cslg.pojo.QueryVo;
import com.tangzhe.cslg.utils.MD5Utils;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 教师 Service
 */
@Service
@Transactional
public class TeacherServiceImpl implements TeacherService {

    private static final Integer IS_ADMIN = 0;
    private static final Integer IS_COMMON = 1;

    @Autowired
    private TeacherMapper teacherMapper;

    /**
     * 教师登录功能
     */
    public Teacher login(Teacher teacher) {
        teacher.setPassword(MD5Utils.md5(teacher.getPassword()));
        return teacherMapper.findTeacherByUsernameAndPwd(teacher);
    }

    /**
     * 修改密码
     */
    public void editPassword(Teacher loginUser) {
        loginUser.setPassword(MD5Utils.md5(loginUser.getPassword()));
        teacherMapper.updateByPrimaryKeySelective(loginUser);
    }

    /**
     * 添加教师
     */
    public void add(Teacher teacher) {
        //设置id
        teacher.setId(UUIDUtils.getId());
        //设置密码，初始为123456
        teacher.setPassword(MD5Utils.md5("123456"));
        //插入
        teacherMapper.insert(teacher);
    }

    /**
     * 分页查询
     */
    public void pageQuery(PageBean pageBean) {
        int count = teacherMapper.findByCount();
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<QueryVo> list = teacherMapper.findByPage((currentPage-1)*pageSize, pageSize);
            pageBean.setRows(list);
        }
    }

    /**
     * 修改教师
     */
    public void edit(Teacher teacher) {
        Teacher t = teacherMapper.selectByPrimaryKey(teacher.getId());
        t.setDepartId(teacher.getDepartId());
        t.setAddress(teacher.getAddress());
        t.setTeacherName(teacher.getTeacherName());
        t.setUsername(teacher.getUsername());
        t.setGender(teacher.getGender());
        t.setPositional(teacher.getPositional());
        t.setTeacherName(teacher.getTeacherName());
        t.setEmail(teacher.getEmail());
        t.setQq(teacher.getQq());

        teacherMapper.updateByPrimaryKeySelective(t);
    }

    /**
     * 批量删除
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            teacherMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 设置管理员
     */
    public void saveBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            teacherMapper.updateType(id, IS_ADMIN);
        }
    }

    /**
     * 还原为普通用户
     */
    public void restoreBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            teacherMapper.updateType(id, IS_COMMON);
        }
    }

    /**
     * 查询所有课程出去该教师已经关联过的课程
     */
    public List<Course> findCourseToAssociation(String id) {
        return teacherMapper.findCourseToAssociation(id);
    }

    /**
     * 通过教师id查询课程
     */
    public List<Course> findCourseByTeacherId(String id) {
        return teacherMapper.findCourseByTeacherId(id);
    }

    /**
     * 确定教师关联课程
     */
    public void assignCourseToTeacher(String id, String[] courseIds) {
        //删除课程教师关系表中所有教师id为当前教师id的记录
        teacherMapper.deleteCourseTeacherByTeacherId(id);

        //插入多条记录到课程教师关系表，课程id为courseId，教师id为当前教师id
        if(courseIds != null) {
            for(String cid : courseIds) {
                teacherMapper.insertCourseTeacher(cid, id);
            }
        }
    }

}
