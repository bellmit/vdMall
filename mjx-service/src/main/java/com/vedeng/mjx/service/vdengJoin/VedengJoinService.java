package com.vedeng.mjx.service.vdengJoin;

import com.vedeng.mjx.common.ResultInfo;
import com.vedeng.mjx.service.feign.passport.dto.ReqUserDto;

/**
 * @InterfaceName VedengJoinService
 * @Description TODO
 * @Author Cooper.xu
 * @Date 2019-07-01 16:59
 * @Version 1.0
 **/
public interface VedengJoinService {

    ResultInfo applyJoinVedeng(String mobile);

    void pushAcountDataToERP(String mobile);

}
