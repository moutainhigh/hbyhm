<%@ page import="com.tt.tool.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                                身份证
                            </th>
                            <th class="text-center">
                                产品名称
                            </th>
                            <th class="hidden-xs text-center">
                                贷款金额
                            </th>
                            <th class="hidden-xs text-center">
                                贷款期数
                            </th>
                            <th class="hidden-xs text-center">
                                还款日期
                            </th>
                            <th class="hidden-xs text-center">
                                车辆评估价格
                            </th>
                            <th class="hidden-xs text-center">
                                每月应还
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
                                    <c:if test="${u.loan_tpid == '1'}">
                                        卡分期
                                    </c:if>
                                </td>
                                <td class="text-center">
                                        ${u.c_loaninfo_dkze}
                                </td>
                                <td class="text-center">
                                        ${u.c_loaninfo_periods}
                                </td>
                                <td class="text-center">
                                        ${u.first_month_date}
                                </td>
                                <td class="text-center">
                                        ${u.c_loaninfo_car_priceresult}
                                </td>
                                <td class="text-center">
                                        ${u.myyh}
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

