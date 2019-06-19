<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.Config" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
        <!-- Sidebar Menu -->
        <ul class="sidebar-menu">
            <li class="header">管理菜单</li>
            <%
                //TtMap minfo = (TtMap) request.getAttribute("minfo");
                String urlHome = "index";
                String nowcn = "";
                String nowsdo = "";
                String nowtype = "";
            %>
            <li <% if (cn.equals("homeHx") && type.equals("demo")) { %> class="active" <%}%>>
                <a href="<%=urlHome%>"> <i class="fa fa-home"></i> <span>华夏管理首页</span></a>
            </li>
            <li <% if (cn.equals("homeXm") && type.equals("demo")) { %> class="active" <%}%>>
                <a href="/manager/index?cn=homeXm&sdo=form&type=demo"> <i class="fa fa-home"></i>
                    <span>厦门管理首页</span></a>
            </li>
            <li <% if (cn.equals("homeHb") && type.equals("demo")) { %> class="active" <%}%>>
                <a href="/manager/index?cn=homeHb&sdo=form&type=demo"> <i class="fa fa-home"></i>
                    <span>河北管理首页</span></a>
            </li>
            <%
                Map<String, Object> menus = (Map<String, Object>) request.getAttribute("menus");
                for (String key : menus.keySet()) {  //一级菜单循环开始
                    Map<String, Object> mainList = (Map<String, Object>) menus.get(key);
                    TtList submenus = (TtList) mainList.get("submenu");
                    TtMap mainInfo = (TtMap) mainList.get("mainmenu");
                    String iconHtmlMain = mainInfo.get("icohtml");
                    iconHtmlMain = !Tools.myIsNull(iconHtmlMain) ? iconHtmlMain : "<i class=\"fa fa-sitemap\"></i>";
            %>
            <li>
                <a href="#"><%=iconHtmlMain%> <span><%=key%></span></a>
                <ul class="treeview-menu">
                    <%

                        for (TtMap keysub : submenus) {//二级级菜单循环开始
                            String icohtml = keysub.get("icohtml");
                            String subMenuName = keysub.get("showmmenuname");
                            String urlotherstr = keysub.get("urlotherstr");
                            String ismark = keysub.get("ismark");
                            nowcn = keysub.get("cn");
                            nowsdo = keysub.get("sdo");
                            nowtype = keysub.get("type");
                            boolean active = nowcn.equals(cn) && nowtype.equals(type) /*&& nowsdo.equals(sdo)*/;
                    %>
                    <li <% if (active) { %> class="active" <%}%> >
                        <a href="index?cn=<%=nowcn%>&sdo=<%=nowsdo%>&type=<%=nowtype%><%=urlotherstr%>"><%=icohtml%>
                            <span><%=subMenuName%></span>
                            <%--显示小图标--%>
                            <%if (ismark.equals("1")) {%>
                            <span class="pull-right-container">
                                <%--<small id="<%=nowcn%>1" name="<%=nowcn%>" class="label pull-right bg-green">0</small>--%>
                                 (<font id="<%=nowcn%>2" color="Lime">0</font>)
                            </span>
                            <script>
                                $(document).ready(function () {
                                    $.post("/ttAjax?do=count&cn=<%=nowcn%>",function (data) {
                                        var obj = eval('(' + data + ')');
                                        document.getElementById("<%=nowcn%>2").innerText = obj.num1;
                                        }
                                    )
                                    //document.getElementById("<%=nowcn%>1").innerText = "";

                                })
                            </script>
                            <%}%>
                        </a>
                    </li>
                    <%
                            //二级级菜单循环结束
                        }
                    %>
                </ul>
            </li>
            <%
                    //一级菜单循环结束
                }
            %>
						<li class="header"><%=Config.APP_TITLE+" "+Config.APP_VER%><br>程序内核：<%=Config.TTVER%><br>当前调试模式：<%=Config.DEBUGMODE?"是":"否"%></li>
        </ul> <!-- /.sidebar-menu -->
    </section> <!-- /.sidebar -->
</aside>
<script>
    $('li.active').parents('li').addClass('treeview').addClass('active');
</script>
