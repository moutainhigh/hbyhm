package com.example.wx;

import com.tt.data.TtList;
import com.tt.data.TtMap;
import com.tt.tlzf.util.XmlExercise;
import com.tt.tool.Tools;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class wx_xcx {
    /**
     * 笨方法：String s = "你要去除的字符串";
     * <p>
     * 1.去除空格：s = s.replace('\\s','');
     * <p>
     * 2.去除回车：s = s.replace('\n','');
     * <p>
     * 这样也可以把空格和回车去掉，其他也可以照这样做。
     * <p>
     * 注：\n 回车(\u000a)
     * \t 水平制表符(\u0009)
     * \s 空格(\u0008)
     * \r 换行(\u000d)
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    @RequestMapping("/wx/xcx")
    @ResponseBody
    public String xcx(HttpServletRequest request, HttpServletResponse response) {
        TtMap post = Tools.getPostMap(request);
        int pageInt = Integer.valueOf(Tools.myIsNull(post.get("p")) == false ? post.get("p") : "1"); // 当前页
        int limtInt = Integer.valueOf(Tools.myIsNull(post.get("l")) == false ? post.get("l") : "10"); // 每页显示多少数据量
        int start = 0;
        if (pageInt == 0)
            limtInt = 1;
        if (start == 0)
            start = limtInt * (pageInt - 1);
        if (start < 0)
            start = 0;
        String r = " limit " + start + "," + limtInt;
        TtList jsonlist = Tools.reclist("select * from http_content" + r);
        //System.out.println(jsonlist);
        TtList newlist=new TtList();
        for(TtMap ttMap:jsonlist){
            TtMap newmap=new TtMap();
            newmap.put("title",ttMap.get("title")
                    .replaceAll("\"","")
                    .replaceAll("'","")
                    .replaceAll("<strong>","")
                    .replaceAll("</strong>","")
                    .replaceAll("&quot;",""));
            newmap.put("name",ttMap.get("name"));
            newmap.put("urlfrom",ttMap.get("urlfrom"));
            newmap.put("id",ttMap.get("id"));
            newmap.put("type",ttMap.get("type"));
            newmap.put("url",ttMap.get("url"));
            newmap.put("pn",ttMap.get("pn"));
            newlist.add(newmap);
        }
        String result=Tools.jsonEncode(newlist);
        return replaceBlank(result);
    }

}
