package com.bsoft.template.common.auth;

import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.entity.auth.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 获取当前登录用户信息
 * @author Artolia Pendragon
 */
@Component
public class UserInfo {

    /**
     * 获取当前登录用户
     * @return User
     */
    public User getUser() {
        final Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /**
     * 获取当前用户名
     * @return String
     */
    public String getUsername() {
        return getUser().getUsername();
    }

    /**
     * 获取角色
     * @return List<Role>
     */
    public List<Role> getRoles() {
        return getUser().getRoles();
    }
}
