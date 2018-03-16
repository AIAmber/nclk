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
	<script src="<%=path %>/devconfig/js/timeserverconfig.js"></script>
	<fieldset>
		<div class="clearfix tab-content">
	  		<div class="table-toolbar clearfix" id="toolbar-cataloged" >
				<div class="table-toolbar-search pull-left clearfix" style="padding-right: 30px;">
					<form class="form-inline clearfix">
						<ul>
							<li class="form-group">
								<select class="form-control pull-left" style="width: 10px">
							    	<option selected="selected" value=""></option>
						   		    <option>江苏省</option>
						   	    </select>
							</li>
							<li class="form-group">
								<select class="form-control pull-left" name="" style="width: 100px">
							    	<option selected="selected" value=""></option>
						   		    <option>南京市</option>
						   	    </select>
							</li>
							<li class="form-group">
								<select class="form-control pull-left" name="" style="width: 100px">
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
				<div class="btn-group btn-group-sm table-toolbar-btn-group" style="float: right;">
					<a class="btn btn-default"><span class="glyphicon glyphicon-trash"></span>&nbsp;删除</a>
				</div>
	  		</div>
	  		<table id="grids-timeserverconfig" data-undefined-text="没有检索到数据"></table>
		</div>
	</fieldset>
</body>
</html>