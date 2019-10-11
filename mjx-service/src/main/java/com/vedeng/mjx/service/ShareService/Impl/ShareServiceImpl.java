//package com.vedeng.mjx.service.ShareService.Impl;
//
//import com.vedeng.mjx.domain.ShareInfo;
//import com.vedeng.mjx.service.ShareService.ShareService;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ShareServiceImpl implements ShareService {
//    @Override
//    public String getShareUrl(ShareInfo shareInfo) {
//        String listUrl = null;
//        int shareType = shareInfo.getShareType();
//        switch (shareType) {
//            //1 商品详情页
//            case 1:
//                String detailUrl = "/goods/" + shareInfo.getInfo() + "/goods-detail.html";
//                listUrl = detailUrl;
//                break;
//            //关于我们
//            case 2:
//                listUrl = "关于我们";
//                break;
//            // 活动详情页
//            case 3:
//                listUrl = "活动页";
//                break;
//
//        }
//        return listUrl;
//    }
//}
