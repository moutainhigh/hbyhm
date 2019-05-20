package com.tt.tool;

import com.tt.data.TtMap;

public class AddPaySchedule {

    public Integer addPaySchedule(TtMap pd) {
        /**
         * 生成客户还款计划
         * icbc 金额  期数  月还款额  首期还款日
         */
//        TtMap toS = new TtMap();
//        toS.put("icbc_id",post.get("icbc_id"));
//        toS.put("yhdksh_61_je", post.get("yhdksh_61_je")); //金额
//        toS.put("yhdksh_61_syhk",post.get("yhdksh_61_syhk")); //首月还款
//        toS.put("yhdksh_61_fq",post.get("yhdksh_61_fq"));//分期数
//        toS.put("yhdksh_61_sqhkr", post.get("firstMonthPayDate"));  //首期还款日
//        toS.put("yhdksh_61_yh",post.get("yhdksh_61_yh")); //月还
        //////////////
        String sql="select * from kj_icbc where id="+pd.get("icbc_id");
        TtMap getInfo = Tools.recinfo(sql);//通过icbc_id 获取用户基本信息
        //获取放款成功后的字段信息
        //pd.getString("yhdksh_61_je"); //贷款金额
        //pd.getString("yhdksh_61_syhk"); //首月还款
        int counts = Integer.parseInt(pd.get("yhdksh_61_fq")); //分期数
        String sqhkr = pd.get("yhdksh_61_sqhkr"); //首月还款日  "2019-01-25"
        int year = Integer.parseInt(sqhkr.substring(0,4));
        int month = Integer.parseInt(sqhkr.substring(5,7));
        int day = Integer.parseInt(sqhkr.substring(8,10));
        String yh = pd.get("yhdksh_61_yh"); // 月还
        //生成还款计划
        TtMap addPS = new TtMap();
        addPS.put("icbc_id",pd.get("icbc_id"));
        System.err.println(getInfo.get("c_cardno")+"--99999");
        addPS.put("c_cardno",getInfo.get("c_cardno"));
        addPS.put("c_name",getInfo.get("c_name"));
        addPS.put("should_money",yh); //应还金额
        String should_data="xxxx-yy-mm";
        for(int i=0;i<counts;i++){
            if(month > 12){
                year = year+1;
                month=1;
            }
            should_data = year+"-"+month+"-"+day;
            addPS.put("should_date",should_data);
            addPS.put("overdue_which",i+1+"");
            Tools.recAdd(addPS,"loan_repayment_schedule");
            month++;
        }
        return 0;
    }
}
