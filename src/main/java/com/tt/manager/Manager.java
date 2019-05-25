/*
 * @Description:TT后台管理
 * @Author: tt
 * @Date: 2019-01-03 16:30:50
 * @LastEditTime: 2019-02-22 14:11:10
 * @LastEditors: tt
 */
package com.tt.manager;

import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

@Controller
public class Manager {
    /**
     * INDEX 的 GET处理
     *
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/manager/index", method = RequestMethod.GET)
    public String index(String cn, String type, String sdo, String id, HttpServletRequest request,
                        HttpServletResponse resp) throws ServletException, IOException {
        String sLogin = ManagerTools.checkLogin();// 检查是否登陆
        if (!Tools.myIsNull(sLogin)) {// 如未登陆跳转到登陆页面
            String newUrl = URLEncoder.encode(Tools.urlKill("toExcel|toZip"), "UTF-8");
            if (Tools.getRighStr(newUrl, 3).equals("%3F")) {//?号
                newUrl = Tools.trimRight(newUrl, 3);
            }
            return sLogin + "?refer=" + newUrl;
        }
        return new ManagerGet().doGet(cn, type, sdo, id, request, resp);
    }

    /**
     * INDEX的POST 处理
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/manager/index", method = RequestMethod.POST)
    @ResponseBody
    private String indexpost(HttpServletRequest request) throws ServletException, IOException {
        String sLogin = ManagerTools.checkLogin();// 检查是否登陆
        if (!Tools.myIsNull(sLogin)) {// 如未登陆跳转到登陆页面
            return sLogin + "?refer=" + URLEncoder.encode(Tools.urlKill(""), "UTF-8");
        }
        return Tools.jsonEncode(new ManagerPost().doPost(request));
    }

    /**
     * @param {type}
     * @description: Login的GET处理
     * @return:
     */
    @RequestMapping(value = "/manager/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        request.setAttribute("cssName", Config.MANAGER_SKINCSS);
        return "jsp/manager/login";
    }

    /**
     * @param {type}
     * @throws UnsupportedEncodingException
     * @description: Login的POST处理
     * @return:
     */
    @RequestMapping(value = "/manager/login", method = RequestMethod.POST)
    @ResponseBody
    public String loginPost(HttpServletRequest request) throws UnsupportedEncodingException {
        TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
        String refer = post.get("refer");
        String loginTb = Config.DB_USERTABLENAME;
        System.out.println(Tools.jsonEncode(post));
        TtMap result2 = new TtMap();
        Tools.formatResult(result2, false, 999, "异常，请重试！", "");// 初始化返回
        if (ManagerTools.checkSdo(post.get("sdo"))) {// 过滤掉sdo
            switch (post.get("sdo")) {
                case "login":/** 登陆 */
                    String wherestring = " and fs_type=2 and showtag=1 and deltag=0 and icbc_erp_tag=1";
                    String pass = Tools.md5(Tools.md5(post.get("password")));
                    String sql = "select id,isadmin from " + loginTb + " where username='" + post.get("username")
                            + "' AND userpass='" + pass + "'" + wherestring;
                    System.out.println("SQL:" + sql);
                    TtMap info = Tools.recinfo(sql);
                    if (info.size() > 0) {
                        Tools.formatResult(result2, true, 0, "登陆成功！",
                                Tools.myIsNull(refer) ? "/manager/index?cn=homeHx&sdo=form&type=demo" : URLDecoder.decode(refer, "UTF-8"));
                        long id = Long.parseLong(info.get("id"));
                        Tools.setNowUser(id, Boolean.parseBoolean(info.get("isadmin")));
                    } else {
                        Tools.formatResult(result2, false, 998, "用户名或者密码错误！", Tools.urlKill(""));
                    }
                    break;
                case "logout":
                    HttpSession session = request.getSession();
                    session.removeAttribute("tt_mid");
                    session.removeAttribute("tt_isadmin");
                    Tools.formatResult(result2, true, 0, "退出成功！", "/manager/login");
                    break;
            }
        }
        return Tools.jsonEncode(result2);
    }

    /**
     * 错误页面
     *
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @RequestMapping(value = "/manager/404", method = RequestMethod.GET)
    public String Show404(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        return "jsp/manager/404";
    }
}