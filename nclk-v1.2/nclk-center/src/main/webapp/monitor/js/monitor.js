require(['jquery', 'Util', 'moment', 'bootstrap-dialog', 'bootstrap-grid', 'table-toolbar', 'ztree'], function ($, Util, moment, BootstrapDialog, grid) {

    var currentState = "error";
    var endpoints = $("#endpoints");
    var $blockInfo = $("#blockdiv");
    var isLoadSuccess = false;//ztree是否已经加载完成
    var endpointTemplete = $("#endpoint-templete").html();
    $("[data-toggle='tooltip']").tooltip();

    var setting = {
        view: {
            dblClickExpand: function (treeId, treeNode) {
                return treeNode.level > 0;
            },
            showIcon: false,
            showTitle: false,
            fontCss: {
                "text-decoration": "none"
            }
        },
        async: {
            enable: true,
            url: Util.getPath() + "/MonitorController/findAllArea",
            dataFilter: function (treeId, parentNode, childNodes) {
                return childNodes.rows;
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: function () {
                return false;
            },
            onAsyncSuccess: function (event, treeId, treeNode, msg) {
                isLoadSuccess = true;
                $.fn.zTree.getZTreeObj(treeId).expandAll(true);
                $.fn.zTree.getZTreeObj(treeId).checkAllNodes(true);
            },
            onCheck: function (event, treeId, treeNode) {
                $("#endpoints-container")[0].scrollTop = 0;
                updateCount();
                findEndpoints();
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "s", "N": "ps"}
        }
    };
    var tree = $.fn.zTree.init($("#area_tree"), setting);

    updateCount();
    findEndpoints();

    //根据状态，切换显示不同的考点
    $("#error-switching").on("click", "button", function () {
        if (tree.getCheckedNodes(true).length === 0) {
            Util.warningDialog("请先勾选区域！");
            return;
        }
        $("#error-switching").children().removeClass("active");
        var $this = $(this);
        $this.addClass("active");
        if ($this.hasClass("error-btn")) {
            currentState = "error";
        } else {
            currentState = "all";
        }
        $("#endpoints-container")[0].scrollTop = 0;
        findEndpoints();
    });

    //获取定时任务的周期
    $.post(Util.getPath() + "/MonitorController/getIntervalTime", function (res) {
        if (res.success === true) {
            var interval = setInterval(function () {
                updateCount();
                findEndpoints(false);
            }, res.bean);
            DATA.intervals.push(interval);
        }
    });

    /**
     * 更新个数统计信息
     */
    function updateCount() {
        var params = {};
        if (isLoadSuccess === true) {
            params = {
                areaIds: getCheckedNodes()
            };
        }
        $.post(Util.getPath() + "/MonitorController/monitorCount", params, function (res) {
            if (res.success === true) {
                var obj = res.bean;
                $(".monitor-head .endpointTotal").text(obj.endpointTotal);
                $(".monitor-head .enableTotal").text(obj.enableTotal);
                $(".monitor-head .normalCount").text(obj.normalCount);
                $(".monitor-head .errorCount").text(obj.errorCount);
            }
        });
    }

    /**
     * 查询考点
     */
    function findEndpoints() {
        var params = {};
        if (isLoadSuccess === true) {
            params = {
                areaIds: getCheckedNodes()
            };
        }
        $.extend(params, {state: currentState});
        $.post(Util.getPath() + "/MonitorController/findEndpoints", params, function (res) {
            if (res.rows != null) {
                updateEndpoints(res.rows);
            }
        });
    }

    /**
     * 获得已选择的区域ID，以;号分割数据
     * @returns {string}
     */
    function getCheckedNodes() {
        var checkedNodes = tree.getCheckedNodes(true);
        var areaIds = [];
        $.each(checkedNodes, function (i, o) {
            if (o.level === 2) {
                areaIds.push(o.id);
            }
        });
        return areaIds.join(";");
    }

    /**
     *添加或更新考点
     * @param rows
     */
    function updateEndpoints(rows) {
        var trFragments = $(document.createDocumentFragment());
        var rowsDom = [];
        $.each(rows, function (i, o) {
            var row = endpointTemplete.replace(/{{num}}/g, (i + 1)).replace(/{{endpointname}}/g, o.ENDPOINT_NAME)
                .replace(/{{place}}/g, o.CITY_NAME + " " + o.AREA_NAME).replace(/{{normalCount}}/g, o.NORMAL_COUNT)
                .replace(/{{errorCount}}/g, o.ERROR_COUNT).replace(/{{disableCount}}/g, o.DISABLE_COUNT)
                .replace(/{{id}}/g, o.ENDPOINT_ID);
            rowsDom.push(row);
        });
        trFragments.append(rowsDom.join(""));
        endpoints.html(trFragments);
    }

    //-------------------------------------------------点击查看详情

    $(".error-content").on("click", ".rows-error", function (e) {
        var errorCount = $(this).text();
        if (parseInt(errorCount) === 0) {
            Util.promptNoticeDialog("该考点没有异常信息可以查看！");
            return;
        }
        var emdpointId = $(this).parents("tr").attr("id");
        $.post(Util.getPath() + "/MonitorController/findDetailError", {endpointId: emdpointId}, function (res) {
            if (res.rows != null) {
                var table = $blockInfo.find(".info-table tbody");
                var item = $("#errorinfo-templete").children();
                table.empty();
                $.each(res.rows, function (i, o) {
                    var errorinfoTemplete = item.clone();
                    errorinfoTemplete.find(".num").text(i + 1);
                    errorinfoTemplete.find(".ip").text(o.EXAMROOM_IP || "");
                    errorinfoTemplete.find(".examRoomName").text(o.EXAMROOM_NAME || "");
                    errorinfoTemplete.find(".errorType").text(o.ERROR_TYPE || "");
                    errorinfoTemplete.find(".detail-content .endpointPerson").text(o.ENDPOINT_PERSON || "");
                    errorinfoTemplete.find(".detail-content .endpointTel").text(o.ENDPOINT_TEL || "");
                    errorinfoTemplete.find(".detail-content .examroomPerson").text(o.EXAMROOM_PERSON || "");
                    errorinfoTemplete.find(".detail-content .examroomTel").text(o.EXAMROOM_TEL || "");
                    errorinfoTemplete.find(".detail-content .examroomAddr").text(o.EXAMROOM_ADDR || "");
                    errorinfoTemplete.find(".detail-content .errorDesc").text(o.ERROR_DESC || "");
                    table.append(errorinfoTemplete);
                });

                //显示列表弹出框
                $blockInfo.show();
                var left;
                var top;
                if (($blockInfo.width() + e.clientX) > $(window).width()) {
                    left = e.clientX - $blockInfo.width();
                } else {
                    left = e.clientX;
                }
                if (($blockInfo.height() + e.clientY) > $(window).height()) {
                    top = e.clientY - $blockInfo.height();
                } else {
                    top = e.clientY;
                }
                $blockInfo.css("left", left).css("top", top);
            }
        });
    }).on("mouseleave", ".rows-error", function (e) {
        $blockInfo.hide();
    });

    //点击查看详情
    $blockInfo.mouseenter(function () {
        $(this).show();
    }).mouseleave(function () {
        $(this).hide();
        $("#blockdiv .detail").hide();
    }).on("click", ".content-show-detail", function () {
        var $detail = $(this).closest("tr").next();
        $("#blockdiv .detail").not($detail).hide();
        $detail.toggle();
    });

    //-----------------------------------------------更新时钟时间

    function showTime(deviationTime) {
        var timeStrs = moment().add(deviationTime, 'milliseconds').format("YYYY-MM-DD HH:mm:ss").split(" ");
        $("#day").empty().text(timeStrs[0]);
        $("#time").empty().text(timeStrs[1]);
        $(".time-head").show();
    }

    function getServerTime(callback) {
        $.post(Util.getPath() + "/MonitorController/getClockTime", function (res) {
            callback(new Date(res.bean.timestamp));
        });
    }

    //获取定时任务的周期
    $.post(Util.getPath() + "/DashboardController/getDashboardRTime", function (res) {
        if (res.success === true) {
            getServerTime(function (time) {
                var deviationTime = time.getTime() - new Date().getTime();
                showTime(deviationTime);

                var showTimeInterval = window.setInterval(function () {
                    showTime(deviationTime);
                }, 1000);

                var getServerTimeInterval = window.setInterval(function () {
                    getServerTime(function (time) {
                        deviationTime = time.getTime() - new Date().getTime();
                    });
                }, res.bean.timestampRTime);

                DATA.intervals.push(showTimeInterval);
                DATA.intervals.push(getServerTimeInterval);
            });
        }
    });

    //-----------------------------------------------设置窗口缩放事件
    var setresize = function setresize() {
        //动态控制元素的高度
        $(".endpoints").css("height", $("#rightContent").height() - 272);
        $(".area-tree .ztree").css("height", $("#rightContent").height() - 216);
    };
    //设定窗口缩放事件
    setresize();
    $(window, document).resize(setresize);
    DATA.resizes.push(setresize);


});