package com.atguigu.common.constant;

/**
 * @author djl
 * @create 2021/8/7 10:59
 * 秒杀常量
 */
public class SeckillConstant {
    /**
     * 秒杀商品分布式信号量
     */
    public static final String SKU_STOCK_SEMAPHORE = "seckill:stock:";

    /**
     * 用户秒杀商品唯一性判断的RedisKey前缀
     */
    public static final String USERID_SECKILL_POTMOTION_SKUID_PRE = "user:seckill:";

}
