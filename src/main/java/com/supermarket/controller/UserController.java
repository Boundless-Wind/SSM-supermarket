package com.supermarket.controller;

import com.supermarket.domain.User;
import com.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录页面
     *
     * @return login.html
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 登录处理
     *
     * @param userCode     用户账号
     * @param userPassword 用户密码
     * @param model        模型
     * @param session      session
     * @return welcome.html
     */
    @PostMapping("/dologin")
    public String doLogin(@RequestParam String userCode, @RequestParam String userPassword, Model model, HttpSession session) {
        // 读取用户和密码
        System.out.println("帐号和密码是" + userCode + "-" + userPassword);
        User user = userService.getUserByUserCode(userCode);    // 从数据库中读取真实的密码

        // 登录失败，回到login.html
        if (user == null || !userPassword.equals(user.getUserPassword())) {
            model.addAttribute("error", "用户名或密码不正确");
            return "login";
        }

        // 登录成功，转到welcome.html
        session.setAttribute("user", user); // 添加session值
        return "redirect:/welcome";   // 密码正确就跳转去welcome.html
    }

    /**
     * 欢迎页面
     *
     * @return welcome.html
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
