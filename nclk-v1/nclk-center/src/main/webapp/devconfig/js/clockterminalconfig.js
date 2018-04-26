require(['jquery','Util','bootstrap-dialog','bootstrap-grid','table-toolbar'], function($,Util,BootstrapDialog,grid) {	
	
	function getPath(){
		return location.pathname.match(/\/(.+?)(?=\/)/g)[0];
	}
	
	//省市区三级联动
	$(document).ready(function(){
		//页面加载完成之后先通过getjson方法获取所有的省份数据
		//第一个参数为请求的url callBack为固定参数，  第二个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getProvince?callBack=?",function(res){
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#province").append(new Option("请选择省",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#province").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
			//添加完成之后触发选择事件
			$("#province").change();
		});
	});
	
	//给省份的选择框添加事件
	$("#province").change(function(){
		//获取选择的省份id
		var parentAreaId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		//第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getCity?callBack=?",{parentAreaId:parentAreaId},function(res){
			//清空城市选择看
			$("#city").empty();
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#city").append(new Option("请选择市",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#city").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
			//添加完成之后触发选择事件
			$("#city").change();
		});
	});
	
	//给地级市的选择框添加事件
	$("#city").change(function(){
		//获取选择的地级市id
		var parentAreaId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		//第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getDistrict?callBack=?",{parentAreaId:parentAreaId},function(res){
			//清空城市选择看
			$("#district").empty();
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#district").append(new Option("请选择区县",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#district").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
			//添加完成之后触发选择事件
			$("#district").change();
		});
	});
	
	//给区县选择框添加事件
	$("#district").change(function(){
		//获取选择的区县id
		var districtId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		//第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
		$.getJSON(getPath()+"/EndPointController/getEndPointByDistrictId?callBack=?",{districtId:districtId},function(res){
			//清空城市选择看
			$("#endpoint").empty();
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#endpoint").append(new Option("请选择考点",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#endpoint").append(new Option(item.ENDPOINT_NAME,item.ENDPOINT_ID,false,false));
			});
		});
	});
	
	//给考点选择框添加事件
	$("#endpoint").change(function(){
		//获取选择的考点id
		var endPointId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		$.ajax({
     	   url: getPath()+"/ClockTerminalConfigController/findEndPointClockState",
     	   type: 'POST',
     	   dataType: 'text',
     	   data: {
     		  endPointId : endPointId
     	   },
     	   success: function (data) {
     		   $("#normal_time").empty();
     		   $("#error_time").empty();
     		   //正常时钟数量
     		   var normalCount = JSON.parse(data).normalCount.NORMALCOUNT;
     		   //异常时钟数量
     		   var disableCount = JSON.parse(data).disableCount.NORMALCOUNT;
     		   $("#normalCount").html(normalCount);
     		   $("#disableCount").html(disableCount);
     		   $("#totalCount").html(normalCount+disableCount);
     		   //循环展示 监听状态时钟
     		   for (var i = 0; i < normalCount; i++) {
     			  $("#normal_time").append("<li><img src='"+getPath()+"/devconfig/images/time.png' /><span>"+JSON.parse(data).normalClock[i].EXAMROOM_IP+"</span><a href='javascript:void(0)'>编辑</a></li>");
     		   }
     		   //监听状态时钟 编辑事件
     		   $("#normal_time li a").on("click",function(){
     			  //监听状态时钟 编辑事件
     			  $.ajax({
               	   url: getPath() + "/ClockTerminalConfigController/findExamRoomInfo",
               	   type: 'POST',
               	   data: {
               		   endPointId : $("#endpoint").val(),
               		   examRoomIP : $(this).parent().find("span").html()
               	   },
               	   success: function (data) {
               		   $("[name='examRoomIp']").val(data.EXAMROOM_IP);
               		   $("[name='examRoomName']").val(data.EXAMROOM_NAME);
               		   $("[name='examRoomAddr']").val(data.EXAMROOM_ADDR);
               		   $("[name='examRoomPerson']").val(data.EXAMROOM_PERSON);
               		   $("[name='examRoomTel']").val(data.EXAMROOM_TEL);
               		   $("[name='ord']").val(data.ORD);
               		   endPointclocKEditDialog.setTitle("考场子钟信息编辑");
               		   endPointclocKEditDialog.open();
               	   },
               	   error:function(data){
               		   Util.promptSuccessDialog("");
               	   }
               	});
     			  endPointclocKEditDialog.setTitle("考场子钟信息编辑");
     			  endPointclocKEditDialog.open();
     		   });
     		   //循环展示 停用状态时钟
     		   for (var j = 0; j < disableCount; j++) {
     			  $("#error_time").append("<li><img src='"+getPath()+"/devconfig/images/time.png' /><span>"+JSON.parse(data).disableClock[j].EXAMROOM_IP+"</span><a >编辑</a></li>");
     		   }
     		   $("#error_time li a").on("click",function(){
     			  //停用状态时钟 编辑事件
     		   });
     	   },
     	   error:function(data){
     		   Util.promptSuccessDialog(data.bean);
     	   }
     	});
	});
	
	//考场子钟信息初始化弹出框
	var endPointImportDialog = new BootstrapDialog({
		type:'type-default',
		title:'初始化考场子钟信息',
        message: $('#endpointimport').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
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
            	var formData = new FormData(document.getElementById("uploadendpointtemplet"));//表单id
            	$.ajax({
            	   url: getPath() + "/ClockTerminalConfigController/uploadTemplet",
            	   type: 'POST',
            	   data: formData,
            	   async: false,
            	   cache: false,
            	   contentType: false,
            	   processData: false,
            	   success: function (data) {
            		   if (data.success) {
            			   	endPointImportDialog.close();
   			        		Util.promptSuccessDialog(data.bean);
   			        		$("#uploadendpointtemplet")[0].reset();
   			        	} else {
   			        		Util.warningDialog(data.bean);
   			        		$("#uploadendpointtemplet")[0].reset();
   			        	}
            	   },
            	   error:function(data){
            		   Util.promptSuccessDialog(data.bean);
            	   }
            	});
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	endPointImportDialog.close();
            }
        }]
    });
	
	//手动导入 按钮事件
	$("#btn-import").on("click",function(){
		endPointImportDialog.open();
	});
	
	//手动添加考点信息弹出框表单校验
	var endpointaddform = Util.validate("endpointaddform",{
		rules :{
			examRoomIp:{
				required:true
			}
		},
		messages: {
			examRoomIp:{
				required:"考场子钟IP不能为空"
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
        			$("#normal_time").append("<li><img src='"+getPath()+"/devconfig/images/time.png' /><span>"+$("[name='examRoomIp']").val()+"</span><a onclick=''>编辑</a>");
        			$.ajax({
        			    method: 'post',
        			    url: getPath()+"/ClockTerminalConfigController/addExamRoomInfo",
        			    dataType: 'text',
        			    // 用户名和密码
        			    data: {
        			    	examRoomIp:$("[name='examRoomIp']").val(), 
                        	examRoomName:$("[name='examRoomName']").val(),
                        	examRoomAddr:$("[name='examRoomAddr']").val(),
                        	examRoomPerson:$("[name='examRoomPerson']").val(),
                        	examRoomTel:$("[name='examRoomTel']").val(),
                        	ord:$("[name='ord']").val(),
                        	endpointId:$("#endpoint").val() //考点id
        			    },
        			    success: function(data){
        			        if (JSON.parse(data).success) {
        			        	endPointclockAddDialog.close();
        			        	Util.promptSuccessDialog(JSON.parse(data).bean);
        			        	$("#endpointaddform")[0].reset();
        			        } else {
        			        	/*Util.warningDialog(JSON.parse(data).bean);*/
        			        	$("#endpointaddform")[0].reset();
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
            	endPointclockAddDialog.close();
            }
        }]
    });
	
	//手动添加 按钮事件
	$("#btn-add").on("click",function(){
		if($("#province").val() == ""){
			Util.warningDialog("请选择省");
			return;
		}else if($("#city").val() == ""){
			Util.warningDialog("请选择市");
			return;
		}else if($("#district").val() == ""){
			Util.warningDialog("请选择区县");
			return;
		}else if($("#endpoint").val() == ""){
			Util.warningDialog("请选择考点");
			return;
		}else{
			endPointclockAddDialog.open();
		}
	});
	
	//自动检索 按钮事件
	$("#btn-search").on("click",function(){
		if($("#province").val() == ""){
			Util.warningDialog("请选择省");
			return;
		}else if($("#city").val() == ""){
			Util.warningDialog("请选择市");
			return;
		}else if($("#district").val() == ""){
			Util.warningDialog("请选择区县");
			return;
		}else if($("#endpoint").val() == ""){
			Util.warningDialog("请选择考点");
			return;
		}else{
			//调用接口 自动检索
			$.ajax({
			    method: 'post',
			    url: getPath()+"/ClockTerminalConfigController/autoSearchExamRoomInfo",
			    dataType: 'text',
			    data: {
			    	endPointId : $("#endpoint").val()
			    },
			    success: function(data){
			    	if(JSON.parse(data).total > 0){
			    		for (var i = 0; i < JSON.parse(data).rows.length; i++) {
				    		$("#addAutoSearchClock").append("<li class='form-group'>" +
				    				"<img src='"+getPath()+"/devconfig/images/time.png' />" +
				    				"<div class='' >" +
				    				"<input type='checkbox' name='clockbox'><span>"+JSON.parse(data).rows[i].ip+"<span>" +
				    				"</div></li>");
						}
			    		endPointclockAutoSearchDialog.setTitle("共检索到考场子钟"+JSON.parse(data).total+"台");
			    		endPointclockAutoSearchDialog.open();
			    		//alert("一共检测到：" + JSON.parse(data).total);
			    	}else{
			    		Util.warningDialog("未检索到考场子钟，请手动添加！");
			    	}
			    },
			    error:function(data){
			    	alert("error");
			    }
			});
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
	$("#selectFile").click(function(){
		$("#file").click();
	});
	
	//自动搜索考点子钟弹出框
	var endPointclockAutoSearchDialog = new BootstrapDialog({
		type:'type-default',
        message: $('#endPointAutoSearch').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        cssClass:'endpointautosearch-dialog',
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
            	$("[name='clockbox']:checked").each(function(){
            		$("#normal_time").append("<li><img src='"+getPath()+"/devconfig/images/time.png' /><span>"+$(this).parent().find("span").html()+"</span><a class='clockedit'>编辑</a>");
        		});
            	endPointclockAutoSearchDialog.close();
            	$("#addAutoSearchClock").empty();
            	$("#endPointAutoSearchForm")[0].reset();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	endPointclockAutoSearchDialog.close();
            }
        }]
    });
	
	//自动扫描子钟 全选按钮
	$("#selectAllClock").on("click",function(){
		$("[name='clockbox']").attr("checked",true);
	});
	
	//考场子钟信息编辑弹出框
	var endPointclocKEditDialog = new BootstrapDialog({
		type:'type-default',
        message: $('#endpointadd').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        cssClass:'endpointinfoedit-dialog',
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
            	
            	endPointclocKEditDialog.close();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	endPointclocKEditDialog.close();
            }
        }]
    });
	
});