package com.bsoft.template.common.auth;

import com.bsoft.template.common.Constant;
import com.bsoft.template.util.GsonUtil;
import com.bsoft.template.util.encrypt.AESEncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 对请求参数进行解密
 */
@Slf4j
@ControllerAdvice(basePackages = "com.bsoft.template.web")
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        try {
            if (Constant.SECURITY_MODE.equals(SecurityModeEnum.ALL_ENCRYPT)) {
                log.info("对方法method: 【{}】请求参数进行解密", methodParameter.getMethod().getName());
                return new DecryptHttpInputMessage(httpInputMessage);
            } else if (Constant.SECURITY_MODE.equals(SecurityModeEnum.ANNOTATION_ENCRYPT)) {
                boolean encode = false;
                if (methodParameter.getMethod().isAnnotationPresent(SecurityParameter.class)) {
                    SecurityParameter securityParameter = methodParameter.getMethodAnnotation(SecurityParameter.class);
                    encode = securityParameter.inDecode();
                } else if (methodParameter.getClass().isAnnotationPresent(SecurityParameter.class)) {
                    SecurityParameter securityParameter = methodParameter.getClass().getAnnotation(SecurityParameter.class);
                    encode = securityParameter.inDecode();
                }
                if (encode) {
                    log.info("对方法method: 【{}】请求参数进行解密", methodParameter.getMethod().getName());
                    return new DecryptHttpInputMessage(httpInputMessage);
                }
            }
            return httpInputMessage;

        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method: 【{}】请求参数进行解密出现异常：{}", methodParameter.getMethod().getName(), e.getMessage());
            return httpInputMessage;
        }
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class DecryptHttpInputMessage implements HttpInputMessage {

        private HttpHeaders headers;

        private InputStream body;

        public DecryptHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            this.headers = inputMessage.getHeaders();
            this.body = getBody(inputMessage.getBody());
        }

        @Override
        public InputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        public InputStream getBody(InputStream body) throws Exception {
            String requestData = IOUtils.toString(body, StandardCharsets.UTF_8);
            log.info("入参 =====> {}", requestData);
            Map<String, String> requestMap = GsonUtil.jsonToMap(requestData);

            String secretData = requestMap.getOrDefault("secretData", "");
            String decodeContent = AESEncryptUtil.decrypt(secretData);
            log.info("解密入参 =====> {}", decodeContent);
            return IOUtils.toInputStream(decodeContent, StandardCharsets.UTF_8);
        }
    }
}
