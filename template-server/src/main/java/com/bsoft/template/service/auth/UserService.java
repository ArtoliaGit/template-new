package com.bsoft.template.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.common.auth.UserInfo;
import com.bsoft.template.entity.auth.User;
import com.bsoft.template.mapper.auth.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 用户业务类
 */
@Service("userService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfo userInfo;

    @Value("${custom.default-password}")
    private String defaultPassword;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户实体
     * @throws UsernameNotFoundException 用户没有获取到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    /**
     * 获取用户列表
     */
    public Result getUserList(Map<String, Object> params) {
        Result result = new Result();

        Page<User> page = new Page<>(
                Long.parseLong((String) params.getOrDefault("page", 1)),
                Long.parseLong((String) params.getOrDefault("pageSize", 10))
        );

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.allEq(params);
        IPage<User> iPage = userMapper.selectPage(page, wrapper);

        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(iPage.getRecords())
                .total(iPage.getTotal());
        return result;
    }

    /**
     * 根据用户id获取用户
     */
    public Result getUserById(int userId) {
        Result result = new Result();
        User user = userMapper.getUserById(userId);
        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(user);
        return result;
    }

    /**
     * 根据用户名获取用户
     */
    public Result getUserByUsername(String username) {
        Result result = new Result();
        User user = userMapper.findByUsername(username);
        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(user);
        return result;
    }

    /**
     * 获取当前登录用户信息
     */
    public Result getUserInfo() {
        return getUserByUsername(userInfo.getUsername());
    }

    /**
     * 保存用户
     */
    public Result save(User user) {
        Result result = new Result();
        int num;
        if (user.getUserId() != null) {
            num = userMapper.updateById(user);
        } else {
            if (userMapper.findByUsername(user.getUsername()) != null) {
                result.setCode(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode());
                result.setMessage("用户名已存在");
                return result;
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            final String password = defaultPassword;
            user.setPassword(encoder.encode(password));
            user.setCreateTime(new Date());
            num = userMapper.insert(user);
        }
        if (num > 0) {
            result.code(ResultCodeEnum.OK.getCode()).message("保存成功");
        } else {
            result.code(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode())
                    .message(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getMessage());
        }
        return result;
    }

    /**
     * 删除用户
     */
    public Result remove(int userId) {
        Result result = new Result();
        userMapper.deleteById(userId);
        result.code(ResultCodeEnum.OK.getCode()).message("删除成功");
        return result;
    }

    /**
     * 禁用用户
     */
    public Result disableUser(int userId) {
        Result result = new Result();
        User user = new User();
        user.setUserId(userId);
        user.setStatus(2);
        user.setDisableTime(new Date());
        return result.ok();
    }

    /**
     * 重置密码
     */
    public Result resetPassword(int userId) {
        Result result = new Result();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String password = defaultPassword;
        User user = new User();
        user.setUserId(userId);
        user.setPassword(encoder.encode(password));
        userMapper.updateById(user);
        return result.ok();
    }
}
