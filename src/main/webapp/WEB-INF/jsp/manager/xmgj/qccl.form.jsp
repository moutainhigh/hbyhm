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
                    <h3 class="box-title">订单编号：${infodb.code}</h3>
                    <h3 class="box-title">鉴定编号：${infodb.gems_code}</h3>
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
                <label class="col-sm-2 control-label">基本信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">关联客户</span>
                                <select id="icbc_id" name="icbc_id" title="请选择关联客户" class="selectpicker  form-control"
                                         multiple data-live-search="true" data-max-options="1">
                                    <c:forEach items="${icbclist}" var="i">
                                        <option value="${i.id}">${i.c_name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车辆类型</span>
                                <select class="form-control" id="cars_type" name="cars_type">
                                    <%=Tools.dicopt(DataDic.dic_qccl_cllx, infodb.get("cars_type"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">国产/进口</span>
                                <select class="form-control" id="source_id" name="source_id">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_cllx, infodb.get("source_id"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">使用性质</span>
                                <select class="form-control" id="property_id" name="property_id">
                                    <%=Tools.dicopt(DataDic.dic_cars_property, infodb.get("property_id"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">变速箱</span>
                                <select class="form-control" id="gear_box_id" name="gear_box_id">
                                    <%=Tools.dicopt(DataDic.dic_car_gear_box, infodb.get("gear_box_id"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车辆状况</span>
                                <select class="form-control" id="car_status" name="car_status">
                                    <%=Tools.dicopt(DataDic.dic_car_status, infodb.get("car_status"))%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">行驶里程（公里）</span>
                                <input id="car_km_icbc" name="car_km_icbc" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">出厂日期</span>
                                <input id="cardt2" name="cardt2" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">初次登记日期</span>
                                <input id="cardt1" name="cardt1" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">颜色</span>
                                <select class="form-control" id="color_id" name="color_id">
                                    <%=Tools.dicopt(DataDic.dic_car_color, infodb.get("color_id"))%>
                                </select>
                            </div>
                        </div>
                        <%
                            //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
                            String szs = Tools.dicopt("comm_states", 0);//省会，
                        %>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所在省</span>
                                <select id="mem_states" name="mem_states" class="form-control">
                                    <option value="0">请选择</option>
                                    <%=szs%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所在市</span>
                                <select id="mem_citys" name="mem_citys" class="form-control">
                                    <option value="0">请选择</option>
                                </select>
                            </div>
                        </div>
                        <script>
                            /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
                                                                    /ttAjax也可以单独使用，比如
                                                                    /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
                                                                    * */
                            objacl('#mem_states', '#mem_citys', '/ttAjax?do=opt&cn=comm_citys&id=0&state_id=', '${infodb.mem_states}', '${infodb.mem_citys}');
                        </script>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车牌号码</span>
                                <input id="c_carno" name="c_carno" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">车架号</span>
                                <input id="vincode" name="vincode" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">品牌型号</span>
                                <input id="ppxh" name="ppxh" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">发动机号</span>
                                <input id="motorcode" name="motorcode" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">预期价格（元）</span>
                                <input id="icbc_pricecs" name="icbc_pricecs" class="form-control" value="" placeholder=""/>
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">原车主姓名</span>
                                <input id="oldowner" name="oldowner" class="form-control" value="" placeholder=""/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">是否公牌</span>
                                <select class="form-control" id="isp" name="isp">
                                    <%=Tools.dicopt(DataDic.dic_zzcl_gp, infodb.get("isp"))%>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group">
            <label class="col-sm-2 control-label">车辆型号</label>
            <div class="col-sm-10">
                <div class="row inline-from">
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
                </div>
            </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">车辆材料信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <%
                            String upFile1 = "../upfile.inc.jsp";
                            String imgPreName1 = "imgstep9_1ss";
                            String[] ssImgs1 = { //设置已有值
                                    !Tools.myIsNull(infodb.get(imgPreName1)) ? infodb.get(imgPreName1) : ""
                            };
                            ssImgs1=ssImgs1[0].split("\u0005");
                            String sImgs1 = "";
                            for (int i = 0; i < ssImgs1.length; i++) {
                                if(ssImgs1[i]!=null&&!ssImgs1[i].equals("")) {
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
                                       value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;"/>
                            <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                            <jsp:param name="img_FileStyle"
                                       value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                            <jsp:param name="img_Class" value="imgclass"/>
                            <jsp:param name="img_FileClass" value="uploadfileclass"/>
                            <jsp:param name="img_SmallWidth" value="100"/>
                            <jsp:param name="img_SmallHeight" value="100"/>
                            <jsp:param name="sImgs" value="<%=sImgs1%>"/>
                        </jsp:include>
                        <input id="imgstep9_1ss_num" name="imgstep9_1ss_num" type="hidden" value="<%=ssImgs_1.length%>" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">车辆信息</label>
                <div class="col-sm-10">
                    <div class="row inline-from">

                        <%
                            //String upFile2 = "../upfile.inc.jsp";
                            String imgPreName2 = "imgstep9_2ss";
                            String[] ssImgs2 = { //设置已有值
                                    !Tools.myIsNull(infodb.get(imgPreName2)) ? infodb.get(imgPreName2) : ""
                            };
                            ssImgs2=ssImgs2[0].split("\u0005");
                            String sImgs2 = "";
                            for (int i = 0; i < ssImgs2.length; i++) {
                                if(ssImgs2[i]!=null&&!ssImgs2[i].equals("")) {
                                    sImgs2 = sImgs2 + ssImgs2[i] + "|";
                                }
                            }
                            String[] ssImgs_2 = sImgs1.split("\\|");//获取已有图片
                        %>
                        <%-- 可能这里用<%@include file %>模式更适合--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="<%=ssImgs_2.length%>"/>
                            <jsp:param name="img_NamePre" value="<%=imgPreName2%>"/>
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
                            <jsp:param name="sImgs" value="<%=sImgs2%>"/>
                        </jsp:include>
                        <input id="imgstep9_2ss_num" name="imgstep9_2ss_num" type="hidden" value="<%=ssImgs_2.length%>" />
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
                                <span class="input-group-addon">评估价格</span>
                                <input type="text" class="form-control"  id="price_result" name="price_result" value="">
                                <span class="input-group-addon">元</span>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">类型</span>
                                <input type="text" class="form-control" readonly="" value="汽车材料">
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
        elem: '#cardt1', //指定元素
        type: 'datetime'
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#cardt2', //指定元素
        type: 'datetime'
    });
    //执行一个laydate实例
    laydate.render({
        elem: '#c_buycar_bd', //指定元素
        type: 'datetime'
    });
</script>
<script>
    $(document).ready(function(){
        // 中文重写select 查询为空提示信息
        $('.selectpicker').selectpicker('refresh');
    });
</script>