package com.vedeng.mjx.mapper;

import com.vedeng.mjx.domain.TopAccountJx;

public interface TopAccountJxMapper {
    int deleteByPrimaryKey(Integer accountId);

    int insert(TopAccountJx record);

    int insertSelective(TopAccountJx record);

    TopAccountJx selectByPrimaryKey(Integer accountId);

    int updateByPrimaryKeySelective(TopAccountJx record);

    int updateByPrimaryKey(TopAccountJx record);
}