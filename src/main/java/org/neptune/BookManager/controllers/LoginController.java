package org.neptune.BookManager.controllers;

import org.neptune.BookManager.biz.LoginBiz;
import org.neptune.BookManager.model.User;
import org.neptune.BookManager.service.UserService;
import org.neptune.BookManager.utils.CookieUtils;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private LoginBiz loginBiz;

    @Autowired
    private UserService userService;

    //登录界面
    @RequestMapping(path = {"/users/login"}, method = {RequestMethod.GET})
    public String login() {
        return "login/login";
    }

    //完成登录
    @RequestMapping(path = {"users/login/do"}, method = {RequestMethod.POST})
    public String doLogin(
            Model model,
            HttpServletResponse response,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        try {
            String ticketInfo = loginBiz.login(email, password);
            CookieUtils.writeCookie("ticketInfo", ticketInfo, response);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "404";
        }
    }

    //完成登出
    @RequestMapping(path = "users/logout/do", method = {RequestMethod.GET})
    public String doLogout(@CookieValue("ticketInfo") String ticketInfo) {
        loginBiz.logout(ticketInfo);
        return "redirect:/index";
    }

    //注册界面
    @RequestMapping(path = {"users/register"}, method = {RequestMethod.GET})
    public String register() {
        return "login/register";
    }

    //完成注册
    @RequestMapping(path = {"users/register"}, method = {RequestMethod.POST})
    public String doRegister(
            Model model,
            HttpServletResponse response,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        try {
            String ticketInfo = loginBiz.register(user);
            CookieUtils.writeCookie("ticketInfo", ticketInfo, response);
            return "redirect:/index";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "404";
        }
    }
}
