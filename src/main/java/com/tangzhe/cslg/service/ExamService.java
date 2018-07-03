package com.tangzhe.cslg.service;

import com.tangzhe.cslg.entity.Question;
import com.tangzhe.cslg.entity.TestPaper;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.StuExpVo;
import com.tangzhe.cslg.pojo.StuTpScoreVo;
import com.tangzhe.cslg.utils.PageBean;

import java.util.List;

public interface ExamService {
    void addTestPaper(TestPaper testPaper);

    void pageQuery(PageBean pageBean, String teacherId);

    void editTestPaper(TestPaper testPaper);

    void deleteTestPaperBatch(String ids);

    void restoreTestPaperBatch(String ids);

    void addQuestion(Question question);

    void pageQuestionQuery(PageBean pageBean, String tpId);

    void editQuestion(Question question);

    void deleteQuestionBatch(String ids);

    TestPaper findTestPaperById(String id);

    List<Question> findQuestionListByTestPaperId(String id);

    List<TestPaper> findTestPaperByTeacherId(String id);

    void saveStsBatch(List<StuTpScoreVo> stsList);

    void saveStuExpBatch(List<StuExpVo> seList);

    List<Question> findQuestionListByPointId(String pointId);

    void addQuestion2(Question question);

    List<Zbyyq> findZbbyqByTestPaperId(String id);

    void editQuestion2(Question question);

    List<Question> findQuestionListByTpIdAndZbyyqId(String tpId, String id);
}
