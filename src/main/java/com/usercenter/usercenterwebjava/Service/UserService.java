package com.usercenter.usercenterwebjava.Service;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

 /**
 *
 * User Service
 *
 * @author 明裕学长
 *
 */
@Service
public interface UserService extends IService<User> {

    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     *
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);


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

}

