/*
 * @Description: file content
 * @desc [华云网GPS相关接口]，参考对应接口文档：华云网科技对外接口V1.1.1.docx
 * 测试环境:
 * 接口测试环境：地址: http://120.234.33.70:11009/get.html
 * username: testComcenter
 * password: Test123
 * srcnodetype: 202
 * 测试设备号: 000000000020019
 * @Author: tt
 * @Date: 2019-01-02 09:44:20
 * @LastEditTime: 2019-01-22 11:09:09
 * @LastEditors: tt
 */
package com.tt.api;

import java.util.Map;
import com.tt.tool.HttpTools;
import com.tt.tool.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ApiHyw {
  public final String API_URL = "http://120.234.33.70:11009/get.html";
  public final String API_USER = "testCommcenter";
  public final String API_PASS = "Test123";
  public final int API_SRCNODETYPE = 202;
  public final String API_TESTDEVICENO = "000000000020019";
  public final int API_SRCNODEID = 88;

  /**
   * @description: 测试函数
   * @param {type}
   * @return:
   */
  public String api_demo() {
    String sessionid = login(1).get("sessionid");
    sessionid = Tools.myIsNull(sessionid) ? "" : sessionid;
    System.out.println(this.getLastGpsInfo(API_TESTDEVICENO, Tools.getTimeMd5FileName(), sessionid, 3));
    String getGpsHistorySn = Tools.getTimeMd5FileName();
    System.out.println(this.getHistoryGpsInfo(API_TESTDEVICENO, getGpsHistorySn,
        Tools.time("2001-01-01 22:00:00", true), Tools.time("2019-01-01 22:00:00", true), sessionid, 6));
    System.out.println("get next:");
    System.out.println(this.getHistoryGpsInfoNext(API_TESTDEVICENO, getGpsHistorySn, sessionid, 7));
    return "";
  }

  /**
   * @description: 登录通信中心, 返回sessionID
   * @param {type}
   * @return:
   */
  public Map<String, String> login(int seqNo) {
    Map<String, String> result = null;
    try {
      long stamp = System.currentTimeMillis();
      String json = "{\"commbases\": [{\"id\": 1,\"seqno\": " + seqNo + ",\"content\": \"{\\\"username\\\": \\\""
          + this.API_USER + "\\\",\\\"password\\\": \\\"" + this.API_PASS + "\\\",\\\"stamp\\\": " + stamp
          + ",\\\"srcnodetype\\\": " + this.API_SRCNODETYPE + ",\\\"srcnodeid\\\": " + this.API_SRCNODEID
          + ",\\\"remotenodetype\\\": " + 114 + "}\"}]}";
      System.out.println(">>:" + json);
      String sResult = HttpTools.httpClientGet(this.API_URL, json, true); // 第三个参数超时时间
      JSONObject ss = JSONObject.fromObject(sResult);
      JSONArray ja = (JSONArray) ss.get("commbases");
      if (ja.size() > 0) {
        JSONObject job = ja.getJSONObject(0); // todo 如果数组有多条，需要遍历 jsonarray 数组，把每一个对象转成 json 对象
        result = Tools.jsonDeCode_mp(job.get("content").toString());
        System.out.println(result.toString());
      }
    } finally {

    }
    return result;
  }

  /**
   * @description: 获取GPS最后位置信息
   * @param {type}
   * @return:
   */
  public Map<String, String> getLastGpsInfo(String imei, String sn, String sessionid, int seqno) {
    Map<String, String> result = null;
    try {
      String json = "{\"commbases\":[{\"id\":4356,\"seqno\":" + seqno + ",\"content\":\"{\\\"imeis\\\":[\\\"" + imei
          + "\\\"],\\\"infotype\\\":4097,\\\"sn\\\": \\\"" + sn + "\\\",\\\"sessionid\\\": \\\"" + sessionid
          + "\\\"}\"}]}";
      System.out.println("getLastGpsInfo JSON:" + json);
      String sResult = HttpTools.httpClientGet(this.API_URL, json, true); // 第三个参数是否urlEncode
      JSONObject ss = JSONObject.fromObject(sResult);
      JSONArray ja = (JSONArray) ss.get("commbases");
      if (ja.size() > 0) {
        JSONObject job = ja.getJSONObject(0); // todo 如果数组有多条，需要遍历 jsonarray 数组，把每一个对象转成 json 对象
        result = Tools.jsonDeCode_mp(job.get("content").toString());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

  /**
   * 获取历史信息，第一页 3 sn 1 String 序列号(唯一ID) 需要保证唯一性 4 starttime 1 int 开始时间(从1970-1-1
   * 0:0:0开始的秒数，格林威治时间) 0:全部，1:卫星定位，2:混合定位, 3:全部定位(1+2)，默认3 5 endtime 1 int
   * 结束时间(从1970-1-1 0:0:0开始的秒数，格林威治时间) True不返回混合信息和车台源码 6 pageNumber ? int
   * 分页，每页最多条数（默认100条） 7 totalNumber ? int 最多取总条数（默认5000条） 8 locstate ? int 定位类型
   * 0:全部，1:卫星定位，2:混合定位, 3:全部定位(1+2)，默认3 9 norepeat ? bool
   * 重复不返回（GPSInfo，GPSBaseInfo信息） 默认false 10 motion ? bool 运动类型,
   * 如果只选择运动，连续静止点，只读第一个点 默认false
   */
  public Map<String, String> getHistoryGpsInfo(String imei, String sn, long startTime, long endTime, String sessionid,
      int seqno) {
    Map<String, String> result = null;
    try {
      String json = "{\"commbases\":[{\"id\":4353,\"seqno\":" + seqno + ",\"content\":\"{\\\"imei\\\":\\\"" + imei
          + "\\\",\\\"infotype\\\":4097,\\\"sn\\\": \\\"" + sn + "\\\",\\\"starttime\\\":" + startTime
          + ",\\\"endtime\\\":" + endTime
          + ",\\\"pageNumber\\\":10,\\\"totalNumber\\\":100,\\\"locstate\\\": 3,\\\"norepeat\\\": false,\\\"motion\\\": false,\\\"sessionid\\\": \\\""
          + sessionid + "\\\"}\"}]}";
      System.out.println(">>:" + json);
      String sResult = HttpTools.httpClientGet(this.API_URL, json, true); // 第三个参数是否urlEncode
      JSONObject ss = JSONObject.fromObject(sResult);
      System.out.println(ss);
      JSONArray ja = (JSONArray) ss.get("commbases");
      if (ja.size() > 0) {
        JSONObject job = ja.getJSONObject(0); // todo 如果数组有多条，需要遍历 jsonarray 数组，把每一个对象转成 json 对象
        result = Tools.jsonDeCode_mp(job.get("content").toString());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }

  /**
   * @description: 获取下一页历史数据
   * @param {type}
   * @return:
   */
  public Map<String, String> getHistoryGpsInfoNext(String imei, String sn, String sessionid, int seqno) {
    Map<String, String> result = null;
    try {
      String json = "{\"commbases\":[{\"id\":4354,\"seqno\":" + seqno + ",\"content\":\"{\\\"imei\\\":\\\"" + imei
          + "\\\",\\\"infotype\\\":4097,\\\"sn\\\": \\\"" + sn + "\\\",\\\"sessionid\\\": \\\"" + sessionid
          + "\\\"}\"}]}";
      System.out.println(">>:" + json);
      String sResult = HttpTools.httpClientGet(this.API_URL, json, true); // 第三个参数是否urlEncode
      JSONObject ss = JSONObject.fromObject(sResult);
      System.out.println(ss);
      JSONArray ja = (JSONArray) ss.get("commbases");
      if (ja.size() > 0) {
        JSONObject job = ja.getJSONObject(0); // todo 如果数组有多条，需要遍历 jsonarray 数组，把每一个对象转成 json 对象
        result = Tools.jsonDeCode_mp(job.get("content").toString());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return result;
  }
}