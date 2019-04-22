package com.tt.table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class orderutil {

    // 1.UUID生成十六位数唯一订单号
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
//         0 代表前面补充0
//         4 代表长度为4
//         d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    //2.时间+随机数
    public static String getOrderIdByTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        return newDate + result;
    }
//3.英文+0+id

    /**
     * @param name 前缀
     * @param num  长度
     * @param id   id
     * @return
     */
    public static String getOrderId(String name,
                                    int num,
                                    Object id
    ) {
        String num_count = "";
        int id_length = String.valueOf(id).length();
        num = num - id_length;
        for (int i = 0; i < num; i++) {
            num_count = num_count + "0";
        }
//System.out.println(name+num_count);
        return name + num_count + id;
    }

    public static void main(String[] args) {
        System.out.println("order0:" + getOrderId("ddbx", 7, 11));
        System.out.println("order1:" + getOrderIdByUUId());
        System.out.println("order2:" + getOrderIdByTime());
    }
}
