<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="box">
	<%
		String url = Tools.urlKill("sdo|id")+"&sdo=form&id=";
	%>
	<!-- /.box-header -->
	<div class="box-body">
		<div id="example2_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
			<div class="row">
				<div class="col-sm-6"></div>
				<div class="col-sm-6"></div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<table id="example2" class="table table-bordered table-hover dataTable" role="grid" aria-describedby="example2_info">
						<thead>
							<tr role="row">
								<th class="hidden-xs text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
									编号
								</th>
								<th class="hidden-xs text-center">
									模块级别
								</th>
								<th class="text-center">
									显示名称
								</th>
								<th class="hidden-xs text-center">
									后台菜单显示
								</th>
								<th class="text-center">
									排序
								</th>
								<th class="text-center">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="u" varStatus="num">
								<tr role="row" class="odd">
									<td class="hidden-xs text-center">
											${u.id}
									</td>
									<td class="hidden-xs text-center">
										${u.level}
									</td>
									<td class="text-center">
										${u.showmmenuname}
									</td>
									<td class="hidden-xs text-center">
										${u.showmmenutag.equals("1")?"<span class='label label-success'>显示</span>":"<span class='label label-danger'>不显示</span>"}
									</td>
									<td class="text-center">
										${u.sort}
									</td>
									<td class="text-center">
										<div class="table-button">
											<a href="<%=url%>${u.id}" class="btn btn-default">
												<i class="fa fa-pencil"></i>
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
</script>
</body>
</html>