package com.bsoft.template.common;

import lombok.Getter;

/**
 * 响应状态码
 */
@Getter
public enum ResultCodeEnum {

    OK(true, 200, "操作成功"),
    FAIL(false, -2, "操作失败"),
    NO_CONTENT(false, 204, "没有数据"),
    BAD_REQUEST(false, 400, "请求参数有误"),
    UNAUTHORIZED(false, 401, "请求需要验证"),
    FORBIDDEN(false, 403, "禁止访问"),
    NOT_FOUND(false, 404, "无法找到资源"),
    INTERNAL_SERVER_ERROR(false, 500, "服务错误"),
    SAVE_OR_UPDATE_FAIL(false, 20001, "保存失败"),
    SIGN_FAIL(false, -1, "验证签名失败");

    private final Boolean success;

    private final Integer code;

    private final String message;

    ResultCodeEnum(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
