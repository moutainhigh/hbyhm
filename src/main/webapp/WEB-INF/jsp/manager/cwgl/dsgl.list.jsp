<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="ch.qos.logback.classic.layout.TTLLLayout" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="box">
    <%
        String url = Tools.urlKill("sdo|id") + "&sdo=form&id=";
        String url_modal = Tools.urlKill("sdo|id") + "&sdo=float&id=";
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
                    <table id="example2" class="table table-bordered table-hover dataTable" role="grid"
                           aria-describedby="example2_info">
                        <thead>
                        <tr role="row">
                            <th class="hidden-xs text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                                编号
                            </th>
                            <th class="hidden-xs text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                                类型
                            </th>
                            <th class="text-center">
                                客户名
                            </th>
                            <th class="hidden-xs text-center">
                                期数
                            </th>
                            <th class="text-center">
                                服务费(分)
                            </th>
                            <th class="hidden-xs text-center">
                                日期
                            </th>
                            <th class="text-center">
                                总金额(分)
                            </th>
                            <th class="hidden-xs text-center">
                                更新时间
                            </th>
                            <th class="hidden-xs text-center">
                                完成时间
                            </th>
                            <th class="text-center" style="width: 200px">
                                API执行
                            </th>
                            <th class="hidden-xs text-center" style="width: 200px">
                                API返回
                            </th>
                            <th class="text-center">
                                手动状态
                            </th>
                            <%--                            <th class="text-center">
                                                            操作
                                                        </th>--%>
                        </tr>
                        </thead>
                        <tbody>
                        <%
                            TtList list = (TtList) request.getAttribute("list");
                            if (list != null && list.size() > 0) {
                                for (TtMap m : list) {
                        %>
                        <tr role="row" class="odd">
                            <td class="hidden-xs text-center">
                                <%=m.get("id")%>
                            </td>
                            <td class="hidden-xs text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                                <%
                                 if(m.get("api_type").equals("0")){
                                %>
                                <span class="label label-success">代收</span>
                                <%}else{%>
                                <span class="label label-warning">代付</span>
                                <%}%>
                            </td>
                            <td class="text-center">
                                <%=m.get("account_name")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("periods")%>
                            </td>
                            <td class="text-center">
                                <%=m.get("fw_price")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("ds_date")%>
                            </td>
                            <td class="text-center">
                                <%=m.get("amount")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("dt_edit")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("settle_day")%>
                            </td>
                            <td class="text-center" style="width: 200px">
                                <select id="bc_status" name="bc_status" disabled class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_tlzf_ds_bc_status, m.get("bc_status"))%>
                                </select>

                                <%
                                    if(m.get("api_type").equals("0")){
                                    if (m.get("bc_status").equals("1")||m.get("bc_status").equals("2")) {
                                %>
                                <a href="javascript:ds('<%=m.get("id")%>')">
					            <span class="label pull-right label-success">
                                <i class="fa fa-refresh"></i>
                                    手动交易
					            </span>
                                </a>
                                <%} else {%>
                                <a href="javascript:findds_result('<%=m.get("req_sn")%>','<%=m.get("id")%>')">
					            <span class="label pull-right label-success">
                                <i class="fa fa-refresh"></i>
                                    查询结果
					            </span>
                                </a>
                                <%
                                    }
                                }else{
                                    if (m.get("bc_status").equals("1")||m.get("bc_status").equals("2")) {
                                %>
                                <a href="javascript:df('<%=m.get("id")%>')">
					            <span class="label pull-right label-success">
                                <i class="fa fa-refresh"></i>
                                    手动交易
					            </span>
                                </a>
                                <%} else {%>
                                <a href="javascript:findds_result('<%=m.get("req_sn")%>','<%=m.get("id")%>')" >
					            <span class="label pull-right label-success">
                                <i class="fa fa-refresh"></i>
                                    查询结果
					            </span>
                                </a>

                                <%}
                                    }%>
                            </td>
                            <td class="hidden-xs text-center" style="width: 200px">
                               <%=m.get("result_msg")%>
                            </td>
                            <td class="text-center">
                                <select id="sd_status" name="sd_status"
                                        onchange="ajax_edit('<%=m.get("id")%>','sd_status',this.value,'tlzf_dk_details');"
                                        n class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_tlzf_sd_status, m.get("sd_status"))%>
                                </select>
                            </td>
                            <%--                            <td class="text-center">
                                                            <div class="table-button">
                                                                <a id="modal_qy<%=m.get("id")%>" data-toggle="modal" style="" data-target="#modal"
                                                                   href="/manager/index?cn=dsgl&type=cwgl&sdo=float&tl=1&id=<%=m.get("icbc_id")%>&nextUrl=dsgl_cwgl"
                                                                   class="btn btn-success">
                                                                    <i class="fa fa-credit-card"></i>
                                                                </a>
                                                                <a id="modal_ds<%=m.get("id")%>" data-toggle="modal" style="" data-target="#modal"
                                                                   href="/manager/index?cn=dsgl&type=cwgl&sdo=float&tl=2&id=<%=m.get("icbc_id")%>&nextUrl=dsgl_cwgl"
                                                                   class="btn btn-success">
                                                                    <i class="fa fa-sign-in"></i>
                                                                </a>
                                                            </div>
                                                        </td>--%>
                        </tr>
                        <%
                                }
                            }
                        %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
<script>

    function ds(id) {
        $.post("/manager/command?sdo=tlzf_ds&id=" + id, {},
            function (res) {
                eval("var res=" + res);
                alert(res.msg);
                window.location.href = "";
            });
    }
    function df(id) {
        $.post("/manager/command?sdo=tlzf_df&id=" + id, {},
            function (res) {
                eval("var res=" + res);
                alert(res.msg);
                window.location.href = "";
            });
    }
    function findds_result(QUERY_SN,id) {
        var data = {
            QUERY_SN: QUERY_SN
        };
        $.post("/kcdhttp?query=2&type=200004", data,
            function (result) {
                var res = eval('(' + result + ')');
                if (res.INFO != null && res.INFO != "" && res.INFO != "undefined") {
                    alert(res.INFO.ERR_MSG);
                    if(res.QTRANSRSP != null && res.QTRANSRSP != "" && res.QTRANSRSP != "undefined"){
                    if(res.QTRANSRSP[0].RET_CODE=="0000"){
                        ajax_edit(id,'bc_status','3','tlzf_dk_details');
                    }
                    }
                }else {
                    alert(res[0].ERR_MSG);
                }
                window.location.href = "";
            });
    }
</script>