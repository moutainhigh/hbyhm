<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
		TtMap infodb = (TtMap) request.getAttribute("infodb");
		long id_uplevel = 0;
		if (!Tools.myIsNull(infodb.get("id_uplevel"))){
			id_uplevel = Long.parseLong(infodb.get("id_uplevel"));
		}
%>
<head>
  <style>
    .form-horizontal table .checkbox-inline{padding-top: 0; font-size:14px;}
	</style>
</head>
<div class="admin-content box">
  <div class="box-header with-border">
    <h3 class="box-title">公司模块勾选</h3>
  </div>
  <div class="form-horizontal">
    <div class="box-body">
      <div class="form-group">
        <label for="name" class="col-sm-2 control-label">公司名称</label>
        <div class="col-sm-10">
          <input type="text" class="form-control" placeholder="" readonly name="name">
        </div>
      </div>
      <div class="form-group">
        <label for="link" class="col-sm-2 control-label">权限列表</label>
        <div class="col-sm-10">
          <table class="table table-bordered table-hover">
            <tbody>
              <tr>
                <th style="width: 140px"><label class="checkbox-inline"><input type="checkbox" class="check_all"
                      onclick="checkall(this,1)">全选</label></th>
                <th><label class="checkbox-inline"><input type="checkbox" class="check_all" onclick="checkall(this,2)">全选</label></th>
              </tr>
						<%
                Map<String,Object> menus = (Map<String,Object>)request.getAttribute("modals");
                for (String key :menus.keySet()){  //一级菜单循环开始
                    Map<String,Object>  mainList = (Map<String,Object>) menus.get(key);
                    TtList submenus =(TtList)mainList.get("submenu");
                    TtMap mainInfo = (TtMap)mainList.get("mainmenu");
                    String iconHtmlMain = mainInfo.get("icohtml");
                    iconHtmlMain = !Tools.myIsNull(iconHtmlMain)?iconHtmlMain:"<i class=\"fa fa-sitemap\"></i>";
										boolean haveAgp = (","+infodb.get("purview_map_ty")).indexOf(","+mainInfo.get("id")+",")!=-1;
                    String mainCheckName = "MAIN/"+mainInfo.get("id");
            %>
              <tr>
                <td><label class="checkbox-inline"><input type="checkbox" name="<%=mainCheckName%>" id="<%=mainCheckName%>"
                      value="<%=haveAgp?"1":"0"%>"  onclick="checkfl(this)" "<%=haveAgp?"checked":""%>" /><%=key%></label>
                </td>
                <td>
                <%
                    for (TtMap keysub :submenus){//二级级菜单循环开始
                        String cn = keysub.get("cn");
                        String type = keysub.get("type");
                        String sdo = keysub.get("sdo");
                        String icohtml = keysub.get("icohtml");
                        String subMenuName = keysub.get("showmmenuname");
												boolean haveAgp2 = (","+infodb.get("purview_map_ty")).indexOf(","+keysub.get("id")+",")!=-1;
                        String subCheckName = mainCheckName.replace("MAIN","SUBMAIN")+"/"+keysub.get("id");
                %>
                  <label class="checkbox-inline"><input type="checkbox" name="<%=subCheckName%>" id="<%=subCheckName%>"
                      value="<%=haveAgp2?"1":"0"%>" onclick="check(this)" />
                    <%=subMenuName%></label>
										<%}%>
                </td>
              </tr>
							<%}%>
            </tbody>
          </table>
        </div>
      </div>

    </div>
  </div>
</div>
<script>
$(document).ready(function() {
  input = document.getElementsByTagName("input");
  for (var i = 0; i < input.length; i++) {
    if ((input[i].type == "checkbox") ) {
      input[i].checked = input[i].value==1;
    }
  }
  $("#info_form").submit(function() {
    //alert($("input:checkbox[name='orders']").val());
    if (this.name.value.length == 0) {
      alert("请输入管理组名称");
      return false;
    }
  });
});

function check(obj) {
  obj.value = (obj.checked ? "1" : "0");
}

function checkall(obj, type) {
  if (type == 1) { //勾选大类
    input = document.getElementsByTagName("input");
    for (var i = 0; i < input.length; i++) {
      if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("MAIN/") == 0)) {
        input[i].checked = obj.checked;
        input[i].value = obj.checked ? 1 : 0;
      }
    }
  } else {
    input = document.getElementsByTagName("input");
    for (var i = 0; i < input.length; i++) {
      if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("SUBMAIN/") == 0)) {
        input[i].checked = obj.checked;
        input[i].value = obj.checked ? 1 : 0;
      }
    }

  }
}

function checkfl(obj) {
  obj.value = (obj.checked ? "1" : "0");
  input = document.getElementsByTagName("input");
  for (var i = 0; i < input.length; i++) {
    if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf(obj.name) >= 0)) {
      input[i].checked = obj.checked;
      input[i].value = obj.checked ? 1 : 0;
    }
  }
}
</script>