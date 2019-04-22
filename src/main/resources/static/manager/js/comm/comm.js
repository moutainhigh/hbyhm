/**
 * 将select用powerFloat的形式显示出来
 */
function selectPowerFloat(objid) {
	if ($(objid).is('select')) {
		var optnow = $(objid).find('option:selected');
		if (optnow.length < 1) {
			optnow = $(objid).find('option:first');
		}
		if (optnow.length > 0) {
			var optstr = optnow.text();
		}
		optwidth = $(objid).width();
		obja = $('<a class="select_ui" href="javascript:void(null)" style="width: '
				+ optwidth + 'px" id="house_zone_quick">' + optstr + '</a>');
		var targetobj = [];
		$(objid).find('option').each(function() {
					var item_val = $(this).val();
					var item_str = $(this).text();
					targetobj.push({
								'objid' : objid,
								'objval' : item_val,
								'text' : item_str
							});
				})
		$(objid).after(obja).hide();
		obja.powerFloat({
					eventType : "click-out",
					position : "4-1",
					offsets : {
						y : -1
					},
					width : "inherit",
					reverseSharp : "false",
					target : targetobj,
					targetMode : "list"
				});
	}
}
function toright(lobj, robj) {
	robj.append(lobj.find('option:selected'));
	robj.find('option').attr('selected', 'selected');
}
function toleft(lobj, robj) {
	lobj.append(robj.find('option:selected'));
}
/**
 * 文件上传
 */
function fileup(id, succfun) {
	if (arguments[2])
		errfun = arguments[2];
	else
		errfun = false;

	jQuery.ajaxFileUpload({
				url : '/ajax.php?do=fileup&cn=',
				secureuri : false,
				fileElementId : id,
				dataType : 'json',
				begin : function() {
					jQuery('#' + id)
							.after('<span id="file_upload_tip">文件上传中, 请耐心等待</span>');
					jQuery('#' + id).hide();
				},
				complete : function() {
					jQuery('#file_upload_tip').remove();
					jQuery('#' + id).show();
				},
				success : function(data, status) {
					if (data.url) {
						succfun(data);
					} else {
						jQuery('#file_upload_tip').remove();
						jQuery('#' + id).show();
						if (errfun) {
							errfun();
						} else {
							alert(data.error);
						}
					}
					$('#' + id).off().on('change', function() {
								fileup(id, succfun, errfun);
							})
				},
				error : function(data, status, e) {
					jQuery('#file_upload_tip').remove();
					jQuery('#' + id).show();
					if (errfun) {
						errfun();
					} else {
						alert('文件上传错误');
					}
					$('#' + id).off().on('change', function() {
								fileup(id, succfun, errfun);
							})
				}
			});
	return false;
}
function getTimeDiff(dt1, dt2) {
	var endint = Date.parse(dt1) / 1000;
	var nowint = Date.parse(dt2) / 1000;
	var diffs = endint - nowint;
	var diffd = parseInt(diffs / (60 * 60 * 24));
	diffs -= diffd * (60 * 60 * 24);
	var diffh = parseInt(diffs / (60 * 60));
	diffs -= diffh * (60 * 60);
	var diffi = parseInt(diffs / (60));
	diffs -= diffi * (60);
	if (arguments[2]) {
		if (diffd < 10)
			diffd = '0' + diffd;
		if (diffh < 10)
			diffh = '0' + diffh;
		if (diffi < 10)
			diffi = '0' + diffi;
		if (diffs < 10)
			diffs = '0' + diffs;
	}
	return {
		'd' : diffd,
		'h' : diffh,
		'i' : diffi,
		's' : diffs
	};
}
function objacl(obj1, obj2, url, def1, def2) {
	if (typeof(obj1) != 'object' || !obj1.jquery) {
		if (jQuery(obj1).length > 0)
			var obj1 = jQuery(obj1);
		else
			var obj1 = jQuery('select[name=' + obj1 + ']');
	}
	if (typeof(obj2) != 'object' || !obj2.jquery) {
		if (jQuery(obj2).length > 0)
			var obj2 = jQuery(obj2);
		else
			var obj2 = jQuery('select[name=' + obj2 + ']');
	}

	obj1.change(function() {
				obj2.find('option[value!="0"]:not([value=""])').remove();
				if (jQuery(this).val()) {
					jQuery.get(url + jQuery(this).val(), function(opt) {
								if (opt) {
									obj2
											.find('option[value!="0"]:not([value=""])')
											.remove(); // 必须再清空一次,否则一个select绑定两次的时候,第一个会重复
									obj2.append(jQuery(opt));
									if (def2)
										obj2.val(def2);
									try {
										obj2.msDropDown()
									} catch (e) {
									}
									obj2.change();
								}
							})
				}
			});
	// 重新加载 dd.js
	// obj2.change(function (){
	// try{
	// $("select[id]").msDropDown();
	// }catch(e){
	// }
	// })
	if (def1 && obj1.find('option[value="' + def1 + '"]').length > 0)
		obj1.val(def1);
	obj1.change();
}

function isint(str) {
	return /^\d+$/.test(str);
}
function isfloat(str) {
	return /^(\+|-)?\d+($|\.\d+$)/.test(str);
}
function isdate(str) {
	return /^(\d{4})([/|-])(\d{1,2})([/|-])(\d{1,2})$/.test(str);
}
function istel(str) {
	return /^(\d{11})$/.test(str);
}
function isemail(str) {
	return /^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/
			.test(str);
}
function urlParam(k) { // 获得地址栏参数
	var URLParams = new Array();
	var aParams = document.location.search.substr(1).split('&');
	for (i = 0; i < aParams.length; i++) {
		var aParam = aParams[i].split('=');
		URLParams[aParam[0]] = aParam[1];
	}
	if (URLParams[k])
		return URLParams[k];
	else
		return null;
}

/** 弹出窗的调用 * */
function openWin(url, width, height, type) {
	if (!type)
		type = "ajax";
	$.fancybox({
				'href' : url,
				'width' : width,
				'height' : height,
				'autoScale' : false,
				'transitionIn' : 'none',
				'transitionOut' : 'none',
				'type' : type
			})
}