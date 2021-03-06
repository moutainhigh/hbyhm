/*
 * @Description: TT总配置文件。
 * @Author: tt
 * @Date: 2018-12-26 17:42:40
 * @LastEditTime: 2019-06-20 15:38:08
 * @LastEditors: tt
 */
package com.tt.tool;

import org.apache.log4j.Logger;

/**
 * TT总配置文件。
 */
public class Config {
	/* 测试/生产环境开关 */
	public final static boolean TESTMODE = true;
	/* 调试模式开关，调试模式下会打印日志。部分异常输出到浏览器 ，非调试模式，不打印日志 */
	public static boolean DEBUGMODE = false;
	/* 版本号 */
	public final static String TTVER = "2.0 build 20190620"; 
	// 项目名称：类似大地保险
	public final static String APP_TITLE = "人保进件"; 
	// 项目版本
	public final static String APP_VER = " 1.0 build 20190620"; 
	// 皮肤主题{skin-navy,skin-tyjj,skin-kcd，skin-black,skin-purple,skin-yellow,skin-red,skin-green}
	public static String MANAGER_SKINCSS = "skin-kcd";
	/**
	 * NOTE: 文件上传相关配置开始
	 */
	public final static String[] FILEUP_OKEXT = new String[] { "JPG", "PNG", "GIF", "BMP", "MP4", "ZIP","PDF",
			"RAR" };/* 允许的上传文件类型 */
	public final static long FILEUP_MAXSIZE = 1024 * 1024 * 5;/* 最大支持文件大小 */
	/* 后台管理员支持上传最大文件大小 */
	public final static long FILEUP_MAXSIZE_ISADMIN = 1024 * 1024 * 50;
	/* 测试/生产模式上传文件保存路径，/\开头的为绝对路径，否则是相对路径 */
	public final static String FILEUP_SAVEPATH = TESTMODE
	? "src/main/webapp/upload/" /* 测试模式上传文件保存路径，/\开头的为绝对路径，否则是相对路径 */
	: "/KCDIMG/assess/upload/"; /* 生产模式上传文件保存路径，/\开头的为绝对路径，否则是相对路径 */
	/* 对应访问的url前缀 */
	public final static String FILEUP_URLPRE = TESTMODE ? "/upload/" : "/upload/";
	/* 写文件时每次写入的块大小，默认8KB每次读写量 */
	public final static int FILEUP_WRITEBLOCKSIZE = 1024 * 8;
	/**
	 * NOTE: 数据库配置
	 * 数据库连接前缀，指定服务器和端口
	 */
	public final static String DB_PRECONNSTR = !TESTMODE?"jdbc:mysql://localhost:3306/":"jdbc:mysql://125.77.23.30:3306/";
	// 6.0以下用 "com.mysql.jdbc.Driver"
	public final static String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
	/* 连接池最大连接数.连接池里面最大的连接数，超过这个数时，再获取连接需要等待 */
	public final static String DB_MAXACTIVECOMNNECTION = "50";
	// 最大PSCache池里的数量,mysql，目前的项目可以关闭。提升性能不大
	public final static String DB_MAXACTIVECONN = "0"; 
	/* 数据源配置，格式：数据库名/用户名/密码，每三个一组为一个数据源 */
	public final static String[] DB_CONFIG = TESTMODE/* 数据源配置，格式：数据库名/用户名/密码，每三个一组为一个数据源 */
	? new String[]{ //测试环境
		"kcway2", "rootkcd", "4015d76a77f97180", 
	}
	: new String[]{ //生产环境
	"kcway2", "kcway", "NDXppG2qUNB6pXcA",
/*        "kgcdb", "kgcu","nULbhh9fZ79jQfUW" ,
"kjbdb", "kjb", "exxH29nVn232mDMS", */
	};
	/* 数据库连接字符串尾端配置，指定配置的一些参数，SSL，字符编码等 */
	public final static String DB_ENDCONNSTR = "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false&useSSL=false&serverTimezone=GMT%2B8";
	// 默认用户表
	public final static String DB_USERTABLENAME = "assess_admin"; 
	/**
	 * NOTE: 自写日志文件相关配置，使用log4j,文件保存位置等配置在log4j.Properties文件;
	 */
	public final static Boolean GLOBAL_SHOWLOG = true;
	public static Logger log = null;
	/**
	 * SQL防注入的相关配置
	 */
	/* SQL防注入白名单 */
	public static String[] SQLINJ_EXINCLUDE = new String[] { "form", "do", "cn", "id", "mid_add", "mid_edit",
			"wxmini_car_store", "car_stora", "color" };
	/* NOTE: SQL防注入黑名单，可以优化，自写过滤方法 */
	public static String[] SQLINJ_INJ_STR = new String[] { "'", " and ", "exec(", "exec ", "insert ", "select ","delete ", "update ", "count ", "*", "%", "chr(", "chr ", "mid(", "mid ", "master ", "truncate", "char(", "char ","declare ",  " or ", "+",  "alert(", "alert " };
	/**
	 * UI方面配置，字体，字体大小todo:可存于用户个性化设置的数据库中
	 */
	public static String UI_FONT_SIZE = "14";
	public static String UI_ZOOM = "1";
	public static String UI_FONT_FAMILY = null;// "kreon";
	// 字体设置的使用范围(jquery选择器)
	public static String UI_FONT_COMPONENTS = "body,span,b,input,select,a";
  // table包路径，如有修改，这里改为正确的
	public static String TT_TABLEPATH = "com.tt.table"; 
	// 后台测试管理员账号，可以重复登陆不会被挤下来
	public static String[] DEBUG_MIDS = new String[] { "4193", "217","4793" };
	public static boolean USESTATICIMG = true;
	// 动态修改debugMode
	synchronized public static void setDebugMode(boolean newDebugMode) {
		DEBUGMODE = newDebugMode;
	}
	/**
  * @说明: TT初始化方法
  * @param {type} {type}
  * @return: 返回
  */
 synchronized public static void initTT(){
		DataDic.initDic();//初始化select ,option的字典
		System.setProperty("LOG4JDIR", Config.FILEUP_SAVEPATH + "log/"); // 初始化log4j日志文件保存文件夹。以便可以直接下载
		log = Logger.getLogger(Config.class);
		DbConfig.init(true); //初始化数据库链接。
	}
}