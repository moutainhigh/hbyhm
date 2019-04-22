jQuery.fn.extend({
	combo : function(opt) {
		var click_fun = null;
		if (arguments[1] && typeof(arguments[1]) == 'function') {
			click_fun = arguments[1];
		}
		var ajax_timeout = null;
		var io_nowval = null;
		var float_div = null;
		this.each(function() {
			var io = $(this);
			io.attr('autocomplete', 'off');
			if (io.parent().is(':not(span.combo_float_span)')) {
				var io_parent_div = $('<span class="combo_float_span" style="position: relative;"></span>');
				io_parent_div.hover(function() {
							clearTimeout(io.ajax_timeout);
						}, function() {
							io.ajax_timeout = setTimeout(function() {
								io.next('div.combo_float').hide();
								io.next('div.combo_float').attr('is_show', '0');
							}, 1000);
						})
				io.after(io_parent_div);
				io_parent_div.append(io);
				var div_width = 350;
				if (io.width() > 350) {
					div_width = io.width();
				}
				io
						.after('<div class="combo_float" style="position: absolute; display: none; z-index: 10000; left: 0px; top: 0px;"><div class="float_list_box" style="display: block; width: '
								+ div_width
								+ 'px; position: absolute; left: 0; top: 0; z-index: 10001;"></div></div>')
				// io.after('<div class="combo_float" style="position: relative;
				// display: none; z-index: 10000;"><div class="float_list_box"
				// style="display: block; width: '+div_width+'px; position:
				// absolute; left: 0; top: 0; z-index: 10001;"></div></div>')
			}
			$(this).unbind('keydown').keydown(function(e) {
						var code = (e.keyCode ? e.keyCode : e.which);
						var combo_float_div = io.next('div.combo_float');
						if (code == 13) {
							if (combo_float_div.find('li.on').length > 0) {
								combo_float_div.find('li.on').click();
								return false;
							}
							return true;
						} else if (code == 9) {
							$('div.combo_float').hide();
							$('div.combo_float').attr('is_show', '0');
						}
					}).unbind('keyup').keyup(function(e) {
				var code = (e.keyCode ? e.keyCode : e.which);
				var combo_float_div = io.next('div.combo_float');
				clearTimeout(this.ajax_timeout);
				this.ajax_timeout = setTimeout(function() {
					if (code == 13) {
						return false;
					} else if (io.val() != io.io_nowval || code == 40
							|| code == 38) {
						if ((code == 40 || code == 38)
								&& combo_float_div.attr('is_show') == '1') {
							var liobj = null
							if (code == 40) {
								if (combo_float_div.find('li.on').length > 0) {
									liobj = combo_float_div.find('li.on')
											.next('li');
								} else {
									liobj = combo_float_div.find('li:first');
								}
							}
							if (code == 38) {
								if (combo_float_div.find('li.on').length > 0) {
									liobj = combo_float_div.find('li.on')
											.prev('li');
								} else {
									liobj = combo_float_div.find('li:last');
								}
							}
							if (liobj != null && liobj.length > 0) {
								$(liobj).mouseover();
							}
						} else {
							io.io_nowval = io.val();
							$.get(opt.url + io.val(), function(jstr) {
								html = gethtml(jstr, io);
								if (html) {
									combo_float_div
											.children('div.float_list_box')
											.html(html);
									combo_float_div.find('li').mouseover(
											function() {
												$(this).siblings()
														.removeClass('on').css(
																{
																	'backgroundColor' : '#FFF'
																});
												$(this).addClass('on').css({
													'backgroundColor' : '#EFF9E6'
												});
											}).click(function(e) {
										io.val($(this).text());
										$(this).parent().remove();
										$('div.combo_float').hide();
										$('div.combo_float').attr('is_show',
												'0');
										if (click_fun) {
											click_fun($(this).attr('dic_key'),
													$(this).text(), io, e);
										}
									});
									var x = io.position().left;
									var y = io.position().top
											+ io.outerHeight(true) - 2;
									combo_float_div.css({
												'left' : x + 'px',
												'top' : y + 'px'
											});
									combo_float_div.show();
									combo_float_div.attr('is_show', '1');
								} else {
									$('div.combo_float').hide();
									$('div.combo_float').attr('is_show', '0');
								}
							});
						}
					}
				}, 10);
			})
		})
		var gethtml = function(jstr, io) {
			var ioname = io.attr('name');
			var html = '';
			if (jstr) {
				eval('jo=' + jstr);
				if (jo.length > 0) {
					html += '<ul class="float_list_ul">';
					$(jo).each(function(i, o) {
						html += '<li dic_key="' + o.id + '" id="' + ioname
								+ '_' + o.id + '" style="text-align: left;">'
								+ o.name + '</li>';
					})
					html += '</ul>';
				}
			}
			return html;
		}
	}
})