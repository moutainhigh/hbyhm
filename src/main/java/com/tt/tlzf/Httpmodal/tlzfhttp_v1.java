package com.tt.tlzf.Httpmodal;

import com.tt.tlzf.AIPGException;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class tlzfhttp_v1 {

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
        TtMap post = Tools.getPostMap(request);
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
        try {
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
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            /*AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();*/
            String json = XmlExercise.xml2json(respText);
            System.out.println("***********json***********");
            System.out.println(json);
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
        TtMap post = Tools.getPostMap(request);
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
        try {
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
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                return "";
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
            /*AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();*/
            String json = XmlExercise.xml2json(respText);
            System.out.println("***********json***********");
            System.out.println(json);
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
        TtMap post = Tools.getPostMap(request);

        InfoReq infoReq = DemoUtil.makeReq("310011");

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

        try {
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
            System.out.println(json);
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
