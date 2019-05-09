package com.tt.tlzf;

import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.trans.qry.QTDetail;
import com.tt.tlzf.xstruct.trans.qry.QTransRsp;
import com.tt.tlzf.xstruct.trans.qry.TransQueryReq;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @Description 交易查询
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx200004 {

    public static void main(String[] args) {
        InfoReq infoReq = DemoUtil.makeReq("200004");
        TransQueryReq queryReq = new TransQueryReq();
        queryReq.setMERCHANT_ID(DemoConfig.merchantid);
        queryReq.setQUERY_SN("200604000006429-1555484266039");//查询交易的文件名
        //200604000006429-1555484266039  200604000006429-1555381739566
        AipgReq req = new AipgReq();
        req.setINFO(infoReq);
        req.addTrx(queryReq);

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
/*			AipgRsp rsp = XmlParser.parseRsp(respText);
			InfoRsp infoRsp = rsp.getINFO();
			System.out.println(infoRsp.getRET_CODE());
			System.out.println(infoRsp.getERR_MSG());
			if("0000".equals(infoRsp.getRET_CODE())){
				QTransRsp ret = (QTransRsp) rsp.trxObj();
				@SuppressWarnings("unchecked")
				List<QTDetail> list = ret.getDetails();
				for(QTDetail dt : list){
					System.out.println(dt.getRET_CODE());
					System.out.println(dt.getERR_MSG());
				}
			}*/
            String s = XmlExercise.xml2json(respText);
            System.out.println(s);

            if (s.substring(0, 1).equals("[")) {
                System.out.println("第一种情况");
                JSONArray ary = JSONArray.fromObject(s);
                System.out.println(ary.get(0));
                if (!ary.get(0).equals("")) {
                    JSONObject INFO = JSONObject.fromObject(ary.get(0));
                    System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                }
            } else {
                System.out.println("第二种情况");
                JSONObject res = JSONObject.fromObject(s);
                System.out.println("INFO:" + res.get("INFO"));
                if (res.get("INFO") != null && !res.get("INFO").equals("")) {
                    JSONObject INFO = JSONObject.fromObject(res.get("INFO"));
                    System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                }
                System.out.println("FAGRARET:" + res.get("FAGRARET"));
                if (res.get("FAGRARET") != null && !res.get("FAGRARET").equals("")) {
                    JSONObject FAGRARET = JSONObject.fromObject(res.get("FAGRARET"));
                    System.out.println("ERR_MSG:" + FAGRARET.get("ERR_MSG"));
                }
            }


        } catch (AIPGException e) {
            e.printStackTrace();
        }
    }

}
