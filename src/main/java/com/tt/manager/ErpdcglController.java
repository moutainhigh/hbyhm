package com.tt.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tt.data.TtMap;
import com.tt.tool.DbTools;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErpdcglController {
    @PostMapping(value = "/manager/hxglajaxpost")
    @ResponseBody
    public TtMap ajaxposts(HttpServletRequest request) {
        System.out.println("进入核销");
        TtMap post = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println("参数列表为:" + post);
        TtMap res = new TtMap();
        String msg = "编辑成功";
        TtMap minfo = Tools.minfo();//当前登录用户信息
        System.out.println("minfo：" + minfo);

        TtMap map = new TtMap();
        map.put("qryid", post.get("lolId"));
        map.put("mid_add", minfo.get("gemsid"));
        map.put("mid_edit", minfo.get("gemsid"));
        map.put("dt_add", LoanImportExcelController.Getnow());
        map.put("dt_edit", LoanImportExcelController.Getnow());
        map.put("result_msg", post.get("result_msg"));
        map.put("icbc_id", post.get("icbc_id"));
        switch (post.get("type_status")) {
            case "71":   //未核销的信息录入提交
                DbTools dbTools1 = new DbTools();
                dbTools1.recupdate("update hbloan_overdue_list set type_id=7,type_status=72 where icbc_id="+post.get("icbc_id"));

                map.put("type_id", "7");
                map.put("type_status", "72");
                map.put("remark", "未核销页进行信息录入栏提交");
                Tools.recAdd(map, "hbloan_overdue_list_result");
                break;
            case "72": //已核销的信息录入提交
                String coolStatus = post.get("coolStatus"); //获取处置结果
                DbTools dbTools = new DbTools();
                dbTools.recupdate("update hbloan_overdue_list set type_id=6,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));
                if ("61".equals(coolStatus)) {  //正常结清

                    map.put("type_id", "6");
                    map.put("type_status", "61");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                } else if ("62".equals(coolStatus)) { //提前结清

                    map.put("type_id", "6");
                    map.put("type_status", "62");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                }  else if ("63".equals(coolStatus)) { //强制结清

                    map.put("type_id", "6");
                    map.put("type_status", "63");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                } else if ("64".equals(coolStatus)) { //亏损结清

                    map.put("type_id", "6");
                    map.put("type_status", "64");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                }
                break;
        }

        res.put("msg", msg);
        return res;
    }

    @PostMapping(value = "/manager/ssglajaxpost")
    @ResponseBody
    public TtMap ajaxpostq(HttpServletRequest request) {
        System.out.println("进入诉讼");
        TtMap post = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println("参数列表为:" + post);
        TtMap res = new TtMap();
        String msg = "编辑成功";
        TtMap minfo = Tools.minfo();//当前登录用户信息
        System.out.println("minfo：" + minfo);

        TtMap map = new TtMap();
        map.put("qryid", post.get("lolId"));
        map.put("mid_add", minfo.get("gemsid"));
        map.put("mid_edit", minfo.get("gemsid"));
        map.put("dt_add", LoanImportExcelController.Getnow());
        map.put("dt_edit", LoanImportExcelController.Getnow());

        if ("41".equals(post.get("type_status"))) {   //未诉讼
            DbTools dbTools = new DbTools();
            dbTools.recupdate("update hbloan_overdue_list set type_id=4,type_status=42 where icbc_id="+post.get("icbc_id"));

            map.put("type_id", "4");
            map.put("type_status", "41");
            map.put("remark", "开始诉讼");
            map.put("result_msg", post.get("result_msg"));
            map.put("icbc_id", post.get("icbc_id"));
            Tools.recAdd(map, "hbloan_overdue_list_result");
        } else if ("42".equals(post.get("type_status"))) {
            DbTools dbTools = new DbTools();
            dbTools.recupdate("update hbloan_overdue_list set type_id=7,type_status=71 where icbc_id="+post.get("icbc_id"));

            map.put("type_id", "4");
            map.put("type_status", "42");
            map.put("remark", "已诉讼提交进入未核销");
            map.put("result_msg", post.get("result_msg"));
            map.put("icbc_id", post.get("icbc_id"));
            Tools.recAdd(map, "hbloan_overdue_list_result");
        }

        res.put("msg", msg);
        return res;
    }



    @PostMapping(value = "/manager/pmglajaxpost")
    @ResponseBody
    public TtMap ajaxposta(HttpServletRequest request) {
        System.out.println("进入拍卖");
        TtMap post = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println("参数列表为:" + post);
        TtMap res = new TtMap();
        String msg = "编辑成功";
        TtMap minfo = Tools.minfo();//当前登录用户信息
        System.out.println("minfo：" + minfo);

        TtMap map = new TtMap();
        map.put("qryid", post.get("lolId"));
        map.put("mid_add", minfo.get("gemsid"));
        map.put("mid_edit", minfo.get("gemsid"));
        map.put("dt_add", LoanImportExcelController.Getnow());
        map.put("dt_edit", LoanImportExcelController.Getnow());
        map.put("result_msg", post.get("result_msg"));
        map.put("icbc_id", post.get("icbc_id"));
        switch (post.get("type_status")) {
            case "51":   //未拍卖的信息录入提交
                String coolStatus = post.get("coolStatus"); //获取处置结果
                if ("53".equals(coolStatus)) {  //盈利(拍卖完成)
                    DbTools dbTools = new DbTools();
                    dbTools.recupdate("update hbloan_overdue_list set type_id=5,type_status=53 where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "53");
                    map.put("remark", "未拍卖页进行信息录入栏提交");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                } else if ("41".equals(coolStatus)) { //亏损>6000(拍卖完成)[进入诉讼]
                    DbTools dbTools = new DbTools();
                    dbTools.recupdate("update hbloan_overdue_list set type_id=4,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "52");
                    map.put("remark", "亏损进入诉讼");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                }  else if ("71".equals(coolStatus)) { //亏损<6000(拍卖完成)[进入未核销]
                    DbTools dbTools = new DbTools();
                    dbTools.recupdate("update hbloan_overdue_list set type_id=7,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "52");
                    map.put("remark", "亏损进入核销");
                    Tools.recAdd(map, "hbloan_overdue_list_result");
                }
                break;
            case "52": //亏损(拍卖完成)的信息录入提交

                break;

            case "53":   //盈利(拍卖完成)的信息录入提交
                DbTools dbTools = new DbTools();    //进入未核销
                dbTools.recupdate("update hbloan_overdue_list set type_id=7,type_status=71 where icbc_id="+post.get("icbc_id"));

                map.put("type_id", "5");
                map.put("type_status", "53");
                map.put("remark", "盈利(拍卖完成)进行信息录入栏提交");
                Tools.recAdd(map, "hbloan_overdue_list_result");
                break;
        }
        res.put("msg", msg);
        return res;
    }



    @PostMapping(value = "/manager/jrdcajaxpost")
    @ResponseBody
    public TtMap ajaxpost(HttpServletRequest request) {
        System.out.println("进入电催");
        TtMap post = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println("参数列表为:" + post);
        TtMap res = new TtMap();
        String msg = "编辑成功";
        TtMap minfo = Tools.minfo();//当前登录用户信息
        System.out.println("minfo：" + minfo);

        switch (post.get("dctype_id")){
            case "1":
                DbTools dbTools = new DbTools();//客户逾期名单手动进入电催
                dbTools.recupdate("update hbloan_overdue_list set type_id=2,type_status=0 where icbc_id="+post.get("icbc_id"));

                String sql = "select * from hbloan_overdue_list where icbc_id = " + post.get("icbc_id");
                TtMap recinfo = Tools.recinfo(sql);     //查询loan_overdue_list信息

                TtMap map = new TtMap();
                map.put("qryid", recinfo.get("id"));
                map.put("mid_add", minfo.get("gemsid"));
                map.put("mid_edit", minfo.get("gemsid"));
                map.put("dt_add", LoanImportExcelController.Getnow());
                map.put("dt_edit", LoanImportExcelController.Getnow());
                map.put("type_id", "2");
                map.put("remark", "手动进入电催");
                map.put("result_msg", "手动点击,逾期客户进入电催");
                map.put("icbc_id", recinfo.get("icbc_id"));
                Tools.recAdd(map, "hbloan_overdue_list_result");
            break;
            case "2":                       //电催录入栏
                TtMap map1 = new TtMap();
                map1.put("qryid", post.get("lolId"));
                map1.put("mid_add", minfo.get("gemsid"));
                map1.put("mid_edit", minfo.get("gemsid"));
                map1.put("dt_add", LoanImportExcelController.Getnow());
                map1.put("dt_edit", LoanImportExcelController.Getnow());
                map1.put("type_id", post.get("type_id"));
                map1.put("remark", "用户提交电催录入栏");
                map1.put("result_msg", post.get("result_msg"));
                map1.put("icbc_id", post.get("icbc_id"));
                Tools.recAdd(map1, "hbloan_overdue_list_result");
            break;
            case "3":           //申请拖车||诉讼
                DbTools dbTools1 = new DbTools();
                dbTools1.recupdate("update hbloan_overdue_list set type_id=" + post.get("type_id") + ",type_status=" + post.get("type_status") + " where icbc_id=" + post.get("icbc_id"));

                TtMap map2 = new TtMap();
                map2.put("qryid", post.get("lolId"));
                map2.put("mid_add", minfo.get("gemsid"));
                map2.put("mid_edit", minfo.get("gemsid"));
                map2.put("dt_add", LoanImportExcelController.Getnow());
                map2.put("dt_edit", LoanImportExcelController.Getnow());
                map2.put("type_id", post.get("type_id"));
                map2.put("type_status", post.get("type_status"));
                map2.put("remark", post.get("result_msg"));
                map2.put("result_msg", post.get("result_msg"));
                map2.put("icbc_id", post.get("icbc_id"));
                Tools.recAdd(map2, "hbloan_overdue_list_result");
                break;
            case "4":           //拖车信息录入栏提交
                DbTools dbTools2 = new DbTools();
                TtMap map3 = new TtMap();
                map3.put("qryid", post.get("lolId"));
                map3.put("mid_add", minfo.get("gemsid"));
                map3.put("mid_edit", minfo.get("gemsid"));
                map3.put("dt_add", LoanImportExcelController.Getnow());
                map3.put("dt_edit", LoanImportExcelController.Getnow());
                map3.put("icbc_id", post.get("icbc_id"));
                if (post.get("tctype").equals("31")){        //拖车未受理信息录入提交
                    dbTools2.recupdate("update hbloan_overdue_list set type_status=32 where icbc_id=" + post.get("icbc_id"));

                    map3.put("type_id", post.get("type_id"));
                    map3.put("type_status", "32");
                    map3.put("remark", "拖车(未受理)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "hbloan_overdue_list_result");

                } else if(post.get("tctype").equals("32")){  //拖车已受理信息录入提交
                    String coolStatus = post.get("coolStatus");     //拖车结果  33:完成 34:失败
                    if ("33".equals(coolStatus)) {
                        dbTools2.recupdate("update hbloan_overdue_list set type_id=3,type_status=33 where icbc_id=" + post.get("icbc_id"));

                        map3.put("type_id", "3");
                        map3.put("type_status", "33");
                        map3.put("remark", "拖车完成");
                        map3.put("result_msg", post.get("result_msg"));
                        map3.put("result_value", JSONObject.parseObject(JSON.toJSONString(map3)).toString());
                        Tools.recAdd(map3, "hbloan_overdue_list_result");
                    } else if ("34".equals(coolStatus)) {  //34:拖车失败
                        dbTools2.recupdate("update hbloan_overdue_list set type_id=7,type_status=71 where icbc_id=" + post.get("icbc_id"));

                        map3.put("type_id", "3");
                        map3.put("type_status", coolStatus);
                        map3.put("remark", "拖车(已受理)信息录入栏提交");
                        map3.put("result_msg", post.get("result_msg"));
                        Tools.recAdd(map3, "hbloan_overdue_list_result");
                    }
                } else if(post.get("tctype").equals("33")) {  //拖车完成信息录入提交
                    String coolStatus = post.get("coolStatus");     //拖车结果  51:拍卖 63:强制结清
                    if (coolStatus.equals("51")){
                        dbTools2.recupdate("update hbloan_overdue_list set type_id=5,type_status=" + coolStatus + " where icbc_id=" + post.get("icbc_id"));
                        map3.put("type_id", "5");
                    } else {
                        dbTools2.recupdate("update hbloan_overdue_list set type_id=6,type_status=" + coolStatus + " where icbc_id=" + post.get("icbc_id"));
                        map3.put("type_id", "6");
                    }

                    map3.put("type_status", coolStatus);
                    map3.put("remark", "拖车(完成)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "hbloan_overdue_list_result");
                } else if(post.get("tctype").equals("34")) {  //拖车失败信息录入提交
                    map3.put("type_id", post.get("type_id"));
                    map3.put("type_status", "34");
                    map3.put("remark", "拖车(失败)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "hbloan_overdue_list_result");
                }
                break;
            default:
                break;
        }
        res.put("msg", msg);
        return res;
    }

}
