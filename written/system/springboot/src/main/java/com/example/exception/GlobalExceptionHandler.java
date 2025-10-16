package com.example.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Shimmer
 * @date 2025/10/16
 */
@ControllerAdvice("com.example.controller")
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        log.error("异常信息如下", e);
        return Result.fail();
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result exception(CustomException e) {
        log.error("异常信息如下", e);
        return Result.fail(e.getCode(), e.getMessage());
    }

}