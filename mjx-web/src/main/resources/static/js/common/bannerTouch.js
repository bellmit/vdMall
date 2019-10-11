$.fn.bannerTouch = function (opt) {
    
    //console.log(opt);
    opt.paddingCon = opt.paddingCon || 0;
    opt.duration = opt.duration || 300;
    opt.autoTime = opt.autoTime || 5000;
    
    
    //console.log(opt);
    
    
    var iTouchX = 0; // 手指按下初始化 X 方向值
    var moveVal = 0; // 手指移动 X 方向值
    
    var diffVal = 0; //（移动 - 按下）的差值
    var iSlideL = 0; // 拖动的元素与浏览器左面的距离
    var screenW = screen.width - opt.paddingCon; //拖动的元素的宽度
    var halfSceen = screenW / 2;//屏幕宽的一般（控制滑动区域）
    var initSliderW = screenW * 0.1;
    
    var touchInitVal = 0;
    var touchEndVal = 0;
    var endVal = 0;
    var num = 0;
    
    
    var timer = null;//定时器
    
    
    var newTime = new Date(); //防快速拖动的时间间隔初始时间值
    var onOff = false; //touchmove开关
    
    //console.log(screenW);
    var oListLen = $(this).children('*').length - 2;
    
    //初始化元素轮播位置
    $(this).css('transform', 'translateX(' + (-screenW) + 'px)');
    //手指触发事件
    $(this).on('touchstart', iTouchStart);
    $(this).on('touchmove', iTouchMove);
    $(this).on('touchend', iTouchEnd);
    
    
    //手机滑动开始事件
    function iTouchStart(ev) {
        clearInterval(timer);
        //如果手指按下拖动的间隔大于 动画时间 + 80 ms，触发拖动事件
        if (new Date() - newTime > (opt.duration + 30)) {
            touchInitVal = iTouchX = ev.changedTouches[0].clientX;
            //iSlideL = $(this).offset().left;
            
            if (num == 0) {
                $(this).css('transform', 'translateX(' + (-screenW) + 'px)');
            } else if (num == -1) {
                num = oListLen - 1;
                $(this).css('transform', 'translateX(' + (-screenW * oListLen) + 'px)');
            }
            
            iSlideL = $(this).offset().left;
            newTime = new Date();
            onOff = true;
            
            
        }
    }
    
    
    //手机滑动中事件
    function iTouchMove(ev) {
        if (onOff) {
            moveVal = ev.changedTouches[0].clientX;
            diffVal = moveVal - iTouchX + iSlideL;
            $(this).css('transform', 'translateX(' + diffVal + 'px)')
        }
    }
    
    
    //手指滑动结束事件
    function iTouchEnd(ev) {
        if (onOff) {
            if (new Date() - newTime >  30) {
                autoplay();
                onOff = false;
                touchEndVal = ev.changedTouches[0].clientX;
                
                var prop = (iSlideL + ev.changedTouches[0].clientX - iTouchX) / screenW;
                endVal = Math.round(prop) * screenW;
                
                if (touchEndVal - touchInitVal < 0) {
                    if (Math.abs(touchEndVal - touchInitVal) >= initSliderW && Math.abs(touchEndVal - touchInitVal) <= halfSceen) {
                        num++;
                        num %= oListLen;
                        //console.log(num);
                        endVal -= screenW;
                        
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                        $(this).siblings().find('span').eq(num).addClass('active').siblings().removeClass('active');
                    } else if (Math.abs(touchEndVal - touchInitVal) > halfSceen) {
                        num++;
                        num %= oListLen;
                        //console.log(num,-(num)*screenW);
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                        $(this).siblings().find('span').eq(num).addClass('active').siblings().removeClass('active');
                    } else {
                        //endVal -= screenW;
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                    }
                } else {
                    if (Math.abs(touchEndVal - touchInitVal) >= initSliderW && Math.abs(touchEndVal - touchInitVal) <= halfSceen) {
                        num--;
                        //console.log(num);
                        endVal += screenW;
                        //console.log(endVal);
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                        $(this).siblings().find('span').eq(num).addClass('active').siblings().removeClass('active');
                    } else if (Math.abs(touchEndVal - touchInitVal) > halfSceen) {
                        num--;
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                        $(this).siblings().find('span').eq(num).addClass('active').siblings().removeClass('active');
                    } else {
                        $(this).animate({
                            transform: 'translateX(' + endVal + 'px)'
                        }, opt.duration)
                    }
                }
                newTime = new Date();
            }
        }
    }
    
    
    //自动轮播
    var This = $(this);
    
    autoplay();
    
    function autoplay() {
        timer = setInterval(function () {
            num++;
            if (num == oListLen) {
                num = 0;
                This.css('transform', 'translateX(0)');
                
            }
            
            This.animate({
                transform: 'translateX(' + (-screenW * (num + 1)) + 'px)'
            }, opt.duration)
            This.siblings().find('span').eq(num).addClass('active').siblings().removeClass('active');
        }, opt.autoTime)
        
    }
    
}


