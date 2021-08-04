package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author djl
 * @create 2021/8/4 22:24
 */
@Data
public class SeckillSkuRelationVo {
    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    @NotNull
    private Long promotionSessionId;
    /**
     * 商品id
     */
    @NotNull
    private Long skuId;
    /**
     * 秒杀价格
     */
    @NotNull
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    @NotNull
    private BigDecimal seckillCount;
    /**
     * 每人限购数量
     */
    @NotNull
    private BigDecimal seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;
}
