//微信端页面获取code

;(function () {
    //当前url
    var currentUrL = window.location.href
    //判断是否登录
    // var logFlag = document.getElementById('J-mjx-accountStatus').value
    var variablPath = document.querySelector('variablPath');
    // var logFlag = getCookie("online");
    var logFlag = variablPath.getAttribute('accountStatus');
    var appId = variablPath.getAttribute('appId');
    var openId = getCookie("mjxopenId");

    console.log("openId:"+openId);
    console.log("appId:"+appId);
    console.log("logFlag:"+logFlag);
    console.log("variablPath:"+variablPath);
    // var  appId = document.getElementById('J-mjx-appId').value
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
            console.log("EEEEEEEEEEE:");
            //在微信中打开 && (openId == null || openId == '' || undefined == openId || openId == 'null')
            console.log(openId);
            console.log(openId == null);
            console.log(openId == '');
            console.log(undefined == openId);
            console.log(openId == 'null');
            console.log(logFlag == 0);
            console.log(logFlag == '0');
            if(appId && (logFlag == 0 || logFlag == '0') && (openId == null || openId == '' || undefined == openId || openId == 'null')){
            //    获取code重定向
                var reqCodeUrl = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + appId + '&redirect_uri=' + encodeURIComponent(currentUrL) + '&response_type=code&scope=snsapi_base&state=1#wechat_redirect'
                window.location.replace(reqCodeUrl);
            }else{

                // alert("appId="+appId+"===="+"logFlag"+logFlag)
            }



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
})()



// 写cookies
function setWxbrowserCookie(name, value)
{
    var ua = navigator.userAgent;
    // 微信浏览器
    if(ua.toLowerCase().indexOf('MicroMessenger'.toLowerCase()) != -1) {
        document.cookie = name + "="+ value ;
    }
}

function getCookie(name) {
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    if(arr=document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return "";
}


// 读取cookies
function getWxbrowserCookie(name)
{
    var arr,reg = new RegExp("(^| )"+name+"=([^;]*)(;|$)");
    var ua = navigator.userAgent;
    // 微信浏览器
    if(ua.toLowerCase().indexOf('micromessenger') != -1) {
        if(arr = document.cookie.match(reg)) {
            return arr[2];
        }
    }
    return '';
}

