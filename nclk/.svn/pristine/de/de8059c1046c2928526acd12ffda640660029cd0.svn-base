define(['jquery','bootstrap','paginator','bootstrap-table','bootstrap-table-sticky-header'],function($,bootstrap,paginator,bootstrapTable){
	
	$.extend($.fn.bootstrapTable.defaults, $.fn.bootstrapTable.locales['zh-CN']);
	//创建对象
	var BootstrapGrid= function(el,options){
		this.options=$.extend({},BootstrapGrid.DEFAULTS,options);
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        this._inintGrid(options);
	};
	BootstrapGrid.DEFAULTS={
		autoAdapt:true,	//是否自适应
		paginator:true,  //是否分页,
	    cache:false
	};
	//初始化表格	
	BootstrapGrid.prototype={
		_inintGrid:function(){
			var that = this;
			that.toolbarheight=30;
			if(that.options.customHeight!=undefined&&that.options.customHeight!=null){
				that.toolbarheight+=that.options.customHeight||0;
			}
			that.url = that.options.url||"";
			this.options.url="";
			this.options.locale='zh-CN';
			if(this.options.height==undefined){
				this.options.height=$('#rightContent').height()-(68+that.toolbarheight);
			}
			that.bootstrapTable = that.$el.bootstrapTable(this.options);
			if(this.options.paginator==true){
				that._initPaginator();
			}
			that.resetSearch=that.resetSearch;
			that.table=that.table;
			if(this.options.toolbarc!=undefined){
				this.options.toolbarc.foldEvent(function($element){
					that.toolbarheight=$element.height()+(that.options.customHeight||0);
					that.bootstrapTable.bootstrapTable('resetView',{height:$('#rightContent').height()-(68+that.toolbarheight)});
			  	});
			}
		},
		//初始化分页工具
		_initPaginator:function(){
			var that = this;
			$container = $('<div class="fixed-table-paginationc" style="display: block;"></div>');
			var paginator = that.paginator=$container.paginator({
				url:that.url,
				pageShowTotal:that.options.pageShowTotal!=undefined?that.options.pageShowTotal:true,							//是否显示总页数和当前页显示条数
				pageShowCurrentCount:that.options.pageShowCurrentCount!=undefined?that.options.pageShowCurrentCount:true,	
				pageShowSize:that.options.pageShowSize||'default',                 //分页大小设置 ： small->default->large
				skipPart:that.options.skipPart!=undefined?that.options.skipPart:true,  						//跳转模块是否添加
				callback:function(data){
					that.bootstrapTable.bootstrapTable('load', data.rows)
					//执行加载后事件
					if(that.options.loaded){
						that.options.loaded(data.rows,that);
					}
					//表格是否自适应
					if(that.options.autoAdapt==true){
						that.bootstrapTable.bootstrapTable('resetView',{height:$('#rightContent').height()-(68+that.toolbarheight)});
					}else{
						that.bootstrapTable.bootstrapTable('resetView');
					}
				}
	  		});
	  		that.$el.parent().parent().parent().find('div.fixed-table-container').after($container);
	  		$(window).resize(function() {
	  			//表格是否自适应
	  			if(that.options.autoAdapt==true){
	  				that.bootstrapTable.bootstrapTable('resetView',{height:$('#rightContent').height()-(68+that.toolbarheight)});
	  			}else{
	  				that.bootstrapTable.bootstrapTable('resetView');
	  			}
	  			
			});
	  		that.paginator=paginator;
		},
		//提供查询方法
		resetSearch:function(params){
			this.paginator.resetSearch(params);
		},
		//抛出官方接口
		table:function(method,params){
			return this.bootstrapTable.bootstrapTable(method,params);
		}
	}
			
  	$.fn.bootstrapGrid = function (options) {
  		 return new BootstrapGrid(this,options);
    };
    //左侧菜单缩进，让表头按照比例缩进
    $('#rightContent').parent().parent().parent().on('click','.left-main .sidebar-fold',function(){
    	$('#rightContent .fixed-table-header table').css('width','100%');
    });
});


