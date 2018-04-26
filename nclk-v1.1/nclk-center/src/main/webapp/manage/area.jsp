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
        							<div class="btn-group btn-group-sm" id="btn-group-tab">
        								<a class="btn btn-default active">全部</a>
        								<a class="btn btn-default">有效</a>
        								<a class="btn btn-default">无效</a>
        							</div>
  						      </li>
  		              <li class="form-group">
  		                	<input id="searchText" class="form-control form-control-large pull-left dynamic" style="width: 250px" placeholder="请输入区域名称搜索"/>
  		             	</li>
  		            </ul>
  		         </form>
  	        </div>
  	        <div class="btn-group btn-group-sm table-toolbar-btn-group">
  	            <button id="btn-delete" type="button" class="btn btn-default">
  	            	<span class='glyphicon glyphicon-trash'></span>&nbsp;删除
  	            </button>
  	        </div>
	       </div>
	        <table id="areaGrid"  data-undefined-text="没有检索到数据" ></table>
	   </div>
  </div>
  <!-- 新增表单开始-->
  <div style="display: none;">
    <div id="modelContentAdd">
      <form class="form-horizontal" id="add-info">
        <div class="form-group" style="display:none">
           <label class="col-sm-3 control-label">区域ID</label>
           <div class="col-sm-8">
             <input type="text" class="form-control" id="areaId_add" name="areaId_add" >
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>区域名称</label>
           <div class="col-sm-8">
             <input type="text" class="form-control" id="areaName_add" name="areaName_add" >
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>区域类型</label>
           <div class="col-sm-8">
             <select class="form-control" id="areaType_add" name="areaType_add">
                <option selected="selected" value="">请选择区域类型</option>
                <option value="1">省级指挥中心</option>
                <option value="2">市级指挥中心</option>
                <option value="3">县区级指挥中心</option>
             </select>
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>上级区域</label>
           <div class="col-sm-8">
             <select class="form-control" id="upArea_add" name="upArea_add">
                  <option selected="selected" value="">请选择上级区域</option>
               </select>
          	</div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label"><font class="red">*</font>状态</label>
          <div class="col-sm-8" style="margin-top:-8px">
              <div class="radio-inline" style="width:40%">
                <label class="control-label">
                    <input type="radio" name="state_add" id="optionsRadios1_add"  value="A" checked>有效
                </label>
              </div>
              <div class="radio-inline" style="width:40%" >
                  <label class="control-label" >
                      <input type="radio" name="state_add" id="optionsRadios2_add" value="X">无效
                  </label>
              </div>
          </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label">顺序号</label>
           <div class="col-sm-8">
           		<input type="number" min="0" class="form-control" id="ord_add" name="ord_add">
           </div>
        </div>
      </form>
    </div>
  </div>
  
  
    <!-- 编辑表单 开始-->
  <div style="display: none;">
    <div id="modelContentEdit">
      <form class="form-horizontal" id="edit-info">
        <div class="form-group" style="display:none">
           <label class="col-sm-3 control-label">区域ID</label>
           <div class="col-sm-8">
             <input type="text" class="form-control" id="areaId_edit" name="areaId_edit" >
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>区域名称</label>
           <div class="col-sm-8">
             <input type="text" class="form-control" id="areaName_edit" name="areaName_edit" >
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>区域类型</label>
           <div class="col-sm-8">
             <select class="form-control" id="areaType_edit" name="areaType_edit">
                <option selected="selected" value="">请选择区域类型</option>
                <option value="1">省级指挥中心</option>
                <option value="2">市级指挥中心</option>
                <option value="3">县区级指挥中心</option>
             </select>
           </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label"><font class="red">*</font>上级区域</label>
           <div class="col-sm-8">
             <select class="form-control" id="upArea_edit" name="upArea_edit">
                  <option selected="selected" value="">请选择上级区域</option>
               </select>
            </div>
        </div>
        <div class="form-group">
          <label class="col-sm-3 control-label"><font class="red">*</font>状态</label>
          <div class="col-sm-8" style="margin-top:-8px">
              <div class="radio-inline" style="width:40%">
                <label class="control-label">
                    <input type="radio" name="state_edit" id="optionsRadios1_edit"  value="A" checked>有效
                </label>
              </div>
              <div class="radio-inline" style="width:40%" >
                  <label class="control-label" >
                      <input type="radio" name="state_edit" id="optionsRadios2_edit" value="X">无效
                  </label>
              </div>
          </div>
        </div>
        <div class="form-group">
           <label class="col-sm-3 control-label">顺序号</label>
           <div class="col-sm-8">
              <input type="number" min="0" class="form-control" id="ord_edit" name="ord_edit">
           </div>
        </div>
      </form>
    </div>
  </div>
</body>
</html>