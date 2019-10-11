$(function () {
    // 回弹效果
    var scroll = null;
    function cleanbScroll() {
        scroll && scroll.destroy();
    }
    function initScroll(ele){
        const options = {
            scrollY: true, 
            scrollX: false,
            mouseWheel: true,
            click: true,
          }
        scroll = new BScroll(ele,options)
    }
    
function toDecimal2(x) { 
    var f = parseFloat(x); 
    if (isNaN(f)) { 
      return false; 
    } 
    var f = Math.round(x*100)/100; 
    var s = f.toString(); 
    var rs = s.indexOf('.'); 
    if (rs < 0) { 
      rs = s.length; 
      s += '.'; 
    } 
    while (s.length <= rs + 2) { 
      s += '0'; 
    } 
    return s; 
  } 
    FastClick.attach(document.body);
    FastClick.attach(document.body);
    var textInput = document.querySelector('input');
    Array.prototype.forEach.call(document.getElementsByClassName('test'), function (testEl) {
        testEl.addEventListener('click', function () {
            textInput.focus();
        }, false)
    });
    var shareUrl = window.location.href;
    var domainUri = $('#domain').attr('value');
    var jp_title = '';
    var jp_desc = '';
    var shareFlag = '0';
    
    if ($('#historysearch').attr('value') != '') {
        jp_title = '' + $('#historysearch').attr('value') + '' + '-搜索结果_贝登精选'
        jp_desc = ' 我正在看【' + $('#historysearch').attr('value') + '-搜索结果_贝登精选】，分享给你，一起看吧。'
        shareFlag = '1';
        
    } else if ($('.categoryName').attr('value') != '') {
        jp_title = '' + $('.categoryName').attr('value') + '' + '-分类_贝登精选'
        jp_desc = ' 我正在看【' + $('.categoryName').attr('value') + '-分类_贝登精选】，分享给你，一起看吧。'
        shareFlag = '1';
       
    } else if ($('.brandName').attr('value') != '') {
        jp_title = '' + $('.brandName').attr('value') + '' + '-品牌_贝登精选'
        jp_desc = ' 我正在看【' + $('.brandName').attr('value') + '-品牌_贝登精选】，分享给你，一起看吧。'
        shareFlag = '1';
        
    } else if ($('#historysearch').attr('value') == '' && $('.categoryName').attr('value') == '' && $('.brandName').attr('value') == '') {
        jp_title = '搜索结果列表页_贝登精选'
        jp_desc = ' 我正在看搜索结果列表页_贝登精选，分享给你，一起看吧。'
        shareFlag = '1';
    }

    if ( shareFlag==1) {
        jp_title.replace(/\s*/g, "")
        var domainUri = $('#domain').attr('value');
        var sharImgUrl = domainUri + 'images/logo.png';
        var shareUrl = window.location.href;
        // 首页分享调用
        wxShare(domainUri + "/common/wechat/share",
            '',
            shareUrl,
            jp_title,
            jp_desc,
            sharImgUrl,
            '',
            '');

    }

    var priceShowType = ''
    if ($('.checkPriceAuth').attr('value') == 'true') {
        priceShowType = 'goods-class-one-kind-desc-price'

    } else {
        priceShowType = 'goods-class-one-kind-desc-price hidden'
    }

    $('.goods-class-title-middle').on('click', function () {
        var kw = $('#historysearch').val();
        window.location.href = '/index/search.html?keywords=' + encodeURIComponent(kw);
    })


    $('.i-up').each(function(){
        if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
            $(this).prev().css('marginRight','10px')
        }else{
            $(this).prev().css('marginRight','0px')
        }
    })
    // $('.goods-class-choice').on("click",".choice_main",function(){
    //     console.log(1)
    //     $(this).addClass('active')
    // })
    
    if (getQueryString("filter_brands") && getQueryString("filter_brands") != '') {
        var filterBrandsArr = getQueryString("filter_brands").split(',')
        // console.log(filterBrandsArr)
        filterBrandsArr.forEach(function (item) {
            $('.goods-brand').each(function () {
                if ($(this).attr('brandid') == item) {
                    $(this).addClass('active')
                }
            })
        })
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
            obj[info[0]] = info[1];
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
    //为搜索框赋值
    if (!$('#historysearch').val() || $('#historysearch').val() == '') {
        $('.placeHloder').text('输入商品名称、品牌查找')
    } else {
        $('.placeHloder').text($('#historysearch').val())
        $('.placeHloder').css('color', '#333')
    }
    // 点击收起方法
    function retract(cele, sele) {
        $(sele).hide()
        cele.hide()
        cele.next().show()

    }
    // 点击展开
    function spread(cele, sele) {
        $(sele).css('display', '-webkit-box')
        // console.log(cele)
        cele.prev().show()
        cele.hide()
    }
    // 已选分类的情况下调用渲染筛选页面
    function initattrFilter(filter_categoryId, categoryId, brandId, url) {

        var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
        $.ajax({
            url: url,
            type: 'get',
            async: false,
            data: {
                keyword: keywords,
                categoryId: categoryId,
                filter_categoryId: filter_categoryId,
                brandId: brandId
            },
            dataType: 'json',
            success: function (res) {
                if (res.success) {
                    var resData = res.data.filterCondition
                    // 渲染属性数据
                    if (resData.attrList == null || resData.attrList.length == 0) {
                        $('.attribute_box').children().remove()
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                        $('.attribute_box').hide()
                        $('.i-up').each(function(){
                            if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                $(this).prev().css('marginRight','10px')
                            }else{
                                $(this).prev().css('marginRight','0px')
                            }
                        })
                    } else {
                        $('.attribute_box').children().remove()
                        $('.attribute_box').show()
                        var attrData = resData.attrList
                        var attrHtml = template('test', {
                            list: attrData
                        })
                        $('.attribute_box').html(attrHtml)
                        $('.i-up').each(function(){
                            if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                $(this).prev().css('marginRight','10px')
                            }else{
                                $(this).prev().css('marginRight','0px')
                            }
                        })
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                    }
                    //渲染品牌数据
                    if (resData.brandList == null || resData.brandList.length == 0) {
                        $('.goods-class-choice-brand-body').children().remove()
                        $('.goods-class-choice-brand').hide()
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                    } else {
                        $('.goods-class-choice-brand-body').children().remove()
                        $('.goods-class-choice-brand').show()
                        var brandData = resData.brandList

                        var brandHtml = template('brandtest', {
                            brandlist: brandData
                        })

                        $('.goods-class-choice-brand-body').html(brandHtml)
                        if (brandData.length >= 7) {
                            $('.goods-class-choice-brand-title-right>.i-up').hide()
                            $('.goods-class-choice-brand-title-right>.i-down').show()
                        } else {
                            $('.goods-class-choice-brand-title-right>.i-up').hide()
                            $('.goods-class-choice-brand-title-right>.i-down').hide()
                        }
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                    }
                    //渲染科室
                    if (resData.deptList == null || resData.deptList.length == 0) {
                        $('.goods-class-choice-Department-body').children().remove()
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                        $('.goods-class-choice-Department').hide()
                    } else {
                        $('.goods-class-choice-Department-body').children().remove()
                        $('.goods-class-choice-Department').show()
                        var DepartmentData = resData.deptList
                        var DepartmentHtml = template('departmenttest', {
                            list: DepartmentData
                        })
                        $('.goods-class-choice-Department-body').html(DepartmentHtml)
                        if (DepartmentData.length >= 7) {
                            $('.goods-class-choice-Department-title-right>.i-up').hide()
                            $('.goods-class-choice-Department-title-right>.i-down').show()
                        } else {
                            $('.goods-class-choice-Department-title-right>.i-up').hide()
                            $('.goods-class-choice-Department-title-right>.i-down').hide()
                        }
                   
                        $('.i-up').each(function(){
                            if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                $(this).prev().css('marginRight','10px')
                            }else{
                                $(this).prev().css('marginRight','0px')
                            }
                        })
                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }
                    if(scroll){
                        scroll.refresh()
                    }
                } else {
                    $('.brand_ChildrenList').show()
                    $('.brand_loading-gif').hide()
                }
              
            },
            error: function (err) {
                $('.brand_ChildrenList').fadeIn()
                $('.attribute_box').hide()
            }
        })
    }
    //获取当前所有初始选中状态
    function getactiveStatus() {
        $('.goods-type').removeClass('active')
        // 获取是否选择了分类
        var URLfilter_categoryId = getUrlParams('filter_categoryId') //分类的选中值
        var URLfilter_brands = getUrlParams('filter_brands') //选中的品牌
        var URLfilter_attrIds = getUrlParams('filter_attrIds') //选中分类值
        var URLfilter_deptList = getUrlParams('filter_depts')
        // debugger
        if(!URLfilter_categoryId&&!URLfilter_brands&&!URLfilter_attrIds&&!URLfilter_deptList){
            var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
            var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null
            var brandId = $('.brandVal').val() ? $('.brandVal').val() : null

            var url = '/index/list-api1.html'
           
            $.ajax({
                url: url,
                type: 'get',
                async: false,
                data: {
                    keyword: keywords,
                    categoryId: categoryId,
                    brandId: brandId
                },
                dataType: 'json',
                success: function (res) {
                    if (res.success) {
                       
                        var resData = res.data.filterCondition
                        $('.attribute_box').hide()
                        $('.brand-selected').text('')
                        $('.type-selected').text('')
                        $('.Department-selected').text('')
                        //渲染品牌数据
                        if (resData.brandList == null || resData.brandList.length == 0) {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').hide()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        } else {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').show()
                            var brandData = resData.brandList

                            var brandHtml = template('brandtest', {
                                brandlist: brandData
                            })

                            $('.goods-class-choice-brand-body').html(brandHtml)
                            if (brandData.length >= 7) {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').hide()
                            }
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        }
                        //渲染科室
                        if (resData.deptList == null || resData.deptList.length == 0) {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                            $('.goods-class-choice-Department').hide()
                        } else {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.goods-class-choice-Department').show()
                            var DepartmentData = resData.deptList
                            var DepartmentHtml = template('departmenttest', {
                                list: DepartmentData
                            })
                            $('.goods-class-choice-Department-body').html(DepartmentHtml)
                            if (DepartmentData.length >= 7) {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').hide()
                            }

                     
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                            $('.brand_ChildrenList').show()
                            $('.brand_loading-gif').hide()
                        }
                        if(scroll){
                            scroll.refresh()
                        }
                    } else {
                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }

                },
                error: function (err) {
                    $('.brand_loading-gif').hide()
                    $('.brand_ChildrenList').fadeIn()
                }
            })
            return
        }
       
        if (URLfilter_categoryId && URLfilter_categoryId != '') {
            // 跳转时切换到当前选中的分类
            var filter_categoryId = URLfilter_categoryId
            var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null
            var brandId = $('.brandVal').val() ? $('.brandVal').val() : null
            // 如果当前分类是超过六条切换时先恢复收起状态
            if ($('.goods-type').length >= 7) {
                $('.goods-class-choice-type-title-right>.i-up').hide()
                $('.goods-class-choice-type-title-right>.i-down').show()
                // 分类点击收起
                $(".goods-class-choice-type-body div:nth-of-type(n+7)").hide()
            }
            var url = '/index/list-api1.html'
            initattrFilter(filter_categoryId, categoryId, brandId, url)
            $('.type-selected').text('')
            $('.goods-type').removeClass('active')
            var typeSelectedVal = ''
            $('.goods-type').each(function () {
                if ($(this).attr('cate_id') == filter_categoryId) {
                    $(this).addClass('active')
                    if ($('.goods-type').index(this) >= 6) {
                        // 分类点击展开
                        $('.goods-class-choice-type-title-right>.i-up').show()
                        $('.goods-class-choice-type-title-right>.i-down').hide()
                        $(".goods-class-choice-type-body div:nth-of-type(n+7)").show()
                    }
                    typeSelectedVal += $(this).text() + '、'
                }
            })
            $('.type-selected').text(typeSelectedVal.replace(/、$/, ''))
        }else{
            var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
            var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null
            var brandId = $('.brandVal').val() ? $('.brandVal').val() : null

            var url = '/index/list-api1.html'
           
            $.ajax({
                url: url,
                type: 'get',
                async: false,
                data: {
                    keyword: keywords,
                    categoryId: categoryId,
                    brandId: brandId
                },
                dataType: 'json',
                success: function (res) {
                    if (res.success) {
                       
                        var resData = res.data.filterCondition
                        $('.attribute_box').hide()
                        $('.brand-selected').text('')
                        $('.type-selected').text('')
                        $('.Department-selected').text('')
                        //渲染品牌数据
                        if (resData.brandList == null || resData.brandList.length == 0) {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').hide()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        } else {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').show()
                            var brandData = resData.brandList

                            var brandHtml = template('brandtest', {
                                brandlist: brandData
                            })

                            $('.goods-class-choice-brand-body').html(brandHtml)
                            if (brandData.length >= 7) {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').hide()
                            }
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        }
                        //渲染科室
                        if (resData.deptList == null || resData.deptList.length == 0) {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                            $('.goods-class-choice-Department').hide()
                        } else {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.goods-class-choice-Department').show()
                            var DepartmentData = resData.deptList
                            var DepartmentHtml = template('departmenttest', {
                                list: DepartmentData
                            })
                            $('.goods-class-choice-Department-body').html(DepartmentHtml)
                            if (DepartmentData.length >= 7) {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').hide()
                            }

                     
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                            $('.brand_ChildrenList').show()
                            $('.brand_loading-gif').hide()
                        }
                        if(scroll){
                            scroll.refresh()
                        }
                    } else {
                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }

                },
                error: function (err) {
                    $('.brand_loading-gif').hide()
                    $('.brand_ChildrenList').fadeIn()
                }
            })
        }

        //切换到品牌选中状态 
        if (URLfilter_brands && URLfilter_brands != '') {
            var URLfilter_brandsArr = URLfilter_brands.split(',')
            $('.brand-selected').text('')
            $('.goods-brand').removeClass('active')
            var brandSelectedVal = ''
            // 如果品牌的元素大于六条先将状态恢复为默认收起
            if ($('.goods-brand').length >= 7) {
                $('.goods-class-choice-brand-title-right>.i-up').hide()
                $('.goods-class-choice-brand-title-right>.i-down').show()
                $(".goods-class-choice-brand-body div:nth-of-type(n+7)").hide()
            }
            URLfilter_brandsArr.forEach(function (item) {
                $('.goods-brand').each(function () {
                    if ($(this).attr('brandid') == item) {
                        $(this).addClass('active')
                        if ($('.goods-brand').index(this) >= 6) {
                            $('.goods-class-choice-brand-title-right>.i-up').show()
                            $('.goods-class-choice-brand-title-right>.i-down').hide()
                            $(".goods-class-choice-brand-body div:nth-of-type(n+7)").show()
                        }
                        brandSelectedVal += $(this).text() + '、'
                    }
                })
            })
            $('.brand-selected').text(brandSelectedVal.replace(/、$/, ''))
        }else{
          
        }
        //切换科室
        if (URLfilter_deptList && URLfilter_deptList != '') {
            var URLfilter_deptListArr = URLfilter_deptList.split(',')
          
            $('.goods-Department').removeClass('active')
            // 如果品牌的元素大于六条先将状态恢复为默认收起
            if ($('.goods-Department').length >= 7) {
                $('.goods-class-choice-Department-title-right>.i-up').hide()
                $('.goods-class-choice-Department-title-right>.i-down').show()
                $(".goods-class-choice-Department-body div:nth-of-type(n+7)").hide()
            }
            var DepartmentName = ''
            URLfilter_deptListArr.forEach(function (item) {
                $('.goods-Department').each(function () {
                    if ($(this).attr('Departmentid') == item) {
                        $(this).addClass('active')
                        if ($('.goods-Department').index(this) >= 6) {
                            $('.goods-class-choice-Department-title-right>.i-up').show()
                            $('.goods-class-choice-Department-title-right>.i-down').hide()
                            $(".goods-class-choice-Department-body div:nth-of-type(n+7)").show()
                        }
                        DepartmentName += $(this).text() + '、'
                    }
                })
            })
            $('.Department-selected').text(DepartmentName.replace(/、$/, ''))
        }else{
            
        }
        //切换到属性的选中状态
        if (URLfilter_attrIds && URLfilter_attrIds != '') {
            var URLfilter_attrIdsArr = URLfilter_attrIds.split(',')
            $('.goods-attribute').removeClass('active')
            $('.attribute-selected').text('')
            // 如果品牌的元素大于六条先将状态恢复为默认收起
            if ($('.goods-attribute').length >= 7) {
                $('.goods-class-choice-attribute-title-right>.i-up').hide()
                $('.goods-class-choice-attribute-title-right>.i-down').show()
                $(".goods-class-choice-attribute-body div:nth-of-type(n+7)").hide()
            }
            URLfilter_attrIdsArr.forEach(function (item) {
                $('.goods-attribute').each(function () {
                    if ($(this).attr('attrvalueid') == item) {

                        $(this).addClass('active')
                        if ($('.goods-attribute').index(this) >= 6) {
                            $('.goods-class-choice-attribute-title-right>.i-up').show()
                            $('.goods-class-choice-attribute-title-right>.i-down').hide()
                            $(".goods-class-choice-attribute-body div:nth-of-type(n+7)").show()
                        }
                    }
                })
            })
            $('.goods-class-choice-attribute-body').each(function () {
                var attributeName = ''
                var brandSevarcedDel = ''
                $(this).children().find('.goods-attribute').each(function () {
                    if ($(this).hasClass('active')) {
                        // 找出选中的重新赋值
                        brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                    }
                })
                $(this).parent().children().find('.attribute-selected').text(brandSevarcedDel.substring(1))
            })
        }else{
           
        }

    }


    //切换分类保存选中值
    function checkTypeStatus(childrencategoryId) {
        var URLfilter_deptList = getUrlParams('filter_depts')
        var URLfilter_categoryId = getUrlParams('filter_categoryId') //分类的选中值
        var URLfilter_brands = getUrlParams('filter_brands') //选中的品牌
        var URLfilter_attrIds = getUrlParams('filter_attrIds') //选中分类值
        //切换到品牌选中状态 
        if (URLfilter_brands && URLfilter_brands != '' && URLfilter_categoryId == childrencategoryId) {
            var URLfilter_brandsArr = URLfilter_brands.split(',')
            $('.brand-selected').text('')
            $('.goods-brand').removeClass('active')
            if ($('.goods-brand').length >= 7) {
                $('.goods-class-choice-brand-title-right>.i-up').hide()
                $('.goods-class-choice-brand-title-right>.i-down').show()
                $(".goods-class-choice-brand-body div:nth-of-type(n+7)").hide()
            }


            var brandSelectedVal = ''
            URLfilter_brandsArr.forEach(function (item) {
                $('.goods-brand').each(function () {
                    if ($(this).attr('brandid') == item) {
                        $(this).addClass('active')
                        if ($('.goods-brand').index(this) >= 6) {
                            $('.goods-class-choice-brand-title-right>.i-up').show()
                            $('.goods-class-choice-brand-title-right>.i-down').hide()
                            $(".goods-class-choice-brand-body div:nth-of-type(n+7)").show()
                        }
                        brandSelectedVal += $(this).text() + '、'
                    }
                })
            })
            $('.brand-selected').text(brandSelectedVal.replace(/、$/, ''))
        }
      
        //切换科室
        if (URLfilter_deptList && URLfilter_deptList != '' && URLfilter_categoryId == childrencategoryId) {
            var URLfilter_deptListArr = URLfilter_deptList.split(',')
            
            $('.Department-selected').text('')
            $('.goods-Department').removeClass('active')
            var DepartmentName = ''
            // 如果品牌的元素大于六条先将状态恢复为默认收起
            if ($('.goods-Department').length >= 7) {
                $('.goods-class-choice-Department-title-right>.i-up').hide()
                $('.goods-class-choice-Department-title-right>.i-down').show()
                $(".goods-class-choice-Department-body div:nth-of-type(n+7)").hide()
            }
            URLfilter_deptListArr.forEach(function () {
                $('.goods-Department').each(function () {
                    if ($(this).attr('Departmentid') == item) {
                        $(this).addClass('active')
                        if ($('.goods-Department').index(this) >= 7) {
                            $('.goods-class-choice-Department-title-right>.i-up').show()
                            $('.goods-class-choice-Department-title-right>.i-down').hide()
                            $(".goods-class-choice-Department-body div:nth-of-type(n+7)").show()
                        }
                        DepartmentName += $(this).text() + '、'
                    }
                })
            })
            $('.Department-selected').text(DepartmentName.replace(/、$/, ''))
        }
        //切换到属性的选中状态
        if (URLfilter_attrIds && URLfilter_attrIds != '' && URLfilter_categoryId == childrencategoryId) {
            var URLfilter_attrIdsArr = URLfilter_attrIds.split(',')
            $('.goods-attribute').removeClass('active')
            $('.attribute-selected').text('')
            if ($('.goods-attribute').length >= 7) {
                $('.goods-class-choice-attribute-title-right>.i-up').hide()
                $('.goods-class-choice-attribute-title-right>.i-down').show()
                $(".goods-class-choice-attribute-body div:nth-of-type(n+7)").hide()
            }

            URLfilter_attrIdsArr.forEach(function (item) {
                $('.goods-attribute').each(function () {
                    if ($(this).attr('attrvalueid') == item) {
                        if ($('.goods-attribute').index(this) >= 6) {
                            $('.goods-class-choice-attribute-title-right>.i-up').show()
                            $('.goods-class-choice-attribute-title-right>.i-down').hide()
                            $(".goods-class-choice-attribute-body div:nth-of-type(n+7)").show()
                        }
                        $(this).addClass('active')
                    }
                })
            })
            $('.goods-class-choice-attribute-body').each(function () {
                var attributeName = ''
                var brandSevarcedDel = ''
                $(this).children().find('.goods-attribute').each(function () {
                    if ($(this).hasClass('active')) {
                        // 找出选中的重新赋值
                        brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                    }
                })
                $(this).parent().children().find('.attribute-selected').text(brandSevarcedDel.substring(1))
                //    console.log(attributeName)
            })


        }


    }
    // 统计分类和品牌数量 展开收起图标


    function typeBrandToal() {
        if ($('.goods-type').length < 7) {
            $('.goods-class-choice-type-title-right>.i-up').hide()
            $('.goods-class-choice-type-title-right>.i-down').hide()
        }
        if ($('.goods-brand').length < 7) {
            $('.goods-class-choice-brand-title-right>.i-up').hide()
            $('.goods-class-choice-brand-title-right>.i-down').hide()
        }
        if ($('.goods-type').length >= 7) {
            $('.goods-class-choice-type-title-right>.i-up').hide()
            $('.goods-class-choice-type-title-right>.i-down').show()
        }
        if ($('.goods-brand').length >= 7) {
            $('.goods-class-choice-brand-title-right>.i-up').hide()
            $('.goods-class-choice-brand-title-right>.i-down').show()
        }
        if ($('.goods-Department').length >= 7) {
            $('.goods-class-choice-Department-title-right>.i-up').hide()
            $('.goods-class-choice-Department-title-right>.i-down').show()
        } else {
            $('.goods-class-choice-Department-title-right>.i-up').hide()
            $('.goods-class-choice-Department-title-right>.i-down').hide()
        }
    }

    getactiveStatus()
    typeBrandToal()
    // 筛选搜索 关键字+分类+品牌
    function searchFilter() {
        // $('.goods-class-body').children().remove()
        var keywords = $('#historysearch').val() ? $('#historysearch').val() : ''
        var category = $('.categoryVal').val()
        var brand = $('.brandVal').val()
        var Mfilter_category = ''


        var Mfilter_brands = []
        // filter_brands.length=0
        $('.goods-brand').each(function () {
            if ($(this).hasClass('active')) {
                Mfilter_brands.push(+$(this).attr('brandid'))
            }
        })
        var Mfilter_depts = []
        // filter_depts.length=0
        $('.goods-Department').each(function () {
            if ($(this).hasClass('active')) {
                Mfilter_depts.push(+$(this).attr('Departmentid'))
            }
        })
        var Mfilter_attrIds = []
        var Mfilter_attrNameIds = []
        // filter_attrIds.length=0
        $('.goods-attribute').each(function () {
            if ($(this).hasClass('active')) {
                Mfilter_attrIds.push(+$(this).attr('attrvalueid'))
                Mfilter_attrNameIds.push(+$(this).parents('.goods-class-choice-attribute').attr('data-attr'))
            }
        })
        Mfilter_attrIds = Mfilter_attrIds.join(',')
        Mfilter_depts = Mfilter_depts.join(',')
        Mfilter_brands = Mfilter_brands.join(",")
        Mfilter_attrNameIds=Mfilter_attrNameIds.join(',')
        var currentPage = 1
        var url = '/index/list.html'
        url = url + '?' +
            'keyword=' + keywords +
            '&' + 'categoryId=' + category +
            '&' + 'brandId=' + brand +
            '&' + 'filter_categoryId=' + Mfilter_category +
            '&' + 'filter_brands=' + Mfilter_brands +
            '&' + 'filter_depts=' + Mfilter_depts +
            '&' + 'filter_attrIds=' + Mfilter_attrIds +
            '&' + 'attrNameIds=' + Mfilter_attrNameIds +
            '&' + 'currentPage=' + currentPage



           window.location.href = url
    }

    // 选择分类
    $('.goods-class-choice-type-body').on('click', '.goods-type', function () {
        // debugger
        $('.brand_ChildrenList').hide()
        $('.brand_loading-gif').show()

        // 选中
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            // 根据分类获取品牌
            var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
            var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null
            var brandId = $('.brandVal').val() ? $('.brandVal').val() : null

            var url = '/index/list-api1.html'
            $.ajax({
                url: url,
                type: 'get',
                async: false,
                data: {
                    keyword: keywords,
                    categoryId: categoryId,
                    brandId: brandId
                },
                dataType: 'json',
                success: function (res) {
                    if (res.success) {
                        var resData = res.data.filterCondition
                        $('.goods-class-choice-attribute').children().remove()   
                        $('.attribute_box').hide()
                        $('.brand-selected').text('')
                        $('.type-selected').text('')
                        $('.Department-selected').text('')
                        //渲染品牌数据
                        if (resData.brandList == null || resData.brandList.length == 0) {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').hide()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        } else {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').show()
                            var brandData = resData.brandList

                            var brandHtml = template('brandtest', {
                                brandlist: brandData
                            })

                            $('.goods-class-choice-brand-body').html(brandHtml)
                            if (brandData.length >= 7) {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').hide()
                            }
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        }
                        //渲染科室
                        if (resData.deptList == null || resData.deptList.length == 0) {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                            $('.goods-class-choice-Department').hide()
                        } else {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.goods-class-choice-Department').show()
                            var DepartmentData = resData.deptList
                            var DepartmentHtml = template('departmenttest', {
                                list: DepartmentData
                            })
                            $('.goods-class-choice-Department-body').html(DepartmentHtml)
                            if (DepartmentData.length >= 7) {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').hide()
                            }

                       
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                            $('.brand_ChildrenList').show()
                            $('.brand_loading-gif').hide()
                        }
                        if(scroll){
                            scroll.refresh()
                        }
                    } else {
                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }
                   
                },
                error: function (err) {
                    $('.brand_loading-gif').hide()
                    $('.brand_ChildrenList').fadeIn()
                }
            })
            // 未选中
        } else {
            var childrencategory = $(this).attr('cate_id')
            $(this).addClass('active')
            $(this).parent().siblings().children().removeClass('active')
            $('.type-selected').text($(this).text())
            $('.brand-selected').text('')

            // checkTypeStatus()
            // 根据分类获取品牌
            var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
            var filter_categoryId = $(this).attr('cate_id')
            var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null

            var url = '/index/list-api1.html'
            var brandId = $('.brandVal').val() ? $('.brandVal').val() : null
            
            $.ajax({
                url: url,
                type: 'get',
                async: false,
                data: {
                    keyword: keywords,
                    categoryId: categoryId,
                    filter_categoryId: filter_categoryId,
                    brandId: brandId
                },
                dataType: 'json',
                success: function (res) {
                    if (res.success) {
                        $('.attribute_box').hide()
                        $('.brand-selected').text('')
                        // $('.type-selected').text('')
                        $('.Department-selected').text('')
                        var resData = res.data.filterCondition

                        // 渲染属性数据
                        if (resData.attrList == null || resData.attrList.length == 0) {
                            $('.attribute_box').children().remove()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                            $('.attribute_box').hide()
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                        } else {
                            $('.attribute_box').children().remove()
                            $('.attribute_box').show()
                            var attrData = resData.attrList
                            var attrHtml = template('test', {
                                list: attrData
                            })
                            $('.attribute_box').html(attrHtml)
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()

                        }
                        //渲染品牌数据
                        if (resData.brandList == null || resData.brandList.length == 0) {
                            $('.goods-class-choice-brand-body').children().remove()    
                            $('.goods-class-choice-brand').hide()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        } else {
                            $('.goods-class-choice-brand-body').children().remove()
                            $('.goods-class-choice-brand').show()
                            var brandData = resData.brandList

                            var brandHtml = template('brandtest', {
                                brandlist: brandData
                            })
                            $('.goods-class-choice-brand-body').html(brandHtml)
                            if (brandData.length >= 7) {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-brand-title-right>.i-up').hide()
                                $('.goods-class-choice-brand-title-right>.i-down').hide()
                            }

                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                        }
                        //渲染科室
                        if (resData.deptList == null || resData.deptList.length == 0) {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.brand_loading-gif').hide()
                            $('.brand_ChildrenList').fadeIn()
                            $('.goods-class-choice-Department').hide()
                        } else {
                            $('.goods-class-choice-Department-body').children().remove()
                            $('.goods-class-choice-Department').show()
                            var DepartmentData = resData.deptList
                            var DepartmentHtml = template('departmenttest', {
                                list: DepartmentData
                            })
                            $('.goods-class-choice-Department-body').html(DepartmentHtml)
                            if (DepartmentData.length >= 7) {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').show()
                            } else {
                                $('.goods-class-choice-Department-title-right>.i-up').hide()
                                $('.goods-class-choice-Department-title-right>.i-down').hide()
                            }

                        
                            $('.i-up').each(function(){
                                if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                    $(this).prev().css('marginRight','10px')
                                }else{
                                    $(this).prev().css('marginRight','0px')
                                }
                            })
                            $('.brand_ChildrenList').show()
                            $('.brand_loading-gif').hide()
                        }

                    } else {

                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }
                  
                    checkTypeStatus(childrencategory)
                    if(scroll){
                        scroll.refresh()
                    }
                },
                error: function (err) {
                    $('.brand_ChildrenList').fadeIn()
                    $('.attribute_box').hide()
                }

            })
            // typeBrandToal()
        }
    })
    // 选择品牌
    $('.goods-class-choice-brand-body').on('click', '.goods-brand', function () {
        // 选中
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            var brandSevarcedDel = ''
            $('.goods-brand').each(function () {
                if ($(this).hasClass('active')) {
                    // 找出选中的重新赋值
                    brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                }
            })
            $('.brand-selected').text(brandSevarcedDel.substring(1))
            // 未选中
        } else {
            $(this).addClass('active')
            // 点一下拼接一个
            if ($('.brand-selected').text().length == 0) {
                $('.brand-selected').text(($('.brand-selected').text() + $(this).text()))
            } else {
                $('.brand-selected').text(($('.brand-selected').text() + '、' + $(this).text()))
            }
        }
    })
    // 选择科室
    $('.goods-class-choice-Department-body').on('click', '.goods-Department', function () {
        // 选中
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            var brandSevarcedDel = ''
            $('.goods-Department').each(function () {
                if ($(this).hasClass('active')) {
                    // 找出选中的重新赋值
                    brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                }
            })
            $('.Department-selected').text(brandSevarcedDel.substring(1))
            // 未选中
        } else {
            $(this).addClass('active')
            // 点一下拼接一个
            if ($('.Department-selected').text().length == 0) {
                $('.Department-selected').text(($('.Department-selected').text() + $(this).text()))
            } else {
                $('.Department-selected').text(($('.Department-selected').text() + '、' + $(this).text()))
            }
        }
    })
    // 选择属性
    $('.attribute_box').on('click', '.goods-attribute', function () {
        // 选中
        var texts = $(this).parent().parent().parent().find('.attribute-selected');
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            var brandSevarcedDel = ''
            $(this).parent().parent().find('.goods-attribute').each(function () {
                if ($(this).hasClass('active')) {
                    // 找出选中的重新赋值
                    brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                }
            })
            texts.text(brandSevarcedDel.substring(1))
            // 未选中
        } else {
            $(this).addClass('active')
            // 点一下拼接一个
            if (texts.text().length == 0) {
                texts.text((texts.text() + $(this).text()))
            } else {
                texts.text((texts.text() + '、' + $(this).text()))
            }
        }
    })
    // 选择材质
    $('.goods-class-choice-material-body').on('click', '.goods-material', function () {
        // 选中
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            var brandSevarcedDel = ''
            $('.goods-material').each(function () {
                if ($(this).hasClass('active')) {
                    // 找出选中的重新赋值
                    brandSevarcedDel = brandSevarcedDel + '、' + $(this).text()
                }
            })
            $('.material-selected').text(brandSevarcedDel.substring(1))
            // 未选中
        } else {
            $(this).addClass('active')
            // 点一下拼接一个
            if ($('.material-selected').text().length == 0) {
                $('.material-selected').text(($('.material-selected').text() + $(this).text()))
            } else {
                $('.material-selected').text(($('.material-selected').text() + '、' + $(this).text()))
            }
        }
    })
    // 懒加载请求
    var canAjax = true;

    function lazyLoading(currentPage) {
        // 传参
        var keywords = $('#historysearch').val() ? $('#historysearch').val() : ''
        var category = $('.categoryVal').val()
        var brand = $('.brandVal').val()
        var filter_category = getUrlParams('filter_categoryId') //分类的选中值
        var filter_attrIds = getUrlParams('filter_attrIds') //选中分类值
        var filter_depts = getUrlParams('filter_depts')
        var filter_brands = getUrlParams('filter_brands') //选中的品牌
        // 请求
        var url = '/index/list-api1.html'
        if (canAjax) {
            canAjax = false;
            $.ajax({
                url: url,
                type: 'get',
                data: {
                    keyword: keywords,
                    categoryId: category,
                    brandId: brand,
                    filter_categoryId: filter_category,
                    filter_brands: filter_brands,
                    filter_depts: filter_depts,
                    filter_attrIds: filter_attrIds,
                    currentPage: currentPage
                },
                dataType: 'json',
                beforeSend: function () {
                    $('.loading-gif').show()
                },
                success: function (data) {
                   
                    var res = data.data.pageInfo.list; //列表
                    if(!data.success||res.length==0){
                        $('.loading-gif').hide()
                    }
                    $.each(res, function (i, val) {


                        var newGoods = ''
                        var hotGoods = ''
                        var sevenGoods = ''

                        if (res[i].isNew) {
                            newGoods = 'new-img'

                        } else {
                            newGoods = 'new-none'

                        }
                        if (res[i].isHot) {
                            hotGoods = 'hot-img'

                        } else {
                            hotGoods = 'hot-none'
                        }

                        if (res[i].isSeven) {
                            sevenGoods = 'seven-img'

                        } else {
                            sevenGoods = 'seven-none'
                        }
                        if(res[i].isMjx){
                            mjsGoods = 'jx-img'
                        }else{
                            mjsGoods = 'jx-none'
                        }

                        var priceShowType = ''
                        var marketPrice = toDecimal2(res[i].jxMarketPrice)
                        if ($('.checkPriceAuth').val() == 'true'&&marketPrice!=0) {

                            priceShowType = 'goods-class-one-kind-desc-price'

                        } else {

                            priceShowType = 'goods-class-one-kind-desc-price hidden'
                        }
                        if (res[i].modelOrSpecValue) {
                            modelShowType = 'goods-class-one-kind-desc-type'
                        } else {
                            modelShowType = 'goods-class-one-kind-desc-type hidden'

                        }



                        if (res != []) {
                            $('.goods-class-body').append(
                                '<a p="' + currentPage + '" href="/goods/' + res[i].skuNo + '/goods-detail.html" class="goods-class-one-kind">' +
                                '<div class="goods-class-one-kind-pic">' + '<img src="' + res[i].picUrl + '">' +
                                '</div>' +
                                '<div class="goods-class-one-kind-desc">' +
                                '<div class="goods-class-one-kind-desc-title">' +
                                '<span class="word">' +
                                res[i].skuName +
                                '</span>' +
                                '</div>' +
                                '<div class="imgbox">' +
                                ' <div class="'+mjsGoods+'" th:if="${skuVo.isMjx}">'+
                                     '<img src="/images/goods-class/sjx.png" alt="">'+
                                  '</div>'+
                                '<div class="' + newGoods + '"><img src="/images/goods-class/new.png">&nbsp</div>' +
                                '<div class="' + hotGoods + '"><img src="/images/goods-class/hot.png">&nbsp</div>' +
                                '<div class="' + sevenGoods + '"><img src="/images/goods-class/sevenNoReason.png">&nbsp</div>' +
                                '</div>' +
                                '<div class="' + modelShowType + '">' +
                                res[i].modelOrSpecName + ':' + res[i].modelOrSpecValue +
                                '</div>' +
                                '<div class="goods-class-one-kind-desc-order">' +
                                '订货号：' + res[i].skuNo +
                                '</div>' +
                                '<div class="' + priceShowType + '">' +
                                '<span class="left">' +
                                '市场价：' +
                                '</span>' +
                                '<span class="right">' +
                                '￥<span class="right-int">' +
                                marketPrice +
                                '</span>' +
                                '</span>' +
                                '</div>' +
                                '</div>' +
                                '</a>'
                            )
                            $('.loading-gif').hide()
                            canAjax = true
                        } else {
                            canAjax = false
                        }
                    })
                    $('.loading-gif').hide()
                },
                error: function (err) {
                    canAjax = true

                    $('.loading-gif').hide()
                }
            })
        }
    }
    // 懒加载
    // 获取浏览器窗口的可视区域的高度
    // function getViewPortHeight() {
    //     return document.documentElement.clientHeight || document.body.clientHeight;
    // }
    //获取滚动条当前位置
    function getScrollTop() {
        var scrollTop = 0;
        if (document.documentElement && document.documentElement.scrollTop) {
            scrollTop = document.documentElement.scrollTop;
        } else if (document.body) {
            scrollTop = document.body.scrollTop;
        }
        return scrollTop;
    }

    //获取文档完整的高度
    function getScrollHeight() {
        return Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);
    }

    //获取当前可视范围的高度
    function getClientHeight() {
        var clientHeight = 0;
        if (document.body.clientHeight && document.documentElement.clientHeight) {
            clientHeight = Math.min(document.body.clientHeight, document.documentElement.clientHeight);
        } else {
            clientHeight = Math.max(document.body.clientHeight, document.documentElement.clientHeight);
        }
        return clientHeight;
    }

    var fixedHeight = ''
    var timer;
    $(document).scroll(function () {
        var totalCount = $('.J-totalcount').val()
        var totalPage = Math.ceil(totalCount / 10);
        var scrollTop  = getScrollTop()
        fixedHeight = scrollTop
        var oneHeight = 140
        // 条数
        var n = 10
        var currentPage = $('.goods-class-body').children('a:last-child').attr('p')
        // var reqHeight = oneHeight * n * currentPage * 0.57 - 50;
        currentPage = +currentPage + 1; 
        if (totalPage >= currentPage && getScrollTop() + getClientHeight() >= getScrollHeight()) {
           $('.loading-gif').show()
                // currentPage++;
               lazyLoading(currentPage); 
        }
        if (scrollTop < 80) {
            $('.goods-class-title').css({
                'box-shadow': ''
            })
        }
        if (scrollTop >= 80) {
            $('.goods-class-title').css({
                'box-shadow': '0 3px 5px rgba(0,0,0,0.15)'
            })
        }
    })

    // 返回键
    $('.goods-class-title-left').on('click', function () {

        if (getQueryString("keywords") && getQueryString("keywords") != '' && getQueryString("keywords") != 'null') {
            window.history.go(-1)
        } else {
            window.location.href = "/index.html";
        }
    })
    // 回到首页
    $('.back-index').on('click', function () {
        window.location.href = '/index.html'
    })
    // 回到首页
    $('.go-index').on('click', function () {
        window.location.href = '/index.html'
    })
    // 搜索框监听、聚焦、失焦、清空
    $('.goods-class-input').bind('input propertychange', function () {
        if ($('.goods-class-input').val().length > 0) {
            $('.i-error2').show()
        } else if ($('.goods-class-input').val().length == 0) {
            setTimeout(function () {
                $('.i-error2').hide()
            }, 300)
        }
    })
    $('.goods-class-input').focus(function () {
        if ($('.goods-class-input').val().length > 0) {
            $('.i-error2').show()
        } else if ($('.goods-class-input').val().length == 0) {
            setTimeout(function () {
                $('.i-error2').hide()
            }, 300)
        }
    })
    $('.goods-class-input').blur(function () {
        setTimeout(function () {
            $('.i-error2').hide()
        }, 300)
    })
    $('.i-error2').on('click', function () {
        $('.goods-class-input').val('')
        setTimeout(function () {
            $('.i-error2').hide()
        }, 300)
        $('.goods-class-input').focus()
    })
    $('.icon-search').on('click', function () {
        $('.goods-class-input').focus()
    })

    // 分类点击收起
    $('.goods-class-choice-type-title-right .i-up').on('click', function () {
        var that = $(this)
        retract(that, '.goods-class-choice-type-body div:nth-of-type(n+7)')
    })

    // 分类点击展开
    $('.goods-class-choice-type-title-right .i-down').on('click', function () {
        var that = $(this)
        spread(that, '.goods-class-choice-type-body div:nth-of-type(n+7)')
    })

    // 品牌点击收起
    $('.goods-class-choice-brand-title-right .i-up').on('click', function () {
        var that = $(this)
        retract(that, '.goods-class-choice-brand-body div:nth-of-type(n+7)')
    })
    // 品牌点击展开
    $('.goods-class-choice-brand-title-right .i-down').on('click', function () {

        var that = $(this)
        spread(that, '.goods-class-choice-brand-body div:nth-of-type(n+7)')
    })

    // 科室点击收起
    $('.goods-class-choice-Department-title-right .i-up').on('click', function () {
        var that = $(this)
        retract(that, '.goods-class-choice-Department-body div:nth-of-type(n+7)')
    })
    // 科室点击展开
    $('.goods-class-choice-Department-title-right .i-down').on('click', function () {
        var that = $(this)
        spread(that, '.goods-class-choice-Department-body div:nth-of-type(n+7)')
    })
    // 属性点击收起
    $('.attribute_box').on('click','.goods-class-choice-attribute-title-right .i-up', function () {
        var that = $(this)
        retract(that, '.goods-class-choice-attribute-body div:nth-of-type(n+7)')
    })
    // 属性点击展开
    $('.attribute_box').on('click', '.goods-class-choice-attribute-title-right .i-down',function () {
        console.log( $(this))
        var that = $(this)
        spread(that, '.goods-class-choice-attribute-body div:nth-of-type(n+7)')
    })
    // 材质点击收起
    $('.goods-class-choice-material-title-right .i-up').on('click', function () {
        var that = $(this)
        retract(that, '.goods-class-choice-material-body div:nth-of-type(n+7)')
    })
    // 材质点击展开
    $('.goods-class-choice-material-title-right .i-down').on('click', function () {

        var that = $(this)
        spread(that, '.goods-class-choice-material-body div:nth-of-type(n+7)')
    })

    // 禁止页面拖动
    document.querySelector(".mc").addEventListener(
        "touchmove",
        function (e) {
            e.preventDefault(); //阻止默认的处理方式(阻止下拉滑动的效果)
        }, {
            passive: false
        }
    ); //passive 参数不能省略，用来兼容ios和android
    // 禁止页面拖动
    document.querySelector(".brand_loading-gif").addEventListener(
        "touchmove",
        function (e) {
            e.preventDefault(); //阻止默认的处理方式(阻止下拉滑动的效果)
        }, {
            passive: false
        }
    ); //passive 参数不能省略，用来兼容ios和android  
    function getScrollOffset() {
        /*
         * @Author: @Guojufeng
         * @Date: 2019-01-31 10:58:54
         * 获取页面滚动条的距离-兼容写法封装
         */
        if (window.pageXOffset) {
            return {
                x: window.pageXOffset,
                y: window.pageYOffset
            }
        } else {
            return {
                x: document.body.scrollLeft || document.documentElement.scrollLeft,
                y: document.body.scrollTop || document.documentElement.scrollTop
            }
        }
    };
    /* 动态获取当前页面的滚动位置 */
    var scrollT = null;
    var LastScrollT = 0;
    window.onscroll = function (e) {
        scrollT = getScrollOffset().y; //滚动条距离
    }
    // 弹出弹框
    $('.goods-class-title-right').on('click', function () {
        initScroll('.choice_main');
        // $('html,body').addClass('Bactive')
        //  在这里获取滚动的距离，赋值给body，好让他不要跳上去。
        document.body.style.overflow = 'hidden';
        document.body.style.position = 'fixed';
        document.body.style.top = -scrollT + 'px'; //改变css中top的值，配合fixed使用
        // 然后找个变量存一下刚才的scrolltop，要不然一会重新赋值，真正的scrollT会变0
        LastScrollT = scrollT;
        $('.mc').fadeIn()
        $('.goods-class-choice-wrapper').animate({
            right: '0'
        }, 200, 'ease-out-in')


        $('.goods-class-choice').animate({
            right: '0'
        }, 200, 'ease-out-in')

        $('.goods-class-bottom').animate({
            right: '0'
        }, 200, 'ease-out-in')
        // 禁止页面滚动
        $('body').css({
            'position': 'fixed',
            'top': -fixedHeight
        })
        $('body').attr('bodytop', fixedHeight)
    })
    // 关闭弹框
    $('.mc').on('click', function () {
        cleanbScroll()
        if(timer){
            clearTimeout(timer)
        }
        $('.mc').fadeOut(200)
        document.body.style.overflow = 'auto';
        document.body.style.position = 'static';
        $('.goods-class-choice-wrapper').animate({
            right: '-100%'
        }, 200, 'ease-out-in')
        $('.goods-class-choice').animate({
            right: '-100%'
        }, 200, 'ease-in-out')
        $('.goods-class-bottom').animate({
            right: '-100%'
        }, 200, 'ease-in-out')
        $('.goods-class-left').hide()
        setTimeout(function () {
            getactiveStatus()
        }, 220)

        // 允许页面滚动
        $('body').css('position', '')
        window.scrollTo(0, $('body').attr('bodytop'))

    })

    // 重置弹框
    $('.goods-class-reset').on('click', function () {
        $('.goods-type').removeClass('active')
        $('.type-selected').text('')
        $('.goods-brand').removeClass('active')
        $('.brand-selected').text('')
        $('.goods-Department').removeClass('active')
        $('.Department-selected').text('')
        $('.goods-attribute').removeClass('active')
        $('.attribute-selected').text('')
        $('.attribute_box').hide()
        $(this).removeClass('active')
        // 根据分类获取品牌
        var keywords = $('#historysearch').val() ? $('#historysearch').val() : null
        var categoryId = $('.categoryVal').val() ? $('.categoryVal').val() : null
        var brandId = $('.brandVal').val() ? $('.brandVal').val() : null

        var url = '/index/list-api1.html'
        $.ajax({
            url: url,
            type: 'get',
            async: false,
            data: {
                keyword: keywords,
                categoryId: categoryId,
                brandId: brandId
            },
            dataType: 'json',
            success: function (res) {
                if (res.success) {
                    var resData = res.data.filterCondition
                    $('.goods-class-choice-attribute').children().remove()      
                    $('.attribute_box').hide()
                    $('.brand-selected').text('')
                    $('.type-selected').text('')
                    $('.Department-selected').text('')
                    //渲染品牌数据
                    if (resData.brandList == null || resData.brandList.length == 0) {
                        $('.goods-class-choice-brand-body').children().remove()
                        $('.goods-class-choice-brand').hide()
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                    } else {
                        $('.goods-class-choice-brand-body').children().remove()
                        $('.goods-class-choice-brand').show()
                        var brandData = resData.brandList

                        var brandHtml = template('brandtest', {
                            brandlist: brandData
                        })

                        $('.goods-class-choice-brand-body').html(brandHtml)
                        if (brandData.length >= 7) {
                            $('.goods-class-choice-brand-title-right>.i-up').hide()
                            $('.goods-class-choice-brand-title-right>.i-down').show()
                        } else {
                            $('.goods-class-choice-brand-title-right>.i-up').hide()
                            $('.goods-class-choice-brand-title-right>.i-down').hide()
                        }
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                    }
                    //渲染科室
                    if (resData.deptList == null || resData.deptList.length == 0) {
                        $('.goods-class-choice-Department-body').children().remove()
                        $('.brand_loading-gif').hide()
                        $('.brand_ChildrenList').fadeIn()
                        $('.goods-class-choice-Department').hide()
                    } else {
                        $('.goods-class-choice-Department-body').children().remove()
                        $('.goods-class-choice-Department').show()
                        var DepartmentData = resData.deptList
                        var DepartmentHtml = template('departmenttest', {
                            list: DepartmentData
                        })
                        $('.goods-class-choice-Department-body').html(DepartmentHtml)
                        if (DepartmentData.length >= 7) {
                            $('.goods-class-choice-Department-title-right>.i-up').hide()
                            $('.goods-class-choice-Department-title-right>.i-down').show()
                        } else {
                            $('.goods-class-choice-Department-title-right>.i-up').hide()
                            $('.goods-class-choice-Department-title-right>.i-down').hide()
                        }

                       
                        $('.i-up').each(function(){
                            if($(this).css("display")=="none"&&$(this).next().css("display")=="none"){
                                $(this).prev().css('marginRight','10px')
                            }else{
                                $(this).prev().css('marginRight','0px')
                            }
                        })
                        $('.brand_ChildrenList').show()
                        $('.brand_loading-gif').hide()
                    }
                    if(scroll){
                        scroll.refresh()
                    }
                    
                } else {
                    $('.brand_ChildrenList').show()
                    $('.brand_loading-gif').hide()
                }
               
            },
            error: function (err) {
                $('.brand_loading-gif').hide()
                $('.brand_ChildrenList').fadeIn()
            }
        })



        typeBrandToal()
        // 分类点击收起
        $(".goods-class-choice-type-body div:nth-of-type(n+7)").hide()
        // 品牌点击收起
        $(".goods-class-choice-brand-body div:nth-of-type(n+7)").hide()
        // 科室点击收起
        $(".goods-class-choice-Department-body div:nth-of-type(n+7)").hide()
        //属性点击收起
        $(".goods-class-choice-attribute-body div:nth-of-type(n+7)").hide()

    })
    // 确定弹框
    $('.goods-class-confirm').on('click', function () {
        searchFilter()
        // activeFlag = 1;
        // 允许页面滚动
        $('body').css('position', '')
        window.scrollTo(0, $('body').attr('bodytop'))
    })

    //列表页各模块初始化加载动效参数
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
    }
    //列表页初始化加载动效
    $.each(initHandle, function (key, item) {
        animateMove(item.ele, item.parames)
    })
    //点击触发动效参数
    var clickHandle = {
        //咨询图标消失
        'consult-icon-hide': {
            ele: $('#consultation .consult-icon'),
            parames: {
                properties: {
                    scale: 0
                },
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)'
            }
        },
        //咨询图标显现
        'consult-icon-show': {
            ele: $('#consultation .consult-icon'),
            parames: {
                properties: {
                    scale: 1
                },
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
                delay: 350
            }
        },
        //咨询信息显现
        'sus-con-show': {
            ele: $('.sus-con'),
            parames: {
                properties: {
                    bottom: '15px'
                },
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)'
            }
        },
        //咨询信息消失
        'sus-con-hide': {
            ele: $('.sus-con'),
            parames: {
                properties: {
                    bottom: '-480px'
                },
                duration: 200,
                easing: 'cubic-bezier(0.645, 0.045, 0.355, 1)',
                delay: 100
            }
        }
    }
    $('.sus-tel').on('click', function () {
        $('.sus-con').animate({
            bottom: '-480px'
        }, 200, 'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
        $('.consult-icon').animate({
            scale: 1
        }, 200, 'cubic-bezier(0.645, 0.045, 0.355, 1)', function () {}, 600)
    })
    $('.sus-customer').on('click', function () {
        $('.sus-con').animate({
            bottom: '-480px'
        }, 200, 'cubic-bezier(0.645, 0.045, 0.355, 1)')
        $('.sus-cover').fadeOut(800);
        $('.consult-icon').animate({
            scale: 1
        }, 200, 'cubic-bezier(0.645, 0.045, 0.355, 1)', function () {}, 600)
        setTimeout(function () {
            window.location.href = 'http://mjx.vedeng.com/chatdialog.html?websiteid=150272&wc=f899b5';
        }, 150)
    })
    //点击图标,背景层,提示信息显现,图标,提示信息消失
    $('#consultation .consult-icon').on('click', function () {
        $('.suspension .sus-cover').fadeIn();
        animateMove(clickHandle['sus-con-show'].ele, clickHandle['sus-con-show'].parames);
        animateMove(clickHandle['consult-icon-hide'].ele, clickHandle['consult-icon-hide'].parames);
    })
    //点击背景层,背景层消失,提示信息隐藏,图标显示
    $('.suspension .sus-cover').on('click', function () {
        setTimeout(function () {
            $('.suspension .sus-cover').fadeOut(300);
        }, 300)
        animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames);
        animateMove(clickHandle['consult-icon-show'].ele, clickHandle['consult-icon-show'].parames);
    })
    //点击取消,背景层消失,提示信息隐藏,图标显示
    $('.suspension .service .cannel').on('click', function () {
        setTimeout(function () {
            $('.suspension .sus-cover').fadeOut(300);
        }, 300)
        animateMove(clickHandle['sus-con-hide'].ele, clickHandle['sus-con-hide'].parames);
        animateMove(clickHandle['consult-icon-show'].ele, clickHandle['consult-icon-show'].parames);
    })
    //animate动画封装
    function animateMove(ele, opt) {

        opt.properties = opt.properties || '';
        opt.duration = opt.duration || 300;
        opt.easing = opt.easing || 'linear';
        opt.callback = opt.callback || function () {};
        opt.delay = opt.delay || 0;

        ele.animate(opt.properties, opt.duration, opt.easing, opt.callback, opt.delay)
    }
    $("#historysearch").click(function () {
        var kw = $(this).val().trim();
        if (kw.length >= 0) {
            window.location.href = "/index/search.html?keywords=" + kw;
        }
    })

    

    // CC客服
    var c = document.createElement('script');
    c.src = '//kefu.ziyun.com.cn/vclient/?webid=150272&wc=f899b5';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(c, s);
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