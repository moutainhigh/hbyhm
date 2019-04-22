<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="java.util.*" %>
<%
	String tmpstr = request.getParameter("id_uplevel ");
	int state_id = 0 ;
	if (Tools.myIsNull(tmpstr)==false){
		state_id =Integer.valueOf(tmpstr);
	}
	String kw = request.getParameter("kw");
	if (kw==null){
		kw = "";
	}
	String cn = request.getParameter("cn");
	String type = request.getParameter("type");
%>
<input type="hidden" name="cn" value="<%=cn%>" />
<input type="hidden" name="type" value="<%=type%>" />
<div class="form-group">
	<label>菜单级别</label>
	<select class="form-control" name="id_uplevel">
		<option value="0" <c:if test="${id_uplevel==0}"> selected="selected" </c:if>>请选择</option>
		<option value="1" <c:if test="${id_uplevel==1}"> selected="selected" </c:if>>一级菜单</option>
		<option value="2" <c:if test="${id_uplevel==2}"> selected="selected" </c:if>>二级菜单</option>
	</select>
</div>
<div class="form-group">
	<label>输入关键字:</label>
	<input type="text" name="kw" value="<%=kw%>" class="form-control" placeholder="显示菜单名称中包含">
</div>
<a type="submit" onclick="$('#search_form').submit()" class="btn btn-block btn-primary">搜索</a>