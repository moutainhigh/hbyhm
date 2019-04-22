/*
 * @Description: druid的SQL监控页面WebServlet
 * @Author: tt
 * @Date: 2018-12-06 10:34:56
 * @LastEditTime: 2019-03-25 15:49:33
 * @LastEditors: tt
 */

package com.example.demo;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/druid_tt/*", initParams = {
		// @WebInitParam(name="allow",value="192.168.16.110,127.0.0.1"),// IP白名单
		// (没有配置或者为空，则允许所有访问)
		// @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单
		// (存在共同时，deny优先于allow)
		@WebInitParam(name = "loginUsername", value = "admin"), // 用户名
		@WebInitParam(name = "loginPassword", value = "888888"), // 密码
		@WebInitParam(name = "resetEnable", value = "false")// 禁用HTML页面上的“Reset All”功能
})
public class DruidServlet extends StatViewServlet {
	private static final long serialVersionUID = 1L;
}
