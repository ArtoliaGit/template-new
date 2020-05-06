package com.bsoft.template.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 菜单实体类
 */
@Data
@TableName("menu")
public class Menu {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 菜单name
     */
    private String name;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 第三方链接
     */
    private String link;

    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 父菜单id
     */
    private Integer parentId;

    /**
     * 父菜单名
     */
    private String parentName;

    /**
     * 是否激活 1 激活 0 未激活
     */
    private Integer activite;

    /**
     * 创建时间
     */
    @JsonFormat
    private Date createTime;
}
