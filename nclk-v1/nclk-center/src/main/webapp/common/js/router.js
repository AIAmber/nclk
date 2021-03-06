define(['jquery','Router'],function($,Router){
	//定义数据对象 记录用到的数据
	DATA={};
	return {
		inint:function(){
			//创建用户发布正确路由时执行的一些功能。
			//首页
			var home = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页</span>");
				$('#rightContent').load("dashboard/dashboard.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//运行监控-->实时状态监控
			var statusmonitor = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;运行监控&nbsp;/&nbsp;实时状态监控</span>");
				$('#rightContent').load("monitor/statusmonitor.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//运行监控-->异常信息查询
			var errorinfo = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;运行监控&nbsp;/&nbsp;异常信息查询</span>");
				$('#rightContent').load("monitor/errorinfo.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//设备配置-->授时服务器配置
			var timeserverconfig = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;设备配置&nbsp;/&nbsp;授时服务器配置</span>");
				$('#rightContent').load("devconfig/timeserverconfig.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//设备配置-->网络时钟终端配置
			var clockterminalconfig = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;设备配置&nbsp;/&nbsp;网络时钟终端配置</span>");
				$('#rightContent').load("devconfig/clockterminalconfig.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//网络时钟终端配置（已选定）
			var selectedclockconfig = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;设备配置&nbsp;/&nbsp;网络时钟终端配置&nbsp;/&nbsp;已选定的网络时钟配置</span>");
				$('#rightContent').load("devconfig/selectedclockconfig.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//基础设置-->区域管理
			var area = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;基础设置&nbsp;/&nbsp;区域管理</span>");
				$('#rightContent').load("manage/area.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//基础设置-->考点管理
			var exampointer = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;基础设置&nbsp;/&nbsp;考点管理</span>");
				$('#rightContent').load("manage/exampointer.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			//基础设置-->用户管理
			var user = function() {
				$("#headnavigation").empty();
				$("#headnavigation").append("<span>首页&nbsp;/&nbsp;基础设置&nbsp;/&nbsp;用户管理</span>");
				$('#rightContent').load("manage/user.jsp",null,function(response,status){
					if (status=="success"){
			            document.getElementById('rightContent').innerHTML=response;
			        }
				});
			};
			
			var allroutes = function() {
				var route = window.location.hash.slice(2);
			};
			// 定义路由表。
			var routes = {
				'/home': home,
				'/statusmonitor':statusmonitor,
				'/errorinfo':errorinfo,
				'/timeserverconfig':timeserverconfig,
				'/clockterminalconfig':clockterminalconfig,
				'/selectedclockconfig':selectedclockconfig,
				'/area':area,
				'/exampointer':exampointer,
				'/user':user
			};
			// 实例化路由器
			var router = Router(routes);
			// 全局配置设置.
			router.configure({
				//on:当路由匹配成功后，需要执行的方法
				on: allroutes,
				//before:在触发"on"方法之前执行的方法
				before:function(){
					
				},
				//after:当离开当前注册路径时，需要执行的方法
				after:function(){
					$("#rightContent").children().empty();
					//清除$(window)绑定的相关事件
					$(window).unbind();
					//清空首页定时器，
					window.clearInterval(DATA.timeId);
				}
			});
			router.init();
		}
	}
});