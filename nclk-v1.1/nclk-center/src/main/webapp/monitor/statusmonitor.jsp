<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>运行状态监控</title>
</head>
<body>
<link href="<%=path%>/monitor/css/monitor.css" rel="stylesheet">
<script src="<%=path%>/monitor/js/monitor.js"></script>

<div class="panel panel-default panel-nobody clearfix area-content" style="height: 135px">
    <div class="time-head">
        <P id="day" class="day text-left"></P>
        <p id="time" class="time text-center"></p>
    </div>
    <div class="monitor-head error-list">
        <ul>
            <li>
                <div class="wrap-head wh-fr0">
                    <span class="endpointTotal">0</span>
                </div>
                <div class="wrap-bottom wb-fr0">
                    <span class="wb-left tf">监控考点总数</span>
                    <span class="wb-right tr pull-right">个</span>
                </div>
            </li>
            <li>
                <div class="wrap-head wh-fr1">
                    <span class="enableTotal">0</span>
                </div>
                <div class="wrap-bottom wb-fr1">
                    <span class="wb-left tf">已启用时钟总数</span>
                    <span class="wb-right tr pull-right">台</span>
                </div>
            </li>
            <li>
                <div class="wrap-head wh-fr2">
                    <span class="normalCount">0</span>
                </div>
                <div class="wrap-bottom wb-fr2">
                    <span class="wb-left tf">已启用正常时钟数</span>
                    <span class="wb-right tr pull-right">台</span>
                </div>
            </li>
            <li>
                <div class="wrap-head wh-fr3">
                    <span class="errorCount">0</span>
                </div>
                <div class="wrap-bottom wb-fr3">
                    <span class="wb-left tf">已启用异常时钟数</span>
                    <span class="wb-right tr pull-right">台</span>
                </div>
            </li>
        </ul>
    </div>
</div>

<!-- 考场信息开始 -->
<div class="panel panel-default panel-nobody clearfix area-content">
    <div class="area-tree">
        <div class="panel panel-default">
            <div class="panel-heading clearfix">
                <span class="pull-left title">区域信息</span>
            </div>
            <div class="panel-body">
                <ul id="area_tree" class="ztree "></ul>
            </div>
        </div>
    </div>

    <div class="panel panel-default panel-nobody error-list">
        <div class="panel-heading ">
            <div class="error_status">
                <span>考点信息</span>
                <div class="btn-group btn-group-sm" id="error-switching" style="margin-top: 2px">
                    <button type="button" class="btn btn-default all-btn"
                            data-toggle="tooltip" data-placement="bottom" title="所有考点"
                            data-container="body">
                        全部
                    </button>
                    <button type="button" class="btn btn-default active error-btn"
                            data-toggle="tooltip" data-placement="bottom" title="存在异常时钟的考点"
                            data-container="body">
                        异常
                    </button>
                </div>
            </div>
        </div>
        <div class="panel-body error-content">
            <table class="row-table">
                <thead>
                <tr>
                    <th style="width:10%">序号</th>
                    <th style="width:45%" class="stu-th">考点名称</th>
                    <th style="width:15%">正常数量</th>
                    <th style="width:15%">异常数量</th>
                    <th style="width:15%">停用数量</th>
                </tr>
                </thead>
            </table>
            <div class="endpoints" id="endpoints-container">
                <table class="row-table">
                    <tbody id="endpoints">
                    <%--<tr>
                        <td>1</td>
                        <td class="stu-td">
                            <p>南京师范大学附属中学<p>
                            <p>南京市秦淮区</p>
                        </td>
                        <td>
                            <span class="stu-span stu-normal">16</span>
                        </td>
                        <td>
                            <p class="stu-erp"><span class="stu-span stu-error">21</span></p>
                        </td>
                        <td>
                            <span class="stu-span stu-disable">30</span>
                        </td>
                    </tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!--区域管理结束  -->

<!-- 流程进度 -->
<div class="block-message" id="blockdiv">
    <div class="block-content">
        <table class="table info-table">
            <thead>
            <tr>
                <th>#</th>
                <th>IP</th>
                <th>考场名称</th>
                <th>错误类型</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <%--<tr>
              <td scope="row">1</td>
              <td>192.168.1.1</td>
              <td>建邺中学</td>
              <td>与母钟时间不匹配</td>
              <td><a href="javascript:void(0)" class="content-show-detail">点击查看</a></td>
            </tr>
            <tr class="detail">
                <td></td>
                  <td colspan="4">
                      <div class="detail-content">
                          <table class="table table-condensed table-nobody">
                              <tr>
                              <td style="width: 120px" class="text-right">详细原因：</td>
                              <td style="width: 300px">不通过</td>
                            </tr>
                            <tr>
                              <td class="text-right">考场负责人：</td>
                              <td>张某人</td>
                            </tr>
                            <tr>
                              <td class="text-right">考场负责人电话：</td>
                              <td>15950539456</td>
                            </tr>
                            <tr>
                              <td class="text-right">考场地址：</td>
                              <td style="width: 300px">南京理工大学，实验楼1-1，教室501</td>
                            </tr>
                          </table>
                    </div>
                </td>
            </tr>--%>
            </tbody>
        </table>
    </div>
</div>

<%-- ----------------------------------------------------黄金分割线----------------------------------------%>

<div style="display: none">
    <table>
        <tbody id="endpoint-templete">
        <tr id="{{id}}">
            <td class="endpoint-num" style="width:10%">{{num}}</td>
            <td class="stu-td" style="width:45%">
                <p class="endpoint-endpointname">{{endpointname}}
                <p>
                <p class="endpoint-place">{{place}}</p>
            </td>
            <td style="width:15%">
                <span class="stu-span stu-normal endpoint-normalCount">{{normalCount}}</span>
            </td>
            <td style="width:15%">
                <p class="stu-erp"><span class="stu-span stu-error endpoint-errorCount rows-error">{{errorCount}}</span>
                </p>
            </td>
            <td style="width:15%">
                <span class="stu-span stu-disable endpoint-disableCount">{{disableCount}}</span>
            </td>

        </tr>
        </tbody>
    </table>
    <table>
        <tbody id="errorinfo-templete">
        <tr>
            <td scope="row" class="num">1</td>
            <td class="ip" style="max-width: 112px">--</td>
            <td class="examRoomName" style="max-width: 290px">--</td>
            <td class="errorType" style="max-width: 100px">--</td>
            <td><a href="javascript:void(0)" class="content-show-detail">详情</a></td>
        </tr>
        <tr class="detail">
            <td></td>
            <td colspan="4">
                <div class="detail-content">
                    <table class="table table-condensed table-nobody">
                        <tbody>
                        <tr>
                            <td class="text-right">考点负责人：</td>
                            <td class="endpointPerson"></td>
                        </tr>
                        <tr>
                            <td class="text-right">考点负责人电话：</td>
                            <td class="endpointTel"></td>
                        </tr>
                        <tr>
                            <td class="text-right">考场负责人：</td>
                            <td class="examroomPerson"></td>
                        </tr>
                        <tr>
                            <td class="text-right">考场负责人电话：</td>
                            <td class="examroomTel"></td>
                        </tr>
                        <tr>
                            <td class="text-right">考场地址：</td>
                            <td style="width: 300px" class="examroomAddr"></td>
                        </tr>
                        <tr>
                            <td style="width: 120px" class="text-right">异常描述：</td>
                            <td style="width: 300px" class="errorDesc"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
</div>
</body>
</html>