<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path =  request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>考点管理</title>
</head>
<body>
	 <link href="<%=path %>/manage/css/manage.css" rel="stylesheet">
  <script src="<%=path %>/manage/js/exampointer.js"></script>
  <div class="panel panel-default panel-nobody ">
     <div class="table-toolbar clearfix">
        <div class="btn-group btn-group-sm pull-right">
            <button id="btn-add" class="btn btn-default"><span class='glyphicon glyphicon-plus'></span>&nbsp;新增</button>
            <button id="btn-delete" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
        </div>
        <div class="table-toolbar-search pull-left clearfix">
          <form class="form-inline clearfix">
            <ul>
                <li class="form-group">
                    <select class="form-control" id="province">
                    </select>
                </li>
                <li class="form-group">
                    <select class="form-control" id="city">
                    </select>
                </li>
                <li class="form-group">
                    <select class="form-control" id="district" >
                    </select>
                </li>
                <li class="form-group">
                    <input type="text" class="form-control form-control-large pull-left" style="width: 250px" placeholder="请输入考点编号/考点名称搜索"/>
                </li>
            </ul>
          </form>
        </div>
     </div>
     <table id="exampointerGrid" data-undefined-text="没有检索到数据"></table>
  </div>
  <div style="display:none">
      <form  class="form-horizontal" id="exampointerAddForm" style="width:767px;margin-left:-132px;">
          <div class="form-group">
              <label for="examName" class="control-label col-sm-4">考点名称</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examName" name="examName"/>
              </div>
          </div>   
          <div class="form-group">
              <label for="examNum" class="control-label col-sm-4">考点编号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examNum" name="examNum" readonly/>
              </div>
              <span class="glyphicon glyphicon-repeat test-connect icon-connect" data-container="body" data-toggle="tooltip"  title="点击获取" style="margin-top:8px;margin-left:-7px"></span>
           <span class="fa fa-spinner test-connect" style="display: none;cursor: default;margin-top:8px;margin-left:-7px;" data-container="body" data-toggle="tooltip"  title="正在获取，请稍候"></span>
          </div>   
          <div class="form-group">
              <label for="examArea" class="control-label col-sm-4">所属区域</label>
              <div class="form-inline col-sm-6">
                  <select class="form-control" style="width:120px;" id="areaProvince">
                  </select>
                  <select class="form-control"style="width:120px;" id="areaCity">
                  </select>
                  <select class="form-control"style="width:120px;" id="areaDistrict">
                  </select>
              </div>
          </div>
          <div class="form-group">
              <label for="examAddress" class="control-label col-sm-4">考点地址</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examAddress"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPerson" class="control-label col-sm-4">考点负责人</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control"name="examPerson"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPhone" class="control-label col-sm-4">考点负责人电话</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examPhone"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examIPAddress" class="control-label col-sm-4">考点服务器IP</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examIPAddress" name="examIPAddress"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPort" class="control-label col-sm-4">考点服务器端口</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examPort" name="examPort" value="8080"/>
              </div>
          </div>
          <div class="form-group">
              <label for="number" class="control-label col-sm-4">顺序号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="ord"/>
              </div>
          </div>
      </form>
      
      <form  class="form-horizontal" id="exampointerCorrectForm" style="width:767px;margin-left:-132px;">
          <div class="form-group">
              <label for="examName" class="control-label col-sm-4">考点名称</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examName"/>
              </div>
          </div>   
          <div class="form-group">
              <label for="examNum" class="control-label col-sm-4">考点编号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examNum" readonly/>
              </div>
              <span class="glyphicon glyphicon-repeat test-connect icon-connect" data-container="body" data-toggle="tooltip"  title="点击获取" style="margin-top:8px;margin-left:-7px"></span>
           <span class="fa fa-spinner test-connect" style="display: none;cursor: default;margin-top:8px;margin-left:-7px;" data-container="body" data-toggle="tooltip"  title="正在获取，请稍候"></span>
          </div>   
          <div class="form-group">
              <label for="examArea" class="control-label col-sm-4">所属区域</label>
              <div class="form-inline col-sm-6">
                  <select class="form-control" style="width:120px;" id="area-province">
                  </select>
                  <select class="form-control"style="width:120px;" id="area-city">
                  </select>
                  <select class="form-control"style="width:120px;" id="area-district">
                  </select>
              </div>
          </div>
          <div class="form-group">
              <label for="examAddress" class="control-label col-sm-4">考点地址</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examAddress"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPerson" class="control-label col-sm-4">考点负责人</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control"name="examPerson"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPhone" class="control-label col-sm-4">考点负责人电话</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examPhone"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examIPAddress" class="control-label col-sm-4">考点服务器IP</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examIPAddress"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPort" class="control-label col-sm-4">考点服务器端口</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="examPort" value="8080"/>
              </div>
          </div>
          <div class="form-group">
              <label for="number" class="control-label col-sm-4">顺序号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" name="ord"/>
              </div>
          </div>
      </form>
  </div>
</body>
</html>