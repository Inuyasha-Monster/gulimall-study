package com.atguigu.gulimall.coupon.service.impl;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.coupon.feign.WareFeignService;
import com.atguigu.gulimall.coupon.vo.LockSeckillStockVo;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.coupon.dao.SeckillSkuRelationDao;
import com.atguigu.gulimall.coupon.entity.SeckillSkuRelationEntity;
import com.atguigu.gulimall.coupon.service.SeckillSkuRelationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("seckillSkuRelationService")
public class SeckillSkuRelationServiceImpl extends ServiceImpl<SeckillSkuRelationDao, SeckillSkuRelationEntity> implements SeckillSkuRelationService {

    @Resource
    private WareFeignService wareFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<SeckillSkuRelationEntity> queryWrapper = new QueryWrapper<SeckillSkuRelationEntity>();

        //1、获取key
        String key = (String) params.get("key");

        String promotionSessionId = (String) params.get("promotionSessionId");

        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key);
        }

        if (!StringUtils.isEmpty(promotionSessionId)) {
            queryWrapper.eq("promotion_session_id", promotionSessionId);
        }
        IPage<SeckillSkuRelationEntity> page = this.page(
                new Query<SeckillSkuRelationEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSeckill(SeckillSkuRelationEntity seckillSkuRelation) {

        // 先尝试锁定库存
        final LockSeckillStockVo stockVo = new LockSeckillStockVo();
        stockVo.setSkuId(seckillSkuRelation.getSkuId().intValue());
        stockVo.setLockCount(seckillSkuRelation.getSeckillCount().intValue());
        final R result = wareFeignService.lockSeckillStock(stockVo);
        if (result.getCode() != 0) {
            throw new RuntimeException("锁定秒杀库存失败");
        }

        // 保存秒杀商品信息
        this.save(seckillSkuRelation);

        // 例如:1/0 todo:如果远程调用成功但是本地事务因为意外异常(例如:远程调用超时等)导致本地事务回滚了,需要远程调用也需要回滚 该如何去做呢?
    }

}