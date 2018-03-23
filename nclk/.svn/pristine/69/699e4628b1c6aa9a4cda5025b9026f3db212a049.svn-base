define(['jquery', 'bootstrap', 'bootstrap-table', 'bootstrap-table-zh-CN'], function ($, bootstrap, bootstrapTable) {

    $.fn.bootstrapGrid = function (options) {
        var opt = $.extend({}, options, {
            queryParamsType: "limit",
            contentType: "application/x-www-form-urlencoded",
            method: "post",
            dataField: "data",
            totalField: "total",
            sidePagination: "server",
            pagination: true,
            paginationLoop: false,
            paginationNextText: "下一页",
            paginationPreText: "上一页",
            pageSize: 30,
            pageList: [15, 30, 50, 100, 300, 500, "全部"],
            responseHandler: function (res) {
                return {
                    total: res.total,
                    data: res.rows
                };
            },
            queryParams: function (params) {
                return {
                    start: params.offset,
                    limit: params.limit
                }
            },
            formatShowingRows: function (pageFrom, pageTo, totalRows) {
                return '共 ' + totalRows + ' 条';
            },
            formatRecordsPerPage: function (pageNumber) {
                return ' 每页显示 ' + pageNumber + ' 条' + ' <div class="pull-right pagination-skip"><span>跳转至&nbsp;</span><span class="pull-right">&nbsp;页</span><input type="number" min="1" class="form-control input-sm pull-right pagination-num"></div>';
            },
            formatAllRows: function () {
                return "全部";
            }
        });
        var table = $(this).bootstrapTable(opt);
        resetTableView(table);
        $(window).resize(function () {
            resetTableView(table);
        });
        $(this).parents(".bootstrap-table").on("keyup", ".pagination-num", function (event) {
            if (event.keyCode == "13") {
                var num = $(this).val();
                var reg = /^[1-9]\d*$/;
                if (reg.test(num)) {
                    table.bootstrapTable("selectPage", parseInt(num));
                }
            }
        });
        return {
            resetSearch: function (param) {
                table.bootstrapTable("refresh", {"query": param});
            },
            table: function (method, param) {
                return table.bootstrapTable(method, param);
            }
        };
    };

    function resetTableView(table) {
        var dynamicHeight = document.documentElement.clientHeight - $(".navbar.navbar-static-top").height() - $(".breadcrumb").height() - $(".footer").height();
        var height = dynamicHeight - 30 - 15 - 30;
        table.bootstrapTable("resetView", {"height": height})
    }

});


