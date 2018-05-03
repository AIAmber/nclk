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
            <button id="btn-import" role="group" type="button" class="btn btn-default"><span class="glyphicon glyphicon-log-in"></span>&nbsp;导入</button>
            <button id="btn-delete" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
        </div>
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
                    <select class="form-control" id="province" style="width:115px">
                      <option value="">请选择省</option>
                    </select>
                </li>
                <li class="form-group">
                    <select class="form-control" id="city" style="width:115px">
                      <option value="">请选择市</option>
                    </select>
                </li>
                <li class="form-group">
                    <select class="form-control" id="district" style="width:115px">
                      <option value="">请选择县区</option>
                    </select>
                </li>
                <li class="form-group">
                    <input type="text" id="searchText" class="form-control form-control-large pull-left dynamic " style="width: 300px" placeholder="请输入考点编号/考点名称/考点服务器IP搜索"/>
                </li>
            </ul>
          </form>
        </div>
     </div>
     <table id="exampointerGrid" ></table>
  </div>
  <!--新增数据表单  -->
  <div style="display:none">
      <form  class="form-horizontal" id="exampointerAddForm" style="width:767px;margin-left:-132px;">
          <div class="form-group" style="display:none">
              <label for="examID" class="control-label col-sm-4">考点ID</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examID_add" name="examID_add"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examName" class="control-label col-sm-4"><font class="red">*</font>考点名称</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examName_add" name="examName_add"/>
              </div>
          </div>
           <div class="form-group">
              <label for="examIPAddress" class="control-label col-sm-4"><font class="red">*</font>考点服务器</label>
              <div class="col-sm-4">
                  <input type="text" class="form-control" id="examIPAddress_add" name="examIPAddress_add"/>
              </div>
              <div class="col-sm-2" >
                  <input type="number" min="0" max="65535" style="margin-left:-12px;width:115px" class="form-control" id="examPort_add" name="examPort_add" value="8080"/>
              </div>
              <font class="col-sm-1" style="font-weight:bold ;margin-left:-156px; margin-top:4px" >:</font>
          </div>
          <div class="form-group">
              <label for="examNum" class="control-label col-sm-4"><font class="red">*</font>考点编号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examNum_add" name="examNum_add" placeholder="请点击右方按钮自动获取/手动录入" />
              </div>
              <span class="glyphicon glyphicon-repeat test-connect icon-connect" id="icon-connect-add" data-container="body" data-toggle="tooltip"  title="点击获取" style="margin-top:8px;margin-left:-7px"></span>
           <span class="fa fa-spinner test-connect" id="test-connect-add" style="display: none;cursor: default;margin-top:8px;margin-left:-7px;" data-container="body" data-toggle="tooltip"  title="正在获取，请稍候"></span>
          </div>   
          <div class="form-group">
              <label for="examArea" class="control-label col-sm-4"><font class="red">*</font>所属区域</label>
              <div class="form-inline col-sm-6">
                  <select class="form-control" style="width:120px;" id="areaProvince_add">
                  <option value="">请选择省</option>
                  </select>
                  <select class="form-control"style="width:120px;" id="areaCity_add">
                  <option value="">请选择市</option>
                  </select>
                  <select class="form-control"style="width:120px;" id="areaDistrict_add">
                  <option value="">请选择县区</option>
                  </select>
              </div>
          </div>
          <div class="form-group">
              <label for="examAddress" class="control-label col-sm-4">考点地址</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examAddress_add" name="examAddress_add"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPerson" class="control-label col-sm-4">考点负责人</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examPerson_add" name="examPerson_add"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPhone" class="control-label col-sm-4">考点负责人电话</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examPhone_add" name="examPhone_add"/>
              </div>
          </div>
          <div class="form-group">
              <label for="state" class="control-label col-sm-4"><font class="red">*</font>状态</label>
              <div class="col-sm-6" style="margin-top:-8px">
                  <div class="radio-inline"  style="width:40%" >
                      <label class="control-label">
                          <input type="radio" name="state_add" id="optionsRadios1_add" value="A" checked>有效
                      </label>
                  </div>
                  <div class="radio-inline" style="width:40%">
                      <label class="control-label" >
                          <input type="radio" name="state_add" id="optionsRadios2_add" value="X">无效
                      </label>
                  </div>
              </div>
          </div>
          <div class="form-group">
              <label for="number" class="control-label col-sm-4">顺序号</label>
              <div class="col-sm-6">
                  <input type="number"  class="form-control" id="ord_add" name="ord_add"/>
              </div>
          </div>
      </form>
    </div>
    <!--编辑表单  -->
    <div style="display:none">
      <form  class="form-horizontal" id="exampointerEditForm" style="width:767px;margin-left:-132px;">
          <div class="form-group" style="display:none">
              <label for="examID" class="control-label col-sm-4">考点ID</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examID_edit" name="examID_edit"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examName" class="control-label col-sm-4"><font class="red">*</font>考点名称</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examName_edit" name="examName_edit"/>
              </div>
          </div>
           <div class="form-group">
              <label for="examIPAddress" class="control-label col-sm-4"><font class="red">*</font>考点服务器</label>
              <div class="col-sm-4">
                  <input type="text" class="form-control" id="examIPAddress_edit" name="examIPAddress_edit"/>
              </div>
              <div class="col-sm-2" >
                  <input type="number" min="0" max="65535" style="margin-left:-12px;width:115px" class="form-control" id="examPort_edit" name="examPort_edit" value="8080"/>
              </div>
              <font class="col-sm-1" style="font-weight:bold ;margin-left:-156px; margin-top:4px" >:</font>
          </div>
          <div class="form-group">
              <label for="examNum" class="control-label col-sm-4"><font class="red">*</font>考点编号</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examNum_edit" name="examNum_edit" placeholder="请点击右方按钮自动获取/手动录入" />
              </div>
              <span class="glyphicon glyphicon-repeat test-connect icon-connect" id="icon-connect-edit" data-container="body" data-toggle="tooltip"  title="点击获取" style="margin-top:8px;margin-left:-7px"></span>
           <span class="fa fa-spinner test-connect" id="test-connect-edit" style="display: none;cursor: default;margin-top:8px;margin-left:-7px;" data-container="body" data-toggle="tooltip"  title="正在获取，请稍候"></span>
          </div>   
          <div class="form-group">
              <label for="examArea" class="control-label col-sm-4"><font class="red">*</font>所属区域</label>
              <div class="form-inline col-sm-6">
                  <select class="form-control" style="width:120px;" id="areaProvince_edit">
                  <option value="">请选择省</option>
                  </select>
                  <select class="form-control"style="width:120px;" id="areaCity_edit">
                  <option value="">请选择市</option>
                  </select>
                  <select class="form-control"style="width:120px;" id="areaDistrict_edit">
                  <option value="">请选择县区</option>
                  </select>
              </div>
          </div>
          <div class="form-group">
              <label for="examAddress" class="control-label col-sm-4">考点地址</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examAddress_edit" name="examAddress_edit"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPerson" class="control-label col-sm-4">考点负责人</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examPerson_edit" name="examPerson_edit"/>
              </div>
          </div>
          <div class="form-group">
              <label for="examPhone" class="control-label col-sm-4">考点负责人电话</label>
              <div class="col-sm-6">
                  <input type="text" class="form-control" id="examPhone_edit" name="examPhone_edit"/>
              </div>
          </div>
          <div class="form-group">
              <label for="state" class="control-label col-sm-4"><font class="red">*</font>状态</label>
              <div class="col-sm-6" style="margin-top:-8px">
                  <div class="radio-inline"  style="width:40%" >
                      <label class="control-label">
                          <input type="radio" name="state_edit" id="optionsRadios1_edit" value="A" checked>有效
                      </label>
                  </div>
                  <div class="radio-inline" style="width:40%">
                      <label class="control-label" >
                          <input type="radio" name="state_edit" id="optionsRadios2_edit" value="X">无效
                      </label>
                  </div>
              </div>
          </div>
          <div class="form-group">
              <label for="number" class="control-label col-sm-4">顺序号</label>
              <div class="col-sm-6">
                  <input type="number"  class="form-control" id="ord_edit" name="ord"/>
              </div>
          </div>
      </form>
  </div>
  <!-- 考点信息手动导入 -->
  <div id="endpointimport" style="display: none">
    <form enctype="multipart/form-data" class="form-horizontal" id="endPointInfo">
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input id="filename" name="filename" type="text" class="form-control" readonly="readonly" placeholder="请选择xlsx格式的文件进行上传！" required="" aria-describedby="filename-error" aria-invalid="false">
            <div class="input-group-btn" style="cursor:pointer;">
              <button id="selectFile" class="btn btn-default" type="button" style="position: relative;">选择文件</button>
              <input id="file" type="file" name="file" class="form-control" style="cursor:pointer;filter: alpha(opacity = 0);opacity: 0;background-color:red;position: absolute;top: 0px;right: 0px;width:163px;height:42px" required="">
            </div>
            </div>
        <small id="filename-error" class="text-danger"></small></div>
      </div>
    </form>
    <a type="" href="<%=path%>/EndPointController/downloadTemplet">考点信息-模板下载</a>
  </div>
  
  
</body>
</html>