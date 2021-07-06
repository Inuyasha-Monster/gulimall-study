package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author djl
 * @create 2021/7/6 23:18
 */
@Data
public class FareVo {
    private MemberAddressVo address;

    private BigDecimal fare;
}
