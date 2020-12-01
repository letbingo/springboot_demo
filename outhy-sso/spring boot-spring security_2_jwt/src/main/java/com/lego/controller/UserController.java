package com.lego.controller;

import com.lego.common.RespCode;
import com.lego.common.RespResult;
import com.lego.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录、角色管理控制器
 *
 * @author lego 2019/12/13
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    protected BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 返回未登录信息
     *
     * @return
     */
    @RequestMapping("/unlogin")
    public String unlogin() {
        return new RespResult(RespCode.UnLogin).toString();
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @RequestMapping("/info")
    public String getUserInfo() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new RespResult(RespCode.OperSuccess.getCode(), "", user, "userinfo").toString();
    }
}
