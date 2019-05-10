package com.tt.tool;

import com.tt.api.Jdpush;
import com.tt.data.TtMap;

public class Addadmin_msg {

    /**
     *
     * @param receiveid     推送的用户id assess_admin表中的gems_id
     * @param bc_status     审核状态
     * @param remark1       留言
     * @param c_name        客户姓名
     * @param typeStatus    业务状态
     * @param bankName      银行名称
     * @param mid_add       添加人
     * @return
     */
    public static long addmsg(String receiveid, String bc_status, String remark1, String c_name, String typeStatus, String bankName, String mid_add){

        System.out.println("进入addmsg---");
        TtMap recinfo1 = Tools.recinfo("select a.*, f.`name` fs_name from assess_admin a left join assess_fs f on a.icbc_erp_fsid = f.id where a.id ="+mid_add);
        System.out.println("查询的用户数据"+ recinfo1);
        String status = "";
        String cp = "";
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

        switch (recinfo1.get("cp")){
            case "0":
                cp = "{三级账号}";
                break;
            case "1":
                cp = "{一级账号}";
                break;
            case "2":
                cp = "{二级账号}";
                break;
        }

        String msg = "客户" + c_name + "，" + typeStatus + "-" + bankName + "订单，提交人：" + recinfo1.get("name") + cp + "，来自" + recinfo1.get("fs_name") + "，订单状态已更新，状态为" + status + "，留言：" + remark1;

        System.out.println("msg:" + msg);

        TtMap ttMap = new TtMap();
        ttMap.put("receiveid", receiveid);
        ttMap.put("sendid", "0");
        ttMap.put("dt_add", Tools.getnow());
        ttMap.put("dt_edit", Tools.getnow());
        ttMap.put("status", "0");
        ttMap.put("mid_add", receiveid);
        ttMap.put("msg", msg);

        long assess_admin_msg = Tools.recAdd(ttMap, "assess_admin_msg");

        //手机弹框推送
        String sql = "select jgid from assess_admin where id ="+receiveid;
        TtMap recinfo = Tools.recinfo(sql);
        if (recinfo!=null && recinfo.size()>0){
            Jdpush.testSendPush("7e21faf06524b22f0ee1414c","c87361ae4d7d91067b3ea01a", recinfo.get("jgid"), msg);
        }

        return assess_admin_msg;
    }
}
