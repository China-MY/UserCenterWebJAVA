package com.usercenter.usercenterwebjava.Model.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 用户登录
 *
 * @author 明裕学长
 *
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 2L;

    private String userAccount;

    private String userPassword;

}
