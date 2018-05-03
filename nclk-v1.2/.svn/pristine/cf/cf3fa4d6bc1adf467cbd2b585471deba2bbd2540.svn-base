require(['jquery', 'bootstrap', 'echarts', 'Util', 'moment'], function ($, bootstrap, echarts, Util, moment) {
    var localPath = location.pathname.match(/\/(.+?)(?=\/)/g)[0];

    // 解决ie8不支持indexOf， start
    if (!Array.prototype.indexOf) {
        Array.prototype.indexOf = function (elt /*, from*/) {
            var len = this.length >>> 0;
            var from = Number(arguments[1]) || 0;
            from = (from < 0)
                ? Math.ceil(from)
                : Math.floor(from);
            if (from < 0)
                from += len;
            for (; from < len; from++) {
                if (from in this &&
                    this[from] === elt)
                    return from;
            }
            return -1;
        };
    }
    // if end, 解决ie8不支持indexOf

    // 获取定时器刷新时间
    var configLineFreshTime = 2000; // center.dashboard.dashboardRTime=1000
    var configTimestampFreshTime = 59000; // center.dashboard.timestampRTime=10000
    var lineFreshTime = 1000; // center.dashboard.dashboardRTime=1000
    var timestampFreshTime = 10000; // center.dashboard.timestampRTime=10000
    $.ajax({
        type: "post",
        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/nclk-center/DashboardController/getDashboardRTime", //
        dataType: "json", //返回数据形式为json
        timeout: 1000,
        success: function (res) {
            configLineFreshTime = res.bean.dashboardRTime; // 页面加载时，从配置文件获取 ecahrts - line 的刷新时间
            configTimestampFreshTime = res.bean.timestampRTime; // 页面加载时，从配置文件获取 timestamp 的刷新时间
            lineFreshTime = configLineFreshTime; // 本页面的刷新时间
            timestampFreshTime = configTimestampFreshTime; // timestamp 的刷新时间
        }
    });

    function dashClockSet(dashClockTimestamp) {
        // fixed button, now datetime 当前时间, button [2]
        $("#clockinfo-time-now").html((dashClockTimestamp).slice(0, 17) + "<span style='color: red;'>"
            + (dashClockTimestamp).slice(17, 19) + "</span>");
        // fixed button, now datetime 当前时间, button [3]
        $("#log-time").html((dashClockTimestamp).slice(0, 17) + "<span style='color: red;'>"
            + (dashClockTimestamp).slice(17, 19) + "</span>");

        // title, card - time
        $("#dashhead-date-1").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(0, 1) + ".png");
        $("#dashhead-date-2").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(1, 2) + ".png");
        $("#dashhead-date-3").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(2, 3) + ".png");
        $("#dashhead-date-4").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(3, 4) + ".png");
        $("#dashhead-date-5").attr("src", localPath + "/dashboard/img/dash-d.png");
        $("#dashhead-date-6").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(5, 6) + ".png");
        $("#dashhead-date-7").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(6, 7) + ".png");
        $("#dashhead-date-8").attr("src", localPath + "/dashboard/img/dash-d.png");
        $("#dashhead-date-9").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(8, 9) + ".png");
        $("#dashhead-date-10").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(9, 10) + ".png");

        $("#dashhead-time-1").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(11, 12) + ".png");
        $("#dashhead-time-2").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(12, 13) + ".png");
        $("#dashhead-time-3").attr("src", localPath + "/dashboard/img/dash-m.png");
        $("#dashhead-time-4").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(14, 15) + ".png");
        $("#dashhead-time-5").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(15, 16) + ".png");

        $("#dashhead-sec-1").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(17, 18) + ".png");
        $("#dashhead-sec-2").attr("src", localPath + "/dashboard/img/dash-" + (dashClockTimestamp).slice(18, 19) + ".png");
    }

    // clock set
    var dashTimeGap = 0; // 服务器与客户端时间差（服务器 - 客户端）
    var getTimeAcc = 0;
    function getTimestamp() {
        $.ajax({
            type: "post",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/MonitorController/getClockTime", //
            dataType: "json", //返回数据形式为json
            timeout: 800,
            success: function (data) {
                var dashTimestampValue = parseInt(data.bean.timestamp); // 获取服务器时间
                var pageTimestampValue = new Date().getTime(); // 获取当前页面的时间戳
                dashTimeGap = dashTimestampValue - pageTimestampValue;
                getTimeAcc ++; // get time 计数
            }
        });

        // return dashTimeGap;
    }
    // clock
    getTimestamp();
    getTimestamp();
    var clockTimestamp = moment().milliseconds(dashTimeGap).format("YYYY-MM-DD HH:mm:ss");
    var dashTimestampId = window.setInterval(function () {
        getTimestamp();
    }, timestampFreshTime); // 每10秒刷新一次时间戳
    var dashClockRunId = window.setInterval(function () {
        // console.log(dashTimeGap);
        clockTimestamp = moment().milliseconds(dashTimeGap).format("YYYY-MM-DD HH:mm:ss");
        if( 1 <= getTimeAcc ){
            dashClockSet(clockTimestamp);
        }
    }, 1000);

    // main start
    var myChartTotal = echarts.init(document.getElementById('mainTotal'));
    var myChartPie = echarts.init(document.getElementById('mainPie'));
    var myChartLine = echarts.init(document.getElementById('mainLine'));

    // 'show' or 'not show' the legend. 是否展示legend
    var legendMode = true;

    var optionLine = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#969696'
                }
            }
        },
        legend: {
            data: ['当前监控数量', '当前异常数量']
        },
        axisPointer: {
            link: {xAxisIndex: 'all'}
        },
        // dataZoom: [
        //     {
        //         type: 'slider', //slider表示有滑动块的，inside表示内置的
        //         show: true, // 是否显示组件。如果设置为 false，不会显示，但是数据过滤的功能还存在。
        //         realtime: true,
        //         bottom: '0%',
        //         start: 75,
        //         end: 100,
        //         xAxisIndex: 0
        //     }
        // ], // 缩放区域 在ie8下会出现异常
        xAxis: {
            type: 'category',
            data: [],
            // x, line color
            axisLine: {
                lineStyle: {
                    color: '#EFF3F8'
                },
                onZero: true //+
            },
            axisLabel: {
                color: '#555'
            },
            boundarygap: false //+
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
        // color: ['#5BB0F0', '#DE4330', '#FFB880', '#D97A80', '#4DB7AD', '#e5cf0d', '#97b552', '#95706d', '#8d98b3', '#C3B5E1'],
        color: ['#42A5F6', '#F174A0', '#FFB880', '#D97A80', '#4DB7AD', '#e5cf0d', '#97b552', '#95706d', '#8d98b3', '#C3B5E1'],
        grid: {
            left: '4%',
            right: '1%',
            top: '15%',
            // bottom: '12%',// 加入缩放区域时使用该配置
            bottom: '4%',// 加入缩放区域时使用该配置
            containLabel: true
        },
        series: [{
            name: '当前监控数量',
            type: 'line',
            smooth: true,
            xAxisIndex: 0,
            showAllSymbol: false, // 不显示小圆点
            symbolSize: 0, // 去掉小圆点
            data: [],
            lineStyle: {
                normal: {
                    color: '#42A5F6'
                }
            },
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#A8D6FC'},
                            {offset: 0.22, color: '#E5F2FC'},
                            {offset: 1, color: '#fff'}
                        ]
                    )
                }
            }
        }, {
            name: '当前异常数量',
            type: 'line',
            smooth: true,
            xAxisIndex: 0,
            showAllSymbol: false, // 不显示小圆点
            symbolSize: 0, // 去掉小圆点
            data: [],
            lineStyle: {
                normal: {
                    color: '#F174A0'
                }
            },
            areaStyle: {
                normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 0, 1,
                        [
                            {offset: 0, color: '#F6C2D6'},
                            {offset: 0.5, color: '#FAF1F6'},
                            {offset: 1, color: '#fff'}
                        ]
                    )
                }
            }
        }]
    };

    //main21 mainTotal bar
    var optionTotal = {
        title : {
            subtext: '（查询时间）',
            x: 'right',
            y: "-4%",
            align: 'right'
        },
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
            barMaxWidth: 30, //最大宽度
            barMinHeight: 1, //最小高度
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
            show: true,
            subtext: "（默认展示近7天数据）",
            x: "right",
            y: "-4%",
            align: "right",
            textStyle: {
                color: '#555'
            }
        },
        legend: {
            show: legendMode,
            orient: 'vertical',
            x: '10%',
            y: '30%',
            data: [],
            textStyle: {
                color: '#555'
            },
            selectedMode: true
        },
        color: ['#5BB0F0', '#FFB880', '#D97A80', '#4DB7AD', '#e5cf0d', '#97b552', '#95706d', '#8d98b3', '#C3B5E1'],
        series: [{
            name: '异常原因',
            type: 'pie',
            radius: ['38%', '55%'],
            center: ['55%', '50%'],
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
            radius: ['58%', '59%'],
            center: ['55%', '50%'],
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
            radius: ['34%', '35%'],
            center: ['55%', '50%'],
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

    myChartTotal.setOption(optionTotal);
    myChartPie.setOption(optionPie);
    myChartLine.setOption(optionLine);

    // myChartTotal, data load start
    myChartTotal.showLoading({
        animation: false,
        text: "请点击选择按钮",
        textStyle: {
            fontSize: 32
        }
    });
    // myChartTotal, data load start
    myChartPie.showLoading({
        animation: false,
        text: "请点击选择按钮",
        textStyle: {
            fontSize: 32
        }
    });

    // time checkBox pie
    $("#timeRangePie").daterangepicker($.extend({
        autoUpdateInput: false,
        timePicker: true,
        timePicker24Hour: true,
        locale: {
            format: 'YYYY-MM-DD HH:mm',
            applyLabel: "选定",
            cancelLabel: "取消"
        }
    }, "daterangepickerPie")).on('apply.daterangepicker', function (ev, picker) {
        // $(this).val(picker.startDate.format(picker.locale.format) + ' ~ ' + picker.endDate.format(picker.locale.format));
        $(this).val("请查询地区");
        var myChartPieTitle = "(" + picker.startDate.format(picker.locale.format) + ' ~ ' + picker.endDate.format(picker.locale.format) + ")";
        $("#pieEchartCheckTime").text(myChartPieTitle);
        myChartPie.setOption({ // 标记当前时间
            title:{
                subtext: myChartPieTitle
            }
        });
    });

    // var loadTimeoutAcc = 0;
    // var loadTimeoutId = window.setInterval(function () {
    //     loadTimeoutAcc++;
    //     if( 2<=loadTimeoutAcc ){
    //
    //     }
    //     // clearInterval(loadTimeoutId);
    // }, 500);// yanchijiazai

    var pageStatus = 1; // 监控页面状态
    var areaPid = "000000032"; // use for total
    var areaPidPie = "000000032"; // use for pie
    var echartsPieCheckFlag = 0;
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

    // // table x, 保存table的tbody标签 // 该方式不支持ie
    // var tbodyTotal = window.document.getElementById("tbodyTotal");
    // var tbodyPie = window.document.getElementById("tbodyPie");

    if (1 >= areaPidCheckFlag && "province0000a" != $("#provinceTotal").val()) {
        // $("#totalTitleCheckTime").text(moment().format("YYYY-MM-DD HH:mm:ss")); // 标记当前时间
        myChartTotal.setOption({ // 标记当前时间
            title:{
                subtext: moment().format("YYYY-MM-DD HH:mm:ss")
            }
        });
        $.ajax({
            type: "post",
            async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonTotalTypeA", //
            data: {'areaPid': areaPidPie},
            dataType: "json", //返回数据形式为json
            timeout: 1000,
            success: function (result) {
                $.each(result, function (index) {
                    // districtNames.push(result["AREA_NAME"]); //挨个取出区名并填入区名数组, X
                    districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X

                    $.ajax({
                        type: "post",
                        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                        url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
                        data: {"areaName": result[index].AREA_NAME},
                        dataType: "json", //返回数据形式为json
                        timeout: 1000,
                        success: function (data) {
                            if (JSON.stringify(data) == "{}") {
                                districtNums.push(0); // Y, 插入index, Y
                            } else {
                                $.each(data, function (item, num) {
                                    districtNums.push(num); // Y, 插入index, Y
                                });
                            }
                        }
                    });

                });
                mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartTotal.hideLoading();
            }
        });
    }

    // 为myChartTotal增加点击事件
    if ("province0000a" == areaPid) {
        $("#divTotal").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#divTotalPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        $("#mainTotal").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#tablePanelTotal").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tablePanelTotalDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#tablePanelTotalDivPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
    }// if end, 省份按钮选择空白时
    $("#provinceTotal").change(function () { // 一级按钮（省份）点击事件， total
        totalDivInit(); // Div初始化

        checkModA = 1; // 标记省份按钮状态， 开启
        // $("#province").val()获取当前所选择省份的id，将选择的城市id复制给areaPid,
        areaPid = $("#provinceTotal").val();

        if ("" == areaPid) {
            areaPidNullFlag++; // 记录省份选择框，切换选择的次数
        }
        if ("province0000a" != areaPid) {
            areaPidCheckFlag++; // 记录省份选择框，切换到“江苏省”的次数
            areaPidNullFlag++; // 记录省份选择框，切换选择的次数
        }

        // if (( "province0000a" != areaPid && 1 <= areaPidCheckFlag ) || 1 >= areaPidNullFlag) { // 省份选择框第一次切换到“江苏省”或初次访问页面时
        if ("province0000a" != areaPid) { // 省份选择框切换到“江苏省”时
            $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": areaPid}, function (data) {
                $.each(data, function (index) {
                    childNamesA.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });
            }, 'json');

            $.ajax({
                type: "post",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeA", //
                data: {'areaPid': areaPid},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    $.each(result, function (index) {
                        // districtNames.push(result["AREA_NAME"]); //挨个取出区名并填入区名数组, X
                        districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X

                        $.ajax({
                            type: "post",
                            async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                            url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
                            data: {"areaName": result[index].AREA_NAME},
                            dataType: "json", //返回数据形式为json
                            timeout: 1000,
                            success: function (data) {
                                if (JSON.stringify(data) == "{}") {
                                    districtNums.push(0); // Y, 插入index, Y
                                } else {
                                    $.each(data, function (item, num) {
                                        districtNums.push(num); // Y, 插入index, Y
                                    });
                                }
                            }
                        });
                    });
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });
            // myChartTotal, data load end
            // end, 一级按钮（省级）点击事件， total, end
        }
        // if end, 省份按钮选择正常时

        if ("province0000a" == areaPid) {
            $("#divTotal").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#divTotalPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
            $("#mainTotal").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
            $("#tablePanelTotal").css("display", "none"); // echarts中未检测到数据，隐藏table区域
            $("#tablePanelTotalDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#tablePanelTotalDivPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        }
        // if end, 省份按钮选择空白时

    });

    if (1 >= echartsPieCheckFlag && "province0000b" != $("#provincePie").val()) { // echarts-pie 省级按钮 未点击时
        // areaPidPie = $("#provincePie").val();
        mychartPieInit(); // echarts 数据初始化

        $.ajax({
            type: "POST",
            async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonTestForEcharts.do", //
            data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
            dataType: "json", //返回数据形式为json
            timeout: 1000,
            success: function (result) {
                // json file method
                $.each(result, function (name) {
                    pieDataXs.push(name); //挨个取出类别并填入类别数组
                    // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                    dataMap = {"name": name, "value": result[name]};
                    datas.push(dataMap);
                });
                mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartPie.hideLoading();
            }
        });
        // pie end

        tablePieLoadData(pieDataXs, datas); // table加载数据
    }

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    if ("province0000b" == areaPidPie) {
        $("#divPie").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#divPiePro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
        $("#pieEchartCheckTime").css("display", "block"); // echarts中未检测到数据，显示时间戳
        $("#mainPie").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#tableBorderPie").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tableBorderPieDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#tableBorderPieDivPro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
    } // if end, 省份按钮选择空白时
    $("#provincePie").change(function () {
        pieDivInit(); // Div初始化
        $("#divPiePro").css("display", "none"); //初始化
        $("#tableBorderPieDivPro").css("display", "none"); //初始化
        echartsPieCheckFlag += 1;

        areaPidPie = $("#provincePie").val();

        if ("province0000b" != areaPidPie) {
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "post",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEcharts.do", //
                data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    // json file method
                    $.each(result, function (name) {
                        pieDataXs.push(name); //挨个取出类别并填入类别数组
                        // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                        dataMap = {"name": name, "value": result[name]};
                        datas.push(dataMap);
                    });
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                }
            });
            // pie end

            tablePieLoadData(pieDataXs, datas); // table加载数据
        }
        // if end, 省份按钮选择正常时

        if ("province0000b" == areaPidPie) {
            $("#divPie").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#divPiePro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
            $("#pieEchartCheckTime").css("display", "block"); // echarts中未检测到数据，显示时间戳
            $("#mainPie").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
            $("#tableBorderPie").css("display", "none"); // echarts中未检测到数据，隐藏table区域
            $("#tableBorderPieDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#tableBorderPieDivPro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
        }
        // if end, 省份按钮选择空白时

    });
    // myChartPie, data load end

    // 为myChartTotal增加点击事件
    $("#cityTotal").change(function () { // 二级按钮（城市）点击事件， total
        totalDivInit(); // Div初始化

        // $("#city").val()获取当前所选择城市的id，将选择的城市id复制给areaPid,
        areaPid = $("#cityTotal").val();
        // // 检测到选择空白按钮时，返回上级
        if ("city0000a" == areaPid) {
            areaPid = $("#provinceTotal").val();

            $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": areaPid}, function (data) {
                $.each(data, function (index) {
                    childNamesA.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });
            }, 'json');

            $.ajax({
                type: "post",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeA", //
                data: {'areaPid': areaPid},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    $.each(result, function (index) {
                        // districtNames.push(result["AREA_NAME"]); //挨个取出区名并填入区名数组, X
                        districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X

                        $.ajax({
                            type: "post",
                            async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                            url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
                            data: {"areaName": result[index].AREA_NAME},
                            dataType: "json", //返回数据形式为json
                            timeout: 1000,
                            success: function (data) {
                                if (JSON.stringify(data) == "{}") {
                                    districtNums.push(0); // Y, 插入index, Y
                                } else {
                                    $.each(data, function (item, num) {
                                        districtNums.push(num); // Y, 插入index, Y
                                    });
                                }
                            }
                        });
                    });
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });
        }
        // if end, 选择市级空白按钮 执行省级策略

        if ("city0000a" != areaPid) {
            var provinceId = $("#provinceTotal").val();
            // 判断下拉框是否选择到具体的“市”（借助省级选择框的value）
            $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": provinceId}, function (data) {
                $.each(data, function (index) {
                    childNamesAforB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });

                if (0 <= childNamesAforB.indexOf(areaPid)) { // areaPid 在下拉框中、选择空白按钮
                    $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": areaPid}, function (data) {
                        $.each(data, function (index) {
                            childNamesB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                        });
                    }, 'json');

                    $.ajax({
                        type: "post",
                        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                        url: "/nclk-center/DashboardController/jsonTotalTypeBName", // 通过当前城市id（areaPid）获取区县级name
                        data: {'areaPid': areaPid},
                        dataType: "json", //返回数据形式为json
                        timeout: 1000,
                        success: function (result) {
                            $.each(result, function (index) {
                                districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X

                                $.ajax({
                                    type: "post",
                                    async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                                    url: "/nclk-center/DashboardController/jsonTotalTypeCErrname", //
                                    data: {"areaName": result[index].AREA_NAME, "areaPid": areaPid},
                                    dataType: "json", //返回数据形式为json
                                    timeout: 1000,
                                    success: function (data) {
                                        if (JSON.stringify(data) == "{}") {
                                            districtNums.push(0); // Y, 插入index, Y
                                        } else {
                                            $.each(data, function (item, num) {
                                                districtNums.push(num); // Y, 插入index, Y
                                            });
                                        }
                                    }
                                });
                            });
                            mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                        },
                        error: function (errorMsg) {
                            //请求失败时执行该函数
                            Util.dashboardErrorDialog("当前网络出现异常！");
                            // myChartTotal.hideLoading();
                        }
                    });

                } // if end

                childNamesAforB.splice(0, childNamesAforB.length); // 内存释放，垃圾数据清理
            }, 'json');

            // myChartTotal, data load end
            // end, 二级按钮（市级）点击事件， total, end
        }
        // if end, 选择市级空白按钮 执行市级策略
    });

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    $("#cityPie").change(function () {
        pieDivInit(); // Div初始化
        $("#divPiePro").css("display", "none"); //初始化
        $("#tableBorderPieDivPro").css("display", "none"); //初始化
        areaPidPie = $("#cityPie").val();

        if ("city0000b" == areaPidPie) {
            areaPidPie = $("#provincePie").val();
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "POST",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEcharts.do", //
                data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    // json file method
                    $.each(result, function (name) {
                        pieDataXs.push(name); //挨个取出类别并填入类别数组
                        // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                        dataMap = {"name": name, "value": result[name]};
                        datas.push(dataMap);
                    });
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                }
            });
            // pie end

            tablePieLoadData(pieDataXs, datas); // table加载数据
        }
        // if end, 市级按钮选择空白时

        if ("city0000b" != areaPidPie && $("#provincePie").val() != areaPidPie) {
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "POST",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsB", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    // json file method
                    $.each(result, function (name) {
                        pieDataXs.push(name); //挨个取出类别并填入类别数组
                        // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                        dataMap = {"name": name, "value": result[name]};
                        datas.push(dataMap);
                    });
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                }
            });
            // pie end

            tablePieLoadData(pieDataXs, datas); // table加载数据
        }
        // if end, 市级按钮选择正常时
    });
    // myChartPie, data load end

    // 为myChartTotal增加点击事件
    $("#districtTotal").change(function () {
        totalDivInit(); // Div初始化

        // start, 三级按钮（区县）点击事件， total, start
        // $("#district").val()获取当前所选择区县的id，将区县的城市id复制给areaPid,
        areaPid = $("#districtTotal").val();
        if ("district0000a" == areaPid) {
            areaPid = $("#cityTotal").val();

            var provinceId = $("#provinceTotal").val();
            // 判断下拉框是否选择到具体的“市”（借助省级选择框的value）
            $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": provinceId}, function (data) {
                $.each(data, function (index) {
                    childNamesAforB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });

                if (0 <= childNamesAforB.indexOf(areaPid)) { // areaPid 在下拉框中、选择空白按钮
                    $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": areaPid}, function (data) {
                        $.each(data, function (index) {
                            childNamesB.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                        });
                    }, 'json');

                    $.ajax({
                        type: "post",
                        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                        url: "/nclk-center/DashboardController/jsonTotalTypeBName", // 通过当前城市id（areaPid）获取区县级name
                        data: {'areaPid': areaPid},
                        dataType: "json", //返回数据形式为json
                        timeout: 1000,
                        success: function (result) {
                            $.each(result, function (index) {
                                districtNames.push(result[index].AREA_NAME); //挨个取出区名并填入区名数组, X

                                $.ajax({
                                    type: "post",
                                    async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                                    url: "/nclk-center/DashboardController/jsonTotalTypeCErrname", //
                                    data: {"areaName": result[index].AREA_NAME, "areaPid": areaPid},
                                    dataType: "json", //返回数据形式为json
                                    timeout: 1000,
                                    success: function (data) {
                                        if (JSON.stringify(data) == "{}") {
                                            districtNums.push(0); // Y, 插入index, Y
                                        } else {
                                            $.each(data, function (item, num) {
                                                districtNums.push(num); // Y, 插入index, Y
                                            });
                                        }
                                    }
                                });
                            });
                            mychartTotalLoadData(districtNames, districtNums); // echarts加载数据，table加载数据
                        },
                        error: function (errorMsg) {
                            //请求失败时执行该函数
                            Util.dashboardErrorDialog("当前网络出现异常！");
                            // myChartTotal.hideLoading();
                        }
                    });

                } // if end

                childNamesAforB.splice(0, childNamesAforB.length); // 内存释放，垃圾数据清理
            }, 'json');

            // myChartTotal, data load end
            // end, 二级按钮（市级）点击事件， total, end
        }
        // if end, 区县选择按钮选择空白时
        if ("district0000a" != areaPid) {
            // 判断下拉框是否选择到具体的“区/县”
            // 抓取当前市级目录下的子目录（区县目录）
            // $.ajaxSettings.async = false; //重要设置, 同步请求
            $.post(localPath + "/DashboardController/jsonTotalTypeId", {"areaPid": ( $("#cityTotal").val() )}, function (data) {
                $.each(data, function (index) {
                    childNamesBforC.push(data[index].AREA_ID); // 获取下拉框下级菜单的id
                });

                if (0 <= childNamesBforC.indexOf(areaPid) || "district0000a" == areaPid) { // areaPid 在下拉框中、选择空白按钮
                    $.post(localPath + "/DashboardController/jsonTotalTypeCId", {"areaId": areaPid}, function (data) {
                        $.each(data, function (index) {
                            childNamesC.push(data[index].ENDPOINT_ID); // 获取下拉框下级菜单的id,
                        });
                    }, 'json');

                    $.ajax({
                        type: "post",
                        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                        url: "/nclk-center/DashboardController/jsonTotalTypeCName", //  考点
                        data: {"areaId": areaPid},
                        dataType: "json", //返回数据形式为json
                        timeout: 1000,
                        success: function (result) {
                            $.each(result, function (index) { // 获取考点name
                                districtNames.push(result[index].ENDPOINT_NAME); //X, 挨个取出区名并填入区名数组, X

                                $.ajax({
                                    type: "post",
                                    async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                                    url: "/nclk-center/DashboardController/jsonTotalTypeDErrname", //
                                    data: {"endpointName": result[index].ENDPOINT_NAME},
                                    dataType: "json", //返回数据形式为json
                                    timeout: 1000,
                                    success: function (data) {
                                        if ("{}" == JSON.stringify(data)) { //数据库无数据时插入0
                                            districtNums.push(0);
                                        }
                                        $.each(data, function (item, num) { // 说明：数据库无数据时，该语句不会执行。
                                            districtNums.push(num); // Y, 插入index, Y
                                        });
                                    }
                                });
                            });
                            mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                        },
                        error: function (errorMsg) {
                            //请求失败时执行该函数
                            Util.dashboardErrorDialog("当前网络出现异常！");
                            // myChartTotal.hideLoading();
                        }
                    });

                } // if end

                childNamesBforC.splice(0, childNamesBforC.length); // 内存释放，垃圾数据清理
            }, 'json');
            // myChartTotal, data load end
            // end, 三级按钮（区县）点击事件， total, end
        }
        // if end, 区县选择按钮选择正常时

    });

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    $("#districtPie").change(function () {
        pieDivInit(); // 初始化Div
        areaPidPie = $("#districtPie").val();

        if ("district0000b" == areaPidPie) {
            areaPidPie = $("#cityPie").val();
            mychartPieInit(); // echarts 数据初始化
            $.ajax({
                type: "POST",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsB", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    // json file method
                    $.each(result, function (name) {
                        pieDataXs.push(name); //挨个取出类别并填入类别数组
                        // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                        dataMap = {"name": name, "value": result[name]};
                        datas.push(dataMap);
                    });
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                }
            });
            // pie end
            tablePieLoadData(pieDataXs, datas); // table加载数据
        }
        // if end, 区县按钮选择空白时

        if ("district0000b" != areaPidPie && $("#cityPie").val() != areaPidPie) {
            mychartPieInit();
            $.ajax({
                type: "POST",
                async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsC", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 1000,
                success: function (result) {
                    // json file method
                    $.each(result, function (name) {
                        pieDataXs.push(name); //挨个取出类别并填入类别数组
                        // pieDataYs.push(result[name]); //挨个取出销量并填入销量数组
                        dataMap = {"name": name, "value": result[name]};
                        datas.push(dataMap);
                    });

                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                }
            });
            // pie end
            tablePieLoadData(pieDataXs, datas); // table加载数据
        }
        // if end, 区县按钮选择正常时时

    });
    // myChartPie, data load end

    // myChartLine, data load start
    myChartLine.showLoading({
        animation: false,
        text: "加载中...",
        textStyle: {
            fontSize: 32
        }
    });
    var lineDataXs = [];
    var lineDataYs = [];
    var lineDataYSums = [];
    var i = 0;

    for (; i < 150; i++) {
        lineDataXs.push(""); // X轴初始化
        lineDataYs.push("零"); // Y轴初始化
        lineDataYSums.push("零"); // Y轴sum线初始化
    }
    var i = 0;

    // 千位数
    function toThousands(num) {
        var result = '', counter = 0;
        num = (num || 0).toString();
        for (var i = num.length - 1; i >= 0; i--) {
            counter++;
            result = num.charAt(i) + result;
            if (!(counter % 3) && i != 0) {
                result = ',' + result;
            }
        }
        return result;
    }

    // var dashTimestamp = new Date(1524450530868); // 2018-04-23 10:28:50
    var myLineAcc = 0;
    var myLineInterval = function () {
        clearInterval(DATA.lineInterval);

        //记录定时器ID
        DATA.timeId = DATA.lineInterval;

        $.ajax({
            type: "post",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/monitorCount", // jsonline.do
            dataType: "json", //返回数据形式为json
            timeout: 800,
            success: function (result) {
                pageStatus = 1; //
                lineFreshTime = configLineFreshTime; // 正常请求时间频率（从config文件中获取）

                var dashcardErrorNum = (result.bean).errorCount;
                var dashcardEnableNum = (result.bean).enableTotal;
                var dashcardDisableNum = (result.bean).disableTotal;
                var dashcardNormalNum = (result.bean).normalCount;

                var dashcardTotalNum = parseInt(dashcardEnableNum) + parseInt(dashcardDisableNum) // 共接入时钟数
                var dashcardMonitorNum = parseInt(dashcardEnableNum); // 已启用时钟数

                var dashcardTotal = toThousands(parseInt(dashcardTotalNum));
                var dashcardMonitor = toThousands(parseInt(dashcardMonitorNum));
                var dashcardNormal = toThousands(parseInt(dashcardNormalNum));
                var dashcardError = toThousands(parseInt(dashcardErrorNum));
                var dashcardDisable = toThousands(parseInt(dashcardDisableNum));

                // card
                $("#total-num").html(dashcardTotal);
                $("#disable-num").html(dashcardDisable);
                $("#normal-num").html(dashcardNormal);
                $("#error-num").html(dashcardError);

                // fixed button
                $("#clockinfo-innum").html(dashcardTotal);
                $("#clockinfo-onnum").html(dashcardMonitor);
                $("#clockinfo-offnum").html(dashcardDisable);
                $("#log-errornum").html(dashcardError);


                lineDataYs.splice(i, 1, dashcardErrorNum); // 队列栈模式，先进先出 / 堆模式，后进先出
                var linesums = parseInt(dashcardNormalNum) + parseInt(dashcardErrorNum) + parseInt(dashcardDisableNum); // 当前监控数量 （不计算停用时钟）
                lineDataYSums.splice(i, 1, linesums ); // Y轴sum线， 同 <-

                if ( 0 == myLineAcc % 60){
                    getTimestamp();
                    getTimestamp();
                }
                myLineAcc ++; // refresh timestamp 计数
                if ( 1 <= getTimeAcc ){
                    var lineTimestamp = moment().milliseconds(dashTimeGap).format("YYYY-MM-DD HH:mm:ss"); // echarts - line 时间戳
                    lineDataXs.splice(i, 1, (lineTimestamp).slice(11, 19)); // 队列栈模式，先进先出 / 堆模式，后进先出
                    // lineDataXs.unshift(data); //挨个取出类别并填入类别数组, X - unshift：插入头；push：插入尾
                }
                i++;

                myChartLine.hideLoading();
                myChartLine.setOption({ //加载数据图表
                    xAxis: {
                        data: lineDataXs
                    },
                    series: [{
                        // 根据名字对应到相应的系列，当前监控数量
                        data: lineDataYSums
                    }, {
                        // 根据名字对应到相应的系列，当前异常数量
                        data: lineDataYs
                    }]
                });
            },
            error: function (errorMsg) {
                pageStatus = 0;
                lineFreshTime += 5000; // 出现异常后 减少ajax请求频率(echarts - line)
                //请求失败时执行该函数
                // Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartLine.hideLoading();
                DATA.lineInterval = setInterval(myLineInterval, lineFreshTime); // 递归调用
            }
        });

        // DATA.lineInterval = setInterval(myLineInterval, lineFreshTime); // 递归调用
    };
    DATA.lineErrIntervals = setInterval(myLineInterval, lineFreshTime); // 出现异常则慢速请求
    // myChartLine, data load end

    var timeId = window.setInterval(function () {
        if (0 == pageStatus) {
            Util.dashboardErrorDialog("当前网络出现异常！");
        }

        if (1 == pageStatus) {
            pageStatus = 999; // 执行标记
        }
    }, 10000);

    function pieDivInit() {
        $("#divPie").css("display", "none"); // 初始化
        $("#mainPie").css("display", "block"); //初始化
        $("#pieEchartCheckTime").css("display", "none"); // 初始化
        $("#tableBorderPie").css("display", "block"); //初始化
        $("#tableBorderPieDiv").css("display", "none"); //初始化
    }

    function pieDivChange() {
        $("#divPie").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        $("#pieEchartCheckTime").css("display", "block"); // echarts中未检测到数据，显示时间戳
        $("#mainPie").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#tableBorderPie").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tableBorderPieDiv").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
    }

    function totalDivInit() {
        $("#divTotal").css("display", "none"); // 初始化
        $("#divTotalPro").css("display", "none"); // 初始化
        $("#mainTotal").css("display", "block"); // 初始化
        $("#tablePanelTotal").css("display", "block"); //初始化
        $("#tablePanelTotalDiv").css("display", "none"); //初始化
        $("#tablePanelTotalDivPro").css("display", "none"); //初始化

        if ( 1<= getTimeAcc ) {
            // $("#totalTitleCheckTime").text(moment().format("YYYY-MM-DD HH:mm:ss")); // 标记当前时间
            myChartTotal.setOption({ // 标记当前时间
                title:{
                    subtext: moment().milliseconds(dashTimeGap).format("YYYY-MM-DD HH:mm:ss")
                }
            });
        }
    }

    function totalDivChange() {
        $("#mainTotal").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#divTotal").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        totalTableNone(); // echarts中未检测到数据，隐藏table区域,显示“暂无数据”
    }

    function totalTableNone() {
        $("#tablePanelTotal").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tablePanelTotalDiv").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
    }

    function mychartTotalLoadData(districtNames, districtNums) {
        // echarts 加载数据
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

        if (0 == districtNames.length) {
            totalDivChange(); // 提示信息
        }

        // table加载数据
        var tbodyContent = "";
        var numsSum = 0;
        var numsPer = 0;
        var numsPers = [];

        // 计算numsSum， 异常数量和
        for (var i = 0; i < districtNums.length; i++) {
            numsSum += districtNums[i];
        }

        // 计算numsPers， 异常百分比
        for (var i = 0; i < districtNums.length; i++) {
            numsPer = Number(districtNums[i] / numsSum * 100).toFixed(1);
            numsPer += "%";
            numsPers.push(numsPer);
            numsPer = 0;
        }

        for (var i = 0; i < districtNames.length; i++) {
            if (0 < districtNums[i]) {
                tbodyContent += "<tr>" +
                    "<td>" + districtNames[i] + "</td>" +
                    "<td style='text-align: center'>" + districtNums[i] + "</td>" +
                    "<td style='text-align: center'>" + numsPers[i] + "</td>" +
                    "</tr>";
            }
        }
        // tbodyTotal.innerHTML = tbodyContent; // 该写法不支持ie

        // 若无异常，则不显示table
        if ("" != tbodyContent) {
            $("#tbodyTotal").html(tbodyContent);
        } else {
            totalTableNone(); // echarts中未检测到数据，隐藏table区域,显示“暂无数据”
        }

        // table事件, end

        districtNames.splice(0, districtNames.length); // 内存释放，垃圾数据清理
        districtNums.splice(0, districtNums.length); // 内存释放，垃圾数据清理
        childNamesC.splice(0, childNamesC.length); // 内存释放，垃圾数据清理
    }

    // ecahrts - pie 数据初始化
    var pieDataXs = []; // legend
    var dataMap = {}; // for data
    var datas = []; // data
    var timeRangePie = $("#timeRangePie");
    var dpPie = timeRangePie.data("daterangepicker"); // 取值
    if (timeRangePie.val() != '' && timeRangePie.val() != null) {
        var startTimePie = dpPie.startDate.format("YYYY-MM-DD HH:mm:SS");
        var endTimePie = dpPie.endDate.format("YYYY-MM-DD HH:mm:SS");
    }
    function mychartPieInit() {
        // pie start
        myChartPie.showLoading();
        pieDataXs = []; // legend
        dataMap = {}; // for data
        datas = []; // data

        timeRangePie = $("#timeRangePie");
        dpPie = timeRangePie.data("daterangepicker"); // 取值
        if (timeRangePie.val() != '' && timeRangePie.val() != null) {
            startTimePie = dpPie.startDate.format("YYYY-MM-DD HH:mm:SS");
            endTimePie = dpPie.endDate.format("YYYY-MM-DD HH:mm:SS");
        }
    }
    function mychartPieLoadData(pieDataXs, datas){
        myChartPie.hideLoading();
        myChartPie.setOption({ //加载数据图表
            legend: {
                data: pieDataXs
            },
            series: [{
                // 根据名字对应到相应的系列
                data: datas
            }, {
                // 根据名字对应到相应的系列
                data: datas
            }, {
                // 根据名字对应到相应的系列
                data: datas
            }]
        });

        if (0 == pieDataXs.length || "district0000b" == areaPidPie) { // 无数据或选择空白按钮
            pieDivChange(); // 提示信息
        }
    }

    function tablePieLoadData(pieDataXs, datas) {
        // table 事件, pie
        var tbodyContent = "";
        var numsSum = 0;
        var numsPer = 0;
        var numsPers = [];

        // 计算numsSum， 异常数量和
        for (var i = 0; i < pieDataXs.length; i++) {
            numsSum += datas[i].value;
        }

        // 计算numsPers， 异常百分比
        for (var i = 0; i < pieDataXs.length; i++) {
            numsPer = Number(datas[i].value / numsSum * 100).toFixed(1);
            numsPer += "%";
            numsPers.push(numsPer);
            numsPer = 0;
        }

        for (var i = 0; i < pieDataXs.length; i++) {
            if (0 < datas[i].value) {
                tbodyContent += "<tr>" +
                    "<td>" + pieDataXs[i] + "</td>" +
                    "<td style='text-align: center'>" + datas[i].value + "</td>" +
                    "<td style='text-align: center'>" + numsPers[i] + "</td>" +
                    "</tr>";
            }
        }
        // tbodyPie.innerHTML = tbodyContent; // 该写法不支持ie
        $("#tbodyPie").html(tbodyContent);
        // table事件， end
    }

    DATA.intervals.push(dashTimestampId);
    DATA.intervals.push(dashClockRunId);
    DATA.intervals.push(timeId);
});
