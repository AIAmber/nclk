require(['jquery','Util','bootstrap-dialog','bootstrap-grid','table-toolbar'], function($,Util,BootstrapDialog,grid) {	
	//授时服务器配置 编辑按钮 弹框事件
	var statusMonitorDialog = new BootstrapDialog({
		type:'type-default',
		title:'异常详情',
        message: $('#error_timeModal').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        cssClass:'statusMonitorConfig-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        /*buttons: [{
        	id:"savebtn",
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'col-sm-1 btn-success btn-sm btn-model-diy',
            action: function(dialogRef){
            	statusMonitorDialog.close();
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' col-sm-1 btn-default btn-sm btn-model-diy',
            action: function(dialogRef){
            	statusMonitorDialog.close();
            }
        }]*/
    });
	
	//新增授时服务器配置弹出框
	$("#error_time li").on("click",function(){
		statusMonitorDialog.open();
	});
});
