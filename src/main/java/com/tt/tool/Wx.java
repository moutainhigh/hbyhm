/*
 * @Description: 微信公众号和小程序相关处理类
 * @Author: tt
 * @Date: 2018-12-14 16:13:00
 * @LastEditTime: 2019-02-22 14:09:24
 * @LastEditors: tt
 */
package com.tt.tool;

/**
 * 微信公众号和小程序处理工具
 * 必须结合DbCtrl使用，读取当前DbCtrl里面的数据源的
 * this.tbWxConfig表里面name为defWxName的记录里面设置的appid和appsecret为当前
 * 公众号的appid和appsecret，同时，如果有获取assess_token，将把最新获取到的assess_token保存在此条记录中
 * this.tbWxConfig表结果如下
 * id
 * appid
 * appsecret
 * token
 * dt_add
 * dt_edit
 * remark
 * name
 * expirestime
 * type
 * 创建日期：2018-11-29
 * 最后更新：2018-12-12
 */

import com.tt.data.TtMap;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class Wx {
    // 网页授权获取code
    public final String GetPageCode = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=URL&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";
    // 网页授权接口
    public final String GetPageAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 网页授权得到用户基本信息接口
    public final String GetPageUsersUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
    // 小程序通过code获取openid
    public final String GetWxMiniOpenId = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    // 保存appid和appsecret信息的表名
    public String tbWxConfig = "tt_wxconfig";
    // 本次处理的微信公众号/小程序号的name为defWxName的记录
    public String defWxName = "mytest";

    /**
     * 获取微信小程序的openid和sessionkey
     *
     * @param code
     * @param defWxName
     * @return
     */
    public TtMap ttWxMini_GetOpenId(String code, String defWxName) {
        TtMap result = new TtMap();
        TtMap info = this.getWxConfig(defWxName);
        String requestUrl = this.GetWxMiniOpenId.replace("APPID", info.get("appid"))
                .replace("SECRET", info.get("appsecret")).replace("JSCODE", code);
        JSONObject jsonObject = HttpTools.httpsRequest(requestUrl, "GET", null);
        try {
            result.put("openid", jsonObject.getString("openid"));
            result.put("session_key", jsonObject.getString("session_key"));
            result.put("unionid", jsonObject.getString("unionid"));// 如果有返回
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    /**
     * 网页授权登陆方式获取用户Code，然后自动跳转到/ttwxcode页用code获取openid和UserInfo
     *
     * @param extUrl
     * @param request
     * @param response
     */
    public void tt_GetWxCode(String extUrl, HttpServletRequest request, HttpServletResponse response) {
        extUrl = extUrl + "&defWxName=" + this.defWxName;
        String getCodeUrl = "";
        try {
            TtMap info = this.getWxConfig(this.defWxName);
            String appid = info.get("appid");
            getCodeUrl = URLEncoder.encode(Tools.getBaseUrl() + "ttwxcode?" + extUrl, "utf-8");
            String requestUrl = GetPageCode.replace("URL", getCodeUrl).replace("APPID", appid);
            System.out.println(requestUrl);
            try {
                response.sendRedirect(requestUrl);
                // request.getRequestDispatcher(requestUrl).forward(request, response);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            // 获取Code
            // HttpTools.httpsRequest(requestUrl, "GET", null);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            // e.printStackTrace();
        }
    }

    /**
     * 得到微信用户信息 微信网页授权方式。
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public TtMap tt_getUserInfo(String accessToken, String openId) {
        TtMap weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = GetPageUsersUrl;
        return getStringStringMap(accessToken, openId, weixinUserInfo, requestUrl);
    }

    private TtMap getStringStringMap(String accessToken, String openId,
                                     TtMap weixinUserInfo, String requestUrl) {
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = HttpTools.httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                weixinUserInfo = new TtMap();
                // 用户的标识
                weixinUserInfo.put("openid", jsonObject.getString("openid"));
                // 昵称
                weixinUserInfo.put("nickname", jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.put("sex", String.valueOf(jsonObject.getInt("sex")));
                // 用户所在国家
                weixinUserInfo.put("country", jsonObject.getString("country"));
                // 用户所在省份
                weixinUserInfo.put("province", jsonObject.getString("province"));
                // 用户所在城市
                weixinUserInfo.put("city", jsonObject.getString("city"));
                // 用户头像
                weixinUserInfo.put("headimgurl", jsonObject.getString("headimgurl"));
                try {
                    weixinUserInfo.put("subscribe", jsonObject.getString("subscribe"));
                } catch (Exception e) {
                    System.err.println("出错：" + e.getMessage());
                }
            } catch (Exception e) {
                System.err.println("出错：" + e.getMessage());
            }
        }
        return weixinUserInfo;
    }

    /**
     * 获取用户信息，通过正式的access_token和openid获取用户信息。
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public TtMap ttWx_getUserInfo(String accessToken, String openId) {
        TtMap weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        return getStringStringMap(accessToken, openId, weixinUserInfo, requestUrl);
    }

    /**
     * Mapping一个根路径的/ttwxcode页面，接收来自腾讯的回调数据code，然后用code拿assess_token和openid,最后用assess_token和openid拿用户的userinfo，包括头像，性别等数据
     *
     * @param 得到网页授权凭证和用户openid,通过code
     * @return
     */
    @RequestMapping("/ttwxcode")
    public Map<String, Object> tt_oauth2GetOpenid(HttpServletRequest request, HttpServletResponse responses) {
        TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
        String code = post.get("code");
        if (Tools.myIsNull(post.get("defWxName")) == false) {
        }
        TtMap info = this.getWxConfig(this.defWxName);
        String appid = info.get("appid");
        String appsecret = info.get("appsecret");
        System.out.println("id:" + appid + ",SECRET:" + appsecret);
        String requestUrl = GetPageAccessTokenUrl.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE",
                code);
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            CloseableHttpClient client = HttpClientBuilder.create().setProxy(null).build();
            JSONObject OpenidJSONO = getJsonObject(requestUrl, client);
            // OpenidJSONO可以得到的内容：access_token expires_in refresh_token openid scope
            String Openid = String.valueOf(OpenidJSONO.get("openid"));
            String AccessToken = String.valueOf(OpenidJSONO.get("access_token"));
            // String Scope = String.valueOf(OpenidJSONO.get("scope"));//用户保存的作用域
            // String refresh_token = String.valueOf(OpenidJSONO.get("refresh_token"));
            TtMap wxUserInfo = this.tt_getUserInfo(AccessToken, Openid);
            result.put("Openid", Openid);
            result.put("AccessToken", AccessToken);
            result.put("wxinfo", wxUserInfo.toString());
            // result.put("scope", Scope);
            // result.put("refresh_token", refresh_token);
            HttpSession session = request.getSession();
            session.setAttribute("ttopenid", Openid);
            String sreturn = post.get("return");
            if (Tools.myIsNull(sreturn) == false) { // 如果传过来有return参数，网页中打开return
                responses.sendRedirect(sreturn);
            }
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
        }
        return result;
    }

    private JSONObject getJsonObject(String requestUrl, HttpClient client) throws java.io.IOException {
        HttpGet httpget = new HttpGet(requestUrl);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(httpget, responseHandler);
        return JSONObject.fromObject(response);
    }

    /**
     * 获取token 要注意的是这个token跟网页授权的token不一样。官方解释如下
     * 关于网页授权access_token和普通access_token的区别
     * 1、微信网页授权是通过OAuth2.0机制实现的，在用户授权给公众号后，公众号可以获取到一个网页授权特有的接口调用凭证（网页授权access_token），通过网页授权access_token可以进行授权后接口调用，如获取用户基本信息；
     * 2、其他微信接口，需要通过基础支持中的“获取access_token”接口来获取到的普通access_token调用。
     */
    private String doGetToken(String appid, String appsecret) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        TtMap info = this.getWxConfig(this.defWxName);
        requestUrl = requestUrl.replace("APPID", info.get("appid")).replace("APPSECRET", info.get("appsecret"));
        String result = "";
        try {
            @SuppressWarnings({"all"})
            //HttpClient client = new DefaultHttpClient();
            CloseableHttpClient client = HttpClientBuilder.create().setProxy(null).build();
            JSONObject OpenidJSONO = getJsonObject(requestUrl, client);
            client.close();
            return String.valueOf(OpenidJSONO.get("access_token"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return result;
    }

    /**
     * 获取assess_token并保存到数据库里，如果数据库里已经有此assess_token，直接获取数据库里的
     *
     * @return
     */
    public String ttWx_GetToken(boolean bMustRefresh) {
        String result = "";
        DbCtrl dbCtrl = new DbCtrl(this.tbWxConfig);
        TtMap info = this.getWxConfig(this.defWxName);
        System.out.println(info.toString());
        try {
            if (info.size() > 0) {
                if (bMustRefresh == false // 不是必须刷新
                        && Tools.myIsNull(info.get("expirestime")) == false
                        && Integer.parseInt(info.get("expirestime")) > Tools.time()
                        && Tools.myIsNull(info.get("token")) == false) {// 还没过期
                    result = info.get("token");
                } else {
                    String token = doGetToken(info.get("appid"), info.get("appsecret"));
                    if (Tools.myIsNull(token) == false) {
                        if (Tools.myIsNull(info.get("dt_add"))) {
                            info.put("dt_add", Tools.dateToStrLong(null));
                        }
                        info.put("token", token);
                        long ex = Tools.time() + 2 * 60 * 60 - 10 * 60; // 过期时间2小时减去10分钟
                        info.put("expirestime", Long.toString(ex));
                        dbCtrl.edit(info, Long.parseLong(info.get("id")));// 保存
                        result = token;
                    }
                }
            }
        } finally {
            dbCtrl.closeConn();
        }
        System.out.println("获取到的token:" + result);
        return result;
    }

    /**
     * 从数据库里读取微信公众号配置信息
     */
    private TtMap getWxConfig(String wxName) {
        String sql = "select * from " + this.tbWxConfig + " where name='" + wxName + "'";
        System.out.println(sql);
        return Tools.recinfo(sql);
    }

    /**
     * @param request
     * @Description: 发起微信支付
     */
    @RequestMapping("/ttwxpay")
    public Map<String, Object> wxPay(HttpServletRequest request) {
        try {
            TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
            String openid = post.get("openid");
            // 生成的随机字符串
            String nonce_str = Tools.getRandomStringByLength(32);
            // 商品名称
            String body = "测试商品名称";
            // 获取客户端的ip地址
            String spBillCreateIp = HttpTools.getIpAddress(request);
            String notify_Url = Tools.getBaseUrl() + "ttwxpay_notifyurl";
            String payType = "JSAPI";
            String payUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
            // 组装参数，用户生成统一下单接口的签名
            TtMap packageParams = new TtMap();
            TtMap info = this.getWxConfig(defWxName);
            packageParams.put("appid", info.get("appid"));
            packageParams.put("mch_id", info.get("pay_mch_id"));
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            // todo 订单号需要进行修改并保存到数据库里。
            packageParams.put("out_trade_no", "123456789");// 商户订单号
            packageParams.put("total_fee", "1");// 支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spBillCreateIp);
            packageParams.put("notify_url", notify_Url);// 支付成功后的回调地址
            packageParams.put("trade_type", payType);// 支付方式
            packageParams.put("openid", openid);
            String prestr = HttpTools.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            // MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = Tools.md5(prestr + "&key=" + info.get("pay_key")).toUpperCase();
            // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + info.get("appid") + "</appid>" + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + info.get("pay_mch_id") + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>"
                    + "<notify_url>" + notify_Url + "</notify_url>" + "<openid>" + openid + "</openid>"
                    + "<out_trade_no>" + "123456789" + "</out_trade_no>" + "<spbill_create_ip>" + spBillCreateIp
                    + "</spbill_create_ip>" + "<total_fee>" + "1" + "</total_fee>" + "<trade_type>" + payType
                    + "</trade_type>" + "<sign>" + mysign + "</sign>" + "</xml>";
            System.out.println("pay_XML：" + xml);
            // 调用统一下单接口，并接受返回的结果
            String result = HttpTools.httpsRequest(payUrl, "POST", xml).toString();
            System.out.println("调试模式_统一下单接口 返回XML数据：" + result);
            // 将解析结果存储在HashMap中
            TtMap map = HttpTools.doXMLParse(result);
            String return_code = (String) map.get("return_code");// 返回状态码
            Map<String, Object> response = new HashMap<String, Object>();// 返回给小程序端需要的参数
            if (return_code.equals("SUCCESS")) {
                String prepay_id = (String) map.get("prepay_id");// 返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                // 拼接签名需要的参数
                String stringSignTemp = "appId=" + info.get("appid") + "&nonceStr=" + nonce_str + "&package=prepay_id="
                        + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                // 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = Tools.md5(stringSignTemp + "&key=" + info.get("pay_key")).toUpperCase();
                response.put("paySign", paySign);
            }
            response.put("appid", info.get("appid"));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     * @Description:微信支付
     */
    @RequestMapping(value = "/ttwxpay_notifyurl")
    @ResponseBody
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        // sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        System.out.println("接收到的报文：" + notityXml);
        TtMap map = HttpTools.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        if ("SUCCESS".equals(returnCode)) {
            // 验证签名是否正确
            TtMap validParams = HttpTools.paraFilter((TtMap)map); // 回调验签时需要去除sign和空值参数
            String validStr = HttpTools.createLinkString(validParams);// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            TtMap info = this.getWxConfig(defWxName);
            String sign = Tools.md5(validStr + "&key=" + info.get("pay_key")).toUpperCase();// 拼装生成服务器端验证的签名
            // 根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if (sign.equals(map.get("sign"))) {
                /** todo,支付成功后要执行的一些操作。比如入库什么的。begin **/
                /** todo end **/
                // 通知微信服务器已经支付成功
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            }
        } else {
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        System.out.println(resXml);
        System.out.println("微信支付回调数据结束");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    public String wxTempMessage(Map<String, Object> params) {
        String result;
        /**
         * 发送模板消息，小程序和微信公众号的模板消息有所区别 小程序模板数据示例 var temp = { "touser": touser,//用户的openid
         * "template_id": template_id,//模板id "page": "", "form_id": formid,//表单id
         * "data": { "keyword1": { "value": title, "color": "#173177" }, "keyword2": {
         * "value": gettime() }, }, "emphasis_keyword": "keyword1.DATA" //将keyword1放大 }
         * 微信公众号模板数据示例 { "touser":"OPENID",
         * "template_id":"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY",
         * "url":"http://weixin.qq.com/download", "topcolor":"#FF0000", "data":{ "User":
         * { "value":"黄先生", "color":"#173177" } } }
         */
        TtMap info = this.getWxConfig(defWxName);
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=TOKEN";// 小程序的
        if (info.get("type").equals("0")) { // 数据库里type为0表示为微信公众号，1为小程序，微信公众号地址不一样
            url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=TOKEN";
        }
        String token = this.ttWx_GetToken(false);
        url = url.replace("TOKEN", token);
        result = HttpTools.httpClientPost(url, Tools.jsonEncode(params));
        return result;
    }
}
