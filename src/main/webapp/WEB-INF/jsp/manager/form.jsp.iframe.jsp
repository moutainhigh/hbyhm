<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.JspTools" %>
<form id="info_form" action="<%=Tools.urlKill("id")%>" class="form-horizontal" method="post" enctype="multipart/form-data">
	<input type="hidden" id="id" name="id" value="${id }" />
	<!-- Content Wrapper. Contains page content -->
		<!-- Main content -->
		<section class="content">
			<%
				String errorMsg =(String) request.getAttribute("errorMsg");
				if (!Tools.myIsNull(errorMsg)){
					JspTools.alert(errorMsg,"404",out);
					return ;
				}
				errorMsg =(String) request.getAttribute("showmsg"); //如果有提示信息，就弹出显示一下了。
				if (!Tools.myIsNull(errorMsg)){
					JspTools.alert(errorMsg,out);
				}				
				String type =(String) request.getParameter("type");
				String cn =(String)  request.getParameter("cn");
				String sdo =(String)  request.getParameter("sdo");
				String msg =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".form.jsp";
				String info =request.getAttribute("info")!=null?String.valueOf(request.getAttribute("info")):null;
        String sHideButton =(String)request.getAttribute("sHideButton");
			%>
			<jsp:include page="<%=msg%>"></jsp:include>
		</section><% if (Tools.myIsNull(sHideButton)){%>
		<div class="footer-wrap">
			<div class="box-footer">
				<button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">取消返回</button>
				<button type="button" onclick="sure();" id="btnsure" class="btn btn-primary pull-right">保存提交</button>
			</div>
		</div><%}%>
	<!-- /.content-wrapper -->
</form>
<script>
function sure(){//todo
	var form = $("#info_form").get(0);
	$(form).ajaxSubmit(function(res){
			eval("var res=" + res);
			if (res.msg){
				alert(res.msg);
			}
			if (res.next_url){
				window.location.href=res.next_url;
			}
	});
}
</script>
<%if (!Tools.myIsNull(info)){%>
<script>
editFun(${info});
</script>
<%}%>


