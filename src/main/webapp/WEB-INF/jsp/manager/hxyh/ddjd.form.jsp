<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url = Tools.urlKill("sdo|icbc_id|tab|id") + "&sdo=form&icbc_id=";
    TtList modallist = (TtList) request.getAttribute("modallist");
    TtMap jdtag = (TtMap) request.getAttribute("jdtag");
    String tab = "28";
    if (request.getAttribute("tab") != null && !request.getAttribute("tab").equals("")) {
        tab = request.getAttribute("tab").toString();
    }
%>
<div class="admin-content nav-tabs-custom box">
    <div class="box-header with-border">
        <h3 class="box-title">订单进度</h3>
    </div>
        <div class="box-body" id="tab-content">
            <div style="border:1px solid #478FCA;   margin:5px; padding:20px;border-radius: 10px;">
                <ul class="nav nav-tabs">
                    <%

                        if (!modallist.isEmpty() && modallist.size() > 0) {
                            for (int i = 0; i < modallist.size(); i++) {
                                TtMap modal = modallist.get(i);
                                if (jdtag.get("id_" + modal.get("id")) != null && !jdtag.get("id_" + modal.get("id")).equals("")) {
                                    if (modal.get("id").equals(tab)) {
                    %>
                    <li class="active">
                        <a style="background-color: rgb(25, 53, 78); color: rgb(255, 255, 255); font-size: 15px;"
                           id="<%=modal.get("cn")%>"
                           href="<%=url%>${icbc_id}&id=<%=jdtag.get("id_" + modal.get("id"))%>&tab=<%=modal.get("id")%>"
                           class="btn btn-block btn-info">
                            <%=modal.get("name")%>
                        </a>
                    </li>
                    <%} else {%>
                    <li>
                        <a style="background-color: rgb(51, 122, 183); color: rgb(255, 255, 255); font-size: 15px;"
                           id="<%=modal.get("cn")%>"
                           href="<%=url%>${icbc_id}&id=<%=jdtag.get("id_" + modal.get("id"))%>&tab=<%=modal.get("id")%>"
                           class="btn btn-block btn-info">
                            <%=modal.get("name")%>
                        </a>
                    </li>
                    <%
                        }

                    } else {
                    %>
                    <li>
                        <a style="background-color: rgb(167, 167, 167); color: rgb(255, 255, 255); font-size: 15px;"
                           id="<%=modal.get("cn")%>" href="javascript:alert('暂无处理过程!!!');"
                           class="btn btn-block btn-info">
                            <%=modal.get("name")%>
                        </a>
                    </li>
                    <%
                        }
                        if (i < modallist.size() - 1) {
                    %>
                    <li style="display:block;text-align:center; line-height:50px">
                        <i class="fa fa-long-arrow-right"></i>
                    </li>
                    <%
                                }
                            }
                        }
                    %>
                </ul>
            </div>
            <div class="tab-content">
                <div class="tab-pane active" id="">
                    <div class="row">
                        <div class="col-md-12">
                            <!-- The time line -->
                            <ul class="timeline">
                                <!-- timeline time label -->
                                <li class="time-label">
<%--                  <span class="bg-red">
                  <font style="vertical-align: inherit;">
                  <font style="vertical-align: inherit;">
                      开始时间 2018-12-27 08:56:00
                  </font>
                  </font>
                  </span>--%>
                                </li>
                                <!-- /.timeline-label -->
                                <!-- timeline item -->
                                <%
                                 TtList jdlist=(TtList)request.getAttribute("jdlist");
                                 if(jdlist!=null&&jdlist.size()>0){
                                     for(int j=0;j<jdlist.size();j++){
                                         TtMap jm=jdlist.get(j);
                                %>
                                <li>
                                    <i class="fa fa-user bg-aqua"></i>
                                    <div class="timeline-item">
                                        <span class="time">
                                            <i class="fa fa-clock-o"></i>
                                        <%=jm.get("dt_add")%>
                                        </span>
                                        <h3 class="timeline-header">
                                            <%=DataDic.dic_zx_status.get(jm.get("status"))%>
                                        </h3>
                                        <div class="timeline-body">
                                        备注：<%=jm.get("remark").replace("null","")%>
                                        </div>
                                    </div>
                                </li>
                                <%
                                    }
                                    }
                                %>
                                <li>
                                    <i class="fa fa-clock-o bg-gray"></i>
                                </li>
                            </ul>

                        </div>
                        <!-- /.col -->
                    </div>
                </div>
            </div>


        </div>
    </div>
