package com.supermarket;

import com.supermarket.domain.User;
import com.supermarket.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootCourseApplicationTests {

    @Autowired
    private UserService userService;

    /**
     * 正常性测试
     */
    @Test
    void contextLoads() {
        System.out.println("SpringBootCourseApplicationTests");
    }

    /**
     * 测试用户登录
     */
    @Test
    void testGetUserByUserCode(){
        String userCode="zhanghua";
        User user = userService.getUserByUserCode(userCode);
        System.out.println("用户帐号: " + user.getUserCode() +
                "，用户密码: "+user.getUserPassword()+
                "，用户名:"+ user.getUserName() +
                "，用户地址:"+user.getAddress());
    }

}