/*
 * @Description: In User Settings Edit
 * 文件上传处理附带文件复制移动方法
 * 创建日期：2018-11-17
 * @Author: tt
 * @Date: 2018-12-07 10:46:47
 * @LastEditTime: 2019-02-11 16:16:25
 * @LastEditors: tt
 */
package com.tt.tool;

import com.tt.data.TtMap;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.*;

public class FileUp {
    public String savePath;
    public String urlPre;
    public int writeBlockSize;
    public String[] okExt;
    public long maxSize;
    public long maxSize_Admin;

    public FileUp() {
        savePath = Config.FILEUP_SAVEPATH;
        urlPre = Config.FILEUP_URLPRE;
        writeBlockSize = Config.FILEUP_WRITEBLOCKSIZE;
        okExt = Config.FILEUP_OKEXT;
        maxSize = Config.FILEUP_MAXSIZE;
        maxSize_Admin = Config.FILEUP_MAXSIZE_ISADMIN;
    }

    /**
     * todo 改为多线程处理 文件上传处理函数
     *
     * @param srcFile
     * @param toFile  为相对路径比如/2018/11/22/xxxxsddsa.jpg，实际保存的文件完整路径是savePath+tofile路径，
     * @return
     */
    public TtMap upFile(MultipartFile srcFile, String toFile, int smallWidth, int smallHeight,
                        String shuiText) {
        TtMap result = new TtMap();
        result.put("success", "false");
        result.put("errorcode", "999");
        result.put("msg", "上传出错,初始化出错！");
        try {
            String sExt = Tools.getFileExt(toFile).toUpperCase();
            if (Tools.arrayIndexOf(okExt, sExt) == false) {// 不允许的文件类型
                result.put("msg", "上传出错,不允许的文件类型！");
                result.put("errorcode", "998");
                return result;
            }
            long size = srcFile.getSize();
            if (size > (Tools.isAdmin() ? maxSize_Admin : maxSize)) {// 文件太大
                result.put("msg", "上传出错,文件尺寸超过指定数值！");
                result.put("errorcode", "997");
                return result;
            }
            String toFileFullPath = savePath + "/" + toFile;
            toFileFullPath = Tools.formatFilePath(toFileFullPath);
            System.out.println(toFileFullPath);
            if (Tools.delFile(toFileFullPath)) {// 目标文件如果已经存在，就删除旧文件，不存在，直接写入
                String s = Tools.delSpc(Tools.extractFilePath(toFileFullPath));
                System.out.println("crdir:" + s);
                if (!Tools.createDir(Tools.delSpc(Tools.extractFilePath(toFileFullPath)))) {
                    result.put("msg", "创建文件夹失败！");
                    result.put("errorcode", "996");
                    return result;
                }
                System.out.println(size);
                DataInputStream in = new DataInputStream(srcFile.getInputStream());
                OutputStream out = new FileOutputStream(toFileFullPath);
                byte[] bufferOut = new byte[writeBlockSize];
                int bytes = 0;
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                in.close();
                out.flush();
                out.close();
                if (Tools.fileExists(toFileFullPath)) {
                    if (smallWidth > 0) {// 生成缩略图
                        if (smallHeight <= 0) {
                            smallHeight = smallWidth;
                        }
                        /** 开始生成缩略图,支持水印，最后一个参数是本地水印图片完整路径，方法返回缩略图的相对url */
                        String sSmall = urlPre + ImgTools.small(toFileFullPath, smallWidth, smallHeight, toFile, "");
                        if (sSmall.equals("") == false) {
                            result.put("small", Tools.formatFilePath(sSmall).replace("\\", "/"));
                        }
                    }
                    if (Tools.myIsNull(shuiText) == false) {// 文字水印
                        Font font = new Font("YaHei Consolas Hybrid", Font.PLAIN, 35); // 水印字体
                        ImgTools.shuiyTxt(toFileFullPath, toFileFullPath, shuiText, new Color(255, 0, 0, 128), font);
                    }
                    result.put("success", "true");
                    result.put("errorcode", "0");
                    result.put("fileName", srcFile.getOriginalFilename());
                    result.put("fileSize", Long.toString(size));
                    result.put("url", Tools.formatFilePath(urlPre + toFile).replace("\\", "/"));
                    result.put("fileExt", sExt);
                    result.put("msg", "");// 成功时不输出错误信息
                }
            }
        } catch (Exception E) {
            System.err.println(E.getMessage());
            result.put("msg", "处理文件过程中异常,信息：" + E.getMessage());
            result.put("errorcode", "996");
        }
        return result;
    }

    /**
     * 字节流格式上传文件？？
     *
     * @param file
     * @param filePath
     * @param fileName
     * @throws Exception
     */
    public void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(Tools.formatFilePath(filePath + "/" + fileName));
        out.write(file);
        out.flush();
        out.close();
    }

    public void showmessage() {// 获取一些路径，参考用
        System.out.println("Java运行时环境版本:" + System.getProperty("java.version"));
        System.out.println("Java 运行时环境供应商:" + System.getProperty("java.vendor"));
        System.out.println("Java 供应商的URL:" + System.getProperty("java.vendor.url"));
        System.out.println("Java安装目录:" + System.getProperty("java.home"));
        System.out.println("Java 虚拟机规范版本:" + System.getProperty("java.vm.specification.version"));
        System.out.println("Java 类格式版本号:" + System.getProperty("java.class.version"));
        System.out.println("Java类路径：" + System.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表:" + System.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径:" + System.getProperty("java.io.tmpdir"));
        System.out.println("要使用的 JIT 编译器的名称:" + System.getProperty("java.compiler"));
        System.out.println("一个或多个扩展目录的路径:" + System.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称:" + System.getProperty("os.name"));
        System.out.println("操作系统的架构:" + System.getProperty("os.arch"));
        System.out.println("操作系统的版本:" + System.getProperty("os.version"));
        System.out.println("文件分隔符（在 UNIX 系统中是“/”）:" + System.getProperty("file.separator"));
        System.out.println("路径分隔符（在 UNIX 系统中是“:”）:" + System.getProperty("path.separator"));
        System.out.println("行分隔符（在 UNIX 系统中是“/n”）:" + System.getProperty("line.separator"));
        System.out.println("用户的账户名称:" + System.getProperty("user.name"));
        System.out.println("用户的主目录:" + System.getProperty("user.home"));
        System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
        System.out.println("当前的classpath的绝对路径的URI表示法:" + Thread.currentThread().getContextClassLoader().getResource(""));
        System.out.println("得到的是当前的classpath的绝对URI路径:" + FileUp.class.getResource("/"));
        System.out.println("得到的是当前类FileUp.class文件的URI目录:" + FileUp.class.getResource(""));
        File directory = new File("");// 参数为空
        String courseFile = null;// 标准的路径 ;
        try {
            courseFile = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String author = directory.getAbsolutePath();// 绝对路径;
        System.out.println(courseFile);
        System.out.println(author);
    }
}
