package com.atguigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author djl
 * @create 2021/6/13 15:04
 */
@Data
public class AttrResponseVo extends AttrVo {

    /**
     * 所属分类名字，如："手机/数码/手机"
     */
    private String catelogName;
    /**
     * 所属分组名字
     */
    private String groupName;


    //分类完整路
    private Long[] catelogPath;

}
