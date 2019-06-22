package com.tt.table;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Config;
import com.tt.tool.DbCtrl;
import com.tt.tool.Tools;

import javax.servlet.http.HttpServletRequest;
import javax.tools.Tool;

public class ddjd extends DbCtrl {
    private final String classAgpId = "32"; // 随便填的，正式使用时应该跟model里此模块的ID相对应
    public boolean agpOK = false;// 默认无权限

    public ddjd() {
        super("sys_modal_hbyh");
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

        String sql = "";
        switch (post.get("tab")) {
            case "28":
                sql = "select * from kj_icbc_result where qryid=" + post.get("id");
                break;
            case "29":
                sql = "select * from hbyh_xxzl_result where qryid=" + post.get("id");
                break;
            case "49":
                sql = "select * from hbyh_yhht_result where qryid=" + post.get("id");
                break;
            case "50":
                sql = "select * from hbyh_gsht_result where qryid=" + post.get("id");
                break;
            case "34":
                sql = "select * from hbyh_gpsgd_result where qryid=" + post.get("id");
                break;
            case "40":
                sql = "select * from hbyh_dygd_result where qryid=" + post.get("id");
                break;
            case "51":
                sql = "select * from hbyh_yhclhs_result where qryid=" + post.get("id");
                break;
            case "52":
                sql = "select * from hbyh_gsclhs_result where qryid=" + post.get("id");
                break;
            case "53":
                sql = "select * from hbyh_dyclhs_result where qryid=" + post.get("id");
                break;
        }
        TtList jdlist = Tools.reclist(sql);
        request.setAttribute("jdlist", jdlist);

        //获取进度各板块
        TtList modallist = Tools.reclist("select * from sys_modal_hbyh where modal_tag=1 and id_uplevel=" +post.get("id_uplevel") + " order by sort");
        request.setAttribute("modallist", modallist);

        String bankname = "";
        String jdsql1 = "";
        if ("27".equals(post.get("id_uplevel"))){ //河北银行
            bankname = "hbyh";
            jdsql1 = "select \n" +
                    "i1.id as id_28, \n" +
                    "i2.id as id_29,\n" +
                    "i3.id as id_49,\n" +
                    "i4.id as id_50,\n" +
                    "i5.id as id_34,\n" +
                    "i6.id as id_40,\n" +
                    "i7.id as id_51,\n" +
                    "i8.id as id_52,\n" +
                    "i9.id as id_53\n" +
                    "from kj_icbc i1\n";
        } else if ("57".equals(post.get("id_uplevel"))){
            bankname = "hxyh";
            jdsql1 = "select \n" +
                    "i1.id as id_58, \n" +
                    "i2.id as id_59,\n" +
                    "i3.id as id_60,\n" +
                    "i4.id as id_61,\n" +
                    "i5.id as id_62,\n" +
                    "i6.id as id_63,\n" +
                    "i7.id as id_64,\n" +
                    "i8.id as id_65,\n" +
                    "i9.id as id_66\n" +
                    "from kj_icbc i1\n";
        } else if ("67".equals(post.get("id_uplevel"))){
            bankname = "xmgj";
            jdsql1 = "select \n" +
                    "i1.id as id_68, \n" +
                    "i2.id as id_69,\n" +
                    "i3.id as id_70,\n" +
                    "i4.id as id_71,\n" +
                    "i5.id as id_72,\n" +
                    "i6.id as id_73,\n" +
                    "i7.id as id_74,\n" +
                    "i8.id as id_75,\n" +
                    "i9.id as id_76\n" +
                    "from kj_icbc i1\n";
        }

        //查询各板块是否已进件
        String jdsql = jdsql1 +
                "LEFT JOIN " +bankname + "_xxzl i2 ON i2.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_yhht i3 ON i3.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_gsht i4 ON i4.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_gpsgd i5 ON i5.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_dygd i6 ON i6.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_yhclhs i7 ON i7.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_gsclhs i8 ON i8.icbc_id=i1.id\n" +
                "LEFT JOIN " +bankname + "_dyclhs i9 ON i9.icbc_id=i1.id\n" +
                "where i1.id=" + post.get("icbc_id");
        TtMap jdtag = Tools.recinfo(jdsql);
        request.setAttribute("jdtag", jdtag);
        request.setAttribute("tab", post.get("tab"));
        request.setAttribute("id", post.get("id"));
        request.setAttribute("icbc_id", post.get("icbc_id"));
        request.setAttribute("saveButton", "true");
    }


}
