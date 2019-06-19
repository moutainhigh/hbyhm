<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.data.TtList" %>
<%@ page import="com.tt.data.TtMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="admin-content nav-tabs-custom box">
	<div class="box-header with-border">
		<div class="box-header with-border">
			<h3 class="box-title">修改用户</h3>
		</div>
		<div class="box-body">
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="name" name="name" placeholder="姓名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名拼音</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="namepy" name="namepy" placeholder="拼音">
				</div>
			</div>
			<script>
				$("#name").blur(function(){
					var name=$("#name").val();
				  var py=getPinYinFirstCharacter(name,"","");
					py = py.replace(/\ +/g,"").replace(/[\r\n]/g,"");
					document.getElementById("namepy").value=py;
				});
				</script>			
			<%
				TtMap infodb = (TtMap) request.getAttribute("infodb");
				boolean bAdd = Tools.myIsNull(request.getParameter("id"));
				boolean bNotReadOnley = Tools.myIsNull(request.getParameter("id")) || Tools.myIsNull(infodb.get("username"));
				TtMap minfo = (TtMap) request.getAttribute("minfo");
			%>
			<%--selcet，下拉框演示--%>
			<%
   			//dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
    		String sp = Tools.dicopt("comm_states", 0);//省会，
			%>
			<div class="form-group">
				<label class="col-sm-2 control-label">城市选择</label>
				<div class="col-sm-10">
					<div class="row inline-from">
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">省</span>
								<select name="state_id" id="state_id"  class="selectpicker  form-control" multiple data-live-search="true" data-max-options="1">
									<option value="0">请选择</option>
									<%=sp%>
								</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">市</span>
								<select name="city_id" id="city_id" class="form-control">
									<option value="0">请选择</option>
								</select>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script>
				/*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
														/ttAjax也可以单独使用，比如
														/ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
														* */
				objacl('#state_id', '#city_id', '/ttAjax?do=opt&cn=comm_citys&id=3&state_id=', '${infodb.state_id}', '${infodb.city_id}');
			</script>
			<div class="form-group">
				<label class="col-sm-2 control-label">用户名</label>
				<div class="col-sm-10">
					<input type="text" <%=bNotReadOnley?"":"readonly"%> class="form-control" id="username" name="username" placeholder="用户名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">用户密码</label>
				<div class="col-sm-10">
					<div class="input-append input-group">
						<input type="password" class="form-control" id="password" name="password" placeholder="密码"> <input type="text"
						 class="form-control" id="userpass1" name="userpass1" placeholder="密码" style="display: none;">
						<span onclick="sh_password()" tabindex="100" title="点击显示/隐藏密码" class="add-on input-group-addon" style="cursor: pointer;">
							<i id="i_style" class="glyphicon icon-eye-open glyphicon-eye-open"></i>
						</span>
					</div>
				</div>
				<script>
					function sh_password() {
						var s = document.getElementById("i_style").className;
						//alert(s);
						if (s == 'glyphicon icon-eye-open glyphicon-eye-open') {
							document.getElementById("userpass1").value = document
								.getElementById("password").value;
							document.getElementById("i_style").className = 'glyphicon icon-eye-close glyphicon-eye-close';
							document.getElementById("userpass1").style.display = 'inline-block';
							document.getElementById("password").style.display = 'none';
						} else {

							document.getElementById("password").value = document
								.getElementById("userpass1").value;
							document.getElementById("i_style").className = 'glyphicon icon-eye-open glyphicon-eye-open';
							document.getElementById("password").style.display = 'inline-block';
							document.getElementById("userpass1").style.display = 'none';
						}

					}
				</script>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">昵称</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="nickname" name="nickname" placeholder="昵称">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">头像</label>
				<div class="col-sm-10">
					<% 
								String  upFile = "../upfile.inc.jsp";
								String imgPreName = "avatarurl";
                String[] ssImgs = { //设置已有值
											!Tools.myIsNull(infodb.get(imgPreName))?infodb.get(imgPreName):""
                    };
                String sImgs = "";                    
                for (int i =0 ;i<ssImgs.length;i++){
                    sImgs=sImgs+ssImgs[i]+"|";
                }
            %>
					<%-- 可能这里用<%@include file %>模式更适合--%>
					<jsp:include page="<%=upFile%>">
						<jsp:param name="img_MarginImgSrc" value="" />
						<jsp:param name="img_MarginImgClass" value="" />
						<jsp:param name="img_Total" value="1" />
						<jsp:param name="img_NamePre" value="avatarurl" />
						<jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg" />
						<jsp:param name="l1div_Style" value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;" />
						<jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;" />
						<jsp:param name="img_FileStyle" value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;" />
						<jsp:param name="img_Class" value="imgclass" />
						<jsp:param name="img_FileClass" value="uploadfileclass" />
						<jsp:param name="img_SmallWidth" value="100" />
						<jsp:param name="img_SmallHeight" value="100" />
						<jsp:param name="sImgs" value="<%=sImgs%>" />
					</jsp:include>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">显示/隐藏</label>
				<div class="col-sm-10">
					<select id="showtag" name="showtag" class="form-control" >
						<option value="">请选择</option>
						<option value="1">显示</option>
						<option value="0">隐藏</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">所属角色组</label>
				<div class="col-sm-10">
					<select id="agp_id" name="agp_id" class="form-control">
					<%if (bAdd){%>
						<option value="0" selected>请选择</option>
					<%}else{%>
						<option value="0" <%=infodb.get("agp_id").equals("0")?"selected=\"selected\"":""%> >请选择</option>	
					<%}%>
						<%=Tools.dicopt("admin_agp",Tools.myIsNull(infodb.get("agp_id"))?0:Long.valueOf(infodb.get("agp_id")),"systag=0 AND fsid="+minfo.get("fsid"),"")%>
					</select>
				</div>
			</div><%if (Tools.isSuperAdmin(minfo) || Tools.isCcAdmin(minfo)){%>
			<div class="form-group">
				<label class="col-sm-2 control-label">所属公司</label>
				<div class="col-sm-10">
				<select id="fsid" name="fsid" class="form-control">
					<%if (bAdd){%>
							<option value="0" selected="selected">请选择</option>
							<%=Tools.dicopt("fs",0)%>
					<%}else{%>
							<option value="0" <%=infodb.get("fsid").equals("0")?"selected=\"selected\"":""%> >请选择</option>
							<%=Tools.dicopt("fs",Long.valueOf(infodb.get("fsid")))%>
					<%}%>
				</select>
				</div>
			</div><%}else{%>
				<input type="hidden" id="fsid" name="fsid" value="<%=minfo.get("fsid")%>">
			<%}%>
			
		</div>
	</div>
</div>
<script>
 $(window).on('load', function () {
        // 中文重写select 查询为空提示信息
        $('.selectpicker').selectpicker('refresh');
    });
</script>