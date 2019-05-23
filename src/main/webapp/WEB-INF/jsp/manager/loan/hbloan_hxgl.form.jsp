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
                    ${infodb.gems_code}
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
                <label class="col-sm-1" >业务员:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.gems_name }
                </div>
                <label class="col-sm-1">代理团队:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.fs_name }
                </div>
                <label class="col-sm-1" >现住地址:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.c_buycar_live_address }
                </div>
                <label class="col-sm-1" >单位名称:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.c_work_name }
                </div>
            </div>
            <div class="row" >
                <label class="col-sm-1" >单位电话:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.c_work_tel }
                </div>
                <label class="col-sm-1">单位地址:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${mapafter.c_work_address }
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
                    --
                </div>
                <label class="col-sm-1">评估价格:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_car_priceresult}
                </div>
                <label class="col-sm-1" >品牌:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.cbname}
                </div>
                <label class="col-sm-1" >车辆型号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.cmname}
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" >车辆类型:<i class="red">*</i></label>
                <div class="col-sm-2">
                    <!-- 1国产，2'进口 -->
                    ${infodb.c_loaninfo_car_type==1?'国产':'进口'}
                </div>
                <label class="col-sm-1">车架号:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.vincode}
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
                    <c:if test="${infodb.color_id == '1'}">黑</c:if>
                    <c:if test="${infodb.color_id == '2'}">白</c:if>
                    <c:if test="${infodb.color_id == '3'}">灰</c:if>
                    <c:if test="${infodb.color_id == '4'}">红</c:if>
                    <c:if test="${infodb.color_id == '5'}">银</c:if>
                    <c:if test="${infodb.color_id == '6'}">蓝</c:if>
                    <c:if test="${infodb.color_id == '7'}">金</c:if>
                    <c:if test="${infodb.color_id == '8'}">棕</c:if>
                    <c:if test="${infodb.color_id == '9'}">橙</c:if>
                    <c:if test="${infodb.color_id == '10'}">黄</c:if>
                    <c:if test="${infodb.color_id == '11'}">紫</c:if>
                    <c:if test="${infodb.color_id == '12'}">绿</c:if>
                    <c:if test="${infodb.color_id == '13'}">褐</c:if>
                    <c:if test="${infodb.color_id == '14'}">栗</c:if>
                    <c:if test="${infodb.color_id == '15'}">米</c:if>
                    <c:if test="${infodb.color_id == '16'}">银灰</c:if>
                    <c:if test="${infodb.color_id == '17'}">青</c:if>
                    <c:if test="${infodb.color_id == '18'}">香槟</c:if>
                    <c:if test="${infodb.color_id == '19'}">咖啡</c:if>
                    <c:if test="${infodb.color_id == '20'}">天山</c:if>
                    <c:if test="${infodb.color_id == '21'}">其他色</c:if>
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
                    --
                </div>
                <label class="col-sm-1">业务产品名称:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.loan_tpid==1?'存量车':'汽车分期'}
                </div>
                <label class="col-sm-1" >贷款银行:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.loan_bank }
                </div>
                <label class="col-sm-1" >执行利率（%）:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_car_loanrate }
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" >首付金额:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_sfje}
                </div>
                <label class="col-sm-1">实际贷款额:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_dkze }
                </div>
                <label class="col-sm-1" >首付比例:<i class="red">*</i></label>
                <div class="col-sm-2">
                    --
                </div>
                <label class="col-sm-1" >贷款期数:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_periods }
                </div>
            </div>

            <div class="row" >
                <label class="col-sm-1" style="" >银行分期本金:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_car_pcpprice }
                </div>
                <label class="col-sm-1">金融服务费:<i class="red">*</i></label>
                <div class="col-sm-2">
                    ${infodb.c_loaninfo_fee }
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

        <c:if test="${bbmap.type_status == 72}">
            <div class="box-header with-border">
                <h3 class="box-title">处置结果: </h3>
            </div>
            <ul class="pagination no-margin" style="padding-top: 10px;">
                <select id="coolStatus" name="coolStatus" style="width: 180px;" class="form-control">
                    <option value="">--请选择--</option>
                    <option value="61">正常结清</option>
                    <option value="62">提前结清</option>
                    <option value="63">强制结清</option>
                    <option value="64">亏损结清</option>
                </select>
            </ul>
        </c:if>
        <div class="box-header with-border">
            <h3 class="box-title">信息录入栏: </h3>
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
    //信息录入栏 诉讼
    function addPhoneResult(){
        var type_id = ${bbmap.type_id};
        var type_status = ${bbmap.type_status};
        var icbc_id = ${bbmap.icbc_id};
        var lolId = ${bbmap.id};


        var coolStatus = $('#coolStatus').val();//处置结果
        if (coolStatus=='') {
            alert("请选择处置结果!");
            return false;
        }

        var result_msg = $('#result_msg').val();
        //alert(result_msg);
        if(result_msg==''){
            alert("请在录入栏填写信息!");
            return false;
        }
        //alert(lolId+"--"+icbc_id+"--"+type_status+"--"+type_id+"--");
        $.ajax({
            type: "POST",
            url: "/manager/hxglajaxpost",
            data:{
                result_msg:result_msg,
                type_id:type_id,
                type_status:type_status,
                icbc_id:icbc_id,
                lolId:lolId,
                coolStatus:coolStatus
            },
            success:function(data){
                alert("提交成功");
                self.location = document.referrer;  //返回上一页面
                //location.reload(true);
            }
        })
    }

    //申请拖车和诉讼
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
                    alert(data。msg);
                    self.location = document.referrer;  //返回上一页面
                    //location.reload(true);
                    // location.href="";
                }
            })
        }else if(confirmMsg==false){
            //如果取消，暂时不做操作
        }

    }

</script>