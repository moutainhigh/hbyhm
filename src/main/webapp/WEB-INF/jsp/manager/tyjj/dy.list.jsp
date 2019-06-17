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
        TtMap minfo = Tools.minfo();
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
                                所属城市
                            </th>
                            <th class="hidden-xs text-center">
                                所属公司
                            </th>
                            <th class="text-center">
                                提交/更新时间
                            </th>
                            <th class="text-center">
                                垫资/放款状态
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
                                    request.setAttribute("m",m);
                        %>
                        <tr role="row" class="odd">
                            <td class="hidden-xs text-center">
                                <%=m.get("gems_code")%>
                            </td>
                            <td class="text-center">
                                <%=m.get("c_name")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("adminname")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <%=m.get("state_name")%>-<%=m.get("city_name")%>
                            </td>
                            <td class="hidden-xs text-center">
                                <a href="/manager/index?cn=dy&type=tyjj&sdo=list&fsid=<%=m.get("fsid")%>"><%=m.get("fsname")%></a>
                            </td>
                            <td class="text-center">
                                <%=m.get("dt_add")%><br><%=m.get("dt_edit")%>
                            </td>
                            <td>
                                <select id="fk_status" onchange="change('<%=m.get("icbc_id")%>',this.value)" class="selectpicker  form-control" data-max-options="1">
                                    <option id="0" value="0" ${m.fk_status eq '0'?"selected = 'selected'":''}>未垫资</option>
                                    <option id="1" value="1" ${m.fk_status eq '1'?"selected = 'selected'":''}>已垫资</option>
                                    <option id="2" value="2" ${m.fk_status eq '2'?"selected = 'selected'":''}>已放款</option>
                                </select>

                            </td>
                            <td class="text-center">
                                <span class="label label-success"><%=DataDic.dic_zx_status.get(m.get("bc_status"))%></span>
                            </td>
                            <td class="text-center">
                                <div class="table-button">
                                    <%
                                        if (!(m.get("current_editor_id").length() == 0 || "-1".equals(m.get("current_editor_id")) || minfo.get("id").equals(m.get("current_editor_id")))) {
                                    %>
                                    <span class="label label-success">用户<%=m.get("aa_name")%>正在操作</span>
                                    <%
                                        }
                                    %>
                                    <a href="<%=url%><%=m.get("id")%>" class="btn btn-default">
                                        <i class="fa fa-pencil"></i>
                                    </a>
                                    <a href="index?cn=ddjd&sdo=form&type=tyjj&icbc_id=<%=m.get("icbc_id")%>&id=<%=m.get("id")%>&tab=40" class="btn btn-default">
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

        function change(icbc_id,value) {
            // alert(icbc_id);
            /*var options = $("#fk_status option:selected");
            var fk_status = options.val();*/

            // alert(value);
            $.ajax({
                type: "POST",      //data 传送数据类型。post 传递
                dataType: 'json',  // 返回数据的数据类型json
                url: "/ttAjaxPost",  // 控制器方法
                data: {
                    do:'xgdz',
                    icbc_id:icbc_id,
                    fk_status:value
                },  //传送的数据
                error: function () {
                    alert("编辑失败...请稍后重试！");
                },
                success: function (data) {
                     alert("更改成功");
                }
            });
        }


</script>
</body>
</html>