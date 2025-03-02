package com.usercenter.usercenterwebjava.Service;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author myxz
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-02-26 16:24:42
*/
public interface UserService extends IService<User> {

    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


    /**
     *
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 用户 id
     */
    long userLogin(String userAccount, String userPassword);

}
