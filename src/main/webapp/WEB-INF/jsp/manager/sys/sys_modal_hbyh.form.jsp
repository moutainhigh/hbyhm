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
    long id_uplevel = 0;
    if (!Tools.myIsNull(infodb.get("id_uplevel"))) {
        id_uplevel = Long.parseLong(infodb.get("id_uplevel"));
    }
%>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">
        <div class="box-header with-border">
            <h3 class="box-title">编辑模块</h3>
        </div>
        <div class="box-body" id="tab-content">
            <div class="form-group">
                <label class="col-sm-2 control-label">模块相关设置</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">完整名称</span>
                                <input type="text" class="form-control" id="cn" name="name" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">上级模块</span>
                                <select id="id_uplevel" name="id_uplevel" class="form-control">
                                    <option value="0">一级菜单</option>
                                    ${Tools.dicopt("sys_modal_hbyh",id_uplevel,"level=1","")}
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">菜单显示</span>
                                <select id="showmmenutag" name="showmmenutag" class="form-control">
                                    <option value="">请选择</option>
                                    <option value="1">显示</option>
                                    <option value="0">隐藏</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">板块标记</span>
                                <select id="modal_tag" name="modal_tag" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_modal_tag, infodb.get("modal_tag"))%>
                                </select>
                            </div>
                        </div>
                        <%if (Tools.isSuperAdmin(minfo)) {%>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">内部属性</span>
                                <select id="superadmin" name="superadmin" class="form-control">
                                    <%=Tools.dicopt("sys_modal_superadmin", Tools.strToLong(minfo.get("superadmin")))%>
                                </select>
                            </div>
                        </div>
                        <%}%>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">菜单设置</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">菜单名称</span>
                                <input type="text" class="form-control" id="showmmenuname" name="showmmenuname"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">cn</span>
                                <input type="text" class="form-control" id="cn" name="cn" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">type</span>
                                <input type="text" class="form-control" id="type" name="type" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">sdo</span>
                                <input type="text" class="form-control" id="sdo" name="sdo" placeholder="">
                            </div>
                        </div>
                        <%if (Tools.isSuperAdmin(minfo)) {%>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">urlotherstr</span>
                                <input type="text" class="form-control" id="urlotherstr" name="urlotherstr"
                                       placeholder="">
                            </div>
                        </div>
                        <%}%>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">排序</span>
                                <input type="number" class="form-control" id="sort" name="sort" placeholder="" step="1">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">图标</span>
                                <input type="text" class="form-control" id="icohtml" name="icohtml" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">角标</span>
                                <select id="ismark" name="ismark" class="form-control">
                                    <option value="0">隐藏</option>
                                    <option value="1">显示</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">相关设置说明</label>
                <div class="col-sm-10">
				<textarea style="width: 100%; height: 200px" class="form-control" disabled="">模块化管理下的每个模块可以单独添加/删除和设置，这里设置的是模块的一些属性，模块名称是用来标识用，具体显示到后台菜单的名称以菜单设置里面的显示名称为准，模块名称长度不限，但是菜单名称建议4个字。&#13内部属性是设置此模块是否对外公开，如果设置为公开模块，那么所有客户都可以进入后台添加此模块，如果为超级管理模块，那么只有后台设置为超级管理员的人才可以看到此模块，如果设置为普通内部模块，那么只有快车道内部员工账号可以看到和使用。&#13菜单相关设置，除菜单名称外，其他几个参数建议专业人士设置。
				</textarea>
                </div>
            </div>
        </div>
    </div>
</div>