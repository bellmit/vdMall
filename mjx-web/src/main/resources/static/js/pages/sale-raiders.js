$(function() {
    setImgSize()
    // 设置图片尺寸
    function setImgSize() {
        var bodyWidth = document.body.offsetWidth-30
        $('.detail-body img').each(function() {
            var imgW = $(this).attr('width')
            var imgH = $(this).attr('height')
            var imgHeight = bodyWidth*imgH/imgW
            $(this).css({
                'width': '100%',
                'height': imgHeight
            })
        })
    }
    changeStyle()
    // 改变富文本样式
    function changeStyle() {
        // 表格格式
        $('.detail-body table').css({
            'width':'100%'
        })
        // 字体颜色
        $('.detail-body p').css({
            'color':'#333333'
        })
        $('.detail-body span').css({
            'color':'#333333'
        })
        $('.one-detail span').css({
            'color':'#333333'
        })
    }

    $('.header-back').click(function() {
        var prePageUrl = document.referrer
        if(undefined == prePageUrl || '' == prePageUrl) {
            return false
        } else {
            window.history.go(-1)
        }
    })

    checkPicture()
    // 查看大图
    function checkPicture() {
        $('.one-detail img').click(function() {
            if ($(this).hasClass('fixed-img')) {
                $(this).removeClass('fixed-img')
                $('.fixed-img-fullpage').hide()
                $('body').css('position','')
                window.scrollTo(0,$('body').attr('bodytop'))
            }else {
                $(this).addClass('fixed-img')
                $('.fixed-img-fullpage').show()
                // 禁止页面滚动
                $('body').css({
                    'position':'fixed',
                    'top': -fixedHeight
                })
                $('body').attr('bodytop',fixedHeight)
            }
        })
        $('.fixed-img-fullpage').click(function() {
            $('.one-detail img').removeClass('fixed-img')
            $('.fixed-img-fullpage').hide()
            $('body').css('position','')
            window.scrollTo(0,$('body').attr('bodytop'))
        })
    }
    
    var fixedHeight = ''
    // 监听页面高度
    $(document).scroll(function() {
        var scrollTop = document.documentElement.scrollTop || document.body.scrollTop || window.pageYOffset
        fixedHeight = scrollTop
    })

})
