package com.tt.tlzf.xstruct.quickpay;

public class QAGRINFO {
    private String MERCHANT_ID;//商户代码 商户ID
    private String AGRTYPE;//协议类型  01：标准快捷协议
    private String QUERY_MODE;// 查询模式  3:通过签约卡号查询
    private String ACCOUNT_NO;//账号

    public String getMERCHANT_ID() {
        return MERCHANT_ID;
    }

    public void setMERCHANT_ID(String MERCHANT_ID) {
        this.MERCHANT_ID = MERCHANT_ID;
    }

    public String getAGRTYPE() {
        return AGRTYPE;
    }

    public void setAGRTYPE(String AGRTYPE) {
        this.AGRTYPE = AGRTYPE;
    }

    public String getQUERY_MODE() {
        return QUERY_MODE;
    }

    public void setQUERY_MODE(String QUERY_MODE) {
        this.QUERY_MODE = QUERY_MODE;
    }

    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }

    public void setACCOUNT_NO(String ACCOUNT_NO) {
        this.ACCOUNT_NO = ACCOUNT_NO;
    }
}
