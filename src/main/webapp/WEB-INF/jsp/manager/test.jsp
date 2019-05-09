<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%-- head.jsp 页面--%>
<head>
    <meta charset="UTF-8">
    <title>车辆状况查询-新建</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover,user-scalable=0">
    <meta name="description" content="后台演示">
    <script src="${pageContext.request.contextPath}/manager/js/jQuery-2.1.4.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/js/jquery.form.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/js/common.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/js/fileupload.js?v=4.0" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/js/exif.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/js/megapic-image.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/dist/bootstrap/js/bootstrap.min.js"
            type="text/javascript"></script>

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/manager/dist/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="navbar">
    <div class="navbar-inner">
        <a class="brand" href="#">Title</a>
        <ul class="nav">
            <li class="active"><a href="#">首页</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
        </ul>
    </div>
</div>
<h1>Hello, world!</h1>
<p>
    <button class="btn btn-large btn-primary" type="button">Large button</button>
    <button class="btn btn-large" type="button">Large button</button>
</p>
<p>
    <button class="btn btn-primary" type="button">Default button</button>
    <button class="btn" type="button">Default button</button>
</p>
<p>
    <button class="btn btn-small btn-primary" type="button">Small button</button>
    <button class="btn btn-small" type="button">Small button</button>
</p>
<p>
    <button class="btn btn-mini btn-primary" type="button">Mini button</button>
    <button class="btn btn-mini" type="button">Mini button</button>
</p>
<div class="control-group error">
    <label class="control-label" for="inputError">Input with error</label>
    <div class="controls">
        <input type="text" id="inputError">
        <span class="help-inline">Please correct the error</span>
    </div>
</div>

<div class="control-group info">
    <label class="control-label" for="inputInfo">Input with info</label>
    <div class="controls">
        <input type="text" id="inputInfo">
        <span class="help-inline">Username is already taken</span>
    </div>
</div>

<div class="control-group success">
    <label class="control-label" for="inputSuccess">Input with success</label>
    <div class="controls">
        <input type="text" id="inputSuccess">
        <span class="help-inline">Woohoo!</span>
    </div>
</div>
</body>
</html>

