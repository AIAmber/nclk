define(['jquery', 'Router','Util'], function ($, Route,Util) {
    //定义数据对象 记录用到的数据
    DATA = {resizes: [], intervals: []};

    //左侧菜单刷新后显示原来的选中状态
    var path = window.location.hash;
    $("[href='" + path + "']").parents(".sBox").find(".subNav").removeClass("sublist-up").addClass("sublist-down");
    $("[href='" + path + "']").parent().addClass("active");
    $("[href='" + path + "']").parents(".sBox").find(".navContent").show();

    return {
        inint: function () {
            //创建用户发布正确路由时执行的一些功能。
            //首页
            var home = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>首页</span>");
                $('#rightContent').load("dashboard/dashboard.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //运行监控-->实时状态监控
            var statusmonitor = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>运行监控&nbsp;/&nbsp;实时状态监控</span>");
                $('#rightContent').load("monitor/statusmonitor.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //运行监控-->异常信息查询
            var errorinfo = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>运行监控&nbsp;/&nbsp;异常信息查询</span>");
                $('#rightContent').load("monitor/errorinfo.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //设备配置-->网络时钟终端配置
            var clockterminalconfig = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>设备配置&nbsp;/&nbsp;网络时钟终端配置</span>");
                $('#rightContent').load("devconfig/clockterminalconfig.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //基础设置-->区域管理
            var area = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>基础设置&nbsp;/&nbsp;区域管理</span>");
                $('#rightContent').load("manage/area.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //基础设置-->考点管理
            var exampointer = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>基础设置&nbsp;/&nbsp;考点管理</span>");
                $('#rightContent').load("manage/exampointer.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };
            //基础设置-->用户管理
            var user = function () {
                $("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>基础设置&nbsp;/&nbsp;用户管理</span>");
                $('#rightContent').load("manage/user.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
            };

            var allroutes = function () {
                var route = window.location.hash.slice(2);
            };
            // 定义路由表。
            var routes = {
                '/home': home,
                '/statusmonitor': statusmonitor,
                '/errorinfo': errorinfo,
                '/clockterminalconfig': clockterminalconfig,
                '/area': area,
                '/exampointer': exampointer,
                '/user': user
            };
            // 实例化路由器
            var router = Router(routes);
            // 全局配置设置.
            router.configure({
                //on:当路由匹配成功后，需要执行的方法
                on: allroutes,
                //before:在触发"on"方法之前执行的方法
                before: function () {

                },
                //after:当离开当前注册路径时，需要执行的方法
                after: function () {
                    $("#rightContent").children().empty();
                    $.each(DATA.intervals, function () {
                        //清除定时器
                        window.clearInterval(this);
                    });
                    window.clearInterval(DATA.lineErrIntervals);
                    window.clearInterval(DATA.lineInterval);
                    DATA.intervals = [];
                    $.each(DATA.resizes, function () {
                        //清除$(window)绑定的相关事件
                        $(window).unbind("resize", this);
                    });
                    DATA.resizes = [];
                }
            });
            router.init();
            
            //若浏览器地址栏没有路由地址 默认跳转到首页
            if(window.location.hash == ""){
            	$("#headnavigation").empty();
                $("#headnavigation").append("&nbsp;&nbsp;<span>首页</span>");
                $('#rightContent').load("dashboard/dashboard.jsp", null, function (response, status) {
                    if (status == "success") {
                        document.getElementById('rightContent').innerHTML = response;
                    }
                });
                window.location.href = Util.getPath() + "/index.jsp#/home";
            }
        }
    }
});