package com.bsoft.template.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.template.entity.auth.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

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

    /**
     * 获取用户列表
     * @param page 分页对象
     * @return Ipage
     */
    IPage<User> getUserList(Page<User> page, @Param("params") Map<String, String> map);
}
