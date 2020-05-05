package com.bsoft.template.common.auth;

import com.bsoft.template.common.Constant;
import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 验签拦截器
 */
@Slf4j
public class SignInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String t = request.getHeader("t");
        if (Constant.CLOSE_SIGN || Constant.TEST_TOKEN.equals(t)) {
            return true;
        }

        if (verifySign(request)) {
            return true;
        } else {
            log.info("验证签名失败");
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json;charset=UTF-8");
            Result result = new Result();
            result.code(ResultCodeEnum.SIGN_FAIL.getCode()).message("访问被拒绝，验证签名失败");
            response.getWriter().write(GsonUtil.objectToJson(result));
        }

        return false;
    }

    /**
     * 验证签名
     */
    private boolean verifySign(HttpServletRequest request) {
        BodyReaderHttpServletRequestWrapper servletRequestWrapper = new BodyReaderHttpServletRequestWrapper(request);
        String requestParams;
        if (HttpMethod.GET.name().equals(request.getMethod())) {
            requestParams = servletRequestWrapper.getQueryString();
            if (StringUtils.isBlank(requestParams)) {
                requestParams = "";
            }
        } else {
            String urlParams = servletRequestWrapper.getQueryString();
            String params = servletRequestWrapper.getBodyString(request);
            if (StringUtils.isBlank(urlParams)) {
                urlParams = "";
            }
            if (StringUtils.isBlank(params)) {
                params = "";
            }
            requestParams = urlParams + params;
        }
        String sign = request.getHeader("sign");
        log.info("sign值 =====> {}", sign);

        String timeStamp = request.getHeader("timeStamp");
        String token = request.getHeader("X-Token");
        requestParams = token + requestParams + timeStamp;

        try {
            String computeSign = DigestUtils.md5Hex(requestParams.getBytes("UTF-8"));
            log.info("验证sign值 =====> {}", computeSign);
            if (Objects.equals(sign, computeSign)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
