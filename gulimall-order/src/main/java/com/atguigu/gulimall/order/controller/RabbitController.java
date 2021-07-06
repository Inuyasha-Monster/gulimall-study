package com.atguigu.gulimall.order.controller;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author djl
 * @create 2021/7/6 21:22
 */

@Slf4j
@RestController
public class RabbitController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMq")
    public String sendMq() {
        for (int i = 0; i < 10; i++) {

            if (i % 2 == 0) {

                OrderReturnReasonEntity reasonEntity = new OrderReturnReasonEntity();
                reasonEntity.setId(1L);
                reasonEntity.setCreateTime(new Date());
                reasonEntity.setName("reason" + i);
                reasonEntity.setStatus(1);
                reasonEntity.setSort(2);

                //2、发送的对象类型的消息，可以是一个json
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello.java",
                        reasonEntity, new CorrelationData(UUID.randomUUID().toString()));
                log.info("消息发送完成:{}", reasonEntity);
            } else {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setId((long) i);
                orderEntity.setOrderSn(UUID.randomUUID().toString());
                rabbitTemplate.convertAndSend("hello-java-exchange", "hello22.java",
                        orderEntity, new CorrelationData(UUID.randomUUID().toString()));
                log.info("消息发送完成:{}", orderEntity);
            }

        }
        return "ok";
    }
}
