$(document).on("pageInit", function(e, pageId, $page) {
	alert(1);
	//aside_close();
	var href=window.location.href;
	$('nav a').each(function (){
		var a_href=$(this).attr('href');
		if(href.indexOf(a_href)>0){
			$('nav a').removeClass('active');
			$(this).addClass('active');
		}
	})
	$('[data-aside]',$page).click(function (){
		var u=$(this).attr('data-aside');
		var c=$(this).attr('data-aside-dont-close');
		aside_load(u,c?true:false);
	})
	document_load($page);
	
	if($.router.nowPageTitle){
		document.title = $.router.nowPageTitle;
		var $iframe = $("<iframe style='display:none;' src='/images/none.png'></iframe>");
		$iframe.bind('load',function() {
			setTimeout(function() {
				$iframe.off('load').remove();
			}, 0);
		});
	}
	$('body').append($iframe)

	if($('#page_items').length>0){
		var next_page_loading = false;
		$($page).on('infinite', function() {
			if (next_page_loading) return;
			next_page_loading = true;
			setTimeout(function() {
				next_page_loading = false;
				next_page();
			}, 1000);
		})
	}
	
	$(".content").on('scroll',function(i){
		if($('.content').scrollTop()>100){
			$('.GoTop').css("display","block");
		}else{
			$('.GoTop').css("display","none");
		}
    });
	$('.GoTop').click(function (){
		$('.content').scrollTop(0);
	})
});