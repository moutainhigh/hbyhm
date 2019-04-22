<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="java.util.*" %>
<%
	String dtbe = request.getParameter("dtbe");
	String kw = request.getParameter("kw");
	if (kw==null){
		kw = "";
	}
	if (dtbe==null){
		dtbe = "";
	}
	String cn = request.getParameter("cn");
	String type = request.getParameter("type");
%>
<input type="hidden" name="cn" value="<%=cn%>" />
<input type="hidden" name="type" value="<%=type%>" />
<div class="form-group">
	<label>时间区间</label>
	<div class="input-group">
		<input type="text" class="form-control daterange" name="dtbe" value="<%=dtbe%>" placeholder="区间">
		<div class="input-group-addon"><i class="fa fa-calendar"></i></div>
	</div>
</div>
<div class="form-group">
	<label>输入关键字:</label>
	<input type="text" name="kw" value="<%=kw%>" class="form-control" placeholder="姓名中包含字符">
</div>
<a type="submit" onclick="$('#search_form').submit()" class="btn btn-block btn-primary">搜索</a>