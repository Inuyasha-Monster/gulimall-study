package com.atguigu.gulimall.seckill.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author djl
 * @create 2021/8/7 14:46
 */
@Configuration
public class RabbitMQConfig {


    //region 秒杀活动创建MQ配置
    @Bean
    public Exchange seckillExchange() {
        return new TopicExchange("seckill-event-exchange", true, false, null);
    }

    @Bean
    public Queue seckillSessionCreateQueue() {
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "seckill-event-exchange");
        arguments.put("x-dead-letter-routing-key", "seckill.session.timeout");
//        arguments.put("x-message-ttl", 600000); // 消息过期时间 1分钟 取消Queue级别的延时配置,颗粒度到每一个消息体上
        return new Queue("seckill.session.create.queue", true, false, false, arguments);
    }

    @Bean
    public Binding seckillSessionCreateBinding() {
        return new Binding("seckill.session.create.queue",
                Binding.DestinationType.QUEUE,
                "seckill-event-exchange",
                "seckill.session.create",
                null);
    }
    //endregion

    //region 秒杀活动到期MQ配置
    @Bean
    public Queue seckillSessionTimeoutQueue() {
        return new Queue("seckill.session.timeout.queue", true, false, false, null);
    }

    @Bean
    public Binding seckillSessionTimeoutBinding() {
        return new Binding("seckill.session.timeout.queue",
                Binding.DestinationType.QUEUE,
                "seckill-event-exchange",
                "seckill.session.timeout",
                null);
    }
    //endregion
}
