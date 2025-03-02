package com.usercenter.usercenterwebjava.Mapper;

import com.usercenter.usercenterwebjava.Model.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 17929
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2025-02-26 16:24:42
* @Entity com.usercenter.usercenterwebjava.Model.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




