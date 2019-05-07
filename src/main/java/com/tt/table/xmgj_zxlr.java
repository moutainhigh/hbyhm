package com.tt.table;



import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class xmgj_zxlr extends DbCtrl {
    private final String title = "征信录入";
    private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = true;
    private boolean canAdd = true;
    private final String classAgpId = "68"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public xmgj_zxlr() {
        super("kj_icbc");
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

    @Override
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

        int appid=3;
        if(post.get("appid")!=null&&!post.get("appid").isEmpty()){
            appid=Integer.parseInt(post.get("appid")) ;
        }
        String whereString = "t.app="+appid;
        String tmpWhere = "";
        String fieldsString = "t.*" +
                ",f.name as fsname" +
                ",a.name as adminname" +
                ",dy.id as dy_id" +
                ",dy.bc_status as dy_bc_status" +
                ",qy.bc_status as qy_bc_status" +
                ",qy.qy_status as qy_qy_status";
        // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;

        if (Tools.isSuperAdmin(minfo) || Tools.isCcAdmin(minfo)) {
            TtList fslist = Tools.reclist("select id,up_id from assess_fs where id=" + minfo.get("icbc_erp_fsid") + " or up_id=" + minfo.get("icbc_erp_fsid"));
            String sql="";
            //whereString += " AND ("; // 显示自己和下级公司的
            if (fslist.size() > 0) {
                for (int l=0;l<fslist.size();l++) {
                    TtMap fs=fslist.get(l);
                    if(l==fslist.size()-1) {
                        sql = sql + fs.get("id");
                    }else{
                        sql = sql + fs.get("id")+",";
                    }
                }
            }
            whereString += " and t.gems_fs_id in ("+sql+")";
        } else {
            whereString += " AND t.gems_fs_id=" + minfo.get("icbc_erp_fsid"); // 只显示自己公司的
        }

        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            whereString += " AND c_name like '%" + kw + "%'";
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
        leftsql = " LEFT JOIN assess_fs f ON f.id=t.gems_fs_id " +
                " LEFT JOIN assess_gems a ON a.id=t.gems_id" +
                " LEFT JOIN xmgj_dygd dy ON dy.icbc_id=t.id" +
                " LEFT JOIN tlzf_qy qy ON qy.icbc_id=t.id";
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

    //图片处理
    public TtMap tozip(String imgs,String imgsname){
        TtMap imginfo = new TtMap();
        if(!Tools.myIsNull(imgs)){
            String[] imgstep2_1ss=imgs.split("\u0005");
            for(int i=0;i<imgstep2_1ss.length;i++){
                if(!Tools.myIsNull(imgstep2_1ss[i])){
                    imginfo.put(imgsname+(i+1),imgstep2_1ss[i]);
                }
            }
        }
        return imginfo;
    }

    @Override
    public void doGetForm(HttpServletRequest request, TtMap post) {
        String f = "t.*,a.name as admin_name,fs.name as fs_name";
        leftsql = " LEFT JOIN assess_gems a ON a.id=t.gems_id" +
                " LEFT JOIN assess_fs fs ON fs.id=t.gems_fs_id";
        long nid = Tools.myIsNull(post.get("id"))?0:Tools.strToLong(post.get("id"));
        TtMap info = info(nid,f);
        String jsonInfo = Tools.jsonEncode(info);
        if(!Tools.myIsNull(post.get("toZip"))&& post.get("toZip").equals("1")) {
            TtMap imginfo = new TtMap();
            //征信录入资料
            TtMap imginfo1=tozip(info.get("imgstep2_1ss"),"主贷人照片");
            TtMap imginfo2=tozip(info.get("imgstep2_2ss"),"主贷人配偶照片");
            TtMap imginfo3=tozip(info.get("imgstep2_3ss"),"共还人1照片");
            TtMap imginfo4=tozip(info.get("imgstep2_4ss"),"共还人2照片");
            imginfo.putAll(imginfo1);
            imginfo.putAll(imginfo2);
            imginfo.putAll(imginfo3);
            imginfo.putAll(imginfo4);
            try {
                if (!Zip.imgsToZipDown(imginfo, title + ".zip", null)) {
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
        }else{
            //历史操作查询
            if(nid>0) {
                TtList lslist = Tools.reclist("select * from kj_icbc_result where qryid=" + nid);
                request.setAttribute("lslist", lslist);//
            }
            request.setAttribute("info", jsonInfo);//info为json后的info
            request.setAttribute("infodb", info);//infodb为TtMap的info
            request.setAttribute("id", nid);
        }
    }


    /**
     * post 其他操作 图片 字段替换 添加
     */
    public void addicbc_erp_zx(TtMap post) {
        TtMap minfo = Tools.minfo();

        //图片路径存放操作
        String imgstep1_1ss = "";
        String imgstep1_2ss = "";
        String imgstep1_3ss = "";
        String imgstep1_4ss = "";
        String imgstep1_5ss = "";
        for (int i = 1; i <= 4; i++) {
            imgstep1_1ss = imgstep1_1ss + post.get("imgstep2_1ss" + i) + "\u0005";
            imgstep1_2ss = imgstep1_2ss + post.get("imgstep2_2ss" + i) + "\u0005";
            imgstep1_3ss = imgstep1_3ss + post.get("imgstep2_3ss" + i) + "\u0005";
            imgstep1_4ss = imgstep1_4ss + post.get("imgstep2_4ss" + i) + "\u0005";
            //imgstep1_5ss = imgstep1_5ss + post.get("imgstep1_5ss" + i) + ",";
        }
        post.put("imgstep2_1ss", imgstep1_1ss);
        post.put("imgstep2_2ss", imgstep1_2ss);
        post.put("imgstep2_3ss", imgstep1_3ss);
        post.put("imgstep2_4ss", imgstep1_4ss);
    }

    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        TtMap newpost=  new TtMap();
        newpost.putAll(post);
        TtMap minfo = Tools.minfo();
        //addicbc_erp_zx(post);
        long icbc_id=0;
        if (id > 0) { // id为0时，新增
            edit(post, id);
            icbc_id=id;
        } else {
            post.put("app","3");
            post.put("gems_id",minfo.get("id"));
            post.put("gems_fs_id",minfo.get("icbc_erp_fsid"));
            post.put("gems_code","0");
            post.put("query_type","0");
            icbc_id= add(post);
            TtMap ordermap=new TtMap();
            ordermap.put("gems_code",orderutil.getOrderId("XMGJKCD", 7, icbc_id));
            //更新订单字段
            Tools.recEdit(ordermap,"kj_icbc",icbc_id);
        }
        //历史添加
        TtMap res=new TtMap();
        res.put("qryid",String.valueOf(icbc_id));
        res.put("status",post.get("bc_status"));
        res.put("remark",newpost.get("remark"));
        Tools.recAdd(res,"kj_icbc_result");

        Addadmin_msg.addmsg(minfo.get("gemsid"), post.get("bc_status"), newpost.get("remark"));

        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑"+title+"成功！" : errorMsg,
                bSuccess ? nextUrl : "");//失败时停留在当前页面,nextUrl为空
    }
}
