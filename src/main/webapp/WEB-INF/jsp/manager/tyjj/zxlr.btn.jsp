<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.Config" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="Tools" uri="/tld/manager" %>
<%
	String saerch_fsid = request.getParameter("saerch_fsid");
	String dtbe = request.getParameter("dtbe");
	int fsid = Tools.strToInt(saerch_fsid);
	String kw = request.getParameter("kw");
	if (kw==null){
		kw = "";
	}
	if (dtbe==null){
		dtbe = "";
	}
	String cn = request.getParameter("cn");
	String type = request.getParameter("type");
	String search_status = request.getParameter("search_status");
	search_status = search_status;
%>
<form action="<%=Tools.urlKill("kw|search_cityid|saerch_fsid|dtbe|search_status|kw")%>" id="search_btn_form">
<input type="hidden" name="cn" value="<%=cn%>" />
<input type="hidden" name="type" value="<%=type%>"/>
<input type="hidden" name="sdo" value="list"/>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="row inline-from">
												<div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所属城市</span>
                                <select id="search_cityid" name="search_cityid" title="所属城市" class="selectpicker  form-control"
                                         multiple data-live-search="true" data-max-options="1">
                                   <%-- <c:forEach items="${citylist}" var="i">
                                        <option value="${i.id}">${i.name}</option>
                                    </c:forEach>--%>
                                </select>
                            </div>
                        </div>
												<div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">所属公司</span>
                                <select id="saerch_fsid" name="saerch_fsid" title="公司" class="selectpicker  form-control"
                                         multiple data-live-search="true" data-max-options="1">
                                    <c:forEach items="${compylist}" var="i">
                                        <option value="${i.id}">${i.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
												<div class="col-sm-4">
														<div class="input-group">
														<span class="input-group-addon">时间区间</span>
															<input style="width:100%;" type="text" class="form-control daterange selectpicker  form-control" multiple data-live-search="true" data-max-options="1" id="dtbe" name="dtbe" value="" placeholder="区间">
														</div>
												</div>
												<div class="col-sm-4">
                            <div class="input-group">
                                <span class="input-group-addon">状态</span>
                                <select style="width:100%;" id="search_status" name="search_status" title="选择状态" class="form-control">
																<option value="-1">请选择</option>
                                    <%=Tools.dicopt(DataDic.dic_tlzf_qystatus,search_status)%>
                                </select>
                            </div>
                        </div>
                        <div class="col-sm-8">
                            <div class="input-group">
                                <input style="width:100%;" type="text" class="form-control" name="kw" id="kw" placeholder="模糊匹配:客户(配偶)姓名/电话/身份证)" onkeydown="searchkeypress(event);" value="<%=kw%>">
																<span class="input-group-addon"><a onclick="dosearch();">搜索</a></span>
																<span class="input-group-addon"><a onclick="docleansearch();">清除搜索</a></span>
                            </div>
                        </div>
                    </div>
                </div>
				</div>
</form>
<script>
function dosearch(){
	$('#search_btn_form').submit();
}
function searchkeypress(e){
	if(e.keyCode == 13){
		dosearch();
	}
}
function docleansearch(){
	window.location.href='<%=Tools.urlKill("kw|search_cityid|saerch_fsid|dtbe|search_status|kw")%>';
}
$(document).ready(function () {
    $.post("/ttAjax?do=list&cn=comm_citys",function (data) {
       // alert("222222222");
            var obj = eval('(' + data + ')');
            $("#search_cityid").empty();
            for(var i=0;i<obj.length;i++){
                $("#search_cityid").append("<option value='"+obj[i].id+"'>"+obj[i].name+"</option>");
            }
        $('.selectpicker').selectpicker('val', '');
        $('.selectpicker').selectpicker('refresh');
        }
    )
})
</script>
