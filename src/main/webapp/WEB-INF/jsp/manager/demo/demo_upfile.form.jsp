<%--
 * @Description: file content
 * @Author: tt
 * @Date: 2018-11-30 16:44:58
 * @LastEditTime: 2019-01-13 20:32:55
 * @LastEditors: tt
 **/
--%>
 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page import="com.tt.tool.DataDic" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<body class="am-with-fixed-header">
		<div class="box-body">
			<div class="form-group">
				<label class="col-sm-2 control-label">select组件</label>
				<div class="col-sm-10">
					<div class="row inline-from">
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">性别</span>
							<select id="" name="" class="form-control">
							<%=Tools.dicopt(DataDic.dicSex,"1")%>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">是否</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dicYesOrNo,"1")%>
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">业务等级</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dicBsType,"0")%>	
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">征信审核状态</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dic_zx_status,"0")%>
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">通融状态</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dic_tr_status,"2")%>	
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">通融状态</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dic_tr_status,"2")%>	
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">其他</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt(DataDic.dic_zx1_tag,"0")%>		
								</select>	
							</select>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="input-group">
								<span class="input-group-addon">其他</span>
								<select id="" name="" class="form-control">
									<%=Tools.dicopt("admin",33,"name","")%>	
								</select>	
							</select>
							</div>
						</div>
					<%--selcet，下拉框演示，支持搜索的 class="selectpicker form-control" multiple data-live-search="true" data-max-options="1" onchange="$('#city_id').selectpicker('refresh');"--%>
					<%
						//dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
						String sp = Tools.dicopt("comm_states", 0);//省会，
					%>
					<div class="col-sm-6">
						<div class="input-group">
							<span class="input-group-addon">省</span>
							<select name="state_id" id="state_id"  class="selectpicker form-control" multiple data-live-search="true" data-max-options="1">
								<option value="0">请选择</option>
								<%=sp%>
							</select>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="input-group">
							<span class="input-group-addon">市</span>
							<select name="city_id" id="city_id" class="selectpicker  form-control" multiple data-live-search="true" data-max-options="1" onchange="$('#city_id').selectpicker('refresh');">
								<option value="0">请选择</option>
							</select>
						</div>
					</div>
			<script>
				/*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
														/ttAjax也可以单独使用，比如
														/ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
														* */
				objacl('#state_id', '#city_id', '/ttAjax?do=opt&cn=comm_citys&id=3&state_id=', '${infodb.state_id}', '${infodb.city_id}');
				$('.selectpicker').selectpicker({
					language: 'zh_CN',
					// 设置下拉方向始终向下
					dropupAuto: false,
					size: 12
				});

			</script>
      <%=Tools.htmlDate("dt_add","","2019-03-02","6")%>
			<form id="myform" action="xxxx提交保存数据的url"
      class="uniform" method="post" enctype="multipart/form-data">
            <% String 
                upFile = "../upfile.inc.jsp";
                String[] ssImgs = { //设置已有值
                   // "/upload/2019/01/11/9a747d32c6807b3b16e352539a47b946.jpg","/upload/2019/01/11/5711c668544730bbef922176ffa26c23.jpg","/upload/2019/01/11/c5475cb485f6e60f373a3ae753c2aefc.jpg",
                    //"/upload/2019/01/11/e4ca4881b66691388a9ede3aa4b4b26d.jpg","/upload/2019/01/11/dbcacf426335dd9c0d3c1159ece72d31.jpg","/upload/2019/01/11/c45f826bb2a7272cb63dd18b48a054db.jpg",
                    //"/upload/2019/01/11/9a747d32c6807b3b16e352539a47b946.jpg","/upload/2019/01/11/c5475cb485f6e60f373a3ae753c2aefc.jpg"
                    };
                String sImgs = "";              
                if (ssImgs.length>0){
                    for (int i =0 ;i<ssImgs.length;i++){
                        sImgs=sImgs+ssImgs[i]+"|";
                    }
                }
            %>
            <%-- 
                img_MarginImgSrc外层背景
                img_MarginImgClass为外层背景CSS
                img_Total 图片总数
                img_NamePre 生成的input/img等组件的id和name。
                img_DefaultImgSrc 无图时显示的默认的图图
                l1div_Style 外层div的style
                img_Style 上传效果图片img的img的style
                img_FileStyle input type=file的style
                img_SmallWidth，img_SmallHeight 生成缩略图的宽和高
                sImgs 已有图片列表，按顺序一一对应
             --%>
                <jsp:include page="<%=upFile%>">
                <jsp:param name="img_MarginImgSrc" value=""/>
                <jsp:param name="img_MarginImgClass" value=""/>
                <jsp:param name="img_Total" value="8"/>
                <jsp:param name="img_NamePre" value="imgurl"/>
                <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                <jsp:param name="l1div_Style" value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;"/>
                <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                <jsp:param name="img_FileStyle" value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                <jsp:param name="img_Class" value="imgclass"/>
                <jsp:param name="img_FileClass" value="uploadfileclass"/>
                <jsp:param name="img_SmallWidth" value="100"/>
                <jsp:param name="img_SmallHeight" value="100"/>
                <jsp:param name="sImgs" value="<%=sImgs%>"/>
                </jsp:include>
						</form>
					</div>
				</div>
	</div>
	<%--材料照片--%>
            <div class="form-group">
                <label class="col-sm-2 control-label">材料照片</label>
                <div class="col-sm-10">
                    <div class="row inline-from" id="imgs_div">
                        <%
                            String upFile1 = "../upfiles.inc.jsp";
                            String imgName = "imgstep3_1,imgstep3_2,imgstep3_3,imgstep3_4,imgstep3_5,imgstep3_6,imgstep3_7,imgstep3_8,imgstep3_9";
                            String imgNames_title = "身份证正面, 身份证反面, 面签照片, 借款人资料1, 借款人资料2, 信用卡申请1, 信用卡申请2, 个人税收声明, 电话调查申请";
                            String[] imgNames= imgName.split(",");
                                for(int s = 0; s < imgNames.length; s++){
                                String[] ssImgs1 = { //设置已有值
                                       ""
                                };
                                ssImgs1 = ssImgs1[0].split(",");
                                String sImgs1 = "";
                                for (int i = 0; i < ssImgs1.length; i++) {
                                    if (ssImgs1[i] != null && !ssImgs1[i].equals("")) {
                                        sImgs1 = sImgs1 + ssImgs1[i] + "|";
                                    }
                                }
                                }
                        %>
                        <%--&lt;%&ndash; 可能这里用<%@include file %>模式更适合&ndash;%&gt;--%>
                        <jsp:include page="<%=upFile1%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="9"/>
                            <jsp:param name="img_NamePre" value="imgName"/>
                            <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                            <jsp:param name="l1div_Style"
                                       value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;"/>
                            <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                            <jsp:param name="img_FileStyle"
                                       value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                            <jsp:param name="img_Class" value="imgclass"/>
                            <jsp:param name="img_FileClass" value="uploadfileclass"/>
                            <jsp:param name="img_SmallWidth" value="100"/>
                            <jsp:param name="img_SmallHeight" value="100"/>
                            <jsp:param name="sImgs" value=""/>
                            <jsp:param name="img_title" value="<%=imgNames_title%>"/>
                        </jsp:include>
                        <%
                            String upFile2 = "../upfile.inc.jsp";
                            String[] ssImgs2 = { //设置已有值
                                    ""
                            };
                            //System.out.println("收拾收拾"+ssImgs2[0]);
                            ssImgs2 = ssImgs2[0].split(",");
                            int num=0;
                            if(ssImgs2.length>0){
                                num=ssImgs2.length;
                            }
                            String sImgs2 = "";
                            for (int i = 0; i < ssImgs2.length; i++) {
                                if (ssImgs2[i] != null && !ssImgs2[i].equals("")) {
                                    sImgs2 = sImgs2 + ssImgs2[i] + "|";
                                }
                            }
                        %>
                        <%--&lt;%&ndash; 可能这里用<%@include file %>模式更适合&ndash;%&gt;--%>
                        <jsp:include page="<%=upFile2%>">
                            <jsp:param name="img_MarginImgSrc" value=""/>
                            <jsp:param name="img_MarginImgClass" value=""/>
                            <jsp:param name="img_Total" value="<%=num%>"/>
                            <jsp:param name="img_NamePre" value="imgstep3_1s"/>
                            <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                            <jsp:param name="l1div_Style"
                                       value="width: 120px;height:120px;display: inline-block;text-align: center;margin: auto;"/>
                            <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                            <jsp:param name="img_FileStyle"
                                       value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                            <jsp:param name="img_Class" value="imgclass"/>
                            <jsp:param name="img_FileClass" value="uploadfileclass"/>
                            <jsp:param name="img_SmallWidth" value="100"/>
                            <jsp:param name="img_SmallHeight" value="100"/>
                            <jsp:param name="sImgs" value="<%=sImgs2%>"/>
                            <jsp:param name="img_title" value="补充材料"/>
                        </jsp:include>
                        <div style="position: relative;width: 100px;height:140px;display: inline-block;text-align: center;margin: auto;"
                             id="div_imgstep3_1s">
                            <img id="imgstep3_1s_view" onclick="addimgFileup('imgstep3_1s');" name="imgstep3_1s_view" src="images/mgcaraddimg.jpg"
                                 class="imgclass gallery-pic" style="width: 100%;height:100px;border-radius:10px;">
                            <img onclick="addimgFileup('imgstep3_1s');" id="imgstep3_1s_views" name="imgstep3_1s_views"
                                 style="float:center;width:12px;height:12px;text-align:center;display:none;"
                                 src="iframe/dist/img/loading/loading-spinner-grey.gif">
                            <input id="imgstep3_1s" name="imgstep3_1s" value="0" type="hidden"/>
                            <%-- <input type="hidden" id="imgstep3_1" name="imgstep3_1" value="images/mgcaraddimg.jpg">
                            <input type="file" id="upload_imgstep3_1" runat="server" name="upload_immm" accept="image/*" style="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;" class="uploadfileclass">--%>
                            <div style="padding-top:20px;"><a href="javascript:addimgFileup('imgstep3_1s');">补充材料</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>		
</div>
<script>
$('.form_datetime').datetimepicker({
        weekStart: 0, //一周从哪一天开始
        minView: "month",//设置只显示到月份
        todayBtn:  1, //
        autoclose: 1, //当选择一个日期之后是否立即关闭此日期时间选择器。
        todayHighlight: 1, //当天日期高亮
        startView: 2, //0 or 'hour' 为小时视图，1 or 'day' 为天视图，2 or 'month' 为月视图（为默认值），3 or 'year'  为年视图，4 or 'decade' 为十年视图
        forceParse: 0, //当选择器关闭的时候，是否强制解析输入框中的值。
        showMeridian: 1
    });
</script>
<script>
$(window).on('load', function () {
        // 中文重写select 查询为空提示信息
				$('.selectpicker').selectpicker('refresh');
});
</script>