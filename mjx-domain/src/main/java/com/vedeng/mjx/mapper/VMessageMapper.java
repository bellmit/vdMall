package com.vedeng.mjx.mapper;


import com.vedeng.mjx.domain.MessageCount;
import com.vedeng.mjx.domain.Vmessage;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface VMessageMapper {
//    int countByExample(VmessageExample example);
//
//    int deleteByExample(VmessageExample example);

    int deleteByPrimaryKey(Integer messageId);

    int insert(Vmessage record);

    int insertSelective(Vmessage record);

    List<Vmessage> selectByExample(Integer userId,Integer messageType);

    List<Vmessage> selectByPage(@Param("userId") Integer userId, @Param("messageType") Integer messageType);

    Vmessage selectByPrimaryKey(Integer messageId);

    int updateByExampleSelective(@Param("record") Vmessage record);

    int updateByExample(@Param("record") Vmessage record);

    int updateByPrimaryKeySelective(Vmessage record);

    int updateByPrimaryKey(Vmessage record);

    long selectNoReadNum(Integer userId);


    int updateForType(@Param("type") Integer type, @Param("time") Timestamp time);

    int updateForId(@Param("messageId") Integer messageId, @Param("time") Timestamp time);

    MessageCount selectCountByUserIdAndMessageType(@Param("userId") Integer userId, @Param("messageType") Integer messageType);

    int updateMessage(@Param("userId") Integer userId, @Param("messageType") Integer messageType);

    MessageCount selectNewMessage(@Param("userId") Integer userId, @Param("messageType") Integer messageType);

    int selectNoReadCount(Integer userId);

    int getTotalCount(@Param("userId") Integer userId, @Param("messageType") Integer messageType);

}