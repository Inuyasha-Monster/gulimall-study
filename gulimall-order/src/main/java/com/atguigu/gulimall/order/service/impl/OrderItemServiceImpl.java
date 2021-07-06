package com.atguigu.gulimall.order.service.impl;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.atguigu.gulimall.order.entity.OrderReturnReasonEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.order.dao.OrderItemDao;
import com.atguigu.gulimall.order.entity.OrderItemEntity;
import com.atguigu.gulimall.order.service.OrderItemService;

@RabbitListener(queues = {"hello-java-queue"})
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * queues：声明需要监听的队列
     * channel：当前传输数据的通道
     * 场景：
     * 1、订单服务启动多个，同一个消息，只能有一个客户端收到
     * 2、只有一个消息完成处理完成之后，我们才可以接收下一个消息
     */
//    @RabbitListener(queues = {"hello-java-queue"})
    @RabbitHandler
    public void revieveMessage(Message message,
                               OrderReturnReasonEntity content,
                               Channel channel) throws IOException {
        //拿到主体内容
        byte[] body = message.getBody();
        //拿到的消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("接受到的消息 OrderReturnReasonEntity...内容" + message + "===内容：" + content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitHandler
    public void revieveMessage2(Message message,
                                OrderEntity content,
                                Channel channel) throws IOException {
        //拿到主体内容
        byte[] body = message.getBody();
        //拿到的消息头属性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("接受到的消息 OrderEntity...内容" + message + "===内容：" + content);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        // 拒收：可以指定是否重新回到队列
//        channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        // 可以设置是否批量拒收以及是否重新入队
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
    }

}