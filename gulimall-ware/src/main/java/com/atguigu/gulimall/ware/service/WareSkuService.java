package com.atguigu.gulimall.ware.service;

import com.atguigu.common.to.OrderTo;
import com.atguigu.common.to.SkuHasStockVo;
import com.atguigu.common.to.mq.StockLockedTo;
import com.atguigu.gulimall.ware.vo.LockSeckillStockVo;
import com.atguigu.gulimall.ware.vo.UnlockSeckillStockVo;
import com.atguigu.gulimall.ware.vo.WareSkuLockVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author dujianglong
 * @email dujianglong@gmail.com
 * @date 2021-05-29 11:41:20
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByCondition(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    boolean orderLockStock(WareSkuLockVo vo);

    void unlockStock(StockLockedTo to);

    void unlockStock(OrderTo orderTo);

    Long getRemaindStock(Long skuId);

    void lockSeckillStock(LockSeckillStockVo lockSeckillStockVo);

    void checkSeckillAndUnlockStock(UnlockSeckillStockVo vo);
}

