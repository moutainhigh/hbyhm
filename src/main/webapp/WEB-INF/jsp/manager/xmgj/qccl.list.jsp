<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="ch.qos.logback.classic.layout.TTLLLayout" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.DataDic" %>
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
                                订单编号
                            </th>
                            <th class="text-center">
                                客户姓名
                            </th>
                            <th class="hidden-xs text-center">
                                业务员
                            </th>
                            <th class="hidden-xs text-center">
                                所属公司
                            </th>
                            <th class="text-center">
                                提交/更新时间
                            </th>
                            <th class="text-center">
                                状态
                            </th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            TtList list=(TtList) request.getAttribute("list");
                            if(list!=null&&list.size()>0){
                                for(TtMap m:list){
                        %>
                        <tr role="row" class="odd">
                            <td class="hidden-xs text-center">
                                <%=m.get("code")%>
                            </td>
                            <td class="text-center">
                                <%=m.get("c_name")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("adminname")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("fsname")%>
                            </td>
                            <td class="text-center">
                                <%=m.get("dt_add")%><br><%=m.get("dt_edit")%>
                            </td>
                            <td class="text-center">
                                <span class="label label-success"><%=DataDic.dic_zx_status.get(m.get("bc_status"))%></span>
                            </td>
                            <td class="text-center">
                                <div class="table-button">
                                    <a href="<%=url%><%=m.get("id")%>" class="btn btn-default">
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    <a href="index?cn=ddjd&sdo=form&type=tyjj&icbc_id=<%=m.get("icbc_id")%>&id=<%=m.get("id")%>&tab=30" class="btn btn-default">
                                        <i class="fa fa-search"></i>
                                    </a>
                                </div>
                            </td>
                        </tr>
                        <%}}%>
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