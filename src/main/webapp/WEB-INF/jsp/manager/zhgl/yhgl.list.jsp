<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="box">
    <%
        String url = Tools.urlKill("sdo|id")+"&sdo=form&id=";
        TtMap minfo = (TtMap) request.getAttribute("minfo");
    %>
    <!-- /.box-header -->
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
                            <th class="text-center" style="width:120px;">
                                姓名
                            </th>
                            <th class="hidden-xs text-center">
                                <!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                                编号
                            </th>
                            <th class="hidden-xs text-center">
                                用户名
                            </th>
                            <th class="hidden-xs text-center">
                                所属公司
                            </th>
                            <th class="hidden-xs text-center">
                                最后更新时间
                            </th>
                            <th class="hidden-xs text-center">显示</th>
                            <th class="text-center">操作</th>
                        </tr>
                        </thead>
                        <tbody class="gallerys"><!--class为gallerys表示图片放大组件的起始范围。 -->
                        <c:forEach items="${list}" var="u" varStatus="num">
                            <tr role="row" class="odd">
                                <td class="user-table-info text-center">
                                    <div class="media">
                                        <div class="media-left hidden-xs">
                                            <div class="user-face-pic">
                                                <img class="media-object gallery-pic" onclick="$.openPhotoGallery(this)"
                                                     src="${Tools.myIsNull(u.imgurl)?"images/face.png":u.imgurl}" style="width: 36px;height:36px;">
                                                <!--gallery-pic的class表示这个图片加入到图片放大缩小组件列表里。 -->
                                            </div>
                                        </div>
                                        <div class="media-body media-middle">
                                            <h5 class="media-heading">
                                                <a href="<%=url%>${u.id}">${u.name}</a>
                                            </h5>
                                            <p></p>
                                        </div>
                                    </div>
                                </td>
                                <td class="hidden-xs text-center">
                                        ${u.id}
                                </td>
                                <td class="hidden-xs text-center">
                                        ${u.username}
                                </td>
                                <td class="hidden-xs text-center">
                                    <a href="index?cn=yhgl&sdo=list&type=zhgl&fsid=${u.icbc_erp_fsid}">${u.fsname}</a>
                                </td>
                                <td class="hidden-xs text-center">
                                        ${fn:replace(u.dt_edit, ".0", "")}
                                </td>
                                <td class="hidden-xs text-center"><%
                                    String cn = request.getParameter("cn");
                                %>
                                    <select id="showtag_${u.id}" onchange="ajax_edit(${u.id},'showtag',this.value,'assess_admin');" class="form-control">
                                            ${u.choice}
                                    </select>
                                </td>
                                <td class="text-center">
                                    <div class="table-button">
                                        <a href="<%=url%>${u.id}" class="btn btn-default">
                                            <i class="fa fa-pencil"></i>
                                        </a>
                                    </div>
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