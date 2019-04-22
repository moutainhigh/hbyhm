/*
 * @Description : file content
 * @Author      : tt
 * @Date        : 2016-02-05 13: 28: 54
 * @LastEditTime: 2019-01-29 13:43:53
 * @LastEditors : tt
 */
function num_sub_obj(obj) {
    var ipt = $(obj).next();
    if (ipt.is('input:text')) {
        var val = ipt.val();
        var isnum = /^\d+$/;
        if (val && isnum.test(val) && val > 1) {
            ipt.val(val - 1);
        } else {
            ipt.val(1);
        }
        ipt.blur();
    }
}

function num_add_obj(obj) {
    var ipt = $(obj).prev();
    if (ipt.is('input:text')) {
        var val = ipt.val();
        var isnum = /^\d+$/;
        if (val && isnum.test(val)) {
            ipt.val(val - (-1));
        } else {
            ipt.val(1);
        }
        ipt.blur();
    }
}

function ajax_edit(id, field, val) {
    var cn = '';
    var succfun = null;
    if (arguments[4]) {
        cn = arguments[4];
        succfun = arguments[3];
    } else if (arguments[3]) {
        if (typeof(cn) == 'function') {
            succfun = arguments[3];
        } else {
            cn = arguments[3];
        }
    } else if (page_cn) {
        cn = page_cn;
    }
    var param = {};
    eval('param.' + field + '="' + val + '"');
    if (!id) {
        var list_ids_param = list_get_ids();
        if (list_ids_param == "") {
            myalert('请选择您要操作的信息');
            return false;
        }

        list_ids_param = "ids=" + list_ids_param;

    } else {
        var list_ids_param = 'id=' + id;
    }
    jQuery.post('command?cn=' + cn + '&sdo=edit&' + list_ids_param, param, function(jstr) {
        if (succfun) {
            succfun(jstr);
        } else {
            window.location.reload();
        }
    });
}

jQuery(function() {
    $('form').each(function() {
        var this_form = $(this);
        this_form.find(':text,textarea').each(function() {
            var alt = $(this).attr('placeholder');
            if (alt) {
                $(this).blur(function() {
                    if ($(this).val() == '') {
                        $(this).val(alt);
                    }
                }).focus(function() {
                    if ($(this).val() == alt) {
                        $(this).val('');
                    }
                }).blur();
            }
        })
        this_form.submit(function() {
            this_form.find(':text,textarea').each(function() {
                var alt = $(this).attr('placeholder');
                if (alt) {
                    if ($(this).val() == alt) {
                        $(this).val('');
                    }
                }
            })
            return true;
        })

    })

    jQuery('a[id^=delid]').click(function() {
        var thisobj = jQuery(this);
        var succfun = jQuery(this).attr('succfun');
        var id = thisobj.attr('id').replace('delid_', '');
        var cn = '';
        if (thisobj.attr('href') == '#' && page_cn) {
            cn = page_cn;
        } else {
            cn = thisobj.attr('href').replace('#', '');
        }
        if (cn && id && confirm('您确定要删除这个项目吗?')) {
            jQuery.getJSON('command?cn=' + cn + '&sdo=del&id=' + id, function(jo) {
                if (succfun) {
                    eval(succfun.replace(/jQuery\(this\)/g, 'thisobj'));
                } else {
                    if (jo.msg) {
                        alert(jo.msg);
                    }
                    window.location.reload();
                }
            });
        }
    });
    jQuery('a[id^=undelid]').click(function() {
        var thisobj = jQuery(this);
        var succfun = jQuery(this).attr('succfun');
        var id = thisobj.attr('id').replace('undelid_', '');
        var cn = '';
        if (thisobj.attr('href') == '#' && page_cn) {
            cn = page_cn;
        } else {
            cn = thisobj.attr('href').replace('#', '');
        }
        if (cn && id) {
            jQuery.get('command?cn=' + cn + '&sdo=undel&id=' + id, function(jo) {
                if (succfun) {
                    eval(succfun.replace(/jQuery\(this\)/g, 'thisobj'));
                } else {
                    if (jo.msg) {
                        alert(jo.msg);
                    }
                    window.location.reload();
                }
            });
        }
    });
    //jQuery(':input.date').datepicker();
});
$.fn.smartFloat = function() {
    var position = function(element) {
        var top = element.position().top,
            pos = element.css("position");
        $(window).scroll(function() {
            var scrolls = $(this).scrollTop();
            if (scrolls > top) {
                if (window.XMLHttpRequest) {
                    element.css({
                        position: "fixed",
                        top: 0
                    });
                } else {
                    element.css({
                        top: scrolls
                    });
                }
            } else {
                element.css({
                    position: pos,
                    top: top,
                    margin: "0 auto"
                });
            }
        });
    };

    return $(this).each(function() {
        position($(this));
    });
};