package com.bsoft.template.common.exception;

import com.bsoft.template.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 通用异常处理方法
     */
    @ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        e.printStackTrace();
        log.error("catch exception ==> " + e.getMessage());
        return new Result().error();
    }

    /**
     * 空指针异常处理方法
     */
    @ExceptionHandler(NullPointerException.class)
    public Result error(NullPointerException e) {
        e.printStackTrace();
        log.error("catch exception ==> " + e.getMessage());
        return new Result().error().message("空指针错误");
    }

}
