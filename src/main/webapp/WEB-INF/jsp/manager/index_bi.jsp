<%-- 非iframe模式的Manager的index.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.JspTools" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%@ page errorPage="404.jsp" %>
<%
			String sdo = request.getParameter("sdo");
			String type = request.getParameter("type");
			String cn = request.getParameter("cn");
			TtMap minfo = ((TtMap) request.getAttribute("minfo"));
			String msg ="404";
			boolean bFloatMode = false;
			switch (sdo) {//过滤恶意的
				case "form":
				case "list":
				case "float":
					if (sdo.equals("float")){
						bFloatMode = true;
					}
					msg = "/WEB-INF/jsp/manager/" + sdo + ".jsp.iframe.jsp";
					break;
				default:
					break;
			}
%>
<%
	if (bFloatMode){//modal模式
%>
<body class="skin-blue sidebar-mini fixed">
<jsp:include page="<%=msg%>"></jsp:include>
<%
	}else{
%>
<%-- head.jsp 页面--%>
<%@include file="head.jsp.iframe.jsp"%>
<body class="${cssName} sidebar-mini fixed">
	<%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
	<div class="wrapper">
		<%}%>
		<%--header.jsp 页面--%>
		<%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
		<%@include file="header.jsp.iframe.jsp"%>
		<!-- Left side column. contains the logo and sidebar -->
		<%--sidebar.jsp 页面--%>
		<%@include file="sidebar.jsp.iframe.jsp"%>
		<%}%>
		<%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
		<div class="wrapper">
			<%}%>
			<%if(request.getParameter("inframe")!=null || request.getParameter("iframe")==null){%>
			<!-- Content Wrapper. Contains page content -->
			<jsp:include page="<%=msg%>"></jsp:include>
			<%}%>
			<%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
		</div>
		<%}%>
  <%if(request.getParameter("inframe")==null||request.getParameter("inframe")==""){%>
	</div>
	<%}%>
	<!--弹窗框体开始-->
	<div class="modal fade" id="modal" role="dialog" data-backdrop="static">
		<div class="modal-dialog" role="document">
			<div id="mycontent" class="modal-content">
				<!--将在这里载入链接页面-->
			</div>
		</div>
	</div>
<%
	}
%>	
	<!-- 弹窗框体结束-->
	<script>
		$('#modal').on('hidden.bs.modal', function (e) {
			$(this).removeData("bs.modal");
		})
	</script>
	<script type="text/javascript" src="js/comm/ui.js"></script>
</body>

</html>