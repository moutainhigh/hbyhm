<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="java.util.*" %>
<%
    String kw = request.getParameter("kw");
    if (kw==null){
        kw = "";
    }
    String cn = request.getParameter("cn");
    String type = request.getParameter("type");
    String stateid = request.getParameter("stateid");
    String cityid = request.getParameter("cityid");
%>
<%
    //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
    String sp = Tools.dicopt("comm_states", 0);//省会，
%>
<input type="hidden" name="cn" value="<%=cn%>" />
<input type="hidden" name="type" value="<%=type%>" />
<div class="form-group">
    <label>选择省</label>
    <select name="stateid" id="stateid"  class="selectpicker  form-control" multiple data-live-search="true" data-max-options="1">
        <option value="0">请选择</option>
        <%=sp%>
    </select>
</div>
<div class="form-group">
    <label>选择市</label>
    <select name="cityid" id="cityid" class="form-control">
        <option value="0">请选择</option>
    </select>
</div>
<div class="form-group">
    <label>输入关键字:</label>
    <input type="text" name="kw" value="<%=kw%>" class="form-control" placeholder="显示菜单名称中包含">
</div>
<script>
    /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                            /ttAjax也可以单独使用，比如
                                            /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                            * */
    objacl('#stateid', '#cityid', '/ttAjax?do=opt&cn=comm_citys&state_id=', '<%=stateid%>', '<%=cityid%>');
</script>
<a type="submit" onclick="$('#search_form').submit()" class="btn btn-block btn-primary">搜索</a>