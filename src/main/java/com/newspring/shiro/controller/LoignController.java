package com.newspring.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("user")
public class LoignController {
    //    @RequestMapping(value = "login",method = RequestMethod.POST)
    @GetMapping(value = "/tologin")
    public String login() {
        System.out.println("去登录页面");
        return "/login/login";
    }

    //未授权页面
    @RequestMapping("/toUnauth")
    public String unauth() {
        return "/unauth";
    }

    // 登录成功
    @RequestMapping(value = "/xiaoyan")
    public String tologin(String name, String password, org.springframework.ui.Model model) {
        /**
         * 使用shiro编写认证操作
         *
         */
        //1.获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        //3.执行登录方法
        try {
            subject.login(token);
            System.out.println("登录成功");
            return "redirect:/toindex";
        } catch (UnknownAccountException e) {
            model.addAttribute("msg", "用户名不存在");
            System.out.println("用户名不存在");
            return "/login/login";
        } catch (IncorrectCredentialsException e) {
            model.addAttribute("msg", "密码错误");
            System.out.println("密码错误");
            return "/login/login";
        }


    }
}
