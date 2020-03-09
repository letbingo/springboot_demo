package com.lego.controller;

import com.lego.pojo.Result;
import com.lego.pojo.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result doLogin(@RequestBody UserInfo user) throws Exception {
        Result result = new Result();
        try {
            // Subject代表了当前用户的安全操作
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
            subject.login(token);
            String authorization = (String) subject.getSession().getId();
            result.setSuccess(true);
            // 将authorization传给前端，用于MySessionManager中请求的验证
            result.setContent(authorization);
            result.setMessage("登陆成功");
        } catch (IncorrectCredentialsException e) {
            result.setMessage("密码错误");
        } catch (LockedAccountException e) {
            result.setMessage("该用户已被禁用");
        } catch (AuthenticationException e) {
            result.setMessage("该用户不存在");
        }
        return result;
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }
}