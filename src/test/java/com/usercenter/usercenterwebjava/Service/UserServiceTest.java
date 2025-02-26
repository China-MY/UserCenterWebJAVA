package com.usercenter.usercenterwebjava.Service;
import java.util.Date;

import com.usercenter.usercenterwebjava.Model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("TESTMY");
        user.setUserAccount("123");
        user.setAvatarUrl("https://pic1.zhimg.com/70/v2-92392172531ba8e252e3f9afaa4232d2_1440w.avis?source=172ae18b&biz_tag=Post");
        user.setGender(0);
        user.setUserPassword("xxxx");
        user.setPhone("123");
        user.setEmail("456");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        boolean res =  userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(res);
    }
}