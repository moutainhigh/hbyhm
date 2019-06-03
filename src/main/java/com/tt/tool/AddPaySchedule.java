package com.tt.tool;

import com.tt.data.TtMap;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

public class AddPaySchedule {


    /**
     * 生成客户还款计划
     * icbc 金额  期数  月还款额  首期还款日
     *     //    1.贷款信息表  pd.get("xx_xxzl")
     *     //    2.icbc_id  pd.get("icbc_id")
     *     //    3.分期数
     *     //    4.首期还款日  pd.get("yhdksh_61_sqhkr")
     *     //    5.月还
     *     //    6.生成还款计划表 pd.get("xxloan_repayment_schedule")
     */
    public Integer addPaySchedule(TtMap pd) {
        String yqsql = "select xx.c_loaninfo_dkze,xx.c_loaninfo_periods from "+pd.get("xx_xxzl")+" xx where icbc_id = " + pd.get("icbc_id");
        TtMap yqmap = new TtMap();
        yqmap = Tools.recinfo(yqsql);
        //计算月还
        String Myyh ="0";
        if (StringUtils.isNotEmpty(yqmap.get("c_loaninfo_dkze")) && StringUtils.isNotEmpty(yqmap.get("c_loaninfo_periods"))) {
            BigDecimal dk_total_price = new BigDecimal(yqmap.get("c_loaninfo_dkze")); //贷款总额
            BigDecimal aj_date = new BigDecimal(yqmap.get("c_loaninfo_periods"));    //贷款期限
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

        //获取基本信息
        String sql="select * from kj_icbc where id="+pd.get("icbc_id");
        TtMap getInfo = Tools.recinfo(sql);//通过icbc_id 获取用户基本信息
        int counts = Integer.parseInt(yqmap.get("c_loaninfo_periods")); //分期数
        String sqhkr = pd.get("yhdksh_61_sqhkr"); //首月还款日  "2019-01-25"
        int year = Integer.parseInt(sqhkr.substring(0,4));
        int month = Integer.parseInt(sqhkr.substring(5,7));
        int day = Integer.parseInt(sqhkr.substring(8,10));
        //生成还款计划
        TtMap addPS = new TtMap();
        addPS.put("icbc_id",pd.get("icbc_id"));
        System.err.println(getInfo.get("c_cardno")+"--99999");
        addPS.put("c_cardno",getInfo.get("c_cardno"));
        addPS.put("c_name",getInfo.get("c_name"));
        addPS.put("should_money",Myyh); //应还金额
        String should_data="xxxx-yy-mm";
        for(int i=0;i<counts;i++){
            if(month > 12){
                year = year+1;
                month=1;
            }
            should_data = year+"-"+month+"-"+day;
            addPS.put("should_date",should_data);
            addPS.put("overdue_which",i+1+"");
            Tools.recAdd(addPS,pd.get("xxloan_repayment_schedule"));
            month++;
        }
        return 0;
    }

    public static void main(String[] args) {
        //生成还款计划
        TtMap toS = new TtMap();
        toS.put("xx_xxzl","");//贷款信息表
        toS.put("icbc_id",""); //icbc_id
        toS.put("yhdksh_61_sqhkr","");  //首期还款日
        toS.put("xxloan_repayment_schedule",""); //生成还款计划表
        AddPaySchedule APS = new AddPaySchedule();
        APS.addPaySchedule(toS);
    }
}
