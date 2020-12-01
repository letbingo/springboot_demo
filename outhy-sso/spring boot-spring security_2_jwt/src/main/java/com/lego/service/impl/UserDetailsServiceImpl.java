package com.lego.service.impl;

import com.lego.mapper.PermissionMapper;
import com.lego.mapper.UserMapper;
import com.lego.pojo.SysPermission;
import com.lego.pojo.User;
import com.lego.mapper.RoleMapper;
import com.lego.pojo.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据用户名获取该用户的所有信息， 包括用户信息和权限点
 *
 * @author lego 2019/12/12
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据登录时的"username"字段获取用户信息
     *
     * @param userName 这里的username应该是数据库中能唯一确定用户的字段
     * @return 用户信息
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(userName);
        if (null != user) {
            List<SysRole> sysRoles = roleMapper.getRolesByUserId(user.getId());
            user.setAuthorities(sysRoles);
            // 获取List<SysRole>列表中每一个Role的role字段，并转成List<String>对象
            user.setRoles(sysRoles.stream().map(SysRole::getRoleCode).collect(Collectors.toList()));
            // 获取权限列表
            List<SysPermission> lstPermissions = permissionMapper.getPermissionsByUserId(user.getId());
            user.setPermissions(lstPermissions.stream().map(SysPermission::getPermitName).collect(Collectors.toList()));
        }
        return user;
    }
}
