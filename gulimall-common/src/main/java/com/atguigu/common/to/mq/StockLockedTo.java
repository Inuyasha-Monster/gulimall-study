package com.atguigu.common.to.mq;

import lombok.Data;
import lombok.ToString;

/**
 * @Description: 发送到mq消息队列的to
 * @Created: with IntelliJ IDEA.
 * @author: djl
 * @createTime: 2020-07-06 21:03
 **/

@ToString
@Data
public class StockLockedTo {

    /** 库存工作单的id **/
    private Long id;

    /** 工作单详情的所有信息 **/
    private StockDetailTo detailTo;
}
