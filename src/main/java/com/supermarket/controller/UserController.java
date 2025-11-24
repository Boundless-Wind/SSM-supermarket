package com.supermarket.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supermarket.domain.Role;
import com.supermarket.domain.User;
import com.supermarket.service.RoleService;
import com.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    /**
     * 用户列表
     *
     * @param model         模型
     * @param session       session
     * @param queryUserName 用户名称
     * @param queryUserRole 用户角色
     * @param pageNum       页码
     * @return userlist.html
     */
    @RequestMapping("/userlist")
    // @RequestMapping如果没有指定请求方式，将接收所有的请求方式（GET，POST）
    public String getUserList(Model model, HttpSession session, @RequestParam(value = "queryUserName", required = false) String queryUserName, @RequestParam(value = "queryUserRole", required = false) String queryUserRole, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        // 未登录踢出
        if (session.getAttribute("user") == null)   // 如果用户没有登录就直接来到userlist就回到syserror
            return "redirect:/syserror";
        System.out.println("用户姓名:" + queryUserName + ",用户角色:" + queryUserRole);

        // 将用户角色id赋值给_queryUserRole
        int _queryUserRole = 0;  // 给用户角色赋值
        if (queryUserRole != null && !queryUserRole.trim().isEmpty())   // 如果前端传过来的角色不为空
            _queryUserRole = Integer.parseInt(queryUserRole);

        // 显示传入的参数
        model.addAttribute("queryUserName", queryUserName); // 传递查询的用户名给前端
        model.addAttribute("queryUserRole", queryUserRole);// 传递查询的角色id给前端

        // 查询角色信息
        List<Role> roleList = roleService.getRoleList();  // 读取用色表信息
        model.addAttribute("roleList", roleList); // 传递角色表值给前端

        // // 开启分页
        PageHelper.startPage(pageNum, 5);
        List<User> userList = userService.getUserListByPage(queryUserName, _queryUserRole);// 查询用户列表
        model.addAttribute("pageInfo", new PageInfo<>(userList));// 将查询出的值传给前端

        return "user/userlist";
    }

    /**
     * 添加用户页面
     *
     * @return useradd.html
     */
    @RequestMapping("/useradd")   // 打开添加页面
    public String addUser() {
        return "user/useradd";
    }

    /**
     * 添加用户数据
     *
     * @param user    用户
     * @param session session
     * @return useradd.html
     */
    @RequestMapping(value = "/useraddsave", method = RequestMethod.POST)
    // 以对象方式传值
    // 1. 前端的useradd.html会把Form里的一系列的input控件的值，组合成一个user对象，传给controller端
    // 2. 在进行form表单提交时如果input标签中没有name属性的话，那么该标签中的数据是不会被提交到服务器的。
    // 3. 提交的name的值要和实体类(User)的属性名一致，便于序列化。不一致会造成这个值传递不过去
    // 4. VIEW端向Controller以对象方式传值的时候，Controller端不要加上@RequestParam
    public String addUserSave(User user, HttpSession session) {
        user.setCreatedBy(((User) session.getAttribute("user")).getId());   // 添加用户表的createBy值，代表由当前用户创建
        user.setCreationDate(new Date());   // 添加用户表的createDate值
        if (userService.add(user)) {    // 如果添加成功就返回userlist
            return "redirect:/userlist";
        }
        return "user/useradd";   // 添加不成功留在useradd
    }

    /**
     * 修改用户页面
     *
     * @param userid 用户id
     * @param model  模型
     * @return usermodify.html
     */
    @RequestMapping("/usermodify")  // 打开修改页面
    // 直接标记为参数代表会尝试在post或get里找值，不写value就是get参数
    public String getUserById(@RequestParam int userid, Model model) {
        User user = userService.getUserById(userid);
        model.addAttribute("user", user);
        return "user/usermodify";
    }

    /**
     * 修改用户数据
     *
     * @param user    用户
     * @param session session
     * @return usermodify.html
     */
    @RequestMapping(value = "/usermodifysave", method = RequestMethod.POST)
    public String modifyUserSave(User user, HttpSession session) {
        user.setModifyBy(((User) session.getAttribute("user")).getId());
        user.setModifyDate(new Date());
        if (userService.modify(user)) {
            return "redirect:/userlist";
        }
        return "user/usermodify";
    }

    /**
     * 查看用户详情页面
     *
     * @param userid 用户id
     * @param model  模型
     * @return userview.html
     */
    @RequestMapping(value = "/userview")
    public String view(@RequestParam int userid, Model model) {
        User user = userService.getUserById(userid);
        model.addAttribute("user", user);
        return "user/userview";
    }

//    /**
//     * 根据id发送user数据，以Json格式
//     *
//     * @param uid 用户id
//     * @return User-json对象
//     */
//    @RequestMapping(value = "/getuserjson", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
//    @ResponseBody   // 不再要求返回一个视图，而是返回一个数据，将结果直接写入响应体中
//    public String getuserjson(@RequestParam int uid) {
//        User user = userService.getUserById(uid);
//        String json = JSON.toJSONString(user);// 解析成json格式
//        return JSONArray.toJSONString(json);
//    }

    /**
     * 判断userCode是否存在
     *
     * @param userCode 用户编码
     * @return boolean-json对象
     */
    @RequestMapping(value = "/ucexist", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public Object ucexist(@RequestParam String userCode) {
        String data = "noexist";
        // 前端传入校验，如果userCode是空值，直接返回已存在。后端检测校验，如果不为空，代表存在
        if (userCode == null || userCode.isEmpty() || userService.selectUserCodeExist(userCode) != null) data = "exist";
        // 将data转为json对象,并将结果发回给当前页面
        return JSONArray.toJSONString("{\"userCode\":\"" + data + "\"}");
    }

    /**
     * 删除用户数据
     *
     * @param uid 用户id
     * @return boolean-json对象
     */
    @RequestMapping("/deluser")
    @ResponseBody
    public Object deluser(@RequestParam int uid) {
        String data = "{\"delResult\":\"false\"}";  // 初始化字符串
        boolean result = userService.deleteUserById(uid);
        if (result)
            data = "{\"delResult\":\"true\"}"; // 删除成功
        else
            data = "{\"delResult\":\"false\"}"; // 删除失败
        return JSONArray.toJSONString(data);// 将data转为json对象,并将结果发回给当前页面
    }

}
