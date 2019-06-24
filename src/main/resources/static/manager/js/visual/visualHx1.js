/*点击切换事件 直接用js吧*/

var axistick="{lineStyle:{color:'white',type:'dashed'}}";//刻度

/* -----------------------------------------报单统计开始----------------------------------------- */
var baodan = echarts.init(document.getElementById('baodan'));
//前台数据图后台获取数据绘制
function baodanselect(){
    var sel=document.getElementById("baodanval").value
    var obj1 = document.getElementById('baodantime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType : "json",
        type : "POST",
        url : "/manager/visual/getPathMap.do",
        data : {baodanname:sel,baodantime:time,bank:"4"},
        success : function(data) {
            var summarydata = [197,206,209,211,213,156,2471,5147,5598];
            var fangkuandata = [951.74,1177.31,1163.18,1281.71,1297.3,851.05,15382.81,27389.6,30812.17];
            var timeline = ["2018-09","2018-10","2018-11","2018-12","2019-01","2019-02","2019-03","2019-04","2019-05"];
            if(time == "2018"){
                summarydata = [81,64,138,114,110,139,128,196,197];
                fangkuandata = [559.57,353.68,767.05,633.71,677.21,896.17,863.3,1241.49,951.74];
                timeline = ["2018-01","2018-02","2018-03","2018-04","2018-05","2018-06","2018-07","2018-08","2018-09"];
            }

//数据图绘制
            var option_baodan = {
                tooltip : {
                    trigger: 'axis',
                },
                xAxis : [
                    {
                        type : 'category',
                        name:'时间',
                        boundaryGap : false,
                        axisTick:axistick,
                        nameTextStyle:{//坐标轴名称的文字样式。
                            padding: [0, 0, 0, -8]
                        },

                        axisLine :{symbolSize:['10', '13'],lineStyle:{color:'#6A5046'}},//轴线
                        data : timeline
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name:'数量',
                        axisTick:axistick,
                        axisLine :{symbol:['none', 'arrow'],symbolSize:['10', '13'],lineStyle:{color:'#6A5046'}},//轴线
                        splitLine:{show: false},//去除网格线
                    },
                    {
                        type : 'value',
                        name:'放款金额(万)',
                        axisTick:axistick,
                        axisLine :{symbol:['none', 'arrow'],symbolSize:['10', '13'],lineStyle:{color:'#6A5046'}},//轴线
                        splitLine:{show: false},//去除网格线
                    }
                ],
                series : [
                    {
                        name:'报单统计',
                        type:'line',
                        smooth:true,
                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                        data:summarydata
                    },
                    {
                        name:'放款金额',
                        type:'line',
                        smooth:true,
                        yAxisIndex: 1,
                        itemStyle: {normal: {areaStyle: {type: 'default'}}},
                        data:fangkuandata
                    }
                ]
            };
            baodan.setOption(option_baodan);
//ajax结尾
        },
        error : function(e, type, msg) {
            console.log(type + "=报单统计=" + msg);
        }
    })
}


/* -------------------------------------------抵押完成天数分布开始--------------------------------------- */
var diyawancheng = echarts.init(document.getElementById('diyawancheng'));
function diyaselect() {
    var sel = document.getElementById("diyaval").value
    var obj1 = document.getElementById('diyatime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "/manager/visual/getPawnPathMap.do",
        data: {diyaname: sel,diyatime:time,bank:"4"},
        success: function (data) {
            var paw5=230;
            var paw2=865;
            var paw3=1989;
            var paw4=876;
            var paw1=320;
            if(time == "2019-4"){ paw5 = 64; paw2 = 813; paw3 = 1688; paw4 = 913; paw1 = 148;}
            if(time == "2019-3"){ paw5 = 23; paw2 = 280; paw3 = 1128; paw4 = 401; paw1 = 59;}
            if(time == "2019-2"){ paw5 = 0; paw2 = 14; paw3 = 66; paw4 = 29; paw1 = 3;}
            if(time == "2019-1"){ paw5 = 1; paw2 = 32; paw3 = 78; paw4 = 51; paw1 = 4;}


            var option_diyawancheng = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                series: [
                    {
                        name: '抵押归档情况',
                        type: 'pie',
                        radius:  '65%',
                        minAngle: 20,

                        roseType: 'radius',
                        labelLine: {
                            length: 14,//视觉引导线第一段的长度。
                            length2: 8 //视觉引导项第二段的长度。
                        },
                        data: [
                            {value: paw5, name: '60天以上'},
                            {value: paw1, name: '0-15天', itemStyle: {color: '#985858'}},
                            {value: paw2, name: '15-30天'},
                            {value: paw3, name: '30-45天'},
                            {value: paw4, name: '45-60天'}
                        ],
                        label: {
                            normal: {
                                textStyle: {
                                    color: 'rgba(255, 255, 255, 0.3)',
                                    fontSize: 12,
                                    color: '#505050'
                                }
                            }
                        },
                    }
                ]
            };
            diyawancheng.setOption(option_diyawancheng);
//ajax结尾
        },
        error: function (e, type, msg) {
            console.log(type + "=抵押完成天数=" + msg);
        }
    })
}

/* -------------------------------------------抵押材料回收开始--------------------------------------- */
var cailiaohuishou = echarts.init(document.getElementById('cailiaohuishou'));
function cailiaoselect() {
    var sel = document.getElementById("cailiaoval").value
    var obj1 = document.getElementById('cailiaotime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "/manager/visual/getRecyclePathMap.do",
        data: {cailiaoname: sel,cailiaotime:time,bank:"4"},
        success: function (data) {
            var recycledata = [126,147,152,163,166,114,1901,3652,4064];
            var recycletime = ["2018-09","2018-10","2018-11","2018-12","2019-01","2019-02","2019-03","2019-04","2019-05"];
            if(time == "2018"){
                recycledata = [54,45,100,81,89,121,107,168,126];
                recycletime = ["2018-01","2018-02","2018-03","2018-04","2018-05","2018-06","2018-07","2018-08","2018-09"];
            }

            var option_cailiaohuishou = {
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    x: 'left',//图例组件离容器下侧的距离
                    y: 'top',
                    width: 300,//图例组件的宽度
                    data: ['材料回收'],
                    itemWidth: 15,//图例标记的图形宽度
                    itemHeight: 10,//图例标记的图形高度
                    textStyle: {
                        fontSize: 12,
                        color: '#6A5046'
                    },
                    itemGap: 7,
                    bottom: -5
                },
                grid: {//直角坐标系内绘图网格
                    show: false,//是否显示直角坐标系网格。[ default: false ]
                    right: "12%"
                },
                xAxis: [
                    {
                        type: 'category',
                        data: recycletime,
                        axisLine: {symbol: ['none', 'arrow'], symbolSize: ['10', '13'], lineStyle: {color: '#6A5046'}},//轴线
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '过件数',
                        splitLine: {show: false},//去除网格线
                        axisLine: {symbol: ['none', 'arrow'], symbolSize: ['10', '13'], lineStyle: {color: '#6A5046'}},//轴线

                    },
                ],
                series: [
                    {
                        name: '材料回收',
                        type: 'line',
                        data: recycledata
                    },

                ],
                /* color:['#e07805','#344bb1'] //全局调色板  好用到爆炸 */
            };
            cailiaohuishou.setOption(option_cailiaohuishou);
//ajax结尾
        },
        error: function (e, type, msg) {
            console.log(type + "=抵押完成=" + msg);
        }
    })
}


/* -------------------------------------------征信查询开始--------------------------------------- */

var zhengxinchaxun = echarts.init(document.getElementById('zhengxinchaxun'));
function zhengxinselect() {
    var sel = document.getElementById("zhengxinval").value
    var obj1 = document.getElementById('zhengxintime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "/manager/visual/getCreditPathMap.do",
        data: {zhengxinname: sel,zhengxintime:time,bank:"4"},
        success: function (data) {
            var credit1= 4064;
            var credit2= 1534;
            if(time == "2019-4"){credit1= 3653;credit2= 1495;}
            if(time == "2019-3"){credit1= 1901;credit2= 570;}
            if(time == "2019-2"){credit1= 114;credit2= 42;}
            if(time == "2019-1"){credit1= 166;credit2= 47;}


            var option_zhengxinchaxun = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    y: 'bottom',
                    x: 'center',
                    width: 180,
                    itemWidth: 15,//图例标记的图形宽度
                    itemHeight: 10,//图例标记的图形高度
                    data: ['征信通过', '征信不通过'],
                    bottom: -5,
                },

                series: [
                    {
                        name: '征信查询通过率',
                        type: 'pie',
                        minAngle: 2,
                        radius: '62%',
                        center: ['51%', '45%'],//圆心位置[width，height]
                        data: [
                            {value: credit1, name: '征信通过'},
                            {value: credit2, name: '征信不通过'},
                        ]
                    }
                ]
            };

            zhengxinchaxun.setOption(option_zhengxinchaxun);
//ajax结尾
        },
        error: function (e, type, msg) {
            console.log(type + "=征信查询=" + msg);
        }
    })
}


/* -------------------------------------------客户年龄开始--------------------------------------- */
var kehunianling = echarts.init(document.getElementById('kehunianling'));
function kehuselect() {
    var obj1 = document.getElementById('kehutime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType : "json",
        type : "POST",
        url : "/manager/visual/getAgePathMap.do",
        data : {bank:"4"},
        success : function(data) {
            var age1=785;
            var age2=1986;
            var age3=862;
            var age4=498;
            if(time == "2019-4"){age1=813;age2=1752;age3=913;age4=148;}
            if(time == "2019-3"){age1=280;age2=1151;age3=401;age4=59;}
            if(time == "2019-2"){age1=14;age2=66;age3=29;age4=3;}
            if(time == "2019-1"){age1=32;age2=79;age3=51;age4=4;}

            var option_kehunianling = {
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                title : {
                    text: '年龄分布',
                    x:'7px',
                    y:'6px',
                    textStyle :	{
                        fontSize: 16,
                    }
                },
                series : [
                    {
                        name:'客户年龄分布',
                        type:'pie',
                        minAngle: 2,
                        radius : '55%',
                        center: ['52%', '55%'],//圆心位置[width，height]
                        data:[
                            {value:age1, name:'18-30岁'},
                            {value:age2, name:'30-40岁'},
                            {value:age3, name:'40-50岁'},
                            {value:age4, name:'50岁以上'}
                        ]
                    }
                ]
            };

            kehunianling.setOption(option_kehunianling);
//ajax结尾
        },
        error : function(e, type, msg) {
            console.log(type + "=客户年龄=" + msg);
        }
    })
}

/* -------------------------------------------客户年龄结束--------------------------------------- */

/* -------------------------------------------代理商综合能力分析开始--------------------------------------- */
var dalifenxi = echarts.init(document.getElementById('dalifenxi'));
function dailiselect() {
    var sel = document.getElementById("dailival").value
    var obj = document.getElementById('dailitime'); //定位id
    var index = obj.selectedIndex; // 选中索引
    var value = obj.options[index].value; // 选中值
    var amount = [];
    var max1;
    var max2;
    var max3;
    var max4;
    var max5;
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "",
        data: {dailiname: sel,dailitime:value,bank:"4"},
        success: function (data) {
            var year = "2019年";
            data[1] = 5598;
            data[2] = 3;
            data[3] = 4064;
            data[4] = 5;
            data[5] = 11;
            if(value == "2018"){
                year = "2018年"
                data[1] = 211;
                data[2] = 3;
                data[3] = 163;
                data[4] = 0;
                data[5] = 0;
            }
            for(var i=1;i<6;i++){
                amount[i-1]=data[i];
            }
            //报单量数据上限计算绘图
            if(data[1] < 100){
                max1 = 100/data[1]/10+2
            }else if(data[1] < 300){
                max1 = 300/data[1]/10+1.7
            }else if(data[1] < 600){
                max1 = 600/data[1]/10+1.5
            }else if(data[1] < 1200){
                max1 = 1200/data[1]/10+1.3
            }else if(data[1] < 2000){
                max1 = 2000/data[1]/10+1.1
            }else{
                max1 = 1.1-(data[1]/2000)/100
            }
            //抵押完成上限计算绘图
            if(data[3] < 80){
                max3 = 80/data[3]/10+2
            }else if(data[3] < 240){
                max3 = 240/data[3]/10+1.7
            }else if(data[3] < 480){
                max3 = 480/data[3]/10+1.5
            }else if(data[3] < 960){
                max3 = 960/data[3]/10+1.3
            }else if(data[3] < 1600){
                max3 = 1600/data[3]/10+1.1
            }else{
                max3 = 1.1-(data[3]/1600)/100
            }
            //进件效率上限计算绘图
            if(data[2] < 5){
                max2 = 1.0+data[2]/20
            }else if(data[2] < 10){
                max2 = 1.2+data[2]/40
            }else if(data[2] < 20){
                max2 = 1.6+data[2]/50
            }else if(data[2] < 40){
                max2 = 1.8+data[2]/100
            }else{
                max2 = 2.0+data[2]/200
            }
            //M3逾期上限计算绘图
            if(data[4] < 5){
                max4 = 1.0+data[4]/20
            }else if(data[4] < 10){
                max4 = 1.2+data[4]/40
            }else if(data[4] < 20){
                max4 = 1.4+data[4]/50
            }else if(data[4] < 40){
                max4 = 1.6+data[4]/100
            }else{
                max4 = 1.8+data[4]/200
            }
            //M1逾期上限计算绘图
            if(data[5] < 10){
                max5 = 1.0+data[5]/40
            }else if(data[5] < 20){
                max5 = 1.2+data[5]/70
            }else if(data[5] < 40){
                max5 = 1.4+data[5]/100
            }else if(data[5] < 60){
                max5 = 1.6+data[5]/200
            }else{
                max5 = 1.9+data[5]/200
            }
            var option_dalifenxi = {
                tooltip: {
                    trigger: 'item',
                    show: true,
                    formatter: function (params) {
                        var s = '';
                        s += params.name + '\n';
                        var values = params.value.toString().split(",");
                        s += '业务能力:' + values[0] + '\n';
                        s += '进件效率' + values[1] + '\n';
                        s += '运营能力' + values[2] + '\n';
                        s += '贷后能力' + values[3] + '\n';
                        s += '风控能力' + values[4] + '\n';
                        //系列名称:seriesName: string  数据名，类目名 : name: string   传入的数据值:value: number|Array
                        return s;
                    },
                    extraCssText: 'width:120px; white-space:pre-wrap'//额外附加到浮层的 css 样式 pre-wrap:保留空白符序列，可是正常地进行换行。
                },
                radar : [
                    {
                        indicator : [
                            {text : '业务能力-报单量',  max  : data[1] == 0?10:data[1] * max1},
                            {text : '进件效率-订单提交至-银行放款时长',  max : data[1] == 0?10:data[2] * max2},
                            {text : '运营能力-抵押完成情况', max  : data[1] == 0?10:data[3] * max3},
                            {text : '贷后能力-M3及以上逾期率', max : data[1] == 0?10:data[4] * max4},
                            {text : '风控能力-M1逾期率',  max : data[1] == 0?10:data[5] * max5}
                        ],
                        radius : 100,
                        axisLine: {//坐标线 直接隐藏
                            show:false
                        },
                        splitLine: {//区域中的分隔线。
                            show:true,
                            lineStyle: {
                                color: ['#B4B4B4']
                            }
                        },
                        nameGap: -2,
                        name :{
                            formatter: function (value, indicator) {
                                var values = value.split('-');
                                var v = '';
                                v += '{a|' + values[0] + '}\n{b|' + values[1] + '}';
                                if (!!values[2]) {
                                    v += '\n{b|' + values[2] + '}'
                                }
                                ;
                                return v;
                            },
                            width: 100,
                            rich: {//富文本标签
                                a: {
                                    color: '#2F4554',
                                    lineHeight: 20,
                                    fontSize: 13,
                                    align: 'center'
                                },
                                b: {
                                    color: '#61A0A8',
                                    lineHeight: 15,
                                    fontSize: 11,
                                    align: 'center'
                                }
                            }
                        }
                    }
                ],
                series : [
                    {
                        type: 'radar',
                        itemStyle: {
                            normal: {
                                areaStyle: {
                                    type: 'default'
                                }
                            }
                        },
                        data : [
                            {
                                value : amount,
                                name : year+'年'
                            },
                        ],
                    }
                ]
            };
            dalifenxi.setOption(option_dalifenxi);
        },
        error: function (e, type, msg) {
            console.log(type + "=代理商综合能力分析=" + msg);
        }
    })
}


/* -------------------------------------------逾期率开始--------------------------------------- */
var yuqilv_1 = echarts.init(document.getElementById('yuqilv_1'));
function yuqiselect() {
    var sel = document.getElementById("yuqival").value
    var obj1 = document.getElementById('yuqitime'); //定位id
    var index1 = obj1.selectedIndex; // 选中索引
    var time = obj1.options[index1].value; // 选中值
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "",
        data: {yuqiname: sel,bank:"4"},
        success: function (data) {
            var amount = [8.12,6.09,4.06];
            var newcars = [1,1.09,0];
            var oldcars = [7.12,5,4.06];
            var amountmoney = [61.62,46.21,30.81];
            var newcarsmoney = [2.12,1.01,0];
            var oldcarsmoney = [59.50,45.20,30.81];
            if(time == "2019-4"){
                amount = [7.30,3.65,3.65];
                newcars = [0,0,0];
                oldcars = [7.30,3.65,3.65];
                amountmoney = [54.78,27.39,27.39];
                newcarsmoney = [0,0,0];
                oldcarsmoney = [54.78,27.39,27.39];
            }
            if(time == "2019-3"){
                amount = [4.75,3.80,1.90];
                newcars = [0,0,0];
                oldcars = [5,4,2];
                amountmoney = [38.46,30.77,15.38];
                newcarsmoney = [0,0,0];
                oldcarsmoney = [38.46,30.77,15.38];
            }
            if(time == "2019-2"){
                amount = [0.34,0.22,0.17];
                newcars = [0,0,0];
                oldcars = [0.34,0.22,0.17];
                amountmoney = [2.55,1.70,1.28];
                newcarsmoney = [0,0,0];
                oldcarsmoney = [2.55,1.70,1.28];
            }
            if(time == "2019-1"){
                amount = [0.33,0.16,0.33];
                newcars = [0,0,0];
                oldcars = [0.33,0.16,0.33];
                amountmoney = [2.59,1.30,2.59];
                newcarsmoney = [0,0,0];
                oldcarsmoney = [2.59,1.30,2.59];
            }
            var option_yuqilv_1 = {
                tooltip: { //提示框组件。
                    trigger: 'axis',//触发类型:'axis'坐标轴触发，主要在柱状图，折线图等会使用类目轴的图表中使用。
                    axisPointer: { //坐标轴指示器配置项。
                        type: 'cross', //指示器类型。 'cross' 十字准星指示器
                        crossStyle: {
                            color: '#999' //交叉风格
                        }
                    }
                },
                legend: {
                    x: 'center',//图例组件离容器下侧的距离
                    width: 250,//图例组件的宽度
                    data: ['订单总数量', '新车数量', '二手车数量', '订单总金额', '新车金额', '二手车金额'],
                    itemWidth: 15,//图例标记的图形宽度
                    itemHeight: 10,//图例标记的图形高度
                    textStyle: {
                        fontSize: 12,

                    },
                    itemGap: 7,
                    bottom: -5
                },
                grid: {//直角坐标系内绘图网格
                    show: false,//是否显示直角坐标系网格。[ default: false ]
                    right: "12%"
                },
                xAxis: [
                    {
                        name: '逾期率',
                        nameTextStyle: {//坐标轴名称的文字样式。
                            padding: [27, 0, 0, -6]
                        },
                        type: 'category',
                        data: ['M1', 'M2', 'M3'],
                        axisPointer: {//坐标轴指示器配置项。
                            type: 'shadow'
                        },
                        axisTick: axistick,
                        axisLine: {symbolSize: ['10', '13'], lineStyle: {color: '#6A5046'}}//轴线
                    }
                ],
                yAxis: [
                    {
                        type: 'value',
                        name: '逾期订单数量',
                        min: 0,
                        max: 20,
                        interval: 50,
                        axisLabel: {
                            formatter: '{value}'
                        },
                        nameTextStyle: {//坐标轴名称的文字样式。
                            padding: [0, 0, 0, 20]
                        },
                        splitLine: {show: false},//去除网格线
                        axisTick: axistick,
                        axisLine: {lineStyle: {color: '#6A5046'}}//轴线
                    },
                    {
                        type: 'value',
                        name: '逾期订单金额(万)',
                        axisLabel: {
                            formatter: '{value} '
                        },
                        nameTextStyle: {//坐标轴名称的文字样式。
                            padding: [0, 0, 0, -20]
                        },
                        splitLine: {show: false},//去除网格线
                        axisTick: axistick,
                        axisLine: {lineStyle: {color: '#6A5046'}}//轴线
                    }
                ],
                series: [
                    {
                        name: '订单总数量',
                        type: 'bar',
                        data: amount,
                        barWidth: 16
                    },
                    {
                        name: '新车数量',
                        type: 'bar',
                        data: newcars,
                        barWidth: 16
                    },
                    {
                        name: '二手车数量',
                        type: 'bar',
                        data: oldcars,
                        barWidth: 16
                    },
                    {
                        name: '订单总金额',
                        type: 'line',
                        yAxisIndex: 1,
                        data: amountmoney,
                    },
                    {
                        name: '新车金额',
                        type: 'line',
                        yAxisIndex: 1,
                        data: newcarsmoney,

                    },
                    {
                        name: '二手车金额',
                        type: 'line',
                        yAxisIndex: 1,
                        data: oldcarsmoney,

                    }
                ],
            };
            yuqilv_1.setOption(option_yuqilv_1);
        },
        error: function (e, type, msg) {
            console.log(type + "=逾期率M1，M2，M3查询=" + msg);
        }
    })
}
/* ------------------------------------------逾期率right----------------------------------------------------- */
var yuqilv_2 = echarts.init(document.getElementById('yuqilv_2'));
$.ajax({
    dataType : "json",
    type : "POST",
    url : "",
    data : {bank:"4"},
    success : function(data) {
        var option_yuqilv_2= {
            tooltip: {
                trigger: 'item',
                formatter: "{a}<br/>{b}: {c} "
            },
            series: [
                {
                    name:'省份逾期数量',
                    type:'pie',
                    minAngle: 2,
                    selectedMode: 'single',
                    radius: [0, '30%'],
                    label: {
                        show:false
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    data:[
                        {value:3, name:"陕西省"},
                        {value:2, name:"云南省"},
                        {value:2, name:"山东省"},
                        {value:2, name:"河北省"},
                        {value:1, name:"江苏省"},
                        {value:1, name:"其他省"}
                    ],
                },
                {
                    name:'省份逾期金额',
                    type:'pie',
                    radius: ['40%', '53%'],
                    data:[
                        {value:212000, name:"陕西省"},
                        {value:121000, name:"云南省"},
                        {value:106000, name:"山东省"},
                        {value:88000, name:"河北省"},
                        {value:42000, name:"江苏省"},
                        {value:38000, name:"其他省"}
                    ]
                }
            ],
        };
        yuqilv_2.setOption(option_yuqilv_2);
    },
    error : function(e, type, msg) {
        console.log(type + "=逾期省份=" + msg);
    }
})

