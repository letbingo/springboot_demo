package com.lego.demo_springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;

import java.util.Date;



@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String Hello(Model m) {
//        m.addAttribute("now", DateFormat.getDateTimeInstance().format(new Date()));
        return "hello";
    }
}
