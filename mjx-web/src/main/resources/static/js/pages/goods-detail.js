$(function() {
    // 回弹效果
    var scroll = null;
    function bScroll(ele) {
        scroll && scroll.destroy();
        const options = {
            scrollY: true, 
            scrollX: false,
            mouseWheel: true,
            click: true,
            taps: true
          }
        scroll = new BScroll(ele,options)
    }
    
    // 判断是否从购物车过来
    // var shopcartRouter = window.localStorage.getItem('shopcartRouter')
    // if (shopcartRouter) {
    //     if (shopcartRouter == 1) {
    //         window.localStorage.setItem('shopcartRouter',2)
    //         // var myHref = window.location.href
    //         // window.location.replace(myHref)
    //         initShopcartNum()
    //     }
    // }
    window.onpageshow = function (e) {
        // console.log(e.persisted);
        if (e.persisted) {
            initShopcartNum()
        }
    }

    // var marin_url = 'http://172.16.100:8280'
    getUserLoginStatus()
    // 用户是否登录 用于点击事件判断
    var loginStatus = ''
    function getUserLoginStatus() {
        $.ajax({
            // url: marin_url + '/isLogin',
            url: _contextUrl + '/isLogin',
            type: 'get',
            dataType: 'json',
            success: function(res) {
                loginStatus = res
            }
        })
    }

    initShopcartNum()
    // 一进来就获取 购物车数量
    function initShopcartNum() {
        var initloginStatus = ''
        $.ajax({
            // url: marin_url + '/isLogin',
            url: _contextUrl + '/isLogin',
            type: 'get',
            dataType: 'json',
            success: function(res) {
                initloginStatus = res
                // 获取购物车数量
                if (initloginStatus == true) {
                    $.ajax({
                        // url: marin_url + '/mjx/goodsCar/queryGoodsCarList',
                        url: _contextUrl + '/mjx/goodsCar/getGoodCarCount',
                        type: 'post',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            isCount: 0
                        }),
                        dataType: 'json',
                        success: function(res) {
                            if (res.success == true) {
                                if (res.data.totalCount>0&&res.data.totalCount<10) {
                                    $('.vd-m-9').css('display','flex')
                                    $('.vd-m-99').hide()
                                    $('.vd-m-99more').hide()
                                    $('.vd-m-9').text(res.data.totalCount)
                                }else if (res.data.totalCount>=10&&res.data.totalCount<=99) {
                                    $('.vd-m-9').hide()
                                    $('.vd-m-99').css('display','flex')
                                    $('.vd-m-99more').hide()
                                    $('.vd-m-99').text(res.data.totalCount)
                                }else if (res.data.totalCount>99) {
                                    $('.vd-m-9').hide()
                                    $('.vd-m-99').hide()
                                    $('.vd-m-99more').css('display','flex')
                                }else {
                                    $('.vd-m-9').hide()
                                    $('.vd-m-99').hide()
                                    $('.vd-m-99more').hide()
                                }
                            }else {
                                console.log('获取购物车数量失败',res.message)
                                $('.vd-m-9').hide()
                                $('.vd-m-99').hide()
                                $('.vd-m-99more').hide()
                            }
                        }
                    })
                }else {
                    $('.vd-m-9').hide()
                    $('.vd-m-99').hide()
                    $('.vd-m-99more').hide()
                }
            }
        })
    }
    var firstSkuNo = ''
    // 一进来就获取 商品sku
    function initSkuNo() {
        if($('.attr-one-wrapper').length){
           
            $('.attr-one-wrapper').each(function() {
                if ($(this).children().hasClass('active')) {
                    firstSkuNo = $(this).attr('data-skuno')
                }
            })
        }else{
           $('.spec-one-wrapper').each(function(){
            if ($(this).children().hasClass('active')) {
                firstSkuNo = $(this).children().attr('data-skuno')
            }
           })
        }
   
    }
    initSkuNo()
    setImgSize()
    // 设置图片尺寸
    function setImgSize() {
        // 图文详情图片
        var bodyWidth = document.body.offsetWidth-30
        $('.pic-and-word-content img').each(function() {
            var imgW = $(this).attr('width')
            var imgH = $(this).attr('height')
            var imgHeight = bodyWidth*imgH/imgW
            $(this).css({
                'width': '100%',
                'height': imgHeight
            })
        })
        // 轮播图图片
        var swiperWidth = document.body.offsetWidth
        $('.swiper-slide').css({
            'width': swiperWidth,
            'height': swiperWidth
        })
    }
    
    //首页分享调用
    var shareLists = $('.commodity-sharing .share-type .type-show')
    var jp_title = $('.skuName').text() + '_贝登精选'
    var jp_img = $('.swiper-wrapper').find('.swiper-slide').eq(0).find('img').eq(0).attr('src')
    var jp_desc =' 我正在看【' + $('.skuName').text() + '_贝登精选】，分享给你，一起看吧。'
    var shareUrl = encodeURI(window.location.href);
    var domain = $('#domain').val();
    //首页分享调用
    wxShare(
        domain + "/common/wechat/share",
        shareLists,
        shareUrl,
        jp_title,
        jp_desc,
        jp_img,
        '',
    '')
 
    //点击触发动效参数
    var clickHandle = {
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
        }
    }
    // 禁止页面滚动
    function forbidScroll() {
        $('body').css({
            'position':'fixed',
            'top': -fixedHeight
        })
        $('body').attr('bodytop',fixedHeight)
    }
    // 允许页面滚动
    function allowScroll() {
        $('body').css('position','')
        window.scrollTo(0,$('body').attr('bodytop'))
    }
    // 联系客服
    linkService()
    function linkService() {
        //点击联系客服,背景层,提示信息显现
        $('.link-service').on('click', function () {
            $('.suspension .sus-cover').fadeIn();
            animateMove(clickHandle['sus-con-show'].ele, clickHandle['sus-con-show'].parames)
        })
        //点击背景层,背景层消失,提示信息隐藏,图标显示
        $('.suspension .sus-cover').on('click', function () {
            setTimeout(function() {
                $('.suspension .sus-cover').fadeOut(300);
            },300)
            animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames)
        })
        //点击取消,背景层消失,提示信息隐藏,图标显示
        $('.suspension .service .cannel').on('click', function () {
            setTimeout(function() {
                $('.suspension .sus-cover').fadeOut(300);
            },300)
            animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames)
        })
    }
    // 电话咨询
    $('.sus-tel').on('click',function (){
        $('.sus-con').animate({
            bottom: '-480px'
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
    })
    // 在线咨询
    $('.sus-customer').on('click',function(){
        $('.sus-con').animate({
            bottom: '-480px'
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
        setTimeout(function(){
            window.location.href = 'http://mjx.vedeng.com/chatdialog.html?websiteid=150272&wc=f899b5';
        },150)
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
        ele.animate(opt.properties, opt.duration, opt.easing, opt.callback, opt.delay)
    }

    if($('.swiper-slide').length > 1){
        var smallSwiper = new Swiper ('.swiper-container-small', {
            direction: 'horizontal', // 垂直切换选项
            loop: true, // 循环模式选项
            // 如果需要分页器
            pagination: {
              el: '.swiper-pagination-small',
              type: 'fraction'
            }  
        })
    }
    if ($('.swiper-slide').length == 1) {
        $('.swiper-pagination').css('display','none')
    }
    // 点击开关轮播图蒙层
    $('.swiper-container-small').click(function() {
        if ($('.swiper-container-small').hasClass('fixed-banner') == false) {
            // 点击弹出轮播图蒙层
            $('.swiper-container-small').addClass('fixed-banner')
            $('.swiper-pagination-small').addClass('fixed-banner-pagi')
            $('body').css({
                'overflow':'hidden',
                'position':'fixed',
                'top': -fixedHeight
            })
            $('body').attr('bodytop',fixedHeight)
        }else {
            $('.swiper-container-small').removeClass('fixed-banner')
            $('.swiper-pagination-small').removeClass('fixed-banner-pagi')
            $('body').css({
                'overflow':'auto',
                'position':''
            })
            window.scrollTo(0,$('body').attr('bodytop'))
        }
    })

    var fixedHeight = ''
    // 监听页面高度
    $(document).scroll(function() {
        $('.goods-detail-router').fadeOut()
        $('.router-fullpage').fadeOut()
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop || window.pageYOffset
        fixedHeight = scrollTop
        // 小于44不展示商品详情
        if (scrollTop <= 44) {
            $('.goods-detail-header').css('display','flex')
            $('.goods-detail-header-all').css('display','none')
            $('.goods-detail-header-all').animate({
                'opacity':'0'
            },300,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            $('.mid-product').addClass('active')
            $('.mid-detail').removeClass('active')
        }
        // 44到详情高度-44展示商品详情 选中商品
        if (scrollTop > 44 && scrollTop < $('#pic-and-word').offset().top - 44) {
            $('.goods-detail-header').css('display','none')
            $('.goods-detail-header-all').css('display','flex')
            $('.goods-detail-header-all').animate({
                'opacity':'1'
            },300,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            $('.mid-product').addClass('active')
            $('.mid-detail').removeClass('active')
        }
        // 大于详情高度-90展示商品详情 选中详情
        if (scrollTop >= $('#pic-and-word').offset().top - 44) {
            $('.goods-detail-header').css('display','none')
            $('.goods-detail-header-all').css('display','flex')
            $('.goods-detail-header-all').animate({
                'opacity':'1'
            },300,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            $('.mid-product').removeClass('active')
            $('.mid-detail').addClass('active')
        }
    })

    routerAnyWhere()
    // 路由跳转
    function routerAnyWhere() {
        // 弹出跳转其他页面
        $('.goods-detail-header-right').click(function() {
            $('.goods-detail-router').fadeIn()
            $('.router-fullpage').fadeIn()
        })
        $('.goods-detail-header-all-right').click(function() {
            $('.goods-detail-router').fadeIn()
            $('.router-fullpage').fadeIn()
        })
        // 关闭跳转其他页面
        $('.router-fullpage').click(function() {
            $('.goods-detail-router').fadeOut()
            $('.router-fullpage').fadeOut()
        })
        // 返回键
        $('.goods-detail-header-left').click(function() {
            var prePageUrl = document.referrer
            if(undefined == prePageUrl || '' == prePageUrl) {
                return false
            } else {
                window.history.go(-1)
            }
        })
        $('.goods-detail-header-all-left').click(function() {
            var prePageUrl = document.referrer
            if(undefined == prePageUrl || '' == prePageUrl) {
                return false
            } else {
                window.history.go(-1)
            }
        })
        // 点击去首页
        $('.router-index').click(function() {
            window.location.href = '/index.html'
        })
        // 点击去搜索
        $('.router-search').click(function() {
            window.location.href = '/index/search.html'
        })
        // 点击去购物车
        $('.router-shopcart').click(function() {
            initSkuNo()
            window.localStorage.removeItem('shopcartRouter')
            window.localStorage.setItem('backUrl','/goods/'+ firstSkuNo +'/goods-detail.html')
            window.localStorage.setItem('redirect','/shopcart')
            window.location.href = '/dispatch.html#/shopcart'
        })
        // 点击去销售攻略
        $('.sale-raiders').click(function() {
            window.location.href = '/goods/'+ $('#J-skuNo').val() +'/sale-raiders.html'
        })
    }

    personalCenterFunc()
    // 个人中心
    function personalCenterFunc() {
        // 点击去个人中心
        $('.router-personal').click(function() {
            window.location.href = '/dispatch.html#/myindex'
        })
    }
    proOrDetailFunc()
    // 商品详情锚点定位
    function proOrDetailFunc() {
        // 切换商品
        $('.mid-product').click(function() {
            var currentY = document.documentElement.scrollTop || document.body.scrollTop
            scrollAnimation(currentY,0)
        })
        // 切换详情
        $('.mid-detail').click(function() {
            var currentY = document.documentElement.scrollTop || document.body.scrollTop
            var picAndWordPosition = $('#pic-and-word').offset().top-44
            scrollAnimation(currentY,picAndWordPosition)
        })
        // 滚动方法
        function scrollAnimation(currentY, targetY) {
            // 获取当前位置方法
            // var currentY = document.documentElement.scrollTop || document.body.scrollTop
            // 计算需要移动的距离
            var needScrollTop = targetY - currentY
            var _currentY = currentY
            setTimeout(function() {
                // 一次调用滑动帧数，每次调用会不一样
                var dist = Math.ceil(needScrollTop / 10)
                _currentY += dist
                window.scrollTo(_currentY, currentY)
                // 如果移动幅度小于十个像素，直接移动，否则递归调用，实现动画效果
                if (needScrollTop > 10 || needScrollTop < -10) {
                    scrollAnimation(_currentY, targetY)
                } else {
                    window.scrollTo(_currentY, targetY)
                }
            }, 1)
        }
    }

    clickSpecAndAttr()
    // 点击规格和属性
    function clickSpecAndAttr() {
        // 展示规格和属性
        $('.spec-and-attr').click(function() {
            $('.show-join-button').show()
            $('.spec-and-attr-fullpage').fadeIn(200)
            $('.spec-and-attr-prop').animate({
                'bottom': '0'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 禁止页面滚动
            forbidScroll()
            bScroll('.prop-goods-main-body')
            window.localStorage.setItem('whichButton','joinButton')
        })
        // 点击叉关闭规格和属性
        $('.icon-delete').click(function() {
            $('.show-join-button').hide()
            $('.show-sure-button').hide()
            $('.spec-and-attr-fullpage').fadeOut(200)
            $('.spec-and-attr-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
            window.sessionStorage.removeItem('isSpecChange')
            window.localStorage.removeItem('whichButton')
        })
        var canAjax = true    
        // 加入购物车关闭规格和属性
        $('.sure-button').click(function() {
            initSkuNo()
            if (canAjax == true) {
                canAjax = false
                $('.show-join-button').hide()
                $('.show-sure-button').hide()
                $('.spec-and-attr-fullpage').fadeOut(200)
                $('.spec-and-attr-prop').animate({
                    'bottom': '-482px'
                },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
                // 允许页面滚动
                allowScroll()
                window.sessionStorage.removeItem('isSpecChange')
                window.localStorage.removeItem('whichButton')
                initSkuNo()
                if (loginStatus == true) {
                    // 加入购物车
                    $.ajax({
                        // url: marin_url + '/mjx/goodsCar/addGoodsIntoCar',
                        url: _contextUrl + '/mjx/goodsCar/addGoodsIntoCar',
                        type: 'post',
                        contentType: 'application/json',
                        data: JSON.stringify({
                                skuNo: firstSkuNo,
                                count: $('.vd-m-input').val()
                        }),
                        dataType: 'json',
                        success: function(res) {
                            setTimeout(function() {
                                canAjax = true
                            },3000)
                            // console.log('加入购物车接口',res);
                            if(res.success == true) {
                                $('.J-error-bubble').css('display','none')
                                successBubble()
                                // 加入成功后，步进器变为1
                                $('.vd-m-input').val(1)
                                // 重新判断加减禁用
                                stepperNumJudge()
                            }else {
                                $('.J-error-bubble .error-inner').text(res.message)
                                errorBubble()
                                $('.J-success-bubble').hide()
                            }
                            // 获取购物车数量
                            $.ajax({
                                // url: marin_url + '/mjx/goodsCar/queryGoodsCarList',
                                url: _contextUrl + '/mjx/goodsCar/getGoodCarCount',
                                type: 'post',
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    isCount: 0
                                }),
                                dataType: 'json',
                                success: function(res) {
                                    // console.log('加入后获取购物车数量',res);
                                    if (res.success == true) {
                                        if (res.data.totalCount>0&&res.data.totalCount<10) {
                                            $('.vd-m-9').css('display','flex')
                                            $('.vd-m-99').hide()
                                            $('.vd-m-99more').hide()
                                            $('.vd-m-9').text(res.data.totalCount)
                                        }else if (res.data.totalCount>=10&&res.data.totalCount<=99) {
                                            $('.vd-m-9').hide()
                                            $('.vd-m-99').css('display','flex')
                                            $('.vd-m-99more').hide()
                                            $('.vd-m-99').text(res.data.totalCount)
                                        }else if (res.data.totalCount>99) {
                                            $('.vd-m-9').hide()
                                            $('.vd-m-99').hide()
                                            $('.vd-m-99more').css('display','flex')
                                        }else {
                                            $('.vd-m-9').hide()
                                            $('.vd-m-99').hide()
                                            $('.vd-m-99more').hide()
                                        }
                                    }else if(!res.success&&res.message=='商品已下架，添加失败'){
                                        $('.prop-hasdown-button').show()
                                        $('.show-join-button').hide()
                                        $('.show-sure-button').hide()   
                                    }
                                }
                            })
                        },
                        error: function(res) {
                            setTimeout(function() {
                                canAjax = true
                            },3000)
                            $('.J-error-bubble .error-inner').text(res.message)
                            errorBubble()
                            $('.J-success-bubble').hide()
                        }
                    })
                } else {
                    // 用户未登录
                    initSkuNo()
                    window.localStorage.setItem('backUrl','/goods/'+ firstSkuNo +'/goods-detail.html')
                    window.localStorage.removeItem('redirect')
                    window.location.href = '/dispatch.html#/login'
                }
            }else {
                $('.J-error-bubble .error-inner').text('操作失败，请稍候再试')
                errorBubble()
                $('.J-success-bubble').hide()
            }
        })
        // 点击蒙层关闭规格和属性
        $('.spec-and-attr-fullpage').click(function() {
            $('.show-join-button').hide()
            $('.show-sure-button').hide()
            $('.spec-and-attr-fullpage').fadeOut(200)
            $('.spec-and-attr-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
            window.sessionStorage.removeItem('isSpecChange')
            window.localStorage.removeItem('whichButton')
        })
    }
    clickParam()
    // 点击商品参数
    function clickParam() {
        // 展示商品参数
        $('.goods-detail-param').click(function() {
            $('.goods-detail-fullpage').fadeIn(200)
            $('.goods-detail-prop').animate({
                'bottom': '0'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 禁止页面滚动
            forbidScroll()
            bScroll('.prop-content-goods-detail')
        })
        // 关闭商品参数
        $('.close-button').click(function() {
            $('.goods-detail-fullpage').fadeOut(200)
            $('.goods-detail-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
        // 点击蒙层关闭商品参数
        $('.goods-detail-fullpage').click(function() {
            $('.goods-detail-fullpage').fadeOut(200)
            $('.goods-detail-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
    }
    clickPackage()
    // 点击包装
    function clickPackage() {
        // 展示包装
        $('.goods-package-param').click(function() {
            $('.goods-package-fullpage').fadeIn(200)
            $('.goods-package-prop').animate({
                'bottom': '0'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 禁止页面滚动
            forbidScroll()
            bScroll('.prop-content-package')
        })
        // 关闭包装
        $('.close-button').click(function() {
            $('.goods-package-fullpage').fadeOut(200)
            $('.goods-package-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
        // 点击蒙层关闭包装
        $('.goods-package-fullpage').click(function() {
            $('.goods-package-fullpage').fadeOut(200)
            $('.goods-package-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
    }
    clickStorage()
    // 点击存储
    function clickStorage() {
        // 展示存储
        $('.goods-storage-param').click(function() {
            $('.goods-storage-fullpage').fadeIn(200)
            $('.goods-storage-prop').animate({
                'bottom': '0'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 禁止页面滚动
            forbidScroll()
            bScroll('.prop-content-storage')
        })
        // 关闭存储
        $('.close-button').click(function() {
            $('.goods-storage-fullpage').fadeOut(200)
            $('.goods-storage-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
        // 点击蒙层关闭存储
        $('.goods-storage-fullpage').click(function() {
            $('.goods-storage-fullpage').fadeOut(200)
            $('.goods-storage-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
    }
    clickAfterSale()
    // 点击售后
    function clickAfterSale() {
        // 展示售后
        $('.goods-after-sale-param').click(function() {
            $('.goods-after-sale-fullpage').fadeIn(200)
            $('.goods-after-sale-prop').animate({
                'bottom': '0'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 禁止页面滚动
            forbidScroll()
            bScroll('.prop-content-after-sale')
        })
        // 关闭售后
        $('.close-button').click(function() {
            $('.goods-after-sale-fullpage').fadeOut(200)
            $('.goods-after-sale-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
        // 点击蒙层关闭售后
        $('.goods-after-sale-fullpage').click(function() {
            $('.goods-after-sale-fullpage').fadeOut(200)
            $('.goods-after-sale-prop').animate({
                'bottom': '-482px'
            },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
            // 允许页面滚动
            allowScroll()
        })
    }



    
    chooseSpecAttr()
    // 选择规格属性
    function chooseSpecAttr() {
        // 点击规格选中
        $(".canClick").on('click',function() {
            var url = window.location.href.split('/');
            if ($(this).hasClass('active')) {
                console.log('不跳转');
            }else {
                $(this).addClass('active')
                $(this).parent().siblings().children().removeClass('active')
                initSkuNo()
                var skuNO = $('.attr-one-wrapper').eq(0).attr('data-skuno')?$('.attr-one-wrapper').eq(0).attr('data-skuno'):firstSkuNo
                var specName =  $(this).text()
                var _this = this
                $.ajax({
                    // url: marin_url + '/isLogin',
                    url:'/goods/queryAttrsByModelSpec',
                    type: 'post',
                    dataType: 'json',
                    contentType:'application/json',
                    data: JSON.stringify({
                        skuNo: skuNO,
                        modelSpecShow:specName
                    }),
                    success: function(res) {
                        if(res.success){
                            
                            if(res.data.allOnsaleFlag){
                                $(_this).removeClass('canClick');
                                $(_this).removeClass('active');
                                $(_this).addClass('disabled');
                                return
                            }
                            if(!$('.attr-one-wrapper').length){
                                window.sessionStorage.setItem('isSpecChange',1)
                                window.location.replace('/goods/'+ firstSkuNo  +'/goods-detail.html') 
                               
                                
                            }
                            var data = res.data.curModelSpecSkuList
                            var html =''
                            $('.attr-body').hide()
                            if(data&&data.length>0){
                               
                                data.forEach(function(item,index){
                                    html+=`<div class="attr-one-wrapper" data-skuno=${item.skuAttrCombin.skuNo}>
                                               <div class="attr-one ${item.skuAttrCombin.exsitFlag==1?'glen':'disabled'} ">${item.skuAttrCombin.baseAttributeValues}</div>
                                          </div>`
                                })
                              $('.attr-body').children().remove()
                              $('.attr-body').html(html)
                              $('.glen').eq(0).addClass('active')
                              $('.attr-body').fadeIn()
                
                                if($('.glen').eq(0).parent().attr('data-skuno')&&$('.glen').eq(0).parent().attr('data-skuno')!=''){
                                    window.sessionStorage.setItem('isSpecChange',1)
                                    window.location.replace('/goods/'+  $('.glen').eq(0).parent().attr('data-skuno') +'/goods-detail.html')
                                   
                                }
                                    
                            }else{
                                $('.prop-goods-attr').hide()
                            }
                        
                        }else{
                            $('.prop-goods-attr').hide() 
                        }
                    }
                })

            }
        })
  
    }   
     // 点击属性选中
    $('.attr-body').on('click','.attr-one',function() {
        if ($(this).hasClass('active')) {
            console.log('不跳转');
        }else if($(this).hasClass('disabled')){
            console.log('不跳转');
        }else {
            $(this).addClass('active')
            $(this).parent().siblings().children().removeClass('active')
            window.sessionStorage.setItem('isSpecChange',1)
            window.location.replace('/goods/'+ $(this).parent().attr('data-skuno') +'/goods-detail.html')
          
        }
    })


    $('.join-shopcart').click(function() {
        $('.show-sure-button').show()
        $('.spec-and-attr-fullpage').fadeIn(200)
        $('.spec-and-attr-prop').animate({
            'bottom': '0'
        },200,'cubic-bezier(0.645, 0.045, 0.355, 1)')
        // 禁止页面滚动
        forbidScroll()
        bScroll('.prop-goods-main-body')
        window.localStorage.setItem('whichButton','sureButton')
    })

    // 切换进入页面，蒙层显现
    var specUrl = window.sessionStorage.getItem('isSpecChange')
    var whichButton = window.localStorage.getItem('whichButton')
    if (specUrl == 1) {
        $('.spec-and-attr-fullpage').show()
        $('.spec-and-attr-prop').css({
            'bottom': '0'
        })
        window.sessionStorage.removeItem('isSpecChange')
        // 禁止页面滚动
        forbidScroll()
        bScroll('.prop-goods-main-body')
        if (whichButton == 'sureButton') {
            $('.show-sure-button').show()
            $('.show-join-button').hide()
        }else {
            $('.show-join-button').show()
            $('.show-sure-button').hide()
        }
    }else {
        console.log('不是切换进入页面');
    }
    subTitleCss()
    // 副标题文本样式
    function subTitleCss() {
        $('.title-sub p b').css({
            'font-size': '12px',
            'font-weight': '400',
            'line-height': '18px',
            'color': '#999999',
        })
        $('.title-sub span b').css({
            'font-size': '12px',
            'font-weight': '400',
            'line-height': '18px',
            'color': '#999999',
        })
        $('.title-sub span').css({
            'font-size': '12px',
            'font-weight': '400',
            'line-height': '18px',
            'color': '#999999',
        })
        $('.title-sub a').css({
            'font-size': '12px',
            'font-weight': '400',
            'line-height': '18px',
            'color': '#999999',
        })
        $('.title-sub b').css({
            'font-size': '12px',
            'font-weight': '400',
            'line-height': '18px',
            'color': '#999999',
        })
        // 消除价格 人民币标识空白格
        $.trim($('.goods-detail-name-price .price-num-sign').text())
        // 规格属性蒙层，最后一个没有底线
        $('.prop-goods-main-body .spec-attr-line:last-child').css('border-bottom','none')
        // 4个关键词 最后一个不显示线
        $('.goods-four-keywords .goods-one-keywords:last-child').css('border-bottom','none')
        // 图文详情表格格式
        $('.pic-and-word-content table').css({
            'width':'100%'
        })
        if ($('.inner-right').children().length == 1) {
            $('.inner-right').children().css({
                'margin-top': '11px'
            })
        }else {
            console.log('规格属性都有');
        }
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

    stepperFunc()
    // 步进器
    function stepperFunc() {
        $('.vd-m-input').val(1)
        // 监听input的值
        $('.vd-m-input').on('input',function() {
            stepperNumJudge()
            stepperTip()
        })
        $('.vd-m-input').blur(function() {
            window.scrollTo(0,$('body').attr('bodytop'))
            stepperNumJudge()
            stepperTip()
            // 如果小于1则为1
            if ($('.vd-m-input').val()<1) {
                $('.vd-m-input').val(1)
            }
        })
        $('.vd-m-left').click(function() {
            var count = parseInt($('.vd-m-input').val())-1
            $('.vd-m-input').val(count)
            stepperNumJudge()
        })
        $('.vd-m-right').click(function() {
            var count = parseInt($('.vd-m-input').val())+1
            $('.vd-m-input').val(count)
            stepperNumJudge()
        })
    }
    stepperNumJudge()
    // 步进器数字判断
    function stepperNumJudge() {
        // 如果小于1则为1
        if ($('.vd-m-input').val()<=1) {
            $('.vd-m-left').addClass('disabled')
            $('.vd-m-right').removeClass('disabled')
        }
        // 如果大于9999则为9999
        else if ($('.vd-m-input').val()>=9999) {
            $('.vd-m-left').removeClass('disabled')
            $('.vd-m-right').addClass('disabled')
        }
        // 如果大于1小于9999则不变
        else {
            $('.vd-m-left').removeClass('disabled')
            $('.vd-m-right').removeClass('disabled')
        }
    }
    // 步进器提示判断
    function stepperTip() {
        // 如果小于1则为1
        if ($('.vd-m-input').val()<1&&$('.vd-m-input').val()!='') {
            if(!/^[1-9]\d*$/gi.test($('.vd-m-input').val())||$('.vd-m-input').val()==0) {
                $('.vd-m-input').val(0)
            }
            $('.J-error-bubble .error-inner').text('数量超出范围')
            errorBubble()
        }
        // 如果大于9999则为9999
        else if ($('.vd-m-input').val()>9999) {
            $('.vd-m-input').val(9999)
            $('.J-error-bubble .error-inner').text('数量超出范围')
            errorBubble()
        }
        else {
            var count = parseInt($('.vd-m-input').val())
            $('.vd-m-input').val(count)
            // 一点几取整变为1的时候 需要额外判断一次
            stepperNumJudge()
        }
    }

    $('.footer-shopcart').click(function() {
        initSkuNo()
        window.localStorage.setItem('shopcartRouter',0)
        window.location.href = '/dispatch.html#/shopcart'
        window.localStorage.setItem('backUrl','/goods/'+ firstSkuNo +'/goods-detail.html')
        window.localStorage.setItem('redirect','shopcart')
    })
    // 错误气泡提示 数量已达到上限
    function errorBubble() {
        $('.J-error-bubble').css('display','block')
        setTimeout(function() {
            $('.J-error-bubble').css('display','none')
        },3000)
    }
    // 成功气泡提示 加入购物车
    function successBubble() {
        $('.J-success-bubble').show()
        setTimeout(function() {
            $('.J-success-bubble').hide()
        },3000)
    }
})