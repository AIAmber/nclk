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
<script type="text/javascript" src="<%=path %>/dashboard/js/checkbox.js"></script>
<script type="text/javascript" src="<%=path %>/dashboard/js/dashboard.js"></script>
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
                                <img id="dashcard-left-img" style="width: 105px; height: 84px;"
                                     src="<%=path %>/dashboard/img/dash-card-left.png">
                            </div>
                            <div id="dashcard-panel" class="panel panel-default"
                                 style="width: 74%; height: 84px; float: left; border-radius: 0px; border-top-color: #54B2E9; border-bottom-color: #54B2E9; border-left: none; border-right: none; text-align: right;">
                                <div id="dashcard-panel-disable-space" class="panel-body"
                                     style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #E53417;">
                                    <p>&nbsp;</p>
                                </div>
                                <div id="dashcard-panel-disable" class="panel-body"
                                     style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-disable"
                                                                                       style="width: 20px; height: 20px; margin-top: -1px;"
                                                                                       src="<%=path %>/dashboard/img/dash-icon-disable-add.png">停用数量
                                    </p>
                                    <p id="disable-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-error-space" class="panel-body"
                                     style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620; border-right-color: red;">
                                    <div class="panel"
                                         style="border-right-color: #D4EBF9; height: 58%; margin-top: 18px; border-radius: 0px;"></div>
                                </div>
                                <div id="dashcard-panel-error" class="panel-body"
                                     style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #E53417;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-error"
                                                                                       style="width: 20px; height: 20px; margin-top: -1px;"
                                                                                       src="<%=path %>/dashboard/img/dash-icon-error-add.png">异常数量
                                    </p>
                                    <p id="error-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-normal-space" class="panel-body"
                                     style="width: 4%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #FFA620; border-right-color: red;">
                                    <div class="panel"
                                         style="border-right-color: #D4EBF9; height: 58%; margin-top: 18px; border-radius: 0px;"></div>
                                </div>
                                <div id="dashcard-panel-normal" class="panel-body"
                                     style="width: 20%; height: 84px; float: right; padding: 0px; margin: 0 auto; color: #1FA579;">
                                    <p style="margin-top: 18px; font-size: 12px;"><img id="dashcard-icon-normal"
                                                                                       style="width: 20px; height: 20px; margin-top: -1px;"
                                                                                       src="<%=path %>/dashboard/img/dash-icon-normal-add.png">正常数量
                                    </p>
                                    <p id="normal-num" style="margin-top: -5px; font-size: 20px;">0</p>
                                </div>
                                <div id="dashcard-panel-Total" class="panel-body"
                                     style="width: 28%; height: 83px; float: right; padding: 0px; margin: 0 auto; color: #ffffff; background-color: #54B2E9; text-align: right;">
                                    <p style="margin-top: 18px; margin-right: 15px; font-size: 12px;">已接入时钟</p>
                                    <p style="margin-top: -10px;">
                                        <span id="total-num"
                                              style="font-size: 26px; margin-right: 0px; text-align: center;">0</span>
                                        <span id="total-bit" style="font-size: 12px; margin-right: 15px;">台</span>
                                    </p>

                                </div>
                            </div>
                            <div id="dashcard-right" style="width: 6px; height: 84px; float:left;">
                                <img id="dashcard-right-img" style="width: 6px; height: 84px;"
                                     src="<%=path %>/dashboard/img/dash-card-right.png">
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
                    <div class="panel-heading" style="height: 36px; background-color: #ffffff; border-color: #ffffff;">
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

        <%--中央控制栏--%>
        <div class="row" style="padding-left: 35px; padding-right: 50px;">
            <!-- 第3排 -->
            <div class="col-md-1 col-xs-1"></div>
            <div class="col-md-4 col-xs-4">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #FFFFFF; border-color: #FFFFFF;">
                        <%--插入工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <%--时间选择工具栏--%>
                                <div class="pull-left has-feedback dateCustom-wrapper">
                                    <input id="dashTimeRange"
                                           class="form-control pull-left dateCustom dateCustom-detail"
                                           style="width: 305px;" readonly
                                           placeholder="            请选择开始时间 ~ 结束时间            "/>
                                    <span class="glyphicon glyphicon-calendar form-control-feedback"></span>
                                    <span class="glyphicon glyphicon-remove form-control-feedback dateCustom-remove"></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2 col-xs-2">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #FFFFFF; border-color: #FFFFFF;">
                        <%--插入工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <%--省选择工具栏--%>
                                <select class="form-control pull-left"
                                        style="width: 152px; text-align: center;"
                                        id="dashProvince">
                                    <option value="province0000">请选择省</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2 col-xs-2">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #FFFFFF; border-color: #FFFFFF;">
                        <%--插入工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <%--市选择工具栏--%>
                                <select class="form-control pull-left" style="width: 152px;" id="dashCity">
                                    <option value="city0000">请选择市</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2 col-xs-2">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #FFFFFF; border-color: #FFFFFF;">
                        <%--插入工具栏--%>
                        <div class="table-toolbar clearfix" id="toolbar-cataloged"
                             style="margin-top: -7px; float: right;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 0px;">
                                <%--区县选择工具栏--%>
                                <select class="form-control pull-left" style="width: 152px;"
                                        id="dashDistrict">
                                    <option value="district0000">请选择县区</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-1 col-xs-1"></div>
        </div>
        <%--中央控制栏结束--%>

        <!--第三排开始-->
        <!--<div class="panel-heading clearfix"><i class="glyphicon glyphicon-user"></i> 访问情况</div>-->
        <div class="row" style="padding-left: 35px; padding-right: 50px;">
            <!-- 第3排 -->
            <div class="col-md-8 col-xs-8">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第3排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #ffffff; border-color: #ffffff;">

                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 110px; line-height: 16px;">异常次数统计</span>
                    </div>

                    <div class="panel-body" style="height: 275px;">
                        <div id="divTotal" style="display: none; text-align: center; margin-top: 108px;">
                            <p style="font-size: 16px; color: #969696">暂无数据</p>
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
                    <!-- 第4排第1个 -->
                    <div class="panel-heading"
                         style="height: 36px; background-color: #ffffff; border-color: #ffffff;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 120px; line-height: 16px;">异常类型占比</span>
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
                                <span id="pieEchartCheckTime" style="font-size: 10px; color: #969696; display: none;">（默认展示近30天数据）</span>
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
            <div class="col-md-12 col-xs-12">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <div class="panel-heading" style="height: 36px; background-color: #ffffff; border-color: #ffffff;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-list-alt"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常次数详情</span>
                    </div>
                    <div class="panel-body" style="height: 55px;">
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
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">地区</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">异常次数</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">时钟误差次数</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">上报超时次数</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">网络异常次数</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">异常占比</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">时钟误差占比</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">上报超时占比</th>
                                    <th style="border-bottom-color: #5BB0F0; text-align: center;">网络异常占比</th>
                                </tr>
                                </thead>
                                <tbody id="tbodyTotal">
                                </tbody>
                            </table>
                            <div style="height: 60px;">
                                <span>&nbsp;</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--第四排结束-->

    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>