<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cn.com.sparknet.nclk.config.Config,java.util.Map,java.util.List"%>
<%
	String path = request.getContextPath();
	//用户id
	String userId = (String)request.getSession().getAttribute("USER_ID");
	//用户姓名
	String name = (String)request.getSession().getAttribute("NAME");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
	<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>江苏省教育考试院网络时钟监控系统</title>
	<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/common/img/favicon.ico" />
	<link rel="stylesheet" href="<%=path%>/common/plugins/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="<%=path%>/common/plugins/bootstrap-dialog/css/bootstrap-dialog.min.css"/>
	<link rel="stylesheet" href="<%=path%>/common/plugins/daterangepicker/css/daterangepicker.css"/>
	<link rel="stylesheet" href="<%=path%>/common/plugins/font-awesome/css/font-awesome.min.css"/>
	<link rel="stylesheet" href="<%=path%>/common/css/style.css"/>
	<script data-main="<%=path%>/common/js/require.config" src="<%=path%>/common/plugins/requirejs/require.min.js"></script>
	<!-- bootstrap3支持ie8和媒体查询插件 -->
	<!--[if lt IE 9]>
   		<script src="<%=path%>/common/plugins/bootstrap/js/html5shiv.min.js"></script>
   		<script src="<%=path%>/common/plugins/bootstrap/js/respond.min.js"></script>
   		<script src="<%=path%>/common/plugins/css3-mediaqueries/css3-mediaqueries.js"></script>
   	<![endif]-->
</head>
<body style="overflow-x:hidden;overflow-y:hidden">
	<header class="main-header">
		<nav class="navbar navbar-static-top" style="height: 52px;">
			<img src="<%=path%>/common/img/logo.png" style="margin-left: 10px;margin-top: 7px;width: 34px;color: white;float: left;">
			<span style="display: block;float: left; margin-left: 10px;margin-top: 8px;font-size: 23px;color: white;">江苏省教育考试院网络时钟监控系统</span>
			<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
				  <li class="dropdown notifications-menu">
		            <a href="#/home" class="dropdown-toggle" >
		            	<img alt="" src="<%=path%>/common/img/home.png" style="margin-top: -5px;">
		            </a>
		          </li>		
	         	  <!-- User Account -->
				  <li class="dropdown user user-menu">
					  <a href="#" class="dropdown-toggle" data-toggle="dropdown">
					     <img id="photoCircle" src="<%=path %>/UserController/findUserPhotoById?userId=<%=userId %>" onerror="this.src='<%=path %>/common/img/protrait.png'" class="user-image">
					  	 <span class="hidden-xs"><%=name %></span>
					  	 <input type="hidden" value="<%=userId %>" name="userid">
					  </a>
					<ul class="dropdown-menu" id="userPhoto">
					  <li class="user-header">
					    <img id="userImagephoto" src="<%=path %>/UserController/findUserPhotoById?userId=<%=userId %>" onerror="this.src='<%=path %>/common/img/protrait.png'" class="img-circle">
					    <div style="width: 50px;margin: auto;margin-top: 0px;overflow: hidden;">
					  		<a class="glyphicon glyphicon-edit" style="float: left;color:  white;opacity: 0.5;">
					  			<input type="file" name="REPORT" id="REPORT" style="position: absolute;cursor: pointer;filter: alpha(opacity = 0);opacity: 0;width: 148px;top: 0px;right: 0px;height: 100%;"/>
					  		</a>
					  		<a class="glyphicon glyphicon-trash" id="updelete" style="float: right;cursor: pointer;color: white;opacity: 0.5;"></a>
					  	</div>
					  	<p style="margin-top: 6px;font-size: 16px;"><%=name %></p>
					  	<input type="hidden" name="filePath">
					  </li>
					  <!-- Menu Footer-->
					  <li class="user-footer">
					     <div class="btn-group">
					 		<button type="button" class="btn btn-default btn-pass-edit" id="changepassword">
					 			<i class="fa fa-lock user-foot-button" aria-hidden="true">&nbsp;更改密码</i>
								</button>
					 		<button type="button" class="btn btn-default btn-login-out" id="loginout">
							<i class="fa fa-sign-out user-foot-button" aria-hidden="true" >&nbsp;注销</i>
							</button>
						</div>
					  </li>
					</ul>
				</li>
	        </ul>
	      </div>
	    </nav>
  	</header>
	<div class="down-main">
		<div class="left-main left-full">
			<div class="sidebar-fold">
				<span class="glyphicon glyphicon-menu-hamburger"></span>
			</div>
			<div class="subNavBox">
			<!-- 循环遍历左侧菜单 -->
			<%
			List<Map<String,Object>> list = null;
			if(null != request.getSession().getAttribute("moduleTreeList")){
				list = (List<Map<String,Object>>)request.getSession().getAttribute("moduleTreeList");
			}
			
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i <list.size() ; i++) {
				if(list.get(i).get("PARENT_MODULE_ID") == null){
						sb.append("<div class='sBox'>");
						sb.append("<div class='subNav sublist-up'>");
						sb.append("<i class='"+list.get(i).get("MODULE_ICON")+"' aria-hidden='true' ></i>");
						sb.append("<span class='sublist-title'>"+list.get(i).get("MODULE_NAME")+"</span></div>");
						sb.append("<ul class='navContent' style='display:none'>");
						for (int j = 0; j < list.size(); j++) {
							if(null != list.get(j).get("PARENT_MODULE_ID") && list.get(j).get("PARENT_MODULE_ID").equals(list.get(i).get("MODULE_ID"))){
								sb.append("<li><div class='showtitle'><img src='common/img/leftimg.png' />"+list.get(j).get("MODULE_NAME"));
								sb.append("</div><a href='"+list.get(j).get("MODULE_URL")+"'>");
								sb.append("<span class='"+list.get(j).get("MODULE_ICON")+"'></span>");
								sb.append("<span class='sub-title'>"+list.get(j).get("MODULE_NAME")+"</span>");					
								sb.append("</a></li>");
							}
						}		
						sb.append("</ul></div>");			
					}
				}%>
				<%=sb.toString() %>			
			</div>
		</div>
		<div class="right-product my-index right-full">
			<!-- 后退 前进 刷新 首页按钮 -->
			<div class="right-product-div">
				<div class="breadcrumb breadcrumb-title">
					<div id="headnavigation"></div>
					<a href="JavaScript:history.go(-1);" class="a-back">
						<i class="fa fa-arrow-left" aria-hidden="true" data-toggle="tooltip" data-placement="bottom" title="后退"></i>
					</a>
					<a href="JavaScript:history.go(1);" class="a-forward">
						<i class="fa fa-arrow-right" aria-hidden="true" data-toggle="tooltip" data-placement="bottom" title="前进"></i>
					</a>
					<a href="javascript:void(0)" onclick="location.reload();" class="a-refresh">
						<i class="fa fa-repeat" aria-hidden="true" data-toggle="tooltip" data-placement="bottom" title="刷新"></i>
					</a>
				</div>
			</div>
			<!-- 引入其他页面区域 -->
			<div class="container-fluid">
				<div class="info-center" id="rightContent" style="margin:10px 0px 10px 10px;overflow-x:hidden;overflow-y:auto;"></div>
			</div>
			<!-- 底部版权信息 -->
			<div class="footer" style="z-index: 10000;height: 30px;">
				<span class="footer-span">Copyright © 2012-<span id="currentDate"></span>&nbsp;<a class="footer-spanstyle" href="http://www.sparksoft.com.cn" target="_blank"><span>江苏星网软件有限公司</span></a>&nbsp;All Rights Reserved.</span>
			</div>
		</div>
	</div>
	<!-- 修改个人密码 -->
	<div style="display: none" id="changepasswordform">
		<form class="form-horizontal" id="changepwd">
			<div class="form-group">
			    <label class="col-sm-2 control-label"><font class="red">*</font>原密码</label>
			    <div class="col-sm-10">
			      <input type="password" class="form-control" name="password" autocomplete="off">
			    </div>
		    </div>
		  	<div class="form-group">
			    <label class="col-sm-2 control-label"><font class="red">*</font>新密码</label>
			    <div class="col-sm-10">
			      <input type="password" class="form-control" name="newpassword" id="newpassword" autocomplete="off">
			    </div>
		    </div>
		    <div class="form-group">
			    <label class="col-sm-2 control-label"><font class="red">*</font>确认密码</label>
			    <div class="col-sm-10">
			      <input type="password" class="form-control" name="confirmpassword" autocomplete="off">
			    </div>
		    </div>
		</form>
	</div>
	<script type="text/javascript">
	  	//页面底部年份自动获取当前年份
		window.onload = function(){
			var initializationTime=(new Date()).getTime();
		  	document.getElementById("currentDate").innerHTML=(new Date()).getFullYear();
		}
   	</script>
</body>
</html>
