package com.tt.table;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

/**
 * 银行签约
 */
public class yhqy extends DbCtrl {
    private final String title = "通联绑卡审核";
    private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = true;
    private final String classAgpId = "56"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限
    public yhqy() {
        super("tlzf_qy");
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

        String whereString = "true";;
        String tmpWhere = "";
        String fieldsString = "t.*,f.name as fsname,a.name as adminname";
        // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;

        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            whereString += "AND account_name like '%" + kw + "%'";
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
        leftsql = "LEFT JOIN assess_fs f ON f.id=t.gems_fs_id " +
                "LEFT JOIN assess_gems a ON a.id=t.gems_id";
        list = lists(whereString, fieldsString);

        if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
            for (TtMap info : list) {
                info.put("account_name",
                        info.get("account_name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
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
    public void doGetForm(HttpServletRequest request, TtMap post) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", "权限访问错误！");
            return;
        }
        String wsql="";
        if(!Tools.myIsNull(post.get("app"))){
            switch (post.get("app")) {
                case "2":
                    wsql=" and app=2";
                    break;
                case "3":
                    wsql=" and app=3";
                    break;
                case "4":
                    wsql=" and app=4";
                    break;
            }
        }
        //查询主订单客户
        TtList icbclist = Tools.reclist("select * from kj_icbc where true "+wsql);
        request.setAttribute("icbclist", icbclist);
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        TtMap info = info(nid);

        if(nid>0) {
            TtList resultlist = Tools.reclist("select * from tlzf_qy_result where qryid=" + nid);
            request.setAttribute("resultlist", resultlist);
        }
        String jsonInfo = Tools.jsonEncode(info);
        request.setAttribute("info", jsonInfo);// info为json后的info
        request.setAttribute("infodb", info);// infodb为TtMap的info
        request.setAttribute("id", nid);
    }

    public void doPost(TtMap post, long id, TtMap result2) {
        TtMap newpost=new TtMap();
        newpost.putAll(post);
        TtMap mmap=Tools.minfo();
        if (id > 0) { // id为0时，新增
            edit(post, id);
        } else {
            post.put("qd_type","2");
            post.put("gems_id",mmap.get("gemsid"));
            post.put("gems_fs_id",mmap.get("icbc_erp_fsid"));
            add(post);
        }
        //添加记录
        TtMap resmap=new TtMap();
        resmap.put("qryid",String.valueOf(id));
        resmap.put("status",newpost.get("bc_status"));
        resmap.put("remark",newpost.get("remark1"));
        Tools.recAdd(resmap,"tlzf_qy_result");

        String nextUrl = Tools.urlKill("sdo") + "&sdo=list";
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "编辑成功！" : errorMsg, bSuccess ? nextUrl : "");// 失败时停留在当前页面,nextUrl为空
    }

    /**
     * 添加/编辑/删除成功一条记录会执行此方法，子类重载用。sqltp：0-添加，1：编辑，-1：删除
     *
     * @param {type} {type}
     * @return: 返回
     */
    @Override
    public void succ(TtMap array, long id, int sqltp) {
        System.out.println("新增后的数据展示："+array);
        TtMap ttMap=new TtMap();
        ttMap.put("gems_code",orderutil.getOrderId("TLQYKJY", 8, id));
        Tools.recEdit(ttMap,"tlzf_qy",id);

    }
}
