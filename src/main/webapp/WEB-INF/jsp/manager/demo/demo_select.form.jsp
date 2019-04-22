<%@ page import="com.tt.tool.Tools" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<%-- head.jsp 页面--%>
<head>
    <meta charset="UTF-8">
    <title>车辆状况查询-新建</title>
    <meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover,user-scalable=0">
    <meta name="description"
          content="快加认证专业第三方汽车鉴定评估系统还提供车辆核档、维保记录、风控系统化管理等，为金融公司把控风险，节省成本！快加认证作为专业权威的第三方车辆鉴定评估机构，创新 制订出高于行业规范标准的服务+、规范+、诚信+、专业+、 创新+的“五+”服务体系。为二手车 交易、汽车金融提供最专业的服务。目前全国已有近百家实体 门店相继加盟并开业，区域覆盖上海、北京、武汉、西安、福 建等全国多个重点城市和区域，用心给客户提供细致、周到的 服务。">
    <meta name="keywords" content="快加认证,车辆鉴定,车辆评估,二手车鉴定,二手车评估,福建,上海,福州">
    <script src="js/jQuery-2.1.4.min.js" type="text/javascript"></script><%--这个必须--%>
    <script src="js/jquery.form.js" type="text/javascript"></script><%--这个必须--%>
    <script src="js/common.js" type="text/javascript"></script><%--这个js必须--%>
</head>
<%--selcet，下拉框演示--%>
<%
    //dicopt功能演示，指定表里面的name和id，并用name组成<option></option>
    String s = Tools.dicopt("kjb_user", 3);
    //urlKill删除当前访问的url里面的指定的字段功能，请用demo_select.jsp?dsafdsafd=2323&id=34&cn=7888&page=3&l=4加参数访问,测试此功能
    String surlkill = Tools.urlKill("id");
    //dicopt的使用演示之省市选择功能，请保存测试数据库里有省和城市数据表comm_states和comm_citys表才能看到效果，没有的话从服务器上拉一个下来
    String sp = Tools.dicopt("comm_states", 3);//省会，
    //undic,显示id为16的name值
    String sp2 = Tools.unDic("kjb_user",16);
    String sp3 = Tools.recinfo("select * from kjb_user where id=3").toString();
    //更多用法参考HelloController.java
%>
<select name="mid_add">
    <%=s%>
</select>
<div class="col-sm-4">
    <div class="input-group">
        <span class="input-group-addon">所在城市</span>
        <select name="state_id" id="state_id" class="form-control">
            <%=sp%>
        </select>
    </div>
</div>
<div class="col-sm-4">
    <select name="city_id" id="city_id" class="form-control">
    </select>
</div>
<script>
    /*选择省后，动态获取省下面的市，并默认选中你指定的id的市，/ttAjax在Ajax.java中处理
    /ttAjax也可以单独使用，比如
    /ttAjax?do=opt&cn=kjb_user&id=3&mid_add=100000 //显示创建人id为100000的所有用户，默认选择id为3的记录
    * */
    objacl('#state_id', '#city_id', '/ttAjax?do=opt&cn=comm_citys&id=3&state_id=', '33', '198');
</script>
<%=surlkill%>
<br>========================<br>
<%=sp2%>
<br>========================<br>
<%=sp3%>
</body>
</html>

