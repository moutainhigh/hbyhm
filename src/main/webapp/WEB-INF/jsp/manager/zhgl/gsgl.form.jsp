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
                <h3 class="box-title">编辑公司</h3>
            </div>
        </c:if>
        <c:if test="${id eq 0}">
            <div class="box-header with-border">
                <h3 class="box-title">新增公司</h3>
            </div>
        </c:if>
        <div class="box-body">
            <div class="form-group">
                <label for="inputHouse" class="col-sm-2 control-label">加盟店信息1</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
							<span class="input-group-addon">
								公司名称
							</span>
                                <input id="name" name="name" type="text" class="form-control" onblur="showun();"
                                       placeholder="">
                            </div>
                        </div>
                        <script>
                            // $(document).ready(function() {
                            //
                            // });
                            function showun() {
                                var name = $("#name").val();
                                var py = getPinYinFirstCharacter(name, "", "");
                                py = py.replace(/\ +/g, "").replace(/[\r\n]/g, "");
                                document.getElementById("code_pre").value = py.toUpperCase();
                                document.getElementById("namepy").value = py.charAt(0).toUpperCase();
                            }

                        </script>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">签约公司名称</span>
                                <input id="name_qy" name="name_qy" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">上级公司</span>
                                <select id="up_id" name="up_id" title="请选择上级公司" data-size="10"
                                        class="selectpicker  form-control"
                                        multiple data-live-search="true" data-max-options="1">
                                    <c:forEach items="${fslist}" var="fs">
                                        <c:if test="${!empty infodb.up_id }">
                                            <option value="${fs.id}" ${infodb.up_id eq fs.id?"selected='selected'":''}>${fs.name}</option>
                                        </c:if>
                                        <c:if test="${empty infodb.up_id }">
                                            <option value="${fs.id}" ${minfo.icbc_erp_fsid eq fs.id?"selected='selected'":''}>${fs.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">启用状态</span>
                                <select id="showtag" name="showtag" class="form-control">
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                        <%
                            String s = Tools.dicopt("comm_states", 0);
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所在城市</span>
                                <select name="state_id" id="state_id" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=s%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <select name="city_id" id="city_id" class="form-control">
                                <option value="0">请选择</option>
                            </select>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">地址</span>
                                <input id="address" name="address" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                    </div>
                    <script>
                        objacl('#state_id', '#city_id', '/ttAjax?do=opt&cn=comm_citys&id=0&state_id=', '${infodb.state_id}', '${infodb.city_id}');
                    </script>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">金融商户店加盟店信息2</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">编号前缀</span>
                                <input id="code_pre" name="code_pre" type="text" value="${infodb.code_pre}"
                                       class="form-control" placeholder=""
                                       onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">名字拼音(排序用)</span>
                                <input id="namepy" name="namepy" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">OEM标识</span>
                                <input id="oem" name="oem" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">APP简称</span>
                                <input id="apptitle" name="apptitle" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">安卓下载链接</span>
                                <input id="url_apk" name="url_apk" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">iphone下载链接</span>
                                <input id="url_ios" name="url_ios" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">人行征信最低余额</span>
                                <input id="zx_mbg" name="zx_mbg" type="number" class="form-control" placeholder=""
                                       value="3000.00">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">技术支持电话(BD)</span>
                                <input id="sup_tel" name="sup_tel" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">查档绿色通道</span>
                                <select id="super_queryarchives_tag" name="super_queryarchives_tag"
                                        class="form-control">
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">金融商户店OEM图标</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <%
                            String upFile1 = "../upfile.inc.jsp";
                            String imgPreName1 = "oemimgurl";
                            String[] ssImgs1 = { //设置已有值
                                    !Tools.myIsNull(infodb.get(imgPreName1)) ? infodb.get(imgPreName1) : ""
                            };
                            ssImgs1 = ssImgs1[0].split("\u0005");
                            String sImgs1 = "";
                            for (int i = 0; i < ssImgs1.length; i++) {
                                if (ssImgs1[i] != null && !ssImgs1[i].equals("")) {
                                    sImgs1 = sImgs1 + ssImgs1[i] + "|";
                                }
                            }
                        %>
                        <%-- 可能这里用<%@include file %>模式更适合--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="1"/>
                            <jsp:param name="img_NamePre" value="<%=imgPreName1%>"/>
                            <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                            <jsp:param name="l1div_Style"
                                       value="width: 100px;height:140px;display: inline-block;text-align: center;margin: auto;"/>
                            <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                            <jsp:param name="img_FileStyle"
                                       value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                            <jsp:param name="img_Class" value="imgclass"/>
                            <jsp:param name="img_FileClass" value="uploadfileclass"/>
                            <jsp:param name="img_SmallWidth" value="100"/>
                            <jsp:param name="img_SmallHeight" value="100"/>
                            <jsp:param name="sImgs" value="<%=sImgs1%>"/>
                        </jsp:include>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label" style="color: red">金融商户店全局功能管理</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-2">
                            <div class="input-group">
                                <span class="input-group-addon">币种</span>
                                <select class="form-control" id="bintype" name="bintype">
                                    <option value="0" selected="">现金</option>
                                    <option value="1">体验币</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <span class="input-group-addon">金额</span>
                                <input id="addmoney" name="addmoney" type="number" step="0.01" class="form-control"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-2">
                            <div class="input-group">
                                <span class="input-group-addon">类型</span>
                                <select name="fctype" id="fctype" class="form-control" onchange="getfctype()">
                                    <option selected="selected" value="0">正常充值</option>
                                    <option value="1">现金打折</option>
                                    <option value="2">充值折扣</option>
                                    <option value="3">退款</option>
                                    <option value="4">违规扣款</option>
                                </select>
                            </div>
                        </div>
                        <script>
                             function getfctype() {
                                var  fctypename=$("#fctype option:selected").text();
                                 document.getElementById("czremark").value=fctypename;
                             }
                        </script>
                        <div class="col-sm-6">
                            <div class="input-group">
                                <span class="input-group-addon">备注</span>
                                <input name="czremark" id="czremark" type="text"  value="正常充值" class="form-control">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon">开通进件功能</span>
                                <select name="mg_tag" id="mg_tag" class="form-control">
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>
                        <%--                        <div class="col-sm-3">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">工行征信专项进件</span>
                                                        <select name="mgicbc_tag" id="mgicbc_tag" class="form-control">
                                                            <option value="1">是</option>
                                                            <option value="0">否</option>
                                                        </select>
                                                    </div>
                                                </div>--%>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon">授权书分类</span>
                                <select name="books_id" id="books_id" class="form-control">
                                    <option value="0">典当行</option>
                                    <option value="1">快车道</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon">推荐业务员</span>
                                <select name="ssm_id" id="ssm_id" class="form-control">
                                    <option value="0">请选择</option>
                                    <option value="1">张颖</option>
                                    <option value="2">林晴</option>
                                    <option value="3">薛花</option>
                                    <option value="4">唐伟</option>
                                    <option value="5">洪启荣</option>
                                    <option value="6">游振鑫</option>
                                    <option value="7">洪凯东</option>
                                    <option value="8">余剑钊</option>
                                    <option value="9">林福良</option>
                                    <option value="10">薛强</option>
                                    <option value="12">游振威</option>
                                    <option value="13">万晓峰</option>
                                    <option value="17">杨章伟</option>
                                    <option value="18">马燕辉</option>
                                    <option value="19">王信威</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">API 接口配置</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">API APPID</span>
                                <input id="appid" name="appid" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">API APPKEY</span>
                                <input id="appkey" name="appkey" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">API IP限制</span>
                                <input id="ip" name="ip" type="text" class="form-control" placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script type="text/javascript">
                $(document).ready(function () {
                    var purview_map = '${infodb.purview_map }';
                    var purview_map_kjs = '${infodb.purview_map_kjs }';
                    var purview_maps = purview_map.split(',');
                    for (var i in purview_maps) {
                        if (purview_maps[i]) {
                            $("#" + purview_maps[i]).attr("checked", "checked");
                            $("#" + purview_maps[i]).val("1");
                        }
                    }
                    var purview_map_kjss = purview_map_kjs.split(',');
                    for (var i in purview_map_kjss) {
                        if (purview_map_kjss[i]) {
                            $("#" + purview_map_kjss[i]).attr("checked", "checked");
                            $("#" + purview_map_kjss[i]).val("1");
                        }
                    }
                });

            </script>
            <input id="purview_map" name="purview_map" value="${infodb.purview_map }" type="hidden"/>
            <input id="purview_map_kjs" name="purview_map_kjs" value="${infodb.purview_map_kjs }" type="hidden"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">编辑权限</label>
                <div class="col-sm-10">

                    <p>勾选表示开通权限，当全部不勾选时表示此账号拥有所有权限</p>
                    <table class="table table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th><label class="checkbox-inline">
                                <input id="ckall" type="checkbox" class="check_all" onclick="checkallkjs(this,2)">
                                全选
                            </label></th>
                        </tr>
                        <tr>
                            <td><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_ks" id="order_ks" value="0"
                                       onclick="checkfl(this,2)">快速评估
                            </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_zy" id="order_zy" value="0"
                                       onclick="checkfl(this,2)">专业评估
                            </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_da" id="order_query_da" value="0"
                                       onclick="checkfl(this,2)">车辆状况查询 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_by" id="order_query_by" value="0"
                                       onclick="checkfl(this,2)">车辆保养查询 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_zx" id="order_query_zx" value="0"
                                       onclick="checkfl(this,2)">征信-人行 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_bdzx" id="order_query_bdzx" value="0"
                                       onclick="checkfl(this,2)">征信-大数据 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_thjl" id="order_query_thjl" value="0"
                                       onclick="checkfl(this,2)">通讯数据查询 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_bx" id="order_query_bx" value="0"
                                       onclick="checkfl(this,2)">保险数据查询 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_wdhmd" id="order_query_wdhmd" value="0"
                                       onclick="checkfl(this,2)">网贷黑名单 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_yhksm" id="order_query_yhksm" value="0"
                                       onclick="checkfl(this,2)">银行卡实名 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_query_yhkls" id="order_query_yhkls" value="0"
                                       onclick="checkfl(this,2)">银行卡流水 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="kj_zxjb" id="kj_zxjb" value="0" onclick="checkfl(this,2)">征信简版</label>
                                <label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kj_qcyz" id="order_kj_qcyz" value="0"
                                       onclick="checkfl(this,2)">汽车验证 </label></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">编辑快金所权限</label>
                <div class="col-sm-10">

                    <p>勾选表示开通权限，当全部不勾选时表示此账号啥权限都没有， 如果没【<strong>开通进件功能</strong>】，下面的勾了也没用！
                    </p>
                    <table class="table table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th><label class="checkbox-inline">
                                <input id="ckall" type="checkbox" class="check_all" onclick="checkallkjs(this,1)">
                                全选
                            </label></th>
                        </tr>
                        <tr>
                            <td><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_icbc" id="order_kjs_icbc" value="0"
                                       onclick="checkfl(this,1)">工行贷 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_mgcert" id="order_kjs_mgcert" value="0"
                                       onclick="checkfl(this,1)">优信租赁 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_mgcar" id="order_kjs_mgcar" value="0"
                                       onclick="checkfl(this,1)">快车贷 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_cxfq" id="order_kjs_cxfq" value="0"
                                       onclick="checkfl(this,1)">车险分期 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_pazl" id="order_kjs_pazl" value="0"
                                       onclick="checkfl(this,1)">平安租赁 </label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_zjzl" id="order_kjs_zjzl" value="0"
                                       onclick="checkfl(this,1)">安吉租赁</label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_hbyh" id="order_kjs_hbyh" value="0"
                                       onclick="checkfl(this,1)">河北银行</label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_xmgjyh" id="order_kjs_xmgjyh" value="0"
                                       onclick="checkfl(this,1)">厦门国际银行</label><label class="btn btn-info" style="">
                                <input type="checkbox" name="order_kjs_hxyh" id="order_kjs_hxyh" value="0"
                                       onclick="checkfl(this,1)">华夏银行</label>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <script>
                function checkallkjs(obj, type) {
                    var purview_map = "";
                    var purview_map_kjs = "";
                    if (type == 1) {//勾选大类
                        input = document.getElementsByTagName("input");
                        for (var i = 0; i < input.length; i++) {
                            if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("/") < 0)) {
                                if (input[i].name.indexOf("kjs_") < 0) {
                                    continue;
                                }
                                input[i].checked = obj.checked;
                                input[i].value = obj.checked ? 1 : 0;
                                if (input[i].value == 1) {
                                    purview_map_kjs = purview_map_kjs + input[i].name + ",";
                                }
                            }
                        }

                        document.getElementById("purview_map_kjs").value = purview_map_kjs;
                    }
                    if (type == 2) {
                        input = document.getElementsByTagName("input");
                        for (var i = 0; i < input.length; i++) {
                            if (input[i].name.indexOf("kjs_") >= 0) {
                                continue;
                            }
                            if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("/") < 0)) {
                                input[i].checked = obj.checked;
                                input[i].value = obj.checked ? 1 : 0;
                                if (input[i].value == 1) {
                                    purview_map = purview_map + input[i].name + ",";
                                }
                            }
                        }
                        document.getElementById("purview_map").value = purview_map;
                    }
                }


            </script>
            <div class="form-group">
                <label class="col-sm-2 control-label">启用账号列表：</label>
                <div class="col-sm-10">
                </div>
            </div>
            <div class="form-group">
                <label for="link" class="col-sm-2 control-label">权限列表</label>
                <div class="col-sm-10">
                    <table class="table table-bordered table-hover">
                        <tbody>
                        <tr>
                            <th style="width: 140px"><label class="checkbox-inline"><input type="checkbox"
                                                                                           class="check_all"
                                                                                           onclick="checkall(this,1)">全选</label>
                            </th>
                            <th><label class="checkbox-inline"><input type="checkbox" class="check_all"
                                                                      onclick="checkall(this,2)">全选</label></th>
                        </tr>
                        <%
                            Map<String, Object> menus = (Map<String, Object>) request.getAttribute("modals");
                            for (String key : menus.keySet()) {  //一级菜单循环开始
                                Map<String, Object> mainList = (Map<String, Object>) menus.get(key);
                                TtList submenus = (TtList) mainList.get("submenu");
                                TtMap mainInfo = (TtMap) mainList.get("mainmenu");
                                String iconHtmlMain = mainInfo.get("icohtml");
                                iconHtmlMain = !Tools.myIsNull(iconHtmlMain) ? iconHtmlMain : "<i class=\"fa fa-sitemap\"></i>";
                                boolean haveAgp = ("," + infodb.get("purview_map_ty")).indexOf("," + mainInfo.get("id") + ",") != -1;
                                String mainCheckName = "MAIN/" + mainInfo.get("id");
                        %>
                        <tr>
                            <td><label class="checkbox-inline"><input type="checkbox" name="<%=mainCheckName%>"
                                                                      id="<%=mainCheckName%>"
                                                                      value="<%=haveAgp?"1":"0"%>"
                                                                      onclick="checkfl(this)"
                                "<%=haveAgp ? "checked" : ""%>" /><%=key%>
                            </label>
                            </td>
                            <td>
                                <%
                                    for (TtMap keysub : submenus) {//二级级菜单循环开始
                                        String cn = keysub.get("cn");
                                        String type = keysub.get("type");
                                        String sdo = keysub.get("sdo");
                                        String icohtml = keysub.get("icohtml");
                                        String subMenuName = keysub.get("showmmenuname");
                                        boolean haveAgp2 = ("," + infodb.get("purview_map_ty")).indexOf("," + keysub.get("id") + ",") != -1;
                                        String subCheckName = mainCheckName.replace("MAIN", "SUBMAIN") + "/" + keysub.get("id");
                                %>
                                <label class="checkbox-inline"><input type="checkbox" name="<%=subCheckName%>"
                                                                      id="<%=subCheckName%>"
                                                                      value="<%=haveAgp2?"1":"0"%>"
                                                                      onclick="check(this)"/>
                                    <%=subMenuName%>
                                </label>
                                <%}%>
                            </td>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        input = document.getElementsByTagName("input");
        for (var i = 0; i < input.length; i++) {
            if ((input[i].type == "checkbox")) {
                input[i].checked = input[i].value == 1;
            }
        }
        $("#info_form").submit(function () {
            //alert($("input:checkbox[name='orders']").val());
            if (this.name.value.length == 0) {
                alert("请输入管理组名称");
                return false;
            }
        });
    });

    function check(obj) {
        obj.value = (obj.checked ? "1" : "0");
    }

    function checkall(obj, type) {
        if (type == 1) { //勾选大类
            input = document.getElementsByTagName("input");
            for (var i = 0; i < input.length; i++) {
                if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("MAIN/") == 0)) {
                    input[i].checked = obj.checked;
                    input[i].value = obj.checked ? 1 : 0;
                }
            }
        } else {
            input = document.getElementsByTagName("input");
            for (var i = 0; i < input.length; i++) {
                if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("SUBMAIN/") == 0)) {
                    input[i].checked = obj.checked;
                    input[i].value = obj.checked ? 1 : 0;
                }
            }

        }
    }

    function checkfl(obj, type) {
        var purview_map_kjs="";
        var purview_map="";
        obj.value = (obj.checked ? "1" : "0");
        input = document.getElementsByTagName("input");
        for (var i = 0; i < input.length; i++) {

            if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf(obj.name) >= 0)) {
                input[i].checked = obj.checked;
                input[i].value = obj.checked ? 1 : 0;
            }
            if(type==1){
            if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("/") < 0)) {
                if (input[i].name.indexOf("kjs_")>=0&&input[i].value==1) {
                    purview_map_kjs = purview_map_kjs + input[i].name + ",";
                }
            }
            }
            if(type==2){
                if ((input[i].type == "checkbox") && (input[i].name.length > 0) && (input[i].name.indexOf("/") < 0)) {
                    if (input[i].name.indexOf("kjs_")<0&&input[i].value==1) {
                        purview_map = purview_map + input[i].name + ",";
                    }
                }
            }
        }
        if(type==1) {
            document.getElementById("purview_map_kjs").value = purview_map_kjs;
        }
        if(type==2) {
            document.getElementById("purview_map").value = purview_map;
        }
    }


</script>