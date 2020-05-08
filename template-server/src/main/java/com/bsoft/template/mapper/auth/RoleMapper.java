package com.bsoft.template.mapper.auth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.entity.auth.User;

import java.util.List;
import java.util.Map;

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
     * 获取用户角色列表
     * @return IPage
     */
    IPage<Role> getRoleList(Page<Role> page, Map<String, String> params);

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

    /**
     * 保存菜单
     * @param role 角色实体
     * @return int
     */
    int saveResource(Role role);

    /**
     * 删除菜单
     */
    int removeResource(int roleId);
}
