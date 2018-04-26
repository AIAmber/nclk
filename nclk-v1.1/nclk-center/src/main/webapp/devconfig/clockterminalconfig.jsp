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
							<select class="form-control pull-left" id="province">
								<option value="">请选择省</option>
							</select>
						</li>
						<li class="form-group">
							<select class="form-control pull-left" id="city">
								<option value="">请选择市</option>
							</select>
						</li>
						<li class="form-group">
							<select class="form-control pull-left" id="district">
								<option value="">请选择县区</option>
							</select>
						</li>
						<li class="form-group" style="width: 120px">
							<select class="form-control pull-left" id="endpoint" style="width: 120px">
								<option value="">请选择考点</option>
							</select>
						</li>
					</ul>
				</form>
			</div>
		</div>
	</div>
	<div class="show_tip mr_bt20">
		时钟总数:<span class="totalCount" id="totalCount">0</span>台,启用数量<span class="normalCount" id="normalCount">0</span>台,停用数量<span class="disableCount" id="disableCount">0</span>台
	</div>
	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="normal_status">
				<span>已启用时钟列表</span>
				<span id="normaldeleteAll" >删除</span>
				<span id="normalselectAll" >全选</span>
				<button id="disableClock" class='btn btn-default btn-warning'>停用</button>
				<!--搜索框  -->
				 <div class="btn_searchDiv" style="float:right">
				     <div class="has-feedback">
				  		<input id="btn_normal_clock_ip" type="text" class="form-control" placeholder="请输入时钟IP进行搜索" style="margin-right: 40px;"> <!--删除按钮-->
				    	<a href="javascript:void(0)" class="glyphicon glyphicon-remove btn form-control-feedback chear_input" id="normal_delete" style="display: none"></a>
				     </div>
				     <span class="input-group-btn">
			          <button id="btn_normal_clock" class="btn btn-default" type="button" style="margin-top: -34px;margin-right: -205px;"><i class="fa fa-search" aria-hidden="true"></i></button>
			         </span>
			     </div> 
			</div>
		</div>
		<div class="panel-body error_statusul">
			<ul id="normal_time"></ul>
			<div id="normal_time_result" style="text-align: center;"></div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<div class="disable_status">
				<span>已停用时钟列表</span>
				<span id="disabledeleteAll" >删除</span>
				<span id="disableselectAll">全选</span>
				<button id="normalClock" class='btn btn-default btn-info'>启用</button>
				 
				 <!--搜索框  -->
				 <div class="btn_searchDiv" style="float:right">
				     <div class="has-feedback">
				  		<input id="btn_disable_clock_ip" type="text" class="form-control" placeholder="请输入时钟IP进行搜索" style="margin-right: 40px;"> <!--删除按钮-->
				    	<a class="glyphicon glyphicon-remove btn form-control-feedback chear_input" id="disable_delete" style="display: none"></a>
				     </div>
				     <span class="input-group-btn">
			          <button id="btn_disable_clock" class="btn btn-default" type="button" style="margin-top: -34px;margin-right: -205px;"><i class="fa fa-search" aria-hidden="true"></i></button>
			         </span>
			     </div>  
			</div>
		</div>
		<div class="panel-body error_statusul">
			<ul id="error_time"></ul>
			<div id="error_time_result" style="text-align: center"></div>
		</div>
	</div>
	<!-- 考场子钟信息手动导入 -->
	<div id="endpointimport" style="display: none">
		<form enctype="multipart/form-data" class="form-horizontal" id="uploadendpointtemplet">
			<div class="form-group">
				<div class="col-sm-12">
					<div class="input-group">
						<input id="filename" name="filename" type="text" class="form-control" readonly="readonly" placeholder="请选择xlsx格式的文件进行上传！" required="" aria-describedby="filename-error" aria-invalid="false">
						<div class="input-group-btn" style="cursor:pointer;">
							<button id="selectFile" class="btn btn-default" type="button" style="position: relative;">
							选择文件
							</button>
							<input id="file" type="file" name="file" class="form-control" style="cursor:pointer;filter: alpha(opacity = 0);opacity: 0;background-color:red;position: absolute;top: 0px;right: 0px;width:163px;height:42px" required="">
						</div>
				    </div>
				<small id="filename-error" class="text-danger"></small></div>
			</div>
		</form>
		<a type="" href="<%=path%>/ClockTerminalConfigController/downloadTemplet">点击下载模板</a>
	</div>
	<!-- 手动添加考场子钟信息信息 -->
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
		</form>
	</div>
	<!-- 自动搜索子钟 -->
	<div id="endPointAutoSearch" style="display:none">
		<form class="form-inline clearfix" id="endPointAutoSearchForm">
			<ul>
				<li class="form-group">
					<input type="radio" name="terminalIP" id="terminalIP" checked="checked">终端IP地址
				</li>
				<li class="form-group">
					<input type="radio" name="terminalIP" id="terminalIPRange">终端IP地址段
				</li>
				<li class="form-group" id="terminalIPInput">
					<input type="text" class="form-control" style="width: 120px">
				</li>
				<li class="form-group" id="terminalIPRangeStart" style="display: none">
					<input type="text" class="form-control" style="width: 120px">~
				</li>
				<li class="form-group" id="terminalIPRangeEnd" style="display: none">
					<input type="text" class="form-control" style="width: 120px">
				</li>
				<li class="form-group">
					<div class="pull-left">
						<a href="javascript:void(0)" class="searchBtn glyphicon glyphicon-search">
						</a>
					</div>
				</li>
				<li class="form-group" style="float: right">
					<input type="button" class="btn btn-default btn-primary" id="selectAllClock" value="全选">
				</li>
			</ul>
			<div class="error_statusul" style="height: 250px;width: 618px;overflow-x:hidden;overflow-y:auto">
				<ul id="addAutoSearchClock"></ul>
				<div id="autoSearchResultLoading" style="text-align: center">
					<img alt="" src="<%=path %>/devconfig/images/loading.gif">
				</div>
			</div>
		</form>
	</div>
	<!-- 编辑子钟信息 -->
	<div style="display: none" id="examroominfoedit">
		<form class="form-horizontal" id="examroominfoeditform" novalidate="novalidate">
			<div class="form-group">
				<label class="col-sm-3 control-label"><font class="red">*</font>考场子钟IP</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="examRoomIp" name="exam_room_ip">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场名称</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="examRoomName" name="exam_room_name">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场地址</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="examRoomAddr" name="exam_room_addr">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场负责人</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="examRoomPerson" name="exam_room_person">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">考场负责人电话</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" id="examRoomTel" name="exam_room_tel">
				</div>
			</div>
		</form>
	</div>
</body>
</html>