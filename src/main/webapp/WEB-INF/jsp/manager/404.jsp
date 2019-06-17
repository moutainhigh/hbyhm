<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.JspTools" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%@ page isErrorPage="true" %>
<%
    String errorMsg = (String) request.getAttribute("errorMsg");
    if (pageContext.getException() != null) {
        String msg = request.getRequestURL().toString() + "|" + request.getRequestURI() + "|" + request.getQueryString();
        msg = Tools.urlEncode(msg);
        Tools.logError(msg + ":" + pageContext.getException().getMessage(), true, false);
    }
%>
<%@include file="head.jsp" %>
<body class="skin-blue sidebar-mini fixed">
<div class="box-body">
    <div class="callout callout-danger">
        <h4>很抱歉，页面出错了！</h4>
        <p>请联系管理员。</p>
        <p>
        <h1>这个锅我不背！</h1>
        <%if (pageContext.getException() != null) {%>
        <table width="100%" border="1">
            <tr valign="top">
                <td width="40%"><b>Error:</b></td>
                <td>${pageContext.exception}</td>
            </tr>
            <tr valign="top">
                <td><b>URI:</b></td>
                <td>${pageContext.errorData.requestURI}</td>
            </tr>
            <tr valign="top">
                <td><b>Status code:</b></td>
                <td>${pageContext.errorData.statusCode}</td>
            </tr>
            <tr valign="top">
                <td><b>Stack trace:</b></td>
                <td>
                    <c:forEach var="trace" items="${pageContext.exception.stackTrace}">
                        <p>${trace}</p>
                    </c:forEach>
                </td>
            </tr>
        </table>
        <%}%>
        </p>
    </div>
    <%
        if (!Tools.myIsNull(errorMsg)) {
    %>
    <div class="callout callout-danger">
        <h4>错误信息</h4>

        <p>
            <%=errorMsg%>
        </p>
    </div>
    <%
        }
    %>
    <div class="callout callout-info">
        <h4>你确定你是正常打开链接进来的？</h4>

        <p>请检查您的操作。</p>
    </div>
    <div class="callout callout-warning">
        <h4>404!</h4>

        <p>404404404404404404404404404404404.</p>
    </div>
    <div class="callout callout-success">
        <h4>异常访问</h4>

        <p>异常访问异常访问异常访问异常访问异常访问</p>
    </div>
    <a type="button" href="javascript:history.go(-2);" class="btn btn-block btn-danger btn-lg">返回上一页</a>
    <a type="button" href="/manager/index?cn=homeHx&sdo=form&type=demo" class="btn btn-block btn-primary btn-lg">返回首页</a>
</div>
</body>