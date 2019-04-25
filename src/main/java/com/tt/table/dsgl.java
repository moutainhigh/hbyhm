package com.tt.table;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tlzf.AIPGException;
import com.tt.tlzf.DemoConfig;
import com.tt.tlzf.Httpmodal.insideHttp;
import com.tt.tlzf.util.DemoUtil;
import com.tt.tlzf.util.HttpUtil;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tlzf.xml.XmlParser;
import com.tt.tlzf.xstruct.common.AipgReq;
import com.tt.tlzf.xstruct.common.AipgRsp;
import com.tt.tlzf.xstruct.common.InfoReq;
import com.tt.tlzf.xstruct.common.InfoRsp;
import com.tt.tlzf.xstruct.quickpay.FASTTRX;
import com.tt.tlzf.xstruct.quickpay.FASTTRXRET;
import com.tt.tlzf.xstruct.trans.LedgerDtl;
import com.tt.tlzf.xstruct.trans.Ledgers;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * 代收
 */
public class dsgl extends DbCtrl {
    private final String title = "通联代收列表";
    private String orderString = "ORDER BY t.dt_edit DESC"; // 默认排序
    private boolean canDel = false;
    private boolean canAdd = false;
    private final String classAgpId = "55"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public dsgl() {
        super("tlzf_dk_details");
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

        String whereString = "true";
        ;
        String tmpWhere = "";
        String fieldsString = "t.*,i.c_name as c_name";
        // 显示字段列表如t.id,t.name,t.dt_edit,字段数显示越少加载速度越快，为空显示所有
        TtList list = null;

        /* 开始处理搜索过来的字段 */
        kw = post.get("kw");
        dtbe = post.get("dtbe");
        if (Tools.myIsNull(kw) == false) {
            whereString += "AND c_name like '%" + kw + "%'";
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
        leftsql = " LEFT JOIN kj_icbc i ON i.id=t.icbc_id";
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


    @Override
    public void doGetForm(HttpServletRequest request, TtMap post) {
        TtMap qy = Tools.recinfo("select * from tlzf_qy where icbc_id=" + post.get("id"));
        TtMap icbc = Tools.recinfo("select * from kj_icbc where id=" + post.get("id"));

        if (!Tools.myIsNull(post.get("nextUrl"))) {
            String[] urlstr = post.get("nextUrl").split("_");
            String cn = urlstr[0];
            String type = urlstr[1];
            request.setAttribute("cn", cn);
            request.setAttribute("type", type);
        }

        //tl入口  1签约  2 分期代收  3代收
        switch (post.get("tl")) {
            case "1":
                //TtMap qy=Tools.recinfo("select * from tlzf_qy where icbc_id="+post.get("id"));
                if (qy.isEmpty()) {
                    qy.put("cardid", icbc.get("c_cardno"));
                    qy.put("tel", icbc.get("c_tel"));
                    qy.put("account_name", icbc.get("c_name"));
                }
                break;
            case "2":
                TtList dslist = Tools.reclist("select * from tlzf_dk_details where icbc_id=" + post.get("id"));
                request.setAttribute("dslist", dslist);
                break;
        }
        request.setAttribute("qy", qy);
        request.setAttribute("icbc", icbc);
        long nid = Tools.myIsNull(post.get("id")) ? 0 : Tools.strToLong(post.get("id"));
        TtMap info = info(nid);
        String jsonInfo = Tools.jsonEncode(info);
        request.setAttribute("info", jsonInfo);// info为json后的info
        request.setAttribute("infodb", info);// infodb为TtMap的info
        //request.setAttribute("id", nid);
    }


    @Override
    public void doPost(TtMap post, long id, TtMap result2) {
        System.out.println("post:" + post);
        TtMap minfo = Tools.minfo();
        if (chk(post, 0)) {
            switch (post.get("tl")) {
                case "1":
                    if (id > 0) {
                        Tools.recEdit(post, "tlzf_qy", id);
                    } else {
                        post.put("gems_id", minfo.get("gemsid"));
                        post.put("gems_fs_id", minfo.get("icbc_erp_fsid"));
                        post.put("bc_status", "2");
                        post.put("qd_type", "2");
                        long qy_id = Tools.recAdd(post, "tlzf_qy");
                        TtMap ttMap = new TtMap();
                        ttMap.put("gems_code", orderutil.getOrderId("TLQYKJY", 7, qy_id));
                        Tools.recEdit(ttMap, "tlzf_qy", qy_id);
                        //添加记录
                        TtMap resmap = new TtMap();
                        resmap.put("qryid", String.valueOf(qy_id));
                        resmap.put("status", "2");
                        resmap.put("remark", post.get("remark1"));
                        Tools.recAdd(resmap, "tlzf_qy_result");
                    }
                    break;
                case "2":
                    TtMap qy = Tools.recinfo("select * from tlzf_qy where icbc_id=" + post.get("icbc_id"));
                    if (!qy.isEmpty()) {
                        TtMap ttMap = new TtMap();
                        InfoReq infoReq = DemoUtil.makeReq("310011");
                        FASTTRX ft = new FASTTRX();
                        ft.setMERCHANT_ID(DemoConfig.merchantid);
                        ft.setBUSINESS_CODE(DemoConfig.BUSINESS_CODE);//必须使用业务人员提供的业务代码，否则返回“未开通业务类型”
                        ft.setSUBMIT_TIME(DemoUtil.getNow());
                        ft.setAGRMNO(qy.get("agrmno"));
                        ft.setACCOUNT_NAME(qy.get("account_name"));
//                    int at=0;
//                    int fwf=0;
//                    if(post.get("amount")!=null&&!post.get("amount").equals("")){
//                        at=Integer.valueOf(post.get("amount"));
//                    }
//                    if(post.get("fw_price")!=null&&!post.get("fw_price").equals("")){
//                        fwf=Integer.valueOf(post.get("fw_price"));
//                    }
                        ft.setAMOUNT(post.get("amount"));
                        //ft.setCUST_USERID("哈哈哈哈");
                        ft.setREMARK(post.get("remark"));
                        //ft.setSUMMARY("asjdfasdfkasdf");
/*                    LedgerDtl dtl1 = new LedgerDtl();
                    dtl1.setAMOUNT(post.get("acount"));
                    dtl1.setMERCHANT_ID(DemoConfig.merchantid);
                    dtl1.setSN("0");
                    dtl1.setTYPE("0");*/
                        //Ledgers ledgers = new Ledgers();
                        //ledgers.addTrx(dtl1);
                        AipgReq req = new AipgReq();
                        req.setINFO(infoReq);
                        req.addTrx(ft);
                        //req.addTrx(ledgers);
                        //调用通联接口 返回xml字符串
                        insideHttp insideHttp = new insideHttp();
                        String res = insideHttp.kjyTranx310011(req);
                        ttMap.put("result_content", res);
                        if (res.substring(0, 1).equals("[")) {
                            System.out.println("第一种情况");
                            JSONArray ary = JSONArray.fromObject(res);
                            System.out.println(ary.get(0));
                            if (!ary.get(0).equals("")) {
                                JSONObject INFO = JSONObject.fromObject(ary.get(0));
                                System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                                ttMap.put("result_code", INFO.get("RET_CODE").toString());
                                ttMap.put("result_msg", INFO.get("ERR_MSG").toString());
                                errorMsg = INFO.get("ERR_MSG").toString();
                            }
                            ttMap.put("bc_status", "2");
                        } else {
                            System.out.println("第二种情况");
                            JSONObject ress = JSONObject.fromObject(res);
                            System.out.println("INFO:" + ress.get("INFO"));
                            if (ress.get("INFO") != null && !ress.get("INFO").equals("")) {
                                JSONObject INFO = JSONObject.fromObject(ress.get("INFO"));
                                System.out.println("ERR_MSG:" + INFO.get("ERR_MSG"));
                                ttMap.put("result_code", INFO.get("RET_CODE").toString());
                                ttMap.put("result_msg", INFO.get("ERR_MSG").toString());
                                errorMsg = INFO.get("ERR_MSG").toString();
                                if ("0000".equals(INFO.get("RET_CODE"))) {
                                    ttMap.put("bc_status", "3");
                                } else if (
                                        "2000".equals(INFO.get("RET_CODE")) ||
                                                "2001".equals(INFO.get("RET_CODE")) ||
                                                "2003".equals(INFO.get("RET_CODE")) ||
                                                "2005".equals(INFO.get("RET_CODE")) ||
                                                "2007".equals(INFO.get("RET_CODE")) ||
                                                "2008".equals(INFO.get("RET_CODE"))
                                ) {
                                    ttMap.put("bc_status", "1");
                                } else {
                                    ttMap.put("bc_status", "2");
                                }
                            }
                            System.out.println("FASTTRXRET:" + ress.get("FASTTRXRET"));
                            if (ress.get("FASTTRXRET") != null && !ress.get("FASTTRXRET").equals("")) {
                                JSONObject FASTTRXRET = JSONObject.fromObject(ress.get("FASTTRXRET"));
                                System.out.println("ERR_MSG:" + FASTTRXRET.get("ERR_MSG"));
                                ttMap.put("settle_day", FASTTRXRET.get("SETTLE_DAY").toString());
                            }
                        }
                        ttMap.put("req_sn", infoReq.getREQ_SN());
                        ttMap.put("business_code", ft.getBUSINESS_CODE());
                        ttMap.put("submit_time", ft.getSUBMIT_TIME());
                        ttMap.put("agrmno", ft.getAGRMNO());
                        ttMap.put("account_no", ft.getACCOUNT_NO());
                        //ttMap.put("bank_code",ft.getBANK_CODE());
                        ttMap.put("account_name", ft.getACCOUNT_NAME());
                        ttMap.put("amount", ft.getAMOUNT());
                        ttMap.put("fw_price", post.get("fw_price"));
                        ttMap.put("ds_date", post.get("ds_date"));
                        ttMap.put("periods", post.get("periods"));
                        //ttMap.put("currency",ft.getACCOUNT_NO());
                        ttMap.put("cardid_type", qy.get("cardid_type"));
                        ttMap.put("cardid", qy.get("cardid"));
                        ttMap.put("tel", qy.get("tel"));
                        ttMap.put("cvv2", qy.get("cvv2"));
                        ttMap.put("vailddate", qy.get("vailddate"));
                        //ttMap.put("cust_userid","");
                        //ttMap.put("summary",ft.getACCOUNT_NO());
                        ttMap.put("remark", ft.getREMARK());
                        ttMap.put("qd_type", "2");
                        ttMap.put("sd_status", "1");
                        ttMap.put("icbc_id", post.get("icbc_id"));
                        ttMap.put("gems_id", minfo.get("gemsid"));
                        ttMap.put("gems_fs_id", minfo.get("icbc_erp_fsid"));
                        long dk_id = Tools.recAdd(ttMap, "tlzf_dk_details");
                        TtMap result = new TtMap();
                        result.put("qryid", String.valueOf(dk_id));
                        result.put("status", ttMap.get("bc_status"));
                        result.put("remark", ft.getREMARK());
                        Tools.recAdd(result, "tlzf_dk_details_result");
                    }
                    break;
            }
        }
        String nextUrl = "";
        if (post.get("cn") != null
                && !post.get("cn").equals("")
                && post.get("type") != null
                && !post.get("type").equals("")) {
            nextUrl = Tools.urlKill("sdo|cn|type") + "&type=" + post.get("type") + "&cn=" + post.get("cn") + "&sdo=list";
        }
        boolean bSuccess = errorCode == 0;
        Tools.formatResult(result2, bSuccess, errorCode, bSuccess ? "提交成功！" : errorMsg,
                bSuccess ? nextUrl : "");//失败时停留在当前页面,nextUrl为空

    }

    @Override
    public boolean chk(TtMap array, long id) {
        if (!agpOK) {// 演示在需要权限检查的地方插入权限标志判断
            return false;
        }
        if (!Tools.myIsNull(array.get("fromcommand"))) { // 从ManagerCmd来的。不用过滤参数
        } else {
            System.out.println("表单验证star");
            String myErroMsg = "";

            if (Tools.myIsNull(array.get("periods"))) {
                myErroMsg += "期数不能为空！\n";
            }
            if (Tools.myIsNull(array.get("ds_date"))) {
                myErroMsg += "代收日期不能为空！\n";
            }
            if (Tools.myIsNull(array.get("amount"))) {
                myErroMsg += "代收总金额不能为空！\n";
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
