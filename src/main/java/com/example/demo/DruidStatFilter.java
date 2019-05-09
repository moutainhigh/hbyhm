/*
 * @Description: druid的web监控配置
 * @Author: tt
 * @Date: 2018-12-06 10:34:56
 * @LastEditTime: 2019-03-25 16:03:29
 * @LastEditors: tt
 */

package com.example.demo;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
                @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid_tt/*"),//忽略资源
                @WebInitParam(name = "sessionStatEnable", value = "true"),//session监控开关
                @WebInitParam(name = "principalSessionName", value = "tt_mid")//监控sessionname
        }
)
public class DruidStatFilter extends WebStatFilter {
}
