define(['jquery','Util','bootstrap','bootstrap-dialog','ajaxfileupload'],function($,Util,bootstrap,bootstrapDialog,ajaxfileupload){
	
	/**
	 * ajaxSetup 可以设置未来(全局)的AJAX请求默认选项
	 * 主要设置了AJAX请求遇到Session过期的情况
	 */
     $.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
	    type: 'POST',
	    complete: function(xhr,status) {
	    	if(xhr.getResponseHeader instanceof Function && undefined != xhr.getResponseHeader('Session-Status')){
	    		var sessionStatus = xhr.getResponseHeader('Session-Status');
		        if(sessionStatus == 'timeout') {
		            var top = getTopWindow();
		            //session失效时，清除所有定时器
		            $.each(DATA.intervals, function() {
                        //清除定时器
                        window.clearInterval(this);
                    });
                    window.clearInterval(DATA.lineErrIntervals);
                    window.clearInterval(DATA.lineInterval);
                    window.clearInterval(DATA.timeId);
                    
                    Util.warningDialog("由于长时间未操作，会话已过期，请重新登陆！5秒后将自动跳转到登录页！");
                    setTimeout(function(){
                    	location.href = Util.getPath() + "/login/index.jsp";
                    }, 5000);
		        }
	    	}
	    }
	});
	
	/**
	 * 在页面中任何嵌套层次的窗口中获取顶层窗口
	 * @return 当前页面的顶层窗口对象
	 */
	function getTopWindow(){
	    var p = window;
	    while(p != p.parent){
	        p = p.parent;
	    }
	    return p;
	}
	
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
	
	//
	$(document).ready(function() {
		//$(".navbar.navbar-static-top").height() 52px;
		//$(".breadcrumb").height() 14px;
		//$(".footer").height() 30px;
		$("#rightContent").height(document.documentElement.clientHeight - 96);
	});
		
	/*当浏览器大小变化时 ,窗口发生相应的变化 */
	$(window).resize(function() {
		$("#rightContent").height(document.documentElement.clientHeight - 96);
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
		Util.questionDialog('您确定需要注销系统？',function(dialog, result){
			if(result){
				location.href= location.pathname.match(/\/(.+?)(?=\/)/g)[0] + "/LoginController/loginOut";
			}
			dialog.close();
		});
	});
	
	//修改用户密码表单校验代码
	var changepasswordformValidate = Util.validate("changepwd",{
		rules :{
			password:{
				required:true,
				rangelength:[1,50]
			},
			newpassword: {
				required:true,
				rangelength:[6,50]
			},
			confirmpassword: {
				required:true,
				rangelength:[6,50],
				equalTo: "#newpassword"
			}
		},
		messages: {
			password:{
				required:"原密码不能为空",
				rangelength : "原密码长度不能超过50个字符"
			},
			newpassword:{
				required:"新密码不能为空",
				rangelength:"新密码长度为6-50个字符"
			},
			confirmpassword: {
				required:"确认密码不能为空",
				rangelength:"确认密码长度为6-50个字符",
				equalTo:"确认密码与新密码不一致"
			}
		}
	});
	
	//修改密码
	var changePasswordDialog=new bootstrapDialog({
		type:'type-default',
		title:'修改登录密码',
        message: $("#changepasswordform").children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        draggable:true,
        cssClass:'changePasswordDialog',
        buttons: [{
        	id:'savebtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
            	var flag = $("#changepwd").valid();
        		if(flag==false){
        			changepasswordformValidate.focusInvalid();
        		}else{
        			$.ajax({
        			    method: 'post',
        			    url: Util.getPath()+'/UserController/updatePassword',
        			    dataType: 'text',
        			    // 用户名和密码
        			    data: {
        			    	password: $("[name='password']").val(),
        			    	newPassword: $("[name='newpassword']").val(),
        			    	userId : $("[name='userid']").val()
        			    },
        			    success: function(data){
        			        //假设后端传回的 1 表示成功， 0 表示用户名或密码错误
        			        if (JSON.parse(data).success) {
        			        	dialogRef.close();
        			        	Util.promptSuccessDialog(JSON.parse(data).bean);
        			        	$("#changepwd")[0].reset();
        			        	changepasswordformValidate.resetForm();
        			        } else {
        			        	Util.warningDialog(JSON.parse(data).bean);
        			        	$("#changepwd")[0].reset();
        			        	changepasswordformValidate.resetForm();
        			        }
        			    }
        			});
        		}
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' btn-default btn-sm',
            action: function(dialogRef){
            	$("#changepwd")[0].reset();
            	changepasswordformValidate.resetForm();
            	changePasswordDialog.close();
            }
        }]
	});
	
	$("#changepassword").click(function(){
		changePasswordDialog.open();
	});
	
	//删除用户头像
	$("#updelete").click(function(){
		Util.questionDialog('您确定删除用户头像？',function(dialog, result){
			if(result){
				$("#photoCircle").attr("src",Util.getPath()+'/common/img/protrait.png');
				$("#userImagephoto").attr("src",Util.getPath()+'/common/img/protrait.png');
			   	$("[name='filePath']").val("");
			   	$("[name='REPORT']").val("");
			   	changeUserPhoto("删除用户头像");
			}
			dialog.close();
		});
	 });
	
	$("[name='REPORT']").change(function(){
		$.ajaxFileUpload({
 		   url: Util.getPath() + "/FileController/uploadFile",//用于文件上传的服务器端请求地址
 		   type: 'post',
 		   data: { ProdcutID: 1 },  
 		   secureuri: false,         //是否需要安全协议，一般设置为false
 		   fileElementId: ['REPORT'],//文件上传域的ID 在这里设置要上传的多个input的ID
 		   dataType: 'json',         //返回值类型 一般设置为json
 		   success: function (data){ 
 			   //
 		   },
 		   error: function (data, status, e){
 			   if(undefined != data.responseText && undefined != JSON.parse(data.responseText).bean[0]){
 				  $("[name='filePath']").val(encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 	 			   $("#photoCircle").attr("src",Util.getPath()+'/FileController/showPic?filePath='+ encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 	 			   $("#userImagephoto").attr("src",Util.getPath()+'/FileController/showPic?filePath='+ encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 	 			   changeUserPhoto("修改用户头像");
 			   }
 		   }
 		});
	});
	//修改用户头像方法
	function changeUserPhoto(info){
		$.ajax({
       	   url: Util.getPath() + "/UserController/saveUserPhoto",
       	   type: 'POST',
       	   async : false,
       	   dataType: 'json',
       	   data: {
       		   filePath : $("[name='filePath']").val(),
       	   },
       	   success: function (data) {
       		   if(data.success){
       			   Util.promptSuccessDialog(info+"成功！");
       		   }
       	   },
       	   error:function(data){
       		   Util.promptSuccessDialog(info+"失败！");
       	   }
		});
	}
	
});
});