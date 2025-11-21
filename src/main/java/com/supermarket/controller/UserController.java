package com.supermarket.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supermarket.domain.Bill;
import com.supermarket.domain.Provider;
import com.supermarket.domain.Role;
import com.supermarket.domain.User;
import com.supermarket.service.BillService;
import com.supermarket.service.ProviderService;
import com.supermarket.service.RoleService;
import com.supermarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private BillService billService;

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

        return "userlist";
    }

    /**
     * 供应商列表
     *
     * @param model             模型
     * @param session           session
     * @param queryProviderName 供应商名称
     * @param pageNum           页码
     * @return providerlist.html
     */
    @RequestMapping("/providerlist")
    public String getProviderList(Model model, HttpSession session, @RequestParam(value = "queryProviderName", required = false) String queryProviderName, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        if (session.getAttribute("user") == null) {
            return "redirect:/syserror";
        }
        System.out.println("供应商姓名:" + queryProviderName);

        // 显示传入的参数
        model.addAttribute("queryProviderName", queryProviderName); // 传递查询的供应商名给前端

        // 开启分页
        PageHelper.startPage(pageNum, 5);
        List<Provider> providerList = providerService.getProviderListByPage(queryProviderName);// 查询供应商列表
        model.addAttribute("pageInfo", new PageInfo<>(providerList));// 将查询出的值传给前端

        return "providerlist";
    }

    /**
     * 订单列表
     *
     * @param model           模型
     * @param session         session
     * @param queryBillName   订单名称
     * @param queryProviderId 订单供货商
     * @param pageNum         页码
     * @return billlist.html
     */
    @RequestMapping("/billlist")
    public String getBillList(Model model, HttpSession session, @RequestParam(value = "queryBillName", required = false) String queryBillName, @RequestParam(value = "queryProviderId", required = false) String queryProviderId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        if (session.getAttribute("user") == null) {
            return "redirect:/syserror";
        }
        System.out.println("订单姓名:" + queryBillName + ",订单供货商:" + queryProviderId);

        // 将供货商id赋值给_queryProviderId
        int _queryProviderId = 0;  // 给订单供货商赋值
        if (queryProviderId != null && !queryProviderId.trim().isEmpty())   // 如果前端传过来的供货商不为空
            _queryProviderId = Integer.parseInt(queryProviderId);

        // 显示传入的参数
        model.addAttribute("queryProviderId", queryProviderId);// 传递查询的供应商id给前端
        model.addAttribute("queryBillName", queryBillName); // 传递查询的用户名给前端

        // 查询供应商信息
        List<Provider> providerList = providerService.getProviderListByPage(null);  // 读取供应商表信息，传入空值返回全部供应商
        model.addAttribute("providerList", providerList); // 传递供应商表值给前端

        // 开启分页，必须在查询之前开启
        PageHelper.startPage(pageNum, 5);
        List<Bill> billList = billService.getBillListByPage(queryBillName, _queryProviderId);// 查询订单列表
        model.addAttribute("pageInfo", new PageInfo<>(billList));// 将查询出的值传给前端

        return "billlist";
    }

}
