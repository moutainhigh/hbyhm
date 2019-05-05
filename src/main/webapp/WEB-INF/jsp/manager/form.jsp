<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.JspTools" %>
<form id="info_form" action="<%=Tools.urlKill("id")%>" class="form-horizontal" method="post"
      enctype="multipart/form-data">
    <input type="hidden" id="id" name="id" value="${id }"/>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper fixed-footer">
        <!-- Main content -->
        <section class="content" id="pro_show">
            <%
                String errorMsg = (String) request.getAttribute("errorMsg");
                if (!Tools.myIsNull(errorMsg)) {
                    JspTools.alert(errorMsg, "404", out);
                    return;
                }
                errorMsg = (String) request.getAttribute("showmsg"); //如果有提示信息，就弹出显示一下了。
                if (!Tools.myIsNull(errorMsg)) {
                    JspTools.alert(errorMsg, out);
                }
                String type = (String) request.getParameter("type");
                String cn = (String) request.getParameter("cn");
                String sdo = (String) request.getParameter("sdo");
                String msg = "/WEB-INF/jsp/manager/" + type + "/" + cn + ".form.jsp";
                String info = request.getAttribute("info") != null ? String.valueOf(request.getAttribute("info")) : null;
                String sHideButton = (String) request.getAttribute("sHideButton"); //隐藏保存提交和取消返回标志，非空时隐藏
            %>
            <jsp:include page="<%=msg%>"></jsp:include>
        </section>
        <% if (Tools.myIsNull(sHideButton)) {%>
        <div class="footer-wrap">
            <div class="box-footer">
                <button type="button" class="btn btn-default" onclick="javascript:history.go(-1);">取消返回</button>
                <button type="button" onclick="sure();" id="btnsure" class="btn btn-primary pull-right">保存提交</button>
            </div>
        </div>
        <%}%>
    </div>
    <!-- /.content-wrapper -->
</form>
<script>
    var isCommitted = false;//表单是否已经提交标识，默认为false
    function sure() {//todo
        var form = $("#info_form").get(0);
        if (isCommitted == false) {
            isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
            $(form).ajaxSubmit(function (res) {
                eval("var res=" + res);
                if (res.msg) {
                    alert(res.msg);
                }
                if (res.next_url) {
                    window.location.href = res.next_url;
                }
            });
        } else {
            return false;//返回false那么表单将不提交
        }
    }
</script>
<%if (!Tools.myIsNull(info)) {%>
<script>
    editFun(${info});
</script>
<%}%>


