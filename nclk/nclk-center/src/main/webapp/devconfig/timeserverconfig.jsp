<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path =  request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>授时服务器配置</title>
	
</head>
<body>
	<script src="<%=path %>/devconfig/js/timeserverconfig.js"></script>
	<link href="<%=path %>/devconfig/css/devconfig.css" rel="stylesheet">
	<fieldset>
		<div class="clearfix tab-content">
	  		<div class="table-toolbar clearfix" id="toolbar-cataloged" >
				<div class="table-toolbar-search pull-left clearfix" style="padding-right: 30px;">
					<form class="form-inline clearfix">
						<ul>
							<li class="form-group">
								<select class="form-control pull-left" style="width: 10px;">
							    	<option selected="selected" value=""></option>
						   		    <option>江苏省</option>
						   	    </select>
							</li>
							<li class="form-group">
								<select class="form-control pull-left" name="" style="width:100px;">
							    	<option selected="selected" value=""></option>
						   		    <option>南京市</option>
						   	    </select>
							</li>
							<li class="form-group">
								<select class="form-control pull-left" name="" style="width:100px;">
							    	<option selected="selected" value=""></option>
						   		    <option>建邺区</option>
						   	    </select>
							</li>
							<li class="form-group">
                            	<input class="form-control form-control-large pull-left" placeholder="请输入设备编号/授时ip进行查询" style="width: 250px"/>
                        	</li>
						</ul>
					</form>
					<div class="search-btn clearfix">
						<div class="pull-left">
							<a href="javascript:void(0)" class="searchBtn glyphicon glyphicon-search"></a>
						</div>
					</div>
				</div>
				<div class="btn-group btn-group-sm table-toolbar-btn-group">
			 		<button id="btn-add" role="group" type="button" class="btn btn-default"><span class='glyphicon glyphicon-plus'></span>&nbsp;新增</button>
			 	 	<button id="btn-delete" role="group" type="button" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
				</div>
	  		</div>
	  		<table id="grids-timeserverconfig" data-undefined-text="没有检索到数据"></table>
		</div>
	</fieldset>
	<!-- 授时服务器配置 修改页面 -->
	<div id="editTimeServerConfig" style="display: none">
		<form class="form-horizontal" id="" novalidate="novalidate">
			<div class="form-group">
				<label class="col-sm-3 control-label"><font class="red">*</font>设备编号</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="name" required="" readonly="readonly">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label"><font class="red">*</font>时间源</label>
				<div class="col-sm-8">
					<select class="form-control" name="">
				    	<option value="">请选择时间源</option>
			   		    <option selected="selected">GSP</option>
			   		    <option>北斗</option>
			   		    <option>上级时间</option>
			   	    </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">当前时间</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="" value="12:12:30">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">授时ip地址</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="" value="192.168.1.3">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">设备状态</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="" value="正常">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">所属考点</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="" value="南京市金陵中学">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">是否启用</label>
				<div class="col-sm-8">
					<div class="img-switch img-open" title="启用"></div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>