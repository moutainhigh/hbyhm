package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;

public class xmloan_hxgl extends DbCtrl {

    private final String title = "核销管理";
    private String orderString = "ORDER BY dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = false;
    private final String classAgpId = "95"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public xmloan_hxgl(){
        super("xmloan_overdue_list");

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

    @Override
    public void doGetList(HttpServletRequest request, TtMap post) {
        System.out.println("查询list!!!!!!!!!");
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", errorMsg);
            return;
        }
        String kw = ""; // 搜索关键字
        String dtbe = ""; // 搜索日期选择
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        String whereString = "true";
        String tmpWhere = " and t.type_id = 7";
        System.out.println("............."+post.get("hxtype"));
        if ("1".equals(post.get("hxtype"))){     //未核销
            tmpWhere = " and t.type_id = 7 and t.type_status = 71";
        }
        if ("2".equals(post.get("hxtype"))){     //已核销
            tmpWhere = " and t.type_id = 7 and t.type_status = 72";
        }


        String fieldsString = "t.*,k.carno,c.bank_id,c.loan_tpid,c.gems_code,c.c_name,c.c_cardno,f.`name` fs_name,g.`name` gems_name"; // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;
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

        System.out.println("查询list");

        whereString += tmpWhere; // 过滤
        orders = orderString;// 排序
        p = pageInt; // 显示页
        limit = limtInt; // 每页显示记录数
        showall = true; // 忽略deltag和showtag
        leftsql= "LEFT JOIN kj_icbc c on c.id=t.icbc_id " +
                "LEFT JOIN xmgj_xxzl k on k.icbc_id=t.icbc_id " +
                "LEFT JOIN assess_fs f on f.id=t.gems_fs_id " +
                "LEFT JOIN assess_gems g on g.id=t.gems_id ";

        list = lists(whereString, fieldsString);
        System.out.println("list::::++  "+list);
        if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
            for (TtMap info : list) {
                info.put("c_name", info.get("c_name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
            }
        }
        System.out.println("li::::  "+list);
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
        System.out.println("pppp"+post);
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));

        String bbsql = "select * from xmloan_overdue_list where id = " + nid;
        TtMap bbmap = Tools.recinfo(bbsql);

        //客户信息
        String sql = "SELECT SQL_CALC_FOUND_ROWS\n" +
                "\tt.*,\n" +
                "\tk.*, \n" +
                "\tcb.name as cbname, \n" +
                "\tcm.name as cmname\n" +
                "\tFROM\n" +
                "\tkj_icbc t\n" +
                "\tLEFT JOIN xmgj_xxzl k ON k.icbc_id = t.id\n" +
                "\tLEFT JOIN car_brand cb ON cb.id = k.brid_v2\n" +
                "\tLEFT JOIN car_model cm ON cm.id = k.carid_v2\n" +
                "\tWHERE\n" +
                "\tt.id = " + bbmap.get("icbc_id");
        TtMap map = Tools.recinfo(sql);

        //还款计划
        String hkjhsql = "SELECT * FROM xmloan_repayment_schedule WHERE icbc_id = " + bbmap.get("icbc_id");
        TtList reclist = Tools.reclist(hkjhsql);

        //贷后信息
        String dhsql = "select k.*,a.`name` gems_name,f.`name` fs_name from xmgj_xxzl k left join assess_gems a on a.id=k.gems_id left join assess_fs f on f.id=k.gems_fs_id where k.icbc_id = " + bbmap.get("icbc_id");
        TtMap mapafter = Tools.recinfo(dhsql);

        //记录栏
        String jlsql = "select lo.*,a.`name` gems_name from xmloan_overdue_list_result lo left join assess_gems a on a.id = lo.mid_add where lo.icbc_id = " + bbmap.get("icbc_id");
        TtList jllist = Tools.reclist(jlsql);

        System.out.println("jjjjjjj" + jllist);
        System.out.println("主贷人信息:"+map);
        String jsonInfo = Tools.jsonEncode(map);
        request.setAttribute("info", jsonInfo);//info为json后的info
        request.setAttribute("infodb", map);//infodb为TtMap的info
        request.setAttribute("hkjh", reclist);
        request.setAttribute("mapafter", mapafter);
        request.setAttribute("bbmap",bbmap);
        request.setAttribute("jllist", jllist);
        request.setAttribute("hxtype", post.get("hxtype"));
        request.setAttribute("id", nid);
    }

    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        super.doPost(post, id, result2);
    }
}
