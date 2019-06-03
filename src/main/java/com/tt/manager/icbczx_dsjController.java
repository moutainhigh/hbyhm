package com.tt.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tt.data.TtMap;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tool.Config;
import com.tt.tool.HttpTools;
import com.tt.tool.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.PageData;
import javax.swing.plaf.synth.SynthSpinnerUI;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class icbczx_dsjController {

    public final static String merchantNo = Config.TESTMODE?"QDS04477":"QDS04477";
    public final static String loginId = "kuaichedao";
    public final static String accessKey = "5D883646019F232F9E528D21C0E4C353";
    public final static String shopNumber = "QDS04477";
    public final static String url = Config.TESTMODE
            ? "https://www.qhrtcb.com/hbservice/risk/bodyguard"
            : "http://www.hibdata.com:7070/hbservice/risk/bodyguard";


    public static String getdsjzxhttp(Map<String, Object> map)
            throws UnsupportedEncodingException {

        TtMap headers = new TtMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        map.put("merchantNo", merchantNo);
        map.put("loginId", loginId);
        map.put("accessKey", accessKey);
        map.put("shopNumber", shopNumber);
        // map.put("requestId",
        // getnewstr(UUID.randomUUID().toString().replaceAll("-", "")));
        // map.put("accountName", "皮晴晴");
        // map.put("accountMobile", "13333333333");
        // map.put("idNumber", "330105196602220623");
        String mpstr = HttpTools.httpClientPost(url, map, "UTF-8", headers);
        // mpstr = new String(mpstr.getBytes("GBK"), "ISO-8859-1");
        // mpstr = new String(mpstr.getBytes("ISO-8859-1"), "UTF-8");
        // System.out.println(mpstr);
        return mpstr;
    }


    /**
     * 字符串去重
     *
     * @param args
     */

    public static String getnewstr(String str) {
        String s = ""; // 新建一个数组保存字符
        // String str = UUID.randomUUID().toString().replaceAll("-", "");
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i); // 取出字符串中的字符数
            if (s.indexOf(c) == -1) { // 判断新建数组中是否含有某个字符
                s += c;
            }
        }
        System.out.println(s);
        return s;
    }

    @PostMapping(value = "manager/getxdfx_dsjzx")
    @ResponseBody
    public String getxdfx_dsjzx(HttpServletRequest request) {
        TtMap paramemap = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        long nid = Tools.myIsNull(paramemap.get("id")) ? 0 : Tools.strToLong(paramemap.get("id"));
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("requestId", UUID.randomUUID().toString().replaceAll("-", ""));
        map.put("accountName", paramemap.get("name"));
        map.put("accountMobile", paramemap.get("mobileNo"));
        map.put("idNumber", paramemap.get("idCardNo"));
        TtMap icbc = new TtMap();
        String result = "";
        try {
            String report_id = "";
            result = getdsjzxhttp(map);
            System.out.println("map:"+map);
            // result = new String(result.getBytes("GBK"), "ISO-8859-1");
            // result = new String(result.getBytes("ISO-8859-1"), "UTF-8");
            JSONObject resjJsonObject = JSONObject.parseObject(result);
            if (resjJsonObject.get("status") != null && !resjJsonObject.get("status").equals("")) {
                JSONObject status = JSONObject.parseObject(resjJsonObject.getString("status"));
                if (status.get("responseCode").equals("0000")) {
                    report_id = status.getString("requestId");
                } else {
                    report_id = "";
                }
            }
//            PageData pd = new PageData();
            TtMap dsjpd = new TtMap();
            icbc.put("id", paramemap.get("id"));
            switch (paramemap.get("dsj_type")) {
                case "1":
                    dsjpd.put("result1", result);
                    // icbc.setDsj_result(result);
                    icbc.put("dsj_report_id", report_id);
                    icbc.put("dsj_result_time", Tools.getnow());
                    break;
                case "2":
                    dsjpd.put("result2", result);
                    icbc.put("po_dsj_report_id", report_id);
                    // icbc.setPo_dsj_result(result);
                    break;
                case "3":
                    dsjpd.put("result3", result);
                    icbc.put("gjr_dsj_report_id1", report_id);
                    // icbc.setGjr_dsj_result1(result);
                case "4":
                    dsjpd.put("result4", result);
                    icbc.put("gjr_dsj_report_id2", report_id);
                    // icbc.setGjr_dsj_result2(result);
                    break;
            }
            Tools.recEdit(icbc, "kj_icbc", nid);

            TtMap dsj = Tools.recinfo("select * from kjs_icbc_dsj where icbc_id=" + nid);
            if (dsj != null && dsj.size() != 0) {
                dsjpd.put("dt_edit", Tools.getnow());
                dsjpd.put("dt_add", Tools.getnow());
                dsjpd.put("icbc_id", paramemap.get("id"));
                dsjpd.put("id", dsj.get("id"));
                Tools.recEdit(dsjpd, "kjs_icbc_dsj", Long.parseLong(dsj.get("id")));
            } else {
                dsjpd.put("dt_edit", Tools.getnow());
                dsjpd.put("dt_add", Tools.getnow());
                dsjpd.put("icbc_id", paramemap.get("id"));
                Tools.recAdd(dsjpd, "kjs_icbc_dsj");
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping("manager/getxdfx_dsjzx_result")
    public String getxdfx_dsjzx_result(HttpServletRequest request) {
        TtMap paramemap = Tools.getPostMap(request, false);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println("post::" + paramemap);
        String sql = "select ki.*,\n" +
                "fs.name as gname,\n" +
                "gems.name as pname \n" +
                "from kj_icbc ki\n" +
                "LEFT JOIN assess_fs fs on ki.gems_fs_id=fs.id\n" +
                "LEFT JOIN assess_gems gems on ki.gems_id=gems.id\n" +
                "where ki.id=" + paramemap.get("id");
        TtMap icbc = Tools.recinfo(sql);
        TtMap dsj = Tools.recinfo("select * from kjs_icbc_dsj where icbc_id=" + paramemap.get("id"));
        String result = "";
        switch (paramemap.get("dsjtype")) {
            case "1":
                result = (String) dsj.get("result1");
                break;
            case "2":
                result = (String) dsj.get("result2");
                break;
            case "3":
                result = (String) dsj.get("result3");
                break;
            case "4":
                result = (String) dsj.get("result4");
                break;
        }
        if (result != null) {
            JSONObject res = JSONObject.parseObject(Tools.jsonDeCode(result.replace(":\"{", ":{").replace("}\"", "}")).toString());
            if (res.get("detail") != null && !res.get("detail").equals("")) {
                JSONObject detail = JSONObject.parseObject(res
                        .getString("detail"));
                System.out.println("detail:" + detail);
                if (detail.get("resultDesc") != null
                        && !detail.get("resultDesc").equals("")) {
                    JSONObject resultDesc = JSONObject.parseObject(detail
                            .getString("resultDesc"));
                    System.out.println("resultDesc:" + resultDesc);
                    System.out.println("ANTIFRAUD:"
                            + resultDesc.get("ANTIFRAUD"));
                    if (resultDesc.get("ANTIFRAUD") != null
                            && !resultDesc.get("ANTIFRAUD").equals("")) {
                        JSONObject ANTIFRAUD = JSONObject
                                .parseObject(resultDesc.getString("ANTIFRAUD"));
                        request.setAttribute("ANTIFRAUD", ANTIFRAUD);
                        if (ANTIFRAUD.get("risk_items") != null
                                && !ANTIFRAUD.get("risk_items").equals("")) {
                            JSONArray risk_items = JSONArray
                                    .parseArray(ANTIFRAUD
                                            .getString("risk_items"));
                            request.setAttribute("risk_items", risk_items);
                        }
                    }
                }
            }
        }
        request.setAttribute("icbc", icbc);
        request.setAttribute("dsj", dsj);
        return "jsp/manager/AppraisalReport";
    }


    public static void main(String[] args) {
        String s = "{\"detail\":{\"success\":1,\"id\":\"WF2019050617215212669706\",\"reasonDesc\":null,\"reasonCode\":null,\"resultDesc\":\"{\"ANTIFRAUD\":{\"risk_items\":[{\"risk_id\":1,\"rule_id\":\"30415014\",\"score\":1,\"rule_uuid\":\"08e7a359b530492bbf27f993a47bbff0\",\"risk_name\":\"6个月内申请人在多个平台申请借款\",\"risk_detail\":{\"cross_partner_details\":[{\"count\":1,\"industryDisplayName\":\"一般消费分期平台\"}],\"cross_partner_count\":1,\"type\":\"cross_partner\"}},{\"risk_id\":2,\"rule_id\":\"30415024\",\"score\":0,\"rule_uuid\":\"d65fd878700c45918c74a5cc8057f159\",\"risk_name\":\"12个月内申请人在多个平台申请借款\",\"risk_detail\":{\"cross_partner_details\":[{\"count\":1,\"industryDisplayName\":\"一般消费分期平台\"},{\"count\":1,\"industryDisplayName\":\"小额贷款公司\"}],\"cross_partner_count\":2,\"type\":\"cross_partner\"}},{\"risk_id\":3,\"rule_id\":\"30415034\",\"score\":0,\"rule_uuid\":\"92ce3f80062945219c650a0ef0890746\",\"risk_name\":\"24个月内申请人在多个平台申请借款\",\"risk_detail\":{\"cross_partner_details\":[{\"count\":1,\"industryDisplayName\":\"一般消费分期平台\"},{\"count\":1,\"industryDisplayName\":\"小额贷款公司\"}],\"cross_partner_count\":2,\"type\":\"cross_partner\"}}],\"score\":1,\"decision\":\"PASS\"}}\"},\"status\":{\"isSuccess\":true,\"requestId\":\"78582c07a7e046bf919ea3ba935c9a47\",\"responseCode\":\"0000\",\"responseMessage\":\"查询成功!\",\"warningMessage\":null}}";

        Object obj = Tools.jsonDeCode(s.replace(":\"{", ":{").replace("}\"", "}"));

    }
}
