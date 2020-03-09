package com.lego.mapper;

import com.lego.entity.RolePermisson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    @Select("select a.name as rolename,c.url from role as a left join role_permission b " +
            "on a.id=b.role_id left join permission as c on b.permission_id=c.id")
    List<RolePermisson> getRolePermissions();

}
