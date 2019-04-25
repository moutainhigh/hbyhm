package com.tt.tlzf.servlet;

import com.tt.tlzf.Httpmodal.tlzfhttp;
import com.tt.tlzf.Httpmodal.tlzfhttp_v1;
import com.tt.tlzf.util.ExcelPoi;
import com.tt.tlzf.util.apiutil;
import com.tt.data.TtMap;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class KcdTlzfHttp {


    //method = RequestMethod.POST
    @RequestMapping(value = "/kcdhttp")
    @ResponseBody
    public String kcdhttp(HttpServletRequest request, HttpServletResponse response) {
        TtMap post = Tools.getPostMap(request);
        apiutil apiutil = new apiutil();
        //入库
/*        if (post.get("query").equals("1")) {
            if (post.get("type").equals("310001")) {
                return new tlzfhttp().kcd310001(request, response);
            } else if (post.get("type").equals("310002")) {
                return new tlzfhttp().kcd310002(request, response);
            } else if (post.get("type").equals("310011")) {
                return new tlzfhttp().kcd310011(request, response);
            } else if (post.get("type").equals("340009")) {
                return new tlzfhttp().kcd340009(request, response);
            } else if (post.get("type").equals("200004")) {
                return new tlzfhttp().kcd200004(request, response);
            } else {
                return "你好";
            }
        }*/
        //未入库
        if (post.get("query").equals("2")) {
            if (post.get("type").equals("310001")) {
                return new tlzfhttp_v1().kcd310001(request, response);
            } else if (post.get("type").equals("310002")) {
                return new tlzfhttp_v1().kcd310002(request, response);
            } else if (post.get("type").equals("310003")) {
                return new tlzfhttp_v1().kcd310003(request, response);
            } else if (post.get("type").equals("310011")) {
                return new tlzfhttp_v1().kcd310011(request, response);
            } else if (post.get("type").equals("340009")) {
                return new tlzfhttp_v1().kcd340009(request, response);
            } else if (post.get("type").equals("200004")) {
                return new tlzfhttp_v1().kcd200004(request, response);
            } else {
                return "你好";
            }
        }
        return "";
    }


}
