<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TtMap minfo = (TtMap) request.getAttribute("minfo");
    TtMap qy = (TtMap) request.getAttribute("qy");
%>
<c:if test="${param.tl eq '1'}">
    <div class="box-body">
        <input type="hidden" name="cn" id="cn" value="${cn}">
        <input type="hidden" name="type" id="type" value="${type}">
        <input type="hidden" name="id" id="id" value="${empty qy.id?0:qy.id}">
        <input type="hidden" name="tl" id="tl" value="1">
        <input type="hidden" name="icbc_id" id="icbc_id" value="${icbc.id}">
            <%--<input type="hidden" name="gems_id" id="gems_id" value="${minfo.gemsid}">--%>
            <%--<input type="hidden" name="gems_fs_id" id="gems_fs_id" value="${minfo.icbc_erp_fsid}">--%>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">选择签约银行</label>
            <div class="col-sm-9">
                <select class="form-control" name="bank_code" id="bank_code">
                    <%=Tools.dicopt(DataDic.dic_tlzf_bank_code, qy.get("bank_code"))%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">账号类型</label>
            <div class="col-sm-9">
                <select class="form-control" name="account_type" id="account_type">
                    <%=Tools.dicopt(DataDic.dic_tlzf_account_type, qy.get("account_type"))%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">cvv2</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="cvv2" value="${qy.cvv2}" placeholder="信用卡时必填">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">有效期</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="vailddate" value="${qy.vailddate}"
                       placeholder="信用卡时必填,信用卡上的两位月两位年">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">借记卡号/信用卡号</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="account_no" value="${qy.account_no}"
                       onkeyup="(this.v=function(){this.value=this.value.trim();this.value=this.value.replace(/[^0-9-]+/,'');}).call(this)"
                       onblur="this.v();">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">持有人姓名</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="account_name" value="${qy.account_name}"
                       onblur="this.value=this.value.trim();">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">私人/公司</label>
            <div class="col-sm-9">
                <select class="form-control" name="account_prop">
                    <option value="0">私人</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">证件类型</label>
            <div class="col-sm-9">
                <select class="form-control" name="cardid_type">
                    <%=Tools.dicopt(DataDic.dic_tlzf_cardid_type, qy.get("cardid_type"))%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">证件号码</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="cardid" value="${qy.cardid}"
                       onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">电话</label>
            <div class="col-sm-9">
                <div class="input-group">
                    <input type="text" class="form-control" name="tel" value="${qy.tel}"
                           onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();">
                    <span class="input-group-addon"><a href="tel:">拨打</a></span>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9"><textarea style="width: 100%; height: 40px" class="form-control" value="${qy.remark}"
                                            name="remark"></textarea>
            </div>
        </div>
    </div>
</c:if>
<c:if test="${param.tl eq '2'}">
    <div class="box-body">
        <div class="modal-header">
            <h4 class="modal-title" id="" style="display:inline;">已有<font color="green">代收</font>记录</h4>
            <a href="javascript:0" style="float: right" onclick="confirm('确定清除代收记录吗?')?qcds(${icbc.id}):alert('取消')" class="btn btn-default">清除代收记录</a>
        </div>
        <c:if test="${empty dslist}">
            <div class="modal-body form-horizontal">
                <ul class="nav nav-pills nav-stacked">
                    <li style="padding-bottom: 10px"><i class="fa fa-circle-o" style="color: green"></i>暂无记录<span
                            class="pull-right">暂无<font color="green">代收</font>记录</span></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${!empty dslist}">
            <table class="table table-bordered table-hover">
                <tbody>
                <tr>
                    <th class="text-center" style="width: 80px">期数</th>
                    <th class="text-center" style="width: 80px">服务费(分)</th>
                    <th class="text-center" style="width: 100px">代收日期</th>
                    <th class="text-center" style="width: 150px">代收总金额(分)</th>
                    <th class="text-center" style="width: 150px">执行结果</th>
                    <th class="text-center" style="width: 150px">手动审核</th>
                </tr>
                <%
                    TtList dslist = (TtList) request.getAttribute("dslist");
                    if (dslist.size() > 0) {
                        for (TtMap ds : dslist) {
                %>
                <tr>
                    <td class="text-center" style="width: 80px"><%=ds.get("periods")%>
                    </td>
                    <td class="text-center" style="width: 80px"><%=ds.get("fw_price")%>
                    </td>
                    <td class="text-center" style="width: 100px"><%=ds.get("ds_date")%>
                    </td>
                    <td class="text-center" style="width: 100px"><%=ds.get("amount")%>
                    </td>
                    <td class="text-center" style="width: 150px">
                        <select class="form-control" disabled>
                            <%=Tools.dicopt(DataDic.dic_tlzf_ds_bc_status, ds.get("bc_status"))%>
                        </select>
                    </td>
                    <td class="text-center" style="width: 150px">
                        <select id="sd_status" name="sd_status"
                                onchange="ajax_edit('<%=ds.get("id")%>','sd_status',this.value,'tlzf_dk_details');" n
                                class="form-control">
                            <%=Tools.dicopt(DataDic.dic_tlzf_sd_status, ds.get("sd_status"))%>
                        </select>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>

        </c:if>
        <div class="modal-header">
            <h4 class="modal-title" id="">新建<font color="green">代收</font></h4>
        </div>
        <input type="hidden" name="tl" value="2">
        <input type="hidden" name="cn" value="${cn}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="id" value="0">
        <input type="hidden" name="icbc_id" value="${icbc.id}">
        <div class="form-group" style="padding-top: 25px">
            <label for="title2" class="col-sm-3 control-label">签约信息:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="bt" value="${qy.account_name}-${qy.account_no}" disabled="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">服务费</font>金额(分):</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="fw_price" id="fw_price" placeholder="输入服务费,单位(分)"
                       onblur="getTotlePrice()" value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">期数:</font></label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="periods" id="periods" placeholder=""
                       onblur="getTotlePrice()"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">代收日期:</font></label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="ds_date" id="ds_date" placeholder=""
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">代收</font>总金额(分):</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="amount" id="amount" placeholder="输入代收金额,单位(分)"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">备注(可不填)</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="remark" placeholder="比如:正常代收" value="">
            </div>
                <%--<div class="col-sm-6">
                    <div class="input-group">
                        <span class="input-group-addon">短信通知用户</span>
                        <select name="sendsms" class="form-control"
                                id="sendsms">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>--%>
        </div>
    </div>
    <script>
        function getTotlePrice() {
            var fw_price = $("#fw_price").val();
            var periods = $("#periods").val();
            var amount = fw_price * periods;
            $("#amount").val(amount);
        }

        //执行一个laydate实例
        laydate.render({
            elem: '#ds_date', //指定元素
            type: 'date'
        });


        function qcds(icbcid) {
            alert(icbcid);

            $.ajax({
                type: "POST",      //data 传送数据类型。post 传递
                dataType: 'json',  // 返回数据的数据类型json
                url: "/ttAjaxPost",  // 控制器方法
                data: {
                    do:'qcds',
                    icbc_id:icbcid
                },  //传送的数据
                error: function () {
                    alert("编辑失败...请稍后重试！");
                },
                success: function (data) {
                    // alert(data.msg);
                    window.location.reload();
                }
            });
        }

    </script>
</c:if>
<c:if test="${param.tl eq '3'}">
    <div class="box-body">
        <div class="modal-header">
            <h4 class="modal-title" id="">已有<font color="green">代付</font>记录</h4>
        </div>
        <c:if test="${empty dslist}">
            <div class="modal-body form-horizontal">
                <ul class="nav nav-pills nav-stacked">
                    <li style="padding-bottom: 10px"><i class="fa fa-circle-o" style="color: green"></i>暂无记录<span
                            class="pull-right">暂无<font color="green">代付</font>记录</span></li>
                </ul>
            </div>
        </c:if>
        <c:if test="${!empty dslist}">
            <table class="table table-bordered table-hover">
                <tbody>
                <tr>
                    <th class="text-center" style="width: 80px">期数</th>
                    <th class="text-center" style="width: 80px">服务费(分)</th>
                    <th class="text-center" style="width: 100px">代付日期</th>
                    <th class="text-center" style="width: 150px">总代付金额(分)</th>
                    <th class="text-center" style="width: 150px">执行结果</th>
                    <th class="text-center" style="width: 150px">手动审核</th>
                </tr>
                <%
                    TtList dslist = (TtList) request.getAttribute("dslist");
                    if (dslist.size() > 0) {
                        for (TtMap ds : dslist) {
                %>
                <tr>
                    <td class="text-center" style="width: 80px"><%=ds.get("periods")%>
                    </td>
                    <td class="text-center" style="width: 80px"><%=ds.get("fw_price")%>
                    </td>
                    <td class="text-center" style="width: 100px"><%=ds.get("ds_date")%>
                    </td>
                    <td class="text-center" style="width: 100px"><%=ds.get("amount")%>
                    </td>
                    <td class="text-center" style="width: 150px">
                        <select class="form-control" disabled>
                            <%=Tools.dicopt(DataDic.dic_tlzf_ds_bc_status, ds.get("bc_status"))%>
                        </select>
                    </td>
                    <td class="text-center" style="width: 150px">
                        <select id="sd_status" name="sd_status"
                                onchange="ajax_edit('<%=ds.get("id")%>','sd_status',this.value,'tlzf_dk_details');" n
                                class="form-control">
                            <%=Tools.dicopt(DataDic.dic_tlzf_sd_status, ds.get("sd_status"))%>
                        </select>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
                </tbody>
            </table>
        </c:if>
        <div class="modal-header">
            <h4 class="modal-title" id="">新建<font color="green">代付</font></h4>
        </div>
        <input type="hidden" name="tl" value="3">
        <input type="hidden" name="cn" value="${cn}">
        <input type="hidden" name="type" value="${type}">
        <input type="hidden" name="id" value="0">
        <input type="hidden" name="icbc_id" value="${icbc.id}">
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">账户名:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="account_name" name="account_name" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">证件类型:</label>
            <div class="col-sm-9">
                <select id="cardid_type" name="cardid_type" class="form-control">
                    <%=Tools.dicopt(DataDic.dic_tlzf_cardid_type, "0")%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">证件号:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="cardid" name="cardid" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">银行卡号:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="account_no" name="account_no" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">银行代码:</label>
            <div class="col-sm-9">
                <select id="bank_code" name="bank_code" class="form-control">
                    <%=Tools.dicopt(DataDic.dic_tlzf_bank_code, "0102")%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">手机号:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="tel" name="tel" value=""/>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">信用卡CVV2:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="cvv2" id="cvv2" placeholder="仅用于信用卡"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">有效期:</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="vailddate" id="vailddate" placeholder="MMYY，用于信用卡"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">货币类型:</label>
            <div class="col-sm-9">
                <select id="currency" name="currency" class="form-control">
                    <%=Tools.dicopt(DataDic.dic_tlzf_currency, "CNY")%>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">服务费</font>金额(分):</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="fw_price" id="fw_price" placeholder="输入服务费,单位(分)"
                       onblur="getTotlePrice()"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">期数:</font></label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="periods" id="periods" placeholder=""
                       onblur="getTotlePrice()"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">代付日期:</font></label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="ds_date" id="ds_date" placeholder=""
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label"><font color="green">代付</font>总金额(分):</label>
            <div class="col-sm-9">
                <input type="number" class="form-control" name="amount" id="amount" placeholder="输入代付金额,单位(分)"
                       value="">
            </div>
        </div>
        <div class="form-group">
            <label for="title2" class="col-sm-3 control-label">备注(可不填)</label>
            <div class="col-sm-9">
                <input type="text" class="form-control" name="remark" placeholder="比如:正常代付" value="">
            </div>
                <%--<div class="col-sm-6">
                    <div class="input-group">
                        <span class="input-group-addon">短信通知用户</span>
                        <select name="sendsms" class="form-control"
                                id="sendsms">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </div>
                </div>--%>
        </div>
    </div>
    <script>
        function getTotlePrice() {
            var fw_price = $("#fw_price").val();
            var periods = $("#periods").val();
            var amount = fw_price * periods;
            $("#amount").val(amount);
        }

        //执行一个laydate实例
        laydate.render({
            elem: '#ds_date', //指定元素
            type: 'date'
        });


    </script>
</c:if>
