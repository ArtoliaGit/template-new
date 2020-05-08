package com.bsoft.template.web.auth;

import com.bsoft.template.common.Result;
import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.service.auth.RoleService;
import com.bsoft.template.util.RequestParamPaser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 角色controller
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 获取角色列表
     * @param request 请求
     * @return String
     */
    @GetMapping("/getRoleList")
    public Result getRoleList(HttpServletRequest request) {
        return roleService.getRoleList(RequestParamPaser.getParameters(request));
    }

    /**
     * 保存或更新角色
     * @param role 角色实体
     * @return String
     */
    @PostMapping("/save")
    public Result save(@RequestBody Role role) {
        return roleService.save(role);
    }

    /**
     * 删除角色
     * @param roleId 角色id
     * @return String
     */
    @GetMapping("/remove")
    public Result remove(int roleId) {
        return roleService.remove(roleId);
    }

    /**
     * 保存角色菜单
     * @param role 角色实体
     * @return String
     */
    @PostMapping("/saveResource")
    public Result saveResource(@RequestBody Role role) {
        return roleService.saveResource(role);
    }
}
