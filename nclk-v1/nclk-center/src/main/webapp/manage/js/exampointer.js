require(['jquery','bootstrap-grid','Util','table-toolbar','bootstrap-dialog','ztree'],function($,bootstrapGrid,Util,toolbar,bootstrapDialog,ztree){
	var toolbarc = $(".table-toolbar").toolbar();
	var gridTable=$('#exampointerGrid').bootstrapGrid({
		url:Util.getPath()+'/EndPointController/findEndPoints',
		uniqueId:'ENDPOINT_ID',
		toolbarc:toolbarc,	
		customHeight:64,
		columns:[{
			checkbox:true
		},{
			visible:false,
			field:'ENDPOINT_ID',
			title:'主键',
		},{
			field:'ENDPOINT_NO',
			title:'考点编号',
			width:'12%',
		},{
			field:'ENDPOINT_NAME',
			title:'考点名称',
			width:'12%',
		},{
			field:'ENDPOINT_ADDR',
			title:'考点地址',
			width:'20%',
		},{
			field:'ENDPOINT_IP',
			title:'考点服务器IP',
			width:'12%',
		},{
			field:'ENDPOINT_PORT',
			title:'考点服务器端口',
			width:'12%',
		},{
			field:'ENDPOINT_PERSON',
			title:'考点负责人',
			width:'10%',
		},{
			field:'ENDPOINT_TEL',
			title:'考点负责人电话',
			width:'12%',
		},{
			field:'ENDPOINT_ID',
			title:'操作',
			formatter:function(value,row,index){
				return[
					'<a class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
					'<a data-id="'+value+'" class="table-delete table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
				].join('');
			}
		}]
	});
	var exampointerAddInfo=$('#exampointerAddForm');
	var exampointerDialog=new bootstrapDialog({
		title:'新增考点',
		type:'type-default',
		message:exampointerAddInfo,
		closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        draggable:true,
        cssClass:'exampointerDialog',
        buttons: [{
        	id:'exampointerBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
            	if(!exampointerAddInfo.valid()){
            		validator.focusInvalid();
            	}else{
            		$.ajax({
        			    method: 'post',
        			    url: getPath()+"/EndPointController/addEndPointInfo",
        			    dataType: 'json',
        			    data: {
        			    	endPointName:$("[name='examName']").val(), 
        			    	endPointNum:$("[name='examNum']").val(),
        			    	endPointAreaId:$("#area-district").val(),
        			    	endPointAddr:$("[name='examAddress']").val(),
        			    	endPointPerson:$("[name='examPerson']").val(),
        			    	endPointTel:$("[name='examPhone']").val(),
        			    	endPointIPAddr:$("[name='examIPAddress']").val(),
        			    	endPointPort:$("[name='examPort']").val(),
                        	ord:$("[name='ord']").val(),
      			    },
        			    success: function(data){
        			        if (data.success) {
        			        	exampointerDialog.close();
        			        	Util.promptSuccessDialog(data.bean);
        			        	exampointerAddInfo[0].reset();
        			        } else {
        			        	Util.warningDialog(data.bean);
        			        	exampointerAddInfo[0].reset();
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
            	exampointerAddInfo[0].reset();
            	validator.resetForm();
            	dialogRef.close();
            }
        }]
	});
	$('#btn-add').on('click',function(){
		exampointerDialog.open();
	});
	
	
	
	//IP校验
	$.validator.addMethod("ip", function(value, element) { 
		 var ip= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	      return this.optional(element) || ip.test(value);    
	    }, "请填写正确的IP地址。");
	//手机号码11位校验
	$.validator.addMethod("phone", function(value, element) { 
		var phone=/^[1][3,4,5,7,8][0-9]{9}$/;
		return this.optional(element) || phone.test(value);    
	}, "请填写正确的手机号码。");
	
	var  validator=Util.validate('exampointerAddForm',{
		rules:{
			examName:{
				required:true
			},
			examNum:{
				required:true
			},
			examAddress:{
				required:true
			},
			examIPAddress:{
				required:true,
				ip:true
			},
			examPort:{
				required:true,
				digits:true
			},
			examPerson:{
				required:true
			},
			examPhone:{
				required:true,
				phone:true
			},
			ord:{
				required:true,
				digits:true
			}
		},
		messages:{
			examName:{
				required:"请输入考点名称"
			},
			examNum:{
				required:"请输入考点编号"
			},
			examAddress:{
				required:"请输入考点地址"
			},
			examIPAddress:{
				required:"请输入考点服务器IP"
			},
			examPort:{
				required:"请输入考点服务器端口",
				digits:"请输入正确的端口号"
			},
			examPerson:{
				required:"请输入考点负责人"
			},
			examPhone:{
				required:"请输入考点负责人电话"
			},
			ord:{
				required:"请输入顺序号",
				digits:"请输入正确的顺序号"
			}
		}
	})
	
	
	
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
		//获取选择的省份id
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
		});
	});
	
	//所属区域省市区三级联动
	$(document).ready(function(){
		//页面加载完成之后先通过getjson方法获取所有的省份数据
		//第一个参数为请求的url callBack为固定参数，  第二个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getProvince?callBack=?",function(res){
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#areaProvince").append(new Option("请选择省",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#areaProvince").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
			//添加完成之后触发选择事件
			$("#areaProvince").change();
		});
		
	});
	
	//给省份的选择框添加事件
	$("#areaProvince").change(function(){
		//获取选择的省份id
		var parentAreaId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		//第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getCity?callBack=?",{parentAreaId:parentAreaId},function(res){
			//清空城市选择看
			$("#areaCity").empty();
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#areaCity").append(new Option("请选择市",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#areaCity").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
			//添加完成之后触发选择事件
			$("#areaCity").change();
		});
	});
	
	//给地级市的选择框添加事件
	$("#areaCity").change(function(){
		//获取选择的省份id
		var parentAreaId = $(this).val();
		//和请求省份一样，通过getjson请求城市
		//第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
		$.getJSON(getPath()+"/AreaController/getDistrict?callBack=?",{parentAreaId:parentAreaId},function(res){
			//清空城市选择看
			$("#areaDistrict").empty();
			//通过jquery的each方法遍历res
			//Option(text, value, defaultSelected, selected)
			$("#areaDistrict").append(new Option("请选择区县",'',true,true));
			$.each(res,function(index,item){
				//查询元素，并且添加option
				$("#areaDistrict").append(new Option(item.AREA_NAME,item.AREA_ID,false,false));
			});
		});
	});
	
	
	//删除
	$('#exampointerGrid').on('click','.table-operation-del',function(){
		//当前行数据的主键值
		var id = $(this).parent().find("a").attr('data-id');
		Util.questionDialog('确定删除数据？',function(dialog, result){
			if(result==true){
				dialog.close();
				Util.promptSuccessDialog('数据删除成功！');
			}else{
				dialog.close();
			}
		});
	});
	
	
	$("#btn-delete").click(function(){
		var items = gridTable.table('getSelections');
		if(items.length==0){
			Util.warningDialog('尚未选择数据，请选择！');
		}else{
			Util.questionDialog('确定删除数据？',function(dialog, result){
				if(result){
					//TODO　这里调用后台
					Util.promptSuccessDialog('删除用户信息成功！');
				}
				dialog.close();
			});
		}
	});
	
	$('.test-connect').tooltip();
	$(".icon-connect").click(function(){
		if($("#examIPAddress").val() == ""){
			Util.warningDialog("请输入考点服务器ip");
			return ;
		}
		if($("#examPort").val() == ""){
			Util.warningDialog("请输入考点服务器端口");
			return ;
		}
		var $this = $(this);
		$this.next().show();
		$this.hide();
			$.ajax({
				method:"post",
				url:getPath() + "/EndPointController/getEndPointNo",
				dataType:"text",
				data:{
					endPointIPAddr : $("#examIPAddress").val(),
					endPointPort : $("#examPort").val()
				},
				success:function(data){
					if(data.success){
						$("#examNum").val(data.bean);
						Util.promptSuccessDialog("获取考点编号成功！");
					}else{
						Util.warningDialog("考点服务器IP或端口错误，获取失败！");
					}
				},
				complete:function(){
					$this.next().hide();
					$this.show();
				}
			})
			
	});
	
});