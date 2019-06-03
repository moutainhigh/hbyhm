<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<a href="javascript:;" class="btn btn-default">
    <i class="fa fa-arrow-circle-o-up"></i>导入还款<input type="file" id="picture_upload" runat="server" name="file" onchange="upload_cover(this)" accept="xls/xlsx/*" style="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;" class="uploadfileclass">
</a>