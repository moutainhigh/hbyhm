package com.tt.table;

import com.tt.api.Jdpush;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Addadmin_msg;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class hxyh_yhclhs extends DbCtrl {
    private final String title = "征信授权书回收";
    private String orderString = "ORDER BY dt_edit DESC"; // 默认排序
    private boolean canDel = true;
    private boolean canAdd = true;
    private final String classAgpId = "64"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限
    public hxyh_yhclhs() {
        super("hxyh_yhclhs");
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
        String f = "t.*,a.name as admin_name,fs.name as fs_name,i.c_name as c_name,i.id as icbc_id";
        leftsql = " LEFT JOIN assess_gems a ON a.id=t.gems_id" +
                " LEFT JOIN assess_fs fs ON fs.id=t.gems_fs_id" +
                " LEFT JOIN kj_icbc i ON i.id=t.icbc_id";
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        TtMap info = info(nid, f);
        String jsonInfo = Tools.jsonEncode(info);
        TtMap minfo = Tools.minfo();
        TtMap assess_admin = Tools.recinfo("select * from assess_admin where id =" + info.get("current_editor_id"));
        System.out.println("当前操作人信息：" + assess_admin);

        //历史操作查询
        if (nid > 0) {
            TtList lslist = Tools.reclist("select * from hxyh_yhclhs_result where qryid=" + nid);
            request.setAttribute("lslist", lslist);//
        }
        long app=4;
        if(post.get("app")!=null&&!post.get("app").isEmpty()){
            app=Integer.valueOf(post.get("app"));
        }

        //查询主订单客户
        TtList icbclist=Tools.reclist("select * from kj_icbc where app="+app);
        request.setAttribute("icbclist", icbclist);

        TtMap map = new TtMap();
        map.put("current_editor_id", minfo.get("id"));
        Tools.recEdit(map, "hxyh_yhclhs", nid);

        request.setAttribute("info", jsonInfo);//info为json后的info
        request.setAttribute("infodb", info);//infodb为TtMap的info
        request.setAttribute("id", nid);
        request.setAttribute("assess_admin", assess_admin);
    }

    public void imgs(TtMap post){
        String imgstep13_1ss="";
        if(!post.get("imgstep13_1ss_num").isEmpty()&&!post.get("imgstep13_1ss_num").equals("")){
            int imgstep13_1ss_num=Integer.parseInt(post.get("imgstep13_1ss_num"));
            for(int i=1;i<=imgstep13_1ss_num;i++){
                imgstep13_1ss=imgstep13_1ss+post.get("imgstep13_1ss"+i)+"\u0005";
            }
        }
        post.put("imgstep13_1ss",imgstep13_1ss);

    }

    /**
     * @param {type} {type}
     * @说明: 给子类重载用，处理post
     * @return: 返回
     */
    public void doPost(TtMap post, long id, TtMap result2) {
        TtMap newpost=new TtMap();
        newpost.putAll(post);
        long icbc_id = 0;
        //imgs(post);
        if (id > 0) { // id为0时，新增
            edit(post, id);
            icbc_id = id;
        } else {
            icbc_id = add(post);
            TtMap map=new TtMap();
            //订单编号更新操作
            map.put("gems_code",orderutil.getOrderId("HSKCD", 7, icbc_id));
            edit(map,icbc_id);
        }
        //历史添加
        TtMap res = new TtMap();
        res.put("qryid", String.valueOf(icbc_id));
        res.put("status", newpost.get("bc_status"));
        res.put("remark", newpost.get("remark1"));
        Tools.recAdd(res, "hxyh_yhclhs_result");

        String sql = "select c_name from kj_icbc where id=" + newpost.get("icbc_id");
        TtMap recinfo = Tools.recinfo(sql);

        if(StringUtils.isNotEmpty(newpost.get("mid_add")) && newpost.get("mid_add").equals(newpost.get("mid_edit"))){
            Addadmin_msg.addmsg(newpost.get("mid_edit"), newpost.get("bc_status"), newpost.get("remark1"), recinfo.get("c_name"), "征信授权书", "华夏银行", newpost.get("mid_add"));

        } else {
            Addadmin_msg.addmsg(newpost.get("mid_add"), newpost.get("bc_status"), newpost.get("remark1"), recinfo.get("c_name"),"征信授权书","华夏银行", newpost.get("mid_add"));
            Addadmin_msg.addmsg(newpost.get("mid_edit"), newpost.get("bc_status"), newpost.get("remark1"), recinfo.get("c_name"), "征信授权书", "华夏银行", newpost.get("mid_add"));

        }

        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg, bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
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

        String whereString ="true";;
        String tmpWhere = "";
        String fieldsString = "t.*,f.name as fsname" +
                ",f.id as fsid" +
                ",cs.name as state_name" +
                ",cc.name as city_name" +
                ",a.name as adminname,i.c_name as c_name,aa.name as aa_name"+",i.checkname as checkname";
        // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;

        //根据权限获取公司id
        String fsids = "";
        TtList fslist = new TtList();
        switch (minfo.get("superadmin")) {
            case "0":
                fslist = Tools.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id=" + minfo.get("icbc_erp_fsid"));
                break;
            case "1":
                fslist = Tools.reclist("select id from assess_fs where deltag=0 and showtag=1 and name!=''");
                break;
            case "2":
                fslist = Tools.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and (id=" + minfo.get("icbc_erp_fsid") + " or up_id=" + minfo.get("icbc_erp_fsid") + ")");
                break;
            case "3":
                fslist = Tools.reclist("select id from assess_fs where fs_type=2 and deltag=0 and showtag=1 and name!='' and id in (" + Tools.getfsids(Integer.parseInt(minfo.get("icbc_erp_fsid"))) + ")");
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
        whereString += " AND t.gems_fs_id in (" + fsids + ")";

        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        int search_status = Tools.strToInt(post.get("search_status"));
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            /* 模糊搜索开始 */
            String [] ssKewords =new String []{"i.c_name","i.c_tel","i.c_cardno","i.c_name_mt","i.c_tel_mt","i.c_cardno_mt",};
            whereString += " AND ("+ssKewords[0]+" like '%" + kw + "%'";
            for (String tmpstr :ssKewords){
                whereString +=" || "+tmpstr+" like '%" + kw + "%'";
            }
            whereString +=")";
        }
        if (search_status>0){
            whereString += " AND t.bc_status="+search_status;
        }
        if (Tools.myIsNull(dtbe) == false) {
            dtbe = dtbe.replace("%2f", "-").replace("+", "");
            //System.out.println("dtbe:"+dtbe);
            String[] dtArr = dtbe.split(" - ");
            //dtArr[0] = dtArr[0].trim();
            //dtArr[1] = dtArr[1].trim();
            System.out.println("DTBE开始日期:" + dtArr[0] + "结束日期:" + dtArr[1]);
            // todo处理选择时间段
            whereString += " AND t.dt_add >='" + dtArr[0] +"' and t.dt_add <='"+dtArr[1]+"'";
        }
        if (!Tools.myIsNull(post.get("stateid"))) {
            whereString += " AND admin.stateid=" + post.get("stateid");
        }
        if (!Tools.myIsNull(post.get("cityid"))) {
            whereString += " AND admin.cityid=" + post.get("cityid");
        }
        if (!Tools.myIsNull(post.get("fsid"))) {
            whereString += " AND f.id=" + post.get("fsid");
        }
        if (!Tools.myIsNull(post.get("search_cityid"))) {
            whereString += " AND admin.cityid=" + post.get("search_cityid");
        }
        if (!Tools.myIsNull(post.get("saerch_fsid"))) {
            whereString += " AND f.id=" + post.get("saerch_fsid");
        }
        /* 搜索过来的字段处理完成 */


        whereString += tmpWhere; // 过滤
        orders = orderString;// 排序
        p = pageInt; // 显示页
        limit = limtInt; // 每页显示记录数
        showall = true; // 忽略deltag和showtag
        leftsql = "LEFT JOIN assess_fs f ON f.id=t.gems_fs_id " +
                "LEFT JOIN assess_gems a ON a.id=t.gems_id " +
                " LEFT JOIN assess_admin admin ON admin.gemsid=a.id" +
                " LEFT JOIN comm_states cs ON cs.id=admin.stateid" +
                " LEFT JOIN comm_citys cc ON cc.id=admin.cityid" +

                " LEFT JOIN kj_icbc i ON i.id=t.icbc_id " +
                "LEFT JOIN assess_admin aa ON aa.id=t.current_editor_id";
        list = lists(whereString, fieldsString);

        if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
            for (TtMap info : list) {
                info.put("c_name",
                        info.get("c_name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
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



}
