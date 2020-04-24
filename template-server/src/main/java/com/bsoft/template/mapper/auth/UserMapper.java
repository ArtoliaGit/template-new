package com.bsoft.template.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsoft.template.entity.auth.User;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询用户
     * @param userId 用户id
     * @return user
     */
    User getUserById(int userId);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);
}
