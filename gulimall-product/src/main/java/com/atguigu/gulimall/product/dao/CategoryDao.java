package com.atguigu.gulimall.product.dao;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 09:51:20
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
