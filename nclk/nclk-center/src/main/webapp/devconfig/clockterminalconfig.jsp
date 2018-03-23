<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path =  request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>网络时钟终端配置</title>
</head>
<body>
	<script src="<%=path %>/devconfig/js/clockterminalconfig.js"></script>
	<link href="<%=path %>/devconfig/css/devconfig.css" rel="stylesheet">
	
	<div class="panel panel-default panel-nobody">
		<div class="table-toolbar clearfix">
			<div class="btn-group btn-group-sm pull-right">
				<button id="btn-search" role="group" type="button" class="btn btn-default"><span class="glyphicon glyphicon glyphicon-search"></span>&nbsp;自动检索</button>
		 		<button id="btn-add" role="group" type="button" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>&nbsp;手动添加</button>
		 	 	<button id="btn-import" role="group" type="button" class="btn btn-default"><span class="glyphicon glyphicon-log-in"></span>&nbsp;导入</button>
			</div>
			<div class="table-toolbar-search pull-left clearfix">
				<form class="form-inline clearfix">
					<ul>
						<li class="form-group">
							<select class="form-control pull-left" id="province"></select>
						</li>
						<li class="form-group">
							<select class="form-control pull-left" id="city"></select>
						</li>
						<li class="form-group">
							<select class="form-control pull-left" id="district"></select>
						</li>
						<li class="form-group">
							<select class="form-control pull-left" id="endpoint"></select>
						</li>
						<li class="form-group">
							<div class="pull-left">
								<a href="javascript:void(0)"
									class="searchBtn glyphicon glyphicon-search">
								</a>
							</div>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<table id="exampointerGrid" data-undefined-text="没有检索到数据"></table>
	</div>
	<div class="show_tip mr_bt20">
		时钟总数:<span class="totalCount" id="totalCount">0</span>台,正常数量<span class="normalCount" id="normalCount">0</span>台,停用数量<span class="disableCount" id="disableCount">0</span>台
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="normal_status">
				<span>正常时钟列表</span>
			</div>
		</div>
		<div class="panel-body error_statusul">
			<ul id="normal_time">
				<%-- <li>
					<img src="<%=path%>/monitor/images/time.png" />
					<span>192.168.1.1</span>
					<a onclick="">编辑</a>
				</li> --%>
			</ul>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="disable_status">
				<span>停用时钟列表</span>
			</div>
		</div>
		<div class="panel-body error_statusul">
			<ul id="error_time">
				<%-- <li>
					<img src="<%=path%>/monitor/images/time.png" />
					<span>192.168.1.1</span>
					<a onclick="">编辑</a>
				</li> --%>
			</ul>
		</div>
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
	<!-- 考场子钟信息手动导入 -->
	<div id="endpointimport" style="display: none">
		<form enctype="multipart/form-data" class="form-horizontal" id="uploadendpointtemplet">
			<div class="form-group">
				<div class="col-sm-12">
					<div class="input-group">
						<input id="filename" name="filename" type="text" class="form-control" readonly="readonly" placeholder="请选择xlsx格式的文件进行上传！" required="" aria-describedby="filename-error" aria-invalid="false">
						<input id="file" type="file" name="file" class="form-control" style="display: none;" required="">
						<div class="input-group-btn">
							<button id="selectFile" class="btn btn-default" type="button">选择文件</button>
						</div>
				    </div>
				<small id="filename-error" class="text-danger"></small></div>
			</div>
		</form>
		<a type="" href="<%=path%>/ClockTerminalConfigController/downloadTemplet">考场子钟信息-模板下载</a>
	</div>
	<!-- 手动添加考场信息 -->
	<div style="display: none" id="endpointadd">
		<form class="form-horizontal" id="endpointaddform" novalidate="novalidate">
			<div class="form-group">
				<label class="col-sm-3 control-label"><font class="red">*</font>考场子钟IP</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="examRoomIp">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="examRoomName">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场地址</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="examRoomAddr">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场负责人</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="examRoomPerson">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场负责人电话</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="examRoomTel">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">顺序号</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="ord">
				</div>
			</div>
		</form>
	</div>
	<!-- 自动搜索子钟 -->
	<div id="endPointAutoSearch" style="display:none">
		<form class="form-inline clearfix" id="endPointAutoSearchForm">
			<ul>
				<li class="form-group">
					<input type="radio" name="terminalIP">终端IP地址
				</li>
				<li class="form-group">
					<input type="radio" name="terminalIP">终端IP地址段
				</li>
				<li class="form-group">
					<input type="text" class="form-control">
				</li>
				<li class="form-group">
					~
				</li>
				<li class="form-group">
					<input type="text" class="form-control">
				</li>
				<li class="form-group">
					<div class="pull-left">
						<a href="javascript:void(0)" class="searchBtn glyphicon glyphicon-search">
						</a>
					</div>
				</li>
				<li class="form-group">
					<button class="btn btn-default btn-primary" id="selectAllClock">全选</button>
				</li>
			</ul>
			<div class="error_statusul">
				<ul id="addAutoSearchClock">
				</ul>
			</div>
		</form>
	</div>
</body>
</html>