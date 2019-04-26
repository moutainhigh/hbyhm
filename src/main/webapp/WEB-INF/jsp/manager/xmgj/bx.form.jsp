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
                    <h3 class="box-title">订单编号：${infodb.gems_code}</h3>
                </div>
            </div>
        </c:if>
        <c:if test="${id eq 0}">
            <div class="box-header with-border">
                <h3 class="box-title">新增订单</h3>
            </div>
            <input id="gems_id" name="gems_id" value="<%=minfo.get("id")%>" type="hidden"/>
            <input id="gems_fs_id" name="gems_fs_id" value="<%=minfo.get("icbc_erp_fsid")%>" type="hidden"/>
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
                </div>
            </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">车身照片车身照片（定位水印）</label>
                <div class="col-sm-10">
                    <div class="row inline-from">
                        <%
                            String upFile1 = "../upfile.inc.jsp";
                            String imgPreName1 = "imgstep15_1ss";
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
                            int imgs_length1=10;
                            if(ssImgs_1.length>0){
                                imgs_length1=ssImgs_1.length;
                            }
                        %>
                        <%-- 可能这里用<%@include file %>模式更适合--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="<%=imgs_length1%>"/>
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
                        <input id="<%=imgPreName1%>_num" name="<%=imgPreName1%>_num" type="hidden" value="<%=imgs_length1%>" />
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
                                <input type="text" class="form-control" readonly="" value="保险归档">
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
    $(document).ready(function(){
        // 中文重写select 查询为空提示信息
        $('.selectpicker').selectpicker('refresh');
    });
   /* var imgs='${infodb.imgstep9_1ss}';
    String imgss="[{type: \"office\", size: 102400, caption: \"123.docx\", url: \"/amp/project/delFile.do\", key: '1519788281200pwxfx87'}]";
    $("#myFile").fileinput({
        language: 'zh', //设置语言
        uploadUrl:"${pageContext.request.contextPath }/ttAjaxPost?do=fileup&smallwidth=100&smallheight=100&shuitext=快加认证", //上传的地址
        allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
        //uploadExtraData:{"id": 1, "fileName":'123.mp3'},
        uploadAsync: true, //默认异步上传
        showUpload:true, //是否显示上传按钮
        showRemove :true, //显示移除按钮
        showPreview :true, //是否显示预览
        showCaption:false,//是否显示标题
        browseClass:"btn btn-primary", //按钮样式
        dropZoneEnabled: true,//是否显示拖拽区域
        //minImageWidth: 50, //图片的最小宽度
        //minImageHeight: 50,//图片的最小高度
        //maxImageWidth: 1000,//图片的最大宽度
        //maxImageHeight: 1000,//图片的最大高度
        //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小
        //minFileCount: 0,
        maxFileCount:10, //表示允许同时上传的最大文件个数
        enctype:'multipart/form-data',
        validateInitialCount:true,
        previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
        msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    }).on("fileuploaded", function (event, data, previewId, index){
      $("#img_div").val("<input id=\"myFile\" name=\"upload_immm\" type=\"file\" multiple class=\"file-loading\">");
    });*/
</script>