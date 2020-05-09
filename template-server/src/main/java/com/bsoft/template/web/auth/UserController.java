package com.bsoft.template.web.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.entity.auth.User;
import com.bsoft.template.service.auth.UserService;
import com.bsoft.template.util.RequestParamPaser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户controller
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户列表
     */
    @GetMapping("/getList")
    public Result getList(HttpServletRequest request) {
        return userService.getUserList(RequestParamPaser.getParameters(request));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo() {
        return userService.getUserInfo();
    }

    /**
     * 删除用户
     */
    @GetMapping("/remove")
    public Result remove(int userId) {
        return userService.remove(userId);
    }

    /**
     * 禁用用户
     */
    @GetMapping("/disableUser")
    public Result disableUser(int userId) {
        return userService.disableUser(userId);
    }

    /**
     * 重置密码
     */
    @GetMapping("/resetPassword")
    public Result resetPassword(int userId) {
        return userService.resetPassword(userId);
    }

    /**
     * 根据用户id获取用户信息
     */
    @GetMapping("/getUserById")
    public Result getUserById(int userId) {
        return userService.getUserById(userId);
    }
}
