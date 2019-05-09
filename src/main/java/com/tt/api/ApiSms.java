/*
 * @说明: 短信发送类接口。目前使用国都的短信接口。
 * 后台链接：https://web.guodulink.net:8505/gdums/enterLogin，用户密码已经忘记。接口用户名和密码保存着呢
 * @Description: file content
 * @Author: tt
 * @Date: 2019-02-15 15:24:44
 * @LastEditTime: 2019-02-19 09:18:33
 * @LastEditors: tt
 */
package com.tt.api;

import java.util.HashMap;
import java.util.Map;

import com.tt.data.TtMap;
import com.tt.tool.HttpTools;
import com.tt.tool.Tools;

/**
 * 短信发送类 第三方短信接口方：国都
 */
public class ApiSms {
    private static String posturl = "http://124.251.7.68:8000/HttpQuickProcess/submitMessageAll";
    private static String postUsr = "kuaiched";
    private static String postPwd = "aG5u2stC";

    public static void main(String[] args) {
        mmsSend("18637815946", "测试一下！", "");
    }

    /**
     * 发送短信，支持群发
     *
     * @param tel 群发时每个手机号码以,为间隔。如果无,则表示非群发，只发送给一个目标号码。
     * @return: boolean 是否成功
     */
    public static boolean mmsSend(String tel, String msg, String postKey) {
        boolean r = false;
        postKey = !Tools.myIsNull(postKey) ? postKey : "【快加认证】";
        msg = postKey + msg;
        Map<String, Object> params = new HashMap<>();
        params.put("OperID", postUsr);
        params.put("OperPass", postPwd);
        // 'SendTime'=>'', //发送时间,YYYYMMDDHHMMSS格式,为空表示立即发送
        // 'ValidTime'=>'', //消息存活有效期,YYYYMMDDHHMMSS格式
        // 'AppendID'=>'',
        // //附加号码,例如用户在企信通短信平台中的身份标识的为13，若附加号码为0000，则接收号码收到的短信显示为来自spnumber+13+0000,否则不填附加短信标识，则显示为spnumber+13。并且：spnumber+企信通短信平台的短信身份标识+
        // AppendID的总长度不能大于20.
        params.put("DesMobile", tel);// 接收手机号集合,1.发送单条消息时，此字段填写11位的手机号码。2.群发消息时，此字段为使用逗号分隔的手机号码串。3.每批发送的手机号数量不得超过500
        params.put("Content", Tools.toGbk(msg));
        TtMap headers = new TtMap();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String re = HttpTools.httpClientPost(posturl, params, "UTF-8", headers);
        // 成功时返回类似03,f80385f026bcadae800，03为状态码，后面为订单号。
        r = re.contains("03,");
        System.out.println(re);
        return r;// 批量发送时，以最后一个发送的返回为准
    }
}