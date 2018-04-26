var localPath = "."; // use for eclipse

var data2302x = ['网络断线', '时间不同步', '终端损坏', '其他'];

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
        data: [],
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
        data: []
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
        text: "异常原因占比",
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
        data: data2302x,
        textStyle: {
            color: '#555'
        },
        selectedMode: true
    },
    color: ['#4DB7AD', '#C3B5E1', '#5BB0F0', '#FFB880', '#D97A80', '#97b552', '#e5cf0d', '#95706d', '#8d98b3'],
    series: [{
        name: '异常原因',
        type: 'pie',
        radius: ['35%', '52%'],
        center: ['50%', '50%'],
        data: [],
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
        data: [],
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
        data: [],
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
        data: [],
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
    //yAxis: {},
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
        data: []
            }]
};

myChartTotal.setOption(optionTotal);
myChartPie.setOption(optionPie);
myChartLine.setOption(optionLine);

// myChartTotal, data load start
myChartTotal.showLoading();
var totalDataXs = [];
var totalDataYs = [];

$.ajax({
    type: "get",
    async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url: localPath + "/dashboard/data/echartsData.json", //
    dataType: "json", //返回数据形式为json
    success: function (result) {
        $.each(result.totalData, function (index, item) {
            for (var i = 0; i < item.totalDataX.length; i++) {
                totalDataXs.push(item.totalDataX[i]);
            }

            for (var i = 0; i < item.totalDataY.length; i++) {
                totalDataYs.push(item.totalDataY[i]);
            }
        });

        setTimeout("pause()", 2000); //暂停0.5s
        myChartTotal.hideLoading();

        myChartTotal.setOption({ //加载数据图表
            xAxis: {
                data: totalDataXs
            },
            series: [{
                // 根据名字对应到相应的系列
                data: totalDataYs
                    }]
        });
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("load failed!");
        myChartTotal.hideLoading();
    }
});
// myChartTotal, data load end

// myChartPie, data load start
myChartPie.showLoading();
var pieDataXs = [];
var pieDataYs = [];

$.ajax({
    type: "post",
    async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url: "http://localhost:8080/nclk-center/DashboardController/jsonTestForEcharts.do", //
    dataType: "json", //返回数据形式为json
    success: function (result) {
        // json file method
        $.each(result, function (index) {
            names.push(index); //挨个取出类别并填入类别数组                    
            nums.push(result[index]); //挨个取出销量并填入销量数组
        });

        setTimeout("pause()", 500); //暂停0.5s
        myChartPie.hideLoading();

        myChartPie.setOption({ //加载数据图表
            legend: {
                data: pieDataXs
            },
            series: [{
                // 根据名字对应到相应的系列
                data: pieDataYs
                    }, {
                // 根据名字对应到相应的系列
                data: pieDataYs
                    }, {
                // 根据名字对应到相应的系列
                data: pieDataYs
                    }]
        });
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("load failed!");
        myChartPie.hideLoading();
    }
});
// myChartPie, data load end


// myChartLine, data load start
myChartLine.showLoading();
var lineDataXs = [];
var lineDataYs = [];

$.ajax({
    type: "get",
    async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
    url: localPath + "/dashboard/data/echartsData.json", //
    dataType: "json", //返回数据形式为json
    success: function (result) {
        $.each(result.lineData, function (index, item) {
            $.each(item.lineDataX, function (index, item) {
                lineDataXs.push(item);
            });

            $.each(item.lineDataY, function (index, item) {
                lineDataYs.push(item);
            });
        });

        setTimeout("pause()", 500); //暂停0.5s
        myChartLine.hideLoading();

        myChartLine.setOption({ //加载数据图表
            xAxis: {
                data: lineDataXs
            },
            series: [{
                // 根据名字对应到相应的系列
                data: lineDataYs
                    }]
        });
    },
    error: function (errorMsg) {
        //请求失败时执行该函数
        alert("load failed!");
        myChartLine.hideLoading();
    }
});
// myChartLine, data load end
