<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.Config" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%
  String urlPre = Config.FILEUP_URLPRE;
%>
<a href="<%=urlPre%>log/tt_debug.log" class="btn btn-default"><i class="fa fa-arrow-circle-o-down"></i>下载Debug日志</a>
<a href="<%=urlPre%>log/tt_error.log" class="btn btn-default"><i class="fa fa-arrow-circle-o-down"></i>下载Error日志</a>