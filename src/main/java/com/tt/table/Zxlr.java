/*
 * @说明: 这里写说明哈
 * @Description: file content
 * @Author: tt
 * @Date: 2019-06-19 13:11:58
 * @LastEditTime: 2019-06-22 11:02:16
 * @LastEditors: tt
 */
package com.tt.table;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Addadmin_msg;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import com.tt.tool.Zip;

import org.apache.commons.lang.StringUtils;

public class Zxlr extends DbCtrl {
	private final String title = "征信录入";
	private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
	private boolean canDel = false;
	private boolean canAdd = false;
	private final String classAgpId = "28"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
	public boolean agpOK = false;// 默认无权限

	public Zxlr() {
		super("kj_icbc");
		AdminAgp adminAgp = new AdminAgp();
		try {
			if (adminAgp.checkAgp(classAgpId)) { // 如果有权限
				Tools.mylog("权限检查成功！");
				agpOK = true;
			} else {
				errorCode = 444;
				errorMsg = "您好，您暂无权限！";
			}
		} catch (Exception e) {
			Tools.logError(e.getMessage(), true, false);
		} finally {
			adminAgp.closeConn();
		}
	}

	@Override
	public void doGetList(HttpServletRequest request, TtMap post) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			request.setAttribute("errorMsg", errorMsg);
			return;
		}
		TtMap minfo = Tools.minfo();// 获取当前登录用户信息
		String kw = ""; // 搜索关键字
		String dtbe = ""; // 搜索日期选择
		int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
		int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量

		int appid = 2;
		if (post.get("appid") != null && !post.get("appid").isEmpty()) {
			appid = Integer.parseInt(post.get("appid"));
		}
		String whereString = "t.app=" + appid;
		String tmpWhere = "";
		String fieldsString = "t.*" + ",f.name as fsname" + ",aa.name as aa_name" + ",a.name as adminname"
				+ ",dy.id as dy_id" + ",f.id as fsid" + ",cs.name as state_name" + ",cc.name as city_name"
				+ ",dy.bc_status as dy_bc_status" + ",qy.bc_status as qy_bc_status" + ",qy.qy_status as qy_qy_status";
		// 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
		TtList list = null;
		// 根据权限获取公司id
		String fsids = "";
		TtList fslist = new TtList();
		switch (minfo.get("superadmin")) {
		case "0":
			fslist = Tools.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id="
					+ minfo.get("icbc_erp_fsid"));
			break;
		case "1":
			fslist = Tools.reclist("select id from assess_fs where deltag=0 and showtag=1 and name!=''");
			break;
		case "2":
			fslist = Tools.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id="
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
		whereString += " AND t.gems_fs_id in (" + fsids + ")";
		/* 开始处理搜索过来的字段 */
		kw = post.get("kw");
		int search_status = Tools.strToInt(post.get("search_status"));
		dtbe = post.get("dtbe");
		if (Tools.myIsNull(kw) == false) {
			/* 模糊搜索开始 */
			String [] ssKewords =new String []{"t.c_name","t.c_tel","t.c_cardno","t.c_name_mt","t.c_tel_mt","t.c_cardno_mt",};
			whereString += " AND ("+ssKewords[0]+" like '%" + kw + "%'";
			for (String tmpstr :ssKewords){
				whereString +=" || "+tmpstr+" like '%" + kw + "%'";
			}
			whereString +=")";
		}
		if (search_status>0){
			whereString += " AND t.bc_status="+search_status;
		}
		if (Tools.myIsNull(dtbe) == false) {
			dtbe = dtbe.replace("%2f", "-").replace("+", "");
			//System.out.println("dtbe:"+dtbe);
			String[] dtArr = dtbe.split(" - ");
			//dtArr[0] = dtArr[0].trim();
			//dtArr[1] = dtArr[1].trim();
			System.out.println("DTBE开始日期:" + dtArr[0] + "结束日期:" + dtArr[1]);
			// todo处理选择时间段
			whereString += " AND t.dt_add >='" + dtArr[0] +"' and t.dt_add <='"+dtArr[1]+"'";
		}
		if (!Tools.myIsNull(post.get("stateid"))) {
			whereString += " AND admin.stateid=" + post.get("stateid");
		}
		if (!Tools.myIsNull(post.get("cityid"))) {
			whereString += " AND admin.cityid=" + post.get("cityid");
		}
		if (!Tools.myIsNull(post.get("fsid"))) {
			whereString += " AND f.id=" + post.get("fsid");
		}
		if (!Tools.myIsNull(post.get("search_cityid"))) {
			whereString += " AND admin.cityid=" + post.get("search_cityid");
		}
		if (!Tools.myIsNull(post.get("saerch_fsid"))) {
			whereString += " AND f.id=" + post.get("saerch_fsid");
		}
		/* 搜索过来的字段处理完成 */
		whereString += tmpWhere; // 过滤
		orders = orderString;// 排序
		p = pageInt; // 显示页
		limit = limtInt; // 每页显示记录数
		showall = true; // 忽略deltag和showtag
		leftsql = " LEFT JOIN assess_fs f ON f.id=t.gems_fs_id " + " LEFT JOIN assess_gems a ON a.id=t.gems_id"
				+ " LEFT JOIN assess_admin aa ON aa.id=t.current_editor_id"
				+ " LEFT JOIN assess_admin admin ON admin.gemsid=a.id" + " LEFT JOIN comm_states cs ON cs.id=admin.stateid"
				+ " LEFT JOIN comm_citys cc ON cc.id=admin.cityid" + " LEFT JOIN hbyh_dygd dy ON dy.icbc_id=t.id"
				+ " LEFT JOIN tlzf_qy qy ON qy.icbc_id=t.id";
		list = lists(whereString, fieldsString);
		Tools.mylog("get list ,whereString:"+whereString);
		if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
			for (TtMap info : list) {
				info.put("c_name",
						info.get("c_name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
			}
		}
		request.setAttribute("list", list);// 列表list数据
		request.setAttribute("recs", recs); // 总记录数
		String htmlpages = getPage("", 0, false); // 分页html代码,
		request.setAttribute("pages", pages); // 总页数
		request.setAttribute("p", pageInt); // 当前页码
		request.setAttribute("l", limtInt); // limit量
		request.setAttribute("lsitTitleString", title); // 标题
		request.setAttribute("htmlpages", htmlpages); // 分页的html代码
		request.setAttribute("canDel", canDel); // 是否显示删除按钮
		request.setAttribute("canAdd", canAdd); // 是否显示新增按钮
		// request.setAttribute("showmsg", "测试弹出消息提示哈！"); //如果有showmsg字段，在载入列表前会提示

	}

	// 图片处理
	public TtMap tozip(String imgs, String imgsname) {
		TtMap imginfo = new TtMap();
		if (!Tools.myIsNull(imgs)) {
			String[] imgstep2_1ss = imgs.split("\u0005");
			for (int i = 0; i < imgstep2_1ss.length; i++) {
				if (!Tools.myIsNull(imgstep2_1ss[i])) {
					imginfo.put(imgsname + (i + 1), imgstep2_1ss[i]);
				}
			}
		}
		return imginfo;
	}

	@Override
	public void doGetForm(HttpServletRequest request, TtMap post) {
		String f = "t.*,a.name as admin_name,fs.name as fs_name";
		leftsql = " LEFT JOIN assess_gems a ON a.id=t.gems_id" + " LEFT JOIN assess_fs fs ON fs.id=t.gems_fs_id";
		TtMap minfo = Tools.minfo();
		System.out.println("pp:" + post);
		long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
		TtMap info = info(nid, f);
		String jsonInfo = Tools.jsonEncode(info);
		TtMap assess_admin = Tools.recinfo("select * from assess_admin where id =" + info.get("current_editor_id"));
		System.out.println("当前操作人信息：" + assess_admin);
		if (!Tools.myIsNull(post.get("toZip")) && post.get("toZip").equals("1")) {
			TtMap imginfo = new TtMap();
			// 征信录入资料
			TtMap imginfo1 = tozip(info.get("imgstep2_1ss"), "主贷人照片");
			TtMap imginfo2 = tozip(info.get("imgstep2_2ss"), "主贷人配偶照片");
			TtMap imginfo3 = tozip(info.get("imgstep2_3ss"), "共还人1照片");
			TtMap imginfo4 = tozip(info.get("imgstep2_4ss"), "共还人2照片");
			imginfo.putAll(imginfo1);
			imginfo.putAll(imginfo2);
			imginfo.putAll(imginfo3);
			imginfo.putAll(imginfo4);
			try {
				if (!Zip.imgsToZipDown(imginfo, info.get("c_name") + title + ".zip", null, "jpg")) {
					errorMsg = "导出ZIP失败!";
					request.setAttribute("errorMsg", errorMsg);
				}
			} catch (IOException e) {

				errorMsg = "导出ZIP失败:" + e.getMessage();
				request.setAttribute("errorMsg", errorMsg);
				if (Config.DEBUGMODE) {
					e.printStackTrace();
				}
			}
		} else {
			// 历史操作查询
			if (nid > 0) {
				TtList lslist = Tools.reclist("select * from kj_icbc_result where qryid=" + nid);
				request.setAttribute("lslist", lslist);//
			}

			TtMap map = new TtMap();
			map.put("current_editor_id", minfo.get("id"));
			Tools.recEdit(map, "kj_icbc", Long.parseLong(post.get("id")));
			request.setAttribute("info", jsonInfo);// info为json后的info
			request.setAttribute("infodb", info);// infodb为TtMap的info
			request.setAttribute("id", nid);
			request.setAttribute("assess_admin", assess_admin);
			// request.setAttribute("tl", post.get("tl"));
		}
	}

	/**
	 * post 其他操作 图片 字段替换 添加
	 */
	public void addicbc_erp_zx(TtMap post) {
		// 图片路径存放操作
		String imgstep1_1ss = "";
		String imgstep1_2ss = "";
		String imgstep1_3ss = "";
		String imgstep1_4ss = "";
		for (int i = 1; i <= 4; i++) {
			imgstep1_1ss = imgstep1_1ss + post.get("imgstep2_1ss" + i) + "\u0005";
			imgstep1_2ss = imgstep1_2ss + post.get("imgstep2_2ss" + i) + "\u0005";
			imgstep1_3ss = imgstep1_3ss + post.get("imgstep2_3ss" + i) + "\u0005";
			imgstep1_4ss = imgstep1_4ss + post.get("imgstep2_4ss" + i) + "\u0005";
			// imgstep1_5ss = imgstep1_5ss + post.get("imgstep1_5ss" + i) + ",";
		}
		post.put("imgstep2_1ss", imgstep1_1ss);
		post.put("imgstep2_2ss", imgstep1_2ss);
		post.put("imgstep2_3ss", imgstep1_3ss);
		post.put("imgstep2_4ss", imgstep1_4ss);
	}

	@Override
	public void doPost(TtMap post, long id, TtMap result2) {
		TtMap newpost = new TtMap();
		newpost.putAll(post);
		TtMap minfo = Tools.minfo();
		// addicbc_erp_zx(post);
		if (id > 0) { // id为0时，新增
			//首次审核人绑定
			TtMap check=Tools.recinfo("select checkname from kj_icbc where id="+id);
			if(post.get("bc_status").equals("3")&&Tools.myIsNull(check.get("checkname"))){
				post.put("checkname",minfo.get("name"));
			}
			edit(post, id);
		} else {
			post.put("app", "2");
			post.put("gems_id", minfo.get("id"));
			post.put("gems_fs_id", minfo.get("icbc_erp_fsid"));
			post.put("gems_code", "0");
			post.put("query_type", "0");
			add(post);
		}
		String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
		boolean bSuccess = errorCode == 0;
		Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑" + title + "成功！" : errorMsg,
				bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
	}

	@Override
	public int edit(TtMap ary, long id) {
		TtMap map = new TtMap();
		map.put("current_editor_id", "-1");
		int kj_icbc = Tools.recEdit(map, "kj_icbc", id);
		System.out.println("issucc::" + kj_icbc);
		return super.edit(ary, id);
	}

	@Override
	public void succ(TtMap array, long id, int sqltp) {
		if (array == null) {
			return;
		}
		TtMap ordermap = new TtMap();
		ordermap.put("gems_code", orderutil.getOrderId("HBYHKCD", 7, id));
		// 更新订单字段
		Tools.recEdit(ordermap, "kj_icbc", id);
		// 历史添加
		TtMap res = new TtMap();
		res.put("qryid", String.valueOf(id));
		res.put("status", array.get("bc_status"));
		res.put("remark", array.get("remark"));
		Tools.recAdd(res, "kj_icbc_result");
  	    // 推送
		String sql = "select c_name from kj_icbc where id=" + id;
		TtMap recinfo = Tools.recinfo(sql);
		Tools.mylog("征信编辑成功后处理,icbcinfo:"+recinfo.toString());
		Tools.mylog("征信编辑成功后处理,array:"+array.toString());
		if (StringUtils.isNotEmpty(array.get("mid_add")) && array.get("mid_add").equals(array.get("mid_edit"))) {
			System.out.println("添加人审核人相同");
			Addadmin_msg.addmsg(array.get("mid_edit"), array.get("bc_status"), array.get("remark"), recinfo.get("c_name"),
					"征信录入", "河北银行", array.get("mid_add"));

		} else {
			System.out.println("添加人审核人不同");
			Addadmin_msg.addmsg(array.get("mid_add"), array.get("bc_status"), array.get("remark"), recinfo.get("c_name"),
					"征信录入", "河北银行", array.get("mid_add"));
			Addadmin_msg.addmsg(array.get("mid_edit"), array.get("bc_status"), array.get("remark"), recinfo.get("c_name"),
					"征信录入", "河北银行", array.get("mid_add"));
		}
	}

	@Override
	public boolean chk(TtMap array, long id) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			return false;
		}
		if (!Tools.myIsNull(array.get("fromcommand"))) { // 从ManagerCmd来的。不用过滤参数

		} else {
			System.out.println("表单验证star");

			String myErroMsg = "";

			super.errorMsg = super.chkMsg = myErroMsg;
			if (!Tools.myIsNull(myErroMsg)) {
				super.errorCode = 888;
			}

			System.out.println("表单验证end");
		}
		return super.errorCode == 0;

	}
}
