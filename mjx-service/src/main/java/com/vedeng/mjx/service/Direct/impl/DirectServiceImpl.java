package com.vedeng.mjx.service.Direct.impl;

import com.vedeng.mjx.mapper.VMessageMapper;
import com.vedeng.mjx.service.Direct.DirectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DirectServiceImpl implements DirectService {

    @Resource
    private VMessageMapper vmessageMapper;

    @Override
    public List<Object> getDirectUrl(Integer accountId) {
        long noReadNum = vmessageMapper.selectNoReadNum(accountId);
        List listMenu=new ArrayList();
        //首页地址
        listMenu.add("/app/index");
        //搜索地址
        listMenu.add("/app/search");
        //采购车
        listMenu.add("/app/shopcart");
        //个人中心
        listMenu.add("/app/myindex");
        //消息
        listMenu.add("/app/message/getAll/"+noReadNum);
        //在线咨询
        listMenu.add("/app/chatdialog");



        return listMenu;
    }
}
