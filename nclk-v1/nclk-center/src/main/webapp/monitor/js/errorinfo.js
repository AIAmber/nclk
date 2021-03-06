require(['jquery', 'Util', 'bootstrap-grid', 'table-toolbar', 'daterangepicker-zh-CN'], function ($, Util) {

    Util.datecustomRangePicker();

    var timeRange = $("#timeRange");
    var dp = timeRange.data("daterangepicker");
    console.log(a = dp);
    $(".table-toolbar").toolbar({
        searchEvent: function () {
            var startTime, endTime;
            if (timeRange.val() != '' && timeRange.val() != null) {
                startTime = dp.startDate.format("YYYY-MM-DD HH:mm:SS");
                endTime = dp.endDate.format("YYYY-MM-DD HH:mm:SS");
            }
            var searchText = $("#searchText").val();
            gridTable.resetSearch({
                "searchText": searchText,
                "startTime": startTime,
                "endTime": endTime
            });
        }
    });

    var gridTable = $('#grids').bootstrapGrid({
        url: Util.getPath() + "/MonitorController/findErrorInfo",
        uniqueId: 'ID',
        columns: [{
            checkbox: true
        }, {
            field: 'EXAMROOM_IP',
            title: '考场子钟IP'
        }, {
            field: 'EXAMROOM_NAME',
            title: '考场名称'
        }, {
            field: 'ERROR_TYPE',
            title: '异常类型'
        }, {
            field: 'ERROR_DATE',
            title: '异常时间'
        }, {
            field: 'ERROR_DESC',
            title: '异常描述'
        }]
    });

});