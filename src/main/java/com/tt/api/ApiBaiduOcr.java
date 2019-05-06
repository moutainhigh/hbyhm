/*
 * @Description: 百度云API相关，OCR识别等
 * @Author: tt
 * @Date: 2019-02-12 17:18:08
 * @LastEditTime: 2019-02-27 13:28:41
 * @LastEditors: tt
 */
package com.tt.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import com.tt.api.apitool.FileUtil;
import com.tt.api.apitool.HttpUtil;
import com.tt.tool.Base64Tools;
import com.tt.tool.Tools;

import org.json.JSONObject;

/**
 * 获取token类
 */
public class ApiBaiduOcr {
  private static String token;
  private static long extime = 0;

  public static void main(String[] args) {
    bdOcrIdcard("/work/doc/psd/p1_1.jpg");
  }

  public ApiBaiduOcr() {
    //init();
  }

  public static void init() {
    if (extime <= 0 || Tools.time() >= extime) {
      getAuth();
    }
  }

  /**
   * 获取权限token
   * 
   * @return 返回示例： { "access_token":
   *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
   *         "expires_in": 2592000 }
   */
  public static String getAuth() {
    // 官网获取的 API Key 更新为你注册的
    String clientId = "UgzPizDX69F1HV1sxKaAXPnI";
    // 官网获取的 Secret Key 更新为你注册的
    String clientSecret = "eLi0D5r4pnGQXCFeTeSCgHI6duxfDWoT";
    return getAuth(clientId, clientSecret);
  }

  /**
   * 获取API访问token 该token有一定的有效期，需要自行管理，当失效时需重新获取.
   * 
   * @param ak - 百度云官网获取的 API Key
   * @param sk - 百度云官网获取的 Securet Key
   * @return assess_token 示例：
   *         "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
   */
  public static synchronized String getAuth(String ak, String sk) {
    // 获取token地址
    String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
    String getAccessTokenUrl = authHost
        // 1. grant_type为固定参数
        + "grant_type=client_credentials"
        // 2. 官网获取的 API Key
        + "&client_id=" + ak
        // 3. 官网获取的 Secret Key
        + "&client_secret=" + sk;
    try {
      URL realUrl = new URL(getAccessTokenUrl);
      // 打开和URL之间的连接
      HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
      connection.setRequestMethod("GET");
      connection.connect();
      // 获取所有响应头字段
      Map<String, List<String>> map = connection.getHeaderFields();
      // 遍历所有的响应头字段
      for (String key : map.keySet()) {
        System.err.println(key + "--->" + map.get(key));
      }
      // 定义 BufferedReader输入流来读取URL的响应
      BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String result = "";
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
      /**
       * 返回结果示例
       */
      System.err.println("result:" + result);
      JSONObject jsonObject = new JSONObject(result);
      String access_token = jsonObject.getString("access_token");
      token = access_token;
      extime = jsonObject.getLong("expires_in") + Tools.time();
      return access_token;
    } catch (Exception e) {
      System.err.printf("获取token失败！");
      e.printStackTrace(System.err);
    }
    return null;
  }

  /**
   * 百度身份证识别OCR
   */
  public static String bdOcrIdcard(String filePath) {
    String idcardIdentificate = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
    try {
      byte[] imgData = FileUtil.readFileByBytes(filePath);
      String imgStr = Base64Tools.encode(imgData);
      // 识别身份证正面id_card_side=front;识别身份证背面id_card_side=back;
      String params = "id_card_side=front&" + URLEncoder.encode("image", "UTF-8") + "="
          + URLEncoder.encode(imgStr, "UTF-8");
      /**
       * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
       */
      init();
      String accessToken = token;
      String result = HttpUtil.post(idcardIdentificate, accessToken, params);
      System.out.println(result);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

}
