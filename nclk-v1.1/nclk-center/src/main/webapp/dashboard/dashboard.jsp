<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    //用户id
    String dashUserId = (String) request.getSession().getAttribute("USER_ID");
    //用户姓名
    String dashName = (String) request.getSession().getAttribute("NAME");
    //上次登录时间
    String dashLoginDate = (String) request.getSession().getAttribute("LOGIN_DATE");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>主页</title>
</head>
<body>
<script type="text/javascript" src="<%=path %>/dashboard/js/dashboard.js"></script>
<script type="text/javascript" src="<%=path %>/dashboard/js/checkbox.js"></script>
<link rel="stylesheet" href="<%=path %>/dashboard/css/wgt-fund.css" type="text/css"/>
<link rel="stylesheet" href="<%=path %>/dashboard/css/dashboard-table.css" type="text/css"/>
<style type="text/css">
    .container-fluid {
        padding-right: 0px;
        padding-left: 0px;
    }
</style>
<div class="container-fluid" style="padding-right: 0px; padding-left: 0px;">
    <div class="row-fluid">

        <!--<div class="panel-heading clearfix"><i class="glyphicon glyphicon-flash"></i> 今日发布</div>-->
        <!--第一排开始-->
        <div class="row" style="padding-left: 20px; padding-right: 35px;">
            <!-- 第1排 -->
            <div class="col-md-6 col-xs-6" style="padding-right: 15px;">
                <div class="panel panel-default" style="border-color: #ffffff; box-shadow: none; margin-bottom: 0px;">
                    <!-- 第1排第2个 -->
                    <div class="panel-body"
                         style="background-color: #ffffff; text-align: center; margin: 0 auto; padding: 0px;">
                        <div style="height: 100px;">
                            <div id="dashhead-sec" style="width: 13%;  float: right; margin-top: 37px;">
                                <span><img id="dashhead-sec-1" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="18.3px" height="25.92px"/></span>
                                <span><img id="dashhead-sec-2" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="18.3px" height="25.92px"/></span>
                            </div>
                            <div id="dashhead-time" style="width: 44%;  float: right; margin-top: 7px;">
                                <span><img id="dashhead-time-1" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="36.59px" height="51.84px"/></span>
                                <span><img id="dashhead-time-2" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="36.59px" height="51.84px"/></span>
                                <span><img id="dashhead-time-3" src="<%=path %>/dashboard/img/dash-m.png"
                                           width="27.44px" height="51.84px"/></span>
                                <span><img id="dashhead-time-4" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="36.59px" height="51.84px"/></span>
                                <span><img id="dashhead-time-5" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="36.59px" height="51.84px"/></span>
                            </div>
                            <div id="dashhead-date" style="width: 40%; float: right; margin-top: 28px;">
                                <span><img id="dashhead-date-1" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-2" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-3" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-4" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-5" src="<%=path %>/dashboard/img/dash-d.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-6" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-7" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-8" src="<%=path %>/dashboard/img/dash-d.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-9" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                                <span><img id="dashhead-date-10" src="<%=path %>/dashboard/img/dash-0.png"
                                           width="12.19px" height="17.28px"/></span>
                            </div>
                            <div style="width: 2%; float: right; margin-top: 54px;">&nbsp;</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-xs-6" style="padding-left: 0px;">
                <div class="panel panel-default" style="border-color: #ffffff; box-shadow: none; margin-bottom: 0px;">
                    <div class="panel-body"
                         style="background-color: #ffffff; padding: 0px; margin: 0 auto;">
                        <div id="dash-card">
                            <div id="dashcard-left" style="width: 105px; height: 84px; float: left;">
                                <img id="dashcard-left-img" style="width: 105px; height: 84px;" src="<%=path %>/dashboard/img/dash-card-left.png">
                            </div>
                            <div id="dashcard-panel" class="panel panel-default"
                                 style="width: 75%; height: 84px; float: left; border-radius: 0px; border-top-color: #54B2E9; border-bottom-color: #54B2E9; border-left: none; border-right: none; text-align: right;">
                                <div id="dashcard-panel-disable-space" class="panel-body" style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #E53417;">
                                    <p>&nbsp;</p>
                                </div>
                                <div id="dashcard-panel-disable" class="panel-body" style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #E53417;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-disable" style="width: 20px; height: 20px; margin-top: -1px;" src="<%=path %>/dashboard/img/dash-icon-disable-add.png">停用数量</p>
                                    <p id="disable-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-error-space" class="panel-body" style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620; border-right-color: red;">
                                    <div class="panel" style="border-right-color: #D4EBF9; height: 58%; margin-top: 18px; border-radius: 0px;"></div>
                                </div>
                                <div id="dashcard-panel-error" class="panel-body" style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-error" style="width: 20px; height: 20px; margin-top: -1px;" src="<%=path %>/dashboard/img/dash-icon-error-add.png">异常数量</p>
                                    <p id="error-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-normal-space" class="panel-body" style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620; border-right-color: red;">
                                    <div class="panel" style="border-right-color: #D4EBF9; height: 58%; margin-top: 18px; border-radius: 0px;"></div>
                                </div>
                                <div id="dashcard-panel-normal" class="panel-body" style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #1FA579;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-normal" style="width: 20px; height: 20px; margin-top: -1px;" src="<%=path %>/dashboard/img/dash-icon-normal-add.png">正常数量</p>
                                    <p id="normal-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-Total" class="panel-body" style="width: 28%; height: 83px; float: right; padding: 0px; margin: 0 auto; color: #ffffff; background-color: #54B2E9; text-align: right;">
                                    <p style="margin-top: 18px; margin-right: 15px; font-size: 12px;">已接入时钟</p>
                                    <p style="margin-top: -10px;">
                                        <span id="total-num" style="font-size: 26px; margin-right: 0px; text-align: center;">0</span>
                                        <span id="total-bit" style="font-size: 12px; margin-right: 15px;">台</span>
                                    </p>

                                </div>
                            </div>
                            <div id="dashcard-right" style="width: 6px; height: 84px; float:left;">
                                <img id="dashcard-right-img" style="width: 6px; height: 84px;" src="<%=path %>/dashboard/img/dash-card-right.png">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--第一排结束-->

        <!--第二排开始-->
        <div class="row" style="padding-left: 35px; padding-right: 50px;">
            <div class="col-md-12 col-xs-12">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第2排第3个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常实时记录</span>
                    </div>
                    <div class="panel-body" style="height: 280px">
                        <div id="mainLine" style="height:276px; z-index: 0;"></div>
                    </div>
                </div>
            </div>
        </div>
        <!--第二排结束-->

        <!--第三排开始-->
        <!--<div class="panel-heading clearfix"><i class="glyphicon glyphicon-user"></i> 访问情况</div>-->
        <div class="row" style="padding-left: 35px; padding-right: 50px;">
            <!-- 第3排 -->
            <div class="col-md-8 col-xs-8">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">

                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 110px; line-height: 16px;">异常实时分析</span>
                        <%--插入工具栏--%>
                        <%--区域选择工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged-dropTotal"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <form class="form-inline clearfix">
                                    <ul>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width: 105px;"
                                                    id="provinceTotal">
                                                <option value="province0000a">请选择省</option>
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:105px;" id="cityTotal">
                                                <option value="city0000a">请选择市</option>
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:105px;"
                                                    id="districtTotal">
                                                <option value="district0000a">请选择县区</option>
                                            </select>
                                        </li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                        <%--工具栏结束--%>
                    </div>

                    <div class="panel-body" style="height: 275px;">
                        <div id="divTotal" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">暂未设置考点</p>
                        </div>
                        <div id="divTotalPro" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">请选择区域范围</p>
                        </div>
                        <div id="mainTotal" style="height:265px; display: block; z-index:0;"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-4 col-xs-4">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-list-alt"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常数量详情</span>
                    </div>
                    <div class="panel-body" style="height: 275px;">
                        <div id="tablePanelTotalDiv" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">暂无异常信息</p>
                        </div>
                        <div id="tablePanelTotalDivPro" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">请选择区域范围</p>
                        </div>
                        <div id="tablePanelTotal" style="display: block;">
                            <table id="tableTotal" class="table table-condensed table-hover">
                                <thead id="theadTotal">
                                <tr id="trTotal">
                                    <th style="border-bottom-color: #78C7C1; text-align: center;">当前区域</th>
                                    <th style="border-bottom-color: #78C7C1; text-align: center;">异常数量</th>
                                    <th style="border-bottom-color: #78C7C1; text-align: center;">异常占比</th>
                                </tr>
                                </thead>
                            </table>
                            <div style="height: 215px; overflow-y: auto;">
                                <table class="table table-condensed table-hover" style="margin-top: -5px;">
                                    <%--<table class="table table-condensed table-hover">--%>
                                    <tbody id="tbodyTotal">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--第三排结束-->

        <!--第四排开始-->
        <!--<div class="panel-heading clearfix"><i class="glyphicon glyphicon-user"></i> 访问情况</div>-->
        <div class="row" style="padding-left: 35px; padding-right: 50px;">
            <!-- 第4排 -->
            <div class="col-md-8 col-xs-8">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第4排第1个 -->
                    <div class="panel-heading"
                         style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 120px; line-height: 16px;">异常类型占比</span>
                        <%--插入工具栏--%>
                        <%--时间选择工具栏--%>
                        <div class="table-toolbar-search pull-left clearfix" style="margin-top: -7px; padding-right: 0px;">
                            <ul>
                                <li class="form-group has-feedback">
                                    <div class="pull-left has-feedback dateCustom-wrapper">
                                        <input id="timeRangePie"
                                               class="form-control pull-left dateCustom dateCustom-detail"
                                               style="width: 110px;" readonly placeholder="请选择时间"/>
                                        <span class="glyphicon glyphicon-calendar form-control-feedback"></span>
                                        <span class="glyphicon glyphicon-remove form-control-feedback dateCustom-remove"></span>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <%--区域选择工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <form class="form-inline clearfix">
                                    <ul>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width: 105px;"
                                                    id="provincePie">
                                                <option value="province0000b">请选择省</option>
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:105px;" id="cityPie">
                                                <option value="city0000b">请选择市</option>
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:105px;"
                                                    id="districtPie">
                                                <option value="district0000b">请选择县区</option>
                                            </select>
                                        </li>
                                    </ul>
                                </form>
                            </div>
                        </div>
                        <%--工具栏结束--%>
                    </div>
                    <div class="panel-body" style="height: 315px;">
                        <div id="divPie" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">暂无异常</p>
                        </div>
                        <div id="divPiePro" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">请选择区域范围</p>
                        </div>
                        <div id="mainPie" style="height:290px; display: block; z-index: 0;"></div>
                        <div style="margin-top: -15px;">
                            <div style="text-align: center;">
                                <span id="pieEchartCheckTime" style="font-size: 10px; color: #969696; display: none;">（默认展示近7天数据）</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-4 col-xs-4">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-list-alt"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常原因详情</span>
                    </div>
                    <div class="panel-body" style="height: 290px;">
                        <div id="tableBorderPieDiv" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">暂无异常</p>
                        </div>
                        <div id="tableBorderPieDivPro" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">请选择区域范围</p>
                        </div>
                        <div id="tableBorderPie" style="display: block;">
                            <table id="tablePie" class="table table-condensed table-hover">
                                <thead>
                                <tr>
                                    <th style="border-bottom-color: #E3A0A5; text-align: center;">异常原因</th>
                                    <th style="border-bottom-color: #E3A0A5; text-align: center;">异常数量</th>
                                    <th style="border-bottom-color: #E3A0A5; text-align: center;">异常占比</th>
                                </tr>
                                </thead>
                            </table>
                            <div style="height: 240px; overflow-y: hidden;">
                                <table class="table table-condensed table-hover" style="margin-top: -5px;">
                                    <%--<table class="table table-condensed table-hover">--%>
                                    <tbody id="tbodyPie">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--第四排结束-->

        <%--右下角 悬浮小图标--%>
        <div id="ad-lock" style="bottom: 200px; border-bottom: 1px solid #dcdcdc;">
            <span id="ad-lock-content" style="color: #3C8DBC"><i class="glyphicon glyphicon-triangle-bottom"></i></span>
        </div>

        <div id="ad-top" style="bottom: 150px;">
            <span id="ad-top-content" style="color: #969696"><i class="glyphicon glyphicon-user"></i></span>
        </div>
        <div id="ad-center" style="bottom: 100px;">
            <span id="ad-center-content" style="color: #969696"><i class="glyphicon glyphicon-time"></i></span>
        </div>
        <div id="ad-foot" style="bottom: 50px; border-bottom: 1px solid #dcdcdc;">
            <span id="ad-foot-content" style="color: #969696"><i class="glyphicon glyphicon-list-alt"></i></span>
        </div>

        <%--用户信息--%>
        <div style="display: none;">
            <div id="userinfo-form">
                <table class="table table-condensed table-hover" style="width: 400px;">
                    <thead></thead>
                    <tbody>
                    <tr>
                        <td style="line-height: 75px;">头像</td>
                        <td id="userinfo-photo" style="height: 100px; text-align: center;">
                            <%--<img id="userphoto" src="<%=path %>/common/img/protrait.png">--%>
                            <img id="userphoto"
                                 src="<%=path %>/UserController/findUserPhotoById?userId=<%=dashUserId %>"
                                 onerror="this.src='<%=path %>/common/img/protrait.png'" alt="User Image">
                            <%--<img id="dashUserphoto" src="<%=path %>/UserController/findUserPhotoById?userId=<%=dashUserId %>" >--%>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">用户名</td>
                        <td style="height: 40px; line-height: 35px;">
                            <span id="userinfo-name"><%=dashName %></span>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">上一次登录时间</td>
                        <td style="height: 40px; line-height: 35px;">
                            <%--<span id="userinfo-lgtime">2018/01/01 00:00:00</span>--%>
                            <span id="userinfo-lgtime"><%=dashLoginDate %></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%--时钟信息--%>
        <div style="display: none;">
            <div id="clockinfo-form">
                <table class="table table-condensed table-hover" style="width: 400px;">
                    <thead></thead>
                    <tbody>
                    <tr>
                        <td style="line-height: 35px;">服务器当前时间</td>
                        <td id="clockinfo-time" style="height: 40px; line-height: 35px;">
                            <span id="clockinfo-time-now">2018/01/01 00:00:00</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">已接入时钟</td>
                        <td id="clockinfo-in" style="height: 40px; line-height: 35px;">
                        <td id="clockinfo-innum" style="height: 40px; line-height: 35px;">-,---</td>
                        <td style="height: 40px; line-height: 35px;">台</td>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">已启用时钟</td>
                        <td id="clockinfo-on" style="height: 40px; line-height: 35px;">
                        <td id="clockinfo-onnum" style="height: 40px; line-height: 35px;">-,---</td>
                        <td style="height: 40px; line-height: 35px;">台</td>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">已停用时钟</td>
                        <td id="clockinfo-off" style="height: 40px; line-height: 35px;">
                        <td id="clockinfo-offnum" style="height: 40px; line-height: 35px;">-,---</td>
                        <td style="height: 40px; line-height: 35px;">台</td>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <%--日志信息--%>
        <div style="display: none;">
            <div id="loginfo-form">
                <table class="table table-condensed table-hover" style="width: 400px;">
                    <thead></thead>
                    <tbody>
                    <tr>
                        <td style="line-height: 35px;">服务器当前时间</td>
                        <td id="log-time" style="height: 40px; line-height: 35px;">
                            <span>2018/01/01 00:00:00</span>
                        </td>
                    </tr>
                    <tr>
                        <td style="line-height: 35px;">累计出现异常</td>
                        <td id="log-error" style="height: 40px; line-height: 35px;">
                        <td id="log-errornum" style="height: 40px; line-height: 35px;">-,---</td>
                        <td style="height: 40px; line-height: 35px;">次</td>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>