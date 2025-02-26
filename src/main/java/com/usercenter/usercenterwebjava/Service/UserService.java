package com.usercenter.usercenterwebjava.Service;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17929
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-02-26 16:24:42
*/
public interface UserService extends IService<User> {

    void save();
}
