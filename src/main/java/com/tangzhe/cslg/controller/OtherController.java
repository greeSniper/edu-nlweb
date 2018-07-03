package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.*;
import com.tangzhe.cslg.pojo.StuExpVo;
import com.tangzhe.cslg.service.CourseService;
import com.tangzhe.cslg.service.CslgClassService;
import com.tangzhe.cslg.service.OtherService;
import com.tangzhe.cslg.service.StudentService;
import com.tangzhe.cslg.utils.FileUtils;
import com.tangzhe.cslg.utils.PageBean;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 其他题目 Controller
 */
@Controller
@RequestMapping("other")
public class OtherController {

    private static final String LIST = "pre/other";

    @Autowired
    private OtherService otherService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private CslgClassService cslgClassService;
    @Autowired
    private CourseService courseService;

    /**
     * 新增其他题目
     */
    @RequestMapping("/add")
    public String add(Other other, HttpSession session) {
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        other.setTeacherId(teacher.getId());
        other.setTeacherName(teacher.getTeacherName());
        otherService.add(other);
        return LIST;
    }

    /**
     * 分页查询其他题目
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows, HttpSession session) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);
        //获取负责人
        Teacher teacher = (Teacher) session.getAttribute("loginUser");
        otherService.pageQuery(pageBean, teacher.getId());
        return pageBean;
    }

    /**
     * 批量删除其他题目
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        otherService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 其他题目关联毕业要求指标点
     */
    @RequestMapping("/associate")
    public String associate(Other other) {
        otherService.edit(other);
        return LIST;
    }

    /**
     * 修改其他题目
     */
    @RequestMapping("/edit")
    public String edit(Other other, String content2) {
        other.setContent(content2);
        otherService.edit(other);
        return LIST;
    }

    /**
     * 下载班级学生其他成绩模板
     */
    @RequestMapping("/downloaOtherTemplet")
    public void downloaOtherTemplet(String classId, String courseId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取负责人
        Teacher teacher = (Teacher) request.getSession().getAttribute("loginUser");
        //通过课程id和负责人id查询其他题目列表
        List<Other> otherList = otherService.findOtherListByCourseIdAndTeacherId(courseId, teacher.getId());

        //查询当前班级
        CslgClass cslgClass = cslgClassService.findById(classId);
        //查询当前科目
        Course course = courseService.findCourseById(courseId);

        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("学生其他成绩模板");
        //设置列宽
        sheet.setColumnWidth(0, 14*256);
        sheet.setColumnWidth(1, 8*256);
        for(int i=2; i<12; i++) {
            sheet.setColumnWidth(i, 10*256);
        }

        int index = 0;
        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(index++).setCellValue("学号");
        headRow.createCell(index++).setCellValue("姓名");
        //遍历其他题目列表
        for(Other other : otherList) {
            headRow.createCell(index++).setCellValue(other.getOtherName());
        }

        //创建数据
        //调用业务查询该班级所有学生
        List<Student> list = studentService.findStudentListByClassId(classId);
        for(Student stu : list) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(stu.getStuCode());
            dataRow.createCell(1).setCellValue(stu.getStuName());
        }

        //使用输出流进行文件下载（一个流、两个头）
        String filename = cslgClass.getClassName() + "学生" + course.getCourseName() + "其他成绩模板.xls";
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
     * 上传学生其他成绩
     */
    @RequestMapping("/importOtherRes")
    @ResponseBody
    public String importOtherRes(String courseId, MultipartFile otherResFile, HttpSession session) throws IOException {
        try {
            //获取负责人
            Teacher teacher = (Teacher) session.getAttribute("loginUser");
            String teacherId = teacher.getId();

            //使用POI解析Excel文件
            HSSFWorkbook workbook = new HSSFWorkbook(otherResFile.getInputStream());

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
                    //题目名称：大作业1
                    String otherName = sheet.getRow(0).getCell(i).getStringCellValue();
                    //通过课程id和其他题目名称查询其他题目
                    Other other = otherService.findOtherByCourseIdAndTeacherIdAndOtherName(courseId, teacherId, otherName);
                    //其他成绩
                    Double score = row.getCell(i).getNumericCellValue();

                    StuExpVo se = new StuExpVo(stuId, score, other.getQuanzhong(), other.getId());
                    seList.add(se);
                }
            }

            //往stu_exp表中批量插入数据
            otherService.saveStuOtherBatch(seList);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }

}
