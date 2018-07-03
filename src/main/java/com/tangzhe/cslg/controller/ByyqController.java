package com.tangzhe.cslg.controller;

import com.tangzhe.cslg.entity.Byyqmb;
import com.tangzhe.cslg.entity.Zbyyq;
import com.tangzhe.cslg.pojo.*;
import com.tangzhe.cslg.service.ByyqService;
import com.tangzhe.cslg.utils.FileUtils;
import com.tangzhe.cslg.utils.PageBean;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 毕业要求 Controller
 */
@Controller
@RequestMapping("byyq")
public class ByyqController {

    private static final String LIST = "byyq/byyq";
    private static final String ADD_BYYQMB_VIEW = "byyq/addbyyqview";
    private static final String EDIT_BYYQMB_VIEW = "byyq/editbyyqview";

    @Autowired
    private ByyqService byyqService;

    /**
     * 查询所有父毕业要求
     */
    @RequestMapping("/fbyyqList")
    @ResponseBody
    public List<FbyyqVo> fbyyqList() {
        return byyqService.fbyyqList();
    }

    /**
     * 添加毕业要求指标点
     */
    @RequestMapping("/add")
    public String add(Zbyyq zbyyq) {
        byyqService.add(zbyyq);
        return LIST;
    }

    /**
     * 修改毕业要求指标点
     */
    @RequestMapping("/edit")
    public String edit(Zbyyq zbyyq) {
        byyqService.edit(zbyyq);
        return LIST;
    }

    /**
     * 批量删除毕业要求指标点
     */
    @RequestMapping("/deleteBatch")
    public String deleteBatch(String ids) {
        byyqService.deleteBatch(ids);
        return LIST;
    }

    /**
     * 分页查询毕业要求指标点
     */
    @RequestMapping("/pageQuery")
    @ResponseBody
    public PageBean pageQuery(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        byyqService.pageQuery(pageBean);
        return pageBean;
    }

    /**
     * 进入添加毕业要求模板页面
     */
    @RequestMapping("/toAddByyqmb")
    public String toAddByyqmb() {
        return ADD_BYYQMB_VIEW;
    }

    /**
     * 获取添加毕业要求模板页面所需数据
     */
    @RequestMapping("/getAddByyqmbData")
    @ResponseBody
    public ByyqmbVo getAddByyqmbData() {
        ByyqmbVo byyqmbVo = byyqService.getAddByyqmbData();
        return byyqmbVo;
    }

    /**
     * 保存毕业要求模板数据
     */
    @RequestMapping("/saveByyqmb")
    @ResponseBody
    public String saveByyqmb(@RequestParam(value = "courseIdPointIdQuanzhong") String courseIdPointIdQuanzhong,
                             @RequestParam(value = "byyqmbName") String byyqmbName) {
        try {
            byyqService.saveByyqmb(courseIdPointIdQuanzhong, byyqmbName);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 分页查询毕业要求模板
     */
    @RequestMapping("/pageByyqmbQuery")
    @ResponseBody
    public PageBean pageByyqmbQuery(Integer page, Integer rows) {
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(page);
        pageBean.setPageSize(rows);

        byyqService.pageByyqmbQuery(pageBean);
        return pageBean;
    }

    /**
     * 进入毕业要求模板编辑页面
     */
    @RequestMapping("/toEditByyqmb")
    public String toEditByyqmb(String id, HttpServletRequest request) {
        Byyqmb byyqmb = byyqService.findByyqmbById(id);
        request.setAttribute("mid", byyqmb.getId());
        request.setAttribute("mname", byyqmb.getName());
        return EDIT_BYYQMB_VIEW;
    }

    /**
     * 获取毕业要求模板编辑页面数据
     */
    @RequestMapping("/getEditByyqmbData")
    @ResponseBody
    public ByyqmbVo getEditByyqmbData(String mid) {
        ByyqmbVo byyqmbVo = byyqService.getEditByyqmbData(mid);
        return byyqmbVo;
    }

    /**
     * 修改毕业要求模板数据
     */
    @RequestMapping("/editByyqmb")
    @ResponseBody
    public String editByyqmb(@RequestParam(value = "courseIdPointIdQuanzhong") String courseIdPointIdQuanzhong,
                             @RequestParam(value = "byyqmbName") String byyqmbName,
                             @RequestParam(value = "mid") String mid) {
        try {
            byyqService.editByyqmb(courseIdPointIdQuanzhong, byyqmbName, mid);
            return "1";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 批量删除毕业要求模板
     */
    @RequestMapping("/deleteByyqmbBatch")
    public String deleteByyqmbBatch(String ids) {
        byyqService.deleteByyqmbBatch(ids);
        return LIST;
    }

    /**
     * 导出毕业要求模板矩阵Excel
     */
    @RequestMapping("/exportXls")
    public void exportXls(String mid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //查询当前毕业要求模板
        Byyqmb byyqmb = byyqService.findByyqmbById(mid);
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
        //font.setColor(HSSFFont.COLOR_RED);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中
        cellStyle.setFont(font); //加粗

        //创建一个标签页
        HSSFSheet sheet = workbook.createSheet("毕业要求矩阵");

        //设置标题，课程序号，课程\毕业要求
        HSSFRow firstRow = sheet.createRow(0);
        HSSFRow secondRow = sheet.createRow(1);
        CellRangeAddress cra = new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(cra);
        HSSFCell cell = firstRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("课程序号");
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
                        cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, i+2, i+2);
                        sheet.addMergedRegion(cra);
                        cell = thirdRow.createCell(i+2);
                        cell.setCellStyle(cellStyle);
                        cell.setCellValue(qzv.getQuanzhong());
                        sigma += qzv.getQuanzhong();
                        break;
                    }
                }
            }

            //设置∑课程贡献度权重值
            cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, lastCol+1, lastCol+1);
            sheet.addMergedRegion(cra);
            cell = thirdRow.createCell(lastCol+1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(sigma);

            thirdFirstRow ++;
        }

        //设置标题，∑课程贡献度权重值
        cra = new CellRangeAddress(0, 0, lastCol+1, lastCol+1);
        sheet.addMergedRegion(cra);
        cell = firstRow.createCell(lastCol+1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("∑课程贡献度权重值");
        sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

        //设置标题，∑指标点达成度权重系数
        HSSFRow sigmaRow = sheet.createRow(thirdFirstRow);
        cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, 0, 1);
        sheet.addMergedRegion(cra);
        cell = sigmaRow.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("∑指标点达成度权重系数");
        //设置∑指标点达成度权重系数
        int sigmaFirstRow = 2;
        int sigmaLastRow = 2;
        for(ByyqVo bv : byyqList) {
            for(int i=0; i<bv.getPoint().size(); i++) {
                cra = new CellRangeAddress(thirdFirstRow, thirdFirstRow, sigmaFirstRow++, sigmaLastRow++);
                sheet.addMergedRegion(cra);
                cell = sigmaRow.createCell(sigmaFirstRow-1);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(1);
            }
        }

        //使用输出流进行文件下载（一个流、两个头）
        String filename = byyqmb.getName() + ".xls";
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
     * 查询所有毕业要求模板
     */
    @RequestMapping("/findAllByyqmb")
    @ResponseBody
    public List<Byyqmb> findAllByyqmb() {
        return byyqService.findAllByyqmb();
    }

    /**
     * 根据班级id查询毕业要求模板
     */
    @RequestMapping("/findByyqmbByClassId")
    @ResponseBody
    public Byyqmb findByyqmbByClassId(String classId) {
        return byyqService.findByyqmbByClassId(classId);
    }

}
