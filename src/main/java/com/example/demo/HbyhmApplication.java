package com.example.demo;

import com.tt.tool.Config;
import com.tt.tool.DataDic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;

@ComponentScan(basePackages = { "com.example.demo",
        "com.tt.tool",
        "com.tt.manager",
        "com.tt.tlzf.servlet",
        "com.example.wx"}) // 映射
@SpringBootApplication
// @EnableAutoConfiguration
// ServletComponentScan只扫描启动类所在文件夹下的Servlet，故Druid的Servlet只能放到启动类的文件夹下
@ServletComponentScan // 加载druid的servlet，如果不用可以注释掉
public class HbyhmApplication extends SpringBootServletInitializer implements WebApplicationInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        DataDic.initDic();
        System.setProperty("LOG4JDIR", Config.FILEUP_SAVEPATH + "log/"); // 配置log4j保存路径，使用http://xxx.com/upload/log/tt_debug.log可以下载
        return application.sources(HbyhmApplication.class);
    }

    public static void main(String[] args) {
        DataDic.initDic();
        System.setProperty("LOG4JDIR", Config.FILEUP_SAVEPATH + "log/");// 配置log4j保存路径，使用http://xxx.com/upload/log/tt_debug.log可以下载
        SpringApplication.run(HbyhmApplication.class, args);
    }
}
