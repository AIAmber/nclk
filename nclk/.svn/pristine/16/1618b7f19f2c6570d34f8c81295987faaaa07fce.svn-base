require(['jquery','Util','bootstrap-dialog','bootstrap-grid','table-toolbar'], function($,Util,BootstrapDialog,grid) {	
	
	$(function(){
		$(".form-control.pull-left").width("100px");
	});
	
	var tableToolbarCataloged = $("#toolbar-cataloged").toolbar({
		maxLabelWidth:56,
		searchBarWidth:904,
		searchEvent:function(){
			gridTableCataloged.resetSearch();
		}
	});
	
	var gridTableCataloged = $('#grids-timeserverconfig').bootstrapGrid({
		url:"DevConfigController/findTimeServer",
		uniqueId:'id',
		toolbarc:tableToolbarCataloged,
		customHeight:64,
		loaded:function(){
			$('.prompt-info').tooltip();
		},
		columns:[{
	        checkbox: true
	    }, {
	    	visible:false,
	        field: 'id',
	        title: '主键'
	    }, {
	        field: 'editAble',
	        title: '',
	        "class":"prompt",
	        width: '10',
	        formatter:function(value,row,index){
	        	if(value==true){
    		 		return '<span class="fa fa-exclamation-circle prompt-info" data-toggle="tooltip" data-placement="right" title="'+row.errorMsg+'"></span>';
	        	}
        		return "";
	        }
	    },{
	        field: 'machineId',
	        title: '设备编号'
	    }, {
	        field: 'timeSource',
	        title: '时间源'
	    }, {
	        field: 'time',
	        title: '当前时间'
	    }, {
	        field: 'clockip',
	        title: '授时ip地址'
	    }, {
	        field: 'machineStatus',
	        title: '设备状态'
	    }, {
	        field: 'testCenter',
	        title: '所属考点'
	    }, {
	        field: 'isEnable',
	        title: '是否启用',
	        formatter:function(value,row,index){
	        	if(value==true){
	        		return '<div class="img-switch img-close" title="停用"></div>';
	        	}else{
	        		return '<div class="img-switch img-open" title="启用"></div>';
	        	}
	        }
	    },{
	    	field: 'id',
	        title: '操作',
	        width: '64',
	    	formatter:function(value,row,index){
	    		return [
	    		         '<a data-id="'+value+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改" ></a>',
	    		         '<a data-id="'+value+'" class="table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
    		     ].join('');
	        }
	    }]
	});
	
	//授时服务器配置 列表 是否启用 点击事件
	$("#grids-timeserverconfig").on("click",".img-switch",function(e){
		var $this = $(this);
		var isOpen = $this.hasClass("img-open");
		var parent = $this.parent();
		if(isOpen){
			Util.questionDialog('确定停用该设备？',function(dialog, result){
				if(result){
					parent.empty();
					parent.append('<div class="img-switch img-close" title="停用"></div>');
					parent.next().find(".table-operation").removeClass("disable");
					Util.promptSuccessDialog("停用成功！");
				}
				dialog.close();
			});
		}else{
			parent.empty();
			parent.append('<div class="img-switch img-open" title="启用"></div>');
			parent.next().find(".table-operation-edit").addClass("disable");
			parent.next().find(".table-operation-del").addClass("disable");
			Util.promptSuccessDialog("开启成功！");
		}
	});
	
	//授时服务器配置 编辑按钮 弹框事件
	var TimeServerConfigDialog = new BootstrapDialog({
		title: '修改授时服务器配置',
		type:'type-default',
        message: $('#editTimeServerConfig').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        cssClass:'timeServerConfig-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        buttons: [{
        	id:"savebtn",
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	//资源提供方信息 选择资源格式模态框 保存按钮 事件
            	TimeServerConfigDialog.close();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	TimeServerConfigDialog.close();
            }
        }]
    });
	
	//授时服务器配置 列表 编辑按钮
	$("#grids-timeserverconfig").on("click",".table-operation-edit",function(e){
		//获取当前行数据的主键值
		$(this).parent().find("a").attr('data-id');
		//使用ajax 请求去后台查找数据
		
		//弹出弹框
		TimeServerConfigDialog.open();
	});
	
	//授时服务器配置 列表 删除按钮
	$('#grids-timeserverconfig').on('click','.table-operation-del',function(){
		//当前行数据的主键值
		var id = $(this).parent().find("a").attr('data-id');
		Util.questionDialog('确定删除数据？',function(dialog, result){
			if(result==true){
				console.log("主键值" + id);
				dialog.close();
				Util.promptSuccessDialog('数据删除成功！');
			}else{
				dialog.close();
			}
		});
	});
	
	$("#btn-delete").on("click",function(){
		var selectData = gridTableCataloged.table("getSelections");
		if(selectData.length > 0){
			Util.questionDialog('确定删除数据？',function(dialog, result){
				for(var i = 0 ; i < selectData.length; i++ ){
					console.log(selectData[i].id);	
				}
				dialog.close();
				if(result==true){
					Util.promptErrorDialog('数据删除成功！');
				}
			});
		}else{
			Util.warningDialog('尚未选择数据，请选择！',function(dialog, result){
				dialog.close();
			});
		}
	});
	
});