package com.bsoft.template.service.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bsoft.template.common.Result;
import com.bsoft.template.common.ResultCodeEnum;
import com.bsoft.template.entity.auth.Role;
import com.bsoft.template.mapper.auth.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Result getRoleList(Map<String, String> params) {
        Result result = new Result();

        Page<Role> page = new Page<>(
                Long.parseLong(params.getOrDefault("page", "1")),
                Long.parseLong(params.getOrDefault("pageSize", "10"))
        );

        IPage<Role> iPage = roleMapper.getRoleList(page, params);

        result.code(ResultCodeEnum.OK.getCode())
                .message("查询成功")
                .data(iPage.getRecords())
                .total(iPage.getTotal());
        return result;
    }

    /**
     * 根据用户获取角色
     */
    public Result getRolesByUser(int userId) {
        List<Role> list = roleMapper.getRolesByUser(userId);
        return new Result().ok().data(list);
    }

    /**
     * 根据角色id获取角色
     */
    public Result getRoleById(int roleId) {
        Role role = roleMapper.selectById(roleId);
        return new Result().ok().data(role);
    }

    /**
     * 保存角色
     */
    public Result save(Role role) {
        Result result = new Result();
        int num;
        if (role.getRoleId() != null) {
            num = roleMapper.updateById(role);
        } else {
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("role_code", role.getRoleCode());
            if (roleMapper.selectOne(wrapper) != null) {
                result.setCode(ResultCodeEnum.SAVE_OR_UPDATE_FAIL.getCode());
                result.setMessage("角色代码已存在");
                return result;
            }
            role.setCreateTime(new Date());
            num = roleMapper.insert(role);
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
     * 删除角色
     */
    public Result remove(int roleId) {
        Result result = new Result();
        int num = roleMapper.deleteById(roleId);
        if (num > 0) {
            result.ok().message("删除成功");
        } else {
            result.error().message("删除失败");
        }
        return result;
    }

    /**
     * 保存角色菜单配置
     * @param role 角色实体
     * @return Result
     */
    @Transactional
    public Result saveResource(Role role) {
        Result result = new Result();

        if (role.getRoleId() != null) {
            roleMapper.removeResource(role.getRoleId());
        }
        if (role.getResource() != null && role.getResource().size() > 0) {
            roleMapper.saveResource(role);
        }
        result.ok().message("保存成功");
        return result;
    }
}
