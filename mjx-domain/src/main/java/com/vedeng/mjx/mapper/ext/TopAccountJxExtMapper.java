package com.vedeng.mjx.mapper.ext;

import com.vedeng.mjx.domain.TOpAccount;
import com.vedeng.mjx.domain.TopAccountJx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TopAccountJxExtMapper {

    int updateByMobileSelective(TopAccountJx record);

    TopAccountJx selectByMobile(String mobile);

    TopAccountJx selectBySsoAcountId(Integer ssoAcountId);

    TopAccountJx selectByAcountId(Integer acountId);

    boolean updateTopAccountJxByAccountId(TopAccountJx topAccountJx);

    TopAccountJx selectTopAccountJxByOpenId(@Param("openId") String openId, @Param("phone") String phone);

    TopAccountJx selectByPhone(String phone);

    int clearOpenidByPhone(String phone);

    List<TopAccountJx> selectAccountList();

}