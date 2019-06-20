/*
 * @Description: In User Settings Edit
 * Ajax的get和post处理参考，如微信小程序get,post处理参考
 * 通用ajax提交处理
 * 创建日期：2018-11-16
 * @Author: tt
 * @Date: 2018-12-18 16:01:52
 * @LastEditTime: 2019-06-19 16:49:01
 * @LastEditors: tt
 */
package com.tt.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.tt.data.TtList;
import com.tt.data.TtMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class Ajax {
	/**
	 * method为GET时处理代码演示 测试代码/ttAjax?cn=comm_citys&do=opt&state_id=23 cn为表名
	 * do为操作类型,目前是只有OPT，获取<option></option>
	 */
	@RequestMapping("/ttAjax")
	private String ShowAjax(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String result = "";
		TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
		String cn = post.get("cn");
		long id = 0L;
		try {
			id = Long.parseLong(post.get("id"));
		} catch (Exception E) {
			System.err.println(E.getMessage());
		}
		switch (post.get("do")) {
		case "list":
			switch (post.get("cn")) {
			case "assess_querythjl":
				String sql = "select aq.id,aq.c_name,aq.c_tel,aq.c_cardno,aq.dt_add,fs.name as fs_name from assess_querythjl aq"
						+ " LEFT JOIN assess_fs fs on fs.id=aq.gems_fs_id" + " where aq.query_type=0 and aq.bc_status=5";
				if (!Tools.myIsNull(post.get("c_name"))) {
					sql += " and aq.c_name='" + post.get("c_name") + "'";
				}
				if (!Tools.myIsNull(post.get("c_tel"))) {
					sql += " and aq.c_tel='" + post.get("c_tel") + "'";
				}
				if (!Tools.myIsNull(post.get("c_cardno"))) {
					sql += " and aq.c_cardno='" + post.get("c_cardno") + "'";
				}
				TtList ttList = Tools.reclist(sql);
				if (ttList.size() > 0) {
					result = Tools.jsonEncode(ttList);
				}
				break;
			default:

				break;
			}
			return result;
		case "count":
			String fsids = Tools.getfsidsbyminfo(Tools.minfo());
			String sql1 = "";
			// String sql2="";
			switch (cn) {
			case "hxyh_zxlr":
				sql1 = "select count(*) as num from kj_icbc where app=4 and bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_xxzl":
				sql1 = "select count(*) as num from hxyh_xxzl where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_yhht":
				sql1 = "select count(*) as num from hxyh_yhht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_gsht":
				sql1 = "select count(*) as num from hxyh_gsht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_gpsgd":
				sql1 = "select count(*) as num from hxyh_gpsgd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_dygd":
				sql1 = "select count(*) as num from hxyh_dygd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_yhclhs":
				sql1 = "select count(*) as num from hxyh_yhclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_gsclhs":
				sql1 = "select count(*) as num from hxyh_gsclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hxyh_dyclhs":
				sql1 = "select count(*) as num from hxyh_dyclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "zxlr":
				sql1 = "select count(*) as num from kj_icbc where app=2 and bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xxzl":
				sql1 = "select count(*) as num from hbyh_xxzl where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hbyh_yhht":
				sql1 = "select count(*) as num from hbyh_yhht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hbyh_gsht":
				sql1 = "select count(*) as num from hbyh_gsht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "gps":
				sql1 = "select count(*) as num from hbyh_gpsgd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "dy":
				sql1 = "select count(*) as num from hbyh_dygd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hbyh_yhclhs":
				sql1 = "select count(*) as num from hbyh_yhclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hbyh_gsclhs":
				sql1 = "select count(*) as num from hbyh_gsclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "hbyh_dyclhs":
				sql1 = "select count(*) as num from hbyh_dyclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_zxlr":
				sql1 = "select count(*) as num from kj_icbc where app=3 and bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_xxzl":
				sql1 = "select count(*) as num from xmgj_xxzl where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_yhht":
				sql1 = "select count(*) as num from xmgj_yhht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_gsht":
				sql1 = "select count(*) as num from xmgj_gsht where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_gpsgd":
				sql1 = "select count(*) as num from xmgj_gpsgd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_dygd":
				sql1 = "select count(*) as num from xmgj_dygd where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_yhclhs":
				sql1 = "select count(*) as num from xmgj_yhclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_gsclhs":
				sql1 = "select count(*) as num from xmgj_gsclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			case "xmgj_dyclhs":
				sql1 = "select count(*) as num from xmgj_dyclhs where bc_status=2 and gems_fs_id in (" + fsids + ")";
				// sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and
				// gems_fs_id in ("+fsids+")";
				break;
			default:
				break;
			}
			TtMap ttMap1 = Tools.recinfo(sql1);
			//System.out.println(sql1);
			// TtMap ttMap2=Tools.recinfo(sql2);
			int num1 = 0;//, num2 = 0;
			if (!ttMap1.isEmpty()) {
				num1 = Integer.parseInt(ttMap1.get("num"));
			}
			// if(!ttMap2.isEmpty()){
			// num2=Integer.parseInt(ttMap2.get("num"));
			// }
			TtMap counts = new TtMap();
			counts.put("num1", String.valueOf(num1));
			result = Tools.jsonEncode(counts);
			break;
		case "opt":
			/**
			 * opt为操作类型中的获取某表里面的id和name的所有记录，条件由url参数决定，比如&state_id=23代表某表里面的state_id=23的所有记录
			 * 可加参数&id=104&re=json，id代表默认的选择项目(option模式有效,re=json时，返回json格式
			 */
			if (Tools.myIsNull(cn) != true) {
				String re = post.get("re");
				try {
					post.remove("cn");
					post.remove("do");
					post.remove("id");
					post.remove("re");
				} catch (Exception E) {
					System.err.println(E.getMessage());
				}
				result = Tools.dicopt(cn, id, post, re);
			}
			return result;
		case "info":// 查询某表某条记录的值
			if (Tools.myIsNull(cn) != true) {
				String re = post.get("re");
				if (re.equals("json")) {
					TtMap ttMap = Tools.recinfo("select * from " + cn + " where id=" + id);
					result = Tools.jsonEncode(ttMap);
				} else {
					TtMap ttMap = Tools.recinfo("select * from " + cn + " where id=" + id);
					result = ttMap.toString();
				}
			}
			return result;
		case "wxMini_list":// 微信小程序获取列表
			System.out.println(cn);
			String wxopenid = post.get("wx_openid");// 发送过来的微信openid
			switch (cn) {
			case "car_stora":// 车辆入库列表
				if (Tools.myIsNull(wxopenid) == false) {
					TtMap mInfo = Tools.recinfo("select * from admin where wxopenid='" + wxopenid + "'");// 通过微信ID定位用户id
					if (mInfo.size() > 0) {// 此用户存在，获取该用户所有提交的入库列表。
						DbCtrl wxDb = new DbCtrl(cn);
						TtList list = wxDb.lists("mid_add=" + mInfo.get("id"), "");
						wxDb.closeConn();
						List<Map<String, Object>> lmso = Tools.lssTolso(list);
						lmso.get(0).put("list", list);
						result = JSON.toJSONString(lmso);
					}
				}
				break;
			}
			break;
		case "show_img":/* 显示图片，在这里可以加入各种验证，比如必须登陆会员才能查看图片 */
			int w = Tools.strToInt(post.get("w"));
			int h = Tools.strToInt(post.get("h"));
			String srcFile = post.get("img");
			String srcFileFullPath = null;
			srcFileFullPath = Config.FILEUP_SAVEPATH + srcFile;
			srcFileFullPath = srcFileFullPath.replaceFirst(Config.FILEUP_URLPRE, "/");
			srcFileFullPath = Tools.formatFilePath(srcFileFullPath);
			w = w == 0 ? h : w;
			h = h == 0 ? w : h;

			String smallImgPath = ""; //
			boolean bDefImg = true;
			if (Tools.fileExists(srcFileFullPath)) {
				bDefImg = false;
				if (w == 0 && h == 0) { // 直接显示原图
					smallImgPath = srcFileFullPath;
				} else {
					smallImgPath = ImgTools.small(srcFileFullPath, w, h, srcFile, "");
					if (!Tools.myIsNull(smallImgPath)) {
						smallImgPath = Config.FILEUP_SAVEPATH + smallImgPath;
						smallImgPath = smallImgPath.replaceFirst(Config.FILEUP_URLPRE, "/");
						smallImgPath = Tools.formatFilePath(smallImgPath);
					}
				}
			}
			try {
				InputStream fis = null;
				if (bDefImg || !Tools.fileExists(smallImgPath)) {
					ServletContext context = request.getSession().getServletContext();
					String defImgPath = "/WEB-INF/jsp/manager/none.png";
					fis = context.getResourceAsStream(defImgPath);
				} else {
					File file = new File(smallImgPath);
					fis = new BufferedInputStream(new FileInputStream(file));
				}
				int fSize = fis.available();
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				response.reset();
				// 设置response的Header
				response.addHeader("Content-Length", "" + fSize);
				fis.close();
				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("image/jpeg");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			break;
		}
		return result;
	}

	/**
	 * POST ,带object的字段
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/ttAjaxPost2", method = RequestMethod.POST)
	private Object showAjaxPost2(HttpServletRequest request) throws ServletException, IOException {
		Map<String, Object> result = new HashMap<>();
		formatResultobj(result, false, 999, "服务器异常");
		DbCtrl db = new DbCtrl("admin");
		TtList ls = db.lists();
		db.closeConn();
		;
		formatResultobj(result, true, 0, "");
		result.put("info", ls);// info里面为List
		return result;
	}

	/**
	 * POST
	 *
	 * @param request
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/ttAjaxPost", method = RequestMethod.POST)
	private String showAjaxPost(HttpServletRequest request) throws ServletException, IOException {
		TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
		System.out.println(Tools.jsonEncode(post));
		String cn = post.get("cn") == null ? "" : post.get("cn");
		TtMap result2 = new TtMap();
		formatResult(result2, false, 999, "接口异常，请重试！");// 初始化返回
		String wxOpenid = "";
		switch (post.get("do")) {
		case "fileup":/** 文件上传处理 */
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("upload_immm");// 固定为upload_immm的name
			if (fileList == null || fileList.size() == 0) {
				result2.put("errormsg", "文件为空");
				return Tools.jsonEncode(result2);
			}
			for (MultipartFile file : fileList) {// 支持多文件上传
				String filename = Tools.getTimeMd5FileName() + "." + Tools.getFileExt(file.getOriginalFilename());// file.getOriginalFilename();
				System.out.println(filename);
				try {
					String smallWidth = post.get("smallwidth");// 缩略图高
					String smallHeight = post.get("smallheight");//// 缩略图宽
					String shuiText = post.get("shuitext");//// 缩略图宽
					int nSmallWidth = 0;
					int nSmallHeight = 0;
					if (Tools.myIsNull(smallWidth) == false) {
						nSmallWidth = Integer.parseInt(smallWidth);
					}
					if (Tools.myIsNull(smallHeight) == false) {
						nSmallHeight = Integer.parseInt(smallHeight);
						if (nSmallHeight == 0 && nSmallWidth != 0) {
							nSmallHeight = nSmallWidth;
						}
					}
					FileUp fu = new FileUp();
					/* 下面一行是设置文件保持路径，如不设置，就使用默认的在FileUp里面配置。 */
					// fu.savePath =
					// "/work/sd128/work/source/JAVA/springboot1/src/main/webapp/upload/";
					/* 开始上传文件 */
					result2 = fu.upFile(file, Tools.dirDate()/* 这个函数是格式话2018/11/23这种格式的路径 */ + filename, nSmallWidth,
							nSmallHeight, shuiText);
				} catch (Exception E) {
					System.err.println(E.getMessage());
				}
			}
			break;
		case "wxMini_Car_Store":/** 微信小程序车辆入库 */
			wxOpenid = post.get("wx_openid");
			System.out.println("wxopenid:" + wxOpenid);
			System.out.println("cn:" + cn);
			if (cn.equals("car_stora") && Tools.myIsNull(wxOpenid) == false) { //
				System.out.println(wxOpenid);
				String adminid = Tools.recinfo("select id from admin where wxopenid='" + wxOpenid + "'").get("id");
				long nmid = 0;
				if (Tools.myIsNull(adminid)) { // 演示：提交的微信openid不是会员,自动添加成会员，或者直接跳出绑定会员的窗口让微信小程序端绑定账号
					DbCtrl tbMem = new DbCtrl("admin");
					TtMap mpMem = new TtMap();
					mpMem.put("wxopenid", wxOpenid);
					mpMem.put("nickname", post.get("nickname"));
					mpMem.put("cp", "3");
					mpMem.put("showtag", "1");
					mpMem.put("deltag", "0");
					mpMem.put("isadmin", "0");
					nmid = tbMem.add(mpMem);
					tbMem.closeConn();
				} else {
					nmid = Long.parseLong(adminid);
				}
				System.out.println("nmid:" + nmid);
				post.put("mid_add", Long.toString(nmid));
				post.put("mid_edit", Long.toString(nmid));
				DbCtrl testDb = new DbCtrl(cn);
				long id = testDb.add(post);
				if (id > 0) {// 添加成功
					formatResult(result2, true, 0, "");
					result2.put("id", Long.toString(id));
				}
				testDb.closeConn();
			}
			break;

		case "qcds":
			String sql = "DELETE FROM tlzf_dk_details WHERE icbc_id=" + post.get("icbc_id");
			boolean recexec = new DbTools().recexec(sql);
			// new DbTools().recexec("DELETE FROM tlzf_dk_details_result WHERE qryid=");
			result2.put("结果", String.valueOf(recexec));
			break;
		case "xgdz":
			Long icbc_id = Tools.strToLong(post.get("icbc_id"));
			if (icbc_id > 0) {
				TtMap newpost = Tools
						.recinfo("select c_name,fk_status,mid_add,mid_edit,bc_status from kj_icbc where id=" + icbc_id);
				TtMap map = new TtMap();
				map.put("fk_status", post.get("fk_status"));
				int i = Tools.recEdit(map, "kj_icbc", icbc_id);
				String res = "";
				if (i > 0) {
					res = "success";
				} else {
					res = "error";
				}
				if (StringUtils.isNotEmpty(newpost.get("mid_add")) && newpost.get("mid_add").equals(newpost.get("mid_edit"))) {
					Addadmin_msg.addmsg_fkstatus(newpost.get("mid_edit"), post.get("fk_status"), "放款状态发生变化!",
							newpost.get("c_name"), "放款状态", "", newpost.get("mid_add"));
				} else {
					Addadmin_msg.addmsg_fkstatus(newpost.get("mid_add"), post.get("fk_status"), "放款状态发生变化!",
							newpost.get("c_name"), "放款状态", "", newpost.get("mid_add"));
					Addadmin_msg.addmsg_fkstatus(newpost.get("mid_edit"), post.get("fk_status"), "放款状态发生变化!",
							newpost.get("c_name"), "放款状态", "", newpost.get("mid_add"));
				}
				result2.put("结果", res);
			}
			break;

		}
		return Tools.jsonEncode(result2);
	}

	private void formatResult(TtMap result, boolean success, int code, String msg) {
		result.put("success", success ? "true" : "false");
		result.put("errorcode", success ? "0" : String.valueOf(code));
		result.put("errormsg", msg);
	}

	private void formatResultobj(Map<String, Object> result, boolean success, int code, String msg) {
		result.put("success", success ? true : false);
		result.put("errorcode", success ? 0 : code);
		result.put("errormsg", msg);
	}
}
