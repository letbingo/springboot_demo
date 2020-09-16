package com.lego.dynamicdatasource.service;

import com.lego.dynamicdatasource.datasource.DynamicDataSource;
import com.lego.dynamicdatasource.datasource.DynamicDataSourceType;
import com.lego.dynamicdatasource.mapper.UserMapper;
import com.lego.dynamicdatasource.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 根据注解中的参数指定使用·哪一个数据源
 */
@Component
public class UserService {

    @Autowired
    UserMapper userMapper;

    @DynamicDataSource(value = DynamicDataSourceType.secondary)
    public User getUser(int id) {
        return userMapper.getUserbyId(id);
    }

    @DynamicDataSource(value = DynamicDataSourceType.primary)
    public int addUser(User user) {
        return userMapper.addUserInfo(user);
    }
}
