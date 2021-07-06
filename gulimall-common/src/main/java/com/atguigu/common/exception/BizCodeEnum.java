package com.atguigu.common.exception;

import lombok.Data;

/**
 * @author djl
 * @create 2021/6/12 11:57
 */
public enum BizCodeEnum {

    /**
     * 用于系统未知异常使用
     */
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败"),
    TO_MANY_REQUEST(10002,"请求流量过大，请稍后再试"),
    SMS_CODE_EXCEPTION(10002,"验证码获取频率太高，请稍后再试"),
    USER_EXIST_EXCEPTION(15001,"存在相同的用户"),
    PHONE_EXIST_EXCEPTION(15002,"存在相同的手机号"),
    LOGINACCT_PASSWORD_EXCEPTION(15003,"账号或密码错误"),
    NO_STOCK_EXCEPTION(21000,"商品库存不足"),
    PRODUCT_EXCEPTION(11000,"商品上架异常");

    private final int code;
    private final String msg;

    BizCodeEnum(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
