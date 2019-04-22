<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/21/021
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<% 
  String AdminLTE_Path = "/manager/AdminLTE-2.4.5/";
%>
<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | with iframe</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="iframe/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="iframe/dist/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="iframe/dist/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="iframe/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="iframe/dist/css/skins/all-skins.min.css">

  <!--http://aimodu.org:7777/admin/index_iframe.html?q=audio&search=#-->
  <%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
  <style type="text/css">
    html {
      overflow: hidden;
    }
  </style>
  <%}%>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="iframe/plugins/ie9/html5shiv.min.js"></script>
    <script src="iframe/plugins/ie9/respond.min.js"></script>
    <![endif]-->

<!-- jQuery 2.2.3 -->
<script src="iframe/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="iframe/bootstrap/js/bootstrap.min.js"></script>
<!-- Slimscroll -->
<script src="iframe/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="iframe/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="iframe/dist/js/app.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="iframe/dist/js/demo.js"></script>

<!--tabs-->
<script src="iframe/dist/js/app_iframe.js?ver=3"></script>
<link href="dist/css/style.css" rel="stylesheet" type="text/css" />

 <script src="js/common.js" type="text/javascript"></script>
  <script src="js/jquery.form.js" type="text/javascript"></script>
  <script src="js/ajaxfileupload.js" type="text/javascript"></script>
  <script type="text/javascript" src="js/fileupload.js?v=4.0"></script><%--这个必须--%>
  <script type="text/javascript" src="js/exif.js"></script><%--这个必须,处理图片用--%>
  <script type="text/javascript" src="js/megapic-image.js"></script><%--这个必须，压缩和处理图片用--%>
  <script src="js/index.js" type="text/javascript"></script>
</head>