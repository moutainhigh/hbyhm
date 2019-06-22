/*
 * @Description: 常用功能方法汇总。包括字符串类，数据库类，日期操作类，文件类
 * @Author: tt
 * @Date: 2018-12-12 17:55:41
 * @LastEditTime: 2019-06-19 16:19:38
 * @LastEditors: tt
 */
package com.tt.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

/**
 * TT工具类
 * 
 * @备注 TT所有工具类的封装类，纯静态的调用。
 * @包括 时间类，字符串类，文件IO类等。
 */
public class Tools {
	static final public char sp = 5;// 分割符号

	public static void mylog(String mString) {
		if (!Config.DEBUGMODE) { // 如果不是调试模式，不输出日志
			return;
		}
		if (Config.GLOBAL_SHOWLOG) {
			Log log = LogFactory.getLog(Tools.class); //
			log.info(mString);
			if (!Config.TESTMODE) { // 不是测试模式，log4j也打印
				Tools.logInfo(mString, Tools.class.toString());
			}
		}
	}

	/**
	 * 写入error级别的log,使用log4j,log4j的配置文件为log4j.properties
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public static void logInfo(String msg, String pre) {
		if (!Config.DEBUGMODE) { // 如果不是调试模式，不输出日志
			return;
		}
		if (Config.GLOBAL_SHOWLOG) {
			Config.log.debug(pre + ":" + msg);
		}
	}

	/**
	 * Map<String,Object>到Map<String,String>的转换
	 * 
	 * @param mso
	 * @return
	 */
	public static TtMap msoToMss(Map<String, Object> mso) {
		TtMap params = new TtMap();
		for (String key : mso.keySet()) {
			params.put(key, mso.get(key).toString());
		}
		return params;
	}

	/**
	 * List<Map<String,String>>到List<Map<String,Object>>的转换
	 * 
	 * @param lss
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> lssTolso(TtList lss) {
		String lssJson = jsonEncode(lss);
		List<Map<String, Object>> lmso = new ArrayList<>();
		lmso = (List<Map<String, Object>>) JSON.parse(lssJson);
		return lmso;
	}

	/**
	 * OBJECT格式的转换到JSONG字符串
	 *
	 * @param object
	 * @return
	 */
	public static String jsonEncode(Object object) {
		return JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 返回object
	 *
	 * @param mpStr
	 * @return
	 */
	public static Object jsonDeCode(String mpStr) {
		return JSON.parse(mpStr);
	}

	/**
	 * 返回mss,先mso再转换mss
	 *
	 * @param mpStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TtMap jsonDeCode_mpob(String mpStr) {
		TtMap mp = new TtMap();
		Map<String, Object> mo = JSONObject.fromObject(mpStr);
		for (String key : mo.keySet()) {
			mp.put(key, mo.get(key).toString());
		}
		return mp;
	}

	/**
	 * 转换Map格式到json格式字符串
	 *
	 * @param mpStr
	 * @return
	 */
	public static String jsonEncode(TtMap mpStr) {
		return JSON.toJSONString(mpStr);
	}

	/**
	 * JSON格式字符串到TtMap Map<String,String>的转换
	 *
	 * @param mpStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TtMap jsonDeCode_mp(String mpStr) {
		TtMap mp = new TtMap();
		Map<Object, Object> maps = (Map<Object, Object>) JSON.parse(mpStr);
		for (Object map : maps.entrySet()) {
			mp.put(((Map.Entry<Object, Object>) map).getKey().toString(),
					((Map.Entry<Object, Object>) map).getValue().toString());
		}
		return mp;
	}

	/**
	 * 返回格式为2018/09/11这样的字符串路径（取当前日期）
	 *
	 * @return
	 */
	public static String dirDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("/yyyy/MM/dd/");
		String dateString = formatter.format(new Date());
		return dateString;
	}

	/**
	 * 取MD5值
	 *
	 * @param inStr
	 * @return
	 */
	public static String md5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			mylog(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 获取随机字符串
	 *
	 * @param length
	 * @return
	 */
	public static String getRandomStringByLength(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(62);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 字符串数组中是否包含指定值
	 *
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean arrayIndexOf(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 字符串数组中是否包含指定值
	 *
	 * @param arr
	 * @param targetValue
	 * @return
	 */
	public static boolean inArray(String[] arr, String targetValue) {
		return Arrays.asList(arr).contains(targetValue);
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 *
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * @description 计算时间戳
	 * @param 无
	 * @return 当前时间秒级别的时间戳 86400秒为一天 更多的可以参考{@link Tools#time(String, Boolean)
	 *         带参数的time()}.
	 */
	public static long time() {
		return System.currentTimeMillis() / 1000;
	}

	/**
	 * @description 计算时间戳bMills代表毫秒级
	 * @param strDate 字符串的日期，为null或者""时取当前时间
	 * @param bMillis 为true时，毫秒级返回
	 * @return time(null,true)为当前时间的毫秒级时间戳
	 */
	public static long time(String strDate, Boolean bMillis) {
		long result = myIsNull(strDate) ? new Date().getTime() : Tools.strToDateLong(strDate).getTime();
		result = bMillis == false ? result / 1000 : result;
		return result;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 *
	 * @param dateDate
	 * @return 返回示例 2019-02-12 22:33:22
	 */
	public static String dateToStrLong(Date dateDate) {
		if (dateDate == null) {
			dateDate = new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 *
	 * @param dateDate
	 * @param k
	 * @return 返回示例 2019-02-12
	 */
	public static String dateToStr(Date dateDate) {
		if (dateDate == null) {
			dateDate = new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 *
	 * @param strDate
	 * @return 返回示例 Date类
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * @description: 判断字符串是否为空，""和null时为true
	 * @param {type}
	 * @return 返回示例myIsNull("")=true myIsNull(null)=true, s为""或者null时为true
	 */
	public static boolean myIsNull(String s) {
		return s == null || s.isEmpty();
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyyMMddHHmmss
	 *
	 * @param strDate
	 * @return
	 */
	public static String getDatetoaa() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = formatter.format(new Date());
		return dateString;
	}

	/**
	 * 获取当前登陆用户的id，从session中获取id
	 * 
	 * @return 返回示例 168
	 */
	public static long mid() {
		// todo 获取当前登陆用户的id。可以更加完善下。防止多用户登陆同一账号
		long id = 0;
		try {
			HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getSession();
			String idmd5 = (String) session.getAttribute("idmd5");
			if (!myIsNull(idmd5)) {
				id = strToLong(recinfo("select mid from sys_session where idmd5='" + idmd5 + "' and outdt=0").get("mid"));
				// id
				// =
				// (long)
				// session.getAttribute("tt_mid");
			}
		} catch (Exception e) {
			if (Config.DEBUGMODE) {
				// Tools.logError(e.getMessage());
				e.printStackTrace();
			}
		} finally {
		}
		return id;
	}

	/**
	 * 获取当前登陆的用户是否是后台管理员权限，通过session
	 */
	public static boolean isAdmin() {
		// todo 获取当前登陆用户是否是后台管理员，需要设置一个标志。
		/* 直接从session中获取，也可以从数据表中获取当前登陆用户的isadmin字段是否为1来判断是否管理员 */
		try {
			return (boolean) ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
					.getSession().getAttribute("tt_isadmin");
		} catch (Exception e) {
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 指定的list里是否包含某个String
	 *
	 * @param str
	 * @param arr
	 * @return
	 */
	public static boolean inArrayList(String str, List<String> arr) {
		return arr.contains(str);
	}

	/**
	 * MAP到String的转换
	 *
	 * @param map
	 * @param fgchar
	 * @return
	 */
	public static String mapToString(TtMap map, String fgchar) {
		if (myIsNull(fgchar)) {
			fgchar = ",";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iter.next();
			sb.append(entry.getKey());
			sb.append('=').append('"');
			sb.append(entry.getValue());
			sb.append('"');
			if (iter.hasNext()) {
				sb.append(fgchar).append(' ');
			}
		}
		return sb.toString();
	}

	/**
	 * 过滤mysql 注入
	 */
	public static boolean sqlInj(String str) {
		str = str.toLowerCase();
		if (arrayIndexOf(Config.SQLINJ_EXINCLUDE, str)) { // 在白名单里面
			return false;
		}
		// 这里的东西还可以自己添加
		for (int i = 0; i < Config.SQLINJ_INJ_STR.length; i++) {
			if (str.indexOf(Config.SQLINJ_INJ_STR[i]) >= 0) {
				String msg = str + "_注入__，匹配过滤关键字：" + Config.SQLINJ_INJ_STR[i];
				mylog(msg);
				return true;
			}
		}
		return false;
	}

	/**
	 * @description: js过滤替换，正则表达式方法,去掉js标签
	 * @param {type}
	 * @return:
	 */
	public static String jsInjReplace(String str) {
		str = java.util.regex.Pattern.compile("<[^><]*script[^><]*>", Pattern.CASE_INSENSITIVE).matcher(str).replaceAll("");
		return str;
	}

	/**
	 * 获取request的所有参数名和值安全过滤后保存到map，包括url和post表单提交的数据
	 * 
	 * @param {type}
	 * @return 完整的map型的参数值，key为参数名，value为对应的值。统一转换为String
	 */
	public static TtMap getPostMap(HttpServletRequest request) {
		return getPostMap(request, false);// url参数和post参数都一起处理，url和post表单里有重复的值时，每条值用sp连接起来
	}

	/**
	 * 获取request的所有参数名和值安全过滤后保存到map
	 *
	 * @param request
	 * @param onlyPost 是否只获取post表单里面的数据，忽略url传递的参数值
	 * @return
	 */
	public static TtMap getPostMap(HttpServletRequest request, boolean onlyPost) {
		TtMap result = new TtMap();
		Enumeration<String> ennum = request.getParameterNames();
		TtMap mpUrlFiters = null;
		if (onlyPost) {
			String urlQuerysString = request.getQueryString();
			mpUrlFiters = URLRequest(urlQuerysString); // 如果只收集post数据，url里面不考虑，就要过滤掉url里重复的值
		}
		while (ennum.hasMoreElements()) {
			String paramName = (String) ennum.nextElement();
			if (sqlInj(paramName) == true) {// 过滤参数名
				mylog("mysql参数名注入：" + paramName);
				continue;
			} else {

			}
			String[] values = request.getParameterValues(paramName);
			if (onlyPost && mpUrlFiters.containsKey(paramName)) {
				if (values.length > 1) {
					values[0] = "{****}";// 跟url里面的值重复，设置第一个（即url里面重复的字段）值无效
				} else {// 只有一个
					continue;
				}
			}
			String value = "";
			for (int i = 0; i < values.length; i++) {
				if (values[i].equals("{****}")) {// 过滤掉第一个
					continue;
				}
				value += values[i] + sp;
			}
			if (myIsNull(value) == false) {
				value = value.substring(0, value.length() - 1);// 去掉char(5);
			}
			if (sqlInj(value) == true) {// 过滤参数值
				mylog("mysql参数值注入：" + value);
			} else {
				result.put(jsInjReplace(paramName), jsInjReplace(value));
			}
		}
		return result;
	}

	/**
	 * @description: 获取URL参数列表到map
	 * @param {type}
	 * @return:
	 */
	public static TtMap getUrlParam() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String urlQuerysString = request.getQueryString();
		mylog("getQueryString:" + urlQuerysString);
		TtMap mpUrl = URLRequest(urlQuerysString);
		return mpUrl;
	}

	/**
	 * 解析出url参数中的键值对 如 "cn=admin&type=demo&sdo=form&id=21"cn:admin,type:demo等存入map中
	 * 
	 * @return mapRequest
	 */
	public static TtMap URLRequest(String strUrlParam) {
		TtMap mapRequest = new TtMap();

		String[] arrSplit = null;
		if (strUrlParam == null) {
			return mapRequest;
		}
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");
			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * @description: 返回删除指定字段后的url*,useAge urlKill("cn|id|type"); 不作注入过滤验证
	 * @param {type}
	 * @return:
	 */
	public static String urlKill(String s) {
		String result = "";
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			String urlQuerysString = request.getQueryString();
			TtMap mpUrl = URLRequest(urlQuerysString);
			s = s.toLowerCase();
			String[] ss = s.split("\\|");
			for (Iterator<Map.Entry<String, String>> it = mpUrl.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> item = it.next();
				if (arrayIndexOf(ss, item.getKey().toLowerCase())) {
					it.remove();
				}
				// ... todo with item
			}
			for (String key : mpUrl.keySet()) {
				if (myIsNull(key)) {
					continue;
				}
				result = result + "&" + key + "=" + mpUrl.get(key);
			}
			if (!myIsNull(result)) {
				result = "?" + result.substring(1, result.length());// 去掉前面多余出来的&
			} else {
				result = "?";
			}
			result = request.getRequestURI() + result;
		} catch (Exception e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @description: 获取当前URL的基本网址，如http://kjtest.kcway.net/
	 * @param {type}
	 * @return:
	 */
	public static String getBaseUrl() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			return request.getScheme() + "://" + request.getServerName()
					+ (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + "/";
		} catch (Exception E) {
			if (Config.DEBUGMODE) {
				E.printStackTrace();
			}
			mylog(E.getMessage());
			return "";
		}
	}

	// =================================数据库相关处理方法=================================
	/**
	 * undic获取id为nid的name值,TtMap模式
	 */
	public static String unDic(TtMap tbName, String nid) {
		return tbName.get(nid);
	}

	/**
	 * undic获取id为nid的name值,TtMap模式
	 */
	public static String unDic(TtMap tbName, long nid) {
		return tbName.get(longToStr(nid));
	}

	/**
	 * undic获取id为nid的name值,mysql表模式
	 */
	public static String unDic(String tbName, long nid) {
		DbTools dbt = new DbTools();
		String result = null;
		try {
			result = dbt.unDic(tbName, nid);
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * undic获取id为nid的name值,mysql表模式
	 */
	public static String unDic(String tbName, String nid) {
		DbTools dbt = new DbTools();
		String result = null;
		try {
			result = dbt.unDic(tbName, nid);
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * undic("kjb_user","111111","username","userpass");获取kjb_user表里的userpass为111111的username值
	 */
	public static String unDic(String tbName, String nid, String fieldName, String fieldId) {
		DbTools dbt = new DbTools();
		String result = null;
		try {
			result = dbt.unDic(tbName, nid, fieldName, fieldId);
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dtTools的recinfo的简化版
	 */
	public static TtMap recinfo(String sql) {
		TtMap result = null;
		DbTools dbt = new DbTools();
		try {
			result = dbt.recinfo(sql);
		} catch (Exception e) {
			Tools.logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dbCtrl的edit简化版
	 */
	public static int recEdit(TtMap info, String table, long id) {
		int result = 0;
		DbCtrl dbCtrl = new DbCtrl(table);
		try {
			result = dbCtrl.edit(info, id);
		} catch (Exception e) {
			Tools.logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbCtrl.closeConn();
		}
		return result;
	}

	/**
	 * dbCtrl的add简化版
	 */
	public static long recAdd(TtMap info, String table) {
		long result = 0;
		DbCtrl dbCtrl = new DbCtrl(table);
		try {
			result = dbCtrl.add(info);
		} catch (Exception e) {
			Tools.logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbCtrl.closeConn();
		}
		return result;
	}

	/**
	 * dtTools的recexec的简化版
	 */
	public static boolean recexec(String sql) {
		DbTools dbt = new DbTools();
		boolean result = false;
		try {
			result = dbt.recexec(sql);
		} catch (Exception e) {
			Tools.logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dtTools的reclist的简化版
	 */
	public static TtList reclist(String sql) {
		TtList result = null;
		DbTools dbt = new DbTools();
		try {
			result = dbt.reclist(sql);
		} catch (Exception e) {
			Tools.logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dicOpt,显示某个表里所有的id和name值的<option value=
	 * "id">name</option>的HTML代码，指定name字段名和id字段名
	 * ttDic为字典名称，演示在DataDic里面的字典，defValue为默认选中的值
	 */
	public static String dicopt(TtMap ttDic, String defValue) {
		String result = "";
		for (String key : ttDic.keySet()) {
			result = result + "<option value=\"" + key + "\"" + (key.equals(defValue) ? " selected" : "") + ">"
					+ ttDic.get(key) + "</option>";
		}
		return result;
	}

	/**
	 * dicopt,显示某个表里所有的id和name值的<option value=
	 * "id">name</option>的HTML代码，指定name字段名和id字段名 tbName为表名，id为默认选中id,暂未使用。
	 */
	public static String dicopt(String tbName, long id, String nameFiled, String idField, String re, String wheres) {
		if (Tools.myIsNull(nameFiled)) {
			nameFiled = "name";
		}
		if (Tools.myIsNull(idField)) {
			idField = "id";
		}
		String result = "";
		DbCtrl dbt = new DbCtrl(tbName);
		dbt.showall = true;
		dbt.getall = true;
		dbt.nopage = true;
		try {
			TtList lttmp = dbt.lists(wheres, "t." + nameFiled + ",t." + idField);
			for (int i = 0; i < lttmp.size(); i++) {
				long nowid = Long.parseLong(lttmp.get(i).get("id"));
				result = result + "<option value=\"" + lttmp.get(i).get(idField) + "\"" + (nowid == id ? " selected" : "") + ">"
						+ lttmp.get(i).get(nameFiled) + "</option>";
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dicopt,显示某个表里所有的id和name值的<option value=
	 * "id">name</option>的HTML代码，指定name字段名和id字段名 tbName为表名，id为默认选中id,暂未使用。
	 */
	public static String dicopt(String tbName, long id, String nameFiled, String idField, String re) {
		if (Tools.myIsNull(nameFiled)) {
			nameFiled = "name";
		}
		if (Tools.myIsNull(idField)) {
			idField = "id";
		}
		String result = "";
		DbCtrl dbt = new DbCtrl(tbName);
		dbt.showall = true;
		dbt.getall = true;
		dbt.nopage = true;
		try {
			TtList lttmp = dbt.lists("", "t." + nameFiled + ",t." + idField);
			for (int i = 0; i < lttmp.size(); i++) {
				long nowid = Long.parseLong(lttmp.get(i).get("id"));
				result = result + "<option value=\"" + lttmp.get(i).get(idField) + "\"" + (nowid == id ? " selected" : "") + ">"
						+ lttmp.get(i).get(nameFiled) + "</option>";
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dicOpt,显示某个表里所有的id和name值的<option value="id">name</option>的HTML代码
	 * tbName为表名，id为默认选中id
	 */
	public static String dicopt(String tbName, long id) {
		String result = "";
		DbCtrl dbt = new DbCtrl(tbName);
		dbt.showall = true;
		dbt.getall = true;
		dbt.nopage = true;
		try {
			TtList lttmp = dbt.lists("", "t.name,t.id");
			for (int i = 0; i < lttmp.size(); i++) {
				long nowid = Long.parseLong(lttmp.get(i).get("id"));
				result = result + "<option value=\"" + lttmp.get(i).get("id") + "\"" + (nowid == id ? " selected" : "") + ">"
						+ lttmp.get(i).get("name") + "</option>";
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * @description: dicOpt,带条件和返回格式的显示某个表里所有的id和name值的<option value=
	 *               "id">name</option>的HTML代码
	 * @param wheres 带条件
	 * @param re     re为返回格式，为"json"时，返回json格式
	 * @return 演示<option value="1">xxxx</option><option value="2">yyyy</option>
	 */
	public static String dicopt(String tbName, long id, String wheres, String re) {
		DbCtrl dbt = new DbCtrl(tbName);
		dbt.showall = true;
		dbt.getall = true;
		dbt.nopage = true;
		String result = "";
		try {
			mylog(wheres);
			TtList lttmp = dbt.lists(wheres, "t.name,t.id");
			// TtMap mpTmp = new TtMap();
			if (myIsNull(re) == true) {
				re = "";
			}
			for (int i = 0; i < lttmp.size(); i++) {
				long nowid = Long.parseLong(lttmp.get(i).get("id"));
				if (re.equals("json")) {
					// mpTmp = lttmp.get(i);
				} else {
					result = result + "<option value=\"" + lttmp.get(i).get("id") + "\"" + (nowid == id ? " selected" : "") + ">"
							+ lttmp.get(i).get("name") + "</option>";
				}
			}
			if (re.equals("json")) {
				result = JSON.toJSONString(lttmp);
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	/**
	 * dicOpt，根据post里传送的条件来定位 post.put("state_id","104");就是where state_id='104'
	 * post.put("mid_add","59");就是where state_id='104' AND mid_add='59' id是默认的选择项
	 * re代表返回的数据类型，为"json"时返回json格式，否则返回<option value="id">name</option>这样的
	 */
	public static String dicopt(String tbName, long id, TtMap post, String re) {
		DbCtrl dbt = new DbCtrl(tbName);
		dbt.showall = true;
		dbt.getall = true;
		dbt.nopage = true;
		String wheres = "";
		String result = "";
		try {
			if (post != null) {
				for (String key : post.keySet()) {
					wheres = wheres + key + "='" + post.get(key) + "' AND ";
					mylog(key);
				}
			}
			if (myIsNull(wheres) == false) {
				wheres = wheres.substring(0, wheres.length() - 5);
			}
			mylog(wheres);
			TtList lttmp = dbt.lists(wheres, "t.name,t.id");
			// TtMap mpTmp = new TtMap();
			if (myIsNull(re) == true) {
				re = "";
			}
			for (int i = 0; i < lttmp.size(); i++) {
				long nowid = Long.parseLong(lttmp.get(i).get("id"));
				if (re.equals("json")) {
					// mpTmp = lttmp.get(i);
				} else {
					result = result + "<option value=\"" + lttmp.get(i).get("id") + "\"" + (nowid == id ? " selected" : "") + ">"
							+ lttmp.get(i).get("name") + "</option>";
				}
			}
			if (re.equals("json")) {
				result = JSON.toJSONString(lttmp);
			}
		} finally {
			dbt.closeConn();
		}
		return result;
	}

	// =================================文件io相关处理方法,更多功能参考FileTools.java=================================
	/**
	 * FileExists，检测文件/目录是否存在
	 */
	public static boolean fileExists(String strFileFullPath) {
		File file = new File(strFileFullPath);
		return file.exists();
	}

	/**
	 * 是否目录
	 *
	 * @param strFileFullPath
	 * @return
	 */
	public static boolean isDir(String strFileFullPath) {
		File file = new File(strFileFullPath);
		return file.exists() ? file.isDirectory() : false;
	}

	/**
	 * 创建文件夹，层级创建。包括子文件夹，一直到创建完成
	 * 
	 * @param strFileFullPath 要创建的目录完整路径，如 /tt/ttxx/aaa/bbbb 或者 d:\xxx\bbb\aaa\ccc
	 * @return 如果目录存在，返回成功，否则失败
	 */
	public static boolean createDir(String strFileFullPath) {
		if (isDir(strFileFullPath)) {
			return true;
		}
		File file = new File(strFileFullPath);
		file.mkdirs();
		try {
			Runtime.getRuntime().exec("chmod 777 -R " + strFileFullPath);
		} catch (IOException e) {

			logError(e.getMessage());
			e.printStackTrace();
		}
		return isDir(strFileFullPath);
	}

	/**
	 * 删除一个文件/目录，目录时必须空目录，更多功能参考FileTools.java
	 *
	 * @param strFileFullPath
	 * @return
	 */
	public static boolean delFile(String strFileFullPath) {
		File file = new File(strFileFullPath);
		if (file.exists()) {// 文件存在
			return file.delete();
		} else { // todo 不存在，直接返回真
			return true;
		}
	}

	/**
	 * 获取文件扩展名，不带.
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String getFileExt(String strFullFilePath) {
		return strFullFilePath.substring(strFullFilePath.lastIndexOf(".") + 1);
	}

	/**
	 * 获取文件扩展名，不带.
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String extractFileExt(String strFullFilePath) {
		return getFileExt(strFullFilePath);
	}

	/**
	 * 格式化一个文件路径字符串，处理路径分隔符斜杆和反斜杆windows下\\,linux下/的问题，格式化后变成目前部署的环境下正确的分隔符，同时去掉重复的分隔符
	 * 带http的必须用小写
	 *
	 * @param strFilePath
	 * @return
	 */
	public static String formatFilePath(String strFilePath) {
		strFilePath = strFilePath.replace("\\\\", "\\");
		strFilePath = strFilePath.replace("\\\\", "\\");// 再次，防止有\\\\格式的
		strFilePath = strFilePath.replace("//", "/");
		strFilePath = strFilePath.replace("//", "/");
		strFilePath = strFilePath.replace("http:/", "http://"); // 原来http://被变成http:/了，所以恢复http的//
		strFilePath = strFilePath.replace("https:/", "https://");
		return strFilePath.replace('/', File.separatorChar).replace('\\', File.separatorChar)
				.replace("//", File.separatorChar + "").replace("\\\\", File.separatorChar + "");
	}

	/**
	 * 获取一个长路径字符串的文件名
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String extractFileName(String strFullFilePath) {
		String r = strFullFilePath.substring(formatFilePath(strFullFilePath).lastIndexOf(File.separator) + 1);
		return r;
	}

	/**
	 * 获取一个长路径串的去掉文件名后的，保留/或者\\
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String extractFilePath(String strFullFilePath) {
		return strFullFilePath.substring(0, strFullFilePath.length() - extractFileName(strFullFilePath).length());
	}

	/**
	 * 给当前路径末尾加上分隔符\\或者/
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String addSpc(String strFullFilePath) {
		if (myIsNull(strFullFilePath) == false
				&& !strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
			return strFullFilePath + File.separator;
		} else {
			return strFullFilePath;
		}
	}

	/**
	 * 删除路径串末尾的/或者\\
	 *
	 * @param strFullFilePath
	 * @return
	 */
	public static String delSpc(String strFullFilePath) {
		if (myIsNull(strFullFilePath) == false
				&& strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
			while (strFullFilePath.substring(strFullFilePath.length() - 1).equals(File.separator)) {
				strFullFilePath = strFullFilePath.substring(0, strFullFilePath.length() - 1);
			}
			return strFullFilePath;
		} else {
			return strFullFilePath;
		}
	}

	/**
	 * 复制文件，自动创建目标文件所在文件夹，如果目标文件复制前已经存在，自动先删除
	 *
	 * @param srcFile 源文件
	 * @param toFile  目标文件
	 * @return boolean，返回成功信息
	 * @throws IOException
	 */
	public static boolean ttCopyFile(String srcFile, String toFile) throws IOException {
		boolean result = false;
		// System.out.println("ttCopyFile1");
		if (Tools.fileExists(srcFile) == false) {
			return result;
		}
		// System.out.println("ttCopyFile11");
		if (Tools.delFile(toFile) == false) {
			return result;
		}
		// System.out.println("ttCopyFile2");
		Tools.createDir(Tools.delSpc(Tools.extractFilePath(toFile)));
		File source = new File(srcFile);
		File dest = new File(toFile);
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		// System.out.println("ttCopyFile3");
		try {
			// System.out.println("开始复制文件：" + srcFile);
			Tools.delFile(toFile);// 删除旧的文件
			FileInputStream fSource = new FileInputStream(source);
			FileOutputStream fDest = new FileOutputStream(dest);
			inputChannel = fSource.getChannel();
			outputChannel = fDest.getChannel();
			// System.out.println("开始transferFrom：" + toFile);
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			result = true;
			inputChannel.close();
			outputChannel.close();
			fSource.close();
			fDest.close();
		} catch (Exception E) {
			if (Config.DEBUGMODE) {
				E.printStackTrace();
			}
			// System.out.println("copy file exception:" + E.getMessage());
			logError(E.getMessage());
		} finally {
		}
		if (result) {// 复制完成，检查文件是否存在。
			result = Tools.fileExists(toFile);
		}
		return result;
	}

	/**
	 * 移动文件，复制完文件，删除源文件
	 *
	 * @param srcFile
	 * @param toFile
	 * @return
	 * @throws IOException
	 */
	public static boolean ttMoveFile(String srcFile, String toFile) throws IOException {
		boolean result = false;
		try {
			result = ttCopyFile(srcFile, toFile);
			if (result) {
				Tools.delFile(srcFile);
			}
		} catch (IOException ee) {
			if (Config.DEBUGMODE) {
				ee.printStackTrace();
			}
			result = false;
		}
		return result;
	}

	/**
	 * @description: 清理指定文件夹，包括文件和子文件夹下所有
	 * @param {type}
	 * @return:
	 */
	public static void delTree(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			delFile(folderPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description: 清理指定文件夹下的所有文件和子文件夹
	 * @param {type}
	 * @return:
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delTree(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 生成文件名，根据当前毫秒时间+随机10位字符串然后MD5后的文件名
	 */
	public static String getTimeAndRandMd5FileName() {
		String result = String.valueOf(System.currentTimeMillis());
		return md5(getRandomStringByLength(10) + result);
	}

	/**
	 * 生成文件名，根据当前毫秒时间+随机10位字符串然后MD5后的文件名
	 */
	public static String getTimeMd5FileName() {
		String result = String.valueOf(System.currentTimeMillis());
		return md5(result);
	}

	/**
	 * 生成文件名，根据当前时间，如20190129105438907
	 */
	public static String getNowDateFileName() {
		Date dateDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * @description: 错误日志写入数据库表中
	 * @param {type}
	 * @return 无返回
	 */
	public static void writeLogToDb(String errorMsg) {
		DbCtrl dbCtrl = new DbCtrl("sys_error");
		try {
			dbCtrl.lists();
			if (dbCtrl.recs < 5000) {// 避免写库死循环
				TtMap info = new TtMap();
				info.put("errormsg", errorMsg);
				dbCtrl.add(info);
			}
		} catch (Exception e) {
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		} finally {
			dbCtrl.closeConn();
		}
	}

	/**
	 * 写入error级别的log,使用log4j,log4j的配置文件为log4j.properties
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void logError(String arg, boolean writeDbLog, boolean go404) {
		if (Config.GLOBAL_SHOWLOG && Config.DEBUGMODE) {
			Config.log.error("Tools.logError:" + arg);
		}
		if (Config.DEBUGMODE) { // 调试模式下，终止当前页面，跳转到404页面，显示错误日志
			if (writeDbLog) {
				writeLogToDb(arg);
			} // 日志写入数据库
			if (go404) {
				HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				req.setAttribute("errorMsg", arg);
				UrlTools.goForward("/manager/404");
			}
		}
	}

	/**
	 * 写入error级别的log,只写入log4j的log文档，但不写入数据库，不跳转到错误提示页面
	 * 
	 * @param arg
	 * @throws Exception
	 */
	public static void logError(String arg) {
		logError(arg, false, false);
	}

	/**
	 * @description: 格式化结果
	 * @param {type}
	 * @return:
	 */
	public static void formatResult(TtMap result, boolean success, int code, String msg, String next_url) {
		result.put("success", success ? "true" : "false");
		result.put("errorcode", success ? "0" : String.valueOf(code));
		result.put("msg", msg);
		result.put("next_url", next_url);
	}

	/**
	 * @description: 格式化结果，结果里带object
	 * @param {type}
	 * @return:
	 */
	public static void formatResultobj(Map<String, Object> result, boolean success, int code, String msg) {
		result.put("success", success ? true : false);
		result.put("errorcode", success ? 0 : code);
		result.put("msg", msg);
	}

	/**
	 * @description: 设置当前登陆用户，需要sys_session表的配合。
	 * @param {type}
	 * @return:
	 */
	public static void setNowUser(long id, boolean isadmin) {
		HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()
				.getSession();
		String[] arrDebugs = Config.DEBUG_MIDS;
		String idmd5 = md5(String.valueOf(time("", true)));
		session.setAttribute("idmd5", idmd5); // session.setAttribute("tt_mid", id);
		session.setAttribute("tt_isadmin", isadmin);
		String sip = HttpTools
				.getIpAddress(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
		if (!inArray(arrDebugs, id + "")) {// 不是演示账号，清除上次登陆信息
			recexec("update sys_session set outdt='" + time() + "',outip='" + sip + "' where mid='" + id + "' and outdt=0");
		}
		recexec("insert into sys_session set idmd5='" + idmd5 + "', mid='" + id + "',logdt='" + time() + "',logip='" + sip
				+ "'");
	}

	/**
	 * @description: 获取项目路径
	 * @param {type}
	 * @return:
	 */
	public static String getRootPath() {
		File directory = new File("");// 参数为空
		String author = directory.getAbsolutePath();// 绝对路径;
		return addSpc(author);
	}

	/**
	 * @description: 获取当前登陆用户信息
	 * @param tbName 用户信息表的表名。
	 * @return:
	 */
	public static TtMap minfo(String tbName) {
		return Tools.recinfo("select * from " + tbName + " where id=" + mid());
	}

	/**
	 * @description: 获取当前登陆用户的信息，Config.DB_USERTABLENAME中此用户记录值，完整的值
	 * @param {type}
	 * @return:
	 */
	public static TtMap minfo() {
		return minfo(Config.DB_USERTABLENAME);
	}

	/**
	 * @description: long到String的转换
	 * @param {type}
	 * @return:
	 */
	public static String longToStr(long l) {
		String result = "";
		try {
			result = String.valueOf(l);
		} catch (Exception e) {
			logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @description: String到long的转换
	 * @param {type}
	 * @return:
	 */
	public static long strToLong(String str) {
		long result = 0;
		try {
			result = Long.parseLong(str);
		} catch (Exception e) {
			logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @description: String到int的转换
	 * @param {type}
	 * @return:
	 */
	public static int strToInt(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (Exception e) {
			logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @description: int到String的转换
	 * @param {type}
	 * @return:
	 */
	public static String intToStr(int n) {
		String result = null;
		try {
			result = String.valueOf(n);
		} catch (Exception e) {
			logError(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * @description: 当前登陆用户是否超级管理员 用户信息表里的superadmin字段值为1
	 * @param {type}
	 * @return:
	 */
	public static boolean isSuperAdmin(TtMap minfo) {// 内置超级管理员
		return minfo.get("superadmin").equals("1");
	}

	/**
	 * @description: 当前登陆用户是否公司内部人员 用户信息表里的superadmin字段值为2
	 * @param {type}
	 * @return:
	 */
	public static boolean isCcAdmin(TtMap minfo) { // 内部员工
		return minfo.get("superadmin").equals("2");
	}

	/**
	 * @description: urlEnCode的简化版，支持编码
	 * @param charSet 为UTF-8或者GBK，或者其他编码
	 * @return:
	 */
	public static String urlEncode(String s, String charSet) {
		String result = "";
		try {
			result = URLEncoder.encode(s, charSet);
		} catch (UnsupportedEncodingException e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * @description: urlEnCode的简化版
	 * @param {type}
	 * @return:
	 */
	public static String urlEncode(String s) {
		return urlEncode(s, "UTF-8");
	}

	/**
	 * @description: urlDecode时处理%符号和+符号
	 * @param {type}
	 */
	public static String replacer(String data) {
		try {
			data = data.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
			data = data.replaceAll("\\+", "%2B");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * @description: urlDeCode的简化版
	 * @param {type}
	 * @return:
	 */
	public static String urlDeCode(String s) {
		String result = "";
		try {
			result = URLDecoder.decode(replacer(s), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logError(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 转GBK，UTF8->GBK
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static String toGbk(String s) {
		try {
			s = URLEncoder.encode(s, "GBK");
			return s;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	// 首字母大写
	public static String captureName(String name) {
		name = name.substring(0, 1).toUpperCase() + name.substring(1).replace("_", "");
		return name;
	}

	/**
	 * 取字符串的右n位字符
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static String getRighStr(String s, int n) {
		return s.substring(s.length() - n);
	}

	/**
	 * 取字符串的左边n位字符
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static String getLeftStr(String s, int n) {
		return s.substring(0, n);
	}

	/**
	 * 删除字符串的右边n位字符
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static String trimRight(String s, int n) {
		return s.substring(0, s.length() - n);
	}

	/**
	 * 删除字符串的左边n个字符
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public static String trimLeft(String s, int n) {
		return s.substring(n);
	}

	/* 输出日期选择框的html */
	public static String htmlDate(String fieldName, String dataFormat, String defValue, String width) {
		if (myIsNull(dataFormat)) {
			dataFormat = "yyyy-mm-dd";
		}
		if (Tools.myIsNull(width)) {
			width = "4";
		}
		String result = "<div style=\"padding-left:15px;\" class=\"input-group date form_datetime col-md-" + width
				+ "\" data-date=\"\" data-date-format=\"" + dataFormat + "\" data-link-field=\"" + fieldName
				+ "\"><input class=\"form-control\" size=\"16\" type=\"text\" value=\"" + defValue
				+ "\" readonly><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-remove\"></span></span><span class=\"input-group-addon\"><span class=\"glyphicon glyphicon-th\"></span></span></div><input type=\"hidden\" id=\""
				+ fieldName + "\" name=\"" + fieldName + "\" value=\"\" /><br/>";
		return result;
	}

	public static String urlImg(String imgPath, int smallWidth, int smallHeight) {
		return "/ttAjax?do=show_img&img=" + imgPath + "&w=" + smallWidth + "&h=" + smallHeight;
	}

	public static String urlImgStatic(String imgPath, int w, int h) {
		return urlImgStatic(imgPath, w, h, "images/none.png", false);
	}

	public static String urlImgStatic(String imgPath, int w, int h, String noneImg, boolean noIfEx) {
		String srcFile = imgPath;
		String srcFileFullPath = null;
		srcFileFullPath = Config.FILEUP_SAVEPATH + srcFile;
		srcFileFullPath = srcFileFullPath.replaceFirst(Config.FILEUP_URLPRE, "/");
		srcFileFullPath = Tools.formatFilePath(srcFileFullPath);
		w = w == 0 ? h : w;
		h = h == 0 ? w : h;

		String smallImgPath = noneImg;
		if (!myIsNull(imgPath)) {
			if (w == 0 && h == 0) { // 直接显示原图
				if (noIfEx || Tools.fileExists(srcFileFullPath)) {
					smallImgPath = imgPath;
				}
			} else {
				if (noIfEx || Tools.fileExists(srcFileFullPath)) {
					smallImgPath = ImgTools.small(srcFileFullPath, w, h, srcFile, "");
				}
			}
		}
		return smallImgPath;
	}

	/**
	 * /* 获取精确到秒的时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecondTimestampTwo(Date date) {
		if (null == date) {
			return 0;
		}
		String timestamp = String.valueOf(date.getTime() / 1000);
		return Integer.valueOf(timestamp);
	}

	/**
	 * 金额转换
	 * 
	 * @return
	 */
	public static BigDecimal getprice(int price, int num) {
		BigDecimal bigDecimal1 = new BigDecimal(price);
		BigDecimal bigDecimal2 = new BigDecimal(num);
		BigDecimal bigDecimalDivide = bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_UP);
		return bigDecimalDivide;
	}

	public static String getnow() {
		LocalDateTime now = LocalDateTime.now(); // 获取当前系统时间
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");// 定义时间格式
		return now.format(dateTimeFormatter);
	}

	// 根据id递归获取下级公司
	public static String getfsids(int fsid) {
		TtMap ttMap = Tools.recinfo("select * from(\n" + "select t1.id,t1.up_id,\n"
				+ "              if(find_in_set(up_id, @pids) > 0, @pids := concat(@pids, ',', id), 0) as ischild\n"
				+ "              from (\n"
				+ "                   select id,up_id from assess_fs fs where fs.fs_type=2 and fs.deltag=0 order by fs.up_id,fs.id\n"
				+ "                  ) t1,\n" + "              (select @pids :=" + fsid + " ) t2\n"
				+ ") t3 where t3.ischild!=0 order by t3.ischild DESC LIMIT 1");
		mylog("ischild:" + ttMap.get("ischild"));
		return ttMap.get("ischild");
	}

	public static String getfsidsbyminfo(TtMap minfo) {
		String fsids = "";
		TtList fslist = new TtList();
		if (myIsNull(minfo.get("superadmin")) == false) {
			switch (minfo.get("superadmin")) {
			case "0":
				fslist = Tools
						.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id="
								+ minfo.get("icbc_erp_fsid"));
				break;
			case "1":
				fslist = Tools.reclist("select id from assess_fs where deltag=0 and showtag=1 and name!=''");
				break;
			case "2":
				fslist = Tools
						.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id="
								+ minfo.get("icbc_erp_fsid") + " or up_id=" + minfo.get("icbc_erp_fsid") + ")");
				break;
			case "3":
				fslist = Tools
						.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id in ("
								+ Tools.getfsids(Integer.parseInt(minfo.get("icbc_erp_fsid"))) + ")");
				break;
			default:

				break;
			}
			if (fslist.size() > 0) {
				for (int l = 0; l < fslist.size(); l++) {
					TtMap fs = fslist.get(l);
					if (l == fslist.size() - 1) {
						fsids = fsids + fs.get("id");
					} else {
						fsids = fsids + fs.get("id") + ",";
					}
				}
			}
		}
		mylog("fsids:" + fsids);
		return fsids;
	}
}
