<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.tt.tool.addDate" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date nowDate = new Date();
    Date date7 = addDate.dayAddNum(nowDate, 7);
    Date date30 = addDate.dayAddNum(nowDate, 30);
    Date date365 = addDate.dayAddNum(nowDate, 365);
    Date date1 = addDate.dayAddNum(nowDate, 1);
    String nd = sdf.format(nowDate);
    String nd7 = sdf.format(date7);
    String nd30 = sdf.format(date30);
    String nd365 = sdf.format(date365);
    String nd1 = sdf.format(date1);
%>

<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl&dt=<%=nd%>" class="btn btn-default">
    <i class="fa fa-plus"></i>今天的
</a>
<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl&dtbe=<%=nd%> - <%=nd7%>" class="btn btn-default">
    <i class="fa fa-plus"></i>7天的
</a>
<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl&dtbe=<%=nd%> - <%=nd30%>" class="btn btn-default">
    <i class="fa fa-plus"></i>30天的
</a>
<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl&dtbe=<%=nd%> - <%=nd365%>" class="btn btn-default">
    <i class="fa fa-plus"></i>365天的
</a>
<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl&dt=<%=nd1%>" class="btn btn-default">
    <i class="fa fa-plus"></i>明天的
</a>
<a href="/manager/index?cn=dsgl&sdo=list&type=cwgl" class="btn btn-default" style="background: #800000;color:white;">
    <i class="fa fa-plus"></i>总额：${totals}元
</a>