package com.lego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lego.pojo.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    @Select("select r.role,p.url from sys_role r left join role_permission rp on r.id = rp.role_id " +
            "left join sys_permission p on rp.permission_id = p.id")
    List<RolePermission> getRolePermission();
}
