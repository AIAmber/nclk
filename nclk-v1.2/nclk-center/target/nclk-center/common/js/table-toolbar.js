(function ($, window) {

    var Toolbar = function (element, options) {
        this.$element = $(element);
        this.options = $.extend({}, $.fn.toolbar.defaults, options);
        this._init();
    };

    Toolbar.prototype = {
        constructor: Toolbar,
        _init: function () {
            this._initSearchBtn();
            this._initLabel();
            this._initFormControl();
            this._initFixWidth();
            this.resize();
            this._initResizeListener();
        },
        //初始化查询按钮，并给查询按钮绑定监听事件
        _initSearchBtn: function () {
            var searchBtnDiv = '<div class="search-btn clearfix">' +
                '<div class="pull-left">' +
                '<a href="javascript:void(0)" class="searchBtn glyphicon glyphicon-search"></a>' +
                '</div>' +
                '</div>';
            this.$element.find("form").after(searchBtnDiv);
            var options = this.options;
            var searchEvent = options.searchEvent;
            if (searchEvent instanceof Function) {
                $(this.$element).on("click", ".searchBtn", function () {
                    searchEvent();
                });
            }
        },
        //找出最大的label的宽度，所有label的宽度按照最大的来，保证所有查询框对齐
        _initLabel: function () {
            var $element = this.$element;
            var options = this.options;

            var $label = $element.find("form.form-inline span.form-label");
            //如果已经指定了最大的label的宽度，不再进行每个label的长度计算
            if (options.maxLabelWidth > 0) {
                $label.outerWidth(options.maxLabelWidth);
                return;
            }
            $label.width("auto").each(function () {
                var width = $(this).outerWidth();
                if (options.maxLabelWidth < width) {
                    options.maxLabelWidth = width;
                }
            }).outerWidth(options.maxLabelWidth);
        },
        _initFormControl: function () {
            var $element = this.$element;
            var options = this.options;

            this._getDynamicWithOutLarge().outerWidth(options.defaultFormControlWidth);
            $element.find(".form-control-large").outerWidth(options.defaultFormControlWidth + options.defaultFormControlLarge);
        },
        _initFixWidth: function () {
            var $element = this.$element;
            var options = this.options;

            //找出所有动态的元素，剩下就全是固定的元素
            var dynamicWidth = 0;
            $element.find(".form-control.dynamic").each(function () {
                dynamicWidth += $(this).outerWidth();
            });
            options.fixWidth = $element.find(".table-toolbar-search").width() - dynamicWidth;
        },
        _getDynamicWithOutLarge: function () {
            return this.$element.find(".form-control.dynamic").not(".form-control-large");
        },
        resize: function () {
            var $element = this.$element;
            var options = this.options;

            // 内容隐藏时不进行缩放、没有dynamic元素，不进行缩放
            var dynamicItem = $element.find(".form-control.dynamic");
            if (dynamicItem.length === 0||$element.css("display") === 'none' || $element.is(":hidden")) {
                return;
            }

            //得到无缩放状态下搜索区域自然伸缩的宽度
            if (options.searchBarWidth === 0) {
                options.searchBarWidth = $element.find(".table-toolbar-search").width();
            }

            var searchBarWidth = $element.width() - $element.children(".btn-group").outerWidth() - options.interval
                - parseFloat($element.find(".table-toolbar-search").css("padding-right"));
            if (options.searchBarWidth > searchBarWidth) {
                var itemWidth = (searchBarWidth - options.defaultFormControlLarge - options.fixWidth) / dynamicItem.length;
                this._getDynamicWithOutLarge().outerWidth(itemWidth);
                $element.find(".form-control-large").outerWidth(itemWidth + options.defaultFormControlLarge);
            } else {
                this._initFormControl();
            }

        },
        _initResizeListener: function () {
            var self = this;

            //toolBar动态宽度
            $(window).resize(function () {
                self.resize();
            });
        }
    };

    $.fn.toolbar = function (options) {
        var el = $(this);
        var toolBar = el.data("toolbar");
        if (toolBar) {
            // 如果该dom对象已经绑定了toolbar，则直接返回该对象
            $.extend(toolBar.options, options);
            return el.data("toolbar");
        }
        toolBar = new Toolbar(this.first(), options);
        el.data("toolbar", toolBar);
        return toolBar;
    };

    $.fn.toolbar.defaults = {
        "toolbarMinHeight": 30,//toolbar的最小高度
        "defaultFormControlWidth": 161,//每一个输入框或下拉框的默认宽度
        "defaultFormControlLarge": 90,//大的查询框默认比普通输入框大90
        "interval": 30,//搜索区域和按钮组的间隔
        "maxLabelWidth": 0,//最大的label宽度
        "searchBarWidth": 0,//无缩放状态下搜索区域自然伸缩的宽度
        "searchEvent": null//查询按钮被点击的回调事件
    };

    $.fn.toolbar.Constructor = Toolbar;

})(jQuery, window);