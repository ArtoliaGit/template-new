package com.bsoft.template.common.auth;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
@WebFilter(urlPatterns = "/*", filterName = "RequestCommonFilter")
public class RequestCommonFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getContentType() == null ||
                !servletRequest.getContentType().toLowerCase().startsWith("multipart/form-data")) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
