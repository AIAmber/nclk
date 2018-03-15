require(['jquery', 'bootstrap', 'echarts'], function ($, bootstrap, echarts) {
// data all, start
// main21, data, click button to show
var main21Mod = 2;

var data2101x = ['行政许可审批', '环保项目审批', '法人基本信息', '市环保局公文', '全民社会保障'];
var data2101y = [235, 441, 306, 482, 643];
var data2102x = ['行政许可审批', '环保项目审批', '法人基本信息', '食品经营许可', '投资项目建设', '不动产登记', '企业注册登记', '税务登记', '药品经营许可', '法人信用记录'];
var data2102y = [235, 441, 306, 482, 643, 412, 206, 225, 649, 411];
var data2103x = ['行政许可审批', '环保项目审批', '法人基本信息', '市环保局公文', '全民社会保障', '社会救助', '便民服务事项', '教育局公文', '财政局公文', '南京市公文', '江苏省公文', '工商登记', '食品经营许可', '投资项目建设', '不动产登记'];
var data2103y = [235, 441, 306, 482, 643, 412, 206, 225, 649, 411, 201, 223, 648, 415, 206];
var dashData21x = data2102x;
var dashData21y = data2102y;
var dashColor21 = ['#5BB0F0'];
$("#dash-count-top5").click(function () {
    main21Mod = 1; // shift control...
    dashColor21 = ['#FFB74E'];
    dashData21x = data2101x;
    dashData21y = data2101y;
});
$("#dash-count-top10").click(function () {
    main21Mod = 2; // shift control...
    dashColor21 = ['#5BB0F0'];
    dashData21x = data2102x;
    dashData21y = data2102y;
});
$("#dash-count-top20").click(function () {
    main21Mod = 3; // shift control...
    dashColor21 = ['#81C884'];
    dashData21x = data2103x;
    dashData21y = data2103y;
});
// end21

// main23, data
var data2301x = ['法人基本信息', '企业注册登记', '公共服务事项', '其他'];
var data2301y = [{
    value: 335,
    name: '法人基本信息'
}, {
    value: 310,
    name: '企业注册登记'
}, {
    value: 234,
    name: '公共服务事项'
}, {
    value: 1548,
    name: '其他'
}];
var data2302x = ['法人基本信息', '企业注册登记', '公共服务事项', '全民社会保障', '行政审批', '其他'];
var data2302y = [{
    value: 335,
    name: '法人基本信息'
}, {
    value: 310,
    name: '企业注册登记'
}, {
    value: 234,
    name: '公共服务事项'
}, {
    value: 135,
    name: '全民社会保障'
}, {
    value: 335,
    name: '行政审批'
}, {
    value: 1248,
    name: '其他'
}];
var data2302xBy = ['法人基本信息', '企业注册登记', '公共服务事项', '全民社会保障', '行政审批', '其他'];
var data2302yBy = [{
    value: 365,
    name: '法人基本信息'
}, {
    value: 320,
    name: '企业注册登记'
}, {
    value: 244,
    name: '公共服务事项'
}, {
    value: 165,
    name: '全民社会保障'
}, {
    value: 385,
    name: '行政审批'
}, {
    value: 1098,
    name: '其他'
}];
var data2303x = ['法人基本信息', '企业注册登记', '公共服务事项', '全民社会保障', '监测报告', '行政审批', '便民服务', '政府公文', '其他'];
var data2303y = [{
    value: 335,
    name: '法人基本信息'
}, {
    value: 310,
    name: '企业注册登记'
}, {
    value: 234,
    name: '公共服务事项'
}, {
    value: 135,
    name: '全民社会保障'
}, {
    value: 247,
    name: '监测报告'
}, {
    value: 174,
    name: '行政审批'
}, {
    value: 265,
    name: '便民服务'
}, {
    value: 335,
    name: '政府公文'
}, {
    value: 648,
    name: '其他'
}];
var dashData23x = data2302x;
var dashData23y = data2302y;
var main23Mod = 992;
var main23Title = 881;
var dashTitle23 = "资源请求占比";
// button req_or_pub start
// req or pub, click to change
var req_or_pub_echarts_clickTimes23 = 0;
$('#dash-resource-reqOrPub').click(function () {
    req_or_pub_echarts_clickTimes23 = req_or_pub_echarts_clickTimes23 + 1;
    if (req_or_pub_echarts_clickTimes23 % 2 == 0) {
        main23Title = 1;
        dashTitle23 = "资源请求占比";
        dashData23x = data2302x;
        dashData23y = data2302y;
    }
    if (req_or_pub_echarts_clickTimes23 % 2 == 1) {
        main23Title = 2;
        dashTitle23 = "资源发布占比";
        dashData23x = data2302xBy;
        dashData23y = data2302yBy;
    }
})
// button req_or_pub end
$("#dash-resource-req").click(function () {
    main23Title = 1;
    dashTitle23 = "资源请求占比";
    dashData23x = data2302x;
    dashData23y = data2302y;
});
$("#dash-resource-pub").click(function () {
    main23Title = 2;
    dashTitle23 = "资源发布占比";
    dashData23x = data2302xBy;
    dashData23y = data2302yBy;
});
$("#dash-publish-top3").click(function () {
    main23Mod = 1;
    dashData23x = data2301x;
    dashData23y = data2301y;
});
$("#dash-publish-top5").click(function () {
    main23Mod = 2;
    dashData23x = data2302x;
    dashData23y = data2302y;
});
$("#dash-publish-top8").click(function () {
    main23Mod = 3;
    dashData23x = data2303x;
    dashData23y = data2303y;
});
// end23

// main22 - realtime, date, data
var main22Mod = 4;

var base22 = +new Date(2018, 2, 28, 8, 0, 0);
var quaMinute22 = 5 * 60 * 1000;
var date22 = [];
var data22 = [];
var dataA22 = [];
var dataA22B7 = [];
var myDate22 = new Date(base22);
var Minute22 = 60;
var nowRe22 = new RegExp("a");
var nowRe22 = /\d+:\d+/;

//        now = [myDate.getHours(), myDate.getMinutes() + 1, myDate.getSeconds()].join(':');

function addData22(shift) {
    now22 = myDate22.toTimeString().match(nowRe22);
    nowNum22 = (Math.random() - 0.03) * 925 + (Math.random() + 0.8) * 275 + (Math.random() - 0.58) * 480 + (Math.random() + 0.36) * 52;
    nowNumA22 = parseInt((Math.random() + 0.36) * 100);
    //nowNumStr22 = nowNum22 + "ss"; // 转换成String类型，添加单位，不适用于echarts。

    date22.push(now22);
    data22.push(nowNum22);
    dataA22.push(nowNumA22);
    if (shift) {
        //console.log(data22);
        date22.shift();
        data22.shift();
        dataA22.shift();
    }
    myDate22 = new Date(+new Date(myDate22) + quaMinute22);
}

for (var i22 = 0; i22 < 41; i22++) {
    addData22();
}

// main22 - 3 days(7 days), date, data
var checkDate22Mod3 = [];
var timeBase22 = myDate22.getTime();
var timeSetBase22 = new Date(base22);

timeSetBase22Mod3 = timeSetBase22.setTime(timeBase22 - 3.6 * 60 * 60 * 1000);
var MyDate22Mod3 = new Date(timeSetBase22Mod3);

for (var i22mod = 0; i22mod < 41; i22mod++) {
    timeSetBase22Mod3 = timeSetBase22.setTime(timeBase22 - 72 * 60 * 60 * 1000 + 3.6 * 60 * 60 * 1000 * i22mod);
    MyDate22Mod3 = new Date(timeSetBase22Mod3);
    Date22Mod3 = MyDate22Mod3.getMonth() + "-" + MyDate22Mod3.getDate();
    Now22Mod3 = MyDate22Mod3.toTimeString();
    checkDate22Mod3.push(Date22Mod3);
}

var data2204x = checkDate22Mod3;
var data2204y = [0, 0, 0, 12, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 16, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 15, 0, 12, 0, 0, 11, 0, 0];
var dashData22x = data2204x; // use '30days', slice(13,20) to show 7days
var dashData22y = data2204y;
// end22


// main start
var myChartTotal = echarts.init(document.getElementById('mainTotal'));
var myChartPie = echarts.init(document.getElementById('mainPie'));
var myChartLine = echarts.init(document.getElementById('mainLine'));

// 'show' or 'not show' the legend. 是否展示legend
var legendMode = true;

//main21 mainTotal bar
var optionTotal = {
    tooltip: {
        show: true,
        formatter: "{a} <br/>{b} : {c} 次"
    },
    color: ['#5BB0F0'],
    grid: {
        left: '4%',
        right: '5%',
        top: '13%',
        bottom: '0%',
        containLabel: true
    },
    xAxis: [{
        type: 'category',
        data: dashData21x,
        // x, font color
        axisLabel: {
            show: true,
            interval: 0,
            rotate: 20,
            margin: 8,
            textStyle: {
                color: "#555",
                fontsize: "10px"
            },
            color: '#555'
        },
        // x, line color
        axisLine: {
            lineStyle: {
                color: '#EFF3F8'
            }
        }
            }],
    yAxis: [{
        type: 'value',
        name: '访问次数',
        axisLabel: {
            formatter: '{value} 次'
        },
        // y, line color
        splitLine: {
            lineStyle: {
                color: ['#EFF3F8']
            }
        },
        nameTextStyle: {
            color: ['#555']
        },
        axisLine: {
            lineStyle: {
                color: '#EFF3F8',
                width: 1
            }
        },
        axisLabel: {
            color: '#555'
        }
            }],
    series: [{
        name: '资源请求次数',
        type: 'bar',
        barCategoryGap: '48%',
        data: dashData21y
            }]
};

// 资源交换分类占比, change, main23 pie
var optionPie = {
    tooltip: {
        show: true,
        formatter: "{a} <br/>{b} : {c} 次 ({d}%)"
    },
    title: {
        show: false,
        text: dashTitle23,
        left: 'left',
        textStyle: {
            color: '#555'
        }
    },
    legend: {
        show: false,
        orient: 'vertical',
        x: 'left',
        y: '30%',
        data: dashData23x,
        textStyle: {
            color: '#555'
        },
        selectedMode: legendMode
    },
    color: ['#4DB7AD', '#C3B5E1', '#5BB0F0', '#FFB880', '#D97A80', '#97b552', '#e5cf0d', '#95706d', '#8d98b3'],
    series: [{
        name: '热门资源',
        type: 'pie',
        radius: ['35%', '52%'],
        center: ['50%', '50%'],
        data: dashData23y,
        itemStyle: {
            normal: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            },
            emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)',
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            }
        }
        }, {
        name: '热门资源',
        type: 'pie',
        radius: ['55%', '56%'],
        center: ['50%', '50%'],
        data: dashData23y,
        itemStyle: {
            normal: {
                label: {
                    show: true
                },
                labelLine: {
                    show: true
                }
            },
            emphasis: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            }
        }
                }, {
        name: '热门资源',
        type: 'pie',
        radius: ['31%', '32%'],
        center: ['50%', '50%'],
        data: dashData23y,
        itemStyle: {
            normal: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            },
            emphasis: {
                label: {
                    show: false
                },
                labelLine: {
                    show: false
                }
            }
        }
                }]
};

var optionLine = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross'
        },
        formatter: "{a} <br/>{b} : {c} M"
    },
    legend: {
        data: ['交换流量', '交换次数']
    },
    xAxis: {
        type: 'category',
        data: dashData22x,
        // x, line color
        axisLine: {
            lineStyle: {
                color: '#EFF3F8'
            }
        },
        axisLabel: {
            color: '#555'
        }
    },
    yAxis: [{
        type: 'value',
        name: '交换流量',
        position: 'left',
        axisLabel: {
            formatter: '{value} M',
            color: '#555'
        },
        axisPointer: {
            snap: true
        },
        // y, line style
        axisLine: {
            lineStyle: {
                color: '#EFF3F8'
            }
        },
        splitLine: {
            lineStyle: {
                color: ['#EFF3F8']
            }
        },
        nameTextStyle: {
            color: ['#555']
        },
    }],
    color: ['#FFB74E', '#89CA94'],
    grid: {
        left: '4%',
        right: '1%',
        top: '15%',
        bottom: '3%',
        containLabel: true
    },
    series: [{
        name: '交换流量',
        type: 'line',
        smooth: true,
        data: dashData22y
            }]
};

myChartTotal.setOption(optionTotal);
myChartPie.setOption(optionPie);
myChartLine.setOption(optionLine);

});
