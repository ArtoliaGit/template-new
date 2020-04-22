package com.bsoft.template.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 通用返回类
 */
@Data
@ToString
public class Result implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public Result error() {
        return this.message(ResultCodeEnum.INTERNAL_SERVER_ERROR.getMessage())
                .code(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode());
    }

    public Result ok() {
        return this.message(ResultCodeEnum.OK.getMessage())
                .code(ResultCodeEnum.OK.getCode());
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(Object data) {
        this.setData(data);
        return this;
    }
}
