package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2021/6/6 17:04
 */
@RestController
public class TestNacosConfigController {

    @Value("${spring.cloud.alicloud.access-key:fuck}")
    private String accessKey;

    @RequestMapping(value = "/test/accessKey", method = RequestMethod.GET)
    public R getAccessKey() {
        return R.ok().put("data", accessKey);
    }
}
