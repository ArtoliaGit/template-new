package com.bsoft.template.web.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.common.auth.SecurityParameter;
import com.bsoft.template.entity.auth.User;
import com.bsoft.template.service.auth.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录验证
 * @author Artolia Pendragon
 */
@RestController
@RequestMapping("authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 登录
     * @param user 用户实体类
     * @return String
     */
    @SecurityParameter
    @PostMapping("/login")
    public Result login(@RequestBody User user) throws JsonProcessingException {
        return authService.login(user.getUsername(), user.getPassword());
    }
}
