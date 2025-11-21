package com.supermarket;

import com.supermarket.domain.Role;
import com.supermarket.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RoleTests {

    @Autowired
    private RoleService roleService;

    /**
     * 测试获取角色列表
     */
    @Test
    void testGetRoleList() {
        List<Role> roleList = roleService.getRoleList();
        for (Role role : roleList) {
            System.out.println("角色ID: " + role.getId() + "，角色代码: " + role.getRoleCode() + "，角色名称:" + role.getRoleName());
        }
    }

}
