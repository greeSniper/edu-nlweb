package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.ExpMapper;
import com.tangzhe.cslg.dao.PointMapper;
import com.tangzhe.cslg.dao.QuestionMapper;
import com.tangzhe.cslg.dao.ZbyyqMapper;
import com.tangzhe.cslg.entity.Exp;
import com.tangzhe.cslg.entity.Point;
import com.tangzhe.cslg.entity.Question;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.ExpVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 实验 Service
 */
@Service
@Transactional
public class ExpServiceImpl implements ExpService {

    @Autowired
    private ExpMapper expMapper;
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ZbyyqMapper zbyyqMapper;

    /**
     * 添加实验
     */
    public void add(Exp exp) {
        exp.setId(UUIDUtils.getId());
        expMapper.insertSelective(exp);
    }

    /**
     * 分页查询实验
     */
    public void pageQuery(PageBean pageBean, String teacherId) {
        //通过出卷人id查询试卷
        int count = expMapper.findCountByTeacherId(teacherId);
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<ExpVo> list = expMapper.findPageByTeacherId((currentPage-1)*pageSize, pageSize, teacherId);
            pageBean.setRows(list);
        }
    }

    /**
     * 批量删除实验
     */
    public void deleteBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            Exp exp = expMapper.selectByPrimaryKey(id);
            String pointId = exp.getPointId();

            expMapper.deleteByPrimaryKey(id);

            //设置权重
            //查询该指标点的所有题目和实验，通过分值计算权重
            //通过指标点id查询题目
            List<Question> qList = questionMapper.findQuestionListByPointId(pointId);

            //通过指标点id查询实验
            List<Exp> eList = expMapper.findExpListByPointId(pointId);

            if(qList.size() == 0) {
                qList = null;
            }
            if(eList.size() == 0) {
                eList = null;
            }

            //若qList和eList只要其中一个的size>0，说明该指标点已经有了题目或实验，需要计算权重
            if((qList!=null&&qList.size()>0) && (eList==null)) {
                //1. qList!=null && eList==null，有题目没有实验
                Double fenmu = 0.0; //分母
                for(Question q : qList) {
                    fenmu += q.getScore();
                }
                //为所有题目设置新的权重
                for(Question q : qList) {
                    //设置权重：分子/分母
                    q.setQuanzhong(q.getScore() / fenmu);
                    //批量更新题目
                    questionMapper.updateByPrimaryKeySelective(q);
                }

            } else if((qList==null) && (eList!=null&&eList.size()>0)) {
                //2. qList==null && eList!=null，有实验没有题目
                Double fenmu = 0.0; //分母
                for(Exp e : eList) {
                    fenmu += e.getScore();
                }
                //为所有实验设置新的权重
                for(Exp e : eList) {
                    e.setQuanzhong(e.getScore() / fenmu);
                    //批量更新实验
                    expMapper.updateByPrimaryKeySelective(e);
                }

            } else if((qList!=null&&qList.size()>0) && (eList!=null&&eList.size()>0)) {
                //3. qList!=null && eList!=null，有题目也有实验
                Double fenmu = 0.0;
                for(Question q : qList) {
                    fenmu += q.getScore();
                }
                for(Exp e : eList) {
                    fenmu += e.getScore();
                }
                //为所有题目设置新的权重
                for(Question q : qList) {
                    q.setQuanzhong(q.getScore() / fenmu);
                    //批量更新题目
                    questionMapper.updateByPrimaryKeySelective(q);
                }
                //为所有实验设置新的权重
                for(Exp e : eList) {
                    e.setQuanzhong(e.getScore() / fenmu);
                    //批量更新实验
                    expMapper.updateByPrimaryKeySelective(e);
                }
            }
        }
    }

    /**
     * 修改实验
     */
    public void edit(Exp exp) {
        Point point = pointMapper.selectByPrimaryKey(exp.getPointId());
        if(point != null) {
            exp.setPointName(point.getPointName());
        }
        expMapper.updateByPrimaryKeySelective(exp);

        //设置权重
        //查询该指标点的所有题目和实验，通过分值计算权重
        //通过指标点id查询题目
        List<Question> qList = questionMapper.findQuestionListByPointId(exp.getPointId());

        //通过指标点id查询实验
        List<Exp> eList = expMapper.findExpListByPointId(exp.getPointId());

        if(qList.size() == 0) {
            qList = null;
        }
        if(eList.size() == 0) {
            eList = null;
        }

        //若qList和eList只要其中一个的size>0，说明该指标点已经有了题目或实验，需要计算权重
        if((qList!=null&&qList.size()>0) && (eList==null)) {
            //1. qList!=null && eList==null，有题目没有实验
            Double fenmu = 0.0; //分母
            for(Question q : qList) {
                fenmu += q.getScore();
            }
            //为所有题目设置新的权重
            for(Question q : qList) {
                //设置权重：分子/分母
                q.setQuanzhong(q.getScore() / fenmu);
                //批量更新题目
                questionMapper.updateByPrimaryKeySelective(q);
            }

        } else if((qList==null) && (eList!=null&&eList.size()>0)) {
            //2. qList==null && eList!=null，有实验没有题目
            Double fenmu = 0.0; //分母
            for(Exp e : eList) {
                fenmu += e.getScore();
            }
            //为所有实验设置新的权重
            for(Exp e : eList) {
                e.setQuanzhong(e.getScore() / fenmu);
                //批量更新实验
                expMapper.updateByPrimaryKeySelective(e);
            }

        } else if((qList!=null&&qList.size()>0) && (eList!=null&&eList.size()>0)) {
            //3. qList!=null && eList!=null，有题目也有实验
            Double fenmu = 0.0;
            for(Question q : qList) {
                fenmu += q.getScore();
            }
            for(Exp e : eList) {
                fenmu += e.getScore();
            }
            //为所有题目设置新的权重
            for(Question q : qList) {
                q.setQuanzhong(q.getScore() / fenmu);
                //批量更新题目
                questionMapper.updateByPrimaryKeySelective(q);
            }
            //为所有实验设置新的权重
            for(Exp e : eList) {
                e.setQuanzhong(e.getScore() / fenmu);
                //批量更新实验
                expMapper.updateByPrimaryKeySelective(e);
            }
        }
    }

    /**
     * 通过课程id和实验名称查询实验
     */
    public Exp findExpByCourseIdAndExpName(String courseId, String expName) {
        return expMapper.findExpByCourseIdAndExpName(courseId, expName);
    }

    /**
     * 通过指标点id查询实验
     */
    public List<Exp> findExpListByPointId(String pointId) {
        return expMapper.findExpListByPointId(pointId);
    }

    /**
     * 添加实验重写
     */
    public void add2(Exp exp) {
        exp.setId(UUIDUtils.getId());
        expMapper.insertSelective(exp);
    }

    /**
     * 实验关联指标点重写
     */
    public void edit2(Exp exp) {
        Zbyyq zbyyq = zbyyqMapper.selectByPrimaryKey(exp.getPointId());
        if(zbyyq != null) {
            exp.setPointName(zbyyq.getName());
        }
        expMapper.updateByPrimaryKeySelective(exp);
    }

    /**
     * 批量删除实验重写
     */
    public void deleteBatch2(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            expMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 查询该矩阵该课程该毕业要求指标点的所有实验
     */
    public List<Exp> findExpListByMidAndPointId(String courseId, String mid, String pointId) {
        return expMapper.findExpListByMidAndPointId(courseId, mid, pointId);
    }

    /**
     * 查询该矩阵该课程该毕业要求指标点以及负责人为当前老师的所有实验
     */
    public List<Exp> findExpListByCourseIdAndMidAndPointIdAndTeacherId(String courseId, String mid, String pointId, String teacherId) {
        return expMapper.findExpListByCourseIdAndMidAndPointIdAndTeacherId(courseId, mid, pointId, teacherId);
    }

}
