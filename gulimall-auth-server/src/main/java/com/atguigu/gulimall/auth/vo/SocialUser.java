package com.atguigu.gulimall.auth.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author djl
 * @create 2021/6/27 18:39
 */
@Data
@ToString
public class SocialUser {
    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;
}
