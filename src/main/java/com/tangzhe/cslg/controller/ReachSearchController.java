package com.tangzhe.cslg.controller;

import com.alibaba.fastjson.JSON;
import com.tangzhe.cslg.entity.Course;
import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.service.ByyqService;
import com.tangzhe.cslg.service.CourseService;
import com.tangzhe.cslg.service.ReachService;
import com.tangzhe.cslg.service.StudentService;
import com.tangzhe.cslg.utils.ArithUtils;
import com.tangzhe.cslg.utils.FileUtils;
import com.tangzhe.cslg.utils.PageBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 达成度查询 Controller
 * Created by 唐哲
 * 2017-11-26 16:50
 */
@Controller
@RequestMapping("reachSearch")
public class ReachSearchController {

//    private static final String DEFAULT_GRADE = "2012.0";
//    private static final String DEFAULT_CLASS_ID = "F692AA93506C4FB3B016A6949766F689";
//    private static final String DEFAULT_POINT_ID = "A4BB65FE8FAF4133BAB4960033AD0F2E";
    private static final Double DEFAULT_REACH = 0.7;

    private static final String COURSE_STATISTIC_VIEW = "/search/course_statistic_view";

    @Autowired
    private ReachService reachService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ByyqService byyqService;
    @Autowired
    private CourseService courseService;

    /**
     * 查询所有学生达成度列表
     */
    @RequestMapping("/findListByPage")
    @ResponseBody
    public PageBean findListByPage(Integer page, Integer rows, ReachSearchVo reachSearchVo) {
        if(StringUtils.isBlank(reachSearchVo.getGrade())) {
            reachSearchVo.setGrade(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getClassId())) {
            reachSearchVo.setClassId(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getCourseId())) {
            reachSearchVo.setCourseId(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getPointId())) {
            reachSearchVo.setPointId(null);
        }
        if(reachSearchVo.getReach() == null) {
            reachSearchVo.setReach(DEFAULT_REACH);
        }

        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        reachService.findListByPage(pageBean, reachSearchVo);
        return pageBean;
    }

    /**
     * 查询所有班级
     */
    @RequestMapping("/findAllClass")
    @ResponseBody
    public List<CslgClass> findAllClass() {
        return reachService.findAllClass();
    }

    /**
     * 查询所有课程
     */
    @RequestMapping("/findAllCourse")
    @ResponseBody
    public List<Course> findAllCourse() {
        return courseService.findAllCourse();
    }

    /**
     * 查询所有指标点
     */
    @RequestMapping("/findAllPoint")
    @ResponseBody
    public List<Zbyyq> findAllPoint() {
        return reachService.findAllPoint();
    }

    /**
     * 下载学生达成度
     */
    @RequestMapping("/download")
    public void download(HttpServletRequest request,
                         HttpServletResponse response,
                         ReachSearchVo reachSearchVo) throws IOException {
        if(StringUtils.isBlank(reachSearchVo.getGrade())) {
            reachSearchVo.setGrade(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getClassId())) {
            reachSearchVo.setClassId(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getCourseId())) {
            reachSearchVo.setCourseId(null);
        }
        if(StringUtils.isBlank(reachSearchVo.getPointId())) {
            reachSearchVo.setPointId(null);
        }
        if(reachSearchVo.getReach() == null) {
            reachSearchVo.setReach(DEFAULT_REACH);
        }
        List<ReachSearchVo> list = reachService.findDownload(reachSearchVo);

        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("学生达成度名单");

        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("学号");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("指标点");
        headRow.createCell(3).setCellValue("达成度");
        headRow.createCell(4).setCellValue("班级");
        headRow.createCell(5).setCellValue("专业");
        headRow.createCell(6).setCellValue("学年");

        //写入数据
        for(ReachSearchVo rsv : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(rsv.getStuId());
            dataRow.createCell(1).setCellValue(rsv.getStuName());
            dataRow.createCell(2).setCellValue(rsv.getPointName());
            dataRow.createCell(3).setCellValue(rsv.getReachFormat());
            dataRow.createCell(4).setCellValue(rsv.getClassName());
            dataRow.createCell(5).setCellValue(rsv.getMajorName());
            dataRow.createCell(6).setCellValue(rsv.getGrade());
        }

        //使用输出流进行文件下载（一个流、两个头）
        String filename = "学生达成度名单.xls";
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
     * 进入课程达成度分析页面
     */
    @RequestMapping("/toCourseStatisticView")
    public String toCourseStatisticView(String stuId, String courseId, HttpServletRequest request) {
        //通过学生id查询毕业要求模板id
        String classId = studentService.findStudentById(stuId).getClassId();
        String mid = byyqService.findByyqmbByClassId(classId).getId();

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

        Course c = courseService.findCourseById(courseId);
        request.setAttribute("cname", c.getCourseName());

        return COURSE_STATISTIC_VIEW;
    }

    /**
     * 分页查询毕业要求
     */
    @RequestMapping("/findByyqByPage")
    @ResponseBody
    public PageBean findByyqByPage(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        byyqService.findByyqByPage(pageBean);
        return pageBean;
    }

    /**
     * 进入毕业要求达成度分析页面
     */
    @RequestMapping("/toByyqStatisticView")
    public String toByyqStatisticView(String stuId, String id, HttpServletRequest request) {
        //通过学生id查询毕业要求模板id
        String classId = studentService.findStudentById(stuId).getClassId();
        String mid = byyqService.findByyqmbByClassId(classId).getId();

        //封装传递给页面的数据
        List<StatisticVo> statisticList = new ArrayList<>();
        //查询所有父毕业要求
        //List<FbyyqVo> fvList = byyqService.findAllFbyyq();
        List<FbyyqVo> fvList = new ArrayList<>();
        FbyyqVo f = byyqService.findFbyyqById(id);
        fvList.add(f);
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
