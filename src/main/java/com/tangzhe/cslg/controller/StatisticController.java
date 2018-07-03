package com.tangzhe.cslg.controller;

import com.alibaba.fastjson.JSON;
import com.tangzhe.cslg.entity.Byyqmb;
import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.service.ByyqService;
import com.tangzhe.cslg.service.CourseService;
import com.tangzhe.cslg.service.StudentService;
import com.tangzhe.cslg.utils.ArithUtils;
import com.tangzhe.cslg.utils.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 达成度统计 Controller
 */
@Controller
@RequestMapping("statistic")
public class StatisticController {

    private static final String COURSE_STATISTIC_VIEW = "/byyq/coursestatisticview";

    @Autowired
    private StudentService studentService;
    @Autowired
    private ByyqService byyqService;
    @Autowired
    private CourseService courseService;

    /**
     * 下载学生达成度统计表
     */
    @RequestMapping("/toDownloadStuStatistic")
    public void toDownloadStuStatistic(String stuId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //通过学生id查询当前学生
        Student student = studentService.findStudentById(stuId);
        //通过班级id查询当前学生所在班级的毕业要求模板矩阵
        Byyqmb byyqmb = byyqService.findByyqmbByClassId(student.getClassId());
        String mid = byyqmb.getId();
        //获取毕业要求模板矩阵数据
        ByyqmbVo byyqmbVo = byyqService.getEditByyqmbData(mid);
        List<ByyqVo> byyqList = byyqmbVo.getByyqVo(); //父毕业要求列表
        List<CourseVo> courseList = byyqmbVo.getCourseVo(); //课程列表
        List<String> pList = new ArrayList<>(); //用于存储所有指标点id

        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个单元格样式
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        cellStyle.setFont(font); //加粗

        //创建另一个单元格样式
        HSSFFont font2 = workbook.createFont();
        font2.setColor(HSSFFont.COLOR_RED); //红色
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        cellStyle2.setFont(font2);
        //创建另一个单元格样式
        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("Sheet1");

        //设置标题，课程序号，课程\毕业要求
        HSSFRow firstRow = sheet.createRow(0);
        HSSFRow secondRow = sheet.createRow(1);
        CellRangeAddress cra = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(cra);
        HSSFCell cell = firstRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("序号");
        sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);
        cra = new CellRangeAddress(0, 1, 1, 1);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("课程\\毕业要求");
        sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

        //设置标题，毕业要求1，毕业要求2...
        int firstCol = 2;
        int lastCol = 0;
        int size = 0;
        for(ByyqVo bv : byyqList) {
            firstCol += size;
            size = bv.getSize();
            lastCol = firstCol + size - 1;
            cra = new CellRangeAddress(0, 0, firstCol, lastCol);
            sheet.addMergedRegion(cra);
            cell = firstRow.createCell(firstCol);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(bv.getName());
            //设置1.1, 1.2, 1.3...
            int secondCol = firstCol;
            for(PointVo pv : bv.getPoint()) {
                cra = new CellRangeAddress(1, 1, secondCol, secondCol);
                sheet.addMergedRegion(cra);
                cell = secondRow.createCell(secondCol);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(pv.getName());
                secondCol ++;

                pList.add(pv.getId());
            }
            //设置达成度合计
            cra = new CellRangeAddress(1, 1, secondCol, secondCol);
            sheet.addMergedRegion(cra);
            cell = secondRow.createCell(secondCol);
            cell.setCellStyle(cellStyle2);
            cell.setCellValue("达成度合计");
            sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);
            pList.add("0");
            firstCol ++;
        }

        //设置课程，权重
        HSSFRow thirdRow = sheet.createRow(2);
        int thirdFirstRow = 2;
        int thirdIndex = 1;
        for(CourseVo cv : courseList) {
            double sigma = 0.0;
            thirdRow = sheet.createRow(thirdFirstRow);
            int thirdFirstCol = 0;
            int thirdLastCol = 0;
            //设置课程序号
            cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, thirdFirstCol++, thirdLastCol++);
            sheet.addMergedRegion(cra);
            cell = thirdRow.createCell(thirdFirstCol-1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(thirdIndex++);

            //设置课程\毕业要求
            cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, thirdFirstCol++, thirdLastCol++);
            sheet.addMergedRegion(cra);
            cell = thirdRow.createCell(thirdFirstCol-1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(cv.getCourseName());

            //设置权重
            for(String pid : pList) {
                for(QuanzhongVo qzv : cv.getQzList()) {
                    if(qzv.getId().equals(pid)) {
                        int i = pList.indexOf(pid);
                        //设置达成度
                        CourseVo course = courseList.get(thirdFirstRow - 2);
                        String courseId = course.getId();
                        //通过 学生id 毕业要求指标点id 课程id 查询达成度
                        ReachVo reach = studentService.findReachByStuIdAndZbyyqIdAndCourseId(stuId, pid, courseId);
                        String val = "";
                        if(reach != null) {
                            val = String.valueOf(reach.getReachFormat()) + " ";
                        }

                        cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, i+2, i+2);
                        sheet.addMergedRegion(cra);
                        cell = thirdRow.createCell(i+2);
                        cell.setCellStyle(cellStyle3);
                        val += "(" + qzv.getQuanzhong() + ")";
                        cell.setCellValue(val);
                        sigma += qzv.getQuanzhong();
                        break;
                    }
                }
            }
            thirdFirstRow ++;
        }

        //使用输出流进行文件下载（一个流、两个头）
        String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String filename = student.getClassName() + "学生达成度-" + student.getStuCode() + "-" + student.getStuName() + "-" + today + ".xls";
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
     * 进入课程达成度统计页面
     */
    @RequestMapping("/toCourseStatisticView")
    public String toCourseStatisticView(String mid, HttpServletRequest request) {
        if(mid.endsWith(",")) {
            mid = mid.substring(0, mid.indexOf(","));
            //通过学生id查询毕业要求模板id
            String classId = studentService.findStudentById(mid).getClassId();
            mid = byyqService.findByyqmbByClassId(classId).getId();
        }

        //封装传递给页面的数据
        List<StatisticVo> statisticList = new ArrayList<>();
        //查询所有父毕业要求
        List<FbyyqVo> fvList = byyqService.findAllFbyyq();
        //遍历父毕业要求
        for(FbyyqVo fv : fvList) {
            StatisticVo statistic = new StatisticVo();
            statistic.setName(fv.getFname());
            //通过父毕业要求id查询子毕业要求
            List<PointStatistic> pointList = byyqService.findPointStatisticListByFbyyqId(fv.getFid());
            //通过毕业要求模板id和父毕业要求id查询课程
            List<CourseStatistic> cList = courseService.findCourseListByMidAndFbyyqId(mid, fv.getFid());

            //遍历子毕业要求指标点
            for(PointStatistic ps : pointList) {
                //通过 毕业要求模板id 和 毕业要求指标点id 查询课程列表
                List<CourseStatistic> courseList = courseService.findCourseListByMidAndZbyyqId(mid, ps.getPointId());
                ps.setCourseList(courseList);
            }

            //遍历课程
            for(CourseStatistic cs : cList) {
                List<PointStatistic> pList = new ArrayList<>();
                for(PointStatistic ps : pointList) {
                    //通过 毕业要求模板id 毕业要求指标点id 课程id 查询达成度
                    List<ReachVo> reachList = studentService.findReachByMidAndPointIdAndCourseId(mid, ps.getPointId(), cs.getCourseId());
                    if (reachList != null && reachList.size() > 0) {
                        //计算达成度平均值
                        double reachAvg = 0.0;
                        for (ReachVo r : reachList) {
                            reachAvg += r.getReach();
                        }
                        reachAvg /= reachList.size();
                        PointStatistic p = new PointStatistic();
                        p.setPointId(ps.getPointId());
                        double quanzhong = byyqService.findQuanzhongByMidAndZbyyqIdAndCourseId(mid, ps.getPointId(), cs.getCourseId())*100;
                        p.setValue(String.valueOf(ArithUtils.myDecimal(reachAvg)) + " " + " （" + quanzhong + "%）");
                        pList.add(p);
                        break;
                    } else {
//                        PointStatistic p = new PointStatistic();
//                        p.setPointId(ps.getPointId());
//                        p.setValue(cs.getQuanzhong());
//                        pList.add(p);
                    }
                }
                cs.setPointList(pList);
            }
            statistic.setPointList(pointList);
            statistic.setCourseList(cList);
            statisticList.add(statistic);
        }

        //将统计数据转为json并传递给页面
        String statisticListJson = JSON.toJSONString(statisticList);
        request.setAttribute("statisticList", statisticListJson);

        return COURSE_STATISTIC_VIEW;
    }

}













