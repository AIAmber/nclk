<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
</head>
<body>
<link href="<%=path%>/monitor/css/monitor.css" rel="stylesheet">
<script src="<%=path %>/monitor/js/errorinfo.js"></script>
<div class="panel panel-default panel-nobody">
    <div class="table-toolbar clearfix">
        <div class="table-toolbar-search pull-left clearfix">
            <form class="form-inline clearfix" id="searchForm">
                <ul>
                    <li class="form-group has-feedback">
                        <div class="pull-left has-feedback dateCustom-wrapper">
                            <input id="timeRange" class="form-control pull-left dateCustom dateCustom-detail" readonly placeholder="      异常开始时间    ~    异常结束时间" />
                            <span class="glyphicon glyphicon-calendar form-control-feedback"></span>
                            <span class="glyphicon glyphicon-remove form-control-feedback dateCustom-remove"></span>
                        </div>
                    </li>
                    <li class="form-group">
                        <input id="searchText" class="form-control form-control-large pull-left dynamic" placeholder="请输入考点名称、考场名称或子种IP进行查询"/>
                    </li>
                </ul>
            </form>
        </div>
        <div class="btn-group btn-group-sm pull-right">
            <button id="btn-delete" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
        </div>
    </div>
    <table id="grids"  data-undefined-text="没有检索到数据" ></table>
    <p class="warning-info text-right">
        <span class="fa fa-exclamation-triangle warning"></span>异常信息列表默认只展示近3天数据，如需查看更多请通过查询选项精确检索
    </p>
</div>
<div style="display: none;">
    <table class="table table-condensed table-nobody" id="info-detail">
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
</body>
</html>