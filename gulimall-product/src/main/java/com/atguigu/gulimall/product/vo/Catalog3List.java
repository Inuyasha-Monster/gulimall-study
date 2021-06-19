package com.atguigu.gulimall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author djl
 * @create 2021/6/19 19:32
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
/**
 * 三级分类VO
 */
public class Catalog3List {

    private String catalog2Id;
    private String id;
    private String name;
}