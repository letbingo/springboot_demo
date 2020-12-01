package com.lego.mapper;

import com.lego.pojo.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    @Select("select distinct p.id,p.permit_name from sys_permit p left join " +
            "sys_role_permit rp on rp.permit_id=p.id left join sys_role r on " +
            "r.id=rp.role_id left join sys_user_role ur on ur.role_id=r.id left join " +
            "sys_user u on u.id=ur.user_id where u.id = #{nUserId}")
    List<SysPermission> getPermissionsByUserId(@Param("nUserId") int nUserId);
}
