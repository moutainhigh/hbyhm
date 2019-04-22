<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<div class="box">
    <%
        String url = Tools.urlKill("sdo|id") + "&sdo=form&id=";
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
                    <table id="example2" class="table table-bordered table-hover dataTable" role="grid"
                           aria-describedby="example2_info">
                        <thead>
                        <tr role="row">
                            <th class="text-center" style="width:120px;">
                                名称
                            </th>
                            <th class="hidden-xs text-center">
                                <!-- hidden-xs为手机模式时自动隐藏， text-center为居中-->
                                账户总数
                            </th>
                            <th class="hidden-xs text-center">
                                更新时间
                            </th>
                            <th class="hidden-xs text-center">
                                创建时间
                            </th>
                            <th class="hidden-xs text-center">状态</th>
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
                                                <c:choose>
                                                    <c:when test="${!Tools.myIsNull(u.oemimgurl)}">
                                                        <c:if test="${fn:contains(u.oemimgurl,'assess/')==true}">
                                                            <img class="media-object gallery-pic"
                                                                 onclick="$.openPhotoGallery(this)"
                                                                 src="http://a.kcway.net/${u.oemimgurl}"
                                                                 style="width: 36px;height:36px;">
                                                        </c:if>
                                                        <c:if test="${fn:contains(u.oemimgurl,'assess/')==false}">
                                                            <img class="media-object gallery-pic"
                                                                 onclick="$.openPhotoGallery(this)"
                                                                 src="${u.oemimgurl}"
                                                                 style="width: 36px;height:36px;">
                                                        </c:if>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img class="media-object gallery-pic"
                                                             onclick="$.openPhotoGallery(this)"
                                                             src="images/face.png"
                                                             style="width: 36px;height:36px;">
                                                    </c:otherwise>
                                                </c:choose>
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
                                        ${u.usercount}
                                </td>
                                <td class="hidden-xs text-center">
                                        ${u.dt_edit}
                                </td>
                                <td class="hidden-xs text-center">
                                        ${u.dt_add}
                                </td>
                                <td class="hidden-xs text-center">
                                        ${u.showtag eq '1'?"<span class='label label-success'>正常</span>":"<span class='label label-danger'>屏蔽</span>"}
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