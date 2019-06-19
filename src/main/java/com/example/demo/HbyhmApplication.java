/*
 * @说明: 这里写说明哈
 * @Description: file content
 * @Author: tt
 * @Date: 2019-06-17 11:24:55
 * @LastEditTime: 2019-06-19 09:17:43
 * @LastEditors: tt
 */
package com.example.demo;

import com.tt.tool.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;

@ComponentScan(basePackages = {"com.example.demo",
        "com.tt.tool",
        "com.tt.manager",
        "com.tt.tlzf.servlet",
        "com.example.wx",
        "com.tt.timedtask",
        "com.tt.visual"}) // 映射
@SpringBootApplication
// @EnableAutoConfiguration
// ServletComponentScan只扫描启动类所在文件夹下的Servlet，故Druid的Servlet只能放到启动类的文件夹下
@ServletComponentScan // 加载druid的servlet，如果不用可以注释掉
public class HbyhmApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        Config.initTT();
        return application.sources(HbyhmApplication.class);
    }

    public static void main(String[] args) {
        Config.initTT();
        SpringApplication.run(HbyhmApplication.class, args);
    }
}
