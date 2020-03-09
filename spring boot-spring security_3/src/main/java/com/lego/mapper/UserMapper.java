package com.lego.mapper;

import com.lego.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Select("select id, username, password from user where username = #{username}")
    User loadUserByUsername(@Param("username") String username);
}
