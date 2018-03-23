<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path =  request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>区域管理页面</title>
</head>
<body>
  <link href="<%=path %>/manage/css/area.css" rel="stylesheet">
	<link href="<%=path %>/manage/css/manage.css" rel="stylesheet">
  <script src="<%=path %>/manage/js/area.js"></script>
  <div class="panel panel-default panel-nobody clearfix area-content">
    <div class="area-tree">
      <div class="panel panel-default">
                <div class="panel-heading clearfix">
                  <span class="pull-left title">区域管理</span>
                    <div class="pull-right btns">
              <span id="tree-add" class='glyphicon glyphicon-plus font-btn' title="新增"></span>
              <span id="tree-update" class='fa fa-pencil-square-o update font-btn' title="修改"></span>
              <span id="tree-del" class='glyphicon glyphicon-trash font-btn' title="删除"></span>
          </div>
                </div>
                <div class="panel-body">
                    <ul id="area_tree" class="ztree "></ul>
                </div>
            </div>
    </div>
    <div class="panel panel-default panel-nobody area-table">
      <div class="table-toolbar clearfix">
        <div class="table-toolbar-search pull-left clearfix">
          <form class="form-inline clearfix">
            <ul>
              <li class="form-group">
                <input id="test" class="form-control form-control-large pull-left" style="width: 250px" placeholder="请输入省/市/区县指挥中心名称搜索"/>
              </li>
            </ul>
          </form>
        </div>
        <div class="btn-group btn-group-sm table-toolbar-btn-group">
            <button id="btn-delete" type="button" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
        </div>
        </div>
        <table id="areaGrid"  data-undefined-text="没有检索到数据" ></table>
    </div>
  </div>
  <div style="display: none;">
    <div id="modelContent">
      <form class="form-horizontal" id="add-info">
        <div class="form-group">
          <label class="col-sm-3 control-label">区域名称</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" name="areaName" required>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label">区域类型</label>
          <div class="col-sm-8">
            <select class="form-control" name="areaType" required>
                <option selected="selected" value=""></option>
                <option>省级指挥中心</option>
                <option>市级指挥中心</option>
                <option>区县级指挥中心</option>
            </select>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label">上级区域</label>
          <div class="col-sm-8">
            <div class="input-group">
              <input id="selected-node-info" type="text" class="form-control" id="code" readonly="readonly">
              <span class="input-group-btn">
                <button id="btn-show-select-tree" class="btn-show-select-tree btn btn-default" type="button">选择</button>
              </span>
             </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label">顺序号</label>
          <div class="col-sm-8">
            <input type="number" class="form-control" name="order">
          </div>
        </div>
      </form>
    </div>
    <div id="treeContent" style="height:420px;overflow-y:auto">
      <ul id="treeContentDemo" class="ztree"></ul>
    </div>
  </div>
</body>
</html>