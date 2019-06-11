/*
 * @Description: 单独的管理员管理类演示，继承自DbCtrl类
 * @Author: tt
 * @Date: 2019-01-26 11:35:00
 * @LastEditTime: 2019-03-21 11:40:20
 * @LastEditors: tt
 */
package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Excel;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.tt.tool.Tools;
import com.tt.tool.Zip;

public class Admin extends DbCtrl {
    private final String title = "用户管理";
    private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = true;
    private final String classAgpId = "46"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public Admin() {
        super("assess_admin");
        AdminAgp adminAgp = new AdminAgp();
        try {
            if (adminAgp.checkAgp(classAgpId)) { // 如果有权限
                Tools.logInfo("权限检查成功！", getClass().getName());
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
     * @param {type}
     * @description: 重载lists方法，这里可以处理一些lists前的逻辑
     * @return:
     */
    @Override
    public TtList lists(String wheres, String f) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            return null;
        }
        TtList lmss = super.lists(wheres, f);
        for (TtMap tmpInfo : lmss) {
            tmpInfo.put("fsname", Tools.unDic("assess_fs", Tools.strToLong(tmpInfo.get("icbc_erp_fsid"))));// 所属公司
            tmpInfo.put("choice", Tools.dicopt("sys_dic_tag", Tools.strToLong(tmpInfo.get("showtag")))); // 显示/隐藏
        }
        return lmss;
    }

    /**
     * @param {type}
     * @description: 重载info方法，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public TtMap info(long id, String f) {
        return super.info(id, f);
    }

    /**
     * @param {type}
     * @description: 重载edit方法，可以在这里处理一些edit逻辑，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public int edit(TtMap ary, long id) {
        return super.edit(ary, id);
    }

    /**
     * @param {type}
     * @description: 重载add方法，可以在这里添加一些添加数据时的逻辑，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public long add(TtMap ary) {
        return super.add(ary);
    }

    /* 重载dbCtrl的方法，处理info()前的数据的过滤和添加处理 */
    @Override
    public TtMap show(TtMap rec) {
        if (!Tools.myIsNull(rec.get("userpass"))) { // info()时，把密码项目清空
            rec.put("userpass", "");
        }
        return rec;
    }

    /**
     * @param {type}
     * @description: 重载closeConn方法，可以在关闭数据库前处理一些逻辑，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public void closeConn() {
        super.closeConn();
    }

    /**
     * @param {type}
     * @description: 重载的param方法，此方法在保存数据前调用！就是Add和Edit前都会调用这个方法，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public TtMap param(TtMap ary, long id) {
        if (!Tools.myIsNull(ary.get("password"))) { // 密码处理
            ary.put("userpass", Tools.md5(Tools.md5(ary.get("password"))));
        } else {
            ary.remove("userpass");
        }
        return ary;
    }

    /**
     * @param {type}
     * @description: 处理后台的get, 演示独立类处理sdo=form的get，如果不需要重载，整个方法可以删掉
     * @return:
     */
    @Override
    public void doGetForm(HttpServletRequest request, TtMap post) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", "权限访问错误！");
            return;
        }
        TtMap minfo = Tools.minfo();
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        String f = "t.*,g.idcard as idcard";
        leftsql = " LEFT JOIN assess_gems g ON g.id=t.gemsid";
        TtMap info = info(nid, f);
        String fssql = "";
        int fsid = 0;
        int up_id = 0;
        if (nid > 0) {
            fsid = Integer.parseInt(info.get("icbc_erp_fsid"));
            up_id = Integer.parseInt(info.get("icbc_erp_fsid"));
        } else {
            fsid = Integer.parseInt(minfo.get("icbc_erp_fsid"));
            up_id = Integer.parseInt(minfo.get("icbc_erp_fsid"));
        }
        switch (minfo.get("superadmin")) {
            case "0":
                fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' where id="+fsid;
                break;
            case "1":
                fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!=''";
                break;
            case "2":
                fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id=" + fsid + " or up_id=" + up_id + ")";
                break;
            case "3":
                fssql = "select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id in (" + Tools.getfsids(fsid) + ")";
                break;
            default:

                break;
        }
        TtList fslist = Tools.reclist(fssql);
        request.setAttribute("fslist", fslist);
        String jsonInfo = Tools.jsonEncode(info);
        request.setAttribute("info", jsonInfo);// info为json后的info
        request.setAttribute("infodb", info);// infodb为TtMap的info
        request.setAttribute("id", nid);
    }

    /**
     * @param {type}
     * @description: 处理后台的get, 演示独立类处理sdo=list的get
     * @return:
     */
    @Override
    public void doGetList(HttpServletRequest request, TtMap post) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", errorMsg);
            return;
        }
        TtMap minfo = Tools.minfo();
        String kw = ""; // 搜索关键字
        String dtbe = ""; // 搜索日期选择
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        String whereString = "true";
        String tmpWhere = "";
        String fieldsString = ""; // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;
        String fsid = post.get("fsid");
        if (Tools.myIsNull(fsid)) {
            String fsids="";
            TtList fslist=new TtList();
            switch (minfo.get("superadmin")) {
                case "0":
                    fslist = Tools.reclist("select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id="+minfo.get("icbc_erp_fsid"));
                    break;
                case "1":
                    fslist = Tools.reclist("select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!=''");
                    break;
                case "2":
                    fslist = Tools.reclist("select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id=" + minfo.get("icbc_erp_fsid") + " or up_id=" + minfo.get("icbc_erp_fsid") + ")");
                    break;
                case "3":
                    fslist = Tools.reclist("select * from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id in (" + Tools.getfsids(Integer.parseInt(minfo.get("icbc_erp_fsid"))) + ")");
                    break;
                default:

                    break;
            }
            if (fslist.size() > 0) {
                for (int l = 0; l < fslist.size(); l++) {
                    TtMap fs = fslist.get(l);
                    if (l == fslist.size() - 1) {
                        fsids = fsids + fs.get("id");
                    } else {
                        fsids = fsids + fs.get("id") + ",";
                    }
                }
            }
            whereString += " and t.icbc_erp_fsid in (" + fsids + ")";
        } else {
            whereString += " AND t.icbc_erp_fsid=" + fsid;
        }
        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            whereString += " AND t.name like '%" + kw + "%'";
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

        // 导出到Excel处理
        boolean bToExcel = false, toZip = false;
        if (!Tools.myIsNull(post.get("toExcel")) && post.get("toExcel").equals("1")) {// 导出excel时设置不分页，导出所有
            nopage = true;
            bToExcel = true;
        }
        if (!Tools.myIsNull(post.get("toZip")) && post.get("toZip").equals("1")) {// 导出excel时设置不分页，导出所有
            nopage = true;
            toZip = true;
        }
        whereString += tmpWhere; // 过滤
        orders = orderString;// 排序
        p = pageInt; // 显示页
        limit = limtInt; // 每页显示记录数
        showall = true; // 忽略deltag和showtag
        leftsql = "LEFT JOIN assess_gems g ON g.id=t.gemsid";
        list = lists(whereString, fieldsString);

        if (bToExcel) { // Excel导出演示：导出到Excel并下载
            String[] headers = new String[]{"管理员名称", "密码MD5", "用户名"};
            String[] fields = new String[]{"name", "password", "username"};
            String toFile = Config.FILEUP_SAVEPATH + "excel/" + title + ".xlsx";
            closeConn();// 因为要跳到下载，所以要提前closeConn
            if (!Excel.doOut(list, headers, fields, toFile, "excel2007", true)) {
                errorMsg = "导出Excel失败";
                request.setAttribute("errorMsg", errorMsg);
            }
        } else if (toZip) { // ZIP打包演示：打包头像图片到zip并下载
            TtMap info = new TtMap();
            for (TtMap mss : list) {
                if (!Tools.myIsNull(mss.get("avatarurl"))) {
                    info.put(mss.get("name"), mss.get("avatarurl"));
                }
            }
            try {
                closeConn();// 因为要跳到下载，所以要提前closeConn
                if (!Zip.imgsToZipDown(info, title + ".zip", null)) {
                    errorMsg = "导出ZIP失败!";
                    request.setAttribute("errorMsg", errorMsg);
                }
            } catch (IOException e) {

                errorMsg = "导出ZIP失败:" + e.getMessage();
                request.setAttribute("errorMsg", errorMsg);
                if (Config.DEBUGMODE) {
                    e.printStackTrace();
                }
            }
        } else {
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
        }
        // request.setAttribute("showmsg", "测试弹出消息提示哈！"); //如果有showmsg字段，在载入列表前会提示
    }

    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        if (post.get("imgurl").equals("images/mgcaraddimg.jpg")) {
            post.put("imgurl", "");
        }
        if (id > 0) { // id为0时，新增
            edit(post, id);
        } else {
            post.put("icbc_erp_tag", "1");
            post.put("loginip", "0");
            post.put("fs_type", "2");
            post.put("gemsid", "0");
            post.put("isadmin", "1");
            long adminid = add(post);
        }
        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg, bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
    }

    // 操作成功会执行这个方法
    @Override
    public void succ(TtMap array, long id, int sqltp) {
        System.out.println("aarrr: " + array);
        System.out.println("id: " + id);
        System.out.println("dopost方法执行成功进入srcc");
        TtMap gems = new TtMap();
        gems.put("name", array.get("name"));
        gems.put("fsid", array.get("icbc_erp_fsid"));
        gems.put("update_time", String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        gems.put("showtag", array.get("showtag"));
        gems.put("mobile", array.get("tel"));
        gems.put("cp", array.get("cp"));
        gems.put("username", array.get("username"));
        gems.put("idcard", array.get("idcard"));
        gems.put("upac_id", array.get("upac_id"));
        if (array.get("imgurl").equals("images/mgcaraddimg.jpg")) {
            gems.put("imgurl", "");
            array.put("imgurl", "");
        } else {
            gems.put("imgurl", array.get("imgurl"));
        }

        TtMap recinfo = Tools.recinfo("select * from assess_gems where mem_id=" + id);
        if (recinfo.size() > 0) {
            System.out.println("修改用户");
            Tools.recEdit(gems, "assess_gems", Long.valueOf(array.get("gemsid")));
            TtMap map = new TtMap();
            map.put("gemsid", String.valueOf(recinfo.get("id")));
            Tools.recEdit(map, "assess_admin", id);
        } else {
            System.out.println("新增用户");
            gems.put("deltag", "0");
            gems.put("aid_ssm", "0");
            gems.put("hometag", "0");
            gems.put("appkey", "0");
            gems.put("usedays", "0");
            gems.put("ssbm", "0");
            gems.put("moneycan_tag", "0");
            gems.put("purview_map", "");
            gems.put("aid", "0");
            gems.put("fs_type", "2");
            gems.put("create_time", String.valueOf(Tools.getSecondTimestampTwo(new Date())));
            gems.put("mem_id", String.valueOf(id));
            long gemsid = Tools.recAdd(gems, "assess_gems");
            TtMap map = new TtMap();
            map.put("gemsid", String.valueOf(gemsid));
            Tools.recEdit(map, "assess_admin", id);
        }
    }

    /**
     * @param {type}
     * @description: 重载chk方法，就是数据写入前的一些判断逻辑可以在这里完成，如果返回false，将放弃数据写入操作
     * @return:
     */
    @Override
    public boolean chk(TtMap array, long id) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            return false;
        }
        if (!Tools.myIsNull(array.get("fromcommand"))) { // 从ManagerCmd来的。不用过滤参数
        } else {
            System.out.println("表单验证star");
            String myErroMsg = "";
            String userName = Tools.myIsNull(array.get("username")) ? "" : array.get("username").trim();// 去掉用户名前后空格;
            boolean bNew = id == 0;
            if (Tools.myIsNull(userName)) {
                myErroMsg = "您貌似忘记输入用户名了！\n";
                System.out.println(myErroMsg);
            } else {
                if (bNew) {// 新增用户
                    String sql = "select id from " + table + " where username='" + array.get("username") + "'";
                    TtList adminlist = Tools.reclist(sql);
                    if (adminlist != null && adminlist.size() > 0) {
                        myErroMsg = "已经存在此用户：" + array.get("username") + "\n";
                    }
                }
                System.out.println(myErroMsg);
            }

            if (Tools.myIsNull(array.get("name"))) {
                myErroMsg += "您忘记输入用户姓名啦！\n";
            }
            if (array.get("icbc_erp_fsid").equals("0")) {
                myErroMsg += "您忘记选择给哪个公司开户啦！\n";
            }
            if (array.get("agpid").equals("0")) {
                myErroMsg += "您忘记选择用户所属角色啦！\n";
            }
            if (bNew) {// 新增用户，需要密码为空验证
                if (Tools.myIsNull(array.get("password"))) {
                    myErroMsg += "您忘记输入用户密码啦！！\n";
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

    /**
     * 演示在类里添加其他功能方法。
     *
     * @param {type} {type}
     * @return: 返回
     */
    public String doOther() {
        String r = null;
        // 演示其他功能。自定义操作方法。
        return r;
    }
}