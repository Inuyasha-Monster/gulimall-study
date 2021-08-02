package com.atguigu.gulimall.coupon.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author djl
 * @create 2021/8/2 21:25
 */
@FeignClient("gulimall-product")
public interface ProductFeignService {
    /**
     * 根据skuId查询sku信息
     * @param skuId
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
    R getInfo(@PathVariable("skuId") Long skuId);
}
