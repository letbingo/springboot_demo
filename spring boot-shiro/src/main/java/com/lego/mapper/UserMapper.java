package com.lego.mapper;

import com.lego.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user_info where username=#{sUserName}")
    UserInfo getUserInfo(String sUserName);
}
