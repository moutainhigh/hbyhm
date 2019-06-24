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
        data : {baodanname:sel,baodantime:time,bank:"2"},
        success : function(data) {
            var summarydata = [135,136,136,140,144,108,191,223,2810];
            var fangkuandata = [634.49,784.87,775.45,854.47,865.29,567.37,1172.18,1171.63,15466.19];
            var timeline = ["2018-09","2018-10","2018-11","2018-12","2019-01","2019-02","2019-03","2019-04","2019-05"];
            if(time == "2018"){
                summarydata = [55,42,92,77,74,91,89,129,135];
                fangkuandata = [373.05,235.79,511.37,422.48,451.47,597.44,575.53,827.66,634.49];
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
        data: {diyaname: sel,diyatime:time,bank:"2"},
        success: function (data) {
            var paw = [[100,530,680,400,73],[5,38,74,40,1],[2,27,81,34,1],[1,10,45,23,0],[1,25,66,20,0]];
            var paw1=paw[time][0];
            var paw2=paw[time][1];
            var paw3=paw[time][2];
            var paw4=paw[time][3];
            var paw5=paw[time][4];
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
                        minAngle: 5,

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
        data: {cailiaoname: sel,cailiaotime:time,bank:"2"},
        success: function (data) {
            var recycledata = [86,97,99,108,112,79,147,158,2040];
            var recycletime = ["2018-09","2018-10","2018-11","2018-12","2019-01","2019-02","2019-03","2019-04","2019-05"];
            if(time == "2018"){
                recycledata = [37,30,67,55,60,79,74,111,86];
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
        data: {zhengxinname: sel,zhengxintime:time,bank:"2"},
        success: function (data) {
            var credit1;
            var credit2;
            var zhengxin =[[2040,770],[158,65],[147,44],[79,29],[112,32],
                           [108,32],[99,37],[97,39],[86,49],[111,18],[74,15],
                           [79,12],[60,14],[55,22],[67,25],[30,12],[37,18]];
            credit1 = zhengxin[time][0];
            credit2 = zhengxin[time][1];

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
        data : {bank:"2"},
        success : function(data) {
            var age = [[503,1705,404,198],[39,74,40,5],[28,81,34,2],[10,45,23,1],[25,66,20,1]];
            var age1=age[time][0];
            var age2=age[time][1];
            var age3=age[time][2];
            var age4=age[time][3];


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
    var amount = [];
    var max1;
    var max2;
    var max3;
    var max4;
    var max5;
    $.ajax({
        dataType: "json",
        type: "POST",
        url: "/manager/visual/getAgencyMap.do",
        data: {dailiname: sel,bank:"2"},
        success: function (data) {
            var year = "2019年";
            data[1] = 2810;
            data[2] = 3;
            data[3] = 2027;
            data[4] = 2;
            data[5] = 4;

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
        url: "/manager/visual/getOverdueMap.do",
        data: {yuqiname: sel,bank:"2"},
        success: function (data) {
            var amountVar = [[4.08,3.09,2.04] , [0.31,0.12,0.16] , [0.36,0.16,0.14] , [0.23,0.21,0.07] , [0.22,0.11,0.22]];
            var newcarsVar = [[0,0,0] , [0,0,0] , [0,0,0] , [0,0,0] , [0,0,0]];
            var oldcarsVar = [[4.08,3.09,2.04] , [0.31,0.12,0.16] , [0.36,0.16,0.14] , [0.23,0.21,0.07] , [0.22,0.11,0.22]];
            var amountmoneyVar = [[30.93,23.20,15.46] , [2.34,1.17,1.17] , [2.93,2.34,1.17] , [1.70,1.13,0.56] , [1.73,0.86,1.73]];
            var newcarsmoneyVar = [[0,0,0] , [0,0,0] , [0,0,0] , [0,0,0] , [0,0,0]];
            var oldcarsmoneyVar = [[30.93,23.20,15.46] , [2.34,1.17,1.17] , [2.93,2.34,1.17] , [1.70,1.13,0.56] , [1.73,0.86,1.73]];

            var amount = amountVar[time];
            var newcars = newcarsVar[time];
            var oldcars = oldcarsVar[time];
            var amountmoney = amountmoneyVar[time];
            var newcarsmoney = newcarsmoneyVar[time];
            var oldcarsmoney = oldcarsmoneyVar[time];
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
                        max: 10,
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
    url : "/manager/visual/getStateMap.do",
    data : {bank:"2"},
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
                        {value:1, name:"河北省"},
                        {value:1, name:"山西省"},
                        {value:1, name:"山东省"},
                        {value:1, name:"河南省"},
                        {value:1, name:"江苏省"},
                        {value:1, name:"其他省"}
                    ],
                },
                {
                    name:'省份逾期金额',
                    type:'pie',
                    radius: ['40%', '53%'],
                    data:[
                        {value:72000, name:"河北省"},
                        {value:61000, name:"山西省"},
                        {value:56000, name:"山东省"},
                        {value:48000, name:"河南省"},
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

