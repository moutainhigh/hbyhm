/*
 * @Description: file content
 * @Author: tt
 * @Date: 2019-01-28 11:03:46
 * @LastEditTime: 2019-02-11 17:20:52
 * @LastEditors: tt
 */
package com.tt.tool;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;

public class JspTools {
  /**
   * @description: 弹出一个提示窗口
   * @param {type}
   * @return:
   */
  public static String alert(String errorMsg, String backUrl, JspWriter out) throws IOException {
    String r = "<script type=\"text/javascript\" language=\"javascript\">alert(\"" + errorMsg + "\");";
    switch (backUrl) {
    case "":
      break;
    case "-1":
      r += "history.go(-1);";
      break;
    case "404":
      r += "window.document.location.href=\"404\";";
    default:
      r += "window.document.location.href=\"" + backUrl + "\";";
      break;
    }
    r += "</script>	";
    out.print(r);
    //out.flush();
    //out.close();
    return r;
  }
  /**
   * @description: JSP跳转到指定页面。
   * @param {type} 
   * @return: 
   */
  public static String jspJsGo(String url, JspWriter out) throws IOException {
    String r = "<script type=\"text/javascript\" language=\"javascript\">";
    r += "window.document.location.href=\"" + url + "\";";
    r += "</script>	";
    out.print(r);
    out.flush();
    out.close();
    return r;
  }
  /**
   * @description: JSP跳转到指定页面。
   * @param {type} 
   * @return: 
   */
  public static String jspJsGoNew(String url, JspWriter out) throws IOException {
    String r = "<script type=\"text/javascript\" language=\"javascript\">";
    r += "window.open('"+ url + "');";
    r += "</script>	";
    out.print(r);
    out.flush();
    out.close();
    return r;
  }
  /**
   * @description: 弹出提示窗口
   * @param {type} 
   * @return: 
   */
  public static void alert(String msg, JspWriter out){
    try {
      alert(msg, "", out);
    } catch (IOException e) {
      
      e.printStackTrace();
    }
  }
}