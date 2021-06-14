package com.atguigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author djl
 * @create 2021/6/14 18:03
 */
@Data
public class MergeVo {
    private Long purchaseId;
    private List<Long> items;
}
