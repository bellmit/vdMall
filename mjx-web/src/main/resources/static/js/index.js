$(function () {
    // 刷新购物车
    // window.addEventListener('pageshow', function (event) {
    //     console.log(event.persisted);
    //     console.log(window.performance);
    //     console.log(window.performance.navigation.type);
    //     if (event.persisted || window.performance && window.performance.navigation.type == 2)
    //         {
    //             console.log('后退回来的页面');
    //             checkShopcart()
    //         }
    // },false)
    window.onpageshow = function (e) {
        // console.log(e.persisted);
        if (e.persisted) {
            checkShopcart()
        }
    }
    checkShopcart()
    function checkShopcart() {
        if ($('#accountStatus').val() == '1') {
            //已登录
            $.ajax({
                url: _contextUrl + '/mjx/goodsCar/getGoodCarCount',
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                success: function (res) {
                    // console.log(res)
                    if (res.code == 'noLogin') {
                        //用户尚未登录
                        $('.footer .footer-shopcart .img-shopcart').empty();
                    } else if (res.code == 'MJX.UNKNOWN_EXCEPTION') {
                        console.log('查询购物车商品数量接口系统错误')
                    } else {
                        //成功
                        var count = res.data.totalCount;
                        var innerHtml = '';
                        if (count == 0) {
                            //购物车商品数量为0
                            $('.footer .footer-shopcart .img-shopcart').empty();
                        } else if (count>0 && count<=9) {
                            innerHtml = '<div class="vd-m-9">' + count + '</div>';
                        } else if (count>=10 && count<=99) {
                            innerHtml = '<div class="vd-m-99">' + count + '</div>';
                        } else if (count >= 100) {
                            innerHtml = '<div class="vd-m-99more">' + count + '</div>';
                        }
                        $('.footer .footer-shopcart .img-shopcart').append(innerHtml);
                    }
                },
                error: function (err) {
                    console.log(err)
                }
            })
        } else {
            $('.footer .footer-shopcart .img-shopcart').empty();
            console.log('用户未登录')
        }
    }

    if(window.localStorage.getItem('isJoinSuccess')){
        var message = window.localStorage.getItem('isJoinSuccess')
        $('.toast>h3').html(message);
        $('.toast').fadeIn()
        setTimeout(function () {
            $('.toast').fadeOut()
            window.localStorage.removeItem('isJoinSuccess')
        },3000)

    }
    var shareUrl = encodeURI(window.location.href);
    var domainUri = $('#domain').val();
    //首页分享调用
    var shareLists = $('.commodity-sharing .share-type .type-show')
    wxShare(
        domainUri + "/common/wechat/share",
        shareLists,
        shareUrl,
        '贝登精选-医疗器械B2B电商平台',
        '我正在看【贝登精选-医疗器械B2B电商平台】，分享给你，一起看吧。',
        encodeURI(domainUri + '/images/logo.png'),
        '',
    '')

    // 底部导航 去购物车
    $('.footer-shopcart').click(function() {
        window.localStorage.setItem('backUrl','/index.html')
        window.localStorage.setItem('redirect','/shopcart')
        window.location.href = '/dispatch.html#/shopcart'
    })
    // 去个人中心
    $('.footer-myindex').click(function() {
        window.localStorage.setItem('backUrl','/index.html')
        window.localStorage.setItem('redirect','/myindex')
        window.location.href = '/dispatch.html#/myindex'
    })
   
    //首页搜索框跳转至搜索页
    $(".J-to-search").click(function(){
        window.location.href="/index/search.html";
    })

    //首页各模块初始化加载动效参数
    var initHandle = {
        //右下角客服联系提示图标
        'consult-icon': {
            ele: $('#consultation .consult-icon'),
            parames: {
                properties: {
                    scale: 1
                },
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)'
            }
        },
        //右下角客服联系提示信息
        // 'consult-txt': {
        //     ele: $('#consultation .consult-txt'),
        //     parames: {
        //         properties: {
        //             opacity: 1,
        //             left: '-252px'
        //         },
        //         duration: 200,
        //         easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
        //         callback: function(){
        //             setTimeout(function (){
        //                 $('#consultation .consult-txt').fadeOut();
        //             },5000)
        //         },
        //         delay: 1000
        //     }
        // },
    }
    //首页初始化加载动效
    $.each(initHandle, function (key, item) {
        animateMove(item.ele, item.parames)
    })

    //点击触发动效参数
    var clickHandle = {
        //头部搜索固定
        'scrollTop': {
            ele: $('.sus-search'),
            downParames: {//下滑
                properties: {top: 0},
                duration: 300,
                easing: 'ease-in'
            },
            upParames: {//上滑
                properties: {top: '-205px'},
                duration: 350,
                easing: 'ease-out'
            }
        },
        //咨询图标消失
        'consult-icon-hide': {
            ele: $('#consultation .consult-icon'),
            parames: {
                properties: {scale: 0},
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)'
            }
        },
        //咨询图标显现
        'consult-icon-show': {
            ele: $('#consultation .consult-icon'),
            parames: {
                properties: {scale:  1},
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
                delay: 350
            }
        },
        //咨询信息显现
        'sus-con-show': {
            ele: $('.sus-con'),
            parames: {
                properties: {bottom: '15px'},
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)'
            }
        },
        //咨询信息消失
        'sus-con-hide': {
            ele: $('.sus-con'),
            parames: {
                properties: {bottom: '-480px'},
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
                delay: 100
            }
        },
        //分享信息显现
        'share-info-show': {
            ele: $('.commodity-sharing .sharing-con'),
            parames: {
                properties: {bottom: 0},
                duration: 300,
                easing: 'ease-in'
            }
        },
        //分享信息消失
        'share-info-hide': {
            ele: $('.commodity-sharing .sharing-con'),
            parames: {
                properties: {bottom: '-345px'},
                duration: 300,
                easing: 'ease-out'
            }
        }
    }
    $('.sus-tel').on('click',function (){
        $('.sus-con').animate({
            bottom: '-480px'
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
        $('.consult-icon').animate({
            scale: 1
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)',function(){},600)
    })
    $('.sus-customer').on('click',function(){
        $('.sus-con').animate({
            bottom: '-480px'
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
        $('.consult-icon').animate({
            scale: 1
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)',function(){},600)
        setTimeout(function(){
            window.location.href = 'http://mjx.vedeng.com/chatdialog.html?websiteid=150272&wc=f899b5';
        },150)
    })

    //头部搜索栏固定
    var serachTop = $('.con-search').offset().top;
    $(document).scroll(function () {

        var scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

        if (scrollTop >= serachTop + 30) {
            animateMove(clickHandle['scrollTop'].ele, clickHandle['scrollTop'].downParames)
        } else {
            animateMove(clickHandle['scrollTop'].ele, clickHandle['scrollTop'].upParames)
        }
    })
   
     
        


    // if(sessionStorage.getItem('consult') == 1){
    //     $('.consult-txt').css('display','none')
    // }else {
    //     sessionStorage.setItem('consult',1);
    // }
    //点击咨询信息消失
    // $('#consultation .consult-txt').on('click', function () {
    //     $(this).fadeOut();
    // })

    //点击图标,背景层,提示信息显现,图标,提示信息消失
    $('#consultation .consult-icon').on('click', function () {
        // $('#consultation .consult-txt').fadeOut(200);
        $('.suspension .sus-cover').fadeIn();
        animateMove(clickHandle['sus-con-show'].ele, clickHandle['sus-con-show'].parames);
        animateMove(clickHandle['consult-icon-hide'].ele, clickHandle['consult-icon-hide'].parames);
    })

    //点击背景层,背景层消失,提示信息隐藏,图标显示
    $('.suspension .sus-cover').on('click', function () {
        setTimeout(function() {
            $('.suspension .sus-cover').fadeOut(300);
        },300)
        animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames);
        animateMove(clickHandle['consult-icon-show'].ele, clickHandle['consult-icon-show'].parames);
    })

    //点击取消,背景层消失,提示信息隐藏,图标显示
    $('.suspension .service .cannel').on('click', function () {
        setTimeout(function() {
            $('.suspension .sus-cover').fadeOut(300);
        },300)
        animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames);
        animateMove(clickHandle['consult-icon-show'].ele, clickHandle['consult-icon-show'].parames);
    })

    $('.weixin-share').on('click',function (){
        $(this).fadeOut();
    })

    //点击背景层分享信息消失
    $('.commodity-sharing .sharing-cover').on('click', function () {
        $(this).fadeOut(300);
        animateMove(clickHandle['share-info-hide'].ele, clickHandle['share-info-hide'].parames);
    })
    //点击取消按钮分享信息消失
    $('.commodity-sharing .cannel').on('click', function () {
        $('.commodity-sharing .sharing-cover').fadeOut(300);
        animateMove(clickHandle['share-info-hide'].ele, clickHandle['share-info-hide'].parames);
    })

    //animate动画封装
    function animateMove(ele, opt) {
        opt.properties = opt.properties || '';
        opt.duration = opt.duration || 300;
        opt.easing = opt.easing || 'linear';
        opt.callback = opt.callback || function () {
        };
        opt.delay = opt.delay || 0;

        ele.animate(opt.properties, opt.duration, opt.easing, opt.callback(), opt.delay)
    }
    // CC客服
    var c=document.createElement('script');
    c.src='//kefu.ziyun.com.cn/vclient/?webid=150272&wc=f899b5';
    var s=document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(c,s);
    forbidScrollCC('.sus-cover')
    forbidScrollCC('.sus-con')
    function forbidScrollCC(ele) {
        // 禁止页面拖动
        document.querySelector(ele).addEventListener(
            "touchmove",
            function(e) {
            e.preventDefault(); //阻止默认的处理方式(阻止下拉滑动的效果)
            },
            { passive: false }
        ); //passive 参数不能省略，用来兼容ios和android
    }
})
window.onload = function(){
    // 3个banner
    var yourSwiper = new Swiper ('.three-banner', {
        direction: 'horizontal', // 垂直切换选项
        autoplay: {
            delay: 3000,
            disableOnInteraction: false
        }, // 自动播放
        loop: true, // 循环模式选项
        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination-three-banner',
            type: 'bullets'
        },
    })
    // 十六种分类
    var mySwiper = new Swiper ('.sixteen-type', {
        direction: 'horizontal', // 垂直切换选项
        autoplay: {
            delay: 400,
        }, // 自动播放
        loop: false, // 循环模式选项
        // 如果需要分页器
        pagination: {
            el: '.swiper-pagination-sixteen-type',
            type: 'bullets'
        },
    })
    var isAutoOnce = window.sessionStorage.getItem('isAutoOnce')
    if (isAutoOnce == 0) {
        mySwiper.autoplay.stop()
    }else {
        setTimeout(function() {
            mySwiper.autoplay.stop()
            window.sessionStorage.setItem('isAutoOnce',0)
        }, 1600)
    }
}