package com.usercenter.usercenterwebjava.Service;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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


    /**
     * 查询用户
     *
     * @param username 昵称
     * @param userAccount 账户
     * @return 用户信息列表
     */
     List<User> searchUsers(String username, String userAccount);


     /**
      *
      * 删除用户
      *
      * @param id 用户id
      * @return 返回 成功或失败
      */
    User deleteUser(long id);

     /**
      *
      * 增加用户
      *
      * @param user 用户类
      * @return  返回新增后的用户内容
      */
    User addUser(User user);

     /**
      *
      * 更新用户
      *
      * @param user 用户类
      * @return 返回更新后的值
      */
    User updateUser(User user);
}

