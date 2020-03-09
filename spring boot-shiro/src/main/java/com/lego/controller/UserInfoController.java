package com.lego.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @RequestMapping("/index")
    @RequiresPermissions("userInfo:view")
    public String index() {
        return "index";
    }

    /**
     * 用户查询
     *
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")
    public String userInfo() {
        return "userInfo";
    }

    /**
     * 用户添加
     *
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd() {
        return "userInfoAdd";
    }

    /**
     * 用户删除
     *
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")
    public String userDel() {
        return "userInfoDel";
    }
}