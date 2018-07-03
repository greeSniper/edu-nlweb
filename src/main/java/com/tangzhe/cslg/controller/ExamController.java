package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.*;
import com.tangzhe.cslg.service.CourseService;
import com.tangzhe.cslg.service.ExamService;
import com.tangzhe.cslg.service.MajorService;
import com.tangzhe.cslg.service.PointService;
import com.tangzhe.cslg.utils.PageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 考试 Controller
 */
@Controller
@RequestMapping("exam")
public class ExamController {

    private static final String LIST = "pre/exam";
    private static final String QUESTION_LIST = "pre/questionview";
    private static final String TP_VIEW = "pre/testpaperview";

    @Autowired
    private ExamService examService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private PointService pointService;

    /**
     * 通过院系id查询专业
     */
    @RequestMapping("/findMajorByDepartId")
    @ResponseBody
    public List<Major> findMajorByDepartId(HttpServletRequest request) {
        //获取当前用户
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");
        //获取当前用户所属院系
        String departId = teacher.getDepartId();

        //调用业务通过院系id查询专业
        List<Major> list = majorService.findMajorByDepartId(departId);
        return list;
    }

    /**
     * 通过教师id查询课程
     */
    @RequestMapping("/findCourseByTeacherId")
    @ResponseBody
    public List<Course> findCourseByTeacherId(HttpServletRequest request) {
        //获取当前用户
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");

        //调用业务通过教师id查询课程
        List<Course> list = courseService.findCourseByTeacherId(teacher.getId());
        return list;
    }

    /**
     * 新增试卷
     */
    @RequestMapping("/addTestPaper")
    public String addTestPaper(TestPaper testPaper, HttpServletRequest request) {
        //设置出卷人
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");
        testPaper.setTeacherId(teacher.getId());
        testPaper.setTeacherName(teacher.getTeacherName());

        examService.addTestPaper(testPaper);
        return LIST;
    }

    /**
     * 修改试卷
     */
    @RequestMapping("/editTestPaper")
    public String editTestPaper(TestPaper testPaper) {
        examService.editTestPaper(testPaper);
        return LIST;
    }

    /**
     * 批量作废试卷
     */
    @RequestMapping("/deleteTestPaperBatch")
    public String deleteTestPaperBatch(String ids) {
        examService.deleteTestPaperBatch(ids);
        return LIST;
    }

    /**
     * 批量还原试卷
     */
    @RequestMapping("/restoreTestPaperBatch")
    public String restoreTestPaperBatch(String ids) {
        examService.restoreTestPaperBatch(ids);
        return LIST;
    }

    /**
     * 分页查询试卷
     */
    @RequestMapping("/pageQueryTestPaper")
    @ResponseBody
    public PageBean pageQueryTestPaper(Integer page, Integer rows, HttpServletRequest request) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        //获取出卷人
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");

        examService.pageQuery(pageBean, teacher.getId());
        return pageBean;
    }

    /**
     * 根据试卷id查询指标点
     */
    @RequestMapping("/findPointByTestPaperId")
    @ResponseBody
    public List<Point> findPointByTestPaperId(String id) {
        List<Point> list = pointService.findPointByTestPaperId(id);
        return list;
    }

    /**
     * 添加题目
     */
    @RequestMapping("/addQuestion")
    public String addQuestion(Question question) {
        examService.addQuestion(question);
        return LIST;
    }

    /**
     * 添加题目重写
     */
    @RequestMapping("/addQuestion2")
    public String addQuestion2(Question question) {
        examService.addQuestion2(question);
        return LIST;
    }

    /**
     * 跳转到试卷题目页面
     */
    @RequestMapping("/toQuestionView")
    public String toQuestionView(String id, HttpServletRequest request) {
        //将试卷id放入request域中
        request.setAttribute("tpId", id);
        //跳转页面
        return QUESTION_LIST;
    }

    /**
     * 分页查询所选试卷题目
     */
    @RequestMapping("/pageQuestionQuery")
    @ResponseBody
    public PageBean pageQuestionQuery(String tpId, Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        examService.pageQuestionQuery(pageBean, tpId);
        return pageBean;
    }

    /**
     * 修改题目
     */
    @RequestMapping("/editQuestion")
    public String editQuestion(String tpId, Question question, HttpServletRequest request) {
        //将试卷id放入request域中
        request.setAttribute("tpId", tpId);

        //调用业务修改题目
        examService.editQuestion(question);

        //跳转页面
        return QUESTION_LIST;
    }

    /**
     * 修改题目重写
     */
    @RequestMapping("/editQuestion2")
    public String editQuestion2(String tpId, Question question, HttpServletRequest request) {
        //将试卷id放入request域中
        request.setAttribute("tpId", tpId);
        //调用业务修改题目
        examService.editQuestion2(question);
        //跳转页面
        return QUESTION_LIST;
    }

    /**
     * 批量删除题目
     */
    @RequestMapping("/deleteQuestionBatch")
    public String deleteQuestionBatch(String ids, String tpId, HttpServletRequest request) {
        //将试卷id放入request域中
        request.setAttribute("tpId", tpId);

        //调用业务批量删除题目
        examService.deleteQuestionBatch(ids);

        //跳转页面
        return QUESTION_LIST;
    }

    /**
     * 进入查看试卷页面
     */
    @RequestMapping("/toTestPaperView")
    public String toTestPaperView(String id, HttpServletRequest request) {
        //调用业务通过id查询试卷
        TestPaper tp = examService.findTestPaperById(id);

        //调用业务通过试卷id查询该试卷所有的题目
        List<Question> questionList = examService.findQuestionListByTestPaperId(id);

        //将试卷对象及题目列表放入request域中
        request.setAttribute("tp", tp);
        request.setAttribute("questionList", questionList);

        //跳转页面
        return TP_VIEW;
    }

    /**
     * 查询该试卷指定指标点权重矩阵的指标点
     */
    @RequestMapping("/findZbbyqByTestPaperId")
    @ResponseBody
    public List<Zbyyq> findZbbyqByTestPaperId(String id) {
        return examService.findZbbyqByTestPaperId(id);
    }

}
























