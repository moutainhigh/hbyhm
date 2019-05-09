/*
 * @说明: cookie操作类
 * @Description: file content
 * @Author: tt
 * @Date: 2019-02-16 10:30:06
 * @LastEditTime: 2019-02-16 16:18:16
 * @LastEditors: tt
 */
package com.tt.tool;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieTools {
    private static HttpServletRequest req;
    private static HttpServletResponse resp;

    /**
     * 说明：初始化，获取当前的servlet的getRequest和Response
     *
     * @param {type} {type}
     * @return: 返回
     */
    public static void init() {
        req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * @param {type} {type}
     * @说明 删除一个cookie
     * @return: 返回
     */
    public static void del(String key) {
        init();
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    break;
                }
            }
        }
    }

    /**
     * @param {type} {type}
     * @说明 设置cookie，不存在就新建，存在就更新
     * @return: 返回
     */
    public static void set(String key, String value) {
        set(key, value, "", 7 * 86400);//默认7天
    }

    /**
     * @param {type} {type}
     * @说明 设置cookie，不存在就新建，存在就更新
     * @return: 返回
     */
    public static void set(String key, String value, String path, int maxAge) {
        init();
        Cookie[] cookies = req.getCookies();
        boolean bHaveSet = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    cookie.setValue(value);
                    cookie.setMaxAge(maxAge);
                    if (!Tools.myIsNull(path)) {
                        cookie.setPath(path);
                    }
                    resp.addCookie(cookie);
                    bHaveSet = true;
                    break;
                }
            }
        }
        if (!bHaveSet) {
            Cookie cookie = new Cookie(key, value);
            cookie.setMaxAge(maxAge);
            if (!Tools.myIsNull(path)) {
                cookie.setPath(path);
            }
            resp.addCookie(cookie);
        }
    }

    /**
     * @param {type} {type}
     * @说明 获取cookie值
     * @return: 返回
     */
    public static String get(String key) {
        init();
        String r = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    r = cookie.getValue();
                    break;
                }
            }
        }
        return r;
    }
}