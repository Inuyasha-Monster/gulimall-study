package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author djl
 * @create 2021/8/2 22:03
 */
@Data
public class UnlockSeckillStockVo {
    private Long skuId;
    private Integer lockCount;
    private Long relationId;
    private Long wareId;
}
