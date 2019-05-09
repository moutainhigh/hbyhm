package com.tt.manager;
/*
 * @Description: 后台模块管理类
 * @Author: tt
 * @Date: 2019-01-21 10:03:57
 * @LastEditTime: 2019-02-22 11:09:19
 * @LastEditors: tt
 */

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.table.AdminAgp;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 模块管理类
 * @param {type} 
 */
public class Modal {
  /**
   * @description: mustShowMenuTag只显示后台菜菜单显示标志showmmenutag为1的，即只显示设置为后台菜单要显示的数据
   * allTag,0为普通角色拥有的模块，1为公司所有可以勾选，2为公司已经勾选的
   * @param {type}
   * @return:
   */
  public Object getMenus(boolean mustShowMenuTag, int allTag) {
    Map<String, Object> result = new LinkedHashMap<>(); // 不使用HashMap的原因是因为HashMap会自动排序
    DbCtrl dbCtrl = new DbCtrl("sys_modal_hbyh");
    try {
      dbCtrl.showall = true;
      dbCtrl.nopage = true;
      dbCtrl.orders = "ORDER BY t.sort,t.showmmenuname";
      String sqlMustShowMenuTag = "";
      if (mustShowMenuTag) {
        sqlMustShowMenuTag = " AND showmmenutag=1";
      }
      AdminAgp aa = new AdminAgp();
      try {
        String sqlA = " AND false";
        switch (allTag) {
        case 0:
          sqlA = aa.getAgpSqlWhere();
          break;
        case 1:
          sqlA = aa.getAgpSqlWhereForFsAll();
          break;
        case 2:
          sqlA = aa.getAgpSqlWhereForFsChecked();
          break;
        }
        sqlMustShowMenuTag = sqlMustShowMenuTag + sqlA;
      } catch (Exception e) {
        Tools.logError(e.getMessage(),true,false);
        sqlMustShowMenuTag = " AND FALSE";
      } finally {
        aa.closeConn();
      }
      String sql = "level=1" + sqlMustShowMenuTag;
      //System.out.println("menu sql:" + sql);
      TtList list = dbCtrl.lists(sql, "t.name,t.id,t.showmmenuname,t.icohtml,t.urlotherstr");
      //System.out.println("list order:" + list.toString());
      for (TtMap model : list) {
        String whString = "level=2 AND id_uplevel='" + model.get("id") + "'" + sqlMustShowMenuTag; // 查询二级菜单
        TtList listsub = dbCtrl.lists(whString,
            "t.name,t.id,t.showmmenuname,t.cn,t.type,t.icohtml,t.sdo,t.urlotherstr");
        //System.out.println(model.get("showmmenuname"));
        Map<String, Object> data = new HashMap<>();
        data.put("submenu", listsub);
        data.put("mainmenu", model);
        result.put(model.get("showmmenuname"), data);
      }
      // Map<String, String> info = dbCtrl.info();
    } catch (Exception e) {
      Tools.logError(e.getMessage(),true,false);
    } finally {
      dbCtrl.closeConn();
    }
    return (Object) result;
  }

  public Object getMenus2(boolean mustShowMenuTag, int allTag,int fsid,int agpid) {
    Map<String, Object> result = new LinkedHashMap<>(); // 不使用HashMap的原因是因为HashMap会自动排序
    DbCtrl dbCtrl = new DbCtrl("sys_modal_hbyh");
    try {
      dbCtrl.showall = true;
      dbCtrl.nopage = true;
      dbCtrl.orders = "ORDER BY t.sort,t.showmmenuname";
      String sqlMustShowMenuTag = "";
      if (mustShowMenuTag) {
        sqlMustShowMenuTag = " AND showmmenutag=1";
      }
      AdminAgp aa = new AdminAgp();
      try {
        String sqlA = " AND false";
        switch (allTag) {
          case 0:
            sqlA = aa.getAgpSqlWhere2(fsid,agpid);
            break;
          case 1:
            sqlA = aa.getAgpSqlWhereForFsAll2();
            break;
          case 2:
            sqlA = aa.getAgpSqlWhereForFsChecked2(fsid);
            break;
        }
        sqlMustShowMenuTag = sqlMustShowMenuTag + sqlA;
      } catch (Exception e) {
        Tools.logError(e.getMessage(),true,false);
        sqlMustShowMenuTag = " AND FALSE";
      } finally {
        aa.closeConn();
      }
      String sql = "level=1" + sqlMustShowMenuTag;
      //System.out.println("menu sql:" + sql);
      TtList list = dbCtrl.lists(sql, "t.name,t.id,t.showmmenuname,t.icohtml,t.urlotherstr");
      //System.out.println("list order:" + list.toString());
      for (TtMap model : list) {
        String whString = "level=2 AND id_uplevel='" + model.get("id") + "'" + sqlMustShowMenuTag; // 查询二级菜单
        TtList listsub = dbCtrl.lists(whString,
                "t.name,t.id,t.showmmenuname,t.cn,t.type,t.icohtml,t.sdo,t.urlotherstr");
        //System.out.println(model.get("showmmenuname"));
        Map<String, Object> data = new HashMap<>();
        data.put("submenu", listsub);
        data.put("mainmenu", model);
        result.put(model.get("showmmenuname"), data);
      }
      // Map<String, String> info = dbCtrl.info();
    } catch (Exception e) {
      Tools.logError(e.getMessage(),true,false);
    } finally {
      dbCtrl.closeConn();
    }
    return (Object) result;
  }
  /**
   * @description: 获取模块：后台菜单显示的。
   * @param {type} 
   * @return: 
   */
  public Object getMenus() {
    return this.getMenus(true, 0);
  }
  /**
   * @description: 获取模块，普通
   * @param {type} 
   * @return: 
   */
  public Object getModals() {
    return this.getMenus(false, 0);
  }
  /**
   * @description: 获取模块，公司级别所有可以勾选
   * @param {type} 
   * @return: 
   */
  public Object getAllModals() {
    return this.getMenus(false, 1);
  }
  /**
   * @description: 获取模块，公司级别已经勾选的
   * @param {type} 
   * @param {type} 
   * @return: 
   */
  public Object getAllFsCheckedModals() {
    return this.getMenus(false, 2);
  }
}