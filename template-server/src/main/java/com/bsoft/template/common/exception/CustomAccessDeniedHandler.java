package com.bsoft.template.common.exception;

import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("访问被拒绝，没有权限");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");
        Result result = new Result();
        result.code(ResultCodeEnum.FORBIDDEN.getCode()).message("访问被拒绝，没有权限");
        response.getWriter().write(GsonUtil.objectToJson(result));
    }
}
