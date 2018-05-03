require(['jquery','bootstrap-grid','Util','table-toolbar','bootstrap-dialog','ztree','jquery-placeholder'],function($,bootstrapGrid,Util,toolbar,bootstrapDialog,ztree,placeholder){
	var loadPage=true;
	var totalExamNumEdit="";
	//添加ie8下placeholder的支持
	$(function(){
	//判断该登录用户是否具备查看此菜单的权限，没有则跳转首页
	$.ajax({
     	   url: Util.getPath()+"/EndPointController/identityAuthentication",
     	   method: 'post',
     	   dataType: 'text',
     	   async:true,
     	   data: {moduleId:"4e9e975cea554ee1850163eeac44c218"},
     	   success: function(data) {
     		   if(data=="no"){
                   Util.warningDialog("您没有访问页面权限,请重新登录！！",function(){
   						location.href = Util.getPath() + "/login/index.jsp";
                   },{
                	   closable:false
                   });
     		   }
     		 },
     		 error:function(data){
                 Util.warningDialog("网络异常,请重新登录！",function(){
                	 location.href = Util.getPath() + "/login/index.jsp"
                 },{
              	   closable:false
                 });
     		 }
     	});
		//查询框ie兼容placeholder
		if($("#searchText").val() == ""){
			$("#searchText").placeholder();
		}
		//导出框支持ie兼容placeholder
		if($("#filename").val() == ""){
			$("#filename").placeholder();
		}
		//初始化页面时查询区省份下拉框赋值
		$.ajax({
	     	   url: Util.getPath()+"/AreaController/getProvinceAll",
	     	   type: 'post',
	     	   dataType: 'json',
	     	   async:false,
	     	   data: {},
	     	   success: function (data) {
	     		  for (var i = 0; i < data.length; i++) {
	     			 $("#province").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
//	      			 $("#areaProvince_add").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
//	      			 $("#areaProvince_edit").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
	      		  }
	     		  //添加完成之后触发选择事件
		  	      $("#province").change();
//		  	      exampointerAddInfo.find("#areaProvince_add").change();
//		  	      exampointerAddInfo.find("#areaProvince_edit").change();
	     	   }
	     	});
	    //IP校验
	    $.validator.addMethod("ip", function(value, element) {
	        var ip= /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
	        return this.optional(element) || ip.test(value);
	    }, "请填写正确的IP地址。");

	});
	//考点管理 全部 有效 无效按钮事件
	$("#btn-group-tab").on("click","a",function(){
		$(".active"). removeClass("active");
		$(this).addClass("active");
    		
		var state = $(this).html();
		var stateValue = "A";
        if("有效" == state){
        	stateValue = "A";
        }else if("无效" == state){
        	stateValue = "X";
        }else{
        	stateValue = "";
        }
		gridTable.resetSearch({
			"state" : stateValue
		});
	});
	
    $(".table-toolbar").toolbar({
    	defaultFormControlLarge:140,
    	searchEvent: function () {
            var searchText = $("#searchText").val();
            var areaId = "";
            var state = $("#btn-group-tab").find(".active").html();
    		var stateValue = "A";
            if("有效" == state){
            	stateValue = "A";
            }else if("无效" == state){
            	stateValue = "X";
            }else{
            	stateValue = "";
            }
            
            var provinceId = $("#province").val();
            var cityId = $("#city").val();
            var districtId = $("#district").val();
    		
            var areaId= "";
            var areaType = ""; //1 2 3 代表省市区
            if(districtId!=""){
            	//areaId的值为县区的id
            	areaId = districtId;
            	areaType = "3";
            }else if(cityId!=""){
            	//areaId的值为地级市的id
            	areaId = cityId;
            	areaType = "2";
            }else if(provinceId!=""){
            	//areaId的值为省的id
            	areaId = provinceId;
            	areaType = "1";
            }
            
            gridTable.resetSearch({
                "searchText": searchText,
                "areaId": areaId,
                "state" : stateValue,
                "areaType":areaType
            });
        }
    });
    var gridTable=$('#exampointerGrid').bootstrapGrid({
        url:Util.getPath()+'/EndPointController/findEndPoints',
        uniqueId:'ENDPOINT_ID',
        columns:[{
            checkbox:true
        },{
            field:'ENDPOINT_NO',
            title:'考点编号'
        },{
            field:'ENDPOINT_NAME',
            title:'考点名称'
        },{
            field:'ENDPOINT_SERVER',
            title:'考点服务器'
        },{
            field:'ENDPOINT_PERSON',
            title:'考点负责人'
        },{
            field:'ENDPOINT_TEL',
            title:'考点负责人电话'
        },{
            field:'STATE',
            title:'状态',
            formatter:function(value,row,index){
               if(value =="A"){
            	   return "有效";
               }else if(value == "X"){
            	   return "<font class='red'>无效</font>";
               }
            }
        },{
            field:'ENDPOINT_ID',
            title:'操作',
            formatter:function(value,row,index){
            	//区域为禁用状态时
	    		if(row.STATE=="X"){
	    			return[
	                        '<a data-id="'+row.ENDPOINT_ID+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
	                        '<a data-id="'+value+'" class="table-delete table-operation table-operation-del fa fa-trash-o" style="opacity: 0;filter: alpha(opacity = 0);" title="删除"></a>'
	                ].join('');
	    		}else{
	    			 return[
	                        '<a data-id="'+row.ENDPOINT_ID+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
	                        '<a data-id="'+value+'" class="table-delete table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
	                 ].join('');
	    		}
            }
        }]
    });
    //新增考点表单
    var exampointerAddInfo=$('#exampointerAddForm');
    var exampointerDialogAdd=new bootstrapDialog({
        type:'type-default',
        message:exampointerAddInfo,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        draggable:true,
        closable: false,
        cssClass:'exampointerDialog',
        buttons: [{
            id:'exampointerBtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
                if(!exampointerAddInfo.valid()){
                    validatorAdd.focusInvalid();
                }else{
                	var endPointId=exampointerAddInfo.find("#examID_add").val();
            		$.ajax({
                        method: 'post',
                        url: Util.getPath()+"/EndPointController/findEndPointNo",
                        dataType: 'json',
                        data: {
                            endPointNum:exampointerAddInfo.find("#examNum_add").val(),
                        },
                        success: function(data){
	                        	//新增考点编号检验是否存在
	                        	if(data==0){
	                        		addPointerForm(endPointId);
	                        	}else{
	                        		Util.promptNoticeDialog("考点编号已存在，请重新输入");
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
                validatorAdd.resetForm();
                exampointerAddInfo.find('#areaProvince_add').children('option').first().prop("selected","selected");
                exampointerAddInfo.find("#areaCity_add").empty();
                exampointerAddInfo.find("#areaCity_add").prepend("<option value='' selected>请选择市</option>");
                exampointerAddInfo.find("#areaDistrict_add").empty();
                exampointerAddInfo.find("#areaDistrict_add").prepend("<option value='' selected>请选择县区</option>");
                dialogRef.close();

            }
        }]
    });
    
    
    //考点模态框信息保存
    var exampointerEditInfo=$('#exampointerEditForm');
    var exampointerDialogEdit=new bootstrapDialog({
        type:'type-default',
        message:exampointerEditInfo,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        draggable:true,
        closable: false,
        cssClass:'exampointerDialog',
        buttons: [{
            id:'exampointerBtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass:' btn-success btn-sm',
            action: function(dialogRef){
                if(!exampointerEditInfo.valid()){
                    validatorEdit.focusInvalid();
                }else{
                	var endPointId=exampointerEditInfo.find("#examID_edit").val();
                	var endPointerNum=exampointerEditInfo.find("#examNum_edit").val();
                	if(totalExamNumEdit!=endPointerNum){
                		$.ajax({
                            method: 'post',
                            url: Util.getPath()+"/EndPointController/findEndPointNo",
                            dataType: 'json',
                            data: {
                                endPointNum:endPointerNum,
                            },
                            success: function(data){
    	                        	//新增考点编号检验是否存在
    	                        	if(data==0){
    	                        		editPointerForm(endPointId)
    	                        	}else{
    	                        		Util.promptNoticeDialog("考点编号已存在，请重新输入");
    	                        	}
                            	}
                            });
                	}else{
                		editPointerForm(endPointId);
                	}
                }
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:' btn-default btn-sm',
            action: function(dialogRef){
            	exampointerEditInfo[0].reset();
                validatorEdit.resetForm();
                exampointerEditInfo.find('#areaProvince_edit').children('option').first().prop("selected","selected");
                exampointerEditInfo.find("#areaCity_edit").empty();
                exampointerEditInfo.find("#areaCity_edit").prepend("<option value='' selected>请选择市</option>");
                exampointerEditInfo.find("#areaDistrict_edit").empty();
                exampointerEditInfo.find("#areaDistrict_edit").prepend("<option value='' selected>请选择县区</option>");
                totalExamNumEdit="";
                dialogRef.close();
             

            }
        }]
    });
    
    
    //考点新增按钮
    $('#btn-add').on('click',function(){
    	 exampointerAddInfo.find("#areaProvince_add").empty();
    	 exampointerAddInfo.find("#areaProvince_add").prepend("<option value='' selected>请选择省</option>");
    	 exampointerAddInfo.find("#areaCity_add").empty();
    	 exampointerAddInfo.find("#areaCity_add").prepend("<option value='' selected>请选择市</option>");
    	 exampointerAddInfo.find("#areaDistrict_add").empty();
    	 exampointerAddInfo.find("#areaDistrict_add").prepend("<option value='' selected>请选择县区</option>");
    	 exampointerAddInfo.find(".icon-connect_add").show();
        exampointerDialogAdd.setTitle("新增考点");
        exampointerDialogAdd.onShown(function(){
    		$.ajax({
 	     	   url: Util.getPath()+"/AreaController/getProvinceAll",
 	     	   type: 'post',
 	     	   dataType: 'json',
 	     	   async:false,
 	     	   data: {},
 	     	   success: function (data) {
 	     		  for (var i = 0; i < data.length; i++) {
 	     			exampointerAddInfo.find("#areaProvince_add").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
 	      		  }
 	     		  //添加完成之后触发选择事件
 		  	      exampointerAddInfo.find("#areaProvince_add").change();
 	     	   }
 	     	});
        })
        exampointerDialogAdd.open();
    });

    //考点修改图标获取考点信息
    $('#exampointerGrid').on('click','.table-operation-edit',function(){
        var areaId = null;
        var endPointId=$(this).attr("data-id");
        $(".icon-connect").show();
        $.ajax({
            method: 'post',
            url: Util.getPath()+"/EndPointController/getEndPointByEndPointId",
            dataType: 'json',
            data: {
                endPointId : endPointId
            },
            success: function(data){
            	totalExamNumEdit=data.ENDPOINT_NO;
            	areaId = data.AREA_ID;
                //区 id 所有的
            	exampointerEditInfo.find("#examID_edit").val(data.ENDPOINT_ID);
                exampointerEditInfo.find("#examName_edit").val(data.ENDPOINT_NAME);
                exampointerEditInfo.find("#examNum_edit").val(data.ENDPOINT_NO);
                exampointerEditInfo.find("#examAddress_edit").val(data.ENDPOINT_ADDR);
                exampointerEditInfo.find("#examPerson_edit").val(data.ENDPOINT_PERSON);
                exampointerEditInfo.find("#examPhone_edit").val(data.ENDPOINT_TEL);
                exampointerEditInfo.find("#examIPAddress_edit").val(data.ENDPOINT_IP);
                exampointerEditInfo.find("#examPort_edit").val(data.ENDPOINT_PORT);
                exampointerEditInfo.find("input[name='state_edit'][value='"+data.STATE+"']").prop("checked",true);
                exampointerEditInfo.find("#ord_edit").val(data.ORD);
              //区县id，通过区县Id查找市Id，再通过市Id查找省Id
                $.ajax({
                    method: 'post',
                    url: Util.getPath()+"/AreaController/getAreaInfoByAreaId",
                    dataType: 'json',
                    data: {
                        areaId : areaId
                    },
                    success: function(data){
                    	 $.ajax({
                             method: 'post',
                             url: Util.getPath()+"/AreaController/getChildAreaInfoByParentAreaId",
                             dataType: 'json',
                             data: {
                                 parentId : data.PARENT_AREA_ID
                             },
                             success: function(data0){
                            	 var districtParentId=data0.bean[0].PARENT_AREA_ID;
                                 for(var i=0;i<data0.bean.length;i++){
                                     if(data0.bean[i].AREA_ID==areaId){
                                    	 exampointerEditInfo.find("#areaDistrict_edit").append("<option value='"+data0.bean[i].AREA_ID+"' selected='selected'>"+data0.bean[i].AREA_NAME+"</option>");
                                     }else{
                                    	 exampointerEditInfo.find("#areaDistrict_edit").append("<option value='"+data0.bean[i].AREA_ID+"'>"+data0.bean[i].AREA_NAME+"</option>");
                                     }
                                 }
                                 $.ajax({
                                     method: 'post',
                                     url: Util.getPath()+"/AreaController/getAreaInfoByAreaId",
                                     dataType: 'json',
                                     data: {
                                         areaId : districtParentId
                                     },
                                     success: function(data1){
                                    	 var provinceId=data1.PARENT_AREA_ID;
                                    	 $.ajax({
                                             method: 'post',
                                             url: Util.getPath()+"/AreaController/getChildAreaInfoByParentAreaId",
                                             dataType: 'json',
                                             data: {
                                                 parentId : provinceId
                                             },
                                             success: function(data2){
                                            	 var cityParentId=data2.bean[0].PARENT_AREA_ID;
                                                 for(var i=0;i<data2.bean.length;i++){
                                                     if(data2.bean[i].AREA_ID==districtParentId){
                                                    	 exampointerEditInfo.find("#areaCity_edit").append("<option value='"+data2.bean[i].AREA_ID+"' selected='selected'>"+data2.bean[i].AREA_NAME+"</option>");
                                                     }else{
                                                    	 exampointerEditInfo.find("#areaCity_edit").append("<option value='"+data2.bean[i].AREA_ID+"'>"+data2.bean[i].AREA_NAME+"</option>");
                                                     }
                                                 }
                                                 $.ajax({
                                                     method: 'post',
                                                     url: Util.getPath()+"/AreaController/getAreaInfoByAreaId",
                                                     dataType: 'json',
                                                     data: {
                                                         areaId : cityParentId
                                                     },
                                                     success: function(data3){
                                                    	 exampointerEditInfo.find("#areaProvince_edit").empty();
                                                    	 exampointerEditInfo.find("#areaProvince_edit").append("<option value=''>请选择省</option>");
                                                    	 exampointerEditInfo.find("#areaProvince_edit").append("<option value='"+data3.AREA_ID+" ' selected='selected'>"+data3.AREA_NAME+"</option>");
                                                         exampointerDialogEdit.setTitle("修改考点");
                                                         exampointerDialogEdit.open();
                                                    	 }
                                                     });
                                            	 }
                                             });
                                    	 }
                                     });
                            	 }
                             });
                    	}
                    });
            	}	
            });
    });

   
    //表单校验
    var  validatorAdd=Util.validate('exampointerAddForm',{
        rules:{
            examName_add:{
                required:true,
                maxlength:25
            },
            examIPAddress_add:{
                required:true,
                ip:true
            },
            examPort_add:{
                required:true
            },
            examNum_add:{
            	required:true,
            	maxlength:25
            },
            examAddress_add:{
            	maxlength:50
            },
            examPerson_add:{
            	maxlength:50
            },
            examPhone_add:{
            	maxlength:50
            },
            state_add:{
                required:true
            },
            ord_add:{
                digits:true,
                maxlength:11
            }
        },
        messages:{
            examName_add:{
                required:"请输入考点名称"
            },
            examNum_add:{
            	required:"请点击右方按钮自动获取考点编号/手动输入考点编号"
            },
            examIPAddress_add:{
                required:"请输入考点服务器IP"
            },
            examPort_add:{
                required:"请输入端口号"
            },
            state_add:{
            	required:"请选择状态"
            },
            ord_add:{
                digits:"请输入正确的顺序号",
                maxlength:"最大长度为11位"
            }
        }
    });
	
    //表单校验
    var  validatorEdit=Util.validate('exampointerEditForm',{
        rules:{
            examName_edit:{
                required:true,
                maxlength:25
            },
            examIPAddress_edit:{
                required:true,
                ip:true
            },
            examPort_edit:{
                required:true
            },
            examNum_edit:{
            	required:true,
            	maxlength:25
            },
            examAddress_edit:{
            	maxlength:50
            },
            examPerson_edit:{
            	maxlength:50
            },
            examPhone_edit:{
            	maxlength:50
            },
            state_edit:{
                required:true
            },
            ord_edit:{
                digits:true,
                maxlength:11
            }
        },
        messages:{
            examName_edit:{
                required:"请输入考点名称"
            },
            examNum_edit:{
            	required:"请点击右方按钮自动获取考点编号/手动输入考点编号"
            },
            examIPAddress_edit:{
                required:"请输入考点服务器IP"
            },
            examPort_edit:{
                required:"请输入端口号"
            },
            state_edit:{
            	required:"请选择状态"
            },
            ord_edit:{
                digits:"请输入正确的顺序号",
                maxlength:"最大长度为11位"
            }
        }
    });
    
    
	//给省份的选择框添加事件
	$("#province").change(function(){
		//获取选择的省份id
		var parentAreaId = $(this).val();
		$.ajax({
     	   url: Util.getPath()+"/AreaController/getCityAll",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {
     		  parentAreaId:parentAreaId
     	   },
     	   success: function (data) {
     		   $("#city").empty();
     		   $("#city").append("<option value='' selected>请选择市</option>");
     		 for (var i = 0; i < data.length; i++) {
     			 $("#city").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
     		  }
     		 //添加完成之后触发选择事件
	  		  $("#city").change();
     	   }
     	});
	});
	
	//给地级市的选择框添加事件
	$("#city").change(function(){
		//获取选择的地级市id
		var parentAreaId = $("#city").val();
		$.ajax({
     	   url: Util.getPath()+"/AreaController/getDistrictAll",
     	   type: 'post',
     	   dataType: 'json',
     	   async:false,
     	   data: {
     		  parentAreaId:parentAreaId
     	   },
     	   success: function (data) {
     		   $("#district").empty();
     		   $("#district").append("<option value='' selected>请选择县区</option>");
     		   if(null != data && data != undefined && data != ""){
     			  var i = 0;
     			  for (i = 0; i < data.length ; i++) {
          			 $("#district").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
          		  }
     		   }
     	   }
     	});
	});

    //给省份的选择框添加事件
	exampointerAddInfo.find("#areaProvince_add").change(function(){
        //获取选择的省份id
        var parentAreaId = $(this).val();
        $.ajax({
            url: Util.getPath()+"/AreaController/getCityAll",
            type: 'post',
            dataType: 'json',
            data: {
                parentAreaId:parentAreaId
            },
            success: function (data) {
            	exampointerAddInfo.find("#areaCity_add").empty();
            	exampointerAddInfo.find("#areaCity_add").append("<option value='' selected>请选择市</option>");
                for (var i = 0; i < data.length; i++) {
                	exampointerAddInfo.find("#areaCity_add").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
                }
                //添加完成之后触发选择事件
                exampointerAddInfo.find("#areaCity_add").change();
            }
        });
    });

    //给地级市的选择框添加事件
	exampointerAddInfo.find("#areaCity_add").change(function(){
        //获取选择的地级市id
        var parentAreaId = exampointerAddInfo.find("#areaCity_add").val();
        $.ajax({
            url: Util.getPath()+"/AreaController/getDistrictAll",
            type: 'post',
            dataType: 'json',
            data: {
                parentAreaId:parentAreaId
            },
            success: function (data) {
            	exampointerAddInfo.find("#areaDistrict_add").empty();
            	exampointerAddInfo.find("#areaDistrict_add").append("<option value='' selected>请选择县区</option>");
                if(null != data && data != undefined && data != ""){
                    var i = 0;
                    for (i = 0; i < data.length ; i++) {
                    	exampointerAddInfo.find("#areaDistrict_add").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
                    }
                }
            }
        });
    });

    //给省份的选择框添加事件
	exampointerEditInfo.find("#areaProvince_edit").change(function(){
        //获取选择的省份id
        var parentAreaId = $(this).val();
        $.ajax({
            url: Util.getPath()+"/AreaController/getCityAll",
            type: 'post',
            dataType: 'json',
            data: {
                parentAreaId:parentAreaId
            },
            success: function (data) {
            	exampointerEditInfo.find("#areaCity_edit").empty();
            	exampointerEditInfo.find("#areaCity_edit").append("<option value='' selected>请选择市</option>");
                for (var i = 0; i < data.length; i++) {
                	exampointerEditInfo.find("#areaCity_edit").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
                }
                //添加完成之后触发选择事件
                exampointerEditInfo.find("#areaCity_edit").change();
            }
        });
    });

    //给地级市的选择框添加事件
	exampointerEditInfo.find("#areaCity_edit").change(function(){
		debugger
        //获取选择的地级市id
        var parentAreaId = exampointerEditInfo.find("#areaCity_edit").val();
        $.ajax({
            url: Util.getPath()+"/AreaController/getDistrictAll",
            type: 'post',
            dataType: 'json',
            data: {
                parentAreaId:parentAreaId
            },
            success: function (data) {
            	exampointerEditInfo.find("#areaDistrict_edit").empty();
            	exampointerEditInfo.find("#areaDistrict_edit").append("<option value='' selected>请选择县区</option>");
                if(null != data && data != undefined && data != ""){
                    var i = 0;
                    for (i = 0; i < data.length ; i++) {
                    	exampointerEditInfo.find("#areaDistrict_edit").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
                    }
                }
            }
        });
    });


    //点击删除图标进行删除
    $('#exampointerGrid').on('click','.table-operation-del',function(){
        var id = $(this).attr('data-id');
        Util.questionDialog('确定删除数据？',function(dialog, result){
            if(result==true){
                $.ajax({
                    method:"post",
                    url:Util.getPath()+"/EndPointController/deleteEndPointInfoByEndPointId",
                    dataType:"json",
                    data:{
                        endPointId:id
                    },
                    success:function(data){
                        if(data.success){
                            Util.promptSuccessDialog(data.bean);
                            gridTable.table('refresh');
                            dialog.close();
                        }else{
                            Util.warningDialog(data.bean);
                            dialog.close();
                        }
                    }
                });
            }else{
                dialog.close();
            }
        });
    });

    //点击删除按钮进行删除
    $("#btn-delete").click(function(){
        var items = gridTable.table('getSelections');
        if(items.length==0){
            Util.warningDialog('尚未选择数据，请选择！');
        }else{
            var idsArray=new Array();
            for(var i=0;i<items.length;i++){
                idsArray.push(items[i].ENDPOINT_ID);
            }
            Util.questionDialog('确定删除数据？',function(dialog, result){
                if(result){
                    $.ajax({
                        method:"post",
                        url:Util.getPath()+"/EndPointController/deleteEndPointInfoByEndPointId",
                        dataType:"json",
                        data:{
                            endPointId:idsArray.toString(),
                        },
                        success:function(data){
                            if(data.success){
                                Util.promptSuccessDialog(data.bean);
                                gridTable.table('refresh');
                                dialog.close();
                            }else{
                                Util.warningDialog(data.bean);
                                dialog.close();
                            }
                        }
                    });
                }
                dialog.close();
            });
        }
    });

    //新增表单获取考点编号按钮
    exampointerAddInfo.find("#test-connect-add").tooltip();
    exampointerAddInfo.find("#icon-connect-add").click(function(){
    	getPointerNum("add");
    });
  //编辑表单获取考点编号按钮
    exampointerEditInfo.find("#test-connect-edit").tooltip();
    exampointerEditInfo.find("#icon-connect-edit").click(function(){
    	getPointerNum("edit");
    });
    
    function getPointerNum(type){
		if(type=="add"){
	        if(exampointerAddInfo.find("#examIPAddress_"+type).val() == ""){
	            Util.warningDialog("请输入考点服务器IP");
	            return ;
	        }
	        if(exampointerAddInfo.find("#examPort_"+type).val() == ""){
	            Util.warningDialog("请输入考点服务器端口");
	            return ;
	        }
	        var $this = $(this);
	        $this.next().show();
			$this.hide();
	        $.ajax({
	            method:"post",
	            timeout:"3000",
	            url:Util.getPath() + "/EndPointController/getEndPointNo",
	            dataType:"json",
	            data:{
	                endPointIPAddr : exampointerAddInfo.find("#examIPAddress_"+type).val(),
	                endPointPort : exampointerAddInfo.find("#examPort_"+type).val()
	            },
	            success:function(data){
	            	debugger
	            	$this.next().hide();
	     			$this.show();
	                if(data.success==true){
	                	exampointerAddInfo.find("#examNum_"+type).val(data.bean);
	                    Util.promptSuccessDialog("获取考点编号成功！");
	                    exampointerAddInfo.find("#examNum_"+type).attr("readonly","readonly");
	                }else{
	                    Util.warningDialog("获取失败，请手动输入！");
	                    exampointerAddInfo.find("#examNum_"+type).removeAttr("readonly");
	                }
	            },
	          　　		complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
	        	　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
		             	$this.next().hide();
		     			$this.show();
		             	Util.warningDialog("请求超时，请重试或手动录入！");
	        	　　　　}
	        		 if(status=="error"){
	                 	$this.next().hide();
	         			$this.show();
	                 	Util.warningDialog("获取失败，请手动输入！");
	        		 }
	        	　　}
	        });
		}else{
	        if(exampointerEditInfo.find("#examIPAddress_"+type).val() == ""){
	            Util.warningDialog("请输入考点服务器IP");
	            return ;
	        }
	        if(exampointerEditInfo.find("#examPort_"+type).val() == ""){
	            Util.warningDialog("请输入考点服务器端口");
	            return ;
	        }
	        var $this = $(this);
	        $this.next().show();
			$this.hide();
	        $.ajax({
	            method:"post",
	            timeout:"3000",
	            url:Util.getPath() + "/EndPointController/getEndPointNo",
	            dataType:"json",
	            data:{
	                endPointIPAddr : exampointerEditInfo.find("#examIPAddress_"+type).val(),
	                endPointPort : exampointerEditInfo.find("#examPort_"+type).val()
	            },
	            success:function(data){
	            	$this.next().hide();
	     			$this.show();
	                if(data.success==true){
	                	exampointerEditInfo.find("#examNum_"+type).val(data.bean);
	                    Util.promptSuccessDialog("获取考点编号成功！");
	                    exampointerEditInfo.find("#examNum_"+type).attr("readonly","readonly");
	                }else{
	                    Util.warningDialog("获取失败，请手动输入！");
	                    exampointerEditInfo.find("#examNum_"+type).removeAttr("readonly");
	                }
	            },
	          　　		complete : function(XMLHttpRequest,status){ //请求完成后最终执行参数
	        	　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
		             	$this.next().hide();
		     			$this.show();
		             	Util.warningDialog("请求超时，请重试或手动录入！");
	        	　　　　}
	        		 if(status=="error"){
	                 	$this.next().hide();
	         			$this.show();
	                 	Util.warningDialog("获取失败，请手动输入！");
	        		 }
	        	　　}
	        });
		}

    	
    }
    
    
  //考点信息初始化弹出框
    var endPointInfo=$("#endPointInfo");
	var endPointImportDialog = new bootstrapDialog({
		type:'type-default',
		title:'考点信息',
        message:$('#endpointimport').children(),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy:false,
        closable: false, //控制弹出框拉右上角是否显示 ‘x' 默认为true
        cssClass:'endpointimport-dialog',
        onshown : function(dialogRef){
        	//模态框加载前事件
        },
        buttons: [{
        	id:"savebtn",
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',    
            cssClass:'btn-success btn-sm ',
            action: function(dialogRef){
            	$.ajaxFileUpload({
        		   url: Util.getPath() + "/EndPointController/uploadTemplet",//用于文件上传的服务器端请求地址
        		   type: 'post',
        		   data: { ProdcutID: 1 },  
        		   secureuri: false,       //是否需要安全协议，一般设置为false
        		   fileElementId: ['file'],//文件上传域的ID 在这里设置要上传的多个input的ID
        		   dataType: 'json',       //返回值类型 一般设置为json
        		   success: function (data){ 
        			   endPointInfo[0].reset();
        			   if (data.success) {
           			   		endPointImportDialog.close();
  			        		Util.promptSuccessDialog(data.bean);
  			        		gridTable.table('refresh');
  			        	} else {
  			        		endPointImportDialog.close();
  			        		Util.warningDialog(data.bean);
  			        	}
        		   },
        		   error: function (data, status, e){//服务器响应失败处理函数
        			   var text = JSON.parse(data.responseText).bean;
        			   if(undefined != text && ""!= text){
        				   Util.warningDialog(text);
        			   }
        			   endPointInfo[0].reset();
        			   endPointImportDialog.close();
        		   }
        		});
            }
        },{
            label: '取消',
            autospin: false,
            icon:'glyphicon glyphicon-remove',
            cssClass:'btn-default btn-sm',
            action: function(dialogRef){
            	endPointInfo[0].reset();
            	endPointImportDialog.close();
            }
        }]
    });
    
	
	//手动导入 按钮事件
	$("#btn-import").on("click",function(){
		endPointImportDialog.open();
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
	
	//保存表单
	function addPointerForm(endPointId){
		$.ajax({
            method: 'post',
            url: Util.getPath()+"/EndPointController/saveEndPointInfo",
            dataType: 'json',
            data: {
                endPointId:endPointId,
                endPointName:exampointerAddInfo.find("#examName_add").val(),
                endPointNum:exampointerAddInfo.find("#examNum_add").val(),
                endPointAreaId:exampointerAddInfo.find("#areaDistrict_add").val(),
                endPointAddr:exampointerAddInfo.find("#examAddress_add").val(),
                endPointPerson:exampointerAddInfo.find("#examPerson_add").val(),
                endPointTel:exampointerAddInfo.find("#examPhone_add").val(),
                endPointIPAddr:exampointerAddInfo.find("#examIPAddress_add").val(),
                endPointPort:exampointerAddInfo.find("#examPort_add").val(),
                state:exampointerAddInfo.find("input[name='state_add']:checked").val(),
                ord:exampointerAddInfo.find("#ord_add").val()
            },
            success: function(data){
                if (data.success) {
                    Util.promptSuccessDialog(data.bean);
                } else {
                    Util.warningDialog(data.bean);
                }
                exampointerAddInfo.find('#areaProvince_add').children('option').first().attr("selected","selected");
                exampointerAddInfo.find("#areaCity_add").empty();
                exampointerAddInfo.find("#areaCity_add").prepend("<option value='' selected>请选择市</option>");
                exampointerAddInfo.find("#areaDistrict_add").empty();
                exampointerAddInfo.find("#areaDistrict_add").prepend("<option value='' selected>请选择县区</option>");
                exampointerAddInfo[0].reset();
                validatorAdd.resetForm();
                gridTable.table('refresh');
                exampointerDialogAdd.close();
            }
        });
	}
	
	
	//保存表单
	function editPointerForm(endPointId){
		$.ajax({
            method: 'post',
            url: Util.getPath()+"/EndPointController/saveEndPointInfo",
            dataType: 'json',
            data: {
                endPointId:endPointId,
                endPointName:exampointerEditInfo.find("#examName_edit").val(),
                endPointNum:exampointerEditInfo.find("#examNum_edit").val(),
                endPointAreaId:exampointerEditInfo.find("#areaDistrict_edit").val(),
                endPointAddr:exampointerEditInfo.find("#examAddress_edit").val(),
                endPointPerson:exampointerEditInfo.find("#examPerson_edit").val(),
                endPointTel:exampointerEditInfo.find("#examPhone_edit").val(),
                endPointIPAddr:exampointerEditInfo.find("#examIPAddress_edit").val(),
                endPointPort:exampointerEditInfo.find("#examPort_edit").val(),
                state:exampointerEditInfo.find("input[name='state_edit']:checked").val(),
                ord:exampointerEditInfo.find("#ord_edit").val()
            },
            success: function(data){
                if (data.success) {
                    Util.promptSuccessDialog(data.bean);
                } else {
                    Util.warningDialog(data.bean);
                }
                exampointerEditInfo.find('#areaProvince_edit').children('option').first().attr("selected","selected");
                exampointerEditInfo.find("#areaCity_edit").empty();
                exampointerEditInfo.find("#areaCity_edit").prepend("<option value='' selected>请选择市</option>");
                exampointerEditInfo.find("#areaDistrict_edit").empty();
                exampointerEditInfo.find("#areaDistrict_edit").prepend("<option value='' selected>请选择县区</option>");
                exampointerEditInfo[0].reset();
                validatorEdit.resetForm();
                totalExamNumEdit="";
                gridTable.table('refresh');
                exampointerDialogEdit.close();
            }
        });
	}

});