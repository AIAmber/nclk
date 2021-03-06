require(['jquery','ztree','Util','bootstrap-dialog','bootstrap-grid','table-toolbar','jquery-placeholder'], function($,ztree,Util,BootstrapDialog,placeholder) {
	//定义全局变量记录编辑表单有效无效状态
	var editStateValue="";
	//添加ie8下placeholder的支持
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
			if($("#examNum").val() == ""){
				$("#examNum").placeholder();
			}
			if($("#searchText").val() == ""){
				$("#searchText").placeholder();
			}
	});
	
	//区域管理 全部 有效 无效按钮事件
	$("#btn-group-tab").on("click","a",function(){
		$(".active"). removeClass("active");
		$(this).addClass("active");
		
		var treeObj=$.fn.zTree.getZTreeObj("area_tree");
        var nodes = treeObj.getSelectedNodes();
        
        //未选中左侧树 直接点击有效无效按钮
        if(nodes.length == 0){
        	Util.warningDialog("请先点击选择左侧区域！");
        	return ;
        }else{
        	//左侧菜单树 默认只能点击选中一个节点
            var parid = treeObj.getSelectedNodes()[0].id;
            var level = treeObj.getSelectedNodes()[0].level;
    		
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
				"state" : stateValue,
	            "areaId" : parid,
	            "areaType" : level+2
			});
        }
	});
	
	//-------------------------左侧树
    //区域管理树
	var isFirst = true;
	var setting = {
			view:{
				dblClickExpand: false,
				showLine: false,
				showIcon: false,
				selectedMulti: false
			},
			async: {
				enable: true,
				url:Util.getPath()+"/AreaController/getTreeInfo",
				autoParam:["id", "name=n"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: function(treeId, parentNode, childNodes){
					return childNodes.rows;
				}
				
			}, 
			data: {
			    simpleData: {
			        enable: true
			    }
			},
			callback:{
				onClick:function(event, treeId, treeNode){
						var state = $(".btn.btn-default.active").html();
				        var stateValue = "A";
				        if("有效" == state){
				        	stateValue = "A";
				        }else if("无效" == state){
				        	stateValue = "X";
				        }else{
				        	stateValue = "";
				        }
						gridTable.resetSearch({
							"areaId":treeNode.id,
							"areaType":treeNode.level+2,
							"state":stateValue
						});
				},
				onAsyncSuccess:function(event, treeId) {
					  if (isFirst) {
					      //获得树形图对象
					      var zTree = $.fn.zTree.getZTreeObj("area_tree");
					      //获取根节点个数,getNodes获取的是根节点的集合
					      var nodeList = zTree.getNodes();
					　　　　　　//展开第一个根节点
					      zTree.expandNode(nodeList[0], true);
					　　　　　　//当再次点击节点时条件不符合,直接跳出方法
					      isFirst= false;
					      var zTree = $.fn.zTree.getZTreeObj("area_tree");
					      var nodes = zTree.getNodes();
					      zTree.selectNode(nodes[0]);
					      if(nodes==""||nodes==null){
					    	  gridTable.resetSearch({
						    	  "areaId":"",
						    	  "areaType":""
						      });
					      }else{
					    	  gridTable.resetSearch({
						    	  "areaId":nodes[0].id,
						    	  "areaType":nodes[0].level+2
						      }); 
					      }
					      
					  }
				}
			}
        };

	
	var treeObj = $.fn.zTree.init($("#area_tree"), setting);
	
	//区域类型与上级区域联动新增窗口
//	$("#areaType_add").change(function(){
//		var areaType=$(this).val();
//		if(areaType=="1"){
//			$("#upArea_add").empty();
//			$("#upArea_add").attr("disabled",true);
//		}else if(areaType==""){
//			$("#upArea_add").empty();
//			$("#upArea_add").append("<option value='' >请选择上级区域</option>");
//		}else{
//			$.ajax({
//	            url: Util.getPath()+"/AreaController/findAreaInfoByAreaType",
//	            type: 'post',
//	            dataType: 'json',
//	            data: {
//	            	areaType:areaType
//	            },
//	            success: function (data) {
//	            	$("#upArea_add").removeAttr("disabled");
//	            	if(data[0].AREA_TYPE=="1"){
//	            		$("#upArea_add").empty();
//	            		$("#upArea_add").append("<option value='' >请选择上级区域</option>");
//	            		for(var i=0;i<data.length;i++){
//		            		$("#upArea_add").append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
//		            	}
//	            	}else if(data[0].AREA_TYPE=="2"){
//	            		$("#upArea_add").empty();
//	            		$("#upArea_add").append("<option value='' >请选择上级区域</option>");
//	            		for(var i=0;i<data.length;i++){
//		            		$("#upArea_add").append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
//		            	}
//	            	}
//	            }
//	        });
//		}
//	});
	
	function areaTypeChange(areaType,upAreaId){
		if(areaType=="1"){
			upAreaId.empty();
			upAreaId.attr("disabled",true);
		}else if(areaType==""){
			upAreaId.empty();
			upAreaId.append("<option value='' >请选择上级区域</option>");
		}else{
			$.ajax({
	            url: Util.getPath()+"/AreaController/findAreaInfoByAreaType",
	            type: 'post',
	            dataType: 'json',
	            data: {
	            	areaType:areaType
	            },
	            success: function (data) {
	            	upAreaId.removeAttr("disabled");
	            	if(data[0].AREA_TYPE=="1"){
	            		upAreaId.empty();
	            		upAreaId.append("<option value='' >请选择上级区域</option>");
	            		for(var i=0;i<data.length;i++){
	            			upAreaId.append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
		            	}
	            	}else if(data[0].AREA_TYPE=="2"){
	            		upAreaId.empty();
	            		upAreaId.append("<option value='' >请选择上级区域</option>");
	            		for(var i=0;i<data.length;i++){
	            			upAreaId.append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
		            	}
	            	}
	            }
	        });
		}
	}
	
	
	//区域类型与上级区域联动编辑窗口
//	$("#areaType_edit").change(function(){
//		var areaType=$(this).val();
//		if(areaType=="1"){
//			$("#upArea_edit").empty();
//			$("#upArea_edit").attr("disabled",true);
//		}else if(areaType==""){
//			$("#upArea_edit").empty();
//			$("#upArea_edit").append("<option value='' >请选择上级区域</option>");
//		}else{
//			$.ajax({
//	            url: Util.getPath()+"/AreaController/findAreaInfoByAreaType",
//	            type: 'post',
//	            dataType: 'json',
//	            data: {
//	            	areaType:areaType
//	            },
//	            success: function (data) {
//	            	$("#upArea_edit").removeAttr("disabled");
//	            	if(data[0].AREA_TYPE=="1"){
//	            		$("#upArea_edit").empty();
//	            		$("#upArea_edit").append("<option value='' >请选择上级区域</option>");
//	            		for(var i=0;i<data.length;i++){
//		            		$("#upArea_edit").append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
//		            	}
//	            	}else if(data[0].AREA_TYPE=="2"){
//	            		$("#upArea_edit").empty();
//	            		$("#upArea_edit").append("<option value='' >请选择上级区域</option>");
//	            		for(var i=0;i<data.length;i++){
//		            		$("#upArea_edit").append("<option value='"+data[i].AREA_ID+"' >"+data[i].AREA_NAME+"</option>");
//		            	}
//	            	}
//	            }
//	        });
//		}
//	});
	
	//左侧树区域新增
	$("#tree-add").on("click",function(){
		dialogAdd.setTitle("新增区域");
//		dialogAdd.onShown(function(){
//			addInfoForm.find("#areaType_add").change(function(){
//				var areaType=$(this).val();
//				alert(areaType);
//				areaTypeChange(areaType,addInfoForm.find("#upArea_add"));
//			})
//		})
		dialogAdd.open();
	});
	
	//左侧树区域修改
	$("#tree-update").on("click",function(){
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length == 0){
			Util.warningDialog('尚未选择数据，请选择！');
			return;
		}
		$.ajax({
		    method: 'post',
		    url: Util.getPath()+"/AreaController/findAreaInfoByAreaId",
		    dataType: 'json',
		    data:{
		    	areaId:nodes[0].id,
		    },
		    success: function(data){
		    	editInfoForm.find("#areaId_edit").val(data.AREA_ID);
		    	editInfoForm.find("#areaName_edit").val(data.AREA_NAME);
		    	editInfoForm.find("#areaType_edit").val(data.AREA_TYPE);
		    	editInfoForm.find("#ord_edit").val(data.ORD);
		    	editInfoForm.find("input[name='state_edit'][value='"+data.STATE+"']").prop("checked",true);
		    	editStateValue=data.STATE;
		    	var parentId=data.PARENT_AREA_ID;
		    	if(data.AREA_TYPE=="1"){
		    		editInfoForm.find("#upArea_edit").empty();
		    		editInfoForm.find("#upArea_edit").attr("disabled",true);
		    	}else {
		    		 $.ajax({
		                 url: Util.getPath()+"/AreaController/findAreaInfoByAreaType",
		                 type: 'post',
		                 dataType: 'json',
		                 data: {
		                     areaType:data.AREA_TYPE
		                 },
		                 success: function (data) {
		                	 	   editInfoForm.find("#upArea_edit").removeAttr("disabled");
		                	 	   editInfoForm.find("#upArea_edit").empty();
		                	 	   editInfoForm.find("#upArea_edit").append("<option value='' >请选择上级区域</option>");
		                           for(var i=0;i<data.length;i++){
		                        	   editInfoForm.find("#upArea_edit").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
		                           }
		                           editInfoForm.find("#upArea_edit option[value="+parentId+"]").attr("selected",true);
		                	 }
		                 });
		    	}
		    }
		    });
			dialogEdit.setTitle("修改区域");
//			dialogEdit.onShown(function(){
//				editInfoForm.find("#areaType_edit").change(function(){
//					var areaType=$(this).val();
//					areaTypeChange(areaType,addInfoForm.find("#upArea_edit"));
//				})
//			})
			dialogEdit.open();
	});
	//左侧树区域删除
	$("#tree-del").on("click",function(){
		var nodes = treeObj.getSelectedNodes();
		if(nodes.length == 0){
			Util.warningDialog('尚未选择数据，请选择！');
			return;
		}
		Util.questionDialog('确定删除数据？',function(dialog, result){
			if(result){
				$.ajax({
				    method: 'post',
				    url: Util.getPath()+"/AreaController/deleteAreaInfoByAreaId",
				    dataType: 'json',
				    data:{
				    	areaId:nodes[0].id,
				    },
				    success: function(data){
				    	if(data.success){
				    		Util.promptSuccessDialog(data.bean);
				    	}else{
				    		Util.promptSuccessDialog(data.bean);
				    	}
				    		treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
					    	treeObj.selectNode(nodes[0].getParentNode());
					    	var state = $(".btn.btn-default.active").html();
					        var stateValue = "A";
					        if("有效" == state){
					        	stateValue = "A";
					        }else if("无效" == state){
					        	stateValue = "X";
					        }else{
					        	stateValue = "";
					        }
					        if(nodes[0].level==0){
					        	isFirst=true;
					    		gridTable.resetSearch({
						    		"areaId" : "",
					                "areaType" :"",
					                "state":""
						    	});
					        }else{
						    	gridTable.resetSearch({
						    		"areaId" : nodes[0].getParentNode().id,
					                "areaType" :  nodes[0].getParentNode().level+2,
					                "state":stateValue
						    	});
					    	}
				    	
				    	dialog.close();
				    }
				    });
			}
			dialog.close();
		});
	});
	
	//--------------------------------右侧列表
	
	$(".table-toolbar").toolbar({
		 searchEvent: function () {
            var searchText = $("#searchText").val();
            var treeObj=$.fn.zTree.getZTreeObj("area_tree");
            var nodes = treeObj.getSelectedNodes();
            var state = $(".btn.btn-default.active").html();
            var stateValue = "A";
            if("有效" == state){
            	stateValue = "A";
            }else if("无效" == state){
            	stateValue = "X";
            }else{
            	stateValue = "";
            }
            if(nodes.length == 0){
            	Util.warningDialog("请先点击选择左侧区域！");
            	return;
            }else{
            	//左侧菜单树 默认只能点击选中一个节点
	            var parid = treeObj.getSelectedNodes()[0].id;
	            var level = treeObj.getSelectedNodes()[0].level;
            	var zTree = $.fn.zTree.getZTreeObj("area_tree");
					gridTable.resetSearch({
						"searchText": searchText,
		                "state" : stateValue,
						"areaId" : parid,
						"areaType":level+2
					});
            }
		 }
	    });
	
	var gridTable = $('#areaGrid').bootstrapGrid({
			url:Util.getPath()+"/AreaController/findAreaInfo",
			uniqueId:'AREA_ID',
			columns:[{
		        checkbox: true,
		    }, {
		        field: 'AREA_NAME',
		        title: '区域名称',
		        width:'40%'
		    }, {
		    	field: 'AREA_TYPE',
		    	title: '区域类型',
		    	width:'40%',
		    	formatter:function(value,row,index){
		    		if(value == "1"){
		    			return "省级指挥中心";
		    		}else if(value == "2"){
		    			return "市级指挥中心";
		    		}else if(value == "3"){
		    			return "县区级指挥中心";
		    		}  
		        }
		    }, {
		    	field: 'STATE',
		        title: '状态',
		        width:'20%',
		    	formatter:function(value,row,index){
		    		if(value == "A"){
		    			return "有效";
		    		}else if(value == "X"){
		    			return "<font class='red'>无效</font>";
		    		} 
		        }
		    }
		    , {
		    	field: 'AREA_ID',
		        title: '操作',
		        width:'20%',
		    	formatter:function(value,row,index){
		    		//区域为禁用状态时
		    		if(row.STATE=="X"){
		    			return [
			    		        '<a data-id="'+value+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
			    		        '<a data-id="'+value+'" class="table-operation table-operation-del fa fa-trash-o" style="opacity: 0;filter: alpha(opacity = 0);" title="删除"></a>'
			    		    ].join('');
		    		}else{
		    			return [
		    		        '<a data-id="'+value+'" class="table-operation table-operation-edit fa fa-pencil-square-o" href="javascript:void(0)" title="修改"></a>',
		    		        '<a data-id="'+value+'" id="areaLineDelete" class="table-operation table-operation-del fa fa-trash-o" href="javascript:void(0)" title="删除"></a>'
		    		    ].join('');
		    		}
		        }
		    }]
		});
		
	//右侧列表图标修改
	$(".bootstrap-table").on("click",".table-operation-edit",function(){
		var areaId=	$(this).attr("data-id");
		$.ajax({
		    method: 'post',
		    url: Util.getPath()+"/AreaController/findAreaInfoByAreaId",
		    dataType: 'json',
		    data:{
		    	areaId:areaId,
		    },
		    success: function(data){
		    	editInfoForm.find("#areaId_edit").val(data.AREA_ID);
		    	editInfoForm.find("#areaName_edit").val(data.AREA_NAME);
		    	editInfoForm.find("#areaType_edit").val(data.AREA_TYPE);
		    	editInfoForm.find("#ord_edit").val(data.ORD);
		    	editInfoForm.find("input[name='state_edit'][value='"+data.STATE+"']").prop("checked",true);
		    	editStateValue=data.STATE;
		    	var parentId=data.PARENT_AREA_ID;
		    	if(data.AREA_TYPE=="1"){
		    		editInfoForm.find("#upArea_edit").empty();
		    		editInfoForm.find("#upArea_edit").attr("disabled",true);
		    	}else {
		    		 $.ajax({
		                 url: Util.getPath()+"/AreaController/findAreaInfoByAreaType",
		                 type: 'post',
		                 dataType: 'json',
		                 data: {
		                     areaType:data.AREA_TYPE
		                 },
		                 success: function (data) {
		                	       editInfoForm.find("#upArea_edit").removeAttr("disabled");
		                	       editInfoForm.find("#upArea_edit").empty();
		                	       editInfoForm.find("#upArea_edit").append("<option value='' >请选择上级区域</option>");
		                           for(var i=0;i<data.length;i++){
		                        	   editInfoForm.find("#upArea_edit").append("<option value='"+data[i].AREA_ID+"'>"+data[i].AREA_NAME+"</option>");
		                           }
		                           editInfoForm.find("#upArea_edit option[value="+parentId+"]").attr("selected",true);
		                	 }
		                 });
		    	}
		    }
		    });
//		dialogEdit.onShown(function(){
//			editInfoForm.find("#areaType_edit").change(function(){
//				var areaType=$(this).val();
//				alert(areaType);
//				areaTypeChange(areaType,addInfoForm.find("#upArea_edit"));
//			})
//		})
		dialogEdit.setTitle("修改区域");
		dialogEdit.open();
	});
	
	//右侧列表图标删除
	$('#areaGrid').on('click','#areaLineDelete',function(){
		var id = $(this).attr('data-id');
		var nodes = treeObj.getSelectedNodes();
		Util.questionDialog('确定删除数据？',function(dialog, result){
			if(result==true){
				$.ajax({
				    method: 'post',
				    url: Util.getPath()+"/AreaController/deleteAreaInfoByAreaId",
				    dataType: 'json',
				    data:{
				    	areaId:id,
				    },
				    success: function(data){
				    	if(data.success){
				    		Util.promptSuccessDialog(data.bean);
				    	}else{
				    		Util.promptSuccessDialog(data.bean);
				    	}
    		    		if(nodes[0].id==id){
    		    			treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
    		    			treeObj.selectNode(nodes[0].getParentNode());
    		    			var state = $(".btn.btn-default.active").html();
    				        var stateValue = "A";
    				        if("有效" == state){
    				        	stateValue = "A";
    				        }else if("无效" == state){
    				        	stateValue = "X";
    				        }else{
    				        	stateValue = "";
    				        }
    				        if(nodes[0].level==0){
    				        	isFirst=true;
    				        	gridTable.resetSearch({
        				    		"areaId" : "",
        			                "areaType" :"",
        			                "state":stateValue
        				    	});
    				        }else{
    				        	gridTable.resetSearch({
        				    		"areaId" : nodes[0].getParentNode().id,
        			                "areaType" :  nodes[0].getParentNode().level+2,
        			                "state":stateValue
        				    	});
    				        }
    				    	
        		    	}else{
        		    		treeObj.reAsyncChildNodes(nodes[0], "refresh");
        		    		treeObj.selectNode(nodes[0]);
        		    		var state = $(".btn.btn-default.active").html();
    				        var stateValue = "A";
    				        if("有效" == state){
    				        	stateValue = "A";
    				        }else if("无效" == state){
    				        	stateValue = "X";
    				        }else{
    				        	stateValue = "";
    				        }
    				    	gridTable.resetSearch({
    				    		"areaId" : nodes[0].id,
    			                "areaType" :  nodes[0].level+2,
    			                "state":stateValue
    				    	});
        		    	}
				    	dialog.close();
				    	gridTable.table("refresh");
				    }
				    });
			}else{
				dialog.close();
			}
		});
	});
	
	//右侧列表按钮删除
	$("#btn-delete").click(function(){
		var items = gridTable.table('getSelections');
		if(items.length==0){
			Util.warningDialog('尚未选择数据，请选择！');
		}else{
			var areaIdArray=new Array();
			for(var i=0;i<items.length;i++){
				areaIdArray.push(items[i].AREA_ID);
			} 
			Util.questionDialog('确定删除数据？',function(dialog, result){
				if(result){
					$.ajax({
	            		method:"post",
	            		url:Util.getPath()+"/AreaController/deleteAreaInfoByAreaId",
	            		dataType:"json",
	            		data:{
	            			areaId:areaIdArray.toString(),
	            		},
	            		success:function(data){
	            			if(data.success){
	            				Util.promptSuccessDialog(data.bean);
	            			}else{
	            				Util.warningDialog(data.bean);
	            			}
	            			var nodes = treeObj.getSelectedNodes();
            		    	for(var j=0;j<items.length;j++){
            		    		if(nodes[0].id==items[j].AREA_ID){
            		    			treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
            		    			treeObj.selectNode(nodes[0].getParentNode());
            		    			var state = $(".btn.btn-default.active").html();
            				        var stateValue = "A";
            				        if("有效" == state){
            				        	stateValue = "A";
            				        }else if("无效" == state){
            				        	stateValue = "X";
            				        }else{
            				        	stateValue = "";
            				        }
            				        if(nodes[0].level==0){
            				        	isFirst=true;
            				        	gridTable.resetSearch({
                				    		"areaId" : "",
                			                "areaType" :"",
                			                "state":stateValue
                				    	});
            				        }else{
            				        	gridTable.resetSearch({
                				    		"areaId" : nodes[0].getParentNode().id,
                			                "areaType" :  nodes[0].getParentNode().level+2,
                			                "state":stateValue
                				    	});
            				        }
            				    	
                		    	}else{
                		    		treeObj.reAsyncChildNodes(nodes[0], "refresh");
                		    		treeObj.selectNode(nodes[0]);
                		    		var state = $(".btn.btn-default.active").html();
            				        var stateValue = "A";
            				        if("有效" == state){
            				        	stateValue = "A";
            				        }else if("无效" == state){
            				        	stateValue = "X";
            				        }else{
            				        	stateValue = "";
            				        }
            				    	gridTable.resetSearch({
            				    		"areaId" : nodes[0].id,
            			                "areaType" :  nodes[0].level+2,
            			                "state":stateValue
            				    	});
                		    	}
            		    	}
	            			gridTable.table("refresh");
            				dialog.close();
	            		}
	            	});
				}
				dialog.close();
			});
		}
	});
	
	//-----------------------------弹出表单部分
	//新增表单校验
	var validatorAdd = Util.validate("add-info",{
		rules:{
			areaName_add:{
				required:true,
				maxlength:25
			},
			areaType_add:{
				required:true
			},
			ord_add:{
				digits:true,
				maxlength:11
			}
		},
		messages:{
			areaName_add:{
				required:'请输入区域名称'
			},
			areaType_add:{
				required:'请输入区域类型'
			},
			ord_add:{
				digits:'请输入正确的顺序号',
				maxlength:"最大长度为11位"
			}
		}
	});
	
	//新增表单校验
	var validatorEdit = Util.validate("edit-info",{
		rules:{
			areaName_edit:{
				required:true,
				maxlength:25
			},
			areaType_edit:{
				required:true
			},
			ord_edit:{
				digits:true,
				maxlength:11
			}
		},
		messages:{
			areaName_edit:{
				required:'请输入区域名称'
			},
			areaType_edit:{
				required:'请输入区域类型'
			},
			ord_edit:{
				digits:'请输入正确的顺序号',
				maxlength:"最大长度为11位"
			}
		}
	});
	//区域模态框信息保存
	var editInfoForm= $("#edit-info");
	var dialogEdit = new BootstrapDialog({
        message: editInfoForm,
        type:'type-default',
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable:true,
        closable:false,
        cssClass:'areaCommandDialog',
        buttons: [{
        	id:"savebtn",
            label: '提交',
            icon: 'glyphicon glyphicon-check',
            cssClass:'btn-success btn-sm',
            action: function(dialogRef){
            	if(!editInfoForm.valid()){
            		validatorEdit.focusInvalid();
            	}else{
            		var areaId=editInfoForm.find("#areaId_edit").val();
            		var state = editInfoForm.find("input[name='state_edit']:checked").val();
        			//修改区域状态从X到A
        			if(state=="A"&&state!=editStateValue){
        				$.ajax({
        	    		    method: 'post',
        	    		    url: Util.getPath()+"/AreaController/updateAreaInfoByAreaId",
        	    		    dataType: 'json',
        	    		    data:{
        	    		    	areaId:editInfoForm.find("#areaId_edit").val()
        	    		    },
        	    		    success: function(data){
        	    		    	saveFormInfo(areaId,dialogRef);
        	    		    }
        				});
        			//修改区域状态从A到X
        			}else if(state=="X"&&state!=editStateValue){
        				$.ajax({
                		    method: 'post',
                		    url: Util.getPath()+"/AreaController/deleteAreaInfoByAreaId",
                		    dataType: 'json',
                		    data:{
                		    	areaId:editInfoForm.find("#areaId_edit").val()
                		    },
                		    success: function(data){
                		    	saveFormInfo(areaId,dialogRef);
            		    	}
            		    });
        			}else{
        				saveFormInfo(areaId,dialogRef);
        			}
            	}
            }
        },{
            label: '取消',
            icon:'glyphicon glyphicon-remove',
            cssClass:'btn-default btn-sm',
            action: function(dialogRef){
            	editInfoForm[0].reset();
            	validatorEdit.resetForm();
            	editInfoForm.find("#upArea_edit").empty();
            	editInfoForm.find("#upArea_edit").append("<option value='' >请选择上级区域</option>");
            	dialogRef.close();
            }
        }]
	});
	
	//区域模态框信息新增
	var addInfoForm= $("#add-info");
	var dialogAdd = new BootstrapDialog({
        message: addInfoForm,
        type:'type-default',
        closeByBackdrop: false,
        closeByKeyboard: false,
        autodestroy: false,
        draggable:true,
        closable:false,
        cssClass:'areaCommandDialog',
        buttons: [{
        	id:"savebtn",
            label: '提交',
            icon: 'glyphicon glyphicon-check',
            cssClass:'btn-success btn-sm',
            action: function(dialogRef){
            	if(!addInfoForm.valid()){
            		validatorAdd.focusInvalid();
            	}else{
            		var areaId=addInfoForm.find("#areaId_add").val();
            		var state = addInfoForm.find("input[name='state_add']:checked").val();
            		//区域新增
        			$.ajax({
            		    method: 'post',
            		    url: Util.getPath()+"/AreaController/saveAreaInfo",
            		    dataType: 'json',
            		    data:{
            		    	areaId:addInfoForm.find("#areaId_add").val(),
            		    	areaName:addInfoForm.find("#areaName_add").val(),
            		    	areaType:addInfoForm.find("#areaType_add").val(),
            		    	parentId:addInfoForm.find("#upArea_add").val(),
            		    	state : addInfoForm.find("input[name='state_add']:checked").val(),
            		    	ord:addInfoForm.find("#ord_add").val()
            		    },
            		    success: function(data){
            		    	if(data.success){
            		    		Util.promptSuccessDialog(data.bean);
            		    	}else{
            		    		Util.promptErrorDialog(data.bean);
            		    	}
            		    	addInfoForm[0].reset();
            		    	var nodes = treeObj.getSelectedNodes();
	            			treeObj.reAsyncChildNodes(null, "refresh");
	            			isFirst=true;
	            			addInfoForm.find("#upArea_add").empty();
	            			addInfoForm.find("#upArea_add").append("<option value='' >请选择上级区域</option>");
	                        gridTable.table('refresh');
        		    		dialogRef.close();
            		    }
            		});

            	}
            }
        },{
            label: '取消',
            icon:'glyphicon glyphicon-remove',
            cssClass:'btn-default btn-sm',
            action: function(dialogRef){
            	addInfoForm[0].reset();
            	validatorAdd.resetForm();
            	addInfoForm.find("#upArea_add").empty();
            	addInfoForm.find("#upArea_add").append("<option value='' >请选择上级区域</option>");
            	dialogRef.close();
            }
        }]
	});
	
	
	
	
	//获取查询区状态按钮的值
	function getStateValue(){
  		var state = $(".btn.btn-default.active").html();
        var stateValue = "A";
        if("有效" == state){
        	stateValue = "A";
        }else if("无效" == state){
        	stateValue = "X";
        }else{
        	stateValue = "";
        }
        return stateValue;
	}
	
	//保存表单信息
	function saveFormInfo(areaId,dialogRef){
    	$.ajax({
		    method: 'post',
		    url: Util.getPath()+"/AreaController/saveAreaInfo",
		    dataType: 'json',
		    data:{
		    	areaId:editInfoForm.find("#areaId_edit").val(),
		    	areaName:editInfoForm.find("#areaName_edit").val(),
		    	areaType:editInfoForm.find("#areaType_edit").val(),
		    	parentId:editInfoForm.find("#upArea_edit").val(),
		    	state : editInfoForm.find("input[name='state_edit']:checked").val(),
		    	ord:editInfoForm.find("#ord_edit").val()
		    },
		    success: function(data){
	    		Util.promptSuccessDialog(data.bean);
		    	var nodes = treeObj.getSelectedNodes();
		    	//判断节点为空情况
		    	if(nodes==""||nodes==null){
		    		var stateValue=getStateValue();
			    	gridTable.resetSearch({
			    		"areaId" : "",
		                "areaType" :"",
		                "state":stateValue
			    	});
			    	treeObj.reAsyncChildNodes(null, "refresh")
			    	isFirst=true;
			    	//判断选中节点区域Id与列表区域Id，如果相同则区域管理树刷新父节点，如果不同则刷新当前节点
		    	}else if(nodes[0].id==areaId){
		    			//判断是否为第一级节点
    		    		if(nodes[0].level==0){
    		    			treeObj.reAsyncChildNodes(nodes[0], "refresh");
        		    		treeObj.selectNode(nodes[0]);
        		    		isFirst=true;
        		    		var stateValue=getStateValue();
    				    	gridTable.resetSearch({
    				    		"areaId" : nodes[0].id,
    			                "areaType" :  nodes[0].level+2,
    			                "state":stateValue
    				    	});
    		    		}else{
    		    			treeObj.reAsyncChildNodes(nodes[0].getParentNode(), "refresh");
        		    		treeObj.selectNode(nodes[0].getParentNode());
        		    		var stateValue=getStateValue();
    				    	gridTable.resetSearch({
    				    		"areaId" : nodes[0].getParentNode().id,
    			                "areaType" :  nodes[0].getParentNode().level+2,
    			                "state":stateValue
    				    	});
    		    		}
		    	}else{
		    		treeObj.reAsyncChildNodes(nodes[0], "refresh");
		    		treeObj.selectNode(nodes[0]);
		    		var stateValue=getStateValue();
			    	gridTable.resetSearch({
			    		"areaId" : nodes[0].id,
		                "areaType" :  nodes[0].level+2,
		                "state":stateValue
			    	});
		    	}
		    	editInfoForm[0].reset();
		    	validatorEdit.resetForm();
		    	dialogRef.close();
		    	editInfoForm.find("#upArea_edit").empty();
		    	editInfoForm.find("#upArea_edit").append("<option value='' >请选择上级区域</option>");
                gridTable.table('refresh');
	    	
		    }
		});
	}
	
	//绑定change事件
	editInfoForm.find("#areaType_edit").change(function(){
		var areaType=$(this).val();
		areaTypeChange(areaType,editInfoForm.find("#upArea_edit"));
	});
	
	addInfoForm.find("#areaType_add").change(function(){
		var areaType=$(this).val();
		areaTypeChange(areaType,addInfoForm.find("#upArea_add"));
	})
	
});