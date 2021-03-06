/*
 * @说明: 这里写说明哈
 * @Description: file content
 * @Author: tt
 * @Date: 2019-06-19 13:11:58
 * @LastEditTime: 2019-06-19 14:12:30
 * @LastEditors: tt
 */
package com.tt.table;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.manager.Modal;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

public class assess_fs extends DbCtrl {
    private String purview_map = "";
    private final String title = "公司管理";
    private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = true;
    private final String classAgpId = "45"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限


    public assess_fs() {
        super("assess_fs");
        AdminAgp adminAgp = new AdminAgp();
        try {
            if (adminAgp.checkAgp(classAgpId)) { // 如果有权限
                Tools.mylog("权限检查成功！");
                agpOK = true;
            } else {
                errorCode = 444;
                errorMsg = "您好，您暂无权限！";
            }
        } catch (Exception e) {
            Tools.logError(e.getMessage(), true, false);
        } finally {
            adminAgp.closeConn();
        }
    }

    /**
     * @param {type} {type}
     * @说明: 给继承的子类重载用的
     * @return: 返回
     */
    public void doGetForm(HttpServletRequest request, TtMap post) {
        TtMap minfo = Tools.minfo();
        //String f = "*";
        //leftsql = "LEFT JOIN assess_fs_details d ON d.fs_id=t.id";
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        TtMap info = info(nid);
        String jsonInfo = Tools.jsonEncode(info);
        request.setAttribute("info", jsonInfo);//info为json后的info
        request.setAttribute("infodb", info);//infodb为TtMap的info
        request.setAttribute("id", nid);
        //获取公司列表
        //request.setAttribute("saveButton", "true");
        Modal modalMenu = new Modal();
        String fssql = "";
        int fsid = 0;
        int up_id = 0;
        if (nid > 0) {
            fsid = Integer.parseInt(info.get("id"));
            up_id = Integer.parseInt(info.get("up_id"));
        } else {
            fsid = Integer.parseInt(minfo.get("icbc_erp_fsid"));
            up_id = Integer.parseInt(minfo.get("icbc_erp_fsid"));
        }
        if (Tools.isSuperAdmin(minfo)) {
            fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!=''";
        } else if (Tools.isCcAdmin(minfo)) {
            fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id=" + fsid + " or up_id=" + up_id + ")";
        } else {
            fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and  up_id=" + up_id;
        }
        TtList fslist = Tools.reclist(fssql);
        request.setAttribute("fslist", fslist);
        request.setAttribute("modals", modalMenu.getMenus(true, 2)); // 后台左侧菜单,sidebar.jsp里面用到的菜
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
    public TtList lists(String wheres, String f) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            return null;
        }
        TtMap minfo = Tools.minfo();
        String superwheres = "";
        switch (minfo.get("superadmin")) {
            case "0":
                superwheres = " ti.id=" + minfo.get("icbc_erp_fsid");
                break;
            case "1":
                //superwheres =" ti.id="+minfo.get("icbc_erp_fsid");
                break;
            case "2":
                superwheres = " (t.id=" + minfo.get("icbc_erp_fsid") + " or t.up_id=" + minfo.get("icbc_erp_fsid") + ")";
                break;
            case "3":
                String ids = Tools.getfsids(Tools.myIsNull(minfo.get("icbc_erp_fsid")) ? 0 : Integer.parseInt(minfo.get("icbc_erp_fsid")));
                superwheres = " t.id in (" + ids + ")";
                break;
            default:

                break;
        }
        if (Tools.myIsNull(wheres)) {
            wheres = Tools.isSuperAdmin(minfo) ? "" : superwheres;
        } else {
            wheres += Tools.isSuperAdmin(minfo) ? "" : " AND " + superwheres; // 只显示自己公司的
        }
        TtList lmss = super.lists(wheres, f);
        for (TtMap tmpInfo : lmss) {
            tmpInfo.put("choice", Tools.dicopt("sys_dic_tag", Tools.strToLong(tmpInfo.get("showtag")))); // 显示/隐藏
            TtMap uermap = Tools.recinfo("select count(*) as usercount from assess_admin where icbc_erp_fsid=" + tmpInfo.get("id"));
            tmpInfo.put("usercount", uermap.get("usercount")); // 显示/隐藏
            TtMap upmap = Tools.recinfo("select name from assess_fs where id=" + tmpInfo.get("up_id"));
            tmpInfo.put("up_name", upmap.get("name"));
            TtMap money = Tools.recinfo("SELECT SUM(amount) as money FROM moneyfs where bintype=0 and fsid=" + tmpInfo.get("id"));
            if(money.isEmpty()){
                tmpInfo.put("money", String.valueOf(new BigDecimal("0.00")));
            }else{
                if(Tools.myIsNull(money.get("money"))){
                    tmpInfo.put("money", String.valueOf(new BigDecimal("0.00")));
                }else {
                    tmpInfo.put("money", String.valueOf(new BigDecimal(money.get("money"))));
                }
            }
        }
        return lmss;
    }

    /**
     * @param {type} {type}
     * @说明: 给子类重载用，处理post
     * @return: 返回
     */
    public void doPost(TtMap post, long id, TtMap result2) {
        if (post.get("oemimgurl").equals("images/mgcaraddimg.jpg")) {
            post.put("oemimgurl", "");
        }
        System.out.println("new Date().getTime()" + String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("create_time", String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("update_time", String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("zone_id", "0");
        post.put("support", "");
        post.put("deltag", "0");
        post.put("mgicbc_tag", "0");
        if (post.get("mg_tag").equals("0")) {
            post.put("purview_map_kjs", "");
        } else {
            post.put("purview_map_kjs", post.get("purview_map_kjs"));
        }
        if (Tools.myIsNull(post.get("purview_map"))) {
            post.put("purview_map", "order_ks,order_zy,order_query_da,order_query_by,order_query_zx,order_query_bdzx,order_query_thjl,order_query_bx,order_query_wdhmd,order_query_yhksm,order_query_yhkls,kj_zxjb,order_kj_qcyz,");
        } else {
            post.put("purview_map", post.get("purview_map"));
        }
//        post.put("up_id",Tools.minfo().get("icbc_erp_fsid"));
        post.put("icbc_erp_tag", "1");
        post.put("fs_type", "2");
        TtMap newpost = new TtMap();
        newpost.putAll(post);
        long icbc_id = 0;
        //imgs(post);
        System.out.println("post:" + post.get("purview_map") + "----------" + post.get("purview_map_kjs"));
        if (id > 0) { // id为0时，新增
            edit(post, id);
        } else {
            add(post);

        }
        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg, bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
    }

    @Override
    public void succ(TtMap array, long id, int sqltp) {
        TtList ttMaps = Tools.reclist("select * from icbc_admin_agp where fsid=" + id);
        if (ttMaps.size() > 0) {

        } else {
            TtList apglist = Tools.reclist("select * from icbc_admin_agp where showtag=1 and fsid=0 and systag=1");
            for (TtMap apg : apglist) {
                TtMap apgmap = new TtMap();
                apgmap.put("name", apg.get("name"));
                apgmap.put("purview_map", apg.get("purview_map"));
                apgmap.put("showtag", "1");
                apgmap.put("fsid", String.valueOf(id));
                apgmap.put("systag", "0");
                Tools.recAdd(apgmap, "icbc_admin_agp");
            }
        }
        //充值扣款
        TtMap minfo = Tools.minfo();//获取当前登录用户信息
        if (array.get("addmoney") != null
                && !array.get("addmoney").equals("")) {
            TtMap moneyfs = new TtMap();
            moneyfs.put("type", "1");
            moneyfs.put("status", "1");
            moneyfs.put("otherid", "0");
            moneyfs.put("orderid", "0");
            moneyfs.put("mid", minfo.get("id"));
            moneyfs.put("fsid", String.valueOf(id));
            moneyfs.put("gemsid", "0");
            moneyfs.put("amount",array.get("addmoney"));
            moneyfs.put("remark","人保后台-"+array.get("czremark"));
            moneyfs.put("bintype", array.get("bintype"));
            moneyfs.put("fctype", array.get("fctype"));
            long moneyfsid= Tools.recAdd(moneyfs,"moneyfs");
            TtMap moneyfs1 = new TtMap();
            moneyfs1.put("mid", minfo.get("id"));
            moneyfs1.put("fsid", String.valueOf(id));
            moneyfs1.put("gemsid", "0");
            moneyfs1.put("amount", array.get("addmoney"));
            moneyfs1.put("remark","人保后台-"+array.get("czremark"));
            moneyfs1.put("moneyid", String.valueOf(moneyfsid));
            moneyfs1.put("status","1");
            moneyfs1.put("bintype",array.get("bintype"));
            moneyfs1.put("fctype", array.get("fctype"));
            long moneyfs1id= Tools.recAdd(moneyfs1,"moneyfs_1");
            //退款 扣费
            if(array.get("fctype").equals("3")
                    || array.get("fctype").equals("4")){
                TtMap moneyfs2 = new TtMap();
                moneyfs2.put("mid", minfo.get("id"));
                moneyfs2.put("fsid", String.valueOf(id));
                moneyfs2.put("gemsid", "0");
                moneyfs2.put("amount", array.get("addmoney"));
                moneyfs2.put("remark","人保后台-"+array.get("czremark"));
                moneyfs2.put("moneyid", String.valueOf(moneyfsid));
                moneyfs2.put("status","1");
                moneyfs2.put("bc_type","0");
                moneyfs2.put("orderid", "0");
                moneyfs2.put("type","0");
                moneyfs2.put("bintype",array.get("bintype"));
                Tools.recAdd(moneyfs2,"moneyfs_2");
            }
        }
    }

    /**
     * @param {type} {type}
     * @说明: 给继承的子类重载用的
     * @return: 返回
     */
    public void doGetList(HttpServletRequest request, TtMap post) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", errorMsg);
            return;
        }
        TtMap minfo = Tools.minfo();//获取当前登录用户信息
        String kw = ""; // 搜索关键字
        String dtbe = ""; // 搜索日期选择
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        String whereString = "t.fs_type=2 and t.name!='' and t.deltag=0";
        String tmpWhere = "";
        String fieldsString = "t.*";
        // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;
        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            whereString += "AND name like '%" + kw + "%'";
        }
        if (Tools.myIsNull(dtbe) == false) {
            dtbe = dtbe.replace("%2f", "-").replace("+", "");
            String[] dtArr = dtbe.split("-");
            dtArr[0] = dtArr[0].trim();
            dtArr[1] = dtArr[1].trim();
            System.out.println("DTBE开始日期:" + dtArr[0] + "结束日期:" + dtArr[1]);
            // todo处理选择时间段
        }
        /* 搜索过来的字段处理完成 */
        whereString += tmpWhere; // 过滤
        orders = orderString;// 排序
        p = pageInt; // 显示页
        limit = limtInt; // 每页显示记录数
        showall = true; // 忽略deltag和showtag
        //leftsql = "LEFT JOIN assess_fs_details d ON d.fs_id=t.id" ;
        list = lists(whereString, fieldsString);

        if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
            for (TtMap info : list) {
                info.put("name",
                        info.get("name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
            }
        }
        request.setAttribute("list", list);// 列表list数据
        request.setAttribute("recs", recs); // 总记录数
        String htmlpages = getPage("", 0, false); // 分页html代码,
        request.setAttribute("pages", pages); // 总页数
        request.setAttribute("p", pageInt); // 当前页码
        request.setAttribute("l", limtInt); // limit量
        request.setAttribute("lsitTitleString", title); // 标题
        request.setAttribute("htmlpages", htmlpages); // 分页的html代码
        request.setAttribute("canDel", canDel); // 是否显示删除按钮
        request.setAttribute("canAdd", canAdd); // 是否显示新增按钮
        // request.setAttribute("showmsg", "测试弹出消息提示哈！"); //如果有showmsg字段，在载入列表前会提示
    }

    @Override
    public boolean chk(TtMap array, long id) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            return false;
        }
        if (!Tools.myIsNull(array.get("fromcommand"))) { // 从ManagerCmd来的。不用过滤参数

        } else {
            boolean bNew = id == 0;
            System.out.println("表单验证star");
            String myErroMsg = "";
            if (Tools.myIsNull(array.get("name"))) {
                myErroMsg = "公司名称不能为空！\n";
            } else {

                if (bNew) {// 新增用户
                    TtList fslist = Tools.reclist("select * from assess_fs where name='" + array.get("name") + "'");
                    if (fslist.size() > 0) {
                        myErroMsg = "公司名称不能重复！\n";
                    }
                }
            }
            super.errorMsg = super.chkMsg = myErroMsg;
            if (!Tools.myIsNull(myErroMsg)) {
                super.errorCode = 888;
            }

            System.out.println("表单验证end");
        }
        return super.errorCode == 0;
    }
}
