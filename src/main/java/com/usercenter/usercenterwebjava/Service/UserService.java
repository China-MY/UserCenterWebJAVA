package com.usercenter.usercenterwebjava.Service;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * 用户服务
 * User Service
 *
 * @author 明裕学长
 *
 */
@Service
public interface UserService extends IService<User> {


    /**
     * 获取脱敏后的用户信息
     * @param originalUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    User getSafetyUser(User originalUser);

    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param stuId 学号
     *
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String stuId);


    /**
     *
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 返回脱敏后的用户信息
     *
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request) ;


    /**
     *
     * 用户退出
     * @param request 请求
     * @return 退出结果
     *
     */
    int userLogout(HttpServletRequest request);


}

