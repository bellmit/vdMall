package com.vedeng.mjx.service.message.impl;


import com.alibaba.fastjson.JSON;
import com.vedeng.mjx.common.enumUtils.MessageEnum;
import com.vedeng.mjx.common.page.PageModel;
import com.vedeng.mjx.common.util.VeDate;
import com.vedeng.mjx.domain.MessageCount;
import com.vedeng.mjx.domain.VMessageType;
import com.vedeng.mjx.domain.VMessageTypeExample;
import com.vedeng.mjx.domain.Vmessage;
import com.vedeng.mjx.mapper.VMessageMapper;
import com.vedeng.mjx.mapper.VMessageTypeMapper;
import com.vedeng.mjx.service.message.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {


    @Resource
    private VMessageMapper vmessageMapper;
    @Resource
    private VMessageTypeMapper messageTypeMapper;


    @Override
    public int insertVmessage(Vmessage vmessage) {
        vmessage.setReadTime(new Date());
        int returnNum = vmessageMapper.insertSelective(vmessage);
        return returnNum;
    }

    @Override
    public int updateVmessage(Vmessage vmessage) {

        int returnNum = vmessageMapper.updateByPrimaryKeySelective(vmessage);
        return returnNum;
    }

    @Override
    public String updateVmessageRead(Vmessage vmessage) {
        if (null == vmessage.getMessageId() && null == vmessage.getMessageType()) {
            return "请求数据为空，无法修改已读信息";
        } else if (null != vmessage.getMessageId()) {
            vmessageMapper.updateForId(vmessage.getMessageId(), new Timestamp(System.currentTimeMillis()));
            return "已读单条信息成功";
        } else {
            vmessageMapper.updateForType(vmessage.getMessageType(), new Timestamp(System.currentTimeMillis()));
            return "已读批量信息成功";
        }
    }

    @Override
    public List<Vmessage> getNoReadMessage(Integer accountId,Integer messageType) {
        List<Vmessage> listMessage = vmessageMapper.selectByPage(accountId,messageType);
        return listMessage;
    }

    @Override
    public Vmessage getOneMessage(Integer messageId) {
        Vmessage vmessage = vmessageMapper.selectByPrimaryKey(messageId);
        return vmessage;
    }

    @Override
    public List<Vmessage> getPageMessage(Integer userId, Integer messageType, PageModel page) {
        List<Vmessage> vmessage = vmessageMapper.selectByPage(userId, messageType);
        return vmessage;
    }

    @Override
    public List<MessageCount> selectCount(Integer userId) {

        List<MessageCount> messageCountList = new ArrayList<>();
        VMessageTypeExample example = new VMessageTypeExample();
        example.createCriteria().andStatusEqualTo(1).andIsDelEqualTo(0);
        List<VMessageType> messageTypeList = messageTypeMapper.selectByExample(example);
        for(VMessageType messageType : messageTypeList){
            MessageCount result = vmessageMapper.selectCountByUserIdAndMessageType(userId,messageType.getId());
            if(result != null && result.getMessageType() != null){
                messageCountList.add(result);
            }else{
                MessageCount messageCount = vmessageMapper.selectNewMessage(userId,messageType.getId());
                if(messageCount != null) {
                    messageCount.setCount(0);
                }else{
                    messageCount = new MessageCount();
                    messageCount.setMessageType(messageType.getId());
                    messageCount.setCount(0);
                    messageCount.setMessageTitle("");
                }
                messageCountList.add(messageCount);
            }
        }
        return messageCountList;
    }

    @Override
    public int updateMessage(Integer userId,Integer messageType) {
        return vmessageMapper.updateMessage(userId,messageType);
    }

    @Override
    public int selectNoReadCount(Integer userId) {
        return vmessageMapper.selectNoReadCount(userId);
    }

    @Override
    public int getTotalCount(Integer accountId, Integer messageType) {
        return vmessageMapper.getTotalCount(accountId,messageType);
    }
}
