require(['jquery', 'bootstrap', 'echarts', 'Util', 'moment'], function ($, bootstrap, echarts, Util, moment) {
    var localPath = location.pathname.match(/\/(.+?)(?=\/)/g)[0];

    var dashClockinfo = $("#clockinfo-form");
    var dashLoginfo = $("#loginfo-form");

    var timeRangeTotal = $("#dashTimeRange");
    var dpTotal = timeRangeTotal.data("daterangepicker"); // 取值
    var startTimeTotal = "";
    var endTimeTotal = "";
    if (timeRangeTotal.val() != '' && timeRangeTotal.val() != null) {
        startTimeTotal = dpTotal.startDate.format("YYYY-MM-DD HH:mm:SS");
        endTimeTotal = dpTotal.endDate.format("YYYY-MM-DD HH:mm:SS");
    }

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
        async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/nclk-center/DashboardController/getDashboardRTime", //
        dataType: "json", //返回数据形式为json
        timeout: 5000,
        success: function (res) {
            configLineFreshTime = res.bean.dashboardRTime; // 页面加载时，从配置文件获取 ecahrts - line 的刷新时间
            configTimestampFreshTime = res.bean.timestampRTime; // 页面加载时，从配置文件获取 timestamp 的刷新时间
            lineFreshTime = configLineFreshTime; // 本页面的刷新时间
            timestampFreshTime = configTimestampFreshTime; // timestamp 的刷新时间
        }
    });

    function dashClockSet(dashClockTimestamp) {
        // fixed button, now datetime 当前时间, button [2]
        dashClockinfo.find("#clockinfo-time-now").html((dashClockTimestamp).slice(0, 17) + "<span style='color: red;'>"
            + (dashClockTimestamp).slice(17, 19) + "</span>");
        // fixed button, now datetime 当前时间, button [3]
        dashLoginfo.find("#log-time").html((dashClockTimestamp).slice(0, 17) + "<span style='color: red;'>"
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
                getTimeAcc++; // get time 计数
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
        clockTimestamp = moment().milliseconds(dashTimeGap).format("YYYY-MM-DD HH:mm:ss");
        if (1 <= getTimeAcc) {
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
        animation: false, // 取消动画效果，适配ie8、防止数据量过多导致的图形抖动
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
            x: '76%',
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
            // name: '当前异常数量',
            position: 'left',
            axisLabel: {
                formatter: '{value}',
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
            smooth: false,
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
            smooth: false,
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
        title: {
            subtext: "（默认展示近30天数据）",
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
            // name: '异常次数',
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
            subtext: "（默认展示近30天数据）",
            x: "right",
            y: "-4%",
            align: "right",
            textStyle: {
                color: '#555'
            }
        },
        legend: {
            // show: legendMode,
            show: false,
            orient: 'vertical',
            x: '10%',
            y: '70%',
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
            radius: ['58%', '59%'],
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
            radius: ['34%', '35%'],
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

    // Util.datecustomRangePicker();
    // time checkBox pie
    $(".dateCustom-remove").click(function(){
        $(this).parent().find(".dateCustom").val("");
        startTimeTotal = "";
        endTimeTotal = "";
        myChartPie.setOption({ // 更新提示
            title:{
                subtext: "（默认展示近30天数据）"
            }
        });
        myChartTotal.setOption({ // 更新提示
            title: {
                subtext: "（默认展示近30天数据）"
            }
        });
        $("#pieEchartCheckTime").text("（默认展示近30天数据）");
    });
    $(".dateCustom-wrapper .dateCustom").daterangepicker($.extend({
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
        var myChartTitle = "(" + picker.startDate.format(picker.locale.format) + ' ~ ' + picker.endDate.format(picker.locale.format) + ")";
        $("#pieEchartCheckTime").text(myChartTitle);
        $(this).val(picker.startDate.format(picker.locale.format) + ' ~ ' + picker.endDate.format(picker.locale.format));
        myChartPie.setOption({ // 标记当前时间
            title:{
                subtext: myChartTitle
            }
        });
        myChartTotal.setOption({ // 标记当前时间
            title: {
                subtext: myChartTitle
            }
        });

        // 点击确定时 亦更新echarts
        var pickerProvince = $("#dashProvince").val();
        var pickerCity = $("#dashCity").val();
        var pickerDistrict = $("#dashDistrict").val();
        if("district0000" != pickerDistrict){
            $("#dashDistrict").change();
        }
        if("district0000" == pickerDistrict && "city0000" != pickerCity){
            $("#dashCity").change();
        }
        if("district0000" == pickerDistrict && "city0000" == pickerCity && "province0000" != pickerProvince){
            $("#dashProvince").change();
        }
    });

    var pageStatus = 1; // 监控页面状态
    var areaPid = "000000032"; // use for total
    var areaPidPie = "000000032"; // use for pie
    var echartsPieCheckFlag = 0;
    var areaPidNullFlag = 0;
    var areaPidCheckFlag = 0;
    var checkModA = 1; // 省份按钮状态, 1: 开启，2: 关闭
    var districtNames = []; // X
    var districtNums = []; // Y

    var childDistrictNames = []; // 名字
    var childDistrictSBCS = []; //上报超时
    var childDistrictWLYC = []; //网络异常（原->时钟掉线）
    var childDistrictSJWC = []; //时间误差


    if (1 >= areaPidCheckFlag && "province0000" != $("#dashProvince").val()) { // 初始化选择具体的省时
        // $("#totalTitleCheckTime").text(moment().format("YYYY-MM-DD HH:mm:ss")); // 标记当前时间
        totalGetDatetime(); // 获取startTimeTotal，endTimeTotal

        areaPid = $("#dashProvince").val();
        $.ajax({
            type: "post",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
            data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
            dataType: "json", //返回数据形式为json
            timeout: 5000,
            success: function (result) {
                for (var i = 0; i < result.bean.length; i++) {
                    districtNames.push(result.bean[i].DISTRICT_NAME); // X
                    districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                }
                mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartTotal.hideLoading();
                totalDivChange(); // 提示信息
            }
        });

        $.ajax({
            type: "post",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonTotalTypeBErrInfo", //
            data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
            dataType: "json", //返回数据形式为json
            timeout: 5000,
            success: function (result) {
                for (var i = 0; i < result.bean.length; i++) {
                    childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                    childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                    childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                    childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                }
                tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartTotal.hideLoading();
            }
        });
    }
    // 为myChartTotal增加点击事件
    if ("province0000" == areaPid) {
        $("#divTotal").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#divTotalPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        $("#mainTotal").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#tablePanelTotal").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tablePanelTotalDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#tablePanelTotalDivPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
    }// if end, 省份按钮选择空白时

    $("#dashProvince").change(function () { // 一级按钮（省份）点击事件， total
        totalDivInit(); // Div初始化
        totalGetDatetime(); // 获取startTimeTotal，endTimeTotal

        checkModA = 1; // 标记省份按钮状态， 开启
        // $("#province").val()获取当前所选择省份的id，将选择的城市id复制给areaPid,
        areaPid = $("#dashProvince").val();

        if ("" == areaPid) {
            areaPidNullFlag++; // 记录省份选择框，切换选择的次数
        }
        if ("province0000a" != areaPid) {
            areaPidCheckFlag++; // 记录省份选择框，切换到“江苏省”的次数
            areaPidNullFlag++; // 记录省份选择框，切换选择的次数
        }

        // if (( "province0000a" != areaPid && 1 <= areaPidCheckFlag ) || 1 >= areaPidNullFlag) { // 省份选择框第一次切换到“江苏省”或初次访问页面时
        if ("province0000" != areaPid) { // 省份选择框切换到“江苏省”时
            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        districtNames.push(result.bean[i].DISTRICT_NAME); // X
                        districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                    }
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                    totalDivChange(); // 提示信息
                }
            });

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeBErrInfo", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                        childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                        childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                        childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                    }
                    tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });
        }
        // if end, 省份按钮选择正常时 total

        if ("province0000" == areaPid) {
            $("#divTotal").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#divTotalPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
            $("#mainTotal").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
            $("#tablePanelTotal").css("display", "none"); // echarts中未检测到数据，隐藏table区域
            $("#tablePanelTotalDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#tablePanelTotalDivPro").css("display", "block"); // echarts中未检测到数据，显示“暂无数据”
        }
        // if end, 省份按钮选择空白时 total
    });

    if (1 >= echartsPieCheckFlag && "province0000" != $("#dashProvince").val()) { // echarts-pie 省级按钮 初始化选择具体的省时
        // areaPidPie = $("#dashProvince").val();
        mychartPieInit(); // echarts 数据初始化

        $.ajax({
            type: "POST",
            async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
            url: "/nclk-center/DashboardController/jsonTestForEcharts", //
            data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
            dataType: "json", //返回数据形式为json
            timeout: 5000,
            success: function (result) {
                pieDataXs = [];
                datas = [];
                // json file method
                for (var i = 0; i < result.bean.length; i++) {
                    pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                    dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                    datas.push(dataMap);
                }
                mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
            },
            error: function (errorMsg) {
                //请求失败时执行该函数
                // Util.dashboardErrorDialog("当前网络出现异常！");
                // myChartPie.hideLoading();
                pieDivChange(); //提示信息
            }
        });
        // pie end

    }

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    if ("province0000" == areaPidPie) {
        $("#divPie").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#divPiePro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
        $("#pieEchartCheckTime").css("display", "block"); // echarts中未检测到数据，显示时间戳
        $("#mainPie").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
        $("#tableBorderPie").css("display", "none"); // echarts中未检测到数据，隐藏table区域
        $("#tableBorderPieDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
        $("#tableBorderPieDivPro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
    } // if end, 省份按钮选择空白时
    $("#dashProvince").change(function () {
        pieDivInit(); // Div初始化
        $("#divPiePro").css("display", "none"); //初始化
        $("#tableBorderPieDivPro").css("display", "none"); //初始化
        echartsPieCheckFlag += 1;

        areaPidPie = $("#dashProvince").val();

        if ("province0000" != areaPidPie) {
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEcharts", //
                data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    pieDataXs = [];
                    datas = [];
                    // json file method
                    for (var i = 0; i < result.bean.length; i++) {
                        pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                        dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                        datas.push(dataMap);
                    }
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    // Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                    pieDivChange(); //提示信息
                }
            });
            // pie end

        }
        // if end, 省份按钮选择正常时 pie

        if ("province0000" == areaPidPie) {
            $("#divPie").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#divPiePro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
            $("#pieEchartCheckTime").css("display", "block"); // echarts中未检测到数据，显示时间戳
            $("#mainPie").css("display", "none"); // echarts中未检测到数据，隐藏echarts区域
            $("#tableBorderPie").css("display", "none"); // echarts中未检测到数据，隐藏table区域
            $("#tableBorderPieDiv").css("display", "none"); // echarts中未检测到数据，显示“暂无数据”
            $("#tableBorderPieDivPro").css("display", "block"); // echarts中未检测到数据，显示“请选择区域范围”
        }
        // if end, 省份按钮选择空白时 pie

    });
    // myChartPie, data load end

    // 为myChartTotal增加点击事件
    $("#dashCity").change(function () { // 二级按钮（城市）点击事件， total
        totalDivInit(); // Div初始化
        totalGetDatetime(); // 获取startTimeTotal，endTimeTotal

        // $("#city").val()获取当前所选择城市的id，将选择的城市id复制给areaPid,
        areaPid = $("#dashCity").val();
        // // 检测到选择空白按钮时，返回上级
        if ("city0000" == areaPid) {
            var areaPidNew = $("#dashProvince").val();

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeBErrname", //
                data: {"areaId": areaPidNew, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        districtNames.push(result.bean[i].DISTRICT_NAME); // X
                        districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                    }
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                    totalDivChange(); // 提示信息
                }
            });

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeBErrInfo", //
                data: {"areaId": areaPidNew, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                        childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                        childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                        childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                    }
                    tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });

        }
        // if end, 选择市级空白按钮 执行省级策略

        if ("city0000" != areaPid) {
            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeCErrname", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        districtNames.push(result.bean[i].DISTRICT_NAME); // X
                        districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                    }
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                    totalDivChange(); // 提示信息
                }
            });

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeCErrInfo", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                        childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                        childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                        childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                    }
                    tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });

        }
        // if end, 选择市级空白按钮 执行市级策略
    });

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    $("#dashCity").change(function () {
        pieDivInit(); // Div初始化
        $("#divPiePro").css("display", "none"); //初始化
        $("#tableBorderPieDivPro").css("display", "none"); //初始化
        areaPidPie = $("#dashCity").val();

        if ("city0000" == areaPidPie) {
            areaPidPie = $("#dashProvince").val();
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "POST",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEcharts", //
                data: {"areaPid": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    // json file method
                    for (var i = 0; i < result.bean.length; i++) {
                        pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                        dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                        datas.push(dataMap);
                    }
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    // Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                    pieDivChange(); //提示信息
                }
            });
            // pie end

        }
        // if end, 市级按钮选择空白时

        if ("city0000" != areaPidPie && $("#dashProvince").val() != areaPidPie) {
            mychartPieInit(); // echarts 数据初始化

            $.ajax({
                type: "POST",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsB", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    // json file method
                    for (var i = 0; i < result.bean.length; i++) {
                        pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                        dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                        datas.push(dataMap);
                    }
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    // Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                    pieDivChange(); //提示信息
                }
            });
            // pie end

        }
        // if end, 市级按钮选择正常时
    });
    // myChartPie, data load end

    // 为myChartTotal增加点击事件
    $("#dashDistrict").change(function () {
        totalDivInit(); // Div初始化
        totalGetDatetime(); // 获取startTimeTotal，endTimeTotal

        // start, 三级按钮（区县）点击事件， total, start
        // $("#district").val()获取当前所选择区县的id，将区县的城市id复制给areaPid,
        areaPid = $("#dashDistrict").val();
        if ("district0000" == areaPid) {
            var areaPidNew = $("#dashCity").val();

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeCErrname", //
                data: {"areaId": areaPidNew, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        districtNames.push(result.bean[i].DISTRICT_NAME); // X
                        districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                    }
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                    totalDivChange(); // 提示信息
                }
            });

            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeCErrInfo", //
                data: {"areaId": areaPidNew, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                        childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                        childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                        childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                    }
                    tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });

            // myChartTotal, data load end
            // end, 二级按钮（市级）点击事件， total, end
        }
        // if end, 区县选择按钮选择空白时
        if ("district0000" != areaPid) {
            // $.ajaxSettings.async = false; //重要设置, 同步请求
            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeDErrname", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        districtNames.push(result.bean[i].DISTRICT_NAME); // X
                        districtNums.push(result.bean[i].DISTRICT_NUM); // Y
                    }
                    mychartTotalLoadData(districtNames, districtNums); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                    totalDivChange(); // 提示信息
                }
            });
            // myChartTotal, data load end
            $.ajax({
                type: "post",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTotalTypeDErrInfo", //
                data: {"areaId": areaPid, "startTime": startTimeTotal, "endTime": endTimeTotal},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    for (var i = 0; i < result.bean.length; i++) {
                        childDistrictNames.push(result.bean[i].DISTRICT_NAME); // 名字
                        childDistrictSBCS.push(result.bean[i].上报超时); // 名字
                        childDistrictWLYC.push(result.bean[i].网络异常); // 名字
                        childDistrictSJWC.push(result.bean[i].时间误差); // 名字
                    }
                    tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartTotal.hideLoading();
                }
            });
            // end, 三级按钮（区县）点击事件， total, end
        }
        // if end, 区县选择按钮选择正常时

    });

    // 为myChartPie增加点击事件
    // myChartPie, data load start
    $("#dashDistrict").change(function () {
        pieDivInit(); // 初始化Div
        areaPidPie = $("#dashDistrict").val();

        if ("district0000" == areaPidPie) {
            areaPidPie = $("#dashCity").val();
            mychartPieInit(); // echarts 数据初始化
            $.ajax({
                type: "POST",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsB", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    // json file method
                    for (var i = 0; i < result.bean.length; i++) {
                        pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                        dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                        datas.push(dataMap);
                    }
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    // Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                    pieDivChange(); //提示信息
                }
            });
            // pie end
        }
        // if end, 区县按钮选择空白时

        if ("district0000" != areaPidPie && $("#dashCity").val() != areaPidPie) {
            mychartPieInit();
            $.ajax({
                type: "POST",
                async: true, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                url: "/nclk-center/DashboardController/jsonTestForEchartsC", //
                data: {"areaId": areaPidPie, "startTime": startTimePie, "endTime": endTimePie},
                dataType: "json", //返回数据形式为json
                timeout: 5000,
                success: function (result) {
                    // json file method
                    for (var i = 0; i < result.bean.length; i++) {
                        pieDataXs.push(result.bean[i].ERROR_TYPE); //挨个取出类别并填入类别数组
                        dataMap = {"name": result.bean[i].ERROR_TYPE, "value": result.bean[i].ERROR_NUM};
                        datas.push(dataMap);
                    }
                    mychartPieLoadData(pieDataXs, datas); // echarts加载数据，table加载数据
                },
                error: function (errorMsg) {
                    //请求失败时执行该函数
                    // Util.dashboardErrorDialog("当前网络出现异常！");
                    // myChartPie.hideLoading();
                    pieDivChange(); //提示信息
                }
            });
            // pie end
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

                dashClockinfo.find("#clockinfo-innum").html(dashcardTotal);
                dashClockinfo.find("#clockinfo-onnum").html(dashcardMonitor);
                dashClockinfo.find("#clockinfo-offnum").html(dashcardDisable);
                dashLoginfo.find("#log-errornum").html(dashcardError);


                lineDataYs.splice(i, 1, dashcardErrorNum); // 队列栈模式，先进先出 / 堆模式，后进先出
                var linesums = parseInt(dashcardNormalNum) + parseInt(dashcardErrorNum) + parseInt(dashcardDisableNum); // 当前监控数量 （不计算停用时钟）
                lineDataYSums.splice(i, 1, linesums); // Y轴sum线， 同 <-

                if (0 == myLineAcc % 60) {
                    getTimestamp();
                    getTimestamp();
                }
                myLineAcc++; // refresh timestamp 计数
                if (1 <= getTimeAcc) {
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

        if (1 <= getTimeAcc) {
            // $("#totalTitleCheckTime").text(moment().format("YYYY-MM-DD HH:mm:ss")); // 标记当前时间
            // myChartTotal.setOption({ // 时间选择提示
            //     title: {
            //         subtext: "（默认展示近30天数据）"
            //     }
            // });
        }
    }

    function totalGetDatetime() {
        timeRangeTotal = $("#dashTimeRange");
        dpTotal = timeRangeTotal.data("daterangepicker"); // 取值
        if (timeRangeTotal.val() != '' && timeRangeTotal.val() != null) {
            startTimeTotal = dpTotal.startDate.format("YYYY-MM-DD HH:mm:SS");
            endTimeTotal = dpTotal.endDate.format("YYYY-MM-DD HH:mm:SS");
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

        districtNames.splice(0, districtNames.length); // 内存释放，垃圾数据清理
        districtNums.splice(0, districtNums.length); // 内存释放，垃圾数据清理
    }

    function tableLoadData(childDistrictNames, childDistrictSBCS, childDistrictWLYC, childDistrictSJWC) {
        var tbodyContent = "";
        var numsSum = [];
        var numsSums = 0;
        var numsPersSBCS = [];
        var numsPersSZDX = [];
        var numsPersSJWC = [];
        var numsPersSums = [];

        // 计算numsSum， 异常数量和
        for (var i = 0; i < childDistrictNames.length; i++) {
            numsSum[i] = parseInt(childDistrictSBCS[i]) + parseInt(childDistrictWLYC[i]) + parseInt(childDistrictSJWC[i]);
            numsSums = numsSums + parseInt(childDistrictSBCS[i]) + parseInt(childDistrictWLYC[i]) + parseInt(childDistrictSJWC[i]);
        }

        // 计算numsPers， 异常百分比
        for (var i = 0; i < childDistrictNames.length; i++) {
            if( 0 != numsSum[i] ){
                numsPersSBCS[i] = Number(parseInt(childDistrictSBCS[i]) / numsSum[i] * 100).toFixed(1);
                numsPersSBCS[i] += "%";
                numsPersSZDX[i] = Number(parseInt(childDistrictWLYC[i]) / numsSum[i] * 100).toFixed(1);
                numsPersSZDX[i] += "%";
                numsPersSJWC[i] = Number(parseInt(childDistrictSJWC[i]) / numsSum[i] * 100).toFixed(1);
                numsPersSJWC[i] += "%";
                numsPersSums[i] = Number(numsSum[i] / numsSums * 100).toFixed(1);
                numsPersSums[i] += "%";
            }
            if( 0 == numsSum[i] ){
                numsPersSBCS[i] = "0%";
                numsPersSZDX[i] = "0%";
                numsPersSJWC[i] = "0%";
                numsPersSums[i] = "0%";
            }
        }
        for (var i = 0; i < childDistrictNames.length; i++) {
            tbodyContent += "<tr>" +
                "<td>" + childDistrictNames[i] + "</td>" +
                "<td style='text-align: center'>" + numsSum[i] + "</td>" +
                "<td style='text-align: center'>" + childDistrictSJWC[i] + "</td>" +
                "<td style='text-align: center'>" + childDistrictSBCS[i] + "</td>" +
                "<td style='text-align: center'>" + childDistrictWLYC[i] + "</td>" +
                "<td style='text-align: center'>" + numsPersSums[i] + "</td>" +
                "<td style='text-align: center'>" + numsPersSJWC[i] + "</td>" +
                "<td style='text-align: center'>" + numsPersSBCS[i] + "</td>" +
                "<td style='text-align: center'>" + numsPersSZDX[i] + "</td>" +
                "</tr>";
        }
        // tbodyTotal.innerHTML = tbodyContent; // 该写法不支持ie

        // 若无异常，则不显示table
        if ("" != tbodyContent) {
            $("#tbodyTotal").html(tbodyContent);
        } else {
            totalTableNone(); // echarts中未检测到数据，隐藏table区域,显示“暂无数据”
        }

        childDistrictNames.splice(0, childDistrictNames.length); // 内存释放，垃圾数据清理
        childDistrictSBCS.splice(0, childDistrictSBCS.length); // 内存释放，垃圾数据清理
        childDistrictWLYC.splice(0, childDistrictWLYC.length); // 内存释放，垃圾数据清理
        childDistrictSJWC.splice(0, childDistrictSJWC.length); // 内存释放，垃圾数据清理
    }

    // ecahrts - pie 数据初始化
    var pieDataXs = []; // legend
    var dataMap = {}; // for data
    var datas = []; // data
    var dashTimeRange = $("#dashTimeRange");
    var dpPie = dashTimeRange.data("daterangepicker"); // 取值
    if (dashTimeRange.val() != '' && dashTimeRange.val() != null) {
        var startTimePie = dpPie.startDate.format("YYYY-MM-DD HH:mm:SS");
        var endTimePie = dpPie.endDate.format("YYYY-MM-DD HH:mm:SS");
    }

    function mychartPieInit() {
        // pie start
        myChartPie.showLoading();
        pieDataXs = []; // legend
        dataMap = {}; // for data
        datas = []; // data

        dashTimeRange = $("#dashTimeRange");
        dpPie = dashTimeRange.data("daterangepicker"); // 取值
        if (dashTimeRange.val() != '' && dashTimeRange.val() != null) {
            startTimePie = dpPie.startDate.format("YYYY-MM-DD HH:mm:SS");
            endTimePie = dpPie.endDate.format("YYYY-MM-DD HH:mm:SS");
        }
    }

    function mychartPieLoadData(pieDataXs, datas) {
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

        pieDataXs.splice(0, pieDataXs.length); // 内存释放，垃圾数据清理
        datas.splice(0, datas.length); // 内存释放，垃圾数据清理
    }

    DATA.intervals.push(dashTimestampId);
    DATA.intervals.push(dashClockRunId);
    DATA.intervals.push(timeId);
});
