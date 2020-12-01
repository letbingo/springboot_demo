package com.lego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lego.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select id,username,password,realname from sys_user where username = #{username}")
    User loadUserByUsername(@Param("username") String username);
}
