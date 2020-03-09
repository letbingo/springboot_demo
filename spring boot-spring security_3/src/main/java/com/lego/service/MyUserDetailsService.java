package com.lego.service;

import com.lego.entity.Role;
import com.lego.entity.User;
import com.lego.mapper.RoleMapper;
import com.lego.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 根据用户名获取该用户的所有信息， 包括用户信息和权限点
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 查询数据库
        User user = userMapper.loadUserByUsername(userName);
        if (null != user) {
            List<Role> roles = roleMapper.getRolesByUserId(user.getId());
            user.setAuthorities(roles);
        }
        return user;
    }
}
