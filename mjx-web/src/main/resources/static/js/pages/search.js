$(function () {

    var textInput = document.querySelector('input');
    FastClick.attach(document.body);
    FastClick.attach(document.body);
    Array.prototype.forEach.call(document.getElementsByClassName('test'), function(testEl) {
        testEl.addEventListener('click', function() {
            textInput.focus();
        }, false)
    });

    // 存储在localhost历史关键字数组
    var historyObj = [];
    // 获取当前可视区域高度
    var wHeight = $(window).height();
    // 获取历史关键字
    var historyKeys = window.localStorage.getItem('historyKeys')
    //  将历史关键字显示在历史搜索中
    if (historyKeys) {
        var historyArr = historyKeys.split('&');
        var html = '';
        for (var i = 0; i < historyArr.length; i++) {
            var str = historyArr[i]
            html += `<li>${str}</li>`
        }
        $('.history_keys').html(html)
        $('.history').css('display', 'block')
    }
    // 搜索框的高度
    var sHeight = $('.search-header-wrapper').height()
    var jp_title = '搜索页_贝登精选'
    var jp_desc = ' 我正在看搜索页_贝登精选，分享给你，一起看吧。'
    $('.form-search').on('click',function () {
        $('.search-input').focus()
    })

        // 错误气泡提示 数量已达到上限
    function errorBubble() {
            $('.toast').css('display','block')
            setTimeout(function() {
                $('.toast').css('display','none')
            },3000)
        }

            //获取url中的参数
    function getUrlParams(name) { // 不传name返回所有值，否则返回对应值
        var url = window.location.search;
        if (url.indexOf('?') == 1) {
            return false;
        }
        url = url.substr(1);
        url = url.split('&');
        var name = name || '';
        var nameres;
        // 获取全部参数及其值
        for (var i = 0; i < url.length; i++) {
            var info = url[i].split('=');
            var obj = {};
            obj[info[0]] = decodeURI(info[1]);
            url[i] = obj;
        }
        // 如果传入一个参数名称，就匹配其值
        if (name) {
            for (var i = 0; i < url.length; i++) {
                for (const key in url[i]) {
                    if (key == name) {
                        nameres = url[i][key];
                    }
                }
            }
        } else {
            nameres = url;
        }
        // 返回结果
            return nameres;
    }
    /*if(getUrlParams('keyword')&&getUrlParams('keyword')!=""){
        $('.J-search').val(getUrlParams('keyword'))
    }*/
    // 搜索框监听、聚焦、失焦、清空
    $('.search-input').bind('input propertychange', function () {
        var kw = $('.search-input').val()
        // var str = /\s/
        // var flag = true
        // // console.log(kw.length)
        // for (var i = 0; i < kw.length; i++) {
        //     if (str.test(kw[i])) {
        //         flag = false
        //     } else {

        //         flag = true
        //     }
        // }
        if ($('.search-input').val().length > 0) {
            $('.i-error2').show()
            // $('.search-recommend-wrapper').hide()
            // $('.search_matching').show()
        } else if ($('.search-input').val().length == 0) {

            setTimeout(function() {
                $('.i-error2').hide()
            },300)
        }
    })
    $('.search-input').focus(function () {
        if ($('.search-input').val().length > 0) {
            $('.i-error2').show()

        } else if ($('.search-input').val().length == 0) {
            setTimeout(function() {
                $('.i-error2').hide()
            },300)
        }

    })
    // 失焦隐藏
    $('.search-input').blur(function () {
        setTimeout(function() {
            $('.i-error2').hide()
        },300)
    })
    // 点叉获焦
    $('.i-error2').on('click', function () {
        $('.search-input').val('')
        setTimeout(function() {
            $('.i-error2').hide()
            $('.search-recommend-wrapper').show()
        }, 300)
        $('.search-input').focus()
    })
    // 点搜索获焦
    $('.i-search').on('click', function () {
        $('.search-input').focus()
    })

    // 点击关键词推荐赋值
    $('.search-recommend-name').on('click', function (){
        if($('.history_keys li').eq(10)){
            $('.history_keys li').eq(10).remove()
        }
        var kw = $(this).text();

        var historyKeys = window.localStorage.getItem('historyKeys');
        kw = kw.trim()
        var str = `<li>${kw}</li>`
        // console.log($('.history_keys').children().length)
        for(var i =0;i<$('.history_keys').children().length;i++){
            var arr = $('.history_keys').children();
              if(kw==arr[i].innerHTML){
                $(arr[i]).remove()
              }
        }
        if (historyKeys) {
            $('.history_keys').prepend(str);
            var arr = historyKeys.split('&')
            if (arr.indexOf(kw) == -1) {
                arr.unshift(kw)
                if(arr.length>10){
                    var nArr = arr.slice(0,9)
                    window.localStorage.setItem('historyKeys', nArr.join('&'))
                }else{
                    window.localStorage.setItem('historyKeys', arr.join('&'))
                }
            } else {
                arr.splice(arr.indexOf(kw), 1)
                arr.unshift(kw)
                window.localStorage.setItem('historyKeys', arr.join('&'))
           
            }
        } else {
            $('.history_keys').append(str);
            $('.history').show()
            historyObj.push(kw);
            window.localStorage.setItem('historyKeys', historyObj.join('&'))
        }
        $('.search-input').val(kw);
        if(window.location.href){
            window.location.href = "/index/list.html?keyword=" + kw;
        }else{
            window.location.replace( "/index/list.html?keyword=" + kw)
        }

    })

    $(document).keyup(function (e) {
        var e = event || window.event;
        var k = e.keyCode || e.which;
        if (k == 13) {
            var kw = $(".J-search").val().trim();
            if (kw.length > 0) {
                window.location.href = "/index/list.html?keyword=" + encodeURIComponent(kw);
            }
        }
    })

    $(".J-search-key").click(function () {
        if($('.J-search').val().trim()==''){
            errorBubble()
            return
        }
        var kw = $(".J-search").val().trim().toLowerCase();
        var historyKeys = window.localStorage.getItem('historyKeys');
        var str = `<li>${kw}</li>`
        // console.log($('.history_keys').children().length)
        for(var i =0;i<$('.history_keys').children().length;i++){
            var arr = $('.history_keys').children();
            //   console.log(arr[i].innerHTML)
              if(kw==arr[i].innerHTML){
              
                $(arr[i]).remove()
              }
        }
        if (kw.length > 0) {
            // alert(historyKeys)
            // console.log(historyKeys)
            if (historyKeys) {
                $('.history_keys').prepend(str);
                if($('.history_keys >li').eq(9)){
                    $('.history_keys >li').eq(9).remove()

                }
                var arr = historyKeys.split('&')
                // console.log(arr.indexOf('ass'))
                if (arr.indexOf(kw) == -1) {
                    arr.unshift(kw)
                    if(arr.length>10){
                        var nArr = arr.slice(0,10)
                        window.localStorage.setItem('historyKeys', nArr.join('&'))
                    }else{
                        window.localStorage.setItem('historyKeys', arr.join('&'))
                    }


                } else {
                    arr.splice(arr.indexOf(kw), 1)
                    arr.unshift(kw)
                    window.localStorage.setItem('historyKeys', arr.join('&'))
                }

            } else {
                $('.history_keys').empty()
                $('.history_keys').html('')
                document.getElementById("history_keys").innerHTML = "";
                $('.history_keys').append(str);
                $('.history').show()
                historyObj.push(kw);
                window.localStorage.setItem('historyKeys', historyObj.join('&'))
            }
            window.location.href = "/index/list.html?keyword=" + encodeURIComponent(kw);
        }
    })
    // document.getElementById('search_matching').style.height = wHeight - sHeight + 'px'
    // 原生键盘搜索事件
    $('.form-search').bind('submit', function () {
        if($('.J-search').val().trim()==''){
            errorBubble()
            return
        }
        //获取历史数据长度
        var historyKeyLength = $('.history_keys').children().length;
        // do something
        var historyKeys = window.localStorage.getItem('historyKeys');
        // 获取localstroge中的搜索词数组
        var kw = $(".J-search").val().trim().toLowerCase();
        // keyArr.push(kw)
        var str = `<li>${kw}</li>`
        for(var i =0;i<$('.history_keys').children().length;i++){
            var arr = $('.history_keys').children();
            //   console.log(arr[i].innerHTML)
              if(kw==arr[i].innerHTML){
                $(arr[i]).remove()
              }
        }
        if (kw.length > 0) {
            if (historyKeys) {
                var arr = historyKeys.split('&')
                $('.history_keys').prepend(str);
                if($('.history_keys li').eq(9)){
                    $('.history_keys li').eq(9).remove()

                }
                if (arr.indexOf(kw) == -1) {
                  
                    arr.unshift(kw)
                    if(arr.length>10){
                        var nArr = arr.slice(0,10)
                        window.localStorage.setItem('historyKeys', nArr.join('&'))
                    }else{
                        window.localStorage.setItem('historyKeys', arr.join('&'))
                    }
                } else {
                    arr.splice(arr.indexOf(kw), 1)
                    arr.unshift(kw)
                    window.localStorage.setItem('historyKeys', arr.join('&'))
                }

            } else {
                $('.history_keys').empty()
                $('.history_keys').html('')
                document.getElementById("history_keys").innerHTML = "";
                $('.history_keys').append(str);
                $('.history').show()
                historyObj.push(kw);
                window.localStorage.setItem('historyKeys', historyObj.join('&'))
            }
            window.location.href = "/index/list.html?keyword=" + encodeURIComponent(kw);
        }

    });
    //   删除历史信息
    $('.cleanHistory').on('click', function () {
        window.localStorage.removeItem('historyKeys')
        $('.history').css('display', 'none')
        $('.history_keys').empty()
        $('.history_keys').html('')
        document.getElementById("history_keys").innerHTML = "";
    })
    // 点击历史值赋值
    $('.history_keys ').on('click','li', function () {
        if($('.history_keys >li').eq(10)){
            $('.history_keys >li').eq(10).remove()

        }

        var historyKeys = window.localStorage.getItem('historyKeys');
        var kw = $(this).text();
        kw = kw.trim()
        var str = `<li>${kw}</li>`
        for(var i =0;i<$('.history_keys').children().length;i++){
            var arr = $('.history_keys').children();
            //   console.log(arr[i].innerHTML)
              if(kw==arr[i].innerHTML){
                $(arr[i]).remove()
              }
        }
        if (historyKeys) {
            $('.history_keys').prepend(str);
            var arr = historyKeys.split('&')
            if (arr.indexOf(kw) == -1) {
               
                arr.unshift(kw)
                if(arr.length>10){
                    var nArr = arr.slice(0,9)
                    window.localStorage.setItem('historyKeys', nArr.join('&'))
                }else{
                    window.localStorage.setItem('historyKeys', arr.join('&'))
                }
            } else {
                arr.splice(arr.indexOf(kw), 1)
                arr.unshift(kw)
                window.localStorage.setItem('historyKeys', arr.join('&'))
                // arr.unshift(kw)
            }
        } else {
            $('.history_keys').empty()
            $('.history_keys').html('')
            document.getElementById("history_keys").innerHTML = "";
            $('.history_keys').append(str);
            historyObj.push(kw);
            window.localStorage.setItem('historyKeys', historyObj.join('&'))
        }
        $('.search-input').val(kw);
        window.location.href = "/index/list.html?keyword=" + encodeURIComponent(kw);

    })

    //    点击搜索匹配关键字
    // $('.search_matchingBox').on('click', 'li', function () {
    //     var historyKeys = window.localStorage.getItem('historyKeys');
    //     var kw = $(this).text();
    //     kw = kw.trim()
    //     if (historyKeys) {
    //         var arr = historyKeys.split('&')
    //         if (arr.indexOf(kw) == -1) {
    //             arr.unshift(kw)
    //             window.localStorage.setItem('historyKeys', arr.join('&'))
    //         } else {
    //             arr.splice(arr.indexOf(kw), 1)
    //             arr.unshift(kw)
    //             window.localStorage.setItem('historyKeys', arr.join('&'))
    //             // arr.unshift(kw)
    //         }
    //     } else {
    //         historyObj.push(kw);
    //         window.localStorage.setItem('historyKeys', historyObj.join('&'))
    //     }
    //     window.location.href = "/index/list.html?keyword=" + kw;
    // })

    // 浏览器内核
    var browser = {
        versions: function () {
            var u = navigator.userAgent,
                app = navigator.appVersion;
            return { //移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
                iPhone: u.indexOf('iPhone') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
            };
        }(),
        language: (navigator.browserLanguage || navigator.language).toLowerCase()
    }
    // 判断哪个手机端打开

    if (browser.versions.mobile) { //判断是否是移动设备打开。browser代码在下面
        var ua = navigator.userAgent.toLowerCase(); //获取判断用的对象
        if (ua.match(/MicroMessenger/i) == "micromessenger") {
            //在微信中打开
            // $('.search').css('paddingTop', '45px')
        }
        if (ua.match(/WeiBo/i) == "weibo") {
            //在新浪微博客户端打开
        }
        if (ua.match(/QQ/i) == "qq") {
            //在QQ空间打开
        }
        if (browser.versions.ios) {
            //是否在IOS浏览器打开
        }
        if (browser.versions.android) {
            //是否在安卓浏览器打开
        }
    } else {
        //否则就是PC浏览器打开
    }

    var shareUrl = window.location.href;
    var domainUri = $('#domain').val();

    //

    wxShare(domainUri + "/common/wechat/share",
        '',
        shareUrl,
        jp_title,
        jp_desc,
        domainUri + '/images/logo.png',
        '',
        ''
    );
})