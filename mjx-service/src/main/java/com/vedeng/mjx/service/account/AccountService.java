package com.vedeng.mjx.service.account;

import com.vedeng.mjx.domain.TOpAccount;
import com.vedeng.mjx.domain.TopAccountJx;
import com.vedeng.mjx.domain.VVersion;
import com.vedeng.mjx.domain.VWxPhone;

import java.util.List;

/**
 * @InterfaceName AccountService
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-07-01 21:37
 * @Version 1.0
 **/
public interface AccountService {

    TopAccountJx selectBySsoAcountId(Integer ssoAccountId);

    Integer insertSelective(TopAccountJx topAccountJx);

    Integer updateByMobileSelective(TopAccountJx topAccountJx);

    TopAccountJx selectByAcountId(Integer acountId);

    boolean updateTopAccountJxByAccountId(TopAccountJx topAccountJx);

    TopAccountJx selectTopAccountJxByOpenId(String openId,String phone);

    TopAccountJx selectByPhone(String phone);

    int clearOpenidByPhone(String phone);

    VWxPhone selectWxphoneByPhone(String phone);

    VWxPhone selectWxphoneByOpenid(String openid);

    VWxPhone selectWxphone(String phone, String openid);

    int saveWxPhone(String phone,String openid);

    int updatexPhone(String phone,String openid);

    void initialize();

}
