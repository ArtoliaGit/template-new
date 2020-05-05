package com.bsoft.template.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户基本信息
 */
@Data
@TableName(value = "person")
public class Person {

    /**
     * 基本信息主键
     */
    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String personName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    @JsonFormat
    private Date birthday;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 住址
     */
    private String address;

    /**
     * 维护时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
