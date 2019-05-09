/*
 * @Description: In User Settings Edit
 * Ajax的get和post处理参考，如微信小程序get,post处理参考
 * 通用ajax提交处理
 * 创建日期：2018-11-16
 * @Author: tt
 * @Date: 2018-12-18 16:01:52
 * @LastEditTime: 2019-03-21 11:39:56
 * @LastEditors: tt
 */
package com.tt.tool;

import com.alibaba.fastjson.JSON;
import com.tt.data.TtList;
import com.tt.data.TtMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Ajax {
    /**
     * method为GET时处理代码演示 测试代码/ttAjax?cn=comm_citys&do=opt&state_id=23 cn为表名
     * do为操作类型,目前是只有OPT，获取<option></option>
     */
    @RequestMapping("/ttAjax")
    private String ShowAjax(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String result = "";
        TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
        String cn = post.get("cn");
        long id = 0L;
        try {
            id = Long.parseLong(post.get("id"));
        } catch (Exception E) {
            System.err.println(E.getMessage());
        }
        switch (post.get("do")) {
            case "opt":
                /**
                 * opt为操作类型中的获取某表里面的id和name的所有记录，条件由url参数决定，比如&state_id=23代表某表里面的state_id=23的所有记录
                 * 可加参数&id=104&re=json，id代表默认的选择项目(option模式有效,re=json时，返回json格式
                 */
                if (Tools.myIsNull(cn) != true) {
                    String re = post.get("re");
                    try {
                        post.remove("cn");
                        post.remove("do");
                        post.remove("id");
                        post.remove("re");
                    } catch (Exception E) {
                        System.err.println(E.getMessage());
                    }
                    result = Tools.dicopt(cn, id, post, re);
                }
                return result;
            case "info":// 查询某表某条记录的值
                if (Tools.myIsNull(cn) != true) {
                    String re = post.get("re");
                    if (re.equals("json")) {
                        TtMap ttMap = Tools.recinfo("select * from " + cn + " where id=" + id);
                        result = Tools.jsonEncode(ttMap);
                    } else {
                        TtMap ttMap = Tools.recinfo("select * from " + cn + " where id=" + id);
                        result = ttMap.toString();
                    }
                }
                return result;
            case "wxMini_list":// 微信小程序获取列表
                System.out.println(cn);
                String wxopenid = post.get("wx_openid");// 发送过来的微信openid
                switch (cn) {
                    case "car_stora":// 车辆入库列表
                        if (Tools.myIsNull(wxopenid) == false) {
                            TtMap mInfo = Tools.recinfo("select * from admin where wxopenid='" + wxopenid + "'");// 通过微信ID定位用户id
                            if (mInfo.size() > 0) {// 此用户存在，获取该用户所有提交的入库列表。
                                DbCtrl wxDb = new DbCtrl(cn);
                                TtList list = wxDb.lists("mid_add=" + mInfo.get("id"), "");
                                wxDb.closeConn();
                                List<Map<String, Object>> lmso = Tools.lssTolso(list);
                                lmso.get(0).put("list", list);
                                result = JSON.toJSONString(lmso);
                            }
                        }
                        break;
                }
                break;
        }
        return result;
    }

    /**
     * POST ,带object的字段
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/ttAjaxPost2", method = RequestMethod.POST)
    private Object showAjaxPost2(HttpServletRequest request) throws ServletException, IOException {
        Map<String, Object> result = new HashMap<>();
        formatResultobj(result, false, 999, "服务器异常");
        DbCtrl db = new DbCtrl("admin");
        TtList ls = db.lists();
        db.closeConn();
        ;
        formatResultobj(result, true, 0, "");
        result.put("info", ls);// info里面为List
        return result;
    }

    /**
     * POST
     *
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/ttAjaxPost", method = RequestMethod.POST)
    private String showAjaxPost(HttpServletRequest request) throws ServletException, IOException {
        TtMap post = Tools.getPostMap(request);// 过滤参数，过滤mysql的注入，url参数注入
        System.out.println(Tools.jsonEncode(post));
        String cn = post.get("cn") == null ? "" : post.get("cn");
        TtMap result2 = new TtMap();
        formatResult(result2, false, 999, "接口异常，请重试！");// 初始化返回
        String wxOpenid = "";
        switch (post.get("do")) {
            case "fileup":/** 文件上传处理 */
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                List<MultipartFile> fileList = multipartRequest.getFiles("upload_immm");// 固定为upload_immm的name
                if (fileList == null || fileList.size() == 0) {
                    result2.put("errormsg", "文件为空");
                    return Tools.jsonEncode(result2);
                }
                for (MultipartFile file : fileList) {// 支持多文件上传
                    String filename = Tools.getTimeMd5FileName() + "." + Tools.getFileExt(file.getOriginalFilename());// file.getOriginalFilename();
                    System.out.println(filename);
                    try {
                        String smallWidth = post.get("smallwidth");// 缩略图高
                        String smallHeight = post.get("smallheight");//// 缩略图宽
                        String shuiText = post.get("shuitext");//// 缩略图宽
                        int nSmallWidth = 0;
                        int nSmallHeight = 0;
                        if (Tools.myIsNull(smallWidth) == false) {
                            nSmallWidth = Integer.parseInt(smallWidth);
                        }
                        if (Tools.myIsNull(smallHeight) == false) {
                            nSmallHeight = Integer.parseInt(smallHeight);
                            if (nSmallHeight == 0 && nSmallWidth != 0) {
                                nSmallHeight = nSmallWidth;
                            }
                        }
                        FileUp fu = new FileUp();
                        /* 下面一行是设置文件保持路径，如不设置，就使用默认的在FileUp里面配置。 */
                        // fu.savePath =
                        // "/work/sd128/work/source/JAVA/springboot1/src/main/webapp/upload/";
                        /* 开始上传文件 */
                        result2 = fu.upFile(file, Tools.dirDate()/* 这个函数是格式话2018/11/23这种格式的路径 */ + filename, nSmallWidth,
                                nSmallHeight, shuiText);
                    } catch (Exception E) {
                        System.err.println(E.getMessage());
                    }
                }
                break;
            case "wxMini_Car_Store":/** 微信小程序车辆入库 */
                wxOpenid = post.get("wx_openid");
                System.out.println("wxopenid:" + wxOpenid);
                System.out.println("cn:" + cn);
                if (cn.equals("car_stora") && Tools.myIsNull(wxOpenid) == false) { //
                    System.out.println(wxOpenid);
                    String adminid = Tools.recinfo("select id from admin where wxopenid='" + wxOpenid + "'").get("id");
                    long nmid = 0;
                    if (Tools.myIsNull(adminid)) { // 演示：提交的微信openid不是会员,自动添加成会员，或者直接跳出绑定会员的窗口让微信小程序端绑定账号
                        DbCtrl tbMem = new DbCtrl("admin");
                        TtMap mpMem = new TtMap();
                        mpMem.put("wxopenid", wxOpenid);
                        mpMem.put("nickname", post.get("nickname"));
                        mpMem.put("cp", "3");
                        mpMem.put("showtag", "1");
                        mpMem.put("deltag", "0");
                        mpMem.put("isadmin", "0");
                        nmid = tbMem.add(mpMem);
                        tbMem.closeConn();
                    } else {
                        nmid = Long.parseLong(adminid);
                    }
                    System.out.println("nmid:" + nmid);
                    post.put("mid_add", Long.toString(nmid));
                    post.put("mid_edit", Long.toString(nmid));
                    DbCtrl testDb = new DbCtrl(cn);
                    long id = testDb.add(post);
                    if (id > 0) {// 添加成功
                        formatResult(result2, true, 0, "");
                        result2.put("id", Long.toString(id));
                    }
                    testDb.closeConn();
                }
                break;
        }
        return Tools.jsonEncode(result2);
    }

    private void formatResult(TtMap result, boolean success, int code, String msg) {
        result.put("success", success ? "true" : "false");
        result.put("errorcode", success ? "0" : String.valueOf(code));
        result.put("errormsg", msg);
    }

    private void formatResultobj(Map<String, Object> result, boolean success, int code, String msg) {
        result.put("success", success ? true : false);
        result.put("errorcode", success ? 0 : code);
        result.put("errormsg", msg);
    }
}
