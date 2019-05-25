<%@ page import="com.tt.tool.Tools" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<body>
<div class="box">

<div class="box-body">
    <div id="example2_wrapper" class="dataTables_wrapper form-inline dt-bootstrap">
        <div class="row">
            <div class="col-sm-6"></div>
            <div class="col-sm-6"></div>
        </div>
        <div class="row">
            <div class="col-sm-12">
                <table id="example2" class="table table-bordered table-hover dataTable" role="grid" aria-describedby="example2_info">
                    <thead>
                    <tr role="row">
                        <th class="text-center"><!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                            序号
                        </th>
                        <th class="text-center">
                            文件名称
                        </th>
                        <th class="text-center">
                            导入时间
                        </th>
                        <th class="text-center">
                            金融产品
                        </th>
                        <th class="hidden-xs text-center">
                            操作人员
                        </th>
                        <th class="text-center">查看</th>
                        <th class="text-center">下载</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="u" varStatus="num">
                        <tr role="row" class="odd">
                            <td class="text-center">
                                    ${u.uuid}
                            </td>
                            <td class="text-center">
                                    ${u.oriName}
                            </td>
                            <td class="text-center">
                                    ${u.dt_add}
                            </td>
                            <td class="text-center">
                                    ${u.financial_products}
                            </td>
                            <td class="hidden-xs text-center">
                                    ${u.admin_name}
                            </td>
                            <td class="text-center">
                                <div class="table-button">
                                        <c:if test="${!empty u.filepath}">
                                            <a href="http://hbyhm.kcway.net${u.filepath}" target="view_window">
                                                <i class="fa fa-search-plus"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${empty u.filepath}">
                                            <a href="javascript:alert('未检测到相关文档!')">
                                                <i class="fa fa-search-plus"></i>
                                            </a>
                                        </c:if>
                                </div>
                            </td>
                            <td class="text-center">
                                <c:if test="${!empty u.filepath}">
                                    <a href="http://hbyhm.kcway.net${u.filepath}" target="view_window">
                                        <i class="glyphicon glyphicon-download-alt"></i>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</div>

<script>

    function upload_cover(obj) {
        ajax_upload(obj.id, function(data) { //function(data)是上传图片的成功后的回调方法
            var isrc = data.relatPath.replace(/\/\//g, '/'); //获取的图片的绝对路径
            console.log("图片的绝对路径->"+isrc)
        });
    }

    function ajax_upload(feid, callback) { //具体的上传图片方法
        console.log("上传开始")
        if (image_check(feid)) { //自己添加的文件后缀名的验证
            $.ajaxFileUpload({
                fileElementId: feid,    //需要上传的文件域的ID，即<input type="file">的ID。
                url:"/manager/importExcelControllerhx",
                type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                dataType: 'text/html',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                secureuri: false,   //是否启用安全提交，默认为false。
                async : true,   //是否是异步
                success: function(data) {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                    //$("#titleBox").html("");
                    //alert(data);
                    if(data == 1){
                        alert("导入成功");
                    }else if(data == 0){
                        alert("导入失败");
                    }
                    window.location.reload();
                },
                error: function(data, status, e) {  //提交失败自动执行的处理函数。
                    alert("NO");
                    console.error(e);
                }
            });
        }
    }

    function image_check(feid) { //自己添加的文件后缀名的验证
        var img = document.getElementById(feid);
        return /.(xlsx|xls)$/.test(img.value)?true:(function() {
            alert("格式不对")
            return false;
        })();
    }

</script>

</body>
</html>
