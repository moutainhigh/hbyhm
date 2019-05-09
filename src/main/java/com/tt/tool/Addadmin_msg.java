package com.tt.tool;

import com.tt.data.TtMap;

public class Addadmin_msg {

    /**
     *
     * @param receiveid     推送的用户id assess_admin表中的gems_id
     * @param bc_status     审核状态
     * @param remark1       留言
     * @return
     */
    public static long addmsg(String receiveid, String bc_status, String remark1, String mid_add){
        String status = "";
        switch (bc_status){
            case "0":
                status = "请选择";
                break;
            case "1":
                status = "草稿箱";
                break;
            case "2":
                status = "审核中";
                break;
            case "3":
                status = "通过";
                break;
            case "4":
                status = "不通过";
                break;
            case "5":
                status = "回退补件";
                break;
            case "6":
                status = "过件待补";
                break;
        }

        TtMap ttMap = new TtMap();
        ttMap.put("receiveid", receiveid);
        ttMap.put("sendid", "0");
        ttMap.put("dt_add", Tools.getnow());
        ttMap.put("dt_edit", Tools.getnow());
        ttMap.put("status", "0");
        ttMap.put("mid_add", mid_add);
        ttMap.put("msg", "您的订单状态发生变化,最新状态为：" + status + ", 留言：" + remark1);

        long assess_admin_msg = Tools.recAdd(ttMap, "assess_admin_msg");

        return assess_admin_msg;
    }
}
