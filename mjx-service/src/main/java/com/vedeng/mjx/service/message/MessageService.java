package com.vedeng.mjx.service.message;


import com.vedeng.mjx.common.page.PageModel;
import com.vedeng.mjx.domain.MessageCount;
import com.vedeng.mjx.domain.VMessageType;
import com.vedeng.mjx.domain.Vmessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MessageService {


    int insertVmessage(Vmessage vmessage);

    public int updateVmessage(Vmessage messageId);

    public String updateVmessageRead(Vmessage vmessage);

    List<Vmessage> getNoReadMessage(Integer accountId,Integer messageType);

    Vmessage getOneMessage(Integer messageId);


    List<Vmessage> getPageMessage(Integer userId,Integer messageType, PageModel page);

    List<MessageCount> selectCount(Integer userId);

    int updateMessage(Integer userId,Integer messageType);

    int selectNoReadCount(Integer userId);

    int getTotalCount(Integer accountId,Integer messageType);

}