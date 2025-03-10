package com.usercenter.usercenterwebjava.Service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usercenter.usercenterwebjava.Common.ErrorCode;
import com.usercenter.usercenterwebjava.Exception.BusinessException;
import com.usercenter.usercenterwebjava.Model.domain.User;
import com.usercenter.usercenterwebjava.Service.UserService;
import com.usercenter.usercenterwebjava.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.usercenter.usercenterwebjava.Contant.UserConstant.ADMIN_ROLE;
import static com.usercenter.usercenterwebjava.Contant.UserConstant.USER_LOGIN_STATE;


/**
 *
 * 用户服务实现类
 *
* @author myxz
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 加盐，私钥，混淆密码
     */
    private static final String SALT = "myxz";



    /**
     *
     * 用户脱敏
     *
     * @return 脱敏后的用户
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setClassName(originUser.getClassName());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setStuId(originUser.getStuId());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }


    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param stuId 学号
     * @return 返回用户注册后的id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String stuId) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, stuId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (stuId.length() > 20) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号号过长");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;,\\\\.<>/?！￥…（）—【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 学号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("stuId", stuId);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "学号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setStuId(stuId);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }


    /**
     *
     * 用户登录
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 登录后返回用户信息
     */

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request ) {
// 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    /**
     * 是否为管理员
     *
     */
    public boolean isAdmin(HttpServletRequest request) {
        // 系统管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE&& user.getId() == 1;
    }

    /**
     *更新用户信息
     */
    @Override
    public User userUpdate(Long id, String username, String stuId, String className, String avatarUrl, Integer gender, String phone, String email, Integer userStatus, Integer isDelete, Integer userRole, HttpServletRequest request) throws BusinessException {
        // 检查是否为管理员
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限修改用户信息");
        }
        // 验证用户ID是否存在
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        }
        // 根据ID查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        // 更新用户信息
        user.setUsername(username);
        user.setStuId(stuId);
        user.setClassName(className);
        user.setAvatarUrl(avatarUrl);
        user.setGender(gender);
        user.setPhone(phone);
        user.setEmail(email);
        user.setUserStatus(userStatus);
        user.setIsDelete(isDelete);
        user.setUserRole(userRole);
        // 执行更新操作
        boolean updateResult = this.updateById(user);
        if (!updateResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新用户信息失败");
        }
        // 返回更新后的用户信息
        return this.getSafetyUser(user);
    }



    /**
     *  用户注销
     * @param request 请求
     */

    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }


    /**
     *
     * 获取用户详细信息
     * @param id 用户ID
     * @param request 请求
     * @return 用户ID信息
     *
     */
    @Override
    public User userIdInfo(Long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取用户信息
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        // 返回用户信息
        return getSafetyUser(user);
    }


    /**
     *
     * 删除用户
     * @param id 用户ID
     * @param request 请求
     * @return 删除结果
     *
     */
    @Override
    public int userDelete(Long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "缺少管理员权限");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 新增验证逻辑：检查 id 是否等于 1 且 userRole 是否等于 1
        User user = userMapper.selectById(id); // 获取用户信息
        if (id.equals(1L) && user.getUserRole() == 1) {
            throw new BusinessException(ErrorCode.NO_DELETE_SYSTEM_ADMIN, "系统管理员无法删除");
        }
       return userMapper.deleteById(id);
    }

    /**
     *
     * 修改用户密码
     * @param request 请求
     *
     */
    @Override
    public User userPassword(Long id, String userPassword, String userNewPassword, String checkNewPassword,HttpServletRequest request) throws BusinessException {
        if (request == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "未登录");
        }
        // 验证用户ID是否存在
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        }
        if (StringUtils.isAnyBlank(userPassword,userNewPassword,checkNewPassword )) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (!userNewPassword.equals(checkNewPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次密码不一致");
        }
        // 2. 加密旧密码
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
//         加密新密码
        String encryptNewPassword = DigestUtils.md5DigestAsHex((SALT + userNewPassword).getBytes());
        // 根据ID查询用户
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        if (!encryptPassword.equals(user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "旧密码错误");
        }
        if (encryptNewPassword.equals(user.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "新密码不能与旧密码相同");
        }
        // 更新用户信息
        user.setUserPassword(encryptNewPassword);
        // 执行更新操作
        boolean userPasswordResult = this.updateById(user);
        if (!userPasswordResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "修改密码失败");
        }
        // 返回更新后的用户信息
        return this.getSafetyUser(user);
    }

    /**
     *
     * 获取用户详细信息
     * @param id 用户ID
     * @param request 请求
     * @return 用户ID信息
     *
     */
    @Override
    public User userIdInfoPassword(Long id, HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NO_AUTH, "未登录");
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 获取用户信息
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "用户不存在");
        }
        // 返回用户信息
        return getSafetyUser(user);
    }

}



