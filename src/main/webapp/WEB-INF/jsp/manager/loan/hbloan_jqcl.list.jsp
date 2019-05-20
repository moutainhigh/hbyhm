<%@ page import="com.tt.tool.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<body>
<div class="box">
    <%
        String url = Tools.urlKill("sdo|id")+"&sdo=form&id=";
    %>
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
                        <th class="text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                            订单编号
                        </th>
                        <th class="text-center">
                            客户姓名
                        </th>
                        <th class="text-center">
                            身份证号
                        </th>
                        <th class="text-center">
                            贷款银行
                        </th>
                        <th class="text-center">
                            车辆类型
                        </th>
                        <th class="text-center">
                            业务员
                        </th>
                        <th class="text-center">
                            业务团队
                        </th>
                        <th class="text-center">
                            车辆号牌
                        </th>
                        <th class="text-center">
                            申请单状态
                        </th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="u" varStatus="num">
                        <tr role="row" class="odd">
                            <td class="text-center">
                                    ${u.order_code}
                            </td>
                            <td class="text-center">
                                    ${u.c_name}
                            </td>
                            <td class="text-center">
                                    ${u.c_cardno}
                            </td>
                            <td class="text-center">
                                    ${u.bank_name}
                            </td>
                            <td class="text-center">
                                <c:if test="${u.car_type == '1'}">
                                    新车
                                </c:if>
                                <c:if test="${u.car_type == '2'}">
                                    二手车
                                </c:if>
                            </td>
                            <td class="text-center">
                                    ${u.gems_name}
                            </td>
                            <td class="text-center">
                                    ${u.fs_name}
                            </td>
                            <td class="text-center">
                                    ${u.carno}
                            </td>
                            <td class="text-center">
                                <c:if test="${u.type_status == '61'}">
                                    正常结清
                                </c:if>
                                <c:if test="${u.type_status == '62'}">
                                    提前结清
                                </c:if>
                                <c:if test="${u.type_status == '63'}">
                                    强制结清
                                </c:if>
                                <c:if test="${u.type_status == '64'}">
                                    亏损结清
                                </c:if>
                            </td>
                            <td class="text-center">
                                <div class="table-button">
                                    <%--location.href='/manager/jrdcajax?id=${u.id}&icbc_id=${u.icbc_id}--%>
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


</body>
</html>
