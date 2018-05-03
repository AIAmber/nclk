require(['jquery', 'Util', 'moment', 'ztree', 'bootstrap-grid', 'table-toolbar', 'daterangepicker-zh-CN', 'jquery-placeholder'], function ($, Util, moment) {

    Util.datecustomRangePicker();

    $('input').placeholder();

    var timeRange = $("#timeRange");
    var dp = timeRange.data("daterangepicker");
    $(".table-toolbar").toolbar({
        defaultFormControlLarge: 134,
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
        uniqueId: 'EXAMROOM_LOG_ID',
        columns: [{
            field: 'EXAMROOM_LOG_ID',
            title: '所属市区',
            formatter: function (value, row, index) {
                return row.CITY_NAME + " " + row.AREA_NAME;
            }
        }, {
            field: 'ENDPOINT_NAME',
            title: '考点名称'
        }, {
            field: 'EXAMROOM_NAME',
            title: '考场名称'
        }, {
            field: 'EXAMROOM_IP',
            title: '考场子钟IP'
        }, {
            field: 'ERROR_TYPE',
            title: '异常类型'
        }, {
            field: 'ERROR_DATE',
            title: '异常时间'
        }, {
            width: 120,
            field: 'EXAMROOM_LOG_ID',
            title: '操作',
            formatter: function (value, row, index) {
                return [
                    '<a data-id="' + value + '" class="table-operation error-detail" href="javascript:void(0)">详情</a>',
                    '<a data-id="' + row.EXAMROOM_ID + '" class="table-operation error-history" href="javascript:void(0)">历史日志</a>'
                ].join("");
            }
        }]
    });

    $('#grids').on("click", "a.error-detail", function () {
        var $detailView = $(this).parents("table").find(".detail-view");
        var $dV = $(this).closest("tr").next(".detail-view");
        $detailView.remove();
        if ($dV.length > 0) {
            return;
        }

        var options = gridTable.table("getOptions");
        var $detailTable = $("#info-detail");
        var row = gridTable.table("getRowByUniqueId", $(this).attr("data-id"));
        $detailTable.find(".endpointPerson").text(row.ENDPOINT_PERSON || "");
        $detailTable.find(".endpointTel").text(row.ENDPOINT_TEL || "");
        $detailTable.find(".examroomPerson").text(row.EXAMROOM_PERSON || "");
        $detailTable.find(".examroomTel").text(row.EXAMROOM_TEL || "");
        $detailTable.find(".examroomAddr").text(row.EXAMROOM_ADDR || "");
        $detailTable.find(".errorDesc").text(row.ERROR_DESC || "");
        $(this).closest("tr").after(['<tr class="detail-view">'
            , '<td colspan="' + options.columns[0].length + '">',
            $detailTable.prop("outerHTML"),
            '</td></tr>'].join(""));
    });

    $('#grids').on("click", "a.error-history", function () {
        DATA.params.examroomId = $(this).attr("data-id");
        location.hash = "/historyerrorinfo";
    });
});