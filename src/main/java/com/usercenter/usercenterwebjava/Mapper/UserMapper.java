package com.usercenter.usercenterwebjava.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.usercenter.usercenterwebjava.Model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
