package com.supermarket;

import com.supermarket.domain.User;
import com.supermarket.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTests {

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

}
