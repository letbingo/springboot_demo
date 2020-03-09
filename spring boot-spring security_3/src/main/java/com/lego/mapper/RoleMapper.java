package com.lego.mapper;

import com.lego.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    @Select("select a.id,a.name from role a left join user_role b on a.id=b.role_id where " +
            "b.user_id=${userId}")
    List<Role> getRolesByUserId(@Param("userId") Long userId);

}
