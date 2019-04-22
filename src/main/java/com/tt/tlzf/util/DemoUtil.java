package com.tt.tlzf.util;

import com.tt.tlzf.AIPGException;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.xstruct.common.InfoReq;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月24日
 **/
public class DemoUtil {
	private static Provider prvd = null;
	
	static{
		prvd = new BouncyCastleProvider();
	}
	
	public static InfoReq makeReq(String trxcod){
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(DemoConfig.merchantid +"-"+System.currentTimeMillis());
		info.setUSER_NAME(DemoConfig.username);
		info.setUSER_PASS(DemoConfig.userpass);
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("04");
		if("300000".equals(trxcod)||"300001".equals(trxcod)||"300003".equals(trxcod)||"REFUND".equals(trxcod)){
			info.setMERCHANT_ID(DemoConfig.merchantid);
		}
		return info;
	}
	
	public static String getNow(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}
	
	/**
	 * @param xmlMsg   待签名报文
	 * @param pathPfx   
	 * @param pass
	 * @return  签名后的报文
	 * @throws AIPGException
	 */
	public static String buildSignedXml(String xmlMsg) throws AIPGException {
		/*System.out.println("============================签名前报文============================");
		System.out.println(xmlMsg);*/
		if(xmlMsg == null){
			throw new AIPGException("传入的加签报文为空");
		}
		String IDD_STR="<SIGNED_MSG></SIGNED_MSG>";
		if(xmlMsg.indexOf(IDD_STR) == -1){
			throw new AIPGException("找不到签名信息字段");
		}
		String strMsg = xmlMsg.replaceAll(IDD_STR, "");
		AIPGSignature signature = new AIPGSignature(prvd);
		String signedStr = signature.signMsg(strMsg, DemoConfig.pathpfx, DemoConfig.pfxpass);
		String strRnt = xmlMsg.replaceAll(IDD_STR, "<SIGNED_MSG>" + signedStr + "</SIGNED_MSG>");
		return strRnt;
	}
	
	/**
	 * @param xmlMsg  返回报文
	 * @param pathCer  通联公钥
	 * @return
	 * @throws AIPGException
	 */
	public static boolean verifyXml(String xmlMsg) throws AIPGException{
		if(xmlMsg == null){
			throw new AIPGException("传入的验签报文为空");
		}
		int pre = xmlMsg.indexOf("<SIGNED_MSG>");
		int suf = xmlMsg.indexOf("</SIGNED_MSG>");
		if(pre == - 1 || suf == -1 || pre >= suf){
			throw new AIPGException("找不到签名信息");
		}
		String signedStr = xmlMsg.substring(pre + 12, suf);
		String msgStr = xmlMsg.substring(0, pre) + xmlMsg.substring(suf + 13);
		AIPGSignature signature = new AIPGSignature(prvd);
		return signature.verifyMsg(signedStr, msgStr, DemoConfig.pathcer);
	}
}
