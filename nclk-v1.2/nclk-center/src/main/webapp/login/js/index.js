$(function() {
	//判断当前窗口是否顶层窗口
	if(window.top!=window.self){
		window.top.location=Util.getPath()+'/login/index.jsp';
	}
	
	//触发记住密码选中事件
	$(".piaochecked").on("click",function(){
     	$(this).toggleClass("on_check");
     	if($(this).hasClass("on_check")){
     		document.getElementById("r").value='true';
     	}else{
     		document.getElementById("r").value='false';
     	}
	});
	
	//设定窗口缩放事件
	$(window, document).resize(function() {
		resize();
	}).load(function() {
		resize();
	});
	function resize() {
		var clientHeight = document.documentElement.clientHeight;
		var averHeight = (clientHeight - 669) / 2;
		if(averHeight<0){
			$("html").css("overflow","auto");
			$("body").css("padding-top", 0);
		}else{
			$("body").css("padding-top", averHeight);
		}
	}
	
	setInputValue();
	$('.box-input input').placeholder().css("color","#bdbfc4");
});

var setInputValue=function(){
	var username=document.getElementById("u");
	var password=document.getElementById("p");
	var remember=document.getElementById("r");
	//Cookie取值
	var usernameCookie=Util.nullToEmpty(Util.getCookie('u'));
	var passwordCookie=Util.nullToEmpty(Util.getCookie('p'));
	var rememberCookie=Util.nullToEmpty(Util.getCookie('r'));
	if(usernameCookie.trim()!=''){
		username.value=usernameCookie;
	}
	if(passwordCookie.trim()!=''){
		password.value=passwordCookie;
	}else{
		//在IE下，密码框没有值的时候，需要执行一遍placeholder方法
		//在IE下，密码框有值的时候，不能执行placeholder方法
		//非IE浏览器本身支持placeholder属性，运行这个无所谓
		$('.box-input input').placeholder();
	}
	if(rememberCookie.trim()=='true'){
		remember.value=true;
		$(".piaochecked").addClass("on_check");
	}else{
		remember.value=false;
		$(".piaochecked").removeClass("on_check");
	}
};

function refreshCaptcha(){
	document.getElementById("captcha").src=Util.getPath()+"/LoginController/getCaptcha?_dc="+new Date().getTime();
}

function login(){
	var username=document.getElementById("u");
	var password=document.getElementById("p");
	var remember=document.getElementById("r");
	var errorinfo=document.getElementById("errorinfo");
	if(username==null||Util.trim(username.value)==''||username.value=='请输入用户名'){
		errorinfo.innerHTML='<img src="'+Util.getPath()+'/login/images/error.png"/> 请输入用户名';
		return false;
	}
	if(password==null||Util.trim(password.value)==''||password.value=='请输入密码'){
		errorinfo.innerHTML='<img src="'+Util.getPath()+'/login/images/error.png"/> 请输入密码';
		return false;
	}
	var captcha=document.getElementById("c");
	if(captcha!=undefined&&captcha!=null){
		if(captcha==null||Util.trim(captcha.value)==''||captcha.value=='请输入验证码'){
			errorinfo.innerHTML='<img src="'+Util.getPath()+'/login/images/error.png"/> 请输入验证码';
			return false;
		}
	}
	//Cookie赋值
	var cookieRetainDay=7;
	if(remember.value=='true'){
		Util.setCookie('u',username.value,cookieRetainDay);
		Util.setCookie('p',password.value,cookieRetainDay);
		Util.setCookie('r',remember.value,cookieRetainDay);
	}else{
		Util.setCookie('u','');
		Util.setCookie('p','');
		Util.setCookie('r',remember.value);
	}
	var button=document.getElementById("button");
	button.value="正在登录...";
	document.getElementById("form").submit();
}