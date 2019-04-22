<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<a href="<%=Tools.urlKill("toExcel")+"&toExcel=1"%>" class="btn btn-default"><i class="fa fa-arrow-circle-o-down"></i>导出</a>
<a href="<%=Tools.urlKill("toZip")+"&toZip=1"%>" class="btn btn-default"><i class="fa fa-arrow-circle-o-down"></i>Zip头像</a>