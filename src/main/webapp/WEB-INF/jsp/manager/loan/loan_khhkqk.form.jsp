<%@ page import="com.tt.tool.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">

        <div class="box-header with-border">
            <h3 class="box-title">业务状态: </h3>
        </div>

        <div class="modal-body">
            <div class="row" >
                <label class="col-sm-1"  >主贷人姓名:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_name }
                </div>
                <label class="col-sm-1">身份证号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_cardno }
                </div>
                <label class="col-sm-1" >金融产品:<i class="red">*</i></label>
                <div class="col-sm-2">
                    <c:if test="${infodb.loan_tpid==1}">
                        卡分期
                    </c:if>
                </div>
                <label class="col-sm-1" >车辆评估价格:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.pg_price}
                </div>
            </div>
            <div class="row">
                <label class="col-sm-1" >贷款金额:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.dk_price}
                </div>
                <label class="col-sm-1">贷款期数:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.aj_date}
                </div>
                <label class="col-sm-1" >每月应还:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.myyh}
                </div>
                <label class="col-sm-1">贷款银行:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.blankname}
                </div>
                <%-- <label class="col-sm-1" >还款日期:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${lborrow.dt_edit}
                </div> --%>
            </div>
            <div class="row" >
                <!-- <label class="col-sm-1" >是否结清:<i class="red">*</i></label>
                  <div class="col-sm-2">
                      暂时无此数据
                </div> -->
                <!-- <label class="col-sm-1">已还期数:<i class="red">*</i></label>
                  <div class="col-sm-2">
                    1
                </div> -->
            </div>
        </div>


    </div>


    <div class="box-header with-border">
        <h3 class="box-title">还款计划: </h3>
    </div>
    <div class="box" style="margin-top:10px;">
        <!-- 数据载入中结束 -->
        <table class="table table-bordered table-hover">
            <tr>
                <th class="text-center">还款期数</th>
                <th class="text-center">应还日期</th>
                <th class="text-center">应还金额</th>
                <th class="text-center">实还日期</th>
                <th class="text-center">实还金额</th>
                <th class="text-center">是否逾期</th>
                <th class="text-center">逾期金额</th>
                <!-- <th class="text-center">核销日期</th> -->
            </tr>
            <c:forEach items="${hkjh}" var="map"  varStatus="status">
                <tr>
                    <td class="text-center">${map.overdue_which}</td>
                    <td class="text-center">${map.should_date }</td>
                    <td class="text-center">${map.should_money}</td>
                    <td class="text-center">${map.practical_date}</td>
                    <td class="text-center">${map.practical_money}</td>
                    <td class="text-center">
                        <c:if test="${map.overdue_status == 1 }">
                            是
                        </c:if>
                        <c:if test="${map.overdue_status == 2 }">
                            否
                        </c:if>
                    </td>
                    <td class="text-center">${map.overdue_money}</td>
                        <%-- <td class="text-center">${map.hx_date }</td> --%>
                </tr>
            </c:forEach>

        </table>
    </div>


    <div class="box-header with-border">
        <h3 class="box-title">贷后信息: </h3>
    </div>
    <div class="box" style="margin-top:10px;">
        <!-- 数据载入中结束 -->
        <table class="table table-bordered table-hover">
            <tr>
                <th class="text-center">贷款类型</th>
                <th class="text-center">姓名</th>
                <th class="text-center">身份证号</th>
                <th class="text-center">与主贷人关系</th>
                <th class="text-center">银行征信</th>
                <th class="text-center">大数据征信</th>
                <th class="text-center">电话号码</th>
                <th class="text-center">操作</th>
            </tr>
            <tr>
                <td class="text-center">主贷人</td>
                <td class="text-center">${infodb.c_name}</td>
                <td class="text-center">${infodb.c_cardno}</td>
                <td class="text-center">本人</td>
                <td class="text-center"><i class="fa fa-search-plus"></i></td>
                <td class="text-center"><i class="fa fa-search-plus"></i></td>
                <td class="text-center">${infodb.c_tel }</td>
                <td class="text-center" onclick="toggleModel()"><i class="fa fa-search-plus"></i></td>
            </tr>
            <c:if test="${not empty mapafter.jjlxr_c_name}">
                <tr>
                    <td class="text-center">紧急联系人</td>
                    <td class="text-center">${mapafter.jjlxr_c_name}</td>
                    <td class="text-center"></td>
                    <td class="text-center">${mapafter.jjlxr_jdrgx}</td>
                    <td class="text-center"></i>--</td>
                    <td class="text-center"></i>--</td>
                    <td class="text-center">${mapafter.jjlxr_c_tel}</td>
                    <td class="text-center" onclick="jjlxrModel()">--</td>
                </tr>
            </c:if>
            <c:if test="${not empty mapafter.qtlxr_c_name}">
                <tr>
                    <td class="text-center">紧急联系人2</td>
                    <td class="text-center">${mapafter.qtlxr_c_name}</td>
                    <td class="text-center"></td>
                    <td class="text-center">${mapafter.qtlxr_jdrgx}</td>
                    <td class="text-center"></i>--</td>
                    <td class="text-center"></i>--</td>
                    <td class="text-center">${mapafter.qtlxr_c_tel}</td>
                    <td class="text-center" onclick="jjlxrModel()">--</td>
                </tr>
            </c:if>
        </table>
    </div>
    <div style="height:50px;margin:10px 0;">
        <button type="button" class="btn btn-info search-btn" style="float:right">申请催收</button>
    </div>

</div>
<script>

    function toggleModel(){
        $('#myModal').modal({ show: true });
    }

</script>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="addModal_nstrLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">主贷人信息</h4>
            </div>
            <div class="modal-body" style="border:1px solid #ccc;background-color:#F7F7F7;border-radius: 10px;margin:30px;">
                <!-- 模态框插入内容 start -->

                <div class="row" >
                    <label class="col-sm-1">姓名:<i class="red">*</i></label>
                    <div class="col-sm-3">
                        ${infodb.c_name }
                    </div>
                    <label class="col-sm-1">性别:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        <c:if test="${infodb.c_sex == 1}">男</c:if>
                        <c:if test="${infodb.c_sex == 2}">女</c:if>
                    </div>
                    <label class="col-sm-2">手机号:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${infodb.c_tel }
                    </div>
                </div>
                <div class="row" >

                    <label class="col-sm-2">身份证号:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${infodb.c_cardno}
                    </div>
                    <label class="col-sm-2" >居住地:<i class="red">*</i></label>
                    <div class="col-sm-3">
                        ${mapafter.zdr_xzdz }
                    </div>
                </div>
                <div class="row" >
                    <label class="col-sm-2">身份证地址:<i class="red">*</i></label>
                    <div class="col-sm-3">

                    </div>
                    <label class="col-sm-1">学历:<i class="red">*</i></label>
                    <div class="col-sm-1">
                        ${mapafter.zdr_xl }
                    </div>
                    <label class="col-sm-2">婚姻情况:<i class="red">*</i></label>
                    <div class="col-sm-1">
                        未婚
                    </div>
                </div>
                <div class="row" >
                    <label class="col-sm-2 " style="" >单位性质:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        私企
                    </div>
                    <label class="col-sm-2">单位名称:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${mapafter.zdr_gzdw}
                    </div>
                    <label class="col-sm-2" >单位职务:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        开发
                    </div>
                </div>
                <div class="row" >
                    <label class="col-sm-2">单位电话:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${mapafter.zdr_dwdh }
                    </div>
                    <label class="col-sm-2">单位地址:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${mapafter.zdr_dwdz}
                    </div>
                    <label class="col-sm-2" >个人月收入:<i class="red">*</i></label>
                    <div class="col-sm-2">
                        ${mapafter.zdr_grsr }
                    </div>
                </div>
                <!-- 模态框插入内容 end -->
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>