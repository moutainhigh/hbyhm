<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<head>
  <script src="js/jQueryRotate.2.2.js" type="text/javascript"></script>
</head>
<div class="admin-content nav-tabs-custom box">
  <div class="box-header with-border">

    <input type="hidden" name="adminop_tag" value="0">
    <div class="box-body">
      <div class="box-header with-border">
        <h3 class="box-title">商户端车辆查询审核</h3>
      </div>
      <div class="box-body">
        <div class="form-group">
          <label class="col-sm-2 control-label">其他审核信息</label>
          <div class="col-sm-10">
            <div class="row inline-from">
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">查询类型</span>
                  <input type="text" class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">保险单号</span>
                  <input class="form-control" name="po_no" id="po_no" readonly>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">车辆品牌</span>
                  <input class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">车系</span>
                  <input class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">车款</span>
                  <input class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">上牌时间</span>
                  <input class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">行驶公里</span>
                  <input class="form-control" readonly value="">
                  <span class="input-group-addon">万KM</span>
                </div>
              </div>
              <div class="col-sm-3">
                <div class="input-group">
                  <span class="input-group-addon">车身颜色</span>
                  <input class="form-control" readonly value="">
                </div>
              </div>
              <div class="col-sm-12">
                <div class="input-group">
                  <span class="input-group-addon">驾驶证档案编号</span>
                  <input class="form-control" id="jszid" name="jszid" onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();">
                  <span class="input-group-addon">
                    <a href="javascript:dokfapi(1);">API扣分</a>
                    |
                    <a href="javascript:dowzapi(1);">API违章</a>
                    |
                    <a href="javascript:dovinapi(1);">API VIN</a>
                    |
                    <a href="javascript:doocr(0);">OCR行驶证</a>
                    |
                    <a href="javascript:doocr(1);">OCR驾驶证</a>
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="box-header with-border">
          <h3 class="box-title">事故维修查询API</h3>
        </div>
        <div class="box-body">
          <div class="form-group">
            <label class="col-sm-2 control-label">车辆验证查询API</label>
            <div class="col-sm-10">
              <div class="row inline-from">
                <div class="col-sm-3">
                  <div class="input-group">
                    <span class="input-group-addon">车牌</span>
                    <input type="text" class="form-control" onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();"
                      id="c_carno">
                  </div>
                </div>
                <div class="col-sm-3">
                  <div class="input-group">
                    <span class="input-group-addon">车主姓名</span>
                    <input type="text" class="form-control" onblur="this.value=this.value.toUpperCase();this.value=this.value.trim();"
                      id="c_name">
                  </div>
                </div>
                <div class="col-sm-3">
                  <div class="input-group">
                    <span class="input-group-addon">验证结果</span>
                    <input type="text" class="form-control" readonly id="syz1" name='syz1'>
                  </div>
                </div>
                <div class="col-sm-3">
                  <div class="input-group">
                    <span class="input-group-addon">
                      <a href="javascript:domscapi(1);">点击查询</a>
                    </span>
                    <input id="cbs_orderid__" name="cbs_orderid__" type="text" readonly value="点击后等结果,勿重复点" class="form-control">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">瓜子维保查询功能</label>
            <div class="col-sm-10">
              <div class="input-group">
                <span class="input-group-addon">
                  <a href="javascript:docbsapi(1);">点击查询</a>
                </span>
                <input id="cbs_orderid__" name="cbs_orderid__" type="text" readonly value="点击查询后等待出结果，有可能需要等待30-60秒，勿重复点击。如提示「数据正在准备中，请稍候」,那么等1，2分钟重新点击下！"
                  class="form-control">
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">查博士API功能区</label>
            <div class="col-sm-10">
              <div class="input-group">
                <span class="input-group-addon">
                  <a href="javascript:docbsapi(1);">API_可查？</a>
                  |
                  <a href="javascript:docbsapi(2);">提交查询 </a>
                  订单号
                </span>
                <input id="cbs_orderid" name="cbs_orderid" type="text" readonly class="form-control">
                <span class="input-group-addon">
                  <a href="javascript:docbsapi(3);">查询进度</a>
                  |
                  <a href="javascript:docbsapi(4);">获取报告</a>
                </span>
              </div>
            </div>
          </div>
        </div>

        <div class="box-header with-border">
          <h3 class="box-title">查询结果</h3>
        </div>
        <div class="box-body">
          <div class="form-group">
            <label class="col-sm-2 control-label">车辆信息和状态</label>
            <div class="col-sm-10">
              <div class="row inline-from">
                <div class="col-sm-4">
                  <div class="input-group">
                    <span class="input-group-addon">sssssssss</span>
                    <select name="" id="" class="form-control">
                      <option value="111">11111111</option>
                      <option value="111">11111111</option>
                      <option value="111">11111111</option>
                    </select>
                  </div>
                                    <% String 
                upFile = "../upfile.inc.jsp";
                String[] ssImgs = { //设置已有值
                    "/upload/2019/01/11/9a747d32c6807b3b16e352539a47b946.jpg"
                    };
                String sImgs = "";                    
                for (int i =0 ;i<ssImgs.length;i++){
                    sImgs=sImgs+ssImgs[i]+"|";
                }
            %>
                <jsp:include page="<%=upFile%>">
                <jsp:param name="img_MarginImgSrc" value=""/>
                <jsp:param name="img_MarginImgClass" value=""/>
                <jsp:param name="img_Total" value="1"/>
                <jsp:param name="img_NamePre" value="imgurl"/>
                <jsp:param name="img_DefaultImgSrc" value="images/mgcaraddimg.jpg"/>
                <jsp:param name="l1div_Style" value="width: 100px;height:140px;display: inline-block;text-align: center;margin: auto;"/>
                <jsp:param name="img_Style" value="width: 100%;height:100px;border-radius:10px;"/>
                <jsp:param name="img_FileStyle" value="position: absolute;left: 0;top: 0;height: 100%;width: 100%;background: transparent;border: 0;margin: 0;padding: 0;filter: alpha(opacity=0);-moz-opacity: 0;-khtml-opacity: 0;opacity: 0;"/>
                <jsp:param name="img_Class" value="imgclass"/>
                <jsp:param name="img_FileClass" value="uploadfileclass"/>
                <jsp:param name="img_SmallWidth" value="100"/>
                <jsp:param name="img_SmallHeight" value="100"/>
                <jsp:param name="sImgs" value="<%=sImgs%>"/>
                </jsp:include>
                </div>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-2 control-label">保养施工信息：</label>
            <div class="col-sm-10">
              <textarea style="width: 100%; height: 100px" class="form-control" name="wxbyxx_item1" id="wxbyxx_item1"></textarea>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">留言备注说明：</label>
          <div class="col-sm-10">
            <div class="row inline-from">
              <div class="col-sm-4">
                <div class="input-group">
                  <span class="input-group-addon">审核状态</span>
                  <select name="bc_status" class="form-control" id="bc_status" onchange="autoremark();">
                   
                  </select>
                </div>
              </div>
              <div class="col-sm-4">
                <div class="input-group">
                  <span class="input-group-addon">审核留言</span>
                  <input type="text" class="form-control" name="remark" id="remark" value="">
                </div>
              </div>
              <div class="col-sm-4">
                <div class="input-group">
                  <span class="input-group-addon">常用留言快速通道</span>
                  <select class="form-control" id="cyly" onchange="setremark(this)">
                    <option value="xxxx">xxxxxxxxxx
                    </option>
                  </select>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">历次审核事件和留言：</label>
          <div class="col-sm-10">
            <textarea style="width: 100%; height: 100px" class="form-control" readonly></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">API 查询结果：</label>
          <div class="col-sm-4">
            <textarea style="width: 100%; height: 30px" class="form-control" name="textvin" id="textvin"></textarea>
          </div>
          <label class="col-sm-2 control-label">API 询违章查结果：</label>
          <div class="col-sm-4">
            <textarea style="width: 100%; height: 30px" class="form-control" name="textwz" id="textwz"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">API 询扣分查结果：</label>
          <div class="col-sm-4">
            <textarea style="width: 100%; height: 30px" class="form-control" name="textkf" id="textkf"></textarea>
          </div>
          <label class="col-sm-2 control-label">OCR行驶证：</label>
          <div class="col-sm-4">
            <textarea style="width: 100%; height: 30px" class="form-control" name="textxsz" id="textxsz"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">OCR驾驶证：</label>
          <div class="col-sm-4">
            <textarea style="width: 100%; height: 30px" class="form-control" name="textjsz" id="textjsz"></textarea>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">查博士结果：</label>
          <div class="col-sm-10">
            <textarea style="width: 100%; height: 50px" class="form-control" name="cbstext" id="cbstext"></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
</script>