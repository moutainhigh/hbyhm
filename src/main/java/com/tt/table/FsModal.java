/*
 * @Description: 公司的模块权限管理
 * @Author: tt
 * @Date: 2019-01-24 17:15:47
 * @LastEditTime: 2019-02-22 11:14:31
 * @LastEditors: tt
 */
package com.tt.table;


import com.tt.data.TtMap;
import com.tt.manager.Modal;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

public class FsModal extends DbCtrl {
  private String purview_map = "";
  private final String title = "公司模块设置";

  public FsModal() {
    super("assess_fs");
  }


  @Override
  public void doGetForm(HttpServletRequest request, TtMap post) {
    Modal modalMenu = new Modal();
    post.put("id", Tools.minfo().get("icbc_erp_fsid")); // 修改id为当前登陆用户所在公司的id
    request.setAttribute("modals", modalMenu.getMenus(true, 2)); // 后台左侧菜单,sidebar.jsp里面用到的菜
    long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
    TtMap info = info(nid);
    String jsonInfo = Tools.jsonEncode(info);
    request.setAttribute("info", jsonInfo);//info为json后的info
    request.setAttribute("infodb", info);//infodb为TtMap的info
    request.setAttribute("id", nid);
  }

  @Override
  public int edit(TtMap ary, long id) {
    return super.edit(ary, id);
  }

  @Override
  public long add(TtMap ary) {
    return super.add(ary);
  }

  public void closeConn() {
    if (super.conn != null) {
      super.closeConn();
    }
  }

  @Override
  public TtMap param(TtMap ary, long id) {
    for (String key : ary.keySet()) {
      if (ary.get(key).equals("1")) {
        String[] ss = key.split("/");
        if (ss.length > 1) {
          purview_map += ss[ss.length - 1] + ",";
        }
      }
    }
    ary.put("purview_map_ty", purview_map);
    //System.out.println(ary.toString());
    return ary;
  }

  @Override
  public void doPost(TtMap post, long id, TtMap result2) {
    if (id > 0) { // id为0时，新增
      edit(post, id);
    } else {
      add(post);
    }
    String nextUrl = Tools.urlKill("");
    boolean bSuccess = errorCode == 0;
    Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑" + title + "成功！" : errorMsg,
            bSuccess ? nextUrl : "");//失败时停留在当前页面,nextUrl为空
  }

  @Override
  public boolean chk(TtMap array, long id) {
    if (Tools.myIsNull(array.get("name"))) {
      errorMsg = chkMsg = "请输入名称";
      errorCode = 888;
      return false;
    }
    return true;
  }

}