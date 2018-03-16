define(['jquery','bootstrap','bootstrap-dialog'],function($,bootstrap,bootstrapDialog){
	 $(function() {
		/*左侧导航栏显示隐藏功能*/
		 $(".subNav").click(function() {
				if(this != $(".subNav.sublist-down")[0]){
					showOrHiden($(".subNav.sublist-down"));
				}
				showOrHiden($(this));
		})
		
		function showOrHiden($obj){
			if($obj.prototype == 0){
				return;
			}
			/*隐藏*/
			if($obj.parent(".sBox").find("ul").css("display")=="block") {
				$obj.find("span:first-child").removeClass("glyphicon-chevron-down");
				$obj.find("span:first-child").addClass("glyphicon-chevron-right");
				$obj.removeClass("sublist-down");
				$obj.addClass("sublist-up");
			}
			/*显示*/
			else {
				$obj.find("span:first-child").removeClass("glyphicon-chevron-right");
				$obj.find("span:first-child").addClass("glyphicon-chevron-down");
				$obj.removeClass("sublist-up");
				$obj.addClass("sublist-down");
			}
			// 修改数字控制速度， slideUp(500)控制卷起速度
			$obj.next(".navContent").slideToggle(300).siblings(".navContent").slideUp(300);
		}

		/*左侧导航栏缩进功能*/
		$(".left-main .sidebar-fold").click(function() {
			if($(this).parent().attr('class') == "left-main left-full") {
				$(this).parent().removeClass("left-full");
				$(this).parent().addClass("left-off");
	
				$(this).parent().parent().find(".right-product").removeClass("right-full");
				$(this).parent().parent().find(".right-product").addClass("right-off");
			} else {
				$(this).parent().removeClass("left-off");
				$(this).parent().addClass("left-full");
	
				$(this).parent().parent().find(".right-product").removeClass("right-off");
				$(this).parent().parent().find(".right-product").addClass("right-full");
			}
		})
	
		/*左侧鼠标移入提示功能*/
		$(".sBox ul li").mouseenter(function() {
			if($(this).find("span:last-child").css("display") == "none") {
				$(this).find("div").show();
			}
		}).mouseleave(function() {
			$(this).find("div").hide();
		})
	
	/*当浏览器大小变化时 ,窗口发生相应的变化 */
	$(window).resize(function() {
		$("#rightContent").height(document.documentElement.clientHeight - $(".navbar.navbar-static-top").height() - $(".breadcrumb").height() - $(".footer").height());
	});
	//
	$(document).ready(function() {
		$("#rightContent").height(document.documentElement.clientHeight - $(".navbar.navbar-static-top").height() - $(".breadcrumb").height() - $(".footer").height());
	});
	//左侧菜单点击高亮显示
	$(function(){
		$(".sBox ul li a").click(function(){
			$(".sBox ul li.active").removeClass();
			$(this).parent().addClass("active");
		})
	});
	
	$(function () { $("[data-toggle='tooltip']").tooltip(); });
	
	//注销按钮事件
	$("#loginout").click(function(){
		location.href= location.pathname.match(/\/(.+?)(?=\/)/g)[0] + "/LoginController/loginOut";
	});
	
	//修改密码
	var userAddForm=$("#changepasswordform");
	var changePasswordDialog=new bootstrapDialog({
		type:'type-default',
		title:'修改登录密码',
        message: $("#changepasswordform").children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        draggable:true,
        cssClass:'changePasswordDialog',
        buttons: [{
        	id:'savebtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
            	/*var validator = Util.validate("userAddForm");
     			if(!userAddForm.valid()){
     				userAddInfo.focusInvalid()
     				return;
     			}*/
     			dialogRef.close();
     			//$("#userAddForm")[0].reset();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' btn-default btn-sm',
            action: function(dialogRef){
            	changePasswordDialog.close();
            }
        }]
	});
	
	$("#changepassword").on("click",function(){
		changePasswordDialog.open();
	});
	
	//修改用户信息
	//修改密码
	var userAddForm=$("#changeuserinfo");
	var userinfoDialog=new bootstrapDialog({
		type:'type-default',
		title:'修改个人信息',
        message: $("#changeuserinfo").children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        draggable:true,
        cssClass:'changeuserinfo',
        buttons: [{
        	id:'savebtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
            	/*var validator = Util.validate("userAddForm");
     			if(!userAddForm.valid()){
     				userAddInfo.focusInvalid()
     				return;
     			}*/
     			dialogRef.close();
     			//$("#userAddForm")[0].reset();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' btn-default btn-sm',
            action: function(dialogRef){
            	userinfoDialog.close();
            }
        }]
	});
	
	$("#changeinfo").on("click",function(){
		userinfoDialog.open();
	});
	
});
});