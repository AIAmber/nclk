<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%String path = request.getContextPath(); %>
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
        <div class="row" style="padding-left: 16px; padding-right: 16px;">
            <!-- 第1排 -->
            <div class="col-md-6 col-xs-6">
                <div class="panel panel-default" style="border-color: #FFC878; box-shadow: none;">
                    <!-- 第1排第2个 -->
                    <div class="panel-heading"
                         style="height: 32px; background-color: #FFC878; border-color: #FFB74E; text-align:center; font-size: 16px; color: white;">
                        <span style="display: block; margin-top: -4px">已接入网络时钟</span>
                    </div>
                    <div class="panel-body"
                         style="background-color: #FFB74E; text-align: center; margin: 0 auto; cursor: pointer;"
                         onclick="window.location.href='#/department';">
                        <div style="height: 68px; width: 20%; float: left;">
                            <!--<span style="display: block; margin-top: -6px"><img src="<%=path %>/dashboard/img/1-2count.png" width="65px" height="65px" /></span>-->
                            <span><img src="<%=path %>/dashboard/img/1-2count-65-2.png" width="65px"
                                       height="65px"/></span>
                        </div>
                        <div style="height: 68px; width: 80%;  float: right;">
                            <p style="font-size: 40px; color: white; margin-top: 6px"><span id="cardB">31902</span><span
                                    id="cardB-bit" style="font-size: 22px">&nbsp;台</span></p>
                            <!--<p style="font-size: 13px; color: white; display: block; margin-top: -6px">查看详情</p>-->
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 col-xs-6">
                <div class="panel panel-default" style="border-color: #9FD3A2; box-shadow: none;">
                    <!-- 第1排第1个 -->
                    <div class="panel-heading"
                         style="height: 32px; background-color: #9FD3A2; border-color: #81C884; text-align:center; font-size: 16px; color: white;">
                        <P style="display: block; margin-top: -4px;"><span>当前监控时钟</span><span
                                style="font-weight: bold;">： 12022台</span></P>
                    </div>
                    <div class="panel-body"
                         style="background-color: #81C884; text-align: center; margin: 0 auto; cursor: pointer;"
                         onclick="window.location.href='#/catalogmaintenance';">
                        <div style="height: 68px; width: 20%; float: left;">
                            <!--<span style="display: block; margin-top: -6px"><img src="<%=path %>/dashboard/img/1-1print.png" width="65px" height="65px" /></span>-->
                            <span><img src="<%=path %>/dashboard/img/1-1print-65-2.png" width="65px"
                                       height="65px"/></span>
                        </div>
                        <div>
                            <div style="height: 68px; width: 40%;  float: right;">
                                <p style="font-size: 13px; color: white; display: block; margin-top: -6px">异常</p>
                                <p style="font-size: 30px; color: white; margin-top: -4px"><span
                                        id="cardAa">266</span><span id="cardAa-bit"
                                                                    style="font-size: 18px">&nbsp;台</span></p>
                            </div>
                            <div style="height: 68px; width: 40%;  float: right;">
                                <p style="font-size: 13px; color: white; display: block; margin-top: -6px">正常</p>
                                <p style="font-size: 30px; color: white; margin-top: -4px"><span
                                        id="cardAb">11756</span><span id="cardAb-bit"
                                                                      style="font-size: 18px">&nbsp;台</span></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!--第一排结束-->

        <%--插入工具栏--%>
        <div class="row" style="padding-left: 16px; padding-right: 16px;">
            <!-- 第2排 -->
            <div class="col-md-12 col-xs-12">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第2排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #fff; border-color: #fff;">
                        <div class="table-toolbar clearfix" id="toolbar-cataloged" style="margin-top: -7px;">
                            <div class="table-toolbar-search pull-left clearfix" style="padding-right: 30px;">
                                <form class="form-inline clearfix">
                                    <ul>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width: 120px;" id="province">
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:120px;" id="city">
                                            </select>
                                        </li>
                                        <li class="form-group">
                                            <select class="form-control pull-left" style="width:120px;" id="district">
                                            </select>
                                        </li>
                                    </ul>
                                </form>
                            </div>
                            <div style="float: right; margin-top: -2px;">
                                <div class="form-group">
                                    <input class="form-control form-control-large pull-left" placeholder="请输入设备编号/授时ip进行查询"
                                           style="width: 250px"/>
                                </div>
                                <div class="search-btn clearfix">
                                    <div class="">
                                        <a href="javascript:void(0);" class="searchBtn glyphicon glyphicon-search"></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%--工具栏结束--%>

        <!--第二排开始-->
        <!--<div class="panel-heading clearfix"><i class="glyphicon glyphicon-user"></i> 访问情况</div>-->
        <div class="row" style="padding-left: 16px; padding-right: 16px;">
            <!-- 第2排 -->
            <div class="col-md-8 col-xs-8">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第2排第1个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常数量统计</span>
                    </div>

                    <div class="panel-body">
                        <div id="mainTotal" style="height:256px;"></div>
                    </div>
                </div>
            </div>

            <div class="col-md-4 col-xs-4">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第2排第2个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常类型占比</span>
                    </div>
                    <div class="panel-body">
                        <div id="mainPie" style="height:256px"></div>
                    </div>
                </div>
            </div>
        </div>
        <!--第二排结束-->

        <!--第三排开始-->
        <div class="row" style="padding-left: 16px; padding-right: 16px;">
            <div class="col-md-12 col-xs-12">
                <div class="panel panel-default" style="border-radius: 2px; border-color: white; box-shadow: none;">
                    <!-- 第2排第3个 -->
                    <div class="panel-heading" style="height: 36px; background-color: #f5f5f4; border-color: #f5f5f4;">
                        <span style="font-size:16px; float: left; width: 24px; height: 36px; color: #5f5f5f; display: block; margin-top: -2px"><i
                                class="glyphicon glyphicon-indent-left"></i></span>
                        <span style="font-size:16px; float: left; width: 100px; line-height: 16px;">异常实时记录</span>
                    </div>
                    <div class="panel-body">
                        <div id="mainLine" style="height:256px"></div>
                    </div>
                </div>
            </div>
        </div>
        <!--第三排结束-->

    </div>
</div>

<script type="text/javascript">

</script>
</body>
</html>