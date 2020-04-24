package com.bsoft.template.service.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证
 * @author artolia
 */
@Service
@Slf4j
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return Result
     */
    public Result login(String username, String password) {
        Result result = new Result();
        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert requestAttributes != null;
            HttpServletRequest request = requestAttributes.getRequest();
            String sessionId = request.getSession().getId();

            log.info("登录日志 ==> 用户: {} 登录成功", username);
            result.setCode(ResultCodeEnum.OK.getCode());
            result.setMessage("登录成功");
            result.setData(sessionId);
        } catch (DisabledException e) {
            e.printStackTrace();
            log.info("登录日志 ==> 用户: {} 登录成功, 失败信息: 账户已被禁用", username);
            result.setCode(ResultCodeEnum.UNAUTHORIZED.getCode());
            result.setMessage("账户已被禁用");
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            log.info("登录日志 ==> 用户: {} 登录成功, 失败信息: 用户名或密码错误", username);
            result.setCode(ResultCodeEnum.UNAUTHORIZED.getCode());
            result.setMessage("用户名或密码错误");
        } catch (AuthenticationException | AssertionError e) {
            e.printStackTrace();
            log.info("登录日志 ==> 用户: {} 登录成功, 失败信息: {}", username, e.getMessage());
            result.setCode(ResultCodeEnum.UNAUTHORIZED.getCode());
            result.setMessage("验证失败");
        }
        return result;
    }
}
