require(['jquery','Util','bootstrap-dialog','ajaxfileupload','jquery-placeholder'], function($,Util,BootstrapDialog,ajaxfileupload,placeholder) {	
	
	//添加ie8下placeholder的支持
	$(function(){
		if($("#btn_normal_clock_ip").val() == ""){
			$("#btn_normal_clock_ip").placeholder();
		}
		if($("#btn_disable_clock_ip").val() == ""){
			$("#btn_disable_clock_ip").placeholder();
		}
		$("#btn_normal_clock_ip").placeholder();
		if($("#filename").val() == ""){
			$("#filename").placeholder();
		}
	});
	
	//已启用时钟列表 全选事件
	$(".normal_status").on("click","#normalselectAll",function(){
		if($("#normal_time").find("li").hasClass("check")){
			$("#normal_time").find("li").removeClass("check");
			$("#normal_time").find(".controls_check").removeClass("on");
			$("[name='normal_clock']").prop("checked",false);
		}else{
			$("#normal_time").find("li").addClass("check");
			$("#normal_time").find(".controls_check").addClass("on");
			$("[name='normal_clock']").prop("checked",true);
		}
	});
	//已停用时钟列表 全选事件
	$(".disable_status").on("click","#disableselectAll",function(){
		if($("#error_time").find("li").hasClass("check")){
			$("#error_time").find("li").removeClass("check");
			$("#error_time").find(".controls_check").removeClass("on");
			$("[name='disable_clock']").prop("checked",false);
		}else{
			$("#error_time").find("li").addClass("check");
			$("#error_time").find(".controls_check").addClass("on");
			$("[name='disable_clock']").prop("checked",true);
		}
	});
	
	$("#normal_time,#error_time").on("click",".controls_check",function(){
	    /*$(this).hasClass("on")? $(this).removeClass("on"):$(this).addClass("on");*/
	   //或者这么写
	/*   $(this).toggleClass( "on" );*/
	   if($(this).hasClass("on")){
		   $(this).removeClass("on");
		   $(this).children("input").attr("checked",false);
		   $(this).parents("li").removeClass("check");
	   }else{
		   $(this).addClass("on");
		   $(this).children("input").attr("checked",true)
		   $(this).parents("li").addClass("check");
	   }
	})
	/*$(".error_statusul li").hover(function(){
		$(this).addClass("hover");
	},function(){
		$(this).removeClass("hover");
	})*/
	
	//省市区三级联动
	$(document).ready(function(){
		$.ajax({
     	   url: Util.getPath()+"/AreaController/getProvince",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {},
     	   success: function (data) {
     		  var html = "";
     		  for (var i = 0; i < data.length; i++) {
     			 if(i == 0){
     				html += "<option selected='selected' value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>";
     				continue;
     			 }
     			 html += "<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>";
      		  }
     		  $("#province").append(html);
     		  getCity($("#province").val());
     	   }
     	});
	});
	
	//给省份的选择框添加事件
	$("#province").change(function(){
	   //获取选择的省份id
	   var parentAreaId = $(this).val();
	   getCity(parentAreaId);
	});
	
	function getCity(parentAreaId){
		$.ajax({
     	   url: Util.getPath()+"/AreaController/getCity",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {
     		  parentAreaId:parentAreaId
     	   },
     	   success: function (data) {
     		   $("#city").empty();
     		   var html = "<option value='' selected>请选择市</option>";
     		   for (var i = 0; i < data.length; i++) {
     			  html += "<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>";
     		   }
     		   $("#city").append(html);
     		   //添加完成之后触发选择事件
	  		   $("#city").change();
     	   },
     	   error:function(){
     		   
     	   }
     	});
	}
	
	//给地级市的选择框添加事件
	$("#city").change(function(){
		//获取选择的地级市id
		var parentAreaId = $("#city").val();
		$.ajax({
     	   url: Util.getPath()+"/AreaController/getDistrict",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {
     		  parentAreaId:parentAreaId
     	   },
     	   success: function (data) {
     		   $("#district").empty();
     		   var html = "<option value='' selected>请选择县区</option>";
     		   if(null != data && data != undefined && data != ""){
     			  var i = 0;
     			  for (i = 0; i < data.length ; i++) {
          			 html += "<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>";
          		  }
     		   }
     		  $("#district").append(html);
     		  //添加完成之后触发选择事件
	  		  $("#district").change();
     	   }
     	});
	});
	
	//给县区选择框添加事件
	$("#district").change(function(){
		//获取选择的区县id
		var districtId = $("#district").val();
		$.ajax({
     	   url: Util.getPath()+"/EndPointController/getEndPointByDistrictId",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {
     		  districtId:districtId
     	   },
     	   success: function (data) {
     		   $("#endpoint").empty();
     		   var html = "<option value='' selected>请选择考点</option>";
     		   for (var i = 0; i < data.length; i++) {
     			  html += "<option value='"+data[i].ENDPOINT_ID+"'>"+data[i].ENDPOINT_NAME+"</option>";
     		   }
     		   $("#endpoint").append(html);
     		 //添加完成之后触发选择事件
	  		   $("#endpoint").change();
     	   }
     	});
	});
	
	//给考点选择框添加事件
	$("#endpoint").change(function(){
		//获取选择的考点id
		var endPointId = $(this).val();
		$.ajax({
     	   url: Util.getPath()+"/ClockTerminalConfigController/findEndPointClockState",
     	   type: 'post',
     	   dataType: 'json',
     	   async : false,
     	   data: {
     		  endPointId : endPointId
     	   },
     	   success: function (data) {
     		   $("#normal_time").empty();
     		   $("#error_time").empty();
     		   //正常时钟数量
     		   var normalCount = data.normalCount.NORMALCOUNT;
     		   //异常时钟数量
     		   var disableCount = data.disableCount.NORMALCOUNT;
     		   $("#normalCount").html(normalCount);
     		   $("#disableCount").html(disableCount);
     		   $("#totalCount").html(normalCount+disableCount);
     		   //循环展示 监听状态时钟
     		   for (var i = 0; i < normalCount; i++) {
     			 $("#normal_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-normal.png' />" +
     			 	"<span>"+data.normalClock[i].EXAMROOM_IP+"</span>" +
     			 	"<div class='item_foot'>" +
     			 	"<span class='controls_check'>" +
     			 	"<input type=\"checkbox\" name='normal_clock'></span>" +
     			 	"<a href='javascript:void(0)'>" +
     			 	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
     		   } 
     		   
     		  //循环展示 停用状态时钟
     		   for (var j = 0; j < disableCount; j++) {
     			  $("#error_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-disable.png' />" +
     			  	"<span>"+data.disableClock[j].EXAMROOM_IP+"</span>" +
     			  	"<div class='item_foot'><span class='controls_check'>" +
     			  	"<input type=\"checkbox\" name='disable_clock'></span><a href='javascript:void(0)'>" +
     			  	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
     		   } 
     	   }
     	});
	});
	
	//点击子钟图片下面的编辑图片 编辑考场子钟信息编辑事件
	$(".panel-body.error_statusul #normal_time,.panel-body.error_statusul #error_time").on("click","a",function(){
		//监听状态时钟 编辑事件
		$.ajax({
	   	   url: Util.getPath() + "/ClockTerminalConfigController/findExamRoomInfo",
	   	   type: 'POST',
	   	   data: {
	   		   endPointId : $("#endpoint").val(),
	   		   examRoomIP : $(this).parents("li").find("span:first").html() //获取时钟IP值
	   	   },
	   	   success: function (data) {
	       		endPointclocKEditDialog.onShown(function(){
	       			$("#examRoomIp").attr("readonly",true);
		         		$("#examRoomIp").val(data.EXAMROOM_IP);
		      		    $("#examRoomName").val(data.EXAMROOM_NAME);
		      		    $("#examRoomAddr").val(data.EXAMROOM_ADDR);
		      		    $("#examRoomPerson").val(data.EXAMROOM_PERSON);
		      		    $("#examRoomTel").val(data.EXAMROOM_TEL);
	       		});
	   		    endPointclocKEditDialog.setTitle("考场子钟信息编辑");
			    endPointclocKEditDialog.open();
	   	   },
	   	   error:function(data){
	   		   Util.promptSuccessDialog("编辑考场子钟信息出错！");
	   	   }
		});
	});
	
	//禁用考场子钟
	$("#disableClock").on("click",function(){
		if($("[name='normal_clock']:checked").length == 0){
			Util.warningDialog("请选择需要停用的时钟！");
			return;
		}
		
		Util.questionDialog('请选择是否停用时钟？',function(dialog, result){
		if(result){
			dialog.close();
		    //停用考场子钟
			$("[name='normal_clock']:checked").each(function(){
				  var clock = $(this).parents("li");
				  var examRoomIP = $(this).parents("li").find("span:first").html();
				  var endPointId = $("#endpoint").val();
				  $.ajax({
		       	   url: Util.getPath() + "/ClockTerminalConfigController/examRoomNormalOrDisable",
		       	   type: 'POST',
		       	   data: {
		       		   endPointId : $("#endpoint").val(),
		       		   examRoomIP : examRoomIP,
		       		   state : "disable"
		       	   },
		       	   success: function (data) {
		       		  //禁用考场子钟成功
          				clock.remove();
          				$("#error_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-disable.png' />" +
          	     			  	"<span>"+examRoomIP+"</span>" +
          	     			  	"<div class='item_foot'><span class='controls_check'>" +
          	     			  	"<input type=\"checkbox\" name='disable_clock'></span><a href='javascript:void(0)'>" +
          	     			  	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
          				
          				if(NaN != parseInt($("#disableCount")) && NaN != parseInt($("#normalCount"))){
          					$("#disableCount").text(parseInt($("#disableCount").html())+1);
          					$("#normalCount").text(parseInt($("#normalCount").html())-1);
          				}
		       	   },
		       	   error:function(data){
		       		   Util.promptSuccessDialog("");
		       	   }
		       	});	
			});
			Util.promptSuccessDialog("停用考场子钟成功！");
		}else{
			//关闭选择框
			dialog.close();
		}
		});
	});
	
	 //启用考场子钟点击事件
	 $("#normalClock").on("click",function(){
		 if($("[name='disable_clock']:checked").length == 0){
				Util.warningDialog("请选择需要启用的时钟！");
				return ;
		 }

		 $("[name='disable_clock']:checked").each(function(){
			  var clock = $(this).parents("li");
			  var examRoomIP = $(this).parents("li").find("span:first").html();
			  var endPointId = $("#endpoint").val();
			  $.ajax({
		       	   url: Util.getPath() + "/ClockTerminalConfigController/examRoomNormalOrDisable",
		       	   type: 'POST',
		       	   data: {
		       		   endPointId : $("#endpoint").val(),
		       		   examRoomIP : examRoomIP,
		       		   state : "normal"
		       	   },
		       	   success: function (data) {
		       		 //启用考场子钟成功
		       		 clock.remove();
		       		 $("#normal_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-normal.png' />" +
		     			 	"<span>"+examRoomIP+"</span>" +
		     			 	"<div class='item_foot'>" +
		     			 	"<span class='controls_check'>" +
		     			 	"<input type=\"checkbox\" name='normal_clock'></span>" +
		     			 	"<a href='javascript:void(0)'>" +
		     			 	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
		       		 
		       		  if(NaN != parseInt($("#disableCount")) && NaN != parseInt($("#normalCount"))){
							 $("#disableCount").text(parseInt($("#disableCount").html())-1);
							 $("#normalCount").text(parseInt($("#normalCount").html())+1);
					  }
		       	   },
		       	   error:function(data){
		       		   Util.promptSuccessDialog("");
		       	   }
	       	});
		 });
		 Util.promptSuccessDialog("启用考场子钟成功！");
	 });
	
	 //获得焦点 显示搜索框X号
	 $("#btn_normal_clock_ip").focus(function(){
		 $("#normal_delete").show();
	 });
	 
	 $("#btn_disable_clock_ip").focus(function(){
		 $("#disable_delete").show();
	 });
	 
	 //失去焦点 输入框没有值 不显示搜索框X号
	 $("#btn_normal_clock_ip").blur(function(){
		 if($("#btn_normal_clock_ip").val() == ""){
			 $("#normal_delete").hide();
		 }
	 });
	 
	 $("#btn_disable_clock_ip").blur(function(){
		 if($("#btn_disable_clock_ip").val() == ""){
			 $("#disable_delete").hide();
		 }
	 });
	 
	//已启用时钟列表 搜索框 x号 点击事件
	$("#normal_delete").on("click",function(){
		if($("#btn_normal_clock_ip").val() == ""){
			$("#btn_normal_clock_ip").placeholder();
		}
		$("#normal_time_result").empty();
		$("#btn_normal_clock_ip").val("");
		$("#normal_delete").hide();
		$("#normal_time li").each(function(){
			$(this).show();
		});
	});
	
	//已停用时钟列表 搜索框 x号
	$("#disable_delete").on("click",function(){
		if($("#btn_disable_clock_ip").val() == ""){
			$("#btn_disable_clock_ip").placeholder();
		}
		$("#error_time_result").empty();
		$("#btn_disable_clock_ip").val("");
		$("#disable_delete").hide();
		$("#error_time li").each(function(){
			$(this).show();
		});
	});
	
	 //已启用时钟列表 搜索按钮事件
	 $("#btn_normal_clock").on("click",function(){
		 if($("#province").val() == ""){
				Util.warningDialog("请选择具体考点！");
				return;
			}else if($("#city").val() == ""){
				Util.warningDialog("请选择具体考点！");
				return;
			}else if($("#district").val() == ""){
				Util.warningDialog("请选择具体考点！");
				return;
			}else if($("#endpoint").val() == ""){
				Util.warningDialog("请选择具体考点！");
				return;
			}else if($("#btn_normal_clock_ip").val() == ""){
				 Util.warningDialog("请输入时钟IP地址进行搜索！");
				 return ;
			}else{
				var clock_IP = $("#btn_normal_clock_ip").val().trim();
				 $("#normal_time li").each(function(){
					//根据ip地址
					var clockIP = $(this).find("span:first").html();
					if(""!= clock_IP){
						if(clockIP.indexOf(clock_IP) != -1){
							$(this).show();
						}else{
							$(this).hide();
						}
					}
				});
				
				var count = 0;
				$("#normal_time").find("li").each(function(){
					if($(this).css("display")!="none"){
						count++;
					}
				});
				if(count == 0){
					$("#normal_time_result").append("未搜索到已启用的时钟！");
				} 
			}
	 });
	 
	//已停用时钟列表 搜索按钮事件
	 $("#btn_disable_clock").on("click",function(){
		 var ipVerification = /^(?:(?:1[0-9][0-9]\.)|(?:2[0-4][0-9]\.)|(?:25[0-5]\.)|(?:[1-9][0-9]\.)|(?:[0-9]\.)){3}(?:(?:1[0-9][0-9])|(?:2[0-4][0-9])|(?:25[0-5])|(?:[1-9][0-9])|(?:[0-9]))$/;
		 if($("#province").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		 }else if($("#city").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		 }else if($("#district").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		 }else if($("#endpoint").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		 }else if($("#btn_disable_clock_ip").val() == ""){
			 Util.warningDialog("请输入时钟IP地址进行搜索！");
			 return ;
		 }else{
			 var clock_IP = $("#btn_disable_clock_ip").val().trim();
			 $("#error_time li").each(function(){
				//根据ip地址
				var clockIP = $(this).find("span:first").html();
				if(""!= clock_IP){
					if(clockIP.indexOf(clock_IP) != -1){
						$(this).show();
					}else{
						$(this).hide();
					}
				}
			});
			 
			var count = 0;
			$("#error_time").find("li").each(function(){
				if($(this).css("display")!="none"){
					count++;
				}
			});
			
			if(count == 0){
				$("#error_time_result").append("未搜索到已停用的时钟！");
			} 
			 
		}
	 });
	 
	//考场子钟信息初始化弹出框
	var endPointImportDialog = new BootstrapDialog({
		type:'type-default',
		title:'初始化考场子钟信息',
        message: $('#endpointimport').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        cssClass:'endpointimport-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        buttons: [{
        	id:"savebtn",
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	//未选择文件 直接点击上传按钮
            	if($("#file").val()==""){
            		Util.warningDialog("请选择需要上传的文件！");
            		return ;
            	}
            	$.ajaxFileUpload({
        		   url: Util.getPath() + "/ClockTerminalConfigController/uploadTemplet",//用于文件上传的服务器端请求地址
        		   type: 'post',
        		   data: { ProdcutID: 1 },  
        		   secureuri: false,       //是否需要安全协议，一般设置为false
        		   fileElementId: ['file'],//文件上传域的ID 在这里设置要上传的多个input的ID
        		   dataType: 'json',       //返回值类型 一般设置为json
        		   success: function (data){ 
        			   $("#uploadendpointtemplet")[0].reset();
        			   if (data.success) {
           			   		endPointImportDialog.close();
  			        		Util.promptSuccessDialog(data.bean);
  			        	} else {
  			        		endPointImportDialog.close();
  			        		Util.warningDialog(data.bean);
  			        	}
        		   },
        		   error: function (data, status, e){//服务器响应失败处理函数
        			   var text = JSON.parse(data.responseText).bean;
        			   if(undefined != text && ""!= text){
        				   Util.warningDialog(text);
        			   }
        			   $("#uploadendpointtemplet")[0].reset();
        			   endPointImportDialog.close();
        		   }
        		});
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	$("#uploadendpointtemplet")[0].reset();
            	$("#file").val("");
            	endPointImportDialog.close();
            }
        }]
    });
	
	//手动导入 按钮事件
	$("#btn-import").on("click",function(){
		endPointImportDialog.open();
	});
	
    //IP校验
    $.validator.addMethod("clockIP", function(value, element) {
        var ip= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
        return this.optional(element) || ip.test(value);
    }, "请填写正确的IP地址");
	
	//手动添加考点信息弹出框表单校验
	var endpointaddform = Util.validate("endpointaddform",{
		rules :{
			examRoomIp:{
				required:true,
				clockIP:true,
				rangelength:[0,20]
			},
			examRoomName:{
				rangelength:[0,25]
			},
			examRoomAddr:{
				rangelength:[0,50]
			},
			examRoomPerson:{
				rangelength:[0,25]
			},
			examRoomTel:{
				rangelength:[0,25]
			}
		},
		messages: {
			examRoomIp:{
				required:"考场子钟IP不能为空",
				rangelength:"考场子钟IP长度不能超过20字符"
			},
			examRoomName:{
				rangelength:"考场名称长度不能超过25字符"
			},
			examRoomAddr:{
				rangelength:"考场地址长度不能超过50字符"
			},
			examRoomPerson:{
				rangelength:"考场负责人长度不能超过25字符"
			},
			examRoomTel:{
				rangelength:"考场负责人电话长度不能超过25字符"
			}
		}
	});
	
	//考场子钟信息编辑弹出框表单校验
	var endpointinfoeditform = Util.validate("examroominfoeditform",{
		rules :{
			exam_room_ip:{
				required:true,
				clockIP:true
			},
			exam_room_name:{
				rangelength:[0,25]
			},
			exam_room_addr:{
				rangelength:[0,50]
			},
			exam_room_person:{
				rangelength:[0,25]
			},
			exam_room_tel:{
				rangelength:[0,25]
			}
		},
		messages: {
			exam_room_ip:{
				required:"考场子钟IP不能为空",
			},
			exam_room_name:{
				rangelength:"考场名称长度不能超过25字符"
			},
			exam_room_addr:{
				rangelength:"考场地址长度不能超过50字符"
			},
			exam_room_person:{
				rangelength:"考场负责人长度不能超过25字符"
			},
			exam_room_tel:{
				rangelength:"考场负责人电话长度不能超过25字符"
			}
		}
	});
	
	//手动添加考点信息弹出框
	var endPointclockAddDialog = new BootstrapDialog({
		type:'type-default',
		title:'手动添加考场子钟',
        message: $('#endpointadd').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        cssClass:'endpointadd-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        buttons: [{
        	id:"savebtn",
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	var flag = $("#endpointaddform").valid();
        		if(flag==false){
        			endpointaddform.focusInvalid();
        		}else{
        			$.ajax({
        			    method: 'post',
        			    url: Util.getPath()+"/ClockTerminalConfigController/addExamRoomInfo",
        			    dataType: 'text',
        			    data: {
        			    	examRoomIp:$("[name='examRoomIp']").val(), 
                        	examRoomName:$("[name='examRoomName']").val(),
                        	examRoomAddr:$("[name='examRoomAddr']").val(),
                        	examRoomPerson:$("[name='examRoomPerson']").val(),
                        	examRoomTel:$("[name='examRoomTel']").val(),
                        	endpointId:$("#endpoint").val() //考点id
        			    },
        			    success: function(data){
        			        if (JSON.parse(data).success) {
        			        	endPointclockAddDialog.close();
        			        	$("#normal_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-normal.png' />" +
                	     			 	"<span>"+$("[name='examRoomIp']").val()+"</span>" +
                	     			 	"<div class='item_foot'>" +
                	     			 	"<span class='controls_check'>" +
                	     			 	"<input type=\"checkbox\" name='normal_clock'></span>" +
                	     			 	"<a href='javascript:void(0)'>" +
                	     			 	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
        			        	if(NaN != parseInt($("#normalCount").html()) && NaN != parseInt($("#normalCount").html())){
        			        		//正常时钟总数加1
        			        		$("#normalCount").text(parseInt($("#normalCount").html())+1);
            			        	//时钟总数加1
        			        		$("#totalCount").text(parseInt($("#totalCount").html())+1);
        			        	}
        			        	Util.promptSuccessDialog(JSON.parse(data).bean);
        			        	endpointaddform.resetForm();
        			        	$("#endpointaddform")[0].reset();
        			        } else {
        			        	if(undefined != JSON.parse(data).bean){
        			        		Util.warningDialog(JSON.parse(data).bean);
        			        	}else{
        			        		Util.warningDialog("添加考场子钟信息失败！");
        			        	}
//        			        	$("#endpointaddform")[0].reset();
//        			        	endpointaddform.resetForm();
//        			        	endPointclockAddDialog.close();
        			        }
        			    },
        			    error:function(data){
        			    	
        			    }
        			});
        		}
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	$("#endpointaddform")[0].reset();
            	endpointaddform.resetForm();
            	endPointclockAddDialog.close();
            }
        }]
    });
	
	//手动添加 按钮事件
	$("#btn-add").on("click",function(){
		if($("#province").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#city").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#district").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#endpoint").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else{
			endPointclockAddDialog.onShown(function(){
         		$("[name='examRoomIp']").val("");
      		    $("[name='examRoomName']").val("");
      		    $("[name='examRoomAddr']").val("");
      		    $("[name='examRoomPerson']").val("");
      		    $("[name='examRoomTel']").val("");
     		});
			endPointclockAddDialog.open();
		}
	});
	
	//自动检索 按钮事件
	$("#btn-search").on("click",function(){
		if($("#province").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#city").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#district").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else if($("#endpoint").val() == ""){
			Util.warningDialog("请选择具体考点！");
			return;
		}else{
			//打开显示自动扫描子钟信息弹出框
			endPointclockAutoSearchDialog.open();
		}
	});
	
	//文件选择
	var filename = $('#filename');
	$('#file').on('change', function(e){
		var name=this.value;
		if(name != null && name != ''){
	    	name = name.substring(name.lastIndexOf('\\') + 1); 
		}
	    filename.val(name);
	    if(name.split(".")[1].indexOf("xlsx") == -1){
	    	Util.warningDialog("请选择xlsx格式的文件进行上传！");
	    	$("#filename").val("");
	    	$("#filename").attr('placeholder',"请选择xlsx格式的文件进行上传！");
	    }
	});
	
	//自动搜索考点子钟弹出框
	var endPointclockAutoSearchDialog = new BootstrapDialog({
		title:'自动检索考场子钟',
		type:'type-default',
        message: $('#endPointAutoSearch').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        autodestroy:false,
        cssClass:'endpointautosearch-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        	$.ajax({
		    method: 'post',
		    url: Util.getPath() + "/ClockTerminalConfigController/autoSearchExamRoomInfo",
		    dataType: 'text',
		    data: {
		    	endPointId : $("#endpoint").val()
		    },
		    success: function(data){
		    	//检索不到考场子钟ip地址
		    	$("#autoSearchResultLoading").empty();
		    	if(JSON.parse(data).success == false){
		    		$("#autoSearchResultLoading").empty();
		    		$("#autoSearchResultLoading").append("<div style='height:50px;color:red;'>未检索到考场子钟，请<a href='javascript:void(0)'>手动添加</a>！</div>");
		    		$('#savebtn').attr("disabled",true);
		    		return ;
		    	}
		    	//
		    	if(JSON.parse(data).rows.length > 0){
		    		for (var i = 0; i < JSON.parse(data).rows.length; i++) {
			    		$("#addAutoSearchClock").append("<li class='form-group'>" +
			    				"<img src='"+Util.getPath()+"/devconfig/images/time-normal.png'>" +
			    						"<div><input type='checkbox' name='clockbox'><span>"+JSON.parse(data).rows[i].ip+"</span></div></li>");
					}
		    		endPointclockAutoSearchDialog.setTitle("共检索到考场子钟"+JSON.parse(data).total+"台");
		    		endPointclockAutoSearchDialog.open();
		    	}else{
		    		$('#savebtn').attr("disabled",true)
		    		$("#autoSearchResultLoading").empty();
		    		$("#autoSearchResultLoading").append("<div style='height:50px;color:red;'>未检索到考场子钟，请<a href='javascript:void(0)'>手动添加</a>！</div>");
		    	}
		    },
		    error:function(data){
		    	$('#savebtn').attr("disabled",true)
	    		$("#autoSearchResultLoading").empty();
	    		$("#autoSearchResultLoading").append("<div style='height:50px;color:red;'>未检索到考场子钟，请<a href='javascript:void(0)'>手动添加</a>！</div>");
		    }
		});
        },
        buttons: [{
        	id:"savebtn",
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	if($("[name='clockbox']:checked").length > 0){
            		$("[name='clockbox']:checked").each(function(){
                		var examRoomIp = $(this).parents("li").find("span").html();
                		$.ajax({
    					    method: 'post',
    					    url: Util.getPath()+"/ClockTerminalConfigController/findExamRoomIPEXISTS",
    					    dataType: 'text',
    					    data: {
    					    	endPointId : $("#endpoint").val(),
    					    	examRoomIP : examRoomIp
    					    },
    					    success: function(data){
    					    	if(JSON.parse(data).success){
    					    		//Util.promptSuccessDialog(JSON.parse(data).bean);
    					    		$("#normal_time").append("<li ><div class='item_border'><img src='"+Util.getPath()+"/devconfig/images/time-normal.png' />" +
    					     			 	"<span>"+examRoomIp+"</span>" +
    					     			 	"<div class='item_foot'>" +
    					     			 	"<span class='controls_check'>" +
    					     			 	"<input type=\"checkbox\" name='normal_clock'></span>" +
    					     			 	"<a href='javascript:void(0)'>" +
    					     			 	"<img src='"+Util.getPath()+"/devconfig/images/icon_edit.png' class='edit_img' /></a></div></div></li>");
    					    		$.ajax({
    			        			    method: 'post',
    			        			    url: Util.getPath()+"/ClockTerminalConfigController/addAutoSearchExamRoomInfo",
    			        			    dataType: 'text',
    			        			    data: {
    			        			    	examRoomIp : examRoomIp , 
    			                        	endpointId : $("#endpoint").val() //考点id
    			        			    },
    			        			    success: function(data){
    			        			        if (JSON.parse(data).success) {
    			        			        	endPointclockAddDialog.close();
    			        			        	Util.promptSuccessDialog(JSON.parse(data).bean);
    			        			        } else {
    			        			        	/*Util.warningDialog(JSON.parse(data).bean);*/
    			        			        }
    			        			    },
    			        			    error:function(data){
    			        			    	
    			        			    }
    			        			});
    					    	}else{
    					    		Util.warningDialog(JSON.parse(data).bean);
    					    		return ;
    					    	}
    					    },
    					    error:function(data){
    					    	
    					    }
    					});
            		});
                	endPointclockAutoSearchDialog.close();
                	$("#addAutoSearchClock").empty();
                	$("#endPointAutoSearchForm")[0].reset();
                	//重置表单
                	$("#terminalIP").prop("checked",true);
                	$("#terminalIPRange").prop("checked",false);
                	$("#terminalIPInput").show();
                	$("#terminalIPRangeStart").hide();
                	$("#terminalIPRangeEnd").hide();
        		}else{
        			Util.warningDialog("请至少勾选一个子钟！");
        		}
            	$("#autoSearchResultLoading").empty();
            	$("#autoSearchResultLoading").append("<img src='"+Util.getPath()+"/devconfig/images/loading.gif'>");
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	$('#savebtn').attr("disabled",false)
            	$("#endPointAutoSearchForm")[0].reset();
            	$("#terminalIP").prop("checked",true);
            	$("#terminalIPRange").prop("checked",false);
            	$("#terminalIPInput").show();
            	$("#terminalIPRangeStart").hide();
            	$("#terminalIPRangeEnd").hide();
            	$("#addAutoSearchClock").empty();
            	$("#autoSearchResultLoading").empty();
		    	$("#autoSearchResultLoading").append("<img src='"+Util.getPath()+"/devconfig/images/loading.gif'>");
		    	endPointclockAutoSearchDialog.close();
            }
        }]
    });
	
	//自动扫描子钟 全选按钮
	$("#endPointAutoSearch form ul").on("click","#selectAllClock",function(){
		if($("[name='clockbox']").is(":checked") == false){
			$("[name='clockbox']").prop("checked",true);
		}else{
			$("[name='clockbox']").prop("checked",false);
		}
	});
	
	//自动扫描子钟 弹出框 ip选择框和ip段选择框 切换事件
	$("#terminalIP").change(function(){
		$("#terminalIPInput").show();
		$("#terminalIPRangeStart").hide();
		$("#terminalIPRangeEnd").hide();
	});
	$("#terminalIPRange").change(function(){
		$("#terminalIPInput").hide();
		$("#terminalIPRangeStart").show();
		$("#terminalIPRangeEnd").show();
	});
	
	//自动搜索弹出框 搜索按钮事件
	$("#endPointAutoSearchForm ul li div a").on("click",function(){
		//终端IP地址
		if($('#terminalIP').is(':checked')){
			//获取IP地址输入框的值
			var terminalIP = $("#terminalIPInput").find("input").val();
			if(terminalIP != ""){
				$("#addAutoSearchClock li").each(function(){
					//根据ip地址
					var clockIP = $(this).find("span").html();
					if(clockIP != $("#terminalIPInput").find("input").val()){
						//移除无用的内容
						$(this).hide();
					}else if(clockIP == $("#terminalIPInput").find("input").val()){
						//移除无用的内容
						$(this).show();
					}
				});
			}else{
				//输入框中 不输入值 默认展示所有
				$("#addAutoSearchClock li").show();
			}
		}else if($('#terminalIPRange').is(':checked')){//终端IP地址段
			//获取IP地址段的值
			var terminalIPRangeStartValue = $("#terminalIPRangeStart").find("input").val();
			var terminalIPRangeEndValue= $("#terminalIPRangeEnd").find("input").val();
			var startValue = terminalIPRangeStartValue.split(".")[3];
			var endValue = terminalIPRangeEndValue.split(".")[3];
			//截取IP地址的最后一段
			if(startValue != undefined && endValue != undefined){
				$("#addAutoSearchClock li").each(function(){
					//根据ip地址
					var clockIP = $(this).find("span").html().split(".")[3];
					if(NaN != clockIP && (parseInt(clockIP)< parseInt(startValue) || parseInt(clockIP) > parseInt(endValue))){
						//隐藏无用的内容
						$(this).hide();
					}else{
						var clockIP = $(this).find("span").text().split(".")[3];
						if(NaN != clockIP && (parseInt(clockIP) > parseInt(startValue) && parseInt(clockIP) < parseInt(endValue))){
							$(this).show();
						}
					}
				});
			}else{
				//不输入任何值,默认展示搜索到的所有的子钟
				$("#addAutoSearchClock li").show();
			}
		}
	});
	
	//考场子钟信息编辑弹出框
	var endPointclocKEditDialog = new BootstrapDialog({
		type:'type-default',
        message: $('#examroominfoedit').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        cssClass:'endpointinfoedit-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        onshow : function(dialogRef){
        	
        },
        buttons: [{
        	id:"savebtn",
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	var examRoomIP = $("#examRoomIp").val();
            	var examRoomName = $("#examRoomName").val();
            	var examRoomAddr = $("#examRoomAddr").val();
            	var examRoomPerson = $("#examRoomPerson").val();
            	var examRoomTel = $("#examRoomTel").val();
            	var endpointId = $("#endpoint").val(); //考点id
            	var flag = $("#examroominfoeditform").valid();
        		if(flag==false){
        			endpointinfoeditform.focusInvalid();
        		}else{
        			$.ajax({
        			    method: 'post',
        			    url: Util.getPath()+"/ClockTerminalConfigController/examRoomInfoEdit",
        			    dataType: 'text',
        			    data: {
        			    	examRoomIP : examRoomIP, 
                        	examRoomName : examRoomName,
                        	examRoomAddr : examRoomAddr,
                        	examRoomPerson : examRoomPerson,
                        	examRoomTel : examRoomTel,
                        	endpointId : endpointId //考点id
        			    },
        			    success: function(data){
        			    	if(JSON.parse(data).success){
        			    		Util.promptSuccessDialog(JSON.parse(data).bean);
        			    	}else{
        			    		Util.promptSuccessDialog(JSON.parse(data).bean);
        			    	}
        			    },
        			    error:function(data){
        			    	Util.promptSuccessDialog("编辑考场子钟信息失败！");
        			    }
        			});
        			endpointinfoeditform.resetForm();
                	endPointclocKEditDialog.close();
                	$("#examroominfoeditform")[0].reset();
        		}
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	endpointinfoeditform.resetForm();
            	$("#examroominfoeditform")[0].reset();
            	endPointclocKEditDialog.close();
            }
        }]
    });
	
	//未检索到考场子钟 手动添加 点击事件
	$("#autoSearchResultLoading").on("click","a",function(){
		$("#autoSearchResultLoading").empty();
    	$("#autoSearchResultLoading").append("<img src='"+Util.getPath()+"/devconfig/images/loading.gif'>");
		endPointclockAutoSearchDialog.close();
		endPointclockAddDialog.open();
	});
	
	//已启用时钟列表删除按钮
	$(".normal_status").on("click","#normaldeleteAll",function(){
		var selectCount = $("[name='normal_clock']:checked").length;
		if(selectCount == 0){
			//请选择需要删除的时钟
			Util.warningDialog("请至少选择一个需要删除的时钟！");
			return ;
		}
		
		var endPointId = $("#endpoint").val();
		if(endPointId == ""){
			Util.warningDialog("请选择考点！");
			return ;
		}
		
		var ipStr = '';
		$("[name='normal_clock']:checked").each(function(){
			//获取需要删除的时钟IP
			var clockIP = $(this).parents("li").find("span:first").text();
			ipStr += clockIP + ",";
		});
		ipStr = ipStr.substr(0,ipStr.length-1); 
		
		$.ajax({
		    method: 'post',
		    url: Util.getPath()+"/ClockTerminalConfigController/deleteClock",
		    dataType: 'json',
		    data: {
		    	endPointId :endPointId,
		    	ipStr : ipStr
		    },
		    success: function(data){
		    	if(data.success){
		    		$("[name='normal_clock']:checked").parents("li").remove();
		    		Util.promptSuccessDialog(data.bean);
		    		$("#totalCount").text(parseInt($("#totalCount").html())-selectCount);
		    		$("#normalCount").text(parseInt($("#normalCount").html())-selectCount);
		    	}else{
		    		Util.promptSuccessDialog("删除考场子钟信息失败！");
		    	}
		    },
		    error:function(data){
		    	Util.promptSuccessDialog("删除考场子钟信息失败！");
		    }
		});
		
	});
	
	//已停用时钟列表删除按钮
	$(".disable_status").on("click","#disabledeleteAll",function(){
		var selectCount = $("[name='disable_clock']:checked").length;
		if(selectCount == 0){
			//请选择需要删除的时钟
			Util.warningDialog("请至少选择一个需要删除的时钟！");
			return ;
		}
		
		var endPointId = $("#endpoint").val();
		if(endPointId == ""){
			Util.warningDialog("请选择考点！");
			return ;
		}
		
		var ipStr = '';
		$("[name='disable_clock']:checked").each(function(){
			//获取需要删除的时钟IP
			var clockIP = $(this).parents("li").find("span:first").text();
			ipStr += clockIP + ",";
		});
		ipStr = ipStr.substr(0,ipStr.length-1); 
		
		$.ajax({
		    method: 'post',
		    url: Util.getPath()+"/ClockTerminalConfigController/deleteClock",
		    dataType: 'json',
		    data: {
		    	endPointId :endPointId,
		    	ipStr : ipStr
		    },
		    success: function(data){
		    	if(data.success){
		    		$("[name='disable_clock']:checked").parents("li").remove();
		    		Util.promptSuccessDialog(data.bean);
		    		$("#totalCount").text(parseInt($("#totalCount").html())-selectCount);
		    		$("#disableCount").text(parseInt($("#disableCount").html())-selectCount);
		    	}else{
		    		Util.promptSuccessDialog("删除考场子钟信息失败！");
		    	}
		    },
		    error:function(data){
		    	Util.promptSuccessDialog("删除考场子钟信息失败！");
		    }
		});
		
	});
	
});