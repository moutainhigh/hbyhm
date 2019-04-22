package com.tt.tlzf;

import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.trans.qry.QTDetail;
import com.tt.tlzf.xstruct.trans.qry.QTransRsp;
import com.tt.tlzf.xstruct.trans.qry.TransQueryReq;

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
		queryReq.setQUERY_SN("200604000006429-1555381739566");//查询交易的文件名
		
		AipgReq req = new AipgReq();
		req.setINFO(infoReq);
		req.addTrx(queryReq);
		
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
				QTransRsp ret = (QTransRsp) rsp.trxObj();
				@SuppressWarnings("unchecked")
				List<QTDetail> list = ret.getDetails();
				for(QTDetail dt : list){
					System.out.println(dt.getRET_CODE());
					System.out.println(dt.getERR_MSG());
				}
			}
		}catch(AIPGException e){
			e.printStackTrace();
		}
	}

}
