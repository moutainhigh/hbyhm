package com.tt.tlzf;


import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.quickpay.FASTTRX;
import com.tt.tlzf.xstruct.quickpay.FASTTRXRET;
import com.tt.tlzf.xstruct.trans.LedgerDtl;
import com.tt.tlzf.xstruct.trans.Ledgers;


/**
 * @Description 快捷支付
 * @Author meixf@allinpay.com
 * @Date 2018年5月29日
 **/
public class Tranx310011 {

	public static void main(String[] args){
		InfoReq infoReq = DemoUtil.makeReq("310011");
		
		FASTTRX ft = new FASTTRX();
		ft.setMERCHANT_ID(DemoConfig.merchantid);
		ft.setBUSINESS_CODE("19900");//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
		ft.setSUBMIT_TIME(DemoUtil.getNow());
		ft.setAGRMNO("AIP3989190416000002699");
		ft.setACCOUNT_NAME("嵇竹中");
		ft.setAMOUNT("1");
		ft.setCUST_USERID("哈哈哈哈");
		ft.setREMARK("a发送到发斯蒂芬");
		ft.setSUMMARY("asjdfasdfkasdf");
		
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
			AipgRsp rsp = XmlParser.parseRsp(respText);
			InfoRsp infoRsp = rsp.getINFO();
			System.out.println(infoRsp.getRET_CODE());
			System.out.println(infoRsp.getERR_MSG());
			if("0000".equals(infoRsp.getRET_CODE())){
				FASTTRXRET ret = (FASTTRXRET) rsp.trxObj();
				System.out.println(ret.getRET_CODE());
				System.out.println(ret.getERR_MSG());
				System.out.println(ret.getSETTLE_DAY());
				
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}
	
}
