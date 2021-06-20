package com.atguigu.gulimall.product.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author djl
 * @create 2021/6/20 17:27
 */
@Data
@ToString
public class SpuItemAttrGroupVo {
    private String groupName;

    private List<Attr> attrs;
}
