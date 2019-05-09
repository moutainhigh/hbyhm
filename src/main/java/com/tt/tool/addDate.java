package com.tt.tool;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class addDate {

    /**
     * 日期相加减
     *
     * @param time 时间字符串 yyyy-MM-dd HH:mm:ss
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的年的日期
     * @throws ParseException
     */
    public static Date yearAddNum(Date time, Integer num) {
        /*SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(time);*/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.YEAR, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * @param time 时间
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的月份的日期
     * @throws ParseException Date
     */
    public static Date monthAddNum(Date time, Integer num) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = format.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }

    /**
     * @param time 时间
     * @param num  加的数，-num就是减去
     * @return 减去相应的数量的天的日期
     * @throws ParseException Date
     */
    public static Date dayAddNum(Date time, Integer num) {
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //Date date = format.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        Date newTime = calendar.getTime();
        return newTime;
    }


    /**
     * 获取本月第一天时间
     */
    public static Date getMonthStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取本月最后一天
     */
    public static Date getMonthEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    /****
     * 获取月末最后一天
     *
     * @param sDate
     *            2014-11-24
     * @return 30
     */
    public static String getMonthMaxDay(String sDate) {
        SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = sdf_full.parse(sDate + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        int last = cal.getActualMaximum(Calendar.DATE);
        return String.valueOf(last);
    }

    // 判断是否是月末
    public static boolean isMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DATE) == cal
                .getActualMaximum(Calendar.DAY_OF_MONTH))
            return true;
        else
            return false;
    }

    /***
     * 日期减一天、加一天
     *
     * @param option
     *            传入类型 pro：日期减一天，next：日期加一天
     * @param _date
     *            2014-11-24
     * @return 减一天：2014-11-23或(加一天：2014-11-25)
     */
    public static String checkOption(String option, String _date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        Date date = null;

        try {
            date = (Date) sdf.parse(_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cl.setTime(date);
        if ("pre".equals(option)) {
            // 时间减一天
            cl.add(Calendar.DAY_OF_MONTH, -1);

        } else if ("next".equals(option)) {
            // 时间加一天
            cl.add(Calendar.DAY_OF_YEAR, 1);
        } else {
            // do nothing
        }
        date = cl.getTime();
        return sdf.format(date);
    }

    /****
     * 传入具体日期 ，返回具体日期加一个月。
     *
     * @param date
     *            日期(2014-04-20)
     * @return 2014-03-20
     * @throws ParseException
     */
    public static String subMonth(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MONTH, 1);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            //System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2 - day1;
        }
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date();
        Date date2 = null;
        try {
            date2 = sdf.parse("2019-05-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("1:" + differentDays(date1, date2));
        System.out.println("2:" + differentDaysByMillisecond(date1, date2));

        if (differentDays(date1, date2) <= 3 && differentDays(date1, date2) > 0) {
            System.out.println("111111111111");
        }
/*        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds = "2019-01-31";
        String[] dates = ds.split("-");//获取年
        int year=Integer.parseInt(dates[0]);
        int month=Integer.parseInt(dates[1]);
        int day=Integer.parseInt(dates[2]);
        int first_days=Integer.parseInt(getMonthMaxDay(ds));
        for (int i = 1; i <= 12; i++) {
            String ds1="";
            int pm=month%12;

            if(pm>0){
                month=month+1;
            }else {
                month=1;
                year=year+1;
            }
            if(month<10){
                ds1=year+"-0"+month;
            }else{
                ds1=year+"-"+month;
            }
            int ym_days=Integer.parseInt(getMonthMaxDay(ds1));
            //System.out.println(ds1+"天数："+ym_days);
            try {
                boolean is= isMonthEnd(sdf.parse(ds));
                //System.out.println(is);
                if(is){
                    System.out.println("日期1："+ds1+"-"+ym_days);
                }else {
                   if(day>=ym_days){
                       System.out.println("日期2："+ds1+"-"+ym_days);
                   }else{
                       System.out.println("日期3："+ds1+"-"+day);
                   }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }*/

    }
}
