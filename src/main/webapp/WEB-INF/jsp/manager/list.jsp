<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.JspTools" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="java.io.FileInputStream" %>
<%
	String errorMsg =(String) request.getAttribute("errorMsg");//如果有c错误信息，就弹出显示并跳转
	if (!Tools.myIsNull(errorMsg)){
		JspTools.alert(errorMsg,"404",out);
		return ;
	}
	errorMsg =(String) request.getAttribute("showmsg"); //如果有提示信息，就弹出显示一下了。
	if (!Tools.myIsNull(errorMsg)){
		JspTools.alert(errorMsg,out);
	}
	String sPlUrl = Tools.urlKill("l|p");
	int pages =(int) request.getAttribute("pages");
	boolean canDel = request.getAttribute("canDel")==null?false:(boolean)request.getAttribute("canDel"); //是否显示删除按钮
	boolean canAdd = request.getAttribute("canAdd")==null?true:(boolean)request.getAttribute("canAdd"); //是否显示新增按钮
	String type = request.getParameter("type");
	String cn = request.getParameter("cn");
	String sdo = request.getParameter("sdo");
	String msg =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".btn.jsp";
	ServletContext context = request.getSession().getServletContext();
	InputStream is= context.getResourceAsStream(msg);
	boolean havebtnfile = is!=null; //是否有{cn}.btn.jsp，有的话就包含起来
	if (havebtnfile){
		is.close();
	}
%>
<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) --><c:if test="${recs>0}">
	<section class="content-header">
		<h1>
			${lsitTitleString} <small>共${recs}个 </small>
		</h1>
	</section></c:if>
	<!-- Main content -->
	<section class="content">
		<div class="admin-tools">
			<div class="row">
				<div class="col-sm-9 admin-button">	<!--  col-sm-9，后面的数字9表示宽度，总共是12-->
					<div class="btn-group">
						<c:if test="${canAdd}"><a href="<%=Tools.urlKill("id|sdo")+"&sdo=form"%>" data-target="#modal" class="btn btn-default"><i class="fa fa-edit"></i> 新增 </a> 
						<a data-toggle="modal" href="<%=Tools.urlKill("id|sdo")+"&sdo=float"%>"  data-target="#modal" class="btn btn-default"><i class="fa fa-edit"></i> 新增(modal模式)</a></c:if>
						<c:if test="${canDel}"><a href="javascript:;" id="del_more_btn" class="btn btn-default"><i class="fa fa-trash-o"></i> 删除</a></c:if>
						<%if(havebtnfile){%>
							<jsp:include page="<%=msg%>"></jsp:include>
						<%}%>
					</div>
				</div><c:if test="${recs>0}">
				<div class="col-sm-3 admin-page-top hidden-xs">
					<div class="btn-group">
						<a href="<%=sPlUrl%>&p=${p-1<=0?1:p-1}" class="btn btn-default">«</a> <a href="<%=sPlUrl%>&p=${p+1>pages?pages:p+1}"
						 class="btn btn-default">»</a>
						<select id="page_limit_select" onchange="window.location.href='<%=sPlUrl%>&l='+this.value" class="form-control">
						<c:forEach var="i" begin="5" end="100" step="5">	
							<option value="${i}" <c:if test="${i==l}"> selected="selected" </c:if>>每页${i}条</option>
 						</c:forEach>						
						</select>						 
					</div>
				</div></c:if>
			</div>
		</div>
		<div id="main_list" class="admin-content box">
			<!-- 数据载入中 请在搜索，筛选，载入的时候显示 放在.box里 -->
			<div class="overlay" style="display: none;">
				<i class="fa fa-refresh fa-spin"></i>
			</div>
			<!-- 数据载入中结束 -->
			<%
				msg =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".list.jsp";
			%>
			<jsp:include page="<%=msg%>"></jsp:include>
		</div><c:if test="${recs>0}">
		<div class="foot-page">
			<ul class="pagination no-margin">
			${htmlpages}
			</ul>
			<div class="page-num">
				共${recs}个 分${pages}页显示
			</div>
		</div></c:if>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<script type="text/javascript" src="js/list.js"></script>