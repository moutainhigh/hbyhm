/*
 * @说明: 这里写说明哈
 * @Description: file content
 * @Author: tt
 * @LastEditors: tt
 * @Date: 2018-05-31 10:00:54
 * @LastEditTime: 2019-04-15 17:54:33
 */
package com.tt.tlzf;


import com.tt.tool.Config;

/**
 * @Description JDK 1.7
 * @Author meixf@allinpay.com
 * @Date 2018年5月23日
 **/
public class DemoConfig {

    public static String TXZF_URL = Config.TESTMODE
            ? "https://test.allinpaygd.com/aipg/ProcessServlet" //测试环境
            : "https://tlt.allinpay.com/aipg/ProcessServlet";//正式环境
    public static String BUSINESS_CODE = Config.TESTMODE
            ? "19900"
            : "10702";
    public static String merchantid = Config.TESTMODE
            ? "200604000006429"
            : "200393000010128";//商户号

    public static String username = Config.TESTMODE
            ? merchantid + "04"
            : merchantid + "04"; //用户名
    public static String userpass = Config.TESTMODE
            ? "111111"
            : "111111"; //用户密码

    public static String pathpfx = Config.TESTMODE
            ? "config/test/" + merchantid + "02.p12"
            : "/home/tomcat/cert/shqzl/" + merchantid + "04.p12"; //商户私钥路径
    public static String pfxpass = Config.TESTMODE
            ? "111111"
            : "111111"; //私钥密码

    public static String pathcer = Config.TESTMODE
            ? "config/test/allinpay-pds.cer"
            : "/home/tomcat/cert/shqzl/allinpay-pds.cer"; //通联公钥
    // ? "/home/tomcat/cert/allinpay-pds.cer"
    //"/home/tomcat/cert/shqzl/allinpay-pds.cer"; //通联公钥

}
