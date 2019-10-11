package com.vedeng.mjx.service.feign.passport;
import com.vedeng.passport.api.login.service.UserInfoApiService;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value = "${passportApiName}")
public interface UserInfoServiceFeignClient extends UserInfoApiService {

}
