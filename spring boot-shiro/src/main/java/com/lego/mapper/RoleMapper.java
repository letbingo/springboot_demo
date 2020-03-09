package com.lego.mapper;

import com.lego.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("select r.role from user_info u left join " +
            "sys_user_role ur on u.uid = ur.uid left join " +
            "sys_role r on r.id = ur.role_id where " +
            "u.username = #{sUserName}")
    List<Role> getRoles(String sUserName);
}
