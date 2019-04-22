/*
 * @Description:  * 数据库操作基类
 * @Author: tt
 * @Date: 2018-12-07 10:47:04
 * @LastEditTime: 2019-03-29 16:33:37
 * @LastEditors: tt
 */
package com.tt.tool;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.manager.ManagerPage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.utils.CloneUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DbCtrl {
	public DruidPooledConnection conn = null;
	public String title = "";
	public boolean canadd;
	public boolean thick = false;
	public boolean candel = true;
	public boolean realdel = true;
	public boolean canhide = false;
	public boolean cancheck = true;
	// static int now = 0, closenow = 0;
	/**
	 * 是否允許自動設定排序值,在獲取數據表結構的時候會自動設定
	 *
	 * @var boolean
	 */
	public boolean cansort = false;
	/**
	 * 操作的表名,必鬚
	 *
	 * @var string
	 */
	public String table = "";
	/* 多数据源时设置使用的数据源 */
	public String dsi = "1";
	/**
	 * 獲取結果時所使用的字段
	 *
	 * @var string
	 */
	public String fields = "t.*";
	/**
	 * 表結構
	 *
	 * @var array
	 */
	public List<String> fieldconf = null;
	/**
	 * 條件語句
	 *
	 * @var string
	 */
	public String wheres = "";
	/**
	 * group字段
	 *
	 * @var string
	 */
	public String groupby = "";
	/**
	 * 獲取第幾頁
	 *
	 * @var integer
	 */
	public int p = 1;
	/**
	 * 獲取列表時的開始行數
	 *
	 * @var integer
	 */
	public int start = 0;
	/**
	 * 獲取列表時的結束行數
	 *
	 * @var integer
	 */
	public int limit = 10;
	/**
	 * 獲取列表時不限制页數
	 *
	 * @var boolean
	 */
	public boolean nopage = false;
	/**
	 * 獲取資料時不限定showtag字段
	 *
	 * @var boolean
	 */
	public boolean showall = false;
	/**
	 * 獲取資料時不限定deltag字段
	 *
	 * @var boolean
	 */
	public boolean getall = false;
	/**
	 * order字段
	 *
	 * @var string
	 */
	public String orders = "";
	/**
	 * 用於inner join的設定 請使用setinner來進行設定
	 *
	 * @var array
	 * @see setinner();
	 */
	public TtMap innerary = new TtMap();
	/**
	 * inner join的語句
	 *
	 * @var string
	 */
	public String innersql = "";
	/**
	 * 用於left join的設定 請使用setleft來進行設定
	 *
	 * @var array
	 * @see setleft();
	 */
	public TtMap leftary = new TtMap();
	/**
	 * left join的語句
	 *
	 * @var string
	 */
	public String leftsql = "";
	/**
	 * 用於right join的設定 請使用setright來進行設定
	 *
	 * @var array
	 * @see setright();
	 */
	public TtMap rightary = new TtMap();
	/**
	 * right join的語句
	 *
	 * @var string
	 */
	public String rightsql = "";
	/**
	 * 主鍵名
	 *
	 * @var string
	 */
	public String key = "id";
	/**
	 * 是否自增長主鍵
	 *
	 * @var boolean
	 */
	public boolean autokey = true;
	/**
	 * 用於統計符合條件的記錄總數的sql
	 *
	 * @var string
	 */
	public String countSql = "";
	/**
	 * 獲取列表時的總記錄數,需執行getpage()
	 *
	 * @var integer
	 * @see getpage();
	 */
	public long recs = 0;
	/**
	 * 插入或修改時最終的插入數據
	 *
	 * @var array
	 */
	public TtMap recary = null;
	/**
	 * 修改或删除的时候,旧数据的记录
	 *
	 * @var array
	 */
	public TtMap oldrec = null;
	/**
	 * 獲取列表時的總分頁數,需執行getpage()
	 *
	 * @var integer
	 * @see getpage();
	 */
	public int pages = 0;
	/**
	 * 查詢SQL
	 *
	 * @var string
	 */
	public String sql = "";
	/**
	 * 新增SQL
	 *
	 * @var string
	 */
	public String sql_Add = "";
	/**
	 * 返回檢查信息
	 *
	 * @var string
	 */
	public String chkMsg = "";
	/**
	 * 權限檢查,暫時無用
	 *
	 * @var boolean
	 */
	public boolean permCheck = false;
	/**
	 * 上传的文件记录
	 */
	public long id = 0;
	/**
	 * todo 文件上传处理
	 */
	public TtMap uploads = null;
	/**
	 * 错误代码
	 */
	public int errorCode = 0;
	/**
	 * 错误信息
	 */
	public String errorMsg = "";
	/**
	 * 是否显示日志
	 */
	private boolean showlog = true;
	private DbTools myDbTools;

	/* 类初始化，带数据库链接，如果链接为空，使用默认的数据库账号密码 */
	public DbCtrl(DruidPooledConnection connection, String tbName) {
		table = tbName;
		conn = connection;
		if (conn == null) {
			GetdfConnection();
		} else {
		}
		initconn();
		canadd = true;
		candel = true;
	}

	/* 类初始化，带数据库链接，如果链接为空，使用默认的数据库账号密码 */
	public DbCtrl(String tbName) {
		table = tbName;
		GetdfConnection();
		initconn();
		canadd = true;
		candel = true;
	}

	/* 默认的数据库链接，目前直接写到类里 */
	private void GetdfConnection() {
		if (DbConfig.g_dsmap.size() <= 0) {
			mylog("开始初始化,使用默认数据库链接..");
			DbConfig.init(true);
		} else {
			mylog("已经初始化过的..");
		}
		try {
			conn = DbConfig.g_dsmap.get(dsi).getConnection();
			// now++;
			// mylog("累计创建getConnection连接数：" + now);
		} catch (SQLException e) {
			errorCode = 900;
			Tools.logError("DbCtrl:" + e.getMessage());
			mylog(e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
		if (conn == null) {
			errorCode = 999;
			Tools.logError("DbCtrl:connect is null!");
			mylog("connect is null!");
		} else {
		}
	}

	/* 多数据源情况下修改数据源 */
	public void setDsi(String adsi) {
		closeConn();// 先关闭连接
		dsi = adsi;
		GetdfConnection();
		initconn();
	}

	/* 多数据源情况下修改数据源后重新设置表名用，一般情况下不要使用哈，如果硬要使用，在setTable后，要setDsi一下，刷新数据源 */
	public void setTable(String aTable) {
		table = aTable;
	}

	/**
	 * 更改目标表
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public void changeTable(String aTable) {
		setTable(aTable);
		setDsi(dsi);
	}

	/* 初始数据连接 */
	private void initconn() {
		if (conn != null) {
			myDbTools = null;
			myDbTools = new DbTools(conn, showlog);
			getTableField(true);
		} else {
			errorCode = 996;
			Tools.logError("DbCtrl:initconn() error:");
		}
	}

	/**
	 * 實際删除數據 根據表結構是否有deltag字段判斷是真正的删除,還是僅更改deltag的狀態位
	 *
	 * @param unknown_type id 主鍵的值
	 */
	public boolean delete(long id, String deltag) {
		// deltag = "del";
		if (fieldconf == null) {
			getTableField(true);
		}
		if (Tools.inArrayList("deltag", fieldconf)) {
			int delval = (deltag == "del" ? 1 : (deltag == "realdel" ? -1 : 0));
			String setshow = "";
			if (Tools.inArrayList("showtag", fieldconf)) {
				setshow = ",showtag=0";
			}
			String setlast = "";
			if (Tools.inArrayList("dt_edit", fieldconf)) {
				setlast = ",dt_edit=NOW()";
			}
			return myDbTools.recexec("update " + table + " set deltag=" + delval + " " + setshow + " " + setlast + " where "
					+ key + "='" + id + "'");
		} else {
			return myDbTools.recexec("delete from " + table + " where " + key + "='" + id + "'");
		}
	}

	/**
	 * 數據檢查
	 *
	 * @param $ary 待檢查的數據
	 * @param $id  如果id不爲null,則爲更新數據,否則爲新增數據
	 * @return 數據通過返回true, 失敗返回false
	 */
	public boolean chkdel(long id) {
		return chkdel(id, null);
	}

	/**
	 * 數據檢查
	 *
	 * @param $ary 待檢查的數據
	 * @param $id  如果id不爲null,則爲更新數據,否則爲新增數據
	 * @return 數據通過返回true, 失敗返回false
	 */
	public boolean chkdel(long id, TtMap ary) {
		return true;
	}

	/**
	 * 删除數據
	 *
	 * @param 需删除數據的主鍵的值
	 */
	public boolean del(long id) {
		return del(id, "");
	}

	/**
	 * 删除數據
	 *
	 * @param 需删除數據的主鍵的值
	 */
	public boolean del(long id, String deltag) {
		if (Tools.myIsNull(deltag)) {
			deltag = "del";
		}
		oldrec = info(id);
		if (chkdel(id)) {
			boolean re = delete(id, deltag);
			succ(null, id, -1);
			/*
			 * global tablog_tabs; //写删除日志todo if(in_array(table, tablog_tabs)){
			 * tablog(title,table,id,"del",array(),oldrec); }
			 */
			return re;
		} else {
			return false;
		}
	}

	/**
	 * 獲取表結構
	 *
	 * @param boolean nameonly 設置爲true,則只獲取字段名,目前只能允許true
	 */
	public List<String> getTableField(boolean nameonly) {
		// todo 可以加入cache功能。
		if (fieldconf == null || fieldconf.size() <= 0) {
			if (fieldconf == null) {
				fieldconf = new ArrayList<>();
			}
			mylog("加载字段列表。。。");
			TtList list = myDbTools.reclist("SHOW COLUMNS FROM " + table);
			for (int i = 0; i < list.size(); i++) {
				String tmpStr = list.get(i).get("Field");
				if (Tools.myIsNull(tmpStr)) {
					tmpStr = list.get(i).get("COLUMN_NAME");
				}
				if (Tools.myIsNull(tmpStr) == false) { // 如果不为空
					fieldconf.add(tmpStr);
				}
			}
		}
		// mylog(fieldconf == null ? "null" : "not null");
		// mylog(Integer.toString(fieldconf.size()));
		if (fieldconf != null && fieldconf.size() <= 0) {
			mylog("字段列表为空");
			try {
				throw new Exception("TT异常：获取数据表所有字段名失败！" + table);
			} catch (Exception e) {
				mylog(e.getMessage());
				Tools.logError("DbCtrl:" + e.getMessage());
				if (Config.DEBUGMODE) {
					e.printStackTrace();
				}
			}
			mylog("<----------------ERROR,获取字段出错---------------->");
			Tools.logError("DbCtrl:<----------------ERROR,获取字段出错---------------->");
		}
		if (fieldconf != null) {
			if (fieldconf.contains("sort")) {
				cansort = true;
			}
			if (fieldconf.contains("deltag")) {
				realdel = false;
			}
			if (fieldconf.contains("showtag")) {
				canhide = true;
			}
		}
		return fieldconf;
	}

	/**
	 * 實際更新數據
	 *
	 * @param array        array 更新的數據
	 * @param unknown_type id 主鍵的值
	 */
	private int editData(TtMap array, long id) {
		if (this.autokey) {
			array.remove(key);
		}
		int result = 0;
		String sql = getAESql(array, "update ");
		if (array.size() > 0) {
			sql = sql.substring(0, sql.length() - 1);
			sql = sql + " where " + key + "='" + id + "'";
			result = myDbTools.recupdate(sql);
		}
		return result;
	}

	private String getAESql(TtMap array, String s) {
		if (fieldconf == null) {
			getTableField(true);
		}
		/*
		 * todo if(_FILES){ array=files(array,id); }
		 */
		for (Iterator<Map.Entry<String, String>> it = array.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> item = it.next();
			if (!fieldconf.contains(item.getKey())) {
				it.remove();
			}
			// ... todo with item
		}

		/*
		 * todo foreach(array as key=>val){ if(is_array(val)){
		 * array[key]=self::arytostr(val); } }
		 */
		recary = array;
		String sql = s + table + " set";
		for (String key : array.keySet()) { // 过滤不存在的字段
			if (array.get(key) == "null") {
				sql = sql + " " + key + "=NULL,";
			} else {
				sql = sql + " `" + key + "`='" + array.get(key) + "',";
			}
		}
		return sql;
	}

	public boolean chk(TtMap array, long id) {
		return true;
	}

	public TtMap param(TtMap ary, long id) {
		// todo
		return ary;
	}

	/**
	 * 添加/编辑/删除成功一条记录会执行此方法，子类重载用。sqltp：0-添加，1：编辑，-1：删除
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public void succ(TtMap array, long id, int sqltp) {
		// todo

	}

	/**
	 * 更新數據
	 *
	 * @param array ary 待更新的數據
	 * @param id    需更新數據的主鍵的值
	 */
	public int edit(TtMap ary, long id) {
		TtMap tmpAry = (TtMap) ary.clone();
		// todo oldrec=info(id);
		if (chk(tmpAry, id)) {
			if (Tools.myIsNull(tmpAry.get("dt_edit"))) {
				tmpAry.put("dt_edit", Tools.dateToStrLong(null));
			}
			if (Tools.mid() != 0) {
				tmpAry.put("mid_edit", String.valueOf(Tools.mid()));
			}
			tmpAry = param(tmpAry, id);
			if (tmpAry == null) {
				return 0;
			} else {
				int re = editData(tmpAry, id);
				succ(tmpAry, id, 1);
				tmpAry.clear();
				tmpAry = null;
				/*
				 * todo global tablog_tabs; if(in_array(table, tablog_tabs)){
				 * tablog(title,table,id,'edit',tmpAry,oldrec); }
				 */
				return re;
			}
		} else {
			tmpAry.clear();
			tmpAry=null;
			return 0;
		}
	}

	/**
	 * 實際插入數據
	 *
	 * @param array 插入的數據
	 * @return 返回插入的ID
	 */
	public long addData(TtMap array) {
		if (this.autokey) {
			array.remove(key);
		}
		long result = 0;
		String sql = getAESql(array, "insert into ");
		if (array.size() > 0) {
			sql = sql.substring(0, sql.length() - 1);
			// sql = sql+" where "+key+"='"+id+"'";
			result = myDbTools.recinsert(sql);
		}
		return result;
	}

	/**
	 * 新增數據
	 *
	 * @param Map ary 待新增的數據
	 * @return 新增記錄的ID
	 */
	public long add(TtMap ary){
		if (ary == null || ary.size() == 0) {
			return 0;
		}
		TtMap tmpArray = null;
		try {
			tmpArray = (TtMap) CloneUtils.clone(ary);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		if (chk(tmpArray, 0)) {
			String dt = Tools.dateToStrLong(null);
			if (Tools.myIsNull(tmpArray.get("dt_edit"))) {
				tmpArray.put("dt_edit", dt);
			}
			if (Tools.myIsNull(tmpArray.get("dt_add"))) {
				tmpArray.put("dt_add", dt);
			}
			if (Tools.myIsNull(tmpArray.get("mid_add")) && Tools.mid() != 0) {
				tmpArray.put("mid_edit", String.valueOf(Tools.mid()));
				tmpArray.put("mid_add", String.valueOf(Tools.mid()));
			}
			if (Tools.myIsNull(tmpArray.get("sort"))) {
				tmpArray.put("sort", "100");
			}
			if (Tools.myIsNull(tmpArray.get("showtag"))) {
				tmpArray.put("showtag", "0");
			}
			tmpArray = param(tmpArray, 0);
			id = addData(tmpArray);
			succ(tmpArray, id, 0);
			/*
			 * todo 写数据库日志
			 */
			tmpArray.clear();
			tmpArray = null;
			return id;
		} else {
			tmpArray.clear();
			tmpArray = null;
			return 0;
		}
	}

	private String getListSqlStr(String sql) {
		boolean _in_admin = Tools.isAdmin();
		if (fieldconf == null) {
			getTableField(true);
		}
		if (_in_admin && Tools.inArrayList("showtag", fieldconf) && !showall) {
			sql = sql + (sql.equals("") ? " t.showtag=1 " : " and t.showtag=1 ");
		}
		if (_in_admin && Tools.inArrayList("deltag", fieldconf) && !getall) {
			sql = sql + (sql.equals("") ? " t.deltag=0 " : " and t.deltag=0 ");
		}
		return sql;
	}

	private String getListSqlStrlimit() {
		String r = "";
		if (!nopage) {
			if (p == 0)
				p = 1;
			if (start == 0)
				start = limit * (p - 1);
			if (start < 0)
				start = 0;
			r += " limit " + start + "," + limit;
		}
		return r;
	}

	/**
	 * 显示列表,wheres为条件，f为字段名格式t.username,t.password或者t.*,或者直接为""
	 */
	public TtList lists(String wheres, String f) {
		TtList result = null;// new ArrayTtList();
		String strWhere = getListSqlStr(wheres);
		/** todo 组合字符串，查询列表 */
		sql = "select SQL_CALC_FOUND_ROWS " + (Tools.myIsNull(f) == false ? f : fields) + " from " + table + " t " + leftsql
				+ " " + rightsql + " " + innersql + " "
				+ (Tools.myIsNull(wheres) == false ? " where " + strWhere
						: (Tools.myIsNull(strWhere) ? "" : " where " + strWhere))
				+ " " + orders + " " + groupby + " " + getListSqlStrlimit() + " ";
		result = myDbTools.reclist(sql);
		recs = Long.parseLong(myDbTools.recexec_getvalue("SELECT FOUND_ROWS() as rno;", "rno"));
		return result;
	}

	public TtList lists() {
		return lists("", "");
	}

	public TtMap info(long id) {
		return info(id, "");
	}

	/**
	 * 獲取單挑記錄
	 *
	 * @param id 主鍵的值
	 * @return 記錄數組
	 */
	public TtMap info(long id, String f) {
		// f = false;
		boolean _in_admin = Tools.isAdmin();
		String where = "";
		mylog("begin info...");
		if (fieldconf == null) {
			getTableField(true);
		}
		if (_in_admin && Tools.inArrayList("showtag", fieldconf) && !showall) {
			where = where + " and t.showtag=1 ";
		}
		;
		if (_in_admin && Tools.inArrayList("deltag", fieldconf) && !getall) {
			where += " and t.deltag=0 ";
		}
		/*
		 * NOTE:这个比较重要 todo ,left join，right join，inner join查询以及groupby查询功能的加入 if
		 * (sizeof(leftary)>0){ leftsql = ""; foreach (leftary as ltab=>ons){ leftsql+=
		 * " left join ".ltab." on ".implode(",", ons); } } if (sizeof(rightary)>0){
		 * rightsql = ""; foreach (rightary as ltab=>ons){ rightsql+=
		 * " right join ".ltab." on ".implode(",", ons); } } if (sizeof(innerary)>0){
		 * innersql = ""; foreach (innerary as itab=>ons){ innersql+=
		 * " inner join ".itab." on ".implode(",", ons); } } if (groupby != ""){ groupby
		 * = " group by "+groupby; }else if(leftsql || rightsql || innersql){ groupby =
		 * " group by t."+key; }
		 */
		sql = "select " + (Tools.myIsNull(f) == false ? f : fields) + " from " + table + " t " + leftsql + " " + rightsql
				+ " " + innersql + " where t." + key + "='" + id + "' " + where + " " + groupby + " ";
		TtMap rec = myDbTools.recinfo(sql, false, "ary", null);
		for (String key : rec.keySet()) {
			String vString = rec.get(key);
			if (Tools.myIsNull(vString) == false
					&& ((vString.length() >= 10 && vString.substring(0, 10).equals("0000-00-00")) || vString.equals("0000"))) {
				rec.put(key, "");
			}
		}
		return show(rec);
		/*
		 * todo 其他的处理，imags,mapx,mapy,hit等 foreach(rec as k=>v){ if(substr(v, 0,
		 * '10')=='0000-00-00' || v==='0000'){ rec[k]=''; } if(in_array(k,
		 * array('mapx','mapy'))){ rec[k]=rtrim(v,'0'); } rec[k]=strtoary(rec[k]); }
		 * if(f){ rec=show(rec); if(sizeof(rec)==1){ return @array_shift(rec); }else{
		 * return rec; } }else if(!rec){ return array(); }else{ if(isset(rec['imgs'])
		 * &&!rec['imgs']){ rec['imgs']=array(); }
		 *
		 * //添加点击数 if(rec && in_array('hit', fieldconf) && _in_admin){
		 * recupdate("update table set hit=".(rec['hit']+1)+" where id=".rec['id' ]); }
		 *
		 * return show(rec); }
		 */
	}

	private String strGetPage(int pages, int p, String style, int l, boolean auto_hide) {
		// todo
		String myString = ManagerPage.getPage(pages, p, l, auto_hide);// 返回分页的html代码
		return myString;
	}

	/**
	 * 獲取分頁代碼,指定style,一页多少，是否自动隐藏
	 */
	public String getPage(String style, int l, boolean auto_hide) {
		// recs=recinfo(countSql,'rno');
		if (recs > 0) {
			pages = (int) Math.ceil((double) recs / limit); // update by hong 原來統計出錯
		} else {
			recs = pages = 0;
		}
		return strGetPage(pages, p, style, limit, auto_hide);
	}

	/* 给子类重载的，做最后的过滤 */
	public TtMap show(TtMap rec) {
		return rec;
	}

	/**
	 * @说明: 给继承的子类重载用的
	 * @param {type} {type}
	 * @return: 返回
	 */
	public void doGetList(HttpServletRequest request, TtMap post) {
	}

	/**
	 * @说明: 给继承的子类重载用的
	 * @param {type} {type}
	 * @return: 返回
	 */
	public void doGetForm(HttpServletRequest request, TtMap post) {
		long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
		TtMap info = info(nid);
		String jsonInfo = Tools.jsonEncode(info);
		request.setAttribute("info", jsonInfo);// info为json后的info
		request.setAttribute("infodb", info);// infodb为TtMap的info
		request.setAttribute("id", nid);
	}

	/**
	 * @说明: 给子类重载用，处理post
	 * @param {type} {type}
	 * @return: 返回
	 */
	public void doPost(TtMap post, long id, TtMap result2) {
		if (id > 0) { // id为0时，新增
			edit(post, id);
		} else {
			add(post);
		}
		String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
		boolean bSuccess = errorCode == 0;
		Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg, bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
	}

	/**
	 * 输出日志
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	private void mylog(String mString) {
		if (Config.GLOBAL_SHOWLOG) {
			Log log = LogFactory.getLog(DbCtrl.class); //
			log.info(mString);
			if (!Config.TESTMODE) { // 不是测试模式，log4j也打印
				Tools.logInfo(mString, DbCtrl.class.toString());
			}
		}
	}

	/**
	 * 关闭连接，所有数据库操作完成后应该调用此方法关闭连接，释放资源。
	 */
	public void closeConn() {
		myDbTools.closeConn();// 释放由dbtools创建的connection,本类
		try {
			if (conn != null) { // 关闭本类创建的
				conn.close();
			}
		} catch (SQLException e) {
			errorCode = 898;
			mylog(e.getMessage());
			Tools.logError("DbCtrl:" + e.getMessage());
			if (Config.DEBUGMODE) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * tt的版本
	 * 
	 * @param {type} {type}
	 * @return: 返回
	 */
	public String ver() {
		return Config.TTVER;
	}

}
