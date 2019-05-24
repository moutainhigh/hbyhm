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
                            金融产品
                        </th>
                        <th class="text-center">
                            贷款金额
                        </th>
                        <th class="text-center">
                            逾期金额
                        </th>
                        <th class="text-center">
                            逾期天数
                        </th>
                        <th class="text-center">
                            代理团队
                        </th>
                        <th class="text-center">
                            业务员
                        </th>
                        <th class="text-center">
                            催收人员
                        </th>
                        <th class="text-center">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="u" varStatus="num">
                        <tr role="row" class="odd">
                            <td class="text-center">
                                    ${u.gems_code}
                            </td>
                            <td class="text-center">
                                    ${u.c_name}
                            </td>
                            <td class="text-center">
                                    ${u.c_cardno}
                            </td>
                            <td class="text-center">
                                    <c:if test="${u.loan_tpid == '1'}">存量车</c:if>
                            </td>
                            <td class="text-center">
                                    ${u.dk_price}
                            </td>
                            <td class="text-center">
                                    ${u.overdue_amount}
                            </td>
                            <td class="text-center">
                                    ${u.overdue_days}
                            </td>
                            <td class="text-center">
                                    ${u.fs_name}
                            </td>
                            <td class="text-center">
                                    ${u.gems_name}
                            </td>
                            <td class="text-center">
                                    ${u.gems_name}
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

<script>
    function peizhi() {
        // alert("进入配置");
        $('#myModal').modal({show:true});
    }
</script>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="addModal_nstrLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">逾期配置</h4>
            </div>
            <div class="modal-body" style="height:450px;">
                <!-- 模态框插入内容 start -->
                <li class="text-primary">
                    <div class="big-conte_">
                        <div class="task_margin ng-scope" style="border:1px solid #ccc; border-radius: 10px;background-color:#F7F7F7; padding-top:10px;">
                            <form id="configTable" name="modalForm" class="form-horizontal ng-pristine ng-valid ng-scope">
                                <div class="form-group ng-scope">
                                    <label class="col-sm-3 control-label">初级逾期:<i class="red">*</i></label>天
                                    <div class="col-sm-2">
                                        <input value="${getConfig.overdue_one}" id="overdue_one" name="overdue_one" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
                                    </div>
                                </div>
                                <div class="form-group ng-scope">
                                    <label class="col-sm-3 control-label">中级逾期:<i class="red">*</i></label>天
                                    <div class="col-sm-2">
                                        <input value="${getConfig.overdue_two}" id="overdue_two" name="overdue_two" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
                                    </div>
                                </div>
                                <div class="form-group ng-scope">
                                    <label class="col-sm-3 control-label">高级逾期:<i class="red">*</i></label>天
                                    <div class="col-sm-2">
                                        <input value="${getConfig.overdue_three}" id="overdue_three" name="overdue_three" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
                                    </div>
                                </div>
                                <div class="form-group ng-scope">
                                    <label class="col-sm-3 control-label">逾期时长进入电催:<i class="red">*</i></label>天
                                    <div class="col-sm-2">
                                        <input value="${getConfig.overdue_to_phone}" id="overdue_to_phone" name="overdue_to_phone" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
                                    </div>
                                </div>
                                <div class="form-group ng-scope">
                                    <label class="col-sm-3 control-label">拍卖亏损金额:<i class="red">*</i></label>元
                                    <div class="col-sm-2">
                                        <input value="${getConfig.overdue_money}" id="overdue_money" name="overdue_money" type="text" class="form-control ng-pristine ng-untouched ng-valid ng-not-empty ng-valid-required">
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <a onclick="config_commit()" class="btn btn-primary">提交</a>
                                </div>
                            </form>
                            <script type="text/javascript">
                                function config_commit(){
                                    var overdue_one = $("#overdue_one").val();
                                    var overdue_two = $("#overdue_two").val();
                                    var overdue_three = $("#overdue_three").val();
                                    var overdue_to_phone = $("#overdue_to_phone").val();
                                    var overdue_money = $("#overdue_money").val();

                                    if(overdue_one=="" ||
                                        overdue_two=="" ||
                                        overdue_three=="" ||
                                        overdue_to_phone=="" ||
                                        overdue_money==""){
                                        alert("请认真填写配置信息!");
                                        return false;
                                    }
                                    var form = new FormData(document.getElementById("configTable"));
                                    $.ajax({
                                        url:"/manager/loanConfighx",
                                        type:"POST",
                                        data:form,
                                        processData:false,
                                        contentType:false,
                                        success:function(data){
                                            alert("提交成功!");
                                            location.reload();
                                        },
                                        error:function(e){
                                            alert("错误！！");
                                        }
                                    });
                                }
                            </script>
                        </div>
                    </div>
                </li>
                <!-- 模态框插入内容 end -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
