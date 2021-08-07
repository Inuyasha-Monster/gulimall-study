package com.atguigu.gulimall.order.listener;

import com.atguigu.common.to.mq.SeckillOrderTo;
import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description: 定时关闭订单
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-07 09:54
 **/

@RabbitListener(queues = "order.release.order.queue")
@Service
@Slf4j
public class OrderCloseListener {

    @Autowired
    private OrderService orderService;

    @RabbitHandler
    public void listener(OrderEntity orderEntity, Channel channel, Message message) throws IOException {
        System.out.println("收到过期的订单信息，准备关闭订单" + orderEntity.getOrderSn());
        try {
            orderService.closeOrder(orderEntity);
            // todo:手动调用对应平台的收单方法，让用户不能支付关闭的订单 例如:支付宝 alipay.trade.close(统一收单交易关闭接口) 注意需要考虑极端情况: 用户可能在关闭订单的前一刻支付了订单的情况, 所以需要先行查看支付平台订单状态(alipay.trade.query(统一收单线下交易查询)), 如果是已经支付的情况, 需要退款处理
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }

    @RabbitHandler
    public void handleSeckillOrderTimeout(SeckillOrderTo seckillOrderTo, Message message, Channel channel) throws IOException {
        log.info("----收到秒杀订单的超时信号----");
        try {
            orderService.closeSeckillOrder(seckillOrderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception exception) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

}
