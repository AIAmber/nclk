// data all, start
// main21, data, click button to show
var main21Mod = 2;


var data2102x = ['南京市', '苏州市', '连云港', '宿迁市', '常州市', '无锡市', '淮安市', '徐州市', '南通市', '镇江市', '盐城市', '扬州市', '泰州市'];
var data2102y = [34, 28, 12, 32, 23, 8, 16, 42, 18, 28, 23, 13, 17];
var dashData21x = data2102x;
var dashData21y = data2102y;
var dashColor21 = ['#5BB0F0'];
// end21

// main23, data

var data2302x = ['网络断线', '时间不同步', '终端损坏', '其他'];
var data2302y = [{
    value: 335,
    name: '网络断线'
	}, {
    value: 310,
    name: '时间不同步'
	}, {
    value: 234,
    name: '终端损坏'
	}, {
    value: 548,
    name: '其他'
	}];
var dashData23x = data2302x;
var dashData23y = data2302y;
var main23Mod = 992;
var main23Title = 881;
var dashTitle23 = "异常原因占比";
// button req_or_pub start
// req or pub, click to change
var req_or_pub_echarts_clickTimes23 = 0;

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
var nowRe22 = /\d+:\d+/; // 设定正则表达式，便于读取当前的“年、月、日、时、分、秒”。

//	        now = [myDate.getHours(), myDate.getMinutes() + 1, myDate.getSeconds()].join(':');

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
    timeSetBase22Mod3 = timeSetBase22.setTime(timeBase22 - 10 * 60 * 1000 + 1 * 60 * 1000 * i22mod);
    MyDate22Mod3 = new Date(timeSetBase22Mod3);
    //	    Date22Mod3 = MyDate22Mod3.getMonth() + "-" + MyDate22Mod3.getDate(); // 获取当前时间的“月、日”属性。
    Date22Mod3 = MyDate22Mod3.getHours() + ":" + MyDate22Mod3.getMinutes() // 获取当前时间的“时、分、秒”属性。
    Now22Mod3 = MyDate22Mod3.toTimeString();
    checkDate22Mod3.push(Date22Mod3);
}

var data2204x = checkDate22Mod3;
var data2204y = [0, 0, 0, 0, 13, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 0, 16, 0, 0, 0, 7, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 15, 0, 12, 0, 0, 0, 11, 0, 0];
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
        name: '异常次数',
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
        name: '异常出现次数',
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
        name: '异常原因',
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
        name: '异常原因',
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
        name: '异常原因',
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
        formatter: "{a} <br/>{b} : {c} 次"
    },
    legend: {
        data: ['当前异常数量']
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
        name: '当前异常数量',
        position: 'left',
        axisLabel: {
            formatter: '{value} 次',
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
        name: '当前异常数量',
        type: 'line',
        smooth: true,
        data: dashData22y
	            }]
};

myChartTotal.setOption(optionTotal);
myChartPie.setOption(optionPie);
myChartLine.setOption(optionLine);
