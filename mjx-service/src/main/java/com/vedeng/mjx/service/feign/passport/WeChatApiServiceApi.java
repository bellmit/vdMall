package com.vedeng.mjx.service.feign.passport;//package com.vedeng.template.service.feign.user;
import com.vedeng.passport.api.wechat.service.WeChatApiService;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "${passportApiName}")
public interface WeChatApiServiceApi extends WeChatApiService {

}

