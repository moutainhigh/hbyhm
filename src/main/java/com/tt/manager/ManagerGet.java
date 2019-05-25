/*
 * @Description: Manager的get处理
 * @Author: tt
 * @Date: 2019-01-25 16:53:56
 * @LastEditTime: 2019-03-09 09:46:40
 * @LastEditors: tt
 */
package com.tt.manager;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import com.tt.tool.UrlTools;
import com.tt.visual.Visual;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @param {type}
 * @description: 处理后台所有Get请求的统一入口
 * @return:
 */
public class ManagerGet {
    public String doGet(String cn, String type, String sdo, String id, HttpServletRequest request,
                        HttpServletResponse resp) {
        if (Tools.myIsNull(cn) && Tools.myIsNull(type) && Tools.myIsNull(sdo)) {
            String newUrl = ManagerTools.getHomeUrl();// 获取默认的首页地址并隐式转发
            UrlTools.goForward(newUrl);
            return "";
        }
        String realCn = ManagerTools.getRealCn(cn);
        if (!ManagerTools.checkSdo(sdo) || realCn == null) {// 过滤cn和sdo，realCn为null时表示此cn不合法。
            return "jsp/manager/404";
        }
        TtMap minfo = Tools.minfo();
        request.setAttribute("minfo", minfo);
        Modal modalMenu = new Modal();
        request.setAttribute("menus", modalMenu.getMenus()); // 后台左侧菜单,sidebar.jsp里面用到的菜单列表
        request.setAttribute("cssName", Config.MANAGER_SKINCSS);// 默认皮肤配色模板
        if (realCn == "") { // 为空时表示此cn不需要使用数据库，直接返回
            ManagerTools.doFetchDefault(request, cn, sdo);
            Visual.management(request);
            return "jsp/manager/index_b";
        }
        TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
        DbCtrl dbCtrl = null;
        try {
            switch (sdo) {
                case "":
                case "form": /* 下面的如果功能复杂，建议每一个table新建一个专属的class来处理，保持代码清洁生成相关的参数 */
                case "float":
                    switch (cn) {
                        default:
                            Class<?> b = ManagerTools.doGetClass(realCn);// 根据cn自动映射实例化类
                            if (null != b) {
                                dbCtrl = (DbCtrl) b.newInstance();
                            }
                            break;
                    }
                    if (dbCtrl == null) {// 使用dbCtrl默认的配置输出数据
                        dbCtrl = new DbCtrl(realCn.toLowerCase());
                    }
                    dbCtrl.doGetForm(request, post);
                    break;

                // ===============无敌分割线，分割sdo=form和list=================/
                case "list": /* 下面的如果功能复杂，建议每一个table新建一个专属的class来处理，保持代码清洁 */
                    String whereString = "true";// 过滤 如 t.mid=222 ，只显示mid为222的列表
                    String fieldsString = ""; // 显示字段列表如t.id,t.name,t.dt_edit
                    String orderString = ""; // 排序
                    String lsitTitleString = ""; // list的jsp页面左上角的标题
                    String kw = "";
                    int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
                    int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
                    boolean candel = false;
                    boolean canAdd = true;
                    switch (cn) {// list生成前处理
                        case "sys_modal_hbyh":
                            lsitTitleString = "模块管理";
                            orderString = "ORDER BY level,id_uplevel,sort";
                            candel = false;
                            kw = post.get("kw");
                            String id_uplevel = post.get("id_uplevel");
                            if (Tools.myIsNull(kw) == false) {
                                whereString += " AND showmmenuname like '%" + kw + "%'";
                            }
                            if (Tools.myIsNull(id_uplevel) == false && !id_uplevel.equals("0")) {
                                whereString += " AND level=" + id_uplevel;
                            }
                            break;
                        case "sys_error": // 演示单独的类来处理数据
                            lsitTitleString = "错误日志管理";
                            orderString = "ORDER BY dt_add DESC";
                            break;
                        default:
                            lsitTitleString = "相关管理";
                            orderString = "ORDER BY id";
                            Class<?> b = ManagerTools.doGetClass(realCn);
                            if (null != b) {
                                dbCtrl = (DbCtrl) b.newInstance();
                            }
                            break;
                    }
                    if (dbCtrl == null) {// 如果没有处理，使用默认的
                        dbCtrl = new DbCtrl(realCn.toLowerCase());
                        dbCtrl.showall = true;
                        dbCtrl.orders = orderString;
                        dbCtrl.p = pageInt;
                        dbCtrl.limit = limtInt;
                        TtList list = null;
                        list = dbCtrl.lists(whereString, fieldsString);
                        request.setAttribute("list", list);
                        request.setAttribute("recs", dbCtrl.recs); // 总记录数
                        String htmlpages = dbCtrl.getPage("", 0, true); // 分页html代码
                        request.setAttribute("pages", dbCtrl.pages); // 总页数
                        request.setAttribute("p", pageInt); // 当前页码
                        request.setAttribute("l", limtInt); // limit量
                        request.setAttribute("lsitTitleString", lsitTitleString); // 标题
                        request.setAttribute("htmlpages", htmlpages); // 分页的html代码
                        request.setAttribute("canDel", candel); // 分页的html代码
                        request.setAttribute("canAdd", canAdd); // 分页的html代码
                    } else {
                        dbCtrl.doGetList(request, post);
                    }
                    break;
            }
        } catch (Exception e) {
            if (Config.DEBUGMODE) {
                e.printStackTrace();
            }
            Tools.logError(e.getMessage(), true, true);
        } finally {
            if (dbCtrl != null) {
                dbCtrl.closeConn();
                dbCtrl = null;
            }
            if (post != null) {
                post.clear();
                post = null;
            }
        }
        return "jsp/manager/index_b";
    }

}