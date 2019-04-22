<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.CookieTools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.Config" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.*" %>
<%
    String name = minfo.get("name");
%>
<!-- Main Header -->
<header class="main-header">
    <!-- Logo --> <a href="/manager/index" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini">
            <img src="/manager/images/logo.png" width="20" height="20" class="logoimg">
        </span> <!-- logo for regular state and mobile devices -->
        <span class="logo-lg">
            <img src="/manager/images/logo.png" width="20" height="20" class="logoimg hidden-xs">
            <span class="logotxt" style="font-size:20px !important">
                <b id="logo_title"><%=Config.APP_TITLE%></b>
            </span>
        </span>
      </a> <!-- Header Navbar -->
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                    <!-- Messages: style can be found in dropdown.less-->
										<%if(false){%>
                    <li class="dropdown messages-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-envelope-o"></i>
                            <span class="label label-success">4</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 4 messages</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li><!-- start message -->
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="iframe/dist/img/user2-160x160.jpg" class="img-circle"
                                                     alt="User Image">
                                            </div>
                                            <h4>
                                                Support Team
                                                <small><i class="fa fa-clock-o"></i> 5 mins</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                    <!-- end message -->
                                    <li>
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="iframe/dist/img/user3-128x128.jpg" class="img-circle"
                                                     alt="User Image">
                                            </div>
                                            <h4>
                                                AdminLTE Design Team
                                                <small><i class="fa fa-clock-o"></i> 2 hours</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="iframe/dist/img/user4-128x128.jpg" class="img-circle"
                                                     alt="User Image">
                                            </div>
                                            <h4>
                                                Developers
                                                <small><i class="fa fa-clock-o"></i> Today</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="iframe/dist/img/user3-128x128.jpg" class="img-circle"
                                                     alt="User Image">
                                            </div>
                                            <h4>
                                                Sales Department
                                                <small><i class="fa fa-clock-o"></i> Yesterday</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <div class="pull-left">
                                                <img src="iframe/dist/img/user4-128x128.jpg" class="img-circle"
                                                     alt="User Image">
                                            </div>
                                            <h4>
                                                Reviewers
                                                <small><i class="fa fa-clock-o"></i> 2 days</small>
                                            </h4>
                                            <p>Why not buy a new awesome theme?</p>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer"><a href="#">See All Messages</a></li>
                        </ul>
                    </li>
                    <!-- Notifications: style can be found in dropdown.less -->
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">10</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 10 notifications</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-aqua"></i> 5 new members joined today
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-warning text-yellow"></i> Very long description here that
                                            may not fit into the
                                            page and may cause design problems
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-users text-red"></i> 5 new members joined
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-shopping-cart text-green"></i> 25 sales made
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fa fa-user text-light-blue"></i> You changed your username
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer"><a href="#">View all</a></li>
                        </ul>
                    </li>
                    <!-- Tasks: style can be found in dropdown.less -->
                    <li class="dropdown tasks-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-flag-o"></i>
                            <span class="label label-danger">9</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have 9 tasks</li>
                            <li>
                                <!-- inner menu: contains the actual data -->
                                <ul class="menu">
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Design some buttons
                                                <small class="pull-right">20%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-aqua" style="width: 20%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">20% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Create a nice theme
                                                <small class="pull-right">40%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-green" style="width: 40%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">40% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Some task I need to do
                                                <small class="pull-right">60%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-red" style="width: 60%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">60% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                    <li><!-- Task item -->
                                        <a href="#">
                                            <h3>
                                                Make beautiful transitions
                                                <small class="pull-right">80%</small>
                                            </h3>
                                            <div class="progress xs">
                                                <div class="progress-bar progress-bar-yellow" style="width: 80%"
                                                     role="progressbar" aria-valuenow="20" aria-valuemin="0"
                                                     aria-valuemax="100">
                                                    <span class="sr-only">80% Complete</span>
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <!-- end task item -->
                                </ul>
                            </li>
                            <li class="footer">
                                <a href="#">View all tasks</a>
                            </li>
                        </ul>
                    </li><%}%>
                    <li class="notifications-menu hidden-xs">
                        <a onclick="doZoom(0);" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-zoom-in"></i>
                            <span class="label label-success">+</span>
                        </a>
                    </li>
                    <li class="notifications-menu hidden-xs">
                        <a onclick="doZoom(1);" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="glyphicon glyphicon-zoom-out"></i>
                            <span class="label label-danger">-</span>
                        </a>
                    </li>
                    <li class="notifications-menu hidden-xs">
                        <a onclick="doFont(0);" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa  fa-font"></i>
                            <span class="label label-success">+</span>
                        </a>
                    </li>
                    <li class="notifications-menu hidden-xs">
                        <a onclick="doFont(1);" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa  fa-font"></i>
                            <span class="label label-danger">-</span>
                        </a>
                    </li>
                    <!-- User Account: style can be found in dropdown.less -->
                    <li class="dropdown user user-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="iframe/dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                            <span class="hidden-xs"><%=name%></span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- User image -->
                            <li class="user-header">
                                <img src="iframe/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                                <p>
                                    <%=name%>
                                    <small>Member since Nov. 2012</small>
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Followers</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Sales</a>
                                    </div>
                                    <div class="col-xs-4 text-center">
                                        <a href="#">Friends</a>
                                    </div>
                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">设置</a>
                                </div>
                                <div class="pull-right">
                                    <a onclick="dologout();" class="btn btn-default btn-flat">注销</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    <%
                        String msgSearch =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".search.jsp";
                        ServletContext context = request.getSession().getServletContext();
                        InputStream is= context.getResourceAsStream(msgSearch);
                        boolean havebtnfile = is!=null;
                        if (havebtnfile){
                                msgSearch =  "/WEB-INF/jsp/manager/" + type + "/" + cn + ".search.jsp";
                                is.close();
                        }
                        if (havebtnfile){
                        %>
                        <li><a href="#" data-toggle="control-sidebar"><i class="fa fa-search"></i></a></li>
                        <%
                            }
                        %>
                </ul>
            </div>
        </nav>
    </header>
    <%
        if (havebtnfile){
    %>
      <!-- 搜索层 -->
		<aside class="control-sidebar control-sidebar-dark" style="height:100%;">
			<div class="tab-content">
				<!-- Home tab content -->
				<h3 class="control-sidebar-heading">开始搜索</h3>
				<form
					id="search_form"
					action="<%=Tools.urlKill("")%>">
					<input
						type="hidden"
						name="sdo"
						value="list" />
					<jsp:include page="<%=msgSearch%>"></jsp:include></form>
			</div>
		</aside>
        <%
            }
        %>
    <script>
    <%
        String afontSize = CookieTools.get("fontsize");
        String aZoom = CookieTools.get("zoom");
        if (Tools.myIsNull(afontSize) ){
          if ( !Tools.myIsNull(Config.UI_FONT_SIZE)){
           afontSize = Config.UI_FONT_SIZE;
          }else{
            afontSize = "14";  
          }
        }
        if (Tools.myIsNull(aZoom) ){
          if ( !Tools.myIsNull(Config.UI_ZOOM)){
           aZoom = Config.UI_ZOOM;
          }else{
            aZoom = "1";  
          }
        }
      %>
    var afontSize= <%=afontSize%>;
    var aZoom= <%=aZoom%>;
    function doZoom(type){
       $.post("/manager/command?cn=zoom&sdo=sysconfig&stype="+type+"&zoom="+aZoom , {
        }, 
        function(res) {
            eval("var res="+res);
            aZoom = res.msg;
            chgZoomSize(res.msg);
        });
    }
    function chgZoomSize(sZoom){
      $("body").css("zoom",sZoom)
    }

    function doFont(type){
       $.post("/manager/command?cn=font&sdo=sysconfig&stype="+type+"&fontsize="+afontSize , {
        }, 
        function(res) {
            eval("var res="+res);
            afontSize = res.msg;
            chgFontSize(res.msg+"px");
        });
    }
    function chgFontSize(sFontSzie){
      $("<%=Config.UI_FONT_COMPONENTS%>").css("font-size",sFontSzie)
      $("#logo_title").css("font-size","20px");
    }
    <%
      String sFontSzie = CookieTools.get("fontsize"); //从cookie里获取用户设置
      if (!Tools.myIsNull(sFontSzie)){
        sFontSzie = sFontSzie +"px";
    %>
     $(function(){
        //cookie
        chgFontSize("<%=sFontSzie%>")
    });
    <%
      }else{
    %>
    <% if (!Tools.myIsNull(Config.UI_FONT_SIZE)){%>
    $(function(){
       //config
        chgFontSize("<%=Config.UI_FONT_SIZE%>px")
    });
    <%}%>
    <%
      }
    %>
     <%
      String sFontFamily = CookieTools.get("font-family"); //从cookie里获取用户设置
      if (!Tools.myIsNull(sFontFamily)){
    %>
     $(function(){
        $("<%=Config.UI_FONT_COMPONENTS%>").css("font-family","<%=sFontFamily%>")
    });
    <%
      }else{ //从全局配置里获取
    %>
    <% if (!Tools.myIsNull(Config.UI_FONT_FAMILY)){%>
    $(function(){
        $("<%=Config.UI_FONT_COMPONENTS%>").css("font-family","<%=Config.UI_FONT_FAMILY%>")
    });
    <%}%>
    <%
      }
    %>
		function dologout(){
        $.post("/manager/login", {
            sdo: "logout",
        }, 
        function(res) {
          eval("var res="+res);
          alert(res.msg);
          location.href=res.next_url;
        });
    }
    </script>