/*
 * @说明: 这里写说明哈
 * @Description: file content
 * @Author: tt
 * @Date: 2019-06-17 11:24:55
 * @LastEditTime: 2019-06-17 11:38:52
 * @LastEditors: tt
 */
package com.utils;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.pdf.BaseFont;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class PDFutil {

    public static boolean convertHtmlToPdf(String url, String outputFile)
            throws Exception {

        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        InputStream inputStream = new ByteArrayInputStream(Jsoup.connect(url).get().html().getBytes());
        String urlStr = IOUtils.toString(inputStream);
        renderer.setDocumentFromString(urlStr);

//        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(PDFutil.class.getResource("/")+ "/simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //解决图片的相对路径问题，绝对路径不需要写
//        renderer.getSharedContext().setBaseURL("file:/D:/");
        renderer.layout();
        renderer.createPDF(os);

        os.flush();
        os.close();
        return true;
    }


    public static void main(String[] args) {
         String  url=
                 "http://a.kcway.net/assess/manager/index.php?type=bclient&nav=0&query_type=0&bc_status=0&do=order_detail_querythjl&id=1764&action=check";
        try {
            Map map=new HashMap();
            map.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            map.put("Accept-Encoding","gzip, deflate");
            map.put("Accept-Language","zh-CN,zh;q=0.9");
            map.put("Cache-Control","no-cache");
            map.put("Connection","keep-alive");
            map.put("Cookie","PHPSESSID=ekg7vp7m79vppu1jhkidjdg5r4; mid=35f4ee3a687e30a08c5e7dde65472762");
            map.put("Host","a.kcway.net");
            map.put("Pragma","keep-alive");
            map.put("Upgrade-Insecure-Requests","1");
            map.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3724.8 Safari/537.36");
            String document = Jsoup.connect(url).headers(map).get().html();
            System.out.println(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
