/*
 * @Description: 权限类
 * @Author: tt
 * @Date: 2019-01-24 09:38:15
 * @LastEditTime: 2019-02-22 14:09:56
 * @LastEditors: tt
 */
package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.manager.Modal;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

public class AdminAgp extends DbCtrl {
    private String purview_map = "";
    private TtMap minfo = Tools.minfo();// 当前登陆用户信息
    private TtMap infoAgp = info(Tools.strToLong(minfo.get("agpid")));// 用户所属角色组信息
    private boolean canDel = false;
    private boolean canAdd = true;
    private String strAgp = "," + infoAgp.get("purview_map"); // 此角色组拥有的权限集合

    /**
     * @param {type}
     * @description: 管理员权限管理演示，角色管理controller。演示用继承dbCtrl的单独的controller来处理所有与角色管理相关的事务。
     * @return: 返回一个class, 可以在这里新增各种方法处理对应的事务。
     */
    public AdminAgp() {
        super("icbc_admin_agp");
    }

    /**
     * @param Map  ary 为需要编辑的新数据
     * @param long id 为需要编辑的记录id
     * @description: 继承dbCtrl父类的edit方法，可以在这里插入针对本类编辑一个旧的记录时需要特殊处理的代码
     * @return: 返回dbCtrl的edit方法返回值
     */
    @Override
    public int edit(TtMap ary, long id) {
        return super.edit(ary, id);
    }

    /**
     * @param Map ary为需要新增的新数据
     * @description: 继承dbCtrl父类的add方法，可以在这里插入针对本类新增记录时需要特殊处理的代码
     * @return: 返回dbCtrl的add方法返回值，一般为新增记录的id值
     */
    @Override
    public long add(TtMap ary) {
        return super.add(ary);
    }

    public void closeConn() {
        super.closeConn();
    }

    /**
     * @param {type}
     * @description: 重载DbCtrl的param方法，在写入数据前会回调此方法
     * @return:
     */
    @Override
    public TtMap param(TtMap ary, long id) {
        System.out.println(ary.toString());
        for (String key : ary.keySet()) {
            if (ary.get(key).equals("1")) {
                String[] ss = key.split("/");
                if (ss.length > 1) {
                    purview_map += ss[ss.length - 1] + ",";
                }
            }
        }
        ary.put("purview_map", purview_map);
        System.out.println(ary.toString());
        return ary;
    }

    /**
     * @param Map  ary 为需要编辑的新数据
     * @param long id 为需要编辑的记录id，add时，此id为0
     * @description: 继承dbCtrl父类的chk方法，写入任何数据（包括add和edit）时进行的数据检查方法，如果这个方法返回为false，那么写入操作都终止。
     * @return:boolean , 为true时数据检查成功，同时可以设置errorMsg和chkMsg一家errCode,false时，数据检查失败
     */
    @Override
    public boolean chk(TtMap array, long id) {
        if (Tools.myIsNull(array.get("name"))) {
            errorMsg = chkMsg = "请输入完整的角色名称";
            errorCode = 888;
            return false;
        } else {
            return true;
        }
    }

    /**
     * @param long modalId
     * @description: 当前登陆用户是否有模块权限, modalId为权限id
     * @return: boolean 是否有权限
     */
    public boolean haveAgp(long modalId) {
        boolean r = false;
        r = strAgp.indexOf("," + String.valueOf(modalId) + ",") != -1;
        return r;
    }

    public boolean haveAgp(String modalId) {
        boolean r = false;
        r = strAgp.indexOf("," + modalId + ",") != -1;
        return r;
    }

    /**
     * @param {type}
     * @description: 获取权限字符串，admin_agp表里当前登陆用户所属角色组的权限，无最后逗号，类似 1,2,3,4,5,6这种形式
     * @return:
     */
    public String getAgpStr() {
        String purview_map = strAgp.substring(1);// 删除最前面的逗号
        if (purview_map.length() > 0 && purview_map.charAt(purview_map.length() - 1) == ',') { // 删除最后逗号
            purview_map = purview_map.substring(0, purview_map.length() - 1);
        }
        return purview_map;
    }

    /**
     * @param {type}
     * @description: 获取权限字符串，admin_agp表里当前登陆用户所属角色组的权限，比如获取指定公司的所有模块,无前后逗号,
     * @return:
     */
    public String getAgpStrFs(String tbName, String fsid) {
        String purview_map = Tools.recinfo("select purview_map_ty from " + tbName + " where id=" + fsid).get("purview_map_ty");
        if (!Tools.myIsNull(purview_map)) {
            if (purview_map.length() > 0 && purview_map.charAt(purview_map.length() - 1) == ',') { // 删除最后逗号
                purview_map = purview_map.substring(0, purview_map.length() - 1);
            }
        } else {
            purview_map = "";
        }
        return purview_map;
    }

    /**
     * @param {type}
     * @description: 获取权限字符串，admin_agp表里当前登陆用户所属角色组的权限，比如获取指定公司的所有模块,无前后逗号,
     * @return:
     */
    public String getAgpStradmin(String tbName, String fsid) {
        String purview_map = Tools.recinfo("select purview_map from " + tbName + " where id=" + fsid).get("purview_map");
        if (!Tools.myIsNull(purview_map)) {
            if (purview_map.length() > 0 && purview_map.charAt(purview_map.length() - 1) == ',') { // 删除最后逗号
                purview_map = purview_map.substring(0, purview_map.length() - 1);
            }
        } else {
            purview_map = "";
        }
        return purview_map;
    }

    /**
     * @param {type}
     * @description: 获取查询的sql的where，自带AND
     * @return:
     */
    public String getAgpSqlWhere() { // 根据当前登陆用户获取可以操作的模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            sql = getAgpSqlWhereForFsAll(); // 获取公司全部可以操作的模块
            String purview_map = getAgpStrFs("assess_fs", minfo.get("icbc_erp_fsid")); // 所在公司的权限
            sql = sql + " AND t.id in (" + purview_map + ")";
            String agp_id = minfo.get("agpid");
            purview_map = getAgpStradmin("icbc_admin_agp", agp_id); // 所属角色权限
            sql = sql + " AND t.id in (" + purview_map + ")";
        } else {
            sql = "";
        }
        return sql;
    }

    public String getAgpSqlWhere2(int fsid, int agpid) { // 根据当前登陆用户获取可以操作的模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            sql = getAgpSqlWhereForFsAll(); // 获取公司全部可以操作的模块
            String purview_map = getAgpStrFs("assess_fs", String.valueOf(fsid)); // 所在公司的权限
            sql = sql + " AND t.id in (" + purview_map + ")";
            String agp_id = String.valueOf(agpid);
            purview_map = getAgpStradmin("icbc_admin_agp", agp_id); // 所属角色权限
            sql = sql + " AND t.id in (" + purview_map + ")";
        } else {
            sql = "";
        }
        return sql;
    }

    public String getAgpSqlWhereForFsChecked() { // 获取公司已开通（即已经勾选的模块）的所有模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            sql = " AND superadmin=0";
            String purview_map = getAgpStrFs("assess_fs", minfo.get("icbc_erp_fsid")); // 所在公司的权限
            sql = sql + " AND t.id in (" + purview_map + ")";
        } else {
            sql = "";
        }
        return sql;
    }

    public String getAgpSqlWhereForFsChecked2(int fsid) { // 获取公司已开通（即已经勾选的模块）的所有模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            sql = " AND superadmin=0";
            String purview_map = getAgpStrFs("assess_fs", String.valueOf(fsid)); // 所在公司的权限
            sql = sql + " AND t.id in (" + purview_map + ")";
        } else {
            sql = "";
        }
        return sql;
    }

    /**
     * @param {type}
     * @description: 获取公司可以添加的所有模块，查询的sql的where，自带AND
     * @return:
     */
    public String getAgpSqlWhereForFsAll() { // 获取公司可以开通的所有模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            if (Tools.isCcAdmin(minfo)) {
                sql = " AND (superadmin=2 OR superadmin=0)";
            } else {
                sql = " AND superadmin=0";
            }
        } else {
            sql = "";
        }
        return sql;
    }

    public String getAgpSqlWhereForFsAll2() { // 获取公司可以开通的所有模块的sql的where
        String sql = " AND FALSE";
        if (!Tools.isSuperAdmin(minfo)) {// 如果不是内部超级管理员
            if (Tools.isCcAdmin(minfo)) {
                sql = " AND (superadmin=2 OR superadmin=0)";
            } else {
                sql = " AND superadmin=0";
            }
        } else {
            sql = "";
        }
        return sql;
    }

    private TtMap infoModal(String modalId) {
        return Tools.recinfo("select * from sys_modal_hbyh where id='" + modalId + "'");
    }

    /**
     * @param {type}
     * @description: 检查当前用户是否拥有指定权限，fsTableName为公司表名，如果为空，不考虑公司权限
     * @return:
     */
    public boolean checkAgp(String modalId, String fsTableName) {
        boolean result = false;
        TtMap infoModal = infoModal(modalId);
        if (!Tools.isSuperAdmin(minfo)) {// 当前登陆用户非超级管理员
            switch (infoModal.get("superadmin")) {
                case "1":// 此权限ID是超级管理员才能用的哈
                    result = false;
                    break;
                case "2":// 此权限需要公司内部人员才能用的哈
                    result = Tools.isCcAdmin(minfo);
                    break;
                case "0":// 普通模块，直接判断当前用户所属角色组是否包含此权限
                    result = strAgp.indexOf("," + modalId + ",") != -1;
                    if (result && !Tools.myIsNull(fsTableName)) {// 如果此用户角色拥有此权限，判断其所在公司是否勾选了次模块
                        String strAgpFs = getAgpStrFs(fsTableName, minfo.get("icbc_erp_fsid"));
                        strAgpFs = "," + strAgpFs + ",";
                        result = strAgpFs.indexOf("," + modalId + ",") != -1;
                    }
                    break;
                default:
                    break;
            }
            // return
        } else {// 超级管理员拥有所有权限
            result = true;
        }
        return result;
    }

    public boolean checkAgp(String modalId) {
        return checkAgp(modalId, null);
    }

    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        if (id > 0) { // id为0时，新增
            edit(post, id);
        } else {
            add(post);
        }
        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg,
                bSuccess ? nextUrl : "");//失败时停留在当前页面,nextUrl为空
    }

    /**
     * @param request 待setAttribute的HttpServletRequest。
     * @param post    url来的参数
     * @return 直接setAttribute到相关字段里面
     * @description: 处理后台的get, 演示独立类处理sdo=form的get
     */
    @Override
    public void doGetForm(HttpServletRequest request, TtMap post) {
        Modal modalMenu = new Modal();
        request.setAttribute("modals", modalMenu.getAllFsCheckedModals()); // 后台左侧菜单,sidebar.jsp里面用到的菜单列表
        String id = post.get("id");
        long nid = 0;
        if (!Tools.myIsNull(id)) {
            nid = Long.valueOf(id);
        }
        showall = true;
        TtMap info = info(nid);
        System.out.println(info);
        String jsonInfo = Tools.jsonEncode(info);
        System.out.println(jsonInfo);
        request.setAttribute("info", jsonInfo);
        request.setAttribute("infodb", info);
        request.setAttribute("id", nid > 0 ? nid : 0);
    }

    /**
     * @param {type}
     * @description: 处理后台的get, 演示独立类处理sdo=list的get
     * @return:
     */
    @Override
    public void doGetList(HttpServletRequest request, TtMap post) {
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        String whereString = "true";
        String lsitTitleString = "全局角色模板管理";
        String orderString = "ORDER BY systag DESC"; // 默认排序
        String tmpWhere = "";
        String fieldsString = ""; // 显示字段列表如t.id,t.name,t.dt_edit
        TtList list = null;
        if (Tools.isSuperAdmin(minfo)) {
            if (!Tools.myIsNull(post.get("systag")) && post.get("systag").equals("1")) {
                tmpWhere = " AND systag=1";
            }
        } else {
            tmpWhere = " AND fsid=" + minfo.get("icbc_erp_fsid") + " AND systag=0";
        }
        whereString += tmpWhere; // 过滤
        orders = orderString;// 排序
        p = pageInt; // 显示页
        limit = limtInt; // 每页显示记录数
        showall = true; // 忽略deltag和showtag
        list = lists(whereString, fieldsString);
        /* list的后期处理开始 */
        for (TtMap tmpInfo : list) { // 获取当前角色组的成员列表
            String value = "";
            TtList listTmps = Tools
                    .reclist("select name from " + Config.DB_USERTABLENAME + " where agpid=" + tmpInfo.get("id") + " and icbc_erp_fsid=" + minfo.get("icbc_erp_fsid"));
            for (TtMap var : listTmps) {
                value += var.get("name") + ",";
            }
            tmpInfo.put("mans", value);
            if (Tools.isSuperAdmin(minfo)) {
                tmpInfo.put("fsname", Tools.unDic("assess_fs", Tools.strToLong(tmpInfo.get("fsid"))));
                tmpInfo.put("systag", tmpInfo.get("systag").equals("1") ? "是" : "否");
            }
        }
        request.setAttribute("list", list);
        request.setAttribute("recs", recs); // 总记录数
        String htmlpages = getPage("", 0, false); // 分页html代码,
        request.setAttribute("pages", pages); // 总页数
        request.setAttribute("p", pageInt); // 当前页码
        request.setAttribute("l", limtInt); // limit量
        request.setAttribute("lsitTitleString", lsitTitleString); // 标题
        request.setAttribute("htmlpages", htmlpages); // 分页的html代码
        request.setAttribute("canDel", canDel); // 是否显示删除按钮
        request.setAttribute("canAdd", canAdd); // 是否显示删除按钮
    }
}