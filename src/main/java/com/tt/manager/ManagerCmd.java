/*
 * @Description: 后台常用command修改数据库用
 * @Author: tt
 * @Date: 2019-01-29 11:08:47
 * @LastEditTime: 2019-03-25 09:38:31
 * @LastEditors: tt
 */
package com.tt.manager;

import com.tt.data.TtMap;
import com.tt.table.Admin;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.Httpmodal.insideHttp;
import com.tt.tlzf.Httpmodal.tlzfhttp_v1;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.quickpay.FASTTRX;
import com.tt.tlzf.xstruct.trans.LedgerDtl;
import com.tt.tlzf.xstruct.trans.Ledgers;
import com.tt.tlzf.xstruct.trans.qry.TransQueryReq;
import com.tt.tool.CookieTools;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.tools.Tool;
import java.io.IOException;
import java.net.URLEncoder;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
public class ManagerCmd {
    /**
     * command模式的POST处理
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/manager/command", method = RequestMethod.POST)
    @ResponseBody
    private String doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String sLogin = ManagerTools.checkLogin();// 检查是否登陆
        if (!Tools.myIsNull(sLogin)) {// 如未登陆跳转到登陆页面
            return sLogin + "?refer=" + URLEncoder.encode(Tools.urlKill("toExcel"), "UTF-8");
        }
        TtMap postUrl = Tools.getUrlParam();
        TtMap post = Tools.getPostMap(request, true);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println(Tools.jsonEncode(post));
        System.out.println("ajax提交："+post);
        String cn = postUrl.get("cn") == null ? "" : postUrl.get("cn");
        TtMap result2 = new TtMap();
        Tools.formatResult(result2, false, 999, "异常，请重试！", "");// 初始化返回
        String realCn = ManagerTools.getRealCn(cn);
        if (!ManagerTools.checkSdo(postUrl.get("sdo"))) {// 过滤cn和sdo，realCn为null时表示此cn不合法。
            return Tools.jsonEncode(result2);
        }
        post.put("fromcommand", "1");

        switch (postUrl.get("sdo")) { // 目前只有form模式下有post
            case "edit":
                long id = Tools.myIsNull(postUrl.get("id")) ? 0 : Tools.strToLong(postUrl.get("id"));
                switch (cn) {
                    case "admin":
                        if (id > 0) {
                            Admin admin = new Admin();
                            try {
                                admin.edit(post, id);
                                boolean success = admin.errorCode == 0 && Tools.myIsNull(admin.errorMsg);
                                Tools.formatResult(result2, success, admin.errorCode, admin.errorMsg, "");
                            } catch (Exception e) {
                                Tools.logError(e.getMessage(), true, true);
                            } finally {
                                admin.closeConn();
                            }
                        }
                        break;
                    case "tlzf_dk_details":
                        if (id > 0) {
                            Tools.recEdit(post, "tlzf_dk_details", id);
                        }
                        break;
                }
                break;
            case "sysconfig": // 系统设置
                switch (cn) {
                    case "font": // 字体
                        int fontSize = Tools.strToInt(postUrl.get("fontsize"));
                        if (postUrl.get("stype").equals("0")) {
                            fontSize++;
                        } else {
                            fontSize--;
                        }
                        CookieTools.set("fontsize", fontSize + "", "", 7 * 86400); // 演示使用cookie技术来保存用户本地的一些设置。当然你可以用数据库来保存
                        Tools.formatResult(result2, true, fontSize, "" + fontSize, "");
                        break;
                    case "zoom":
                        Double sZoom = Double.parseDouble(postUrl.get("zoom"));
                        if (postUrl.get("stype").equals("0")) {
                            sZoom = sZoom + 0.1;
                        } else {
                            sZoom = sZoom - 0.1;
                        }
                        CookieTools.set("zoom", sZoom + "", "", 7 * 86400); // 演示使用cookie技术来保存用户本地的一些设置。当然你可以用数据库来保存
                        Tools.formatResult(result2, true, 1, "" + sZoom, "");
                        break;
                }
                break;
            case "tlzf_ds"://通联支付 代收
                int errorCode=0;
                String errorMsg="";
                TtMap dk = Tools.recinfo("select * from tlzf_dk_details where id="+postUrl.get("id"));
                if(!dk.isEmpty()){
                    InfoReq infoReq = DemoUtil.makeReq("310011");
                    FASTTRX ft = new FASTTRX();
                    ft.setMERCHANT_ID(DemoConfig.merchantid);
                    ft.setBUSINESS_CODE(DemoConfig.BUSINESS_CODE);//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
                    ft.setSUBMIT_TIME(DemoUtil.getNow());
                    ft.setAGRMNO(dk.get("agrmno"));
                    ft.setACCOUNT_NAME(dk.get("account_name"));
                    ft.setAMOUNT(dk.get("fw_price"));
                    ft.setCUST_USERID(dk.get("cust_userid"));
                    ft.setREMARK(dk.get("remark"));
                    ft.setSUMMARY(dk.get("summary"));
                    AipgReq req = new AipgReq();
                    req.setINFO(infoReq);
                    req.addTrx(ft);
                    String res = insideHttp.kjyTranx310011(req);
                    TtMap ttMap=new TtMap();
                    ttMap.put("req_sn", infoReq.getREQ_SN());
                    ttMap.put("business_code",ft.getBUSINESS_CODE());
                    if (res.substring(0, 1).equals("[")) {
                        System.out.println("第一种情况");
                        JSONArray ary = JSONArray.fromObject(res);
                        System.out.println(ary.get(0));
                        if (!ary.get(0).equals("")) {
                            JSONObject INFO = JSONObject.fromObject(ary.get(0));
                            System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                            ttMap.put("result_code", INFO.get("RET_CODE").toString());
                            ttMap.put("result_msg", INFO.get("ERR_MSG").toString());
                            errorCode=Integer.parseInt(INFO.get("RET_CODE").toString());
                            errorMsg = INFO.get("ERR_MSG").toString();
                        }
                        ttMap.put("bc_status", "2");
                    } else {
                        System.out.println("第二种情况");
                        JSONObject ress = JSONObject.fromObject(res);
                        System.out.println("INFO:" + ress.get("INFO"));
                        if (ress.get("INFO") != null && !ress.get("INFO").equals("")) {
                            JSONObject INFO = JSONObject.fromObject(ress.get("INFO"));
                            System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                            ttMap.put("result_code", INFO.get("RET_CODE").toString());
                            ttMap.put("result_msg", INFO.get("ERR_MSG").toString());
                            errorCode=Integer.parseInt(INFO.get("RET_CODE").toString());
                            errorMsg = INFO.get("ERR_MSG").toString();
                            if ("0000".equals(INFO.get("RET_CODE"))) {
                                ttMap.put("bc_status", "3");
                            } else if (
                                    "2000".equals(INFO.get("RET_CODE")) ||
                                            "2001".equals(INFO.get("RET_CODE")) ||
                                            "2003".equals(INFO.get("RET_CODE")) ||
                                            "2005".equals(INFO.get("RET_CODE")) ||
                                            "2007".equals(INFO.get("RET_CODE")) ||
                                            "2008".equals(INFO.get("RET_CODE"))
                            ) {
                                ttMap.put("bc_status", "4");
                            } else {
                                ttMap.put("bc_status", "2");
                            }
                        }
                        System.out.println("FASTTRXRET:" + ress.get("FASTTRXRET"));
                        if (ress.get("FASTTRXRET") != null && !ress.get("FASTTRXRET").equals("")) {
                            JSONObject FASTTRXRET = JSONObject.fromObject(ress.get("FASTTRXRET"));
                            System.out.println("ERR_MSG:" + FASTTRXRET.get("ERR_MSG"));
                            ttMap.put("settle_day", FASTTRXRET.get("SETTLE_DAY").toString());
                        }
                    }
                    ttMap.put("result_content", res);
                    Tools.recEdit(ttMap,"tlzf_dk_details",Integer.parseInt(postUrl.get("id")));
                }
                boolean success = errorCode == 0 && Tools.myIsNull(errorMsg);
                Tools.formatResult(result2, success,errorCode, errorMsg, "");
                break;
            default:
                break;
        }
        return Tools.jsonEncode(result2);
    }
}