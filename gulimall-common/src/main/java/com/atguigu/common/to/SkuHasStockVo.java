package com.atguigu.common.to;

import lombok.Data;

/**
 * @author djl
 * @create 2021/6/19 15:05
 */
@Data
public class SkuHasStockVo {
    private Long skuId;
    private Boolean hasStock;
}
