<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
            <!-- Sidebar user panel -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="/manager/images/logo.png" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info">
                    <p>Alexander Pierce</p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>
            <!-- search form -->
            <form action="#" method="get" class="sidebar-form">
                <div class="input-group">
                    <input type="text" name="q" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                <button type="button" name="search" id="search-btn" class="btn btn-flat" onclick="search_menu()"><i
                        class="fa fa-search"></i>
                </button>
              </span>
                </div>
            </form>
            <!-- /.search form -->
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <ul class="sidebar-menu">
            </ul>
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper" id="content-wrapper" style="min-height: 421px;">
        <!--bootstrap tab风格 多标签页-->
        <div class="content-tabs">
            <button class="roll-nav roll-left tabLeft" onclick="scrollTabLeft()">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">
                <div class="page-tabs-content" style="margin-left: 0px;">

                </div>
            </nav>
            <button class="roll-nav roll-right tabRight" onclick="scrollTabRight()">
                <i class="fa fa-forward" style="margin-left: 3px;"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown tabClose" data-toggle="dropdown">
                    页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-right" style="min-width: 128px;">
                    <li><a class="tabReload" href="javascript:refreshTab();">刷新当前</a></li>
                    <li><a class="tabCloseCurrent" href="javascript:closeCurrentTab();">关闭当前</a></li>
                    <li><a class="tabCloseAll" href="javascript:closeOtherTabs(true);">全部关闭</a></li>
                    <li><a class="tabCloseOther" href="javascript:closeOtherTabs();">除此之外全部关闭</a></li>
                </ul>
            </div>
            <button class="roll-nav roll-right fullscreen" onclick="App.handleFullScreen()"><i
                    class="fa fa-arrows-alt"></i></button>
        </div>
        <div class="content-iframe " style="background-color: #ffffff; ">
            <div class="tab-content " id="tab-content">

            </div>
        </div>
    </div>
    <!-- /.content-wrapper -->
    <!-- Control Sidebar -->
    <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
            <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
            <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
            <!-- Home tab content -->
            <div class="tab-pane" id="control-sidebar-home-tab">
                <h3 class="control-sidebar-heading">Recent Activity</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-birthday-cake bg-red"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                                <p>Will be 23 on April 24th</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-user bg-yellow"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

                                <p>New phone +1(800)555-1234</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>

                                <p>nora@example.com</p>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <i class="menu-icon fa fa-file-code-o bg-green"></i>

                            <div class="menu-info">
                                <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>

                                <p>Execution time 5 seconds</p>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->

                <h3 class="control-sidebar-heading">Tasks Progress</h3>
                <ul class="control-sidebar-menu">
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Custom Template Design
                                <span class="label label-danger pull-right">70%</span>
                            </h4>

                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Update Resume
                                <span class="label label-success pull-right">95%</span>
                            </h4>

                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Laravel Integration
                                <span class="label label-warning pull-right">50%</span>
                            </h4>

                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)">
                            <h4 class="control-sidebar-subheading">
                                Back End Framework
                                <span class="label label-primary pull-right">68%</span>
                            </h4>

                            <div class="progress progress-xxs">
                                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                            </div>
                        </a>
                    </li>
                </ul>
                <!-- /.control-sidebar-menu -->

            </div>
            <!-- /.tab-pane -->
            <!-- Stats tab content -->
            <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
            <!-- /.tab-pane -->
            <!-- Settings tab content -->
            <div class="tab-pane" id="control-sidebar-settings-tab">
                <form method="post">
                    <h3 class="control-sidebar-heading">General Settings</h3>

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Report panel usage
                            <input type="checkbox" class="pull-right" checked>
                        </label>

                        <p>
                            Some information about this general settings option
                        </p>
                    </div>
                    <!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Allow mail redirect
                            <input type="checkbox" class="pull-right" checked>
                        </label>

                        <p>
                            Other sets of options are available
                        </p>
                    </div>
                    <!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Expose author name in posts
                            <input type="checkbox" class="pull-right" checked>
                        </label>

                        <p>
                            Allow the user to show his name in blog posts
                        </p>
                    </div>
                    <!-- /.form-group -->

                    <h3 class="control-sidebar-heading">Chat Settings</h3>

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Show me as online
                            <input type="checkbox" class="pull-right" checked>
                        </label>
                    </div>
                    <!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Turn off notifications
                            <input type="checkbox" class="pull-right">
                        </label>
                    </div>
                    <!-- /.form-group -->

                    <div class="form-group">
                        <label class="control-sidebar-subheading">
                            Delete chat history
                            <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                        </label>
                    </div>
                    <!-- /.form-group -->
                </form>
            </div>
            <!-- /.tab-pane -->
        </div>
    </aside>
    <!-- /.control-sidebar -->
    <!-- Add the sidebar's background. This div must be placed
         immediately after the control sidebar -->
    <div class="control-sidebar-bg"></div>
<script>
    //$('li.active').parents('li').addClass('treeview').addClass('active');
</script>

<script type="text/javascript">
    /**
     * 本地搜索菜单
     */
    function search_menu() {
        //要搜索的值
        var text = $('input[name=q]').val();
        var $ul = $('.sidebar-menu');
        $ul.find("a.nav-link").each(function () {
            var $a = $(this).css("border", "");
            //判断是否含有要搜索的字符串
            if ($a.children("span.menu-text").text().indexOf(text) >= 0) {
                //如果a标签的父级是隐藏的就展开
                $ul = $a.parents("ul");
                if ($ul.is(":hidden")) {
                    $a.parents("ul").prev().click();
                }
                //点击该菜单
                $a.click().css("border", "1px solid");
//                return false;
            }
        });
    }

    $(function () {
        App.setbasePath("iframe/");
        App.setGlobalImgPath("dist/img/");
        addTabs({
            id: '10008',
            title: '欢迎页',
            close: false,
            url: 'index?cn=welcome&sdo=form&type=demo&inframe=1&iframe=1',
            urlType: "relative"
        });
        App.fixIframeCotent();
        var menus = [
            {
                id: "9000",
                text: "header",
                icon: "",
                isHeader: true
            },
            {
                id: "9001",
                text: "UI Elements",
                icon: "fa fa-laptop",
                children: [
                    {
                        id: "90001",
                        text: "tt_list",
                        icon: "fa fa-arrow-circle-o-right",
                        url: "index?cn=admin&sdo=list&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab"
                    },
                    {
                        id: "90002",
                        text: "tt_form",
                        icon: "fa fa-arrow-circle-o-right",
                        url: "index?cn=admin&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        isOpen:true
                    },
                    {
                        id: "80002",
                        text: "tt_form2",
                        icon: "fa fa-arrow-circle-o-right",
                        url: "index?cn=admin2&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        isOpen:true
                    },
                    {
                        id: "90011",
                        text: "buttons",
                        icon: "fa fa-arrow-circle-o-right",
                        url: "index?cn=button&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab"
                    },
                    {
                        id: "90012",
                        text: "icons",
                        url: "index?cn=icon&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90013",
                        text: "general",
                        url: "index?cn=general&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90014",
                        text: "modals",
                        url: "index?cn=Modals&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90015",
                        text: "Timeline",
                        url: "index?cn=Timeline&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90016",
                        text: "table",
                        url: "index?cn=table&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    }
                ]
            },
            {
                id: "9002",
                text: "Forms",
                icon: "fa fa-edit",
                children: [
                    {
                        id: "90021",
                        text: "advanced",
                        url: "index?cn=advanced&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90022",
                        text: "submit",
                        url: "index?cn=submit&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90023",
                        text: "editors",
                        url: "index?cn=editor&sdo=form&type=demo&inframe=1&iframe=1",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right"
                    },
                    {
                        id: "90024",
                        text: "百度",
                        url: "https://www.baidu.com",
                        targetType: "iframe-tab",
                        icon: "fa fa-arrow-circle-o-right",
                        urlType: 'abosulte'
                    }
                ]
            }
        ];
        $('.sidebar-menu').sidebarMenu({data: menus});

        // 动态创建菜单后，可以重新计算 SlimScroll
        // $.AdminLTE.layout.fixSidebar();

        /*if ($.AdminLTE.options.sidebarSlimScroll) {
            if (typeof $.fn.slimScroll != 'undefined') {
                //Destroy if it exists
                var $sidebar = $(".sidebar");
                $sidebar.slimScroll({destroy: true}).height("auto");
                //Add slimscroll
                $sidebar.slimscroll({
                    height: ($(window).height() - $(".main-header").height()) + "px",
                    color: "rgba(0,0,0,0.2)",
                    size: "3px"
                });
            }
        }*/

    });
</script>