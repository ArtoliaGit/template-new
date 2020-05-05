package com.bsoft.template.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.entity.auth.User;

import java.util.List;

/**
 * 角色mapper
 * @author artolia
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户获取角色
     */
    List<Role> getRolesByUser(int userId);

    /**
     * 保存用户角色
     * @param user 用户
     * @return int
     */
    int saveRoles(User user);

    /**
     * 删除用户角色
     */
    int deleteRoles(User user);
}
