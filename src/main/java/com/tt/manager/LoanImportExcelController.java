package com.tt.manager;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Addadmin_msg;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import static com.tt.tool.Config.FILEUP_SAVEPATH;

@Controller
public class LoanImportExcelController {

    private final String xls = "xls";
    private final String xlsx = "xlsx";

    //excel表格 客户姓名 身份证号 还款卡号 卡余额 逾期金额 连续违约次数 最大违约次数 导入日期 在保余额 +还款日期 +逾期时间
    private static String[] ss =
            { "name", "id_card", "repayment_card","balance_card", "overdue_amount", "continuity", "maximum","add_time", "balance_on", "practical_date", "overdue_days"};


    @RequestMapping(value="/manager/importExcelController",method= RequestMethod.POST)
    @ResponseBody
    public String readExcel(String id_card, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String result = "0"; //导入失败
        TtMap minfo = Tools.minfo();//当前登录用户信息
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        String relatDir1 = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
        // 文件夹不存在则创建
//        File fdir = new File("D:/gitRepository/ddbx/src/main/webapp/upload/Excel/" + relatDir1);
//        ? "src/main/webapp/upload/" /* 测试模式上传文件保存路径，/\开头的为绝对路径，否则是相对路径 */
//        : "/KCDIMG/assess/upload/"; /* 生产模式上传文件保存路径，/\开头的为绝对路径，否则是相对路径 */
        File fdir = new File(FILEUP_SAVEPATH+"Excel/" + relatDir1);
        if (!fdir.exists()) {
            fdir.mkdirs();
        }
        String oriName = file.getOriginalFilename();
        File tempFile = new File(fdir.getPath() + "/" + oriName);
        System.out.println("文件保存地址:  "+tempFile);
        file.transferTo(tempFile);
        String[] string = new String[4];// 创建ss.length个二维数组，每个数组中有x条数据
        // 检查文件
        checkFile(file);
        // 获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
//        PageData rowMap = new PageData();
        TtMap rowMap = new TtMap();
//        PageData pdsession = (PageData) request.getSession().getAttribute("pd");//获取session信息
        if (workbook != null) {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                // 查询有几条相同数据
                Integer c = 0;
                // 获得当前sheet工作表
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet == null) {
                    continue;
                }
                // 获得当前sheet的开始行
                int firstRowNum = sheet.getFirstRowNum();
                // 获得当前sheet的结束行
                int lastRowNum = sheet.getLastRowNum();
                // 循环除了第一行的所有行
                for (int rowNum = firstRowNum + 1; rowNum <= lastRowNum; rowNum++) {
                    System.out.println("进入循环, 循环行数:" + rowNum);
                    // 获得当前行
                    Row row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    // 获得当前行的开始列
                    int firstCellNum = row.getFirstCellNum();
                    // 获得当前行的列数
//					int lastCellNum = row.getPhysicalNumberOfCells(); //获取不是空的列
                    int lastCellNum = row.getLastCellNum(); //获取全部的列   非空的和空的列
                    System.err.println("-------获得当前行的列数-" + lastCellNum);
//					String[] cells = new String[row.getPhysicalNumberOfCells()]; //获取不是空的列
                    String[] cells = new String[row.getLastCellNum()]; //获取全部的列   非空的和空的列
                    // 循环当前行
                    for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                        Cell cell = row.getCell(cellNum);
                        cells[cellNum] = getCellValue(cell);
                        if (cellNum == 1) {
                            System.out.println(cell + "***");
                            System.out.println(cells[cellNum] + "***");
                            // 查询有几条相同数据
//							c = recordService.count(cells[cellNum]);
                            System.out.println("-------" + cell);
                        }
                        rowMap.put("add_time", relatDir1);
                        rowMap.put(ss[cellNum], cells[cellNum]);
                    }
                    System.out.println(c + "****");
                    c = c + 1;
                    rowMap.put("repayment_periods", c + "");

                    rowMap.put("dt_add", Getnow());
                    rowMap.put("dt_edit", Getnow());
                    long loan_import_excels = Tools.recAdd(rowMap, "hbloan_import_excels");//把excel表格中的数据录入数据库表loan_import_excels
                    System.out.println("excel表格数据录入:" + loan_import_excels);

                    double overdue_amount = Double.parseDouble(rowMap.get("overdue_amount")) ;//获取excel表格中的逾期金额
                    if (overdue_amount > 0) { //该人逾期金额大于0
                        System.out.println("预期金额大于0");
                        TtMap icbc = new TtMap();
                        String yqsql = "select * from hbloan_overdue_list where c_cardno = " + rowMap.get("id_card");
                        TtMap yqmap = new TtMap();
                        yqmap = Tools.recinfo(yqsql);
                        if (0 == yqmap.size()) {
                            System.out.println("逾期表里没有此人!");
                            String sql = "select k.*,hbxx.carno as c_carno,hbxx.vincode as c_carvin from kj_icbc k,hbyh_xxzl hbxx where c_cardno="+rowMap.get("id_card")+"and hbxx.icbc_id=k.id";
                            icbc = Tools.recinfo(sql);
                            System.out.println("iiii " + icbc);
                            TtMap addOverdueClient = new TtMap();
                            addOverdueClient.put("mid_add",minfo.get("id"));
                            addOverdueClient.put("mid_edit",minfo.get("id") );
//						addOverdueClient.put("type_id",LoanModel.LoanTypeModel().get("逾期")); //???
//						addOverdueClient.put("type_status",LoanModel.LoanTypeModel().get("初级逾期") );//???
                            addOverdueClient.put("type_id","1"); //???  // 1逾期，2电催，3拖车，4诉讼，5拍卖，6结清
                            addOverdueClient.put("type_status","11");//???
                            addOverdueClient.put("icbc_id",(icbc.get("id")));
                            addOverdueClient.put("gems_id",(icbc.get("gems_id")));
                            addOverdueClient.put("gems_fs_id",(icbc.get("gems_fs_id")));
                            addOverdueClient.put("imp_name",rowMap.get("name")); //导入excel表格时的客户名字
                            addOverdueClient.put("c_name",(icbc.get("c_name")));
                            addOverdueClient.put("c_cardno",(icbc.get("c_cardno")));
                            addOverdueClient.put("c_carno",(icbc.get("c_carno")));
                            addOverdueClient.put("c_carvin",(icbc.get("c_carvin")));
                            addOverdueClient.put("overdue_amount", String.valueOf(overdue_amount)); //逾期金额
                            addOverdueClient.put("overdue_days",rowMap.get("overdue_days")); //逾期天数
                            long loan_overdue_list = Tools.recAdd(addOverdueClient, "hbloan_overdue_list");
                            System.out.println("添加逾期表:: "+loan_overdue_list);
                        }
//                        TtMap upPay = new TtMap();
//                        upPay.put("dt_edit", Getnow());
//                        upPay.put("practical_date",rowMap.get("practical_date"));
//                        upPay.put("practical_money",rowMap.get("balance_card"));
//                        upPay.put("overdue_status", String.valueOf(overdue_amount>0 ? 1:2));//1为逾期  2为正常
//                        upPay.put("overdue_money",rowMap.get("overdue_amount"));
//                        upPay.put("overdue_days",rowMap.get("overdue_days"));
//                        upPay.put("c_bank_card",rowMap.get("repayment_card"));
//                        upPay.put("c_cardno",rowMap.get("id_card"));
//                        upPay.put("icbc_id",(recinfo==null?null:recinfo.get("icbc_id")));
                        //修改还款计划表
                        String hksql = "update hbloan_repayment_schedule set\n" +
                                "dt_edit='" + Getnow() + "', \n" +
                                "practical_date='" + rowMap.get("practical_date") + "',\n" +
                                "practical_money=" + rowMap.get("balance_card") +",\n" +
                                "overdue_status=" + (overdue_amount>0 ? 1:2) + ",\n" +
                                "overdue_money=" + rowMap.get("overdue_amount") + ",\n" +
                                "overdue_days=" + rowMap.get("overdue_days") + ",\n" +
                                "c_bank_card=" + rowMap.get("repayment_card") + "\n" +
                                "where \n" +
                                "c_cardno =" + rowMap.get("id_card") + "\n" +
                                "and YEAR(should_date) =YEAR('" + rowMap.get("practical_date") + "')\n" +
                                "and MONTH(should_date) =MONTH('" + rowMap.get("practical_date") + "')\n" +
                                "and icbc_id=" + icbc.get("id");
                        System.out.println("修改还款计划表的sql :" + hksql);
                        boolean recexec = Tools.recexec(hksql);
                        System.out.println("修改还款计划表结果:" + recexec);
                    }


                }
            }
            workbook.close();
        }

//        System.out.println("mmmmmmm   " + minfo);
        TtMap map = new TtMap();
        map.put("uuid", UUID.randomUUID().toString());// 序列号
        map.put("oriName", oriName);// 文件名称
        map.put("dt_add", Getnow());// 导入时间
        map.put("financial_products", "");
        map.put("filepath", "/upload/Excel/" + relatDir1 + oriName);
        map.put("mid_add", minfo.get("id"));// 获取操作人员
        map.put("mid_name", minfo.get("name"));// 获取操作人员
        map.put("gems_fs_id",minfo.get("fsid"));// 公司ID
        map.put("gems_id", minfo.get("gemsid"));// 公司人员ID
        map.put("fsname", minfo.get("comname"));// 公司名字
        long loan_import_record = Tools.recAdd(map, "hbloan_import_record");//把excel表格中的数据录入数据库表loan_import_excels //添加excel导入文件记录

        if(loan_import_record > 0){
            result = "1";  //导入成功
        }
        return result;
    }


    public void checkFile(MultipartFile file) throws IOException {
        // 判断文件是否存在
        if (null == file) {
            System.out.println("文件不存在!!!");
            throw new FileNotFoundException("文件不存在！");
        }
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是excel文件
        if (!fileName.endsWith(xls) && !fileName.endsWith(xlsx)) {
            System.out.println(fileName + "不是Excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }


    public Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            // 获取excel文件的io流
            InputStream is = file.getInputStream();
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(xls)) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(xlsx)) {
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return workbook;
    }


    public String getCellValue(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC: // 数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: // 字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: // Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: // 空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: // 故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    public static String Getnow(){
        LocalDateTime now = LocalDateTime.now();    //获取当前系统时间
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//定义时间格式
        return now.format(dateTimeFormatter);
    }

}
