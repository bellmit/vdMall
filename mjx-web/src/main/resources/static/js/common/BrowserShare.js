var shareContentConfig;

var BrowserShare = function (shareLists, config, wxconfigInfo) {
   
    var qApiSrc = {
        lower: "http://3gimg.qq.com/html5/js/qb.js",
        higher: "http://jsapi.qq.com/get?api=app.share"
    };
    var bLevel = {
        qq: {forbid: 0, lower: 1, higher: 2},
        uc: {forbid: 0, allow: 1}
    };
    var UA = navigator.appVersion;
    //qq浏览器
    var isqqBrowser = (UA.split("MQQBrowser/").length > 1) ? bLevel.qq.higher : bLevel.qq.forbid;
    //uc 浏览器
    var isucBrowser = (UA.split("UCBrowser/").length > 1) ? bLevel.uc.allow : bLevel.uc.forbid;
    //微信内内置浏览器
    if((UA.split("MicroMessenger/").length) > 1){
        var isweChatBrowser = 3;
        
    }
   
    
    var version = {
        uc: "",
        qq: ""
    };
    var isWeixin = false;

    // var desinUrl = config.url.split('/');
    //desinUrl[0] + '//' + desinUrl[1] + desinUrl[2]
   
    config = config || {};
    this.url = config.url || document.location.href || '';

    this.title = config.title || document.title || '';
    this.desc = config.desc || document.title || '';
    this.img = config.img || document.getElementsByTagName('img').length > 0 && document.getElementsByTagName('img')[0].src || '';
    this.img_title = config.img_title || document.title || '';
    this.from = config.from || window.location.host || '';
    this.ucAppList = {
        sinaWeibo: ['kSinaWeibo', 'SinaWeibo', 11, '新浪微博'],
        WechatFriends: ['kWeixin', 'WechatFriends', 1, '微信好友'],
        WechatFriendship: ['kWeixinFriend', 'WechatTimeline', 8, '微信朋友圈'],
        QQ: ['kQQ', 'QQ', 4, 'QQ好友'],
        QZone: ['kQZone', 'QZone', 3, 'QQ空间']
    };

    this.share = function (to_app) {
        var title = this.title, url = this.url, desc = this.desc, img = this.img, img_title = this.img_title, from = this.from;
        if (isucBrowser) {
            to_app = to_app == '' ? '' : (platform_os == 'iPhone' ? this.ucAppList[to_app][0] : this.ucAppList[to_app][1]);
            if (to_app == 'QZone') {
                B = "mqqapi://share/to_qzone?src_type=web&version=1&file_type=news&req_type=1&image_url="+img+"&title="+title+"&description="+desc+"&url="+url+"&app_name="+from;
                k = document.createElement("div"), k.style.visibility = "hidden", k.innerHTML = '<iframe src="' + B + '" scrolling="no" width="1" height="1"></iframe>', document.body.appendChild(k), setTimeout(function () {
                    k && k.parentNode && k.parentNode.removeChild(k)
                }, 5E3);
            }
            if (typeof(ucweb) != "undefined") {
                ucweb.startRequest("shell.page_share", [title, desc, url, to_app, "", "@" + from, ""])
            } else {
                if (typeof(ucbrowser) != "undefined") {
                    if(ucbrowser.web_shareEX){
                        ucbrowser.web_shareEX(
                            JSON.stringify({
                                title: title,
                                content: desc,
                                sourceUrl: url,
                                imageUrl: img,
                                source: from,
                                target: to_app
                            })
                        )
                    }else{
                        ucbrowser.web_share(title, desc, url, "", to_app, from, img)
                    }
                    
                } else { }
            }
        }
        else if(isqqBrowser && !isWeixin){
            to_app = to_app == '' ? '' : this.ucAppList[to_app][2];
            var ah = {
                url: url,
                title: title,
                description: desc,
                img_url: img,
                img_title: img_title,
                to_app: to_app,//微信好友1,腾讯微博2,QQ空间3,QQ好友4,生成二维码7,微信朋友圈8,啾啾分享9,复制网址10,分享到微博11,创意分享13
                cus_txt: "请输入此时此刻想要分享的内容"
            };
            ah = to_app == '' ? '' : ah;
            if (typeof(browser) != "undefined") {
                if (typeof(browser.app) != "undefined" && isqqBrowser == bLevel.qq.higher) {
                    browser.app.share(ah)
                }
            } else {
                if (typeof(window.qb) != "undefined" && isqqBrowser == bLevel.qq.lower) {
                    window.qb.share(ah)
                } else {}
            }
            
        }
        else if(isweChatBrowser == 3){}
        
    };
    

    this.isloadqqApi = function () {
        if (isqqBrowser) {
            var b = (version.qq < 5.4) ? qApiSrc.lower : qApiSrc.higher;
            var d = document.createElement("script");
            var a = document.getElementsByTagName("body")[0];
            d.setAttribute("src", b);
            a.appendChild(d)
        }
    };

    this.getPlantform = function () {
        ua = navigator.userAgent;
        if ((ua.indexOf("iPhone") > -1 || ua.indexOf("iPod") > -1)) {
            return "iPhone"
        }
        return "Android"
    };

    this.is_weixin = function () {
        var a = UA.toLowerCase();
        if (a.match(/MicroMessenger/i) == "micromessenger") {
            return true
        } else {
            return false
        }
    };

    this.getVersion = function (c) {
        var a = c.split("."), b = parseFloat(a[0] + "." + a[1]);
        return b
    };

 


    this.init = function () {
        platform_os = this.getPlantform();
        version.qq = isqqBrowser ? this.getVersion(UA.split("MQQBrowser/")[1]) : 0;
        version.uc = isucBrowser ? this.getVersion(UA.split("UCBrowser/")[1]) : 0;
        isWeixin = this.is_weixin();
        if ((isqqBrowser && version.qq < 5.4 && platform_os == "iPhone") || (isqqBrowser && version.qq < 5.3 && platform_os == "Android")) {
            isqqBrowser = bLevel.qq.forbid
        } else {
            if (isqqBrowser && version.qq < 5.4 && platform_os == "Android") {
                isqqBrowser = bLevel.qq.lower
            } else {
                if (isucBrowser && ((version.uc < 10.2 && platform_os == "iPhone") || (version.uc < 9.7 && platform_os == "Android"))) {
                    isucBrowser = bLevel.uc.forbid
                }
            }
        }
        this.isloadqqApi();
        var share = this;
        // QQ UC 浏览器
        if (((isqqBrowser && isweChatBrowser != 3)) || isucBrowser) {
            $('.header-show .share').css('display','block');
            //首页 点击分享按钮弹出分享信息
            $('.header-show .icon-share1').on('click',function () {
                $('.commodity-sharing .sharing-cover').fadeIn(300);
                $('.commodity-sharing .sharing-con').animate({
                    bottom: 0
                },300,'ease-in')
            })
            //详情页 全部分享
            $('.footer-share').show()
            $('.footer-share').click(function () {
                $('.commodity-sharing .sharing-cover').fadeIn(300);
                $('.commodity-sharing .sharing-con').animate({
                    bottom: 0
                },300,'ease-in')
            })
            
                for (var i=0,shareList;shareList = shareLists[i++];) {
                    shareList.onclick = function(){
                        share.share(this.getAttribute('data-app'));
                        $('.commodity-sharing .sharing-cover').fadeOut(300)
                        $('.commodity-sharing .sharing-con').animate({
                            bottom: '-345px'
                        },300,'ease-out')
                    }
                }
            
          
        // 微信 浏览器
        } else if((isqqBrowser == 2 && isweChatBrowser == 3) || (isqqBrowser == 0 && isweChatBrowser == 3)){
            $('.header-show .share').css('display','block');
            //首页 点击分享按钮弹出分享信息
            $('.header-show .icon-share1').on('click',function () {
                $('.weixin-share').fadeIn(300);
            })
            // 详情页 微信分享
            $('.footer-share').show()
            $('.footer-share').click(function () {
                $('.weixin-share').fadeIn(300);
            })



            
            var title = this.title, url = this.url, desc = this.desc, img = this.img;

            
            wx.config({
                debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: wxconfigInfo.appId, // 必填，公众号的唯一标识
                timestamp: wxconfigInfo.timestamp, // 必填，生成签名的时间戳
                nonceStr: wxconfigInfo.nonceStr, // 必填，生成签名的随机串
                signature: wxconfigInfo.signature,// 必填，签名
                jsApiList: [// 必填，需要使用的JS接口列表
                    'updateAppMessageShareData',
                    'updateTimelineShareData'
                ] 
            });
            wx.ready(function(){
                //自定义“分享给朋友”及“分享到QQ”按钮的分享内容
                wx.updateAppMessageShareData({ 
                    title: title, // 分享标题
                    desc: desc, // 分享描述
                    link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: img, // 分享图标
                    success: function () {
                      // 设置成功
                    }
                })
                //自定义“分享到朋友圈”及“分享到QQ空间”按钮的分享内容
                wx.updateTimelineShareData({ 
                    title: title, // 分享标题
                    desc: desc, // 分享描述
                    link: url, // 分享链接，该链接域名或路径必须与当前页面对应的公众号JS安全域名一致
                    imgUrl: img, // 分享图标
                    success: function () {
                        // 设置成功
                    }
                })
            });
        }else {
        
        
        }
    };

    this.init();

    var share = this;
    
    


    return this;
};


/**
 * 分享
 * @param reqInterfaceUrl
 * @param shareUrl
 * @param shareTitle
 * @param shareDesc
 * @param shareImgUrl
 * @param shareImgTitle
 * @param shareFrom
 */
function wxShare(reqInterfaceUrl, shareLists, shareUrl, shareTitle, shareDesc, shareImgUrl, shareImgTitle, shareFrom) {
    var config = {
        url: shareUrl,
        title: shareTitle,
        desc: shareDesc,
        img: shareImgUrl,
        img_title: shareImgTitle,
        from: shareFrom
    };

    getReqShare(reqInterfaceUrl, shareUrl);

    if('' == shareContentConfig || undefined == shareContentConfig)
    {
        return;
    }
    new BrowserShare(shareLists, config, shareContentConfig);
}

/**
 * 获取分享签名方法
 * franlin.wu
 */
function getReqShare(reqInterfaceUrl, shareUrl) {

    $.ajax(
        {
            async: false,
            url: reqInterfaceUrl,
            data:JSON.stringify({
                url:shareUrl
             }),
            type: 'post',
            dataType: 'json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                xhr.setRequestHeader("version", "v1");
            },
            success: function (res) {
                console.log(res);
                if("success" == res.code)
                {
                   
                    shareContentConfig = res.data;
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
}