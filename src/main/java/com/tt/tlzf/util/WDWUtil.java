package com.tt.tlzf.util;

public class WDWUtil
{

    public static boolean isExcel2003(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xls)$");

    }

    /**
     *
     * @描述：是否是2007的excel，返回true是2007

     */

    public static boolean isExcel2007(String filePath)
    {

        return filePath.matches("^.+\\.(?i)(xlsx)$");

    }

}