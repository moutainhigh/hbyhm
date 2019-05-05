package com.tt.tlzf.Httpmodal;

import com.tt.tlzf.AIPGException;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.quickpay.FASTTRXRET;
import com.tt.tool.Config;

public class insideHttp {

    /*
      内部接口类
     */
    public static String kjyTranx310011(AipgReq req){
        String json="";
        try{
            //step1 对象转xml
            String xml = XmlParser.toXml(req);
            //step2 加签
            String signedXml = DemoUtil.buildSignedXml(xml);
            //step3 发往通联
            String url = DemoConfig.TXZF_URL;
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
/*            AipgRsp rsp = XmlParser.parseRsp(respText);
            InfoRsp infoRsp = rsp.getINFO();*/
            json = XmlExercise.xml2json(respText);
        }catch(AIPGException e){
            e.printStackTrace();
        }
        return json;
    }
}
