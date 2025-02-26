package com.usercenter.usercenterwebjava;

import com.usercenter.usercenterwebjava.Mapper.UserMapper;
import com.usercenter.usercenterwebjava.Model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest("UserCenterWebJavaApplication")
class UserCenterWebJavaApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

}
