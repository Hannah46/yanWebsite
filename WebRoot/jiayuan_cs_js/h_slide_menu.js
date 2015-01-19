$(function(){		
	//设计案例切换
	$('.title-list li').mouseover(function(){
		var liindex = $('.title-list li').index(this);
		$(this).addClass('on').siblings().removeClass('on');
		$('.menu-wrap div.menu').eq(liindex).fadeIn(0).siblings('div.menu').hide();
		var liWidth = $('.title-list li').width();
		$('.h_slide_menu .title-list p').stop(false,true).animate({'left' : liindex * liWidth + 'px'}, 220)});
	
	//设计案例hover效果
	$('.menu-wrap .menu li').hover(function(){
		$(this).css("border-color","#ff6600");
		$(this).find('p > a').css('color','#ff6600');
	},function(){
		$(this).css("border-color","#fafafa");
		$(this).find('p > a').css('color','#666666');
	});
	});