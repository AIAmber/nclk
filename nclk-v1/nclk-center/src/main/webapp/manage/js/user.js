require(['jquery', 'bootstrap-grid', 'Util', 'table-toolbar', 'bootstrap-dialog', 'ztree'], function ($, bootstrapGrid, Util, toolbar, bootstrapDialog, ztree) {
    var toolbarc = $(".table-toolbar").toolbar();
    var gridTable = $('#userGrid').bootstrapGrid({
        url: Util.getPath() + '/UserController/findUsers',
        uniqueId: 'USER_ID',
        toolbarc: toolbarc,
        customHeight: 64,
        columns: [{
            checkbox: true
        }, {
            visible: false,
            field: 'USER_ID',
            title: '主键'
        }, {
            field: 'NAME',
            title: '姓名',
            width: '18%'
        }, {
            field: 'USERNAME',
            title: '用户名',
            width: '18%'
        }, {
            field: 'authority',
            title: '权限',
            width: '18%',
            formatter: function (value, row, index) {
                return "<a  class='authoritySetting' href='javascript:void(0)'>权限设置</a>";
            }
        }, {
            field: 'PASSWORD',
            title: '密码',
            width: '18%',
            formatter: function (value, row, index) {
                return "<a  data-id="+row.USER_ID+" class='changePassword' href='javascript:void(0)'>密码修改</a>";
            }
        }, {
            field: 'manageArea',
            title: '管辖区域',
            width: '18%',
            formatter: function (value, row, index) {
                return "<a class='manageArea' href='javascript:void(0)'>管辖区域设置</a>";
            }
        }, {
            field: 'USER_ID',
            title: '操作',
            formatter: function (value, row, index) {
                return [
                    '<a class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
                    '<a data-id="' + value + '" class="table-delete table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
                ].join('');
            }
        }]
    });
    
    function getPath(){
		return location.pathname.match(/\/(.+?)(?=\/)/g)[0];
	}
    
    var validator = Util.validate("userAddForm", {
        rules: {
            perName: {
                required: true
            },
            userName: {
                required: true
            },
            addPassword: {
                required: true
            },
            addConfirmPassword: {
                required: true,
                equalTo: "#addPassword"
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
                required: "请输入密码"
            },
            addConfirmPassword: {
                required: "请输入确认密码",
                equalTo: "两次输入密码不一致"
            }
        }
    });
    
    //密码修改校验
    var changePasswordForm=$("#changePasswordForm");
    var changeValidator=Util.validate("changePasswordForm",{
    	rules: {
            password: {
                required: true
            },
            newPassword: {
            	required: true
            },
            confirmPassword: {
                required: true,
                equalTo: "#newPassword"
            }
        },
        messages: {
            password: {
                required: "请输入原密码"
            },
            password: {
            	required: "请输入新密码"
            },
            confirmPassword: {
                required: "请输入确认密码",
                equalTo: "两次输入密码不一致"
            }
        }
    	
    });
//新增用户
    var userAddForm =$('#userAddForm');
    var userDialog = new bootstrapDialog({
        type: 'type-default',
        message: userAddForm,
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        cssClass: 'userAddDialog',
        buttons: [{
            id: 'userBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
                if (!userAddForm.valid()) {
                    validator.focusInvalid();
                }else{
                	$.ajax({
                		method:"post",
                		url:getPath()+"/UserController/addUser",
                		dataType:"json",
                		data:{
                			perName:$("#perName").val(),
                			userName:$("#userName").val(),
                			password:$("#addPassword").val(),
                		},
                		success:function(data){
                			if(data.success){
                				Util.promptSuccessDialog(data.bean);
                				userAddForm[0].reset();
                				userDialog.close();
                			}else{
                				Util.warningDialog(data.bean);
                				userAddForm[0].reset();
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
                userAddInfo[0].reset();
                validator.resetForm();
                dialogRef.close();
            }
        }]
    });

    $('#btn-add').on("click", function () {
        userDialog.setTitle("新增用户管理");
        userDialog.open();
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
        cssClass: 'passwordDialog',
        buttons: [{
            id: 'passwordBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
            	debugger
            	if(!changePasswordForm.valid()){
            		changeValidator.focusInvalid();
            	}else{
            		$.ajax({
        			    method: 'post',
        			    url: getPath()+"/UserController/updatePassword",
        			    dataType: 'json',
        			    data: {
        			    	userId:$(".changePassword").attr("data-id"),
        			    	password:$("#password").val(),
        			    	newPassword:$("#newPassword").val(),
      			    },
        			    success: function(data){
        			    	if(data.success){
        			    		Util.promptSuccessDialog("修改成功！");
        			    		passwordDialog.close();
        			    		changePasswordForm[0].reset();
        			    		
        			    	}else{
        			    		Util.warningDialog(data.bean);
        			    		changePasswordForm[0].reset();
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
                passwordDialog.close();
            }
        }]

    });
    
    $('#userGrid').on('click',".changePassword", function () {
        passwordDialog.open();
    });
    
    var authorityDialog = new bootstrapDialog({
        title: '权限设置',
        type: 'type-default',
        message: $('#authorityForm'),
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable: true,
        cssClass: 'authorityDialog',
        buttons: [{
            id: 'authorityBtn',
            label: '保存',
            autospin: false,
            icon: 'glyphicon glyphicon-check',
            cssClass: ' btn-success btn-sm',
            action: function (dialogRef) {
                authorityDialog.close();
            }
        }, {
            label: '取消',
            autospin: false,
            icon: 'glyphicon glyphicon-remove',
            cssClass: ' btn-default btn-sm',
            action: function (dialogRef) {
                authorityDialog.close();
            }
        }]

    });
    $('.authoritySetting').on('click', function () {
        authorityDialog.open();
    });

    $('.choose-all-input').on('click', function () {
    	if($(this).parents("tr").find(".choose-all-input").is(":checked") == true){
    		$(this).parents("tr").next().find(".choose-single").each(function(){
        		this.checked = "checked";
        	});
    	}else if($(this).parents("tr").find(".choose-all-input").is(":checked") == false){
    		$(this).parents("tr").next().find(".choose-single").each(function(){
        		this.checked = "";
        	});
    	}
    });
    
    $('#choose-all-btn').on('click', function () {
    	if($("#choose-all-btn").is(":checked")==true){
    		$("#choose-all-btn").parents().find(".choose-all-input").prop("checked",true);
    		$("#choose-all-btn").parents().find(".choose-single").prop("checked",true);
    	}else if($("#choose-all-btn").is(":checked")==false){
    		$("#choose-all-btn").parents().find(".choose-all-input").prop("checked",false);
    		$("#choose-all-btn").parents().find(".choose-single").prop("checked",false);
    	}
    	
    });


    //管辖区域树
    var manageArea = $('#manageArea');
    var setting = {
        view: {
            dblClickExpand: true
        },
        async: {
            enable: true,
            url: Util.getPath() + "/manage/data/tree.json",
            dataFilter: function (treeId, parentNode, childNodes) {
                return childNodes.rows;
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
    $.fn.zTree.init($('#manageArea-treeContent'), setting);

    $('.dropdown-menu').on('click', '[data-stopPropagation]', function (e) {
        e.stopPropagation();
    });


    //删除
    $('#userGrid').on('click', '.table-operation-del', function () {
        //当前行数据的主键值
        var id = $(this).parent().find("a").attr('data-id');
        Util.questionDialog('确定删除数据？', function (dialog, result) {
            if (result == true) {
                dialog.close();
                Util.promptSuccessDialog('数据删除成功！');
            } else {
                dialog.close();
            }
        });
    });


    $("#btn-delete").click(function () {
        var items = gridTable.table('getSelections');
        if (items.length == 0) {
            Util.warningDialog('尚未选择数据，请选择！');
        } else {
            Util.questionDialog('确定删除数据？', function (dialog, result) {
                if (result) {
                    //TODO　这里调用后台
                    Util.promptSuccessDialog('删除用户信息成功！');
                }
                dialog.close();
            });
        }
    });

});