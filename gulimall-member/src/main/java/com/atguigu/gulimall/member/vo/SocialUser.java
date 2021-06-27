package com.atguigu.gulimall.member.vo;

import lombok.Data;

/**
 * @author djl
 * @create 2021/6/27 18:41
 */
@Data
public class SocialUser {
    private String access_token;

    private String remind_in;

    private long expires_in;

    private String uid;

    private String isRealName;
}
