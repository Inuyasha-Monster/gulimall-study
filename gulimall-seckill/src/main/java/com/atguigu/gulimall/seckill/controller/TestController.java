package com.atguigu.gulimall.seckill.controller;

import com.atguigu.common.utils.R;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author djl
 * @create 2021/8/7 15:06
 */
@RestController
@RequestMapping("/test")
public class TestController {
    private final RabbitTemplate rabbitTemplate;

    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/send")
    public R testSeckillCreate() {

        ThreadLocalRandom localRandom = ThreadLocalRandom.current();

        for (int i = 0; i < 10; i++) {

            final int num = ((int) (localRandom.nextDouble() * 10000));

            rabbitTemplate.convertAndSend("seckill-event-exchange", "seckill.session.create", "大家好我是延迟数据:" + num + " ms", message -> {
                // 设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(num));
                return message;
            });
        }


        return R.ok();
    }
}
