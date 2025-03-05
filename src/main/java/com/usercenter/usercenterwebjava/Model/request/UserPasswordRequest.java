package com.usercenter.usercenterwebjava.Model.request;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserPasswordRequest  implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 新密码
     */
    private String NewuserPassword;

    /**
     * 校验密码
     */
    private String checkNewPassword;

}
