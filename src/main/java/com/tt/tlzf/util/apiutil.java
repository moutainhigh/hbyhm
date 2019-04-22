package com.tt.tlzf.util;

public class apiutil {
    private  String code;
    private  String msg;
    private  String result;
    private  String RET_CODE;
    private  String ERR_MSG;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg(String err_msg) {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public String getRET_CODE() {
        return RET_CODE;
    }

    public void setRET_CODE(String RET_CODE) {
        this.RET_CODE = RET_CODE;
    }

    public String getERR_MSG() {
        return ERR_MSG;
    }

    public void setERR_MSG(String ERR_MSG) {
        this.ERR_MSG = ERR_MSG;
    }
}
