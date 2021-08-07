package com.atguigu.gulimall.coupon.exception;

import com.atguigu.common.exception.BizCodeEnum;
import com.atguigu.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author djl
 * @create 2021/8/7 9:22
 * 优惠服务统一的异常拦截处理器
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.coupon.controller")
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException exception) {
        log.error("数据校验出现问题:{},异常类型:{}", exception.getMessage(), exception.getClass());
        BindingResult bindingResult = exception.getBindingResult();
        Map<String, String> errorMap = new HashMap<>(16);
        bindingResult.getFieldErrors().forEach(x -> {
            String field = x.getField();
            String message = x.getDefaultMessage();
            errorMap.put(field, message);
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("未知异常", throwable);
        return R.error(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), StringUtils.isNotEmpty(throwable.getMessage()) ? BizCodeEnum.UNKNOW_EXCEPTION.getMsg() + ":" + throwable.getMessage() : BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
