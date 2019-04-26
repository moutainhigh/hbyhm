<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TtMap infodb = (TtMap) request.getAttribute("infodb");
    TtMap icbc = (TtMap) request.getAttribute("icbc");
    TtMap minfo = (TtMap) request.getAttribute("minfo");
    long id_uplevel = 0;
    if (!Tools.myIsNull(infodb.get("id_uplevel"))) {
        id_uplevel = Long.parseLong(infodb.get("id_uplevel"));
    }
%>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">
        <c:if test="${id ne 0}">
            <div class="box-header with-border">
                <h3 class="box-title">订单来自：${infodb.fs_name}-${infodb.admin_name}</h3>
                <h3 class="box-title">提交时间：${infodb.dt_add}</h3>
                <div class="box-tools pull-right">
                    <h3 class="box-title">订单编号：${infodb.gems_code}</h3>
                </div>
            </div>
        </c:if>
        <c:if test="${id eq 0}">
            <input id="gems_id" name="gems_id" value="<%=minfo.get("id")%>" type="hidden"/>
            <input id="gems_fs_id" name="gems_fs_id" value="<%=minfo.get("icbc_erp_fsid")%>" type="hidden"/>
            <div class="box-header with-border">
                <h3 class="box-title">新增订单</h3>
            </div>
        </c:if>
        <div class="box-body" id="tab-content">
            <div class="form-group">
                <div class="col-sm-1">
                    <a href="<%=Tools.urlKill("toZip")+"&toZip=1"%>" class="btn btn-primary">一键下载(相关文件)</a>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">购车人信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">购车人姓名</span>
                                <input type="text" class="form-control" id="c_buycar_name" name="c_buycar_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">性别</span>
                                <select id="c_buycar_sex" name="c_buycar_sex" class="form-control">
                                    <%=Tools.dicopt(DataDic.dicSex, infodb.get("c_buycar_sex"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">年龄</span>
                                <input type="text" class="form-control" id="c_buycar_age" name="c_buycar_age"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">学历</span>
                                <select id="c_buycar_eb" name="c_buycar_eb" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_xl, infodb.get("c_buycar_eb"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">身份证号</span>
                                <input type="text" class="form-control" id="c_buycar_id_cardno"
                                       name="c_buycar_id_cardno" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">身份证归属地</span>
                                <input type="text" class="form-control" id="c_buycar_cardno_local"
                                       name="c_buycar_cardno_local" placeholder="">
                            </div>
                        </div>
                        <%
                            //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
                            String hjs = Tools.dicopt("comm_states", 0);//省会，
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">户籍所在省</span>
                                <select id="c_buycar_cr_state_id" name="c_buycar_cr_state_id" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=hjs%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">户籍所在市</span>
                                <select id="c_buycar_cr_city_id" name="c_buycar_cr_city_id" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                                                    /ttAjax也可以单独使用，比如
                                                                    /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                                                    * */
                            objacl('#c_buycar_cr_state_id', '#c_buycar_cr_city_id', '/ttAjax?do=opt&cn=comm_citys&id=0&state_id=', '${infodb.c_buycar_cr_state_id}', '${infodb.c_buycar_cr_city_id}');
                        </script>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">户籍地址</span>
                                <input type="text" class="form-control" id="c_buycar_cr_address"
                                       name="c_buycar_cr_address" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">手机号</span>
                                <input type="text" class="form-control" id="c_buycar_tel" name="c_buycar_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">归属地</span>
                                <input type="text" class="form-control" id="c_buycar_tel_local"
                                       name="c_buycar_tel_local" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">婚姻状况</span>
                                <select id="c_buycar_marriage" name="c_buycar_marriage" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_hyzk, infodb.get("c_buycar_marriage"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">驾龄</span>
                                <input type="text" class="form-control" id="c_buycar_de" name="c_buycar_de"
                                       placeholder="">
                            </div>
                        </div>
                        <%
                            //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
                            String jzs = Tools.dicopt("comm_states", 0);//省会，
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">居住省</span>
                                <select id="c_buycar_live_state_id" name="c_buycar_live_state_id" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=jzs%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">居住市</span>
                                <select id="c_buycar_live_city_id" name="c_buycar_live_city_id" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                                                    /ttAjax也可以单独使用，比如
                                                                    /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                                                    * */
                            objacl('#c_buycar_live_state_id', '#c_buycar_live_city_id', '/ttAjax?do=opt&cn=comm_citys&id=0&state_id=', '${infodb.c_buycar_live_state_id}', '${infodb.c_buycar_live_city_id}');
                        </script>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">居住地址</span>
                                <input type="text" class="form-control" id="c_buycar_live_address"
                                       name="c_buycar_live_address" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">是否有驾照</span>
                                <select id="c_buycar_have_dr" name="c_buycar_have_dr" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_jz, infodb.get("c_buycar_have_dr"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">银行卡号</span>
                                <input type="text" class="form-control" id="c_buycar_bank_id" name="c_buycar_bank_id"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">出生日期</span>
                                <input type="text" class="form-control" id="c_buycar_bd" name="c_buycar_bd"
                                       placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">工作信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作性质</span>
                                <select id="c_work_type" name="c_work_type" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gzxz, infodb.get("c_work_type"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作单位</span>
                                <input type="text" class="form-control" id="c_work_name" name="c_work_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位邮编</span>
                                <input type="text" class="form-control" id="c_work_zipcode" name="c_work_zipcode"
                                       placeholder="">
                            </div>
                        </div>
                        <%
                            //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
                            String dws = Tools.dicopt("comm_states", 0);//省会，
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位省</span>
                                <select id="c_work_state_id" name="c_work_state_id" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=dws%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位市</span>
                                <select id="c_work_city_id" name="c_work_city_id" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                                                    /ttAjax也可以单独使用，比如
                                                                    /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                                                    * */
                            objacl('#c_work_state_id', '#c_work_city_id', '/ttAjax?do=opt&cn=comm_citys&id=0&state_id=', '${infodb.c_work_state_id}', '${infodb.c_work_city_id}');
                        </script>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位具体地址</span>
                                <input type="text" class="form-control" id="c_work_address" name="c_work_address"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位电话</span>
                                <input type="text" class="form-control" id="c_work_tel" name="c_work_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位性质</span>
                                <select id="c_work_unittype" name="c_work_unittype" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_dwxz, infodb.get("c_work_unittype"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作职称</span>
                                <select id="c_work_level" name="c_work_level" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gzzc, infodb.get("c_work_level"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所属行业</span>
                                <select id="c_work_level_type" name="c_work_level_type" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_sshy, infodb.get("c_work_level_type"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">入职时间</span>
                                <input type="text" class="form-control" id="c_work_intime" name="c_work_intime"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">职务</span>
                                <input type="text" class="form-control" id="c_work_post" name="c_work_post"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工龄</span>
                                <input type="text" class="form-control" id="c_work_age" name="c_work_age"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">月收入</span>
                                <input type="text" class="form-control" id="c_work_income_month"
                                       name="c_work_income_month" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">是否缴纳社保</span>
                                <select id="c_work_havess" name="c_work_havess" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_sb, infodb.get("c_work_havess"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">主要收入来源</span>
                                <select id="c_work_ms" name="c_work_ms" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zyly, infodb.get("c_work_ms"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">是否提供资产证明</span>
                                <select id="c_work_havepa" name="c_work_havepa" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zczm, infodb.get("c_work_havepa"))%>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">配偶信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">与借款人关系</span>
                                <select id="c_workpo_rsforloan" name="c_workpo_rsforloan" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zdrgx, infodb.get("c_work_havepa"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">姓名</span>
                                <input type="text" class="form-control" id="c_po_name" name="c_po_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">性别</span>
                                <select id="c_po_sex" name="c_po_sex" class="form-control">
                                    <%=Tools.dicopt(DataDic.dicSex, infodb.get("c_po_sex"))%>
                                </select>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">学历</span>
                                <select id="c_po_eb" name="c_po_eb" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_xl, infodb.get("c_po_eb"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">身份证号码</span>
                                <input type="text" class="form-control" id="c_po_id_cardno" name="c_po_id_cardno"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">手机号</span>
                                <input type="text" class="form-control" id="c_po_tel" name="c_po_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">归属地</span>
                                <input type="text" class="form-control" id="c_po_tel_local" name="c_po_tel_local"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">居住地址</span>
                                <input type="text" class="form-control" id="c_po_live_address" name="c_po_live_address"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作性质</span>
                                <select id="c_workpo_type" name="c_workpo_type" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gzxz, infodb.get("c_workpo_type"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作单位</span>
                                <input type="text" class="form-control" id="c_workpo_name" name="c_workpo_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位具体地址</span>
                                <input type="text" class="form-control" id="c_workpo_address" name="c_workpo_address"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位电话</span>
                                <input type="text" class="form-control" id="c_workpo_tel" name="c_workpo_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位性质</span>
                                <select id="c_workpo_unittype" name="c_workpo_unittype" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_dwxz, infodb.get("c_workpo_unittype"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">职务</span>
                                <input type="text" class="form-control" id="c_workpo_post" name="c_workpo_post"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工龄</span>
                                <input type="text" class="form-control" id="c_workpo_age" name="c_workpo_age"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">月收入</span>
                                <input type="text" class="form-control" id="c_workpo_income_month"
                                       name="c_workpo_income_month"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">主要收入来源</span>
                                <select id="c_workpo_ms" name="c_workpo_ms" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zyly, infodb.get("c_workpo_ms"))%>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">共还人信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">与借款人关系</span>
                                <select id="c_workghr_rsforloan" name="c_workghr_rsforloan" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zdrgx, infodb.get("c_workghr_rsforloan"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">姓名</span>
                                <input type="text" class="form-control" id="c_ghr_name" name="c_ghr_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">性别</span>
                                <select id="c_ghr_sex" name="c_ghr_sex" class="form-control">
                                    <%=Tools.dicopt(DataDic.dicSex, infodb.get("c_ghr_sex"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">学历</span>
                                <select id="c_ghr_eb" name="c_ghr_eb" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_xl, infodb.get("c_ghr_eb"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">身份证号码</span>
                                <input type="text" class="form-control" id="c_ghr_id_cardno" name="c_ghr_id_cardno"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">手机号</span>
                                <input type="text" class="form-control" id="c_ghr_tel" name="c_ghr_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">归属地</span>
                                <input type="text" class="form-control" id="c_ghr_tel_local" name="c_ghr_tel_local"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">居住地址</span>
                                <input type="text" class="form-control" id="c_ghr_live_address"
                                       name="c_ghr_live_address"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作性质</span>
                                <select id="c_workghr_type" name="c_workghr_type" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gzxz, infodb.get("c_workghr_type"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工作单位</span>
                                <input type="text" class="form-control" id="c_workghr_name" name="c_workghr_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位具体地址</span>
                                <input type="text" class="form-control" id="c_workghr_address" name="c_workghr_address"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位电话</span>
                                <input type="text" class="form-control" id="c_workghr_tel" name="c_workghr_tel"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">单位性质</span>
                                <select id="c_workghr_unittype" name="c_workghr_unittype" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_dwxz, infodb.get("c_workghr_unittype"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">职务</span>
                                <input type="text" class="form-control" id="c_workghr_ghrst" name="c_workghr_ghrst"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">工龄</span>
                                <input type="text" class="form-control" id="c_workghr_age" name="c_workghr_age"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">月收入</span>
                                <input type="text" class="form-control" id="c_workghr_income_month"
                                       name="c_workghr_income_month"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">主要收入来源</span>
                                <select id="c_workghr_ms" name="c_workghr_ms" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zyly, infodb.get("c_workghr_ms"))%>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">抵押人信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">抵押人姓名</span>
                                <input type="text" class="form-control" id="c_mg_name" name="c_mg_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">与借款人关系</span>
                                <select id="c_mg_rsforloan" name="c_mg_rsforloan" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_zdrgx, infodb.get("c_mg_rsforloan"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">抵押人身份证号</span>
                                <input type="text" class="form-control" id="c_mg_cardno" name="c_mg_cardno"
                                       placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                for (int i = 1; i <= 4; i++) {
            %>
            <div class="form-group">
                <label class="col-sm-2 control-label">紧急联系人<%=i%>信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">姓名</span>
                                <input type="text" class="form-control" id="c_ec<%=i%>_name" name="c_ec<%=i%>_name"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">与借款人关系</span>
                                <input type="text" class="form-control" id="c_ec<%=i%>_rsforloan"
                                       name="c_ec<%=i%>_rsforloan"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">手机号</span>
                                <input type="text" class="form-control" id="c_ec<%=i%>_mobile" name="c_ec<%=i%>_mobile"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">电话</span>
                                <input type="text" class="form-control" id="c_ec<%=i%>_tel" name="c_ec<%=i%>_tel"
                                       placeholder="">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="form-group">
                <label class="col-sm-2 control-label">融资信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车辆类型</span>
                                <select id="c_loaninfo_car_type" name="c_loaninfo_car_type" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_cllx, infodb.get("c_loaninfo_car_type"))%>
                                </select>
                            </div>
                        </div>
                        <%--<div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">是否公牌</span>
                                <select id="c_loaninfo_car_isp" name="c_loaninfo_car_isp" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gp, infodb.get("c_loaninfo_car_isp"))%>
                                </select>
                            </div>
                        </div>--%>
                        <%

                            int brid = 0;
                            if (infodb.get("brid_v2") != null && !infodb.get("brid_v2").equals("")) {
                                brid = Integer.parseInt(infodb.get("brid_v2"));
                            }

                            String sp = Tools.dicopt("car_brand_v2", brid);//品牌
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">品牌</span>
                                <select name="brid_v2" id="brid_v2" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=sp%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车系</span>
                                <select name="seid_v2" id="seid_v2" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">型号</span>
                                <select name="carid_v2" id="carid_v2" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            objacl('#brid_v2', '#seid_v2', '/ttAjax?do=opt&cn=car_series_v2&id=0&brand_id=', '${infodb.brid_v2}', '${infodb.seid_v2}');
                            objacl('#seid_v2', '#carid_v2', '/ttAjax?do=opt&cn=car_model_v2&id=0&series_id=', '${infodb.seid_v2}', '${infodb.carid_v2}');
                        </script>
                        <%--                        <div class="col-sm-4">
                                                    <div class="input-group">
                                                        <span class="input-group-addon">上牌地址</span>
                                                        <input type="text" class="form-control" id="c_loaninfo_car_pcaddress"
                                                               name="c_loaninfo_car_pcaddress" placeholder="">
                                                    </div>
                                                </div>--%>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">行驶证车主名</span>
                                <input type="text" class="form-control" id="c_loaninfo_car_owner"
                                       name="c_loaninfo_car_owner" placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">抵押权人</span>
                                <input type="text" class="form-control" id="c_loaninfo_car_mg" name="c_loaninfo_car_mg"
                                       placeholder="">
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">购车用途</span>
                                <select id="c_loaninfo_car_use" name="c_loaninfo_car_use" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gcyt, infodb.get("c_loaninfo_car_use"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">评估价格</span>
                                <input type="text" class="form-control" id="c_loaninfo_car_priceresult"
                                       name="c_loaninfo_car_priceresult" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车贷本金</span>
                                <input type="text" class="form-control" id="c_loaninfo_car_pcpprice"
                                       name="c_loaninfo_car_pcpprice" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">利率（%）</span>
                                <input type="text" class="form-control" id="c_loaninfo_car_loanrate"
                                       name="c_loaninfo_car_loanrate" placeholder="">
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">开票价（元）</span>
                                <input type="text" class="form-control" id="c_loaninfo_kpj"
                                       name="c_loaninfo_kpj" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">贷款总额（元）</span>
                                <input type="text" class="form-control" id="c_loaninfo_dkze"
                                       name="c_loaninfo_dkze" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">合同金额（元）</span>
                                <input type="text" class="form-control" id="c_loaninfo_htje"
                                       name="c_loaninfo_htje" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">服务费（元）</span>
                                <input type="text" class="form-control" id="c_loaninfo_fee"
                                       name="c_loaninfo_fee" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">首付金额（元）</span>
                                <input type="text" class="form-control" id="c_loaninfo_sfje"
                                       name="c_loaninfo_sfje" placeholder="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">贷款期数（月）</span>
                                <select id="c_loaninfo_periods" name="c_loaninfo_periods" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_dkqs, infodb.get("c_loaninfo_periods"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">服务费是否分期</span>
                                <select id="c_loaninfo_feeisin" name="c_loaninfo_feeisin" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_fq, infodb.get("c_loaninfo_feeisin"))%>
                                </select>
                            </div>
                        </div>
                        <%--<div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">垫资类型</span>
                                <select id="c_loaninfo_dzlx" name="c_loaninfo_dzlx" class="form-control">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_dzlx, infodb.get("c_loaninfo_dzlx"))%>
                                </select>
                            </div>
                        </div>--%>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">汽车材料</label>
                <div class="col-sm-10">
                    <div class="row inline-from">

                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">颜色</span>
                                <select class="form-control" id="color_id" name="color_id">
                                    <%=Tools.dicopt(DataDic.dic_car_color, infodb.get("color_id"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">出厂日期</span>
                                <input id="cardt1" name="cardt1" class="form-control" value="<%=infodb.get("cardt1")%>"
                                       placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车架号</span>
                                <input id="vincode" name="vincode" class="form-control"
                                       value="<%=infodb.get("vincode")%>" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">品牌型号</span>
                                <input id="ppxh" name="ppxh" class="form-control" value="<%=infodb.get("ppxh")%>"
                                       placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">发动机号</span>
                                <input id="motorcode" name="motorcode" class="form-control"
                                       value="<%=infodb.get("motorcode")%>" placeholder=""/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">车辆信息材料</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <%
                            String upFile1 = "../upfile.inc.jsp";
                            String imgPreName1 = "imgstep9_1ss";
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
                            String[] ssImgs_1 = sImgs1.split("\\|");//获取已有图片
                        %>
                        <%-- 可能这里用<%@include file %>模式更适合--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="<%=ssImgs_1.length%>"/>
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
                        <input id="imgstep9_1ss_num" name="imgstep9_1ss_num" type="hidden"
                               value="<%=ssImgs_1.length%>"/>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">车身照片</label>
                <div class="col-sm-10">
                    <div class="row inline-from">

                        <%
                            //String upFile2 = "../upfile.inc.jsp";
                            String imgPreName2 = "imgstep9_2ss";
                            String[] ssImgs2 = { //设置已有值
                                    !Tools.myIsNull(infodb.get(imgPreName2)) ? infodb.get(imgPreName2) : ""
                            };
                            ssImgs2 = ssImgs2[0].split("\u0005");
                            String sImgs2 = "";
                            for (int i = 0; i < ssImgs2.length; i++) {
                                if (ssImgs2[i] != null && !ssImgs2[i].equals("")) {
                                    sImgs2 = sImgs2 + ssImgs2[i] + "|";
                                }
                            }
                            String[] ssImgs_2 = sImgs2.split("\\|");//获取已有图片
                        %>
                        <%-- 可能这里用<%@include file %>模式更适合--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="<%=ssImgs_2.length%>"/>
                            <jsp:param name="img_NamePre" value="<%=imgPreName2%>"/>
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
                            <jsp:param name="sImgs" value="<%=sImgs2%>"/>
                        </jsp:include>
                        <input id="imgstep9_2ss_num" name="imgstep9_2ss_num" type="hidden"
                               value="<%=ssImgs_2.length%>"/>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">审核和数据填充处理</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">审核状态</span>
                                <select class="form-control" id="bc_status" name="bc_status"
                                        onchange="autoremark(this)">
                                    <%=Tools.dicopt(DataDic.dic_zx_status, infodb.get("bc_status"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">类型</span>
                                <input type="text" class="form-control" readonly="" value="贷款材料">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">留言备注说明：</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-5">
                            <div class="input-group">
                                <span class="input-group-addon">审核留言</span>
                                <input type="text" class="form-control" name="remark1" id="remark1">
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="input-group">
                                <span class="input-group-addon">留言快速通道</span>
                                <select class="form-control" id="cyly" onchange="setremark(this)">
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
                    <textarea style="width: 80%; height: 200px" class="form-control" disabled><%
                        TtList lslist = (TtList) request.getAttribute("lslist");
                        if (lslist != null && lslist.size() > 0) {
                            for (TtMap l : lslist) {
                    %><%=l.get("dt_add")%>:状态:<%=DataDic.dic_zx_status.get(l.get("status"))%>,留言:<%=l.get("remark").replace("null", "")%>&#10;<%
                            }
                        }
                    %></textarea>
                </div>
            </div>

        </div>
    </div>
</div>
<script>

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
<script>
    //执行一个laydate实例
    laydate.render({
        elem: '#app_date', //指定元素
        type: 'datetime'
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#c_work_intime', //指定元素
        type: 'datetime'
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#c_buycar_bd', //指定元素
        type: 'date'
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#cardt1', //指定元素
        type: 'datetime'
    });
</script>
<script>
    $(document).ready(function () {
        // 中文重写select 查询为空提示信息
        $('.selectpicker').selectpicker('refresh');
    });
</script>