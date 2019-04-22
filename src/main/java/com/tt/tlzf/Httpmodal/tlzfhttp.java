package com.tt.tlzf.Httpmodal;

import com.tt.tlzf.AIPGException;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.util.apiutil;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.quickpay.*;
import com.tt.tlzf.xstruct.trans.LedgerDtl;
import com.tt.tlzf.xstruct.trans.Ledgers;
import com.tt.tlzf.xstruct.trans.qry.TransQueryReq;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.Tools;
import com.tt.tlzf.util.apiutil;
import com.tt.tool.Config;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class tlzfhttp {

    private final String TXZF_URL = Config.TESTMODE
            ? "https://test.allinpaygd.com/aipg/ProcessServlet" //测试环境
            : "https://test.allinpaygd.com/aipg/ProcessServlet";//正式环境

    /**
     * 协议支付签约短信触发(310001)
     *
     * @param request
     * @param response
     * @return
     */
    public String kcd310001(HttpServletRequest request, HttpServletResponse response) {
        apiutil apiutil = new apiutil();
        TtMap post = Tools.getPostMap(request);
        TtMap qymap = new TtMap();
        //接口参数赋值
        InfoReq inforeq = DemoUtil.makeReq("310001");
        FAGRA fagra = new FAGRA();
        fagra.setACCOUNT_NAME(post.get("ACCOUNT_NAME"));//
        fagra.setACCOUNT_NO(post.get("ACCOUNT_NO"));//
        fagra.setACCOUNT_PROP("0");
        fagra.setTEL(post.get("TEL"));//
        fagra.setID(post.get("ID"));//
        fagra.setID_TYPE(post.get("ID_TYPE"));
        fagra.setMERCHANT_ID(DemoConfig.merchantid);
        fagra.setACCOUNT_TYPE(post.get("ACCOUNT_TYPE"));
        fagra.setCVV2(post.get("CVV2"));
        fagra.setVALIDDATE(post.get("VAILDDATE"));
        fagra.setMERREM(post.get("MERREM"));
        fagra.setREMARK(post.get("REMARK"));
        AipgReq req = new AipgReq();
        req.setINFO(inforeq);
        req.addTrx(fagra);
        //签约信息赋值
        if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
            qymap.put("mid_edit",post.get("mid_edit"));
        }
        if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")) {
            qymap.put("qd_type", post.get("qd_type"));
        }
        qymap.put("dt_ediit", Tools.dateToStrLong(new Date()));
        qymap.put("bank_code", post.get("BANK_CODE"));
        qymap.put("account_type", post.get("ACCOUNT_TYPE"));
        qymap.put("account_no", post.get("ACCOUNT_NO"));
        qymap.put("account_name", post.get("ACCOUNT_NAME"));
        qymap.put("account_prop", "0");
        qymap.put("cardid_type", post.get("ID_TYPE"));
        qymap.put("cardid", post.get("ID"));
        qymap.put("tel", post.get("TEL"));
        qymap.put("cvv2", post.get("CVV2"));
        qymap.put("vailddate", post.get("VAILDDATE"));
        qymap.put("merrem", post.get("MERREM"));
        qymap.put("remark", post.get("REMARK"));
        qymap.put("req_sn", inforeq.getREQ_SN());
/*           qymap.put("result_code","");
             qymap.put("result_msg","");
             qymap.put("result_content","");
             qymap.put("agrmno","");*/
        qymap.put("qy_status", "1");
        if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")){
            qymap.put("qd_type",post.get("qd_type"));
        }
        TtMap qy = Tools.recinfo("select * from tlzf_qy where cardid=" + post.get("ID"));
        long qy_id = 0;
        if (!qy.isEmpty()) {
            if (qy.get("qy_status").equals("3")) {
                apiutil.setRET_CODE("001");
                apiutil.setERR_MSG("已签约成功");
                return apiutil.toString();
            }
        }
        //接口记录赋值
        TtMap ttMap = new TtMap();
        ttMap.put("trx_code", "310001");
        ttMap.put("req_sn", inforeq.getREQ_SN());
        ttMap.put("account_no", fagra.getACCOUNT_NO());
        ttMap.put("account_name", fagra.getACCOUNT_NAME());
        ttMap.put("cardid", fagra.getID());
        ttMap.put("tel", fagra.getTEL());
        try {
            //step1 对象转xml
            String xml = XmlParser.toXml(req);

            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);

            ttMap.put("to_json", XmlExercise.xml2json(signedXml));
            ttMap.put("to_xml", signedXml);

            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                apiutil.setRET_CODE("002");
                apiutil.setERR_MSG("验签失败");
                return apiutil.toString();
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            /*AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();*/
            String json = XmlExercise.xml2json(respText);
            JSONObject res = JSONObject.fromObject(json);
            /*JSONObject fagraret = JSONObject.fromObject(res.get("FAGRARET"));*/
            ttMap.put("re_json", json);
            ttMap.put("re_xml", respText);
            //接口返回 添加签约信息
            if (!qy.isEmpty()) {
                if (qy.get("qy_status").equals("3")) {

                } else {
                    qy_id = Long.valueOf(qy.get("id"));
                    Tools.recEdit(qymap, "tlzf_qy", qy_id);
                }
            } else {
                if(post.get("mid_add")!=null&&!post.get("mid_add").equals("")){
                    qymap.put("mid_add",post.get("mid_add"));
                }
                qymap.put("dt_add", Tools.dateToStrLong(new Date()));
                qy_id = Tools.recAdd(qymap, "tlzf_qy");
            }
            //接口返回 更新接口记录

            ttMap.put("qy_id", String.valueOf(qy_id));
            if(post.get("mid_add")!=null&&!post.get("mid_add").equals("")){
                ttMap.put("mid_add",post.get("mid_add"));
            }
            if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
                ttMap.put("mid_edit",post.get("mid_edit"));
            }
            if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")){
                ttMap.put("qd_type",post.get("qd_type"));
            }
            ttMap.put("dt_add", Tools.dateToStrLong(new Date()));
            ttMap.put("dt_edit", Tools.dateToStrLong(new Date()));
            long id = Tools.recAdd(ttMap, "tlzf_result");

            return json;
        } catch (AIPGException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 3.1.2协议支付签约(310002)
     *
     * @param request
     * @param response
     * @return
     */
    public String kcd310002(HttpServletRequest request, HttpServletResponse response) {
        apiutil apiutil = new apiutil();

        TtMap post = Tools.getPostMap(request);

        TtMap qymap = new TtMap();
        //接口参数赋值
        InfoReq inforeq = DemoUtil.makeReq("310002");
        FAGRCEXT fagrcext = new FAGRCEXT();
        fagrcext.setMERCHANT_ID(DemoConfig.merchantid);
        fagrcext.setVERCODE(post.get("VERCODE"));
        fagrcext.setSRCREQSN(post.get("SRCREQSN"));
        fagrcext.setACCOUNT_NAME(post.get("ACCOUNT_NAME"));//
        fagrcext.setACCOUNT_NO(post.get("ACCOUNT_NO"));//
        fagrcext.setTEL(post.get("TEL"));//
        fagrcext.setID(post.get("ID"));//
        fagrcext.setID_TYPE(post.get("ID_TYPE"));

        AipgReq req = new AipgReq();
        req.setINFO(inforeq);
        req.addTrx(fagrcext);
        //签约信息赋值
        qymap.put("dt_ediit", Tools.dateToStrLong(new Date()));

        TtMap qy = Tools.recinfo("select * from tlzf_qy where cardid=" + post.get("ID"));
        long qy_id = 0;
        if (!qy.isEmpty()) {
            if (qy.get("qy_status").equals("3")) {
                apiutil.setRET_CODE("001");
                apiutil.setERR_MSG("已签约成功");
                return apiutil.toString();
            }
        } else {
            apiutil.setRET_CODE("002");
            apiutil.setERR_MSG("尚未申请签约,清先申请签约");
            return apiutil.toString();
        }
        //接口记录赋值
        TtMap ttMap = new TtMap();
        ttMap.put("trx_code", "310002");
        ttMap.put("req_sn", inforeq.getREQ_SN());
        ttMap.put("account_no", fagrcext.getACCOUNT_NO());
        ttMap.put("account_name", fagrcext.getACCOUNT_NAME());
        ttMap.put("cardid", fagrcext.getID());
        ttMap.put("tel", fagrcext.getTEL());
        try {
            //step1 对象转xml
            String xml = XmlParser.toXml(req);

            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);

            ttMap.put("to_json", XmlExercise.xml2json(signedXml));
            ttMap.put("to_xml", signedXml);

            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                apiutil.setRET_CODE("002");
                apiutil.setERR_MSG("验签失败");
                return apiutil.toString();
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            /*AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();*/
            String json = XmlExercise.xml2json(respText);
            JSONObject res = JSONObject.fromObject(json);
            JSONObject fagraret = JSONObject.fromObject(res.get("FAGRCRET"));
            ttMap.put("re_json", json);
            ttMap.put("re_xml", respText);
            System.out.println("fagraret:" + fagraret);
            if (fagraret.get("RET_CODE").toString().equals("0000")) {
                qymap.put("agrmno", fagraret.get("AGRMNO").toString());
                qymap.put("qy_status", "3");
            }
            qymap.put("result_code", fagraret.get("RET_CODE").toString());
            qymap.put("result_msg", fagraret.get("ERR_MSG").toString());
            qymap.put("result_content", json);
            //接口返回 添加签约信息
            if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
                qymap.put("mid_edit",post.get("mid_edit"));
            }
            qy_id = Long.valueOf(qy.get("id"));
            Tools.recEdit(qymap, "tlzf_qy", qy_id);
            //接口返回 更新接口记录
            if(post.get("mid_add")!=null&&!post.get("mid_add").equals("")){
                ttMap.put("mid_add",post.get("mid_add"));
            }
            if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
                ttMap.put("mid_edit",post.get("mid_edit"));
            }
            ttMap.put("qy_id", String.valueOf(qy_id));
            ttMap.put("dt_add", Tools.dateToStrLong(new Date()));
            ttMap.put("dt_edit", Tools.dateToStrLong(new Date()));
            if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")){
                ttMap.put("qd_type",post.get("qd_type"));
            }
            long id = Tools.recAdd(ttMap, "tlzf_result");

            return json;
        } catch (AIPGException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 3.1.4协议支付(310011)
     *
     * @param request
     * @param response
     * @return
     */
    public String kcd310011(HttpServletRequest request, HttpServletResponse response) {
        apiutil apiutil = new apiutil();
        TtMap post = Tools.getPostMap(request);
        InfoReq infoReq = DemoUtil.makeReq("310011");
        TtMap dkmap = new TtMap();
        FASTTRX ft = new FASTTRX();
        ft.setMERCHANT_ID(DemoConfig.merchantid);
        ft.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
        ft.setSUBMIT_TIME(DemoUtil.getNow());
        ft.setAGRMNO(post.get("AGRMNO"));
        ft.setACCOUNT_NAME(post.get("ACCOUNT_NAME"));
        ft.setACCOUNT_NO(post.get("ACCOUNT_NO"));
        ft.setBANK_CODE(post.get("BANK_CODE"));
        ft.setCURRENCY(post.get("CURRENCY"));
        ft.setAMOUNT(post.get("AMOUNT"));
        ft.setID_TYPE(post.get("ID_TYPE"));
        ft.setID(post.get("ID"));
        ft.setTEL(post.get("TEL"));
        ft.setCVV2(post.get("CVV2"));
        ft.setVAILDDATE(post.get("VAILDDATE"));
        ft.setCUST_USERID(post.get("CUST_USERID"));
        ft.setREMARK(post.get("REMARK"));
        ft.setSUMMARY(post.get("SUMMARY"));

        LedgerDtl dtl1 = new LedgerDtl();
        dtl1.setAMOUNT("1");
        dtl1.setMERCHANT_ID(DemoConfig.merchantid);
        dtl1.setSN("0");
        dtl1.setTYPE("0");

        Ledgers ledgers = new Ledgers();
        ledgers.addTrx(dtl1);

        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(ft);
        req.addTrx(ledgers);
        //代扣信息
        dkmap.put("dt_add", Tools.dateToStrLong(new Date()));
        dkmap.put("dt_edit", Tools.dateToStrLong(new Date()));
        dkmap.put("req_sn", infoReq.getREQ_SN());
        dkmap.put("business_code", "19900");
        dkmap.put("submit_time", DemoUtil.getNow());
        dkmap.put("agrmno", post.get("AGRMNO"));
        dkmap.put("account_no", post.get("ACCOUNT_NO"));
        dkmap.put("account_name", post.get("ACCOUNT_NAME"));
        dkmap.put("acount", post.get("AMOUNT"));
        dkmap.put("cardid_type", post.get("ID_TYPE"));
        dkmap.put("cardid", post.get("ID"));
        dkmap.put("tel", post.get("TEL"));
        dkmap.put("cvv2", post.get("CVV2"));
        dkmap.put("vailddate", post.get("VAILDDATE"));
        dkmap.put("cust_userid", post.get("CUST_USERID"));
        dkmap.put("summary", post.get("SUMMARY"));
        dkmap.put("remark", post.get("REMARK"));
        if(post.get("mid_add")!=null&&!post.get("mid_add").equals("")){
            dkmap.put("mid_add",post.get("mid_add"));
        }
        if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
            dkmap.put("mid_edit",post.get("mid_edit"));
        }
        if (post.get("icbc_id") != null && !post.get("icbc_id").equals("")) {
            dkmap.put("icbc_id", post.get("icbc_id"));
        }
        dkmap.put("dk_status", "1");
        if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")){
            dkmap.put("qd_type",post.get("qd_type"));
        }
        Long dk_id = Tools.recAdd(dkmap, "tlzf_dk_details");

        //接口记录赋值
        TtMap ttMap = new TtMap();
        ttMap.put("trx_code", "310011");
        ttMap.put("req_sn", infoReq.getREQ_SN());
        ttMap.put("account_no", post.get("ACCOUNT_NO"));
        ttMap.put("account_name",post.get("ACCOUNT_NAME"));
        ttMap.put("cardid",post.get("ID"));
        ttMap.put("tel",post.get("TEL"));

        try {
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);

            ttMap.put("to_json", XmlExercise.xml2json(signedXml));
            ttMap.put("to_xml", signedXml);

            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            /*AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();

            System.out.println(infoRsp.getRET_CODE());
            System.out.println(infoRsp.getERR_MSG());
            if("0000".equals(infoRsp.getRET_CODE())){
                FASTTRXRET ret = (FASTTRXRET) rsp.trxObj();
                System.out.println(ret.getRET_CODE());
                System.out.println(ret.getERR_MSG());
                System.out.println(ret.getSETTLE_DAY());

            }*/
            String json = XmlExercise.xml2json(respText);
            JSONObject res = JSONObject.fromObject(json);
            JSONObject fagraret = JSONObject.fromObject(res.get("FASTTRXRET"));
            dkmap.put("result_code", fagraret.get("RET_CODE").toString());
            dkmap.put("result_msg", fagraret.get("ERR_MSG").toString());
            dkmap.put("result_content", json);
            if ("0000".equals(fagraret.get("RET_CODE").toString())) {
                dkmap.put("settle_day", fagraret.get("SETTLE_DAY").toString());
                dkmap.put("dk_status", "2");
            }
            dkmap.remove("dt_add");//删除指定元素
            Tools.recEdit(dkmap, "tlzf_dk_details", dk_id);
            //接口返回 更新接口记录
            ttMap.put("re_json", json);
            ttMap.put("re_xml", respText);

            if(post.get("mid_add")!=null&&!post.get("mid_add").equals("")){
                ttMap.put("mid_add",post.get("mid_add"));
            }
            if(post.get("mid_edit")!=null&&!post.get("mid_edit").equals("")){
                ttMap.put("mid_edit",post.get("mid_edit"));
            }
            ttMap.put("qy_id", String.valueOf(dk_id));
            ttMap.put("dt_add", Tools.dateToStrLong(new Date()));
            ttMap.put("dt_edit", Tools.dateToStrLong(new Date()));
            if(post.get("qd_type")!=null&&!post.get("qd_type").equals("")){
                ttMap.put("qd_type",post.get("qd_type"));
            }
            long id = Tools.recAdd(ttMap, "tlzf_result");
            return json;
        } catch (AIPGException e) {
            e.printStackTrace();
        }

        return "";
    }
    /**
     * 3.1.6快捷协议查询(340009)
     * @param request
     * @param response
     * @return
     */
    public String kcd340009(HttpServletRequest request, HttpServletResponse response){
        TtMap post = Tools.getPostMap(request);
        InfoReq infoReq = DemoUtil.makeReq("340009");
        QAGRINFO qagrinfo = new QAGRINFO();
        qagrinfo.setMERCHANT_ID(DemoConfig.merchantid);
        qagrinfo.setAGRTYPE("01");
        qagrinfo.setACCOUNT_NO(post.get("ACCOUNT_NO"));
        qagrinfo.setQUERY_MODE("3");
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(qagrinfo);
        try{
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if(!DemoUtil.verifyXml(respText)){
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            String json = XmlExercise.xml2json(respText);
            return json;
        }catch(AIPGException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 3.1.3协议支付解约(310003)
     * @param request
     * @param response
     * @return
     */
    public String kcd310003(HttpServletRequest request, HttpServletResponse response) {
        TtMap post = Tools.getPostMap(request);
        InfoReq infoReq = DemoUtil.makeReq("310003");
        FAGRCNL fagrc = new FAGRCNL();
        fagrc.setMERCHANT_ID(DemoConfig.merchantid);
        fagrc.setAGRMNO(post.get("AGRMNO"));
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(fagrc);

        try{
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if(!DemoUtil.verifyXml(respText)){
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            String json = XmlExercise.xml2json(respText);
            return json;
        }catch(AIPGException e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 3.10.2交易结果查询(200004)
     * @param request
     * @param response
     * @return
     */
    public String kcd200004(HttpServletRequest request, HttpServletResponse response){
        TtMap post = Tools.getPostMap(request);
        InfoReq infoReq = DemoUtil.makeReq("200004");
        TransQueryReq queryReq = new TransQueryReq();
        queryReq.setMERCHANT_ID(DemoConfig.merchantid);
        queryReq.setQUERY_SN(post.get("QUERY_SN"));//查询交易的文件名
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(queryReq);
        try{
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = TXZF_URL;
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if(!DemoUtil.verifyXml(respText)){
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转json
            String json = XmlExercise.xml2json(respText);
            return json;
        }catch(AIPGException e){
            e.printStackTrace();
        }
        return "";
    }

}
