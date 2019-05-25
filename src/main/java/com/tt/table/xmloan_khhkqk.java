package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class xmloan_khhkqk extends DbCtrl {

    private final String title = "客户还款情况";
    private String orderString = "GROUP BY icbc_id ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = false;
    private final String classAgpId = "89"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public xmloan_khhkqk() {
        super("tlzf_dk_details");

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
        System.out.println("查询list!!!!!!!!!");
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            request.setAttribute("errorMsg", errorMsg);
            return;
        }
        String kw = ""; // 搜索关键字
        String dtbe = ""; // 搜索日期选择
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        String whereString = "t.qd_type=3";   // 2=河北银行   3=厦门银行   4=华夏银行
        String tmpWhere = "";
        String fieldsString = "t.*,c.*,xx.c_loaninfo_dkze,xx.c_loaninfo_periods,xx.c_loaninfo_car_priceresult"; // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
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
        leftsql="LEFT JOIN xmgj_xxzl xx on xx.icbc_id=t.icbc_id " +
                "LEFT JOIN kj_icbc c on c.id=t.icbc_id ";

        list = lists(whereString, fieldsString);
        System.out.println("list::::++  "+list);
        if (!Tools.myIsNull(kw)) { // 搜索关键字高亮
            for (TtMap info : list) {
                info.put("c_name", info.get("c_name").replace(kw, "<font style='color:red;background:#FFCC33;'>" + kw + "</font>"));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            TtMap ttMap = list.get(i);
            String Myyh ="0";
            if (StringUtils.isNotEmpty(ttMap.get("c_loaninfo_dkze")) && StringUtils.isNotEmpty(ttMap.get("c_loaninfo_periods"))) {
                BigDecimal dk_total_price = new BigDecimal(ttMap.get("c_loaninfo_dkze")); //贷款总额
                BigDecimal aj_date = new BigDecimal(ttMap.get("c_loaninfo_periods"));    //贷款期限
                BigDecimal myyh = dk_total_price.divide(aj_date, 3,BigDecimal.ROUND_DOWN);//计算出每月应还并保留三位小数
                String a1 = myyh.toString();
                int c1 = Integer.parseInt(a1.substring(a1.indexOf(".")+3));//截取第三位小数转成int
                if(c1 > 0){//判断是否大于0  是就保留两位小数
                    BigDecimal vv = myyh.setScale(2, BigDecimal.ROUND_DOWN);
                    BigDecimal zero = new BigDecimal("0.01");
                    Myyh = vv.add(zero).toString();
                }else{
                    BigDecimal vv = myyh.setScale(2, BigDecimal.ROUND_DOWN);
                    Myyh=vv.toString();
                }
                System.out.println("----每月应还："+Myyh);
            }
            ttMap.put("myyh", Myyh);
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

        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));

        String sql = "select SQL_CALC_FOUND_ROWS t.*,xx.*,xx.c_loaninfo_dkze,xx.c_loaninfo_periods,xx.c_loaninfo_car_priceresult from kj_icbc t LEFT JOIN xmgj_xxzl xx on xx.icbc_id=t.id where t.id = " + nid;
        TtMap map = Tools.recinfo(sql);
        String Myyh ="0";
        if (StringUtils.isNotEmpty(map.get("c_loaninfo_dkze")) && StringUtils.isNotEmpty(map.get("c_loaninfo_periods"))) {
            BigDecimal dk_total_price = new BigDecimal(map.get("c_loaninfo_dkze")); //贷款总额
            BigDecimal aj_date = new BigDecimal(map.get("c_loaninfo_periods"));    //贷款期限
            BigDecimal myyh = dk_total_price.divide(aj_date, 3,BigDecimal.ROUND_DOWN);//计算出每月应还并保留三位小数
            String a1 = myyh.toString();
            int c1 = Integer.parseInt(a1.substring(a1.indexOf(".")+3));//截取第三位小数转成int
            if(c1 > 0){//判断是否大于0  是就保留两位小数
                BigDecimal vv = myyh.setScale(2, BigDecimal.ROUND_DOWN);
                BigDecimal zero = new BigDecimal("0.01");
                Myyh = vv.add(zero).toString();
            }else{
                BigDecimal vv = myyh.setScale(2, BigDecimal.ROUND_DOWN);
                Myyh=vv.toString();
            }
            System.out.println("----每月应还："+Myyh);
        }
        map.put("myyh", Myyh);

        String hkjhsql = "SELECT * FROM xmloan_repayment_schedule WHERE icbc_id = " + nid;
        TtList reclist = Tools.reclist(hkjhsql);

        //贷后信息
        String dhsql = "select * from xmgj_xxzl where icbc_id=" + nid;
        TtMap mapafter = Tools.recinfo(dhsql);

        System.out.println("主贷人信息:"+map);
        String jsonInfo = Tools.jsonEncode(map);
        request.setAttribute("info", jsonInfo);//info为json后的info
        request.setAttribute("infodb", map);//infodb为TtMap的info
        request.setAttribute("hkjh", reclist);
        request.setAttribute("mapafter", mapafter);
        request.setAttribute("id", nid);
    }

    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        super.doPost(post, id, result2);
    }
}
