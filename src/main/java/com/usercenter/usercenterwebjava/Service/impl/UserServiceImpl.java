package com.usercenter.usercenterwebjava.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.usercenter.usercenterwebjava.Model.domain.User;
import com.usercenter.usercenterwebjava.Service.UserService;
import com.usercenter.usercenterwebjava.Mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 17929
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-02-26 16:24:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public void save() {

    }
}




