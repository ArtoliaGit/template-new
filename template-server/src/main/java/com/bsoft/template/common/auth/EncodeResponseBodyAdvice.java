package com.bsoft.template.common.auth;

import com.bsoft.template.common.Constant;
import com.bsoft.template.common.Result;
import com.bsoft.template.util.encrypt.AESEncryptUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 返回数据加密
 */
@Slf4j
@ControllerAdvice(basePackages = "com.bsoft.template.web")
public class EncodeResponseBodyAdvice implements ResponseBodyAdvice<Result> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Result beforeBodyWrite(Result body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            log.info("方法method: 【{}】的出参: {}", methodParameter.getMethod().getName(), body);
            if (body == null || body.getData() == null) {
                return body;
            }
            if (Constant.SECURITY_MODE.equals(SecurityModeEnum.ALL_ENCRYPT)) {
                log.info("对方法method: 【{}】返回参数进行加密", methodParameter.getMethod().getName());
                String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body.getData());
                body.setData(AESEncryptUtil.encrypt(result));
                return body;
            } else if (Constant.SECURITY_MODE.equals(SecurityModeEnum.ANNOTATION_ENCRYPT)) {
                boolean encode = false;
                if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                    SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);
                    encode = securityParameter.outEncode();
                } else if (methodParameter.getClass().isAnnotationPresent(SecurityParameter.class)) {
                    SecurityParameter securityParameter = methodParameter.getClass().getAnnotation(SecurityParameter.class);
                    encode = securityParameter.outEncode();
                }
                if (encode) {
                    log.info("对方法method: 【{}】返回参数进行加密", methodParameter.getMethod().getName());
                    String result = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(body.getData());
                    body.setData(AESEncryptUtil.encrypt(result));
                    return body;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method: 【{}】返回数据进行加密出现异常: {}", methodParameter.getMethod().getName(), e.getMessage());
        }

        return body;
    }
}
