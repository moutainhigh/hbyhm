
var my_loaded=function (obj_area){
	$(':input[maxlength]',obj_area).each(function (){
		$(this).attr('maxleng_base',$(this).attr('maxlength'));
		$(this).keyup(function (){
			var o=$(this);
			setTimeout(function (){
				var zm=o.val().replace(/[^a-zA-Z\d\s\'\"\\\/\?\.]+/ig,"");
				var max=o.attr('maxleng_base');
				var max2=max-0+intval((strlen(zm)/2));
				if(max*2>max2) max2=max*2;
				o.attr('maxlength',max2);
				var diff=o.attr('maxlength')-o.val().length;
				$('#'+o.attr('name')+'_leng').html(diff>=0?diff:0);
			},10);
		}).keyup();//.keypress();
	})
	
	//$('textarea.html:not(.all)',obj_area).xheditor({tools:'simple',skin:'nostyle'});
	//$('textarea.html.all',obj_area).xheditor({forcePtag:true,upLinkUrl:"../js/xheditor/upload.php?immediate=1",upLinkExt:"zip,rar,txt",upImgUrl:"../js/xheditor/upload.php?immediate=1",upImgExt:"jpg,jpeg,gif,png",upFlashUrl:"../js/xheditor/upload.php?immediate=1",upFlashExt:"swf",upMediaUrl:"../js/xheditor/upload.php?immediate=1",upMediaExt:"avi",skin:'nostyle'});
	//$('input.color').color_select();
	
	$('textarea.html',obj_area).each(function(){
		var myid = $(this).attr("id");
		if(myid){
			UE.getEditor(myid);
		}
	});
	
	$(':input',obj_area).keydown(function (e){
		try{
			if ($.browser.msie) {  // 判断浏览器
				var code=e.keyCode;
			} else {
				var code=e.which;
			}
			if(code==13){
				if($('#ajax_msg_btn').length>0){
					$('#ajax_msg_btn').click();
					return false;
				}
			}
			return true;
		}catch(e){
			return true;
		}
	})

	$('input.numonly',obj_area).keydown(function (e){
		var code=0;
		var noie=0;
		if (/msie/.test(navigator.userAgent.toLowerCase())) {  // 判断浏览器
			code=e.keyCode;
		} else {
			code=e.which;
			noie=17
		}
		var codes=[8,9,13,37,39,110,190];
		if ( (code>47 && code<58) || (code>95 && code<106) || in_array(code,codes) || code==noie ) {
			return true; 
		} else { 
			return false; 
		}
	}).focus(function() {
		this.style.imeMode='disabled';   // 禁用输入法,禁止输入中文字符
	}).bind("paste",function(){ return false; });
	
	try{
		$(':input.daterange').daterangepicker();
		$(':input.date',obj_area).datepicker();
		$(':input.datetime',obj_area).datetimepicker({format:"yyyy-mm-dd hh:ii:ss"});
		$(".select2").select2();
		$(".select2-tags").select2({
		    tags: true
		});
		//$(':input.Wdate',obj_area).click(function(){WdatePicker();});
	}catch (e){
	}
}
function succmsg(msg){
	var url='';
	var note='';
	var width='';
	var height='';
	if(arguments[1] && arguments[2] && is_numeric(arguments[1]) && is_numeric(arguments[2])){
		width=arguments[1];
		height=arguments[2];
	}else{
		if(arguments[1]) note=arguments[1];
		if(arguments[2]) url=arguments[2];
	}
	ajaxmsg(msg,note,'succ',url,width,height);
}
function errmsg(msg){
	var url='';
	var note='';
	var width='';
	var height='';
	if(arguments[1] && arguments[2] && is_numeric(arguments[1]) && is_numeric(arguments[2])){
		width=arguments[1];
		height=arguments[2];
	}else{
		if(arguments[1]) note=arguments[1];
		if(arguments[2]) url=arguments[2];
	}
	ajaxmsg(msg,note,'error',url,width,height);
}
function ajaxmsg(msg,note,icon,url,width,height){
	if(!width) width=500;
	if(!height) height=250;
	var use_modal=url?'true':'flase';
	alert(msg);
}
$(function (){
	var fixHelper = function(e, ui) {
		ui.children().each(function() {
					$(this).width($(this).width());
				});
		return ui;
	}; 
	var sortable_stop=function (oldobj,newobj){
		if(sort_cn){
			jQuery.post('command.php?cn='+sort_cn+'&do=sort',{'oids[]':oldobj,'nids[]':newobj},function (){ });
		}
	} 
	if($('body').sortable){
		$('table.sortable tbody').sortable({
			handle : 'img.move',
			helper : fixHelper,
			placeholder : 'ui-state-highlight',
			forcePlaceholderSize : true,
			start : function() {
				OldSortIds = [];
				jQuery('img[id^=sortid]').each(function() {
					OldSortIds.push(jQuery(this).attr('id').replace('sortid_', ''));
				});
			},
			stop : function() {
				var NewSortIds = [];
				jQuery('img[id^=sortid]').each(function() {
					NewSortIds.push(jQuery(this).attr('id').replace('sortid_', ''));
				});
				if (typeof(sortable_stop) == "function")
					sortable_stop(OldSortIds, NewSortIds);
			}
		}).disableSelection();
	
		$('ul.sortable').sortable({
			handle : 'img.move',
			placeholder : 'ui-state-highlight',
			forcePlaceholderSize : true,
			start : function() {
				OldSortIds = [];
				jQuery('img[id^=sortid]').each(function() {
					OldSortIds.push(jQuery(this).attr('id').replace('sortid_', ''));
				});
			},
			stop : function() {
				var NewSortIds = [];
				jQuery('img[id^=sortid]').each(function() {
					NewSortIds.push(jQuery(this).attr('id').replace('sortid_', ''));
				});
				if (typeof(sortable_stop) == "function")
					sortable_stop(OldSortIds, NewSortIds);
			}
		});
	}
	
	try{
		$.datepicker.setDefaults({
			dateFormat: 'yy-mm-dd',
			dayNames: ['日','一','二','三','四','五','六'],
			dayNamesMin: ['日','一','二','三','四','五','六'],
			dayNamesShort: ['日','一','二','三','四','五','六'],
			monthNames: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			monthNamesShort: ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
			prevText: '上一月',
			nextText: '下一月',
			constrainInput: true,
			showMonthAfterYear: true
		});
	}catch (e){
	}

	my_loaded($('body'));
})
function clearKw(obj){
	if(obj.val().indexOf('关键字')>=0 || obj.val().indexOf('关键词')>=0){
		obj.val('');
	}
}
/**
 * 全选
 */
function check_all(id,obj){
	$(id).find('input:checkbox:not(:disabled)').attr('checked',$(obj).is(':checked'));
}
/**
 * ajax效果
 */
function ajax_begin() {
    //IE6位置
    if (!window.XMLHttpRequest) {
        $("#targetFixed").css("top", $(document).scrollTop() + 2);	
    }
    //创建半透明遮罩层
    if(!$('#targetFixed').length){
        $('<span id="targetFixed" style="height:25px; padding:1px; position:fixed; _position:absolute; top:0; right:0;"></span>').prependTo($("body"));
    }
    if (!$("#overLay").size()) {
        $('<div id="overLay"></div>').prependTo($("body"));
        $("#overLay").css({
            width: "100%",
            backgroundColor: "#000",
            opacity: 0.2,
            position: "absolute",
            left: 0,
            top: 0,
            zIndex: 99
        }).height($(document).height());
    }
    //显示操作提示，最关键核心代码
    $("#targetFixed").powerFloat({
        eventType: null,
        targetMode: "doing",	
        target: "正在处理中...",
        position: "1-2"
    });
}
function ajax_end(){
    $("#overLay").remove();
    $.powerFloat.hide();
}
/**
 * 文件上传
 */
function fileup(id,succfun){
	if(arguments[2]) errfun=arguments[2];
	else errfun=false;
	
	jQuery.ajaxFileUpload({
		url:'ajax.php?do=fileup&cn=',
		secureuri:false,
		fileElementId:id,
		dataType: 'json',
		begin: function(){
			jQuery('#'+id).after('<span id="file_upload_tip">文件上传中, 请耐心等待</span>');
			jQuery('#'+id).hide();
		},
		complete: function(){
			jQuery('#file_upload_tip').remove();
			jQuery('#'+id).show();
		},
		success: function (data, status){
			if(data.url){
				succfun(data);
			}else{
				jQuery('#file_upload_tip').remove();
				jQuery('#'+id).show();
				if(errfun){
					errfun();
				}else{
					alert(data.error);
				}
			}
			$('#'+id).off().on('change',function (){
				fileup(id,succfun,errfun);
			})
		},
		error: function (data, status, e){
			jQuery('#file_upload_tip').remove();
			jQuery('#'+id).show();
			if(errfun){
				errfun();
			}else{
				alert('文件上传错误');
			}
			$('#'+id).off().on('change',function (){
				fileup(id,succfun,errfun);
			})
		}
	});
	return false;
}
function editFun(obj) {
	var fieldName = '';
	if(arguments[1]) fo=arguments[1];
	else fo=$('body');
	$(':input',fo).each( function() {
		fieldName = $(this).attr('name');
		if(fieldName && fieldName.indexOf('[')>0&&!obj[fieldName]){
			var objtmp=obj;
			var isobj=false;
			$(fieldName.split('[')).each(function (i,n){
				var tmpname=n.replace(']','');
				if(tmpname!=''&&objtmp[tmpname]){
					objtmp=objtmp[tmpname];
				}else{
					isobj=true;
				}
			})
			if(!isobj&&objtmp!='') obj[fieldName]=objtmp;
		}
		if (fieldName && obj[fieldName]) {
			switch ($(this).attr('type')) {
			case 'radio':
				$('input:radio[name="'+fieldName+'"][value="'+obj[fieldName]+'"]',fo).attr('checked', 'checked');
				break;
			case 'checkbox':
			case 'file':
			case 'hidden':
				break;
			default:
				$(this).val(obj[fieldName]);
			}
		}
	});
	$('input:checkbox',fo).each( function() {
		if($(this).attr('name')){
			fieldName = $(this).attr('name').replace('[]','');
			if(fieldName && fieldName.indexOf('[')>0&&!obj[fieldName]){
				var objtmp=obj;
				var isobj=false;
				$(fieldName.split('[')).each(function (i,n){
					var tmpname=n.replace(']','');
					if(tmpname!=''&&objtmp[tmpname]){
						objtmp=objtmp[tmpname];
					}else{
						isobj=true;
					}
				})
				if(!isobj&&objtmp!='') obj[fieldName]=objtmp;
			}
			if(fieldName==$(this).attr('name')){
				if(fieldName && obj[fieldName]==$(this).attr('value')){
					$(this).attr('checked', 'checked');
				}
			}else{
				var chkObj=$(this);
				var chkVal=obj[fieldName];
				$.each(chkVal,function (key,val){
					if(chkObj.attr('value')==val){
						chkObj.attr('checked', 'checked');
					}
				})
			}
		}
	});
}


var objacl_fun={};
function objacl(obj1,obj2,url,def1,def2){
	if(typeof(obj1)!='object' || !obj1.jquery){
		if(jQuery(obj1).length>0) var obj1=jQuery(obj1);
		else var obj1=jQuery('select[name='+obj1+']');
	}
	if(typeof(obj2)!='object' || !obj2.jquery){
		if(jQuery(obj2).length>0) var obj2=jQuery(obj2);
		else var obj2=jQuery('select[name='+obj2+']');
	}
	var obj1_id=obj1.attr('id').replace('#','');
	obj1.unbind('change',objacl_fun[obj1_id]);
	objacl_fun[obj1_id]=function (){
		obj2.find('option[value!="0"]:not([value=""])').remove();
		if(obj1.val()){
			jQuery.get(url+obj1.val(),function (opt){
				//if(opt){
					obj2.find('option[value!="0"]:not([value=""])').remove();	//必须再清空一次,否则一个select绑定两次的时候,第一个会重复
					obj2.append(jQuery(opt));
					if(def2) obj2.val(def2);
					try{
						obj2.msDropDown()
					}catch(e){
					}
					obj2.change();
				//}
			})
		}
	}
	obj1.bind('change',objacl_fun[obj1_id]);
	if(def1&&obj1.find('option[value="'+def1+'"]').length>0) obj1.val(def1);
	obj1.change();
}
