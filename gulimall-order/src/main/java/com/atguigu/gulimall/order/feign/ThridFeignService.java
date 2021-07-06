package com.atguigu.gulimall.order.feign;

import com.alipay.api.AlipayApiException;
import com.atguigu.gulimall.order.vo.PayVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-08 21:14
 **/

@FeignClient("gulimall-third-party")
public interface ThridFeignService {

    @GetMapping(value = "/pay",consumes = "application/json")
    String pay(@RequestBody PayVo vo) throws AlipayApiException;

}
