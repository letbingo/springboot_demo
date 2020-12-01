package com.lego.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lego.pojo.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<SysRole> {
    @Select("select r.id,r.role_code from sys_role r left join sys_user_role ur on r.id = ur.role_id where " +
            "user_id = #{userId} order by id")
    List<SysRole> getRolesByUserId(@Param("userId") int userId);
}
