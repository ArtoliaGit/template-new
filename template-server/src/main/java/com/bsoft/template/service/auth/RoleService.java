package com.bsoft.template.service.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.mapper.auth.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 角色业务类
 * @author Artolia Pendragon
 */
@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 获取角色列表
     */
    public Result getRoleList() {
        return null;
    }

    /**
     * 根据用户获取角色
     */
    public Result getRolesByUser(int userId) {
        return null;
    }

    /**
     * 根据角色id获取角色
     */
    public Result getRoleById(int roleId) {
        return null;
    }

    /**
     * 保存角色
     */
    public Result save(Role role) {
        return null;
    }

    /**
     * 删除角色
     */
    public Result remove() {
        return null;
    }
}
