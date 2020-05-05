package com.bsoft.template.common.auth;

import lombok.Getter;

/**
 * 传输参数加密
 */
@Getter
public enum SecurityModeEnum {

    NO_ENCRYPT(1, "不进行加密处理"),
    ANNOTATION_ENCRYPT(2, "用注解进行加密处理"),
    ALL_ENCRYPT(3, "全部进行加密处理");

    private Integer code;

    private String message;

    SecurityModeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    boolean equals(SecurityModeEnum securityModeEnum) {
        return this.getCode().equals(securityModeEnum.getCode());
    }
}
