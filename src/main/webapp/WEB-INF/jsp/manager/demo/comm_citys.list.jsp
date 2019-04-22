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
										所属省
								</th>
								<th class="text-center">
									城市名称
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="u" varStatus="num">
								<tr role="row" class="odd">
									<td class="hidden-xs text-center">
											${u.id}
									</td>
									<td class="hidden-xs text-center">
										<%-- <select id="test" name="test">dicopt的演示
											${Tools.dicopt("comm_states",u.state_id)}
										</select>
										--%>
										<%-- undic的演示 --%>
										${Tools:unDic("comm_states",u.state_id)}
									</td>
									<td class="text-center">
											${u.name}
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