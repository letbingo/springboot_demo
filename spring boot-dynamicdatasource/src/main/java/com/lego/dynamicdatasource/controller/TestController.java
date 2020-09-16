package com.lego.dynamicdatasource.controller;

import com.lego.dynamicdatasource.pojo.User;
import com.lego.dynamicdatasource.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class TestController {

    @Autowired
    UserService userService;

    @GetMapping("/get/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @GetMapping("/add")
    public int addUser() {
        User user=new User();
        user.setUsername("测试用户");
        user.setEmail("test@gmail.com");
        user.setAge(16);
        return userService.addUser(user);
    }
}
