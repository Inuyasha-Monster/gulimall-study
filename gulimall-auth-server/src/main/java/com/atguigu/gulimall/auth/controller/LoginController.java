package com.atguigu.gulimall.auth.controller;

import com.atguigu.common.constant.AuthServerConstant;
import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.auth.feign.ThirdPartFeignService;
import com.atguigu.gulimall.auth.vo.UserRegisterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author djl
 * @create 2021/6/24 21:15
 */
@Controller
public class LoginController {
//    @GetMapping("/login.html")
//    public String loginPage() {
//        return "login";
//    }
//
//    @GetMapping("/reg.html")
//    public String regPage() {
//        return "reg";
//    }

    @Autowired
    private ThirdPartFeignService thirdPartFeignService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sms/sendCode")
    @ResponseBody
    public R sendCode(@RequestParam("phone") String phone) {

        String redisCode = stringRedisTemplate.opsForValue().get(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (StringUtils.isNotBlank(redisCode)) {
            long l = Long.parseLong(redisCode.split("_")[1]);
            if (System.currentTimeMillis() - l < 60 * 1000) {
                // 60秒内不能再发送
                return R.error(BizCodeEnum.SMS_CODE_EXCEPTION.getCode(), BizCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }

        String code = UUID.randomUUID().toString().substring(0, 5) + "_" + System.currentTimeMillis();

        stringRedisTemplate.opsForValue().set(AuthServerConstant.SMS_CODE_CACHE_PREFIX + phone, code, 10, TimeUnit.MINUTES);

        thirdPartFeignService.sendCode(phone, code);

        return R.ok();
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo vo,
                           BindingResult result,
                           RedirectAttributes attributes) {
        if (result.hasErrors()) {
//            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

            Map<String, String> errors = new HashMap<>();
            for (FieldError fieldError : result.getFieldErrors()) {
                errors.putIfAbsent(fieldError.getField(),fieldError.getDefaultMessage());
            }

            attributes.addFlashAttribute("errors", errors);
            // 校验错误直接返回页面展示
            return "forward:/reg.html";
        }
        return "redirect:/login.html";
    }
}
