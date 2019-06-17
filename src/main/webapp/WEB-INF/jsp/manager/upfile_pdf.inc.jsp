<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tt.tool.Tools" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 演示循环上传文件框，是手机样式的 -->
<%
    int ntotal = Integer.valueOf(request.getParameter("img_Total"));
    String sImgs = request.getParameter("sImgs");
    String[] ssImgs = sImgs.split("\\|");//获取已有图片
    int ii = 0;
    String sFile;
    String sFileDef = request.getParameter("img_DefaultImgSrc");
%>
<%
    if (ntotal <= 1) {
        sFile = ii > ssImgs.length - 1 ? sFileDef : ssImgs[ii];
        if (Tools.myIsNull(sFile)) {
            sFile = sFileDef;
        }
        ii++;
%>
<div style="position: relative;${param.l1div_Style}" id="div_${param.img_NamePre}${i}" class="gallerys">
    　<c:if test="${param.img_MarginImgSrc != null &&  param.img_MarginImgSrc !=''}">
    <img src="${param.img_MarginImgSrc}" alt="" class="${param.img_MarginImgClass}">
</c:if>
    <img id="${param.img_NamePre}_view" name="${param.img_NamePre}_view" src="<%=sFile.equals(sFileDef)?sFileDef:"/manager/images/pdf.png"%>"
         class="${param.img_Class} gallery-pic" style="${param.img_Style}">
    <img id="${param.img_NamePre}_view${i}s" name="${param.img_NamePre}_view${i}s"
         style="float:center;width:12px;height:12px;text-align:center;display:none;"
         src="iframe/dist/img/loading/loading-spinner-grey.gif">
    <%--这个用来展示上传后的缩略图用--%>
    <input type="hidden" id="${param.img_NamePre}" name="${param.img_NamePre}" value="<%=sFile%>">
    <%--这个用来保存上传后的图片url值--%>
    <%--图片上传演示--%>
    <input type="file" id="upload_${param.img_NamePre}" runat="server" name="upload_immm" accept="application/pdf"
           style="${param.img_FileStyle}" class="${param.img_FileClass}">
    <div style="padding-top:20px;"><a onclick="$.openPhotoGallery($('#${param.img_NamePre}_view'));">查看大图</a>|<a href="<%=sFile%>" target="_blank">下载</a></div>
    <script>

//要求必须 jquery2.0 以上,因为 fileup 函数中用到了 .off().on()
$('#upload_${param.img_NamePre}').on('change',function (){
	               beforeup("#${param.img_NamePre}_view${i}");
	fileup('upload_${param.img_NamePre}',function (jo){
		if(typeof(upload_succ_${param.img_NamePre})=='function'){
			upload_succ_${param.img_NamePre}(jo,$('#${param.img_NamePre}'));
		}else{
			jo.url = ""+jo.url;
			$('#${param.img_NamePre}').val(jo.url);
						$('#${param.img_NamePre}_view').attr('src',"/manager/images/pdf.png");
						$('#${param.img_NamePre}_view').parents('div.hide:first').removeClass('hide');
						$('#lbl${param.img_NamePre}').html("上传成功！");
			$('#lbl${param.img_NamePre}').attr("href",jo.url);
					}
					                if (typeof (cloaseuplayer) == 'function') {
                    cloaseuplayer("#${param.img_NamePre}_view${i}");
                }
					});
			})
    </script>
</div>
<%} else {%>
<div class="gallerys">
    <c:forEach var="i" begin="1" end="<%=ntotal%>" step="1">
        <%
            sFile = ii > ssImgs.length - 1 ? sFileDef : ssImgs[ii];
            if (Tools.myIsNull(sFile)) {
                sFile = sFileDef;
            }
            ii++;
        %>
        <div style="position: relative;${param.l1div_Style}" id="div_${param.img_NamePre}${i}">
            　<c:if test="${param.img_MarginImgSrc != null &&  param.img_MarginImgSrc !=''}">
            <img src="${param.img_MarginImgSrc}" alt="" class="${param.img_MarginImgClass}">
        </c:if>
            <img id="${param.img_NamePre}_view${i}" name="${param.img_NamePre}_view${i}" src="<%=sFile.equals(sFileDef)?sFileDef:"/manager/images/pdf.png"%>"
                 class="${param.img_Class} gallery-pic" style="${param.img_Style}">
            <img id="${param.img_NamePre}_view${i}s" name="${param.img_NamePre}_view${i}s"
                 style="float:center;width:12px;height:12px;text-align:center;display:none;"
                 src="iframe/dist/img/loading/loading-spinner-grey.gif">
                <%--这个用来展示上传后的缩略图用--%>
            <input type="hidden" id="${param.img_NamePre}${i}" name="${param.img_NamePre}${i}" value="<%=sFile%>">
                <%--这个用来保存上传后的图片url值--%>
                <%--图片上传演示--%>
            <input type="file" id="upload_${param.img_NamePre}${i}" runat="server" name="upload_immm" accept="application/pdf"
                   style="${param.img_FileStyle}" class="${param.img_FileClass}">
            <div style="padding-top:20px;"><a
                    onclick="$.openPhotoGallery($('#${param.img_NamePre}_view${i}'));">查看大图</a>|<a href="<%=sFile%>" target="_blank">下载</a></div>
            <script>
               
//要求必须 jquery2.0 以上,因为 fileup 函数中用到了 .off().on()
$('#upload_${param.img_NamePre}').on('change',function (){
	               beforeup("#${param.img_NamePre}_view${i}");
	fileup('upload_${param.img_NamePre}',function (jo){
		if(typeof(upload_succ_${param.img_NamePre})=='function'){
			upload_succ_${param.img_NamePre}(jo,$('#${param.img_NamePre}'));
		}else{
			jo.url = jo.url;
			$('#${param.img_NamePre}').val(jo.url);
						$('#${param.img_NamePre}_view').attr('src',"/manager/images/pdf.png");
						$('#${param.img_NamePre}_view').parents('div.hide:first').removeClass('hide');
						$('#lbl${param.img_NamePre}').html("上传成功！");
			$('#lbl${param.img_NamePre}').attr("href",jo.url);
					}
					                if (typeof (cloaseuplayer) == 'function') {
                    cloaseuplayer("#${param.img_NamePre}_view${i}");
                }
					});
			})
            </script>
        </div>
    </c:forEach>
</div>
<%}%>
<script src="iframe/dist/js/app_iframe.js?ver=5"></script>
<script>
    function beforeup(eid) {
        $(eid + "s").css("display", "block");
        $(eid + "s").css("text-align", "center");
        App.setbasePath("iframe/");
        App.setGlobalImgPath("dist/img/");
        App.blockUI({
            target: eid,
            boxed: false,
            cenrerY: true,
            zIndex: 1009,
            iconOnly: true,
            message: '正在上传......'      //,
            //animate: true
        });
    }

    function cloaseuplayer(eid) {
        $(eid + "s").css("display", "none");
        App.unblockUI(eid);//解锁界面
    }
</script>