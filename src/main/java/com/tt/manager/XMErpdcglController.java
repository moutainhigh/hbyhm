package com.tt.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tt.data.TtMap;
import com.tt.tool.DbCtrl;
import com.tt.tool.DbTools;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class XMErpdcglController {
    @PostMapping(value = "/manager/jqclajaxpostxm")
    @ResponseBody
    public TtMap ajaxpostxmjq(HttpServletRequest request) {
        System.out.println("进入结清");
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
        map.put("type_id", post.get("type_id"));
        map.put("type_status", post.get("type_status"));
        map.put("remark", "某个结清记录");
        map.put("result_msg", post.get("result_msg"));
        map.put("icbc_id", post.get("icbc_id"));
        Tools.recAdd(map, "xmloan_overdue_list_result");

        res.put("msg", msg);
        return res;
    }


    @PostMapping(value = "/manager/hxglajaxpostxm")
    @ResponseBody
    public TtMap ajaxpostxms(HttpServletRequest request) {
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
                dbTools1.recupdate("update xmloan_overdue_list set type_id=7,type_status=72 where icbc_id="+post.get("icbc_id"));

                map.put("type_id", "7");
                map.put("type_status", "72");
                map.put("remark", "未核销页进行信息录入栏提交");
                Tools.recAdd(map, "xmloan_overdue_list_result");
                break;
            case "72": //已核销的信息录入提交
                String coolStatus = post.get("coolStatus"); //获取处置结果
                DbTools dbTools = new DbTools();
                dbTools.recupdate("update xmloan_overdue_list set type_id=6,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));
                if ("61".equals(coolStatus)) {  //正常结清

                    map.put("type_id", "6");
                    map.put("type_status", "61");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                } else if ("62".equals(coolStatus)) { //提前结清

                    map.put("type_id", "6");
                    map.put("type_status", "62");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                }  else if ("63".equals(coolStatus)) { //强制结清

                    map.put("type_id", "6");
                    map.put("type_status", "63");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                } else if ("64".equals(coolStatus)) { //亏损结清

                    map.put("type_id", "6");
                    map.put("type_status", "64");
                    map.put("remark", "已核销页进行信息录入栏提交");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                }
                break;
        }

        res.put("msg", msg);
        return res;
    }

    @PostMapping(value = "/manager/ssglajaxpostxm")
    @ResponseBody
    public TtMap ajaxpostxmq(HttpServletRequest request) {
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
            dbTools.recupdate("update xmloan_overdue_list set type_id=4,type_status=42 where icbc_id="+post.get("icbc_id"));

            map.put("type_id", "4");
            map.put("type_status", "41");
            map.put("remark", "开始诉讼");
            map.put("result_msg", post.get("result_msg"));
            map.put("icbc_id", post.get("icbc_id"));
            Tools.recAdd(map, "xmloan_overdue_list_result");
        } else if ("42".equals(post.get("type_status"))) {
            DbTools dbTools = new DbTools();
            dbTools.recupdate("update xmloan_overdue_list set type_id=7,type_status=71 where icbc_id="+post.get("icbc_id"));

            map.put("type_id", "4");
            map.put("type_status", "42");
            map.put("remark", "已诉讼提交进入未核销");
            map.put("result_msg", post.get("result_msg"));
            map.put("icbc_id", post.get("icbc_id"));
            Tools.recAdd(map, "xmloan_overdue_list_result");
        }

        res.put("msg", msg);
        return res;
    }



    @PostMapping(value = "/manager/pmglajaxpostxm")
    @ResponseBody
    public TtMap ajaxpostxma(HttpServletRequest request) {
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
                    dbTools.recupdate("update xmloan_overdue_list set type_id=5,type_status=53 where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "53");
                    map.put("remark", "未拍卖页进行信息录入栏提交");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                } else if ("41".equals(coolStatus)) { //亏损>6000(拍卖完成)[进入诉讼]
                    DbTools dbTools = new DbTools();
                    dbTools.recupdate("update xmloan_overdue_list set type_id=4,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "52");
                    map.put("remark", "亏损进入诉讼");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                }  else if ("71".equals(coolStatus)) { //亏损<6000(拍卖完成)[进入未核销]
                    DbTools dbTools = new DbTools();
                    dbTools.recupdate("update xmloan_overdue_list set type_id=7,type_status=" +coolStatus+ " where icbc_id="+post.get("icbc_id"));

                    map.put("type_id", "5");
                    map.put("type_status", "52");
                    map.put("remark", "亏损进入核销");
                    Tools.recAdd(map, "xmloan_overdue_list_result");
                }
                break;
            case "52": //亏损(拍卖完成)的信息录入提交

                break;

            case "53":   //盈利(拍卖完成)的信息录入提交
                DbTools dbTools = new DbTools();    //进入未核销
                dbTools.recupdate("update xmloan_overdue_list set type_id=7,type_status=71 where icbc_id="+post.get("icbc_id"));

                map.put("type_id", "5");
                map.put("type_status", "53");
                map.put("remark", "盈利(拍卖完成)进行信息录入栏提交");
                Tools.recAdd(map, "xmloan_overdue_list_result");
                break;
        }
        res.put("msg", msg);
        return res;
    }



    @PostMapping(value = "/manager/jrdcajaxpostxm")
    @ResponseBody
    public TtMap ajaxpostxm(HttpServletRequest request) {
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
                dbTools.recupdate("update xmloan_overdue_list set type_id=2,type_status=0 where icbc_id="+post.get("icbc_id"));

                String sql = "select * from xmloan_overdue_list where icbc_id = " + post.get("icbc_id");
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
                Tools.recAdd(map, "xmloan_overdue_list_result");
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
                Tools.recAdd(map1, "xmloan_overdue_list_result");
            break;
            case "3":           //申请拖车||诉讼
                DbTools dbTools1 = new DbTools();
                dbTools1.recupdate("update xmloan_overdue_list set type_id=" + post.get("type_id") + ",type_status=" + post.get("type_status") + " where icbc_id=" + post.get("icbc_id"));

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
                Tools.recAdd(map2, "xmloan_overdue_list_result");
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
                    dbTools2.recupdate("update xmloan_overdue_list set type_status=32 where icbc_id=" + post.get("icbc_id"));

                    map3.put("type_id", post.get("type_id"));
                    map3.put("type_status", "32");
                    map3.put("remark", "拖车(未受理)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "xmloan_overdue_list_result");

                } else if(post.get("tctype").equals("32")){  //拖车已受理信息录入提交
                    String coolStatus = post.get("coolStatus");     //拖车结果  33:完成 34:失败
                    if ("33".equals(coolStatus)) {
                        dbTools2.recupdate("update xmloan_overdue_list set type_id=3,type_status=33 where icbc_id=" + post.get("icbc_id"));

                        map3.put("type_id", "3");
                        map3.put("type_status", "33");
                        map3.put("remark", "拖车完成");
                        map3.put("result_msg", post.get("result_msg"));
                        map3.put("coolTime", post.get("coolTime"));
                        map3.put("coolAddress", post.get("coolAddress"));
                        map3.put("coolVideo", post.get("coolVideo")); //JSONObject.parseObject(JSON.toJSONString(map3)).toString()
                        map3.put("result_value", JSONObject.parseObject(JSON.toJSONString(map3)).toString());
                        Tools.recAdd(map3, "xmloan_overdue_list_result");
                    } else if ("34".equals(coolStatus)) {  //34:拖车失败
                        dbTools2.recupdate("update xmloan_overdue_list set type_id=7,type_status=71 where icbc_id=" + post.get("icbc_id"));

                        map3.put("type_id", "3");
                        map3.put("type_status", coolStatus);
                        map3.put("remark", "拖车(已受理)信息录入栏提交");
                        map3.put("result_msg", post.get("result_msg"));
                        Tools.recAdd(map3, "xmloan_overdue_list_result");
                    }
                } else if(post.get("tctype").equals("33")) {  //拖车完成信息录入提交
                    String coolStatus = post.get("coolStatus");     //拖车结果  51:拍卖 63:强制结清
                    if (coolStatus.equals("51")){
                        dbTools2.recupdate("update xmloan_overdue_list set type_id=5,type_status=" + coolStatus + " where icbc_id=" + post.get("icbc_id"));
                    } else {
                        dbTools2.recupdate("update xmloan_overdue_list set type_id=6,type_status=" + coolStatus + " where icbc_id=" + post.get("icbc_id"));
                    }
                    map3.put("type_id", "3");
                    map3.put("type_status", coolStatus);
                    map3.put("remark", "拖车(完成)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "xmloan_overdue_list_result");
                } else if(post.get("tctype").equals("34")) {  //拖车失败信息录入提交
                    map3.put("type_id", post.get("type_id"));
                    map3.put("type_status", "34");
                    map3.put("remark", "拖车(失败)信息录入栏提交");
                    map3.put("result_msg", post.get("result_msg"));
                    Tools.recAdd(map3, "xmloan_overdue_list_result");
                }
                break;
            default:
                break;
        }
        res.put("msg", msg);
        return res;
    }

    //添加或修改配置
    @PostMapping("/manager/loanConfigxm")
    @ResponseBody
    public String loanConfig(
            String overdue_one,
            String overdue_two,
            String overdue_three,
            String overdue_to_phone,
            String overdue_money,
            HttpServletRequest request){
        //获取当前操作人信息
        TtMap pdsession= Tools.minfo();
        System.err.println(pdsession+"--6666666666666666699999999");
        //1先查询是否有公司配置信息 ，
        //2如果有展示信息，修改提交保存
        //3如果没有填写信息，提交保存
        String configSql = "select * from xmloan_config where gems_fs_id="+pdsession.get("icbc_erp_fsid");
        TtMap getConfig = Tools.recinfo(configSql);
        System.err.println(getConfig+"---");
        TtMap updateConfig = new TtMap();
        updateConfig.put("overdue_one",overdue_one);
        updateConfig.put("overdue_two",overdue_two);
        updateConfig.put("overdue_three",overdue_three);
        updateConfig.put("overdue_to_phone",overdue_to_phone);
        updateConfig.put("overdue_money",overdue_money);
        updateConfig.put("gems_fs_id",pdsession.get("icbc_erp_fsid"));
        updateConfig.put("mid_edit",pdsession.get("icbc_erp_fsid"));
        String companyNameSql = "select name from assess_fs where id="+pdsession.get("icbc_erp_fsid");
        TtMap getCName = Tools.recinfo(companyNameSql);
        updateConfig.put("company_name",getCName.get("name"));
        Long a = 0L;
        DbCtrl dbCtrl = new DbCtrl(null,"xmloan_config");
        if(Tools.myIsNull(getConfig.get("id"))){ //公司此条配置不存在,就添加
            updateConfig.put("mid_add",pdsession.get("icbc_erp_fsid"));
            a = dbCtrl.add(updateConfig);
        }else{
            a = (long)dbCtrl.edit(updateConfig,Long.parseLong(getConfig.get("id")));
        }
        String result = "failure";
        if(a > 0){
            result = "successful!";
        }
        return result;
    }

}
