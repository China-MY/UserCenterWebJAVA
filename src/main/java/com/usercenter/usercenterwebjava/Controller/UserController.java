package com.usercenter.usercenterwebjava.Controller;


import com.usercenter.usercenterwebjava.Model.domain.User;
import com.usercenter.usercenterwebjava.Model.request.UserLoginRequest;
import com.usercenter.usercenterwebjava.Model.request.UserRegisterRequest;
import com.usercenter.usercenterwebjava.Service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 *
 * @author 明裕学长
 */
@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    /**
     *
     * 注册
     *
     */
    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null){
            return  null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return  null;
        }
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }


    /**
     *
     * 登录
     *
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest,HttpServletRequest request) {
        if (userLoginRequest == null){
            return  null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            return  null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    /**
     * 查询用户
     *
     * @param username 昵称
     * @param userAccount 账户
     * @return 用户信息列表
     */
    @PostMapping("/search")
    public List<User> searchUsers(@RequestParam(required = false) String username, @RequestParam(required = false) String userAccount) {
        return userService.searchUsers(username, userAccount);
    }

    /**
     *
     * 删除用户
     *
     */
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id){
        if (id <= 0){
            return false;
        }
        return userService.removeById(id);
    }

}
