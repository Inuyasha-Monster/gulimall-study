package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author djl
 * @create 2021/6/14 11:55
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
