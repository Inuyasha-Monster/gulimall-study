package com.atguigu.common.constant;

/**
 * @author djl
 * @create 2021/6/13 15:00
 */
public class ProductConstant {
    public enum AttrEnum {
        ATTR_TYPE_BASE(0, "销售属性"), ATTR_TYPE_SALE(1, "基本属性");
        private int code;
        private String message;

        AttrEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
