package com.bsoft.template.common;

import com.bsoft.template.common.auth.SecurityModeEnum;

/**
 * 公用常量
 */
public class Constant {

    /**
     * AES加密key，长度为16位
     */
    public final static String AES_KEY = "mithrandir";

    /**
     * AES加密偏移量, 长度为16位
     */
    public final static String AES_IV = "mithrandir";

    /**
     * AES加密逻辑，参数代表 算法名称/加密模式/数据填充方式
     */
    public final static String AES_ALGORITHMSTR = "AES/CBC/PKCS5Padding";

    /**
     * 传输参数加密
     */
    public final static SecurityModeEnum SECURITY_MODE = SecurityModeEnum.ALL_ENCRYPT;

    /**
     * 是否关闭验签
     */
    public final static boolean CLOSE_SIGN = false;

    /**
     * 绕过验证
     */
    public final static String TEST_TOKEN = "";
}
