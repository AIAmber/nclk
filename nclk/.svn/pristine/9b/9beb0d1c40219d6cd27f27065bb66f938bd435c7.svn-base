require(['jquery','Util', 'bootstrap-grid','table-toolbar','daterangepicker-zh-CN'], function($,Util) {
	
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
	    		         '<a data-id="'+value+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
	    		         '<a data-id="'+value+'" class="table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
    		     ].join('');
	        }
	    }]
	});
	
	$("#grids-timeserverconfig").on("click",".img-switch",function(e){
		debugger
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
	
});