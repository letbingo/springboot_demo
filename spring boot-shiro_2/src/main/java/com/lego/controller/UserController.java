package com.lego.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    /**
     * 用户查询
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("userInfo:view")
    public String userInfo() {
        return "userInfo";
    }

    /**
     * 用户添加
     *
     * @return
     */
    @RequestMapping("/add")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户删除
     *
     * @return
     */
    @RequestMapping("/del")
    @RequiresPermissions("userInfo:del")
    public String userDel() {
        return "userInfoDel";
    }
}