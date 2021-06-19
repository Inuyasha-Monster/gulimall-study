package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 09:51:20
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    /**
     * 查询指定属性当中可以检索的属性id集合返回
     * @param attrIds
     * @return
     */
    List<Long> selectSearchAttrIds(@Param("attrIds") List<Long> attrIds);
}
