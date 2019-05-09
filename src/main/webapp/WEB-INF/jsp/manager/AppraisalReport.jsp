<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理员模式</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover,user-scalable=0">
    <meta name="description"
          content="快加认证专业第三方汽车鉴定评估系统还提供车辆核档、维保记录、风控系统化管理等，为金融公司把控风险，节省成本！快加认证作为专业权威的第三方车辆鉴定评估机构，创新 制订出高于行业规范标准的服务+、规范+、诚信+、专业+、 创新+的“五+”服务体系。为二手车 交易、汽车金融提供最专业的服务。目前全国已有近百家实体 门店相继加盟并开业，区域覆盖上海、北京、武汉、西安、福 建等全国多个重点城市和区域，用心给客户提供细致、周到的 服务。">
    <meta name="keywords" content="快加认证,车辆鉴定,车辆评估,二手车鉴定,二手车评估,福建,上海,福州">
    <%--<script src="dsj/kjs_icbc_style/amazeui.min.js"></script>--%>
    <script src="dsj/kjs_icbc_style/layer.js"></script>
    <script src="dsj/kjs_icbc_style/swiper.min.js"></script>
    <link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">
    <link rel="stylesheet" href="dsj/kjs_icbc_style/amazeui.min.css"/>
    <%--<link rel="stylesheet" href="dsj/kjs_icbc_style/layer.css" />--%>
    <link rel="stylesheet" href="dsj/kjs_icbc_style/08335.css"/>
    <link rel="stylesheet" href="dsj/kjs_icbc_style/21448.css"/>
    <link rel="stylesheet" href="dsj/kjs_icbc_style/21539.css"/>
    <link rel="stylesheet" href="dsj/kjs_icbc_style/app.css"/>
    <link rel="stylesheet" href="dsj/kjs_icbc_style/swiper.min.css">
</head>
<header data-am-widget="header" class="am-header am-header-default am-header-fixed">
    <div class="am-header-left am-header-nav">
        <a href="javascript:window.history.back();" class="">
            <i class="am-header-icon iconfont icon-back"></i>
        </a>
    </div>
</header>
<body>
<div class="p-steps am-margin-sm am-margin-top-0">
    <ul class="am-avg-sm-3">
        <li class="p-steps-on">
            <div class="p-steps-item">提交订单</div>
            <i class="p-steps-icon iconfont icon-ok1"></i>
        </li>
        <li class="p-steps-on">
            <div class="p-steps-item">查询中</div>
            <i class="p-steps-icon iconfont icon-ok1"></i>
        </li>
        <li class="p-steps-on">
            <div class="p-steps-item">查询完成</div>
            <i class="p-steps-icon iconfont icon-ok1"></i>
        </li>
    </ul>
</div>
<div class="am-margin-sm am-padding-sm am-radius-lg p-boxShadow p-panel p-panel-def p-yhOrder">
    <div class="head">
        <div class="attr">
            <div class="p-yhColor-blove">订单号：${icbc.gems_code }</div>
            <span class="am-text-xs">提交时间：${fn:replace(icbc.dt_add, ".0", "")}</span>
        </div>
        <div class="btn">
            <button type="button" class="am-btn am-btn-success am-round am-text-xs">查询完成</button>
        </div>
    </div>
    <div class="cnt assess vio">
        <div class="cntlt">
            <span class="am-text-lg p-yhColor-secondary">大数据<br>查询</span>
        </div>
        <div class="cntrt ">
            <ul class="list">
                <li>
                    <span class="">被查询人：</span>
                    <span class="p-yhColor-secondary">${icbc.c_name }</span>
                    <%-- <span class="tag" style="color:white;background:green;">
                    实名：${icbc.c_name }</span> --%>
                </li>
                <li>
                    <span class="">身份证：</span>
                    <span class="p-yhColor-common">${icbc.c_cardno }</span>
                </li>
                <li>
                    <span class="">电话：</span>
                    <span class="p-yhColor-secondary">${icbc.c_tel }</span>
                </li>
                <li>
                    <span class="">报告基准日：</span>
                    <span class="p-yhColor-common">${fn:replace(dsj.dt_add, ".0", "")}</span>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="am-margin-sm am-padding-sm am-radius-lg p-boxShadow p-panel p-yhOrder">
    <div class="">
        <span class="am-text-sm p-yhColor-warm">欺诈分:${ANTIFRAUD.score }</span>
        <span class="am-text-xs p-yhColor-disable">（越高风险越高）</span>
    </div>
    <div class="p-yhProgress">
        <span class="grade" style="width: ${ANTIFRAUD.score / 10 }%"></span>
        <div class="am-progress am-progress-xs">
            <div class="am-progress-bar am-progress-bar-danger" style="width: ${ANTIFRAUD.score/10}%"></div>
        </div>
        <span class="start">0</span>
        <span class="finish">1000</span>
    </div>
    <ul class="p-yhOrder-useInfo">
        <%-- <li>
            <div class="title stateError">人工审核</div>
            <div class="sub">个人基本信息核查</div>
        </li>
        <li>
            <div class="title stateError">${data.resultData.idVerification.location }</div>
            <div class="sub">身份证归属地</div>
        </li>
        <li>
            <div class="title stateError">${data.resultData.carrier.location }</div>
            <div class="sub">手机号码归属地</div>
        </li> --%>
    </ul>
</div>
<div style="wigth:1000px">
    <!-- 欺诈风险 -->
    <c:forEach var="r" items="${requestScope.risk_items }">
        <div class="p-yhOrder-rpt p-boxShadow am-radius-lg">
            <div class="rptTitle">${r.risk_name}</div>
            <ul class="am-avg-sm-2 rptCnt">
                <li class="credit">
                    <div class="attr">
                        <span class="am-text-sm p-yhColor-warm">风险分:${r.score }</span>
                    </div>
                </li>
                <li class="base">
                    <div class="attr"></div>
                </li>
            </ul>
            <c:if test="${!empty r.risk_detail }">
                <!--black_list -->
                <c:if test="${r.risk_detail.type eq 'black_list'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <li class="credit">
                            <div class="attr">
                                    ${r.risk_detail.hit_type_displayname}
                                <br>
                                    ${r.risk_detail.description}
                            </div>
                        </li>
                        <li class="base">
                            <div class="attr">
                                <c:forEach var="item" items="${r.risk_detail.black_list_details}">
                                    ${item.fraudTypeDisplayName}：${item.value}<br>
                                </c:forEach>
                            </div>
                        </li>
                    </ul>
                </c:if>
                <!-- frequency_distinct_details -->
                <c:if test="${r.risk_detail.type eq 'frequency_distinct'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <c:forEach var="item" items="${r.risk_detail.frequency_distinct_details}">
                            <li class="credit">
                                <div class="attr">
                                        ${item.description}:${item.result}
                                </div>
                            </li>
                            <li class="base">
                                <div class="attr">
                                    <c:forEach var="d" items="${item.data}">
                                        ${d}<br>
                                    </c:forEach>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
                <!-- frequency_one_dim -->
                <c:if test="${r.risk_detail.type eq 'frequency_one_dim'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <li class="credit">
                            <div class="attr">
                                    ${r.risk_detail.result}
                            </div>
                        </li>
                        <li class="base">
                            <div class="attr">
                            </div>
                        </li>

                    </ul>
                </c:if>

                <!-- association_partner -->
                <c:if test="${r.risk_detail.type eq 'association_partner'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <li class="credit">
                            <div class="attr">
                                平台数：${r.risk_detail.association_partner_count}
                            </div>
                        </li>
                        <li class="base">
                            <div class="attr">
                                <c:forEach var="a" items="${r.risk_detail.association_partner_details}">
                                    ${a.industryDisplayName }:${a.count } <br>
                                </c:forEach>
                            </div>
                        </li>

                    </ul>
                </c:if>
                <!-- grey_list -->
                <c:if test="${r.risk_detail.type eq 'grey_list'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <li class="credit">
                            <div class="attr">
                                    ${r.risk_detail.hit_type_displayname}
                                <br>
                                    ${r.risk_detail.description}
                            </div>
                        </li>
                        <li class="base">
                            <div class="attr">
                                <c:forEach var="a" items="${r.risk_detail.grey_list_details}">
                                    ${a.fraud_type }:${a.value }: ${a.risk_level }<br>
                                </c:forEach>
                            </div>
                        </li>

                    </ul>
                </c:if>
                <!-- discredit_count -->
                <c:if test="${r.risk_detail.type eq 'discredit_count'}">
                    <ul class="am-avg-sm-2 rptCnt">
                        <c:forEach var="a" items="${r.risk_detail.overdue_details}">
                            <li class="credit">
                                <div class="attr">
                                    预期次数：${a.overdue_count}<br>
                                    预期金额范围：${a.overdue_amount_range}
                                </div>
                            </li>
                            <li class="base">
                                <div class="attr">
                                    预期时间：${a.overdue_time}<br>
                                    预期天数范围：${a.overdue_day_range}
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </c:if>


        </div>
    </c:forEach>

</div>
</body>
</html>