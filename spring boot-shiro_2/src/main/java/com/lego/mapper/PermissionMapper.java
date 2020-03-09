package com.lego.mapper;

import com.lego.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {
    @Select("select p.permission from user_info u " +
            "left join sys_user_role ru on u.uid = ru.uid " +
            "left join sys_role r on r.id = ru.role_id " +
            "left join sys_role_permission rp on r.id = rp.role_id " +
            "left join sys_permission p on p.id = rp.permission_id " +
            "where p.permission is not null and u.username =#{sUserName}")
    List<Permission> getPermissions(String sUserName);
}
