/*
 * @Description: Excel操作类，导入和导出，支持excel2003的xls格式和2007以后的xlsx格式
 * 直接与DbCtrl的数据列表无缝结合，List查询的结果可直接导出
 * @Author: tt
 * @Date: 2019-01-28 15:26:29
 * @LastEditTime: 2019-02-14 17:33:21
 * @LastEditors: tt
 */
package com.tt.tool;

import com.tt.data.TtList;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class Excel {
    private static POIFSFileSystem fs;

    @RequestMapping("/excel/demo")
    @ResponseBody
    public static void smain(String[] args) {
        if (Config.DEBUGMODE) {
            String toFile = Config.FILEUP_SAVEPATH + "excel/城市列表.xls";
            System.out.println(Tools.extractFileName(toFile));
            DbCtrl dbCtrl = new DbCtrl("admin");
            try {
                dbCtrl.nopage = true;
                dbCtrl.showall = true;
                TtList list = dbCtrl.lists("", "t.name,t.password,t.avatarurl");
                doOut(list, new String[]{"姓名", "密码", "头像地址"}, new String[]{"name", "password", "avatarurl"},
                        "/work/sd128/downloads/城市列表哈哈哈.xlsx", "excel2007", true); //
                //doIn("/work/sd128/downloads/s2.xlsx");
            } catch (Exception e) {
                Tools.logError(e.getMessage());
                e.printStackTrace();
            } finally {
                dbCtrl.closeConn();
            }
        }
    }

    /**
     * @param {type}
     * @description: 导出到Excel文件，2003/2007格式
     * ver="excel2003"为2003的xls格式，否则为2007后的xlsx格式,bDown设置是否输出到浏览器下载
     * @return:
     */
    public static boolean doOut(TtList list, String[] headers, String[] fields, String toFile,
                                String ver, boolean bDown) {
        boolean result = false;
        if (!Tools.createDir(Tools.extractFilePath(toFile))) { // 保存文件夹失败
            return false;
        }
        if (ver.equals("excel2003")) {
            HSSFWorkbook wb = new HSSFWorkbook();

            // 第二步创建sheet
            HSSFSheet sheet = wb.createSheet("sheet1");

            // 第三步创建行row:添加表头0行
            HSSFRow row = sheet.createRow(0);
            HSSFCellStyle style = wb.createCellStyle();
            // style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

            // 第四步创建单元格
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i); // 第一个单元格
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style);
            }

            for (int i = 0; i < list.size(); i++) {
                Map<String, String> info = list.get(i);
                // 创建行
                row = sheet.createRow(i + 1);
                // 创建单元格并且添加数据
                for (int k = 0; k < fields.length; k++) {
                    row.createCell(k).setCellValue(info.get(fields[k]));
                }

            }
            // 第六步将生成excel文件保存到指定路径下
            try {
                if (Tools.fileExists(toFile)) { // 文件已经存在
                    toFile = toFile + Tools.getTimeMd5FileName() + ".xls";
                }
                FileOutputStream fout = new FileOutputStream(toFile);
                wb.write(fout);
                fout.close();
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Excel文件生成成功...");
            result = true;
        } else {
            // 第一步创建workbook
            XSSFWorkbook wb = new XSSFWorkbook();

            // 第二步创建sheet
            XSSFSheet sheet = wb.createSheet("sheet1");

            // 第三步创建行row:添加表头0行
            XSSFRow row = sheet.createRow(0);
            XSSFCellStyle style = wb.createCellStyle();
            // style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //居中

            // 第四步创建单元格
            for (int i = 0; i < headers.length; i++) {
                XSSFCell cell = row.createCell(i); // 第一个单元格
                cell.setCellValue(headers[i]);
                cell.setCellStyle(style);
            }

            for (int i = 0; i < list.size(); i++) {
                Map<String, String> info = list.get(i);
                // 创建行
                row = sheet.createRow(i + 1);
                // 创建单元格并且添加数据
                for (int k = 0; k < fields.length; k++) {
                    row.createCell(k).setCellValue(info.get(fields[k]));
                }

            }
            // 第六步将生成excel文件保存到指定路径下
            try {
                if (Tools.fileExists(toFile)) { // 文件已经存在
                    toFile = toFile + Tools.getTimeMd5FileName() + ".xlsx";
                }
                FileOutputStream fout = new FileOutputStream(toFile);
                wb.write(fout);
                fout.close();
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Excel文件生成成功...");
            result = true;
        }
        if (result && bDown) { // 直接下载
            String fileName = Tools.extractFileName(toFile) + "_" + Tools.getNowDateFileName();
            String downFileName = ver.equals("excel2003") ? fileName + ".xls" : fileName + ".xlsx";
            Zip.outDownFile(downFileName, toFile, true);
        }
        return result;
    }

    /**
     * @param {type}
     * @description: 导入excel文件
     * @return:
     */
    public static void doIn(String sFile) {
        try {
            // 对读取Excel表格标题测试
            String ver = "excel2003";
            String[] title = null;
            InputStream is = new FileInputStream(sFile);
            try {
                title = readExcelTitle(is, ver);
                is.close();
            } catch (Exception e) {
                ver = "excel2007";
            }
            if (title == null) {
                ver = "excel2007";
                try {
                    InputStream is3 = new FileInputStream(sFile);
                    title = readExcelTitle(is3, "excel2007");
                    is3.close();
                } catch (Exception e) {
                }
            }
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }
            System.out.println();
            try {
                InputStream is2 = new FileInputStream(sFile);
                Map<Integer, String> map = readExcelContent(is2, ver);
                System.out.println("获得Excel表格的内容:");
                is2.close();
                // 这里由于xls合并了单元格需要对索引特殊处理
                for (int i = 1; i <= map.size(); i++) {
                    System.out.println(map.get(i));
                }
            } catch (Exception e) {
            }

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }

    /**
     * 读取Excel表格表头的内容
     *
     * @param is
     * @return String 表头内容的数组
     */
    public static String[] readExcelTitle(InputStream is, String ver) {
        if (ver.equals("excel2003")) {
            HSSFWorkbook wb;
            HSSFSheet sheet;
            HSSFRow row;
            try {
                fs = new POIFSFileSystem(is);
                wb = new HSSFWorkbook(fs);
                sheet = wb.getSheetAt(0);
                // 得到首行的row
                row = sheet.getRow(0);
                // 标题总列数
                int colNum = row.getPhysicalNumberOfCells();
                String[] title = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    title[i] = getCellFormatValue(row.getCell((short) i));
                }
                wb.close();
                return title;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            XSSFWorkbook wb;
            XSSFSheet sheet;
            XSSFRow row;
            try {
                // fs = new POIFSFileSystem(is);
                wb = new XSSFWorkbook(is);
                sheet = wb.getSheetAt(0);
                // 得到首行的row
                row = sheet.getRow(0);
                // 标题总列数
                int colNum = row.getPhysicalNumberOfCells();
                String[] title = new String[colNum];
                for (int i = 0; i < colNum; i++) {
                    title[i] = getCellFormatValue2007(row.getCell((short) i));
                }
                wb.close();
                return title;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    /**
     * 读取Excel数据内容
     *
     * @param is
     * @return Map 包含单元格数据内容的Map对象
     */
    public static Map<Integer, String> readExcelContent(InputStream is, String ver) {
        Map<Integer, String> content = new HashMap<Integer, String>();
        if (ver.equals("excel2003")) {
            String str = "";
            HSSFWorkbook wb;
            HSSFSheet sheet;
            HSSFRow row;
            try {
                fs = new POIFSFileSystem(is);
                wb = new HSSFWorkbook(fs);
                sheet = wb.getSheetAt(0);
                // 得到总行数
                int rowNum = sheet.getLastRowNum();
                // 由于第0行和第1行已经合并了 在这里索引从2开始
                row = sheet.getRow(1);
                int colNum = row.getPhysicalNumberOfCells();
                // 正文内容应该从第二行开始,第一行为表头的标题
                for (int i = 1; i <= rowNum; i++) {
                    row = sheet.getRow(i);
                    int j = 0;
                    while (j < colNum) {
                        str += getCellFormatValue(row.getCell((short) j)).trim() + "-";
                        j++;
                    }
                    content.put(i, str);
                    str = "";
                }
                wb.close();
                return content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            String str = "";
            XSSFWorkbook wb;
            XSSFSheet sheet;
            XSSFRow row;
            try {
                // fs = new POIFSFileSystem(is);
                wb = new XSSFWorkbook(is);
                sheet = wb.getSheetAt(0);
                // 得到总行数
                int rowNum = sheet.getLastRowNum();
                // 由于第0行和第1行已经合并了 在这里索引从2开始
                row = sheet.getRow(1);
                int colNum = row.getPhysicalNumberOfCells();
                // 正文内容应该从第二行开始,第一行为表头的标题
                for (int i = 1; i <= rowNum; i++) {
                    row = sheet.getRow(i);
                    int j = 0;
                    while (j < colNum) {
                        str += getCellFormatValue2007(row.getCell((short) j)).trim() + "-";
                        j++;
                    }
                    content.put(i, str);
                    str = "";
                }
                wb.close();
                return content;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC:
                case FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell
     * @return
     */
    private static String getCellFormatValue2007(XSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum()) {
                // 如果当前Cell的Type为NUMERIC
                case NUMERIC:
                case FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        // 取得当前Cell的数值
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                // 如果当前Cell的Type为STRIN
                case STRING:
                    // 取得当前的Cell字符串
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                // 默认的Cell值
                default:
                    cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }
}