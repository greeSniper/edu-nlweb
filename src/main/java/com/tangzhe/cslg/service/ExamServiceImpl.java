package com.tangzhe.cslg.service;

import com.tangzhe.cslg.dao.*;
import com.tangzhe.cslg.entity.*;
import com.tangzhe.cslg.pojo.QuestionVo;
import com.tangzhe.cslg.pojo.StuExpVo;
import com.tangzhe.cslg.pojo.StuTpScoreVo;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 考试 Service
 */
@Service
@Transactional
public class ExamServiceImpl implements ExamService {

    //试卷mapper
    @Autowired
    private TestPaperMapper testPaperMapper;
    //课程mapper
    @Autowired
    private CourseMapper courseMapper;
    //专业mapper
    @Autowired
    private MajorMapper majorMapper;
    //指标点mapper
    @Autowired
    private PointMapper pointMapper;
    //题目mapper
    @Autowired
    private QuestionMapper questionMapper;
    //实验mapper
    @Autowired
    private ExpMapper expMapper;
    //学生mapper
    @Autowired
    private StudentMapper studentMapper;
    //毕业要求指标点mapper
    @Autowired
    private ZbyyqMapper zbyyqMapper;

    /**
     * 新增试卷
     */
    public void addTestPaper(TestPaper testPaper) {
        //设置id
        testPaper.setId(UUIDUtils.getId());

        //设置课程
        Course course = courseMapper.selectByPrimaryKey(testPaper.getCourseId());
        testPaper.setCourseName(course.getCourseName());

        //设置专业
        Major major = majorMapper.selectByPrimaryKey(testPaper.getMajorId());
        testPaper.setMajorName(major.getMajorName());

        //设置创建时间和更新时间
        testPaper.setCreateTime(new Date());
        testPaper.setUpdateTime(new Date());

        //插入试卷
        testPaperMapper.insertSelective(testPaper);
    }

    /**
     * 分页查询试卷
     */
    public void pageQuery(PageBean pageBean, String teacherId) {
        //通过出卷人id查询试卷
        int count = testPaperMapper.findCountByTeacherId(teacherId);
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<TestPaper> list = testPaperMapper.findPageByTeacherId((currentPage-1)*pageSize, pageSize, teacherId);
            pageBean.setRows(list);
        }
    }

    /**
     * 修改试卷
     */
    public void editTestPaper(TestPaper testPaper) {
        TestPaper tp = testPaperMapper.selectByPrimaryKey(testPaper.getId());
        tp.setTpName(testPaper.getTpName());
        tp.setUpdateTime(new Date());
        testPaperMapper.updateByPrimaryKeySelective(tp);
    }

    /**
     * 批量作废试卷
     */
    public void deleteTestPaperBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            testPaperMapper.updateStateById(id, 1);
        }
    }

    /**
     * 批量还原试卷
     */
    public void restoreTestPaperBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            testPaperMapper.updateStateById(id, 0);
        }
    }

    /**
     * 添加题目
     */
    public void addQuestion(Question question) {
        //设置id
        question.setId(UUIDUtils.getId());
        //设置指标点名称
        Point point = pointMapper.selectByPrimaryKey(question.getPointId());
        if(point != null) {
            question.setPointName(point.getPointName());
        }
        //插入题目
        questionMapper.insertSelective(question);

        //设置权重
        //查询该指标点的所有题目和实验，通过分值计算权重
        arithQuanzhong(question.getPointId());
    }

    /**
     * 分页查询所选试卷题目
     */
    public void pageQuestionQuery(PageBean pageBean, String tpId) {
        //通过试卷id查询题目
        int count = questionMapper.findCountByTpId(tpId);
        pageBean.setTotal(count);
        if(count > 0) {
            int currentPage = pageBean.getCurrentPage();
            int pageSize = pageBean.getPageSize();

            List<QuestionVo> list = questionMapper.findPageByTpId((currentPage-1)*pageSize, pageSize, tpId);
            pageBean.setRows(list);
        }
    }

    /**
     * 修改题目
     */
    public void editQuestion(Question question) {
        //修改题目指标点名称
        Point point = pointMapper.selectByPrimaryKey(question.getPointId());
        question.setPointName(point.getPointName());
        questionMapper.updateByPrimaryKeySelective(question);

        //设置权重
        //查询该指标点的所有题目和实验，通过分值计算权重
        arithQuanzhong(question.getPointId());
    }

    /**
     * 批量删除题目
     */
    public void deleteQuestionBatch(String ids) {
        String[] idArr = ids.split(",");
        for(String id : idArr) {
            //Question question = questionMapper.selectByPrimaryKey(id);
            //String pointId = question.getPointId();

            questionMapper.deleteByPrimaryKey(id);

            //修改权重
            //arithQuanzhong(pointId);
        }
    }

    /**
     * 通过试卷id查询试卷
     */
    public TestPaper findTestPaperById(String id) {
        return testPaperMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过试卷id查询该试卷所有的题目
     */
    public List<Question> findQuestionListByTestPaperId(String id) {
        return questionMapper.findQuestionListByTestPaperId(id);
    }

    /**
     * 通过当前登录教师id查询试卷
     */
    public List<TestPaper> findTestPaperByTeacherId(String id) {
        return testPaperMapper.findTestPaperByTeacherId(id);
    }

    /**
     * 往stu_tp_score表中批量插入数据
     */
    public void saveStsBatch(List<StuTpScoreVo> stsList) {
        for(StuTpScoreVo sts : stsList) {
            studentMapper.insertStuTpScore(sts);
        }
    }

    /**
     * 往stu_exp表中批量插入数据
     */
    public void saveStuExpBatch(List<StuExpVo> seList) {
        for(StuExpVo se : seList) {
            studentMapper.insertStuExp(se);
        }
    }

    /**
     * 通过指标点id查询题目
     */
    public List<Question> findQuestionListByPointId(String pointId) {
        return questionMapper.findQuestionListByPointId(pointId);
    }

    /**
     * 添加题目重写
     */
    public void addQuestion2(Question question) {
        //设置id
        question.setId(UUIDUtils.getId());
        //设置指标点名称
        Zbyyq zbyyq = zbyyqMapper.selectByPrimaryKey(question.getPointId());
        if(zbyyq != null) {
            question.setPointName(zbyyq.getName());
        }
        //插入题目
        questionMapper.insertSelective(question);
    }

    /**
     * 查询该试卷指定指标点权重矩阵的指标点
     */
    public List<Zbyyq> findZbbyqByTestPaperId(String id) {
        //通过试卷id查询试卷
        TestPaper testPaper = testPaperMapper.selectByPrimaryKey(id);
        String courseId = testPaper.getCourseId(); //课程id
        String mid = testPaper.getMid(); //毕业要求模板id
        return courseMapper.findZbyyqByCourseIdAndMid(courseId, mid);
    }

    /**
     * 修改题目重写
     */
    public void editQuestion2(Question question) {
        //修改题目指标点名称
        Zbyyq zbyyq = zbyyqMapper.selectByPrimaryKey(question.getPointId());
        question.setPointName(zbyyq.getName());
        questionMapper.updateByPrimaryKeySelective(question);
    }

    /**
     * 查询该试卷该毕业要求指标点的所有题目
     */
    public List<Question> findQuestionListByTpIdAndZbyyqId(String tpId, String id) {
        return questionMapper.findQuestionListByTpIdAndZbyyqId(tpId, id);
    }

    /**
     * 查询该指标点的所有题目和实验，通过分值计算权重
     */
    private void arithQuanzhong(String pointId) {
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
