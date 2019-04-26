/*
 * 内置简单型数据字典，在项目启动时调用initDic();初始化字典
 * @Description: file content
 * @Author: tt
 * @LastEditors: tt
 * @Date: 2019-03-15 16:23:06
 * @LastEditTime: 2019-03-16 09:44:06
 */
package com.tt.tool;

import com.tt.data.TtMap;

/**
 * 一些常用的数据字典，作为<select></select>用
 */
public class DataDic {
    public static TtMap dicYesOrNo = new TtMap();
    public static TtMap dicSex = new TtMap();
    public static TtMap dicBsType = new TtMap();
    public static TtMap dic_zx_status = new TtMap();
    public static TtMap dic_tr_status = new TtMap();
    public static TtMap dic_zx1_tag = new TtMap();
    public static TtMap dic_cars_type = new TtMap();
    public static TtMap dic_aj_date = new TtMap();
    public static TtMap dic_dz_type = new TtMap();
    public static TtMap dic_hyzk1 = new TtMap();
    public static TtMap dic_hyzk2 = new TtMap();
    public static TtMap dic_xl = new TtMap();
    public static TtMap dic_jzzk = new TtMap();
    public static TtMap dic_dwxz = new TtMap();
    public static TtMap dic_sshy = new TtMap();
    public static TtMap dic_zy = new TtMap();
    public static TtMap dic_zw = new TtMap();
    public static TtMap dic_kk_status = new TtMap();
    public static TtMap dic_cars_source = new TtMap();
    public static TtMap dic_cars_property = new TtMap();
    public static TtMap dic_car_status = new TtMap();
    public static TtMap dic_car_gear_box = new TtMap();
    public static TtMap dic_car_color = new TtMap();
    public static TtMap dic_pro_type = new TtMap();
    public static TtMap dic_ywly = new TtMap();
    public static TtMap dic_zzcl_xl = new TtMap();
    public static TtMap dic_zzcl_hyzk = new TtMap();
    public static TtMap dic_zzcl_jz = new TtMap();
    public static TtMap dic_zzcl_gzxz = new TtMap();
    public static TtMap dic_zzcl_dwxz = new TtMap();
    public static TtMap dic_zzcl_gzzc = new TtMap();
    public static TtMap dic_zzcl_sshy = new TtMap();
    public static TtMap dic_zzcl_sb = new TtMap();
    public static TtMap dic_zzcl_zyly = new TtMap();
    public static TtMap dic_zzcl_zczm = new TtMap();
    public static TtMap dic_zzcl_cllx = new TtMap();
    public static TtMap dic_zzcl_gp = new TtMap();
    public static TtMap dic_zzcl_gcyt = new TtMap();
    public static TtMap dic_zzcl_dkqs = new TtMap();
    public static TtMap dic_zzcl_fq = new TtMap();
    public static TtMap dic_zzcl_zdrgx = new TtMap();
    public static TtMap dic_zzcl_dzlx = new TtMap();
    public static TtMap dic_qccl_cllx = new TtMap();
    public static TtMap dic_clhs_cllx = new TtMap();
    public static TtMap dic_gsclhs_cllx = new TtMap();
    public static TtMap dic_yhclhs_cllx = new TtMap();
    public static TtMap dic_dyclhs_cllx = new TtMap();
    public static TtMap dic_modal_tag = new TtMap();
    public static TtMap dic_fs_type = new TtMap();
    public static TtMap dic_tlzf_qystatus = new TtMap();
    public static TtMap dic_tlzf_bank_code = new TtMap();
    public static TtMap dic_tlzf_account_type = new TtMap();
    public static TtMap dic_tlzf_cardid_type = new TtMap();
    public static TtMap dic_tlzf_ds_bc_status = new TtMap();
    public static TtMap dic_tlzf_sd_status = new TtMap();
    public static TtMap dic_tlzf_xy_status = new TtMap();
    public static TtMap dic_app = new TtMap();
    public static TtMap dic_zzcl_zvzk = new TtMap();
    public static synchronized void initDic() {
        if (dicYesOrNo.size() > 0) { // 已经初始化过了
            return;
        }
        /*资质材料-子女状况*/
        dic_zzcl_zvzk.put("0","请选择");
        dic_zzcl_zvzk.put("1","无");
        dic_zzcl_zvzk.put("2","一个");
        dic_zzcl_zvzk.put("3","两个");
        dic_zzcl_zvzk.put("4","两个以上");
        /*类型*/
        dic_app.put("0","工行贷");
        dic_app.put("1","通用进件");
        dic_app.put("2","河北银行");
        dic_app.put("3","厦门国际");
        dic_app.put("4","华夏银行");
        /*通联支付协议状态*/
        dic_tlzf_xy_status.put("0","");
        dic_tlzf_xy_status.put("1","失效");
        dic_tlzf_xy_status.put("2","有效");
        /*通联支付手动状态*/
        dic_tlzf_sd_status.put("1","待审核");
        dic_tlzf_sd_status.put("2","代收成功");
        dic_tlzf_sd_status.put("3","代收失败");
        /*通联支付交易状态*/
        dic_tlzf_ds_bc_status.put("1", "提交待查询");
        dic_tlzf_ds_bc_status.put("2", "交易失败");
        dic_tlzf_ds_bc_status.put("3", "交易成功");
        /*证件类型*/
        dic_tlzf_cardid_type.put("0", "身份证");
        dic_tlzf_cardid_type.put("1", "户口簿");
        dic_tlzf_cardid_type.put("2", "护照");
        dic_tlzf_cardid_type.put("3", "军官证");
        dic_tlzf_cardid_type.put("4", "士兵证");
        dic_tlzf_cardid_type.put("5", "港澳居民来往内地通行证");
        dic_tlzf_cardid_type.put("6", "台湾同胞来往内地通行证");
        dic_tlzf_cardid_type.put("7", "临时身份证");
        dic_tlzf_cardid_type.put("8", "外国人居留证");
        dic_tlzf_cardid_type.put("9", "警官证");
        dic_tlzf_cardid_type.put("X", "其他证件");
        /*账户类型*/
        dic_tlzf_account_type.put("00", "借记卡");
        dic_tlzf_account_type.put("02", "信用卡");
        /*银行代码*/
        dic_tlzf_bank_code.put("0102", "中国工商银行");
        dic_tlzf_bank_code.put("0103", "中国农业银行");
        dic_tlzf_bank_code.put("0104", "中国银行");
        dic_tlzf_bank_code.put("0105", "中国建设银行");
        dic_tlzf_bank_code.put("0301", "交通银行");
        dic_tlzf_bank_code.put("0308", "招商银行");
        /*通联支付签约状态*/
        dic_tlzf_qystatus.put("0", "草稿");
        dic_tlzf_qystatus.put("1", "草稿箱");
        dic_tlzf_qystatus.put("2", "审核中");
        dic_tlzf_qystatus.put("3", "通过");
        dic_tlzf_qystatus.put("4", "不通过");
        dic_tlzf_qystatus.put("5", "回退补件");
        dic_tlzf_qystatus.put("6", "过件待补");
        dic_tlzf_qystatus.put("7", "解约完成");
        /*公司类型*/
        dic_fs_type.put("2", "icbc工行贷");
        dic_fs_type.put("0", "快加认证");
        dic_fs_type.put("1", "快加商户端");
        /*modal-标记*/
        dic_modal_tag.put("0", "请选择");
        dic_modal_tag.put("1", "进度");
        /*材料回收-抵押材料类型*/
        dic_dyclhs_cllx.put("0", "请选择");
        dic_dyclhs_cllx.put("1", "补给材料");
        dic_dyclhs_cllx.put("2", "其他材料");
        /*材料回收-银行材料类型*/
        dic_yhclhs_cllx.put("0", "请选择");
        dic_yhclhs_cllx.put("1", "征信授权书");
        dic_yhclhs_cllx.put("2", "其他材料");
        /*材料回收-公司材料类型*/
        dic_gsclhs_cllx.put("0", "请选择");
        dic_gsclhs_cllx.put("1", "全部材料");
        dic_gsclhs_cllx.put("2", "其他材料");
        /*汽车材料-车辆类型*/
        dic_qccl_cllx.put("0", "请选择");
        dic_qccl_cllx.put("1", "新车");
        dic_qccl_cllx.put("2", "二手车");
        /*资质材料-垫资类型*/
        dic_zzcl_dzlx.put("0", "请选择");
        dic_zzcl_dzlx.put("1", "不垫资");
        dic_zzcl_dzlx.put("2", "提车垫资");
        /*资质材料-与主贷人关系*/
        dic_zzcl_zdrgx.put("0", "请选择");
        dic_zzcl_zdrgx.put("1", "父母");
        dic_zzcl_zdrgx.put("2", "配偶");
        dic_zzcl_zdrgx.put("3", "直系亲属");
        dic_zzcl_zdrgx.put("4", "朋友");
        dic_zzcl_zdrgx.put("5", "同事");
        /*资质材料-服务费是否分期*/
        dic_zzcl_fq.put("0", "请选择");
        dic_zzcl_fq.put("1", "是");
        dic_zzcl_fq.put("2", "否");
        /*资质材料-贷款期数（月）*/
        dic_zzcl_dkqs.put("0", "请选择");
        dic_zzcl_dkqs.put("1", "12");
        dic_zzcl_dkqs.put("2", "24");
        dic_zzcl_dkqs.put("3", "36");
        /*资质材料-购车用途*/
        dic_zzcl_gcyt.put("0", "请选择");
        dic_zzcl_gcyt.put("1", "自用");
        dic_zzcl_gcyt.put("2", "商用");
        /*资质材料-是否公牌*/
        dic_zzcl_gp.put("0", "请选择");
        dic_zzcl_gp.put("1", "是");
        dic_zzcl_gp.put("2", "否");
        /*资质材料-车辆类型*/
        dic_zzcl_cllx.put("0", "请选择");
        dic_zzcl_cllx.put("1", "国产");
        dic_zzcl_cllx.put("2", "进口");
        /*资质材料-是否提供资产证明*/
        dic_zzcl_zczm.put("0", "请选择");
        dic_zzcl_zczm.put("1", "是");
        dic_zzcl_zczm.put("2", "否");
        /*资质材料-主要收入来源*/
        dic_zzcl_zyly.put("0", "请选择");
        dic_zzcl_zyly.put("1", "工资");
        dic_zzcl_zyly.put("2", "经营/租赁所得");
        dic_zzcl_zyly.put("3", "投资/佣金");
        dic_zzcl_zyly.put("4", "其他");
        dic_zzcl_zyly.put("5", "无");
        /*资质材料-是否缴纳社保*/
        dic_zzcl_sb.put("0", "请选择");
        dic_zzcl_sb.put("1", "是");
        dic_zzcl_sb.put("2", "否");
        /*资质材料-所属行业*/
        dic_zzcl_sshy.put("0", "请选择");
        dic_zzcl_sshy.put("1", "国家机关/党群组织/企业/事业单位负责人");
        dic_zzcl_sshy.put("2", "专业技术人员");
        dic_zzcl_sshy.put("3", "办事人员及有关人员");
        dic_zzcl_sshy.put("4", "商业/服务业人员");
        dic_zzcl_sshy.put("5", "农/林/牧/渔/水利业生产人员");
        dic_zzcl_sshy.put("6", "生产/运输设备操作人员及有关人员");
        dic_zzcl_sshy.put("7", "军人");
        dic_zzcl_sshy.put("8", "不便分类的其他从业人员");
        dic_zzcl_sshy.put("9", "未知");
        /*资质材料-工作职称*/
        dic_zzcl_gzzc.put("0", "请选择");
        dic_zzcl_gzzc.put("1", "高层领导");
        dic_zzcl_gzzc.put("2", "中级领导");
        dic_zzcl_gzzc.put("3", "一般员工");
        dic_zzcl_gzzc.put("4", "其他");
        dic_zzcl_gzzc.put("5", "未知");
        /*资质材料-单位性质*/
        dic_zzcl_dwxz.put("0", "请选择");
        dic_zzcl_dwxz.put("1", "国有企业");
        dic_zzcl_dwxz.put("2", "民营企业");
        dic_zzcl_dwxz.put("3", "个体工商户");
        dic_zzcl_dwxz.put("4", "其他");
        dic_zzcl_dwxz.put("5", "国家机关");
        dic_zzcl_dwxz.put("6", "事业单位");
        /*资质材料-工作性质*/
        dic_zzcl_gzxz.put("0", "请选择");
        dic_zzcl_gzxz.put("1", "工薪阶层");
        dic_zzcl_gzxz.put("2", "农民");
        dic_zzcl_gzxz.put("3", "个体工商户");
        dic_zzcl_gzxz.put("4", "个体工商户");
        dic_zzcl_gzxz.put("5", "自由职业");
        dic_zzcl_gzxz.put("6", "退休");
        /*资质材料-是否有驾照*/
        dic_zzcl_jz.put("0", "请选择");
        dic_zzcl_jz.put("1", "是");
        dic_zzcl_jz.put("2", "否");

        /*资质材料-婚姻状况*/
        dic_zzcl_hyzk.put("0", "请选择");
        dic_zzcl_hyzk.put("1", "已婚有子女");
        dic_zzcl_hyzk.put("2", "已婚无子女");
        dic_zzcl_hyzk.put("3", "已婚");
        dic_zzcl_hyzk.put("4", "未婚单身");
        dic_zzcl_hyzk.put("5", "离异单身");
        dic_zzcl_hyzk.put("6", "丧偶单身");
        /*资质材料-学历*/
        dic_zzcl_xl.put("0", "请选择");
        dic_zzcl_xl.put("1", "初中以下");
        dic_zzcl_xl.put("2", "高中及中专");
        dic_zzcl_xl.put("3", "大专");
        dic_zzcl_xl.put("4", "本科");
        dic_zzcl_xl.put("5", "硕士及以上");
        dic_zzcl_xl.put("6", "其他");
        /*业务来源*/
        dic_ywly.put("0", "请选择");
        dic_ywly.put("1", "公司");
        dic_ywly.put("2", "自有");
        dic_ywly.put("3", "经销商");
        dic_ywly.put("4", "保险公司");
        dic_ywly.put("5", "其他");
        /*产品类型*/
        dic_pro_type.put("0", "请选择");
        dic_pro_type.put("1", "免家访");
        dic_pro_type.put("2", "家访");
        /* 是/否 */
        dicYesOrNo.put("0", "否");
        dicYesOrNo.put("1", "是");
        /* 性别 */
        dicSex.put("0", "女");
        dicSex.put("1", "男");
        /* 业务等级 */
        dicBsType.put("0", "请选择业务等级");
        dicBsType.put("1", "预期贷款额10万以下（含10万）");
        dicBsType.put("2", "预期贷款额10万以上");
        /* 征信审核状态 */
        dic_zx_status.put("0", "请选择");
        dic_zx_status.put("1", "草稿箱");
        dic_zx_status.put("2", "审核中");
        dic_zx_status.put("3", "通过");
        dic_zx_status.put("4", "不通过");
        dic_zx_status.put("5", "回退补件");
        dic_zx_status.put("6", "过件待补");
        /* 通融审核状态 */
        dic_tr_status.put("0", "请选择");
        dic_tr_status.put("1", "提交通融信息");
        dic_tr_status.put("2", "通融不通过");
        dic_tr_status.put("3", "通融通过");
        dic_tr_status.put("4", "通融回退补件");
        /* 征信是否通过状态 */
        dic_zx1_tag.put("0", "请选择");
        dic_zx1_tag.put("1", "通过");
        dic_zx1_tag.put("2", "不通过");
        /*车辆类型*/
        dic_cars_type.put("0", "请选择");
        dic_cars_type.put("1", "新车");
        dic_cars_type.put("2", "二手车");
        /*按揭期限*/
        dic_aj_date.put("0", "请选择");
        dic_aj_date.put("24", "24期");
        dic_aj_date.put("36", "36期");
        /*垫资类型*/
        dic_dz_type.put("0", "请选择");
        dic_dz_type.put("1", "不垫资");
        dic_dz_type.put("2", "提车垫资");
        /*婚姻状况1*/
        dic_hyzk1.put("0", "请选择");
        dic_hyzk1.put("1", "未婚");
        dic_hyzk1.put("2", "已婚");
        dic_hyzk1.put("3", "离异");
        dic_hyzk1.put("4", "丧偶");
        /*婚姻状况2*/
        dic_hyzk2.put("0", "请选择");
        dic_hyzk2.put("1", "已婚有子女");
        dic_hyzk2.put("2", "已婚无子女");
        dic_hyzk2.put("3", "未婚单身");
        dic_hyzk2.put("4", "离异单身");
        dic_hyzk2.put("5", "丧偶单身");
        /*最高学历*/
        dic_xl.put("0", "请选择");
        dic_xl.put("1", "小学");
        dic_xl.put("2", "初中");
        dic_xl.put("3", "高中");
        dic_xl.put("4", "大专");
        dic_xl.put("5", "本科");
        dic_xl.put("6", "硕士");
        dic_xl.put("7", "博士及以上");
        /*居住状况*/
        dic_jzzk.put("0", "请选择");
        dic_jzzk.put("1", "自有住房");
        dic_jzzk.put("2", "租房");
        dic_jzzk.put("3", "分期付款购房");
        dic_jzzk.put("4", "集体宿舍");
        dic_jzzk.put("5", "其他");
        /*单位性质*/
        dic_dwxz.put("0", "请选择");
        dic_dwxz.put("1", "国有");
        dic_dwxz.put("2", "私营");
        dic_dwxz.put("3", "民营");
        dic_dwxz.put("4", "股份合作");
        dic_dwxz.put("5", "其他股份制");
        dic_dwxz.put("6", "个体");
        dic_dwxz.put("7", "三资");
        dic_dwxz.put("8", "其他");
        /*所属行业*/
        dic_sshy.put("0", "请选择");
        dic_sshy.put("1", "农林牧渔");
        dic_sshy.put("2", "邮电通讯");
        dic_sshy.put("3", "房地产");
        dic_sshy.put("4", "科教文卫");
        dic_sshy.put("5", "工业");
        dic_sshy.put("6", "银行");
        dic_sshy.put("7", "证券");
        dic_sshy.put("8", "保险");
        dic_sshy.put("9", "商业");
        dic_sshy.put("10", "机关团体");
        dic_sshy.put("11", "其他");
        /*职业*/
        dic_zy.put("0", "请选择");
        dic_zy.put("1", "公务员");
        dic_zy.put("2", "事业单位员工");
        dic_zy.put("3", "工人");
        dic_zy.put("4", "农民");
        dic_zy.put("5", "军人");
        dic_zy.put("6", "职员");
        dic_zy.put("7", "私人业主");
        dic_zy.put("8", "学生");
        dic_zy.put("9", "自由职业");
        dic_zy.put("10", "其他");
        /*职务*/
        dic_zw.put("0", "请选择");
        dic_zw.put("1", "企业负责人");
        dic_zw.put("2", "总经理");
        dic_zw.put("3", "部门经理");
        dic_zw.put("4", "职员");
        /*开卡审核状态*/
        dic_kk_status.put("0", "请选择");
        dic_kk_status.put("1", "提交申请");
        dic_kk_status.put("2", "开卡成功");
        dic_kk_status.put("3", "开卡失败");
        dic_kk_status.put("4", "回退补件");
        /*国产/进口*/
        dic_cars_source.put("0", "请选择");
        dic_cars_source.put("1", "国产");
        dic_cars_source.put("2", "进口");
        /*使用性质*/
        dic_cars_property.put("0", "请选择");
        dic_cars_property.put("1", "营运");
        dic_cars_property.put("2", "非营运");
        /*车辆状况*/
        dic_car_status.put("0", "请选择");
        dic_car_status.put("1", "优秀");
        dic_car_status.put("2", "良好");
        dic_car_status.put("3", "一般");
        /*变速箱*/
        dic_car_gear_box.put("0", "请选择");
        dic_car_gear_box.put("1", "自动");
        dic_car_gear_box.put("2", "手动");
        /*车辆颜色*/
        dic_car_color.put("0", "请选择");
        dic_car_color.put("1", "黑");
        dic_car_color.put("2", "白");
        dic_car_color.put("3", "灰");
        dic_car_color.put("4", "红");
        dic_car_color.put("5", "银");
        dic_car_color.put("6", "蓝");
        dic_car_color.put("7", "金");
        dic_car_color.put("8", "棕");
        dic_car_color.put("9", "橙");
        dic_car_color.put("10", "黄");
        dic_car_color.put("11", "紫");
        dic_car_color.put("12", "绿");
        dic_car_color.put("13", "褐");
        dic_car_color.put("14", "栗");
        dic_car_color.put("15", "米");
        dic_car_color.put("16", "银灰");
        dic_car_color.put("17", "青");
        dic_car_color.put("18", "香槟");
        dic_car_color.put("19", "咖啡");
        dic_car_color.put("20", "天山");
        dic_car_color.put("21", "其他色");
    }
}