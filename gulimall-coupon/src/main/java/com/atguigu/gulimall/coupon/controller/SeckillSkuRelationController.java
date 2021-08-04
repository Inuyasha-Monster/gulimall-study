package com.atguigu.gulimall.coupon.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.coupon.feign.ProductFeignService;
import com.atguigu.gulimall.coupon.feign.WareFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gulimall.coupon.entity.SeckillSkuRelationEntity;
import com.atguigu.gulimall.coupon.service.SeckillSkuRelationService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;

import javax.annotation.Resource;


/**
 * 秒杀活动商品关联
 *
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 11:09:08
 */
@RestController
@RequestMapping("coupon/seckillskurelation")
public class SeckillSkuRelationController {
    @Autowired
    private SeckillSkuRelationService seckillSkuRelationService;

    @Resource
    private ProductFeignService productFeignService;

    @Resource
    private WareFeignService wareFeignService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("coupon:seckillskurelation:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = seckillSkuRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("coupon:seckillskurelation:info")
    public R info(@PathVariable("id") Long id) {
        SeckillSkuRelationEntity seckillSkuRelation = seckillSkuRelationService.getById(id);

        return R.ok().put("seckillSkuRelation", seckillSkuRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("coupon:seckillskurelation:save")
    public R save(@RequestBody SeckillSkuRelationEntity seckillSkuRelation) {

        // 检查商品是否存在
        final R skuInfo = productFeignService.getInfo(seckillSkuRelation.getSkuId());
        if (skuInfo == null) {
            return R.error("not found skuId:" + seckillSkuRelation.getSkuId());
        }

        // 检查对应sku库存是否足够秒杀数量
        final R remaindStock = wareFeignService.getRemaindStock(seckillSkuRelation.getSkuId());
        final Long remaindStockData = remaindStock.getData(new TypeReference<Long>() {
        });

        if (BigDecimal.valueOf(remaindStockData).compareTo(seckillSkuRelation.getSeckillCount()) < 0) {
            return R.error("not enough stock of skuId:" + seckillSkuRelation.getSkuId());
        }

        seckillSkuRelationService.saveSeckill(seckillSkuRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("coupon:seckillskurelation:update")
    public R update(@RequestBody SeckillSkuRelationEntity seckillSkuRelation) {
        seckillSkuRelationService.updateById(seckillSkuRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("coupon:seckillskurelation:delete")
    public R delete(@RequestBody Long[] ids) {
        seckillSkuRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
