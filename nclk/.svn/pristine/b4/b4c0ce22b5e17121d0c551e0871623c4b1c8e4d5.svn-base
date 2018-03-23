<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="panel panel-default panel-nobody">
		<div class="table-toolbar clearfix">
			<div class="btn-group btn-group-sm pull-right">
				<!--  <button id="btn-delete" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button> -->
				<select class="form-control">
					<option>请选择刷新时间</option>
				</select>
			</div>
			<div class="table-toolbar-search pull-left clearfix">
				<form class="form-inline clearfix">
					<ul>
						<li class="form-group"><select class="form-control">
								<option>请选择</option>
						</select></li>
						<li class="form-group"><select class="form-control">
								<option>请选择</option>
						</select></li>
						<li class="form-group"><select class="form-control">
								<option>请选择</option>
						</select></li>
						<li class="form-group">
							<div class="pull-left">
								<a href="javascript:void(0)"
									class="searchBtn glyphicon glyphicon-search"></a>
							</div>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<table id="exampointerGrid" data-undefined-text="没有检索到数据"></table>
	</div>
	<div class="show_tip mr_bt20">
		时钟总数:<span class="totalCount">400</span>台,正常数量<span class="normalCount">390</span>台,异常数量<span class="errorCount">2</span>台,停用数量<span class="disableCount">8</span>台
	</div>
	<div class="panel panel-default ">
		<div class="panel-heading ">
			<div class="error_status">
				<span>异常时钟列表</span>
			</div>
		</div>
		<div class="panel-body error_statusul">
			<ul id="error_time">
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
				<li><img src="<%=path%>/monitor/images/time.png" /></li>
			</ul>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="normal_status">
				<span>正常时钟列表</span>
			</div>
		</div>
		<div class="panel-body">Panel content</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="disable_status">
				<span>停用时钟列表</span>
			</div>
		</div>
		<div class="panel-body">Panel content</div>
	</div>
	
	
	<div style="display: none;" id="error_timeModal">
		<form class="form-horizontal" id="" novalidate="novalidate">
			<div class="form-group">
				<label class="col-sm-3 control-label">异常类型:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 网络掉线 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">异常描述:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 未检测到网络连接 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场子钟IP:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 192.168.1.1 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场名称:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 001001 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场地址:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> A区4栋301 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场负责人:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 张可可 </span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">联系方式:</label>
				<div class="col-sm-8">
					<span class="form-control clear_border"> 15951978888 </span>
				</div>
			</div>
		</form>
	</div>
</body>
</html>