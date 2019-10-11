package com.vedeng.mjx.web.controller.app.message;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/app/")
@RestController
@Api(value = "App推送信息controller", tags = {"信息控制层"})
public class AppMessagePushController {


    private final Logger log = LoggerFactory.getLogger(this.getClass());

}
