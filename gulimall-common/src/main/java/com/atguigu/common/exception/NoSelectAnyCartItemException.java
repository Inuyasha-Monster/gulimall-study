package com.atguigu.common.exception;

/**
 * @author djl
 * @create 2021/8/2 20:31
 * 没有选择购物车任何商品
 */
public class NoSelectAnyCartItemException extends RuntimeException {
    public NoSelectAnyCartItemException(String msg) {
        super(msg);
    }
}
