package com.tt.tlzf.xstruct.ver;

public class RNPC {
	private String MERCHANT_ID;//商户代码;
	private String SRCREQSN;//原请求流水
	private String VERCODE;//短信验证码
	
	public String getMERCHANT_ID() {
		return MERCHANT_ID;
	}
	public void setMERCHANT_ID(String mERCHANTID) {
		MERCHANT_ID = mERCHANTID;
	}
	public String getSRCREQSN() {
		return SRCREQSN;
	}
	public void setSRCREQSN(String sRCREQSN) {
		SRCREQSN = sRCREQSN;
	}
	public String getVERCODE() {
		return VERCODE;
	}
	public void setVERCODE(String vERCODE) {
		VERCODE = vERCODE;
	}
	
}
