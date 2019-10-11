    var jp_title = '申请加入_贝登精选'
    var jp_desc =' 我正在看【申请加入_贝登精选】，分享给你，一起看吧。'
    // var share_obj = new BrowserShare('',config);

    var shareUrl = window.location.href;
    var domainUri = $('#domain').val();
    //首页分享调用
    wxShare(domainUri + "/common/wechat/share",
        '',
        shareUrl,
        jp_title,
        jp_desc,
        domainUri + 'images/logo.png',
        '',
    '');

    // 监听页面高度
    $(document).scroll(function() {
        $('.goods-detail-router').fadeOut()
        $('.router-fullpage').fadeOut()
    })
    // 弹出跳转其他页面
    $('.user-leader-header-right').on('click',function() {
        $('.user-leader-router').fadeIn()
        $('.router-fullpage').fadeIn()
    })
    // 关闭跳转其他页面
    $('.router-fullpage').on('click',function() {
        $('.user-leader-router').fadeOut()
        $('.router-fullpage').fadeOut()
    })
    // 返回键
    $('.user-leader-header-left').on('click',function() {
        window.location.href = "/index.html";
    })
    // 点击去首页
    $('.router-index').on('click',function() {
        window.location.href = '/index.html'
    })
    // 点击去搜索
    $('.router-search').on('click',function() {
        window.location.href = '/index/search.html'
    })
    // 点击去购物车
    $('.router-shopcart').click(function() {
        window.localStorage.setItem('backUrl','/joinus.html')
        window.localStorage.setItem('redirect','/shopcart')
        window.location.href = '/dispatch.html#/shopcart'
    })
    var variablPath = document.querySelector('variablPath');
    var accountStatus = variablPath.getAttribute('accountStatus');
    var loginAddress = variablPath.getAttribute('src');
    // console.log(loginAddress);
    var currentPage = window.location.href
    // console.log(currentPage);
    // 点击去个人中心
    $('.router-personal').on('click',function() {
        window.location.href = '/dispatch.html#/myindex'
    })
    
    // 先判断是否登录
    $('.apply-button').click(function() {
        // console.log('accountStatus = ' + accountStatus);
        // 已经登录 请求判断用户状态
        if (accountStatus == '1' || accountStatus == 1) {
            userJxStatus()
        }
        // 没有登录 就去跳转登录页 传特殊值
        else {
            window.localStorage.setItem('backUrl','joinToIndex')
            window.localStorage.removeItem('redirect')
            window.location.href = '/dispatch.html#/login'
        }
    })
    function userJxStatus() {
        // 请求判断用户状态
        $.ajax({
            url: _contextUrl + '/apply/vedengJoin',
            type: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                isTest: 0
            }),
            dataType: 'json',
            success: function(res) {
                // console.log(res);
                // 您已成功加入贝登精选，可查看商品价格
                if (res.code == 'MJX.VEDENG_JOIN_IS_JOINED') {
                    $('.full-page-tip-yes').show()
                    $('.tip-wrapper').show()
                    $('.tip-top-success').show()
                    $('body').css({
                        'overflow': 'hidden'
                    })
                }
                // 您已申请加入贝登精选，请等待系统审核
                else if (res.code == 'MJX.VEDENG_JOIN_IS_IN_REVIEW') {
                    $('.full-page-tip-yes').show()
                    $('.tip-wrapper').show()
                    $('.tip-top-ing').show()
                    $('body').css({
                        'overflow': 'hidden'
                    })
                }
                // 您的账号为禁用状态，无法申请
                else if (res.code == 'MJX.VEDENG_JOIN_ERROR_FORBIDDEN') {
                    $('.full-page-tip-yes').show()
                    $('.tip-wrapper').show()
                    $('.tip-top-failed').show()
                    $('body').css({
                        'overflow': 'hidden'
                    })
                }
                else if (res.code == 'FAIL_CODE')  {
                    console.log('error')
                    $('.tip-warning').show()
                    setTimeout(function() {
                        $('.tip-warning').fadeOut(300)
                    },2000)
                }
            }
        })
    }

    // 点击知道了关闭蒙层
    $('.tip-know').click(function() {
        $('.full-page-tip-yes').hide()
        $('.tip-wrapper').hide()
        $('.tip-top-success').hide()
        $('.tip-top-ing').hide()
        $('.tip-top-failed').hide()
        $('body').css({
            'overflow': 'auto'
        })
    })
    // 点击背景关闭蒙层
    $('.full-page-tip-yes').click(function() {
        $('.full-page-tip-yes').hide()
        $('.tip-wrapper').hide()
        $('.tip-top-success').hide()
        $('.tip-top-ing').hide()
        $('.tip-top-failed').hide()
        $('body').css({
            'overflow': 'auto'
        })
    })

    //点击退出登录按钮弹出提示信息
    $('#userinfor .user-out').on('touchend',function () {
        $('.popup').fadeIn(300);
        $('.user-cover').fadeOut();
        $('#userinfor .user').animate({
            top: '-95px'
        }, 300, 'ease-in-out')
    });
    //点击背景层，用户中心以及背景层消失
    $('.user-cover').on('touchend',function () {
        $('body').css('position', '');
        $(this).fadeOut()
        $('#userinfor .user').animate({
            top: '-95px'
        }, 300, 'ease-in-out')
    });
    //点击退出登录按钮弹出提示信息
    $('#userinfor .user-out').tap(function () {
        $('.popup').fadeIn(300);
        $('.user-cover').fadeOut();
        $('#userinfor .user').animate({
            top: '-95px'
        },300,'ease-in-out')
    });
    //点击遮罩层取消按钮回归之前状态
    $('.popup-con .cannel').tap(function () {
        $('body').css('position', '');
        $('.popup').fadeOut(200);
    });
