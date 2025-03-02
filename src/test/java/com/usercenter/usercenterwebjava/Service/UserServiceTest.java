package com.usercenter.usercenterwebjava.Service;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.usercenter.usercenterwebjava.Mapper.UserMapper;
import com.usercenter.usercenterwebjava.Model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 用户服务测试
 *
 * @author myxz
 * */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    @Test
    void testDigest() throws NoSuchAlgorithmException {
        String newPassword  = DigestUtils.md5DigestAsHex("myxz123456".getBytes());
        System.out.println(newPassword);

    }
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("myxzadmin");
        user.setUserAccount("myxzadmin");
        user.setAvatarUrl("https://pic1.zhimg.com/70/v2-92392172531ba8e252e3f9afaa4232d2_1440w.avis?source=172ae18b&biz_tag=Post");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123456");https://pic1.zhimg.com/70/v2-92392172531ba8e252e3f9afaa4232d2_1440w.avis?source=172ae18b&biz_tag=Post
        user.setEmail("123456");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean res =  userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(res);
    }

    @Test
    public void userRegister() {
        // 测试用户名为空
        String userAccount = "myxzadmin";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试用户名长度不足
        userAccount = "my";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试密码长度不足
        userPassword = "1234";
        checkPassword = "1234";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试密码不匹配
        userPassword = "12345678";
        checkPassword = "87654321";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试用户名包含特殊字符
        userAccount = "my +xz";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试用户名重复
        userAccount = "myxzadmin";
        // 预先插入一个用户
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        // 测试成功注册
        userAccount = "myxzadmintest";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}