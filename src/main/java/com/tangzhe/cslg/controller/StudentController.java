package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.CslgClass;
import com.tangzhe.cslg.entity.Major;
import com.tangzhe.cslg.entity.Student;
import com.tangzhe.cslg.pojo.StudentVo;
import com.tangzhe.cslg.service.CslgClassService;
import com.tangzhe.cslg.service.MajorService;
import com.tangzhe.cslg.service.StudentService;
import com.tangzhe.cslg.utils.FileUtils;
import com.tangzhe.cslg.utils.PageBean;
import com.tangzhe.cslg.utils.UUIDUtils;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 学生 Controller
 */
@Controller
@RequestMapping("student")
public class StudentController {

    private static final String LIST = "person/student";

    @Autowired
    private StudentService studentService;
    @Autowired
    private CslgClassService cslgClassService;
    @Autowired
    private MajorService majorService;

    /**
     * 导入学生信息
     */
    @RequestMapping("/importXls")
    public void importXls(MultipartFile studentFile) throws IOException, ParseException {
        //使用POI解析Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook(studentFile.getInputStream());

        //根据名称获得指定Sheet对象
        HSSFSheet sheet = workbook.getSheetAt(0);

        List<Student> studentList = new ArrayList<>();
        //遍历所有行
        for(Row row : sheet) {
            //第一行为标题，无需处理
            if(row.getRowNum() == 0) {
                continue;
            }
            //封装学生对象
            String stuCode = row.getCell(0).getStringCellValue();   //学号
            String stuName = row.getCell(1).getStringCellValue();   //姓名
            String gender = row.getCell(2).getStringCellValue();    //性别

            String className = row.getCell(3).getStringCellValue(); //班级名称
            //调用班级业务通过班级名称查询班级
            CslgClass cslgClass = cslgClassService.findByName(className);

            String majorName = row.getCell(4).getStringCellValue(); //专业名称
            //调用专业业务通过专业名称查询专业
            Major major = majorService.findByName(majorName);

            String grade = String.valueOf(row.getCell(5).getNumericCellValue());     //年级
            String idCard = row.getCell(6).getStringCellValue();    //身份证
            String telephone = row.getCell(7).getStringCellValue(); //电话
            String email = row.getCell(8).getStringCellValue();     //邮箱
            String address = row.getCell(9).getStringCellValue();   //地址
            String enrollment = String.valueOf(row.getCell(10).getNumericCellValue()); //入学年份
            String birthdayStr = row.getCell(11).getStringCellValue();
            Date birthday = new SimpleDateFormat("yyyy.MM.dd").parse(birthdayStr); //生日

            //构造学生对象
            Student student = new Student(stuCode, cslgClass.getId(), stuCode,
                    stuName, gender, className, grade, major.getId(), majorName, idCard,
                    telephone, address, enrollment, birthday, email);

            //将学生对象放入list中
            studentList.add(student);
        }

        //调用业务批量保存
        studentService.saveBatch(studentList);
    }

    /**
     * 下载学生信息模板
     */
    @RequestMapping("/downloadTempletXls")
    public void downloadTempletXls(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //使用POI将数据写到Excel文件中
        //在内存中创建一个Excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("学生基本信息模板");

        //创建标题行
        HSSFRow headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("学号");
        headRow.createCell(1).setCellValue("姓名");
        headRow.createCell(2).setCellValue("性别");
        headRow.createCell(3).setCellValue("班级");
        headRow.createCell(4).setCellValue("专业");
        headRow.createCell(5).setCellValue("学年");
        headRow.createCell(6).setCellValue("身份证");
        headRow.createCell(7).setCellValue("联系电话");
        headRow.createCell(8).setCellValue("电子邮箱");
        headRow.createCell(9).setCellValue("常住地址");
        headRow.createCell(10).setCellValue("入学年份");
        headRow.createCell(11).setCellValue("出生日期");
        headRow.createCell(12).setCellValue("院系");

        //创建第一行参考数据
        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
        dataRow.createCell(0).setCellValue("ZB0716201");
        dataRow.createCell(1).setCellValue("安琪");
        dataRow.createCell(2).setCellValue("男");
        dataRow.createCell(3).setCellValue("ZB电信2班");
        dataRow.createCell(4).setCellValue("电子信息工程");
        dataRow.createCell(5).setCellValue(2016);
        dataRow.createCell(6).setCellValue("320581199501014510");
        dataRow.createCell(7).setCellValue("018888888888");
        dataRow.createCell(8).setCellValue("8888888888@qq.com");
        dataRow.createCell(9).setCellValue("江苏省常熟市虞山镇55号");
        dataRow.createCell(10).setCellValue(2016);
        dataRow.createCell(11).setCellValue("1995.01.01");
        dataRow.createCell(12).setCellValue("物理与电子工程学院");

        //使用输出流进行文件下载（一个流、两个头）
        String filename = "学生基本信息模板.xls";
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
     * 分页查询学生
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows, StudentVo studentVo) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        studentService.pageQuery(pageBean, studentVo);
        return pageBean;
    }

    /**
     * 批量删除学生
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        studentService.deleteBatch(ids);
        return LIST;
    }

}
