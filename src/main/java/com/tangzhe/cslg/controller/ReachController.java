package com.tangzhe.cslg.controller;

import com.alibaba.fastjson.JSON;
import com.tangzhe.cslg.entity.*;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.service.*;
import com.tangzhe.cslg.utils.ArithUtils;
import com.tangzhe.cslg.utils.FileUtils;
import com.tangzhe.cslg.utils.PageBean;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 达成度 Controller
 */
@Controller
@RequestMapping("reach")
public class ReachController {

    private static final Double EXP_FULL_SCORE = 100.0;
    private static final Double OTHER_FULL_SCORE = 100.0;
    private static final String STU_IMAGE_VIEW = "pre/stuimageview";
    private static final String LIST = "pre/reach";

    @Autowired
    private CslgClassService cslgClassService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ExamService examService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ExpService expService;
    @Autowired
    private PointService pointService;
    @Autowired
    private OtherService otherService;
    @Autowired
    private ByyqService byyqService;

    /**
     * 通过当前登录教师id查询班级
     */
    @RequestMapping("/findClassByTeacherId")
    @ResponseBody
    public List<CslgClass> findClassByTeacherId(HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        List<CslgClass> list = cslgClassService.findClassListByTeacherId(teacher.getId());
        return list;
    }

    /**
     * 通过当前登录教师id查询试卷
     */
    @RequestMapping("/findTestPaperByTeacherId")
    @ResponseBody
    public List<TestPaper> findTestPaperByTeacherId(HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        List<TestPaper> list = examService.findTestPaperByTeacherId(teacher.getId());
        return list;
    }

    /**
     * 通过当前登录教师id查询课程
     */
    @RequestMapping("/findCourseListByTeacherId")
    @ResponseBody
    public List<Course> findCourseListByTeacherId(HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        return courseService.findCourseByTeacherId(teacher.getId());
    }

    /**
     * 下载班级学生考试成绩模板
     */
    @RequestMapping("/downloaTpTemplet")
    public void downloaTpTemplet(String classId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("学生考试成绩模板");
        //设置列宽
        sheet.setColumnWidth(0, 14*256);
        sheet.setColumnWidth(1, 8*256);
        for(int i=2; i<12; i++) {
            sheet.setColumnWidth(i, 20*256);
        }

        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("学号");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("期末考试试题第一大题");
        headRow.createCell(3).setCellValue("期末考试试题第二大题");
        headRow.createCell(4).setCellValue("期末考试试题第三大题");
        headRow.createCell(5).setCellValue("期末考试试题第四大题");
        headRow.createCell(6).setCellValue("期末考试试题第五大题");
        headRow.createCell(7).setCellValue("期末考试试题第六大题");
        headRow.createCell(8).setCellValue("期末考试试题第七大题");
        headRow.createCell(9).setCellValue("期末考试试题第八大题");
        headRow.createCell(10).setCellValue("期末考试试题第九大题");
        headRow.createCell(11).setCellValue("期末考试试题第十大题");

        //创建数据
        //调用业务查询该班级所有学生
        List<Student> list = studentService.findStudentListByClassId(classId);
        for(Student stu : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(stu.getStuCode());
            dataRow.createCell(1).setCellValue(stu.getStuName());
        }

        //使用输出流进行文件下载（一个流、两个头）
        String filename = "学生考试成绩模板.xls";
        String contentType = request.getServletContext().getMimeType(filename);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(contentType);

        //获取客户端浏览器类型
        String agent = request.getHeader("User-Agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("content-disposition", "attachment;filename="+filename);
        workbook.write(out);
    }

    /**
     * 上传学生考试成绩
     */
    @RequestMapping("/importExamRes")
    @ResponseBody
    public String importExamRes(String tpId, MultipartFile examResFile) throws IOException {
        try {
            //使用POI解析Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(examResFile.getInputStream());

            //根据名称获得指定Sheet对象
            HSSFSheet sheet = workbook.getSheetAt(0);

            List<StuTpScoreVo> stsList = new ArrayList<>();
            for(Row row : sheet) {
                //第一行为标题，无需处理
                if (row.getRowNum() == 0) {
                    continue;
                }
                String stuId = row.getCell(0).getStringCellValue();
                String stuName = row.getCell(1).getStringCellValue();
                String score = "";
                for(int i=2; i<row.getLastCellNum(); i++) {
                    score += row.getCell(i).getNumericCellValue() + ",";
                }
                StuTpScoreVo sts = new StuTpScoreVo(stuId, tpId, score, stuName);
                stsList.add(sts);
            }

            //往stu_tp_score表中批量插入数据
            examService.saveStsBatch(stsList);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

    /**
     * 下载班级学生实验成绩模板
     */
    @RequestMapping("/downloaExpTemplet")
    public void downloaExpTemplet(String classId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("学生实验成绩模板");
        //设置列宽
        sheet.setColumnWidth(0, 14*256);
        sheet.setColumnWidth(1, 8*256);
        for(int i=2; i<12; i++) {
            sheet.setColumnWidth(i, 10*256);
        }

        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("学号");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("实验一");
        headRow.createCell(3).setCellValue("实验二");
        headRow.createCell(4).setCellValue("实验三");
        headRow.createCell(5).setCellValue("实验四");
        headRow.createCell(6).setCellValue("实验五");
        headRow.createCell(7).setCellValue("实验六");
        headRow.createCell(8).setCellValue("实验七");
        headRow.createCell(9).setCellValue("实验八");
        headRow.createCell(10).setCellValue("实验九");
        headRow.createCell(11).setCellValue("实验十");

        //创建数据
        //调用业务查询该班级所有学生
        List<Student> list = studentService.findStudentListByClassId(classId);
        for(Student stu : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(stu.getStuCode());
            dataRow.createCell(1).setCellValue(stu.getStuName());
        }

        //使用输出流进行文件下载（一个流、两个头）
        String filename = "学生实验成绩模板.xls";
        String contentType = request.getServletContext().getMimeType(filename);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(contentType);

        //获取客户端浏览器类型
        String agent = request.getHeader("User-Agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("content-disposition", "attachment;filename="+filename);
        workbook.write(out);
    }

    /**
     * 上传学生实验成绩
     */
    @RequestMapping("/importExpRes")
    @ResponseBody
    public String importExpRes(String courseId, MultipartFile expResFile) throws IOException {
        try {
            //使用POI解析Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(expResFile.getInputStream());

            //根据名称获得指定Sheet对象
            HSSFSheet sheet = workbook.getSheetAt(0);

            List<StuExpVo> seList = new ArrayList<>();
            for(Row row : sheet) {
                //第一行为标题，无需处理
                if (row.getRowNum() == 0) {
                    continue;
                }
                String stuId = row.getCell(0).getStringCellValue();
                for(int i=2; i<row.getLastCellNum(); i++) {
                    //实验名称：实验一
                    String expName = sheet.getRow(0).getCell(i).getStringCellValue();
                    //通过课程id和实验名称查询实验
                    Exp exp = expService.findExpByCourseIdAndExpName(courseId, expName);
                    //实验成绩
                    Double score = row.getCell(i).getNumericCellValue();

                    StuExpVo se = new StuExpVo(stuId, exp.getId(), score, exp.getQuanzhong());
                    seList.add(se);
                }
            }

            //往stu_exp表中批量插入数据
            examService.saveStuExpBatch(seList);

            //往页面写成功信息
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            //往页面写失败信息
            return "fail";
        }
    }

    /**
     * 下载达成度计算数据Excel
     */
    @RequestMapping("/downloadReach")
    public void downloadReach(HttpServletRequest request, HttpServletResponse response,
                              String classId, String courseId, String tpId) throws IOException {
        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中


        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("达成度计算数据");

        //获取该班级所在专业
        String majorId = cslgClassService.findById(classId).getMajorId();
        //查询该专业该课程的所有指标点
        List<Point> pointList = pointService.findPointListByMajorIdAndCourseId(majorId, courseId);

        List<PointExamExpVo> peeList = new ArrayList<>();
        int temp = 0;
        //存放所有平均值
        double[] avgArr = new double[100];
        //存放reachVo
        List<ReachVo> reachList = new ArrayList<>();

        //=======================================数据输出===========================================
        //通过班级id和试卷id查询学生成绩
        List<StuTpScoreVo> stsList = studentService.findStudentListByClassIdAndTpId(classId, tpId);
        int dataIndex = 5;
        //遍历stsList
        for(StuTpScoreVo sts : stsList) {
            int index = 0;
            temp ++;
            HSSFRow dataRow = sheet.createRow(dataIndex++);
            HSSFCell cell_1 = dataRow.createCell(index++);
            cell_1.setCellValue(sts.getStuId());
            sheet.setColumnWidth(cell_1.getColumnIndex(), cell_1.getStringCellValue().toString().length() * 512);
            dataRow.createCell(index++).setCellValue(sts.getStuName());

            //遍历该专业该课程的指标点
            for(Point point : pointList) {
                //查询该专业该课程该指标点的所有题目
                List<Question> questionList = examService.findQuestionListByPointId(point.getId());

                //查询该专业该课程该指标点的所有实验
                List<Exp> expList = expService.findExpListByPointId(point.getId());

                //封装pee
                if(temp == 1) {
                    PointExamExpVo pee = new PointExamExpVo();
                    pee.setPointName(point.getPointName());
                    pee.setqList(questionList);
                    pee.seteList(expList);
                    peeList.add(pee);
                }

                //学生试卷题目得分
                String[] scores = sts.getScore().split(",");

                //达成度合计
                double reachSum = 0.0;

                //遍历设置题目分数达成度单元格
                for(Question question : questionList) {
                    for(int i=0; i<scores.length; i++) {
                        if(question.getOrderNo() == i+1) {
                            HSSFCell cell = dataRow.createCell(index++);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(Double.parseDouble(scores[i]));
                            avgArr[index-3] += Double.parseDouble(scores[i]);

                            //达成度
                            double qReach = Double.parseDouble(scores[i]) / question.getScore();
                            cell = dataRow.createCell(index++);
                            cell.setCellStyle(cellStyle);
                            cell.setCellValue(ArithUtils.myDecimal(qReach));
                            avgArr[index-3] += qReach;

                            //达成度合计
                            reachSum += qReach * question.getQuanzhong();
                        }
                    }
                }

                for(Exp exp : expList) {
                    StuExpVo se = studentService.findStuExpByStuIdAndExpId(sts.getStuId(), exp.getId());
                    HSSFCell cell = dataRow.createCell(index++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(se.getScore());
                    avgArr[index-3] += se.getScore();

                    //达成度
                    double eReach = se.getScore() / EXP_FULL_SCORE;
                    cell = dataRow.createCell(index++);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(ArithUtils.myDecimal(eReach));
                    avgArr[index-3] += eReach;

                    //达成度合计
                    reachSum += eReach * exp.getQuanzhong();
                }

                //写达成度合计
                HSSFCell cell = dataRow.createCell(index++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(ArithUtils.myDecimal(reachSum));
                avgArr[index-3] += reachSum;

                //将达成度合计存入reachList中
                ReachVo reachVo = new ReachVo(sts.getStuId(), point.getId(), courseId, reachSum);
                reachList.add(reachVo);
            }
        }

        //=======================================设置第一行===========================================
        HSSFRow firstRow = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 4, 0, 0);
        sheet.addMergedRegion(cra);
        HSSFCell cell = firstRow.createCell(0);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("学号");
        cra = new CellRangeAddress(0, 4, 1, 1);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(1);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("姓名");
        int firstIndex = 2;
        for(PointExamExpVo pee : peeList) {
            firstIndex += (pee.getqList().size() + pee.geteList().size())*2 + 1;
        }
        cra = new CellRangeAddress(0, 0, 2, firstIndex);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(2);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("毕业要求指标点");

        //=======================================设置第二三四五行===========================================
        HSSFRow secondRow = sheet.createRow(1);
        HSSFRow thirdRow = sheet.createRow(2);
        HSSFRow forthRow = sheet.createRow(3);
        int forthIndex = 2;
        int secondFirstIndex = 2;
        int secondLastIndex = 2;
        int thirdFirstIndex = 2;
        int thirdLastIndex = 3;
        //遍历peeList
        for(PointExamExpVo pee : peeList) {
            //写第二行
            secondLastIndex = secondFirstIndex + (pee.getqList().size() + pee.geteList().size())*2;
            cra = new CellRangeAddress(1, 1, secondFirstIndex, secondLastIndex);
            sheet.addMergedRegion(cra);
            cell = secondRow.createCell(secondFirstIndex);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(pee.getPointName());
            secondFirstIndex = secondLastIndex + 1;

            //遍历指标点下题目
            for(Question q : pee.getqList()) {
                //写第三行
                cra = new CellRangeAddress(2, 2, thirdFirstIndex, thirdLastIndex);
                sheet.addMergedRegion(cra);
                cell = thirdRow.createCell(thirdFirstIndex);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(q.getQuestionName());
                thirdFirstIndex += 2;
                thirdLastIndex += 2;

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("得分(满分"+String.valueOf(q.getScore()).split("\\.")[0]+"分)");
                sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("达成度");
            }
            //遍历指标点下实验
            for(Exp e : pee.geteList()) {
                //写第三行
                cra = new CellRangeAddress(2, 2, thirdFirstIndex, thirdLastIndex);
                sheet.addMergedRegion(cra);
                cell = thirdRow.createCell(thirdFirstIndex);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(e.getExpName());
                thirdFirstIndex += 2;
                thirdLastIndex += 2;

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("得分(满分"+String.valueOf(EXP_FULL_SCORE).split("\\.")[0]+"分)");
                sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("达成度");
            }
            cra = new CellRangeAddress(2, 4, forthIndex, forthIndex);
            sheet.addMergedRegion(cra);
            cell = thirdRow.createCell(forthIndex++);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue("达成度合计");
            sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

            thirdFirstIndex ++;
            thirdLastIndex ++;
        }

        //=======================================设置平均值行===========================================
        HSSFRow avgRow = sheet.createRow(dataIndex);
        cra = new CellRangeAddress(dataIndex, dataIndex, 0, 1);
        sheet.addMergedRegion(cra);
        cell = avgRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("平均值");
        int size = stsList.size();
        int avgIndex = 2;
        //遍历avgArr
        for(int i=0; i<firstIndex-2; i++) {
            cell = avgRow.createCell(avgIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(ArithUtils.myDecimal(avgArr[i]/size));
        }

        //使用输出流进行文件下载（一个流、两个头）
        //查询当前课程
        Course c = courseService.findCourseById(courseId);
        String today = new SimpleDateFormat("MMdd").format(new Date());
        String filename = c.getCourseName() + "达成度计算数据" + today + ".xls";
        String contentType = request.getServletContext().getMimeType(filename);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(contentType);

        //获取客户端浏览器类型
        String agent = request.getHeader("User-Agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("content-disposition", "attachment;filename="+filename);
        workbook.write(out);

        //将达成度合计存入reach数据表中
        studentService.insertReachBatch(reachList);
    }

    /**
     * 达成度分页查询
     */
    @RequestMapping("/findReachListByPage")
    @ResponseBody
    public PageBean findReachListByPage(Integer page, Integer rows, ReachVo reachVo) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        studentService.findReachListByPage(pageBean, reachVo);
        return pageBean;
    }

    /**
     * 清空，删除所有学生指标点达成度数据
     */
    @RequestMapping("deleteReachBatch")
    public String deleteReachBatch() {
        studentService.deleteReachBatch();
        return LIST;
    }

    /**
     * 下载达成度计算数据Excel重写
     */
    @RequestMapping("/downloadReach2")
    public void downloadReach2(HttpServletRequest request, HttpServletResponse response,
                              String classId, String courseId, String tpId) throws IOException {
        //获取当前老师
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");
        String teacherId = teacher.getId();

        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个单元格样式
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

        HSSFCellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中


        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("达成度计算数据");

        //获取该班级所在专业
        String majorId = cslgClassService.findById(classId).getMajorId();
        //查询该试卷的指标点权重矩阵mid，通过课程id和mid查询毕业要求指标点列表
        String mid = examService.findTestPaperById(tpId).getMid();
        List<ByyqPointVo> pointList = courseService.findZbyyqListByCourseIdAndMid(courseId, mid);

        List<PointExamExpVo> peeList = new ArrayList<>();
        int temp = 0;
        //存放所有平均值
        double[] avgArr = new double[100];
        //存放reachVo
        List<ReachVo> reachList = new ArrayList<>();

        //=======================================数据输出===========================================
        //通过班级id和试卷id查询学生成绩
        List<StuTpScoreVo> stsList = studentService.findStudentListByClassIdAndTpId(classId, tpId);
        int dataIndex = 5;
        //遍历stsList
        for (StuTpScoreVo sts : stsList) {
            int index = 0;
            temp++;
            HSSFRow dataRow = sheet.createRow(dataIndex++);
            HSSFCell cell_1 = dataRow.createCell(index++);
            cell_1.setCellValue(sts.getStuId());
            sheet.setColumnWidth(cell_1.getColumnIndex(), cell_1.getStringCellValue().toString().length() * 512);
            dataRow.createCell(index++).setCellValue(sts.getStuName());

            //遍历该专业该课程的指标点
            for (ByyqPointVo point : pointList) {
                //查询该试卷该毕业要求指标点的所有题目
                List<Question> questionList = examService.findQuestionListByTpIdAndZbyyqId(tpId, point.getId());

                //查询该矩阵该课程该毕业要求指标点以及负责人为当前老师的所有实验
                //List<Exp> expList = expService.findExpListByMidAndPointId(courseId, mid, point.getId());
                List<Exp> expList = expService.findExpListByCourseIdAndMidAndPointIdAndTeacherId(courseId, mid, point.getId(), teacherId);

                //查询该矩阵该课程该毕业要求指标点负责人是当前老师的所有其他题目
                List<Other> otherList = otherService.findOtherListByMidAndCourseIdAndPointIdAndTeacherId(mid, courseId, point.getId(), teacherId);

                //封装pee
                if (temp == 1) {
                    PointExamExpVo pee = new PointExamExpVo();
                    pee.setPointName(point.getName());
                    pee.setqList(questionList);
                    pee.seteList(expList);
                    pee.setoList(otherList);
                    peeList.add(pee);
                }

                //学生试卷题目得分
                String[] scores = sts.getScore().split(",");

                //达成度合计
                double reachSum = 0.0;

                //遍历设置题目分数达成度单元格
                if(questionList!=null && questionList.size()>0) {
                    for (Question question : questionList) {
                        for (int i = 0; i < scores.length; i++) {
                            if (question.getOrderNo() == i + 1) {
                                HSSFCell cell = dataRow.createCell(index++);
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(Double.parseDouble(scores[i]));
                                avgArr[index - 3] += Double.parseDouble(scores[i]);

                                //达成度
                                double qReach = Double.parseDouble(scores[i]) / question.getScore();
                                cell = dataRow.createCell(index++);
                                cell.setCellStyle(cellStyle);
                                cell.setCellValue(ArithUtils.myDecimal(qReach));
                                avgArr[index - 3] += qReach;

                                //达成度合计
                                reachSum += qReach * question.getQuanzhong();
                            }
                        }
                    }
                }

                //遍历实验
                if(expList!=null && expList.size()>0) {
                    for(Exp exp : expList) {
                        StuExpVo se = studentService.findStuExpByStuIdAndExpId(sts.getStuId(), exp.getId());
                        HSSFCell cell = dataRow.createCell(index++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(se.getScore());
                        avgArr[index-3] += se.getScore();

                        //达成度
                        double eReach = se.getScore() / EXP_FULL_SCORE;
                        cell = dataRow.createCell(index++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(ArithUtils.myDecimal(eReach));
                        avgArr[index-3] += eReach;

                        //达成度合计
                        reachSum += eReach * exp.getQuanzhong();
                    }
                }

                //遍历其他题目
                if(otherList!=null && otherList.size()>0) {
                    for(Other other : otherList) {
                        StuExpVo se = studentService.findStuOtherByStuIdAndotherId(sts.getStuId(), other.getId());
                        HSSFCell cell = dataRow.createCell(index++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(se.getScore());
                        avgArr[index-3] += se.getScore();

                        //达成度
                        double eReach = se.getScore() / OTHER_FULL_SCORE;
                        cell = dataRow.createCell(index++);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(ArithUtils.myDecimal(eReach));
                        avgArr[index-3] += eReach;

                        //达成度合计
                        reachSum += eReach * other.getQuanzhong();
                    }
                }

                //写达成度合计
                HSSFCell cell = dataRow.createCell(index++);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(ArithUtils.myDecimal(reachSum));
                avgArr[index-3] += reachSum;

                //将达成度合计存入reachList中
                ReachVo reachVo = new ReachVo(sts.getStuId(), point.getId(), courseId, reachSum);
                reachList.add(reachVo);
            }
        }

        //=======================================设置第一行===========================================
        HSSFRow firstRow = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 4, 0, 0);
        sheet.addMergedRegion(cra);
        HSSFCell cell = firstRow.createCell(0);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("学号");
        cra = new CellRangeAddress(0, 4, 1, 1);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(1);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("姓名");
        int firstIndex = 2;
        for(PointExamExpVo pee : peeList) {
            firstIndex += (pee.getqList().size() + pee.geteList().size() + pee.getoList().size())*2 + 1;
        }
        cra = new CellRangeAddress(0, 0, 2, firstIndex);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(2);
        cell.setCellStyle(titleCellStyle);
        cell.setCellValue("毕业要求指标点");

        //=======================================设置第二三四五行===========================================
        HSSFRow secondRow = sheet.createRow(1);
        HSSFRow thirdRow = sheet.createRow(2);
        HSSFRow forthRow = sheet.createRow(3);
        int forthIndex = 2;
        int secondFirstIndex = 2;
        int secondLastIndex = 2;
        int thirdFirstIndex = 2;
        int thirdLastIndex = 3;
        //遍历peeList
        for(PointExamExpVo pee : peeList) {
            //写第二行
            secondLastIndex = secondFirstIndex + (pee.getqList().size() + pee.geteList().size() + pee.getoList().size())*2;
            cra = new CellRangeAddress(1, 1, secondFirstIndex, secondLastIndex);
            sheet.addMergedRegion(cra);
            cell = secondRow.createCell(secondFirstIndex);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(pee.getPointName());
            secondFirstIndex = secondLastIndex + 1;

            //遍历指标点下题目
            for(Question q : pee.getqList()) {
                //写第三行
                cra = new CellRangeAddress(2, 2, thirdFirstIndex, thirdLastIndex);
                sheet.addMergedRegion(cra);
                cell = thirdRow.createCell(thirdFirstIndex);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(q.getQuestionName());
                thirdFirstIndex += 2;
                thirdLastIndex += 2;

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("得分(满分"+String.valueOf(q.getScore()).split("\\.")[0]+"分)");
                sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("达成度");
            }
            //遍历指标点下实验
            for(Exp e : pee.geteList()) {
                //写第三行
                cra = new CellRangeAddress(2, 2, thirdFirstIndex, thirdLastIndex);
                sheet.addMergedRegion(cra);
                cell = thirdRow.createCell(thirdFirstIndex);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(e.getExpName());
                thirdFirstIndex += 2;
                thirdLastIndex += 2;

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("得分(满分"+String.valueOf(EXP_FULL_SCORE).split("\\.")[0]+"分)");
                sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("达成度");
            }
            //遍历指标点下其他题目
            for(Other o : pee.getoList()) {
                //写第三行
                cra = new CellRangeAddress(2, 2, thirdFirstIndex, thirdLastIndex);
                sheet.addMergedRegion(cra);
                cell = thirdRow.createCell(thirdFirstIndex);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue(o.getOtherName());
                thirdFirstIndex += 2;
                thirdLastIndex += 2;

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("得分(满分"+String.valueOf(OTHER_FULL_SCORE).split("\\.")[0]+"分)");
                sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

                cra = new CellRangeAddress(3, 4, forthIndex, forthIndex);
                sheet.addMergedRegion(cra);
                cell = forthRow.createCell(forthIndex++);
                cell.setCellStyle(titleCellStyle);
                cell.setCellValue("达成度");
            }
            cra = new CellRangeAddress(2, 4, forthIndex, forthIndex);
            sheet.addMergedRegion(cra);
            cell = thirdRow.createCell(forthIndex++);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue("达成度合计");
            sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

            thirdFirstIndex ++;
            thirdLastIndex ++;
        }

        //=======================================设置平均值行===========================================
        HSSFRow avgRow = sheet.createRow(dataIndex);
        cra = new CellRangeAddress(dataIndex, dataIndex, 0, 1);
        sheet.addMergedRegion(cra);
        cell = avgRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("平均值");
        int size = stsList.size();
        int avgIndex = 2;
        //遍历avgArr
        for(int i=0; i<firstIndex-2; i++) {
            cell = avgRow.createCell(avgIndex++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(ArithUtils.myDecimal(avgArr[i]/size));
        }

        //使用输出流进行文件下载（一个流、两个头）
        //查询当前课程
        Course c = courseService.findCourseById(courseId);
        String today = new SimpleDateFormat("MMdd").format(new Date());
        String filename = c.getCourseName() + "达成度计算数据" + today + ".xls";
        String contentType = request.getServletContext().getMimeType(filename);
        ServletOutputStream out = response.getOutputStream();
        response.setContentType(contentType);

        //获取客户端浏览器类型
        String agent = request.getHeader("User-Agent");
        filename = FileUtils.encodeDownloadFilename(filename, agent);
        response.setHeader("content-disposition", "attachment;filename="+filename);
        workbook.write(out);

        //将达成度合计存入reach数据表中
        studentService.insertReachBatch(reachList);
    }

    /**
     * 进入学生图像页面
     */
    @RequestMapping("/toStuImageView")
    public String toStuImageView(String id, HttpSession session, HttpServletRequest request) {
        //准备能力值图数据，用于封装传递给页面的数据
        List<List<ChartData>> chartDataList = new ArrayList<>();

        //通过学生id查询该学生所在班级
        Student student = studentService.findStudentById(id);
        //通过班级id查询该学生班级关联的课程
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        //List<Course> courseList = courseService.findCourseListByClassIdAndTeacherId(student.getClassId(), teacher.getId());
        List<Course> courseList = courseService.findCourseListByClassId(student.getClassId());

        //遍历课程集合
        for(int i=0; i<courseList.size(); i++) {
            List<ChartData> temp = new ArrayList<>();
            //调用业务查询该学生该课程的指标点达成度
            List<ReachVo> rvList = studentService.findReachVoListByStuIdAndCourseId(id, courseList.get(i).getId());

            ChartData cd = new ChartData();
            cd.setData(courseList.get(i).getCourseName());
            cd.setLitres(0.0);
            temp.add(cd);

            //如果该课程有达成度则遍历
            if(rvList!=null && rvList.size()>0) {
                //遍历达成度集合
                for(ReachVo rv : rvList) {
                    ChartData c = new ChartData();
                    c.setData(rv.getPointName());
                    c.setLitres(rv.getReachFormat());
                    temp.add(c);
                }
            } else {
                //如果该课程还没有达成度，则伪造虚拟数据
                for(int x=0; x<4; x++) {
                    ChartData c = new ChartData();
                    c.setData("暂无");
                    c.setLitres(0.0);
                    temp.add(c);
                }
            }

            //将temp加入到chartData集合中
            chartDataList.add(temp);
        }

        //将chartData转为json并传递给页面
        String chartData = JSON.toJSONString(chartDataList);
        //能力值图数据
        request.setAttribute("result", chartData);

        //准备折线图数据
//        List<ChartData> lineChartDataList = new ArrayList<>();
//        //调用业务根据学生id查询查询达成度
//        List<ReachVo> rvList = studentService.findReachVoListByStuId(id);
//        for(ReachVo rv : rvList) {
//            ChartData cd = new ChartData();
//            cd.setPointName("毕业要求 " + rv.getPointName());
//            cd.setReach(rv.getReachFormat());
//            lineChartDataList.add(cd);
//        }
//        //将lineChartData转为json并传递给页面
//        String lineChartData = JSON.toJSONString(lineChartDataList);
//        //折线图数据
//        request.setAttribute("lineResult", lineChartData);

        //准备折线图数据重写
        List<ChartData> lineChartDataList = new ArrayList<>();
        //通过班级id查询毕业要求模板id
        String mid = byyqService.findByyqmbByClassId(student.getClassId()).getId();
        //查询所有毕业要求指标点
        List<Zbyyq> pointList = byyqService.findAllZbyyqList();
        //遍历毕业要求指标点
        for(Zbyyq point : pointList) {
            ChartData cd = new ChartData();
            //设置毕业要求指标点名称
            cd.setPointName(point.getName());
            double reachSum = 0.0;
            //遍历课程
            for(Course course : courseList) {
                //通过 学生id 毕业要求指标点id 课程id 查询达成度
                ReachVo reachVo = studentService.findReachByStuIdAndZbyyqIdAndCourseId(id, point.getId(), course.getId());
                if(reachVo == null) {
                    continue;
                }

                //通过 课程id 毕业要求指标点id 毕业要求模板id 查询权重
                QuanzhongVo quanzhong = studentService.findQuanzhongByCourseIdAndZbyyqIdAndMid(course.getId(), point.getId(), mid);
                reachVo.setQuanzhong(quanzhong.getQuanzhong());
                reachSum += reachVo.getReach()*reachVo.getQuanzhong();
            }
            //设置达成度最终数据
            cd.setReach(ArithUtils.myDecimal(reachSum));
            lineChartDataList.add(cd);
        }

        //将lineChartData转为json并传递给页面
        String lineChartData = JSON.toJSONString(lineChartDataList);
        //折线图数据
        request.setAttribute("lineResult", lineChartData);

        //跳转页面
        return STU_IMAGE_VIEW;
    }

}




















