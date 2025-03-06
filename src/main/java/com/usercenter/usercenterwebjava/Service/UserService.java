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
 * 用户更新
 *
 * @return 返回脱敏后的用户信息
 */
    Object userUpdate( Long id, String username, String stuId,String className,String avatarUrl,Integer gender,String phone,String email,Integer userStatus,Integer isDelete,Integer userRole,HttpServletRequest request);
    /**
     *
     * 用户退出
     * @param request 请求
     * @return 退出结果
     *
     */
    int userLogout(HttpServletRequest request);



    /**
     *
     * 用户id查询
     * @param id 用户id
     * @param request 请求
     * @return 用户信息
     *
     */
    User userIdInfo( Long id,HttpServletRequest request);
    /**
     *
     * 用户删除
     * @param id 用户id
     * @param request 请求
     * @return 删除结果
     *
     */
    int userDelete(Long id,  HttpServletRequest request);

    User userIdInfoPassword( Long id,HttpServletRequest request);
    User userPassword(Long id, String userPassword, String userNewPassword, String checkNewPassword,HttpServletRequest request);
}

