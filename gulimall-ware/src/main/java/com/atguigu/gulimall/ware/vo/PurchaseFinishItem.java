package com.atguigu.gulimall.ware.vo;

import lombok.Data;

/**
 * @author djl
 * @create 2021/6/14 18:31
 */
@Data
public class PurchaseFinishItem {
    private Long itemId;
    private Integer status;
    private String reason;
}
