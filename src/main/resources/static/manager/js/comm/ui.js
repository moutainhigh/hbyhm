function myalert(msg, o) {
	if (o) {
		$(o).tooltip({
					title : msg,
					placement : 'bottom',
					trigger : "click"
				});
		setTimeout(function() {
					$(o).tooltip('hide');
				}, 500);
	} else {
		alert(msg);
	}
}
function html_load_succ(o) {
	$('div.select_div', o).each(function() {
				var div = $(this);
				$('a', div).click(function() {
							var d = $(this).parents('div.select_div:first');
							var k = $(this).attr('tabindex');
							var v = $(this).html();
							$('input:hidden', d).val(k);
							$('button', d).html(v
									+ '<span class="caret"></span>');
						})
				var v = $('input:hidden', div).val();
				if (v) {
					$('a[tabindex=' + v + ']', div).click();
				} else {
					$('a:first', div).click();
				}
			})
}
jQuery(function() {
			html_load_succ($('body'));
		})
