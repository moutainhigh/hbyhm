package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.manager.Modal;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
                Config.log.info("权限检查成功！");
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
        //String f = "*";
        //leftsql = "LEFT JOIN assess_fs_details d ON d.fs_id=t.id";
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        TtMap info = info(nid);
        String jsonInfo = Tools.jsonEncode(info);
        request.setAttribute("info", jsonInfo);//info为json后的info
        request.setAttribute("infodb", info);//infodb为TtMap的info
        request.setAttribute("id", nid);
        //request.setAttribute("saveButton", "true");
        Modal modalMenu = new Modal();
        request.setAttribute("modals", modalMenu.getAllModals()); // 后台左侧菜单,sidebar.jsp里面用到的菜

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
        if (Tools.myIsNull(wheres)) {
            wheres = (Tools.isSuperAdmin(minfo) || Tools.isCcAdmin(minfo)) ? "" : " id=" + minfo.get("icbc_erp_fsid")+" or up_id="+ minfo.get("icbc_erp_fsid"); // 只显示自己公司的
        } else {
            wheres += (Tools.isSuperAdmin(minfo) || Tools.isCcAdmin(minfo)) ? "" : " AND id=" + minfo.get("icbc_erp_fsid")+" or up_id="+ minfo.get("icbc_erp_fsid"); // 只显示自己公司的
        }

        TtList lmss = super.lists(wheres, f);
        for (TtMap tmpInfo : lmss) {
            tmpInfo.put("choice", Tools.dicopt("sys_dic_tag", Tools.strToLong(tmpInfo.get("showtag")))); // 显示/隐藏
            TtMap uermap=Tools.recinfo("select count(*) as usercount from assess_admin where icbc_erp_fsid="+tmpInfo.get("id"));
            tmpInfo.put("usercount",uermap.get("usercount")); // 显示/隐藏
        }
        return lmss;
    }
    /**
     * @param {type} {type}
     * @说明: 给子类重载用，处理post
     * @return: 返回
     */
    public void doPost(TtMap post, long id, TtMap result2) {
        if(post.get("oemimgurl").equals("images/mgcaraddimg.jpg")){
            post.put("oemimgurl","");
        }
        System.out.println("new Date().getTime()"+String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("create_time",String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("update_time",String.valueOf(Tools.getSecondTimestampTwo(new Date())));
        post.put("zone_id","0");
        post.put("support","");
        post.put("deltag","0");
        post.put("mgicbc_tag","0");
        if(post.get("mg_tag").equals("0")){
            post.put("purview_map_kjs","");
        }else{
            post.put("purview_map_kjs",post.get("purview_map_kjs"));
        }
        if(Tools.myIsNull(post.get("purview_map"))){
            post.put("purview_map","order_ks,order_zy,order_query_da,order_query_by,order_query_zx,order_query_bdzx,order_query_thjl,order_query_bx,order_query_wdhmd,order_query_yhksm,order_query_yhkls,kj_zxjb,order_kj_qcyz,");
        }else{
            post.put("purview_map",post.get("purview_map"));
        }
        post.put("up_id",Tools.minfo().get("icbc_erp_fsid"));
        post.put("icbc_erp_tag","1");
        post.put("fs_type","2");
        TtMap newpost=new TtMap();
        newpost.putAll(post);
        long icbc_id = 0;
        //imgs(post);
        System.out.println("post:"+post.get("purview_map")+"----------"+post.get("purview_map_kjs"));
        if (id > 0) { // id为0时，新增
            edit(post, id);
        } else {
            long fsid=add(post);
            TtList apglist=Tools.reclist("select * from icbc_admin_agp where showtag=1 and fsid=0 and systag=1");
            for(TtMap apg : apglist){
                TtMap apgmap=new TtMap();
                apgmap.put("name",apg.get("name"));
                apgmap.put("purview_map",apg.get("purview_map"));
                apgmap.put("showtag","1");
                apgmap.put("fsid",String.valueOf(fsid));
                apgmap.put("systag","0");
                Tools.recAdd(apgmap,"icbc_admin_agp");
            }
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
        String whereString ="t.fs_type=2";
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


}
