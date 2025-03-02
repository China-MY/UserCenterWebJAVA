package com.usercenter.usercenterwebjava.Service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usercenter.usercenterwebjava.Model.domain.User;
import com.usercenter.usercenterwebjava.Service.UserService;
import com.usercenter.usercenterwebjava.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author myxz
* &#064;description  针对表【user(用户表)】的数据库操作Service实现
* &#064;createDate  2025-02-26 16:24:42
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
     * 用户的登录状态
     */
    public static final  String USER_LOGIN_STATE = "user_login_state";

    /**
     * 数据脱敏
     * @param user 数据脱毛
     * @return 返回脱敏数据
     */
    @NotNull
    public static User safeUser(User user) {
        User safeUser = new User();
        safeUser.setId(user.getId());
        safeUser.setUsername(user.getUsername());
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setAvatarUrl(user.getAvatarUrl());
        safeUser.setGender(user.getGender());
        safeUser.setPhone(user.getPhone());
        safeUser.setEmail(user.getEmail());
        safeUser.setUserStatus(user.getUserStatus());
        safeUser.setCreateTime(user.getCreateTime());
        safeUser.setUpdateTime(user.getUpdateTime());
        safeUser.setUserRole(user.getUserRole());
        return safeUser;
    }

    /**
     *
     * 用户注册
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 返回用户注册后的id
     */
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8|| checkPassword.length() < 8) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }
        //账户不能包含特殊字符
        String validPattern = "\\u00A0\\s\\p{Punct}&&[^;]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

        //  账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (saveResult) {
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
        // 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        //账户不能包含特殊字符
        String validPattern = "\\u00A0\\s\\p{Punct}&&[^;]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //  查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //        用户不存在
        if (user == null) {
            log.info("user login fail, userAccount cannot match userPassword");
            return null;
        }
        //  用户脱敏
        User safeUser = safeUser(user);

        //        记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
        return safeUser;
    }


    /**
     * 查询用户
     *
     * @param username    昵称
     * @param userAccount 账户
     * @return 用户信息
     */
    @Override
    public List<User> searchUsers(String username, String userAccount) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        // 检查参数是否为空，并根据条件拼接查询
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.or().like("userAccount", userAccount);
        }
        // 查询用户信息
        List<User> userList = userMapper.selectList(queryWrapper);
        // 对用户数据进行脱敏处理
        List<User> safeUserList = new ArrayList<>();
        for (User user : userList) {
            safeUserList.add(safeUser(user));
        }
        return safeUserList;
    }


    /**
     *
     * 删除用户
     *
     * @param id 用户id
     * @return 成功或失败
     */
    @Override
    public User deleteUser(long id) {
        return null;
    }

    /**
     *
     * 增加用户
     *
     * @param user 用户类
     * @return 返回成功或失败
     */

    @Override
    public User addUser(User user) {
        return null;
    }

    /**
     *
     * 更新用户
     *
     * @param user 用户类
     * @return 返回成功或失败
     */
    @Override
    public User updateUser(User user) {
        return null;
    }


}



