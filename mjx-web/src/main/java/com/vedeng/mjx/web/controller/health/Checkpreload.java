package com.vedeng.mjx.web.controller.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: Wyatt
 * @Date: 2019-06-28 14:53
 */
@RestController
public class Checkpreload {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/checkpreload.html")
    public String health() {
        return "success";
    }

    @PostMapping("/frontError")
    public void frontError(@RequestBody String error) {
        logger.error("frontError:" + error);
    }
}

