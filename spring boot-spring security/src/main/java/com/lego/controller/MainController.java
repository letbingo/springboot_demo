package com.lego.controller;

import com.lego.entity.RespBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用注解方式指定权限需要加注解：@EnableGlobalMethodSecurity(prePostEnabled = true)
 */
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login_p")
    public RespBean login_p() {
        return RespBean.error("尚未登录，请登录!");
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/user/common")
    @PreAuthorize("hasAuthority('common')")
    public String common() {
        return "user/common";
    }

    @GetMapping("/user/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin() {
        return "user/admin";
    }


}
