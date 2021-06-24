package com.atguigu.gulimall.auth.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author djl
 * @create 2021/6/24 22:40
 */
@FeignClient("gulimall-third-party")
public interface ThirdPartFeignService {

    /**
     * 发送验证码服务
     *
     * @param phone
     * @param code
     * @return
     */
    @GetMapping(value = "/sms/sendCode")
    R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
