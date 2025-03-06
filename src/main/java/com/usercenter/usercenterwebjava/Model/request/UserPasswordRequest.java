package com.usercenter.usercenterwebjava.Model.request;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserPasswordRequest  implements Serializable {
    private static final long serialVersionUID = 3191241716373120793L;


    private Long id;

    /**
     * 用户密码
     */
    private String userPassword;
    /**
     * 新密码
     */
    private String userNewPassword;

    /**
     * 校验密码
     */
    private String checkNewPassword;

}
