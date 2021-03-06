<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path =  request.getContextPath(); %>
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
            <button id="btn-add" class="btn btn-default"><span class='glyphicon glyphicon-plus'></span>&nbsp;新增</button>
            <button id="btn-delete" class="btn btn-default"><span class='glyphicon glyphicon-trash'></span>&nbsp;删除</button>
        </div>
        <div class="table-toolbar-search pull-left clearfix">
          <form class="form-inline clearfix">
            <ul>
                <li class="form-group">
                    <input type="text" class="form-control form-control-large pull-left" style="width: 250px" placeholder="请输入用户姓名/用户名搜索"/>
                </li>
            </ul>
          </form>
        </div>
     </div>
     <table id="userGrid" data-undefined-text="没有检索到数据"></table>
  </div>
  <!-- 用户管理模态框 -->
  <div style="display:none">
      <form action="" class=" form-horizontal" id="userAddForm" style="width:800px;">
          <div style="float:left;width:486px">
              <div class="form-group">
              <label for="perName" class="control-label col-sm-2">姓名</label>
              <div class="col-sm-8">
                  <input type="text" class="form-control" id="perName" name="perName"/>
              </div>
          </div>
          <div class="form-group">
              <label for="userName" class="control-label col-sm-2">用户名</label>
              <div class="col-sm-8">
                  <input type="text" class="form-control" id="userName" name="userName" />
              </div>
          </div>
          <div class="form-group">
              <label for="password" class="control-label col-sm-2">密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control"  id="addPassword" name="addPassword"/>
              </div>
          </div>
          <div class="form-group">
              <label for="confirmPassword" class="control-label col-sm-2">确认密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control"  id="addConfirmPassword" name="addConfirmPassword"/>
              </div>
          </div>
          <div class="form-group">
              <label for="authority" class="control-label col-sm-2">权限</label>
              <div class="col-sm-8">
                  <table class="table  table-bordered">
                      <tbody>
                          <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-all-input"/><span style="margin-left:5px;font-weight:bold">基础设置</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">区域管理</span>
                              <input type="checkbox" class="choose-single" style="margin-left:25px"/><span style="margin-left:5px;">考点管理</span>
                              <input type="checkbox" class="choose-single" style="margin-left:25px"/><span style="margin-left:5px;">用户管理</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-all-input"/><span style="margin-left:5px;font-weight:bold">设备设置</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">授时服务器配置</span>
                              <input type="checkbox" class="choose-single" style="margin-left:35px"/><span style="margin-left:5px;">网络时钟终端配置</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-all-input"/><span style="margin-left:5px;font-weight:bold">运行监控</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2">
                              <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">实时状态监控</span>
                              <input type="checkbox" class="choose-single" style="margin-left:50px"/><span style="margin-left:5px;">异常状态统计</span>
                          </td>
                      </tr>
                      <tr>
                          <td colspan="2" style="text-align:center">
                              <input type="checkbox" id="choose-all-btn"/><span style="margin-left:5px;">全选</span>
                          </td>
                      </tr>
                      </tbody>
                  </table>
              </div>
          </div>
          </div>
          <div style="float:left;width:314px;margin-left:-50px;" >
               <div class="form-group">
              <label for="manageArea" class="control-label col-sm-3">管辖区域</label>
              <div class="col-sm-6">
                  <div id="manageArea-tree" style="overflow-y:auto;height:450px;width:236px;margin-top:7px;" >
                     <ul id="manageArea-treeContent" class="ztree"></ul>
                  </div>        
              </div>
          </div>
          </div>
      </form>
  </div>
  <!--密码修改模态框  -->
  <div style="display:none">
      <form class="form-horizontal" id="changePasswordForm" style="width:486px;margin-left:25px">
        <div class="form-group">
              <label for="Password" class="control-label col-sm-2">原密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control" id="password" name="password"/>
              </div>
          </div>
          <div class="form-group">
              <label for="newPassword" class="control-label col-sm-2">新密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control" id="newPassword" name="newPassword"/>
              </div>
          </div>
          <div class="form-group">
              <label for="newConfirmPassword" class="control-label col-sm-2">确认密码</label>
              <div class="col-sm-8">
                  <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"/>
              </div>
          </div>
      </form>
  </div>
  <!--权限设置模态框  -->
  <!-- <div style="display:none">
      <form class="form-horizontal" id="authorityForm">
          <table class="table  table-bordered">
              <tbody>
                  <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-all-input" /><span style="margin-left:5px;font-weight:bold">基础设置</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">区域管理</span>
                      <input type="checkbox"class="choose-single" style="margin-left:25px"/><span style="margin-left:5px;">考点管理</span>
                      <input type="checkbox" class="choose-single" style="margin-left:25px"/><span style="margin-left:5px;">用户管理</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-all-input" /><span style="margin-left:5px;font-weight:bold">设备设置</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">授时服务器配置</span>
                      <input type="checkbox" class="choose-single" style="margin-left:55px"/><span style="margin-left:5px;">网络时钟终端配置</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-all-input"/><span style="margin-left:5px;font-weight:bold">运行监控</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2">
                      <input type="checkbox" class="choose-single"/><span style="margin-left:5px;">实时状态监控</span>
                      <input type="checkbox" class="choose-single" style="margin-left:70px"/><span style="margin-left:5px;">异常状态统计</span>
                  </td>
              </tr>
              <tr>
                  <td colspan="2" style="text-align:center">
                      <input type="checkbox" class="choose-all-btn"/><span style="margin-left:5px;">全选</span>
                  </td>
              </tr>
              </tbody>
          </table>
      </form> -->
  </div>
</body>
</html>