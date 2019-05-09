package com.tt.tlzf;

import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.quickpay.QAGRINFO;


/**
 * @Description 快捷签约验证
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class Tranx340009 {

    public static void main(String args[]) {
        InfoReq infoReq = DemoUtil.makeReq("340009");

        QAGRINFO qagrinfo = new QAGRINFO();
        qagrinfo.setMERCHANT_ID(DemoConfig.merchantid);
        qagrinfo.setAGRTYPE("01");
        qagrinfo.setACCOUNT_NO("6222004000493013989");
        qagrinfo.setQUERY_MODE("3");
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(qagrinfo);

        try {
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = "https://test.allinpaygd.com/aipg/ProcessServlet";
            System.out.println("============================请求报文============================");
            System.out.println(signedXml);
            String respText = HttpUtil.post(signedXml, url);
            System.out.println("============================响应报文============================");
            System.out.println(respText);
            //step4 验签
            if (!DemoUtil.verifyXml(respText)) {
                System.out.println("====================================================>验签失败");
                return;
            }
            System.out.println("====================================================>验签成功");
            //step5 xml转对象
			/*AipgRsp rsp = XmlParser.parseRsp(respText);
			InfoRsp infoRsp = rsp.getINFO();
			System.out.println(infoRsp.getRET_CODE());
			System.out.println(infoRsp.getERR_MSG());
			if("0000".equals(infoRsp.getRET_CODE())){
				*//*FAGRCRET ret = (FAGRCRET)rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());*//*
			}*/
            String s = XmlExercise.xml2json(respText);
            System.out.println(s);
        } catch (AIPGException e) {
            e.printStackTrace();
        }
    }

}
