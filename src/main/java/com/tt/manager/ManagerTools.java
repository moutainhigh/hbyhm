/*
 * @Description: Manager需要用到的一些方法
 * @Author: tt
 * @Date: 2019-01-25 16:44:40
 * @LastEditTime: 2019-03-25 09:38:52
 * @LastEditors: tt
 */
package com.tt.manager;

import com.tt.tool.Config;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

/**
 * @param {type}
 * @description: Manager需要用到的一些方法
 * @return:
 */
public class ManagerTools {

    /**
     * @param {type}
     * @description: url传递过来的sdo参数值合法性检查，列表中的才处理
     * @return:
     */
    public static boolean checkSdo(String sdo) {
        if (Tools.myIsNull(sdo)) {
            return false;
        }
        String[] allowDoList = {"form", "list", "login", "logout", "edit", "float", "sysconfig"}; // 允许的sdo，过滤不安全的url和参数
        return Tools.arrayIndexOf(allowDoList, sdo);
    }

    /**
     * @param {type}
     * @description: 后台是否已经登陆检查，如果未登录，返回登陆页的链接
     * @return:
     */
    public static String checkLogin() {
        String result = null;
        if (Tools.mid() == 0) {
            Tools.logInfo("没有登陆哈..", "ManagerTools");
            result = "redirect:/manager/login";
        } else {
        }
        return result;
    }

    /**
     * @param {type}
     * @return 返回对应真实的使用数据库表的cn
     * @description: 过滤cn并返回真实cn，为null时表示此cn不合法，为""时表示此cn不需要链接数据库,否则表示需要链接数据库
     */
    public static String getRealCn(String cn) {
        switch (cn) {
            // 需要用到数据库的CN
            case "yhqy"://银行签约
                return "yhqy";
            case "dsgl"://代收管理
                return "dsgl";
            case "gsgl"://公司管理
                return "assess_fs";
            case "ddjd"://订单进度
                return "ddjd";
            case "hbyh_dyclhs":
                return "dyclhs";
            case "hbyh_yhclhs":
                return "yhclhs";
            case "hbyh_gsclhs":
                return "gsclhs";
            case "dy":
                return "dygd";
            case "bx":
                return "Bx";
            case "gps":
                return "Gps";
            case "spmq":
                return "Spmq";
            case "hbyh_gsht":
                return "Gsht";
            case "hbyh_yhht":
                return "Yhht";
            case "zmcl":
                return "Zmcl";
            case "jfcl":
                return "Jfcl";
            case "qccl":
                return "Qccl";
            case "xxzl":
                return "Xxzl";
            case "zxlr":
                return "Zxlr";
            case "fs_agp":
                return "FsModal";
            case "yhgl":
                return "Admin";
            case "comm_citys":
                return "CommCitys";
            case "admin_agp":
                return "AdminAgp";
            case "sys_modal_hbyh":
                return "sys_modal_hbyh";
            case "sys_error":
                return "sys_error";
            case "hxyh_zxlr":
                return "hxyh_zxlr";
            case "hxyh_xxzl":
                return "hxyh_xxzl";
            case "hxyh_yhht":
                return "hxyh_yhht";
            case "hxyh_gsht":
                return "hxyh_gsht";
            case "hxyh_gps":
                return "hxyh_gps";
            case "hxyh_dy":
                return "hxyh_dy";
            case "hxyh_yhclhs":
                return "hxyh_yhclhs";
            case "hxyh_gsclhs":
                return "hxyh_gsclhs";
            case "hxyh_dyclhs":
                return "hxyh_dyclhs";
            case "xmgj_zxlr":
                return "xmgj_zxlr";
            case "xmgj_xxzl":
                return "xmgj_xxzl";
            case "xmgj_yhht":
                return "xmgj_yhht";
            case "xmgj_gsht":
                return "xmgj_gsht";
            case "xmgj_gpsgd":
                return "xmgj_gpsgd";
            case "xmgj_dygd":
                return "xmgj_dygd";
            case "xmgj_yhclhs":
                return "xmgj_yhclhs";
            case "xmgj_gsclhs":
                return "xmgj_gsclhs";
            case "xmgj_dyclhs":
                return "xmgj_dyclhs";
            // 不需要使用数据库的CN
            case "home":
            case "button":
            case "admin2":
            case "demo_upfile":
            case "icon":
            case "general":
            case "readmedev":
            case "readme":
            case "table":
            case "Modals":
            case "Timeline":
            case "font":
            case "zoom":


                return "";
            default:
                return null;
        }
    }

    /**
     * @param {type} {type}
     * @说明 填充默认的数据。显示list.jsp和form.jsp
     * @return: 返回
     */
    public static void doFetchDefault(HttpServletRequest request, String cn, String sdo) {
        switch (sdo) {
            case "form":
                request.setAttribute("sHideButton", "true");
                break;
            case "list":
                request.setAttribute("recs", 0); // 总记录数
                request.setAttribute("pages", 0); // 总页数
                request.setAttribute("p", 0); // 当前页码
                request.setAttribute("l", 0); // limit量
                request.setAttribute("lsitTitleString", ""); // 标题
                request.setAttribute("htmlpages", ""); // 分页的html代码
                request.setAttribute("canDel", false); // 分页的html代码
                request.setAttribute("canAdd", false); // 分页的html代码
                if (cn.equals("home")) {
                    request.setAttribute("sHideButton", "true");
                }
        }
    }

    /**
     * 获取默认后台首页的链接
     *
     * @param {type} {type}
     * @return: 返回
     */
    public static String getHomeUrl() {
        return Tools.urlKill("sdo|type|cn|kw") + "&sdo=form&type=demo&cn=home";
    }

    /**
     * 获取目前可以用的class，根据cn值（realcn）
     *
     * @param {type} {type}
     * @return: 返回
     */
    public static Class<?> doGetClass(String realCn) {
        Class<?> b = null; // 使用反射方式来实例化
        try {
            String sRootPath = Config.TT_TABLEPATH;// "";
      /* DbCtrl dbCtrl = new DbCtrl("");
      try {
        sRootPath = dbCtrl.getClass().getPackage().getName();
        sRootPath= sRootPath.replace("tool", "table");
      } catch (Exception e) {
      } finally {
        dbCtrl.closeConn();
      } */
            b = Class.forName(sRootPath + "." + realCn);
        } catch (Exception e) {
            Tools.logError(e.getMessage());
        } finally {
        }
        return b;
    }
}