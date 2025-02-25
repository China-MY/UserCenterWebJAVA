package com.usercenter.usercenterwebjava;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.usercenter.usercenterwebjava.mapper")
public class UserCenterWebJavaApplication {

    public static void main(String[] args) {

        SpringApplication.run(UserCenterWebJavaApplication.class, args);

    }

}
