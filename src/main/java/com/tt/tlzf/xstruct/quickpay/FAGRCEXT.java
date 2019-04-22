package com.tt.tlzf.xstruct.quickpay;

/**
 * @Description
 * @Author meixf@allinpay.com
 * @Date 2018年5月14日
 **/
public class FAGRCEXT extends FAGRC {

    private String ACCOUNT_NO;
    private String ACCOUNT_NAME;
    private String ID;
    private String ID_TYPE;
    private String TEL;

    public String getACCOUNT_NO() {
        return ACCOUNT_NO;
    }

    public void setACCOUNT_NO(String aCCOUNT_NO) {
        ACCOUNT_NO = aCCOUNT_NO;
    }

    public String getACCOUNT_NAME() {
        return ACCOUNT_NAME;
    }

    public void setACCOUNT_NAME(String aCCOUNT_NAME) {
        ACCOUNT_NAME = aCCOUNT_NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String tEL) {
        TEL = tEL;
    }

    public String getID_TYPE() {
        return ID_TYPE;
    }

    public void setID_TYPE(String ID_TYPE) {
        this.ID_TYPE = ID_TYPE;
    }
}
