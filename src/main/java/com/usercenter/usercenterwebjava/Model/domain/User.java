package com.usercenter.usercenterwebjava.Model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户表
 * user
 * @author 明裕学长
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 昵称
     */
    private String username;

    /**
     * 登录账号
     */
    private String userAccount;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 班级
     */
    private String className;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 性别 0-女 1-男
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 用户状态 0- 正常 1-封号
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除 0 1
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员 2-毕业校友 3-在校学生
     */
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}