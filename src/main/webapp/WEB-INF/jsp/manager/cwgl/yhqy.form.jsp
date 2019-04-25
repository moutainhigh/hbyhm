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
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">
        <c:if test="${id ne 0}">
            <div class="box-header with-border">
                <h3 class="box-title">编辑</h3>
            </div>
        </c:if>
        <c:if test="${id eq 0}">
            <div class="box-header with-border">
                <h3 class="box-title">新增</h3>
            </div>
        </c:if>
        <div class="box-body">
            <div class="form-group">
                <label class="col-sm-2 control-label">基础信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">关联客户</span>
                                <select id="icbc_id" name="icbc_id" title="请选择关联客户" data-size="10"
                                        onchange="geticbc(this.value)"
                                        class="selectpicker  form-control"
                                        multiple data-live-search="true" data-max-options="1">
                                    <c:forEach items="${icbclist}" var="i">
                                        <option value="${i.id}">${i.c_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">账户名</span>
                                <input type="text" class="form-control" id="account_name" name="account_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">账号</span>
                                <input type="text" class="form-control" id="account_no" name="account_no"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">银行代码</span>
                                <select id="bank_code" name="bank_code" title="请选择银行"
                                        class="selectpicker  form-control"
                                        multiple data-live-search="true" data-max-options="1" data-size="10"
                                        style="font-size: 14px;">
                                    <%
                                        TtList banklist = Tools.reclist("select * from tlzf_bankcode");
                                        if (banklist.size() > 0) {
                                            for (TtMap bank : banklist) {
                                    %>
                                    <option value="<%=bank.get("code")%>"><%=bank.get("name")%>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>

                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">账户类型</span>
                                <select id="account_type" name="account_type" class="form-control">
                                    <option value="00">借记卡</option>
                                    <option value="02">信用卡</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">账户属性</span>
                                <select id="account_prop" name="account_prop" class="form-control">
                                    <option value="0">私人</option>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">开户证件类型</span>
                                <select id="cardid_type" name="cardid_type" class="form-control">
                                    <option value="0">身份证</option>
                                    <option value="1">户口簿</option>
                                    <option value="2">护照</option>
                                    <option value="3">军官证</option>
                                    <option value="4">士兵证</option>
                                    <option value="5">港澳居民来往内地通行证</option>
                                    <option value="6">台湾同胞来往内地通行证</option>
                                    <option value="7">临时身份证</option>
                                    <option value="8">外国人居留证</option>
                                    <option value="9">警官证</option>
                                    <option value="X">其他证件</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">证件号</span>
                                <input type="text" class="form-control" id="cardid" name="cardid" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">手机号</span>
                                <input type="text" class="form-control" id="tel" name="tel" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">CVV2(信用卡时必填)</span>
                                <input type="text" class="form-control" id="cvv2" name="cvv2" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">有效期(信用卡时必填)</span>
                                <input type="text" class="form-control" id="vailddate" name="vailddate"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">商户保留信息</span>
                                <input type="text" class="form-control" id="merrem" name="merrem" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">备注</span>
                                <input type="text" class="form-control" id="remark" name="remark" placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">签约绑定</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">验证码</span>
                                <input type="text" class="form-control" id="smscode" name="smscode" value="" placeholder="">
                                <span class="input-group-addon"><a id="yzm" name="yzm"
                                                                   onclick="getsmscode()">获取①</a></span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">交易批次号</span>
                                <input type="text" class="form-control" id="req_sn" name="req_sn" placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-6">
                            <div class="input-group">
                                <span class="input-group-addon">协议号</span>
                                <input type="text" class="form-control" id="agrmno" name="agrmno" placeholder="" />
                                <span class="input-group-addon"><a onclick="qy()">签约②</a></span>
                                <span class="input-group-addon"><a onclick="findqy_res()">查询验证③</a></span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon" style="font-size: 14px;">协议状态</span>
                                <select class="form-control"  id="qy_status" name="qy_status"
                                        style="font-size: 14px;">
                                    <%=Tools.dicopt(DataDic.dic_tlzf_xy_status,infodb.get("qy_status"))%>
                                </select>
                                <span class="input-group-addon"><a onclick="tljy()">解约④</a></span>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">审核和数据填充处理</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon" style="font-size: 14px;">审核状态</span>
                                <select class="form-control" id="bc_status" name="bc_status" onchange="autoremark(this)"
                                        style="font-size: 14px;">
                                    <option value="0">请选择</option>
                                    <%=Tools.dicopt(DataDic.dic_tlzf_qystatus,infodb.get("bc_status"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon" style="font-size: 14px;">类型</span>
                                <input type="text" class="form-control" readonly="" value="银行签约"
                                       style="font-size: 14px;">
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">留言备注说明</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-5">
                            <div class="input-group">
                                <span class="input-group-addon" style="font-size: 14px;">审核留言</span>
                                <input type="text" class="form-control" name="remark1" id="remark1"
                                       style="font-size: 14px;">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon" style="font-size: 14px;">留言快速通道</span>
                                <select class="form-control" id="cyly" onchange="setremark(this)"
                                        style="font-size: 14px;">
                                    <option value="请选择">请选择</option>
                                    <option value="查询完成，详情请点击历史查询->已完成->查看订单！">查询完成，详情请点击历史查询-&gt;已完成-&gt;查看订单！</option>
                                    <option value="恭喜您初审通过,请点编辑按钮,按提示上传其他补充材料！">恭喜您初审通过,请点编辑按钮,按提示上传其他补充材料！</option>
                                    <option value="查询完成，但无数据">查询完成，但无数据</option>
                                    <option value="相关资料不完成或不够清晰！接口异常">相关资料不完成或不够清晰！接口异常</option>
                                    <option value="接口异常">接口异常</option>
                                    <option value="行驶证信息不清楚，请重新上传，谢谢">行驶证信息不清楚，请重新上传，谢谢</option>
                                    <option value="皖52U230无该车牌信息">皖52U230无该车牌信息</option>
                                    <option value="相关资料不完整且不够清晰！请重新上传，谢谢">相关资料不完整且不够清晰！请重新上传，谢谢</option>
                                    <option value="请上传完整的行驶证和驾驶证！">请上传完整的行驶证和驾驶证！</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">历史记录</label>
                <div class="col-sm-10">
                    <textarea style="width: 80%; height: 200px" class="form-control" disabled=""><%TtList lslist=(TtList) request.getAttribute("resultlist");if(lslist!=null&&lslist.size()>0){ for(TtMap l:lslist){%><%=l.get("dt_add")%>:状态:<%=DataDic.dic_zx_status.get(l.get("status"))%>,留言:<%=l.get("remark").replace("null","")%>&#10;<%}} %></textarea>
                </div>
            </div>
            <input id="merchant_id" name="merchant_id" value="" type="hidden" />
            <input id="result_code" name="result_code" value="" type="hidden" />
            <input id="result_msg" name="result_msg" value="" type="hidden" />
            <input id="result_content" name="result_content" value="" type="hidden" />
        </div>
    </div>
</div>
<script>

    var flag = 1;
    var i = 60 * 10;
    var timer;
    function descdata() {
        i = i - 1;
        document.getElementById("yzm").text = i + "s";
        if (i == 0) {
            document.getElementById("yzm").text = "重新获取";
            flag = 1;
            i = 60 * 10;
            return;
        }
        timer= setTimeout('descdata()', 1000);
    }

    function stoper()	{
        clearTimeout(timer);
    }


    function geticbc(id) {
        $.getJSON("/ttAjax?do=info&re=json&cn=kj_icbc&id=" + id, function (result) {
            $("#account_name").val(result.c_name);
            $("#tel").val(result.c_tel);
            $("#cardid").val(result.c_cardno);
        });
    }

    //发送验证码
    function getsmscode() {
        var data = {
            BANK_CODE: $("#bank_code").val(),
            ACCOUNT_TYPE: $("#account_type").val(),
            ACCOUNT_NO: $("#account_no").val(),
            ACCOUNT_NAME: $("#account_name").val(),
            ACCOUNT_PROP: $("#account_prop").val(),
            ID_TYPE: $("#cardid_type").val(),
            ID: $("#cardid").val(),
            TEL: $("#tel").val(),
            CVV2: $("#cvv2").val(),
            VAILDDATE: $("#vailddate").val(),
            MERREM: $("#merrem").val(),
            REMARK: $("#remark").val()
        };
        $.post("/kcdhttp?query=2&type=310001", data, function (result) {
            var res = eval('(' + result + ')');
            if (res.INFO != null && res.INFO != "" && res.INFO != "undefined") {
                if (res.INFO.RET_CODE == "0000") {
                    alert(res.INFO.ERR_MSG + ",请在十分钟内输入验证码并签约!");
                    $("#req_sn").val(res.INFO.REQ_SN);
                    descdata();
                }
            } else {
                alert(res[0].ERR_MSG);
            }
        });
    }

    //签约
    function qy() {
        stoper();
        document.getElementById("yzm").text = "重新获取";
        var smscode = $("#smscode").val();
        var req_sn = $("#req_sn").val();

        if (typeof smscode == "undefined" || smscode == null || smscode == "") {
            alert("验证码不能为空");
            return false;
        }
        if (typeof req_sn == "undefined" || req_sn == null || req_sn == "") {
            alert("交易批次号不能为空");
            return false;
        }
        var data = {
            SRCREQSN: req_sn,
            VERCODE: smscode
        };
        $.post("/kcdhttp?query=2&type=310002", data, function (result) {

            $("#result_content").val(result);
            var res = eval('(' + result + ')');
            if (res.INFO != null && res.INFO != "" && res.INFO != "undefined") {
                if (res.INFO.RET_CODE == "0000") {
                    alert(res.FAGRCRET.ERR_MSG);
                    $("#agrmno").val(res.FAGRCRET.AGRMNO);
                    $("#result_msg").val(res.FAGRCRET.ERR_MSG);
                    $("#result_code").val(res.FAGRCRET.RET_CODE);
                }
            } else {
                alert(res[0].ERR_MSG);
                $("#result_msg").val(res[0].ERR_MSG);
                $("#result_code").val(res[0].RET_CODE);
            }

        });
    }
    //查询签约状态
    function findqy_res(){
        var account_no =$("#account_no").val();
        if (typeof account_no == "undefined" || account_no == null || account_no == "") {
            alert("账号不能为空");
            return false;
        }
        var data = {
            ACCOUNT_NO: account_no
        };
        $.post("/kcdhttp?query=2&type=340009", data, function (result) {
            var res = eval('(' + result + ')');
            if (res.INFO != null && res.INFO != "" && res.INFO != "undefined") {
                if (res.INFO.RET_CODE == "0000") {
                    if(res.QAGRRSP[0].STATUS=='2'){
                        alert(res.INFO.ERR_MSG);
                    }else{
                        alert("已失效,请重新签约");
                    }
                    $("#agrmno").val(res.QAGRRSP[0].AGRMNO);
                    document.getElementById("qy_status").value=res.QAGRRSP[0].STATUS;
                }
            } else {
                alert(res[0].ERR_MSG);
            }
        });
    }
    //解约
    function  tljy() {
        var account_no =$("#account_no").val();
        var agrmno =$("#agrmno").val();
        if (typeof account_no == "undefined" || account_no == null || account_no == "") {
            alert("账号不能为空");
            return false;
        }
        if (typeof agrmno == "undefined" || agrmno == null || agrmno == "") {
            alert("协议号不能为空");
            return false;
        }
        var data = {
            ACCOUNT_NO: account_no,
            AGRMNO: agrmno
        };
        var msg="";
        $.post("/kcdhttp?query=2&type=310003", data, function (result) {
            var res = eval('(' + result + ')');
            if (res.INFO != null && res.INFO != "" && res.INFO != "undefined") {
                if(res.INFO.RET_CODE == "0000"){
                    msg=res.FAGRCNLRET.ERR_MSG;
                if (res.FAGRCNLRET.RET_CODE == "0000") {
                    //$("#agrmno").val(res.QAGRRSP[0].AGRMNO);
                    document.getElementById("qy_status").value="1";
                }
                }else{
                    msg=res.INFO.ERR_MSG;
                }
                alert(msg);
            }
        });
    }
    function autoremark() {
        if ($("#bc_status").val() == 3) {//完成
            $("#remark1").val("查询完成，详情请点击订单详情页查看！");
        } else {
            $("#remark1").val("");
        }
    }

    function setremark(obj) {
        if ($("#cyly").val() != "请选择快速留言") {//完成
            $("#remark1").val($("#cyly").val());
        }
    }
</script>
