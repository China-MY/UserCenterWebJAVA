package com.usercenter.usercenterwebjava.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.usercenter.usercenterwebjava.Common.BaseResponse;
import com.usercenter.usercenterwebjava.Common.ErrorCode;
import com.usercenter.usercenterwebjava.Common.ResultUtils;
import com.usercenter.usercenterwebjava.Exception.BusinessException;
import com.usercenter.usercenterwebjava.Model.domain.User;
import com.usercenter.usercenterwebjava.Model.request.DeleteUserRequest;
import com.usercenter.usercenterwebjava.Model.request.UserLoginRequest;
import com.usercenter.usercenterwebjava.Model.request.UserRegisterRequest;
import com.usercenter.usercenterwebjava.Service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.usercenter.usercenterwebjava.Contant.UserConstant.ADMIN_ROLE;
import static com.usercenter.usercenterwebjava.Contant.UserConstant.USER_LOGIN_STATE;

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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String stuId = userRegisterRequest.getStuId();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, stuId)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, stuId);
        return ResultUtils.success(result);
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public BaseResponse userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }


    /**
     *
     * 注销
     *
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }
    /**
     * 是否为管理员
     *
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user == null || user.getUserRole() != ADMIN_ROLE;
    }

    /**
     * 获取当前用户
     *
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // TODO 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 搜索用户
     * @param username 用户昵称
     * @param request 请求
     * @return 数据
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    /**
     * 删除用户
     * @param request 请求
     * @return 成功
     * @throws BusinessException 业务异常
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteUserRequest requestDto, HttpServletRequest request) {
        if (isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        long id = requestDto.getId();
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }
}
