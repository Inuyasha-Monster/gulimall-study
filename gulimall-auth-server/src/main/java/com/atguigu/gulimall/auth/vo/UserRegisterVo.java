package com.atguigu.gulimall.auth.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author djl
 * @create 2021/6/24 23:06
 */
@Data
public class UserRegisterVo {
    @NotEmpty(message = "用户名不能为空")
    @Size(min = 6, max = 19, message = "用户名长度在6-18字符")
    private String userName;

    @NotEmpty(message = "密码必须填写")
    @Size(min = 6, max = 18, message = "密码必须是6—18位字符")
    private String password;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$", message = "手机号格式不正确")
    private String phone;

    @NotEmpty(message = "验证码不能为空")
    private String code;
}
