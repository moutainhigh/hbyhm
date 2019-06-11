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
                            <%
                                String fsids=Tools.getfsidsbyminfo(minfo);
                                String sql1="";
                                //String sql2="";
                                switch (nowcn) {
                                    case "hxyh_zxlr":
                                        sql1="select count(*) as num from kj_icbc where app=4 and bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_xxzl":
                                        sql1="select count(*) as num from hxyh_xxzl where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_yhht":
                                        sql1="select count(*) as num from hxyh_yhht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_gsht":
                                        sql1="select count(*) as num from hxyh_gsht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_gpsgd":
                                        sql1="select count(*) as num from hxyh_gpsgd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_dygd":
                                        sql1="select count(*) as num from hxyh_dygd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_yhclhs":
                                        sql1="select count(*) as num from hxyh_yhclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_gsclhs":
                                        sql1="select count(*) as num from hxyh_gsclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hxyh_dyclhs":
                                        sql1="select count(*) as num from hxyh_dyclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "zxlr":
                                        sql1="select count(*) as num from kj_icbc where app=2 and bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xxzl":
                                        sql1="select count(*) as num from hbyh_xxzl where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hbyh_yhht":
                                        sql1="select count(*) as num from hbyh_yhht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hbyh_gsht":
                                        sql1="select count(*) as num from hbyh_gsht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "gps":
                                        sql1="select count(*) as num from hbyh_gpsgd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "dy":
                                        sql1="select count(*) as num from hbyh_dygd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hbyh_yhclhs":
                                        sql1="select count(*) as num from hbyh_yhclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hbyh_gsclhs":
                                        sql1="select count(*) as num from hbyh_gsclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "hbyh_dyclhs":
                                        sql1="select count(*) as num from hbyh_dyclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_zxlr":
                                        sql1="select count(*) as num from kj_icbc where app=3 and bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_xxzl":
                                        sql1="select count(*) as num from xmgj_xxzl where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_yhht":
                                        sql1="select count(*) as num from xmgj_yhht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_gsht":
                                        sql1="select count(*) as num from xmgj_gsht where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_gpsgd":
                                        sql1="select count(*) as num from xmgj_gpsgd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_dygd":
                                        sql1="select count(*) as num from xmgj_dygd where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_yhclhs":
                                        sql1="select count(*) as num from xmgj_yhclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_gsclhs":
                                        sql1="select count(*) as num from xmgj_gsclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    case "xmgj_dyclhs":
                                        sql1="select count(*) as num from xmgj_dyclhs where bc_status=2 and gems_fs_id in ("+fsids+")";
                                        //sql2="select count(*) as num from kj_icbc where app=4 and bc_status=3 and gems_fs_id in ("+fsids+")";
                                        break;
                                    default:
                                        break;
                                }
                                TtMap ttMap1=Tools.recinfo(sql1);
                                //TtMap ttMap2=Tools.recinfo(sql2);
                                int num1=0,num2=0;
                                if(!ttMap1.isEmpty()){
                                    num1=Integer.parseInt(ttMap1.get("num"));
                                }
//                                if(!ttMap2.isEmpty()){
//                                    num2=Integer.parseInt(ttMap2.get("num"));
//                                }
                            %>
                            <script>
                                $(document).ready(function () {
                                    //document.getElementById("<%=nowcn%>1").innerText = "<%=num2%>";
                                    document.getElementById("<%=nowcn%>2").innerText = "<%=num1%>";
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
            <li class="header"><%=Config.APP_TITLE + " " + Config.APP_VER%><br>程序内核：<%=Config.TTVER%>
            </li>
        </ul> <!-- /.sidebar-menu -->
    </section> <!-- /.sidebar -->
</aside>
<script>
    $('li.active').parents('li').addClass('treeview').addClass('active');
</script>
