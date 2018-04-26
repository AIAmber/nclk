require(['jquery', 'bootstrap', 'echarts'], function ($, bootstrap, echarts) {
    var localPath = location.pathname.match(/\/(.+?)(?=\/)/g)[0];
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
                formatter: '{value} 个',
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
    var areaPid = "000000032";
    var areaPidNullFlag = 0;
    var areaPidCheckFlag = 0;
    var checkModA = 1; // 省份按钮状态, 1: 开启，2: 关闭
    var districtNames = []; // X
    var districtNums = []; // Y
    var childNamesA = [] // 查询省级目录下有哪些子目录， id
    var childNamesB = [] // 查询市级目录下有哪些子目录， id
    var childNamesC = [] // 查询区县级目录下有哪些子目录， id
    var childNamesAforB = [] // 市级操作需要使用的省级子目录（市级清单）
    var childNamesBforC = [] // 区/县级操作需要使用的市级子目录（区县级清单）

    // 为myChartTotal增加点击事件
    $("#province").change(function () { // 一级按钮（省份）点击事件， total
        checkModA = 1; // 标记省份按钮状态， 开启
        // $("#province").val()获取当前所选择省份的id，将选择的城市id复制给areaPid,
        areaPid = $("#province").val();

        if ("" == areaPid) {
            areaPidNullFlag ++; // 记录省份选择框，切换选择的次数
        }
        if ("000000032" == areaPid) {
            areaPidCheckFlag ++; // 记录省份选择框，切换到“江苏省”的次数
            areaPidNullFlag ++; // 记录省份选择框，切换选择的次数
        }

        if ( ( "000000032" == areaPid && 1 <= areaPidCheckFlag ) || 1 >= areaPidNullFlag) { // 省份选择框第一次切换到“江苏省”或初次访问页面时
            $.getJSON(localPath + "/DashboardController/jsonTotalTypeId?areaPid=" + areaPid, function (data) {
                $.each(data, function (index) {
                   childNamesA.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });
                console.log("childNamesA: " + childNamesA);
            });

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeA", //
                data: {'areaPid': areaPid},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    $.each(result, function (index) {
                        // districtNames.push(result["AREA_NAME"]); //挨个取出区名并填入区名数组, X
                        districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X
                        districtNums.push(Math.floor((Math.random() * 30))); // Y, 随机插入数值
                    });
                    myChartTotal.hideLoading();
                    myChartTotal.setOption({ //加载数据图表
                        xAxis: {
                            data: districtNames
                        },
                        series: [{
                            // 根据名字对应到相应的系列
                            data: districtNums
                        }]
                    });
                    districtNames.splice(0, districtNames.length);// 内存释放，垃圾数据清理
                    districtNums.splice(0, districtNums.length);// 内存释放，垃圾数据清理
                    childNamesA.splice(0, childNamesA.length); // 内存释放，垃圾数据清理
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    alert("无法获取数据！(Error Code: 0001)");
                    // myChartTotal.hideLoading();
                }
            });
        } // if end
    });

    // 为myChartTotal增加点击事件
    $("#city").change(function () { // 二级按钮（城市）点击事件， total
        // $("#city").val()获取当前所选择城市的id，将选择的城市id复制给areaPid,
        areaPid = $("#city").val();

        // 判断下拉框是否选择到具体的“市”
        $.getJSON(localPath + "/DashboardController/jsonTotalTypeId?areaPid=000000032", function (data) {
            $.each(data, function (index) {
                childNamesAforB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
            });

            if ( 0 <= childNamesAforB.indexOf(areaPid) ){ // areaPid 在下拉框中
                $.getJSON(localPath + "/DashboardController/jsonTotalTypeId?areaPid=" + areaPid, function (data) {
                    $.each(data, function (index) {
                        childNamesB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                    });
                    console.log("childNamesB: " + childNamesB);
                });

                $.ajax({
                    type: "post",
                    async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                    url: "/nclk-center/DashboardController/jsonTotalTypeBname", //
                    data: {'areaPid': areaPid},
                    dataType: "json", //返回数据形式为json
                    timeout: 1000,
                    success: function (result) {
                        $.each(result, function (index) {
                            districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X
                            // $.post()
                            districtNums.push(Math.floor((Math.random() * 30))); // Y, 随机插入数值
                        });
                        // console.log(districtNames);
                        myChartTotal.hideLoading();
                        myChartTotal.setOption({ //加载数据图表
                            xAxis: {
                                data: districtNames
                            },
                            series: [{
                                // 根据名字对应到相应的系列
                                data: districtNums
                            }]
                        });
                        districtNames.splice(0, districtNames.length); // 内存释放，垃圾数据清理
                        districtNums.splice(0, districtNums.length); // 内存释放，垃圾数据清理
                        childNamesB.splice(0, childNamesB.length); // 内存释放，垃圾数据清理
                    },
                    error: function (errorMsg) {
                        //请求失败时执行该函数
                        alert("无法获取数据！(Error Code: 0001)");
                        // myChartTotal.hideLoading();
                    }
                });

            } // if end

            childNamesAforB.splice(0, childNamesAforB.length); // 内存释放，垃圾数据清理
        });

    });

    // 为myChartTotal增加点击事件
    $("#district").change(function () { // 三级按钮（区县）点击事件， total
        // $("#district").val()获取当前所选择区县的id，将区县的城市id复制给areaPid,
        areaPid = $("#district").val();
        console.log( "areaPid " + areaPid ); // 当前父级按钮（城市）id

        // 判断下拉框是否选择到具体的“区/县”
        // 抓取当前市级目录下的子目录（区县目录）
        $.getJSON(localPath + "/DashboardController/jsonTotalTypeId?areaPid=" + ( $("#city").val() ) , function (data) {
            $.each(data, function (index) {
                childNamesBforC.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
            });
            console.log( "childNamesBforC: " + childNamesBforC ); // 当前父级按钮（城市）id

            if ( 0 <= childNamesBforC.indexOf(areaPid) ){ // areaPid 在下拉框中
                // console.log("areaPid: " + areaPid);
                $.getJSON(localPath + "/DashboardController/jsonTotalTypeCNum?areaId=" + areaPid, function (data) {
                    $.each(data, function (index) {
                        childNamesC.push(data[index].ENDPOINT_ID); // 获取下拉框下级菜单的id,
                    });
                    console.log("childNamesC: " + childNamesC);
                    console.log( childNamesC.length );
                });

                $.ajax({
                    type: "post",
                    async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                    url: "/nclk-center/DashboardController/jsonTotalTypeCName", //  考点
                    data: {"areaId": areaPid},
                    dataType: "json", //返回数据形式为json
                    timeout: 1000,
                    success: function (result) {
                        $.each(result, function (index) {
                            districtNames.push(result[index].ENDPOINT_NAME); //挨个取出区名并填入区名数组, X
                            districtNums.push(Math.floor((Math.random() * 30))); // Y, 随机插入数值
                        });
                        // console.log(districtNames);
                        myChartTotal.hideLoading();
                        myChartTotal.setOption({ //加载数据图表
                            xAxis: {
                                data: districtNames
                            },
                            series: [{
                                // 根据名字对应到相应的系列
                                data: districtNums
                            }]
                        });
                        districtNames.splice(0, districtNames.length); // 内存释放，垃圾数据清理
                        districtNums.splice(0, districtNums.length); // 内存释放，垃圾数据清理
                        childNamesC.splice(0, childNamesC.length); // 内存释放，垃圾数据清理
                    },
                    error: function (errorMsg) {
                        //请求失败时执行该函数
                        alert("无法获取数据！(Error Code: 0001)");
                        // myChartTotal.hideLoading();
                    }
                });

            } // if end

            childNamesBforC.splice(0, childNamesBforC.length); // 内存释放，垃圾数据清理
        });

    });

    // myChartTotal, data load end

    // myChartPie, data load start
    myChartPie.showLoading();
    var pieDataXs = [];
    var pieDataYs = [];

    $.ajax({
        type: "POST",
        async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/nclk-center/DashboardController/jsonTestForEcharts.do", //
        dataType: "json", //返回数据形式为json
        timeout: 1000,
        success: function (result) {
            // json file method
            $.each(result, function (name) {
                pieDataXs.push(name); //挨个取出类别并填入类别数组
                pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
            });

            myChartPie.hideLoading();

            myChartPie.setOption({ //加载数据图表
                legend: {
                    data: pieDataXs
                },
                series: [{
                    // 根据名字对应到相应的系列
                    data: [{"value": pieDataYs[0], "name": pieDataXs[0]},
                        {"value": pieDataYs[1], "name": pieDataXs[1]},
                        {"value": pieDataYs[2], "name": pieDataXs[2]}]
                }, {
                    // 根据名字对应到相应的系列
                    data: [{"value": pieDataYs[0], "name": pieDataXs[0]},
                        {"value": pieDataYs[1], "name": pieDataXs[1]},
                        {"value": pieDataYs[2], "name": pieDataXs[2]}]
                }, {
                    // 根据名字对应到相应的系列
                    data: [{"value": pieDataYs[0], "name": pieDataXs[0]},
                        {"value": pieDataYs[1], "name": pieDataXs[1]},
                        {"value": pieDataYs[2], "name": pieDataXs[2]}]
                }]
            });
        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            //alert("无法获取数据！(Error Code: 0002)");
            // myChartPie.hideLoading();
        }
    });
    // myChartPie, data load end


    // myChartLine, data load start
    myChartLine.showLoading();
    var lineDataXs = [];
    var lineDataYs = [];
    var i = 1000;

    for (; i < 1100; i++) {
        lineDataXs.push("0"); // X轴初始化
        lineDataYs.push(0); // Y轴初始化
    }

    //记录定时器ID
    DATA.timeId = window.setInterval(function () {
        $.ajax({
            type: "get",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonLine.do", //
            dataType: "json", //返回数据形式为json
            timeout: 1000,
            success: function (result) {
                // json file method
                $.each(result, function (item) {
                    if ("error" == item) { // 当each遍历到"error"时
                        lineDataYs.splice(0, 1); // 队列栈模式，先进先出
                        lineDataYs.push(result["error"]); //挨个取出数值并填入数值数组， error num
                        // 调用JSON格式的时间
                        // $.getJSON(localPath + "/DashboardController/stringTime", function (data) {
                        //     console.log(data["time"]);
                        // });

                        lineDataXs.splice(0, 1); // 队列栈模式，先进先出
                        // 调用Text格式的时间
                        $.get(localPath + "/DashboardController/stringTime", function (data) {
                            lineDataXs.push(data); //挨个取出类别并填入类别数组
                        });

                    }
                });

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
                alert("无法获取数据！(Error Code: 00031)");
                // myChartLine.hideLoading();
            }
        });
    }, 1000);
    // myChartLine, data load end

});
