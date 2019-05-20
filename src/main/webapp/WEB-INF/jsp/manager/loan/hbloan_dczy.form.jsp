<%@ page import="com.tt.tool.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">

        <div class="box-header with-border">
            <h3 class="box-title">客户信息: </h3>
            <a href="javascript:0" style="float: right" class="btn btn-default" onclick="appCar(2)"><i class=""></i>申请诉讼</a>
            <a href="javascript:0" style="float: right" class="btn btn-default" onclick="appCar(1)"><i class=""></i>申请拖车</a>
            <h3 class="box-title" style="float: right">操作选项: </h3>
        </div>

        <div class="modal-body">
            <div class="row" >
                <label class="col-sm-1" >订单编号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.order_code}
                </div>
                <label class="col-sm-1">主贷人姓名:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_name }
                </div>
                <label class="col-sm-1" >电话:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_tel }
                </div>
                <label class="col-sm-1" >身份证:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_cardno }
                </div>
            </div>
            <div class="row">
                <label class="col-sm-1" >业务员::<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.gems_name }
                </div>
                <label class="col-sm-1">代理团队:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.fs_name }
                </div>
                <label class="col-sm-1" >现住地址:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.zdr_xzdz }
                </div>
                <label class="col-sm-1" >单位名称:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.zdr_gzdw }
                </div>
            </div>
            <div class="row" >
                <label class="col-sm-1" >单位电话:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.zdr_dwdh }
                </div>
                <label class="col-sm-1">单位地址:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.zdr_dwdz }
                </div>
            </div>
        </div>

        <div class="box-header with-border">
            <h3 class="box-title">车辆信息: </h3>
        </div>

        <div class="modal-body">
            <div class="row" >
                <label class="col-sm-1" >车辆价格:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.pg_price}
                </div>
                <label class="col-sm-1">评估价格:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.pg_price}
                </div>
                <label class="col-sm-1" >品牌:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.ppxh}
                </div>
                <label class="col-sm-1" >车辆型号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.ppxh}
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" >车辆类型:<i class="red">*</i></label>
                <div class="col-sm-2">
                    <!-- 1新车，2二手车 -->
                    ${infodb.cars_type==1?'新车':'二手车'}
                </div>
                <label class="col-sm-1">车架号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.car_vin}
                </div>
                <label class="col-sm-1" >发动机号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.motorcode}
                </div>
                <label class="col-sm-1" >车牌:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.carno}
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" >颜色:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.car_color_id}
                </div>
            </div>
        </div>

        <div class="box-header with-border">
            <h3 class="box-title">贷款方案: </h3>
        </div>

        <div class="modal-body">
            <div class="row" >
                <label class="col-sm-1" >车辆价格:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.pg_price}
                </div>
                <label class="col-sm-1">业务产品名称:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.loan_tpid==1?'卡分期':'汽车分期'}
                </div>
                <label class="col-sm-1" >贷款银行:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.bankname}
                </div>
                <label class="col-sm-1" >执行利率:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.aj_lv}
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" >首付金额:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.sf_price}
                </div>
                <label class="col-sm-1">实际贷款额:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.dk_total_price}
                </div>
                <label class="col-sm-1" >首付比例:<i class="red">*</i></label>
                <div class="col-sm-2">
                    --
                </div>
                <label class="col-sm-1" >贷款期数:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.aj_date}
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" style="" >银行分期本金:<i class="red">*</i></label>
                <div class="col-sm-2">
                    --
                </div>
                <label class="col-sm-1">金融服务费:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.jrfw_price }
                </div>
                <label class="col-sm-1" >本息合计:<i class="red">*</i></label>
                <div class="col-sm-2">
                    --
                </div>
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

    <form id="form1" onsubmit="return false" action="##"  method="post">
    <div class="box-header with-border">
        <h3 class="box-title">电催录入栏: </h3>
        <textarea style="border:1px solid #ccc;margin-top:10px;height:120px" id="result_msg" name="result_msg" class="form-control"></textarea>
    </div>
    <div style="height:50px;margin:20px 0 0 0;">
        <button type="button" class="btn btn-info search-btn" style="float:right" onclick="addPhoneResult()">提交</button>
    </div>
    </form>


    <div class="box-header with-border">
        <h3 class="box-title">记录栏: </h3>
    </div>
    <div class="box" style="margin-top:10px;">
        <!-- 数据载入中结束 -->
        <table class="table table-bordered table-hover">
            <tr>
                <th style="width:3%" class="text-center hidden-xs"><input class="check_all" type="checkbox"></th>
                <th class="text-center">序号</th>
                <th class="text-center">记录时间</th>
                <th class="text-center">记录类型</th>
                <th class="text-center">操作人员</th>
                <th class="text-center">记录查看</th>
                <!-- <th class="text-center">核销日期</th> -->
            </tr>
            <c:forEach items="${jllist}" var="results"  varStatus="status">
                <tr>
                    <td class="text-center hidden-xs"><input name="delid"  type="checkbox"></td>
                    <td class="text-center">${status.index+1}</td>
                    <td class="text-center">${results.dt_add}</td>
                    <td class="text-center">
							<span class="s-font-blue">
								<c:if test="${results.type_id eq 2}">电催</c:if>
								<c:if test="${results.type_id eq 3 }">拖车</c:if>
								<c:if test="${results.type_id eq 4 }">诉讼</c:if>
								<c:if test="${results.type_id eq 5 }">拍卖</c:if>
								<c:if test="${results.type_id eq 6 }">结清</c:if>
								<c:if test="${results.type_id eq 7 }">核销</c:if>
							</span>
                    </td>
                    <td class="text-center">${results.gems_name}</td>
                    <td class="text-center">${results.result_msg}</td>
                </tr>
            </c:forEach>

        </table>
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
<script>

    function addPhoneResult(){
        var result_msg = $('#result_msg').val();
        //alert(result_msg);
        if(result_msg==''){
            alert("请在录入栏填写信息!");
            return false;
        }
        var type_id = ${bbmap.type_id};
        var type_status = ${bbmap.type_status};
        var icbc_id = ${bbmap.icbc_id};
        var lolId = ${bbmap.id};
        //alert(lolId+"--"+icbc_id+"--"+type_status+"--"+type_id+"--");
        $.ajax({
            type: "POST",
            url: "/manager/jrdcajaxpost",
            data:{
                result_msg:result_msg,
                type_id:type_id,
                type_status:type_status,
                icbc_id:icbc_id,
                lolId:lolId,
                dctype_id:'2'
            },
            success:function(data){
                alert("提交成功");
                location.reload(true);
            }
        })
    }


    function appCar(clickType){
        if(clickType==1){//
            var type_id = '3';
            var type_status = '31';
            var result_msg = "开始申请拖车";
        }else if(clickType==2){
            var type_id = '4';
            var type_status = '41';
            var result_msg = "开始申请诉讼";
        }
        var icbc_id = ${bbmap.icbc_id};
        var lolId = ${bbmap.id};
        //alert(icbc_id+"--"+lolId+"--"+type_id+"--"+type_status+"--"+result_msg);
        //确定提示框
        var confirmMsg = confirm("请问确定"+result_msg+"吗?");
        if(confirmMsg==true){
            $.ajax({
                type: "POST",
                url: "/manager/jrdcajaxpost",
                data:{
                    result_msg:result_msg,
                    type_id:type_id,
                    type_status:type_status,
                    icbc_id:icbc_id,
                    lolId:lolId,
                    dctype_id:'3' //申请电催||诉讼
                },
                success:function(data){
                    // alert(data);
                    location.reload(true);
                    // location.href="";
                }
            })
        }else if(confirmMsg==false){
            //如果取消，暂时不做操作
        }
    }

</script>