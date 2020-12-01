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
    @Select("select r.role_code,p.url from sys_role r left join sys_role_permit rp on r.id = rp.role_id " +
            "left join sys_permit p on rp.permit_id = p.id")
    List<RolePermission> getRolePermission();
}
