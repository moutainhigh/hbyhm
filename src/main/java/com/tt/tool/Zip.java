/*
 * @Description: zip文件/文件夹 ，并下载
 * @Author: tt
 * @Date: 2019-01-30 11:01:31
 * @LastEditTime: 2019-01-30 20:33:24
 * @LastEditors: tt
 */
package com.tt.tool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class Zip {
    @RequestMapping("/zip/demo")
    @ResponseBody
    public static void smain(String[] args) {
        if (Config.DEBUGMODE) {
            DbCtrl dbCtrl = new DbCtrl("admin");
            try {
                Map<String, String> info = dbCtrl.info(45);
                Map<String, String> imgs = new HashMap<>();
                imgs.put("个人头像1", info.get("avatarurl"));
                imgsToZipDown(imgs, info.get("name") + ".zip", "");
            } catch (Exception e) {
                Tools.logError(e.getMessage());
                e.printStackTrace();
            } finally {
                dbCtrl.closeConn();
            }
        }
    }

    /**
     * @param {type}
     * @throws IOException
     * @description: 把info里指定的所有文件复制到临时文件夹下，重命名为info.key，后缀名跟源文件后缀一样，然后把文件夹打包成zip并输出到浏览器下载
     * @return:
     */
    public static boolean imgsToZipDown(Map<String, String> info, String fileName, String baseSavePath)
            throws IOException {
        if (Tools.myIsNull(baseSavePath)) {
            baseSavePath = Config.FILEUP_SAVEPATH;
        }
        String tmpZipPath = baseSavePath + "zip/" + Tools.getTimeMd5FileName() + "/";
        System.out.println("开始zip:" + tmpZipPath);
        if (Tools.createDir(tmpZipPath)) { // 创建临时文件夹
            int succCount = 0;
            for (String key : info.keySet()) {
                String srcFilePath = baseSavePath + info.get(key);
                srcFilePath = srcFilePath.replaceFirst(Config.FILEUP_URLPRE, "/");
                srcFilePath = Tools.formatFilePath(srcFilePath);
                String dstFilePath = tmpZipPath + key + "." + Tools.extractFileExt(srcFilePath);
                dstFilePath = Tools.formatFilePath(dstFilePath);
                System.out.println("zip复制文件src:" + srcFilePath + ",dst:" + dstFilePath);
                if (Tools.ttCopyFile(srcFilePath, dstFilePath)) {
                    succCount++;
                }
            }
            System.out.println("zip succount:" + succCount);
            if (succCount > 0) {
                String tmpZipFileSavePath = baseSavePath + "zip/tmpzip/";
                String tmpZipFileFullPath = tmpZipFileSavePath + Tools.getNowDateFileName() + ".zip";
                if (Tools.createDir(tmpZipFileSavePath)) {
                    FileOutputStream fos1 = new FileOutputStream(new File(tmpZipFileFullPath));
                    ZipUtils.toZip(tmpZipPath, fos1, false);
                    System.out.println("ZIP完成:" + tmpZipFileFullPath);
                    fos1.close();
                    Tools.delTree(tmpZipPath);
                    if (Tools.fileExists(tmpZipFileFullPath)) {// 提供下载
                        System.out.println("ZIP完成开始提供下载:" + tmpZipFileFullPath);
                        boolean r = outDownFile(fileName, tmpZipFileFullPath, true);
                        Tools.delFile(tmpZipFileSavePath);
                        return r;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param {type} downFileName下载的文件名，realFilePath为真实存在于服务器上的文件,bDelOldFile是否删除源文件
     * @description: 下载文件，输出文件流到浏览器
     * @return:
     */
    public static boolean outDownFile(String downFileName, String realFilePath, boolean bDelOldFile) {
        try {
            // downFileName = new String(downFileName.getBytes(), "iso-8859-1"); //
            // 保证中文文件名不乱码
            downFileName = java.net.URLEncoder.encode(downFileName, "UTF-8");// 保证中文文件名不乱码
        } catch (UnsupportedEncodingException e1) {

            Tools.logError(e1.getMessage());
            if (Config.DEBUGMODE) {
                e1.printStackTrace();
            }
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.reset(); // 清除buffer缓存
        // response.setContentType("application/vnd.attachment;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + downFileName);
        OutputStream out;
        try {
            out = response.getOutputStream();
            InputStream in = new FileInputStream(realFilePath);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.close();
            if (bDelOldFile) {
                Tools.delFile(realFilePath);
            }
            return true;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }
}

class ZipUtils {
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
            // out.close();
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩成ZIP 方法2
     *
     * @param srcFiles 需要压缩的文件列表
     * @param out      压缩文件输出流
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(List<File> srcFiles, OutputStream out) throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[Config.FILEUP_WRITEBLOCKSIZE];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
            // out.close();
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile       源文件
     * @param zos              zip输出流
     * @param name             压缩后的名称
     * @param KeepDirStructure 是否保留原来的目录结构,true:保留目录结构;
     *                         false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure)
            throws Exception {
        byte[] buf = new byte[Config.FILEUP_WRITEBLOCKSIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }
}