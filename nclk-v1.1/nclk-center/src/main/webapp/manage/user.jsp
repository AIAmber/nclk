<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String path =  request.getContextPath(); 
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>用户管理</title>
</head>
<body>
  <link href="<%=path %>/manage/css/manage.css" rel="stylesheet">
  <script src="<%=path %>/manage/js/user.js"></script>
  <div class="panel panel-default panel-nobody ">
     <div class="table-toolbar clearfix">
        <div class="btn-group btn-group-sm pull-right">
            <button id="btn-add" class="btn btn-default">
                <span class='glyphicon glyphicon-plus'></span>&nbsp;新增
            </button>
            <button id="btn-delete" class="btn btn-default">
                <span class='glyphicon glyphicon-trash'></span>&nbsp;删除
            </button>
             <button id="btn-unlock" class="btn btn-default">
                <span class='glyphicon glyphicon-wrench'></span>&nbsp;解锁
            </button>
        </div>
        <div class="table-toolbar-search pull-left clearfix">
          <form class="form-inline clearfix">
            <ul>
                <li class="form-group">
                    <div class="btn-group btn-group-sm" id="btn-group-tab">
                      <a class="btn btn-default active">全部</a>
                      <a class="btn btn-default">有效</a>
                      <a class="btn btn-default">无效</a>
                      <a class="btn btn-default">锁定</a>
                    </div>
                </li>
                <li class="form-group">
                    <input type="text" id="searchText" class="form-control form-control-large pull-left dynamic" style="width: 250px" placeholder="请输入姓名/用户名搜索"  />
                </li>
            </ul>
          </form>
        </div>
     </div>
     <table id="userGrid" data-undefined-text="没有检索到数据"></table>
  </div>
  <!-- 用户管理模态框 -->
  <div style="display:none">
      <form  class=" form-horizontal" id="userAddForm" style="width:973px;">
        <div style="float:left;width:549px">
            <div style="margin-bottom:32px">
              <img id="preview" src="<%=path %>/common/img/head.png"  onerror="this.src='<%=path %>/common/img/head.png'">
              <div id="btnGroup">
                <a id="uploadUserPhoto" class="btn btn-default btn-sm" style="float: left;">上传
                  <input type="file" name="PHOTO" id="PHOTO" />
                </a>
                <a id="deleteUserPhoto"  class="btn btn-default btn-sm"  style="float: right;cursor: pointer">删除</a>
              </div>
              <input type="hidden" name="photofilePath">
            </div>
           <div class="form-group" style="display:none">
              <label for="userId" class="control-label col-sm-2">用户ID</label>
              <div class="col-sm-4">
                  <input type="text" class="form-control" id="userId" name="userId"/>
              </div>
          </div>
           <div class="form-group">
              <label for="perName" class="control-label col-sm-2"><font class="red">*</font>姓名</label>
              <div class="col-sm-4">
                  <input type="text" class="form-control" id="perName" name="perName"/>
              </div>
          </div>
          <div class="form-group">
              <label for="userName" class="control-label col-sm-2" ><font class="red">*</font>用户名</label>
              <div class="col-sm-4">
                  <input type="text" class="form-control" id="userName" name="userName"  />
              </div>
          </div>
          <div id="passwordInput" class="form-group">
              <label for="password" class="control-label col-sm-2" ><font class="red">*</font>密码</label>
              <div class="col-sm-4">
                  <input type="password" class="form-control"  id="addPassword" name="addPassword" autocomplete="off"/>
              </div>
          </div>
          <div id="confirmPasswordInput" class="form-group">
              <label for="confirmPassword" class="control-label col-sm-2"><font class="red">*</font>确认密码</label>
              <div class="col-sm-4">
                  <input type="password" class="form-control"  id="addConfirmPassword" name="addConfirmPassword" autocomplete="off"/>
              </div>
          </div>
          <div class="form-group">
              <label for="state" class="control-label col-sm-2"><font class="red">*</font>状态</label>
              <div class="col-sm-4" style="margin-top:-8px">
                  <div class="radio-inline"  style="width:40%">
                      <label class="control-label">
                          <input type="radio" name="state" id="optionsRadios1" value="A" checked>有效
                      </label>
                  </div>
                  <div class="radio-inline" style="width:40%">
                      <label class="control-label" >
                          <input type="radio" name="state" id="optionsRadios2" value="X">无效
                      </label>
                  </div>
              </div>
          </div>
          <div class="form-group">
              <label for="ord" class="control-label col-sm-2">顺序号</label>
              <div class="col-sm-4">
                  <input type="number" min="0" class="form-control"  id="ord" name="ord"/>
              </div>
          </div>
          </div>
          <div style="float:left ;margin-left:-295px">
          <label for="authority" class="control-label " style="margin-left:42px">权限</label>
              <div class="form-group">
              <div class="col-sm-6" style="margin-left:53px;width:222px">
                  <div id="authority-tree" style="max-height:230px;overflow-y:auto" >
                      <ul id="authority-treeContent" class="ztree"></ul>
                  </div> 
              </div>
            </div>
          </div>
          <div style="float:left;width:314px;margin-left:-100px;" >
          <label for="manageArea" class="control-label" style="margin-left:72px">管辖区域</label>
           <div class="form-group">
              <div class="col-sm-6" style="margin-left:80px">
                  <div id="manageArea-tree" style="overflow-y:auto;height:338px;width:239px;margin-top:7px;" >
                     <ul id="manageArea-treeContent" class="ztree"></ul>
                  </div>        
              </div>
          </div>
          </div>
      </form>
  </div>
  <!--密码修改模态框  -->
  <div style="display:none">
      <form class="form-horizontal" id="changePasswordForm" style="width:550px;margin-left:25px">
          <div class="form-group">
              <label for="newPassword" class="control-label col-sm-2"><font class="red">*</font>新密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control" id="newPassword" name="newPassword" autocomplete="off"/>
              </div>
          </div>
          <div class="form-group">
              <label for="newConfirmPassword" class="control-label col-sm-2"><font class="red">*</font>确认密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" autocomplete="off"/>
              </div>
          </div>
      </form>
  </div>
  <!--权限设置模态框  -->
   <div style="display:none">
      <form class="form-horizontal" id="authorityForm">
      <div id="authority-treeDialog" >
          <ul id="authority-treeContentDialog" class="ztree"></ul>
      </div> 
      </form> 
  </div>
  <!--区域管理设置模态框  -->
   <div style="display:none">
      <form class="form-horizontal" id="manageAreaForm">
          <div id="manageArea-treeDialog" style="overflow-y:auto;width:310px;height:395px;margin-top:7px;" >
                     <ul id="manageArea-treeDialogContent" class="ztree"></ul>
           </div> 
      </form> 
  </div>
</body>
</html>