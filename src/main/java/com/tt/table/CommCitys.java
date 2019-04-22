/*
 * @Description: 城市列表演示类模板，演示单独的class继承dbctrl来处理各种数据
 * @Author: tt
 * @Date: 2019-01-24 09:38:15
 * @LastEditTime: 2019-03-18 14:56:01
 * @LastEditors: tt
 */
package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Excel;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

public class CommCitys extends DbCtrl {
	private final String lsitTitleString = "城市列表管理";
	private String orderString = "ORDER BY state_id DESC"; // 默认排序
	private boolean canDel = false;
	private boolean canAdd = true;
	private final String classAgpId = "4"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
	public boolean agpOK = false;// 默认无权限

	public CommCitys() {
		super("comm_citys");
		boolean bGo = true;
		if (bGo) {
			AdminAgp adminAgp = new AdminAgp();
			try {
				if (adminAgp.checkAgp(classAgpId)) { // 如果有权限
					Tools.logInfo("权限检查成功！", getClass().getName());
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
	}

	@Override
	public int edit(TtMap ary, long id) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			return 0;
		}
		return super.edit(ary, id);
	}

	@Override
	public long add(TtMap ary) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			return 0;
		}
		return super.add(ary);
	}

	public void closeConn() {
		super.closeConn();
	}

	/**
	 * @description: 重载DbCtrl的param方法，在写入数据前会回调此方法
	 * @param {type}
	 * @return:
	 */
	@Override
	public TtMap param(TtMap ary, long id) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			return null;
		}
		return ary;
	}

	@Override
	public boolean chk(TtMap array, long id) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			return false;
		}
		if (Tools.myIsNull(array.get("name"))) {
			errorMsg = chkMsg = "请输入完整的城市名称";
			errorCode = 888;
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @description: 处理后台的get,演示独立类处理sdo=form的get
	 * @param {type}
	 * @return:
	 */
	@Override
	public void doGetForm(HttpServletRequest request, TtMap post) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			request.setAttribute("errorMsg", "权限访问错误！");
			return;
		}
		String id = post.get("id");
		long nid = 0;
		if (!Tools.myIsNull(id)) {
			nid = Long.valueOf(id);
		}
		showall = true;
		TtMap info = info(nid);
		System.out.println(info);
		String jsonInfo = Tools.jsonEncode(info);
		System.out.println(jsonInfo);
		request.setAttribute("info", jsonInfo);
		request.setAttribute("infodb", info);
		request.setAttribute("id", nid > 0 ? nid : 0);
	}

	/**
	 * @description: 处理后台的get,演示独立类处理sdo=list的get
	 * @param {type}
	 * @return:
	 */
	@Override
	public void doGetList(HttpServletRequest request, TtMap post) {
		if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
			request.setAttribute("errorMsg", errorMsg);
			return;
		}
		String kw = ""; // 搜索关键字
		String dtbe = ""; // 搜索日期选择
		int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
		int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
		String whereString = "true";
		String tmpWhere = "";
		String fieldsString = ""; // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
		TtList list = null;
		/* 开始处理搜索过来的字段 */
		kw = post.get("kw");
		String state_id = post.get("state_id");
		dtbe = post.get("dtbe");
		if (Tools.myIsNull(kw) == false) {
			whereString += " AND name like '%" + kw + "%'";
		}
		if (Tools.myIsNull(state_id) == false && !state_id.equals("0")) {
			whereString += " AND state_id=" + state_id;
		}
		if (Tools.myIsNull(dtbe) == false) {
			dtbe = dtbe.replace("%2f", "-").replace("+", "");
			String[] dtArr = dtbe.split("-");
			dtArr[0] = dtArr[0].trim();
			dtArr[1] = dtArr[1].trim();
			System.out.println("DTBE开始日期:" + dtArr[0] + "结束日期:" + dtArr[1]);
			// todo处理选择时间段
		}
		/* 搜索过来的字段处理完成 */

		// 导出到Excel处理
		boolean bToExcel = false;
		if (!Tools.myIsNull(post.get("toExcel")) && post.get("toExcel").equals("1")) {// 导出excel时设置不分页，导出所有
			nopage = true;
			bToExcel = true;
		}
		whereString += tmpWhere; // 过滤
		orders = orderString;// 排序
		p = pageInt; // 显示页
		limit = limtInt; // 每页显示记录数
		showall = true; // 忽略deltag和showtag
		list = lists(whereString, fieldsString);
		if (bToExcel) {
			String[] headers = new String[] { "城市名称", "城市ID", "所属省份ID" };
			String[] fields = new String[] { "name", "id", "state_id" };
			String toFile = Config.FILEUP_SAVEPATH + "excel/" + lsitTitleString + ".xlsx";
			closeConn();// 关闭数据库链接，要准备跳转了
			if (!Excel.doOut(list, headers, fields, toFile, "excel2007", true)) {
				errorMsg = "导出Excel失败";
				request.setAttribute("errorMsg", errorMsg);
			}
		} else {
			request.setAttribute("list", list);
			request.setAttribute("recs", recs); // 总记录数
			String htmlpages = getPage("", 0, false); // 分页html代码,
			request.setAttribute("pages", pages); // 总页数
			request.setAttribute("p", pageInt); // 当前页码
			request.setAttribute("l", limtInt); // limit量
			request.setAttribute("lsitTitleString", lsitTitleString); // 标题
			request.setAttribute("htmlpages", htmlpages); // 分页的html代码
			request.setAttribute("canDel", canDel); // 是否显示删除按钮
			request.setAttribute("canAdd", canAdd); // 是否显示删除按钮
		}
		// request.setAttribute("showmsg", "测试弹出消息提示哈！"); //如果有showmsg字段，在载入列表前会提示
	}
}