<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.JspTools" %>
<div id="float_page_div">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="myModalLabel"></h4>
          </div>
          <div class="modal-body">
          	<form class="form-horizontal" id="float_form" action="<%=Tools.urlKill("id")%>" method="post">
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
				String type = request.getParameter("type");
				String cn = request.getParameter("cn");
				String sdo = request.getParameter("sdo");
				String msg =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".form.jsp";
				String info =String.valueOf(request.getAttribute("info"));
			%>
			<jsp:include page="<%=msg%>"></jsp:include>
          	</form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default pull-left" data-dismiss="modal" aria-label="Close">取消返回</button>
            <button type="submit" class="btn btn-danger" onclick="$('#float_form').submit()">保存提交</button>
          </div>
</div>
<script>
<%if (!Tools.myIsNull(info)){%>
  editFun(${info});
<% } %>
var float_submit=function (res){
  eval("var res=" + res);
			if (res.msg){
				alert(res.msg);
			}
			if (res.next_url){
				window.location.href=res.next_url;
			}
}
$('#float_form').submit(function (){
	if(typeof(float_form_check)=='function'){
		if(!float_form_check()){
			return false;
		}
	}
	
	$('#float_form').ajaxSubmit(float_submit); 
	return false;
});
my_loaded($('#float_form'));
html_load_succ($('#float_form'));
var float_load_succ_close=null;
function float_reload(html){
	$('#fancybox-content>div').html(html);
	$.fancybox.resize();
	float_load_succ();
}
var float_load_succ=function (){
	if(float_load_succ_close){
		float_load_succ_close=null;
		$.fancybox.close();
		return ;
	}
	$('#fancybox-content form.thickbox').ajaxForm(function (html){
		if(html){
			float_reload(html)
		}
	})
}
//setTimeout(float_load_succ,100);
</script>