package com.supermarket;

import com.supermarket.domain.User;
import com.supermarket.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTests extends BaseTests {

    @Autowired
    private UserService userService;

    /**
     * 测试用户登录
     */
    @Test
    void testGetUserByUserCode() {
        String userCode = "zhanghua";
        User user = userService.getUserByUserCode(userCode);
        System.out.println("用户帐号: " + user.getUserCode() + "，用户密码: " + user.getUserPassword() + "，用户名:" + user.getUserName() + "，用户地址:" + user.getAddress());
    }

    /**
     * 测试用户列表
     */
    @Test
    public void testGetUserListByPage() {
        String userName = null;
        int userRole = 0;
        List<User> userList = userService.getUserListByPage(userName, userRole);
        for (User user : userList) {
            System.out.println("ID:" + user.getId() + " 用户帐号：" + user.getUserCode() + " 用户姓名:" + user.getUserName() + " 用户角色:" + user.getRole().getRoleName() + " 地址:" + user.getAddress());
        }
    }

    /**
     * 测试添加用户
     */
    @Test
    public void testAddUser() {
        User user = new User();
        user.setUserCode("zhangsan");
        user.setUserName("张三");
        user.setUserPassword("<PASSWORD>");
        user.setAddress("上海");
        user.setUserRole(1);
        boolean flag = userService.add(user);
        System.out.println(flag);
    }

    /**
     * 测试添加用户，生日为null
     */
    @Test
    public void testAddUserWithNullBirthday() {
        User user = new User();
        user.setUserCode("zhangsan");
        user.setBirthday(null);
        boolean flag = userService.add(user);
        System.out.println(flag);
    }

    /**
     * 测试添加用户，id重复。就算传递id，插入的mapper也会忽略id
     */
    @Test
    public void testAddUserWithEmptyBirthday() {
        User user = new User();
        user.setId(1);
        user.setUserCode("zhangsan");
        boolean flag = userService.add(user);
        System.out.println(flag);
    }

    /**
     * 测试修改用户
     */
    @Test
    public void testModifyUser() {
        User user = new User();
        user.setId(1);
        user.setUserName("李四");
        boolean flag = userService.modify(user);
        System.out.println(flag);
    }

}
