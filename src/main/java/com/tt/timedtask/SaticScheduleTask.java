package com.tt.timedtask;

import com.tt.api.ApiSms;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.Httpmodal.insideHttp;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.quickpay.FASTTRX;
import com.tt.tool.Tools;
import com.tt.tool.addDate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    /**
     * 短信通知客户
     */
    @Scheduled(cron = "0 30 9 * * ?")
    //@Scheduled(fixedRate=5000)
    private void tlzf_sms() {
        System.out.println("*********短信通知开始*********");
        String time = Tools.dateToStr(new Date());
        TtList dklist = Tools.reclist("select * from  tlzf_dk_details where bc_status!=3 and bc_status!=4  and api_type=0");
        if (dklist.size() > 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TtMap ttMap : dklist) {
                Date date1 = new Date();
                Date date2 = new Date();
                try {
                    date2 = sdf.parse(ttMap.get("ds_date"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int days = addDate.differentDays(date1, date2);
                if (days <= 3 && days > 0) {
                    System.out.println("短信通知："+ttMap.get("account_name"));
                    int account = Integer.parseInt(ttMap.get("fw_price"));
                    //短信通知
                    ApiSms.mmsSend(ttMap.get("tel"), "尊敬的客户" + ttMap.get("account_name") +
                                    "您好！您尾号为" + ttMap.get("account_no").substring(ttMap.get("account_no").length() - 4, ttMap.get("account_no").length()) +
                                    "的银行储蓄卡将于" + ttMap.get("ds_date") + "扣还汽车贷款服务费金额为￥" + Tools.getprice(account, 100) + "元，请保持卡内余额充足！"
                            , "");
                }
            }
        }

        System.out.println("*********短信通知结束*********");
    }

    /**
     * 通联支付 代收
     */
    //3.添加定时任务
    @Scheduled(cron = "0 0 4 * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void tlzf_ds() {
        String time = Tools.dateToStr(new Date());
        System.err.println("*********执行静态定时任务时间: " + time + " 开始*********");
        TtList dklist = Tools.reclist("select * from  tlzf_dk_details where ds_date='" + time + "' and bc_status!=3 and bc_status!=4  and api_type=0");
        int num = 0;
        Tools.myLog("通联支付-代收定时任务开始," + time + "---数量:" + dklist.size());
        if (dklist.size() > 0) {
            int totalprice1 = 0;
            int totalprice2 = 0;
            for (TtMap ttMap : dklist) {
                totalprice1 = totalprice1 + Integer.parseInt(ttMap.get("fw_price"));
                InfoReq infoReq = DemoUtil.makeReq("310011");
                FASTTRX ft = new FASTTRX();
                ft.setMERCHANT_ID(DemoConfig.merchantid);
                ft.setBUSINESS_CODE(DemoConfig.BUSINESS_CODE);//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
                ft.setSUBMIT_TIME(DemoUtil.getNow());
                ft.setAGRMNO(ttMap.get("agrmno"));
                ft.setACCOUNT_NAME(ttMap.get("account_name"));
                ft.setAMOUNT(ttMap.get("fw_price"));
                ft.setCUST_USERID(ttMap.get("cust_userid"));
                ft.setREMARK(ttMap.get("remark"));
                ft.setSUMMARY(ttMap.get("summary"));
                AipgReq req = new AipgReq();
                req.setINFO(infoReq);
                req.addTrx(ft);
                String res = insideHttp.kjyTranx310011(req);
                TtMap dkMap = new TtMap();
                dkMap.put("req_sn", infoReq.getREQ_SN());
                dkMap.put("business_code", ft.getBUSINESS_CODE());
                if (res.substring(0, 1).equals("[")) {
                    System.out.println("第一种情况");
                    JSONArray ary = JSONArray.fromObject(res);
                    System.out.println(ary.get(0));
                    if (!ary.get(0).equals("")) {
                        JSONObject INFO = JSONObject.fromObject(ary.get(0));
                        System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                        dkMap.put("result_code", INFO.get("RET_CODE").toString());
                        dkMap.put("result_msg", INFO.get("ERR_MSG").toString());
                    }
                } else {
                    System.out.println("第二种情况");
                    JSONObject ress = JSONObject.fromObject(res);
                    System.out.println("INFO:" + ress.get("INFO"));
                    if (ress.get("INFO") != null && !ress.get("INFO").equals("")) {
                        JSONObject INFO = JSONObject.fromObject(ress.get("INFO"));
                        System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                        if (
                                "2000".equals(INFO.get("RET_CODE")) ||
                                        "2001".equals(INFO.get("RET_CODE")) ||
                                        "2003".equals(INFO.get("RET_CODE")) ||
                                        "2005".equals(INFO.get("RET_CODE")) ||
                                        "2007".equals(INFO.get("RET_CODE")) ||
                                        "2008".equals(INFO.get("RET_CODE")) ||
                                        "1108".equals(INFO.get("RET_CODE"))

                        ) {
                            dkMap.put("bc_status", "4");
                        }
                    }
                    System.out.println("FASTTRXRET:" + ress.get("FASTTRXRET"));
                    if (ress.get("FASTTRXRET") != null && !ress.get("FASTTRXRET").equals("")) {
                        JSONObject FASTTRXRET = JSONObject.fromObject(ress.get("FASTTRXRET"));
                        System.out.println("ERR_MSG:" + FASTTRXRET.get("ERR_MSG"));
                        dkMap.put("result_code", FASTTRXRET.get("RET_CODE").toString());
                        dkMap.put("result_msg", FASTTRXRET.get("ERR_MSG").toString());
                        if ("0000".equals(FASTTRXRET.get("RET_CODE"))) {
                            dkMap.put("bc_status", "3");
                            totalprice2 = totalprice2 + Integer.parseInt(ttMap.get("fw_price"));
                            num++;
                            dkMap.put("settle_day", FASTTRXRET.get("SETTLE_DAY").toString());
                        } else if (
                                "2000".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "2001".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "2003".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "2005".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "2007".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "2008".equals(FASTTRXRET.get("RET_CODE")) ||
                                        "1108".equals(FASTTRXRET.get("RET_CODE"))
                        ) {
                            dkMap.put("bc_status", "4");
                        }
                    }
                }
                dkMap.put("result_content", res);
                Tools.recEdit(dkMap, "tlzf_dk_details", Integer.parseInt(ttMap.get("id")));
            }
            if (totalprice2 > 0) {
                //短信通知
                ApiSms.mmsSend("18859169090", "今日代收应交易数量:" + dklist.size() + ",实际交易完成数量:" + num +
                                ",应交易总额:" + Tools.getprice(totalprice1, 100) + "元,实际交易完成总额:"
                                + Tools.getprice(totalprice2, 100) + "元"
                        , "");
            }
        }
        Tools.myLog("通联支付-代收定时任务结束," + time + "---数量:" + dklist.size() + "--处理完成数量:" + num);
        System.err.println("*********执行静态定时任务时间: " + time + " 结束*********");
    }
/*
    public static void main(String[] args) {

        System.out.println("6222004000493013989".substring("6222004000493013989".length()-4,"6222004000493013989".length()));
    }*/

}