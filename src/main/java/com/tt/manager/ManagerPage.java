/*
 * @Description: 分页管理，显示分页列表，前台/后台/PC/手机通用
 * @Author: tt
 * @Date: 2019-01-16 20:12:31
 * @LastEditTime: 2019-02-12 11:44:52
 * @LastEditors: tt
 */
package com.tt.manager;

import com.tt.data.TtMap;
import com.tt.tool.Tools;

import java.util.HashMap;
import java.util.Map;

public class ManagerPage {
  /**
   * @description: 获取分页html代码。
   * @param Map<String, Object> mso为生成的html集合，用来处理待处理的分页代码的html
   * @return: 无返回，直接生成html代码到mso引用中。
   */
  public static void SetPageHtml(Map<String, Object> mso) {
    TtMap info = new TtMap();
    /* admin */
    info.put("htmlstr", "%f% %p% %num% %n% %e%");
    info.put("fstr", "");
    info.put("pstr", "<li><a href=\"%url%\" aria-label=\"Previous\"><span aria-hidden=\"true\">«</span></a></li> ");
    info.put("nstr", "<li><a href=\"%url%\" aria-label=\"Next\"><span aria-hidden=\"true\">»</span></a></li> ");
    info.put("estr", "");
    info.put("goto", "<li><a href=\"%url%\">%num%</a></li> ");
    info.put("nowstr",
        "<li class=\"active\"><a href=\"%url%\">%num% <span class=\"sr-only\">(current)</span></a></li> ");
    mso.put("admin", info);
    /* web */
    TtMap info2 = new TtMap();
    info2.put("htmlstr", "%f% %p% <div class=\"p-paging-list\">%num%</div> %n% %e%");
    info2.put("fstr", "");
    info2.put("pstr", "<a href=\"%url%\" class=\"p-paging-next\"><i class=\"p-page-ArrowLt\"></i>上一页</a>");
    info2.put("nstr", "<a href=\"%url%\" class=\"p-paging-next\">下一页<i class=\"p-page-ArrowRt\"></i></a>");
    info2.put("estr", "");
    info2.put("goto", "<a href=\"%url%\">%num%</a>");
    info2.put("nowstr", "<a class=\"p-paging-cur\">%num%</a>");
    mso.put("web", info2);
    /* small */
    TtMap infosmall = new TtMap();
    infosmall.put("htmlstr", "%p% %n%");
    infosmall.put("pstr", "<a href=\"%url%\">&lt; 上一页</a>");
    infosmall.put("nstr", "<a href=\"%url%\">下一页 &gt;</a>");
    mso.put("small", infosmall);
  }

  /**
   * 計算分頁代碼 admin
   * 
   * @param integer $e 總頁數
   * @param integer $now 當前頁數
   * @return string 分頁代碼
   */
  public static String getPage(int e, int now, int l,Boolean auto_hide) {
    String result = "";
    Map<String, Object> mso = new HashMap<>();
    SetPageHtml(mso);
    TtMap mss = (TtMap) mso.get("admin");
    if (e > 1 || !auto_hide) {
      int f = 1;
      int n = now < e ? now + 1 : e;
      int p = now > f ? now - 1 : f;
      int begin = 0;
      int end = 0;
      if (l >= 0) {
        int num = (int) Math.ceil(l / 2);
        if (now > num && now < (e - l + 1)) {
          begin = now - num;
          end = now + num;
        } else if (now > num) {
          begin = e - l + 1;
          end = e;
        } else {
          begin = f;
          end = f + l - 1;
        }
        if (begin < f) {
          begin = f;
        }
        if (end > e) {
          end = e;
        }
      }
      String PAGE_CHR = "PAGECHR";
      String fstr = mss.get("fstr");
      String pstr = mss.get("pstr");
      String nstr = mss.get("nstr");
      String estr = mss.get("estr");
      String url = Tools.urlKill("p") + "&p=" + PAGE_CHR;
      if (f != now) {
        fstr = fstr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(f)));
        fstr = fstr.replace("%num%", String.valueOf(f));
      } else {
        fstr = "";
      }
      if (f != now) {
        pstr = pstr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(p)));
        pstr = pstr.replace("%num%", String.valueOf(p));
      } else {
        pstr = "";
      }
      if (e != now) {
        nstr = nstr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(n)));
        nstr = nstr.replace("%num%", String.valueOf(n));
      } else {
        nstr = "";
      }
      if (Tools.myIsNull(estr) == false) {
        if (e != now) {
          estr = estr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(e)));
          estr = estr.replace("%num%", String.valueOf(e));
        } else {
          estr = "";
        }
      }
      String numstr = "";
      String mstr = "";
      String nowstr = mss.get("nowstr");
      String gotostr = mss.get("goto");
      String htmlstr = mss.get("htmlstr");
      if (l >= 0) {
        for (int i = begin; i <= end; i++) {
          if (i == now) {
            String tmpstr = nowstr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(i)));
            tmpstr = tmpstr.replace("%num%", String.valueOf(i));
            numstr += tmpstr;
          } else {
            String tmpstr = gotostr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(i)));
            tmpstr = tmpstr.replace("%num%", String.valueOf(i));
            numstr += tmpstr;
          }
        }
      }

      if (end < e) {
        mstr = mstr.replace("%url%", url.replace(PAGE_CHR, String.valueOf(end + 1)));
      }
      String str = "";
      if (htmlstr == "") {
        str = fstr + pstr + numstr + mstr + nstr + estr;
      } else {
        htmlstr = htmlstr.replace("%f%", fstr);
        htmlstr = htmlstr.replace("%p%", pstr);
        htmlstr = htmlstr.replace("%n%", nstr);
        htmlstr = htmlstr.replace("%e%", estr);
        htmlstr = htmlstr.replace("%num%", numstr);
        htmlstr = htmlstr.replace("%m%", mstr);
        str = htmlstr;
      }
      /*
       * if($select_page){ $select_page=str_replace('{now_page}', $now, $select_page);
       * $select_page=str_replace('{all_page}', $e, $select_page); $select_option='';
       * for($i=1;$i<=$e;$i++){ if($i==$now){ $select_option.='<option
       * value="'.str_replace(PAGE_CHR, $i, $url).'"
       * selected="selected">'.$i.'</option>'; }else{ $select_option.='<option
       * value="'.str_replace(PAGE_CHR, $i, $url).'">'.$i.'</option>'; } }
       * $select_page=str_replace('{select_option}', $select_option, $select_page); }
       */
      result = str;
    } else {
    }

    // System.out.println(mss.get("pstr"));
    return result;
  }
}