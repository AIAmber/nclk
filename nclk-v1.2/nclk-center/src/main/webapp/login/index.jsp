<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ page import="cn.com.sparknet.nclk.config.Config"%>
<%@ page import="cn.com.sparknet.nclk.util.StringUtil"%>
<%
	String path = request.getContextPath();
	String systemCaptcha = Config.getInstance().getProperty("center.system.captcha");
	String errorinfo = StringUtil.nullToEmpty((String) session.getAttribute("errorinfo"));
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/login/images/favicon.ico" />
<!-- Util -->
<script type="text/javascript" src="<%=path%>/login/js/Util.js"></script>
<script type="text/javascript" src="<%=path%>/login/js/Base64.js"></script>
<!-- JQuery -->
<script type="text/javascript" src="<%=path%>/login/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/login/js/jquery.placeholder.min.js"></script>
<!-- Login -->
<link rel="stylesheet" type="text/css" href="<%=path%>/login/css/index.css" />
<script type="text/javascript" src="<%=path%>/login/js/index.js"></script>
<title>江苏省教育考试院网络时钟监控系统</title>
</head>
<body>
	<form id="form" method="post" action="<%=path%>/LoginController/login">
		<div class="head">
			<div class="head-logo">
				<p><img src="<%=path%>/login/images/logo.png"></p>
				<p><span>江苏省教育考试院网络时钟监控系统</span></p>
			</div>
		</div>
		<div class="spark-content">
			<div>
				<div class="login-img">
					<img src="<%=path%>/login/images/main.png" />
				</div>
				<div class="login-box">
					<div class="box-title">用户登录</div>
					<div class="box-input">
						<p>
							<input id="u" name="u" type="text" onkeydown="if(event.keyCode==13){login()}" placeholder="请输入用户名" />
							<img src="<%=path%>/login/images/user.png" />
						</p>
						<p>
							<input id="p" name="p" type="password" onkeydown="if(event.keyCode==13){login()}" placeholder="请输入密码" autocomplete="off"/>
							<img src="<%=path%>/login/images/lock.png" />
						</p>
						<%if(systemCaptcha.equals("true")){%>
						<p>
							<input id='c' name="c" type="text" onkeydown="if(event.keyCode==13){login()}" class="code_input" maxlength="5" placeholder="请输入验证码"/>
							<img src="<%=path%>/login/images/code.png" />
							<span><img id="captcha" src="<%=path%>/LoginController/getCaptcha" class="code_img" onclick="refreshCaptcha()" title="看不清，点击刷新" /></span>
						</p>
						<%}%>
					</div>
					<div class="box-tip">
						<div class="piaochecked">
							<input id='r' type="checkbox" onfocus="this.blur()"/>
						</div>
						<span>记住我</span><span class="show-tip" id="errorinfo"><%=errorinfo%></span>
					</div>
					<div class="box-button">
						<input id="button" type="button" onclick="login()" value="登&nbsp;&nbsp;录"/>
					</div>
				</div>
				<div class="box-yun">
					<img src="<%=path%>/login/images/yun.png" />
				</div>
			</div>
		</div>
		<div class="spark-foot">
			<p>技术支持：江苏星网软件有限公司</p>
			<p>建议使用1280*800以上分辨率进行浏览</p>
			<p>浏览器兼容：IE8+、Firefox、Google Chrome</p>
		</div>
	</form>
</body>
</html>