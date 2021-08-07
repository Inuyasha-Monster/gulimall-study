package com.atguigu.gulimall.seckill.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author djl
 * @create 2021/8/7 15:21
 */
@Component
@RabbitListener(queues = "seckill.session.timeout.queue")
@Slf4j
public class SeckillSessionTimeout {

    @RabbitHandler
    public void handle(String content, Message message, Channel channel) throws IOException {
        try {
            log.debug("收到秒杀过期消息:{}", content);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception exception) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
