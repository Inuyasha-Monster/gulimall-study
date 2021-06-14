package com.atguigu.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author djl
 * @create 2021/6/14 11:57
 */
@Data
public class MemberPrice {
    //会员等级ID
    private Long id;
    //会员等级名
    private String name;
    //会员价格
    private BigDecimal price;
}
