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

	public static String merchantid = "200604000006429";

	public static String username = merchantid + "04"; //用户名
	public static String userpass = "111111"; //用户密码

	public static String pathpfx = Config.TESTMODE
			? "config/" + merchantid + "02.p12"
			: "/home/tomcat/cert/" + merchantid + "02.p12"; //商户私钥路径
	public static String pfxpass = "111111"; //私钥密码

	public static String pathcer = Config.TESTMODE
			? "config/allinpay-pds.cer"
			: "/home/tomcat/cert/allinpay-pds.cer"; //通联公钥

}
