package com.tt.visual;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController      //  默认类中的方法都会以 json 的格式返回
@Controller
public class Visual {
    private String [] string;
    private Object [][] object;
    private static TtMap minfo;
    private static String fs_id;
    private static String hx = " and di.app=4";
    private static String hb = " and di.app=2";
    private static String xm = " and di.app=3";

    //输入sql语句进行查询操作
    private static TtList selectSQL(String sql){
        return Tools.reclist(sql);
    }

    //遍历查询到的数组，如果为空附一个初始值，并排序
    private Object[][] returnList(TtList chart){
        if(chart.size()<9){
            for(int i=0;i<chart.size();i++){
                object[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//把日期格式输出放入二维数组xxxx-xx
                object[0][i]=(chart.get(i).get("total"));//把每月数据放入二维数组
            }
            for(int i=chart.size();i<9;i++){
                object[1][i] = "2018-";
                object[0][i] = "0";
            }
        }else{
            for(int i=0;i<9;i++){
                object[1][i]=(chart.get(i).get("year")+"-"+chart.get(i).get("month"));//把日期格式输出放入二维数组xxxx-xx
                object[0][i]=(chart.get(i).get("total"));//把每月数据放入二维数组
            }
        }
        return object;
    }

    //查询代理商ID
    private String selectGemsId(String gems){
        String s;
        String sql="select id from assess_fs where name='"+gems+"'";
        TtList chart=selectSQL(sql);//数据库查询操作
        if(chart.size() == 0){
            s="0";
        }else{
            s=chart.get(0).get("id");
        }
        return s;
    }

    //前台数据后台获取
    public static void management(HttpServletRequest request) {
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String sql;


        sql= "select count(*) amount from kj_icbc di " +
        " where month(dt_add)=MONTH(SYSDATE())  " +
                " and YEAR(dt_add)=year(SYSDATE())  " +
                " and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") " ;

        TtList billlistHx=selectSQL(sql+hx);
        request.setAttribute("billlistHx",billlistHx);//每月报单总量     0华夏
        TtList billlistHb=selectSQL(sql+hb);
        request.setAttribute("billlistHb",billlistHb);//每月报单总量     0河北
        TtList billlistXm=selectSQL(sql+xm);
        request.setAttribute("billlistXm",billlistXm);//每月报单总量     0厦门


        sql= "select count(*) amount from hxyh_dygd where bc_status=3 and icbc_id in (select id amount from kj_icbc di " +
                "    where month(dt_add)=MONTH(SYSDATE())  " +
                "    and YEAR(dt_add)=year(SYSDATE())  " +
                "    and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") ";
        TtList fklistHx=selectSQL(sql+hx+" )");
        request.setAttribute("fklistHx",fklistHx);//每月抵押归档完成总量     0华夏

        sql= "select count(*) amount from hbyh_dygd where bc_status=3 and icbc_id in (select id amount from kj_icbc di " +
                "    where month(dt_add)=MONTH(SYSDATE())  " +
                "    and YEAR(dt_add)=year(SYSDATE())  " +
                "    and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") ";
        TtList fklistHb=selectSQL(sql+hb+" )");
        request.setAttribute("fklistHb",fklistHb);//每月抵押归档完成总量     0河北

        sql= "select count(*) amount from xmgj_dygd where bc_status=3 and icbc_id in (select id amount from kj_icbc di " +
                "    where month(dt_add)=MONTH(SYSDATE())  " +
                "    and YEAR(dt_add)=year(SYSDATE())  " +
                "    and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") ";
        TtList fklistXm=selectSQL(sql+xm+" )");
        request.setAttribute("fklistXm",fklistXm);//每月抵押归档完成总量     0厦门

        sql="select round(m2.loan*100/m1.amount,2) yuqilv,m2.gid,m2.gname from   " +
                "  (select count(di.gems_fs_id) amount,f.id gid,f.name gname   " +
                "   from kj_icbc di,assess_fs f   " +
                "   where di.fk_status=3   " +
                "     and f.id=di.gems_fs_id   ";

        String sqlEdit= " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +" )" +
                "     GROUP BY di.gems_fs_id) m1,  " +
                "  (select count(lol.gems_fs_id) loan,f.id gid,f.name gname   " +
                "   from hxloan_overdue_list lol,assess_fs f,kj_icbc di    " +
                "   where lol.type_id!=6   " +
                "     and di.id=lol.icbc_id ";

        String sqlEdit1= " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +" )" +
                "     GROUP BY di.gems_fs_id) m1,  " +
                "  (select count(lol.gems_fs_id) loan,f.id gid,f.name gname   " +
                "   from hbloan_overdue_list lol,assess_fs f,kj_icbc di    " +
                "   where lol.type_id!=6   " +
                "     and di.id=lol.icbc_id ";

        String sqlEdit2= " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +" )" +
                "     GROUP BY di.gems_fs_id) m1,  " +
                "  (select count(lol.gems_fs_id) loan,f.id gid,f.name gname   " +
                "   from xmloan_overdue_list lol,assess_fs f,kj_icbc di    " +
                "   where lol.type_id!=6   " +
                "     and di.id=lol.icbc_id ";


        String sqlEnd= " and f.id=lol.gems_fs_id   " +
                "     and lol.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +" )" +
                "     GROUP BY lol.gems_fs_id) m2   " +
                "where m1.gid=m2.gid ORDER BY yuqilv desc";

        TtList yuqilvHx=selectSQL(sql+hx+sqlEdit+hx+sqlEnd);
        request.setAttribute("yuqilvHx",yuqilvHx );//逾期率代理商排名      华夏

        TtList yuqilvHb=selectSQL(sql+hb+sqlEdit1+hb+sqlEnd);
        request.setAttribute("yuqilvHb",yuqilvHb );//逾期率代理商排名      河北

        TtList yuqilvXm=selectSQL(sql+xm+sqlEdit2+xm+sqlEnd);
        request.setAttribute("yuqilvXm",yuqilvXm );//逾期率代理商排名      厦门

        sql="select id,name from comm_states";
        TtList comm_city=selectSQL(sql);
        request.setAttribute("comm_city",comm_city );//省份列表

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        int formatDate = Integer.parseInt(sdf.format(date));
        int[] years=new int[5];
        for(int i=0;i<5;i++){
            years[i]=formatDate-i;
        }
        request.setAttribute("years",years );//年份列表

        String[] count = new String[10];
        for(int i=0;i<10;i++){
            count[i]=i+"";
        }

        request.setAttribute("count",count );
    }

    //每月数据总单数折线图ajax前台获取           null,null,null
    @RequestMapping("/manager/visual/getPathMap.do")
    @ResponseBody
    public Object[][] getPathMap(String baodanname,String baodantime,String bank){
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        //提取sql语句共同的部分
        String sql="select year(dt_add) year,month(dt_add) month,count(*) total " +
                "  from kj_icbc di" +
                "  where year(SYSDATE())-year(dt_add) < 6 " +
                "  and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") ";
        String sqlEdit="  GROUP BY year(dt_add),month(dt_add) ORDER BY dt_add DESC limit 9 ";
        //判断是否输入代理商,是否选择省份
        String gems_id=selectGemsId(baodanname);
        if(!baodanname.equals("请输入代理商") && !baodanname.equals("")) {
            sql += "  and gems_fs_id= " + gems_id;
        }
        //判断是否选择时间
        if(!baodantime.equals("0")){
            sql += " and year(dt_add)="+ baodantime;
        }
        System.out.println(bank+",9999999");
        switch (bank){
            case "2":
                sql += hb+sqlEdit;
                break;
            case "3":
                sql += xm+sqlEdit;
                break;
            case "4":
                sql += hx+sqlEdit;
                break;
        }
        TtList chart=selectSQL(sql);//数据库查询操作

        object=new Object[2][9];
        object=returnList(chart);//遍历查询判断是否为空，排序，返回处理过的数据
        return object;
    }


    //抵押完成天数分布扇形图ajax前台获取
    @RequestMapping("/manager/visual/getPawnPathMap.do")
    @ResponseBody
    public String[] getPawnPathMap(String diyaname,String diyatime,String bank) {
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String table="";
        switch (bank){
            case "2":
                table = "hbyh";
                break;
            case "3":
                table = "xmgj";
                break;
            case "4":
                table = "hxyh";
                break;
        }
        //拼接sql语句
        String sql ="select sum(case when  to_days(pawn.d1) - to_days(pawn.d) < 15 then 1 end) paw1, " +
                "       sum(case when to_days(pawn.d1) - to_days(pawn.d) >= 15 and to_days(pawn.d1) - to_days(pawn.d) < 30 then 1 end) paw2,  " +
                "       sum(case when to_days(pawn.d1) - to_days(pawn.d) >= 30 and to_days(pawn.d1) - to_days(pawn.d) < 45 then 1 end) paw3,  " +
                "       sum(case when to_days(pawn.d1) - to_days(pawn.d) >= 45 and to_days(pawn.d1) - to_days(pawn.d) < 60 then 1 end) paw4, " +
                "       sum(case when to_days(pawn.d1) - to_days(pawn.d) >= 60 then 1 end) paw5  " +
                "       from (select iekir1.qryid,iekir1.status s,iekir1.dt_add d,iekir2.status s1,iekir2.dt_add d1  " +
                "              from (select dier.qryid,dier.status,dier.dt_add  " +
                "                   from "+table+"_dygd_result dier,kj_icbc di,"+table+"_dygd hd  " +
                "                   where dier.status=2   " +
                "                   and hd.id=dier.qryid " +
                "                   and di.id=hd.icbc_id) iekir1, " +
                "                  (select dier.qryid,dier.status,dier.dt_add  " +
                "                   from "+table+"_dygd_result dier,kj_icbc di,"+table+"_dygd hd  " +
                "                   where dier.status=3  " +
                "                   and hd.id=dier.qryid" +
                "                   and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")  " +
                "                   and di.id=hd.icbc_id  ";
        String sqlEdit="  ) iekir2 where iekir1.qryid=iekir2.qryid ) pawn ";

        //判断是否输入代理商
        String gems_id=selectGemsId(diyaname);
        if(!diyaname.equals("请输入代理商") && !diyaname.equals("")) {
            sql += " and and di.gems_fs_id= " + gems_id;
        }
        //判断是否选择时间
        if(!diyatime.equals("0")){
            sql += " and year(dier.dt_add)="+ diyatime;
        }
        switch (bank){
            case "2":
                sql += hb+sqlEdit;
                break;
            case "3":
                sql += xm+sqlEdit;
                break;
            case "4":
                sql += hx+sqlEdit;
                break;
        }
        TtList chart=selectSQL(sql);
        string = new String[5];
        for (int i = 1; i < 6; i++) {
            if(chart.size() == 0){
                string[i - 1] = "0";
            }else{
                if (chart.get(0).get("paw" + i) == null  || chart.get(0).get("paw" + i).equals("")) {
                    string[i - 1] = "0";
                } else {
                    string[i - 1] = chart.get(0).get("paw" + i);
                }
            }
        }
        return string;
    }


    //征信查询分布扇形图ajax前台获取   null,null       0,1
    @RequestMapping("/manager/visual/getCreditPathMap.do")
    @ResponseBody
    public String[] getCreditPathMap(String zhengxinname,String zhengxintime,String bank) {
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        //拼接sql语句
        String sql="select zx1.zxok_tag,zx2.zxok  " +
                " from (select count(*) zxok_tag from kj_icbc di " +
                " where di.bc_status=3  " +
                " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")" +
                " ";
        String sqlEdit=" ) zx1," +
                " (select count(*) zxok from kj_icbc di " +
                " where di.bc_status != 3 " +
                " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")" +
                "";

        //判断是否输入代理商
        String gems_id=selectGemsId(zhengxinname);
        if(!zhengxinname.equals("请输入代理商") && !zhengxinname.equals("")){
            sql += " and di.gems_fs_id= "+ gems_id;
            sqlEdit += " and di.gems_fs_id= "+ gems_id;
        }
        //判断是否点击选择时间
        if(!zhengxintime.equals("0")){
            sql += " and YEAR(di.dt_add)= "+ zhengxintime;
            sqlEdit += " and YEAR(di.dt_add)= "+ zhengxintime;
        }else{
            sql += " and YEAR(di.dt_add)=YEAR(SYSDATE()) and MONTH(di.dt_add)=MONTH(SYSDATE()) ";
            sqlEdit += " and YEAR(di.dt_add)=YEAR(SYSDATE()) and MONTH(di.dt_add)=MONTH(SYSDATE()) ";
        }
        switch (bank){
            case "2":
                sql += hb+sqlEdit+hb+" ) zx2 ";
                break;
            case "3":
                sql += xm+sqlEdit+xm+" ) zx2 ";
                break;
            case "4":
                sql += hx+sqlEdit+hx+" ) zx2 ";
                break;
        }
        TtList chart=selectSQL(sql);
        string = new String[2];
        if(chart.get(0).get("zxok_tag").equals("0")){
            string[0]="0";
        }else{
            string[0]=chart.get(0).get("zxok_tag");
        }

        if(chart.get(0).get("zxok").equals("0")){
            string[1]="0";
        }else{
            string[1]=chart.get(0).get("zxok");
        }

        return string;
    }


    //客户年龄分布扇形图ajax前台获取      null,null,null,null
    @RequestMapping("/manager/visual/getAgePathMap.do")
    @ResponseBody
    public String[] getAgePathMap(String bank,HttpServletRequest request) {

        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");

        String sql1= "select count(*) amount from kj_icbc di " +
                " where month(dt_add)=MONTH(SYSDATE())  " +
                " and YEAR(dt_add)=year(SYSDATE())  " +
                " and gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +") " ;


        String sql="    select sum(case when cardage.card >= 18 and cardage.card < 30 then 1 end) age1, " +
                "      sum(case when cardage.card >= 30 and cardage.card < 40 then 1 end) age2, " +
                "      sum(case when cardage.card >= 40 and cardage.card < 50 then 1 end) age3, " +
                "      sum(case when cardage.card >= 50 then 1 end) age4 from  " +
                "     (select (year(SYSDATE())-Mid(c_cardno,7,4)) card from kj_icbc di " +
                "     where gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")  ";
        switch (bank){
            case "2":
                sql += hb + " ) cardage";

                break;
            case "3":
                sql += xm + " ) cardage";

                break;
            case "4":
                sql += hx + " ) cardage";

                break;
        }
        TtList chart=selectSQL(sql);
        string = new String[4];
        for(int i=1;i<5;i++){
            if(chart.get(0).get("age"+i)==null || chart.get(0).get("age"+i).equals("")){
                string[i-1]="0";
            }else{
                string[i-1]=chart.get(0).get("age"+i);
            }
        }

        return string;
    }


    //抵押材料回收分布图ajax前台获取   null,null,null
    @RequestMapping("/manager/visual/getRecyclePathMap.do")
    @ResponseBody
    public Object[][] getRecyclePathMap(String cailiaoname,String cailiaotime,String bank) {
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String table="";
        switch (bank){
            case "2":
                table = "hbyh";
                break;
            case "3":
                table = "xmgj";
                break;
            case "4":
                table = "hxyh";
                break;
        }
        //拼接sql语句
        String sql="select year(hd.dt_add) year, " +
                " month(hd.dt_add) month,  " +
                " count(*) total from kj_icbc di,"+table+"_dyclhs hd  " +
                " where hd.bc_status=1  " +
                " and di.id=hd.icbc_id" +
                " and di.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")  " +
                " and year(SYSDATE())-year(hd.dt_add) < 6  " ;
        String sqlEdit= " GROUP BY year(hd.dt_add),month(hd.dt_add)  ORDER BY hd.dt_add DESC limit 9";

        //判读是否输入代理商
        String gems_id=selectGemsId(cailiaoname);
        if(!cailiaoname.equals("请输入代理商") && !cailiaoname.equals("")) {
            sql += " and di.gems_fs_id= " + gems_id;
        }
        //判断是否选择时间
        if(!cailiaotime.equals("0")){
            sql += " and year(hd.dt_add)="+ cailiaotime;
        }
        switch (bank){
            case "2":
                sql += hb + sqlEdit;
                break;
            case "3":
                sql += xm + sqlEdit;
                break;
            case "4":
                sql += hx + sqlEdit;
                break;
        }
        TtList chart=selectSQL(sql);
        object=new Object[2][9];
        object=returnList(chart);
        return object;
    }


    //逾期率M1，M2，M3分布图ajax前台获取    null,null,null,null
    @RequestMapping("/manager/visual/getOverdueMap.do")
    @ResponseBody
    public Object[][] getOverdueMap(String yuqiname,String bank){
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String table="";
        String tab="";
        switch (bank){
            case "2":
                table = "hbyh";
                tab = "hb";
                break;
            case "3":
                table = "xmgj";
                tab = "xm";
                break;
            case "4":
                table = "hxyh";
                tab = "hx";
                break;
        }
        String sqlNew="          and dic.cars_type=1  ";
        String sqlOld="          and dic.cars_type=2  ";
        String gemsId="in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")";
        String sql= "select m1.amount m1a,m1.money/10000 m1m, m2.amount m2a,m2.money/10000 m2m, m3.amount m3a,m3.money/10000 m3m  " +
                "from (select count(*) amount,sum(lol.overdue_amount) money   " +
                "      from "+tab+"loan_overdue_list lol,kj_icbc di,"+table+"_qccl dic  " +
                "        where lol.overdue_days<30   " +
                "          and lol.type_id!=6 " +
                "          and lol.icbc_id=di.id  " +
                "          and dic.icbc_id=lol.icbc_id  ";
        String sqlEdit= "  and di.gems_fs_id " + gemsId +
                "        ) m1,(select count(*) amount,sum(lol.overdue_amount) money   " +
                "      from "+tab+"loan_overdue_list lol,kj_icbc di,"+table+"_qccl dic  " +
                "        where lol.overdue_days<60   " +
                "          and lol.overdue_days>=30   " +
                "          and lol.type_id!=6  " +
                "          and lol.icbc_id=di.id  " +
                "          and dic.icbc_id=lol.icbc_id  ";
        String sqlEnd= "   and di.gems_fs_id " + gemsId +
                "           ) m2,(select count(*) amount,sum(lol.overdue_amount) money   " +
                "      from "+tab+"loan_overdue_list lol,kj_icbc di,"+table+"_qccl dic  " +
                "        where lol.overdue_days>=60   " +
                "          and lol.type_id!=6" +
                "          and lol.icbc_id=di.id  " +
                "          and dic.icbc_id=lol.icbc_id  " +
                "          and di.gems_fs_id "+ gemsId ;
        String e=") m3";
        //判断是否输入代理商
        String gems_id=selectGemsId(yuqiname);
        if(!yuqiname.equals("请输入代理商") && !yuqiname.equals("")){
            sql += " and di.gems_fs_id=  " + gems_id;
            sqlEdit += " and di.gems_fs_id=  " + gems_id;
            sqlEnd += " and di.gems_fs_id=  " + gems_id;

        }
        switch (bank){
            case "2":
                sql += hb;
                sqlEdit += hb;
                sqlEnd += hb;
                break;
            case "3":
                sql += xm;
                sqlEdit += xm;
                sqlEnd += xm;
                break;
            case "4":
                sql += hx;
                sqlEdit += hx;
                sqlEnd += hx;
                break;
        }
        TtList chart=selectSQL(sql+sqlEdit+sqlEnd+e);
        TtList chart1=selectSQL(sql+sqlNew+sqlEdit+sqlNew+sqlEnd+sqlNew+e);
        TtList chart2=selectSQL(sql+sqlOld+sqlEdit+sqlOld+sqlEnd+sqlOld+e);
        String []s={"m1a","m1m","m2a","m2m","m3a","m3m"};
        object=new Object[3][6];

        for(int i=0;i<6;i++){
            if(chart.size()  < 1){
                object[0][i]="0";
            }else{
                object[0][i]=chart.get(0).get(s[i]);
            }
            if(chart.size()  < 1){
                object[1][i]="0";
            }else {
                object[1][i] = chart1.get(0).get(s[i]);
            }
            if(chart.size()  < 1){
                object[2][i]="0";
            }else{
                object[2][i]=chart2.get(0).get(s[i]);
            }
        }
        return object;
    }


    //逾期省份分布图ajax前台获取    null,null,null,null
    @RequestMapping("/manager/visual/getStateMap.do")
    @ResponseBody
    public Object[][] getStateMap(String bank){
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String table="";
        String tab="";
        switch (bank){
            case "2":
                table = "hbyh";
                tab = "hb";
                break;
            case "3":
                table = "xmgj";
                tab = "xm";
                break;
            case "4":
                table = "hxyh";
                tab = "hx";
                break;
        }
        //排名前五逾期省份
        String sql= "select count(cs.name) amount,sum(lol.overdue_amount) money,cs.name cname  " +
                "  from "+tab+"loan_overdue_list lol,kj_icbc di,comm_states cs,"+table+"_xxzl hx  " +
                "  where di.id=lol.icbc_id   " +
                "    and hx.loan_state_id=cs.id " +
                "    and hx.icbc_id=di.id " +
                "    and lol.type_id!=6   ";
        String sqlEdit = "    and lol.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")" +
                "    GROUP BY cname   " +
                "    ORDER BY amount  " +
                "    limit 0,5";
        //其他逾期省份
        String sqlOth="select sum(other.amount) Oamount,sum(other.money) Omoney " +
                "from (select count(cs.name) amount,sum(lol.overdue_amount) money,cs.name cname  " +
                "      from "+tab+"loan_overdue_list lol,kj_icbc di,comm_states cs,"+table+"_xxzl hx   " +
                "      where di.id=lol.icbc_id   " +
                "        and hx.loan_state_id=cs.id " +
                "        and hx.icbc_id=di.id " +
                "        and lol.type_id!=6   ";
        String sqlEnd = " and lol.gems_fs_id in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")" +
                "        GROUP BY cname   " +
                "        ORDER BY amount  " +
                "        limit 5,30  ) other";
        switch (bank){
            case "2":
                sql += hb + sqlEdit;
                sqlOth += hb + sqlEnd;
                break;
            case "3":
                sql += xm + sqlEdit;
                sqlOth += xm + sqlEnd;
                break;
            case "4":
                sql += hx + sqlEdit;
                sqlOth += hx + sqlEnd;
                break;
        }
        TtList chart=selectSQL(sql);
        TtList chart1=selectSQL(sqlOth);
        object=new Object[6][3];
        if(chart.size()<5){
            for(int i=0;i<chart.size();i++){
                object[i][0]=chart.get(i).get("amount");
                object[i][1]=chart.get(i).get("money");
                object[i][2]=chart.get(i).get("cname");
            }
            for(int i=chart.size();i<5;i++){
                object[i][0]="0";
                object[i][1]="0";
                object[i][2]="某某省"+i;
            }
        }else{
            for(int i=0;i<5;i++){
                object[i][0]=chart.get(i).get("amount");
                object[i][1]=chart.get(i).get("money");
                object[i][2]=chart.get(i).get("cname");
            }
        }
        if(chart1.get(0).get("Oamount").equals("") && chart1.get(0).get("Oamount") == ""){
            object[5][0]="0";
            object[5][1]="0";
            object[5][2]="其他省";
        }else{
            object[5][0]=chart1.get(0).get("Oamount");
            object[5][1]=chart1.get(0).get("Omoney");
            object[5][2]="其他省";
        }
        return object;
    }


    //代理商综合能力图ajax前台获取    null,null,null,null
    @RequestMapping("/manager/visual/getAgencyMap.do")
    @ResponseBody
    public Object[] getAgencyMap(String dailiname,String dailitime,String bank){
        minfo =Tools.minfo();
        fs_id=minfo.get("icbc_erp_fsid");
        String tab="";
        switch (bank){
            case "2":
                tab = "hb";
                break;
            case "3":
                tab = "xm";
                break;
            case "4":
                tab = "hx";
                break;
        }
        String GemsId="in(select id from assess_fs where up_id="+ fs_id +" or id ="+ fs_id +")";
        //业务能力
        String sqlBd= "select year(SYSDATE()) years,count(*) amount from kj_icbc di " +
                " where YEAR(di.dt_add)=year(SYSDATE()) " +
                " and di.gems_fs_id " + GemsId;
        //进件效率
        String sqlJj= "select round(avg(timestampdiff(day,di.dt_add,di.dt_fin)),2) da " +
                "from kj_icbc di" +
                " where year(di.dt_fin)!=0 and year(SYSDATE())=year(di.dt_add) " +
                " and di.gems_fs_id " + GemsId;
        //风控能力
        String sqlFk= "select count(*) amount " +
                "  from "+tab+"loan_overdue_list lol,kj_icbc di" +
                "  where lol.overdue_days<30 " +
                "    and lol.type_id!=6 " +
                "    and lol.icbc_id=di.id " +
                "    and year(SYSDATE())=year(lol.dt_add) " +
                "    and lol.gems_fs_id " + GemsId;
        //运营能力
        String sqlYy= "select count(*) amount from kj_icbc di " +
                " where year(di.dt_fin)!=0 " +
                " and year(SYSDATE())=year(di.dt_add)" +
                " and di.gems_fs_id " + GemsId;
        //贷后能力
        String sqlDh= "select count(*) amount  " +
                "  from "+tab+"loan_overdue_list lol,kj_icbc di" +
                "  where lol.overdue_days>=60 " +
                "    and lol.type_id!=6 " +
                "    and lol.icbc_id=di.id " +
                "    and year(SYSDATE())=year(lol.dt_add) " +
                "    and lol.gems_fs_id " + GemsId;
        //判断是否输入代理商
        String gems_id=selectGemsId(dailiname);
        if(!dailiname.equals("请输入代理商") && !dailiname.equals("")){
            String sqlEdit = " and di.gems_fs_id=  " + gems_id;
            sqlBd += sqlEdit;
            sqlJj += sqlEdit;
            sqlFk += sqlEdit;
            sqlYy += sqlEdit;
            sqlDh += sqlEdit;
        }
        Object[] obj = new Object[6];
        switch (bank){
            case "2":
                sqlBd += hb;
                sqlJj += hb;
                sqlFk += hb;
                sqlYy += hb;
                sqlDh += hb;
                break;
            case "3":
                sqlBd += xm;
                sqlJj += xm;
                sqlFk += xm;
                sqlYy += xm;
                sqlDh += xm;
                break;
            case "4":
                sqlBd += hx;
                sqlJj += hx;
                sqlFk += hx;
                sqlYy += hx;
                sqlDh += hx;
                break;
        }
        TtList chart1=selectSQL(sqlBd);//业务-报单
        TtList chart2=selectSQL(sqlJj);//进件-时长
        TtList chart3=selectSQL(sqlYy);//运营-抵押
        TtList chart4=selectSQL(sqlDh);//贷后-M3逾期
        TtList chart5=selectSQL(sqlFk);//风控-M1逾期

        obj[0] = chart1.get(0).get("years");
        obj[1] = chart1.get(0).get("amount");
        if(chart2.get(0).get("da").equals("")){
            obj[2] = "0";
        }else{
            obj[2] = chart2.get(0).get("da");
        }
        obj[3] = chart3.get(0).get("amount");
        obj[4] = chart4.get(0).get("amount");
        obj[5] = chart5.get(0).get("amount");


        return obj;
    }

}
