/*
 * @Description: TT数据库封装-连接池，数据源处理类
 * @Author: tt
 * @Date: 2018-12-20 19:48:20
 * @LastEditTime: 2019-06-18 11:23:18
 * @LastEditors: tt
 */
package com.tt.tool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DbConfig {
	public static int errorCode = 0;
	public static Map<String, DruidDataSource> g_dsmap = new HashMap<>();
	public static Map<String, Map<String, List<String>>> g_dsmap_fieldconf = new HashMap<>();

	/**
	 * @description: 获取数据源，创建数据源
	 * @param {type}
	 * @return:
	 */
	private static final DataSource getDataSource(String dbname, String user, String pass) throws Exception {
		DruidDataSource dataSource = null;

		Properties p = new Properties();
		/**
		 * todo 设置连接参数，具体的可以参考阿里云druid的每项配置的意义 下面的配置参数，每一项的具体意义可以参考阿里云Druid的文档。
		 */
		p.put("initialSize", "1");
		p.put("minIdle", "1");
		p.put("maxActive", Config.DB_MAXACTIVECOMNNECTION);// 比较重要的参数，最大连接数.连接池里面最大的连接数，超过这个数时，再获取连接需要等待
		p.put("maxWait", "3000");// 获取连接等待时间，超时时间
		p.put("timeBetweenEvictionRunsMillis", "60000");
		p.put("minEvictableIdleTimeMillis", "300000");
		p.put("validationQuery", "SELECT 'x' from dual");
		p.put("testWhileIdle", "true");
		p.put("testOnBorrow", "false");
		p.put("testOnReturn", "false");
		p.put("poolPreparedStatements", "false"); // 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
		p.put("maxPoolPreparedStatementPerConnectionSize", Config.DB_MAXACTIVECONN);// PSCache连接池大小。
		p.put("filters", "stat,wall,log4j");// todo 可以加上防sql注入参数,wall,stat
		p.put("logAbandoned", "true");
		p.put("removeAbandoned", "true");
		p.put("removeAbandonedTimeout", "30");
		String DB_URL = Config.DB_PRECONNSTR + dbname + Config.DB_ENDCONNSTR;
		p.put("url", DB_URL);
		p.put("username", user);
		p.put("password", pass);
		p.put("slowSqlMillis", "200");
		p.put("logSlowSql", "true");
		// p.put("sessionStatEnable","true");
		p.put("driverClassName", Config.DB_DRIVER);
		/** 使用阿里云开源数据库连接池管理 Druid */
		dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(p);
		if (g_dsmap.size() == 0) {//首次链接数据源
			dataSource.init();
		}
		g_dsmap.put(Integer.toString(g_dsmap.size() + 1), dataSource);
		return dataSource;
	}

	/**
	 * @description: 写日志。
	 * @param {type}
	 * @return:
	 */
	private static void mylog(String mString) {
		if (!Config.DEBUGMODE) { // 如果不是调试模式，不输出日志
			return;
		}
		if (Config.GLOBAL_SHOWLOG) {
			Log log = LogFactory.getLog(DbConfig.class); //
			log.info(mString);
			if (!Config.TESTMODE) { // 不是测试模式，log4j也打印
				Tools.logInfo(mString, DbConfig.class.toString());
			}
		}
	}

	/* 默认的数据库链接，目前直接写到类里 */
	static private synchronized void CreateCc(String dbname, String user, String pass) {
		try {
			mylog("开始连接数据库：" + dbname);
			getDataSource(dbname, user, pass);
		} catch (SQLException e) {
			// 处理 JDBC 错误
			errorCode = 999;
			mylog(e.getMessage());
			Tools.logError("DbConfig:errorCode :999,CreateCc() error:" + e.getMessage(), true, false);
		} catch (Exception e) {
			// 处理 Class.forName 错误
			errorCode = 998;
			Tools.logError("DbConfig:errorCode :998,CreateCc() error:" + e.getMessage(), true, false);
			mylog(e.getMessage());
		} finally {

		}
		mylog("Goodbye!");
	}

	/**
	 * 初始化DbConfig,为避免线程冲突同时初始化，故使用线程同步模式执行synchronized
	 * 
	 * @param ashowlog
	 */
	static public synchronized void init(boolean ashowlog) {
		if (g_dsmap.size() > 0) {// 已经初始化了
			return;
		}
		for (int i = 0; i < Config.DB_CONFIG.length; i += 3) {
			CreateCc(Config.DB_CONFIG[i], Config.DB_CONFIG[i + 1], Config.DB_CONFIG[i + 2]);
		}
	}

	/**
	 * @说明: 写入数据库表结构缓存，字段列表
	 * @param {type} {type}
	 * @return: 返回
	 */
	synchronized public static void updateFieldCon(String dsIndex, String tbName, List<String> fieldCon) {
		Map<String, List<String>> mpFieldCon = null;
		if (g_dsmap_fieldconf.get(dsIndex) != null) {
			mpFieldCon = g_dsmap_fieldconf.get(dsIndex);
		} else {
			mpFieldCon = new HashMap<>();
		}
		mylog("ADD:" + tbName + ":" + fieldCon);
		mpFieldCon.put(tbName, fieldCon);
		g_dsmap_fieldconf.put(dsIndex, mpFieldCon);
	}

	/**
	 * @说明: 读取数据库表结构缓存
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static List<String> getFieldCon(String dsIndex, String tbName) {
		List<String> re = null;
		try {
			re = g_dsmap_fieldconf.get(dsIndex).get(tbName);
			mylog("read:" + tbName + ":" + re);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return re;
	}

	/**
  * @说明: 清理表结构缓存
  * @param {type} {type}
  * @return: 返回
  */
 synchronized public static void cleanFieldConCache(String dsIndex, String tbName) {
		if (g_dsmap_fieldconf.size() > 0) {
			g_dsmap_fieldconf.get(dsIndex).remove(tbName);
		}
	}

	@RequestMapping("/cleanFieldConCacheAll")
	@ResponseBody
	/**
  * @说明: web页面重建表结构缓存，线上修改表结构时使用。
  * @param {type} {type}
  * @return: 返回
  */
 public static void cleanFieldConCacheAll(String tbName) {
		if (g_dsmap_fieldconf.size() > 0) {
			for (String key : g_dsmap.keySet()) {
				if (g_dsmap_fieldconf.get(key) != null) {
					g_dsmap_fieldconf.get(key).remove(tbName);
					mylog("clean Cache success:" + tbName + " for DS:" + key);
				}
			}
		}
	}

}
