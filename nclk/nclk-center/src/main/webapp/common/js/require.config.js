require.config({
	//定义根路径
	baseUrl:'common/',
	urlArgs:'v='+(new Date()).getTime(),//清除缓存
	paths:{
		'jquery':'plugins/jquery/jquery.min',
		'bootstrap':'plugins/bootstrap/js/bootstrap.min',
		'bootstrap-table':'plugins/bootstrap-table/js/bootstrap-table.min',
		'bootstrap-table-zh-CN':'plugins/bootstrap-table/js/bootstrap-table-zh-CN.min',
		'daterangepicker':'plugins/daterangepicker/js/daterangepicker',
		'daterangepicker-zh-CN':'plugins/daterangepicker/js/daterangepicker.zh-CN',
		'moment':'plugins/daterangepicker/js/moment.min',
		'ztree':'plugins/ztree/js/jquery.ztree.all.3.5.28.min',
		'Router':'plugins/director/director.min',
		'echarts':'plugins/echarts/echarts.min',
		'bootstrap-dialog':'plugins/bootstrap-dialog/js/bootstrap-dialog.min',
		'bootstrap-multiselect':'plugins/bootstrap-multiselect/js/bootstrap-multiselect',
		'bootstrap-switch':'plugins/bootstrap-switch/js/bootstrap-switch.min',
		'jquery-validate':'plugins/jquery-validate/jquery.validate.min',
		'jquery-validate-messages-zh':'plugins/jquery-validate/messages_zh',
		//自定义封装控件
		'table-toolbar':'js/table-toolbar',
		'main':'js/main',
		'bootstrap-grid':'js/bootstrap-grid',
		'sourceInfoDialog':'js/sourceInfoDialog',
		'routes':'js/router',
		//通用工具
		'Util':'js/util'
	},
	//依赖关系
	shim:{
		'jquery': {exports: 'jquery'},
		'bootstrap-table-zh-CN':{'deps':['jquery','bootstrap-table']},
		'bootstrap':{'deps':['jquery',
		             'css!plugins/bootstrap-table/css/bootstrap-table.min',
		             'css!../common/css/bootstrap-table'
		             ],
		             exports:'bootstrap'},
		'bootstrap-multiselect':['jquery',
		                         'bootstrap',
            		             'css!plugins/bootstrap-multiselect/css/bootstrap-multiselect'
		            		    ],
		'bootstrap-switch':['jquery','bootstrap','css!plugins/bootstrap-switch/css/bootstrap-switch.min'],
		'jquery-validate':{'deps':['jquery']},
		'jquery-validate-messages-zh':['jquery-validate','jquery'],
		'echarts':['jquery'],
		'bootstrap-dialog':['jquery','bootstrap'],
		'daterangepicker-zh-CN':['jquery','bootstrap','moment','daterangepicker'],
		'daterangepicker':['jquery','bootstrap','moment'],
		'ztree':['jquery','css!plugins/ztree/css/metroStyle'],
		'bootstrap-grid':{'deps':['jquery','bootstrap','bootstrap-table']},
		'sourceInfoDialog':{'deps':['css!../common/css/sourceInfoDialog']},
		'table-toolbar':{'deps':['jquery','css!../common/css/table-toolbar']},
		'Router': {exports: 'Router'}
	},
	//css样式加载器
    map: {
        '*': {
            css: 'common/plugins/requirejs/css.min.js'
        }
    }
});
require(['jquery','main','routes'], function($,main,routes) {
	routes.inint();
}); 