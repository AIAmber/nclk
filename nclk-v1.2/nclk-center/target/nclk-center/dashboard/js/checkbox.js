require(['jquery', 'Util', 'bootstrap-dialog', 'bootstrap-grid', 'daterangepicker-zh-CN', 'table-toolbar', 'jquery-placeholder'], function ($, Util, bootstrapDialog, bootstrapGrid, toolbar) {

    function getPath() {
        return location.pathname.match(/\/(.+?)(?=\/)/g)[0];
    }

    $("#timeRangePie").placeholder();
    $("#dashTimeRange").placeholder(); // now

    // dash 级联 start
    //省市区三级联动
    $(document).ready(function () {
        $.ajax({
            url: getPath() + "/AreaController/getProvince",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {},
            success: function (data) {
                $("#dashProvince").empty();
                $("#dashProvince").append("<option value='province0000'>请选择省</option>");
                for (var i = 0; i < data.length; i++) {
                    if (i == 0) {
                        $("#dashProvince").append("<option selected='selected' value='" + data[i].AREA_ID + "'>" + data[i].AREA_NAME + "</option>");
                        continue;
                    }
                    $("#dashProvince").append("<option value='" + data[i].AREA_ID + "'>" + data[i].AREA_NAME + "</option>");
                }
                getCityDash($("#dashProvince").val());
                $("#dashProvince").change();
            }
        });
    });

    //给省份的选择框添加事件
    $("#dashProvince").change(function () {
        //获取选择的省份id
        var parentAreaId = $(this).val();
        getCityDash(parentAreaId);
        $("#dashDistrict").empty();
        $("#dashDistrict").append("<option value='district0000' selected>请选择县区</option>");
    });

    function getCityDash(parentAreaId) {
        $.ajax({
            url: Util.getPath() + "/AreaController/getCity",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                parentAreaId: parentAreaId
            },
            success: function (data) {
                $("#dashCity").empty();
                $("#dashCity").append("<option value='city0000' selected>请选择市</option>");
                for (var i = 0; i < data.length; i++) {
                    $("#dashCity").append("<option value='" + data[i].AREA_ID + "'>" + data[i].AREA_NAME + "</option>");
                }
                //添加完成之后触发选择事件
                // $("#cityTotal").change();
            },
            error: function () {

            }
        });
    }

    //给地级市的选择框添加事件
    $("#dashDistrict").empty();
    $("#dashDistrict").append("<option value='district0000' selected>请选择县区</option>");
    $("#dashCity").change(function () {
        //获取选择的地级市id
        var parentAreaId = $("#dashCity").val();
        $.ajax({
            url: getPath() + "/AreaController/getDistrict",
            type: 'post',
            dataType: 'json',
            async: false,
            data: {
                parentAreaId: parentAreaId
            },
            success: function (data) {
                $("#dashDistrict").empty();
                $("#dashDistrict").append("<option value='district0000' selected>请选择县区</option>");
                if (null != data && data != undefined && data != "") {
                    var i = 0;
                    for (i = 0; i < data.length; i++) {
                        $("#dashDistrict").append("<option value='" + data[i].AREA_ID + "'>" + data[i].AREA_NAME + "</option>");
                    }
                }
                // //添加完成之后触发选择事件
                // $("#districtTotal").change();
            }
        });
    });
    // dash 级联 end

    var configDashCardFreshFreshTime = 5000; // center.dashboard.dashboardRTime=1000
    var dashCardFreshTime = 3000; // center.dashboard.timestampRTime=10000
    $.ajax({
        type: "post",
        async: false, //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/nclk-center/MonitorController/getIntervalTime", //
        dataType: "json", //返回数据形式为json
        timeout: 1000,
        success: function (res) {
            configDashCardFreshFreshTime = res.bean; // 页面加载时，从配置文件获取 ecahrts - line 的刷新时间
            dashCardFreshTime = configDashCardFreshFreshTime; // timestamp 的刷新时间
        }
    });

    // 头部样式
    var dashheadRuler = 1.0;
    var dashheadDateWidth = 24.0;
    var dashheadDateHeight = 34.0;
    var dashheadTimeWidth = 72.0;
    var dashheadTimeHeight = 102.0;
    var dashheadSecWidth = 36.0;
    var dashheadSecHeight = 51.0;

    var dashcardRuler = 1.0;
    var dashcardLeftWidth = 137.0;
    var dashcardRightWidth = 8;
    var dashcardHeight = 110.0;
    var dashiconWH = 32.0;
    function dashheadImgSize(dashheadRuler, dashheadDateWidth, dashheadDateHeight, dashheadTimeWidth, dashheadTimeHeight, dashheadSecWidth, dashheadSecHeight) {

        // 模拟媒体查询，调整时间窗大小
        if ($(window).width() < 1280) {
            dashheadRuler = $(window).width() / 2560;
            dashcardRuler = $(window).width() / 1280;
            // $("#dashcard-panel").css("width", 425 * $(window).width() / 1280);
        }
        if (1280 <= $(window).width() && 1440 > $(window).width()) {
            dashheadRuler = $(window).width() / 2340;
            dashcardRuler = $(window).width() / 1450;
        }
        if (1440 <= $(window).width() && 2000 > $(window).width()) {
            dashheadRuler = $(window).width() / 2220;
        }
        if (1440 <= $(window).width() && 1680 > $(window).width()) {
            dashcardRuler = $(window).width() / 1600;
        }
        if (1680 <= $(window).width() && 2000 > $(window).width()) {
            dashcardRuler = $(window).width() / 1880;
        }

        if (2000 <= $(window).width()) {
            dashheadRuler = 1;
            dashcardRuler = 1;
        }
        dashheadDateWidth = 24.0 * dashheadRuler;
        dashheadDateHeight = 34.0 * dashheadRuler;
        dashheadTimeWidth = 72.0 * dashheadRuler;
        dashheadTimeHeight = 102.0 * dashheadRuler;
        dashheadSecWidth = 36.0 * dashheadRuler;
        dashheadSecHeight = 51.0 * dashheadRuler;

        dashcardLeftWidth = 137.0 * dashcardRuler;
        dashcardRightWidth = 8 * dashcardRuler;
        dashcardHeight = 110.0 * dashcardRuler;
        dashiconWH = 32.0 * dashcardRuler;

        $("#dashhead-date").css("margin-top", 83 - dashheadDateHeight - 26);
        $("#dashhead-date-1").css("width", dashheadDateWidth);
        $("#dashhead-date-1").css("height", dashheadDateHeight);
        $("#dashhead-date-2").css("width", dashheadDateWidth);
        $("#dashhead-date-2").css("height", dashheadDateHeight);
        $("#dashhead-date-3").css("width", dashheadDateWidth);
        $("#dashhead-date-3").css("height", dashheadDateHeight);
        $("#dashhead-date-4").css("width", dashheadDateWidth);
        $("#dashhead-date-4").css("height", dashheadDateHeight);
        $("#dashhead-date-5").css("width", dashheadDateWidth);
        $("#dashhead-date-5").css("height", dashheadDateHeight);
        $("#dashhead-date-6").css("width", dashheadDateWidth);
        $("#dashhead-date-6").css("height", dashheadDateHeight);
        $("#dashhead-date-7").css("width", dashheadDateWidth);
        $("#dashhead-date-7").css("height", dashheadDateHeight);
        $("#dashhead-date-8").css("width", dashheadDateWidth);
        $("#dashhead-date-8").css("height", dashheadDateHeight);
        $("#dashhead-date-9").css("width", dashheadDateWidth);
        $("#dashhead-date-9").css("height", dashheadDateHeight);
        $("#dashhead-date-10").css("width", dashheadDateWidth);
        $("#dashhead-date-10").css("height", dashheadDateHeight);

        $("#dashhead-time").css("margin-top", 105 - dashheadTimeHeight - 26);
        $("#dashhead-time-1").css("width", dashheadTimeWidth);
        $("#dashhead-time-1").css("height", dashheadTimeHeight);
        $("#dashhead-time-2").css("width", dashheadTimeWidth);
        $("#dashhead-time-2").css("height", dashheadTimeHeight);
        $("#dashhead-time-3").css("width", dashheadTimeWidth * 0.75);
        $("#dashhead-time-3").css("height", dashheadTimeHeight);
        $("#dashhead-time-4").css("width", dashheadTimeWidth);
        $("#dashhead-time-4").css("height", dashheadTimeHeight);
        $("#dashhead-time-5").css("width", dashheadTimeWidth);
        $("#dashhead-time-5").css("height", dashheadTimeHeight);

        $("#dashhead-sec").css("margin-top", 105 - dashheadSecHeight - 26);
        $("#dashhead-sec-1").css("width", dashheadSecWidth);
        $("#dashhead-sec-1").css("height", dashheadSecHeight);
        $("#dashhead-sec-2").css("width", dashheadSecWidth);
        $("#dashhead-sec-2").css("height", dashheadSecHeight);

        $("#dashcard-left-img").css("width", parseInt(dashcardLeftWidth) + 1);
        $("#dashcard-left-img").css("height", dashcardHeight);
        $("#dashcard-left").css("width", parseInt(dashcardLeftWidth) + 1);
        $("#dashcard-left").css("height", dashcardHeight);
        $("#dashcard-right-img").css("width", dashcardRightWidth);
        $("#dashcard-right-img").css("height", dashcardHeight);
        $("#dashcard-right").css("width", dashcardRightWidth);
        $("#dashcard-right").css("height", dashcardHeight);
        $("#dashcard-panel-disable-space").css("height", dashcardHeight);
        $("#dashcard-panel-disable").css("height", dashcardHeight);
        $("#dashcard-panel-error-space").css("height", dashcardHeight);
        $("#dashcard-panel-error").css("height", dashcardHeight);
        $("#dashcard-panel-normal-space").css("height", dashcardHeight);
        $("#dashcard-panel-normal").css("height", dashcardHeight);
        $("#dashcard-panel-Total").css("height", dashcardHeight - 2);
        $("#dashcard-panel").css("height", dashcardHeight);

        $("#dashcard-icon-disable").css("width", dashiconWH*0.78);
        $("#dashcard-icon-disable").css("height", dashiconWH*0.78);
        $("#dashcard-icon-error").css("width", dashiconWH*0.78);
        $("#dashcard-icon-error").css("height", dashiconWH*0.78);
        $("#dashcard-icon-normal").css("width", dashiconWH*0.78);
        $("#dashcard-icon-normal").css("height", dashiconWH*0.78);

        if (1920 <= $(window).width()) {
            $("#dashhead-date").css("margin-top", 35);
            $("#dashhead-time").css("margin-top", 0);
            $("#dashhead-sec").css("margin-top", 50);
        }

        if ( 475 >= $("#dashcard-panel").width() && 1380 <= $(window).width() ){
            $("#dashcard-panel").css("width", 475 * $(window).width() / 1580);
        }

        if (475 < $("#dashcard-panel").width()){
            $("#dashcard-panel").css("width", "74%");
        }

    }

    function dashcardResize() {
        var dashcardWidth = $("#dash-card").width();
        $("#dashcard-panel").css("width", dashcardWidth - 155);
    }
    dashcardResize();

    dashheadImgSize(dashheadRuler, dashheadDateWidth, dashheadDateHeight, dashheadTimeWidth, dashheadTimeHeight, dashheadSecWidth, dashheadSecHeight);

    $(window).resize(function () {
        dashheadImgSize(dashheadRuler, dashheadDateWidth, dashheadDateHeight, dashheadTimeWidth, dashheadTimeHeight, dashheadSecWidth, dashheadSecHeight);
        dashcardResize();
    });

});