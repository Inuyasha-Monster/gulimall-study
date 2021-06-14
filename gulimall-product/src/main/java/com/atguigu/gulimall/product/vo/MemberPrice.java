package com.atguigu.gulimall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author djl
 * @create 2021/6/14 10:18
 */
@Data
public class MemberPrice {
    private Long id;
    private String name;
    private BigDecimal price;
}
