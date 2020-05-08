package com.bsoft.template.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色实体类
 */
@Data
@TableName(value = "role")
public class Role {

    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescription;

    /**
     * 用户状态 1:正常 2:禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat
    private Date createTime;

    /**
     * 菜单列表
     */
    @TableField(exist = false)
    private List<Menu> resource;
}
