define(['bootstrap-dialog','jquery-validate','jquery-validate-messages-zh'],function(BootstrapDialog){
	
	String.prototype.trim=function(){
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};
	
	String.prototype.replaceAll=function(s1,s2){
		return this.replace(new RegExp(s1,"gm"),s2);
	};
	
	String.prototype.startWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length){
			return false;
		}
		if(this.substr(0,str.length)==str){
			return true;
		}else{
			return false;
		}
		return true;
	};

	String.prototype.endWith=function(str){
		if(str==null||str==""||this.length==0||str.length>this.length){
		  return false;
		}
		if(this.substring(this.length-str.length)==str){
		  return true;
		}else{
		  return false;
		}
		return true;
	};
	
	function getCookieVal(offset) {
		var endstr = document.cookie.indexOf (";", offset);
		if (endstr == -1){
			endstr = document.cookie.length;
		}
		return unescape(document.cookie.substring(offset, endstr));
	}
	
	function openFile(command,filePath){
		if(!Util.isIE()){
			alert('当前浏览器不支持该项操作，请在IE6及以上版本重试！');
			return;
		}
		try{
			var shell = null;
			if(command=='visio'){
				new ActiveXObject("Visio.Application").documents.open(filePath);
			}else if(command=='pdf'){
				window.open(filePath);
			}else{
				shell=new ActiveXObject("wscript.shell");
				shell.run(command+" "+filePath); 
			}
		}catch (e) {
			alert("打开文件失败,请检查浏览器安全级别！");
		}
	}
	
	function getHttpRequest() {
		var http;
		var activeX = ['MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP', 'Microsoft.XMLHTTP'];
		try{
			http = new XMLHttpRequest();
		}catch(e){
			for(var i = 0; i < activeX.length; ++i) {
				try {
					http = new ActiveXObject(activeX[i]);
					break;
				} catch (e) {}
			}
		}finally{
			return http;
		}
	}
	
	return {
			//获取项目名称
			getPath:function(){
				return location.pathname.match(/\/(.+?)(?=\/)/g)[0];
			},
			//获取URL参数的值
			getQueryString:function(key){
				var params = {};
				var qs = location.search.substring(1, location.search.length);
				if (qs.length == 0) {
					return;
				}
				qs = qs.replace(/\+/g, ' ');
				var args = qs.split('&'); 
				for (var i = 0; i < args.length; i++) {
					var pair = args[i].split('=');
					var name = decodeURIComponent(pair[0]);
					var value = (pair.length==2)? decodeURIComponent(pair[1]): name;
					params[name] = value;
				}
				return params[key];
			},
			getAnchorObject : function() {
			    var params = location.hash.split('\/');
			    if (params.length>3) {
			    	return {'option':params[2],'value':params[3]};
			    }else{
			    	return {};
			    }
			},
			//空处理
			nullToEmpty:function(obj){
				if (obj==null||obj==undefined||obj=='null'){
					return '';
				}else{
					return obj;
				}
			},
			//打开窗口（非模式）
			openWin:function(url,name,width,height){
				var iTop = (window.screen.availHeight-30-height)/2;
				var iLeft = (window.screen.availWidth-10-width)/2;
				window.open(url,name,'height='+height+','+'innerHeight='+height+',width='+width+',innerWidth='+width+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
			},
			//打开窗口（模式）
			openDialogWin:function(url,width,height){
				return window.showModalDialog(url,window,"dialogWidth:"+width+"px;dialogHeight:"+height+"px;resizable:no;center:yes;location:no;status:no");
			},
			//去除空格
			trim:function(str){
				return str.trim();
			},
			//替换所有
			replaceAll:function(str,r1,r2){
				return str.replaceAll(r1,r2);
			},
			//是否是IE浏览器
			isIE:function(){
				return navigator.appName=='Microsoft Internet Explorer';
			},
			//str字符中是否以startStr字符开始
			startWith:function(str,startStr){
				return str.startWith(startStr);
			},
			//str字符中是否以endStr字符结束
			endWith:function(str,endStr){
				return str.endWith(endStr);
			},
			//根据name获取选中的对象 支持checkbox|radio
			getChecked:function(name){
				var objArr=[];
				var nameObj=document.getElementsByName(name);
				for(var i=0;i<nameObj.length;i++){
					if(nameObj[i].checked){
						objArr.push(nameObj[i]);
					}
				}
				return objArr;
			},
			//根据id获取选中的对象 支持select
			getSelected:function(id){
				var idObj=document.getElementById(id);
				return idObj.options[idObj.selectedIndex];
			},
			//获取日期
			getDate:function(){
				var date = new Date();
				var year = date.getFullYear();
				var month = parseInt(date.getMonth())+1;
				var day = parseInt(date.getDate());
				return year+'-'+String(month<=9?'0'+month:month)+'-'+String(day<=9?'0'+day:day);
			},
			//获取时间
			getTime:function(){
				var date = new Date();
				var hours = parseInt(date.getHours());
				var minutes = parseInt(date.getMinutes());
				var ss = date.getTime()%60000;
		    	ss =parseInt((ss-(ss%1000))/1000);
				return String(hours<=9?'0'+hours:hours)+':'+String(minutes<=9?'0'+minutes:minutes)+':'+String(ss<=9?'0'+ss:ss);
			},
			//获取日期时间
			getDateTime:function(){
				return this.getDate()+' '+this.getTime();
			},
			//获取星期
			getToday:function(){
				var day = new Array();
			    day[0] = "星期日";
			    day[1] = "星期一";
			    day[2] = "星期二";
			    day[3] = "星期三";
			    day[4] = "星期四";
			    day[5] = "星期五";
			    day[6] = "星期六";
			    var date = new Date();
			    return(day[date.getDay()]); 
			},
			//escape编码
			escape:function(str){
			    return escape(str); 
			},
			//unescape解码
			unescape:function(str){
				return unescape(str);
			},
			//encodeURIComponent编码
			encodeURIComponent:function(str){
				return encodeURIComponent(str);
			},
			//decodeURIComponent解码
			decodeURIComponent:function(str){
				return decodeURIComponent(str);
			},
			//整段URL编码
			encodeURI:function(url){
				return encodeURI(url);
			},
			//整段URL解码
			decodeURI:function(url){
				return decodeURI(url);
			},
			//存放cookie
			setCookie:function(name, value){
				var expdate = new Date ();
				expdate.setTime (expdate.getTime() + (24 * 60 * 60 * 1000 * 31));
				var argv = this.setCookie.arguments;
				var argc = this.setCookie.arguments.length;
				var path = (argc > 3) ? argv[3] : null;
				var domain = (argc > 4) ? argv[4] : null;
				var secure = (argc > 5) ? argv[5] : false;
				document.cookie = name + "=" + escape (value) +"; expires=" + expdate.toGMTString() +((path == null) ? "" : ("; path=" + path)) +((domain == null) ? "" : ("; domain=" + domain)) +((secure == true) ? "; secure" : "");
			},
			//存放cookie(天数)
			setCookie:function(name, value, days){
				var expdate = new Date ();
				expdate.setTime (expdate.getTime() + (24 * 60 * 60 * 1000 * days));
				var argv = this.setCookie.arguments;
				var argc = this.setCookie.arguments.length;
				var path = (argc > 3) ? argv[3] : null;
				var domain = (argc > 4) ? argv[4] : null;
				var secure = (argc > 5) ? argv[5] : false;
				document.cookie = name + "=" + escape (value) +"; expires=" + expdate.toGMTString() +((path == null) ? "" : ("; path=" + path)) +((domain == null) ? "" : ("; domain=" + domain)) +((secure == true) ? "; secure" : "");
			},
			//获取cookie
			getCookie:function(name){
				var arg = name + "=";
				var alen = arg.length;
				var clen = document.cookie.length;
				var i = 0;
				while (i < clen) {
					var j = i + alen;
					if (document.cookie.substring(i, j) == arg){
						return getCookieVal (j);
					}
					i = document.cookie.indexOf(" ", i) + 1;
					if (i == 0) {
						break; 
					}
				}
				return null;
			},
			//打开Word
			openWord:function(filePath){
				openFile('winword',filePath);
			},
			//打开Excel
			openExcel:function(filePath){
				openFile('excel',filePath);
			},
			//打开PPT
			openPowerPoint:function(filePath){
				openFile('powerpnt',filePath);
			},
			//打开Visio
			openVisio:function(filePath){
				openFile('visio',filePath);
			},
			//打开Pdf
			openPdf:function(filePath){
				openFile('pdf',filePath);
			},
			warningDialog:function(message,func){
				BootstrapDialog.show({
			        title: '系统提示',
			        message: '<span class="col-sm-2 fa fa-exclamation-circle"></span><span class="col-sm-10 message">'+message+'</span>',
			        type: BootstrapDialog.TYPE_WARNING,
			        cssClass:'message-dialog',
			        closeByBackdrop: false,
		        	closeByKeyboard: false,
			        draggable: true,
			        callback:function(result){
			        	alert(result);
			        },
			        buttons:[{
					    label : "确定",
					    cssClass : "btn btn-info",
					    action:function(dialog){
					    	if(func instanceof Function){
					    		func(dialog);
					    	}else{
						    	dialog.close();
					    	}
					    }
				    }]
			    });
			},
			questionDialog:function(message,func){
				BootstrapDialog.show({
			        title: '系统提示',
			        message: '<span class="col-sm-2 fa fa-question-circle-o text-info"></span><span class="col-sm-10 message">'+message+'</span>',
			        type: BootstrapDialog.TYPE_INFO,
			        cssClass:'message-dialog',
			        closeByBackdrop: false,
		        	closeByKeyboard: false,
			        draggable: true,
			        buttons:[{
					    label : "确定",
					    cssClass : "btn btn-info",
					    action:function(dialog){
					    	if(func instanceof Function){
					    		func(dialog,true);
					    	}else{
						    	dialog.close();
					    	}
					    }
				    },{
					    label : "取消",
					    cssClass : "btn btn-default",
					    action:function(dialog){
					    	if(func instanceof Function){
					    		func(dialog,false);
					    	}else{
						    	dialog.close();
					    	}
					    }
				    }]
			    });
			},
			promptSuccessDialog:function(message){
				var prompt = $('<div class="promptSuccess"><span class="promptInfo"><span class="fa fa-check"></span>&nbsp;'+message+'</span></div>');
				if($('.mainPrompt').length==0||$('mainPrompt')==undefined){
					$('body').append('<div class="mainPrompt"></div>');
				}
				$('.mainPrompt').append(prompt);
				prompt.addClass("current").animate({'margin-top':'1px'},500); 
				setTimeout(function(){prompt.fadeOut(2000,function(){prompt.remove();})},2000);
			},
			promptErrorDialog:function(message){
				var prompt = $('<div class="promptError"><span class="promptInfo"><span class="fa fa-exclamation"></span>&nbsp;&nbsp;'+message+'</span></div>');
				if($('.mainPrompt').length==0||$('mainPrompt')==undefined){
					$('body').append('<div class="mainPrompt"></div>');
				}
				$('.mainPrompt').append(prompt);
				prompt.addClass("current").animate({'margin-top':'1px'},500); 
				setTimeout(function(){prompt.fadeOut(2000,function(){prompt.remove();})},2000);
			},
			promptNoticeDialog:function(message){
				var prompt = $('<div class="promptNotice"><span class="promptInfo"><span class="fa fa-exclamation"></span>&nbsp;&nbsp;'+message+'</span></div>');
				if($('.mainPrompt').length==0||$('mainPrompt')==undefined){
					$('body').append('<div class="mainPrompt"></div>');
				}
				$('.mainPrompt').append(prompt);
				prompt.addClass("current").animate({'margin-top':'1px'},500); 
				setTimeout(function(){prompt.fadeOut(2000,function(){prompt.remove();})},2000);
			},
			validate:function(id,option){
				var defaultOptions = {
						highlight:function(element, errorClass, validClass){
							var $parent = $(element).parent();
							$parent.addClass("has-feedback").addClass("has-error");
							$( element ).parent().find('i.form-control-feedback').remove();
						},
						unhighlight:function(element, errorClass){
							var $parent = $(element).parent();
							$parent.removeClass("has-feedback").removeClass("has-error");
						},
						success:function(error,element){
							var $parent = $(element).parent();
							$parent.removeClass("has-feedback").removeClass("has-error");
						},
						errorPlacement: function(error, element) {
							var $parent = $(element).parent();
							if($parent.hasClass("input-group")){
								$parent = $parent.parent();
							}
							error.appendTo($parent);
						},
						errorElement: 'small',
						errorClass:"text-danger"
					};
				var options = $.extend({},defaultOptions,option);
				return $("#"+id).validate(options);
			},
			datecustomRangePicker:function(){
				$(".dateCustom-wrapper .dateCustom-remove").click(function(){
					$(this).parent().find(".dateCustom").val("");
				});
				var datePicker = $(".dateCustom-wrapper .dateCustom").daterangepicker({
					autoUpdateInput: false,
					timePicker: true,
			    	timePicker24Hour: true,
					locale: {
			            format: 'YYYY-MM-DD HH:mm',
			            applyLabel: "确定",
			    		cancelLabel: "取消"
			        }
				}).on('apply.daterangepicker',function(ev, picker){
					if(picker.singleDatePicker){
						$(this).val(picker.startDate.format(picker.locale.format));
					}else{
						$(this).val(picker.startDate.format(picker.locale.format)+' ~ '+picker.endDate.format(picker.locale.format));
					}
				});
				return datePicker;
			}
		};
});
