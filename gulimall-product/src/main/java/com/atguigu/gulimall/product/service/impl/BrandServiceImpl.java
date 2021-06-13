package com.atguigu.gulimall.product.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String queryKey = (String) params.get("key");

        QueryWrapper<BrandEntity> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(queryKey)) {
            queryWrapper.eq("brand_id", queryKey)
                    .or()
                    .like("name", queryKey);
        }

        IPage<BrandEntity> page = this.page(
                new Query<BrandEntity>().getPage(params), queryWrapper
        );

        return new PageUtils(page);
    }

}