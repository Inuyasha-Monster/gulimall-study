package com.atguigu.gulimall.coupon.feign;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.coupon.vo.LockSeckillStockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author djl
 * @create 2021/8/2 21:45
 */
@FeignClient("gulimall-ware")
public interface WareFeignService {
    /**
     * 根据skuID获取仓库剩余库存
     *
     * @param skuId
     * @return
     */
    @GetMapping("/ware/waresku/remaindStock/{skuId}")
    R getRemaindStock(@PathVariable("skuId") Long skuId);

    /**
     * 锁定秒杀库存
     *
     * @param lockSeckillStockVo
     * @return
     */
    @PostMapping("/ware/waresku/lockSeckillStock")
    R lockSeckillStock(@RequestBody LockSeckillStockVo lockSeckillStockVo);
}
