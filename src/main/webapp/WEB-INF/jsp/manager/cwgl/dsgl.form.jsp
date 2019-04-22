<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TtMap infodb = (TtMap) request.getAttribute("infodb");
    TtMap minfo = (TtMap) request.getAttribute("minfo");
%>
<%--<div class="box-body">
    <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">已有<font color="green">代收</font>记录(共3)期，
            创建时间：2018-01-15 11:20:18</h4>
    </div>
    <table class="table table-bordered table-hover">
        <tbody>
        <tr>
            <th class="text-center" style="width: 80px">预约日期</th>
            <th class="text-center" style="width: 80px">分期金额</th>
            <th class="text-center" style="width: 60px">已执行</th>
            <th class="text-center" style="width: 100px">执行结果</th>
            <th class="text-center" style="width: 150px">手动审核状态</th>
        </tr>
        <tr>
            <td class="text-center">2018-02-10</td>
            <td class="text-center">965000</td>
            <td class="text-center">是</td>
            <td class="text-center">1651-0000-0000</td>
            <td class="text-center">
                <select id="checkstatus_1651" onchange="" class="form-control">
                    <option value="0">待审核</option>
                    <option selected="selected" value="1">代收成功</option>
                    <option value="2">其他渠道支付成功</option>
                    <option value="3">失败</option>
                </select>
            </td>
        </tr>
        </tbody>
    </table>
</div>--%>
<div class="box-body">
    <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">已有<font color="green">代收</font>记录</h4>
    </div>
    <div class="modal-body form-horizontal">
        <ul class="nav nav-pills nav-stacked">
            <li style="padding-bottom: 10px"><i class="fa fa-circle-o" style="color: green"></i>暂无记录<span class="pull-right">暂无<font color="green">代收</font>记录</span></li>		</ul>
    </div>
    <div class="modal-header">
        <h4 class="modal-title" id="myModalLabel">新建<font color="green">代收</font></h4>
    </div>
    <input type="hidden" name="qryid" value="549"> <input type="hidden" name="type" value="9">
    <input type="hidden" name="gems_fs_id" value="169">
    <input type="hidden" name="bank_id" value="102">
    <input type="hidden" name="ds_type" value="0">
    <input type="hidden" name="c_name" value="吴丽华">
    <input type="hidden" name="c_cardno" value="35032219791211055X">
    <input type="hidden" name="sms_tel" value="18122399382">
    <div class="form-group" style="padding-top: 25px">
        <label for="title2" class="col-sm-3 control-label">签约银联卡:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="bt" value="吴丽华-6212263602050814094" disabled="">
        </div>
    </div>
    <div class="form-group">
        <label for="title2" class="col-sm-3 control-label"><font color="green">代收</font>金额(分):</label>
        <div class="col-sm-9">
            <input type="number" class="form-control" name="AMOUNT" id="AMOUNT" placeholder="输入代收金额,单位(分)" value="19000000">
        </div>
    </div>	<div class="form-group">
    <label for="title2" class="col-sm-3 control-label">备注(可不填)</label>
    <div class="col-sm-3">
        <input type="text" class="form-control" name="remark" placeholder="比如:正常代收" value="">
    </div>
    <div class="col-sm-6">
        <div class="input-group">
            <span class="input-group-addon">短信通知用户</span> <select name="sendsms" class="form-control" id="sendsms">
            <option value="1">是</option><option selected="selected" value="0">否</option>	                            		</select>
        </div>
    </div>
</div>
</div>

