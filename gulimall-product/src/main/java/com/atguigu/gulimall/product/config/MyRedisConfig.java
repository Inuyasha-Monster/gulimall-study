package com.atguigu.gulimall.product.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author djl
 * @create 2021/6/20 8:36
 */
@Configuration
public class MyRedisConfig {
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.199.221.46:6379").setPassword("djlnet");
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
