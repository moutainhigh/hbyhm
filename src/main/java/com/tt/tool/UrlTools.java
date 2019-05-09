/*
 * @Description: url处理工具类
 * @Author: tt
 * @Date: 2019-01-28 11:46:38
 * @LastEditTime: 2019-01-28 12:45:42
 * @LastEditors: tt
 */
package com.tt.tool;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlTools {
    /**
     * @param {type}
     * @description: 跳转 response.sendRedirect(“地址”):效率低，速度慢 a.地址栏改变跳转——客户端跳转
     * b.所有代码执行完毕之后再跳转，跳转语句后面的代码还是会执行，除非在其后面加上return（return）需复杂一些。
     * c.不能保存request属性——两次请求，地址改变了，客户端跳转，不同的request d.通过对URL地址的重写传递参数：
     * esponse.sendRedirect(“responseDemo04.jsp?id=mldn”);
     * @return:
     */
    public static void go(String url) {
        try {
            HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            resp.sendRedirect(url);
        } catch (Exception e) {
            Tools.logError(e.getMessage());
        } finally {
        }
    }

    /**
     * @param {type}
     * @description: 跳转2：针对JSP与Servlet：forward
     * 1.request.getRequestDispatcher("地址").forward(ServletRequest,
     * ServletResponse);效率高，速度快 转发：服务器
     * 接收到客户端的请求之后，服务器把控制权交到另一个JSP页面手里，新的JSP页面接收到请求之后根据情况是继续转交控制权或
     * 显示页面由自己决定，到最后显示页面的整个过程就是一个页面跳转过程，在这个过程中，服务器可以把请求的数据在经过的页面进行传递，而不会担心数据的丢失。
     * a.地址栏不改变跳转——服务器端跳转，服务器之间内部跳转，相同的request，可传参；
     * b.执行到跳转语句后无条件立刻跳转——之后的代码不再被执行；
     * 注意：如果使用forward跳转，则一定要在跳转之前释放掉全部的资源；
     * c.使用forward时，request设置的属性依然能保留在下一个页面(setAttribute);
     * @return:
     */
    public static void goForward(String url) {
        try {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse resp = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            req.getRequestDispatcher(url).forward(req, resp);
        } catch (Exception e) {
            Tools.logError(e.getMessage());
        } finally {
        }
    }
}