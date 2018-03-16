(function($, window, document){
	
	var Toolbar = function(element, options){
		this.$element = $(element);
		this.options = $.extend({},$.fn.toolbar.defaults,options);
		this._init();
	};
	
	Toolbar.prototype = {
		constructor: Toolbar,
		_init:function(){
			this._initSearchBtn();
			this._initArrowBtn();
			this._initLabel();
			this._initFormControl();
			this.resize();
			this._initResizeListener();
		},
		//初始化查询按钮和下拉箭头，追加在末尾，并给查询按钮绑定监听事件
		_initSearchBtn:function(){
			var searchBtnDiv='<div class="search-btn clearfix">'+
								'<div class="pull-right">'+
							    	'<a href="javascript:void(0)" class="arrow-btn">'+
							    	'<i class="fa fa-angle-double-down"></i></a>'+
							    '</div>'+
							    '<div class="pull-right">'+
							    	'<a href="javascript:void(0)" class="arrow-btn arrow-up">'+
							    	'<i class="fa fa-angle-double-up"></i></a>'+
							    '</div>'+
							    '<div class="pull-left">'+
							    	'<a href="javascript:void(0)" class="searchBtn glyphicon glyphicon-search"></a>'+
							    '</div>'+
							'</div>';
			this.$element.find("form").after(searchBtnDiv);
			var options = this.options;
			$(this.$element).on("click",".searchBtn",function(){
				var searchEvent = options.searchEvent;
				if(searchEvent instanceof Function){
					searchEvent();
				}
			});
		},
		//初始化箭头的点击事件，实现查询按钮的展示和隐藏
		_initArrowBtn:function(){
			var $element = this.$element;
			var options = this.options;
			
			var count = $element.find("form ul").length;
			if(count == 1){
				//由于这里行数等于1，没有下拉按钮，所以右边距缩小一半
				$element.find(".arrow-btn").hide();
				$element.find(".table-toolbar-search").css("padding-right",function(index,value){
					return parseFloat(value) / 2;
				});
				return;
			}
			
			$element.find(".arrow-btn").on("click",function(){
				var toolbar = $(this).parents(".table-toolbar-search");
				var form = toolbar.find("form");
				
				//这里剪掉10是因为ul有一个margin-bottom，这个属性会导致搜索区域和按钮的下边距不对齐
				var formHeight = form.height()-10;
				if (toolbar.height() == formHeight){
					toolbar.height(options.toolbarMinHeight);
				} else {
					toolbar.height(formHeight);
				}
				toolbar.find(".arrow-btn").toggle();
				var foldEventFunc = options.foldEvent;
				if(foldEventFunc instanceof Function){
					foldEventFunc($element);
				}
			});
			
		},
		//找出最大的label的宽度，所有label的宽度按照最大的来，保证所有查询框对齐
		_initLabel:function(){
			var $element = this.$element;
			var options = this.options;
			
			var $label = $element.find("form.form-inline span.form-label");
			//得到第一个label的左右margin
			options.labelMar = parseFloat($label.css("margin-left")) + parseFloat($label.css("margin-right"));
			if(options.maxLabelWidth > 0) {
				$label.outerWidth(options.maxLabelWidth);
				return;
			}
			$label.width("auto").each(function(){
					var width = $(this).outerWidth();
					if(options.maxLabelWidth < width){
						options.maxLabelWidth = width;
					}
			}).outerWidth(options.maxLabelWidth);
		},
		_initFormControl:function(){
			var $element = this.$element;
			var options = this.options;
			
			this._getFormControlWithOutLarge().outerWidth(options.defaultFormControlWidth);
			$element.find(".form-control-large").outerWidth(options.maxLabelWidth + options.defaultFormControlWidth + options.labelMar);
		},
		_initResizeListener:function(){
			var self=this;
			
			//toolBar动态宽度
			$(window).resize(function(){
				self.resize();
			});
		},
		_getFormControlWithOutLarge:function(){
			return this.$element.find(".form-control").not(".form-control-large").not(".dateCustom");
		},
		foldEvent:function(foldEvent){
			this.options.foldEvent=foldEvent;
		},
		resize:function(){
			var self=this;
			var $element = this.$element;
			var options = this.options;
			
			if($element.css("display")=='none' || $element.is(":hidden")){
				return;
			}
			
			var count = $element.find("form.form-inline ul:first .form-control").not(".dateCustom").length;
			//得到无缩放状态下搜索区域自然伸缩的宽度
			if(options.searchBarWidth == 0){
				options.searchBarWidth = $element.find(".table-toolbar-search").outerWidth();
			}
			//得到除了FormControl之外其他元素的宽度
			if(options.otherWidth == -1){
				options.otherWidth = options.searchBarWidth - options.defaultFormControlWidth * count + options.interval;
			}
			
			var btnGroupWidth = $element.find(".table-toolbar-btn-group").first().outerWidth();
			var toolBarWidth = $element.outerWidth();

			if(options.searchBarWidth >= (toolBarWidth - btnGroupWidth - options.interval)){
				var controlWidth = toolBarWidth - btnGroupWidth - options.otherWidth;
				self._getFormControlWithOutLarge().outerWidth(controlWidth / count);
				$element.find(".form-control-large").outerWidth(controlWidth / count + options.maxLabelWidth + options.labelMar);
			}else{
				self._getFormControlWithOutLarge().outerWidth(options.defaultFormControlWidth);
				$element.find(".form-control-large").outerWidth(options.maxLabelWidth + options.defaultFormControlWidth + options.labelMar);
			}
			
		}
	};
	
	$.fn.toolbar = function(options) {
		var el = $(this);
		var toolBar = el.data("toolbar");
		if(toolBar){
			$.extend(toolBar.options,options);
			return el.data("toolbar");
		}
		toolBar = new Toolbar(this.first(),options);
		el.data("toolbar",toolBar);
		return toolBar;
        /*this.each(function() {
            new Toolbar(this,options);
        });
        return this;*/
    };
    
    $.fn.toolbar.defaults = {
		"toolbarMinHeight": 30,//toolbar的最小高度
    	"defaultFormControlWidth": 161,//每一个输入框或下拉框的默认宽度
    	"interval" : 50,//搜索区域和按钮组的间隔
    	"maxLabelWidth": 0,//最大的label宽度，通常情况下不需要用到该配置项
    	"otherWidth" : -1,//除了formcontrol之外的其他宽度
		"searchBarWidth": 0,//无缩放状态下搜索区域自然伸缩的宽度
		"labelMar" : -1,//label的左右margin
		"foldEvent":null,//折叠事件
		"searchEvent":null//查询按钮被点击的回调事件
	};
    
	$.fn.toolbar.Constructor = Toolbar;
    
})(jQuery, window, document)