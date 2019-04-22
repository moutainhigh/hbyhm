package com.tt.tlzf.xstruct.quickpay;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2017年9月15日
 **/
public class FAGRC {
    private String MERCHANT_ID;   //商户代码
    private String SRCREQSN;   //对应申请请求报文中的REQ_SN
    private String VERCODE;   //短信验证码

    public String getMERCHANT_ID() {
        return MERCHANT_ID;
    }

    public void setMERCHANT_ID(String MERCHANT_ID) {
        this.MERCHANT_ID = MERCHANT_ID;
    }

    public String getSRCREQSN() {
        return SRCREQSN;
    }

    public void setSRCREQSN(String SRCREQSN) {
        this.SRCREQSN = SRCREQSN;
    }

    public String getVERCODE() {
        return VERCODE;
    }

    public void setVERCODE(String VERCODE) {
        this.VERCODE = VERCODE;
    }





}
