package com.bsoft.template.common;

import lombok.Getter;

/**
 * 响应状态码
 */
@Getter
public enum ResultCodeEnum {

    OK(true, 200, "操作成功"),
    NO_CONTENT(false, 201, "没有数据"),
    INTERNAL_SERVER_ERROR(false, 500, "服务错误");

    private final Boolean success;

    private final Integer code;

    private final String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
