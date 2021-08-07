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

        // 保存秒杀商品信息
        this.save(seckillSkuRelation);

        // 先尝试锁定库存
        final LockSeckillStockVo stockVo = new LockSeckillStockVo();
        stockVo.setSkuId(seckillSkuRelation.getSkuId().intValue());
        stockVo.setLockCount(seckillSkuRelation.getSeckillCount().intValue());
        stockVo.setRelationId(seckillSkuRelation.getId());
        final R result = wareFeignService.lockSeckillStock(stockVo);
        if (result.getCode() != 0) {
            throw new RuntimeException("锁定秒杀库存失败");
        }

//        int num = 1 / 0;

        // 例如:1/0 ; 如果远程调用成功但是本地事务因为意外异常(例如:远程调用超时等)导致本地事务回滚了,需要远程调用也需要回滚 该如何去做呢?
        // 回答:
        // 1.首先调用方法需要通过使用 本地事务 包裹发送消息的方式实现逻辑,保证业务逻辑正确的同时也成功发送消息
        // 2.其次被调服务方需要:面对网络问题导致调用方业务回滚,但是自身业务处理成功,所以可以通过 本地事务+投递MQ延时队列的方式来回滚本地事务
    }

}