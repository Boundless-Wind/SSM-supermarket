package com.supermarket.controller;

import com.alibaba.fastjson.JSONArray;
import com.supermarket.domain.User;
import com.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    // /**
    //  //  * 默认页面
    //  //  *
    //  //  * @return login.html
    //  //  */
    // @GetMapping("/")
    // public String firstpage(){
    //     return "login.html";
    // }

    /**
     * 登录页面、主页
     *
     * @return login.html
     */
    @GetMapping({"/login", "/"})
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
     * 登录成功返回welcome，失败跳转失败页面
     *
     * @param session session
     * @return welcome.html
     */
    @GetMapping("/welcome")
    public String welcome(HttpSession session) {
        // 如果用户没有登录就回到login
        if (session.getAttribute("user") == null) return "redirect:/syserror";
        return "welcome";
    }

    /**
     * 退出登录
     *
     * @param session session
     * @return login.html
     */
    @GetMapping("/logout")  // 退出登录页面
    public String logout(HttpSession session) {
        session.removeAttribute("user"); // 清除掉Session中user的值
        return "redirect:/login"; // 回到login.sjp
    }

    /**
     * 出错页面
     *
     * @return syserror.html
     */
    @GetMapping("/syserror") // 出错页面
    public String sysError() {
        return "syserror";
    }

    /**
     * 判断密码是否正确
     *
     * @param oldpassword 旧密码
     */
    @GetMapping("/pwdexists")
    @ResponseBody
    public String pwdExists(@RequestParam String oldpassword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user.getUserPassword().equals(oldpassword)) {
            return JSONArray.toJSONString("{\"result\":\"true\"}");
        }
        return JSONArray.toJSONString("{\"result\":\"false\"}");
    }

    /**
     * 修改密码页面
     *
     * @return pwdmodify.html
     */
    @GetMapping("/pwdmodify")
    public String pwdModify() {
        return "pwdmodify";
    }

    /**
     * 修改密码处理
     *
     * @param newpassword 新密码
     * @param session     session
     * @return welcome.html
     */
    @PostMapping("/pwdmodifysave")
    public String pwdModifysave(@RequestParam String newpassword, HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setUserPassword(newpassword);
        user.setModifyBy(user.getId());
        user.setModifyDate(new Date());
        if (userService.modify(user)) {
            return "redirect:/welcome";
        }
        return "/pwdmodify";
    }
}
