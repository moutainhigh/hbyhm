<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tt.tool.JspTools" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.Config" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%=Config.APP_TITLE + " " + Config.APP_VER%>
    </title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <script type="text/javascript">
        var _rooturl = "<%=Tools.urlKill("")%>";
        var page_cn = "<%=request.getParameter("cn")%>";
    </script>
    <!-- Bootstrap 3.3.4 -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <!-- Font Awesome Icons -->
    <!-- Font Awesome Icons -->
    <link href="icon/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
    <link href="js/select2/select2.min.css" rel="stylesheet" type="text/css"/>
    <link href="dist/css/ionicons.min.css" rel="stylesheet">
    <!-- Theme style -->
    <link href="dist/css/AdminLTE.css" rel="stylesheet" type="text/css"/>
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
              page. However, you can choose any other skin. Make sure you
              apply the skin class to the body tag so the changes take effect.
      -->
    <link href="dist/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="dist/css/skins/${cssName}.css" rel="stylesheet" type="text/css"/>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
          <script src="https://cdn.bootcss.com/html5shiv/r29/html5.min.js"></script>
          <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
      <![endif]-->
    <!-- jQuery 2.1.4 -->
    <script src="js/jQuery-2.1.4.min.js" type="text/javascript"></script>
    <script src="js/common.js" type="text/javascript"></script>
    <script src="js/jquery.form.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/php.js"></script>
    <!-- Bootstrap 3.3.2 JS -->
    <script src="bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="js/daterangepicker/moment.js" type="text/javascript"></script>
    <script src="js/daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <link href="js/daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css"/>
    <script src="js/datepicker/bootstrap-datepicker.js" type="text/javascript"></script>
    <link href="js/datepicker/datepicker3.css" rel="stylesheet" type="text/css"/>
    <script src="js/datepicker/locales/bootstrap-datepicker.zh-CN.js" type="text/javascript"></script>
    <script src="js/datetimepicker/bootstrap-datetimepicker.js" type="text/javascript"></script>
    <link href="js/datetimepicker/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>
    <script src="js/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
    <script src="js/select2/select2.min.js" type="text/javascript"></script>
    <script src="js/select2/i18n/zh-CN.js" type="text/javascript"></script>
    <!-- AdminLTE App -->
    <script src="dist/js/app.min.js" type="text/javascript"></script>
    <script src="dist/js/jQuery-slimScroll-1.3.8/jquery.slimscroll.js " type="text/javascript"></script>
    <!-- <script src="/comm/jquery.dragsort-0.4.min.js" type="text/javascript"></script> -->
    <link href="dist/css/iconfont.css" rel="stylesheet" type="text/css"/>
    <!-- 图片上传,文件上传js，这个js里默认的上传自动压缩和分辨率调整可以自行修改。 -->
    <script type="text/javascript" src="js/fileupload.js?v=4.0"></script>
    <!-- 图片扩展信息获取js -->
    <script type="text/javascript" src="js/exif.js"></script>
    <!-- 上传图片前图片自动旋转 -->
    <script type="text/javascript" src="js/megapic-image.js"></script>
    <!--  app.iframe -->
    <link href="css/common.css" rel="stylesheet" type="text/css"/>
    <script src="js/index.js" type="text/javascript"></script>
    <!-- 图片旋转放大等 -->
    <script src="js/jquery-photo-gallery/jquery.photo.gallery.js?ver=2"></script>
    <!-- 汉字拼音获取 -->
    <script src="js/namepy/namePY.js"></script>
    <!-- laydate 日期选择器 -->
    <script src="laydate/laydate.js"></script>
    <!--  select 下拉框 搜索匹配 -->
    <script src="bootstrap-select/js/bootstrap-select.js"></script>
    <link href="bootstrap-select/css/bootstrap-select.css" rel="stylesheet" type="text/css"/>
    <!-- bootstrap fileinput-->
    <%-- <script src="bootstrap-fileinput/js/fileinput.js"></script>
    <script src="bootstrap-fileinput/js/locales/zh.js"></script>
    <link href="bootstrap-fileinput/css/fileinput.css" rel="stylesheet" type="text/css"/> --%>
    <!-- 视频插件video.js-->
    <%-- <script src="vide7.4.1/js/video.min.js"></script>
    <link href="vide7.4.1/css/video-js.min.css" rel="stylesheet" type="text/css"/>	 --%>


</head>