<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TtMap infodb = (TtMap) request.getAttribute("infodb");
    boolean bAdd = Tools.myIsNull(request.getParameter("id"));
    boolean bNotReadOnley = Tools.myIsNull(request.getParameter("id")) || Tools.myIsNull(infodb.get("username"));
    TtMap minfo = (TtMap) request.getAttribute("minfo");
%>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">
        <c:if test="${id ne 0}">
            <div class="box-header with-border">
                <h3 class="box-title">编辑用户</h3>
            </div>
        </c:if>
        <c:if test="${id eq 0}">
            <div class="box-header with-border">
                <h3 class="box-title">新增用户</h3>
            </div>
        </c:if>
        <div class="box-body">
            <input type="hidden" id="gemsid" name="gemsid" value="${infodb.gemsid}">
            <%if (minfo.get("superadmin").equals("1")) {%>
            <div class="form-group">
                <label class="col-sm-2 control-label">账户类型</label>
                <div class="col-sm-10">
                    <select id="superadmin" name="superadmin" class="form-control">
                        <option value="0">普通账户</option>
                        <option value="1">超级管理员</option>
                        <option value="3">管理员</option>
                        <option value="2">内部员工</option>
                    </select>
                </div>
            </div>
            <%} else if (minfo.get("superadmin").equals("3")) {%>
            <div class="form-group">
                <label class="col-sm-2 control-label">账户类型</label>
                <div class="col-sm-10">
                    <select id="superadmin" name="superadmin" class="form-control">
                        <option value="0">普通账户</option>
                        <option value="2">内部员工</option>
                        <option value="3">管理员</option>
                    </select>
                </div>
            </div>
            <%} else if (minfo.get("superadmin").equals("2")) {%>
            <div class="form-group">
                <label class="col-sm-2 control-label">账户类型</label>
                <div class="col-sm-10">
                    <select id="superadmin" name="superadmin" class="form-control">
                        <option value="0">普通账户</option>
                        <option value="2">内部员工</option>
                    </select>
                </div>
            </div>
            <%} else {%>
            <div class="form-group">
                <label class="col-sm-2 control-label">账户类型</label>
                <div class="col-sm-10">
                    <select id="superadmin" name="superadmin" class="form-control">
                        <option value="0">普通账户</option>
                    </select>
                </div>
            </div>
            <%}%>
            <%
                //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
                String sp = Tools.dicopt("comm_states", 0);//省会，
            %>
            <div class="form-group">
                <label class="col-sm-2 control-label">城市选择</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-6">
                            <div class="input-group">
                                <span class="input-group-addon">省</span>
                                <select name="stateid" id="stateid"  class="selectpicker  form-control" multiple data-live-search="true" data-max-options="1">
                                    <option value="0">请选择</option>
                                    <%=sp%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="input-group">
                                <span class="input-group-addon">市</span>
                                <select name="cityid" id="cityid" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <script>
                /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                                        /ttAjax也可以单独使用，比如
                                                        /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                                        * */
                objacl('#stateid', '#cityid', '/ttAjax?do=opt&cn=comm_citys&state_id=', '${infodb.stateid}', '${infodb.cityid}');
            </script>
            <div class="form-group">
                <label class="col-sm-2 control-label">所属公司</label>
                <div class="col-sm-10">
                    <select id="icbc_erp_fsid" name="icbc_erp_fsid" title="请选择所属公司" data-size="10"
                            class="selectpicker  form-control"
                            multiple data-live-search="true" data-max-options="1" onchange="getagp(this.value) ">
                        <c:forEach items="${fslist}" var="fs">
                            <option value="${fs.id}" ${infodb.icbc_erp_fsid eq fs.id?"selected='selected'":''}>${fs.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">姓名</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" name="name" value="${infodb.name}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">身份证号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="idcard" name="idcard">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">电话</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="tel" name="tel" onblur="showun()">
                </div>
            </div>
            <script>
                function showun() {
                    var name = $("#name").val();
                    var py = getPinYinFirstCharacter(name, "", "");
                    py = py.replace(/\ +/g, "").replace(/[\r\n]/g, "");
                    var tel = $("#tel").val();
                    $("#username").val(py + "@" + tel);
                    var password = tel.substring(tel.length - 6, tel.length);
                    $("#password").val(password);
                }
            </script>
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名</label>
                <div class="col-sm-10">
                    <input type="text" <%=bNotReadOnley?"":"readonly"%> class="form-control" id="username"
                           name="username">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">用户密码</label>
                <div class="col-sm-10">
                    <div class="input-append input-group">
                        <input type="password" class="form-control" id="password" name="password"
                               value="">
                        <input type="text"
                               class="form-control" id="userpass1" name="userpass1" placeholder="密码"
                               style="display: none;" value="">
                        <span onclick="sh_password()" tabindex="100" title="点击显示/隐藏密码" class="add-on input-group-addon"
                              style="cursor: pointer;">
							<i id="i_style" class="glyphicon icon-eye-open glyphicon-eye-open"></i>
						</span>
                    </div>
                </div>
                <script>
                    function sh_password() {
                        var s = document.getElementById("i_style").className;
                        //alert(s);
                        if (s == 'glyphicon icon-eye-open glyphicon-eye-open') {
                            document.getElementById("userpass1").value = document
                                .getElementById("password").value;
                            document.getElementById("i_style").className = 'glyphicon icon-eye-close glyphicon-eye-close';
                            document.getElementById("userpass1").style.display = 'inline-block';
                            document.getElementById("password").style.display = 'none';
                        } else {

                            document.getElementById("password").value = document
                                .getElementById("userpass1").value;
                            document.getElementById("i_style").className = 'glyphicon icon-eye-open glyphicon-eye-open';
                            document.getElementById("password").style.display = 'inline-block';
                            document.getElementById("userpass1").style.display = 'none';
                        }

                    }
                </script>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">头像</label>
                <div class="col-sm-10">
                    <%
                        String upFile = "../upfile.inc.jsp";
                        String imgPreName = "imgurl";
                        String[] ssImgs = { //设置已有值
                                !Tools.myIsNull(infodb.get(imgPreName)) ? infodb.get(imgPreName) : ""
                        };
                        String sImgs = "";
                        for (int i = 0; i < ssImgs.length; i++) {
                            sImgs = sImgs + ssImgs[i] + "|";
                        }
                    %>
                    <%-- 可能这里用<%@include file %>模式更适合--%>
                    <jsp:include page="<%=upFile%>">
                        <jsp:param name="img_MarginImgSrc" value=""/>
                        <jsp:param name="img_MarginImgClass" value=""/>
                        <jsp:param name="img_Total" value="1"/>
                        <jsp:param name="img_NamePre" value="imgurl"/>
                        <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                        <jsp:param name="l1div_Style"
                                   value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;"/>
                        <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                        <jsp:param name="img_FileStyle"
                                   value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                        <jsp:param name="img_Class" value="imgclass"/>
                        <jsp:param name="img_FileClass" value="uploadfileclass"/>
                        <jsp:param name="img_SmallWidth" value="100"/>
                        <jsp:param name="img_SmallHeight" value="100"/>
                        <jsp:param name="sImgs" value="<%=sImgs%>"/>
                    </jsp:include>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">显示/隐藏</label>
                <div class="col-sm-10">
                    <select id="showtag" name="showtag" class="form-control">
                        <option value="">请选择</option>
                        <option value="1">显示</option>
                        <option value="0">隐藏</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">级别</label>
                <div class="col-sm-10">
                    <select name="cp" id="cp" class="form-control" onchange="upcp()">
                        <option value="1">一级账号</option>
                        <option value="2">二级账号</option>
                        <option value="0">三级账号</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">上级账户</label>
                <div class="col-sm-10">
                    <select name="upac_id" id="upac_id" class="form-control">
                        <option value="0">请选择</option>
                    </select>
                </div>
            </div>
            <script>
                $(document).ready(function () {
                    var upac_s = $("#upac_id");
                    var fsid = $("#icbc_erp_fsid").val();
                    upac_s.empty();
                    upac_s.append("<option value='0'>请选择</option>");
                    var cp = '${infodb.cp}';
                    var upac_id = '${infodb.upac_id}';
                    if (cp == 2) {
                        $.ajax({
                            url: "/ttAjax?do=opt&re=json&cn=assess_admin&id=0&icbc_erp_fsid=" + fsid + "&cp=1&deltag=0&showtag=1",
                            success: function (result) {
                                var jsonObj = eval('(' + result + ')');
                                for (var r in jsonObj) {
                                    if (jsonObj[r].id == upac_id) {
                                        upac_s.append("<option selected='selected'  value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                    } else {
                                        upac_s.append("<option value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                    }
                                }
                            }
                        });
                    }
                    if (cp == 0) {
                        $.ajax({
                            url: "/ttAjax?do=opt&re=json&cn=assess_admin&id=0&icbc_erp_fsid=" + fsid + "&cp=2&deltag=0&showtag=1",
                            success: function (result) {
                                var jsonObj = eval('(' + result + ')');
                                for (var r in jsonObj) {
                                    if (jsonObj[r].id == upac_id) {
                                        upac_s.append("<option selected='selected' value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                    } else {
                                        upac_s.append("<option value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                    }
                                }
                            }
                        });
                    }
                });

                //获取上级列表
                function upcp() {
                    var cp = $("#cp").val();
                    var upac_id = $("#upac_id");
                    var fsid = $("#icbc_erp_fsid").val();
                    upac_id.empty();
                    upac_id.append("<option value='0'>请选择</option>");
                    if (cp == 2) {
                        $.ajax({
                            url: "/ttAjax?do=opt&re=json&cn=assess_admin&id=0&icbc_erp_fsid=" + fsid + "&cp=1&deltag=0&showtag=1",
                            success: function (result) {
                                var jsonObj = eval('(' + result + ')');
                                for (var r in jsonObj) {
                                    upac_id.append("<option value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                }
                            }
                        });
                    }
                    if (cp == 0) {
                        $.ajax({
                            url: "/ttAjax?do=opt&re=json&cn=assess_admin&id=0&icbc_erp_fsid=" + fsid + "&cp=2&deltag=0&showtag=1",
                            success: function (result) {
                                var jsonObj = eval('(' + result + ')');
                                for (var r in jsonObj) {
                                    upac_id.append("<option value='" + jsonObj[r].id + "'>" + jsonObj[r].name + "</option>");
                                }
                            }
                        });
                    }

                }
                function getagp(fsid) {
                    var agpid = $("#agpid");
                    agpid.empty();
                    agpid.append("<option value='0'>请选择</option>");
                    $.post("/manager/command?sdo=yhgl_agp&id=" + fsid,
                        function (result) {
                            var jsonObj = eval('(' + result + ')');
                            var jsonObj1 = eval('(' + jsonObj.msg + ')');
                            for (var r in jsonObj1) {
                                agpid.append("<option value='" + jsonObj1[r].id + "'>" + jsonObj1[r].name + "</option>");
                            }
                        });
                }
                // objacl('#cp', '#upac_id', '/ttAjax?do=opt&cn=assess_admin&id=0&icbc_erp_fsid=${minfo.icbc_erp_fsid}&cp=', '${infodb.cp}', '${infodb.upac_id}');
            </script>
            <div class="form-group">
                <label class="col-sm-2 control-label">所属角色组</label>
                <div class="col-sm-10">
                    <select id="agpid" name="agpid" class="form-control">
                        <%if (bAdd) {%>
                        <option value="0" selected>请选择</option>
                        <%} else {%>
                        <option value="0" <%=infodb.get("agpid").equals("0") ? "selected=\"selected\"" : ""%> >请选择
                        </option>
                        <%}%>
                        <%
                            TtList agplist = Tools.reclist("select * from icbc_admin_agp where showtag=1 and systag=0 AND fsid=" + minfo.get("icbc_erp_fsid"));
                            if (agplist.size() > 0) {
                        %>
                        <%=Tools.dicopt("icbc_admin_agp", Tools.myIsNull(infodb.get("agpid")) ? 0 : Long.valueOf(infodb.get("agpid")), "systag=0 AND fsid=" + minfo.get("icbc_erp_fsid"), "")%>
                        <%
                        } else {
                        %>
                        <%=Tools.dicopt("icbc_admin_agp", Tools.myIsNull(infodb.get("agpid")) ? 0 : Long.valueOf(infodb.get("agpid")), "showtag=1 and systag=1 AND fsid=0", "")%>
                        <%}%>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>