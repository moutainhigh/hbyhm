<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/manager/js/visual/echarts.js"></script>
<link rel="stylesheet" href="/manager/css/visual/Visual.css" type="text/css" />

<!-- 可视化数据图 -->
<div class="content_visual">
    <div class="form_visual" >
        <!-- <%--  数据图一  --%> -->
        <div class="form_visual_form1" id="form_visual_form1" style="width: 1690px; height: 840px;">
            <div class="visual_form_top_box">
                <!-- 报单统计开始 -->
                <div class="visual_form_box">

                    <div class="baodan_left">
                        <div class="  font_color_title" style="margin-bottom: 2%;">报单统计</div>
                        <div style="width: 100%;height: 7%;">
                            <ul class="font_color_1 condition1" >
                                <li>
                                    <input type="text"  value="请输入代理商" onblur="baodanselect()"
                                           onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="baodanval"/>
                                </li>
                                <%--<li>
                                    <select id="baodansel" onchange="baodanselect()" >
                                        <option selected value="0"> 请选择省份</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${comm_city}">
                                            <option value="${list.id}">${list.name}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>--%>
                                <li>
                                    <select id="baodantime" onchange="baodanselect()">
                                        <option selected value="0"> 请选择时间</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${years }" >
                                            <option value="${list}">${list}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>
                            </ul>
                        </div>
                        <div style="height: 80%;width: 100%; position: relative; top: -10px; padding-left: 4%;" id="baodan">
                        </div>
                    </div>
                    <div class="baodan_right">
                        <div class="paiming_top" >
                            <div class="paiming_top_border" style="width:40%;">
                                <p class="danshu_style font_color_2">${billlistHb[0].amount }<font>笔</font></p>
                                <p class="font_color_2 font_size_1">本月报单总量</p>
                            </div>
                        </div>
                        <div style="height: 85%; width: 100%;">
                            <div id="kehunianling" style="height: 90%;width: 100%;position: relative; top: -10px; ">
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 报单统计结束 -->

                <!-- 抵押材料开始 -->
                <div class="visual_form_box" style="float: left;">
                    <div class="baodan_right_1">
                        <div class="  font_color_title" style="margin-bottom: 2%;">抵押归档情况</div>
                        <div class="paiming_top" style="margin-top: 0;margin-left: 10px;">
                            <div class="paiming_bottom_border" style="width:46%;float: left;">
                                <p class="danshu_style font_color_2">${fklistHb[0].amount }<font>笔</font></p>
                                <p class="font_color_2 font_size_1">本月已完成抵押</p>
                                <p class="font_color_2 font_size_1">订单总数</p>
                            </div>
                        </div>
                        <div style="width: 100%;height: 5%;margin-top: 25px;">
                            <ul class="font_color_1 condition1" >
                                <li>
                                    <input type="text"  value="请输入代理商" onblur="diyaselect()"
                                           onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="diyaval"/>
                                </li>
                                <%--<li>
                                    <select id="diyasel" onchange="diyaselect()">
                                        <option selected value="0"> 请选择省份</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${comm_city}">
                                            <option value="${list.id}">${list.name}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>--%>
                                <li>
                                    <select id="diyatime" onchange="diyaselect()">
                                        <option selected value="0"> 请选择时间</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${years }" >
                                            <option value="${list}">${list}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>
                            </ul>
                        </div>
                        <div style="height: 68%;width: 90%; position: relative;" id="diyawancheng">

                        </div>
                    </div>
                    <div class="baodan_right_1" style="float: right;">
                        <div class="  font_color_title" style="margin-bottom: 2%;">材料回收情况</div>
                        <div style="width: 100%;height: 7%;">
                            <ul class="font_color_1 condition1" >
                                <li>
                                    <input type="text"  value="请输入代理商" onblur="cailiaoselect()"
                                           onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="cailiaoval"/>
                                </li>
                                <%--<li>
                                    <select id="cailiaosel" onchange="cailiaoselect()">
                                        <option selected value="0"> 请选择省份</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${comm_city}">
                                            <option value="${list.id}">${list.name}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>--%>
                                <li>
                                    <select id="cailiaotime" onchange="cailiaoselect()">
                                        <option selected value="0"> 请选择时间</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${years }" >
                                            <option value="${list}">${list}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>
                            </ul>
                        </div>
                        <div style="height: 80%;width: 100%; position: relative; top: 10px; padding-left: 4%;" id="cailiaohuishou">
                        </div>
                    </div>
                </div>
                <!-- 抵押材料结束 -->
            </div>

            <div class="visual_form2_box">
                <!-- 逾期率开始 -->
                <div class="form2_box" style="margin-bottom: 20px;">
                    <div class="form2_box_left">
                        <div style="height: 100%;width: 40%; float: left;">
                            <div class="  font_color_title" style="margin-bottom: 2%;">逾期率</div>
                            <div style="width: 100%;height: 7%;">
                                <ul class="font_color_1 condition2" >
                                    <li>
                                        <input type="text"  value="请输入代理商" onblur="yuqiselect()"
                                               onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="yuqival"/>
                                    </li>
                                    <%--<li>
                                        <select id="yuqisel" onchange="yuqiselect()">
                                            <option selected value="0"> 请选择省份</option>
                                            <option value="0"> 全部</option>
                                            <c:forEach var="list" items="${comm_city}">
                                                <option value="${list.id}">${list.name}</option>
                                            </c:forEach>
                                        </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                    </li>--%>

                                </ul>

                            </div>
                            <div style="height: 80%;width: 100%; padding-left: 4%;" id="yuqilv_1">
                            </div>

                        </div>
                        <div style="height: 100%;width: 18%; float: left;">
                            <div style="height: 20%;width: 100%;margin-top: 20%;">
                                <div class="graph_statistics_content" style="width: 60%;height: 65%;margin-top: 10%;margin: auto;">
                                    <img src="${pageContext.request.contextPath }/manager/images/724618841177387879.png" style="width: 23px;height: 23px;padding-top: 5px;"/>
                                    <p style="color:#2F4554;font-size: 12px;padding-top: 2px;">逾期率预警</p>
                                </div>
                            </div>
                            <table class="graph_overdue_center">
                                <tr>
                                    <th>代理商</th>
                                    <th>逾期率</th>
                                </tr>
                                <tr>
                                    <td>${yuqilvHb[0].gname == null?"暂无":yuqilvHb[0].gname}</td>
                                    <td>${yuqilvHb[0].yuqilv == null?0:yuqilvHb[0].yuqilv}%</td>
                                </tr>
                                <tr>
                                    <td>${yuqilvHb[1].gname == null?"暂无":yuqilvHb[1].gname}</td>
                                    <td>${yuqilvHb[1].yuqilv == null?0:yuqilvHb[1].yuqilv}%</td>
                                </tr>
                                <tr>
                                    <td>${yuqilvHb[2].gname == null?"暂无":yuqilvHb[2].gname}</td>
                                    <td>${yuqilvHb[2].yuqilv == null?0:yuqilvHb[2].yuqilv}%</td>
                                </tr>
                                <tr>
                                    <td>${yuqilvHb[3].gname == null?"暂无":yuqilvHb[3].gname}</td>
                                    <td>${yuqilvHb[3].yuqilv == null?0:yuqilvHb[3].yuqilv}%</td>
                                </tr>
                                <tr>
                                    <td>${yuqilvHb[4].gname == null?"暂无":yuqilvHb[4].gname}</td>
                                    <td>${yuqilvHb[4].yuqilv == null?0:yuqilvHb[4].yuqilv}%</td>
                                </tr>
                            </table>
                            <div style="width: 100%; height: 10%;margin-top: 2%;">
                                <div class="paiming_button" onclick="show_bjls1()" style="cursor:pointer">更多</div>
                            </div>
                        </div>
                        <div style="height: 100%;width: 36%; float: left;">
                            <ul class="graph_overdue_right" style="margin-top:15%;" class="font_color_3">
                                <li>
                                    <div  class="paiming_button" style="margin: 0;float: left;">省份</div>
                                </li>
                                <li class="font_color_3">逾期省份</li>
                                <li class="font_color_3">逾期金额</li>
                            </ul>
                            <div style="width: 100%;height:80%; margin-left: 5%;" id="yuqilv_2">
                            </div>
                        </div>
                    </div>
                    <!-- <div class="form2_box_right">

                    </div> -->
                </div>
                <!-- 逾期率结束 -->

                <div class="form2_box1" style="margin-bottom: 20px;">
                    <!-- 代理商综合能力分析开始 -->
                    <div class="form2_box_1">
                        <div class="  font_color_title" style="margin-bottom: 2%;">代理商综合能力分析</div>
                        <div style="width: 100%;height: 5%;">
                            <ul class="font_color_1 condition1" >
                                <li style="margin-left: 5px;">
                                    <input type="text"  value="请输入代理商" onblur="dailiselect()"
                                           onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="dailival"/>
                                </li>

                                <li>
                                    <select id="dailitime" onchange="dailiselect()">
                                        <option selected value="0"> 请选择时间</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${years }" >
                                            <option value="${list}">${list}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>
                            </ul>
                        </div>
                        <div style="height: 90%;width: 100%;" id="dalifenxi">
                        </div>

                    </div>
                    <!-- 代理商综合能力分析结束 -->

                    <!-- 征信查询通过率开始 -->
                    <div class="form2_box_2">
                        <div class="  font_color_title" style="margin-bottom: 2%;">征信查询通过率</div>
                        <div style="width: 100%;height: 5%;margin-top: 25px;">
                            <ul class="font_color_1 condition1" >
                                <li>
                                    <input type="text"  value="请输入代理商" onblur="zhengxinselect()"
                                           onfocus="javascript:if(this.value=='请输入代理商')this.value='';" id="zhengxinval"/>
                                </li>
                                <%--<li>
                                    <select id="zhengxinsel" onchange="zhengxinselect()">
                                        <option selected value="0"> 请选择省份</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="list" items="${comm_city}">
                                            <option value="${list.id}">${list.name}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>--%>
                                <li>
                                    <select id="zhengxintime" onchange="zhengxinselect()">
                                        <option selected value="0"> 请选择时间</option>
                                        <option value="0"> 全部</option>
                                        <c:forEach var="year" items="${years }" >
                                            <option value="${year}">${year}</option>
                                        </c:forEach>
                                    </select><i style="padding-left: 5px; font-weight:bold;">></i>
                                </li>
                            </ul>
                        </div>
                        <div style="height: 80%;width: 100%;" id="zhengxinchaxun">
                        </div>
                    </div>
                    <!-- 征信查询通过率结束 -->
                </div>
            </div>
        </div>
    </div>
</div>



<div class="modal fade in" id="addModal_tdtf1" tabindex="-1" role="dialog" aria-labelledby="imgs_yyclLabel"
     aria-hidden="false">
    <div class="modal-dialog modal-lg" style="width: 400px;height: 600px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" align="center" id="">省份/代理商排名</h4>
            </div>
            <div class="modal-body modal-open"
                 style="height:100%;border:1px solid #ccc;background-color:#F7F7F7;border-radius: 10px;margin:30px;">
                <div class="paiming_table">
                    <%--   逾期更多排名  --%>
                    <table class="graph_overdue_center yuqigd">
                        <tr>
                            <th>代理商</th>
                            <th>逾期率</th>
                        </tr>
                        <c:forEach items="${ count }" var="rank" varStatus="lists">
                            <tr>
                                <td>${yuqilvHb[lists.count-1].gname == null?"暂无":yuqilvHb[lists.count-1].gname}</td>
                                <td>${yuqilvHb[lists.count-1].yuqilv == null?0:yuqilvHb[lists.count-1].yuqilv}%</td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath }/manager/js/visual/visualHb.js"></script>
<script type="text/javascript">
    window.onload =function(){
        //加载可视化组件
        baodanselect();
        diyaselect();
        cailiaoselect();
        zhengxinselect();
        dailiselect();
        yuqiselect();
    };

    function show_bjls1() {
        $('#addModal_tdtf1').modal({show: true});
    }
</script>







