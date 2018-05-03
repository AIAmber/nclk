require(['jquery', 'bootstrap-grid', 'Util', 'table-toolbar', 'bootstrap-dialog', 'ztree','jquery-placeholder'], function ($, bootstrapGrid, Util, toolbar, bootstrapDialog, ztree,placeholder) {
    //记录修改表单时回填到表单的用户名
	var editUserName="";
	$(function(){
		//判断该登录用户是否具备查看此菜单的权限，没有则跳转首页
		$.ajax({
	     	   url: Util.getPath()+"/EndPointController/identityAuthentication",
	     	   method: 'post',
	     	   dataType: 'text',
	     	   async:true,
	     	   data: {moduleId:"d9a309ac2a95406dbd57cdbd520c70bb"},
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
		//添加ie8下placeholder的支持
		if($("#searchText").val() == ""){
			$("#searchText").placeholder();
		}
	});
	//用户管理 全部 有效 无效按钮事件
	$("#btn-group-tab").on("click","a",function(){
		$(".active"). removeClass("active");
		$(this).addClass("active");
    		
		var state = $(this).html();
		var stateValue = "A";
        if("有效" == state){
        	stateValue = "A";
        }else if("无效" == state){
        	stateValue = "X";
        }else if("锁定" == state){
        	stateValue = "L";
        }else{
        	stateValue = "";
        }
        
		gridTable.resetSearch({
			"state" : stateValue
		});
	});
	
	
	//创建了一个用户对象
	USER = {};
	
	$(".table-toolbar").toolbar({
		 searchEvent: function () {
	            var searchText = $("#searchText").val();
	            var state = $("#btn-group-tab").find(".active").html();
	    		var stateValue = "A";
	            if("有效" == state){
	            	stateValue = "A";
	            }else if("无效" == state){
	            	stateValue = "X";
	            }else if("锁定" == state){
	            	stateValue = "L";
	            }else{
	            	stateValue = "";
	            }
	            gridTable.resetSearch({
	                "searchText": searchText,
	                "state" : stateValue
	            });
		 }
	    });
	//用户列表
    var gridTable = $('#userGrid').bootstrapGrid({
        url: Util.getPath() + '/UserController/findUsers',
        uniqueId: 'USER_ID',
        columns: [{
            checkbox: true
        }, {
            field: 'NAME',
            title: '姓名'
        }, {
            field: 'USERNAME',
            title: '用户名'
        }, {
            field: 'authority',
            title: '权限',
            formatter: function (value, row, index) {
                return "<a data-id="+row.USER_ID+" class='authoritySetting' href='javascript:void(0)'>权限设置</a>";
            }
        }, {
            field: 'PASSWORD',
            title: '密码',
            formatter: function (value, row, index) {
                return "<a  data-id="+row.USER_ID+" class='changePassword' href='javascript:void(0)'>密码修改</a>";
            }
        }, {
            field: 'manageArea',
            title: '管辖区域',
            formatter: function (value, row, index) {
                return "<a data-id="+row.USER_ID+" class='manageArea' href='javascript:void(0)'>管辖区域设置</a>";
            }
        }, {
	    	field: 'STATE',
	        title: '状态',
	    	formatter:function(value,row,index){
	    		if(value == "A"){
	    			return "有效";
	    		}else if(value == "X"){
	    			return "<font class='red'>无效</font>";
	    		}else if(value == "L"){
	    			return "<font class='red'>锁定</font>";
	    		}  
	        }
        }, {
            field: 'USER_ID',
            title: '操作',
            formatter: function (value, row, index) {
            	if(row.STATE=='X'){
            		return [
                        '<a data-id="'+row.USER_ID+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
                        '<a data-id="' + value + '" class="table-delete table-operation table-operation-del fa fa-trash-o"style="opacity: 0;filter: alpha(opacity = 0);" href="javascript:void(0)" title="删除"></a>'
                    ].join('');
            	}else{
            		 return [
                         '<a data-id="'+row.USER_ID+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
                         '<a data-id="' + value + '" class="table-delete table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
                     ].join('');
            	}
               
            }
        }]
    });
    
    var setting_authority = {
            view: {
                dblClickExpand: false,
                showIcon: false,
                fontCss: {
                    "text-decoration": "none"
                }
            },
            async: {
                enable: true,
                url: Util.getPath() + "/UserController/getModuleInfo",
                dataFilter: function (treeId, parentNode, childNodes) {
                    return childNodes.rows;
                }
            },
            callback:{
            	beforeClick: function () {
                    return false;
                },
                onAsyncSuccess: function (event, treeId, treeNode, msg) {
                    $.fn.zTree.getZTreeObj(treeId).expandAll(true);
                }
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType: {"Y": "ps", "N": "ps"}
            },
        };
    //新增用户信息权限树
    var treeObj= $.fn.zTree.init($('#authority-treeContent'), setting_authority);
    //权限设置 弹出框 树 
    var tree =  $.fn.zTree.init($('#authority-treeContentDialog'), setting_authority);
    //修改用户权限树
    var treeEdit=$.fn.zTree.init($('#authority-treeContent-edit'), setting_authority);
    
    
    //管辖区域树
    var setting = {
        view: {
            dblClickExpand: false,
            showIcon: false,
            fontCss: {
                "text-decoration": "none"
            }
        },
        async: {
            enable: true,
            url: Util.getPath() + "/AreaController/getTreeEndPointInfo",
            dataFilter: function (treeId, parentNode, childNodes) {
                return childNodes.rows;
            }
        },
        callback:{
        	beforeClick: function () {
                return false;
            }
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        check: {
            enable: true,
            chkStyle: "checkbox",
            chkboxType: {"Y": "ps", "N": "ps"}
        },
    };
    //新增用户树
   var manageAreaTree= $.fn.zTree.init($('#manageArea-treeContent'), setting);
   //管辖区域弹出框树
   var manageAreaDialogTree= $.fn.zTree.init($('#manageArea-treeDialogContent'), setting);
   //修改用户树
   var manageAreaTreeEdit= $.fn.zTree.init($('#manageArea-treeContent-edit'), setting);
    
    //用户管理表单校验
    var validator = Util.validate("userAddForm", {
        rules: {
        	perName: {
        		maxlength:25,
        		required: true
        	},
            userName: {
                required: true,
                maxlength:25
            },
            addPassword: {
                required: true,
                minlength:6,
                maxlength:50
            },
            addConfirmPassword: {
                required: true,
                equalTo: "#addPassword"
            },
            ord:{
                digits:true,
                maxlength:11
            }
        },
        messages: {
        	perName: {
        		 required: "请输入姓名"
        	},
        	userName: {
                required: "请输入用户名"
            },
            addPassword: {
                required: "请输入密码",
                minlength:"至少输入6位",
            },
            addConfirmPassword: {
                required: "请输入确认密码",
                equalTo: "两次输入密码不一致"
            },
            ord:{
                digits:"请输入正确的顺序号",
                maxlength:"最大长度为11位"
            }
        }
    });
    
    
  //用户管理表单校验修改
    var validatorEdit = Util.validate("userEditForm", {
        rules: {
        	perName_edit: {
        		required: true,
        		maxlength:25
        	},
            userName_edit: {
                required: true,
                maxlength:25
            },
            addPassword_edit: {
                required: true,
                minlength:6,
                maxlength:50
            },
            addConfirmPassword_edit: {
                required: true,
                equalTo: "#addPassword"
            },
            ord_edit:{
                digits:true,
                maxlength:11
            }
        },
        messages: {
        	perName_edit: {
       		 required: "请输入姓名"
        	},
        	userName_edit: {
                required: "请输入用户名"
            },
            addPassword_edit: {
                required: "请输入密码",
                minlength:"至少输入6位",
            },
            addConfirmPassword_edit: {
                required: "请输入确认密码",
                equalTo: "两次输入密码不一致"
            },
            ord_edit:{
                digits:"请输入正确的顺序号",
                maxlength:"最大长度为11位"
            }
        }
    });
    
    //密码修改校验
    var changePasswordForm=$("#changePasswordForm");
    var changeValidator=Util.validate("changePasswordForm",{
    	rules: {
            newPassword: {
            	required: true,
            	rangelength:[6,50]
            },
            confirmPassword: {
                required: true,
                equalTo: "#newPassword"
            }
        },
        messages: {
            newPassword: {
            	required: "请输入新密码"
            },
            confirmPassword: {
                required: "请输入确认密码",
                equalTo: "两次输入密码不一致"
            }
        }
    	
    });
  //用户新增
    var userAddForm =$('#userAddForm');
    var userDialog = new bootstrapDialog({
        type: 'type-default',
        message: userAddForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        closable:false,
        cssClass: 'userAddDialog',
        buttons: [{
            id: 'userBtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
                if (!userAddForm.valid()) {
                    validator.focusInvalid();
                }else{
                	var userId=$("#userId").val();
                	var authNodes = treeObj.getCheckedNodes();
                	var idsArray=new Array();
                	for (var int = 0; int < authNodes.length; int++) {
                		idsArray.push(authNodes[int].id);
    				}
                	
                	var areaNodes = manageAreaTree.getCheckedNodes();
                	var areaIdsArray=new Array();
                	var endPointIdsArray=new Array();
                	for (var int = 0; int < areaNodes.length; int++) {
                		if(areaNodes[int].level<3){
                			areaIdsArray.push(areaNodes[int].id);
                		}else{
                			endPointIdsArray.push(areaNodes[int].id);
                		}
    				}
                	
            		$.ajax({
                		method:"post",
                		url:Util.getPath()+"/UserController/findUsername",
                		dataType:"json",
                		data:{
                			userId:userAddForm.find("#userId").val(),
                			userName:userAddForm.find("#userName").val()
                		},
                		success:function(data){
                			//新增用户名检验是否存在
                			if(data==0){
                				$.ajax({
                            		method:"post",
                            		url:Util.getPath()+"/UserController/saveUser",
                            		dataType:"json",
                            		data:{
                            			userId:userAddForm.find("#userId").val(),
                            			perName:userAddForm.find("#perName").val(),
                            			userName:userAddForm.find("#userName").val(),
                            			password:userAddForm.find("#addPassword").val(),
                            			state:userAddForm.find("input[name='state']:checked").val(),
                            			ord:userAddForm.find("#ord").val(),
                            			photoPath:userAddForm.find("[name='photofilePath']").val(),
                            			moduleId:idsArray.toString(),
                            			areaId:areaIdsArray.toString(),
                            			endPointId:endPointIdsArray.toString(),
                            		},
                            		success:function(data){
                            			if(data.success){
                            				Util.promptSuccessDialog(data.bean);
                            			}else{
                            				Util.promptSuccessDialog(data.bean);
                            			}
                            			userAddForm[0].reset();
                            			validator.resetForm();
                                    	treeObj.checkAllNodes(false);
                                    	manageAreaTree.checkAllNodes(false);
                                    	gridTable.table('refresh');
                                    	dialogRef.close();
                            		}
                            	});
                			}else{
                				Util.promptNoticeDialog("用户名已存在，请重新输入");
                			}
                		}
                	});
                }
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
            	userAddForm[0].reset();
            	validator.resetForm();
            	treeObj.checkAllNodes(false);
            	manageAreaTree.checkAllNodes(false);
            	dialogRef.close();
            }
        }]
    });
    
    
    //新增用户按钮
    $('#btn-add').on("click", function () {
    	userDialog.onShown(function(){
    		treeObj.checkAllNodes(false);
        	userAddForm.find("#preview").attr("src",Util.getPath()+"/common/img/head.png");
		});
        userDialog.setTitle("新增用户");
        userDialog.open();
       
    });
    
  //用户修改
    var userEditForm =$('#userEditForm');
    var userDialogEdit = new bootstrapDialog({
        type: 'type-default',
        message: userEditForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        closable:false,
        cssClass: 'userAddDialog',
        buttons: [{
            id: 'userBtn',
            label: '提交',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
                if (!userEditForm.valid()) {
                    validatorEdit.focusInvalid();
                }else{
                	var userId=userEditForm.find("#userId_edit").val();
                	var authNodes = treeEdit.getCheckedNodes();
                	var idsArray=new Array();
                	for (var int = 0; int < authNodes.length; int++) {
                		idsArray.push(authNodes[int].id);
    				}
                	
                	var areaNodes = manageAreaTreeEdit.getCheckedNodes();
                	var areaIdsArray=new Array();
                	var endPointIdsArray=new Array();
                	for (var int = 0; int < areaNodes.length; int++) {
                		if(areaNodes[int].level<3){
                			areaIdsArray.push(areaNodes[int].id);
                		}else{
                			endPointIdsArray.push(areaNodes[int].id);
                		}
    				}
                	//当用户名不做修改时直接保存表单
                	if(editUserName==userEditForm.find("#userName_edit").val()){
	            		$.ajax({
	                		method:"post",
	                		url:Util.getPath()+"/UserController/saveUser",
	                		dataType:"json",
	                		data:{
	                			userId:userEditForm.find("#userId_edit").val(),
	                			perName:userEditForm.find("#perName_edit").val(),
	                			userName:userEditForm.find("#userName_edit").val(),
	                			password:userEditForm.find("#addPassword_edit").val(),
	                			state:userEditForm.find("input[name='state_edit']:checked").val(),
	                			ord:userEditForm.find("#ord_edit").val(),
	                			photoPath:userEditForm.find("[name='photofilePath_edit']").val(),
	                			moduleId:idsArray.toString(),
	                			areaId:areaIdsArray.toString(),
	                			endPointId:endPointIdsArray.toString(),
	                		},
	                		success:function(data){
	                			if(data.success){
	                				Util.promptSuccessDialog(data.bean);
	                			}else{
	                				Util.promptSuccessDialog(data.bean);
	                			}
	                			userEditForm[0].reset();
	                			validatorEdit.resetForm();
	                        	treeEdit.checkAllNodes(false);
	                        	manageAreaTreeEdit.checkAllNodes(false);
	                        	gridTable.table('refresh');
	                        	dialogRef.close();
	                		}
	                	});
                	}else{
                		$.ajax({
                    		method:"post",
                    		url:Util.getPath()+"/UserController/findUsername",
                    		dataType:"json",
                    		data:{
                    			userId:"",
                    			userName:userEditForm.find("#userName_edit").val()
                    		},
                    		success:function(data){
                    			//新增用户名检验是否存在
                    			if(data==0){
            	            		$.ajax({
            	                		method:"post",
            	                		url:Util.getPath()+"/UserController/saveUser",
            	                		dataType:"json",
            	                		data:{
            	                			userId:userEditForm.find("#userId_edit").val(),
            	                			perName:userEditForm.find("#perName_edit").val(),
            	                			userName:userEditForm.find("#userName_edit").val(),
            	                			password:userEditForm.find("#addPassword_edit").val(),
            	                			state:userEditForm.find("input[name='state_edit']:checked").val(),
            	                			ord:userEditForm.find("#ord_edit").val(),
            	                			photoPath:userEditForm.find("[name='photofilePath_edit']").val(),
            	                			moduleId:idsArray.toString(),
            	                			areaId:areaIdsArray.toString(),
            	                			endPointId:endPointIdsArray.toString(),
            	                		},
            	                		success:function(data){
            	                			if(data.success){
            	                				Util.promptSuccessDialog(data.bean);
            	                			}else{
            	                				Util.promptSuccessDialog(data.bean);
            	                			}
            	                			userEditForm[0].reset();
            	                			validatorEdit.resetForm();
            	                        	treeEdit.checkAllNodes(false);
            	                        	manageAreaTreeEdit.checkAllNodes(false);
            	                        	gridTable.table('refresh');
            	                        	dialogRef.close();
            	                		}
            	                	});
                    			}else{
                    				Util.promptNoticeDialog("用户名已存在，请重新输入");
                    			}
                    		}
                    	});
                	}
                }
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
            	userEditForm[0].reset();
            	validatorEdit.resetForm();
            	treeEdit.checkAllNodes(false);
            	manageAreaTreeEdit.checkAllNodes(false);
            	dialogRef.close();
            }
        }]
    });
    
    
    //修改用户图标
   $("#userGrid").on("click",".table-operation-edit",function(){
	  $("#passwordInput_edit").hide();
	  $("#confirmPasswordInput_edit").hide();
	  var userId=$(this).attr("data-id");
	   $.ajax({
   		method:"post",
   		url:Util.getPath()+"/UserController/findUserInfoByUserId",
   		dataType:"json",
   		data:{
   			userId:userId,
   		},
   		success:function(data){
   			editUserName=data.USERNAME;
			if(data.STATE=="L"){
	            Util.questionDialog('该用户已锁定,是否先进行解锁?', function (dialog, result) {
	                if (result) {
	                	$.ajax({
	                		method:"post",
	                		url:Util.getPath()+"/UserController/updateUserState",
	                		dataType:"json",
	                		data:{
	                			userId:userId,
	                		},
	                		success:function(){
	                			gridTable.table('refresh');
	                			data.STATE="A";
	                			if(data.STATE==""){
	               					data.STATE="X";
	               				}
	               				userDialogEdit.onShown(function(){
	               	   				userEditForm.find("#userId_edit").val(data.USER_ID);
	               	   				userEditForm.find("#perName_edit").val(data.NAME);
	               	   				userEditForm.find("#userName_edit").val(data.USERNAME);
	               	   				userEditForm.find("input[name='state_edit'][value='"+data.STATE+"']").prop("checked",true);
	               	   				userEditForm.find("#preview_edit").attr("src",Util.getPath()+"/UserController/findUserPhotoById?userId="+userId);
	               	   				userEditForm.find("#ord_edit").val(data.ORD);
	               	   				$.ajax({
	               	   	        		method:"post",
	               	   	        		url:Util.getPath()+"/UserController/getUserModuleInfo",
	               	   	        		dataType:"json",
	               	   	        		data:{
	               	   	        			userId:userId
	               	   	        		},
	               	   	        		success:function(data1){
	               	   	        			if(null != data && undefined != data1){
	               	   	        				for (var int = 0; int < data1.length; int++) {
	               	   	        					var node = treeEdit.getNodeByParam("id",data1[int].MODULE_ID,null);
	               	   	        					treeEdit.selectNode(node);
	               	   	        					treeEdit.checkNode(node, true, false);
	               	   							}
	               	   	        			}
	               	   	        		}
	               	   	        	});
	               	   				$.ajax({
	               	   	        		method:"post",
	               	   	        		url:Util.getPath()+"/UserController/getUserManageAreaInfo",
	               	   	        		dataType:"json",
	               	   	        		data:{
	               	   	        			userId:userId
	               	   	        		},
	               	   	        		success:function(data2){
	               	   	        			if(null != data2 && undefined != data2){
	               	   	        				for (var int = 0; int < data.length; int++) {
	               	   	        					var node = manageAreaTreeEdit.getNodeByParam("id",data2[int].AREA_ID,null);
	               	   	        					manageAreaTreeEdit.selectNode(node);
	               	   	        					manageAreaTreeEdit.checkNode(node, true, false);
	               	   							}
	               	   	        			}
	               	   	        			$.ajax({
	               	   	                		method:"post",
	               	   	                		url:Util.getPath()+"/UserController/getUserManageEndpointInfo",
	               	   	                		dataType:"json",
	               	   	                		data:{
	               	   	                			userId:userId
	               	   	                		},
	               	   	                		success:function(data3){
	               	   	                			if(null != data3 && undefined != data3){
	               	   	                				for (var int = 0; int < data3.length; int++) {
	               	   	                					var nodes = manageAreaTreeEdit.getNodeByParam("id",data3[int].ENDPOINT_ID,null);
	               	   	                					manageAreaTreeEdit.selectNode(nodes);
	               	   	                					manageAreaTreeEdit.checkNode(nodes, true, false);
	               	   	        						}
	               	   	                			}
	               	   	                		}
	               	   	        			});
	               	   	        		}	
	               	   	        	});
	               				});
               				   dialog.close();
                  			   userDialogEdit.setTitle("修改用户");
                  			   userDialogEdit.open();
	                		},
	                		error:function(){
	            				Util.promptSuccessDialog("解锁用户信息失败！");
	            				dialog.close();
	            				gridTable.table('refresh');
	                		}
	                	});
	                }else{
	                	 dialog.close();
	                }
	            });
			}else{
   				if(data.STATE==""){
   					data.STATE="X";
   				}
   				userDialogEdit.onShown(function(){
   	   				userEditForm.find("#userId_edit").val(data.USER_ID);
   	   				userEditForm.find("#perName_edit").val(data.NAME);
   	   				userEditForm.find("#userName_edit").val(data.USERNAME);
   	   				userEditForm.find("input[name='state_edit'][value='"+data.STATE+"']").prop("checked",true);
   	   				userEditForm.find("#preview_edit").attr("src",Util.getPath()+"/UserController/findUserPhotoById?userId="+userId);
   	   				userEditForm.find("#ord_edit").val(data.ORD);
   	   				$.ajax({
   	   	        		method:"post",
   	   	        		url:Util.getPath()+"/UserController/getUserModuleInfo",
   	   	        		dataType:"json",
   	   	        		data:{
   	   	        			userId:userId
   	   	        		},
   	   	        		success:function(data1){
   	   	        			if(null != data1 && undefined != data1){
   	   	        				for (var int = 0; int < data1.length; int++) {
   	   	        					var node = treeEdit.getNodeByParam("id",data1[int].MODULE_ID,null);
   	   	        					treeEdit.selectNode(node);
   	   	        					treeEdit.checkNode(node, true, false);
   	   							}
   	   	        			}
   	   	        		}
   	   	        	});
   	   				$.ajax({
   	   	        		method:"post",
   	   	        		url:Util.getPath()+"/UserController/getUserManageAreaInfo",
   	   	        		dataType:"json",
   	   	        		data:{
   	   	        			userId:userId
   	   	        		},
   	   	        		success:function(data2){
   	   	        			if(null != data2 && undefined != data2){
   	   	        				for (var int = 0; int < data2.length; int++) {
   	   	        					var node = manageAreaTreeEdit.getNodeByParam("id",data2[int].AREA_ID,null);
   	   	        					manageAreaTreeEdit.selectNode(node);
   	   	        					manageAreaTreeEdit.checkNode(node, true, false);
   	   							}
   	   	        			}
   	   	        			$.ajax({
   	   	                		method:"post",
   	   	                		url:Util.getPath()+"/UserController/getUserManageEndpointInfo",
   	   	                		dataType:"json",
   	   	                		data:{
   	   	                			userId:userId
   	   	                		},
   	   	                		success:function(data3){
   	   	                			if(null != data3 && undefined != data3){
   	   	                				for (var int = 0; int < data3.length; int++) {
   	   	                					var nodes = manageAreaTreeEdit.getNodeByParam("id",data3[int].ENDPOINT_ID,null);
   	   	                					manageAreaTreeEdit.selectNode(nodes);
   	   	                					manageAreaTreeEdit.checkNode(nodes, true, false);
   	   	        						}
   	   	                			}
   	   	                		}
   	   	        			});
   	   	        		}	
   	   	        	});
   				});
      			   userDialogEdit.setTitle("修改用户");
      			   userDialogEdit.open();
			}
   			}
   		});

   });
    
    //密码修改
    var changePasswordForm = $('#changePasswordForm');
    var passwordDialog = new bootstrapDialog({
        title: '密码修改',
        type: 'type-default',
        message: changePasswordForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        closable:false,
        cssClass: 'passwordDialog',
        buttons: [{
            id: 'passwordBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
            	if(!changePasswordForm.valid()){
            		changeValidator.focusInvalid();
            	}else{
            		$.ajax({
        			    method: 'post',
        			    url: Util.getPath()+"/UserController/updateUserPassword",
        			    dataType: 'json',
        			    data: {
        			    	userId: USER.ID,
        			    	newPassword:$("#newPassword").val(),
      			    },
        			    success: function(data){
        			    	if(data.success){
        			    		Util.promptSuccessDialog(data.bean);
        			    	}else{
        			    		Util.warningDialog(data.bean);
        			    	}
        			    	passwordDialog.close();
    			    		changePasswordForm[0].reset();
    			    		changeValidator.resetForm();
        			    }
        			});
            	}
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
            	changePasswordForm[0].reset();
            	changeValidator.resetForm();
                passwordDialog.close();
            }
        }]

    });
    $('#userGrid').on('click',".changePassword", function () {
        var userId =$(this).attr("data-id");
        USER.ID = userId;
        passwordDialog.open();
    });
    
    //权限设置模块拼接 用户权限修改
    $('#userGrid').on('click','.authoritySetting', function () {
    	 //获取用户id值
    	 var userId = $(this).attr("data-id");
    	 USER.AUTHID=userId;
    	　// 获取树对象
    	    /** 查询用户所具有的权限 **/
    	    authorityDialog.onShown(function(){
    	    	$.ajax({
            		method:"post",
            		url:Util.getPath()+"/UserController/getUserModuleInfo",
            		dataType:"json",
            		data:{
            			userId:userId
            		},
            		success:function(data){
            			if(null != data && undefined != data){
            				for (var int = 0; int < data.length; int++) {
            					var node = tree.getNodeByParam("id",data[int].MODULE_ID,null);
            					tree.selectNode(node);
            	    	    	tree.checkNode(node, true, false);
    						}
            			}
            		}
            	});
       		});
    	  authorityDialog.open();
    });
    
    //权限设置
    var authorityForm= $('#authorityForm');
    var authorityDialog = new bootstrapDialog({
        title: '权限设置',
        type: 'type-default',
        message: authorityForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        closable:false,
        cssClass: 'authorityDialog',
        buttons: [{
            id: 'authorityBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
            	var userId = USER.AUTHID;
            	var nodes = tree.getCheckedNodes();
            	//使用ajax异步请求 将用户权限和用户id 入库 T_NCLK_USER_AUTH
            	var idsArray=new Array();
            	for (var int = 0; int < nodes.length; int++) {
            		idsArray.push(nodes[int].id);
				}
            	$.ajax({
            		method:"post",
            		url:Util.getPath()+"/UserController/saveUserModuleInfo",
            		dataType:"json",
            		data:{
            			userId:userId,
            			moduleId:idsArray.toString(),
            		},
            		success:function(data){
            			if(data.success){
            				Util.promptSuccessDialog(data.bean);
            			}else{
            				Util.promptSuccessDialog(data.bean);
            			}
            			tree.checkAllNodes(false);
            			authorityDialog.close();
            			}
            		});
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
            	tree.checkAllNodes(false);
                authorityDialog.close();
            }
        }]

    });
    
    //根据用户Id获取用户管理区域
    $("#userGrid").on("click",".manageArea",function(){
    	var userId=$(this).attr("data-id");
    	USER.AREAID=userId;
    	manageAreaDialog.onShown(function(){
    		$.ajax({
        		method:"post",
        		url:Util.getPath()+"/UserController/getUserManageAreaInfo",
        		dataType:"json",
        		data:{
        			userId:userId
        		},
        		success:function(data){
        			if(null != data && undefined != data){
        				for (var int = 0; int < data.length; int++) {
        					var node = manageAreaDialogTree.getNodeByParam("id",data[int].AREA_ID,null);
        					manageAreaDialogTree.selectNode(node);
        					manageAreaDialogTree.checkNode(node, true, false);
						}
        			}
        			$.ajax({
                		method:"post",
                		url:Util.getPath()+"/UserController/getUserManageEndpointInfo",
                		dataType:"json",
                		data:{
                			userId:userId
                		},
                		success:function(data){
                			if(null != data && undefined != data){
                				for (var int = 0; int < data.length; int++) {
                					var nodes = manageAreaDialogTree.getNodeByParam("id",data[int].ENDPOINT_ID,null);
                					manageAreaDialogTree.selectNode(nodes);
                					manageAreaDialogTree.checkNode(nodes, true, false);
        						}
                			}
                		}
        		});
        		}	
        	});
    	});
    	manageAreaDialog.open();
    });

    //通过用户ID修改管理区域
    var manageAreaForm= $('#manageAreaForm');
    var manageAreaDialog = new bootstrapDialog({
        title: '管辖区域设置',
        type: 'type-default',
        message: manageAreaForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        closable:false,
        cssClass: 'manageAreaDialog',
        buttons: [{
            id: 'authorityBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
            	var userId = USER.AREAID;
            	var nodes = manageAreaDialogTree.getCheckedNodes();
            	var areaIdsArray=new Array();
            	var endPointIdsArray=new Array();
            	for (var int = 0; int < nodes.length; int++) {
            		if(nodes[int].level<3){
            			areaIdsArray.push(nodes[int].id);
            		}else{
            			endPointIdsArray.push(nodes[int].id);
            		}
				}
            	$.ajax({
            		method:"post",
            		url:Util.getPath()+"/UserController/saveUserAreaInfo",
            		dataType:"json",
            		data:{
            			userId:userId,
            			areaId:areaIdsArray.toString(),
            			endPointId:endPointIdsArray.toString()
            		},
            		success:function(data){
            			if(data.success){
            				Util.promptSuccessDialog(data.bean);
            			}else{
            				Util.promptSuccessDialog(data.bean);
            			}
            			tree.checkAllNodes(false);
            			authorityDialog.close();
            			}
            		});
            	
            	manageAreaDialog.close();
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
            	manageAreaDialog.close();
            	manageAreaDialogTree.checkAllNodes(false);
            }
        }]

    });
    
    //删除
    $('#userGrid').on('click', '.table-operation-del', function () {
        var id = $(this).attr('data-id');
        Util.questionDialog('确定删除数据？', function (dialog, result) {
            if (result == true) {
            	$.ajax({
            		method:"post",
            		url:Util.getPath()+"/UserController/deleteUserInfoByUserId",
            		dataType:"json",
            		data:{
            			userId:id
            		},
            		success:function(data){
            			if(data.success){
            				Util.promptSuccessDialog(data.bean);
            			}else{
            				Util.warningDialog(data.bean);
            			}
            			gridTable.table('refresh');
        				dialog.close();
            		}
            	});
            	
            } else {
                dialog.close();
            }
        });
    });

    //按钮删除
    $("#btn-delete").click(function () {
        var items = gridTable.table('getSelections');
        if (items.length == 0) {
            Util.warningDialog('尚未选择数据，请选择！');
        } else {
        	var idsArray=new Array();
			for(var i=0;i<items.length;i++){
				idsArray.push(items[i].USER_ID);
			}
            Util.questionDialog('确定删除数据？', function (dialog, result) {
                if (result) {
                	$.ajax({
                		method:"post",
                		url:Util.getPath()+"/UserController/deleteUserInfoByUserId",
                		dataType:"json",
                		data:{
                			userId:idsArray.toString(),
                		},
                		success:function(data){
                			if(data.success){
                				Util.promptSuccessDialog(data.bean);
                			}else{
                				Util.warningDialog(data.bean);
                			}
                			gridTable.table('refresh');
            				dialog.close();
                		}
                	});

                }
                dialog.close();
            });
        }
    });
    
    //解锁用户
    $("#btn-unlock").click(function () {
        var items = gridTable.table('getSelections');
        if (items.length == 0) {
            Util.warningDialog('尚未选择数据，请选择！');
        } else {
        	var idsArray=new Array();
			for(var i=0;i<items.length;i++){
				idsArray.push(items[i].USER_ID);
			}
            Util.questionDialog('确定需要解锁已选择的数据？', function (dialog, result) {
                if (result) {
                	$.ajax({
                		method:"post",
                		url:Util.getPath()+"/UserController/updateUserState",
                		dataType:"json",
                		data:{
                			userId:idsArray.toString(),
                		},
                		success:function(data){
                			if(data.success){
                				Util.promptSuccessDialog("解锁用户信息成功！");
                				gridTable.table('refresh');
                			}else{
                				Util.promptSuccessDialog("解锁用户信息失败！");
                				gridTable.table('refresh');
                			}
                		},
                		error:function(data){
            				Util.promptSuccessDialog("解锁用户信息失败！");
            				gridTable.table('refresh');
                		}
                	});

                }
                dialog.close();
            });
        }
    });
    
    
    
    
  //删除用户头像
	$("#deleteUserPhoto").on("click",function(){
		$("#preview").attr("src",Util.getPath()+'/common/img/head.png');
	   	$("[name='photofilePath']").val("");
	 });
	
	$("[name='PHOTO']").change(function(){
		$.ajaxFileUpload({
 		   url: Util.getPath() + "/FileController/uploadFile",//用于文件上传的服务器端请求地址
 		   type: 'post',
 		   data: { ProdcutID: 1 },  
 		   secureuri: false,         //是否需要安全协议，一般设置为false
 		   fileElementId: ['PHOTO'],//文件上传域的ID 在这里设置要上传的多个input的ID
 		   dataType: 'json',         //返回值类型 一般设置为json
 		   success: function (data){ 
 			   //
 		   },
 		   error: function (data, status, e){
 			   if(undefined != data.responseText && undefined != JSON.parse(data.responseText).bean[0]){
 				   $("[name='photofilePath']").val(encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 	 			   $("#preview").attr("src",Util.getPath()+'/FileController/showPic?filePath='+ encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 			   }
 		   }
 		});
	});
	
	
	//删除用户头像编辑窗口
	$("#deleteUserPhoto_edit").on("click",function(){
		$("#preview_edit").attr("src",Util.getPath()+'/common/img/head.png');
	   	$("[name='photofilePath_edit']").val("");
	 });
	
	$("[name='PHOTO_edit']").change(function(){
		$.ajaxFileUpload({
 		   url: Util.getPath() + "/FileController/uploadFile",//用于文件上传的服务器端请求地址
 		   type: 'post',
 		   data: { ProdcutID: 1 },  
 		   secureuri: false,         //是否需要安全协议，一般设置为false
 		   fileElementId: ['PHOTO_edit'],//文件上传域的ID 在这里设置要上传的多个input的ID
 		   dataType: 'json',         //返回值类型 一般设置为json
 		   success: function (data){ 
 			   //
 		   },
 		   error: function (data, status, e){
 			   if(undefined != data.responseText && undefined != JSON.parse(data.responseText).bean[0]){
 				   $("[name='photofilePath_edit']").val(encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 	 			   $("#preview_edit").attr("src",Util.getPath()+'/FileController/showPic?filePath='+ encodeURI(encodeURI(JSON.parse(data.responseText).bean[0].filePath)));
 			   }
 		   }
 		});
	});

});