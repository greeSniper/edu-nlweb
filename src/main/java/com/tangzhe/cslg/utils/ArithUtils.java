package com.tangzhe.cslg.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 数字工具类
 */
public class ArithUtils {

    /**
     * 保留两位小数
     */
    public static Double myDecimal(Double num) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static void main(String[] args) throws IOException {
        FileOutputStream fos=new FileOutputStream("F:\\download\\test.xls");

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        CellRangeAddress cra = new CellRangeAddress(3, 4, 2, 2);
        sheet.addMergedRegion(cra);
        HSSFRow row = sheet.createRow(3);
        HSSFCell cell = row.createCell(2);
        cell.setCellValue("得分(满分30分)");
        //sheet.setColumnWidth(cell.getColumnIndex(), cell.getStringCellValue().toString().length() * 512);

        workbook.write(fos);
        fos.close();

//        CellRangeAddress cra = new CellRangeAddress(0, 3, 3, 9);
//
//        //在sheet里增加合并单元格
//        sheet.addMergedRegion(cra);
//
//        HSSFRow row = sheet.createRow(0);
//
//        HSSFCell cell1 = row.createCell(3);
//
//        cell1.setCellValue("When you're right , no one remembers, when you're wrong ,no one forgets .");
//
//        //cell 位置3-9被合并成一个单元格，不管你怎样创建第4个cell还是第5个cell…然后在写数据。都是无法写入的。
//        HSSFCell cell2 = row.createCell(10);
//
//        cell2.setCellValue("what's up ! ");
//
//        workbook.write(fos);
//
//        fos.close();
    }

}
