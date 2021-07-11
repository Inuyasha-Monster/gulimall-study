package com.atguigu.gulimall.ware.listener;

import com.atguigu.common.to.OrderTo;
import com.atguigu.common.to.mq.StockLockedTo;
import com.atguigu.gulimall.ware.service.WareSkuService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Description: 库存解锁的消息队列
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-07 00:20
 **/

@Slf4j
@RabbitListener(queues = "stock.release.stock.queue")
@Service
public class StockReleaseListener {

    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 1、库存自动解锁
     * 下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     * <p>
     * 2、订单失败
     * 库存锁定失败
     * <p>
     * 只要解锁库存的消息失败，一定要告诉服务解锁失败
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo to, Message message, Channel channel) throws IOException {
        log.info("******收到超时-解锁库存的信息******");
        try {

            //当前消息是否被第二次及以后（重新）派发过来了
            // Boolean redelivered = message.getMessageProperties().getRedelivered();

            //解锁库存
            wareSkuService.unlockStock(to);
            // 手动删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("解锁库存出现未知异常", e);
            // 解锁失败 将消息重新放回队列，让别人消费
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /**
     * 订单关闭之后给解锁库存MQ发送一个消息，消费者拿到这个消息进行解锁库存
     * 存在的必要性：防止因为程序卡顿，导致超时解锁库存的消息比订单自动关闭先行执行，所以再关闭订单的时候有必要再进行库存的解锁操作来确保一定能够解锁成功
     *
     * @param orderTo
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {

        log.info("******收到订单关闭-准备解锁库存的信息******");

        // 消费者需要进行可靠的消息消费，进行ack机制的运用
        try {
            wareSkuService.unlockStock(orderTo);
            // 手动删除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 解锁失败 将消息重新放回队列，让别人消费

            // 如果这行代码出现了问题，你觉的此条消息会回到MQ Broker里面吗?回答：会的
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }


}
