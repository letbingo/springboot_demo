package com.lego.dynamicdatasource.mapper;

import com.lego.dynamicdatasource.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserMapper {

    @Select("select * from user where id = #{id}")
    User getUserbyId(int id);

    @Insert("insert into user values (#{id},#{username},#{email},#{age})")
    int addUserInfo(User user);
}
