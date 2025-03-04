package com.usercenter.usercenterwebjava.Model.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户注册请求体
 *
 * @author 明裕学长
 */

@Data
public class UserUpdateRequest implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;
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
     * 是否删除 0 1
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员 2-毕业校友 3-在校学生
     */
    private Integer userRole;
}

