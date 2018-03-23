require(['jquery','Util','bootstrap-dialog','bootstrap-grid','table-toolbar'], function($,Util,BootstrapDialog,grid) {

    function getPath() {
        return location.pathname.match(/\/(.+?)(?=\/)/g)[0];
    }

    var areaPid = "000000032";
    var districtNames = [];

    //省市二级联动
    $(document).ready(function () {
        //页面加载完成之后先通过getjson方法获取所有的省份数据
        //第一个参数为请求的url callBack为固定参数，  第二个参数为回调函数
        $.getJSON(getPath() + "/AreaController/getProvince?callBack=?", function (res) {
            //通过jquery的each方法遍历res
            //Option(text, value, defaultSelected, selected)
            $("#province").append(new Option("请选择省", '', true, true));
            $.each(res, function (index, item) {
                //查询元素，并且添加option
                $("#province").append(new Option(item.AREA_NAME, item.AREA_ID, false, false));
            });
            //添加完成之后触发选择事件
            $("#province").change();
        });

    });

    //给省份的选择框添加事件
    $("#province").change(function () {
        //获取选择的省份id
        var parentAreaId = $(this).val();
        //和请求省份一样，通过getjson请求城市
        //第一个参数为请求的url callBack为固定参数，第二个参数为需要传递的参数,  第三个参数为回调函数
        $.getJSON(getPath() + "/AreaController/getCity?callBack=?", {parentAreaId: parentAreaId}, function (res) {
            //清空城市选择看
            $("#city").empty();
            //通过jquery的each方法遍历res
            //Option(text, value, defaultSelected, selected)
            $("#city").append(new Option("请选择市", '', true, true));
            $.each(res, function (index, item) {
                //查询元素，并且添加option
                $("#city").append(new Option(item.AREA_NAME, item.AREA_ID, false, false));
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


});