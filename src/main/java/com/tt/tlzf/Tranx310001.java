package com.tt.tlzf;

import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.quickpay.FAGRA;
import com.tt.tlzf.xstruct.quickpay.FAGRARET;
import net.sf.json.JSONObject;

/**
 * @Description  快捷签约短信发起
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class Tranx310001 {

	//200604000006429-1555381739566
	public static void main(String args[]){
		System.out.println("11111111111"+System.currentTimeMillis());
		InfoReq inforeq = DemoUtil.makeReq("310001");
		//http://tyjjm.kcway.net/kcdhttp?query=2&type=310001&ACCOUNT_NAME=%E5%B5%87%E7%AB%B9%E4%B8%AD&BANK_CODE=&ACCOUNT_TYPE=&ACCOUNT_NO=6222004000493013989&ID_TYPE=&ID=620528197501116230&TEL=15601257600&CVV2=&VAILDDATE=&MERREM=%E6%B5%8B%E8%AF%95&REMARK=%E6%88%91%E8%A6%81%E7%AD%BE%E7%BA%A6
		FAGRA fagra = new FAGRA();
		fagra.setACCOUNT_NAME("嵇竹中");//
		fagra.setACCOUNT_NO("6222004000493013989");//
		fagra.setACCOUNT_PROP("0");
		fagra.setTEL("15601257600");//
		fagra.setID("620528197501116230");//
		fagra.setID_TYPE("0");
		fagra.setMERCHANT_ID(DemoConfig.merchantid);
		fagra.setACCOUNT_TYPE("00");
		fagra.setCVV2("");
		fagra.setVALIDDATE("");
		fagra.setMERREM("123");
		fagra.setREMARK("123123123");
		
		AipgReq req = new AipgReq();
		req.setINFO(inforeq);
		req.addTrx(fagra);
		try{
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
			if(!DemoUtil.verifyXml(respText)){
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
				FAGRARET fr = (FAGRARET)rsp.trxObj();
				System.out.println(fr.getRET_CODE());
				System.out.println(fr.getERR_MSG());
			}*/
		    String s=	XmlExercise.xml2json(respText);
			System.out.println(s);
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
}
